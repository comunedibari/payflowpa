package it.tasgroup.idp.proxyndp.gde;


import it.tasgroup.idp.gateway.enums.EnumCategoriaEvento;
import it.tasgroup.idp.gateway.enums.EnumComponente;
import it.tasgroup.idp.gateway.enums.EnumSottoTipoEvento;
import it.tasgroup.idp.gateway.enums.EnumTipoEventiNDP;
import it.tasgroup.idp.gateway.enums.EnumTipoVersamento;

import java.io.Serializable;
import java.sql.Timestamp;

public class GiornaleEventiExtDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Timestamp dataOraEvento; 
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
    
    boolean storeDocumentoNDP;
	private Integer   tentativo; 
	private String    tipo;
	private Integer   dimensione;
	private byte[]    documento;
	private Timestamp tsInserimento;
    private Timestamp tsAggiornamento;
    private String    opInserimento;
    private String    opAggiornamento;
    

	private String faultCode;
	private String faultString; 
	private String faultId; 
	private String faultDescription;
	private int    faultSerial;
	
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public String getFaultString() {
		return faultString;
	}
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	public String getFaultId() {
		return faultId;
	}
	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}
	public String getFaultDescription() {
		return faultDescription;
	}
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	public int getFaultSerial() {
		return faultSerial;
	}
	public void setFaultSerial(int faultSerial) {
		this.faultSerial = faultSerial;
	}
	public Timestamp getDataOraEvento() {
		return dataOraEvento;
	}
	public void setDataOraEvento(Timestamp dataOraEvento) {
		this.dataOraEvento = dataOraEvento;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getIdUnivocoVersamento() {
		return idUnivocoVersamento;
	}
	public void setIdUnivocoVersamento(String idUnivocoVersamento) {
		this.idUnivocoVersamento = idUnivocoVersamento;
	}
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	public EnumComponente getComponente() {
		return componente;
	}
	public void setComponente(EnumComponente componente) {
		this.componente = componente;
	}
	public EnumCategoriaEvento getCategoriaEvento() {
		return categoriaEvento;
	}
	public void setCategoriaEvento(EnumCategoriaEvento categoriaEvento) {
		this.categoriaEvento = categoriaEvento;
	}
	public EnumTipoEventiNDP getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(EnumTipoEventiNDP tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public EnumSottoTipoEvento getSottoTipoEvento() {
		return sottoTipoEvento;
	}
	public void setSottoTipoEvento(EnumSottoTipoEvento sottoTipoEvento) {
		this.sottoTipoEvento = sottoTipoEvento;
	}
	public String getIdFruitore() {
		return idFruitore;
	}
	public void setIdFruitore(String idFruitore) {
		this.idFruitore = idFruitore;
	}
	public String getIdErogatore() {
		return idErogatore;
	}
	public void setIdErogatore(String idErogatore) {
		this.idErogatore = idErogatore;
	}
	public EnumTipoVersamento getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(EnumTipoVersamento tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	public String getIdStazioneIntermediarioPA() {
		return idStazioneIntermediarioPA;
	}
	public void setIdStazioneIntermediarioPA(String idStazioneIntermediarioPA) {
		this.idStazioneIntermediarioPA = idStazioneIntermediarioPA;
	}
	public String getCanalePagamento() {
		return canalePagamento;
	}
	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}
	public String getParametriSpecificiInterfaccia() {
		return parametriSpecificiInterfaccia;
	}
	public void setParametriSpecificiInterfaccia(
			String parametriSpecificiInterfaccia) {
		this.parametriSpecificiInterfaccia = parametriSpecificiInterfaccia;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getIdEGov() {
		return idEGov;
	}
	public void setIdEGov(String idEGov) {
		this.idEGov = idEGov;
	}
	public boolean isStoreDocumentoNDP() {
		return storeDocumentoNDP;
	}
	public void setStoreDocumentoNDP(boolean storeDocumentoNDP) {
		this.storeDocumentoNDP = storeDocumentoNDP;
	}
	public Integer getTentativo() {
		return tentativo;
	}
	public void setTentativo(Integer tentativo) {
		this.tentativo = tentativo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getDimensione() {
		return dimensione;
	}
	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}
	public byte[] getDocumento() {
		return documento;
	}
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	public String getOpInserimento() {
		return opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}    

}
