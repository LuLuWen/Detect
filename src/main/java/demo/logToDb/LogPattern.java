package demo.logToDb;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "logpattern")
public class LogPattern {
	
	@Id
	@Column(name = "time")
	private String requestTime;
	@Column(name = "method")
	private String requestMethod;
	@Column(name = "url")
	private String requestUrl;
	//private String requestData;
	@Column(name = "databody")
	private Boolean requestDataBody;

	/*private String usedEntity;
	private String usedEntityTableName;
	private String usedJoinEntity;
	private String usedJoinEntityTableName;*/	
	
	/*@OneToMany(targetEntity = AccessData.class, mappedBy = "id", orphanRemoval = false, fetch = FetchType.LAZY)
	private List<AccessData> accessData;*/

	public LogPattern() {
		
	}
	
	public LogPattern(String requestTime, String requestMethod, String requestUrl, Boolean requestDataBody) {
		super();
		this.requestTime = requestTime;
		this.requestMethod = requestMethod;
		this.requestUrl = requestUrl;
		this.requestDataBody = requestDataBody;
		//this.accessData = accessData;
	}

	/*public List<AccessData> getAccessData() {
		return accessData;
	}

	public void setAccessData(List<AccessData> accessData) {
		this.accessData = accessData;
	}*/

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}


	public String getRequestMethod() {
		return requestMethod;
	}


	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}


	public String getRequestUrl() {
		return requestUrl;
	}


	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Boolean getRequestDataBody() {
		return requestDataBody;
	}

	public void setRequestDataBody(Boolean requestDataBody) {
		this.requestDataBody = requestDataBody;
	}
	/*public String getRequestData() {
		return requestData;
	}


	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}*/


	/*public String getUsedEntity() {
		return usedEntity;
	}


	public void setUsedEntity(String usedEntity) {
		this.usedEntity = usedEntity;
	}


	public String getUsedEntityTableName() {
		return usedEntityTableName;
	}


	public void setUsedEntityTableName(String usedEntityTableName) {
		this.usedEntityTableName = usedEntityTableName;
	}


	public String getUsedJoinEntity() {
		return usedJoinEntity;
	}


	public void setUsedJoinEntity(String usedJoinEntity) {
		this.usedJoinEntity = usedJoinEntity;
	}


	public String getUsedJoinEntityTableName() {
		return usedJoinEntityTableName;
	}


	public void setUsedJoinEntityTableName(String usedJoinEntityTableName) {
		this.usedJoinEntityTableName = usedJoinEntityTableName;
	}*/


	/*@Override
	public String toString() {
		return "LogPattern [requestTime=" + requestTime + ", requestMethod=" + requestMethod + ", requestUrl="
				+ requestUrl + ", requestData=" + requestData + ", usedEntity=" + usedEntity + ", usedEntityTableName="
				+ usedEntityTableName + ", usedJoinEntity=" + usedJoinEntity + ", usedJoinEntityTableName="
				+ usedJoinEntityTableName + "]";
	}*/
	
	
}
