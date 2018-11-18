package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

@WebServlet("/CreateResearcherServlet")
public class CreateResearcherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("true".equals(req.getSession().getAttribute("userAdmin"))) {
			String id = req.getParameter("uid");
			String name = req.getParameter("name");
			String lastName = req.getParameter("last_name");
			Researcher r = new Researcher();
			r.setId(id);
			r.setName(name);
			r.setLastName(lastName);
			ResearcherDAOImplementation.getInstance().create(r);
			resp.sendRedirect(req.getContextPath() + "/ResearcherServlet?id=" + r.getId());

		} else {
			req.setAttribute("message", "You are not allowed to view this page");
			getServletContext().getRequestDispatcher("/FormLogin.jsp").forward(req, resp);
		}
	}

}