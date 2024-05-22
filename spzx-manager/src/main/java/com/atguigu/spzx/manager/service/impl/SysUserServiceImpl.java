package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public LoginVo login(LoginDto loginDto) {

        //验证码校验过程
        //获取输入的验证码和redis存储的key
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //根据的redis中的key查询验证码
        //前缀加上别忘了，一般是把前缀放到常量中的，这里没这样做
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //比较输入的验证码和查询到的验证码，不一致，提示用户校验失败
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode,captcha)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //一致则删除redis当中的验证码
        redisTemplate.delete("user:validate"+key);

        // 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if(sysUser == null){
            /*throw new RuntimeException("用户名不存在");*/
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 用户名存在，验证密码是否正确，数据库中的密码是进行了加密的，而我们输入的不是加密的
        String database_password = sysUser.getPassword();//数据库中存储的密码
        //将我们输入的密码进行md5加密
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());

        //比较
        if (!input_password.equals(database_password)) {
            /*throw new RuntimeException("密码不正确");*/
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 生成令牌，保存数据到Redis中，用户信息转成JSON，七天有效
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login"+token,JSON.toJSONString(sysUser),
                7,TimeUnit.DAYS);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        // 返回
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        //从redis中根据key把数据删除
        redisTemplate.delete("user:login"+token);
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        //1.判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectByUserName(userName);
        if(dbSysUser != null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //2对输入的密码进行加密处理
        String password = sysUser.getPassword();
        sysUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        //设置status的值
        //1代表可用，0代表不可用
        sysUser.setStatus(1);

        sysUserMapper.save(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        //1.判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectByUserName(userName);
        if(dbSysUser != null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //1.如果之前分配过角色，重新分配，把之前的数据删除掉，根据用户id
        sysRoleUserMapper.deleteByUserId(assginRoleDto);
        //2.重新分配角色
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        //遍历得到每个角色id
        for(Long roleId:roleIdList){
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }
}