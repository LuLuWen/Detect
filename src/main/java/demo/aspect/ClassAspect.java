package demo.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import demo.annotation.ClassAnnotation;

@Aspect
@Component
public class ClassAspect {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut("@annotation(demo.annotation.ClassAnnotation)")
    public void classPointCut() {

    }
	
	@Around("classPointCut()")
    public void around(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();//獲取到方法物件

        //獲取到註解類
        ClassAnnotation annotation = method.getAnnotation(ClassAnnotation.class);
        if(annotation != null){
            System.out.println(annotation.value());//列印註解上value的內容
        }
	
        //請求的類名以及方法名
        String className = point.getTarget().getClass().getName();
        //String methodName = signature.getName();
        //System.out.println("執行的方法為:" + className + "." + methodName + "()");
        
        //Class c = Class.forName(className);
        Field[] fields = point.getTarget().getClass().getDeclaredFields();
        for(Field field: fields) {
        	System.out.println("field");
        }
        System.out.println(className);
        
        //Gson gson = new Gson();
        //Object[] aaa = point.getArgs();
        //System.out.println("參數為"+ gson.toJson(aaa));

        /*EventService createService = new EventService();
        createService.create(point.getArgs()[0]);*/

        Object result = point.proceed();
        //System.out.println("有進來" + result);
    }
}
