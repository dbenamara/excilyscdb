package mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import dto.CompanyDto;
import dto.ComputerDto;
import model.Company;
import model.Computer;

/**
 * @author Djamel
 *
 */
@Component
public class ComputerMapper implements RowMapper<Computer> {
	private Computer computer;
	private Company company;
	private static volatile ComputerMapper instance = null;
	
	public ComputerMapper() {}
	

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return 	getComputer(rs).get();
	}

	public static LocalDateTime convertStringToLocalDateTime(String dateString) throws ParseException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime date = LocalDate.parse(dateString,dtf).atTime(LocalTime.MIDNIGHT);
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
		Computer computer = new Computer.ComputerBuilder().setId(computerDto.getId()).setName(computerDto.getName()).setIntroduced(convertStringToLocalDateTime(computerDto.getIntroduced())).setDiscontinued(convertStringToLocalDateTime(computerDto.getDiscontinued())).setCompany(new CompanyMapper().mapFromCompanyDtoToCompany(computerDto.getCompany())).build();   
		return computer;

	}
	
}
