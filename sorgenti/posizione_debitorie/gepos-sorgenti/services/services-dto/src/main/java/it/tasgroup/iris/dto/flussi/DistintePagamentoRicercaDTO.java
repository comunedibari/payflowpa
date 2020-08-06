/**
 * 
 */
package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FabriziE
 *
 */
public class DistintePagamentoRicercaDTO implements Serializable{
	
	private String dataCreazioneDaGG;
	private String dataCreazioneDaMM;
	private String dataCreazioneDaYY;
	private String dataCreazioneAGG;
	private String dataCreazioneAMM;
	private String dataCreazioneAYY;
    private String dataScadenzaGG;
    private String dataScadenzaMM;
    private String dataScadenzaYY;

	private String modPagamento;
	private String modPagamentoAF;
	private String modPagamentoPsp;
	private String tipoVersamento;
	private String modAnonima;
	private String tipoSpontaneo;
	private String cfUtenteCreatore;
	private String emailVersante;
	private String importoPagamentoDa;
	private String importoPagamentoA;
	private String idEnte;
	private String idIntestatarioEnte; 
    private String gruppoIntestatarioEnte;
	
	private String statoPagamento;
	private String flagIncasso;
	private String richiestaRevoca;
	private String idTributo;
	private ArrayList<String> idTributoLista = new ArrayList<String>();
	private String idPagamento;
	private String idPendenza;
	private String codPagamento;
	private String codFiscDebitore;
	private String withQuietanza;
	private String withRiaccredito;
	private String iuv;
    private String idFlusso;
    private String trn;
    private String idPSP;
	private String dataRegolamentoDaGG;
	private String dataRegolamentoDaMM;
	private String dataRegolamentoDaYY;
	private String dataRegolamentoAGG;
	private String dataRegolamentoAMM;
	private String dataRegolamentoAYY;
	
	private String opInserimento;
    private String annoRif;
    
    private String circuito;
    private List<String> modelloVersamento;
    private String istitutoAttestante;
    

	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public List<String> getModelloVersamento() {
		return modelloVersamento;
	}
	public void setModelloVersamento(List<String> modelloVersamento) {
		this.modelloVersamento = modelloVersamento;
	}
	public String getIstitutoAttestante() {
		return istitutoAttestante;
	}
	public void setIstitutoAttestante(String istitutoAttestante) {
		this.istitutoAttestante = istitutoAttestante;
	}

