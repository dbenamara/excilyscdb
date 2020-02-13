package mycdb.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author djamel
 *
 */
public class Computer {
	private int id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private int manufacturer;
	private Company company;
	
	public Computer() {}
	
	public Computer(int id, String name, Company company) {
		this.id = id;
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
				+ ", manufacturer=" + manufacturer + "]";
	}

	public int getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	
	
}
