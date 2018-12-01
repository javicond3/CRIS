package es.upm.dit.apsv.webLab.cris.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class PublicationDAOImplementation implements PublicationDAO{

	private static PublicationDAOImplementation instance;
	private PublicationDAOImplementation() {}
	public static PublicationDAOImplementation getInstance() {
		if (null == instance)
			instance = new PublicationDAOImplementation();
		return instance;
		
	}
	
	@Override
	public Publication create(Publication publication) {
		ofy().save().entity(publication).now();
		return publication;
	}

	@Override
	public Publication read(String publicationId) {
		return ofy().load().type(Publication.class).id(publicationId).now();
		
	}

	@Override
	public Publication update(Publication publication) {
		ofy().save().entity(publication).now();
		return publication;
		
	}

	@Override
	public Publication delete(Publication publication) {
		ofy().delete().entity(publication).now();
		return publication;
	}

	@Override
	public List<Publication> readAll() {
		ArrayList<Publication> list = new ArrayList<>();
		list.addAll(ofy().load().type(Publication.class).list());
		return list;
		
	}

	@Override
	public List<Publication> parsePublications(Collection<String> ids) {
		ArrayList<Publication> list = new ArrayList<>();
		list.addAll(ofy().load().type(Publication.class).ids(ids).values());
		return list;
	}

}
