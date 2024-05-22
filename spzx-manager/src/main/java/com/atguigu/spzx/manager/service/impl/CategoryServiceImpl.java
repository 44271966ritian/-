package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.listener.ExcelListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findCateGoryList(Long id) {
        //1.根据id查询，返回list集合
        //select * from category where parent_id=0
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);

        //2.遍历返回的list集合，然后判断每个分类是否存在下一个分裂，如果有设置hashChildren=true
        if(!CollectionUtils.isEmpty(categoryList)){
            categoryList.forEach(category->{
                //判断每个分类是否存在下一层分类
                int count = categoryMapper.selectCountByParentId(category.getId());
                if(count>0){
                    //表示存在下一层分类
                    category.setHasChildren(true);
                }else {
                    //不存在下一层分类
                    category.setHasChildren(false);
                }
            });
        }

        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {

        try{
            //1.设置响应头的信息和其他信息
            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");


            //设置响应头信息
            // TODO:别的可以没有，这个必须有
            response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

            //2.调用mapper的方法查询所有的分类，返回一个list集合
            List<Category> categoryList = categoryMapper.findAll();
            List<CategoryExcelVo>categoryExcelVoList = new ArrayList<>();
            //集合类型的转换
            for (Category category:categoryList){
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);//工具类复制列的值到另一个对象中
                categoryExcelVoList.add(categoryExcelVo);

            }

            //3.调用EasyExcel的写方法，完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }


    }

    @Override
    public void importData(MultipartFile file) {

        //监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);

        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class,excelListener)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
