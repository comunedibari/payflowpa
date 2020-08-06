package it.tasgroup.idp.domain.enti;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseIdEntity;

 
/**
 * The persistent class for the CONFIG_NOTIFICHE database table.
 * 
 */
@Entity
@Table(name="CFG_NOTIFICA_PAGAMENTO")
@NamedQueries({
@NamedQuery(name="getNotifByFreqAndType", 
		query=" select a from ConfigNotifiche a " +
				" where a.freqNotifica =:freqNotifica " +
				"   and a.tipoNotifica =:tipoNotifica "),
				
@NamedQuery(name="getNotifByFreqAndTypeCheckNotifPagamenti", 
			query=" select a from ConfigNotifiche a " +
						" where a.freqNotifica =:freqNotifica " +
						"   and a.tipoNotifica =:tipoNotifica " +
						"   and exists ("+ 
						"   select n.idNotifica from NotifichePagamenti n where " +
						"      n.idEnte = a.jltentr.id.idEnte " +
						"      and n.cdTrbEnte = a.jltentr.id.cdTrbEnte  " +
						"      and a.tipoNotifica = n.statoPagamento " +
						"      and n.statoNotifica = 'DA_CREARE' "
						+")"),
@NamedQuery(name="getNotifByIdEnteCdTrbEnteAndType", 
				query=" select a from ConfigNotifiche a " +
						" where a.tipoNotifica =:tipoNotifica " +
						"   and a.jltentr.id.idEnte =:idEnte " +
						"   and a.jltentr.id.cdTrbEnte =:cdTrbEnte "),
						
@NamedQuery(name="getNotifByTypeCheckNotifPagamenti", 
				query=" select a from ConfigNotifiche a " +
						" where a.freqNotifica <> :freqNotifica " +
						"   and a.tipoNotifica = :tipoNotifica " +
						"   and exists ("+ 
						"   select n.idNotifica from NotifichePagamenti n where " +
						"      n.idEnte = a.jltentr.id.idEnte " +
						"      and n.cdTrbEnte = a.jltentr.id.cdTrbEnte  " +
						"      and a.tipoNotifica = n.statoPagamento " +
						"      and n.statoNotifica = 'DA_CREARE' "
						+")")
})
public class ConfigNotifiche extends BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/*** Persistent Values***/
	private String freqNotifica;
	private String formatoNotifica;
	private String consegnaNotifica;
	private String tipoNotifica;	
	
	private TributiEnti jltentr;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="cfg_notifiche_pagamento_pk_gen")	
	@SequenceGenerator(
	    name="cfg_notifiche_pagamento_pk_gen",
	    sequenceName="NOTIFICHE_PAGAMENTO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}
	
	@Column(name="FREQ_NOTIFICA")
	public String getFreqNotifica() {
		return freqNotifica;
	}


	public void setFreqNotifica(String freqNotifica) {
		this.freqNotifica = freqNotifica;
	}
	
	@Column(name="FORMATO_NOTIFICA")
	public String getFormatoNotifica() {
		return formatoNotifica;
	}


	public void setFormatoNotifica(String formatoNotifica) {
		this.formatoNotifica = formatoNotifica;
	}

	@Column(name="CONSEGNA_NOTIFICA")
	public String getConsegnaNotifica() {
		return consegnaNotifica;
	}

	public void setConsegnaNotifica(String consegnaNotifica) {
		this.consegnaNotifica = consegnaNotifica;
	}
	
    
	//bi-directional many-to-one association to TributiEnti
    @ManyToOne
    @JoinColumns({
	    @JoinColumn(name="ID_ENTE",referencedColumnName="ID_ENTE"),
        @JoinColumn(name="CD_TRB_ENTE",referencedColumnName="CD_TRB_ENTE")
    })
	public TributiEnti getJltentr() {
		return jltentr;
	}

	public void setJltentr(TributiEnti jltentr) {
		this.jltentr = jltentr;
	}

	@Column(name="TIPO_NOTIFICA")
	public String getTipoNotifica() {
		return tipoNotifica;
	}


	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" getId()=");	
		builder.append(super.id);
		/*** Persistent Values***/
		builder.append(", getFreqNotifica()=");
		builder.append(getFreqNotifica());
		builder.append(", getFormatoNotifica()=");
		builder.append(getFormatoNotifica());
		builder.append(", getConsegnaNotifica()=");
		builder.append(getConsegnaNotifica());
		builder.append(", getTipoNotifica()=");
		builder.append(getTipoNotifica());		
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