package demo.eventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.member.MemberAccount;
 
@Component
public class EventListener<E> implements ApplicationListener<CrudEvent<E>> {
 
	Logger logger = LoggerFactory.getLogger(getClass());
	private MemberAccount member;
	
	//監聽到事件發布後，自動執行onApplicationEvent方法
	@Override
	public void onApplicationEvent(CrudEvent<E> event) {
		
		//System.out.println(event.getType());
			
		switch(event.getType()) {
			case "create" :
				System.out.println("123");
				logger.info("information saving: create member id: {}",event.getX());
				
				member = (MemberAccount) event.getX();
				System.out.println(member.getId());
				break;
			
			case "delete" :
				//System.out.println("456");
				//logger.info("information saving: delete member id: {}",event.getX().getId());
				break;
		}
		
				
	}
}
