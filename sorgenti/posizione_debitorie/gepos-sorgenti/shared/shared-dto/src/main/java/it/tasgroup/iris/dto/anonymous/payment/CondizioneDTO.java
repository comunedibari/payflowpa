package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.tasgroup.iris.dto.anonymous.ddp.DDPAnonymousDTOLight;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.iris.util.NumberUtils;


public class CondizioneDTO implements Serializable {
    
    private String id;
    private String idPagamento;
    private Date scadenza;
    private Date fineValidita;
    private BigDecimal importo; //im pagato
    private BigDecimal importoPendenza; 
	private String ente;
	private String idEnte;
	private String tributo;
    private String descrTributo;
    private String note;
    private String dettaglioImporto;
    private String idPendenza;
    private String idPendenzaEnte;
	private String causalePagamento;  
    private String causalePendenza;
    private boolean inCart;
    private String esitoAttualizzatore;
    private EnumStatoPagamentoCondizione statoPagamentoCalcolato;
    private String idDistintaStatoPagamentoCalcolato;
    private String codPagamento;
    private int annoRiferimento;
    private boolean allegatiPendenza;

	private DDPAnonymousDTOLight validDDP;
    
    private List<DDPAnonymousDTOLight> ddpAnnullati = new ArrayList<DDPAnonymousDTOLight>();
    
    
    
    private String debitori;
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getScadenza() {
        return scadenza;
    }
    
    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }
    
    public BigDecimal getImporto() {
        return importo;
    }
    
    public void setImporto(BigDecimal importo) {
    	if (importo == null)
    		this.importo = null;
    	else
    		this.importo = importo.setScale(NumberUtils.RE_SCALE);
    }
    
    public String getEnte() {
        return ente;
    }
    
    public void setEnte(String ente) {
        this.ente = ente;
    }
    
    public String getTributo() {
        return tributo;
    }
    
    public void setTributo(String tributo) {
        this.tributo = tributo;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public String getDettaglioImporto() {
        return dettaglioImporto;
    }
    
    public void setDettaglioImporto(String dettaglioImporto) {
        this.dettaglioImporto = dettaglioImporto;
    }
    
    public Date getFineValidita() {
        return fineValidita;
    }
    
    public void setFineValidita(Date fineValidita) {
        this.fineValidita = fineValidita;
    }
    
    @Override
    public String toString() {
        return "CondizioneDTO [id=" + id + ", scadenza=" + scadenza
                + ", fineValidita=" + fineValidita + ", importo=" + importo
                + ", ente=" + ente + ", tributo=" + tributo + ", note=" + note
                + ", dettaglioImporto=" + dettaglioImporto + "]";
    }
    
    public String getIdPendenza() {
        return idPendenza;
    }
    
    
    
    public void setIdPendenza(String idPendenza) {
        this.idPendenza = idPendenza;
    }
    
    public boolean isInCart() {
        return inCart;
    }
    
    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }
    
    public String getCausalePagamento() {
        return causalePagamento;
    }
    
    public void setCausalePagamento(String causalePagamento) {
        this.causalePagamento = causalePagamento;
    }

    public String getEsitoAttualizzatore() {
        return esitoAttualizzatore;
    }

    public void setEsitoAttualizzatore(String esitoAttualizzatore) {
        this.esitoAttualizzatore = esitoAttualizzatore;
    }

	public EnumStatoPagamentoCondizione getStatoPagamentoCalcolato() {
		return statoPagamentoCalcolato;
	}

	public void setStatoPagamentoCalcolato(
			EnumStatoPagamentoCondizione statoPagamentoCalcolato) {
		this.statoPagamentoCalcolato = statoPagamentoCalcolato;
	}

	public String getIdDistintaStatoPagamentoCalcolato() {
		return idDistintaStatoPagamentoCalcolato;
	}

	public void setIdDistintaStatoPagamentoCalcolato(String idDistintaStatoPagamentoCalcolato) {
		this.idDistintaStatoPagamentoCalcolato = idDistintaStatoPagamentoCalcolato;
	}

	public String getDebitori() {
		return debitori;
	}

	public void setDebitori(String debitori) {
		this.debitori = debitori;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getDescrTributo() {
		return descrTributo;
	}

	public void setDescrTributo(String descrTributo) {
		this.descrTributo = descrTributo;
	}

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	public String getCausalePendenza() {
		return causalePendenza;
	}

	public void setCausalePendenza(String causalePendenza) {
		this.causalePendenza = causalePendenza;
	}

	public int getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(int annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
    
	public List<DDPAnonymousDTOLight> getDdpAnnullati() {
        return ddpAnnullati;
    }
    
    public void setDdpAnnullati(List<DDPAnonymousDTOLight> ddpAnnullati) {
        this.ddpAnnullati = ddpAnnullati;
    }
    
    public DDPAnonymousDTOLight getValidDDP() {
        return validDDP;
    }
    
    public void setValidDDP(DDPAnonymousDTOLight validDDP) {
        this.validDDP = validDDP;
    }
    public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

    public BigDecimal getImportoPendenza() {
		return importoPendenza;
	}

	public void setImportoPendenza(BigDecimal importoPendenza) {
		this.importoPendenza = importoPendenza;
	}

    public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

    
    public boolean hasAllegatiPendenza() {
		return allegatiPendenza;
	}

	public void setAllegatiPendenza(boolean allegatiPendenza) {
		this.allegatiPendenza = allegatiPendenza;
	}

}
