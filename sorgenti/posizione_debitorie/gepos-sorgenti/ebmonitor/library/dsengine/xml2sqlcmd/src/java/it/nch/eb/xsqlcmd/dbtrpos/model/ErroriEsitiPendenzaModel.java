
package it.nch.eb.xsqlcmd.dbtrpos.model;

import it.nch.eb.common.utils.StringUtils;


public class ErroriEsitiPendenzaModel  implements  java.io.Serializable   {
	private static final long	serialVersionUID	= -666L;


		private java.lang.String idEsitoPendenza;
		private java.lang.String idPendenza;
		private java.lang.String idErrore;
		private java.lang.String codice;
		private java.lang.String idPendenzaEnte;
		private java.lang.String descrizioneErrore;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
	

			public java.lang.String getIdEsitoPendenza() {
				return idEsitoPendenza;
			}

			public void setIdEsitoPendenza(java.lang.String idEsitoPendenza) {
				this.idEsitoPendenza = idEsitoPendenza;
			}
			public java.lang.String getIdPendenza() {
				return idPendenza;
			}

			public void setIdPendenza(java.lang.String idPendenza) {
				this.idPendenza = idPendenza;
			}
			public java.lang.String getIdErrore() {
				return idErrore;
			}

			public void setIdErrore(java.lang.String idErrore) {
				this.idErrore = idErrore;
			}
			public java.lang.String getCodice() {
				return codice;
			}

			public void setCodice(java.lang.String codice) {
				this.codice = codice;
			}
			public java.lang.String getIdPendenzaEnte() {
				return idPendenzaEnte;
			}

			public void setIdPendenzaEnte(java.lang.String idPendenzaEnte) {
				this.idPendenzaEnte = idPendenzaEnte;
			}
			public java.lang.String getDescrizioneErrore() {
				return descrizioneErrore;
			}

			public void setDescrizioneErrore(java.lang.String descrizioneErrore) {
				this.descrizioneErrore = descrizioneErrore;
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