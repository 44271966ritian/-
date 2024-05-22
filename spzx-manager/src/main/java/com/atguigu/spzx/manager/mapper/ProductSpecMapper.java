package com.atguigu.spzx.manager.mapper;


import com.atguigu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    List<ProductSpec> findByPage();

    void save(ProductSpec productSpec);

    void update(ProductSpec productSpec);

    void delete(Long id);

    List<ProductSpec> findAll();
}
