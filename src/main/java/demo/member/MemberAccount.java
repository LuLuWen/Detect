package demo.member;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="member_account")
public class MemberAccount {
  private int id;
  private String email;
  private String cellphone;
  private String password;
  private String address;
  
  public MemberAccount() {
	super();
	// TODO Auto-generated constructor stub
  }

  
  public MemberAccount(int id, String email, String cellphone, String password, String address) {
	super();
	this.id = id;
	this.email = email;
	this.cellphone = cellphone;
	this.password = password;
	this.address = address;
  }

  @Id
  public int getId() {
	return id;
  }
  
  public void setId(int id) {
	this.id = id;
  }
  public String getEmail() {
	return email;
  }
  
  public void setEmail(String email) {
	this.email = email;
  }
  
  public String getCellphone() {
	return cellphone;
  }
  
  public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
  }
  
  public String getPassword() {
	return password;
  }
  
  public void setPassword(String password) {
	this.password = password;
  }
  
  public String getAddress() {
	return address;
  }
  
  public void setAddress(String address) {
	this.address = address;
  }
  
  
  
}
