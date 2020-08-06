
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;


public class AllineamentiModel  implements it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps, java.io.Serializable   {
	private static final long	serialVersionUID	= -666L;


		private java.lang.String idPendenzaEnte;
		private java.lang.String tipoOperazione;
		private java.sql.Timestamp flusso;
		private java.lang.String idAlpd;
		private java.lang.String utenteCreatore;
		private java.lang.String idMittente;
		private java.lang.String idSystem;
		private java.lang.String keyPendenza;
		private java.lang.String keyReplaced;
		private java.lang.String idMittenteTrt;
		private java.lang.String idSystemTrt;
		private java.lang.String coVersione;
		private java.lang.String stRiga;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
	

			public java.lang.String getIdPendenzaEnte() {
				return idPendenzaEnte;
			}

			public void setIdPendenzaEnte(java.lang.String idPendenzaEnte) {
				this.idPendenzaEnte = idPendenzaEnte;
			}
			public java.lang.String getTipoOperazione() {
				return tipoOperazione;
			}

			public void setTipoOperazione(java.lang.String tipoOperazione) {
				this.tipoOperazione = tipoOperazione;
			}
			public java.sql.Timestamp getFlusso() {
				return flusso;
			}

			public void setFlusso(java.sql.Timestamp flusso) {
				this.flusso = flusso;
			}
			public java.lang.String getIdAlpd() {
				return idAlpd;
			}

			public void setIdAlpd(java.lang.String idAlpd) {
				this.idAlpd = idAlpd;
			}
			public java.lang.String getUtenteCreatore() {
				return utenteCreatore;
			}

			public void setUtenteCreatore(java.lang.String utenteCreatore) {
				this.utenteCreatore = utenteCreatore;
			}
			public java.lang.String getIdMittente() {
				return idMittente;
			}

			public void setIdMittente(java.lang.String idMittente) {
				this.idMittente = idMittente;
			}
			public java.lang.String getIdSystem() {
				return idSystem;
			}

			public void setIdSystem(java.lang.String idSystem) {
				this.idSystem = idSystem;
			}
			public java.lang.String getKeyPendenza() {
				return keyPendenza;
			}

			public void setKeyPendenza(java.lang.String keyPendenza) {
				this.keyPendenza = keyPendenza;
			}
			public java.lang.String getKeyReplaced() {
				return keyReplaced;
			}

			public void setKeyReplaced(java.lang.String keyReplaced) {
				this.keyReplaced = keyReplaced;
			}
			public java.lang.String getIdMittenteTrt() {
				return idMittenteTrt;
			}

			public void setIdMittenteTrt(java.lang.String idMittenteTrt) {
				this.idMittenteTrt = idMittenteTrt;
			}
			public java.lang.String getIdSystemTrt() {
				return idSystemTrt;
			}

			public void setIdSystemTrt(java.lang.String idSystemTrt) {
				this.idSystemTrt = idSystemTrt;
			}
			public java.lang.String getCoVersione() {
				return coVersione;
			}

			public void setCoVersione(java.lang.String coVersione) {
				this.coVersione = coVersione;
			}
			public java.lang.String getStRiga() {
				return stRiga;
			}

			public void setStRiga(java.lang.String stRiga) {
				this.stRiga = stRiga;
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
			public java.lang.String getOpAggiornamento() {
				return opAggiornamento;
			}

			public void setOpAggiornamento(java.lang.String opAggiornamento) {
				this.opAggiornamento = opAggiornamento;
			}
			public java.sql.Timestamp getTsAggiornamento() {
				return tsAggiornamento;
			}

			public void setTsAggiornamento(java.sql.Timestamp tsAggiornamento) {
				this.tsAggiornamento = tsAggiornamento;
			}
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
	}
	
}