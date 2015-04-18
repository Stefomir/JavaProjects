package com.library.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "T_BOOKS")
@NamedQueries({
@NamedQuery(name = "AllBooks", query = "select b from Books b"),
@NamedQuery(name = "BooksViaId", query = "select b from Books b where b.idBooks= :idBooks"),
@NamedQuery(name = "CheckForNewBook", query = "select b from Books b where b.name = :name and b.author = :author"),
@NamedQuery(name = "CheckForNewBookWithoutId", query = "select b from Books b where b.name = :name and b.author = :author and b.idBooks <> :idBooks")
})
public class Books {

	 @Id
	    @GeneratedValue
	    private Long idBooks;
	    @Basic
	    private String name;
	    @Basic
	    private String author;
	    @Basic
	    private String flow;
	    @Basic
	    private String credit;
		public Long getIdBooks() {
			return idBooks;
		}
		public void setIdBooks(Long idBooks) {
			this.idBooks = idBooks;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getFlow() {
			return flow;
		}
		public void setFlow(String flow) {
			this.flow = flow;
		}
		public String getCredit() {
			return credit;
		}
		public void setCredit(String credit) {
			this.credit = credit;
		}
}
