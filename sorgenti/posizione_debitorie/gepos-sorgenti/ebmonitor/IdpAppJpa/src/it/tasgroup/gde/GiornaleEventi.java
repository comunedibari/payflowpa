package it.tasgroup.gde;

import it.tasgroup.idp.domain.BaseIdEntity;
import it.tasgroup.idp.gateway.enums.EnumCategoriaEvento;
import it.tasgroup.idp.gateway.enums.EnumComponente;
import it.tasgroup.idp.gateway.enums.EnumSottoTipoEvento;
import it.tasgroup.idp.gateway.enums.EnumTipoEventiNDP;
import it.tasgroup.idp.gateway.enums.EnumTipoVersamento;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="GDE_EVENTI")
public class GiornaleEventi extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 6251489513501022959L;

	public static final String NDP = "NodoDeiPagamentiSPC";
	public static final String PAYTAS_PSP = "PAYTAS-PSP";
	public static final String PSP = "PSP";
	public static final String PSP_TIMER = "PSP-TIMER";
	
	private Long id;
	
	// Campi per il giornale degli eventi
	
	// obbligatori
	private Timestamp dataOraEvento; // Indica la data e l'ora dell'evento secondo il formato ISO 8601, alla risoluzione del millisecondo e sempre riferito al GMT. 
						  // Formato 
						  // [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss.sss] 
						  // (NON usiamo l'esistente tsInserimento ereditato da BaseEntity) 
	private String idDominio; // Campo alfanumerico contenente il codice fiscale dell'ente creditore che invia la richiesta di pagamento.
	private String idUnivocoVersamento; // Riferimento univoco assegnato al pagamento dall'ente beneficiario e presente nel messaggio che ha originato l'evento.
	private String codiceContestoPagamento; // Codice univoco necessario a definire il contesto nel quale viene effettuato il versamento presente nel messaggio che ha originato l'evento.
	private String idPSP; // identificativo del Prestatore servizi di Pagamento univoco nel Dominio scelto dall'Utilizzatore Finale e/o dall'ente creditore
	private EnumComponente componente; // Sistema o sottosistema che ha generato l'evento (es. FESP, WFESP)
	private EnumCategoriaEvento categoriaEvento; // INTERNO/INTERFACCIA, indica se l'evento tracciato e' relativo un'operazione di interfaccia con altri sistemi oppure se rappresenta un'operazione interna (es. cambio di stato) al proprio sistema
	private EnumTipoEventiNDP tipoEvento; // Identificativo del tipo di evento. Nel caso di interazioni SOAP e' il nome del metodo SOAP.
	private EnumSottoTipoEvento sottoTipoEvento; // Nel caso di interazioni SOAP sincrone assume i valori req/rsp per indicare rispettivamente SOAP Request e SOAP Response.
	private String idFruitore; // Nel caso di eventi di tipo INTERFACCIA si deve utilizzare l'Identificativo del sistema del Soggetto richiedente nell'ambito del Dominio.
					   // (Es. identificativoStazioneIntermediarioPA nel caso della nodoInviaRPT)
					   // Nel caso di eventi di tipo INTERNO, si puo' utilizzare un nome di componente o sottocomponente che genera l'evento.
	private String idErogatore; // Nel caso di eventi di tipo INTERFACCIA si deve utilizzare l'Identificativo del sistema del Soggetto rispondente nell'ambito del Dominio. 
						// (Es. "NodoDeiPagamentiSPC" nel caso della nodoInviaRPT) 
						// Nel caso di eventi di tipo INTERNO, si può utilizzare un nome di componente o sottocomponente che processa l'evento. Per quest'ultima tipologia il valore puo' coincidere con l'identificativoFruitore, qualora non vi sia un componente che risponde all'evento stesso.

	// facoltativi
	private EnumTipoVersamento tipoVersamento; // Forma tecnica di pagamento presente nel messaggio che ha originato l'evento.
	private String idStazioneIntermediarioPA; // identificativo della Stazione dell'intermediario dell'ente creditore nel sistema del Nodo dei Pagamenti SPC, da cui e' transitata l'RPT/RT.
	private String canalePagamento; // identificativo del Canale del PSP nel sistema del Nodo dei Pagamenti SPC da cui e' transitata/si vuole far transitare l'RPT/RT.
	private String parametriSpecificiInterfaccia; // parametri specifici utilizzati nell'interfaccia dal PSP o dall'ente creditore nel modello di pagamento 1 o 3
	private String esito; // Campo opzionale in base allo stato dell'operazione al momento della registrazione dell'evento. 
				  // Obbligatorio nel caso di richieste SOAP.
	
	private String idEGov;
	
	private String faultCode;
	private String faultString; 
	private String faultId; 
	private String faultDescription;
	private int    faultSerial;
	
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="GDE_EVENTI_ID_PK_GEN")
    @SequenceGenerator(
    		  name="GDE_EVENTI_ID_PK_GEN"
    		, sequenceName="GDE_EVENTI_ID_SEQ"
    		, allocationSize=1
    )
    @Column(name="ID")	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
		
	@Column(name="DT_EVENTO", nullable = false)
	public Timestamp getDataOraEvento() {
		return dataOraEvento;
	}
	
	public void setDataOraEvento(Timestamp dataOraEvento) {
		this.dataOraEvento = dataOraEvento;
	}
	@Column(name="ID_DOMINIO", nullable = true, length = 35)
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	@Column(name="ID_UNIVOCO_VERSAMENTO", nullable = true, length = 35)
	public String getIdUnivocoVersamento() {
		return idUnivocoVersamento;
	}
	public void setIdUnivocoVersamento(String idUnivocoVersamento) {
		this.idUnivocoVersamento = idUnivocoVersamento;
	}
	@Column(name="CODICE_CONTESTO_PAGAMENTO", nullable = true, length = 35)
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	@Column(name="ID_PRESTATORESERVIZI_PAGAMENTO", nullable = true, length = 35)
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="COMPONENTE", nullable = true, length = 35)
	public EnumComponente getComponente() {
		return componente;
	}
	public void setComponente(EnumComponente componente) {
		this.componente = componente;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="CATEGORIA_EVENTO", nullable = true, length = 35)
	public EnumCategoriaEvento getCategoriaEvento() {
		return categoriaEvento;
	}
	public void setCategoriaEvento(EnumCategoriaEvento categoriaEvento) {
		this.categoriaEvento = categoriaEvento;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_EVENTO", nullable = false, length = 35)
	public EnumTipoEventiNDP getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(EnumTipoEventiNDP tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="SOTTO_TIPO_EVENTO", nullable = true, length = 35)
	public EnumSottoTipoEvento getSottoTipoEvento() {
		return sottoTipoEvento;
	}
	public void setSottoTipoEvento(EnumSottoTipoEvento sottoTipoEvento) {
		this.sottoTipoEvento = sottoTipoEvento;
	}
	@Column(name="ID_FRUITORE", nullable = true, length = 35)
	public String getIdFruitore() {
		return idFruitore;
	}
	public void setIdFruitore(String idFruitore) {
		this.idFruitore = idFruitore;
	}
	@Column(name="ID_EROGATORE", nullable = true, length = 35)
	public String getIdErogatore() {
		return idErogatore;
	}
	public void setIdErogatore(String idErogatore) {
		this.idErogatore = idErogatore;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_VERSAMENTO", nullable = true, length = 35)
	public EnumTipoVersamento getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(EnumTipoVersamento tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	@Column(name="ID_STAZIONE_INTERMEDIARIO_PA", nullable = true, length = 35)
	public String getIdStazioneIntermediarioPA() {
		return idStazioneIntermediarioPA;
	}
	public void setIdStazioneIntermediarioPA(String idStazioneIntermediarioPA) {
		this.idStazioneIntermediarioPA = idStazioneIntermediarioPA;
	}
	@Column(name="CANALE_PAGAMENTO", nullable = true, length = 35)
	public String getCanalePagamento() {
		return canalePagamento;
	}
	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}
	@Column(name="PARAM_SPECIFICI_INTERFACCIA", nullable = true, length = 255)
	public String getParametriSpecificiInterfaccia() {
		return parametriSpecificiInterfaccia;
	}
	public void setParametriSpecificiInterfaccia(String parametriSpecificiInterfaccia) {
		this.parametriSpecificiInterfaccia = parametriSpecificiInterfaccia;
	}
	@Column(name="ESITO", nullable = true, length = 35)
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	@Column(name="ID_EGOV", nullable = true, length = 255)
	public String getIdEGov() {
		return idEGov;
	}
	public void setIdEGov(String idEGov) {
		this.idEGov = idEGov;
	}
	@Column(name="FAULT_CODE", nullable = true, length = 255) 
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	@Column(name="FAULT_STRING", nullable = true, length = 255) 
	public String getFaultString() {
		return faultString;
	}
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	@Column(name="FAULT_ID", nullable = true, length = 255) 
	public String getFaultId() {
		return faultId;
	}
	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}
	@Column(name="FAULT_DESCRIPTION", nullable = true, length = 255) 
	public String getFaultDescription() {
		return faultDescription;
	}
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	@Column(name="FAULT_SERIAL", nullable = true)
	public int getFaultSerial() {
		return faultSerial;
	}
	public void setFaultSerial(int faultSerial) {
		this.faultSerial = faultSerial;
	}
		
	
}
