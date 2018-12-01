package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Servlet implementation class UpdateCitationsApiServlet
 */
@WebServlet("/UpdateCitationsApiServlet")
public class UpdateCitationsApiServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String id = req.getParameter("id");
		String APIkey = "dfe27aca254953ae7089063863e86f94";
		String url = "https://api.elsevier.com/content/abstract/scopus_id/"+id+"?apiKey="+APIkey;
		System.out.println(url);
		JSONObject response = getAPI(url);
		
		if(response != null) {
			JSONObject firstlevel = (JSONObject)response.get("abstracts-retrieval-response");
			JSONObject secondlevel = (JSONObject)firstlevel.get("coredata");
			int citedBy = Integer.parseInt((String)secondlevel.get("citedby-count"));
			PublicationDAOImplementation pdao = PublicationDAOImplementation.getInstance();
			Publication publication = pdao.read(id);
			publication.setCiteCount(citedBy);
			pdao.update(publication);
		}
		resp.sendRedirect(req.getContextPath()+"/PublicationServlet?id="+id);
	}
	
	private JSONObject getAPI(String targetUrl) {
	    JSONObject object = null;
	    try {
	        URL url = new URL(targetUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setInstanceFollowRedirects(false);
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("Accept", "application/json");
	        System.out.println("holaaaaaaaaaaaaaaaa");
	        int responseCode = connection.getResponseCode();
	        if(responseCode>=200 && responseCode<300) {
	            InputStream is = connection.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is);
	            object =(JSONObject) new JSONParser().parse(isr);
	            is.close();
	        } else {
	            System.err.println("Request returned code "+ responseCode);
	            System.err.println(connection.getResponseMessage());
	        }
	        connection.getResponseCode();
	        connection.disconnect();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return object;
	}


	

}
