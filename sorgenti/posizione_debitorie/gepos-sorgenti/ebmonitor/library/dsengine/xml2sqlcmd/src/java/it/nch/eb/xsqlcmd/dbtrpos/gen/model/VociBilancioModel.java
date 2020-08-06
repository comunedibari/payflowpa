
package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import it.nch.eb.common.utils.StringUtils;


public class VociBilancioModel  implements it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps, java.io.Serializable   {
	private static final long	serialVersionUID	= -666L;


		private java.lang.String tiVoce;
		private java.lang.String coVoce;
		private java.lang.String deVoce;
		private java.math.BigDecimal imVoce;
		private java.lang.String coCapbilancio;
		private java.lang.String coAccertamento;
		private java.lang.String idPendenza;
		private java.lang.String idCondizione;
		private java.lang.String idVoce;
		private java.sql.Timestamp tsDecorrenza;
		private java.lang.String stRiga;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
	

			public java.lang.String getTiVoce() {
				return tiVoce;
			}

			public void setTiVoce(java.lang.String tiVoce) {
				this.tiVoce = tiVoce;
			}
			public java.lang.String getCoVoce() {
				return coVoce;
			}

			public void setCoVoce(java.lang.String coVoce) {
				this.coVoce = coVoce;
			}
			public java.lang.String getDeVoce() {
				return deVoce;
			}

			public void setDeVoce(java.lang.String deVoce) {
				this.deVoce = deVoce;
			}
			public java.math.BigDecimal getImVoce() {
				return imVoce;
			}

			public void setImVoce(java.math.BigDecimal imVoce) {
				this.imVoce = imVoce;
			}
			public java.lang.String getCoCapbilancio() {
				return coCapbilancio;
			}

			public void setCoCapbilancio(java.lang.String coCapbilancio) {
				this.coCapbilancio = coCapbilancio;
			}
			public java.lang.String getCoAccertamento() {
				return coAccertamento;
			}

			public void setCoAccertamento(java.lang.String coAccertamento) {
				this.coAccertamento = coAccertamento;
			}
			public java.lang.String getIdPendenza() {
				return idPendenza;
			}

			public void setIdPendenza(java.lang.String idPendenza) {
				this.idPendenza = idPendenza;
			}
			public java.lang.String getIdCondizione() {
				return idCondizione;
			}

			public void setIdCondizione(java.lang.String idCondizione) {
				this.idCondizione = idCondizione;
			}
			public java.lang.String getIdVoce() {
				return idVoce;
			}

			public void setIdVoce(java.lang.String idVoce) {
				this.idVoce = idVoce;
			}
			public java.sql.Timestamp getTsDecorrenza() {
				return tsDecorrenza;
			}

			public void setTsDecorrenza(java.sql.Timestamp tsDecorrenza) {
				this.tsDecorrenza = tsDecorrenza;
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