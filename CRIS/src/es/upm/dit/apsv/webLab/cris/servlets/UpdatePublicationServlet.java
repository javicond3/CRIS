package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
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
 * Servlet implementation class UpdatePublicationServlet
 */
@WebServlet("/UpdatePublicationServlet")
public class UpdatePublicationServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Researcher firstAuthor = (Researcher) req.getSession().getAttribute("firstAuthor");
		Researcher user = (Researcher) req.getSession().getAttribute("user");
		if ((user != null && user.getId().equals(firstAuthor.getId()))||"true".equals(req.getSession().getAttribute("userAdmin"))) {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String title = req.getParameter("title");
			String date = req.getParameter("date");
			String eid = req.getParameter("eid");
			Publication p = (Publication) req.getSession().getAttribute("publication");
			p.setPublicationName(name);
			p.setTitle(title);
			p.setPublicationDate(date);
			p.setEid(eid);
			PublicationDAOImplementation.getInstance().update(p);
			resp.sendRedirect(req.getContextPath() + "/PublicationServlet?id=" + p.getId());

		} else {
			req.setAttribute("message", "You are not allowed to view this page");
			getServletContext().getRequestDispatcher("/ResearcherListServlet").forward(req, resp);
		}
	}

}
