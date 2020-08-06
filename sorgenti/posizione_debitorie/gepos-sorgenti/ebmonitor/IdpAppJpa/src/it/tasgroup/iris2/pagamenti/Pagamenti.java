package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PAGAMENTI database table.
 *
 */
@Entity
@Table(name="PAGAMENTI")
@NamedQueries(
{
@NamedQuery(
		name="updFlagPagaByCondizioneAndStato",
		query="update Pagamenti " +
					" set flagIncasso = :flagIncasso, " +
					" dataAccreditoEnte = :dataAccreditoEnte, " +								
					" tsAggiornamento = :tsUpd, " +
					" opAggiornamento = :opUpd  " +									
					" where idCondizione = :idCondizione " +
					"and stPagamento = :stPagamento"),
@NamedQuery(
		name="updFlagPagaPlusByCondizioneAndStato",
		query="update Pagamenti " +
				" set flagIncasso = :flagIncasso, " +
				" dataAccreditoEnte = :dataAccreditoEnte, " +						
				" dataRegolamento = :dataRegolamento, " +						
				" trn = :trn, " +						
				" dataEsecuzione = :dataEsecuzione, " +						
				" identificativoFlusso = :identificativoFlusso, " +								
				" codRendicontazioneIncasso = :codRendicontazioneIncasso, " +								
				" mittenteRendicontazioneIncasso = :mittenteRendicontazioneIncasso, " +								
				" totaleTransazioneIncasso = :totaleTransazioneIncasso, " +								
				" bicBancaRiversamento = :bicBancaRiversamento, " +								
				" tsAggiornamento = :tsUpd, " +
				" opAggiornamento = :opUpd  " +									
				" where idCondizione = :idCondizione " +
		"and stPagamento = :stPagamento"),
@NamedQuery(
		name="updFlagPagaByDistintaAndFlag",
		query="update Pagamenti set "
					+ " flagIncasso = :flagIncasso " +
					" ,	dataAccreditoEnte = :dataAccreditoEnte " +							
					" , tsAggiornamento = :tsAggiornamento " +
					" , opAggiornamento = :opAggiornamento "  								
					+ " where "
					+ " distintePagamento = :distintePagamento "
					+ " and flagIncasso = :flagIncassoOrig"
				),
@NamedQuery(
		name="updFlagIncassoByDistinta",
		query="update Pagamenti set "
					+ "  flagIncasso = :flagIncasso " 
					+ ", tsAggiornamento = :tsAggiornamento "
					+ ", opAggiornamento = :opAggiornamento "  	
					+ "where "
					+ "  distintePagamento = :distintePagamento "
				),
@NamedQuery(
		name="updDatiIncassoByDistinta",
		query="update Pagamenti set "
					+ "  flagIncasso = :flagIncasso "
				    + ", dataAccreditoEnte = :dataAccreditoEnte"
					+ ", dataRegolamento = :dataRegolamento"
					+ ", identificativoFlusso = :identificativoFlusso"
					+ ", trn = :trn"
					+ ", bicBancaRiversamento = :bicBancaRiversamento"
					+ ", dataEsecuzione = :dataEsecuzione"
					+ ", codRendicontazioneIncasso = :codRendicontazioneIncasso"
					+ ", mittenteRendicontazioneIncasso = :mittenteRendicontazioneIncasso"
					+ ", totaleTransazioneIncasso = :totaleTransazioneIncasso "
					+ ", tsAggiornamento = :tsAggiornamento "
					+ ", opAggiornamento = :opAggiornamento "  	
					+ "where "
					+ "  distintePagamento = :distintePagamento "
				),				
@NamedQuery(
		name="findPagamentoByIdCondizioneAndStato",
		query="SELECT pagamenti FROM Pagamenti pagamenti " +
				" WHERE pagamenti.idCondizione = :idCondizione "
				+ "AND (pagamenti.stPagamento = :eseguito or pagamenti.stPagamento = :eseguitoSbf)"
				),
@NamedQuery(
		name="findPagamentoByIdCondizioneAndListaStati",
		query="SELECT pagamenti FROM Pagamenti pagamenti " +
				" WHERE pagamenti.idCondizione = :idCondizione "
				+ "AND pagamenti.stPagamento in (:listaStati)"),
@NamedQuery(
		name="findPagamentoByMittenteAndData",
		query="SELECT pagamenti FROM Pagamenti pagamenti " +
				" WHERE pagamenti.idCondizione = :idCondizione "
				+ "AND (pagamenti.stPagamento = :eseguito or pagamenti.stPagamento = :eseguitoSbf)"
				),
@NamedQuery(
		name="FindPagamentiNonRendicontati", 
		query=
			"SELECT pagamenti "
			+ " FROM Pagamenti pagamenti 	"
			+ " 	 join fetch pagamenti.distintePagamento  " 
			+ " WHERE "
			+ " pagamenti.flagIncasso = :flagIncasso"
			+ " AND pagamenti.stPagamento = 'ES'"
			+ "	AND pagamenti.tsDecorrenza <= :dataCreazioneEnd "
			+ " AND pagamenti.distintePagamento.idCfgGatewayPagamento=:idCfgGatewayPagamento"),					
@NamedQuery(
		name="updateDocExtIdPagamentoByNotifiche",
		query="UPDATE Pagamenti  SET idDocumentoExt = :idDocumentoExt  where id IN ("+ 
		      " SELECT idPagamento from NotifichePagamenti where e2emsgid = :e2emsgid and receiverid = :receiverid and receiversys =:receiversys ) "

				),
@NamedQuery(
		name="findPagamentoByDistinta",
		query="SELECT pagamenti FROM Pagamenti pagamenti WHERE pagamenti.distintePagamento = :distintePagamento"
			)
})
public class Pagamenti extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String cdTrbEnte;
	private String coPagante;
	private String flagIncasso;
	private String distinta;
	private Date dtScadenza;
	private String idCondizione;
	private String idEnte;	
	private String idPendenza;
	private String idPendenzaente;
	private String idTributo;
	private BigDecimal imPagato;
	private String stPagamento;
	private String stRiga;
	private short  flagOpposizione730;
	private String statoNotifica;
	private String tiDebito;
	private String tiPagamento;
	private String tipospontaneo;
	private String bicBancaRiversamento;
	private Timestamp tsDecorrenza;
	private Timestamp tsOrdine;
	
	private Timestamp notificaEseguito;
	private Timestamp notificaRegolato;
	private Timestamp notificaIncasso;
	
	@Transient
    public static String DOC_NOT_AVAILABLE= "#ND";
    
	//nuovi campi
	private String notePagamento;
	private String idRiscossionePsp;
	private Timestamp tsStorno;
	
	//date per riaccrediti 
	private Date dataAccreditoContoTecnico;
	private Date dataAccreditoEnte;
	
	//campi necessari alla rendicontazione
	private Date dataRegolamento; 
	private String identificativoFlusso;
	private String trn;
	private Date   dataEsecuzione;
	private String codRendicontazioneIncasso;
	private String mittenteRendicontazioneIncasso;
	private BigDecimal totaleTransazioneIncasso;
	
	private String idDocumentoExt;
	/*** Persistent Associations ***/
	private DistintePagamento distintePagamento;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="pagamenti_pk_gen")	
	@SequenceGenerator(
	    name="pagamenti_pk_gen",
	    sequenceName="PAGAMENTI_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	
 
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}


	@Column(name="CO_PAGANTE")
	public String getCoPagante() {
		return this.coPagante;
	}

	public void setCoPagante(String coPagante) {
		this.coPagante = coPagante;
	}


	public String getDistinta() {
		return this.distinta;
	}

	public void setDistinta(String distinta) {
		this.distinta = distinta;
	}


    @Temporal(TemporalType.DATE)
	@Column(name="DT_SCADENZA")
	public Date getDtScadenza() {
		return this.dtScadenza;
	}

	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}


	@Column(name="ID_CONDIZIONE")
	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}


	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}


	@Column(name="ID_PENDENZA")
	public String getIdPendenza() {
		return this.idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}


	@Column(name="ID_PENDENZAENTE")
	public String getIdPendenzaente() {
		return this.idPendenzaente;
	}

	public void setIdPendenzaente(String idPendenzaente) {
		this.idPendenzaente = idPendenzaente;
	}


	@Column(name="ID_TRIBUTO")
	public String getIdTributo() {
		return this.idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}


	@Column(name="IM_PAGATO")
	public BigDecimal getImPagato() {
		return this.imPagato;
	}

	public void setImPagato(BigDecimal imPagato) {
		this.imPagato = imPagato;
	}


	@Column(name="ST_PAGAMENTO")
	public String getStPagamento() {
		return this.stPagamento;
	}

	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}


	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(name="STATO_NOTIFICA")
	public String getStatoNotifica() {
		return this.statoNotifica;
	}

	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}


	@Column(name="TI_DEBITO")
	public String getTiDebito() {
		return this.tiDebito;
	}

	public void setTiDebito(String tiDebito) {
		this.tiDebito = tiDebito;
	}


	@Column(name="TI_PAGAMENTO")
	public String getTiPagamento() {
		return this.tiPagamento;
	}

	public void setTiPagamento(String tiPagamento) {
		this.tiPagamento = tiPagamento;
	}


	public String getTipospontaneo() {
		return this.tipospontaneo;
	}

	public void setTipospontaneo(String tipospontaneo) {
		this.tipospontaneo = tipospontaneo;
	}


	@Column(name="TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}


	@Column(name="TS_ORDINE")
	public Timestamp getTsOrdine() {
		return this.tsOrdine;
	}

	public void setTsOrdine(Timestamp tsOrdine) {
		this.tsOrdine = tsOrdine;
	}

	
	@Column(name="NOTIFICA_ESEGUITO")
	public Timestamp getNotificaEseguito() {
		return this.notificaEseguito;
	}

	public void setNotificaEseguito(Timestamp TsnotificaEseguito) {
		this.notificaEseguito = TsnotificaEseguito;
	}
	

	@Column(name="NOTIFICA_REGOLATO")
	public Timestamp getNotificaRegolato() {
		return this.notificaRegolato;
	}

	public void setNotificaRegolato(Timestamp TsnotificaRegolato) {
		this.notificaRegolato = TsnotificaRegolato;
	}
	
	@Column(name="NOTIFICA_INCASSO")
	public Timestamp getNotificaIncasso() {
		return this.notificaIncasso;
	}

	public void setNotificaIncasso(Timestamp TsnotificaIncasso) {
		this.notificaIncasso = TsnotificaIncasso;
	}
	
	
	
	
	//bi-directional many-to-one association to DistintePagamento
    @ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO")
	public DistintePagamento getDistintePagamento() {
		return this.distintePagamento;
	}

	public void setDistintePagamento(DistintePagamento distintePagamento) {
		this.distintePagamento = distintePagamento;
	}


	@Column(name="FLAG_INCASSO")
	public String getFlagIncasso() {
		return this.flagIncasso;
	}

	public void setFlagIncasso(String flagIncasso) {
		this.flagIncasso = flagIncasso;
	}

	@Column(name="NOTE_PAGAMENTO")
	public String getNotePagamento() {
		return notePagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	@Column(name="ID_RISCOSSIONE_PSP")
	public String getIdRiscossionePsp() {
		return idRiscossionePsp;
	}

	public void setIdRiscossionePsp(String idRiscossionePsp) {
		this.idRiscossionePsp = idRiscossionePsp;
	}

	@Column(name="TS_STORNO")
	public Timestamp getTsStorno() {
		return tsStorno;
	}

	public void setTsStorno(Timestamp tsStorno) {
		this.tsStorno = tsStorno;
	}
	
	
	@Column(name="DATA_ACCREDITO_CONTOTECNICO")	    
	public Date getDataAccreditoContoTecnico() {
		return dataAccreditoContoTecnico;
	}

	public void setDataAccreditoContoTecnico(Date dataAccreditoContoTecnico) {
		this.dataAccreditoContoTecnico = dataAccreditoContoTecnico;
	}

	@Column(name="DATA_ACCREDITO_ENTE")	    	
	public Date getDataAccreditoEnte() {
		return dataAccreditoEnte;
	}

	public void setDataAccreditoEnte(Date dataAccreditoEnte) {
		this.dataAccreditoEnte = dataAccreditoEnte;
	}
    
	@Column(name="DATA_REGOLAMENTO")
	public Date getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Date dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}

	@Column(name="IDENTIFICATIVO_FLUSSO")
	public String getIdentificativoFlusso() {
		return identificativoFlusso;
	}

	public void setIdentificativoFlusso(String identificativoFlusso) {
		this.identificativoFlusso = identificativoFlusso;
	}

	@Column(name="TRN")
	public String getTrn() {
		return trn;
	}

	public void setTrn(String trn) {
		this.trn = trn;
	}
 
	@Column(name="DATA_ESECUZIONE")
	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}
 
	@Column(name="COD_RENDICONTAZIONE_INCASSO")
	public String getCodRendicontazioneIncasso() {
		return codRendicontazioneIncasso;
	}

	public void setCodRendicontazioneIncasso(String codRendicontazioneIncasso) {
		this.codRendicontazioneIncasso = codRendicontazioneIncasso;
	}

	@Column(name="MITT_RENDICONTAZIONE_INCASSO")
	public String getMittenteRendicontazioneIncasso() {
		return mittenteRendicontazioneIncasso;
	}

	public void setMittenteRendicontazioneIncasso(
			String mittenteRendicontazioneIncasso) {
		this.mittenteRendicontazioneIncasso = mittenteRendicontazioneIncasso;
	}
 
	@Column(name="TOTALE_TRANSAZIONE_INCASSO")
	public BigDecimal getTotaleTransazioneIncasso() {
		return totaleTransazioneIncasso;
	}

	public void setTotaleTransazioneIncasso(BigDecimal totaleTransazioneIncasso) {
		this.totaleTransazioneIncasso = totaleTransazioneIncasso;
	}

	@Column(name="ID_DOCUMENTO_EXT")
	public String getIdDocumentoExt() {
		return idDocumentoExt;
	}

	public void setIdDocumentoExt(String idDocumentoExt) {
		this.idDocumentoExt = idDocumentoExt;
	}
	
	@Column(name="FLAG_OPPOSIZIONE_730")
	public short getFlagOpposizione730() {
		return flagOpposizione730;
	}

	public void setFlagOpposizione730(short flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}
    
	@Column(name="BIC_BANCA_RIVERSAMENTO")
	public String getBicBancaRiversamento() {
		return bicBancaRiversamento;
	}

	public void setBicBancaRiversamento(String bicBancaRiversamento) {
		this.bicBancaRiversamento = bicBancaRiversamento;
	}
	
	@Transient
	public boolean isAssociatedDocumentAvailable() {
		if (idDocumentoExt==null || !idDocumentoExt.equals(DOC_NOT_AVAILABLE)) {
		   return true;
	    }else {
		   return false;
	    }
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pagamenti [cdTrbEnte=");
		builder.append(cdTrbEnte);
		builder.append(", coPagante=");
		builder.append(coPagante);
		builder.append(", flagIncasso=");
		builder.append(flagIncasso);
		builder.append(", distinta=");
		builder.append(distinta);
		builder.append(", dtScadenza=");
		builder.append(dtScadenza);
		builder.append(", idCondizione=");
		builder.append(idCondizione);
		builder.append(", idEnte=");
		builder.append(idEnte);
		builder.append(", idPendenza=");
		builder.append(idPendenza);
		builder.append(", idPendenzaente=");
		builder.append(idPendenzaente);
		builder.append(", idTributo=");
		builder.append(idTributo);
		builder.append(", imPagato=");
		builder.append(imPagato);
		builder.append(", stPagamento=");
		builder.append(stPagamento);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", statoNotifica=");
		builder.append(statoNotifica);
		builder.append(", tiDebito=");
		builder.append(tiDebito);
		builder.append(", tiPagamento=");
		builder.append(tiPagamento);
		builder.append(", tipospontaneo=");
		builder.append(tipospontaneo);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append(", tsOrdine=");
		builder.append(tsOrdine);
		//builder.append(", distintePagamento=");
		//builder.append(distintePagamento);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}

	

	
		

	

}