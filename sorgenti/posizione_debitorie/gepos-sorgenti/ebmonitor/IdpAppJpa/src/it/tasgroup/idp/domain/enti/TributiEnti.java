package it.tasgroup.idp.domain.enti;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;

@NamedQueries({
@NamedQuery(
	name="TributiEntiByIdEnte", 
	query=
	"SELECT tributiEnti FROM TributiEnti tributiEnti " +
	" WHERE tributiEnti.id.idEnte = :idEnte and " +
	" tributiEnti.id.cdTrbEnte = :cdTrbEnte"),
@NamedQuery(
	name="TributiEntiByIdEnteCdTrbEnte", 
	query=
	"SELECT tributiEnti FROM TributiEnti tributiEnti " +
	" WHERE tributiEnti.id.idEnte = :idEnte and " +
	" tributiEnti.id.cdTrbEnte = :cdTrbEnte"),
@NamedQuery(name = "TributiEntiByCodEnteTesoreriaAndContoTesoreria", 
			query = "SELECT te FROM TributiEnti te WHERE cfgTsrCodiceEnte = :codEnteTesoreria AND cfgTsrContoEnte = :codContoEnte") 
})

/**
 * The persistent class for the JLTENTR database table.
 * 
 */
@Entity
@Table(name="JLTENTR")
public class TributiEnti extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private TributiEntiPK id;
//	private String idTributo;
	private String deTrb;
	private String flIniziativa;
	private String flPredeterm;	
	private String flNdpModello3;
	private String flNotificaPagamento;
	private String flRicevutaAnonimo;
	private String idSystem;
	private int prVersione;
	private String soggEsclusi;
	private String stato;
	private String iban;
	private Enti jltenti;
	private Tributi jlttrpa;
	//
	private String ndpCodStazPa; 
	private String ndpAuxDigit;  
	private String flNdpIuvGenerato; 
	private String flAddRTVerificaPag; 
	private	Long   ndpIuvStartNum;
	private	String flReplaceOTF; 
	private String IBAN_CCP;
	private String iuvGenSeqType;
	private String ndpCodSegr;
	private String autorizzStampaBP;
    private String intestazioneCcp;
	private String infoTributo;  
	private String uoaCompetente; 
	private List<ConfigNotifiche> cfgNotifiche;
	
	private String tiGestRichRevoca = "KO"; 

	private String cfgTsrCodiceEnte;
	private String cfgTsrContoEnte;
	
	@EmbeddedId
	public TributiEntiPK getId() {
		return this.id;
	}

	public void setId(TributiEntiPK id) {
		this.id = id;
	}
	
	@Column(name="DE_TRB")
	public String getDeTrb() {
		return this.deTrb;
	}

	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}
	
