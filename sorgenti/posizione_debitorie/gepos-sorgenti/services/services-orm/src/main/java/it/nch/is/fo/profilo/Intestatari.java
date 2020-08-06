/**
*
* Classe generata (MALE!!)
*
*/

package it.nch.is.fo.profilo;


import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;
import it.tasgroup.iris.domain.helper.EntityHelper;
import it.tasgroup.services.util.enumeration.EnumCategoriaIntestatario;

import java.math.BigDecimal;
import java.util.Collection;
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

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;


@Entity
@Table(name="INTESTATARI")
@NamedQueries(
{ 
	@NamedQuery(
			name="getIntestatarioByLapl",
			query="select intestataris from Intestatari intestataris where intestataris.lapl=:LAPL"),
			
	@NamedQuery(name="checkIdFiscaleEnte",
	            query="select intestataris from Intestatari intestataris where intestataris.lapl=:LAPL and intestataris.corporate in (select e.intestatarioobj.corporate from Enti e where e.stato='A') "),
	
	@NamedQuery(
			name="getIntestatarioBySessionId",
			query="select i from Intestatari as i, Sessione as s " +
				  "where s.sessionid=:SESSION_ID and s.azienda = i.corporate"),
	@NamedQuery(
			name="getIntestatarioByCorporateAndOperatore",
			query="select i from Intestatari i join i.intestatariOperatori io join io.operatore o where i.corporate = :corporate and o.operatore = :operatore ")
})
public class Intestatari extends PojoImpl implements IntestatariCommon, IntestatariPojo {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Properties***/
    private String corporate;
    private String abi;
    private String abiaccentratore;
    private String cab;
    private String chiavessb;
    private String codicepostel;
    private String codicesoftware;
    private String denominazione;
    private String gruppo;
    private String lapl;
    private String ragionesociale;
    private String rapl;
    private String rcz;
    private String sia;
    private String siaCbi;
    private Integer stato;
    private String tipointestatario;
    private String tiposicurezza;
    private String uffpostale;
    private String codiceCuc;
    private String nonresidente;
    private String categoria;
    private EnumCategoriaIntestatario categoriaEnum;
	private String sottoCategoria;
	private BigDecimal importoMaxFlusso;
	private String flagComunicazioni;
	private String flagEnrollmentAvvisatura;
    
	/*** Persistent References ***/
    private Indirizzipostali indirizzipostaliobj;
    
    private Set<EntiCommon> enti;
    
    /*** Persistent Collections ***/
    private Set<Funzioniintestatari> funzioniIntestatari;	
	private Set<Operatori> operatori;
	private Set<Intestatarioperatori> intestatarioperatori;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;


    @Id
    @Column(name="INTESTATARIO")
    public String getCorporate() {
 		return this.corporate;
 	}

    public void setCorporate(String corporate){
 		this.corporate=corporate;
    }

    public String getAbi() {
 		return this.abi;
 	}
    public void setAbi(String abi){
 		this.abi=abi;
    }

    public String getAbiaccentratore() {
 		return this.abiaccentratore;
 	}
    public void setAbiaccentratore(String abiaccentratore){
 		this.abiaccentratore=abiaccentratore;
    }

    public String getCab() {
 		return this.cab;
 	}
    public void setCab(String cab){
 		this.cab=cab;
    }

    public String getChiavessb() {
 		return this.chiavessb;
 	}
    public void setChiavessb(String chiavessb){
 		this.chiavessb=chiavessb;
    }
    
    public String getCodicepostel() {
 		return this.codicepostel;
 	}
    public void setCodicepostel(String codicepostel){
 		this.codicepostel=codicepostel;
    }
    
    public String getCodicesoftware() {
 		return this.codicesoftware;
 	}
    public void setCodicesoftware(String codicesoftware){
 		this.codicesoftware=codicesoftware;
    }

    public String getDenominazione() {
 		return this.denominazione;
 	}
    public void setDenominazione(String denominazione){
 		this.denominazione=denominazione;
    }

    public String getGruppo() {
 		return this.gruppo;
 	}
    public void setGruppo(String gruppo){
 		this.gruppo=gruppo;
    }

