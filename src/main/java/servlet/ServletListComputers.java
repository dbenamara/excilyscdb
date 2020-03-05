package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Connexion;
import dto.ComputerDto;
import exceptions.Logging;
import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Computer;
import services.ComputerService;

/**
 * @author Djamel
 *
 */
public class ServletListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connexion conn;
	private int taillePage = 20;
	private int pageIterator;

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		ComputerService service;
		
		try {
			service = ComputerService.getInstance();
			
			List<ComputerDto>computerDtoList=new ArrayList<ComputerDto>();
			List<Computer>computerList=new ArrayList<Computer>();
			
			if(request.getParameter("taillePage")!=null) {
				taillePage=Integer.parseInt(request.getParameter("taillePage"));
			}
			if(request.getParameter("pageIterator")!=null) {
				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
				computerList=service.getPageComputer(pageIterator*taillePage,taillePage);

			}
			else {
				pageIterator=0;
				computerList=service.getPageComputer(pageIterator*taillePage,taillePage); 
			}
			int nbComputer = service.readAll().size();
			int nbPages = nbComputer/taillePage;
			
			request.setAttribute("nbPages", nbPages);
			
			for(Computer comp : computerList) {
				computerDtoList.add(ComputerMapper.getInstance().computerToComputerDto(comp));
			}
			request.setAttribute("nbComputer", nbComputer);
			request.setAttribute("computerList", computerDtoList);
			request.setAttribute("pageIterator", pageIterator);
			request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
			

		} catch(ServletException e) {
			Logging.printError(e.getMessage());
		}	

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String listToDelete = request.getParameter("selection");
		if(listToDelete != null && !listToDelete.isEmpty()) {
			List<String> deleteSelectionArray = Arrays.asList(listToDelete.split(","));
			for (String s : deleteSelectionArray) {
				try {
					ComputerService.getInstance().delete(Integer.parseInt(s));
				} catch (NumberFormatException e) {
					Logging.printError(e.getMessage());
				}
			}
		}
		
		String search = request.getParameter("searchForm");
		if(search != null && !search.isEmpty()) {
			List<Computer> computerList = ComputerService.getInstance().findName(search);
			List<ComputerDto> ComputerDtoList= computerList.stream().map(computer -> ComputerMapper.getInstance().computerToComputerDto(computer)).collect(Collectors.toList());;
			request.setAttribute("computerList", ComputerDtoList);
			request.setAttribute("search", search);
			int nbComputer= ComputerDtoList.size();
			System.out.println(nbComputer);
			request.setAttribute("nbComputer", nbComputer);
		}
		request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
	
	}	
}




