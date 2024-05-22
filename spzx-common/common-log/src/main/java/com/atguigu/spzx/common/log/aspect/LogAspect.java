package com.atguigu.spzx.common.log.aspect;


import com.atguigu.spzx.common.log.annnotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect//表示是个切面类
@Component//交给spring管理
public class LogAspect {


    @Autowired
    private AsyncOperLogService operLogService;

    //环绕通知
    //这个方法作为环绕通知，第一个参数可以获得我们的方法及其参数信息，第二个参数是注解
    //我们设置，当方法上使用了该注解，就纳入了环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){

//        String title = sysLog.title();
//        int businessType = sysLog.businessType();
//        System.out.println("title:"+title+"::"+"businessType:"+businessType);

        //业务方法调用之前封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);

        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
//            System.out.println("在业务方法之后执行...."  );
            //调用业务方法之后封装数据
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException();
        }

        //日志添加
        operLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }
}
