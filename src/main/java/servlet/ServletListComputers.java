package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import Logger.Logging;
import dao.Connexion;
import dto.ComputerDto;
import mapper.ComputerMapper;
import model.Computer;
import services.ComputerService;

/**
 * @author Djamel
 *
 */
public class ServletListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private Logging log;
	private Connexion conn;
	private int taillePage = 20;
	private int pageIterator;
	 

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		//request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
		ComputerService service;
		//this.log = new Logging();
		//this.conn = Connexion.getInstance();
		//conn.connect();
		try {
			service = ComputerService.getInstance();
			int nbComputer = service.readAll().size();
			int nbPages = nbComputer/taillePage;
			request.setAttribute("nbPages", nbPages);
			List<ComputerDto>computerDtoList=new ArrayList<ComputerDto>();
			List<Computer>computerList=new ArrayList<Computer>();
			
			computerList=service.getPageComputer(pageIterator*taillePage,taillePage );
			
			for(Computer comp : computerList) {
				computerDtoList.add(ComputerMapper.getInstance().computerToComputerDto(comp));
			}
			request.setAttribute("nbComputer", nbComputer);
			request.setAttribute("computerList", computerDtoList);
			request.setAttribute("pageIterator", pageIterator);
			request.getRequestDispatcher("views/ListComputers.jsp").forward(request, response);
			
		} catch(Exception e) {
			//log.printError(e.getMessage());
		}
		
		
		
		
		

	}
}
