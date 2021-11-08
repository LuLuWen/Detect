package demo.logToDb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "accessdatass")
public class AccessData {

	@Id
	@Column(name = "id")  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "entity")
	private String entity;
	@Column(name = "tablename")
	private String entityTableName;
	@Column(name = "method")
	private String requestMethod;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "log_time")
	@Fetch(FetchMode.JOIN)
	private LogPattern logPattern;*/
	
	@Column(name = "logtime")
	private String logtime;
	
	public AccessData() {
		
	}
	public AccessData(String entity, String entityTableName, String logtime, String requestMethod) {
		super();
		this.entity = entity;
		this.entityTableName = entityTableName;
		this.logtime = logtime;
		this.requestMethod = requestMethod;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getEntityTableName() {
		return entityTableName;
	}

	public void setEntityTableName(String entityTableName) {
		this.entityTableName = entityTableName;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	
}