    public String getLapl() {
 		return this.lapl;
 	}
    public void setLapl(String lapl){
 		this.lapl=lapl;
    }

    public String getRagionesociale() {
 		return this.ragionesociale;
 	}
    public void setRagionesociale(String ragionesociale){
 		this.ragionesociale=ragionesociale;
    }

    public String getRapl() {
 		return this.rapl;
 	}
    public void setRapl(String rapl){
 		this.rapl=rapl;
    }
    
    public String getRcz() {
 		return this.rcz;
 	}
    public void setRcz(String rcz){
 		this.rcz=rcz;
    }
    
    public String getSia() {
 		return this.sia;
 	}
    public void setSia(String sia){
 		this.sia=sia;
    }
    
    public Integer getStato() {
 		return this.stato;
 	}
    public void setStato(Integer stato){
 		this.stato=stato;
    }

    public String getTipointestatario() {
 		return this.tipointestatario;
 	}
    public void setTipointestatario(String tipointestatario){
 		this.tipointestatario=tipointestatario;
    }

    public String getTiposicurezza() {
 		return this.tiposicurezza;
 	}
    public void setTiposicurezza(String tiposicurezza){
 		this.tiposicurezza=tiposicurezza;
    }

    public String getUffpostale() {
 		return this.uffpostale;
 	}
    public void setUffpostale(String uffpostale){
 		this.uffpostale=uffpostale;
    }

    public String getCodiceCuc() {
 		return this.codiceCuc;
 	}
    public void setCodiceCuc(String codiceCuc){
 		this.codiceCuc=codiceCuc;
    }

    public String getNonresidente() {
 		return this.nonresidente;
 	}
    public void setNonresidente(String nonresidente){
 		this.nonresidente=nonresidente;
    }

    public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
		this.categoriaEnum = EnumCategoriaIntestatario.valueOf(categoria);
	}

	public String getSottoCategoria() {
		return sottoCategoria;
	}
	public void setSottoCategoria(String sottoCategoria) {
		this.sottoCategoria = sottoCategoria;
	}
	
	public BigDecimal getImportoMaxFlusso() {
		return importoMaxFlusso;
	}
	public void setImportoMaxFlusso(BigDecimal importoMaxFlusso) {
		this.importoMaxFlusso = importoMaxFlusso;
	}
    
    @OneToOne(targetEntity=Indirizzipostali.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="ID_INDIRIZZIPOSTALI")
    public IndirizzipostaliCommon getIndirizzipostaliobj() {
 		return (IndirizzipostaliCommon)this.indirizzipostaliobj;
 	}
    public void setIndirizzipostaliobj(IndirizzipostaliCommon indirizzipostaliobj){
 		this.indirizzipostaliobj=(Indirizzipostali)indirizzipostaliobj;
    }

    @OneToMany(mappedBy="intestatariobj", targetEntity=Funzioniintestatari.class,cascade=CascadeType.ALL)
    public Set<Funzioniintestatari> getFunzioniIntestatari() {
 		return this.funzioniIntestatari;
 	}
    public void setFunzioniIntestatari(Set funzioniIntestatari){
 		this.funzioniIntestatari=funzioniIntestatari;
    }
	
	@OneToMany(targetEntity=Enti.class, fetch=FetchType.LAZY, mappedBy="intestatarioobj",cascade=CascadeType.ALL)
	public Set<EntiCommon> getEnti(){
		
		return this.enti;
	}
	
	public void setEnti(Set<EntiCommon> enti) {
		this.enti = enti;
	}
	
	@Transient
	public EntiCommon getEntiobj() {
		
		EntityHelper<EntiCommon> entityHelper = new EntityHelper<EntiCommon>();
		
		return entityHelper.getOneToOneObj(enti);
		
 	}
	
	@Transient
    public void setEntiobj(EntiCommon entiobj){
		
		EntityHelper<EntiCommon> entityHelper = new EntityHelper<EntiCommon>();
		
		this.enti = entityHelper.setOneToOneObj(entiobj, enti);
    }

