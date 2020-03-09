package servlet;

import java.io.IOException;
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
import mapper.ComputerMapper;
import model.Computer;
import services.ComputerService;
import services.OrderSQL;

/**
 * @author Djamel
 *
 */
public class ServletListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connexion conn;
	private int taillePage = 20;
	private int pageIterator;
	List<Computer> computerListPagine;

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		ComputerService service;
		
		try {
			service = ComputerService.getInstance();
			
			List<ComputerDto>computerDtoList=new ArrayList<ComputerDto>();
			List<Computer>computerList=new ArrayList<Computer>();
			
			String orderBy = "computer.name";
			if(request.getParameter("order") != null) {
				orderBy = OrderSQL.orderSort(request.getParameter("order"));
				request.setAttribute("order", request.getParameter("order"));
			}

			if(request.getParameter("taillePage")!=null) {
				taillePage=Integer.parseInt(request.getParameter("taillePage"));
			}
			if(request.getParameter("pageIterator")!=null) {
				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
				computerList=service.getPageComputer(pageIterator*taillePage,taillePage,orderBy);

			}
			else {
				pageIterator=0;
				computerList=service.getPageComputer(pageIterator*taillePage,taillePage,orderBy); 
			}
			
			
			int nbComputer = service.readAll().size();
			int nbPages = (int) Math.ceil((double)nbComputer/taillePage);
			
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
			try {
				List<Integer> deleteSelectionArray = Arrays.asList(listToDelete.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
				ComputerService.getInstance().deleteAllComputerSelected(deleteSelectionArray);
			} catch(NumberFormatException e) {
				
			}
			/*for (String s : deleteSelectionArray) {
				try {
					ComputerService.getInstance().delete(Integer.parseInt(s));
				} catch (NumberFormatException e) {
					Logging.printError(e.getMessage());
				}
			}*/
		}
		
		String orderBy = "computer.name";
		if(request.getParameter("order") != null) {
			orderBy = OrderSQL.orderSort(request.getParameter("order"));
			request.setAttribute("order", request.getParameter("order"));
		}
		
		String search = request.getParameter("searchForm");
		if(search != null && !search.isEmpty()) {
			if(request.getParameter("taillePage")!=null) {
				taillePage=Integer.parseInt(request.getParameter("taillePage"));
			}
			if(request.getParameter("pageIterator")!=null) {
				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));	
			}
			List<Computer> computerList = ComputerService.getInstance().findName(search,pageIterator*taillePage, taillePage,orderBy);
			List<ComputerDto> ComputerDtoList= computerList.stream().map(computer -> ComputerMapper.getInstance().computerToComputerDto(computer)).collect(Collectors.toList());
			request.setAttribute("computerSearchedList", ComputerDtoList);
			request.setAttribute("search", search);
			int nbComputer= ComputerDtoList.size();
			request.setAttribute("nbComputer", nbComputer);
		
		
		
		if(request.getParameter("pageIterator")!=null) {
			pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
			computerList=ComputerService.getInstance().getPageComputer(pageIterator*taillePage,taillePage,orderBy);

		}
		else {
			pageIterator=0;
			computerList=ComputerService.getInstance().getPageComputer(pageIterator*taillePage,taillePage,orderBy); 
		}
		request.setAttribute("pageIterator", pageIterator);
		}
		request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
	
	}	
}




