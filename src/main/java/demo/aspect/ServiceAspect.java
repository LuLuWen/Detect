package demo.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.TypeVariable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.logToDb.AccessData;
import demo.logToDb.AccessDataController;
import demo.logToDb.LogController;
import demo.logToDb.LogPattern;

@Aspect
@Component
public class ServiceAspect {

	private Calendar cal;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //時間格式1
	
	@Autowired
	private LogController logController;
	@Autowired
	private AccessDataController accessDataController;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 切入點 */
    /*@Pointcut("(execution(* demo.member.MemberRepository.*(..)))" + 
	"|| (execution(* demo.repo2.repo.AuthorRepository.*(..)))")*/
	@Pointcut("(execution(* demo.CoCoME.repository.*.*(..)))")
    public void log() {
    }
    
    
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        this.logger.info("-------------------- log doBefore ---------------------");
        String methodName = null;
        String entity = null;
        String entityTableName = null;
        String className = joinPoint.getTarget().getClass().getName();
    
        // create a calendar
        cal = Calendar.getInstance();

        // print current time
        System.out.println("Current time is : " + formatter.format(cal.getTime()));       
        
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();//獲取到方法物件
        methodName = method.getName();
        System.out.println("方法名稱: " + methodName);       
         
        if(!methodName.equals("save")){
        	Type genericInterfaces = joinPoint.getTarget().getClass().getInterfaces()[0].getGenericInterfaces()[0];
            ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces;
    	    Type[] typeArgumentss = parameterizedType.getActualTypeArguments();
    	    System.out.println("2 Repository使用的bean: " + typeArgumentss[0]);		
    	    entity = typeArgumentss[0].toString(); 				    

    	    //entity class
    	    Class entityClass = (Class) typeArgumentss[0]; 
    	    
    	    //entity and table name
    	    for(int k = 0 ; k < entityClass.getAnnotations().length ; k++) {
    	    	if(entityClass.getAnnotations()[k].toString().contains("@javax.persistence.Entity")) {
    	    		for(int l = 0 ; l < entityClass.getAnnotations().length ; l++) {
    	    			if(entityClass.getAnnotations()[l].toString().contains("@javax.persistence.Table")) {
    	    				String tableAnnotation = entityClass.getAnnotations()[l].toString();
    	    				String[] retvals = tableAnnotation.split(",");        					    			           					    				
    	    				String tableName = retvals[0].substring(30);
    	    				System.out.println("2 Table name: " + tableName);
    	    				entityTableName = tableName;
    	    			}
    	    		}
    	    	}
    	    }
        }            
	    
        //GET
        if(methodName.equals("findAll") || methodName.equals("findById") || methodName.equals("find") || methodName.equals("get") || methodName.equals("search")) {
        	AccessData acc1 = new AccessData(entity,entityTableName,formatter.format(cal.getTime()),"GET");
        	accessDataController.add(acc1);
        	System.out.println("成功新增 其他的讀取動作");        	
        }
        
        this.logger.info("-------------------- log " + joinPoint.getTarget().getClass().getInterfaces()[0].getGenericInterfaces()[0] + " ---------------------");
        
        
    }

}
