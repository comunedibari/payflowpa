package it.tasgroup.idp.cart.core.dto;


import java.io.Serializable;
import java.util.Date;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;

import it.tasgroup.idp.cart.core.utils.DOMUtils;

/**
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: IdpHeaderDTO.java 487 2014-02-12 12:13:57Z nardi $
 */
public class IdpHeaderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5134184354610826240L;

	private String senderId;
	private String senderSys;
	private String receiverId;
	private String receiverSys;
	
	private String senderIdE;
	private String senderSysE;
	private String receiverIdE;
	private String receiverSysE;
	
	private String service;
	private String e2eMsgId;
	private String versione;
	private XMLGregorianCalendar xmlCrtDt;

	// Valorizzato solo dopo esser stato inserito in database
	private Integer prgE2eMsgId;
	private Integer prgEsito;
	
	public IdpHeaderDTO(SOAPMessage soap, String idRequest, Log log) throws Exception {
		SOAPBody body = null;

		try {
			body = soap.getSOAPBody();
			_IdpHeaderDTO(body, idRequest, log);
		} catch (SOAPException e) {
			throw new Exception("Impossibile recuperare il SOAPBody", e);
		}

	}
	
	private void _IdpHeaderDTO(SOAPElement body, String idRequest, Log log) throws Exception {

		try {
			setSenderId(DOMUtils.getChildElement(body, "SenderId").getValue());
			//log.debug("[" + idRequest + "] SenderId = [" + getSenderId() + "]");
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di SenderId", e);
		}
		try {
			String senderSys = DOMUtils.getChildElement(body, "SenderSys").getValue();
			if("SIL_RT_INVIONOTIFICAPAGAMENTO_GRAM".equals(senderSys))
				setSenderSys("SIL_RTOSCANA_GRAM");
			else
				setSenderSys(senderSys);
			//log.debug("[" + idRequest + "] SenderSys = [" + getSenderSys() + "]");
		} catch (Exception e) {
			throw new Exception(
					"Impossibile recuperare il valore di SenderSys", e);
		}
		try {
			setReceiverId(DOMUtils.getChildElement(body, "ReceiverId").getValue());
			//log.debug("[" + idRequest + "] ReceiverId = [" + getReceiverId() + "]");
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di ReceiverId", e);
		}
		try {
			setReceiverSys(DOMUtils.getChildElement(body, "ReceiverSys").getValue());
			//log.debug("[" + idRequest + "] ReceiverSys = [" + getReceiverSys() + "]");
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di ReceiverSys", e);
		}
		try {
			setService(DOMUtils.getChildElement(body, "ServiceName").getValue());
			//log.debug("[" + idRequest + "] ServiceName = [" + getServiceName() + "]");
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di ServiceName", e);
		}
		try {
			setE2eMsgId(DOMUtils.getChildElement(body, "E2EMsgId").getValue());
			//log.debug("[" + idRequest + "] E2EMsgId = [" + getE2eMsgId() + "]");
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di E2EMsgId", e);
		}
		try {
			String stringCrtDt = DOMUtils.getChildElement(body, "XMLCrtDt").getValue();
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(stringCrtDt);
			setXmlCrtDt(xgc);
			//log.debug("[" + idRequest + "] XMLCrtDt = [" + getXmlCrtDt() + "]");
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di XMLCrtDt", e);
		}
		
		try {
			setSenderIdE(DOMUtils.getChildElement(body, "E2ESndrId").getValue());
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di SenderIdE", e);
		}
		try {
			setSenderSysE(DOMUtils.getChildElement(body, "E2ESndrSys").getValue());
		} catch (Exception e) {
			throw new Exception(
					"Impossibile recuperare il valore di SenderSysE", e);
		}
		try {
			setReceiverIdE(DOMUtils.getChildElement(body, "E2ERcvrId").getValue());
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di ReceiverIdE", e);
		}
		try {
			setReceiverSysE(DOMUtils.getChildElement(body, "E2ERcvrSys").getValue());
		} catch (Exception e) {
			throw new Exception("Impossibile recuperare il valore di ReceiverSysE", e);
		}
		
		
	}

	public IdpHeaderDTO() {

	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderSys() {
		return senderSys;
	}

	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
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

	public String getServiceName() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getE2eMsgId() {
		return e2eMsgId;
	}

	public void setE2eMsgId(String e2eMsgId) {
		this.e2eMsgId = e2eMsgId;
	}

	public XMLGregorianCalendar getXmlCrtDt() {
		return xmlCrtDt;
	}
	
	public Date getData(){
		return xmlCrtDt != null ? xmlCrtDt.toGregorianCalendar().getTime() : null;
	}

	public void setXmlCrtDt(XMLGregorianCalendar xmlCrtDt) {
		this.xmlCrtDt = xmlCrtDt;
	}

	public String getVersione() {
		return versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

	public Integer getPrgE2eMsgId() {
		return prgE2eMsgId;
	}

	public void setPrgE2eMsgId(Integer prgE2eMsgId) {
		this.prgE2eMsgId = prgE2eMsgId;
	}

	public Integer getPrgEsito() {
		return prgEsito;
	}

	public void setPrgEsito(Integer prgEsito) {
		this.prgEsito = prgEsito;
	}

	public String getSenderIdE() {
		return senderIdE;
	}

	public void setSenderIdE(String senderIdE) {
		this.senderIdE = senderIdE;
	}

	public String getSenderSysE() {
		return senderSysE;
	}

	public void setSenderSysE(String senderSysE) {
		this.senderSysE = senderSysE;
	}

	public String getReceiverIdE() {
		return receiverIdE;
	}

	public void setReceiverIdE(String receiverIdE) {
		this.receiverIdE = receiverIdE;
	}

	public String getReceiverSysE() {
		return receiverSysE;
	}

	public void setReceiverSysE(String receiverSysE) {
		this.receiverSysE = receiverSysE;
	}
}
