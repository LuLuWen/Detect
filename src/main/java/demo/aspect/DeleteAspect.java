package demo.aspect;

import org.springframework.stereotype.Component;

import com.microsoft.applicationinsights.core.dependencies.google.gson.Gson;

//import com.google.gson.Gson;

import demo.annotation.CreateAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class DeleteAspect {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 換行
	 */
	private static final String LINE_SEPARATOR = System.lineSeparator();
    
	/**
     * 選擇切面的註解DeleteAnnotation
     */
    @Pointcut("@annotation(demo.annotation.DeleteAnnotation)")
    public void deletePointCut() {

    }
    
    /**
     * 方法增強@Arounbd
     * @param point
     */
    @Around("deletePointCut()")
    public void around(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();//獲取到方法物件

        //獲取到註解類
        CreateAnnotation annotation = method.getAnnotation(CreateAnnotation.class);
        if(annotation != null){
            System.out.println(annotation.value());//列印註解上value的內容
        }

        //請求的類名以及方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        System.out.println("執行的方法為:" + className + "." + methodName + "()");

        Field[] fields = point.getTarget().getClass().getDeclaredFields();
        for(Field field: fields) {
        	if(field.toString().contains("Repository")) {
        		System.out.println("Repository:  " + field.getType().getSimpleName());
            	System.out.println(field.getName());
            	System.out.println("Repository:  " + Arrays.toString(field.getType().getDeclaredFields()));
        	}
        }
        		
        Object result = point.proceed();
        //System.out.println("有進來" + result);
    }
    
    @Before("deletePointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
    	logger.info("要刪除的id為: {}", joinPoint.getArgs()[0]);
    
    }
    
    @After("deletePointCut()")
    public void doAfter(JoinPoint joinPoint) throws Throwable{

    	logger.info("=====end=====" + LINE_SEPARATOR);
    
    }

}
