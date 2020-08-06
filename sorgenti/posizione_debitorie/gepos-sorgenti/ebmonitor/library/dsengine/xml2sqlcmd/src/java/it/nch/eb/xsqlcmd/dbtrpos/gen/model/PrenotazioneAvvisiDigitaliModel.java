package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps;

public class PrenotazioneAvvisiDigitaliModel implements TableTimestamps, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1742332516178831241L;

	
	private Long       id;
	private String     idCondizione;              // - ID_CONDIZIONE
	private String     idEnte;                    // - ID_ENTE
	private String     idPagamento;               // - ID_PAGAMENTO (IUV)
	private String     codiceAvviso;              // - CODICE_AVVISO
	private String     tipoOperazioneOriginale;   // - TIPO_OPERAZIONE_ORIG ('C','U','D')
	private String     tipoOperazioneAvviso   ;   // - TIPO_OPERAZIONE_AVVISO ('C','U','D')	
	private String     tipoProcesso;              // - TIPO_PROCESSO
	private String     idRichiestaAvviso;         //  - ID_RICHIESTA_AVVISO
	private String     statoAvviso;               //  - STATO_AVVISO
	private String     descrStatoAvviso;          //  - DESCR_STATO_AVVISO
	private Long       numTentativiAvviso;        //  - NUM_TENTATIVI_AVVISO
	private String     idFileAvvisatura;          //  - ID_FILE_AVVISATURA
	private String     opInserimento;
	private String     opAggiornamento;
	private Timestamp  tsInserimento;
	private Timestamp  tsAggiornamento;
	private Integer    version;                   //  - VERSION
	
	@Override
	public Integer getPrVersione() {		
		return version;
	}

	@Override
	public void setPrVersione(Integer prVersione) {
		version=prVersione;
	}

	@Override
	public String getStRiga() {		
		return null;
	}

	@Override
	public void setStRiga(String stRiga) {		

	}

	@Override
	public String getOpInserimento() {		
		return opInserimento;
	}

	@Override
	public void setOpInserimento(String opInserimento) {
		this.opInserimento=opInserimento;

	}

	@Override
	public Timestamp getTsInserimento() {		
		return tsInserimento;
	}

	@Override
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento=tsInserimento;
	}

	@Override
	public String getOpAggiornamento() {		
		return opAggiornamento;
	}

	@Override
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento=opAggiornamento;
	}

	@Override
	public Timestamp getTsAggiornamento() {		
		return tsAggiornamento;
	}

	@Override
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento=tsAggiornamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}

	public String getTipoOperazioneOriginale() {
		return tipoOperazioneOriginale;
	}

	public void setTipoOperazioneOriginale(String tipoOperazioneOriginale) {
		this.tipoOperazioneOriginale = tipoOperazioneOriginale;
	}

	public String getTipoOperazioneAvviso() {
		return tipoOperazioneAvviso;
	}

	public void setTipoOperazioneAvviso(String tipoOperazioneAvviso) {
		this.tipoOperazioneAvviso = tipoOperazioneAvviso;
	}

	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public String getIdRichiestaAvviso() {
		return idRichiestaAvviso;
	}

	public void setIdRichiestaAvviso(String idRichiestaAvviso) {
		this.idRichiestaAvviso = idRichiestaAvviso;
	}

	public String getStatoAvviso() {
		return statoAvviso;
	}

	public void setStatoAvviso(String statoAvviso) {
		this.statoAvviso = statoAvviso;
	}

	public String getDescrStatoAvviso() {
		return descrStatoAvviso;
	}

	public void setDescrStatoAvviso(String descrStatoAvviso) {
		this.descrStatoAvviso = descrStatoAvviso;
	}

	public Long getNumTentativiAvviso() {
		return numTentativiAvviso;
	}

	public void setNumTentativiAvviso(Long numTentativiAvviso) {
		this.numTentativiAvviso = numTentativiAvviso;
	}

	public String getIdFileAvvisatura() {
		return idFileAvvisatura;
	}

	public void setIdFileAvvisatura(String idFileAvvisatura) {
		this.idFileAvvisatura = idFileAvvisatura;
	}

}
