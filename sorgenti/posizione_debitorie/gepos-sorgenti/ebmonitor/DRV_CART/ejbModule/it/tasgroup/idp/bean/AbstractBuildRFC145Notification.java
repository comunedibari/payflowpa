package it.tasgroup.idp.bean;

import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder;
import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder.EnumVersioniNotificaRFC145;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractBuildRFC145Notification extends AbstractBuildNotification {
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	/**
	 *
	 * @param tipoSpontaneo
	 * @param model
	 * @param build
	 * @return
	 * @throws Exception
	 */
	protected String buildXmlNotifica(String tipoSpontaneo,
			NotifichePagamentoModel model, EntityManager em)
			throws Exception {
		
		//la creazione del messaggio xml va fatta ad ogni salto codice !!!
		//creo il messaggio di notifica
		//questo va creato a 'saldo di codice' ID_ENTE (sicuro) e CD_TRB_ENTE (?)
		NotifichePagamentoBuilder build = new NotifichePagamentoBuilder();
		build.setTemplateVersion(getVersione());
		
		String notifica = "";
		//da sistemare, gestisco i vari casi ,
		//pagamento pendenze o pagamento spontaneo
		if (tipoSpontaneo.equals("PEND")) {
			notifica = build.creaEsitoPagamentoPendenze(model);
		} else if (tipoSpontaneo.equals("Multa")) {
			throw new UnsupportedOperationException("Tipo Spontaneo Multa obsoleto, passato a gestione Plugins");
			//notifica = build.creaEsitoPagamentoSpontaneo(model);
		}
		logger.debug(" xml notifica = " + notifica );
		return notifica;
	}
	
	/**
	 * Da definire a cura degli implementor (uno per versione..)
	 * @return
	 */
	protected abstract EnumVersioniNotificaRFC145 getVersione();

		
}
