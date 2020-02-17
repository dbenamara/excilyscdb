package mycdb.model;

/**
 * @author djamel
 *
 */
public class Company {
	private int id;
	private String name;
	
	public Company(){}
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "\nCompany [id=" + id + ", name=" + name + "]";
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
	
	public static class CompanyBuilder {
		private int id;
		private String name;

		public CompanyBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public Company build() {
			return new Company(this);
		}
	}

	private Company(CompanyBuilder companyBuilder) {
		this.id = companyBuilder.id;
		this.name = companyBuilder.name;
	}


	
	
}
