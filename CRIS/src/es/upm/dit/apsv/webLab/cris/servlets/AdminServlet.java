package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String admin = (String) req.getSession().getAttribute("userAdmin");
		if (admin !=null && admin.equals("true")) { //Si es null tira exception si no
			getServletContext().getRequestDispatcher("/AdminView.jsp").forward(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/LoginServlet");
		}
	}

	

}