//	@ManyToMany(fetch=FetchType.EAGER,targetEntity=Operatori.class)
//	@JoinTable(name = "INTEST_OPER",
//			joinColumns = {@JoinColumn(name="INTESTATARIO")},
//			inverseJoinColumns={@JoinColumn(name="OPERATORE")})
//    public Set<Operatori> getOperatori() {
//		return this.operatori;
//	}
//	public void setOperatori(Set<Operatori> operatoriIForm) {
//		this.operatori = operatoriIForm;
//	}

    @Transient
    public Set<Operatori> getOperatori() {
    	
    	if (this.intestatarioperatori == null)
    		return null;
    	
    	Set<Operatori> opers = new LinkedHashSet<Operatori>();
    	for (Intestatarioperatori intoper : this.intestatarioperatori) {
			opers.add(intoper.getOperatore());
		}
    	return opers;
    }
    public void setOperatori(Set<Operatori> operatori) {
    	this.operatori = operatori;
    }

	@OneToMany(targetEntity=Intestatarioperatori.class,mappedBy="intestatario",
			fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
    public Set<Intestatarioperatori> getIntestatariOperatori() {
		return this.intestatarioperatori;
	}
	public void setIntestatariOperatori(Set<Intestatarioperatori> intestatariOperatori) {
		this.intestatarioperatori = intestatariOperatori;
	}
	

	
    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("corporate="+this.getCorporate());
       System.out.println("abi="+this.getAbi());
       System.out.println("abiaccentratore="+this.getAbiaccentratore());
       System.out.println("cab="+this.getCab());
       System.out.println("chiavessb="+this.getChiavessb());
       System.out.println("codicepostel="+this.getCodicepostel());
       System.out.println("codicesoftware="+this.getCodicesoftware());
       System.out.println("denominazione="+this.getDenominazione());
       System.out.println("gruppo="+this.getGruppo());
       System.out.println("lapl="+this.getLapl());
       System.out.println("ragionesociale="+this.getRagionesociale());
       System.out.println("rapl="+this.getRapl());
       System.out.println("rcz="+this.getRcz());
       System.out.println("sia="+this.getSia());
       System.out.println("siaCbi="+this.getSiaCbi());
       System.out.println("stato="+this.getStato());
       System.out.println("tipointestatario="+this.getTipointestatario());
       System.out.println("tiposicurezza="+this.getTiposicurezza());
       System.out.println("uffpostale="+this.getUffpostale());
       System.out.println("codiceCuc="+this.getCodiceCuc());
       System.out.println("nonresidente="+this.getNonresidente());
       System.out.println("indirizzipostaliobj="+this.getIndirizzipostaliobj());
       System.out.println("funzioniIntestatari="+this.getFunzioniIntestatari());
       System.out.println("categoria="+this.getCategoria());
       System.out.println("flagComunicazione="+this.getFlagComunicazioni());
       System.out.println("flagEnrollmentAvvisatura="+this.getFlagEnrollmentAvvisatura());
       System.out.println("sottoCategoria="+this.getSottoCategoria());
       System.out.println("importoMaxFlusso="+this.getImportoMaxFlusso());
    }

	@Transient
	public EnumCategoriaIntestatario getCategoriaEnum() {
		return categoriaEnum;
	}
	public void setCategoriaEnum(EnumCategoriaIntestatario categoriaEnum) {
		this.categoriaEnum = categoriaEnum;
	}
    
    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    @Transient
    public String getTiposicurezzaIForm() {
 		return this.tiposicurezza;
 	}
    public void setTiposicurezzaIForm(String tiposicurezzaIForm){
 		this.tiposicurezza=tiposicurezzaIForm;
    }

    @Transient
    public String getTipointestatarioIForm() {
 		return this.tipointestatario;
 	}    
    public void setTipointestatarioIForm(String tipointestatarioIForm){
 		this.tipointestatario=tipointestatarioIForm;
    }
    
    @Transient
    public String getStatoIForm() {
 		return Traslator.integerToString(this.stato);
 	}
    public void setStatoIForm(String statoIForm){
 		this.stato=Traslator.stringToInteger(statoIForm);
    }

    @Transient
    public String getCodicesoftwareIForm() {
 		return this.codicesoftware;
 	}
    public void setCodicesoftwareIForm(String codicesoftwareIForm){
 		this.codicesoftware=codicesoftwareIForm;
    }

    @Transient
    public String getCodiceCucIForm() {
 		return this.codiceCuc;
 	}
    public void setCodiceCucIForm(String codiceCucIForm){
 		this.codiceCuc=codiceCucIForm;
    }

    @Transient
    public String getSiaIForm() {
 		return this.sia;
 	}
    public void setSiaIForm(String siaIForm){
 		this.sia=siaIForm;
    }

    @Transient
    public String getRczIForm() {
 		return this.rcz;
 	}
    public void setRczIForm(String rczIForm){
 		this.rcz=rczIForm;
    }

    @Transient
    public String getAbiaccentratoreIForm() {
 		return this.abiaccentratore;
 	}
    public void setAbiaccentratoreIForm(String abiaccentratoreIForm){
 		this.abiaccentratore=abiaccentratoreIForm;
    }

    @Transient
    public IndirizzipostaliCommon getIndirizzipostaliobjIForm() {
 		return this.indirizzipostaliobj;
 	}
    public void setIndirizzipostaliobjIForm(IndirizzipostaliCommon indirizzipostaliobjIForm){
 		this.indirizzipostaliobj=(Indirizzipostali)indirizzipostaliobjIForm;
    }

    @Transient
    public String getAbiIForm() {
 		return this.abi;
 	}
    public void setAbiIForm(String abiIForm){
 		this.abi=abiIForm;
    }

    @Transient
    public String getLaplIForm() {
 		return this.lapl;
 	}
    public void setLaplIForm(String laplIForm){
 		this.lapl=laplIForm;
    }

    @Transient
    public String getChiavessbIForm() {
 		return this.chiavessb;
 	}
    public void setChiavessbIForm(String chiavessbIForm){
 		this.chiavessb=chiavessbIForm;
    }

    @Transient
    public String getRagionesocialeIForm() {
 		return this.ragionesociale;
 	}
    public void setRagionesocialeIForm(String ragionesocialeIForm){
 		this.ragionesociale=ragionesocialeIForm;
    }

    @Transient
    public String getDenominazioneIForm() {
 		return this.denominazione;
 	}
    public void setDenominazioneIForm(String denominazioneIForm){
 		this.denominazione=denominazioneIForm;
    }

    @Transient
    public String getCodicepostelIForm() {
 		return this.codicepostel;
 	}
    public void setCodicepostelIForm(String codicepostelIForm){
 		this.codicepostel=codicepostelIForm;
    }

    @Transient
    public String getCabIForm() {
 		return this.cab;
 	}
    public void setCabIForm(String cabIForm){
 		this.cab=cabIForm;
    }

    @Transient
    public String getRaplIForm() {
 		return this.rapl;
 	}
    public void setRaplIForm(String raplIForm){
 		this.rapl=raplIForm;
    }

    @Transient
    public String getCorporateIForm() {
 		return this.corporate;
 	}
    public void setCorporateIForm(String corporateIForm){
 		this.corporate=corporateIForm;
    }

    @Transient
    public String getGruppoIForm() {
 		return this.gruppo;
 	}
    public void setGruppoIForm(String gruppoIForm){
 		this.gruppo=gruppoIForm;
    }

    @Transient
    public String getUffpostaleIForm() {
 		return this.uffpostale;
 	}
    public void setUffpostaleIForm(String uffpostaleIForm){
 		this.uffpostale=uffpostaleIForm;
    }

    @Transient
    public String getNonresidenteIForm() {
 		return this.nonresidente;
 	}
    public void setNonresidenteIForm(String nonresidenteIForm){
 		this.nonresidente=nonresidenteIForm;
    }

    @Transient
    public EntiCommon getEntiobjIForm() {
 		return this.getEntiobj();
 	}
    public void setEntiobjIForm(EntiCommon entiobjIForm){
 		this.setEntiobj((Enti)entiobjIForm);
    }

    @Transient
    public String getCategoriaIForm() {
		return categoria;
	}
	public void setCategoriaIForm(String categoriaIForm) {
		this.categoria = categoriaIForm;
	}

	@Transient
	public String getFlagComunicazioniIForm() {
		return flagComunicazioni;
	}
	public void setFlagComunicazioniIForm(String flagComunicazioni) {
		this.flagComunicazioni = flagComunicazioni;
	}
	
	@Transient
	public String getFlagEnrollmentAvvisaturaIForm() {
		return flagEnrollmentAvvisatura;
	}
	public void setFlagEnrollmentAvvisaturaIForm(String flagEnrollmentAvvisatura) {
		this.flagEnrollmentAvvisatura = flagEnrollmentAvvisatura;
	}

	@Transient
	public String getSottoCategoriaIForm() {
		return sottoCategoria;
	}
	public void setSottoCategoriaIForm(String sottoCategoriaIForm) {
		this.sottoCategoria = sottoCategoriaIForm;
	}
	
	 @Column(name="FLAG_COMUNICAZIONI")
	public String getFlagComunicazioni() {
		return flagComunicazioni;
	}

	public void setFlagComunicazioni(String flagComunicazioni) {
		this.flagComunicazioni = flagComunicazioni;
	}

	@Column(name="FLAG_ENROLLMENT_AVVISATURA")
	public String getFlagEnrollmentAvvisatura() {
		return flagEnrollmentAvvisatura;
	}

	public void setFlagEnrollmentAvvisatura(String flagEnrollmentAvvisatura) {
		this.flagEnrollmentAvvisatura = flagEnrollmentAvvisatura;
	}


    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/
	@Transient
	public CommonBusinessObject copy(){

         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf = bfr.getFactory();
         IntestatariForm _form =(IntestatariForm) bf.getBean("IntestatariForm");

          _form.setNativePojo(this);

         IntestatariCommon _pojo = this;
         
         ((PojoImpl)_pojo).addToCopyList(_pojo.getClass());

         _form.setCorporateIForm(_pojo.getCorporateIForm());
         _form.setAbiIForm(_pojo.getAbiIForm());
         _form.setAbiaccentratoreIForm(_pojo.getAbiaccentratoreIForm());
         _form.setCabIForm(_pojo.getCabIForm());
         _form.setChiavessbIForm(_pojo.getChiavessbIForm());
         _form.setCodicepostelIForm(_pojo.getCodicepostelIForm());
         _form.setCodicesoftwareIForm(_pojo.getCodicesoftwareIForm());
         _form.setDenominazioneIForm(_pojo.getDenominazioneIForm());
         _form.setGruppoIForm(_pojo.getGruppoIForm());
         _form.setLaplIForm(_pojo.getLaplIForm());
         _form.setRagionesocialeIForm(_pojo.getRagionesocialeIForm());
         _form.setRaplIForm(_pojo.getRaplIForm());
         _form.setRczIForm(_pojo.getRczIForm());
         _form.setSiaIForm(_pojo.getSiaIForm());
         _form.setSiaCbiIForm(_pojo.getSiaCbiIForm());
         _form.setStatoIForm(_pojo.getStatoIForm());
         _form.setTipointestatarioIForm(_pojo.getTipointestatarioIForm());
         _form.setTiposicurezzaIForm(_pojo.getTiposicurezzaIForm());
         _form.setUffpostaleIForm(_pojo.getUffpostaleIForm());
         _form.setCodiceCucIForm(_pojo.getCodiceCucIForm());
         _form.setNonresidenteIForm(_pojo.getNonresidenteIForm());
         _form.setCategoriaIForm(_pojo.getCategoriaIForm());
         _form.setFlagComunicazioniIForm(_pojo.getFlagComunicazioniIForm());
         _form.setFlagEnrollmentAvvisaturaIForm(_pojo.getFlagEnrollmentAvvisaturaIForm());
         
         _form.setSottoCategoriaIForm(_pojo.getSottoCategoriaIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_pojo.getIndirizzipostaliobjIForm()!=null && !getFlagDoNotCopy()){ 
        	 _form.setIndirizzipostaliobjIForm((IndirizzipostaliCommon)_pojo.getIndirizzipostaliobjIForm().copy());
  		  }
  		  if (_pojo.getEntiobjIForm()!=null && !getFlagDoNotCopy()){
         	 _form.setEntiobjIForm((EntiCommon)_pojo.getEntiobjIForm().copy());
  		  }
  		  
//		  Set destOperatoriIForm = new LinkedHashSet();
//		  if (_pojo.getOperatoriIForm()!=null){
//			  Iterator srcOperatoriIForm = _pojo.getOperatoriIForm().iterator();
//			  while (srcOperatoriIForm.hasNext()){
//				  CommonBusinessObject _innerPojo =  (CommonBusinessObject)srcOperatoriIForm.next();
//				  if (!isInCopyList(_innerPojo.getClass())){
//					  ((PojoImpl)_innerPojo).setCopyList(getCopyList());
//					  destOperatoriIForm.add(_innerPojo.copy());
//				  }
//			  }
//		      _form.setOperatoriIForm(destOperatoriIForm);
//		  }
		  
		  Set destIntestatariOperatoriIForm = new LinkedHashSet();
		  if (_pojo.getIntestatariOperatoriIForm()!=null){
			  Iterator inteOperIterator = _pojo.getIntestatariOperatoriIForm().iterator();
			  while(inteOperIterator.hasNext()){
				  IntestatarioperatoriCommon _innerPojo = (IntestatarioperatoriCommon) inteOperIterator.next();
				  if (!isInCopyList(_innerPojo.getClass())){
					  ((PojoImpl) _innerPojo).setCopyList(getCopyList());
					  destIntestatariOperatoriIForm.add(_innerPojo.copy());
				  }
			  }
			  _form.setIntestatariOperatoriIForm(destIntestatariOperatoriIForm);
		  }
		  
          _form.setImportoMaxFlussoIForm(_pojo.getImportoMaxFlussoIForm());

         return _form;
	  }

	  public DTO<?,Intestatari,?> incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }

    /**
     *
     * Metodo Clone richiesto
     *
     **/
	  @Transient
      public Object clone() {

         Intestatari _pojoCurrent = this;
         Intestatari _pojo = new Intestatari();

         _pojo.setCorporate(_pojoCurrent.getCorporate());
         _pojo.setAbi(_pojoCurrent.getAbi());
         _pojo.setAbiaccentratore(_pojoCurrent.getAbiaccentratore());
         _pojo.setCab(_pojoCurrent.getCab());
         _pojo.setChiavessb(_pojoCurrent.getChiavessb());
         _pojo.setCodicepostel(_pojoCurrent.getCodicepostel());
         _pojo.setCodicesoftware(_pojoCurrent.getCodicesoftware());
         _pojo.setDenominazione(_pojoCurrent.getDenominazione());
         _pojo.setGruppo(_pojoCurrent.getGruppo());
         _pojo.setLapl(_pojoCurrent.getLapl());
         _pojo.setRagionesociale(_pojoCurrent.getRagionesociale());
         _pojo.setRapl(_pojoCurrent.getRapl());
         _pojo.setRcz(_pojoCurrent.getRcz());
         _pojo.setSia(_pojoCurrent.getSia());
         _pojo.setSiaCbi(_pojoCurrent.getSiaCbi());
         _pojo.setStato(_pojoCurrent.getStato());
         _pojo.setTipointestatario(_pojoCurrent.getTipointestatario());
         _pojo.setTiposicurezza(_pojoCurrent.getTiposicurezza());
         _pojo.setUffpostale(_pojoCurrent.getUffpostale());
         _pojo.setCodiceCuc(_pojoCurrent.getCodiceCuc());
         _pojo.setNonresidente(_pojoCurrent.getNonresidente());
         _pojo.setIndirizzipostaliobj(_pojoCurrent.getIndirizzipostaliobj());
         _pojo.setCategoria(_pojoCurrent.getCategoria());
         _pojo.setSottoCategoria(_pojoCurrent.getSottoCategoria());
         _pojo.setImportoMaxFlusso(_pojoCurrent.getImportoMaxFlusso());
         _pojo.setEntiobj(_pojoCurrent.getEntiobj());
         /**
          *
          *Trovata Collection [funzioniIntestatari] NON applico clone annidato!
          *
          */

         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }

	@Transient
	public String getImportoMaxFlussoIForm() {
		return Traslator.bigDecimalToString(this.importoMaxFlusso);
	}

	public void setImportoMaxFlussoIForm(String importoMaxFlussoIForm) {
		this.importoMaxFlusso = Traslator.stringToBigDecimal(importoMaxFlussoIForm);
	}

	@Override
	@Transient
	public Collection getOperatoriIForm() {
		return getOperatori();
	}

	@Override
	public void setOperatoriIForm(Set operatoriIForm) {
		this.operatori = operatoriIForm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((corporate == null) ? 0 : corporate.hashCode());
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
		Intestatari other = (Intestatari) obj;
		if (corporate == null) {
			if (other.corporate != null)
				return false;
		} else if (!corporate.equals(other.corporate))
			return false;
		return true;
	}

