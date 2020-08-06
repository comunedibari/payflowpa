package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="ERRORI_CART")
public class ErroriCart implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values***/
	private String idErroreCart;
	private byte[] messaggioXml;
	private String serviceName;	
	private String tipoMessaggio;
	private String statoErrore;
	private int prVersione;
	private String opInserimento;
	private Timestamp tsInserimento;


	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Id
	@Column(name="ID_ERRORE_CART")
	public String getIdErroreCart() {
		return this.idErroreCart;
	}
	public void setIdErroreCart(String idErroreCart) {
		this.idErroreCart = idErroreCart;
	}

	@Column(name="MESSAGGIO_XML")
	@Lob
	public byte[] getMessaggioXml() {
		return this.messaggioXml;
	}
	public void setMessaggioXml(byte[] messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

	@Column(name="TIPO_MESSAGGIO")
	public String getTipoMessaggio() {
		return this.tipoMessaggio;
	}
	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	@Column(name="STATO_ERRORE")
	public String getStatoErrore() {
		return this.statoErrore;
	}
	public void setStatoErrore(String statoErrore) {
		this.statoErrore = statoErrore;
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
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	@Column(name="SERVICENAME")
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErroriCart [idErroreCart=");
		builder.append(idErroreCart);
		builder.append(", messaggioXml=");
		builder.append(Arrays.toString(messaggioXml));
		builder.append(", serviceName=");
		builder.append(serviceName);
		builder.append(", tipoMessaggio=");
		builder.append(tipoMessaggio);
		builder.append(", statoErrore=");
		builder.append(statoErrore);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idErroreCart == null) ? 0 : idErroreCart.hashCode());
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
		ErroriCart other = (ErroriCart) obj;
		if (idErroreCart == null) {
			if (other.idErroreCart != null) {
				return false;
			}
		} else if (!idErroreCart.equals(other.idErroreCart)) {
			return false;
		}
		return true;
	}	
	

}
