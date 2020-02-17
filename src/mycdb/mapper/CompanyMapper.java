/**
 * 
 */
package mycdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mycdb.model.Company;

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
	
	public Company getCompany(ResultSet res){
        //this.company = new Company();
		try {
			company = new Company.CompanyBuilder().setId(res.getInt("company.id")).
					setName(res.getString("company.name")).build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        
        /*try {
            company.setId(res.getInt("company.id"));
			company.setName(res.getString("company.name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
        return company;
	}
}
