package it.tasgroup.idp.rs.model.pagamenti;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaAnnullaNDP {

	private String identificativoDominio;
	private String identificativoUnivocoVersamento;	
	
	
	public RichiestaAnnullaNDP() {

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
	
}
