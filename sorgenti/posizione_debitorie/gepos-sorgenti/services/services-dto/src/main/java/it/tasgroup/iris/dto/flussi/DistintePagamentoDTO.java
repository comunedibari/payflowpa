 /**
  *
  */
package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author FabriziE
 *
 */
public class DistintePagamentoDTO implements Serializable{
    
    private String id;
    private String stato;
    private String progressivo;
    private Timestamp dataCreazione;
    private Timestamp dataSpedizione;
    private BigDecimal importo;
    private BigDecimal importoCommissione;
    private String utenteCreatore;
    private Integer numDisposizioni;
    private Integer idCfgGateway;
    private String modalitaPagamento;
    
    private String codTransazione;
    private String codPagamento;
    
    private String operatorUsername;
    private String indirizzo;
    
    private String codTransazionePSP;
    private String utentecreatore;
    private String emailVersante;
    private String opInserimento;
    
    public String getCodTransazionePSP() {
        return codTransazionePSP;
    }
    
    public void setCodTransazionePSP(String codTransazionePSP) {
        this.codTransazionePSP = codTransazionePSP;
    }
    
    public String getUtentecreatore() {
        return utentecreatore;
    }
    
    public void setUtentecreatore(String utentecreatore) {
        this.utentecreatore = utentecreatore;
    }
    
    public String getEmailVersante() {
        return emailVersante;
    }
    
    public void setEmailVersante(String emailVersante) {
        this.emailVersante = emailVersante;
    }
    
    public String getOpInserimento() {
        return opInserimento;
    }
    
    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }
    
    public String getStato() {
        return stato;
    }
    
    public void setStato(String stato) {
        this.stato = stato;
    }
    
    public String getProgressivo() {
        return progressivo;
    }
    
    public void setProgressivo(String progressivo) {
        this.progressivo = progressivo;
    }
    
    public Timestamp getDataCreazione() {
        return dataCreazione;
    }
    
    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    
    public Timestamp getDataSpedizione() {
        return dataSpedizione;
    }
    
    public void setDataSpedizione(Timestamp dataSpedizione) {
        this.dataSpedizione = dataSpedizione;
    }
    
    public BigDecimal getImporto() {
        return importo;
    }
    
    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }
    
    public BigDecimal getImportoCommissione() {
        return importoCommissione;
    }
    
    public void setImportoCommissione(BigDecimal importoCommissione) {
        this.importoCommissione = importoCommissione;
    }
    
    public String getUtenteCreatore() {
        return utenteCreatore;
    }
    
    public void setUtenteCreatore(String utenteCreatore) {
        this.utenteCreatore = utenteCreatore;
    }
    
    public Integer getNumDisposizioni() {
        return numDisposizioni;
    }
    
    public void setNumDisposizioni(Integer numDisposizioni) {
        this.numDisposizioni = numDisposizioni;
    }
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("\n]\n");
        sb.append("id="+this.getId());
        sb.append("\n"+this.getClass()+"\n");
        sb.append("[");
        return sb.toString();
        
    }
    
    public String getOperatorUsername() {
        return operatorUsername;
    }
    
    public void setOperatorUsername(String operatorUsername) {
        this.operatorUsername = operatorUsername;
    }
    
    public String getCodTransazione() {
        return codTransazione;
    }
    
    public void setCodTransazione(String codTransazione) {
        this.codTransazione = codTransazione;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    public Integer getIdCfgGateway() {
        return idCfgGateway;
    }
    
    public void setIdCfgGateway(Integer idCfgGateway) {
        this.idCfgGateway = idCfgGateway;
    }
    
    public String getModalitaPagamento() {
        return modalitaPagamento;
    }
    
    public void setModalitaPagamento(String modalitaPagamento) {
        this.modalitaPagamento = modalitaPagamento;
    }
    
    public String getCodPagamento() {
        return codPagamento;
    }
    
    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }
    
    
}
