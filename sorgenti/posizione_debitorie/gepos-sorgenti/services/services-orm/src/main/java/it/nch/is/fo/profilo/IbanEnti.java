
package it.nch.is.fo.profilo;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

@Entity
@Table(name="IBAN_ENTI")


public class IbanEnti extends PojoImpl implements IbanEntiCommon {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Properties***/
	private String idEnte;
    private String iban;    
	private Date dataCensimento;    
	private Date dataAttivazione;   
	private String stRiga;  
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
    private String opInserimento;
    private int prVersione;
   
   

	private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr; 
    private transient BeanFactory bf;
    private Long id;

    @Id
    @Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="iban_enti_pk_gen")	
	@SequenceGenerator(
		    name="iban_enti_pk_gen",
		    sequenceName="IBAN_ENTI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	        
	public void setId(Long id) {		
		this.id = id;	 
	}
	
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="IBAN")
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	@Column(name="DATA_CENSIMENTO")
	public Date getDataCensimento() {
		return dataCensimento;
	}
	public void setDataCensimento(Date dataCensimento) {
		this.dataCensimento = dataCensimento;
	}
	
	@Column(name="DATA_ATTIVAZIONE")
	public Date getDataAttivazione() {
		return dataAttivazione;
	}
	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}
	
	@Column(name="ST_RIGA")
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	
	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	
	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	
	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	
	@Column(name="VERSION")
	public int getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}
	
	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
