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

import java.util.List;

/**
 * @author Battaglil
 *
 * Mapping del messaggio di notifica pagamento
 * 
 */
public class NotifichePagamentoModel  {
	
	private String receiverId;
	private String receiverSys;	
	public String e2emsgid;
	public String senderId;
	public String senderSys;
	
	public String trtReceiverId;
	public String trtReceiverSys;		
	
	private String dataOraCreazioneEsito;		
	
	private List<PagamentoModel> listaPagamenti; 

	public List<PagamentoModel> getListaPagamenti() {
		return listaPagamenti;
	}
	public void setListaPagamenti(List<PagamentoModel> listaPagamenti) {
		this.listaPagamenti = listaPagamenti;
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
	public String getDataOraCreazioneEsito() {
		return dataOraCreazioneEsito;
	}
	public void setDataOraCreazioneEsito(String dataOraCreazioneEsito) {
		this.dataOraCreazioneEsito = dataOraCreazioneEsito;
	}
	public String getTrtReceiverId() {
		return trtReceiverId;
	}
	public void setTrtReceiverId(String trtReceiverId) {
		this.trtReceiverId = trtReceiverId;
	}
	public String getTrtReceiverSys() {
		return trtReceiverSys;
	}
	public void setTrtReceiverSys(String trtReceiverSys) {
		this.trtReceiverSys = trtReceiverSys;
	}
	
}
