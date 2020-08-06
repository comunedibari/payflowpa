package it.tasgroup.idp.domain.messaggi;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the NOTIFICHE_PAGAMENTI database table.
 *
 */

@Entity
@Table(name="NOTIFICHE_PAGAMENTI")

@NamedQueries(
{
@NamedQuery(
		name="notPagamByE2emsgIdReceiverIdReceiverSys",
		query="SELECT n from NotifichePagamenti n where n.e2emsgid = :e2emsgid and n.receiverid = :receiverid and n.receiversys =:receiversys ")
}
)
public class NotifichePagamenti extends BaseEntity implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String idNotifica;
	private String cdEnte;
	private String cdTrbEnte;
	
	
	private String coPagante;
	
	private Date dtScadenza;
	private String e2emsgid;
	private String idEnte;
	
	private String idPagamento;
	private String idPagamentoEnte;
	private String idPendenza;
	private String idPendenzaente;
	private String idSystem;
	private BigDecimal imTotale;
	private String msgid;
	private String receiverid;
	private String receiversys;
	
	private String statoNotifica;
	private String statoPagamento;
	
	private String tiDebito;
	private String tiposervizio;
	private String tipospontaneo;
	private String deCausale;
	
	private Timestamp tmbspedizione;
	private BigDecimal totimportipositivi;
	
	private Timestamp tsDecorrenza;
	private Timestamp tsOperazione;
	
	private Timestamp tsOrdine;
	
	//date per riaccrediti 
	private Date dataAccreditoContoTecnico;
	private Date dataAccreditoEnte;	
	private String trtReceiverId;
	private String trtReceiverSys;	

	private int version;
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Id
	@Column(name="ID_NOTIFICA")
	public String getIdNotifica() {
		return this.idNotifica;
	}

	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}


	@Column(name="CD_ENTE")
	public String getCdEnte() {
		return this.cdEnte;
	}

	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}

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
	@Column(name="DT_SCADENZA")
	public Date getDtScadenza() {
		return this.dtScadenza;
	}

	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}

	public String getE2emsgid() {
		return this.e2emsgid;
	}

	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}

	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	@Column(name="ID_PAGAMENTO")
	public String getIdPagamento() {
		return this.idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
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

	@Column(name="ID_SYSTEM")
	public String getIdSystem() {
		return this.idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	@Column(name="IM_TOTALE")
	public BigDecimal getImTotale() {
		return this.imTotale;
	}

	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}

	public String getMsgid() {
		return this.msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public String getReceiversys() {
		return this.receiversys;
	}

	public void setReceiversys(String receiversys) {
		this.receiversys = receiversys;
	}

	@Column(name="STATO_NOTIFICA")
	public String getStatoNotifica() {
		return this.statoNotifica;
	}

	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}

	@Column(name="STATO_PAGAMENTO")
	public String getStatoPagamento() {
		return this.statoPagamento;
	}

	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}


	@Column(name="TI_DEBITO")
	public String getTiDebito() {
		return this.tiDebito;
	}

	public void setTiDebito(String tiDebito) {
		this.tiDebito = tiDebito;
	}

	public String getTiposervizio() {
		return this.tiposervizio;
	}

	public void setTiposervizio(String tiposervizio) {
		this.tiposervizio = tiposervizio;
	}

	public String getTipospontaneo() {
		return this.tipospontaneo;
	}

	public void setTipospontaneo(String tipospontaneo) {
		this.tipospontaneo = tipospontaneo;
	}

	public Timestamp getTmbspedizione() {
		return this.tmbspedizione;
	}

	public void setTmbspedizione(Timestamp tmbspedizione) {
		this.tmbspedizione = tmbspedizione;
	}

	public BigDecimal getTotimportipositivi() {
		return this.totimportipositivi;
	}

	public void setTotimportipositivi(BigDecimal totimportipositivi) {
		this.totimportipositivi = totimportipositivi;
	}

	@Column(name="TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}

	
	@Column(name="TS_OPERAZIONE")
	public Timestamp getTsOperazione() {
		return this.tsOperazione;
	}

	public void setTsOperazione(Timestamp tsOperazione) {
		this.tsOperazione = tsOperazione;
	}

	@Column(name="TS_ORDINE")
	public Timestamp getTsOrdine() {
		return this.tsOrdine;
	}

	public void setTsOrdine(Timestamp tsOrdine) {
		this.tsOrdine = tsOrdine;
	}

	@Column(name="ID_PAGAMENTOENTE")
	public String getIdPagamentoEnte() {
		return this.idPagamentoEnte;
	}

	public void setIdPagamentoEnte(String idPagamentoEnte) {
		this.idPagamentoEnte = idPagamentoEnte;
	}
	
	@Column(name="DE_CAUSALE")
	public String getDeCausale() {
		return this.deCausale;
	}

	public void setDeCausale(String deCausale) {
		this.deCausale = deCausale;
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
	
	
	
	public void setTrtReceiverId(String trtReceiverId) {
		this.trtReceiverId = trtReceiverId;
	}

	public void setTrtReceiverSys(String trtReceiverSys) {
		this.trtReceiverSys = trtReceiverSys;
	}

	@Column(name="TRT_RECEIVERID")
	public String getTrtReceiverId() {
		return this.trtReceiverId;
	}	

	@Column(name="TRT_RECEIVERSYS")
	public String getTrtReceiverSys() {
		return this.trtReceiverSys;
	}			

	@Column(name="VERSION")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotifichePagamenti [idNotifica=");
		builder.append(idNotifica);
		builder.append(", cdEnte=");
		builder.append(cdEnte);
		builder.append(", cdTrbEnte=");
		builder.append(cdTrbEnte);
		builder.append(", coPagante=");
		builder.append(coPagante);
		builder.append(", dtScadenza=");
		builder.append(dtScadenza);
		builder.append(", e2emsgid=");
		builder.append(e2emsgid);
		builder.append(", idEnte=");
		builder.append(idEnte);
		builder.append(", idPagamento=");
		builder.append(idPagamento);
		builder.append(", idPagamentoEnte=");
		builder.append(idPagamentoEnte);
		builder.append(", idPendenza=");
		builder.append(idPendenza);
		builder.append(", idPendenzaente=");
		builder.append(idPendenzaente);
		builder.append(", idSystem=");
		builder.append(idSystem);
		builder.append(", imTotale=");
		builder.append(imTotale);
		builder.append(", msgid=");
		builder.append(msgid);
		builder.append(", receiverid=");
		builder.append(receiverid);
		builder.append(", receiversys=");
		builder.append(receiversys);
		builder.append(", statoNotifica=");
		builder.append(statoNotifica);
		builder.append(", statoPagamento=");
		builder.append(statoPagamento);
		builder.append(", tiDebito=");
		builder.append(tiDebito);
		builder.append(", tiposervizio=");
		builder.append(tiposervizio);
		builder.append(", tipospontaneo=");
		builder.append(tipospontaneo);
		builder.append(", tmbspedizione=");
		builder.append(tmbspedizione);
		builder.append(", totimportipositivi=");
		builder.append(totimportipositivi);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append(", tsOperazione=");
		builder.append(tsOperazione);
//		builder.append(", tsOrdine=");
//		builder.append(tsOrdine);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idNotifica == null) ? 0 : idNotifica.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NotifichePagamenti other = (NotifichePagamenti) obj;
		if (idNotifica == null) {
			if (other.idNotifica != null) {
				return false;
			}
		} else if (!idNotifica.equals(other.idNotifica)) {
			return false;
		}
		return true;
	}

	
}