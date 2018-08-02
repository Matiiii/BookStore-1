package pl.jstk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "user_name", nullable = false, length = 50)
	private String userName;
	@Column(nullable = false, length = 200)
	private String password;
	@Column(nullable = false)
	private int enabled;
	@Column(nullable = false, length = 20)
	private String role;

	// for hibernate
	protected UserEntity() {
	}

	public UserEntity(Long id, String user, String password, String role) {
		this.id = id;
		this.userName = user;
		this.password = password;
		this.role = role;
		this.enabled = 1;
	}
	public UserEntity(Long id, String user, String password, int enabled, String role) {
		this.id = id;
		this.userName = user;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabled() {return enabled;}

	public void setEnabled(int enabled) {this.enabled = enabled;}

	public String getRole() {return role; }

	public void setRole(String role) {this.role = role;	}
}
