package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the JLTDEPD database table.
 * 
 */
@Entity
@Table(name="JLTDEPD")
@NamedQueries(
{
@NamedQuery(
		name="getDebitoriPendenzaByIdPend",
		query="select coDestinatario from DestinatariPendenza where pendenza.idPendenza=:idPendenza and tiDestinatario <> '" + DestinatariPendenza.TIPO_DEST_DELEGATO + "' "),
@NamedQuery(
		name="listByCodiceFiscale",
	    query="select dest from DestinatariPendenza dest where coDestinatario=:coDestinatario "),
@NamedQuery(
	    name="listByCodiceFiscaleAndStatoPendenza",
	    query="select dest from DestinatariPendenza dest where coDestinatario=:coDestinatario "+
	          "and dest.pendenza.stPendenza = :statoPendenza                                  "),
@NamedQuery(
	    name="listByCodiceFiscaleStatoAndDate",
	    query="select dest from DestinatariPendenza dest where coDestinatario=:coDestinatario "+
	     	  "and (dest.pendenza.stPendenza = :statoPendenza    or :statoPendenza is null)   "+
	     	  "and (dest.pendenza.tsDecorrenza = :dataIni        or dest.pendenza.tsDecorrenza is null)  "+
	     	  "and (dest.pendenza.tsPrescrizione = :dataFin          or :dataFin is null)         ")	    	          
})
public class DestinatariPendenza implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String TIPO_DEST_DELEGATO = "DE";
	
	private String idDestinatario;
	private String coDestinatario;
	private String deDestinatario;
	private String opAggiornamento;
	private String opInserimento;
	private int prVersione;
	private String stRiga;
	private String tiDestinatario;
	private Timestamp tsAggiornamento;
	private Timestamp tsDecorrenza;
	private Timestamp tsInserimento;
	private Pendenza pendenza;
	
	private String tipoSoggettoDestinatario;
	private String anagraficaDestinatario; 
	private String emailDestinatario;
	private String indirizzoDestinatario;
	private String numCivicoDestinatario;
	private String capDestinatario;
	private String localitaDestinatario;
	private String provinciaDestinatario;
	private String nazioneDestinatario;
	private Date dataNascitaDestinatario;
	private String luogoNascitaDestinatario;
	

	//bi-directional many-to-one association to Pendenza
    @ManyToOne
	@JoinColumn(name="ID_PENDENZA", nullable=false)
	public Pendenza getPendenza() {
		return pendenza;
	}


	public void setPendenza(Pendenza pendenza) {
		this.pendenza = pendenza;
	}


	public DestinatariPendenza() {
    }


	@Id
	@Column(name="ID_DESTINATARIO", unique=true, nullable=false, length=40)
	public String getIdDestinatario() {
		return this.idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}


	@Column(name="CO_DESTINATARIO", nullable=false, length=70)
	public String getCoDestinatario() {
		return this.coDestinatario;
	}

	public void setCoDestinatario(String coDestinatario) {
		this.coDestinatario = coDestinatario;
	}


	@Column(name="DE_DESTINATARIO", length=140)
	public String getDeDestinatario() {
		return this.deDestinatario;
	}

	public void setDeDestinatario(String deDestinatario) {
		this.deDestinatario = deDestinatario;
	}


	@Column(name="OP_AGGIORNAMENTO", length=80)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
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


	@Column(name="ST_RIGA", nullable=false, length=2)
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(name="TI_DESTINATARIO", nullable=false, length=4)
	public String getTiDestinatario() {
		return this.tiDestinatario;
	}

	public void setTiDestinatario(String tiDestinatario) {
		this.tiDestinatario = tiDestinatario;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
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
	

	@Column(name="TIPO_SOGGETTO_DEST")
	public String getTipoSoggettoDestinatario() {
		return tipoSoggettoDestinatario;
	}

	public void setTipoSoggettoDestinatario(String tipoSoggettoDestinatario) {
		this.tipoSoggettoDestinatario = tipoSoggettoDestinatario;
	}

	@Column(name="ANAGRAFICA_SOGGETTO_DEST")
	public String getAnagraficaDestinatario() {
		return anagraficaDestinatario;
	}

	public void setAnagraficaDestinatario(String anagraficaDestinatario) {
		this.anagraficaDestinatario = anagraficaDestinatario;
	}

	@Column(name="EMAIL_DEST")
	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	@Column(name="INDIRIZZO_DEST")
	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}

	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
	}

	@Column(name="NUMERO_CIVICO_DEST")
	public String getNumCivicoDestinatario() {
		return numCivicoDestinatario;
	}

	public void setNumCivicoDestinatario(String numCivicoDestinatario) {
		this.numCivicoDestinatario = numCivicoDestinatario;
	}

	@Column(name="CAP_DEST")
	public String getCapDestinatario() {
		return capDestinatario;
	}

	public void setCapDestinatario(String capDestinatario) {
		this.capDestinatario = capDestinatario;
	}

	@Column(name="LOCALITA_DEST")
	public String getLocalitaDestinatario() {
		return localitaDestinatario;
	}

	public void setLocalitaDestinatario(String localitaDestinatario) {
		this.localitaDestinatario = localitaDestinatario;
	}

	@Column(name="PROVINCIA_DEST")
	public String getProvinciaDestinatario() {
		return provinciaDestinatario;
	}

	public void setProvinciaDestinatario(String provinciaDestinatario) {
		this.provinciaDestinatario = provinciaDestinatario;
	}

	@Column(name="NAZIONE_DEST")
	public String getNazioneDestinatario() {
		return nazioneDestinatario;
	}

	public void setNazioneDestinatario(String nazioneDestinatario) {
		this.nazioneDestinatario = nazioneDestinatario;
	}
	
	@Column(name="DATA_NASCITA_DEST")
	public Date getDataNascitaDestinatario() {
		return dataNascitaDestinatario;
	}

	public void setDataNascitaDestinatario(Date dataNascitaDestinatario) {
		this.dataNascitaDestinatario = dataNascitaDestinatario;
	}	
	
	@Column(name="LUOGO_NASCITA_DEST")
	public String getLuogoNascitaDestinatario() {
		return luogoNascitaDestinatario;
	}

	public void setLuogoNascitaDestinatario(String luogoNascitaDestinatario) {
		this.luogoNascitaDestinatario = luogoNascitaDestinatario;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDestinatario == null) ? 0 : idDestinatario.hashCode());
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
		DestinatariPendenza other = (DestinatariPendenza) obj;
		if (idDestinatario == null) {
			if (other.idDestinatario != null)
				return false;
		} else if (!idDestinatario.equals(other.idDestinatario))
			return false;
		return true;
	}
	
}