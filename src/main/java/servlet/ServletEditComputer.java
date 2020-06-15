package servlet;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.CompanyDto;
import dto.ComputerDto;
import exceptions.Logging;
import exceptions.ValidatorException.DateValidator;
import exceptions.ValidatorException.NameValidator;
import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import services.CompanyService;
import services.ComputerService;
import validators.ComputerValidator;



/**
 * @author Djamel
 *
 */

@Controller
public class ServletEditComputer {
//	private static final long serialVersionUID = 1L;
	private int idComputer=5;
	
	private ComputerService computerService;
	private CompanyService companyService;
	
	private ComputerDto compDto;
//	private Logging log;
	private static final String PARSE_ERROR = "Parse error : ";
	private static final String DATE_ERROR = "Date error : ";
	private static final String NAME_ERROR = "Name error : ";
	
	ComputerMapper computerMapper;
	CompanyMapper companyMapper;
	
	
	public ServletEditComputer(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}	

	@GetMapping(value = "/EditComputer")
	public ModelAndView showEditComputer(@RequestParam(value="id") Integer computerId) {
		if(computerId !=null) {
			idComputer = computerId;
		}
		ModelAndView modelAndView = new ModelAndView();
		Computer computerToUpdate=computerService.find(idComputer);
		compDto = computerMapper.computerToComputerDto(computerToUpdate);
		List<CompanyDto> companyDtoList=new ArrayList<>();
		List<Company> companyList=new ArrayList<>();
		companyList=companyService.readAll();
		companyDtoList = companyList.stream().map(company -> new CompanyMapper().companyToCompanyDto(company)).collect(Collectors.toList());
		
		System.out.println(companyDtoList);
		modelAndView.addObject("companies",companyDtoList);
		modelAndView.addObject("computerToUpdate", compDto);
		return modelAndView;
	}
	
	@PostMapping(value = "/EditComputer")
	public ModelAndView editComputer(@ModelAttribute("computerToUpdate") ComputerDto computerDto) {
		System.out.println("POSTMAPPING");
		computerDto.getId();
		ModelAndView modelAndView = new ModelAndView();
		//CompanyDto companyDto = new CompanyDto(compDto.getId());
		//ComputerDto computerDto = new ComputerDto(computerName,introduced,discontinued,companyDto);
		//computerDto.setId(computerId);
		ComputerValidator computerValidator = new ComputerValidator();
		try {
			Computer computerToUpdate = computerMapper.convertFromComputerDtoToComputer(computerDto);
			computerValidator.validateComputer(computerToUpdate);
			computerService.update(computerToUpdate);
		} catch(ParseException e) {
			Logging.printError(PARSE_ERROR + e.getMessage());
		} catch(DateValidator e) {
			Logging.printError(DATE_ERROR + e.getMessage());
		} catch(NameValidator e) {
			Logging.printError(NAME_ERROR + e.getMessage());
		} finally {
			//idComputer=computerDto.getId();
			modelAndView.setViewName("redirect:/ListComputers");
			
		}
		return modelAndView;

	}
	 
}
