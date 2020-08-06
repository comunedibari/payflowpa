package it.tasgroup.cbill.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the CASELLARIO_INFO_BLOCCHI database table.
 * 
 */
@Entity
@Table(name="CASELLARIO_INFO_BLOCCHI")
@NamedQuery(name="CasellarioInfoBlocchi.findAll", query="SELECT c FROM CasellarioInfoBlocchi c")
public class CasellarioInfoBlocchi extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int blocchiTotali;
	private int bloccoCorrente;
	private byte[] block;
	private String centroserviziMittente;
	private Timestamp dataFlusso;
	private Date dataRiferimento;
	private int idCasellarioInfo;
	private String idMittente;
	private String idRicevente;
	private String nomeSupporto;
	private String setCaratteri;
	private String sistemaMittente;
	private String stato;
	private String tipoFlusso;
	private int zipped;

	public CasellarioInfoBlocchi() {
	}

	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="casellario_info_blocchi_pk_gen")	
	@SequenceGenerator(
	    name="casellario_info_blocchi_pk_gen",
	    sequenceName="CASELLARIO_INFO_BLOCC_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	        
	

	@Column(name="BLOCCHI_TOTALI")
	public int getBlocchiTotali() {
		return this.blocchiTotali;
	}

	public void setBlocchiTotali(int blocchiTotali) {
		this.blocchiTotali = blocchiTotali;
	}


	@Column(name="BLOCCO_CORRENTE")
	public int getBloccoCorrente() {
		return this.bloccoCorrente;
	}

	public void setBloccoCorrente(int bloccoCorrente) {
		this.bloccoCorrente = bloccoCorrente;
	}


	@Lob
	public byte[] getBlock() {
		return this.block;
	}

	public void setBlock(byte[] block) {
		this.block = block;
	}


	@Column(name="CENTROSERVIZI_MITTENTE")
	public String getCentroserviziMittente() {
		return this.centroserviziMittente;
	}

	public void setCentroserviziMittente(String centroserviziMittente) {
		this.centroserviziMittente = centroserviziMittente;
	}


	@Column(name="DATA_FLUSSO")
	public Timestamp getDataFlusso() {
		return this.dataFlusso;
	}

	public void setDataFlusso(Timestamp dataFlusso) {
		this.dataFlusso = dataFlusso;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="DATA_RIFERIMENTO")
	public Date getDataRiferimento() {
		return this.dataRiferimento;
	}

	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}


	@Column(name="ID_CASELLARIO_INFO")
	public int getIdCasellarioInfo() {
		return this.idCasellarioInfo;
	}

	public void setIdCasellarioInfo(int idCasellarioInfo) {
		this.idCasellarioInfo = idCasellarioInfo;
	}


	@Column(name="ID_MITTENTE")
	public String getIdMittente() {
		return this.idMittente;
	}

	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}


	@Column(name="ID_RICEVENTE")
	public String getIdRicevente() {
		return this.idRicevente;
	}

	public void setIdRicevente(String idRicevente) {
		this.idRicevente = idRicevente;
	}


	@Column(name="NOME_SUPPORTO")
	public String getNomeSupporto() {
		return this.nomeSupporto;
	}

	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}


	@Column(name="SET_CARATTERI")
	public String getSetCaratteri() {
		return this.setCaratteri;
	}

	public void setSetCaratteri(String setCaratteri) {
		this.setCaratteri = setCaratteri;
	}


	@Column(name="SISTEMA_MITTENTE")
	public String getSistemaMittente() {
		return this.sistemaMittente;
	}

	public void setSistemaMittente(String sistemaMittente) {
		this.sistemaMittente = sistemaMittente;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="TIPO_FLUSSO")
	public String getTipoFlusso() {
		return this.tipoFlusso;
	}

	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}


	public int getZipped() {
		return this.zipped;
	}

	public void setZipped(int zipped) {
		this.zipped = zipped;
	}

}