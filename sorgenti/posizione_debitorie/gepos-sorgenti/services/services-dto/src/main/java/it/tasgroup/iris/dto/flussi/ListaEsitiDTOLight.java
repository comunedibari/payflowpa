package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;
import it.tasgroup.services.util.enumeration.EnumStatoRiconciliazione;
import it.tasgroup.services.util.enumeration.EnumTipoEsitoRCT;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ListaEsitiDTOLight implements Serializable {
	
	private Long id;
	private String tipoEsito;
	private Integer progressivo;
	private BigDecimal importo;
	private Timestamp dataPagamento;
	private Timestamp dataValuta;
	private String riferimento;
	private Integer flagRiconciliazione;
	private Integer idBozzeBonificiRiaccredito;
	private Long idrendicontazione;
	private Object idRiconciliazione;
	
	private String flagRiconciliazioneHTML;
	private EnumStatoRiconciliazione statoRiconciliazione;
	
	private String tipoesitoHTML;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoEsito() {
		return tipoEsito;
	}
	public void setTipoEsito(String tipoEsito) {
		this.tipoEsito = tipoEsito;
	}
	public Integer getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public Timestamp getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Timestamp getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(Timestamp dataValuta) {
		this.dataValuta = dataValuta;
	}
	public String getRiferimento() {
		return riferimento;
	}
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	public Integer getFlagRiconciliazione() {
		return flagRiconciliazione;
	}
	public void setFlagRiconciliazione(Integer flagRiconciliazione) {
		
		this.flagRiconciliazione = flagRiconciliazione;
		
		if  (flagRiconciliazione!=null)
			statoRiconciliazione = EnumStatoRiconciliazione.getByShortValue(Short.valueOf(flagRiconciliazione.toString()));
	}
	public Integer getIdBozzeBonificiRiaccredito() {
		return idBozzeBonificiRiaccredito;
	}
	public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}
	public Object getIdRiconciliazione() {
		return idRiconciliazione;
	}
	public void setIdRiconciliazione(Object idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}
	public Long getIdrendicontazione() {
		return idrendicontazione;
	}
	public void setIdrendicontazione(Long idrendicontazione) {
		this.idrendicontazione = idrendicontazione;
	}
	public String getTipoesitoHTML() {
		
		tipoesitoHTML = "";
		try {
			if (this.tipoEsito == null) {
				tipoesitoHTML = "";
			} else {

				if (this.tipoEsito.equalsIgnoreCase(EnumTipoEsitoRCT.ATM.getChiave())) {
					tipoesitoHTML = EnumTipoEsitoRCT.ATM.getDescrizione();
				} else if (this.tipoEsito.equalsIgnoreCase(EnumTipoEsitoRCT.BHB.getChiave())) {
					tipoesitoHTML = EnumTipoEsitoRCT.BHB.getDescrizione();
				} else if (this.tipoEsito.equalsIgnoreCase(EnumTipoEsitoRCT.BOL.getChiave())) {
					tipoesitoHTML = EnumTipoEsitoRCT.BOL.getDescrizione();
				} else if (this.tipoEsito.equalsIgnoreCase(EnumTipoEsitoRCT.CC.getChiave())) {
					tipoesitoHTML = EnumTipoEsitoRCT.CC.getDescrizione();
				} else  {
					tipoesitoHTML = this.tipoEsito;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tipoesitoHTML;
	}
	
	public void setTipoesitoHTML(String tipoesitoHTML) {
		this.tipoesitoHTML = tipoesitoHTML;
	}
	public void setFlagRiconciliazioneHTML(String flagRiconciliazioneHTML) {
		this.flagRiconciliazioneHTML = flagRiconciliazioneHTML;
		
	}
	public String getFlagRiconciliazioneHTML() {
		
		flagRiconciliazioneHTML = "";
		
		try {
			if (this.flagRiconciliazione == null) {
				flagRiconciliazioneHTML = "";
			} else {
                flagRiconciliazioneHTML  = EnumFlagRiconciliazioneEsiti.getEnumFlagRiconciliazioneEsitiByChiave(this.flagRiconciliazione).getDescrizione();
                
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return flagRiconciliazioneHTML;
	}
	public EnumStatoRiconciliazione getStatoRiconciliazione() {
		return statoRiconciliazione;
	}
	public void setStatoRiconciliazione(
			EnumStatoRiconciliazione statoRiconciliazione) {
		this.statoRiconciliazione = statoRiconciliazione;
	}
	
	
}
