package fr.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserInfo user;

	@Column(name = "user_role")
	private String userRole;

	public UserRoles() {
	}

	public UserRoles(UserInfo user, String userRole) {
		super();
		this.user = user;
		this.userRole = userRole;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

			

	public static class UserRolesBuilder {
		private int id;
		private UserInfo user;
		private String userRole;

		public UserRolesBuilder setId(int id) {
			this.id = id;
			return this;
		}
		
		public UserRolesBuilder setUserInfo(UserInfo user) {
			this.user = user;
			return this;
		}

		public UserRolesBuilder setUserRole(String userRole) {
			this.userRole = userRole;
			return this;
		}

		public UserRoles build() {
			return new UserRoles(this);
		}

	}

	public UserRoles(UserRolesBuilder builder) {
		this.id = builder.id;
		this.user = builder.user;
		this.userRole = builder.userRole;
	}
}
