/**
 * 
 */
package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import dto.CompanyDto;
import dto.ComputerDto;
import model.Company;
import model.Computer;

/**
 * @author excilys
 *
 */
public class CompanyMapper {
	Company company;
	private static volatile CompanyMapper instance = null;
		
	private CompanyMapper() {}
	
	public final static CompanyMapper getInstance() {
        if (CompanyMapper.instance == null) {
           synchronized(CompanyMapper.class) {
             if (CompanyMapper.instance == null) {
            	 CompanyMapper.instance = new CompanyMapper();
             }
           }
        }
        return CompanyMapper.instance;
	}
	
	public Optional<Company> getCompany(ResultSet res){
        //this.company = new Company();
		try {
			company = new Company.CompanyBuilder().setId(res.getInt("company.id")).
					setName(res.getString("company.name")).build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return Optional.ofNullable(company);
	}
	
	public CompanyDto companyToCompanyDto(Company company) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setId(company.getId());
		companyDto.setName(company.getName());
		return companyDto;
		
	}
}
