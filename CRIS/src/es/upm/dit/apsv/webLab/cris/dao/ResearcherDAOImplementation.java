package es.upm.dit.apsv.webLab.cris.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.apsv.webLab.cris.model.Researcher;

public class ResearcherDAOImplementation implements ResearcherDAO {

	private static ResearcherDAOImplementation instance;
	private ResearcherDAOImplementation() {}
	public static ResearcherDAOImplementation getInstance() {
		if (null == instance)
			instance = new ResearcherDAOImplementation();
		return instance;
	}
	
	@Override
	public Researcher create(Researcher researcher) {
		Session session = SessionFactoryService.get().openSession();
		try {
        	session.beginTransaction();
        	session.save(researcher);
        	session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
		    session.close();
		}
		
		return researcher;
	}
	
	@Override
	public Researcher read(String researcherId) {
		Researcher r = null;
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			r = session.get(Researcher.class,researcherId);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return r;
	}
	
	@Override
	public Researcher update(Researcher researcher) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(researcher);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return researcher;
	}
	@Override
	public Researcher delete(Researcher researcher) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(researcher);
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return researcher;
	}
	
	@Override
	public List<Researcher> readAll() {
		List<Researcher> rList = new ArrayList<>();
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			rList.addAll(session.createQuery("from Researcher").list()); // Es como hacer SELECT * FROM Researcher
			session.getTransaction().commit();
		} catch (Exception e) {
			
		} finally {
			session.close();
		}
		
		return rList;
	}
	@Override
	public Researcher readAsUser(String email, String password) {
		Session session = SessionFactoryService.get().openSession();
		Researcher researcher = null;
		try {
			session.beginTransaction();
			researcher = (Researcher) session
			        .createQuery("select r from Researcher r where r.email= :email and r.password = :password")
			        .setParameter("email", email)
			        .setParameter("password", password)
			        .uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
		        	e.printStackTrace();
		} finally {
		        	session.close();
		}
				
		return researcher;
	}
	@Override
	public List<Researcher> parseResearchers(List<String> ids) {
		List<Researcher> rList = new ArrayList<>();
		try {
			for (String id : ids) {
				Researcher r = read(id);
				if(null != r) {
					rList.add(r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rList;
	}
	
}
