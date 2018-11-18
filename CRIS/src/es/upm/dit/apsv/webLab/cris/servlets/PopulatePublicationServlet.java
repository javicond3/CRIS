package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

/**
 * Servlet implementation class PopulatePublicationServlet
 */
@WebServlet("/PopulatePublicationServlet")
@MultipartConfig
public class PopulatePublicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Part filePart = req.getPart("file"); //Cogemos el archivo
		InputStream fileContent = filePart.getInputStream();
		
		ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		
		BufferedReader bReader = new BufferedReader( //Quitamos la cabecera
			      new InputStreamReader(fileContent));
		String line = bReader.readLine();

			while (null != (line = bReader.readLine())) {
			    String[] lSplit = line.split(",");
			    Publication p = new Publication();
			    p.setId(lSplit[0]);
			    p.setTitle(lSplit[1]);
			    p.setEid(lSplit[2]);
			    p.setPublicationName(lSplit[3]);
			    p.setPublicationDate(lSplit[4]);
		    	p.setFirstAuthor(lSplit[5]);
		    	p.setAuthors(Arrays.asList(lSplit[6].split(";")));
		    	Researcher r = rdao.read(p.getFirstAuthor());
			    if (null != r && null == pdao.read(p.getId())) { // Si no está creado, lo creo
			    	pdao.create(p);
			    	r.getPublications().add(p.getId());
			    	rdao.update(r);
			    }
			}
			resp.sendRedirect(req.getContextPath() + "/AdminServlet");
	}

}
