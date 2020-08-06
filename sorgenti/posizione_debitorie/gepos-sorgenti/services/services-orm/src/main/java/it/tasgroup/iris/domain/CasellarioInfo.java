package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;


/**
 * The persistent class for the CASELLARIO_INFO database table.
 * 
 */
@Entity
@Table(name="CASELLARIO_INFO")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name="getComboOptionResultMapping",
            columns = {
                @ColumnResult(name="codice"),
                @ColumnResult(name="descrizione")
            }
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "getListMittenti",
//            query = "select distinct cfg.SYSTEM_ID as codice, "
//            		+ "coalesce(cfg.SYSTEM_NAME, cfg.SYSTEM_ID)  as descrizione " +
//                    "from CFG_GATEWAY_PAGAMENTO cfg " +
//                    "join CASELLARIO_INFO cinfo on cfg.SYSTEM_ID = coalesce(cinfo.MITTENTE, '01030') " +
//                    "order by cfg.SYSTEM_ID ",
            
             query = "select distinct cfg.SYSTEM_ID as codice, "  +
            		"coalesce(cfg.SYSTEM_NAME, cfg.SYSTEM_ID)  as descrizione "  +
            		"from CFG_GATEWAY_PAGAMENTO cfg "  + 
            		"join CASELLARIO_INFO cinfo on cfg.SYSTEM_ID = cinfo.MITTENTE "  +
            		"order by cfg.SYSTEM_ID ",
            resultSetMapping = "getComboOptionResultMapping"
    ),
    @NamedNativeQuery(
            name = "getListMittentiPerRicevente",
             query = "select distinct cfg.SYSTEM_ID as codice, "  +
            		"coalesce(cfg.SYSTEM_NAME, cfg.SYSTEM_ID)  as descrizione "  +
            		"from CFG_GATEWAY_PAGAMENTO cfg "  + 
            		"join CASELLARIO_INFO cinfo on cfg.SYSTEM_ID = cinfo.MITTENTE "  +
            		"where cinfo.RICEVENTE = :ricevente " +
            		"order by cfg.SYSTEM_ID ",
            resultSetMapping = "getComboOptionResultMapping"
    ),
    @NamedNativeQuery(
            name = "getListRiceventi",
            query = "select concat(concat(lapl,'|'), coalesce(sia, '')) as codice, denom as descrizione " 
            		+ " from INTESTATARI inte, JLTENTI  enti "
            		+ " where enti.intestatario = inte.intestatario "
            		+ " AND enti.STATO='A' ",   
            resultSetMapping = "getComboOptionResultMapping"
    )
})

 

public class CasellarioInfo extends BaseIdEntity{
	private static final long serialVersionUID = 1L;

	private Timestamp dataElaborazione;
	private String descErrore;
	private Integer dimensione;
	private Integer flagElaborazione;
	private byte[] flussoCbi;
	private String nomeSupporto;
	private Integer numeroRecord;
	private String opAggiornamento;
	private String opInserimento;
	private Integer tipoErrore;
	private String tipoFlusso;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String mittente;
	private String ricevente;
	private String nomeFile;

	private Rendicontazioni rendicontazioni;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="casellario_info_pk_gen")	
	@SequenceGenerator(
		    name="casellario_info_pk_gen",
		    sequenceName="CASELLARIO_INFO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}		
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	public CasellarioInfo() {
	}

	@Column(name="MITTENTE")
	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	@Column(name="RICEVENTE")
	public String getRicevente() {
		return ricevente;
	}

	public void setRicevente(String ricevente) {
		this.ricevente = ricevente;
	}

	@Column(name="DATA_ELABORAZIONE")
	public Timestamp getDataElaborazione() {
		return this.dataElaborazione;
	}

	public void setDataElaborazione(Timestamp dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}
	@Column(name="DESC_ERRORE")
	public String getDescErrore() {
		return this.descErrore;
	}

	public void setDescErrore(String descErrore) {
		this.descErrore = descErrore;
	}


	public Integer getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}


	@Column(name="FLAG_ELABORAZIONE")
	public Integer getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(Integer flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}

	@Lob()
	@Column(name="FLUSSO_CBI")
	public byte[] getFlussoCbi() {
		return this.flussoCbi;
	}

	public void setFlussoCbi(byte[] flussoCbi) {
		this.flussoCbi = flussoCbi;
	}


	@Column(name="NOME_SUPPORTO")
	public String getNomeSupporto() {
		return this.nomeSupporto;
	}

	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}


	@Column(name="NUMERO_RECORD")
	public Integer getNumeroRecord() {
		return this.numeroRecord;
	}

	public void setNumeroRecord(Integer numeroRecord) {
		this.numeroRecord = numeroRecord;
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


	@Column(name="TIPO_ERRORE")
	public Integer getTipoErrore() {
		return this.tipoErrore;
	}

	public void setTipoErrore(Integer tipoErrore) {
		this.tipoErrore = tipoErrore;
	}


	@Column(name="TIPO_FLUSSO")
	public String getTipoFlusso() {
		return this.tipoFlusso;
	}

	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
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

	@Column(name="NOME_FILE")
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	//bi-directional one-to-one association to Rendicontazioni
	@OneToOne(cascade=CascadeType.ALL,mappedBy="casellarioInfo")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}
}