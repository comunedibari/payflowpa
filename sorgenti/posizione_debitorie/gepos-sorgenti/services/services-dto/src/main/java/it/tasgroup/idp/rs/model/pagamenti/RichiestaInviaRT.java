package it.tasgroup.idp.rs.model.pagamenti;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaInviaRT {

	private String identificativoDominio;
	private String identificativoUnivocoVersamento;	
	private String codiceContestoPagamento;
	private byte[] ricevutaTelematica;
	
	public RichiestaInviaRT() {

	}
	
	@XmlElement(required = true)
	public String getIdentificativoDominio() {
		return identificativoDominio;
	}

	public void setIdentificativoDominio(String identificativoDominio) {
		this.identificativoDominio = identificativoDominio;
	}

	@XmlElement(required = true)
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}

	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}

	@XmlElement(required = true)
	public byte[] getRicevutaTelematica() {
		return ricevutaTelematica;
	}

	public void setRicevutaTelematica(byte[] ricevutaTelematica) {
		this.ricevutaTelematica = ricevutaTelematica;
	}

	@XmlElement(required = true)
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}

	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	
}
