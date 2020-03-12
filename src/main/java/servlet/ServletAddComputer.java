/**
 * 
 */
package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
public class ServletAddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int idComputer=5;
	@Autowired
	public ComputerService computerService;
	@Autowired
	public CompanyService companyService;
	private ComputerDto compDto;
	private String PARSE_ERROR = "Parse error : ";
	private static final String DATE_ERROR = "Date error : ";
	private static final String NAME_ERROR = "Name error : ";
	
	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException servletException) {
			Logging.printError(servletException.getMessage());
		}
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		List<CompanyDto> companyDtoList=new ArrayList<>();
		List<Company> companyList=new ArrayList<>();
		companyList=companyService.readAll();
		
		companyDtoList = companyList.stream().map(company -> new CompanyMapper().companyToCompanyDto(company)).collect(Collectors.toList());
		request.setAttribute("companies", companyDtoList);
		request.getRequestDispatcher("views/AddComputer.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyDto companyDto = new CompanyDto(Integer.parseInt(request.getParameter("companyId")));
		ComputerDto computerDto = new ComputerDto(request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDto);
		ComputerValidator computerValidator = new ComputerValidator();
		try {
			Computer computerToAdd = new ComputerMapper().convertFromComputerDtoToComputer(computerDto);
			computerValidator.validateComputer(computerToAdd);
			computerService.create(computerToAdd);
		} catch(ParseException e) {
			Logging.printError(PARSE_ERROR + e.getMessage());
		} catch(DateValidator e) {
			Logging.printError(DATE_ERROR + e.getMessage());
		} catch(NameValidator e) {
			Logging.printError(NAME_ERROR + e.getMessage());
		} finally {
			response.sendRedirect("ListComputers");
		}
	}
	
}
