package mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;
import model.Computer;

/**
 * @author Djamel
 *
 */
public class ComputerMapper {
	private Computer computer;
	private Company company;
	private static volatile ComputerMapper instance = null;
	
	private ComputerMapper() {}
	
	public final static ComputerMapper getInstance() {
		if (ComputerMapper.instance == null) {
			synchronized(CompanyMapper.class) {
				if (ComputerMapper.instance == null) {
					ComputerMapper.instance = new ComputerMapper();
				}
           }
        }
        return ComputerMapper.instance;
	}
	
	public Computer getComputer(ResultSet resDetailcomputer) throws SQLException {
		//this.computer = new Computer();
		//this.company = new Company();
		if (resDetailcomputer.next()){
			this.company = new Company.CompanyBuilder().setName(resDetailcomputer.getString("company.name"))
					.setId(resDetailcomputer.getInt("company_id")).build();
			
			this.computer = new Computer.ComputerBuilder().setCompany(company).setId(resDetailcomputer.getInt("computer.id"))
					.setName(resDetailcomputer.getString("computer.name"))
					.setIntroduced(resDetailcomputer.getTimestamp("computer.introduced") != null
							? resDetailcomputer.getTimestamp("computer.introduced").toLocalDateTime()
							: null)
					.setDiscontinued(resDetailcomputer.getTimestamp("discontinued") != null
							? resDetailcomputer.getTimestamp("computer.discontinued").toLocalDateTime()
							: null)
					.build();
			
			/*computer.setId(resDetailcomputer.getInt("computer.id"));
			computer.setName(resDetailcomputer.getString("computer.name"));
			computer.setIntroduced(resDetailcomputer.getTimestamp("computer.introduced")!=null?
					resDetailcomputer.getTimestamp("computer.introduced").toLocalDateTime():null);
			computer.setDiscontinued(resDetailcomputer.getTimestamp("discontinued")!=null?
					resDetailcomputer.getTimestamp("computer.discontinued").toLocalDateTime():null);
			company.setId(resDetailcomputer.getInt("company_id"));
			company.setName(resDetailcomputer.getString("company.name"));
			computer.setCompany(company);
		*/}
		
		return computer;
	}
	
}
