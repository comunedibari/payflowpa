/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.notifiche;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.stringtemplate.BaseTemplateModel;
import it.nch.eb.stringtemplate.TemplateWriter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Battaglil
 * 
 * Questo oggetto ha il compito di costruire un file xml rispondente allo schema di Esito Allineam Pend
 * Utilizza la libreria template writer ed è manovrato dagli oggetti 
 * SpedizioneEsiti
 * Timeout
 * CreazioneEsito
 *
 */
public class NotifichePagamentoBuilder {
 
	public static enum EnumVersioniNotificaRFC145 {
		v010300,
		v010302,
		v010303
	}
	
	private static final String NOTIFICA_PAGAMENTO_STG = "it/tasgroup/idp/notifiche/notifichePagamento-${templateVersion}.stg";
	//private static final String NOTIFICA_PAGAMENTO_SPONTANEO_STG = "it/tasgroup/idp/notifiche/notifichePagamentoSpontaneo.stg";
	private final Log logger = LogFactory.getLog(this.getClass());	
	private EnumVersioniNotificaRFC145 templateVersion = EnumVersioniNotificaRFC145.v010300; //Default
	
	/**
	 * 
	 * @throws Exception 
	 */
	public String creaEsitoPagamentoPendenze(NotifichePagamentoModel notifichePagamentoModel) throws Exception {
		
		//modello per gli esiti
//		NotifichePagamentoModel notifichePagamentoModel = new NotifichePagamentoModel();

		//recupero il modello per la costruzione dell notifica
		ByteArrayOutputStream output;
		
		try {
			
			output = new ByteArrayOutputStream();
			BufferedOutputStream buf = new BufferedOutputStream(output);
			
			String templateFullName = NOTIFICA_PAGAMENTO_STG.replace("${templateVersion}", templateVersion.toString());
			
			Reader rdr = ResourceLoaders.CLASSPATH.loadReader(templateFullName);
			TemplateWriter tw = new TemplateWriter(rdr, buf);
			BaseTemplateModel tm = new BaseTemplateModel();
			
			tm.set("notificaPagamentoModel", notifichePagamentoModel);				
			tw.write("templateNotificaPagamento", tm);			

		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			logger.error( this.getClass().getSimpleName() + " NotificaPagamento, errore creazione = " + e.getMessage() );
			throw new Exception(e.getMessage());
		} 
		
//		logger.info( this.getClass().getSimpleName() + " NotificaPagamento, messaggio = " + output.toString() );
		return output.toString();
	}

//	/**
//	 * 
//	 * @throws Exception 
//	 */
//	public String creaEsitoPagamentoSpontaneo(NotifichePagamentoModel notifichePagamentoModel) throws Exception {
//		
//		//modello per gli esiti
////		NotifichePagamentoModel notifichePagamentoModel = new NotifichePagamentoModel();
//
//		//recupero il modello per la costruzione dell notifica
//		ByteArrayOutputStream output;
//		
//		try {
//			
//			output = new ByteArrayOutputStream();
//			BufferedOutputStream buf = new BufferedOutputStream(output);
//			Reader rdr = ResourceLoaders.CLASSPATH.loadReader(NOTIFICA_PAGAMENTO_SPONTANEO_STG);
//			TemplateWriter tw = new TemplateWriter(rdr, buf);
//			BaseTemplateModel tm = new BaseTemplateModel();
//			
//			tm.set("notificaPagamentoModel", notifichePagamentoModel);				
//			tw.write("templateNotificaPagamento", tm);			
//
//		} catch (RuntimeException e) {
//			// TODO Auto-generated catch block
//			logger.info( this.getClass().getSimpleName() + " NotificaPagamento, errore creazione = " + e.getMessage() );
//			e.printStackTrace();
//			throw new Exception(e.getMessage());
//		} 
//		
////		logger.info( this.getClass().getSimpleName() + " NotificaPagamento, messaggio = " + output.toString() );
//		return output.toString();
//	}

	public void setTemplateVersion(EnumVersioniNotificaRFC145 versione) {
		this.templateVersion=versione;	
	}

}
