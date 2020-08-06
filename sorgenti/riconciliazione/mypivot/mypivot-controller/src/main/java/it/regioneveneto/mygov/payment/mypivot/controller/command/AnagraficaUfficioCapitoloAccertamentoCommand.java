/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * 
 * @author Alessandro Paolillo
 *
 */
public class AnagraficaUfficioCapitoloAccertamentoCommand {
	
	/**
	 * Codice anagrafica ufficio
	 */
	private String codiceUfficio;
	/**
	 * Denominazione anagrafica ufficio
	 */
	private String denominazioneUfficio;
	/**
	 * Codice anagrafica capitolo
	 */
	private String codiceCapitolo;
	/**
	 * Denominazione anagrafica capitolo
	 */
	private String denominazioneCapitolo;
	/**
	 * Codice anagrafica accertamento
	 */
	private String codiceAccertamento;
	/**
	 * Denominazione anagrafica accertamento
	 */
	private String denominazioneAccertamento;
	/**
	 * Codice tipo dovuto  		
	 */
	private String codTipoDovuto;
	/**
	 * Flag attivo
	 */
	private Boolean flgAttivo;
	/**
	 * Anno esercizio
	 */
	private String annoEsercizio;
	
	private String myBoxContextURL;
	private String authorizationToken;
	private String requestToken;
	private String uploadPath;
	private Boolean showUploadForm;
	
	private int page;
	private int pageSize;
	
	private Long anagraficaUffCapAccId;
	private Boolean flgAttivoCambiato;
	
	
	//DATI PER LA CONSERVAZIONE DEL DTO 
	
	private Long idDto;
    private String codAccertamentoDto;
    private String codCapitoloDto;
    private String codTipoDovutoDto;
    private String deTipoDovutoDto;
    private String codUfficioDto;
    private String deAccertamentoDto;
    private String deAnnoEsercizioDto;
    private String deCapitoloDto;
    private String deUfficioDto;
    private boolean flgAttivoDto;
	
	

	/**
	 * @return the codiceUfficio
	 */
	public String getCodiceUfficio() {
		return codiceUfficio;
	}

	/**
	 * @param codiceUfficio the codiceUfficio to set
	 */
	public void setCodiceUfficio(String codiceUfficio) {
		this.codiceUfficio = codiceUfficio;
	}

	/**
	 * @return the denominazioneUfficio
	 */
	public String getDenominazioneUfficio() {
		return denominazioneUfficio;
	}

	/**
	 * @param denominazioneUfficio the denominazioneUfficio to set
	 */
	public void setDenominazioneUfficio(String denominazioneUfficio) {
		this.denominazioneUfficio = denominazioneUfficio;
	}

