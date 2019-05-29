package idu.cs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "user_table")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	// database에서 sequence number, auto increment => primary key 역할
	@Column(nullable=false,length=20,unique=true)
	private String userId;
	private String userPw;
	private String name;
	private String company;
	
	public User() {}
	
	public User(String userId, String userPw, String name, String company) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.name = name;
		this.company = company;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userid) {
		this.userId = userid;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userpw) {
		this.userPw = userpw;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
}