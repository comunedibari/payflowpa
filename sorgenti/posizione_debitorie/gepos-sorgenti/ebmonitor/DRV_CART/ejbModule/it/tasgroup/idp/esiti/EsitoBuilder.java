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
package it.tasgroup.idp.esiti;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.stringtemplate.BaseTemplateModel;
import it.nch.eb.stringtemplate.TemplateWriter;
import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;
import it.tasgroup.idp.util.StatoEnum;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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
public class EsitoBuilder {

	private static final String ESITO_FILE_STG = "it/tasgroup/idp/esiti/esiti103.stg";
	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 *
	 * @throws Exception
	 */
	public String creaEsito(List<EsitoPendenza> listEsitoPendenza) throws Exception {

		boolean errori = false;
		//modello per gli esiti
		EsitiErrorModel errModel = new EsitiErrorModel();
		EsitoPendenza ep = null;
		//recupero il modello per la costruzione dell esito
		ByteArrayOutputStream output;

		try {
			for (int i = 0; i < listEsitoPendenza.size(); i++) {
				ep = listEsitoPendenza.get(i);
				logger.debug( this.getClass().getSimpleName() + " Esito = " + ep.getEsito() + " , idPendenza = " + ep.getIdPendenza()
							+ " , idPendenzaEnte = " + ep.getIdPendenzaEnte());
				if (ep!=null && ep.getEsito()!=null
//						&& "KO".equals(ep.getEsito().trim())
						&& ep.getListErroriEsiti()!=null
						&& ep.getListErroriEsiti().size()>0
						) {
					errori = true;
					//aggiungo al modello solo gli esiti che hanno errori
					errModel.add(ep);
					//il messaggio in uscita deve contenere solo riferimenti alle pendenze con esito errato
					logger.debug( this.getClass().getSimpleName() + " N° Errori = " + ep.getListErroriEsiti().size());
				}
				errModel.setServiceNameType(ep.getServiceNameType().trim());
				errModel.setE2emsgid(ep.getE2emsgid().trim());
				errModel.setSenderId(ep.getSenderId().trim());
				errModel.setSenderSys(ep.getSenderSys().trim());
				errModel.setReceiverId(ep.getReceiverId().trim());
				errModel.setReceiverSys(ep.getReceiverSys().trim());
				errModel.setTrtSenderId(ep.getTrtReceiverId().trim());
				errModel.setTrtSenderSys(ep.getTrtReceiverSys().trim());
				Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
				String timeIso = "+01:00";
				if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
					//ora legale attiva
					timeIso = "+02:00";
				} 
				//il timestamp va sempre messo indipendentemente dal numero degli errori
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
				String tmsp = format.format(new Date(System.currentTimeMillis()));
				errModel.setDataOraCreazioneEsito(tmsp);
			}

			output = new ByteArrayOutputStream();
			BufferedOutputStream buf = new BufferedOutputStream(output);
			Reader rdr = ResourceLoaders.CLASSPATH.loadReader(ESITO_FILE_STG);
			TemplateWriter tw = new TemplateWriter(rdr, buf);
			BaseTemplateModel tm = new BaseTemplateModel();

			if (StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE.equals(errModel.getServiceNameType())) {
				if (errori) {
					logger.info( " EsitoXML, elaborato con errori, tipoServizio =  " + ep.getServiceNameType() );
					tm.set("errorModel", errModel);
					tw.write("templateEsitiKO", tm);
				} else {
					logger.info( " EsitoXML, elaborato senza errori, tipoServizio =  " + ep.getServiceNameType() );
					tm.set("errorModel", errModel);
					tw.write("templateEsiti", tm);
				}
			} else if (StatoEnum.SERVICE_NAME_TYPE_ALL_MASSIVO_PENDENZE.equals(errModel.getServiceNameType())) {
				if ("KO".equals(ep.getEsito())) {
					logger.info( " EsitoXML, elaborato con errori, tipoServizio =  " + ep.getServiceNameType() );
					tm.set("errorModel", errModel);
					tw.write("templateEsitiMassivoKO", tm);
				} else if ("OK".equals(ep.getEsito())) {
					logger.info( " EsitoXML, elaborato senza errori, tipoServizio =  " + ep.getServiceNameType() );
					tm.set("errorModel", errModel);
					tw.write("templateEsiti", tm);
				}
			}


		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			logger.info( this.getClass().getSimpleName() + " EsitoXML, errore creazione = " + e.getMessage() );
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		logger.info( this.getClass().getSimpleName() + " Esito Spedito, errori = " + errori );
		return output.toString();
	}

