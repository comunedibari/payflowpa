package it.tasgroup.iris.domain;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the JLTE051 database table.
 * 
 */
@Entity
@Table(name="SESSIONI")
public class Sessione extends BaseEntity {
	
	private String sessionid;
	private String abi;
	private String abiaccentratore;
	private String abiazienda;
	private String azienda;
	private String aziendasel;
	private String cab;
	private String canale;
	private String cap;
	private String certificato;
	private String codicefiscale;
	private Double collegamento;
	private String comune;
	private Double importomax;
	private String indirizzo;
	private String indirizzoip;
	private String lapl;
	private String lingua;
	private String mac;
	private String opAggiornamento;
	private String opInserimento;
	private String password;
	private Integer prVersione;
	private String provincia;
	private String ragionesocialeazienda;
	private String sia;
	private String stato;
	private String tipointestatario;
	private String tiposicurezza;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private Double ultimaoperazione;
	private String username;
	
	private List<IrisGatewayClientShopCart> irisGatewayClientShopCarts;

    public Sessione() {
    }


	@Id
	public String getSessionid() {
		return this.sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}


	public String getAbi() {
		return this.abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}


	public String getAbiaccentratore() {
		return this.abiaccentratore;
	}

	public void setAbiaccentratore(String abiaccentratore) {
		this.abiaccentratore = abiaccentratore;
	}


	public String getAbiazienda() {
		return this.abiazienda;
	}

	public void setAbiazienda(String abiazienda) {
		this.abiazienda = abiazienda;
	}


	public String getAzienda() {
		return this.azienda;
	}

	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}


	public String getAziendasel() {
		return this.aziendasel;
	}

	public void setAziendasel(String aziendasel) {
		this.aziendasel = aziendasel;
	}


	public String getCab() {
		return this.cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}


	public String getCanale() {
		return this.canale;
	}

	public void setCanale(String canale) {
		this.canale = canale;
	}


	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getCertificato() {
		return this.certificato;
	}

	public void setCertificato(String certificato) {
		this.certificato = certificato;
	}


	public String getCodicefiscale() {
		return this.codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}


	public Double getCollegamento() {
		return this.collegamento;
	}

	public void setCollegamento(Double collegamento) {
		this.collegamento = collegamento;
	}


	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}


	public Double getImportomax() {
		return this.importomax;
	}

	public void setImportomax(Double importomax) {
		this.importomax = importomax;
	}


	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getIndirizzoip() {
		return this.indirizzoip;
	}

	public void setIndirizzoip(String indirizzoip) {
		this.indirizzoip = indirizzoip;
	}


	public String getLapl() {
		return this.lapl;
	}

	public void setLapl(String lapl) {
		this.lapl = lapl;
	}


	public String getLingua() {
		return this.lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}


	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="\"PASSWORD\"")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(name="PR_VERSIONE")
	public Integer getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(Integer prVersione) {
		this.prVersione = prVersione;
	}


	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getRagionesocialeazienda() {
		return this.ragionesocialeazienda;
	}

	public void setRagionesocialeazienda(String ragionesocialeazienda) {
		this.ragionesocialeazienda = ragionesocialeazienda;
	}


	public String getSia() {
		return this.sia;
	}

	public void setSia(String sia) {
		this.sia = sia;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	public String getTipointestatario() {
		return this.tipointestatario;
	}

	public void setTipointestatario(String tipointestatario) {
		this.tipointestatario = tipointestatario;
	}


	public String getTiposicurezza() {
		return this.tiposicurezza;
	}

	public void setTiposicurezza(String tiposicurezza) {
		this.tiposicurezza = tiposicurezza;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	public Double getUltimaoperazione() {
		return this.ultimaoperazione;
	}

	public void setUltimaoperazione(Double ultimaoperazione) {
		this.ultimaoperazione = ultimaoperazione;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	//bi-directional many-to-one association to IrisGatewayClientShopCart
	@OneToMany(mappedBy="sessione", cascade=CascadeType.MERGE)
	public List<IrisGatewayClientShopCart> getIrisGatewayClientShopCarts() {
		return this.irisGatewayClientShopCarts;
	}

	public void setIrisGatewayClientShopCarts(List<IrisGatewayClientShopCart> irisGatewayClientShopCarts) {
		this.irisGatewayClientShopCarts = irisGatewayClientShopCarts;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sessione other = (Sessione) obj;
		if (sessionid == null) {
			if (other.sessionid != null)
				return false;
		} else if (!sessionid.equals(other.sessionid))
			return false;
		return true;
	}
	
}