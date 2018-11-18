package es.upm.dit.apsv.webLab.cris.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

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
		Session session = SessionFactoryService.get().openSession();
		try {
		            	session.beginTransaction();
		            	session.save(publication);
		            	session.getTransaction().commit();
		} catch (Exception e) {
		            	// handle exceptions
		} finally {
		            	session.close();
		}
		return publication;
	}

	@Override
	public Publication read(String publicationId) {
		Session session = SessionFactoryService.get().openSession();
		Publication publication = null;
		try {
		            	session.beginTransaction();
		            	publication = session.get(Publication.class,publicationId);
		            	session.getTransaction().commit();
		} catch (Exception e) {
		            	// handle exceptions
		} finally {
		            	session.close();
		}
		return publication;
	}

	@Override
	public Publication update(Publication publication) {
		Session session = SessionFactoryService.get().openSession();
		try {
		            	session.beginTransaction();
		            	session.saveOrUpdate(publication);
		            	session.getTransaction().commit();
		} catch (Exception e) {
		            	// handle exceptions
		} finally {
		            	session.close();
		}

		return publication;
	}

	@Override
	public Publication delete(Publication publication) {
		Session session = SessionFactoryService.get().openSession();
		try {
        	session.beginTransaction();
        	session.delete(publication);
        	session.getTransaction().commit();
		} catch (Exception e) {
		        	// handle exceptions
		} finally {
		        	session.close();
		}

		return publication;
	}

	@Override
	public List<Publication> readAll() {
		Session session = SessionFactoryService.get().openSession();
		try {
        	session.beginTransaction();
        	session.createQuery("from Publication").list(); // Es como hacer SELECT * FROM Publication
        	session.getTransaction().commit();
		} catch (Exception e) {
		        	// handle exceptions
		} finally {
		        	session.close();
		}
		return null;
	}

	@Override
	public List<Publication> parsePublications(Collection<String> ids) {
		List<Publication> rList = new ArrayList<>();
		for (String id : ids) {
			Publication p = read(id);
			if(null != p) {
				rList.add(p);
			}
		}
		return rList;
	}

}
