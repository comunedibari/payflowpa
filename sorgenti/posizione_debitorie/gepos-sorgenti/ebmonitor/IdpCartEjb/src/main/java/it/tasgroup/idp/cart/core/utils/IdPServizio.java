package it.tasgroup.idp.cart.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id$
 */

public enum IdPServizio {
	IdpAllineamentoPendenze, IdpInformativaPagamento, IdpConfigurazioneEnte, IdpRendicontazioneEnti, IdpAllineamentoPendenzeEnteEsito;

	public static IdPServizio getIdPServizio(String serviceName) throws Exception {
		if("IdpAllineamentoPendenze".equals(serviceName)) return IdPServizio.IdpAllineamentoPendenze;
		if("IdpInformativaPagamento".equals(serviceName)) return IdPServizio.IdpInformativaPagamento;
		if("IdpConfigurazioneEnte".equals(serviceName)) return IdPServizio.IdpConfigurazioneEnte;
		if("IdpRendicontazioneEnti".equals(serviceName)) return IdPServizio.IdpRendicontazioneEnti;
		throw new Exception("ServiceName non ha un valore valido: " + serviceName);
	}

	@Override
	public String toString(){
		switch (this) {
		case IdpAllineamentoPendenze:
			return "IdpAllineamentoPendenze";
		case IdpInformativaPagamento:
			return "IdpInformativaPagamento";
		case IdpConfigurazioneEnte:
			return "IdpConfigurazioneEnte";
		case IdpRendicontazioneEnti:
			return "IdpRendicontazioneEnti";
		case IdpAllineamentoPendenzeEnteEsito:
			return "IdpAllineamentoPendenzeEnte.Esito";
		default:
			return "Unknown";
		}
	}

	public static List<String> getElencoServizi() {
		List<String> elenco = new ArrayList<String>();
		elenco.add("IdpAllineamentoPendenze");
		elenco.add("IdpInformativaPagamento");
		elenco.add("IdpConfigurazioneEnte");
		elenco.add("IdpRendicontazioneEnte");
		elenco.add("IdpAllineamentoPendenzeEnte.Esito");
		return elenco;
	}

	public String getNomeEsecutore(){
		switch (this) {
		case IdpAllineamentoPendenze:
			return "IdpAP";
		case IdpInformativaPagamento:
			return "IdpIPE";
		case IdpConfigurazioneEnte:
			return "IdpCE";
		case IdpRendicontazioneEnti:
			return "IdpRE";
		case IdpAllineamentoPendenzeEnteEsito:
			return "IdpAPEnte.Esito";
		default:
			return "Unknown";
		}
	}
}
