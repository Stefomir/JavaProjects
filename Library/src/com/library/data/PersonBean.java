package com.library.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class PersonBean
 */
@Stateless
@LocalBean
public class PersonBean {

	 @PersistenceContext
		private EntityManager em;
	    
	    @SuppressWarnings("unchecked")
		public List<Person> getAllPerson() {
			return em.createNamedQuery("AllPerson").getResultList();
		}
	    
	    @SuppressWarnings("unchecked")
		public List<Person> getPersonViaId(Long idPerson) {
	    	return em.createNamedQuery("PersonViaId").setParameter("idPerson", idPerson).getResultList();
	    }
	    
	    @SuppressWarnings("unchecked")
		public List<Person> getPersonViaFknumber(String fknumber) {
	    	return em.createNamedQuery("PersonViaFknumber").setParameter("fknumber", fknumber).getResultList();
	    }
	    
	    @SuppressWarnings("unchecked")
		public List<Person> getIdPersonViaFknumber(String fknumber) {
	    	return em.createNamedQuery("IdPersonViaFknumber").setParameter("fknumber", fknumber).getResultList(); 
	    }
	    
	    public boolean checkForNewPerson(String fknumber) {
	    	return em.createNamedQuery("CheckForNewPerson").setParameter("fknumber", fknumber).getResultList().isEmpty();
	    }
	    
	    public boolean checkForNewPersonWithoutId(String fknumber, Long idPerson) {
	    	return em.createNamedQuery("CheckForNewPersonWithoutId").setParameter("fknumber", fknumber).setParameter("idPerson", idPerson).getResultList().isEmpty();
	    }
	    
	    public void addPerson(Person person) {
			em.persist(person);
			em.flush();
		}
	    
	    public void delPerson(Long idPerson) {
		     Person ps = em.find(Person.class, idPerson);
		     if ( ps!= null) 
		          em.remove(ps);	              
		 }
	    
	    public void updateRecord(Long idPerson,String newName, String newFknumber) {
	    	Person ps = em.find(Person.class, idPerson);
		     if ( ps!= null){
		      ps.setName(newName);
		      ps.setFknumber(newFknumber);
		     } 
		 }
	}
