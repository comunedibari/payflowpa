
package it.tasgroup.idp.anagrafica;

import it.tasgroup.ge.IntestatariOperatori;
import it.tasgroup.idp.domain.BaseEntity;
import it.tasgroup.idp.domain.bonifici.Intestatari;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Pojo che contiene le informazioni di un operatore.
 * 
 * 
 */
@Entity
@Table(name = "OPERATORI")
@NamedQueries({
		@NamedQuery(name = "getOperatoreByUsername", 
			query = "select op from Operatori op where op.username = :username")
})
public class Operatori extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/*** Persistent Properties ***/
	private String operatore;
	private String username;
	private String corporate;
	private Integer locked;
	private String mobile;
	private String signerCode;
	private String description;
//	private String email;
	private Intestatari intestatario;
	private String name;
	private Integer numFailedlogon;
	private String password;
	private String plainPassword;
	private Timestamp lastLogon;
	private Date expirationDate;
	private Timestamp lockDate;
	private Timestamp failedLogonDate;
	private String codiceFiscale;
	private String surname;
	private String flagOperatorType;

	
		
	@Id
	public String getOperatore() {
		return this.operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	@Column(name = "BLOCCATO")
	public Integer getLocked() {
		return this.locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	@Column(name = "CELLULARE")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "CODICEFIRMATARIO")
	public String getSignerCode() {
		return this.signerCode;
	}

	public void setSignerCode(String signerCode) {
		this.signerCode = signerCode;
	}

	@Column(name = "DESCRIZIONE")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public String getEmail() {
//		return this.email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	@Column(name = "NOME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NULL_COLL_FALL")
	public Integer getNumFailedlogon() {
		return this.numFailedlogon;
	}

	public void setNumFailedlogon(Integer numFailedlogon) {
		this.numFailedlogon = numFailedlogon;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ULTIMOCOLLEGAMENTO")
	public Timestamp getLastLogon() {
		return this.lastLogon;
	}

	public void setLastLogon(Timestamp lastLogon) {
		this.lastLogon = lastLogon;
	}

	@Column(name = "DATASCADENZA")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name = "DATABLOCCO")
	public Timestamp getLockDate() {
		return this.lockDate;
	}

	public void setLockDate(Timestamp lockDate) {
		this.lockDate = lockDate;
	}

	@Column(name = "DATA_COLL_FALL")
	public Timestamp getFailedLogonDate() {
		return this.failedLogonDate;
	}

	public void setFailedLogonDate(Timestamp failedLogonDate) {
		this.failedLogonDate = failedLogonDate;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@Column(name = "COGNOME")
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@OneToOne(targetEntity=Intestatari.class,fetch=FetchType.LAZY ,cascade=CascadeType.ALL)
    @JoinColumn(name="INTESTATARIO")
    public Intestatari getIntestatari() {
 		return this.intestatario;
 	}
    public void setIntestatari(Intestatari intestatario){
 		this.intestatario=intestatario;
    }

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operatore == null) ? 0 : operatore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operatori other = (Operatori) obj;
		if (operatore == null) {
			if (other.operatore != null)
				return false;
		} else if (!operatore.equals(other.operatore))
			return false;
		return true;
	}
	@Override 
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Operatori [operatore=");
		sb.append(operatore);
		sb.append(", \nusername=");
		sb.append(username);
		sb.append(", \ncorporate=");
		sb.append(corporate);		
		sb.append(", \nlocked=");
		sb.append(locked);
		sb.append(", \nmobile=");
		sb.append(mobile);
		sb.append(", \nsignerCode=");
		sb.append(signerCode);
		sb.append(", \ndescription=");
		sb.append(description);
		sb.append(", \nname=");
		sb.append(name);
		sb.append(", \nnumFailedlogon=");
		sb.append(numFailedlogon);
		sb.append(", \npassword=");
		sb.append(password);
		sb.append(", \nplainPassword=");
		sb.append(plainPassword);
		sb.append(", \nlastLogon=");
		sb.append(lastLogon);
		sb.append(", \nexpirationDate=");
		sb.append(expirationDate);
		sb.append(", \nlockDate=");
		sb.append(lockDate);
		sb.append(", \nfailedLogonDate=");
		sb.append(failedLogonDate);
		sb.append(", \ncodiceFiscale=");
		sb.append(codiceFiscale);
		sb.append(", \nsurname=");
		sb.append(surname);
		sb.append(", \nflagOperatorType=");
		sb.append(flagOperatorType);
		sb.append(", \nintestatario=");
		if (intestatario==null){
			sb.append("<empty>");
		}else {
			sb.append(intestatario.toString());
		}
		sb.append(" ]");
		return sb.toString();
	}

}
