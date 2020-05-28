package com.he.controller;

import com.he.domain.SysLog;
import com.he.servicce.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;


//切面通知类
@Component
@Aspect
public class LogAop {
    private Date visitTime;//开始时间
    private Class clazz;//访问的类
    private Method method;//访问的方法


    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;//直接注入获得request对象


    //前置通知 主要是获取开始时间，执行的类是哪一个，执行的哪一个方法
    //joinPoint切入点，表示需要增强通知的方法
    @Before("execution(* com.he.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {

        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//具体要访问的类
        String methodName = jp.getSignature().getName();//获取访问方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数数组

        //获取具体执行方法的Method对象
        if(args==null||args.length==0) {
            method = clazz.getMethod(methodName);//只能获取无参数的方法
        }else{
            Class[] classArgs = new Class[args.length];//方法参数Class的数组
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName,classArgs);
        }
    }

    //后置通知
    @After("execution(* com.he.controller.*.*(..))")
    public void doAfter() throws Exception {
        //获取访问的时长
        long time = new Date().getTime()-visitTime.getTime();

        //获取url
        String url="";
        System.out.println(request.getContextPath());
        if(clazz!=null && method!=null && clazz!=LogAop.class&&clazz!=SysLogController.class){

            //获取类上访问的@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                String[] classValues = classAnnotation.value();//类 RequestMapping的value数组
                RequestMapping methodAnnotation = this.method.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String[] methodValues = methodAnnotation.value();//方法 RequestMapping的value数组
                    //程序运行到这里才表示controller中的方法执行了，再进行url的拼接
                    url= classValues[0]+methodValues[0];
                }

            }

            //获取访问的IP地址,需要获取request对象
            String ip = request.getRemoteAddr();

            //获取当前操作的用户
            //从上下文域对象获取当前登录的用户
            SecurityContext context = SecurityContextHolder.getContext();
            User user = (User)context.getAuthentication().getPrincipal();
            String  username = user.getUsername();

            //将日志相关信息封装到SysLog对象
            SysLog sysLog = new SysLog();
            sysLog.setExecutionTime(time);
            sysLog.setIp(ip);
            sysLog.setMethod("[类名]"+clazz.getName()+"方法名"+method.getName());
            sysLog.setUrl(url);
            sysLog.setUsername(username);
            sysLog.setVisitTime(visitTime);

            //调用Service
            sysLogService.save(sysLog);
        }


    }

}
