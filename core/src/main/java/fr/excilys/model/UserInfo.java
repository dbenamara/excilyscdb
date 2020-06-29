package fr.excilys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled", nullable = false)
    private boolean enabled;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserRoles> userRoles  = new HashSet<>(0);

	public UserInfo() {}
	
	public UserInfo(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public UserInfo(String username, String password, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<UserRoles> getUserRoles() {
		return this.userRoles;
	}
	
	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}
	
	public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", password=" + password + ", userRoles=" + userRoles
				+ "]";
	}


	public static class UserInfoBuilder {
		private int id;
		private String username;
		private String password;
		private Set<UserRoles> userRoles;

		public UserInfoBuilder setId(int id) {
			this.id = id;
			return this;
		}
		
		public UserInfoBuilder setUsername(String name) {
			this.username = name;
			return this;
		}

		public UserInfoBuilder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserInfoBuilder setUserRoles(Set<UserRoles> userRoles) {
			this.userRoles = userRoles;
			return this;
		}

		public UserInfo build() {
			return new UserInfo(this);
		}

	}

	public UserInfo(UserInfoBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
		this.userRoles = builder.userRoles;
	}

}
