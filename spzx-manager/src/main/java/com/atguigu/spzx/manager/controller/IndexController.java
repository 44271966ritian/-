package com.atguigu.spzx.manager.controller;

    import com.atguigu.spzx.manager.service.SysMenuService;
    import com.atguigu.spzx.manager.service.ValidateCodeService;
    import com.atguigu.spzx.model.dto.system.LoginDto;
    import com.atguigu.spzx.model.entity.system.SysUser;
    import com.atguigu.spzx.model.vo.common.Result;
    import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
    import com.atguigu.spzx.model.vo.system.LoginVo;
    import com.atguigu.spzx.manager.service.SysUserService;
    import com.atguigu.spzx.model.vo.system.SysMenuVo;
    import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
    import com.atguigu.spzx.utils.AuthContextUtil;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jdk.jshell.EvalException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

@Tag(name = "用户接口")
    @RestController
    @RequestMapping(value = "/admin/system/index")
    public class IndexController {

        @Autowired
        private SysUserService sysUserService ;

        @Autowired
        private SysMenuService sysMenuService;

        @Operation(summary = "登录接口")
        @PostMapping(value = "/login")
        public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
            LoginVo loginVo = sysUserService.login(loginDto) ;
            return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
        }

        //生成图片验证码
        @Autowired
        private ValidateCodeService validateCodeService;
        @GetMapping(value = "/generateValidateCode")
        public Result<ValidateCodeVo>generateValidateCode(){
            ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
            return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
        }

        //登录成功之后查得到用户信息
        @GetMapping(value = "/getUserInfo")
        public Result getUserInfo(){
            return Result.build(AuthContextUtil.get(),ResultCodeEnum.SUCCESS);
        }
        /*public Result getUserInfo(@RequestHeader(value = "token") String token){
            //1.从请求头获取token
            //String token = request.getHeader("token");
            //2.根据token获取用户信息
            SysUser userInfo = sysUserService.getUserInfo(token);
            //3.用户信息返回
            return Result.build(userInfo,ResultCodeEnum.SUCCESS);
        }*/

        //用户退出
        @GetMapping(value = "/logout")
        public Result logout(@RequestHeader(name = "token") String token){
            sysUserService.logout(token);

            return Result.build(null,ResultCodeEnum.SUCCESS);
        }

        //查询用户可以操作的菜单
        @GetMapping("/menus")
        public Result menus(){
            List<SysMenuVo> list = sysMenuService.findMenusByUserId();

            return Result.build(list,ResultCodeEnum.SUCCESS);
        }

    }