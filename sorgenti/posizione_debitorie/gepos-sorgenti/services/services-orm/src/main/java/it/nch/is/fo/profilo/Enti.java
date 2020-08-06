
package it.nch.is.fo.profilo;


import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;
import it.nch.is.fo.enti.TipologiaEnti;
import it.nch.is.fo.enti.TipologiaEntiCommon;
import it.nch.is.fo.tributi.ITributoEnte;
import it.nch.is.fo.tributi.TributoEnte;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

@Entity
//@Table(name="ENTI")
@Table(name="JLTENTI")
@NamedQueries( { 
	
	 @NamedQuery(name="enteByCodEnte", query="select ente from Enti ente, Intestatari inte where ente.codiceEnte =:codEnte and ente.stato = 'A'"),
	 
	 @NamedQuery(name="entiAttivi",query="select e from Enti e where e.stato='A'  order by e.intestatarioobj.ragionesociale")
	 
})

public class Enti extends PojoImpl implements EntiCommon, EntiPojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Properties***/
	private String idEnte;
    private String codiceEnte;    
	private String denominazione;    
    private IntestatariCommon intestatarioobj;    
	private String opAggiornamento;
    private String opInserimento;
    private int prVersione;
    private String provincia;
    private String stato;
    private String gln;
    private String ndpCodSegr;
    private Timestamp tsAggiornamento;
    private Timestamp tsInserimento;
    private Integer maxNumTributi;

    private String cdAziendaSanitaria;
    
    private String flNdp;
	private String flNdpModello3;
	private String flBancaTesoriera;
	private String flBlf;
	
	private String auxDigit;
	private String codiceStazionePA; 

	private String autorizzStampaDDP;
	
	private String infoHomeBO;
    
	/*** Persistent Reference***/
    private TipologiaEnti tipoEnteobj;
    
    private Set<TributoEnte> tributiEnte;

	private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr; 
    private transient BeanFactory bf;


	@Id
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
    
    @Column(name="MAX_NUM_TRIBUTI")
    public Integer getMaxNumTributi() {
		return maxNumTributi;
	}
	public void setMaxNumTributi(Integer maxNumTributi) {
		this.maxNumTributi = maxNumTributi;
	}

	@Column(name="CD_ENTE")
	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	@Column(name="DENOM")
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
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
	
	@Column(name="CD_AZIENDA_SANITARIA")
	public String getCdAziendaSanitaria() {
		return cdAziendaSanitaria;
	}
	public void setCdAziendaSanitaria(String cdAziendaSanitaria) {
		this.cdAziendaSanitaria = cdAziendaSanitaria;
	}

	@ManyToOne(targetEntity=TipologiaEnti.class)
	@JoinColumn(name="TP_ENTE")
	public TipologiaEntiCommon getTipoEnteobj() {
		return tipoEnteobj;
	}
	public void setTipoEnteobj(TipologiaEntiCommon tipoEnteobj) {
		this.tipoEnteobj = (TipologiaEnti) tipoEnteobj;
	}
	
	@OneToOne(targetEntity=Intestatari.class, optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="INTESTATARIO")
	public IntestatariCommon getIntestatarioobj() {
		return intestatarioobj;
	}
	public void setIntestatarioobj(IntestatariCommon intestatarioobj) {
		this.intestatarioobj = intestatarioobj;
	}
	
	@Override
	@Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("codiceEnte="+this.getCodiceEnte());
       System.out.println("denominazione="+this.getDenominazione());       
       System.out.println("opAggiornamento="+this.getOpAggiornamento());
       System.out.println("opInserimento="+this.getOpInserimento());
       System.out.println("prVersione="+this.getPrVersione());
       System.out.println("provincia="+this.getProvincia());
       System.out.println("stato="+this.getStato());       
       System.out.println("tsAggiornamento="+this.getTsAggiornamento());
       System.out.println("cdAziendaSanitaria="+this.getCdAziendaSanitaria());
       System.out.println("autorizzStampaDDP="+this.getAutorizzStampaDDP());
       System.out.println("tsInserimento="+this.getTsInserimento());       
    }


	@Override
	@Transient
	public TipologiaEntiCommon getTipoEnteobjIForm(){
    	return this.tipoEnteobj;
    }   
    public void setTipoEnteobjIForm(TipologiaEntiCommon tipoEnteobjIForm){
    	this.tipoEnteobj = (TipologiaEnti) tipoEnteobjIForm;
    }
    
	@Override
	@Transient
    public String getCodiceEnteIForm() {
 		return this.codiceEnte;
 	}    
    public void setCodiceEnteIForm(String codiceEnteIForm){
 		this.codiceEnte=codiceEnteIForm;
    } 
    
    @Override
    @Transient
	public String getStatoEnteIForm() {
		return this.stato;
	}
	@Override
	public void setStatoEnteIForm(String statoEnteIForm) {
		this.stato = statoEnteIForm;
		
	}

	@Override
	@Transient
	public String getIdEnteIForm() {
		return this.idEnte;
	}
	@Override
	public void setIdEnteIForm(String idEnteIForm) {
		this.idEnte = idEnteIForm;		
	}
	
	@Override
	@Transient
	public String getDenominazioneIForm() {
		return this.denominazione;
	}	
	@Override
	public void setDenominazioneIForm(String denominazioneIForm) {
		this.denominazione = denominazioneIForm;		
	}

	@Override
	@Transient
	public IntestatariCommon getIntestatarioIForm() {
		return this.intestatarioobj;
	}	
	@Override
	public void setIntestatarioIForm(IntestatariCommon intestatarioIForm) {
		this.intestatarioobj = intestatarioIForm;
	}
	
	@Override
	@Transient
	public String getProvinciaIForm() {
		return this.provincia;
	}
	@Override
	public void setProvinciaIForm(String provinciaIForm) {
		this.provincia = provinciaIForm;
		
	}

	@Override
	@Transient
    public String getCdAziendaSanitariaIForm() {
 		return this.cdAziendaSanitaria;
 	}
 	@Override
    public void setCdAziendaSanitariaIForm(String cdAziendaSanitariaIForm){
 		this.cdAziendaSanitaria = cdAziendaSanitariaIForm;
    } 
 	
 	@Override
	@Transient
	public String getAutorizzStampaDDPIForm() {		
		return autorizzStampaDDP;
	}
	@Override
	public void setAutorizzStampaDDPIForm(String myAutorizzStampaDDP) {
		this.autorizzStampaDDP=myAutorizzStampaDDP;
		
	}
 	

	@Override
	@Transient
	public Set getTributiIForm() {
		return this.tributiEnte;
	} 
	@Override
	public void setTributiIForm(Set tributiIForm) {		
		this.tributiEnte = (Set<TributoEnte>)tributiIForm;
	}
	 
	@OneToMany(targetEntity=TributoEnte.class,mappedBy="entiobj", fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("deTrb ASC")
	public Set<TributoEnte> getTributiEnte() {
		return tributiEnte;
	}
	public void setTributiEnte(Set<TributoEnte> tributiEnte) {
		this.tributiEnte = tributiEnte;
	}

	@Override
	@Transient
	public String getMaxNumTributiIForm() {
		return Traslator.integerToString(this.maxNumTributi);
	}
	@Override
	public void setMaxNumTributiIForm(String maxNumTributiIForm) {
		this.maxNumTributi = Traslator.stringToInteger(maxNumTributiIForm);		
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
         EntiForm _form =(EntiForm) bf.getBean("EntiForm");

          _form.setNativePojo(this);

         EntiCommon _pojo = this;

         _form.setIdEnteIForm(_pojo.getIdEnteIForm());
         if (_pojo.getTipoEnteobjIForm()!=null && !getFlagDoNotCopy()) // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
         	 _form.setTipoEnteobjIForm((TipologiaEntiCommon)_pojo.getTipoEnteobjIForm().copy());                        
         _form.setCodiceEnteIForm(_pojo.getCodiceEnteIForm());
         _form.setStatoEnteIForm(_pojo.getStatoEnteIForm());
         _form.setDenominazioneIForm(_pojo.getDenominazioneIForm());
         _form.setGlnIForm(_pojo.getGlnIForm());
         if (_pojo.getIntestatarioIForm()!=null){
			  CommonBusinessObject _innerPojo =  (CommonBusinessObject)_pojo.getIntestatarioIForm();
 			  PojoImpl source = (PojoImpl)_innerPojo;
 			  source.setFlagDoNotCopy(true); // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
         	 _form.setIntestatarioIForm((IntestatariCommon)source);
         }
         _form.setProvinciaIForm(_pojo.getProvinciaIForm());
         _form.setMaxNumTributiIForm(_pojo.getMaxNumTributiIForm());

         if (_pojo.getTributiIForm()!=null && !getFlagDoNotCopy()) {
        	 Set<ITributoEnte> tributiCopy = new HashSet<ITributoEnte>();
        	 for (Iterator<ITributoEnte> tributiIterator = _pojo.getTributiIForm().iterator(); tributiIterator.hasNext();) {
        		 ITributoEnte tributo = tributiIterator.next();
        		 ((PojoImpl)tributo).setFlagDoNotCopy(true);
        		 tributiCopy.add(tributo);
        	 }
        	 _form.setTributiIForm(tributiCopy);
         }
         _form.setCdAziendaSanitariaIForm(_pojo.getCdAziendaSanitariaIForm());
         
         _form.setFlBancaTesorieraIForm(_pojo.getFlBancaTesorieraIForm());
         _form.setFlBlfIForm(_pojo.getFlBlfIForm());
         _form.setFlNdpIForm(_pojo.getFlNdpIForm());
         _form.setFlNdpModello3IForm(_pojo.getFlNdpModello3IForm());
         _form.setAuxDigitIForm(_pojo.getAuxDigitIForm());
         _form.setCodiceStazionePAIForm(_pojo.getCodiceStazionePAIForm());
         _form.setCodSegregazIForm(_pojo.getCodSegregazIForm());
         _form.setAutorizzStampaDDPIForm(_pojo.getAutorizzStampaDDPIForm());
         
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
		  
         Enti _pojoCurrent = this;
         Enti _pojo = new Enti();

         _pojo.setIdEnte(_pojoCurrent.getIdEnte());
         _pojo.setTipoEnteobj(_pojoCurrent.getTipoEnteobj());
         _pojo.setCodiceEnte(_pojoCurrent.getCodiceEnte());
         _pojo.setStato(_pojoCurrent.getStato());
         _pojo.setDenominazione(_pojoCurrent.getDenominazione());
         _pojo.setIntestatarioobj(_pojoCurrent.getIntestatarioobj());
         _pojo.setProvincia(_pojoCurrent.getProvincia());
         _pojo.setCdAziendaSanitaria(_pojoCurrent.getCdAziendaSanitaria());
         _pojo.setGln(_pojoCurrent.getGln());
         _pojo.setAutorizzStampaDDP(_pojoCurrent.getAutorizzStampaDDP());
         _pojo.setNdpCodSegr(_pojoCurrent.getNdpCodSegr());
         _pojo.setAuxDigit(_pojoCurrent.getAuxDigit());
         _pojo.setCodiceStazionePA(_pojoCurrent.getCodiceStazionePA());
         return _pojo;
	  }
	  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEnte == null) ? 0 : idEnte.hashCode());
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
		Enti other = (Enti) obj;
		if (idEnte == null) {
			if (other.idEnte != null)
				return false;
		} else if (!idEnte.equals(other.idEnte))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Enti [idEnte=" + idEnte + ", codiceEnte=" + codiceEnte
				+ ", denominazione=" + denominazione + ", intestatario="
				+ (intestatarioobj!=null?intestatarioobj.getCorporateIForm():"null") + ", opAggiornamento=" + opAggiornamento
				+ ", opInserimento=" + opInserimento + ", prVersione="
				+ prVersione + ", provincia=" + provincia + ", stato=" + stato
				 + ", cdAziendaSanitaria=" + cdAziendaSanitaria + ", GLN=" + gln
				 + ", autorizzStampaDDP=" + autorizzStampaDDP
				+ ", tsAggiornamento=" + tsAggiornamento + ", tsInserimento="
				+ tsInserimento + ", maxNumTributi=" + maxNumTributi
				+ ", tipoEnteobj=" + tipoEnteobj + "]";
	}
	
	@Column(name="GLN")
	public String getGln() {
		return gln;
	}
	
	public void setGln(String gln) {
		this.gln = gln;
	}
	
	@Override
	@Transient
	public String getGlnIForm() {
		return gln;
	}
	@Override
	public void setGlnIForm(String glnIForm) {
		this.gln = glnIForm;
		
	}
	
	@Override
	@Transient
	public String getFlNdpIForm() {
		return flNdp;
	}
	@Override
	public void setFlNdpIForm(String flNdp) {
		this.flNdp = flNdp;
	}

	@Override
	@Transient
	public String getFlNdpModello3IForm() {
		return flNdpModello3;
	}
	@Override
	public void setFlNdpModello3IForm(String flNdpModello3) {
		this.flNdpModello3 = flNdpModello3;
	}

	@Override
	@Transient
	public String getFlBancaTesorieraIForm() {
		return flBancaTesoriera;
	}
	@Override
	public void setFlBancaTesorieraIForm(String flBancaTesoriera) {
		this.flBancaTesoriera = flBancaTesoriera;
	}

	@Override
	@Transient
	public String getFlBlfIForm() {
		return flBlf;
	}
	@Override
	public void setFlBlfIForm(String flBlf) {
		this.flBlf = flBlf;
	}
	
	@Column(name="FL_NDP")
	public String getFlNdp() {
		return flNdp;
	}
	public void setFlNdp(String flNdp) {
		this.flNdp = flNdp;
	}
	
	@Column(name="FL_NDP_MODELLO3")
	public String getFlNdpModello3() {
		return flNdpModello3;
	}
	public void setFlNdpModello3(String flNdpModello3) {
		this.flNdpModello3 = flNdpModello3;
	}
	@Column(name="FL_BANCA_TESORIERA")
	public String getFlBancaTesoriera() {
		return flBancaTesoriera;
	}
	public void setFlBancaTesoriera(String flBancaTesoriera) {
		this.flBancaTesoriera = flBancaTesoriera;
	}
	@Column(name="FL_BFL")
	public String getFlBlf() {
		return flBlf;
	}
	public void setFlBlf(String flBlf) {
		this.flBlf = flBlf;
	}
	@Column(name="NDP_AUX_DIGIT")
	public String getAuxDigit() {
		return auxDigit;
	}
	public void setAuxDigit(String auxDigit) {
		this.auxDigit = auxDigit;
	}
	@Column(name="NDP_COD_STAZ_PA")
	public String getCodiceStazionePA() {
		return codiceStazionePA;
	}
	public void setCodiceStazionePA(String codiceStazionePA) {
		this.codiceStazionePA = codiceStazionePA;
	}
	@Override
	@Transient
	public String getAuxDigitIForm() {
		return auxDigit;
	}
	public void setAuxDigitIForm(String auxDigit) {
		this.auxDigit = auxDigit;
	}
	@Override
	@Transient
	public String getCodiceStazionePAIForm() {
		return codiceStazionePA;
	}
	public void setCodiceStazionePAIForm(String codiceStazionePA) {
		this.codiceStazionePA = codiceStazionePA;
	}
	
    @Column(name = "AUTORIZZ_STAMPA_DDP")
    public String getAutorizzStampaDDP() {
        return autorizzStampaDDP;
    }

    public void setAutorizzStampaDDP(String autorizzStampaDDP) {
        this.autorizzStampaDDP = autorizzStampaDDP;
    }
	
	@Column(name="INFO_HOME_BO")
    public String getInfoHomeBO() {
		return infoHomeBO;
	}
	public void setInfoHomeBO(String infoHomeBO) {
		this.infoHomeBO = infoHomeBO;
	}
	
	@Override
	@Transient
	public String getCodSegregazIForm() {		
		return this.ndpCodSegr;
	}
	@Override
	public void setCodSegregazIForm(String codSegregaz) {
		this.ndpCodSegr = codSegregaz;		
	}
	
	@Column(name="NDP_COD_SEGR")
	public String getNdpCodSegr() {
		return ndpCodSegr;
	}

	public void setNdpCodSegr(String ndpCodSegr) {
		this.ndpCodSegr = ndpCodSegr;
	}
}
