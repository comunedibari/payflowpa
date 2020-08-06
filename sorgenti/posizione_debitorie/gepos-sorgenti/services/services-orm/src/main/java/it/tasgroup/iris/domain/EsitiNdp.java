package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the ESITI_CBILL database table.
 *
 */
@Entity
@Table(name="ESITI_NDP")
@NamedQueries(
{
	@NamedQuery(
			name="getEsitiNdpByIdFlusso",
			query="select esiti from EsitiNdp esiti where esiti.rendicontazioni.idFlusso =:idFlusso and esiti.flagRiconciliazione =2"),
	@NamedQuery(
		name="getEsitiNdpById",
		query="select esiti from EsitiNdp esiti "+
			  "where esiti.id =:id"),
	@NamedQuery(
		name="listEsitiNdpByIdRendicontazione",
		query="select esiti from EsitiNdp esiti " +
				"where esiti.rendicontazioni.id=:idRendicontazione ")
		}
)
public class EsitiNdp extends BaseIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idRiconciliazione;
    private Integer flagRiconciliazione;
    private BigDecimal importo;
    private String segno;
    private String idRiscossione;
    private Timestamp dataPagamento;
    private String esitoPagamento;
    private String codAnomalia;
    
    private Integer idBozzeBonificiRiaccredito;
    private String opAggiornamento;
    private String opInserimento;
    private Timestamp tsAggiornamento;
    private Timestamp tsInserimento;
    private Rendicontazioni rendicontazioni;
    
    public EsitiNdp() {
    }

    public EsitiNdp(String idRiconciliazione, Integer flagRiconciliazione, BigDecimal importo, String segno, String idRiscossione, Timestamp dataPagamento, String esitoPagamento, Integer idBozzeBonificiRiaccredito, String opAggiornamento, String opInserimento, Timestamp tsAggiornamento, Timestamp tsInserimento, Rendicontazioni rendicontazioni) {
        this.idRiconciliazione = idRiconciliazione;
        this.flagRiconciliazione = flagRiconciliazione;
        this.importo = importo;
        this.segno = segno;
        this.idRiscossione = idRiscossione;
        this.dataPagamento = dataPagamento;
        this.esitoPagamento = esitoPagamento;
        this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
        this.opAggiornamento = opAggiornamento;
        this.opInserimento = opInserimento;
        this.tsAggiornamento = tsAggiornamento;
        this.tsInserimento = tsInserimento;
        this.rendicontazioni = rendicontazioni;
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="esiti_ndp_pk_gen")	
	@SequenceGenerator(
		    name="esiti_ndp_pk_gen",
		    sequenceName="ESITI_NDP_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		
    
    @Column(name="FLAG_RICONCILIAZIONE")
    public Integer getFlagRiconciliazione() {
        return this.flagRiconciliazione;
    }
    
    public void setFlagRiconciliazione(Integer flagRiconciliazione) {
        this.flagRiconciliazione = flagRiconciliazione;
    }    
    
    @Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
    public Integer getIdBozzeBonificiRiaccredito() {
        return this.idBozzeBonificiRiaccredito;
    }
    
    public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
        this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
    }
    
    
    @Column(name="ID_RICONCILIAZIONE")
    public String getIdRiconciliazione() {
        return this.idRiconciliazione;
    }
    
    public void setIdRiconciliazione(String idRiconciliazione) {
        this.idRiconciliazione = idRiconciliazione;
    }
    
    @Column(name="OP_AGGIORNAMENTO")
    public String getOpAggiornamento() {
        return this.opAggiornamento;
    }
    
    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }
    
    
    @Column(name="OP_INSERIMENTO")
    public String getOpInserimento() {
        return this.opInserimento;
    }
    
    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }
        
    @Column(name="TS_AGGIORNAMENTO")
    public Timestamp getTsAggiornamento() {
        return this.tsAggiornamento;
    }
    
    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }
    
    
    @Column(name="TS_INSERIMENTO")
    public Timestamp getTsInserimento() {
        return this.tsInserimento;
    }
    
    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }
    
    //bi-directional many-to-one association to Rendicontazioni
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_RENDICONTAZIONI")
    public Rendicontazioni getRendicontazioni() {
        return this.rendicontazioni;
    }
    
    public void setRendicontazioni(Rendicontazioni rendicontazioni) {
        this.rendicontazioni = rendicontazioni;
    }

    @Column(name="SEGNO")
    public String getSegno() {
        return segno;
    }

    public void setSegno(String segno) {
        this.segno = segno;
    }

    @Column(name="IMPORTO")
    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    @Column(name="ID_RISCOSSIONE")
    public String getIdRiscossione() {
        return idRiscossione;
    }

    public void setIdRiscossione(String idRiscossione) {
        this.idRiscossione = idRiscossione;
    }

    @Column(name = "DATA_PAGAMENTO")
    public Timestamp getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Timestamp dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Column(name = "ESITO_PAGAMENTO")
    public String getEsitoPagamento() {
        return esitoPagamento;
    }

    public void setEsitoPagamento(String esitoPagamento) {
        this.esitoPagamento = esitoPagamento;
    }
 
    @Transient
    public String getImportoConSegno(){
        return getImporto(true);
    }
    
    @Transient
    public String getImportoSenzaSegno(){
       return getImporto(false);
    }
    
    private String getImporto(Boolean conSegno) {
    	DecimalFormat formatoImporto = new DecimalFormat("########0.00");
        StringBuilder imp = new StringBuilder();
        if(getImporto() == null){
            imp.append(formatoImporto.format(BigDecimal.ZERO));
        } else {
        	if (conSegno) {
        		imp.append(getSegno());
        	}
        	imp.append(formatoImporto.format(getImporto()));
        }
        imp.append("&nbsp;&euro;");
        return imp.toString();
    }

    @Column(name="COD_ANOMALIA")
	public String getCodAnomalia() {
		return codAnomalia;
	}

	public void setCodAnomalia(String codAnomalia) {
		this.codAnomalia = codAnomalia;
	}
}