	public String getDataCreazioneDaGG() {
		return dataCreazioneDaGG;
	}
	public void setDataCreazioneDaGG(String dataCreazioneDaGG) {
		this.dataCreazioneDaGG = dataCreazioneDaGG;
	}
	public String getDataCreazioneDaMM() {
		return dataCreazioneDaMM;
	}
	public void setDataCreazioneDaMM(String dataCreazioneDaMM) {
		this.dataCreazioneDaMM = dataCreazioneDaMM;
	}
	public String getDataCreazioneDaYY() {
		return dataCreazioneDaYY;
	}
	public void setDataCreazioneDaYY(String dataCreazioneDaYY) {
		this.dataCreazioneDaYY = dataCreazioneDaYY;
	}
	public String getDataCreazioneAGG() {
		return dataCreazioneAGG;
	}
	public void setDataCreazioneAGG(String dataCreazioneAGG) {
		this.dataCreazioneAGG = dataCreazioneAGG;
	}
	public String getDataCreazioneAMM() {
		return dataCreazioneAMM;
	}
	public void setDataCreazioneAMM(String dataCreazioneAMM) {
		this.dataCreazioneAMM = dataCreazioneAMM;
	}
	public String getDataCreazioneAYY() {
		return dataCreazioneAYY;
	}
	public void setDataCreazioneAYY(String dataCreazioneAYY) {
		this.dataCreazioneAYY = dataCreazioneAYY;
	}
	public String getModPagamento() {
		return modPagamento;
	}
	public void setModPagamento(String modPagamento) {
		this.modPagamento = modPagamento;
	}
	public String getModPagamentoPsp() {
		return modPagamentoPsp;
	}
	public void setModPagamentoPsp(String modPagamentoPsp) {
		this.modPagamentoPsp = modPagamentoPsp;
	}
	public String getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	public String getCfUtenteCreatore() {
		return cfUtenteCreatore;
	}
	public void setCfUtenteCreatore(String cfUtenteCreatore) {
		this.cfUtenteCreatore = cfUtenteCreatore;
	}
	public String getEmailVersante() {
		return emailVersante;
	}
	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}
	public String getImportoPagamentoDa() {
		return importoPagamentoDa;
	}
	public void setImportoPagamentoDa(String importoPagamentoDa) {
		this.importoPagamentoDa = importoPagamentoDa;
	}
	public String getImportoPagamentoA() {
		return importoPagamentoA;
	}
	public void setImportoPagamentoA(String importoPagamentoA) {
		this.importoPagamentoA = importoPagamentoA;
	}
	public String getStatoPagamento() {
		return statoPagamento;
	}
	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}
	public String getFlagIncasso() {
		return flagIncasso;
	}
	public void setFlagIncasso(String flagIncasso) {
		this.flagIncasso = flagIncasso;
	}
	public String getRichiestaRevoca() {
		return richiestaRevoca;
	}
	public void setRichiestaRevoca(String richiestaRevoca) {
		this.richiestaRevoca = richiestaRevoca;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getCodPagamento() {
		return codPagamento;
	}
	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}
	public String getCodFiscDebitore() {
		return codFiscDebitore;
	}
	public void setCodFiscDebitore(String codFiscDebitore) {
		this.codFiscDebitore = codFiscDebitore;
	}
	public String getIdIntestatarioEnte() {
		return idIntestatarioEnte;
	}
	public void setIdIntestatarioEnte(String idIntestatarioEnte) {
		this.idIntestatarioEnte = idIntestatarioEnte;
	}
    public String getIdTributo() {
        return idTributo;
    }
    public void setIdTributo(String idTributo) {
        this.idTributo = idTributo;
    }
    
    public ArrayList<String> getIdTributoLista() {
		return idTributoLista;
	}
	public void setIdTributoLista(ArrayList<String> idTributoLista) {
		this.idTributoLista = idTributoLista;
	}
	public String getDataScadenzaGG() {
        return dataScadenzaGG;
    }
    public void setDataScadenzaGG(String dataScadenzaGG) {
        this.dataScadenzaGG = dataScadenzaGG;
    }
    public String getDataScadenzaMM() {
        return dataScadenzaMM;
    }
    public void setDataScadenzaMM(String dataScadenzaMM) {
        this.dataScadenzaMM = dataScadenzaMM;
    }
    public String getDataScadenzaYY() {
        return dataScadenzaYY;
    }
    public void setDataScadenzaYY(String dataScadenzaYY) {
        this.dataScadenzaYY = dataScadenzaYY;
    }
    public String getOpInserimento() {
        return opInserimento;
    }
    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }
    public String getModAnonima() {
        return modAnonima;
    }
    public void setModAnonima(String modAnonima) {
        this.modAnonima = modAnonima;
    }
    public String getTipoSpontaneo() {
        return tipoSpontaneo;
    }
    public void setTipoSpontaneo(String tipoSpontaneo) {
        this.tipoSpontaneo = tipoSpontaneo;
    }
    public String getWithQuietanza() {
        return withQuietanza;
    }
	public void setWithQuietanza(String withQuietanza) {
        this.withQuietanza = withQuietanza;
    }
    public String getWithRiaccredito() {
		return withRiaccredito;
	}
	public void setWithRiaccredito(String withRiaccredito) {
		this.withRiaccredito = withRiaccredito;
	}
	public String getAnnoRif() {
		return annoRif;
	}
	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getIdFlusso() {
		return idFlusso;
	}
	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}
	public String getTrn() {
		return trn;
	}
	public void setTrn(String trn) {
		this.trn = trn;
	}
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
	}
	public String getDataRegolamentoDaGG() {
		return dataRegolamentoDaGG;
	}
	public void setDataRegolamentoDaGG(String dataRegolamentoDaGG) {
		this.dataRegolamentoDaGG = dataRegolamentoDaGG;
	}
	public String getDataRegolamentoDaMM() {
		return dataRegolamentoDaMM;
	}
	public void setDataRegolamentoDaMM(String dataRegolamentoDaMM) {
		this.dataRegolamentoDaMM = dataRegolamentoDaMM;
	}
	public String getDataRegolamentoDaYY() {
		return dataRegolamentoDaYY;
	}
	public void setDataRegolamentoDaYY(String dataRegolamentoDaYY) {
		this.dataRegolamentoDaYY = dataRegolamentoDaYY;
	}
	public String getDataRegolamentoAGG() {
		return dataRegolamentoAGG;
	}
	public void setDataRegolamentoAGG(String dataRegolamentoAGG) {
		this.dataRegolamentoAGG = dataRegolamentoAGG;
	}
	public String getDataRegolamentoAMM() {
		return dataRegolamentoAMM;
	}
	public void setDataRegolamentoAMM(String dataRegolamentoAMM) {
		this.dataRegolamentoAMM = dataRegolamentoAMM;
	}
	public String getDataRegolamentoAYY() {
		return dataRegolamentoAYY;
	}
	public void setDataRegolamentoAYY(String dataRegolamentoAYY) {
		this.dataRegolamentoAYY = dataRegolamentoAYY;
	}
	public String getModPagamentoAF() {
		return modPagamentoAF;
	}
	public void setModPagamentoAF(String modPagamentoAF) {
		this.modPagamentoAF = modPagamentoAF;
	}

}
