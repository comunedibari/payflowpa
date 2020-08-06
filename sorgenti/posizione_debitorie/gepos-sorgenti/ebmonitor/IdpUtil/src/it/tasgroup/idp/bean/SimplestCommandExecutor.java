package it.tasgroup.idp.bean;

import it.tasgroup.idp.pojo.MonitoringData;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamentiEsito;

public interface SimplestCommandExecutor {

	public abstract IdpVerificaStatoPagamentiEsito verificaStatoPagamento(String message, String idEgov,
			String hash);

	public abstract MonitoringData informativaPagamentiEsito(String message, String idEgov,
			String hash);

	public abstract IdpAllineamentoPendenzeEnteOTFEsito allineamentoPendenzeOTF(String message, String idEgov,
			String hash);

	public abstract MonitoringData allineamentoPendenze(String contenutoXml, String idEgov,
			String hash);

	public abstract MonitoringData executeApplicationTransaction(String message,
			String idEgov, String hash, String method);

}