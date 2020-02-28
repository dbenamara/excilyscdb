package servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CompanyDto;
import dto.ComputerDto;
import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import services.CompanyService;
import services.ComputerService;


/**
 * @author Djamel
 *
 */
public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int idComputer=5;
	private ComputerService service;
	private ComputerDto compDto;
	
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		if(request.getParameter("id")!=null) {
			idComputer=	Integer.parseInt(request.getParameter("id"));
		}
		Computer computerToUpdate=ComputerService.getInstance().find(idComputer);
		compDto = ComputerMapper.getInstance().computerToComputerDto(computerToUpdate);
		List<CompanyDto> companyDtoList=new ArrayList<>();
		List<Company> companyList=new ArrayList<>();
		companyList=CompanyService.getInstance().readAll();
		
		for(Company comp : companyList) {
			companyDtoList.add(CompanyMapper.getInstance().companyToCompanyDto(comp));
		}
		
		request.setAttribute("companies", companyDtoList);
		request.setAttribute("computerToUpdate", computerToUpdate);
		
		request.getRequestDispatcher("views/EditComputer.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyDto companyDto = new CompanyDto(Integer.parseInt(request.getParameter("companyId")));
		
		ComputerDto computerDto = new ComputerDto(Integer.parseInt(request.getParameter("computerId")),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDto);  
		System.out.println(computerDto);
		try {
			Computer computerToUpdate = ComputerMapper.getInstance().convertFromComputerDtoToComputer(computerDto);
			computerToUpdate = ComputerService.getInstance().update(computerToUpdate);
			computerDto = ComputerMapper.getInstance().computerToComputerDto(computerToUpdate);
			request.setAttribute("newComputer",computerDto);
		} catch(Exception e) {
			
		} finally {
			idComputer=computerDto.getId();
			doGet(request, response);
		}
	}
}
