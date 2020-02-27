package mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Updated upstream
import java.util.Optional;
=======
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.text.ParseException;
import java.util.Date;
>>>>>>> Stashed changes

import dto.CompanyDto;
import dto.ComputerDto;
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
	
<<<<<<< Updated upstream
	public Optional<Computer> getComputer(ResultSet resDetailcomputer) throws SQLException {
		//this.computer = new Computer();
		//this.company = new Company();
		
=======
	public static LocalDateTime convertStringToLocalDateTime(String dateString) throws ParseException {
        Date dateToConvert=new SimpleDateFormat("yyyy-MM-dd").parse(dateString);  
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate().atStartOfDay();
	}
	
	public Optional<Computer> getComputer(ResultSet resDetailcomputer) throws SQLException {
>>>>>>> Stashed changes
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
			
		
		return Optional.ofNullable(computer);
	}
	
	public ComputerDto computerToComputerDto(Computer computer) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setId(computer.getCompany().getId());
		companyDto.setName(computer.getCompany().getName());

<<<<<<< Updated upstream
		ComputerDto computerDto = new ComputerDto(computer.getName(),
				computer.getIntroduced()==null?null:computer.getIntroduced().toString(),
				computer.getDiscontinued()==null?null:computer.getDiscontinued().toString(),companyDto);
		return computerDto;
=======
		ComputerDto computerDto = new ComputerDto(computer.getId(),computer.getName(),
				computer.getIntroduced()==null?null:computer.getIntroduced().toString(),
				computer.getDiscontinued()==null?null:computer.getDiscontinued().toString(),companyDto);
		return computerDto;
	}
	
	public static Computer convertFromComputerDtoToComputer(ComputerDto computerDto) throws ParseException {
		Computer computer = new Computer.ComputerBuilder().setId(computerDto.getId()).setName(computerDto.getName()).setIntroduced(convertStringToLocalDateTime(computerDto.getIntroduced())).setDiscontinued(convertStringToLocalDateTime(computerDto.getDiscontinued())).setCompany(CompanyMapper.getInstance().mapFromCompanyDtoToCompany(computerDto.getCompany())).build();   
		return computer;
>>>>>>> Stashed changes
	}
	
}
