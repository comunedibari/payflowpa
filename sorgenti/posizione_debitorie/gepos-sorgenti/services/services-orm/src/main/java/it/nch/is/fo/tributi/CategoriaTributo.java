package it.nch.is.fo.tributi;

import it.nch.fwk.fo.dto.business.PojoImpl;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
//@Table(name="TRIBUTI")
@Table(name="JLTTRPA")
public class CategoriaTributo extends PojoImpl implements ICategoriaTributo {

	/*** Persistent Properties ***/
	private String idTributo;
	private String deTrb;
	private String cdAde;
	private String tpEntrata;
	private String flPredeterm;
	private String flIniziativa;
	private String stato;
	private String soggEsclusi;
	private int prVersione;
	private String tassonomia="0"; 
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;

	private Date tsAggiornamento;
	private String cdPagamentoSpontaneo;
	
	private String istruzioniPagamento;
	
	/*** Persistent Collections ***/
	private Set<TributoEnte> entiTributi;

	
	public CategoriaTributo() {
	}

	public CategoriaTributo(String idTributo, int prVersione, String opInserimento, Date tsInserimento) {
		this.idTributo = idTributo;
		this.prVersione = prVersione;
		this.opInserimento = opInserimento;
		this.tsInserimento = tsInserimento;
	}

	public CategoriaTributo(String idTributo, String deTrb, String cdAde, String tpEntrata, String flPredeterm,
			String flIniziativa, String stato, String soggEsclusi, int prVersione, String opInserimento,
			Date tsInserimento, String opAggiornamento, Date tsAggiornamento) {
		this.idTributo = idTributo;
		this.deTrb = deTrb;
		this.cdAde = cdAde;
		this.tpEntrata = tpEntrata;
		this.flPredeterm = flPredeterm;
		this.flIniziativa = flIniziativa;
		this.stato = stato;
		this.soggEsclusi = soggEsclusi;
		this.prVersione = prVersione;
		this.opInserimento = opInserimento;
		this.tsInserimento = tsInserimento;
		this.opAggiornamento = opAggiornamento;
		this.tsAggiornamento = tsAggiornamento;
	}

	@Id
	@Column(name="ID_TRIBUTO")
	public String getIdTributo() {
		return this.idTributo;
	}
	
	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	@Column(name="DE_TRB")
	public String getDeTrb() {
		return this.deTrb;
	}
	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}

	@Column(name="CD_ADE")
	public String getCdAde() {
		return this.cdAde;
	}
	public void setCdAde(String cdAde) {
		this.cdAde = cdAde;
	}

	@Column(name="TP_ENTRATA")
	public String getTpEntrata() {
		return this.tpEntrata;
	}
	public void setTpEntrata(String tpEntrata) {
		this.tpEntrata = tpEntrata;
	}

	@Column(name="FL_PREDETERM")
	public String getFlPredeterm() {
		return this.flPredeterm;
	}
	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
	}

	@Column(name="FL_INIZIATIVA")
	public String getFlIniziativa() {
		return this.flIniziativa;
	}
	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="SOGG_ESCLUSI")
	public String getSoggEsclusi() {
		return this.soggEsclusi;
	}
	public void setSoggEsclusi(String soggEsclusi) {
		this.soggEsclusi = soggEsclusi;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}
	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Date getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Date getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	
	@Column(name="CDPAGAMENTOSPONTANEO")
	public String getCdPagamentoSpontaneo() {
		return cdPagamentoSpontaneo;
	}

	public void setCdPagamentoSpontaneo(String cdPagamentoSpontaneo) {
		this.cdPagamentoSpontaneo = cdPagamentoSpontaneo;
	}

	@Column(name="TASSONOMIA") 
	public String getTassonomia() { 
		return tassonomia; 
	} 

	public void setTassonomia(String tassonomia) { 
		this.tassonomia = tassonomia; 
	} 
	@OneToMany(targetEntity=TributoEnte.class,mappedBy="categoriaobj",fetch=FetchType.LAZY)
	 public Set<TributoEnte> getEntiTributi() {
		return entiTributi;
	}
	public void setEntiTributi(Set entiTributi) {
		this.entiTributi = entiTributi;
	}
	
	@Column(name="ISTRUZIONI_PAGAMENTO")
	public String getIstruzioniPagamento() {
		return istruzioniPagamento;
	}

	public void setIstruzioniPagamento(String istruzioniPagamento) {
		this.istruzioniPagamento = istruzioniPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTributo == null) ? 0 : idTributo.hashCode());
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
		CategoriaTributo other = (CategoriaTributo) obj;
		if (idTributo == null) {
			if (other.idTributo != null)
				return false;
		} else if (!idTributo.equals(other.idTributo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CategoriaTributo [idTributo=" + idTributo + ", deTrb=" + deTrb
				+ ", cdAde=" + cdAde + ", tpEntrata=" + tpEntrata
				+ ", flPredeterm=" + flPredeterm + ", flIniziativa="
				+ flIniziativa + ", stato=" + stato + ", soggEsclusi="
				+ soggEsclusi + ", prVersione=" + prVersione
				+ ", opInserimento=" + opInserimento + ", tsInserimento="
				+ tsInserimento + ", opAggiornamento=" + opAggiornamento
				+ ", tsAggiornamento=" + tsAggiornamento
				+ ", cdPagamentoSpontaneo=" + cdPagamentoSpontaneo
				+ ", entiTributi=" + entiTributi + "]";
	}
		
}
