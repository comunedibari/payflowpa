package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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


/**
 * The persistent class for the BOZZE_BONIFICI_RIACCREDITO database table.
 * 
 */
@Entity
@Table(name="BOZZE_BONIFICI_RIACCREDITO")
@NamedQueries(
{
	@NamedQuery(
			name="getBozzeBonificiRiaccreditoById",
			query="select bozze from BozzeBonificiRiaccredito bozze "+
				  "where bozze.id =:id"),
	@NamedQuery(
		name="listBozzeBonificiRiaccreditoByIdBonifico",
		query="select bozze from BozzeBonificiRiaccredito bozze " +
				"where bozze.bonificoRiaccredito.id=:idBonificoRiaccredito ")
		}
)
public class BozzeBonificiRiaccredito extends BaseIdEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer flagElaborazione;
	private String ibanBeneficiario;
	
	private CondizionePagamento condizionePagamento;
	private String idEnte;
	private Integer idEsitoOrigine;
	private BigDecimal importo;
	private String opAggiornamento;
	private String opInserimento;
	private String ragSocialeBeneficiario;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private BonificiRiaccredito bonificoRiaccredito;
	private String idPagCondEnte;

	private String tipoEsito;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="bozze_bonifici_riaccredito_pk_gen")	
	@SequenceGenerator(
		    name="bozze_bonifici_riaccredito_pk_gen",
		    sequenceName="BOZZE_BONIFICI_RIACCRE_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	
	
	public void setId(Long id) {		
		this.id = id;	 
	} 		
	
	public BozzeBonificiRiaccredito() {
    }
	
	@Column(name="ID_PAG_COND_ENTE")
	public String getIdPagCondEnte() {
        return idPagCondEnte;
    }



    public void setIdPagCondEnte(String idPagCondEnte) {
        this.idPagCondEnte = idPagCondEnte;
    }



    @Column(name="FLAG_ELABORAZIONE")
	public Integer getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(Integer flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}


	@Column(name="IBAN_BENEFICIARIO")
	public String getIbanBeneficiario() {
		return this.ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}

	@ManyToOne(targetEntity=CondizionePagamento.class,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONDIZIONE")
	public CondizionePagamento getCondizionePagamento() {
		return this.condizionePagamento;
	}

	public void setCondizionePagamento(CondizionePagamento condizionePagamento) {
		this.condizionePagamento = condizionePagamento;
	}


	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}


	@Column(name="ID_ESITO_ORIGINE")
	public Integer getIdEsitoOrigine() {
		return this.idEsitoOrigine;
	}

	public void setIdEsitoOrigine(Integer idEsitoOrigine) {
		this.idEsitoOrigine = idEsitoOrigine;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
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


	@Column(name="RAG_SOCIALE_BENEFICIARIO")
	public String getRagSocialeBeneficiario() {
		return this.ragSocialeBeneficiario;
	}

	public void setRagSocialeBeneficiario(String ragSocialeBeneficiario) {
		this.ragSocialeBeneficiario = ragSocialeBeneficiario;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_BONIFICI_RIACCREDITO")
	public BonificiRiaccredito getBonificoRiaccredito() {
		return bonificoRiaccredito;
	}
	
    public void setBonificoRiaccredito(BonificiRiaccredito bonificoRiaccredito) {
		this.bonificoRiaccredito = bonificoRiaccredito;
	}


	public void setTipoEsito(String tipoEsito) {
		this.tipoEsito = tipoEsito;
	}

	@Column(name="TIPO_ESITO")
	public String getTipoEsito() {
		return tipoEsito;
	}


}