	/**
	 * @return the codiceCapitolo
	 */
	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}

	/**
	 * @param codiceCapitolo the codiceCapitolo to set
	 */
	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}

	/**
	 * @return the denominazioneCapitolo
	 */
	public String getDenominazioneCapitolo() {
		return denominazioneCapitolo;
	}

	/**
	 * @param denominazioneCapitolo the denominazioneCapitolo to set
	 */
	public void setDenominazioneCapitolo(String denominazioneCapitolo) {
		this.denominazioneCapitolo = denominazioneCapitolo;
	}

	/**
	 * @return the codiceAccertamento
	 */
	public String getCodiceAccertamento() {
		return codiceAccertamento;
	}

	/**
	 * @param codiceAccertamento the codiceAccertamento to set
	 */
	public void setCodiceAccertamento(String codiceAccertamento) {
		this.codiceAccertamento = codiceAccertamento;
	}

	/**
	 * @return the denominazioneAccertamento
	 */
	public String getDenominazioneAccertamento() {
		return denominazioneAccertamento;
	}

	/**
	 * @param denominazioneAccertamento the denominazioneAccertamento to set
	 */
	public void setDenominazioneAccertamento(String denominazioneAccertamento) {
		this.denominazioneAccertamento = denominazioneAccertamento;
	}

	/**
	 * @return the authorizationToken
	 */
	public String getAuthorizationToken() {
		return authorizationToken;
	}

	/**
	 * @param authorizationToken the authorizationToken to set
	 */
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	/**
	 * @return the requestToken
	 */
	public String getRequestToken() {
		return requestToken;
	}

	/**
	 * @param requestToken the requestToken to set
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	/**
	 * @return the uploadPath
	 */
	public String getUploadPath() {
		return uploadPath;
	}

	/**
	 * @param uploadPath the uploadPath to set
	 */
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/**
	 * @return the myBoxContextURL
	 */
	public String getMyBoxContextURL() {
		return myBoxContextURL;
	}

	/**
	 * @param myBoxContextURL the myBoxContextURL to set
	 */
	public void setMyBoxContextURL(String myBoxContextURL) {
		this.myBoxContextURL = myBoxContextURL;
	}

	/**
	 * @return the showUploadForm
	 */
	public Boolean getShowUploadForm() {
		return showUploadForm;
	}

	/**
	 * @param showUploadForm the showUploadForm to set
	 */
	public void setShowUploadForm(Boolean showUploadForm) {
		this.showUploadForm = showUploadForm;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the codTipoDovuto
	 */
	public String getCodTipoDovuto() {
		return codTipoDovuto;
	}

	/**
	 * @param codTipoDovuto the codTipoDovuto to set
	 */
	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}

	/**
	 * @return the flgAttivo
	 */
	public Boolean getFlgAttivo() {
		return flgAttivo;
	}

	/**
	 * @param flgAttivo the flgAttivo to set
	 */
	public void setFlgAttivo(Boolean flgAttivo) {
		this.flgAttivo = flgAttivo;
	}

	/**
	 * @return the annoEsercizio
	 */
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}

	/**
	 * @param annoEsercizio the annoEsercizio to set
	 */
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}

	/**
	 * @return the anagraficaUffCapAccId
	 */
	public Long getAnagraficaUffCapAccId() {
		return anagraficaUffCapAccId;
	}

	/**
	 * @param anagraficaUffCapAccId the anagraficaUffCapAccId to set
	 */
	public void setAnagraficaUffCapAccId(Long anagraficaUffCapAccId) {
		this.anagraficaUffCapAccId = anagraficaUffCapAccId;
	}

	/**
	 * @return the flgAttivoCambiato
	 */
	public Boolean getFlgAttivoCambiato() {
		return flgAttivoCambiato;
	}

	/**
	 * @param flgAttivoCambiato the flgAttivoCambiato to set
	 */
	public void setFlgAttivoCambiato(Boolean flgAttivoCambiato) {
		this.flgAttivoCambiato = flgAttivoCambiato;
	}
	
	
	

	/**
	 * @return the idDto
	 */
	public Long getIdDto() {
		return idDto;
	}

	/**
	 * @param idDto the idDto to set
	 */
	public void setIdDto(Long idDto) {
		this.idDto = idDto;
	}

	/**
	 * @return the codAccertamentoDto
	 */
	public String getCodAccertamentoDto() {
		return codAccertamentoDto;
	}

	/**
	 * @param codAccertamentoDto the codAccertamentoDto to set
	 */
	public void setCodAccertamentoDto(String codAccertamentoDto) {
		this.codAccertamentoDto = codAccertamentoDto;
	}

	/**
	 * @return the codCapitoloDto
	 */
	public String getCodCapitoloDto() {
		return codCapitoloDto;
	}

	/**
	 * @param codCapitoloDto the codCapitoloDto to set
	 */
	public void setCodCapitoloDto(String codCapitoloDto) {
		this.codCapitoloDto = codCapitoloDto;
	}

	/**
	 * @return the codTipoDovutoDto
	 */
	public String getCodTipoDovutoDto() {
		return codTipoDovutoDto;
	}

	/**
	 * @param codTipoDovutoDto the codTipoDovutoDto to set
	 */
	public void setCodTipoDovutoDto(String codTipoDovutoDto) {
		this.codTipoDovutoDto = codTipoDovutoDto;
	}

	/**
	 * @return the deTipoDovutoDto
	 */
	public String getDeTipoDovutoDto() {
		return deTipoDovutoDto;
	}

	/**
	 * @param deTipoDovutoDto the deTipoDovutoDto to set
	 */
	public void setDeTipoDovutoDto(String deTipoDovutoDto) {
		this.deTipoDovutoDto = deTipoDovutoDto;
	}

	/**
	 * @return the codUfficioDto
	 */
	public String getCodUfficioDto() {
		return codUfficioDto;
	}

	/**
	 * @param codUfficioDto the codUfficioDto to set
	 */
	public void setCodUfficioDto(String codUfficioDto) {
		this.codUfficioDto = codUfficioDto;
	}

	/**
	 * @return the deAccertamentoDto
	 */
	public String getDeAccertamentoDto() {
		return deAccertamentoDto;
	}

	/**
	 * @param deAccertamentoDto the deAccertamentoDto to set
	 */
	public void setDeAccertamentoDto(String deAccertamentoDto) {
		this.deAccertamentoDto = deAccertamentoDto;
	}

	/**
	 * @return the deAnnoEsercizioDto
	 */
	public String getDeAnnoEsercizioDto() {
		return deAnnoEsercizioDto;
	}

	/**
	 * @param deAnnoEsercizioDto the deAnnoEsercizioDto to set
	 */
	public void setDeAnnoEsercizioDto(String deAnnoEsercizioDto) {
		this.deAnnoEsercizioDto = deAnnoEsercizioDto;
	}

	/**
	 * @return the deCapitoloDto
	 */
	public String getDeCapitoloDto() {
		return deCapitoloDto;
	}

	/**
	 * @param deCapitoloDto the deCapitoloDto to set
	 */
	public void setDeCapitoloDto(String deCapitoloDto) {
		this.deCapitoloDto = deCapitoloDto;
	}

	/**
	 * @return the deUfficioDto
	 */
	public String getDeUfficioDto() {
		return deUfficioDto;
	}

	/**
	 * @param deUfficioDto the deUfficioDto to set
	 */
	public void setDeUfficioDto(String deUfficioDto) {
		this.deUfficioDto = deUfficioDto;
	}

	/**
	 * @return the flgAttivoDto
	 */
	public boolean isFlgAttivoDto() {
		return flgAttivoDto;
	}

	/**
	 * @param flgAttivoDto the flgAttivoDto to set
	 */
	public void setFlgAttivoDto(boolean flgAttivoDto) {
		this.flgAttivoDto = flgAttivoDto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnagraficaCommand [anagraficaUffCapAccId=");
		builder.append(anagraficaUffCapAccId);
		builder.append(", codiceUfficio=");
		builder.append(codiceUfficio);
		builder.append(", denominazioneUfficio=");
		builder.append(denominazioneUfficio);
		builder.append(", codiceCapitolo=");
		builder.append(codiceCapitolo);
		builder.append(", denominazioneCapitolo=");
		builder.append(denominazioneCapitolo);
		builder.append(", codiceAccertamento=");
		builder.append(codiceAccertamento);
		builder.append(", denominazioneAccertamento=");
		builder.append(denominazioneAccertamento);
		builder.append(", authorizationToken=");
		builder.append(authorizationToken);
		builder.append(", requestToken=");
		builder.append(requestToken);
		builder.append(", uploadPath=");
		builder.append(uploadPath);
		builder.append(", myBoxContextURL=");
		builder.append(myBoxContextURL);
		builder.append(", showUploadForm=");
		builder.append(showUploadForm);
		builder.append(", page=");
		builder.append(page);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", codTipoDovuto=");
		builder.append(codTipoDovuto);
		builder.append(", flgAttivo=");
		builder.append(flgAttivo);
		builder.append(", flgAttivoCambiato=");
		builder.append(flgAttivoCambiato);
		builder.append(", annoEsercizio=");
		builder.append(annoEsercizio);
		
		builder.append("]");
		return builder.toString();
	}
	
	
	public String stampaObbligatori() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnagraficaCommand [codiceCapitolo=");
		builder.append(codiceCapitolo);
		builder.append(", denominazioneCapitolo=");
		builder.append(denominazioneCapitolo);
		builder.append(", codiceAccertamento=");
		builder.append(codiceAccertamento);
		builder.append(", denominazioneAccertamento=");
		builder.append(denominazioneAccertamento);
		builder.append(", codTipoDovuto=");
		builder.append(codTipoDovuto);
		builder.append(", flgAttivo=");
		builder.append(flgAttivo);
		builder.append(", annoEsercizio=");
		builder.append(annoEsercizio);
		builder.append("]");
		return builder.toString();
	}
}
