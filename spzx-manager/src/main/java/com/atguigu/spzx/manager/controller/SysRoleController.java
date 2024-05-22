package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.common.log.annnotation.Log;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    //4.角色删除的方法
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId")Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    //3.角色修改
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    @Log(title = "角色管理:添加",businessType = 1)
    //2.角色添加的方法
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }



    //1.角色的列表方法，模糊查询分页显示
    //current 当前页数
    //limit 每页显示的记录数
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current")Integer current,
                             @PathVariable("limit")Integer limit,
                             @RequestBody SysRoleDto sysRoleDto){
        //pagehelper插件实现的分页，所以返回的是pageinfo对象
        PageInfo<SysRole> pageInfo = sysRoleService.finaByPage(sysRoleDto,current,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //5.查询所有的角色
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId")Long userId){
        Map<String,Object> map = sysRoleService.findAll(userId);
        return Result.build(map,ResultCodeEnum.SUCCESS);
    }

}
