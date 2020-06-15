package dto;

/**
 * @author Djamel
 *
 */
public class CompanyDto {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	
	public CompanyDto() {}
	
	public CompanyDto(int id) {
		this.id=id;
	}
	
	public CompanyDto(int id, String name, String introduced, String discontinued) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + "]";
	}
	
	
}
