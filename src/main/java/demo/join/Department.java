package demo.join;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	  @Id
	  @Column(name = "id")
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int id;
	  @Column(name = "name")
	  private String name;
	  @Column(name = "description")
	  private String desciption;
	
	  @OneToMany(targetEntity = Employee.class, mappedBy = "id", orphanRemoval = false, fetch = FetchType.LAZY)
	  private Set<Employee> employees;
	  
	  
	  public int getId() {
		return id;
	  }
	  
	  public void setId(int id) {
		this.id = id;
	  }
	
	  public String getName() {
		return name;
	  }
	
	  public void setName(String name) {
		this.name = name;
	  }
	
	  public String getDesciption() {
		return desciption;
	  }
	
	  public void setDesciption(String desciption) {
		this.desciption = desciption;
	  }

	  
}
