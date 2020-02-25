package servlet;


import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Djamel
 *
 */
public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		request.getRequestDispatcher("views/editComputer.html").forward(request, response);

	}
}
