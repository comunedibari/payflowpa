
package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import it.nch.eb.common.utils.StringUtils;


public class AllegatoModel  implements it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps, java.io.Serializable   {
	private static final long	serialVersionUID	= -666L;


		private java.lang.String tiAllegato;
		private java.lang.String titolo;
		private java.lang.String tiCodificaBody;
		private byte[] datiBody;
		private java.lang.String idAntifalsific;
		private java.lang.String idPendenza;
		private java.lang.String idAllegato;
		private java.sql.Timestamp tsDecorrenza;
		private java.lang.String flContesto;
		private java.lang.String idCondizione;
		private java.lang.String stRiga;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
	

			public java.lang.String getTiAllegato() {
				return tiAllegato;
			}

			public void setTiAllegato(java.lang.String tiAllegato) {
				this.tiAllegato = tiAllegato;
			}
			public java.lang.String getTitolo() {
				return titolo;
			}

			public void setTitolo(java.lang.String titolo) {
				this.titolo = titolo;
			}
			public java.lang.String getTiCodificaBody() {
				return tiCodificaBody;
			}

			public void setTiCodificaBody(java.lang.String tiCodificaBody) {
				this.tiCodificaBody = tiCodificaBody;
			}
			public byte[] getDatiBody() {
				return datiBody;
			}

			public void setDatiBody(byte[] datiBody) {
				this.datiBody = datiBody;
			}
			public java.lang.String getIdAntifalsific() {
				return idAntifalsific;
			}

			public void setIdAntifalsific(java.lang.String idAntifalsific) {
				this.idAntifalsific = idAntifalsific;
			}
			public java.lang.String getIdPendenza() {
				return idPendenza;
			}

			public void setIdPendenza(java.lang.String idPendenza) {
				this.idPendenza = idPendenza;
			}
			public java.lang.String getIdAllegato() {
				return idAllegato;
			}

			public void setIdAllegato(java.lang.String idAllegato) {
				this.idAllegato = idAllegato;
			}
			public java.sql.Timestamp getTsDecorrenza() {
				return tsDecorrenza;
			}

			public void setTsDecorrenza(java.sql.Timestamp tsDecorrenza) {
				this.tsDecorrenza = tsDecorrenza;
			}
			public java.lang.String getFlContesto() {
				return flContesto;
			}

			public void setFlContesto(java.lang.String flContesto) {
				this.flContesto = flContesto;
			}
			public java.lang.String getIdCondizione() {
				return idCondizione;
			}

			public void setIdCondizione(java.lang.String idCondizione) {
				this.idCondizione = idCondizione;
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