package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.Arrays;
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
 * Servlet implementation class CreatePublicationServlet
 */
@WebServlet("/CreatePublicationServlet")
public class CreatePublicationServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Researcher firstAuthor = (Researcher) req.getSession().getAttribute("firstAuthor");
		Researcher user = (Researcher) req.getSession().getAttribute("user");
		if (user != null && user.getId().equals(req.getParameter("first_author"))) {
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String date = req.getParameter("publication_date");
			String name = req.getParameter("publication_name");
			String eid = req.getParameter("eid");
			Publication p = new Publication();
			p.setId(id);
			p.setFirstAuthor(user.getId());
			p.setAuthors(Arrays.asList(req.getParameter("authors").split(";")));
			p.setPublicationName(name);
			p.setTitle(title);
			p.setPublicationDate(date);
			p.setEid(eid);
			Publication existe = PublicationDAOImplementation.getInstance().read(id);
			if(existe == null) {
				PublicationDAOImplementation.getInstance().create(p);
				//debo añadir la publicacion al usuario
				List <String> publicationsR = user.getPublications();
				publicationsR.add(p.getId());
				user.setPublications(publicationsR);
				ResearcherDAOImplementation.getInstance().update(user);
			}
			resp.sendRedirect(req.getContextPath() + "/PublicationServlet?id=" + p.getId());

		} else {
			req.setAttribute("message", "You are not allowed to view this page");
			getServletContext().getRequestDispatcher("/ResearcherListServlet").forward(req, resp);
		}
	}

}
