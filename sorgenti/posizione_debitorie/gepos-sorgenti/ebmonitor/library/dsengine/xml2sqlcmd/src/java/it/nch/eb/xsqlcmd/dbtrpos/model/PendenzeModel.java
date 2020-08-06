
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.xsqlcmd.dbtrpos.model.OperationKinds.OperationKind;


public class PendenzeModel  extends it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel {

		private static final long serialVersionUID = -743310593059811141L;
		private String msgid;
		private java.util.Collection condizioniDiPagamento;
		private java.util.Collection destinatari;
		private java.util.Collection allegati;
		private java.lang.Boolean insert;
		private java.lang.Boolean update;
		private java.lang.Boolean updateMassivo;
		private java.lang.Boolean replace;
		private java.lang.Boolean delete;
		
		
		
		private boolean hidden = false;

		private String senderId;

		private String replacingPendenzaId;
		private OperationKind operationKind;

		public String getOperationName() {
			if (getInsert().booleanValue()) return "insert";
			else if (getUpdate().booleanValue()) return "update";
			else if (getReplace().booleanValue()) return "replace";
			else if (getDelete().booleanValue()) return "delete";
			else if (getUpdateMassivo().booleanValue()) return "updateMassivo";
			else throw new IllegalStateException("bug");
		}

		public OperationKind getTipoOperazione() {
			return operationKind;
		}

		public String getSenderId() {
			return senderId;
		}

		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}

		public void setTipoOperazione(OperationKind opKnd) {
			this.operationKind = opKnd;
		}

		public String getMsgid() {
			return msgid;
		}

		public void setMsgid(String msgid) {
			this.msgid = msgid;
		}

		public java.util.Collection getCondizioniDiPagamento() {
			return condizioniDiPagamento;
		}
	
		public void setCondizioniDiPagamento(java.util.Collection condizioniDiPagamento) {
			this.condizioniDiPagamento = condizioniDiPagamento;
		}
		public java.util.Collection getDestinatari() {
			return destinatari;
		}
	
		public void setDestinatari(java.util.Collection destinatari) {
			this.destinatari = destinatari;
		}
	
		public java.util.Collection getAllegati() {
			return allegati;
		}
	
		public void setAllegati(java.util.Collection allegati) {
			this.allegati = allegati;
		}
	
		public java.lang.Boolean getInsert() {
			return insert;
		}
	
		public void setInsert(java.lang.Boolean insert) {
			this.insert = insert;
		}
		public java.lang.Boolean getUpdate() {
			return update;
		}
	
		public void setUpdate(java.lang.Boolean update) {
			this.update = update;
		}
		public java.lang.Boolean getUpdateMassivo() {
			return updateMassivo;
		}
	
		public void setUpdateMassivo(java.lang.Boolean updateMassivo) {
			this.updateMassivo = updateMassivo;
		}
		public java.lang.Boolean getReplace() {
			return replace;
		}
	
		public void setReplace(java.lang.Boolean replace) {
			this.replace = replace;
		}
		public java.lang.Boolean getDelete() {
			return delete;
		}
	
		public void setDelete(java.lang.Boolean delete) {
			this.delete = delete;
		}
	
		public String getReplacingPendenzaId() {
			return replacingPendenzaId;
		}
	
		public void setReplacingPendenzaId(String replacingPendenzaId) {
			this.replacingPendenzaId = replacingPendenzaId;
		}

		public String toString() {
			return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
		}
		
		public boolean isHidden() {
			return hidden;
		}

		public void setHidden(boolean hidden) {
			this.hidden = hidden;
		}
		

}