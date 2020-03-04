package mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

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
	

	public static LocalDateTime convertStringToLocalDateTime(String dateString) throws ParseException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime date = LocalDate.parse(dateString,dtf).atTime(LocalTime.MIDNIGHT);
		//Date dateToConvert=new SimpleDateFormat("yyyy-mm-dd").parse(dateString);  
	    return date;
	}
	
	public Optional<Computer> getComputer(ResultSet resDetailcomputer) throws SQLException {

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

		ComputerDto computerDto = new ComputerDto(computer.getId(),computer.getName(),
				computer.getIntroduced()==null?null:computer.getIntroduced().toString(),
				computer.getDiscontinued()==null?null:computer.getDiscontinued().toString(),companyDto);
		return computerDto;
	}
	
	public static Computer convertFromComputerDtoToComputer(ComputerDto computerDto) throws ParseException {
		Computer computer = new Computer.ComputerBuilder().setId(computerDto.getId()).setName(computerDto.getName()).setIntroduced(convertStringToLocalDateTime(computerDto.getIntroduced())).setDiscontinued(convertStringToLocalDateTime(computerDto.getDiscontinued())).setCompany(CompanyMapper.getInstance().mapFromCompanyDtoToCompany(computerDto.getCompany())).build();   
		return computer;

	}
	
}
