/**
 * 
 */
package it.tasgroup.idp.bean;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class ObjectMessageContainer implements Serializable {
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String senderId;
   private String senderSys;
   private String receiverId;
   private String receiverSys;
   private String e2eMsgId;
   private String msgContent;
   private String cdTrbEnte;
   private String idEnte;
   private String tipoNotifica;
 
   
   
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
public String getE2eMsgId() {
	return e2eMsgId;
}
public void setE2eMsgId(String e2eMsgId) {
	this.e2eMsgId = e2eMsgId;
}
public String getMsgContent() {
	return msgContent;
}
public void setMsgContent(String msgContent) {
	this.msgContent = msgContent;
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

   public String getCdTrbEnte() {
	return cdTrbEnte;
}
public void setCdTrbEnte(String cdTrbEnte) {
	this.cdTrbEnte = cdTrbEnte;
}
public String getIdEnte() {
	return idEnte;
}
public void setIdEnte(String idEnte) {
	this.idEnte = idEnte;
}
public String getTipoNotifica() {
	return tipoNotifica;
}
public void setTipoNotifica(String tipoNotifica) {
	this.tipoNotifica = tipoNotifica;
}
@Override
   public String toString() {
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("receiverId=");
	   sb.append(receiverId);
	   sb.append(";receiverSys=");
	   sb.append(receiverSys);
	   sb.append("senderId=");
	   sb.append(senderId);
	   sb.append(";senderSys=");
	   sb.append(senderSys);
	   sb.append(";e2eMsgId=");
	   sb.append(e2eMsgId);	   
	   sb.append(";idEnte=");
	   sb.append("idEnte");
	   sb.append(";cdTrbEnte=");
	   sb.append("cdTrbEnte");
	   sb.append(";tipoNotifica=");
	   sb.append("tipoNotifica");
	   sb.append(";msgContent=");
	   sb.append("msgContent");
	   return sb.toString();
	   
   }

}
