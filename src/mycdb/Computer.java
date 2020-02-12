package mycdb;

import java.sql.Date;

/**
 * @author djamel
 *
 */
public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private int manufacturer;
	
	public Computer(int id, String name, Date introduced, Date discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.manufacturer = company_id;
		
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
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date date) {
		this.introduced = date;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date date) {
		this.discontinued = date;
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
