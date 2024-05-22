package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private RedisTemplate<String,String>redisTemplate;


	@Override
	public List<Category> selectOneCategory() {
		//1.查询redis，看redis中是否存在所有的一级分类
		String categoryOneJson = redisTemplate.opsForValue().get("category:one");
		//2.如果redis中包含了所有的一级分类，直接返回就可以
		if(StringUtils.hasText(categoryOneJson)){
			//字符串转换成list集合
			List<Category> categoryList = JSON.parseArray(categoryOneJson, Category.class);
			return categoryList;
		}
		//3.如果没有，就查询数据库，把查询结果返回，并且把查询结果放到redis中
				List<Category> existCategoryList = categoryMapper.selectOneCategory();
		redisTemplate.opsForValue().set("category:one",JSON.toJSONString(existCategoryList),
				7, TimeUnit.DAYS);
		return existCategoryList;
	}


	@Cacheable(value = "category",key="'all'")
    @Override
    public List<Category> findCategoryTree() {
        //1.查询所有的分类，返回list集合
		List<Category> categoryList = categoryMapper.findAll();

		//2.遍历所有分类的list集合，通过条件 parent_id=0，得到所有的一级分类
		List<Category> oneCategoryList = categoryList.stream().
				filter(item -> item.getParentId().longValue() == 0).collect(Collectors.toList());

		//3.得到二级分类
		oneCategoryList.forEach(oneCategory->{
			List<Category> twoCategoryList = categoryList.stream().
					filter(item -> item.getParentId() == oneCategory.getId()).collect(Collectors.toList());
			//二级封装到一级分类里面
			oneCategory.setChildren(twoCategoryList);
			//4.三级分类
			twoCategoryList.forEach(twoCategory->{
				List<Category> threeCategoryList = categoryList.stream().
						filter(item -> item.getParentId() == twoCategory.getId()).collect(Collectors.toList());
				//三级封装到二级
				twoCategory.setChildren(threeCategoryList);
			});
		});

		return oneCategoryList;

    }
}