package it.tasgroup.iris.domain;

import it.nch.is.fo.profilo.Enti;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.helper.PaymentConditionStatusCalculator;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the JLTCOPD database table.
 *
 */
@Entity
@Table(name="JLTCOPD")
@SqlResultSetMappings( {
	  @SqlResultSetMapping(
	      name="condizioniHomePageMapping",
	      entities={@EntityResult(entityClass=CondizionePagamento.class)},
	      columns = {
	    		  	 	@ColumnResult(name="pend_id_pendenza"),
	    		  	 	@ColumnResult(name="pend_id_tributo_strutturato"),
	    		  	 	@ColumnResult(name="pend_de_causale"),
	    		  	 	@ColumnResult(name="id_documento"),
	    		  	 	@ColumnResult(name="pagamentoInErrore"),
	    		  	 	@ColumnResult(name="pagamentoInDelega"),
	    		  	 	@ColumnResult(name="enti_denom"),
	    		  	 	@ColumnResult(name="tributi_de_trb"),
	    		  	 	@ColumnResult(name="cartellaPagamento"),
	    		  	 	@ColumnResult(name="url_upd_service"),
	    		  	 	@ColumnResult(name="cfPaganteDDP"),
	    		  	 	@ColumnResult(name="intestatarioDDP"),
	    		  	 	@ColumnResult(name="pend_coriscossore"),
	    		  	 	@ColumnResult(name="pend_riferimento"),
	    		  	 	@ColumnResult(name="cdPlugin"),
	    		  	 	@ColumnResult(name="idPendenzaEnte"),
	    		  	 	@ColumnResult(name="note"),
	    		  	 	@ColumnResult(name="tsEmissioneEnte"),
	    		  	 	@ColumnResult(name="tsPrescrizione"),
	    		  	 	@ColumnResult(name="annoRiferimento")
	    		     }
	  )
	})
@NamedQueries(
{
@NamedQuery(
		name="getCondizioneMultaByPendenzaDeCausale",
		query="select condizione from CondizionePagamento condizione " +
				"where pendenza.deCausale like :causale and pendenza.stRiga = 'V' " +
				"and dtIniziovalidita <= :inizioValidita " +
				"and dtFinevalidita >= :fineValidita"),

		@NamedQuery(
				name="getCondizionePagamentoByEnteIdPagamento",
				query="select c from CondizionePagamento c JOIN c.pendenza.destinatari cde \n" +
						"              where cde.coDestinatario =:codiceFiscaleDebitore and  c.ente.codiceEnte =:codEnte \n" +
						"              and c.idPagamento =:idPagamento and c.stRiga = 'V' and c.tsAnnullamento is null\n"),
		@NamedQuery(
				name="getCondizionePagamentoByCodCreditoreIdPagamentoEnteImporto",
				query="select c from CondizionePagamento c JOIN c.ente ente where c.idPagamento=:idPagamentoEnte\n" +
						"                              and ente.intestatarioobj.lapl =:codFiscaleCreditore and c.imTotale =:importo and c.stRiga = 'V' and c.tsAnnullamento is null"),
		@NamedQuery(
				name="getCondizionePagamentoByCodCreditoreIdPagamento",
				query="select c from CondizionePagamento c JOIN c.ente ente where " +
						"                              ente.intestatarioobj.lapl =:codFiscaleCreditore and c.idPagamento =:idPagamento and c.stRiga = 'V' and c.tsAnnullamento is null")

})

// Spiegazione a parole di cosa fa la query spaziale qua sotto.
// Questa query seleziona tutte le condizioni di pagamento che sono in stato 'N' (non pagate) e che non hanno pagamenti associati eseguiti, in corso, stornati.
// Le condizioni devono appartenere a pendenze in stato 'A' o anche in stato 'H' solo se hanno però un documento associato.
// Infine una condizione così selezionata viene inclusa nel resultset se accadono una delle seguenti:
// 1. La condizione non pagata è un pagamento a 'RATE': viene inclusa se non è stato pagato una condizione singola per la stessa pendenza
// 2. La condizione non pagata è un pagamento 'SINGOLO': viene inclusa se non è stata pagata un' altra condizione per la stessa pendenza
// Nota. Per quest'ultima verifica le condizioni in pagamento irregolare "E" non sono considerate come pagate.. in modo da visualizzare le condizioni
// inserite per sanare la situazione.

// In caso di più condizioni pagabili per la stessa pendenza il post processing sul FacadeBean ne seleziona una.



//NamedNativeQuery "condizioniPerHomePage" rimossa da qui e messa come stringa in CondizioniPagamentoDaoImpl



