package servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import dao.Connexion;
import dto.ComputerDto;
import exceptions.Logging;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import services.CompanyService;
import services.ComputerService;

/**
 * @author Djamel
 *
 */

@Controller
@RequestMapping(value="/ListComputers")
public class ServletListComputers {
	private static final long serialVersionUID = 1L;

	private Integer taillePage = 20;
	private Integer pageIterator = 0;
	List<Computer> computerListPagine;
	
	private ComputerService computerService;
	private CompanyService companyService;
	
	public ServletListComputers(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}
	
	@GetMapping()
	public ModelAndView ListComputers(@RequestParam(required = false, value = "pageIterator") Integer pageIt,
			@RequestParam(required = false, value = "taillePage") Integer tailleP,
			@RequestParam(required = false, value = "order") String orderBy,
			@RequestParam(required = false, value = "nbComputer") Integer nbComputer,
			@RequestParam(required = false, value = "nbPages") Integer nbPages,
			@RequestParam(required = false, value = "searchForm") String search) {
		
		
		if(pageIt != null)
			pageIterator = pageIt;
		if(tailleP!=null)
			taillePage = tailleP;
		ModelAndView modelAndView = new ModelAndView("ListComputers");
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDto> computerDtoList = new ArrayList<>();
		if(search == null) {
			computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,orderBy);
			computerDtoList = computerList.stream().map(computer -> new ComputerMapper().computerToComputerDto(computer)).collect(Collectors.toList());
		}
		else {
			computerList=computerService.findName(search, pageIterator*taillePage, taillePage, orderBy);
			computerDtoList = computerList.stream().map(computer -> new ComputerMapper().computerToComputerDto(computer)).collect(Collectors.toList());

		}
		
		nbComputer = computerService.readAll().size();
		List<Computer> tmp= computerService.readAll();
		nbPages = (int) Math.ceil((double)nbComputer/taillePage);
		
		
		setDashboardAttribute(modelAndView,pageIterator,taillePage,orderBy,nbComputer,nbPages,search,computerDtoList);
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
	
	
	private void setDashboardAttribute(ModelAndView modelAndView, Integer pageIterator, Integer taillePage, String orderBy, int nbComputer,
		int nbPages, String searchForm,List<ComputerDto> computerList) {
		modelAndView.addObject("taillePage", taillePage);
		modelAndView.addObject("pageIterator", pageIterator);
		modelAndView.addObject("nbComputer", nbComputer);
		modelAndView.addObject("nbPages", nbPages);
		modelAndView.addObject("order", orderBy);
		modelAndView.addObject("searchForm", searchForm);
		modelAndView.addObject("computerList", computerList);
	}
	
	
}





