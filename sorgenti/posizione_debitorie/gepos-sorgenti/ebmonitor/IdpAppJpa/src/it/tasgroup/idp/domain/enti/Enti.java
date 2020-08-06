package it.tasgroup.idp.domain.enti;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;

@NamedQueries({

@NamedQuery(
	name="EnteByIntestatario", 
	query=
	"SELECT enti FROM Enti enti " +
	" WHERE enti.intestatario = :intestatario"),
@NamedQuery(
	name="TributiEntiByCdEnte", 
	query=
	"SELECT enti FROM Enti enti " +
	" WHERE enti.cdEnte = :cdEnte "),
@NamedQuery(
		name="EnteByIdentificativoDominio", 
		query=
		"SELECT enti FROM Enti enti " +
		" WHERE enti.intestatario = :intestatario ")
})
//	+ " and enti.jltentrs.id.cdTrbEnte = :cdTrbEnte")})

/**
 * The persistent class for the JLTENTI database table.
 * 
 */
@Entity
@Table(name="JLTENTI")
public class Enti extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String idEnte;	
	private String cdEnte;
	private String denom;
	private String intestatario;
	private int prVersione;
	private String provincia;
	private String stato;
	private String tpEnte;
	private String cdAziendaSanitaria;
	private String gln;
    private String autorizzStampaDdp;
    private String flNdp;
    private String flNdpModello3;
    
    private String ndpCodStazPa; 
	private String ndpAuxDigit;  
	private String ndpCodSegr;
	
	private int maxNumTributi = 100;

	/*** Persistent Associations***/
	private Set<TributiEnti> jltentrs;

	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Id
	@Column(name="ID_ENTE")	
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	@Column(name="CD_ENTE")
	public String getCdEnte() {
		return this.cdEnte;
	}

	@Column(name="GLN")
	public String getGln() {
		return gln;
	}

	public void setGln(String gln) {
		this.gln = gln;
	}
	
	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}

	public String getDenom() {
		return this.denom;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}

	public String getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="TP_ENTE")
	public String getTpEnte() {
		return this.tpEnte;
	}

	public void setTpEnte(String tpEnte) {
		this.tpEnte = tpEnte;
	}

	@Column(name="CD_AZIENDA_SANITARIA")
	public String getCdAziendaSanitaria() {
		return cdAziendaSanitaria;
	}

	public void setCdAziendaSanitaria(String cdAziendaSanitaria) {
		this.cdAziendaSanitaria = cdAziendaSanitaria;
	}
	
	//bi-directional many-to-one association to TributiEnti
	@OneToMany(mappedBy="jltenti")
	public Set<TributiEnti> getJltentrs() {
		return this.jltentrs;
	}

	public void setJltentrs(Set<TributiEnti> jltentrs) {
		this.jltentrs = jltentrs;
	}

    @Column(name = "AUTORIZZ_STAMPA_DDP")
    public String getAutorizzStampaDdp() {
        return autorizzStampaDdp;
    }

    public void setAutorizzStampaDdp(String autorizzStampaDdp) {
        this.autorizzStampaDdp = autorizzStampaDdp;
    }
    
    @Column(name="NDP_COD_STAZ_PA")
	public String getNdpCodStazPa() {
		return ndpCodStazPa;
	}

	public void setNdpCodStazPa(String ndpCodStazPa) {
		this.ndpCodStazPa = ndpCodStazPa;
	}
	
	@Column(name="NDP_AUX_DIGIT")
	public String getNdpAuxDigit() {
		return ndpAuxDigit;
	}

	public void setNdpAuxDigit(String ndpAuxDigit) {
		this.ndpAuxDigit = ndpAuxDigit;
	}
	
	@Column(name="NDP_COD_SEGR")
	public String getNdpCodSegr() {
		return ndpCodSegr;
	}

	public void setNdpCodSegr(String ndpCodSegr) {
		this.ndpCodSegr = ndpCodSegr;
	}
	
	
	@Column(name="MAX_NUM_TRIBUTI")
	public int getMaxNumTributi() {
		return maxNumTributi;
	}

	public void setMaxNumTributi(int maxNumTributi) {
		this.maxNumTributi = maxNumTributi;
	}	
	
	@Column(name="FL_NDP")
	public String getFlNdp() {
		return flNdp;
	}
	public void setFlNdp(String flNdp) {
		this.flNdp = flNdp;
	}
	
	@Column(name="FL_NDP_MODELLO3")
	public String getFlNdpModello3() {
		return flNdpModello3;
	}
	public void setFlNdpModello3(String flNdpModello3) {
		this.flNdpModello3 = flNdpModello3;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enti [idEnte=");
		builder.append(idEnte);
		builder.append(", cdEnte=");
		builder.append(cdEnte);
		builder.append(", denom=");
		builder.append(denom);
		builder.append(", intestatario=");
		builder.append(intestatario);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", provincia=");
		builder.append(provincia);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", tpEnte=");
		builder.append(tpEnte);
		builder.append(", flNdp=");
		builder.append(flNdp);
		builder.append(", flNdpModello3=");
		builder.append(flNdpModello3);
		//builder.append(", jltentrs=");
		//builder.append(jltentrs);
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
		result = prime * result + ((idEnte == null) ? 0 : idEnte.hashCode());
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
		Enti other = (Enti) obj;
		if (idEnte == null) {
			if (other.idEnte != null) {
				return false;
			}
		} else if (!idEnte.equals(other.idEnte)) {
			return false;
		}
		return true;
	}


	

	
}