package it.tasgroup.iris.gde;

import java.sql.Timestamp;

import it.tasgroup.iris.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="GDE_DOCUMENTI_NDP")
@NamedQueries( 
	{
		@NamedQuery(name="getDocumentiNDP",query="from GiornaleEventiDocumentiNDP doc where doc.tipo =:TIPO AND doc.idDominio =:ID_DOMINIO  AND doc.idUnivocoVersamento =:ID_UNIVOCO_VERSAMENTO AND doc.codiceContestoPagamento =:COD_CONTESTO_PAGAMENTO AND doc.idPSP =:ID_PSP "),
		@NamedQuery(name="getDocumentiNDPNoIdPsp",query="from GiornaleEventiDocumentiNDP doc where doc.tipo =:TIPO AND doc.idDominio =:ID_DOMINIO  AND doc.idUnivocoVersamento =:ID_UNIVOCO_VERSAMENTO AND doc.codiceContestoPagamento =:COD_CONTESTO_PAGAMENTO "),
		@NamedQuery(name="getVersionDocumentiNDP",query="select doc.version from GiornaleEventiDocumentiNDP doc where doc.tipo =:TIPO AND doc.idDominio =:ID_DOMINIO  AND doc.idUnivocoVersamento =:ID_UNIVOCO_VERSAMENTO AND doc.codiceContestoPagamento =:COD_CONTESTO")
		
	}
)

public class GiornaleEventiDocumentiNDP extends BaseEntity {
	private static final long serialVersionUID = 6251489513501022959L;

	public static final String NDP = "NodoDeiPagamentiSPC";
	public static final String PAYTAS_PSP = "PAYTAS-PSP";
	public static final String PSP = "PSP";
	public static final String PSP_TIMER = "PSP-TIMER";
	
	private Long id;
	
	// Campi per il giornale degli eventi
	
	
	private String    idDominio; // Campo alfanumerico contenente il codice fiscale dell'ente creditore che invia la richiesta di pagamento.
	private String    idUnivocoVersamento; // Riferimento univoco assegnato al pagamento dall'ente beneficiario e presente nel messaggio che ha originato l'evento.
	private String    codiceContestoPagamento; // Codice univoco necessario a definire il contesto nel quale viene effettuato il versamento presente nel messaggio che ha originato l'evento.
	private Integer   tentativo; 
	private String    idPSP; // identificativo del Prestatore servizi di Pagamento univoco nel Dominio scelto dall'Utilizzatore Finale e/o dall'ente creditore
	private String    tipo;
	private Integer   dimensione;
	private byte[]    documento;
	private String    ackDownload;
	private Timestamp tsInserimento;
    private Timestamp tsAggiornamento;
    private String    opInserimento;
    private String    opAggiornamento;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="GDE_DOCUMENTI_NDP_ID_PK_GEN")
    @SequenceGenerator(
    		  name="GDE_DOCUMENTI_NDP_ID_PK_GEN"
    		, sequenceName="GDE_DOCUMENTI_NDP_SEQ"
    		, allocationSize=1
    )
    
    @Column(name="ID")	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	
	@Column(name="ID_DOMINIO", nullable = true, length = 35)
	public String getIdDominio() {
		return idDominio;
	}
	
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	
	@Column(name="ID_UNIVOCO_VERSAMENTO", nullable = true, length = 35)
	public String getIdUnivocoVersamento() {
		return idUnivocoVersamento;
	}
	
	public void setIdUnivocoVersamento(String idUnivocoVersamento) {
		this.idUnivocoVersamento = idUnivocoVersamento;
	}
	
	@Column(name="CODICE_CONTESTO_PAGAMENTO", nullable = true, length = 35)
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	@Column(name="TENTATIVO", nullable = true)
	public Integer getTentativo() {
		return tentativo;
	}
	public void setTentativo(Integer tentativo) {
		this.tentativo = tentativo;
	}
	@Column(name="TIPO", nullable = true, length = 2)
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Column(name="DIMENSIONE", nullable = true)
	public Integer getDimensione() {
		return dimensione;
	}
	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}
	@Column(name="DOCUMENTO", nullable = false, length = 1048576)
	public byte[] getDocumento() {
		return documento;
	}
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}
	
	@Column(name="ACK_DOWNLOAD")
	public String getAckDownload() {
		return this.ackDownload;
	}

	public void setAckDownload(String ackDownload) {
		this.ackDownload = ackDownload;
	}
	@Column(name="ID_PRESTATORESERVIZI_PAGAMENTO", nullable = true, length = 35)
	public String getIdPSP() {
		return idPSP;
	}
	public void setIdPSP(String idPSP) {
		this.idPSP = idPSP;
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
}
