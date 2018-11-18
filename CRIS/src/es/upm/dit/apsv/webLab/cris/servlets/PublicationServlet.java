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
 * Servlet implementation class PublicationServlet
 */
@WebServlet("/PublicationServlet")
public class PublicationServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		
		Publication p;
		p=PublicationDAOImplementation.getInstance().read(id);
		req.getSession().setAttribute("publication", p);
		List<String> rListId = new ArrayList<>();
		rListId.addAll(p.getAuthors());
		List<Researcher> rList = new ArrayList<>();
		rList.addAll(ResearcherDAOImplementation.getInstance().parseResearchers(rListId));
		req.getSession().setAttribute("authors", rList);
		Researcher firstAuthor = ResearcherDAOImplementation.getInstance().read(p.getFirstAuthor());
		req.getSession().setAttribute("firstAuthor", firstAuthor);
		getServletContext().getRequestDispatcher("/PublicationView.jsp").forward(req, resp);
	}

}
