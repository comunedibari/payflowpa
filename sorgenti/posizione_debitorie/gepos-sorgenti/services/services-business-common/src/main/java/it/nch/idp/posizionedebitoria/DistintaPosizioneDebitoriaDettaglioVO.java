package it.nch.idp.posizionedebitoria;

import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

public class DistintaPosizioneDebitoriaDettaglioVO extends DistintaPosizioneDebitoriaVO
{
	private String idEnte;
	private String codTrbEnte;
	private BigDecimal importoPositivo;
	private String descrizioneStato;
	private Timestamp timbroConferma;
	
	private String tipoStrumento;
	private Date dataTransazione;
	private String idAutorizzazione;
	private BigDecimal importoNetto;
	private String codiceContestoPagamento;
	private String coNop;
	private String spontaneo;
	private String deOperazione;
	private String tranId;
	private boolean associatedDocAvailable = true;
	private String flagOpposizione730;
	
	private String iuv; 
	private Long idCfgGatewayPagamento;	
	

	private String idPspModello3;
	private String idIntermediarioModello3;
	private String idCanaleModello3;
	
	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public String getSpontaneo() {
		return spontaneo;
	}

	public void setSpontaneo(String spontaneo) {
		this.spontaneo = spontaneo;
	}

	public String getCoNop() {
		return coNop;
	}

	public void setCoNop(String coNop) {
		this.coNop = coNop;
	}

	public BigDecimal getImportoNetto() {
		return importoNetto;
	}

	public void setImportoNetto(BigDecimal importoNetto) {
		this.importoNetto = importoNetto;
	}

	public String getTipoStrumento() {
		return tipoStrumento;
	}

	public void setTipoStrumento(String tipoStrumento) {
		this.tipoStrumento = tipoStrumento;
	}

	public Date getDataTransazione() {
		return dataTransazione;
	}

	public void setDataTransazione(Date dataTransazione) {
		this.dataTransazione = dataTransazione;
	}

	public String getIdAutorizzazione() {
		return idAutorizzazione;
	}

	public void setIdAutorizzazione(String idAutorizzazione) {
		this.idAutorizzazione = idAutorizzazione;
	}

	public BigDecimal getImportoPositivo() {
		return importoPositivo;
	}

	public void setImportoPositivo(BigDecimal decimal) {
		importoPositivo = decimal;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String string) {
		descrizioneStato = string;
	}

	public Timestamp getTimbroConferma() {
		return timbroConferma;
	}

	public void setTimbroConferma(Timestamp timestamp) {
		timbroConferma = timestamp;
	}

	public String getDeOperazione() {
		return deOperazione;
	}

	public void setDeOperazione(String deOperazione) {
		this.deOperazione = deOperazione;
	}

	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}

	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getCodTrbEnte() {
		return codTrbEnte;
	}

	public void setCodTrbEnte(String codTrbEnte) {
		this.codTrbEnte = codTrbEnte;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public boolean isAssociatedDocAvailable() {
		return associatedDocAvailable;
	}

	public void setAssociatedDocAvailable(boolean associatedDocAvailable) {
		this.associatedDocAvailable = associatedDocAvailable;
	}

	public Long getIdCfgGatewayPagamento() {
		return idCfgGatewayPagamento;
	}

	public void setIdCfgGatewayPagamento(Long idCfgGatewayPagamento) {
		this.idCfgGatewayPagamento = idCfgGatewayPagamento;
	}

	public String getFlagOpposizione730() {
		return flagOpposizione730;
	}

	public void setFlagOpposizione730(String flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}
	
	 public String getIdDocumentoRepositoryDownload() {
		String res = null;
		Properties gwProperties = ConfigurationPropertyLoader.getProperties("gateway-ws-client.properties");
		
		String gwBaseUrl= gwProperties.getProperty("iris.gateway.webapp.baseUrl");   	
        String link = gwBaseUrl+"/documentiPagamento.do?method=downloadQuietanza&" + SharedConstants.CRYPTEDPARAMS + "=";
        
        if (isAssociatedDocAvailable() && EnumUtils.matchByChiave(getFlagIncasso(),  EnumStatoIncasso.ACCREDITATO_CONTO_TECNICO,  EnumStatoIncasso.RIACCREDITATO_ENTE, EnumStatoIncasso.NON_GESTITO )) {
        	Integer idPagamento = ((DistintaPosizioneDebitoriaVO)this.getPendenze().get(0)).getIdPagamento().intValue();
        	it.tasgroup.crypt.url.DownloadRicevutaParametersEncrypter dp = new it.tasgroup.crypt.url.DownloadRicevutaParametersEncrypter(getCodPagamento(), getCoPagante(), String.valueOf(idPagamento));
        	res = link + dp.encrypt();
        	
        } 
        return res;
		 
	 }

	public String getIdPspModello3() {
		return idPspModello3;
	}

	public void setIdPspModello3(String idPspModello3) {
		this.idPspModello3 = idPspModello3;
	}

	public String getIdIntermediarioModello3() {
		return idIntermediarioModello3;
	}

	public void setIdIntermediarioModello3(String idIntermediarioModello3) {
		this.idIntermediarioModello3 = idIntermediarioModello3;
	}

	public String getIdCanaleModello3() {
		return idCanaleModello3;
	}

	public void setIdCanaleModello3(String idCanaleModello3) {
		this.idCanaleModello3 = idCanaleModello3;
	}

}