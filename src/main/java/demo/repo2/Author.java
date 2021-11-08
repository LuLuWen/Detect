package demo.repo2;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "author")
public class Author {

	@Id
	@Column(name = "id")  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "bookid")
	private int bookid;
	
	@Column(name = "birthdate")
	private Date birthdate;

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getBookId() {
		return bookid;
	}

	public void setBookId(int bookId) {
		this.bookid = bookId;
	}

	public Date getBirthDate() {
		return birthdate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthdate = birthDate;
	}
	
	
}
