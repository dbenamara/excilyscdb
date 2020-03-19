package servlet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//import dao.Connexion;
import dto.ComputerDto;
import exceptions.Logging;
import mapper.ComputerMapper;
import model.Computer;
import services.CompanyService;
import services.ComputerService;

/**
 * @author Djamel
 *
 */
@RequestMapping(value = "/ListComputers")
@Controller
public class ServletListComputers {
	private static final long serialVersionUID = 1L;

	private int taillePage = 20;
	private int pageIterator;
	List<Computer> computerListPagine;
	
	private ComputerService computerService;
	private CompanyService companyService;
	
	public ServletListComputers(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}
	
	
	public ModelAndView ListComputers(@RequestParam(required = false, value = "pageIterator") int pageIterator,
			@RequestParam(required = false, value = "taillePage") int taillePage,
			@RequestParam(required = false, value = "order") String orderBy,
			@RequestParam(required = false, value = "nbComputer") int nbComputer,
			@RequestParam(required = false, value = "nbPages") int nbPages,
			@RequestParam(required = false, value = "searchForm") String searchForm) {
		
		ModelAndView modelAndView = new ModelAndView("ListComputers");
		List<Computer> computerList; 
		computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy);
		nbComputer = computerService.readAll().size();
		nbPages = (int) Math.ceil((double)nbComputer/taillePage);
		
		List<ComputerDto> computerDtoList = computerList.stream().map(computer -> new ComputerMapper().computerToComputerDto(computer)).collect(Collectors.toList());
		
		setDashboardAttribute(modelAndView,pageIterator,taillePage,orderBy,nbComputer,nbPages,searchForm,computerDtoList);
		return modelAndView;
	}
	
	
	@PostMapping(value="/deleteComputer")
	public ModelAndView deleteComputer(@RequestParam(value = "selection") String listToDelete) {

		ModelAndView modelAndView = new ModelAndView("redirect:/ListComputers");
		if(listToDelete != null && !listToDelete.isEmpty()) {
			try {
				List<Integer> deleteSelectionArray = Arrays.asList(listToDelete.split(","))
						.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
				computerService.deleteAllComputerSelected(deleteSelectionArray);
			} catch(NumberFormatException e) {
				Logging.printError(e.getMessage());
			}
			
		}

		return modelAndView;
	}

	private void setDashboardAttribute(ModelAndView modelAndView, int pageIterator, int taillePage, String orderBy, int nbComputer,
		int nbPages, String searchForm,List<ComputerDto> computerList) {
		modelAndView.addObject("taillePage", taillePage);
		modelAndView.addObject("pageIterator", pageIterator);
		modelAndView.addObject("nbComputer", nbComputer);
		modelAndView.addObject("nbPages", nbPages);
		modelAndView.addObject("order", orderBy);
		modelAndView.addObject("searchForm", searchForm);
		modelAndView.addObject("computerList", computerList);
	}
	
	
	public static void setMessage(String messageName, String message, ModelAndView modelAndView) {
		if (message != null && !message.isEmpty()) {
			modelAndView.addObject(messageName, message);
		}
	}
}
//	}
//	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
//		
//		
//		
//		try {
//			
//			List<ComputerDto>computerDtoList=new ArrayList<ComputerDto>();
//			List<Computer>computerList=new ArrayList<Computer>();
//			
//			String orderBy = "computer.name";
//			if(request.getParameter("order") != null) {
//				orderBy = OrderSQL.orderSort(request.getParameter("order"));
//				request.setAttribute("order", request.getParameter("order"));
//			}
//
//			if(request.getParameter("taillePage")!=null) {
//				taillePage=Integer.parseInt(request.getParameter("taillePage"));
//			}
//			if(request.getParameter("pageIterator")!=null) {
//				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
//				computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy);
//
//			}
//			else {
//				pageIterator=0;
//				computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy); 
//			}
//			
//			
//			int nbComputer = computerService.readAll().size();
//			int nbPages = (int) Math.ceil((double)nbComputer/taillePage);
//			
//			request.setAttribute("nbPages", nbPages);
//			
//			for(Computer comp : computerList) {
//				computerDtoList.add( new ComputerMapper().computerToComputerDto(comp));
//			}
//			
//			request.setAttribute("nbComputer", nbComputer);
//			request.setAttribute("computerList", computerDtoList);
//			request.setAttribute("pageIterator", pageIterator);
//			request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
//		} catch(ServletException e) {
//			Logging.printError(e.getMessage());
//		}	
//		
//	}
//	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String listToDelete = request.getParameter("selection");
//		if(listToDelete != null && !listToDelete.isEmpty()) {
//			try {
//				List<Integer> deleteSelectionArray = Arrays.asList(listToDelete.split(","))
//						.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
//				computerService.deleteAllComputerSelected(deleteSelectionArray);
//			} catch(NumberFormatException e) {
//				
//			}
//			
//		}
//		
//		String orderBy = "computer.name";
//		if(request.getParameter("order") != null) {
//			orderBy = OrderSQL.orderSort(request.getParameter("order"));
//			request.setAttribute("order", request.getParameter("order"));
//		}
//		
//		String search = request.getParameter("searchForm");
//		if(search != null && !search.isEmpty()) {
//			if(request.getParameter("taillePage")!=null) {
//				taillePage=Integer.parseInt(request.getParameter("taillePage"));
//			}
//			if(request.getParameter("pageIterator")!=null) {
//				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));	
//			}
//			List<Computer> computerList = computerService.findName(search,pageIterator*taillePage, taillePage,orderBy);
//			List<ComputerDto> ComputerDtoList= computerList.stream().map(computer -> new ComputerMapper().computerToComputerDto(computer)).collect(Collectors.toList());
//			request.setAttribute("computerSearchedList", ComputerDtoList);
//			request.setAttribute("search", search);
//			int nbComputer= ComputerDtoList.size();
//			request.setAttribute("nbComputer", nbComputer);
//		
//		
//		
//		if(request.getParameter("pageIterator")!=null) {
//			pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
//			computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy);
//
//		}
//		else {
//			pageIterator=0;
//			computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy); 
//		}
//		request.setAttribute("pageIterator", pageIterator);
//		}
//		request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
//	
//	}	





