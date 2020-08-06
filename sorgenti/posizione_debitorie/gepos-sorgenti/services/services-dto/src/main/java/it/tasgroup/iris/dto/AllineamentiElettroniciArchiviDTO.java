/**
 * 
 */
package it.tasgroup.iris.dto;


import it.tasgroup.services.util.enumeration.EnumFlagStornoAEA;
import it.tasgroup.services.util.enumeration.EnumTipoIncassoRid;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author FabriziE
 *
 */
public class AllineamentiElettroniciArchiviDTO implements Serializable{
	
		
	private String sortingField;
	private String sortingDir;
	
	private Long id;
	private String idReq;
	
	private boolean isCittadino;
	private Timestamp dataRichiesta;
	private Timestamp dataAttivazione;
	private Timestamp dataCessazione;
	private Timestamp dataRichiestaRevoca;
	private Integer progressivo;
	private String operatoreUsername;
	private String causale;
	private String stato;
	private String statoRevoca;
	private String descrizioneStato;
	private String codIndividuale;
	private String tipoCodIndividuale;
	private String ibanAddebito;
	private String codPaeseAddebito;
	private String checkDigitAddebito;
	private String cinAddebito;
	private String abiAddebito;
	private String cabAddebito;
	private String numeroCcAddebito;
	private EnumTipoIncassoRid tipoIncassoRid;
	private EnumFlagStornoAEA flagStorno;
	private String flagIniziativa;
//	private String divisa;
	private String ragSocIntAddebito;

	private String codRiferimento;
	
	private String ragSocSottoscrittore;
	private String codificaFiscaleSottoscrittore;
	private String indirizzoCompletoSottoscrittore;
	private String indirizzoSottoscrittore;
	private String comuneSottoscrittore;
	private String capSottoscrittore;
	private String provinciaSottoscrittore;

	private String ragSocCreditore;
	private String codificaFiscaleCreditore;
	private String siaCreditore;
	private String abiBancaAllineamento;
	
	private Long idRevoca;
	private Long idDistintaPagamento;
	private String intestatario;
	
	
	public boolean isCittadino() {
		return isCittadino;
	}
	public void setCittadino(boolean isCittadino) {
		this.isCittadino = isCittadino;
		if (isCittadino){
			this.flagStorno = EnumFlagStornoAEA.OTTO;
			this.flagIniziativa = "C";
			this.tipoIncassoRid = EnumTipoIncassoRid.ORDINARIO;
		}else{
			this.flagStorno = EnumFlagStornoAEA.UNO;
			this.flagIniziativa = "I";
			this.tipoIncassoRid = EnumTipoIncassoRid.VELOCE;
			
		}
	}
	
	public Long getIdDistintaPagamento() {
		return idDistintaPagamento;
	}
	public void setIdDistintaPagamento(Long idDistintaPagamento) {
		this.idDistintaPagamento = idDistintaPagamento;
	}
	
	public Timestamp getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Timestamp dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public String getTipoCodIndividuale() {
		return tipoCodIndividuale;
	}
	public void setTipoCodIndividuale(String tipoCodIndividuale) {
		this.tipoCodIndividuale = tipoCodIndividuale;
	}
	public String getIbanAddebito() {
		return ibanAddebito;
	}
	public void setIbanAddebito(String ibanAddebito) {
		this.ibanAddebito = ibanAddebito;
		if (ibanAddebito!=null && ibanAddebito.length()==27) {
			this.codPaeseAddebito = ibanAddebito.substring(0, 2);
			this.checkDigitAddebito = ibanAddebito.substring(2, 4);
			this.cinAddebito = ibanAddebito.substring(4, 5);
			this.abiAddebito = ibanAddebito.substring(5, 10);
			this.cabAddebito = ibanAddebito.substring(10, 15);
			this.numeroCcAddebito = ibanAddebito.substring(15);
		}
	}
	public String getCodPaeseAddebito() {
		return codPaeseAddebito;
	}
//	public void setCodPaeseAddebito(String codPaeseAddebito) {
//		this.codPaeseAddebito = codPaeseAddebito;
//	}
	public String getCheckDigitAddebito() {
		return checkDigitAddebito;
	}
//	public void setCheckDigitAddebito(String checkDigitAddebito) {
//		this.checkDigitAddebito = checkDigitAddebito;
//	}
	public String getCinAddebito() {
		return cinAddebito;
	}
//	public void setCinAddebito(String cinAddebito) {
//		this.cinAddebito = cinAddebito;
//	}
	public String getAbiAddebito() {
		return abiAddebito;
	}
//	public void setAbiAddebito(String abiAddebito) {
//		this.abiAddebito = abiAddebito;
//	}
	public String getCabAddebito() {
		return cabAddebito;
	}
//	public void setCabAddebito(String cabAddebito) {
//		this.cabAddebito = cabAddebito;
//	}
	public String getNumeroCcAddebito() {
		return numeroCcAddebito;
	}
//	public void setNumeroCcAddebito(String numeroCcAddebito) {
//		this.numeroCcAddebito = numeroCcAddebito;
//	}
	
	public String getFlagIniziativa() {
		return flagIniziativa;
	}
	public void setFlagIniziativa(String flagIniziativa) {
		this.flagIniziativa=flagIniziativa;
	}
	


//	public String toString(){
//		   StringBuffer sb = new StringBuffer();
//		   sb.append("\n]\n");
//		   sb.append("id="+this.getId());
//	       sb.append("\nindirizzoVO="+this.getIndirizziRT().toString());
//	       sb.append("\ncarrello="+this.getCarrello().toString());
//	       sb.append("\ncommissione="+this.getCommissione());
//	       sb.append("\n"+this.getClass()+"\n");
//		   sb.append("[");
//	       if(specificDetails != null)
//	    	   sb.append(specificDetails.toString());
//	       return sb.toString();
//	    
//	}

