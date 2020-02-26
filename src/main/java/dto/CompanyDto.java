package dto;

/**
 * @author Djamel
 *
 */
public class CompanyDto {
	private int id;
	private String name;
	
	public CompanyDto(int id) {
		this.id=id;
	}
	public CompanyDto() {}
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
}
