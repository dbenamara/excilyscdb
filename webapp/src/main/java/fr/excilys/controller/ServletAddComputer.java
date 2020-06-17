/**
 * 
 */
package fr.excilys.controller;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.CompanyDto;
import fr.excilys.dto.ComputerDto;
import fr.excilys.exceptions.Logging;
import fr.excilys.exceptions.ValidatorException.DateValidator;
import fr.excilys.exceptions.ValidatorException.NameValidator;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.services.CompanyService;
import fr.excilys.services.ComputerService;
import fr.excilys.validators.ComputerValidator;

/**
 * @author Djamel
 *
 */

@Controller
public class ServletAddComputer {
//	private static final long serialVersionUID = 1L;
//	private int idComputer=5;

	private ComputerService computerService;
	private CompanyService companyService;
	
//	private ComputerDto compDto;
	private String PARSE_ERROR = "Parse error : ";
	private static final String DATE_ERROR = "Date error : ";
	private static final String NAME_ERROR = "Name error : ";
	
	ComputerMapper computerMapper;
	CompanyMapper companyMapper;
	
	public ServletAddComputer(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}

	
	@GetMapping(value="/AddComputer")
	public ModelAndView companyList() {
		ModelAndView modelAndView = new ModelAndView("AddComputer");

		
		List<Company> companyList = companyService.readAll();
		List<CompanyDto> companyDtoList = companyList.stream().map(company -> new CompanyMapper()
				.companyToCompanyDto(company)).collect(Collectors.toList());

		modelAndView.addObject("companies", companyDtoList);

		return modelAndView;			
	}
	
	@PostMapping(value="/AddComputer")
	public ModelAndView addComputer(@RequestParam(value = "computerName") String computerName,
			@RequestParam(required = false, value = "introduced") String introduced,
			@RequestParam(required = false, value = "discontinued") String discontinued,
			@RequestParam(required = false, value = "companyId") int companyId) {
		ModelAndView modelAndView = new ModelAndView();
		CompanyDto companyDto = new CompanyDto(companyId);
		ComputerDto computerDto = new ComputerDto(computerName,introduced,discontinued,companyDto);
		
		String message = "";
		ComputerValidator computerValidator = new ComputerValidator();

		try {
			Computer computerToAdd = computerMapper.convertFromComputerDtoToComputer(computerDto);
			computerValidator.validateComputer(computerToAdd);
			computerService.create(computerToAdd);
			
			
		} catch(ParseException e) {
			Logging.printError(PARSE_ERROR + e.getMessage());
		} catch(DateValidator e) {
			Logging.printError(DATE_ERROR + e.getMessage());
		} catch(NameValidator e) {
			Logging.printError(NAME_ERROR + e.getMessage());
		} finally {
			modelAndView.setViewName("redirect:/ListComputers");
		}

		return modelAndView;
	}
	

	
}
