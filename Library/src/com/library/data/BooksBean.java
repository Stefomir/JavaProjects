package com.library.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class BooksBean
 */
@Stateless
@LocalBean
public class BooksBean {

    @PersistenceContext
	private EntityManager em;
    
    @SuppressWarnings("unchecked")
	public List<Books> getAllBooks() {
		return em.createNamedQuery("AllBooks").getResultList();
	}
    
    @SuppressWarnings("unchecked")
	public List<Books> getBooksViaId(long idBooks) {
    	return em.createNamedQuery("BooksViaId").setParameter("idBooks", idBooks).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Books> getBooksViaNameAndAuthor(String name, String author) {
    	return em.createNamedQuery("CheckForNewBook").setParameter("name", name).setParameter("author", author).getResultList();
    }
    
	public boolean checkForNewBook(String name, String author) {
    	return em.createNamedQuery("CheckForNewBook").setParameter("name", name).setParameter("author", author).getResultList().isEmpty();
    }
    
    public boolean checkForNewBookWithoutId(String name, String author, Long idBooks){
    	return em.createNamedQuery("CheckForNewBookWithoutId").setParameter("name", name).setParameter("author", author).setParameter("idBooks", idBooks).getResultList().isEmpty();
    }
    
    public void addBook(Books books) {
		em.persist(books);
		em.flush();
	}
    
    public void delBook(Long idBooks) {
	     Books bs = em.find(Books.class, idBooks);
	     if ( bs!= null) 
	          em.remove(bs);
	 
	 }
    
    public void updateRecord(Long idBooks,String newName, String newAuthor, String newFlow, String newCredit) {
	     Books bs = em.find(Books.class, idBooks);
	     if ( bs!= null){
	    	 //em.getTransaction().begin();
	      bs.setName(newName);
	      bs.setAuthor(newAuthor);
	      bs.setFlow(newFlow);
	      bs.setCredit(newCredit);
	      //em.getTransaction().commit();
	      em.flush();
	     } 
	 }
   
    public void updateBookViaFlowAndCredit(Long idBooks, String newFlow, String newCredit) {
    	
    	Books bs = em.find(Books.class, idBooks);
	     if ( bs!= null){
	    	// em.getTransaction().begin();
	      bs.setFlow(newFlow);
	      bs.setCredit(newCredit);
	    //  em.getTransaction().commit();
	      em.flush();
	     } 
	 }
}
