package model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author djamel
 *
 */
@Entity
@Table(name = "computer")
public class Computer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "introduced")
	private LocalDateTime introduced;
	
	@Column(name = "discontinued")
	private LocalDateTime discontinued;
	
	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name="company_id")
	private Company company;
	
	public Computer() {}
	
	public Computer(int id, String name, LocalDateTime introduced, LocalDateTime Discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.company=company;
	}
	
	public Computer(String name, LocalDateTime introduced, LocalDateTime Discontinued, Company company) {
		this.name = name;
		this.company=company;
	}
	
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
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDateTime date) {
		this.introduced = date;
	}
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDateTime date) {
		this.discontinued = date;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company compagny) {
		this.company = company;
	}	
	
	
	@Override
	public String toString() {
		return "\nComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + company.getId() + "]";
	}


	public static class ComputerBuilder {
		private int id;
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private Company company;

		public ComputerBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder setIntroduced(LocalDateTime introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder setDiscontinued(LocalDateTime dicontinued) {
			this.discontinued = dicontinued;
			return this;
		}
		
		public ComputerBuilder setCompany(Company company) {
			this.company = company;
			return this;
		}

		public ComputerBuilder setIdCompagny(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
		
		
	}
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}
	

	
	
	
}
