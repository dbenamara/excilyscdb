package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import dto.CompanyDto;
import model.Company;

/**
 * @author excilys
 *
 */
@Component
public class CompanyMapper implements RowMapper<Company>{
	Company company;
		
	public CompanyMapper() {}
	
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return getCompany(rs).get();
	}
	
	public Optional<Company> getCompany(ResultSet res){

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
	
	public CompanyDto mapFromCompanyToCompanyDto(Company company) {
		CompanyDto companyDto=new CompanyDto();
		companyDto.setId(company.getId());
		companyDto.setName(company.getName());
		return companyDto;
	}
	public Company mapFromCompanyDtoToCompany(CompanyDto companyDto) {
		Company company=new Company();
		company.setId(companyDto.getId());
		company.setName(companyDto.getName());
		return company;

	}
}