	/**
	 *
	 * @param listEsitoPendenza
	 * @return
	 * @throws Exception
	 */
	public String creaEsitoXmlNonValido(String serviceNameType, 
			String msgId, String idMittente,String silMittente, 
			String idRicevente, String silRicevente, 
			String errorCode, Set erroriValidazione,
			String trtIdMittente,String trtSilMittente) throws Exception {

		boolean errori = false;
		//modello per gli esiti
		EsitiErrorModel errModel = new EsitiErrorModel();
		//recupero il modello per la costruzione dell esito
		ByteArrayOutputStream output;

		errModel.setServiceNameType(serviceNameType);
		errModel.setE2emsgid(msgId);
		errModel.setSenderId(idMittente);
		errModel.setSenderSys(silMittente);
		errModel.setReceiverId(idRicevente);
		errModel.setReceiverSys(silRicevente);
		errModel.setTrtSenderId(trtIdMittente);
		errModel.setTrtSenderSys(trtSilMittente);		
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
		String timeIso = "+01:00";
		if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
			//ora legale attiva
			timeIso = "+02:00";
		} 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
		String tmsp = format.format(new Date());
		errModel.setDataOraCreazioneEsito(tmsp);

//		errModel.setCodiceErrore(codErrore);
//		String detailMax140 = detailErrore.length()>140 ? detailErrore.substring(0, 138) : detailErrore;
//		errModel.setDettaglioErrore(detailMax140);
		List setTruncated140 = new ArrayList<ExtendedErrorInfoImpl>();
		Iterator iter = erroriValidazione.iterator();
		
		while (iter.hasNext()) {
			ExtendedErrorInfoImpl type = (ExtendedErrorInfoImpl) iter.next();
			logger.info(" ERRORE " + errorCode + "DATASTORAGE >>>>>" + type.getErrorDetail());
			String detailErrore = "[Posizione = " + type.getErrorId() + "] " + type.getErrorDetail();
			//caso particolare del UPD MASSIVO
			detailErrore = "A0000019".equals(type.getErrorId()) ? type.getErrorDetail() : detailErrore;
			String detailMax140 = detailErrore.length()>140 ? detailErrore.substring(0, 138) : detailErrore;

			ExtendedErrorInfoImpl typeTrunc = null;
			//cambiare appena possibile questo codice !!!
			if ("A0000003".equals(errorCode) || "A0000017".equals(errorCode) || "A0000037".equals(errorCode)) {
				typeTrunc = new ExtendedErrorInfoImpl(errorCode,type.getErrorType(),type.getSeverity(),type.getNameSpaceURI(),type.getName(),type.getPath(),type.getValue(),type.getExpectedValue(),type.getErrorDetail());
			} else {
				//qui siamo nel caso "V0000001"==errorCode
				typeTrunc = new ExtendedErrorInfoImpl(errorCode,type.getErrorType(),type.getSeverity(),type.getNameSpaceURI(),type.getName(),type.getPath(),type.getValue(),type.getExpectedValue(),detailMax140);
			}
			setTruncated140.add(typeTrunc);
		}


		//aggiungo il set per creare l'esito con N tag di codice/descrizione errore
//		errModel.setErroriValidazione(new ArrayList<ExtendedErrorInfoImpl>(erroriValidazione));
		errModel.setErroriValidazione(setTruncated140);

		output = new ByteArrayOutputStream();
		BufferedOutputStream buf = new BufferedOutputStream(output);
		Reader rdr = ResourceLoaders.CLASSPATH.loadReader(ESITO_FILE_STG);
		TemplateWriter tw = new TemplateWriter(rdr, buf);
		BaseTemplateModel tm = new BaseTemplateModel();

		tm.set("errorModel", errModel);
		tw.write("templateEsitiKOGlobale", tm);

		logger.info( this.getClass().getSimpleName() + " Esito XML, errore = " + errorCode + " Spedito ");
		return output.toString();
	}

}
