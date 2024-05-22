package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //传入的参数是我们查询的所有菜单的list集合
        //要将其转化为我们要求的数据格式
        ArrayList<SysMenu> trees = new ArrayList<>();
        //遍历菜单集合,增强for循环
        for (SysMenu sysMenu:sysMenuList){
            //找到递归入口，也就是顶层菜单
            //if parent_id==0,不转换也可以自动开箱
            if(sysMenu.getParentId().longValue()==0){
                //根据第一层去找下层的数据，根据递归查找
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @return
     */
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //根据第一层到所有中找到下一层，然后递归，下一层的下一层
        sysMenu.setChildren(new ArrayList<>());
        //递归查找
        for (SysMenu it:sysMenuList){
            //判断id和parent_id是否相同
            if(it.getParentId().longValue()==sysMenu.getId().longValue()){
                //放入子节点，记得递归，放入it的下层数据
                sysMenu.getChildren().add(findChildren(it,sysMenuList));
            }
        }
        return sysMenu;
    }
}