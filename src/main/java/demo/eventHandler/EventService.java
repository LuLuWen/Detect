package demo.eventHandler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import demo.member.MemberAccount;

public class EventService implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;
	private CrudEvent<MemberAccount> createEvent;
	private String type;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void create(Object object) {
		//System.out.println("666666666"+object);
		MemberAccount member = new MemberAccount();
		member = (MemberAccount) object;
		//System.out.println("666666666"+member.getId());
		type = "create";
		createEvent = new CrudEvent<MemberAccount>(this, member, type);
		applicationEventPublisher.publishEvent(createEvent);
	}
}
