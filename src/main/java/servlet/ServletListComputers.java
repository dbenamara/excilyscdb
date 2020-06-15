package servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//import dao.Connexion;
import dto.ComputerDto;
import dto.RequestParamDto;
import exceptions.Logging;
import mapper.CompanyMapper;
import mapper.ComputerMapper;
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
//	private static final long serialVersionUID = 1L;

	private Integer taillePage = 20;
	private Integer pageIterator = 0;
	List<Computer> computerListPagine;
	
	private ComputerService computerService;
	private CompanyService companyService;
	ComputerMapper computerMapper;
	CompanyMapper companyMapper;
	
	public ServletListComputers(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}
	
	@GetMapping
	public ModelAndView ListComputers(RequestParamDto requestParamDto) {
		
		
		if(requestParamDto.getPageIterator() != null)
			pageIterator = Integer.parseInt(requestParamDto.getPageIterator());
		if(requestParamDto.getTaillePage()!=null)
			taillePage = Integer.parseInt(requestParamDto.getTaillePage());
		ModelAndView modelAndView = new ModelAndView("ListComputers");
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDto> computerDtoList = new ArrayList<>();
		Integer nbComputer=0;
		
		if(requestParamDto.getSearch() == null) {
			computerList=computerService.getPageComputer(pageIterator*taillePage,taillePage,requestParamDto.getOrderBy());
			computerDtoList = computerList.stream().map(computer -> computerMapper.computerToComputerDto(computer)).collect(Collectors.toList());
			nbComputer = computerService.readAll().size();
		}
		else {
			computerList=computerService.findName(requestParamDto.getSearch(), pageIterator*taillePage, taillePage, requestParamDto.getOrderBy());
			System.out.println(computerList);
			computerDtoList = computerList.stream().map(computer -> computerMapper.computerToComputerDto(computer)).collect(Collectors.toList());
	
			nbComputer = computerService.findName(requestParamDto.getSearch(), pageIterator*taillePage, taillePage, requestParamDto.getOrderBy()).size();
		}
		
		
		Integer nbPages = (int) Math.ceil((double)nbComputer/taillePage);
		requestParamDto.setNbPages(String.valueOf(nbPages));
		
		
		setDashboardAttribute(modelAndView,pageIterator,taillePage,requestParamDto.getOrderBy(),nbComputer,nbPages,requestParamDto.getSearch(),computerDtoList);
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



