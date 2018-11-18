package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

/**
 * Servlet implementation class ResearcherServlet
 */
@WebServlet("/ResearcherServlet")
public class ResearcherServlet extends HttpServlet {
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		
		Researcher r;
		r=ResearcherDAOImplementation.getInstance().read(id);
		req.getSession().setAttribute("researcher", r);
		List<String> pListId = new ArrayList<>();
		pListId.addAll(r.getPublications());
		List<Publication> pList = new ArrayList<>();
		pList.addAll(PublicationDAOImplementation.getInstance().parsePublications(pListId));
		req.getSession().setAttribute("publication", pList);
		getServletContext().getRequestDispatcher("/ResearcherView.jsp").forward(req, resp);
	}


}
