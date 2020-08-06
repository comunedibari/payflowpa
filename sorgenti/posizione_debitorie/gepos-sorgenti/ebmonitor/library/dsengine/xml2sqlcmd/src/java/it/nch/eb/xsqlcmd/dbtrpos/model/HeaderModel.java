
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;


public class HeaderModel  implements java.io.Serializable   {

		private static final long serialVersionUID = -666L;
		private java.lang.String serviceName;
		private java.lang.String senderE2EMsgId;
		private java.sql.Timestamp senderXMLCrtDt;
		private java.lang.String senderId;
		private java.lang.String senderSys;
		private java.lang.String receiverId;
		private java.lang.String receiverSys;
		private java.lang.String receiverE2EMsgId;
		private java.sql.Timestamp receiverXMLCrtDt;
	

		public java.lang.String getServiceName() {
			return serviceName;
		}

		public void setServiceName(java.lang.String serviceName) {
			this.serviceName = serviceName;
		}
		public java.lang.String getSenderE2EMsgId() {
			return senderE2EMsgId;
		}

		public void setSenderE2EMsgId(java.lang.String senderE2EMsgId) {
			this.senderE2EMsgId = senderE2EMsgId;
		}
		public java.sql.Timestamp getSenderXMLCrtDt() {
			return senderXMLCrtDt;
		}

		public void setSenderXMLCrtDt(java.sql.Timestamp senderXMLCrtDt) {
			this.senderXMLCrtDt = senderXMLCrtDt;
		}
		public java.lang.String getSenderId() {
			return senderId;
		}

		public void setSenderId(java.lang.String senderId) {
			this.senderId = senderId;
		}
		public java.lang.String getSenderSys() {
			return senderSys;
		}

		public void setSenderSys(java.lang.String senderSys) {
			this.senderSys = senderSys;
		}
		public java.lang.String getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(java.lang.String receiverId) {
			this.receiverId = receiverId;
		}
		public java.lang.String getReceiverSys() {
			return receiverSys;
		}

		public void setReceiverSys(java.lang.String receiverSys) {
			this.receiverSys = receiverSys;
		}
		public java.lang.String getReceiverE2EMsgId() {
			return receiverE2EMsgId;
		}

		public void setReceiverE2EMsgId(java.lang.String receiverE2EMsgId) {
			this.receiverE2EMsgId = receiverE2EMsgId;
		}
		public java.sql.Timestamp getReceiverXMLCrtDt() {
			return receiverXMLCrtDt;
		}

		public void setReceiverXMLCrtDt(java.sql.Timestamp receiverXMLCrtDt) {
			this.receiverXMLCrtDt = receiverXMLCrtDt;
		}
	
		public String toString() {
			return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
		}
	
}