public class CondizionePagamento extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String idCondizione;
	private String cdTrbEnte;
	private String coCip;
	private String deCanalepag;
	private Date dtFinevalidita;
	private Date dtIniziovalidita;
	private Timestamp dtPagamento;
	private Date dtScadenza;
	private String idPagamento;
	private BigDecimal imTotale;
	private String opAggiornamento;
	private String opAnnullamento;
	private String opInserimento;
	private int prVersione;
	private String stPagamento;
	private String stRiga;
	private String tiCip;
	private String tiPagamento;
	private Timestamp tsAggiornamento;
	private Timestamp tsAnnullamento;
	private Long tsAnnullamentoMillis=0L;
	private Timestamp tsDecorrenza;
	private Timestamp tsInserimento;
	private String causalePagamento;

	private String ibanBeneficiario;
	private String ragioneSocaleBeneficiario;

	private BigDecimal imPagamento;
	private String deNotePagamento;
	private String deMezzoPagamento;

	
	private Enti ente;
	private Pendenza pendenza;
	private Set<Pagamenti> pagamenti;
	private List<VocePagamento> vociPagamento;
	private Set<CondizioneDocumento> condizioniDocumento;
	private Set<AllegatiPendenza> allegatiPendenza;

	private IUVPosizEnteMap iuvPosizEnteMap;

	// La seguente proprietà transient serve per memorizzare lo "stato complessivo"
	// calcolato per la condizione di pagamento.
	// Questo valore è calcolato a partire da innumerevoli condizioni:
	//  a. Lo stato della condizione stessa
	//  b. Lo stato dei pagamenti associati
	//  c. Presenza e stato dei pagamenti associati.
	// E' comunque comodo inserire questo campo nell'oggetto Condizione.
	// Si popola con il valore: EnumStatoPagamentoCondizione
	// Per calcolarlo:..

	private EnumStatoPagamentoCondizione statoPagamentoCalcolato;
	private String idDistintaInCorso;

	// proprietà transient per memorizzare i debitori della pendenza
	private List<String> debitori;

	@Transient
	public void updateStatoPagamentoCalcolato() {

		statoPagamentoCalcolato = PaymentConditionStatusCalculator.calculateStatus(this);
	}

	@Transient
	public EnumStatoPagamentoCondizione getStatoPagamentoCalcolato() {

		return statoPagamentoCalcolato;
	}

	public void setStatoPagamentoCondizione(EnumStatoPagamentoCondizione statoPagamentoCondizione) {
		this.statoPagamentoCalcolato = statoPagamentoCondizione;
	}

	/**
	 * L'id della distinta legata al pagamento che ha fatto calcolare come "IN CORSO" lo statoPagamentoCalcolato.
	 * N.B. è diversa da null solo se lo statoPagamentoCalcolato è "IN CORSO"
	 */
	@Transient
	public String getIdDistintaInCorso() {
		return idDistintaInCorso;
	}

	public void setIdDistintaInCorso(String idDistintaInCorso) {
		this.idDistintaInCorso = idDistintaInCorso;
	}

	@Transient
	public List<String> getDebitori() {
		return debitori;
	}

	public void setDebitori(List<String> debitori) {
		this.debitori = debitori;
	}

	@Id
	@Column(name="ID_CONDIZIONE", unique=true, nullable=false, length=40)
	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}


	@Column(name="CD_TRB_ENTE", nullable=false, length=100)
	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}


	@Column(name="CO_CIP", length=512)
	public String getCoCip() {
		return this.coCip;
	}

	public void setCoCip(String coCip) {
		this.coCip = coCip;
	}


	@Column(name="DE_CANALEPAG", length=280)
	public String getDeCanalepag() {
		return this.deCanalepag;
	}

	public void setDeCanalepag(String deCanalepag) {
		this.deCanalepag = deCanalepag;
	}


    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINEVALIDITA", nullable=false)
	public Date getDtFinevalidita() {
		return this.dtFinevalidita;
	}

	public void setDtFinevalidita(Date dtFinevalidita) {
		this.dtFinevalidita = dtFinevalidita;
	}


    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIOVALIDITA")
	public Date getDtIniziovalidita() {
		return this.dtIniziovalidita;
	}

	public void setDtIniziovalidita(Date dtIniziovalidita) {
		this.dtIniziovalidita = dtIniziovalidita;
	}


	@Column(name="DT_PAGAMENTO")
	public Timestamp getDtPagamento() {
		return this.dtPagamento;
	}

	public void setDtPagamento(Timestamp dtPagamento) {
		this.dtPagamento = dtPagamento;
	}


    @Temporal( TemporalType.DATE)
	@Column(name="DT_SCADENZA", nullable=false)
	public Date getDtScadenza() {
		return this.dtScadenza;
	}

	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}


	@Column(name="ID_PAGAMENTO", nullable=false, length=70)
	public String getIdPagamento() {
		return this.idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}


	@Column(name="IM_TOTALE", nullable=false, precision=15, scale=2)
	public BigDecimal getImTotale() {
		return this.imTotale;
	}

	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}


	@Column(name="OP_AGGIORNAMENTO", length=80)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_ANNULLAMENTO", length=80)
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}


	@Column(name="OP_INSERIMENTO", nullable=false, length=80)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="PR_VERSIONE", nullable=false)
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}


	@Column(name="ST_PAGAMENTO", nullable=false, length=2)
	public String getStPagamento() {
		return this.stPagamento;
	}

	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}


	@Column(name="ST_RIGA", nullable=false, length=2)
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(name="TI_CIP", length=20)
	public String getTiCip() {
		return this.tiCip;
	}

	public void setTiCip(String tiCip) {
		this.tiCip = tiCip;
	}


	@Column(name="TI_PAGAMENTO", nullable=false, length=2)
	public String getTiPagamento() {
		return this.tiPagamento;
	}

	public void setTiPagamento(String tiPagamento) {
		this.tiPagamento = tiPagamento;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
		//Mantiene allineato tsAnnullamentoMillis
		if (this.tsAnnullamento!=null) {
			this.tsAnnullamentoMillis=this.tsAnnullamento.getTime();
		} else {
			this.tsAnnullamentoMillis=0L;
		}

	}


	@Column(name="TS_DECORRENZA", nullable=false)
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}


	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	//uni-directional many-to-one association to Ente
    @ManyToOne
	@JoinColumn(name="ID_ENTE", nullable=false)
	public Enti getEnte() {
		return this.ente;
	}

	public void setEnte(Enti ente) {
		this.ente = ente;
	}
    // join con tabella iuvmap... id_pagamento di tabella condizioni = iuv della tabella iuvmap
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumns({
		@JoinColumn(name="ID_ENTE", referencedColumnName="ID_ENTE",insertable=false, updatable=false),
		@JoinColumn(name="ID_PAGAMENTO", referencedColumnName="IUV",insertable=false, updatable=false)
	}) 	
	public IUVPosizEnteMap getIuvPosizEnteMap() {
		return iuvPosizEnteMap;
	}

	public void setIuvPosizEnteMap(IUVPosizEnteMap iuvPosizEnteMap) {
		this.iuvPosizEnteMap = iuvPosizEnteMap;
	}

	//bi-directional many-to-one association to Pendenza
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PENDENZA", nullable=false)
	public Pendenza getPendenza() {
		return this.pendenza;
	}

	public void setPendenza(Pendenza pendenza) {
		this.pendenza = pendenza;
	}

	@Column(name="CAUSALE_PAGAMENTO")
	public String getCausalePagamento() {
		return causalePagamento;
	}

	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}

	@Column(name="IBAN_BENEFICIARIO")
	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}

	@Column(name="RAGIONE_SOCIALE_BENEFICIARIO")
	public String getRagioneSocaleBeneficiario() {
		return ragioneSocaleBeneficiario;
	}

	public void setRagioneSocaleBeneficiario(String ragioneSocaleBeneficiario) {
		this.ragioneSocaleBeneficiario = ragioneSocaleBeneficiario;
	}

	@Column(name="IM_PAGAMENTO")
	public BigDecimal getImPagamento() {
		return imPagamento;
	}

	public void setImPagamento(BigDecimal imPagamento) {
		this.imPagamento = imPagamento;
	}

	@Column(name="DE_NOTEPAGAMENTO")
	public String getDeNotePagamento() {
		return deNotePagamento;
	}

	public void setDeNotePagamento(String deNotePagamento) {
		this.deNotePagamento = deNotePagamento;
	}

	@Column(name="DE_MEZZOPAGAMENTO")
	public String getDeMezzoPagamento() {
		return deMezzoPagamento;
	}

	public void setDeMezzoPagamento(String deMezzoPagamento) {
		this.deMezzoPagamento = deMezzoPagamento;
	}


	@OneToMany(mappedBy="condizioniPagamento", cascade={CascadeType.PERSIST})
	public Set<AllegatiPendenza> getAllegatiPendenza() {
		return this.allegatiPendenza;
	}

	public void setAllegatiPendenza(Set<AllegatiPendenza> allegatiPendenza) {
		this.allegatiPendenza = allegatiPendenza;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCondizione == null) ? 0 : idCondizione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		CondizionePagamento other = (CondizionePagamento) obj;

		if (idCondizione == null) {

			if (other.idCondizione != null)

				return false;

		} else if (!idCondizione.equals(other.idCondizione))

			return false;

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondizionePagamento [idCondizione=");
		builder.append(idCondizione);
		builder.append(", cdTrbEnte=");
		builder.append(cdTrbEnte);
		builder.append(", coCip=");
		builder.append(coCip);
		builder.append(", deCanalepag=");
		builder.append(deCanalepag);
		builder.append(", dtFinevalidita=");
		builder.append(dtFinevalidita);
		builder.append(", dtIniziovalidita=");
		builder.append(dtIniziovalidita);
		builder.append(", dtPagamento=");
		builder.append(dtPagamento);
		builder.append(", dtScadenza=");
		builder.append(dtScadenza);
		builder.append(", idPagamento=");
		builder.append(idPagamento);
		builder.append(", idPendenza=");
		builder.append(", imTotale=");
		builder.append(imTotale);
		builder.append(", opAggiornamento=");
		builder.append(opAggiornamento);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", stPagamento=");
		builder.append(stPagamento);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tiCip=");
		builder.append(tiCip);
		builder.append(", tiPagamento=");
		builder.append(tiPagamento);
		builder.append(", tsAggiornamento=");
		builder.append(tsAggiornamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append(", condizioniDocumento=");
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}


	@OneToMany(mappedBy="condPagamento", fetch=FetchType.LAZY)
	@OrderBy("tsInserimento ASC")
	public Set<Pagamenti> getPagamenti() {
		return pagamenti;
	}

	public void setPagamenti(Set<Pagamenti> pagamenti) {
		this.pagamenti = pagamenti;
	}

	@OneToMany(mappedBy="idCondizione", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
	@OrderBy(value="idVoce")
	public List<VocePagamento> getVociPagamento() {
		return vociPagamento;
	}

	public void setVociPagamento(List<VocePagamento> vociPagamento) {
		this.vociPagamento = vociPagamento;
	}

	@OneToMany(mappedBy="condizionePagamento",fetch=FetchType.LAZY)
	public Set<CondizioneDocumento> getCondizioniDocumento() {
		return condizioniDocumento;
	}

	public void setCondizioniDocumento(Set<CondizioneDocumento> condizioniDocumento) {
		this.condizioniDocumento = condizioniDocumento;
	}



	@Transient
	public boolean isDaPagare(){

		return getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.DA_PAGARE);

	}

	@Transient
	public boolean isDaPagareOInCorso(){

		return (getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.DA_PAGARE)||getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.IN_CORSO));

	}

	@Transient
	public boolean isDetailRequired(){

		return (getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.DA_PAGARE)||getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.NON_PAGABILE_ASSOCIATA_DOCUMENTO) || getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.NON_PAGABILE_TERMINI));

	}

	@Transient
	public boolean isPagataIrregolarmente() {
		return "E".equals(getStPagamento()) ;
	}

	@Transient
	public boolean isCBILLIdsCompatible(){

		boolean isCBILLCompatible = getIdPagamento().length()<=36 && getPendenza().getIdPendenzaente().length()<=18;

		return isCBILLCompatible;
	}

	/**
	 * Metodi e attributo da DTO
	 * services-ws per retaggio storico non usa un DTO per far viaggiare le condizioni di pagamento
	 * ed ora non c'� tempo di crearlo (occorrerebbe un refactoring massiccio)
	 * TODO PAZZIK RIFATTORIZZARE
	 *
	 * @return
	 */

	@Transient
	private String codPagamentoFlusso;

	@Transient
	public String getCodPagamentoFlusso() {
		return codPagamentoFlusso;
	}

	@Transient
	public void setCodPagamentoFlusso(String codPagamentoFlusso) {
		this.codPagamentoFlusso = codPagamentoFlusso;
	}

	@Transient
	private String idPagamentoFlusso;

	@Transient
	public String getIdPagamentoFlusso() {
		return idPagamentoFlusso;
	}

	@Transient
	public void setIdPagamentoFlusso(String idPagamentoFlusso) {
		this.idPagamentoFlusso = idPagamentoFlusso;
	}

	@Transient
	private String codPaganteFlusso;

	@Transient
	public String getCodPaganteFlusso() {
		return codPaganteFlusso;
	}

	@Transient
	public void setCodPaganteFlusso(String codPaganteFlusso) {
		this.codPaganteFlusso = codPaganteFlusso;
	}

	@Column(name="TS_ANNULLAMENTO_MILLIS")
	public Long getTsAnnullamentoMillis() {
		return tsAnnullamentoMillis;
	}

	@Deprecated //usare setTsAnnullamento();
	public void setTsAnnullamentoMillis(Long tsAnnullamentoMillis) {
		this.tsAnnullamentoMillis = tsAnnullamentoMillis;
	}

	public static class DtScadenzaComparator implements Comparator<CondizionePagamento>{

		@Override
		public int compare(CondizionePagamento cond0, CondizionePagamento cond1) {

			return cond0.dtScadenza.compareTo(cond1.dtScadenza);
		}

	}

}
