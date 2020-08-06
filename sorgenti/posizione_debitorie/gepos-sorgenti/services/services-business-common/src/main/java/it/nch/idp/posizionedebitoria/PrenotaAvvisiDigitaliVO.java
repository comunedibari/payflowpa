package it.nch.idp.posizionedebitoria;

import it.nch.idp.eventi.EventiVO;  



public class PrenotaAvvisiDigitaliVO {
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
	private EventiVO     eventoSms;                 //  - ID_EVENTO_SMS
	private EventiVO     eventoEmail;               //  - ID_EVENTO_EMAIL
	
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
	public EventiVO getEventoSms() {
		return eventoSms;
	}
	public void setEventoSms(EventiVO eventoSms) {
		this.eventoSms = eventoSms;
	}
	public EventiVO getEventoEmail() {
		return eventoEmail;
	}
	public void setEventoEmail(EventiVO eventoEmail) {
		this.eventoEmail = eventoEmail;
	}

}
