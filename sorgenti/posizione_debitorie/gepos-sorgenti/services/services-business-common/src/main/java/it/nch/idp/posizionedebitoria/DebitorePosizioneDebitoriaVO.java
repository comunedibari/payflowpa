package it.nch.idp.posizionedebitoria;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.Serializable;


public class DebitorePosizioneDebitoriaVO implements Serializable
{
	private String coDestinatario;
	private String desDestinatario;

	public String getCoDestinatario() {
		if(coDestinatario.equalsIgnoreCase(ConfigurationPropertyLoader.getInstance( "iris-fe.properties").getProperty("iris.codice.fiscale.anonymous")))
			return "ANONIMO";
		else
			return coDestinatario;
	}

	public String getDesDestinatario() {
		return desDestinatario;
	}

	public void setCoDestinatario(String string) {
		coDestinatario = string;
	}

	public void setDesDestinatario(String string) {
		desDestinatario = string;
	}

}
