package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

/**
 * Servlet implementation class CreatePublicationFromEmailServlet
 */
//en vez de activarse con ruta se activa con un email recibido
@WebServlet("/_ah/mail/*")
public class CreatePublicationFromEmailServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			MimeMessage message = new MimeMessage(session, req.getInputStream());
			//Remitente
			String from = new InternetAddress( message.getFrom()[0].toString()).getAddress();
			//Asunto
			String subject = message.getSubject();
			Researcher researcher = rdao.readAsUser(from,null);
			if(null != researcher) {
				 String[] lSplit = subject.split(",");
				    Publication p = new Publication();
				    p.setId(lSplit[0]);
				    p.setTitle(lSplit[1]);
				    p.setEid(lSplit[2]);
				    p.setPublicationName(lSplit[3]);
				    p.setPublicationDate(lSplit[4]);
			    	p.setFirstAuthor(researcher.getId());
			    	p.setAuthors(Arrays.asList(lSplit[6].split(";")));
			    	if(pdao.read(p.getId())==null) {
			    		pdao.create(p);
			    		researcher.getPublications().add(p.getId());
			    		rdao.update(researcher);
			    	}
			}
		}catch(Exception e) {
			
		}
		
	}

}
