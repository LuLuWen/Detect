package demo.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.gson.JsonArray;
import com.microsoft.applicationinsights.core.dependencies.google.gson.Gson;

//import com.google.gson.Gson;

import demo.annotation.CreateAnnotation;
import demo.eventHandler.EventService;
import demo.logToDb.AccessData;
import demo.logToDb.AccessDataController;
import demo.logToDb.LogController;
import demo.logToDb.LogPattern;
import demo.logToDb.LogService;
import net.minidev.json.JSONArray;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.management.Query;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class CreateAspect {

	Logger logger = LoggerFactory.getLogger(getClass());
	private String[] EntityTableName;
	private String[] Entity;
	private Boolean isRepo2 = false; 
	private Boolean isJoin = false;
	private String usedEntityTableName;
	private String usedEntity;
	private String usedJoinEntityTableName;
	private String usedJoinEntity;
	private Calendar cal;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //時間格式1	
	
	private LogPattern logPattern;
	@Autowired
	private LogController logController;
	@Autowired
	private AccessDataController accessDataController;
	/**
	 * 換行
	 */
	private static final String LINE_SEPARATOR = System.lineSeparator();
    
	/**
     * 選擇切面的註解CustomAnnotation
     */
    @Pointcut("@annotation(demo.annotation.CreateAnnotation)")
    public void customPointCut() {

    }

    @Around("customPointCut()")
    public void around(ProceedingJoinPoint point) throws Throwable{
    	// create a calendar
        cal = Calendar.getInstance();

        // print current time
        System.out.println("Current time is : " + cal.getTime());
        System.out.println("Current time is : " + formatter.format(cal.getTime()));
    	
    	MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();//獲取到方法物件

        //獲取到註解類
        CreateAnnotation annotation = method.getAnnotation(CreateAnnotation.class);
        if(annotation != null){
            System.out.println(annotation.value());//列印註解上value的內容
        }

        //if(annotation.value().equals("join")) {
        //請求的類名以及方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        System.out.println("執行的方法為:" + className + "." + methodName + "()");
               
        //System.out.println("參數型態: " + method.getParameterTypes()[0]);               
        
        Field[] fields = point.getTarget().getClass().getDeclaredFields();
        Class ccc = point.getTarget().getClass();
        //System.out.println("ccc " + ccc);
        for(Field field: fields) {
        	System.out.println(field.getType());
        	for(int i = 0; i < field.getType().getAnnotations().length ; i++) {
        		if(field.getType().getAnnotations()[i].toString().contains("@org.springframework.stereotype.Service")) {
        			//System.out.println("yes");
        			Field[] field2 = field.getType().getDeclaredFields();        			
    				//2 repo
        			int repoNumber = 0;
        			for(int t = 0; t < field2.length ; t++) {				
						for(int s = 0; s < field2[t].getType().getAnnotations().length ; s++) {
							if(field2[t].getType().getAnnotations()[s].toString().contains("@org.springframework.stereotype.Repository")) {
								repoNumber++;
							}
						}
					}
        			
    				if(repoNumber == 2) {
    					Entity = new String[field2.length];
    					EntityTableName = new String[field2.length];
    					isRepo2 = true;
    					System.out.println("2 repo");
    					for(int t = 0; t < field2.length ; t++) {				
    						for(int s = 0; s < field2[t].getType().getAnnotations().length ; s++) {
    							if(field2[t].getType().getAnnotations()[s].toString().contains("@org.springframework.stereotype.Repository")) {
    								Type genericInterfaces = field2[t].getType().getGenericInterfaces()[0];
            					    ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces;
            					    Type[] typeArgumentss = parameterizedType.getActualTypeArguments();
            					    System.out.println("2 Repository使用的bean: " + typeArgumentss[0]);  				    
            					    Entity[t] = typeArgumentss[0].toString(); 				    

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
            					    				EntityTableName[t] = tableName;
            					    			}
            					    		}
            					    	}
            					    }    
    							}
    						}
    					}
    				}
    				else {	
        			for(Field field1: field2) {
        				//System.out.println("type type " + field1);
        				for(int j = 0; j < field1.getType().getAnnotations().length ; j++) {
        					//System.out.println("type type " + field1);
        					//System.out.println(field1.getType().getAnnotations().length);
        					if(field1.getType().getAnnotations()[j].toString().contains("@org.springframework.stereotype.Repository")) {
        						System.out.println(field1.getType().getSimpleName());
        						String SimpleName = field1.getType().getSimpleName();
        						String s = SimpleName.replace("Repository", "");     						
        						
        						//取得使用到的bean
        					    System.out.println(field1.getType().getGenericInterfaces()[0]);
        						Type genericInterfaces = field1.getType().getGenericInterfaces()[0];
        					    ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces;
        					    Type[] typeArgumentss = parameterizedType.getActualTypeArguments();
        					    System.out.println("Repository使用的bean: " + typeArgumentss[0]);
        					    for(Type typeArgument : typeArgumentss){
        							 Class typeArgClass = (Class) typeArgument;
        							 //System.out.println(typeArgClass);
        						}    				    
        					    //entity class
        					    Class entityClass = (Class) typeArgumentss[0];  
        					    usedEntity = typeArgumentss[0].toString();
        					    
        						//Repository's Method
        					    if(field1.getType().getDeclaredMethods().length != 0) {
        							System.out.println("Query: " + field1.getType().getDeclaredMethods()[0].getAnnotations()[0]);	
        							//Query name
        							for(int m = 0 ; m < field1.getType().getDeclaredMethods()[0].getAnnotations().length ; m++) {
        								if(field1.getType().getDeclaredMethods()[0].getAnnotations()[m].toString().contains("@org.springframework.data.jpa.repository.Query")) {
        									String queryAnnotation = field1.getType().getDeclaredMethods()[0].getAnnotations()[m].toString();
    					    				String[] retvals = queryAnnotation.split("=");        					    			           					    				
    					    				String queryName = retvals[4].substring(0).replace(", countName", "");
    					    				System.out.println("Query name: " + queryName);
    					    				//which entity(after FROM)
    					    				int entityIndex = 0;
    					    				String[] retvalss = queryName.split(" ");
    					    				for(int n = 0 ; n < retvalss.length ; n++) {
    					    					if(retvalss[n].equals("FROM")) {
    					    						entityIndex = n+1;
    					    						//System.out.println("Entity1: " + retvalss[entityIndex]);
    					    						break;
    					    					}
    					    				}
    					    				System.out.println("Entity: " + retvalss[entityIndex]);
        								}
        							}
        						 }        					       					  
        					    
        					    //join之entity
        					    //System.out.println("EntityField: " + entityClass.getDeclaredFields()[3].getGenericType());
        					    //System.out.println("EntityField: " + entityClass.getDeclaredFields()[3].getAnnotations()[0]);
        					    for(int p = 0 ; p < entityClass.getDeclaredFields().length ; p++) {
        					    	for(int q = 0 ; q < entityClass.getDeclaredFields()[p].getAnnotations().length ; q++) {
        					    		if(entityClass.getDeclaredFields()[p].getAnnotations()[q].toString().contains("@javax.persistence.OneToMany")) {
        					    			isJoin = true;
        					    			Type joinEntity = entityClass.getDeclaredFields()[p].getGenericType();
        	        					    if(joinEntity instanceof ParameterizedType) {
        	        					    	ParameterizedType joinEntityType = (ParameterizedType) joinEntity;
        	        					    	Type[] joinEntitytypeArgumentss = joinEntityType.getActualTypeArguments();
        	        					    	System.out.println("Entity join的bean: " + joinEntitytypeArgumentss[0]);
        	        					    	Class joinEntityClass = (Class) joinEntitytypeArgumentss[0];
        	        					    	System.out.println("join: " + joinEntityClass);
        	        					    	usedJoinEntity = joinEntitytypeArgumentss[0].toString();
        	        					    	for(int r = 0 ; r < joinEntityClass.getAnnotations().length ; r++) {
        	        					    		if(joinEntityClass.getAnnotations()[r].toString().contains("@javax.persistence.Table")) {
        	        					    			String tableAnnotation2 = joinEntityClass.getAnnotations()[r].toString();
                					    				String[] retvals2 = tableAnnotation2.split(",");        					    			           					    				
                					    				String tableName2 = retvals2[0].substring(30);
                					    				System.out.println("Table name: " + tableName2);
                					    				usedJoinEntityTableName = tableName2;
        	        					    		}
        	        					    	}
        	        					    }
            					    	}
        					    	}  					    	
        					    }        					    
        					    
        					    //entity and table name
        					    for(int k = 0 ; k < entityClass.getAnnotations().length ; k++) {
        					    	if(entityClass.getAnnotations()[k].toString().contains("@javax.persistence.Entity")) {
        					    		for(int l = 0 ; l < entityClass.getAnnotations().length ; l++) {
        					    			if(entityClass.getAnnotations()[l].toString().contains("@javax.persistence.Table")) {
        					    				String tableAnnotation = entityClass.getAnnotations()[l].toString();
        					    				String[] retvals = tableAnnotation.split(",");        					    			           					    				
        					    				String tableName = retvals[0].substring(30);
        					    				System.out.println("Table name: " + tableName);
        					    				usedEntityTableName = tableName;
        					    			}
        					    		}
        					    	}
        					    }     
        					    
        					    //System.out.println("entity: " + entityClass.getAnnotations()[0]);	
        					    //System.out.println("Table的名稱: " + entityClass.getAnnotations()[1]);				  
        						
        							/* 進到Repository之後,找method的回傳值(DeptEmpDto)
        							System.out.println(field1.getType().getDeclaredMethods().length);
        							System.out.println(field1.getType().getDeclaredMethods()[1].getGenericReturnType());
        							Type returnType = field1.getType().getDeclaredMethods()[1].getGenericReturnType();
        							if(returnType instanceof ParameterizedType){
        							    ParameterizedType type = (ParameterizedType) returnType;
        							    Type[] typeArguments = type.getActualTypeArguments();
        							    for(Type typeArgument : typeArguments){
        							        Class typeArgClass = (Class) typeArgument;
        							        System.out.println("typeArgClass = " + typeArgClass);
        							    }
        							} */        					        						    					
        					}       					
        				}
        			}
        		}}
        		else
        			System.out.println("no");
        	}  
        	
            /*TypeVariable<?>[] typeParameters = field2[3].getType().getInterfaces()[0].getTypeParameters();
        	for (TypeVariable<?> typeParameter : typeParameters) {
                System.out.println(typeParameter);
            }*/       	
        }
        //}
        
        Annotation[] annotation1 = method.getAnnotations();
        System.out.println(annotation1[1].toString());
        String str = annotation1[1].toString();
        
        String[] retval = str.split("\\.");
        /*for (String token:retval) {
            System.out.println(token);
        }*/
        
        /*if(retval[retval.length-1].contains("GetMapping")) {
        	//System.out.println("The user used the get method");
        	logger.info("The user used the get method and the entity class is {} , entity table name is {}" , usedEntity , usedEntityTableName);
        	if(annotation.value().equals("join")) {
        		logger.info("The entity class join with {} , and its' table name is {}" , usedJoinEntity , usedJoinEntityTableName);
        	}
        	logPattern = new LogPattern(formatter.format(cal.getTime()),"GET","","",usedEntity,usedEntityTableName,usedJoinEntity,usedJoinEntityTableName);
        	System.out.println(logPattern.toString());
        }
        else if(retval[retval.length-1].contains("PostMapping")) {
        	logger.info("The user used the post method and the entity class is {} , entity table name is {}" , usedEntity , usedEntityTableName);
        	//String[] retval2 = str.split("\\,");
            //for (String token:retval2) {
                //System.out.println(token);
            //}
        	//System.out.println("" + retval2[5]);
        	
        	//Object[] arguments = point.getArgs();
            //System.out.println(arguments[0].getClass().getSimpleName());
        	//logPattern = new LogPattern(formatter.format(cal.getTime()),"POST","","",usedEntity,usedEntityTableName,usedJoinEntity,usedJoinEntityTableName);
        	//System.out.println(logPattern.toString());
        }
        else if(retval[retval.length-1].contains("PutMapping")) {  
        	//logger.info("The user used the put method and the entity class is {} , entity table name is {}" , usedEntity , usedEntityTableName);
        	logPattern = new LogPattern(formatter.format(cal.getTime()),"PUT","","",usedEntity,usedEntityTableName,usedJoinEntity,usedJoinEntityTableName);
        	logger.info("{}" , logPattern.toString());
        }
        else if(retval[retval.length-1].contains("DeletetMapping")) {
        	System.out.println("The user used the delete method");
        }*/      
        
        //Object[] arguments = point.getArgs();
        //System.out.println(arguments[0].getClass().getSimpleName());
        
        //Gson gson = new Gson();
        //Object[] aaa = point.getArgs();
        //System.out.println("參數為"+ gson.toJson(aaa));

        Object result = point.proceed();
        isRepo2 = false;
        isJoin = false;
        System.out.println("有進來" + result);
    }
    
    @Before("customPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
    	Gson gson = new Gson();
    	logger.info("====start==== 參數為:  {}" , gson.toJson(joinPoint.getArgs()));
    	
    	//System.out.println("參數為2" + new Gson().toJson(joinPoint.getArgs()));
    		
    	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();//獲取到方法物件
    	
        String mapping = null;      
        for(int i = 0; i < joinPoint.getTarget().getClass().getAnnotations().length; i++) {
        	if(joinPoint.getTarget().getClass().getAnnotations()[i].toString().contains("@org.springframework.web.bind.annotation.RequestMapping")) {
        		mapping = joinPoint.getTarget().getClass().getAnnotations()[i].toString();
        	}
        }
        
        String[] retval = new String[10];
        String mappingUrl = null;
        if(mapping != null) {
        	retval = mapping.split("],");
        	mappingUrl = retval[5].substring(8);
        }
        
    	Annotation[] annotation = method.getAnnotations();
        //System.out.println(annotation[1].toString());
    	//String str = annotation[1].toString();
    	
    	String str = null;
    	String str2 = null;
    	for(int i = 0; i < annotation.length; i++) {
    		if(annotation[i].toString().contains("GetMapping") || annotation[i].toString().contains("PostMapping") || annotation[i].toString().contains("PutMapping") ) {
    			str = annotation[i].toString();
    		}
    		else if(annotation[i].toString().contains("RequestMapping")) {
    			str2 = annotation[i].toString();
    		}
    	}               
    	
    	String[] retval1 = new String[10];
    	String[] retval2 = new String[10];
    	String methodToken = "";
    	String url = "";
    	if(str == null) {
    		retval1 = str2.split("],");
    		methodToken = retval1[2].substring(9);
    		retval2 = str2.split("],");
    		url = retval2[5].substring(8);
    	}
    	else if(str2 == null) {
    		retval1 = str.split("\\.");
    		retval2 = str.split("],");
    		url = retval2[4].substring(8);
    	}
        
    	String URL = "";
    	if(mappingUrl == null) {
    		URL = url;
    	}
    	else {
    		URL = mappingUrl + url;
    	}
    	
    	//String[] retval = str.split("\\.");        
        //String[] retval2 = str.split("],");        					    			           					    				
		//String url = retval2[4].substring(8);
		//System.out.println("str: " + url);
		
        if(isRepo2) {
        	if(retval1[retval1.length-1].contains("GetMapping") || methodToken.equals("GET")) {
        		/*JSONObject obj2 = new JSONObject();
        		obj2.put("requestTime" , "2021/04/22 17:47:28");
        		obj2.put("requestUrl" , "");
        		obj2.put("requestMethod" , "GET");
        		obj2.put("requestDataBody" , true);
        		JSONObject obj3 = new JSONObject();
        		obj3.put("entity", "Author");
        		obj3.put("Table", "AuthorTable");
        		JSONObject obj4 = new JSONObject();
        		obj4.put("entity", "Author");
        		obj4.put("Table", "AuthorTable");
        		JSONArray jsonArray = new JSONArray();
        		jsonArray.add(obj3);
        		jsonArray.add(obj4);
        		obj2.put("accessData", jsonArray);
        		System.out.println(obj2);*/
            	
        		/*HashMap<String,String> hash = new HashMap<String,String>();
        		hash.put("entity1", "1");        		
        		hash.put("entity2", "3");
        		hash.put("table1", "2");
        		hash.put("table2", "4");
        		JSONArray jsonArray1 = new JSONArray();
        		jsonArray1.add(hash);      		
        		logPattern = new LogPattern(formatter.format(cal.getTime()),"GET","",true,hash);
            	JSONObject obj = new JSONObject(logPattern);
            	logger.info("{}" , obj);*/
            	
            	/*AccessData acc1 = new AccessData(Entity[0],EntityTableName[0]);
            	AccessData acc2 = new AccessData(Entity[1],EntityTableName[1]);
            	List<AccessData> list = new ArrayList<>();
            	list.add(acc1);
            	list.add(acc2);
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"GET",url,false,list);
            	//logPattern1.getAccessData().add(acc1);
            	//logPattern1.getAccessData().add(acc2);
            	JSONObject objjj = new JSONObject(logPattern);
            	logger.info("{}" , objjj);
            	logController.add(logPattern);
            	System.out.println("成功新增");*/
            	
            	AccessData acc1 = new AccessData(Entity[0],EntityTableName[0],formatter.format(cal.getTime()),"GET");
            	AccessData acc2 = new AccessData(Entity[1],EntityTableName[1],formatter.format(cal.getTime()),"GET");
            	accessDataController.add(acc1);
            	accessDataController.add(acc2);
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"GET",url,false);
            	JSONObject objjj = new JSONObject(logPattern);
            	logger.info("{}" , objjj);
            	logController.add(logPattern);
            	System.out.println("成功新增");
        	}
            /*else if(retval[retval.length-1].contains("PostMapping")) {
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"POST","",gson.toJson(joinPoint.getArgs()),usedEntity,usedEntityTableName,usedJoinEntity,usedJoinEntityTableName);
            	logger.info("{}" , logPattern.toString());
            }
            else if(retval[retval.length-1].contains("PutMapping")) {  
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"PUT","",gson.toJson(joinPoint.getArgs()),usedEntity,usedEntityTableName,usedJoinEntity,usedJoinEntityTableName);
            	logger.info("{}" , logPattern.toString());
            }*/
    	}
        else {
        	if(retval1[retval1.length-1].contains("GetMapping") || methodToken.equals("GET")) {
        		/*AccessData acc1 = new AccessData(usedEntity,usedEntityTableName,formatter.format(cal.getTime()));
        		accessDataController.add(acc1);
        		if(isJoin) {
        			AccessData acc2 = new AccessData(usedJoinEntity,usedJoinEntityTableName,formatter.format(cal.getTime()));            	
        			accessDataController.add(acc2);
        		}        		   
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"GET",url,false);
            	JSONObject objjj = new JSONObject(logPattern);
            	logger.info("{}" , objjj);    
            	logController.add(logPattern);
            	System.out.println("成功新增");*/
            }
        }
        	if(retval1[retval1.length-1].contains("GetMapping") || methodToken.equals("GET")) {
        		    		
        		//save to DB			    			
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"GET",URL,false);
            	logController.add(logPattern);
            	System.out.println("GET 更新" + URL);        		
        		
        	}
            if(retval1[retval1.length-1].contains("PostMapping") || methodToken.equals("POST")) {
            	/*AccessData acc1 = new AccessData(usedEntity,usedEntityTableName,formatter.format(cal.getTime()));
            	accessDataController.add(acc1);
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"POST",url,true);
            	JSONObject objjj = new JSONObject(logPattern);
            	logger.info("{}" , objjj);
            	logController.add(logPattern);
            	System.out.println("成功更新");*/
            	
            	System.out.println("參數型態: " + method.getParameterTypes()[0]);          
                String entityyy;
                String entityyyTableName;
                Field[] fields = joinPoint.getTarget().getClass().getDeclaredFields();
                for(Field field: fields) {
                	System.out.println("1");
                	for(int i = 0; i < field.getType().getAnnotations().length ; i++) {
                		System.out.println("2");
                		if(field.getType().getAnnotations()[i].toString().contains("@org.springframework.stereotype.Service")) {
                			System.out.println("3");
                			Field[] field2 = field.getType().getDeclaredFields();
                			for(int t = 0; t < field2.length ; t++) {				
                				System.out.println("4");
                				for(int s = 0; s < field2[t].getType().getAnnotations().length ; s++) {
                					System.out.println("5");
                					if(field2[t].getType().getAnnotations()[s].toString().contains("@org.springframework.stereotype.Repository")) {
                						System.out.println("6");
                						Type genericInterfaces = field2[t].getType().getGenericInterfaces()[0];
        								ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces;
                					    Type[] typeArgumentss = parameterizedType.getActualTypeArguments();
                					    entityyy = typeArgumentss[0].getTypeName().toString();
                					    for(int j = 0; j < method.getParameters().length; j++) {
                					    	System.out.println("7" + method.getParameters()[j].getType().getName().toString());
                					    	System.out.println("7" + entityyy);
                					    	if(method.getParameters()[j].getType().getName().toString().equals(entityyy)) {
                					    		System.out.println("8");
                					    		entityyy = typeArgumentss[0].toString();
                					    		Class entityClass = (Class) typeArgumentss[0];
                					    		//entity and table name
                        					    for(int k = 0 ; k < entityClass.getAnnotations().length ; k++) {
                        					    	System.out.println("9");
                        					    	if(entityClass.getAnnotations()[k].toString().contains("@javax.persistence.Entity")) {
                        					    		System.out.println("10");
                        					    		for(int l = 0 ; l < entityClass.getAnnotations().length ; l++) {
                        					    			System.out.println("11");
                        					    			if(entityClass.getAnnotations()[l].toString().contains("@javax.persistence.Table")) {
                        					    				System.out.println("12");
                        					    				String tableAnnotation = entityClass.getAnnotations()[l].toString();
                        					    				String[] retvals = tableAnnotation.split(",");        					    			           					    				
                        					    				String tableName = retvals[0].substring(30);
                        					    				System.out.println("Table name: " + tableName);
                        					    				entityyyTableName = tableName;
                        					    				
                        					    				//save to DB			    			
                        					    				AccessData acc1 = new AccessData(entityyy,entityyyTableName,formatter.format(cal.getTime()),"POST");
                                        		            	accessDataController.add(acc1);
                                        		            	logPattern = new LogPattern(formatter.format(cal.getTime()),"POST",URL,true);
                                        		            	logController.add(logPattern);
                                        		            	System.out.println("POST 更新");
                        					    			}
                        					    		}
                        					    	}
                        					    }     
                					    	}
                					    }
        							}
        						}
        					}
                		}
                	}                	
                }
            }
            if(retval1[retval1.length-1].contains("PutMapping")  || methodToken.equals("PUT")) {  
            	/*AccessData acc1 = new AccessData(usedEntity,usedEntityTableName,formatter.format(cal.getTime()));
            	accessDataController.add(acc1);
            	logPattern = new LogPattern(formatter.format(cal.getTime()),"PUT",url,true);
            	JSONObject objjj = new JSONObject(logPattern);
            	logger.info("{}" , objjj);
            	logController.add(logPattern);
            	System.out.println("成功更新");*/
            	
            	System.out.println("參數型態: " + method.getParameterTypes()[0]);          
                String entityyy;
                String entityyyTableName;
                Field[] fields = joinPoint.getTarget().getClass().getDeclaredFields();
                for(Field field: fields) {
                	System.out.println("1");
                	for(int i = 0; i < field.getType().getAnnotations().length ; i++) {
                		System.out.println("2");
                		if(field.getType().getAnnotations()[i].toString().contains("@org.springframework.stereotype.Service")) {
                			System.out.println("3");
                			Field[] field2 = field.getType().getDeclaredFields();
                			for(int t = 0; t < field2.length ; t++) {				
                				System.out.println("4");
                				for(int s = 0; s < field2[t].getType().getAnnotations().length ; s++) {
                					System.out.println("5");
                					if(field2[t].getType().getAnnotations()[s].toString().contains("@org.springframework.stereotype.Repository")) {
                						System.out.println("6");
                						Type genericInterfaces = field2[t].getType().getGenericInterfaces()[0];
        								ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces;
                					    Type[] typeArgumentss = parameterizedType.getActualTypeArguments();
                					    entityyy = typeArgumentss[0].getTypeName().toString();
                					    for(int j = 0; j < method.getParameters().length; j++) {
                					    	System.out.println("7" + method.getParameters()[j].getType().getName().toString());
                					    	System.out.println("7" + entityyy);
                					    	if(method.getParameters()[j].getType().getName().toString().equals(entityyy)) {
                					    		System.out.println("8");
                					    		entityyy = typeArgumentss[0].toString();
                					    		Class entityClass = (Class) typeArgumentss[0];
                					    		//entity and table name
                        					    for(int k = 0 ; k < entityClass.getAnnotations().length ; k++) {
                        					    	System.out.println("9");
                        					    	if(entityClass.getAnnotations()[k].toString().contains("@javax.persistence.Entity")) {
                        					    		System.out.println("10");
                        					    		for(int l = 0 ; l < entityClass.getAnnotations().length ; l++) {
                        					    			System.out.println("11");
                        					    			if(entityClass.getAnnotations()[l].toString().contains("@javax.persistence.Table")) {
                        					    				System.out.println("12");
                        					    				String tableAnnotation = entityClass.getAnnotations()[l].toString();
                        					    				String[] retvals = tableAnnotation.split(",");        					    			           					    				
                        					    				String tableName = retvals[0].substring(30);
                        					    				System.out.println("Table name: " + tableName);
                        					    				entityyyTableName = tableName;
                        					    				
                        					    				//save to DB			    			
                        					    				AccessData acc1 = new AccessData(entityyy,entityyyTableName,formatter.format(cal.getTime()),"PUT");
                                        		            	accessDataController.add(acc1);
                                        		            	logPattern = new LogPattern(formatter.format(cal.getTime()),"PUT",URL,true);
                                        		            	logController.add(logPattern);
                                        		            	System.out.println("PUT 更新");
                        					    			}
                        					    		}
                        					    	}
                        					    }     
                					    	}
                					    }
        							}
        						}
        					}
                		}
                	}                	
                }
            }
        }         
    //}
    
    @After("customPointCut()")
    public void doAfter(JoinPoint joinPoint) throws Throwable{

    	logger.info("=====end=====" + LINE_SEPARATOR);
    
    }
    
    /*public boolean notifyPOSTMethodIsExecuted() {		
	
    }*/

}
