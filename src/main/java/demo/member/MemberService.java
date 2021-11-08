package demo.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import demo.annotation.ClassAnnotation;
import demo.annotation.CreateAnnotation;
import demo.annotation.DeleteAnnotation;
import demo.eventHandler.CrudEvent;

//@ClassAnnotation("class")
@Service
public class MemberService implements ApplicationEventPublisherAware {
	
	private ApplicationEventPublisher applicationEventPublisher;
	private String type;
	private CrudEvent<MemberAccount> crudEvent;
	
	@Autowired
    private MemberRepository memberRepository;
	
    public List<MemberAccount> listAllMembers() {
        return (List<MemberAccount>) memberRepository.findAll();
    }

    public void saveMember(MemberAccount member) {
    	memberRepository.save(member);
    	
    	//System.out.println("create member");
   
		//type = "create";
		//crudEvent = new CrudEvent<MemberAccount>(this, member, type);
    	
		//消息發布
    	//applicationEventPublisher.publishEvent(new MemberEvent(this, member, type));
		//applicationEventPublisher.publishEvent(crudEvent);
    }

    public MemberAccount getMember(Integer id) {
        return memberRepository.findById(id).get();
    }
    @DeleteAnnotation("deleteMember")
    public void deleteMember(Integer id) {
    	type = "delete";
    	crudEvent = new CrudEvent<MemberAccount>(this, getMember(id), type);
    	//消息發布
    	applicationEventPublisher.publishEvent(crudEvent);
    	
    	System.out.println("delete member");
    	
    	memberRepository.deleteById(id);
    	
    }

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub
		this.applicationEventPublisher = applicationEventPublisher;
		
	}
}
