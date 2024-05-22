package com.atguigu.spzx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {
    public static void main(String[] args) {
        //read();

        write();
    }


    //读操作
    public static void read(){
        //1.定义读取的excel文件的位置
        String fileName="C:\\Users\\ritian\\Desktop\\study-files\\尚硅谷\\尚品甄选\\资料\\01.xlsx";
        //2.调用方法
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener();
        //根据文件位置找到文件并且读取内容，将读取的内容封装到实体类中，并且还要传入监听器（内容传到监听器的invoke的t中中）
        // 然后sheet是行的意思
        //读取行数据，好像第一行不去读取，因为默认是列名
        EasyExcel.read(fileName, CategoryExcelVo.class,excelListener)
                .sheet().doRead();

        List<CategoryExcelVo> data = excelListener.getData();
        System.out.println(data);
    }

    //写操作
    public static void write(){
        ArrayList<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
        list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
        EasyExcel.write("D://02.xlsx", CategoryExcelVo.class)
                .sheet("分类数据").doWrite(list);
    }
}