//	@Column(name="ID_TRIBUTO")
//	public String getIdTributo() {
//		return this.idTributo;
//	}
//	public void setIdTributo(String idTributo) {
//		this.idTributo = idTributo;
//	}		

	@Column(name="FL_INIZIATIVA")
	public String getFlIniziativa() {
		return this.flIniziativa;
	}

	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}

	@Column(name="ID_SYSTEM")
	public String getIdSystem() {
		return this.idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="SOGG_ESCLUSI")
	public String getSoggEsclusi() {
		return this.soggEsclusi;
	}

	public void setSoggEsclusi(String soggEsclusi) {
		this.soggEsclusi = soggEsclusi;
	}
	
	@Column(name="IBAN")
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}	

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	//bi-directional many-to-one association to Enti
    @ManyToOne
	@JoinColumn(name="ID_ENTE", insertable = false, updatable = false)
	public Enti getJltenti() {
		return this.jltenti;
	}

	public void setJltenti(Enti jltenti) {
		this.jltenti = jltenti;
	}
	
	//bi-directional many-to-one association to Tributi
    @ManyToOne
	@JoinColumn(name="ID_TRIBUTO")
	public Tributi getJlttrpa() {
		return this.jlttrpa;
	}

	public void setJlttrpa(Tributi jlttrpa) {
		this.jlttrpa = jlttrpa;
	}
	
	@Column(name="FL_PREDETERM")
	public String getFlPredeterm() {
		return flPredeterm;
	}

	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
	}
	@Column(name="NDP_IUV_START_NUM")
	public Long getNdpIuvStartNum() {
		return ndpIuvStartNum;
	}

	public void setNdpIuvStartNum(Long ndpIuvStartNum) {
		this.ndpIuvStartNum = ndpIuvStartNum;
	}
	@Column(name="NDP_COD_STAZ_PA")
	public String getNdpCodStazPa() {
		return ndpCodStazPa;
	}

	public void setNdpCodStazPa(String ndpCodStazPa) {
		this.ndpCodStazPa = ndpCodStazPa;
	}
	@Column(name="NDP_AUX_DIGIT")
	public String getNdpAuxDigit() {
		return ndpAuxDigit;
	}

	public void setNdpAuxDigit(String ndpAuxDigit) {
		this.ndpAuxDigit = ndpAuxDigit;
	}
	
	@Column(name="FL_NDP_IUV_GENERATO")
	public String getFlNdpIuvGenerato() {
		return flNdpIuvGenerato;
	}
	
	public void setFlNdpIuvGenerato(String flNdpIuvGenerato) {
		this.flNdpIuvGenerato = flNdpIuvGenerato;
	}

	@Column(name="FL_ADD_RT_VERIFICA_PAG")
	public String getFlAddRTVerificaPag(){
		return flAddRTVerificaPag;
	}

	public void setFlAddRTVerificaPag(String flAddRTVerificaPag) {
		this.flAddRTVerificaPag = flAddRTVerificaPag;
	}
	
	@Column(name="FL_REPLACE_OTF")
	public String getFlReplaceOTF() {
		return flReplaceOTF;
	}

	public void setFlReplaceOTF(String flReplaceOTF) {
		this.flReplaceOTF = flReplaceOTF;
	}	

	@Column(name="IBAN_CCP")
	public String getIBAN_CCP() {
		return IBAN_CCP;
	}
	public void setIBAN_CCP(String iban) {
		IBAN_CCP = iban;
	}

	@Column(name="IUV_GEN_SEQ_TYPE")
	public String getIuvGenSeqType() {
		return iuvGenSeqType;
	}
	
	public void setIuvGenSeqType(String iuvGenSeqType) {
		this.iuvGenSeqType = iuvGenSeqType;
	}
	
	@Column(name="NDP_COD_SEGR")
	public String getNdpCodSegr() {
		return ndpCodSegr;
	}

	public void setNdpCodSegr(String ndpCodSegr) {
		this.ndpCodSegr = ndpCodSegr;
	}
	
	@Column(name = "AUTORIZZ_STAMPA_BP")
	public String getAutorizzStampaBP() {
		return autorizzStampaBP;
	}

	public void setAutorizzStampaBP(String autorizzStampaBP) {
		this.autorizzStampaBP = autorizzStampaBP;
	}
	
	@Column(name="INTESTAZIONE_CCP") 
	  public String getIntestazioneCcp() { 
	 	return intestazioneCcp; 
	} 
	public void setIntestazioneCcp(String intestazioneCcp) { 
	 	this.intestazioneCcp = intestazioneCcp; 
	} 
	@Column(name="INFO_TRIBUTO") 
	public String getInfoTributo() { 
	 	return infoTributo; 
	} 
	public void setInfoTributo(String infoTributo) { 
	 	this.infoTributo = infoTributo; 
	} 
	
	@Column(name="TIPO_GESTIONE_RICH_REVOCA")
	public String getTiGestRichRevoca() {
		return tiGestRichRevoca;
	}

	public void setTiGestRichRevoca(String tiGestRichRevoca) {
		this.tiGestRichRevoca = tiGestRichRevoca;
	}
	
	@Column(name="UOA_COMPETENTE") 
	public String getUoaCompetente() { 
	 	return uoaCompetente; 
	} 
	public void setUoaCompetente(String uoaCompetente) { 
	 	this.uoaCompetente = uoaCompetente; 
	} 

	@Column(name="CFG_TSR_CODICE_ENTE")
	public String getCfgTsrCodiceEnte() {
		return cfgTsrCodiceEnte;
	}
	public void setCfgTsrCodiceEnte(String cfgTsrCodiceEnte) {
		this.cfgTsrCodiceEnte = cfgTsrCodiceEnte;
	}
	@Column(name="CFG_TSR_CONTO_ENTE")
	public String getCfgTsrContoEnte() {
		return cfgTsrContoEnte;
	}
	public void setCfgTsrContoEnte(String cfgTsrContoEnte) {
		this.cfgTsrContoEnte = cfgTsrContoEnte;
	}
	
	@Column(name="FL_NDP_MODELLO3")
	public String getFlNdpModello3() {
		return flNdpModello3;
	}

	public void setFlNdpModello3(String flNdpModello3) {
		this.flNdpModello3 = flNdpModello3;
	}
	
	@Column(name="FL_NOTIFICA_PAGAMENTO")
	public String getFlNotificaPagamento() {
		return flNotificaPagamento;
	}

	public void setFlNotificaPagamento(String flNotificaPagamento) {
		this.flNotificaPagamento = flNotificaPagamento;
	}
	
	@Column(name="FL_RICEVUTA_ANONIMO")
	public String getFlRicevutaAnonimo() {
		return flRicevutaAnonimo;
	}

	public void setFlRicevutaAnonimo(String flRicevutaAnonimo) {
		this.flRicevutaAnonimo = flRicevutaAnonimo;
	}
	
	//bi-directional many-to-one association to CfgNotificaPagamento
	@OneToMany(mappedBy="jltentr", cascade={CascadeType.ALL}, orphanRemoval=true)
	public List<ConfigNotifiche> getCfgNotifiche() {
		return cfgNotifiche;
	}

	public void setCfgNotifiche(List<ConfigNotifiche> cfgNotifiche) {
		this.cfgNotifiche = cfgNotifiche;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TributiEnti [id=");
		builder.append(id);
		builder.append(", deTrb=");
		builder.append(deTrb);
		builder.append(", flIniziativa=");
		builder.append(flIniziativa);
		builder.append(", flPredeterm=");
		builder.append(flPredeterm);
		builder.append(", idSystem=");
		builder.append(idSystem);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", soggEsclusi=");
		builder.append(soggEsclusi);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", jltenti=");
		builder.append(jltenti);
		builder.append(", jlttrpa=");
		builder.append(jlttrpa);
		builder.append(",ndpCodStazPa=");
		builder.append(ndpCodStazPa);
		builder.append(",ndpAuxDigit="); 
		builder.append(ndpAuxDigit);  
		builder.append(",flNdpIuvGenerato=");
		builder.append(flNdpIuvGenerato); 
		builder.append(",flAddRTVerificaPag=");
		builder.append(flAddRTVerificaPag);
		builder.append(",ndpIuvStartNum=");
		builder.append(ndpIuvStartNum);
		builder.append(",flReplaceOTF=");
		builder.append(flReplaceOTF);		
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append(", getCfgTsrContoEnte()=");
		builder.append(getCfgTsrContoEnte());
		builder.append(", getCfgTsrCodiceEnte()=");
		builder.append(getCfgTsrCodiceEnte());
		builder.append(", flNotificaPagamento()=");
		builder.append(getFlNotificaPagamento());
		builder.append(", flNdpModello3()=");
		builder.append(getFlNdpModello3());
		builder.append(", flRicevutaAnonimo()=");
		builder.append(getFlRicevutaAnonimo());
		builder.append(", flSpontaneoExt()=");
		builder.append(getFlRicevutaAnonimo());
		builder.append("]");
		return builder.toString();
	}	
	
	
}