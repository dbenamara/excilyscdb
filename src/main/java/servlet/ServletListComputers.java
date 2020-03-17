package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//import dao.Connexion;
import dto.ComputerDto;
import exceptions.Logging;
import mapper.ComputerMapper;
import model.Computer;
import services.CompanyService;
import services.ComputerService;
import services.OrderSQL;

/**
 * @author Djamel
 *
 */
@WebServlet("/ListComputers")
@Controller
public class ServletListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private Connexion conn;
	private int taillePage = 20;
	private int pageIterator;
	List<Computer> computerListPagine;
	
	@Autowired
	public ComputerService computerService;
	@Autowired
	public CompanyService companyService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
		
		try {
			
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
				computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy);

			}
			else {
				pageIterator=0;
				computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy); 
			}
			
			
			int nbComputer = computerService.readAll().size();
			int nbPages = (int) Math.ceil((double)nbComputer/taillePage);
			
			request.setAttribute("nbPages", nbPages);
			
			for(Computer comp : computerList) {
				computerDtoList.add( new ComputerMapper().computerToComputerDto(comp));
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
				List<Integer> deleteSelectionArray = Arrays.asList(listToDelete.split(","))
						.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
				computerService.deleteAllComputerSelected(deleteSelectionArray);
			} catch(NumberFormatException e) {
				
			}
			
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
			List<Computer> computerList = computerService.findName(search,pageIterator*taillePage, taillePage,orderBy);
			List<ComputerDto> ComputerDtoList= computerList.stream().map(computer -> new ComputerMapper().computerToComputerDto(computer)).collect(Collectors.toList());
			request.setAttribute("computerSearchedList", ComputerDtoList);
			request.setAttribute("search", search);
			int nbComputer= ComputerDtoList.size();
			request.setAttribute("nbComputer", nbComputer);
		
		
		
		if(request.getParameter("pageIterator")!=null) {
			pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
			computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy);

		}
		else {
			pageIterator=0;
			computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy); 
		}
		request.setAttribute("pageIterator", pageIterator);
		}
		request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
	
	}	
}




