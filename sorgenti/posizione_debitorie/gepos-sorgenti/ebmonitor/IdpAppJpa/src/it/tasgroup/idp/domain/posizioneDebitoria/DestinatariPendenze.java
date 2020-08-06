package it.tasgroup.idp.domain.posizioneDebitoria;

import it.tasgroup.idp.domain.BaseEntity;

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
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="DestinatariByIdPend", 
	query=
	"SELECT dest FROM  DestinatariPendenze dest " +
	" WHERE dest.pendenze.idPendenza = :idPendenza ")})

/**
 * The persistent class for the JLTDEPD database table.
 * 
 */
@Entity
@Table(name="JLTDEPD")
public class DestinatariPendenze extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String idDestinatario;
	private String coDestinatario;
	private String deDestinatario;
//	private String idPendenza;
	private int prVersione;
	private String stRiga;
	private String tiDestinatario;
	private Timestamp tsDecorrenza;
	
	
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
	
	
	/*** Persistent Associations ***/
	private Pendenze pendenze;		


    @Id
//    @GenericGenerator(name = "dest", parameters = @Parameter(name = "sequence", value = "JLTDEPD_ID_DESTINATARIO_SEQ"), strategy="it.tasgroup.idp.domain.StringSequenceGenerator")
//    @GeneratedValue(generator = "dest")
    @Column( name = "ID_DESTINATARIO" )		
	public String getIdDestinatario() {
		return this.idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	@Column(name="CO_DESTINATARIO")
	public String getCoDestinatario() {
		return this.coDestinatario;
	}

	public void setCoDestinatario(String coDestinatario) {
		this.coDestinatario = coDestinatario;
	}

	@Column(name="DE_DESTINATARIO")
	public String getDeDestinatario() {
		return this.deDestinatario;
	}

	public void setDeDestinatario(String deDestinatario) {
		this.deDestinatario = deDestinatario;
	}

//	@Column(name="ID_PENDENZA")
//	public String getIdPendenza() {
//		return this.idPendenza;
//	}
//
//	public void setIdPendenza(String idPendenza) {
//		this.idPendenza = idPendenza;
//	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	@Column(name="TI_DESTINATARIO")
	public String getTiDestinatario() {
		return this.tiDestinatario;
	}

	public void setTiDestinatario(String tiDestinatario) {
		this.tiDestinatario = tiDestinatario;
	}


	@Column(name="TS_DECORRENZA")
	public Timestamp getTsDecorrenza() {
		return this.tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
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
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DestinatariPendenze [idDestinatario=");
		builder.append(idDestinatario);
		builder.append(", coDestinatario=");
		builder.append(coDestinatario);
		builder.append(", deDestinatario=");
		builder.append(deDestinatario);
//		builder.append(", idPendenza=");
//		builder.append(idPendenza);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", stRiga=");
		builder.append(stRiga);
		builder.append(", tiDestinatario=");
		builder.append(tiDestinatario);
		builder.append(", tsDecorrenza=");
		builder.append(tsDecorrenza);
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		
		builder.append(", tipoSoggettoDestinatario=");
		builder.append(getTipoSoggettoDestinatario());
		builder.append(", anagraficaDestinatario=");
		builder.append(getAnagraficaDestinatario());
		builder.append(", emailDestinatario=");
		builder.append(getEmailDestinatario());
		builder.append(", indirizzoDestinatario=");
		builder.append(getIndirizzoDestinatario());
		builder.append(", numCivicoDestinatario=");
		builder.append(getNumCivicoDestinatario());
		builder.append(", capDestinatario=");
		builder.append(getCapDestinatario());
		builder.append(", localitaDestinatario=");
		builder.append(getLocalitaDestinatario());
		builder.append(", provinciaDestinatario=");
		builder.append(getProvinciaDestinatario());
		builder.append(", nazioneDestinatario=");
		builder.append(getNazioneDestinatario());
		
		builder.append(", dataNascitaDestinatario=");
		builder.append(getDataNascitaDestinatario());
		builder.append(", luogoNascitaDestinatario=");
		builder.append(getLuogoNascitaDestinatario());
		
		builder.append("]");
		return builder.toString();
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DestinatariPendenze other = (DestinatariPendenze) obj;
		if (idDestinatario == null) {
			if (other.idDestinatario != null) {
				return false;
			}
		} else if (!idDestinatario.equals(other.idDestinatario)) {
			return false;
		}
		return true;
	}
	
	//bi-directional many-to-one association to pendenze
    @ManyToOne
	@JoinColumn(name="ID_PENDENZA")
	public Pendenze getPendenze() {
		return this.pendenze;
	}

	public void setPendenze(Pendenze pendenze) {
		this.pendenze = pendenze;
	}
	

}