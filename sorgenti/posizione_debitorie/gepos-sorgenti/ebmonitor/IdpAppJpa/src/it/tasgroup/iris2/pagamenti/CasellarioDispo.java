package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CASELLARIO_DISPO database table.
 * 
 */
@Entity
@Table(name="CASELLARIO_DISPO")
public class CasellarioDispo extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private Timestamp dataElaborazione;
	private String descErrore;
	private int dimensione;
	private short flagElaborazione;
	private byte[] flussoCbi;
	private String nomeSupporto;
	private int numeroRecord;
	private short tipoErrore;
	private String tipoFlusso;
	
	/*** Persistent Associations ***/
	private DistintePagamento distintePagamento;
	private DistinteRiaccredito distinteRiaccredito;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="casellario_dispo_pk_gen")	
	@SequenceGenerator(
	    name="casellario_dispo_pk_gen",
	    sequenceName="CASELLARIO_DISPO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/	
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


	public int getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(int dimensione) {
		this.dimensione = dimensione;
	}


	@Column(name="FLAG_ELABORAZIONE")
	public short getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(short flagElaborazione) {
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
	public int getNumeroRecord() {
		return this.numeroRecord;
	}

	public void setNumeroRecord(int numeroRecord) {
		this.numeroRecord = numeroRecord;
	}


	@Column(name="TIPO_ERRORE")
	public short getTipoErrore() {
		return this.tipoErrore;
	}

	public void setTipoErrore(short tipoErrore) {
		this.tipoErrore = tipoErrore;
	}


	@Column(name="TIPO_FLUSSO")
	public String getTipoFlusso() {
		return this.tipoFlusso;
	}

	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}

	//bi-directional many-to-one association to DistintePagamento
    @OneToOne
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO", nullable=true)
	public DistintePagamento getDistintePagamento() {
		return this.distintePagamento;
	}

	public void setDistintePagamento(DistintePagamento distintePagamento) {
		this.distintePagamento = distintePagamento;
	}
	

    @OneToOne
	@JoinColumn(name="ID_DISTINTE_RIACCREDITO", nullable=true)
	public DistinteRiaccredito getDistinteRiaccredito() {
		return this.distinteRiaccredito;
	}

	public void setDistinteRiaccredito(DistinteRiaccredito distinteRiaccredito) {
		this.distinteRiaccredito = distinteRiaccredito;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CasellarioDispo [dataElaborazione=");
		builder.append(dataElaborazione);
		builder.append(", descErrore=");
		builder.append(descErrore);
		builder.append(", dimensione=");
		builder.append(dimensione);
		builder.append(", flagElaborazione=");
		builder.append(flagElaborazione);
		builder.append(", flussoCbi=");
		builder.append(Arrays.toString(flussoCbi));
		builder.append(", nomeSupporto=");
		builder.append(nomeSupporto);
		builder.append(", numeroRecord=");
		builder.append(numeroRecord);
		builder.append(", tipoErrore=");
		builder.append(tipoErrore);
		builder.append(", tipoFlusso=");
		builder.append(tipoFlusso);
		//builder.append(", distintePagamento=");
		//builder.append(distintePagamento);
		//builder.append(", distinteRiaccredito=");
		//builder.append(distinteRiaccredito);
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