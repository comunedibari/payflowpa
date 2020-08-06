
package it.nch.is.fo.enti;


import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
@Entity
@Table(name="JLTENTP")
public class TipologiaEnti extends PojoImpl implements TipologiaEntiCommon, TipologiaEntiPojo{
	
	/*** Persistent Properties ***/
	private String tipoEnte;    
	private String descrizione;    
	private String stato;    
	private int prVersione;
	private String opAggiornamento;
    private String opInserimento;
    private Timestamp tsAggiornamento;
    private Timestamp tsInserimento;
            
    /*** Transient Properties ***/
    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

    
    @Id
    @Column(name="TP_ENTE")
    public String getTipoEnte() {
		return tipoEnte;
	}
	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}

	@Column(name="DE_ENTE")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name="STATO")
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
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
	
	
	@Override
	@Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("tipoEnte="+this.getTipoEnte());
       System.out.println("descrizione="+this.getDescrizione());
       System.out.println("stato="+this.getStato());
       System.out.println("opAggiornamento="+this.getOpAggiornamento());
       System.out.println("opInserimento="+this.getOpInserimento());
       System.out.println("prVersione="+this.getPrVersione());
       System.out.println("tsAggiornamento="+this.getTsAggiornamento());
       System.out.println("tsInserimento="+this.getTsInserimento());             
    }


	@Override
	@Transient
    public String getTipoIForm(){
    	return this.tipoEnte;
    }
	@Override
    public void setTipoIForm(String tipoEnteIForm){
    	this.tipoEnte = tipoEnteIForm;
    }
    
	@Override
	@Transient
    public String getDescrizioneIForm() {
 		return this.descrizione;
 	} 
	@Override
    public void setDescrizioneIForm(String descrizioneIForm){
 		this.descrizione=descrizioneIForm;
    } 
    
    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/
	@Override
	@Transient
    public CommonBusinessObject copy(){

         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf=bfr.getFactory();
         TipologiaEntiForm _form =(TipologiaEntiForm) bf.getBean("TipologiaEntiForm");

          _form.setNativePojo(this);

          TipologiaEntiCommon _pojo = this;

         _form.setTipoIForm(_pojo.getTipoIForm());
         _form.setDescrizioneIForm(_pojo.getDescrizioneIForm());
         
         return _form;
	  }
    
	@Override
    @Transient
	  public DTO incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }

    /**
     *
     * Metodo Clone richiesto!!!
     *
     **/
    @Override
    public Object clone() {

         TipologiaEnti _pojoCurrent = this;
         TipologiaEnti _pojo = new TipologiaEnti();

         _pojo.setTipoEnte(_pojoCurrent.getTipoEnte());
         _pojo.setDescrizione(_pojoCurrent.getDescrizione());
         return _pojo;
	  }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tipoEnte == null) ? 0 : tipoEnte.hashCode());
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
		TipologiaEnti other = (TipologiaEnti) obj;
		if (tipoEnte == null) {
			if (other.tipoEnte != null)
				return false;
		} else if (!tipoEnte.equals(other.tipoEnte))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TipologiaEnti [tipoEnte=" + tipoEnte + ", descrizione="
				+ descrizione + ", stato=" + stato + ", prVersione="
				+ prVersione + ", opAggiornamento=" + opAggiornamento
				+ ", opInserimento=" + opInserimento + ", tsAggiornamento="
				+ tsAggiornamento + ", tsInserimento=" + tsInserimento + "]";
	}


    
}
