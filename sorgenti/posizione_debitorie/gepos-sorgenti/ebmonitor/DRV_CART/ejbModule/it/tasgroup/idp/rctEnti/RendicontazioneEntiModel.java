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
package it.tasgroup.idp.rctEnti;

import java.util.List;

/**
 * @author Battaglil
 *
 * Mapping del messaggio di notifica pagamento
 * 
 */
public class RendicontazioneEntiModel  {
	
	private String receiverId;
	private String receiverSys;	
	public String e2emsgid;
	public String senderId;
	public String senderSys;	
	
	private String importoTotale;
	private String dataInizioPeriodo;
	private String dataFinePeriodo;
	
	private String XMLCrtDt;
	
	private List<RendicontazioneContoModel> listaRendicontiConto;
  
    

	public List<RendicontazioneContoModel> getListaRendicontiConto() {
		return listaRendicontiConto;
	}
	public void setListaRendicontiConto(List<RendicontazioneContoModel> listaRendicontiConto) {
		this.listaRendicontiConto = listaRendicontiConto;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverSys() {
		return receiverSys;
	}
	public String getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(String importoTotale) {
		this.importoTotale = importoTotale;
	}
	public String getDataInizioPeriodo() {
		return dataInizioPeriodo;
	}
	public void setDataInizioPeriodo(String dataInizioPeriodo) {
		this.dataInizioPeriodo = dataInizioPeriodo;
	}
	public String getDataFinePeriodo() {
		return dataFinePeriodo;
	}
	public void setDataFinePeriodo(String dataFinePeriodo) {
		this.dataFinePeriodo = dataFinePeriodo;
	}
	public void setReceiverSys(String receiverSys) {
		this.receiverSys = receiverSys;
	}	
	public String getE2emsgid() {
		return this.e2emsgid;
	}
	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}
	public String getSenderId() {
		return this.senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderSys() {
		return this.senderSys;
	}
	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}

	public String getXMLCrtDt() {
		return XMLCrtDt;
	}
	public void setXMLCrtDt(String xMLCrtDt) {
		XMLCrtDt = xMLCrtDt;
	}	
	
}
