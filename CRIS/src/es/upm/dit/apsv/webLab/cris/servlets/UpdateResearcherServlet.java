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
 * Servlet implementation class UpdateResearcherServlet
 */
@WebServlet("/UpdateResearcherServlet")
public class UpdateResearcherServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Researcher user = (Researcher) req.getSession().getAttribute("user");
		if ((user != null && user.getId().equals(req.getParameter("id")))||"true".equals(req.getSession().getAttribute("userAdmin"))) {
			String name = req.getParameter("name");
			String lastName = req.getParameter("last_name");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String scopusUrl = req.getParameter("scopus_url");
			String eid = req.getParameter("eid");
			Researcher r = (Researcher) req.getSession().getAttribute("researcher");
			r.setName(name);
			r.setLastName(lastName);
			r.setEmail(email);
			r.setPassword(password);
			r.setScopusUrl(scopusUrl);
			r.setEid(eid);
			ResearcherDAOImplementation.getInstance().update(r);
			resp.sendRedirect(req.getContextPath() + "/ResearcherServlet?id=" + r.getId());

		} else {
			req.setAttribute("message", "You are not allowed to view this page");
			getServletContext().getRequestDispatcher("/ResearcherListServlet").forward(req, resp);
		}
	}

}
