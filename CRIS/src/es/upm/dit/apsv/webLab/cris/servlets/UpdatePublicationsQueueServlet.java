package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.ReceivedMessage;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;


/**
 * Servlet implementation class UpdatePublicationsQueueServlet
 */
@WebServlet("/UpdatePublicationsQueueServlet")
public class UpdatePublicationsQueueServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		ResearcherDAOImplementation rdao = ResearcherDAOImplementation.getInstance();
		PublicationDAOImplementation pdao = PublicationDAOImplementation.getInstance();
		Researcher researcher = rdao.read(id);
		
		//conectar cola
		String projectId = "peerless-haiku-223415";
		String subscriptionId = "cris";
		SubscriberStubSettings subscriberStubSettings = SubscriberStubSettings.newBuilder().build();
		SubscriberStub subscriber = 
		GrpcSubscriberStub.create(subscriberStubSettings);
		String subscriptionName = ProjectSubscriptionName.format(projectId, subscriptionId);
		PullRequest pullRequest = PullRequest.newBuilder()
		        .setMaxMessages(100)
		        .setReturnImmediately(true)
		        .setSubscription(subscriptionName)
		        .build();
		//iterar mensajes
		List<String> ackIds = new ArrayList<>();
		PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
		for (ReceivedMessage message : pullResponse.getReceivedMessagesList()) {
			try {
				JSONObject jsonPublication = (JSONObject) new JSONParser().parse(
				        message.getMessage().getData().toStringUtf8());
				Publication publication = new Publication();
				//recibo un long y debo convertirlo a String
				publication.setId((Long)jsonPublication.get("id")+"");
				publication.setEid((String)jsonPublication.get("eid"));
				publication.setTitle((String)jsonPublication.get("title"));
				publication.setPublicationName((String)jsonPublication.get("publicationName"));
				publication.setPublicationDate((String)jsonPublication.get("publicationDate"));
				publication.setFirstAuthor((Long)jsonPublication.get("firstAuthor")+"");
				//convierto el string en un arrayList
				publication.setAuthors(Arrays.asList(((String) jsonPublication.get("authors")).split(";")));
				 					        
				//si el autor es el researcher creo la publicación y la asocio al researcher
				if(researcher.getId().equals(publication.getFirstAuthor())) {
					//el ack de que ya se leyó ese elemento el del autor
					ackIds.add(message.getAckId());
					pdao.create(publication);
					researcher.getPublications().add(publication.getId());
					rdao.update(researcher);
					}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		if(!ackIds.isEmpty()) {
		    AcknowledgeRequest acknowledgeRequest = AcknowledgeRequest.newBuilder()
		            .setSubscription(subscriptionName)
		            .addAllAckIds(ackIds)
		            .build();
		    subscriber.acknowledgeCallable().call(acknowledgeRequest);
		}
		resp.sendRedirect(req.getContextPath()+"/ResearcherServlet?id="+id);
	}

	

}
