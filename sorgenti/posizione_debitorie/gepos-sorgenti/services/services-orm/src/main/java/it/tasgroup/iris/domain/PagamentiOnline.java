package it.tasgroup.iris.domain;

import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;


/**
 * The persistent class for the JLTPAGA database table.
 *
 */
@Entity
@Table(name="PAGAMENTI_ONLINE")
@NamedQueries({
    @NamedQuery(
            name="getPagamentoOnLineByCodAutorizzazioneAndMaxTsOperazione",
            query="select pol from PagamentiOnline pol " +
                    "where codAutorizzazione = :codiceAutorizzazione " +
                    "and tsOperazione = (select max(tsOperazione) from PagamentiOnline where codAutorizzazione = :codiceAutorizzazione)")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="readSystemIdsList",
            query = "select distinct SYSTEM_ID as value, SYSTEM_ID as label " +
                    "from PAGAMENTI_ONLINE " +
                    "order by SYSTEM_ID",
            resultSetMapping = "comboOptionResultMapping"
    ),
    @NamedNativeQuery(name="readApplicationIdsList",
            query = "select distinct APPLICATION_ID as value, APPLICATION_ID as label " +
                    "from PAGAMENTI_ONLINE " +
                    "order by APPLICATION_ID",
            resultSetMapping = "comboOptionResultMapping"
    ),
    @NamedNativeQuery(name="readTiOperationsList",
            query = "select distinct TI_OPERAZIONE as value, TI_OPERAZIONE as label " +
                    "from PAGAMENTI_ONLINE " +
                    "order by TI_OPERAZIONE",
            resultSetMapping = "comboOptionResultMapping"
    )
})
@SqlResultSetMappings( {
    @SqlResultSetMapping(
            name="comboOptionResultMapping",
            columns = {
                @ColumnResult(name="value"),
                @ColumnResult(name="label")
            }
    ),
    @SqlResultSetMapping(
            name="listaOperazioniPerEsitoResultMapping",
            columns = {
                @ColumnResult(name="esito"),
                @ColumnResult(name="cod_errore"),
                @ColumnResult(name="numero_operazioni")      
            }
    )
})
public class PagamentiOnline extends BaseIdEntity {
    
    /**
     *
     */
    private static final long serialVersionUID = 2063274728858542608L;
    
    private Timestamp tsOperazione;
    private Timestamp tsInserimento;
    private Timestamp tsAggiornamento;
    private String tiOperazione;
    private String sessionIdToken;
    private String sessionIdTimbro;
    private String sessionIdTerminale;
    private String sessionIdSistema;
    private String opInserimento;
    private String opAggiornamento;
    private String numOperazione;
    private String idOperazione;
    private String deOperazione;
    private String codAutorizzazione;
    private String applicationId;
    private String systemId;
    private String esito = EnumBusinessReturnCodes.OK.getDescrizione();
    private String codErrore = EnumBusinessReturnCodes.OK.getChiave();
    
