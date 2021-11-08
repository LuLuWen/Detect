package demo.join;

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
@Table(name = "employee")
public class Employee {
	@Id
	@Column(name = "id")  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")  
	private String name;
	@Column(name = "email")  
	private String email;
	@Column(name = "address")  
	private String address;
	  	  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Department department;

	  public Department getDepartment() {
		return department;
	  }

	  public void setDepartment(Department department) {
		this.department = department;
	  }

	  
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
	  
	  public String getAddress() {
		return address;
	  }
	  
	  public void setAddress(String address) {
		this.address = address;
	  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
