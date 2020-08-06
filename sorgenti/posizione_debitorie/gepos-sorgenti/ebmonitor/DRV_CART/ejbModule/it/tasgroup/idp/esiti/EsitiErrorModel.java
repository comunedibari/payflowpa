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

import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Battaglil
 *
 * Mapping della testata del messaggio di esito
 *
 * inoltre contiene due liste, una utilizzata per scrivere gli errori di validazione,
 * l'altra per scrivere gli errori di business
 *
 *  queste due liste non possono essere valorizzate contemporaneamente
 */
public class EsitiErrorModel extends EsitiModel {

	private String serviceNameType;
	private String receiverId;
	private String receiverSys;
	private String dataOraCreazioneEsito;

//	private String codiceErrore;
//	private String dettaglioErrore;

	private List<ExtendedErrorInfoImpl> erroriValidazione = new ArrayList<ExtendedErrorInfoImpl>();


	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverSys() {
		return receiverSys;
	}
	public void setReceiverSys(String receiverSys) {
		this.receiverSys = receiverSys;
	}
//	public String getCodiceErrore() {
//		return codiceErrore;
//	}
//	public void setCodiceErrore(String codiceErrore) {
//		this.codiceErrore = codiceErrore;
//	}
//	public String getDettaglioErrore() {
//		return dettaglioErrore;
//	}
//	public void setDettaglioErrore(String dettaglioErrore) {
//		this.dettaglioErrore = dettaglioErrore;
//	}

	public void setErroriValidazione(List<ExtendedErrorInfoImpl> erroriValidazione) {
		this.erroriValidazione = erroriValidazione;
	}
	public List<ExtendedErrorInfoImpl> getErroriValidazione() {
		return erroriValidazione;
	}
	public void add(ExtendedErrorInfoImpl extendedError) {
		this.erroriValidazione.add(extendedError);
	}

	public String getDataOraCreazioneEsito() {
		return dataOraCreazioneEsito;
	}
	public void setDataOraCreazioneEsito(String dataOraCreazioneEsito) {
		this.dataOraCreazioneEsito = dataOraCreazioneEsito;
	}
	public String getServiceNameType() {
		return serviceNameType;
	}
	public void setServiceNameType(String serviceNameType) {
		this.serviceNameType = serviceNameType;
	}

}