    private GestioneFlussi flussoDistintaOnline;
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="pagamenti_online_pk_gen")	
	@SequenceGenerator(
		    name="pagamenti_online_pk_gen",
		    sequenceName="PAGAMENTI_ONLINE_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		    
    
    @Column(name="TS_OPERAZIONE")
    public Timestamp getTsOperazione() {
        return tsOperazione;
    }
    
    public void setTsOperazione(Timestamp tsOperazione) {
        this.tsOperazione = tsOperazione;
    }
    
    @Column(name="TS_INSERIMENTO")
    public Timestamp getTsInserimento() {
        return tsInserimento;
    }
    
    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }
    
    @Column(name="TS_AGGIORNAMENTO")
    public Timestamp getTsAggiornamento() {
        return tsAggiornamento;
    }
    
    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }
    
    @Column(name="TI_OPERAZIONE")
    public String getTiOperazione() {
        return tiOperazione;
    }
    
    public void setTiOperazione(String tiOperazione) {
        this.tiOperazione = tiOperazione;
    }
    
    @Column(name="SESSION_ID_TOKEN")
    public String getSessionIdToken() {
        return sessionIdToken;
    }
    
    public void setSessionIdToken(String sessionIdToken) {
        this.sessionIdToken = sessionIdToken;
    }
    @Column(name="SESSION_ID_TIMBRO")
    public String getSessionIdTimbro() {
        return sessionIdTimbro;
    }
    
    public void setSessionIdTimbro(String sessionIdTimbro) {
        this.sessionIdTimbro = sessionIdTimbro;
    }
    @Column(name="SESSION_ID_TERMINALE")
    public String getSessionIdTerminale() {
        return sessionIdTerminale;
    }
    
    public void setSessionIdTerminale(String sessionIdTerminale) {
        this.sessionIdTerminale = sessionIdTerminale;
    }
    @Column(name="SESSION_ID_SISTEMA")
    public String getSessionIdSistema() {
        return sessionIdSistema;
    }
    
    public void setSessionIdSistema(String sessionIdSistema) {
        this.sessionIdSistema = sessionIdSistema;
    }
    @Column(name="OP_INSERIMENTO")
    public String getOpInserimento() {
        return opInserimento;
    }
    
    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }
    @Column(name="OP_AGGIORNAMENTO")
    public String getOpAggiornamento() {
        return opAggiornamento;
    }
    
    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }
    @Column(name="NUM_OPERAZIONE")
    public String getNumOperazione() {
        return numOperazione;
    }
    
    public void setNumOperazione(String numOperazione) {
        this.numOperazione = numOperazione;
    }
    @Column(name="ID_OPERAZIONE")
    public String getIdOperazione() {
        return idOperazione;
    }
    
    public void setIdOperazione(String idOperazione) {
        this.idOperazione = idOperazione;
    }
    
    @Column(name="DE_OPERAZIONE")
    public String getDeOperazione() {
        return deOperazione;
    }
    
    public void setDeOperazione(String deOperazione) {
        this.deOperazione = deOperazione;
    }
    
    @Column(name="COD_AUTORIZZAZIONE")
    public String getCodAutorizzazione() {
        return codAutorizzazione;
    }
    
    public void setCodAutorizzazione(String codAutorizzazione) {
        this.codAutorizzazione = codAutorizzazione;
    }
    
    @Column(name="APPLICATION_ID")
    public String getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    
    @Column(name="SYSTEM_ID")
    public String getSystemId() {
        return systemId;
    }
    
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    @Column(name="ESITO")
    public String getEsito() {
        return esito;
    }
    
    public void setEsito(String esito) {
        this.esito = esito;
    }
    
    @Column(name="COD_ERRORE")
    public String getCodErrore() {
        return codErrore;
    }
    
    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }
    
    
    @ManyToOne(targetEntity=GestioneFlussi.class,fetch=FetchType.LAZY)
    @JoinColumn(name="ID_DISTINTE_PAGAMENTO")
    public GestioneFlussi getFlussoDistintaOnline() {
        return this.flussoDistintaOnline;
    }
    
    public void setFlussoDistintaOnline(GestioneFlussi flussoDistintaOnline) {
        this.flussoDistintaOnline = flussoDistintaOnline;
    }
    
    @Override
    public String toString() {
        return "PagamentiOnline [tsOperazione=" + tsOperazione
                + ", tsInserimento=" + tsInserimento + ", tsAggiornamento="
                + tsAggiornamento + ", tiOperazione=" + tiOperazione
                + ", systemId=" + systemId
                + ", applicationId=" + applicationId
                + ", sessionIdToken=" + sessionIdToken
                + ", sessionIdTimbro=" + sessionIdTimbro
                + ", sessionIdTerminale=" + sessionIdTerminale
                + ", sessionIdSistema=" + sessionIdSistema + ", opInserimento="
                + opInserimento + ", opAggiornamento=" + opAggiornamento
                + ", numOperazione=" + numOperazione + ", idOperazione="
                + idOperazione +  ", deOperazione=" + deOperazione
                + ", codAutorizzazione=" + codAutorizzazione
                + ", esito=" + esito
                + ", codErrore=" + codErrore;
    }
    
    public static class MaxDataComparator implements Comparator<PagamentiOnline> {
        
        @Override
        public int compare(PagamentiOnline o1, PagamentiOnline o2) {
            return ((PagamentiOnline) o1).getTsOperazione().compareTo(((PagamentiOnline) o2).getTsOperazione());
        }
    }
    
}