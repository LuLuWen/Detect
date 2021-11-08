package demo.eventHandler;

import org.springframework.context.ApplicationEvent;

public class CrudEvent<E> extends ApplicationEvent {

	private static final long serialVersionUID = -5481658020206295565L;
	private E x;
	private String type;
	
	public CrudEvent(Object source, E x, String type) {
		super(source);
		// TODO Auto-generated constructor stub
		this.x = x;
		this.type = type;
	}

	public E getX() {		
		return x;
	}

	public String getType() {		
		return type;
	}
}
