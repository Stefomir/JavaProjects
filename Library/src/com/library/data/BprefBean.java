package com.library.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class BprefBean
 */
@Stateless
@LocalBean
public class BprefBean {

	   @PersistenceContext
		private EntityManager em;
	    
	    @SuppressWarnings("unchecked")
		public List<Bpref> getAllBpref() {
			return em.createNamedQuery("AllBpref").getResultList();
		}
	    
	    @SuppressWarnings("unchecked")
		public List<Object[]> getHistory(Long idPerson) {
	    	return em.createNamedQuery("History").setParameter("idPerson", idPerson).getResultList();
	    }
	    
		@SuppressWarnings("unchecked")
		public List<Object[]> getEditBpref(Long idBpref) {
			return em.createNamedQuery("EditBpref").setParameter("idBpref", idBpref).getResultList();
		}
		
	    @SuppressWarnings("unchecked")
		public List<Object[]> getBookTaken(Long idPerson) {
	    	return em.createNamedQuery("BookTaken").setParameter("idPerson", idPerson).getResultList();
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<Object[]> getFromAllTables(char istaken) {
	    	return em.createNamedQuery("FromAllTables").setParameter("istaken", istaken).getResultList();
	    }
	    
	    public void addBpref(Bpref bpref) {
			em.persist(bpref);
			em.flush();
		}
	    
	    public void delBpref(Long idBpref) {
		     Bpref bp = em.find(Bpref.class, idBpref);
		     if ( bp!= null) 
		          em.remove(bp);	              
		 }
	    
	    public void updateRecord(Long idBpref,Long newIdBooks, Long newIdPerson, char newIstaken) {
	    	Bpref bp = em.find(Bpref.class, idBpref);
		     if ( bp!= null){
		      bp.setIdBooks(newIdBooks);
		      bp.setIdPerson(newIdPerson);
		      bp.setIstaken(newIstaken);
		      em.flush();
		     } 
	    }
	    
	    public void updateReturn(Long idBpref, String datereturned, char istaken){
	    	Bpref bp = em.find(Bpref.class, idBpref);
	    	if(bp != null){
	    		//em.getTransaction().begin();
	    		bp.setDatereturned(datereturned);
	    		bp.setIstaken(istaken);
	    		//em.getTransaction().commit();
	    		em.flush();
	    	}
	    }
	    
	    public void updateBookDate(Long idBooks, String datereturned, char istaken) {
	    	Bpref bp = em.find(Bpref.class, idBooks);
	    	if ( bp!= null){
	    		//em.getTransaction().begin();
	    	 bp.setDatereturned(datereturned);
	    	 bp.setIstaken(istaken);
	    	 //em.getTransaction().commit();
	    	 em.flush();
	    	}
	    }
	

}
