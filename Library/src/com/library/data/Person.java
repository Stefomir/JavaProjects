package com.library.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "T_PERSON")
@NamedQueries({
@NamedQuery(name = "AllPerson", query = "select p from Person p"),
@NamedQuery(name = "PersonViaId", query = "select p from Person p where p.idPerson= :idPerson"),
@NamedQuery(name = "CheckForNewPerson", query = "select p from Person p where p.fknumber = :fknumber"),
@NamedQuery(name = "PersonViaFknumber", query = "select p from Person p where p.fknumber = :fknumber"),
@NamedQuery(name = "IdPersonViaFknumber", query = "select p from Person p where p.fknumber = :fknumber"),
@NamedQuery(name = "CheckForNewPersonWithoutId", query = "select p from Person p where p.fknumber = :fknumber and p.idPerson <> :idPerson")
})
public class Person {

	 @Id
	    @GeneratedValue
	    private Long idPerson;
	    @Basic
	    private String name;
	    @Basic
	    private String fknumber;
	    
	    public Long getIdPerson() {
			return idPerson;
		}
		public void setIdPerson(Long idPerson) {
			this.idPerson = idPerson;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFknumber() {
			return fknumber;
		}
		public void setFknumber(String fknumber) {
			this.fknumber = fknumber;
		}
		
}