//	@Override
//	public String toString() {
//		return "Intestatari [corporate=" + corporate + ", abi=" + abi
//				+ ", abiaccentratore=" + abiaccentratore + ", cab=" + cab
//				+ ", chiavessb=" + chiavessb + ", codicepostel=" + codicepostel
//				+ ", codicesoftware=" + codicesoftware + ", denominazione="
//				+ denominazione + ", gruppo=" + gruppo + ", lapl=" + lapl
//				+ ", ragionesociale=" + ragionesociale + ", rapl=" + rapl
//				+ ", rcz=" + rcz + ", sia=" + sia + ", stato=" + stato
//				+ ", tipointestatario=" + tipointestatario + ", tiposicurezza="
//				+ tiposicurezza + ", uffpostale=" + uffpostale + ", codiceCuc="
//				+ codiceCuc + ", nonresidente=" + nonresidente + ", categoria="
//				+ categoria + ", categoriaEnum=" + categoriaEnum
//				+ ", sottoCategoria=" + sottoCategoria + ", importoMaxFlusso="
//				+ importoMaxFlusso + ", indirizzipostaliobj="
//				+ indirizzipostaliobj + ", entiobj=" + entiobj
//				+ ", funzioniIntestatari=" + funzioniIntestatari
//				+ ", operatori=" + operatori + "]";
//	}
	@Override
	public String toString() {
		return "Intestatari [corporate=" + corporate + ", abi=" + abi
				+ ", abiaccentratore=" + abiaccentratore + ", cab=" + cab
				+ ", chiavessb=" + chiavessb + ", codicepostel=" + codicepostel
				+ ", codicesoftware=" + codicesoftware + ", denominazione="
				+ denominazione + ", gruppo=" + gruppo + ", lapl=" + lapl
				+ ", ragionesociale=" + ragionesociale + ", rapl=" + rapl
				+ ", rcz=" + rcz + ", sia=" + sia + ", stato=" + stato
				+ ", tipointestatario=" + tipointestatario + ", tiposicurezza="
				+ tiposicurezza + ", uffpostale=" + uffpostale + ", codiceCuc="
				+ codiceCuc + ", nonresidente=" + nonresidente + ", categoria="
				+ categoria + ", categoriaEnum=" + categoriaEnum
				+ ", sottoCategoria=" + sottoCategoria + ", importoMaxFlusso="
				+ importoMaxFlusso + "]";
	}

	@Override
	@Transient
	public Collection getIntestatariOperatoriIForm() {
		return this.intestatarioperatori;
	}

	@Override
	public void setIntestatariOperatoriIForm(Set intestatariOperatoriIForm) {
		this.setIntestatariOperatori(intestatariOperatoriIForm);
	}

	@Column(name="SIA_CBI")
	public String getSiaCbi() {
		return siaCbi;
	}

	public void setSiaCbi(String siaCbi) {
		this.siaCbi = siaCbi;
	}

	@Transient
	public String getSiaCbiIForm() {
		return this.siaCbi;
	}

	public void setSiaCbiIForm(String siaCbiIForm) {
		this.siaCbi=siaCbiIForm;
	}
	
}
