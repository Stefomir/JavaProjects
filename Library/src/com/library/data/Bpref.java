package com.library.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "T_BPREF")
@NamedQueries({
@NamedQuery(name = "AllBpref", query = "select bp from Bpref bp"),
@NamedQuery(name = "History", query = "select bp, b from Bpref bp,Books b where bp.idBooks = b.idBooks and bp.idPerson = :idPerson"),
@NamedQuery(name = "BookTaken", query = "select bp, b from Bpref bp, Books b where bp.idBooks = b.idBooks and bp.datereturned is null and bp.idPerson = :idPerson"),
@NamedQuery(name = "EditBpref", query = "select bp, b, p from Bpref bp ,Books b ,Person p where bp.idBooks = b.idBooks and bp.idPerson = p.idPerson and bp.idBpref = :idBpref"),
@NamedQuery(name = "FromAllTables", query = "select bp, b, p from Bpref bp ,Books b ,Person p where bp.idBooks = b.idBooks and bp.idPerson = p.idPerson and bp.istaken = :istaken")
})
public class Bpref {

	 @Id
	    @GeneratedValue
	    private Long idBpref;
	    @Basic
	    private Long idBooks;
	    @Basic
	    private Long idPerson;
	    @Basic
	    private char istaken = 'Y';
	    @Basic
	    private String datereturned = null;
	    @Basic
	    private String datetaken = null;
		public Long getIdBpref() {
			return idBpref;
		}
		public void setIdBpref(Long idBpref) {
			this.idBpref = idBpref;
		}
		public Long getIdBooks() {
			return idBooks;
		}
		public void setIdBooks(Long idBooks) {
			this.idBooks = idBooks;
		}
		public Long getIdPerson() {
			return idPerson;
		}
		public void setIdPerson(Long idPerson) {
			this.idPerson = idPerson;
		}
		public char getIstaken() {
			return istaken;
		}
		public void setIstaken(char istaken) {
			this.istaken = istaken;
		}
		public String getDatereturned() {
			return datereturned;
		}
		public void setDatereturned(String datereturned) {
			this.datereturned = datereturned;
		}
		public String getDatetaken() {
			return datetaken;
		}
		public void setDatetaken(String datetaken) {
			this.datetaken = datetaken;
		}
}
