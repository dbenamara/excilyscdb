package fr.excilys.dto;

/**
 * @author Djamel
 *
 */
public class ComputerDto {
	private int id;
	public String name;
	private String introduced;
	private String discontinued;
	private CompanyDto company;
	
	public ComputerDto() {}
	

	public ComputerDto(int id, String name, String introduced, String discontinued, CompanyDto company) {

		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	public ComputerDto( String name, String introduced, String discontinued, CompanyDto company) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public CompanyDto getCompany() {
		return company;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}


	@Override
	public String toString() {
		return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]\n";
	}
	
}