	public static void main(String[] args) {
		
		System.out.println("["+org.apache.commons.lang.StringUtils.leftPad("          cippa ",20,'z')+"]");
		System.out.println("["+org.apache.commons.lang.StringUtils.rightPad("          cippa ",20,'z')+"]");
		
//		AllineamentiElettroniciArchiviDTO dto = new AllineamentiElettroniciArchiviDTO();
//		dto.setIbanAddebito("123456789012345678901234567");
//		
//		System.out.println(dto.getIbanAddebito());
//		System.out.println(dto.getCodPaeseAddebito()+dto.getCheckDigitAddebito()+dto.getCinAddebito()+dto.getAbiAddebito()+dto.getCabAddebito()+dto.getNumeroCcAddebito());
	}
	public String getRagSocSottoscrittore() {
		return ragSocSottoscrittore;
	}
	public void setRagSocSottoscrittore(String ragSocSottoscrittore) {
		this.ragSocSottoscrittore = ragSocSottoscrittore;
	}
	public String getCodificaFiscaleSottoscrittore() {
		return codificaFiscaleSottoscrittore;
	}
	public void setCodificaFiscaleSottoscrittore(
			String codificaFiscaleSottoscrittore) {
		this.codificaFiscaleSottoscrittore = codificaFiscaleSottoscrittore;
	}
	public String getIndirizzoSottoscrittore() {
		return indirizzoSottoscrittore;
	}
	public void setIndirizzoSottoscrittore(String indirizzoSottoscrittore) {
		this.indirizzoSottoscrittore = indirizzoSottoscrittore;
	}
	public String getComuneSottoscrittore() {
		return comuneSottoscrittore;
	}
	public void setComuneSottoscrittore(String comuneSottoscrittore) {
		this.comuneSottoscrittore = comuneSottoscrittore;
	}
	public String getCapSottoscrittore() {
		return capSottoscrittore;
	}
	public void setCapSottoscrittore(String capSottoscrittore) {
		this.capSottoscrittore = capSottoscrittore;
	}
	public String getProvinciaSottoscrittore() {
		return provinciaSottoscrittore;
	}
	public void setProvinciaSottoscrittore(String provinciaSottoscrittore) {
		this.provinciaSottoscrittore = provinciaSottoscrittore;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getDataAttivazione() {
		return dataAttivazione;
	}
	public void setDataAttivazione(Timestamp dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}
	public Timestamp getDataCessazione() {
		return dataCessazione;
	}
	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}
	public Timestamp getDataRichiestaRevoca() {
		return dataRichiestaRevoca;
	}
	public void setDataRichiestaRevoca(Timestamp dataRichiestaRevoca) {
		this.dataRichiestaRevoca = dataRichiestaRevoca;
	}
	public String getStatoRevoca() {
		return statoRevoca;
	}
	public void setStatoRevoca(String statoRevoca) {
		this.statoRevoca = statoRevoca;
	}
	public String getRagSocIntAddebito() {
		return ragSocIntAddebito;
	}
	public void setRagSocIntAddebito(String ragSocIntAddebito) {
		this.ragSocIntAddebito = ragSocIntAddebito;
	}
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}
	public Integer getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}
	public String getCodIndividuale() {
		return codIndividuale;
	}
	public void setCodIndividuale(String codIndividuale) {
		this.codIndividuale = codIndividuale;
	}
	public String getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	public String getAbiBancaAllineamento() {
		return abiBancaAllineamento;
	}
	public void setAbiBancaAllineamento(String abiBancaAllineamento) {
		this.abiBancaAllineamento = abiBancaAllineamento;
	}
	public String getSiaCreditore() {
		return siaCreditore;
	}
	public void setSiaCreditore(String siaCreditore) {
		this.siaCreditore = siaCreditore;
	}
	public String getCodificaFiscaleCreditore() {
		return codificaFiscaleCreditore;
	}
	public void setCodificaFiscaleCreditore(String codificaFiscaleCreditore) {
		this.codificaFiscaleCreditore = codificaFiscaleCreditore;
	}
	public String getRagSocCreditore() {
		return ragSocCreditore;
	}
	public void setRagSocCreditore(String ragSocCreditore) {
		this.ragSocCreditore = ragSocCreditore;
	}
	public String getIndirizzoCompletoSottoscrittore() {
		return this.getIndirizzoSottoscrittore();
//		return this.getIndirizzoSottoscrittore() + " " + getComuneSottoscrittore() + " " + getCapSottoscrittore() + " (" + getProvinciaSottoscrittore() + ")";
	}
	public String getIdReq() {
		return idReq;
	}
	public void setIdReq(String idReq) {
		this.idReq = idReq;
	}
	public String getOperatoreUsername() {
		return operatoreUsername;
	}
	public void setOperatoreUsername(String operatoreUsername) {
		this.operatoreUsername = operatoreUsername;
	}
	public EnumTipoIncassoRid getTipoIncassoRid() {
		return tipoIncassoRid;
	}
	public void setTipoIncassoRid(EnumTipoIncassoRid tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
	}
	public EnumFlagStornoAEA getFlagStorno() {
		return flagStorno;
	}
	public void setFlagStorno(EnumFlagStornoAEA flagStorno) {
		this.flagStorno = flagStorno;
	}
	public String getSortingField() {
		return sortingField;
	}
	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}
	public String getSortingDir() {
		return sortingDir;
	}
	public void setSortingDir(String sortingDir) {
		this.sortingDir = sortingDir;
	}
	public String getCodRiferimento() {
		return codRiferimento;
	}
	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}


}
