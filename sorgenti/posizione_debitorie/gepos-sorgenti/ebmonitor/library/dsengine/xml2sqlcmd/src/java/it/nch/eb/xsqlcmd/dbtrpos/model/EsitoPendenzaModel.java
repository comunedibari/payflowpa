
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;


public class EsitoPendenzaModel  {

		private java.lang.String e2emsgid;
		private java.lang.String senderId;
		private java.lang.String senderSys;
		private java.lang.String idEsitoPendenza;
		private java.lang.String esitoPendenza;
		private java.lang.String descrizioneEsito;
		private java.lang.String stato;
		private java.lang.String idPendenza;
		private java.lang.String idPendenzaEnte;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
	

			public java.lang.String getE2emsgid() {
				return e2emsgid;
			}

			public void setE2emsgid(java.lang.String e2emsgid) {
				this.e2emsgid = e2emsgid;
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
			public java.lang.String getIdEsitoPendenza() {
				return idEsitoPendenza;
			}

			public void setIdEsitoPendenza(java.lang.String idEsitoPendenza) {
				this.idEsitoPendenza = idEsitoPendenza;
			}
			public java.lang.String getEsitoPendenza() {
				return esitoPendenza;
			}

			public void setEsitoPendenza(java.lang.String esitoPendenza) {
				this.esitoPendenza = esitoPendenza;
			}
			public java.lang.String getDescrizioneEsito() {
				return descrizioneEsito;
			}

			public void setDescrizioneEsito(java.lang.String descrizioneEsito) {
				this.descrizioneEsito = descrizioneEsito;
			}
			public java.lang.String getStato() {
				return stato;
			}

			public void setStato(java.lang.String stato) {
				this.stato = stato;
			}
			public java.lang.String getIdPendenza() {
				return idPendenza;
			}

			public void setIdPendenza(java.lang.String idPendenza) {
				this.idPendenza = idPendenza;
			}
			public java.lang.String getIdPendenzaEnte() {
				return idPendenzaEnte;
			}

			public void setIdPendenzaEnte(java.lang.String idPendenzaEnte) {
				this.idPendenzaEnte = idPendenzaEnte;
			}
			public java.lang.Integer getPrVersione() {
				return prVersione;
			}

			public void setPrVersione(java.lang.Integer prVersione) {
				this.prVersione = prVersione;
			}
			public java.lang.String getOpInserimento() {
				return opInserimento;
			}

			public void setOpInserimento(java.lang.String opInserimento) {
				this.opInserimento = opInserimento;
			}
			public java.sql.Timestamp getTsInserimento() {
				return tsInserimento;
			}

			public void setTsInserimento(java.sql.Timestamp tsInserimento) {
				this.tsInserimento = tsInserimento;
			}
			
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
	}
	
}