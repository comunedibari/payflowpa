
package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import it.nch.eb.common.utils.StringUtils;


public class CondizioniPagamentoModel  implements it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps, java.io.Serializable   {
	
		private static final long	serialVersionUID	= -666L;

		private java.lang.String tiPagamento;
		private java.lang.String idPagamento;
		
		private java.lang.String tiCip;
		private java.lang.String coCip;
		
		private java.sql.Date dtScadenza;
		private java.sql.Date dtInizioValidita;
		private java.sql.Date dtFineValidita;
		private java.math.BigDecimal imTotale;
		private java.lang.String stPagamento;
		private java.sql.Date dtPagamento;
		private java.lang.String deCanalepag;

		private java.math.BigDecimal imPagamento;
		private java.lang.String deNotePagamento;
		
		private java.lang.String stRiga;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String idPendenza;
		private java.lang.String idCondizione;
		private java.sql.Timestamp tsDecorrenza;
		private java.lang.String idEnte;
		private java.lang.String cdTrbEnte;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
		private java.lang.String opAnnullamento;
		private java.sql.Timestamp tsAnnullamento;
		private java.lang.Long tsAnnullamentoMillis = 0L;
		
		//nuovi campi per gestione cartella di pagamento
		private java.lang.String causalePagamento;
		private java.lang.String codiceIBAN;
		private java.lang.String beneficiario;
		private java.lang.String mezzoPagamento;		

		private java.lang.String tiOperazioneUpdate;
		

		public java.lang.String getTiPagamento() {
			return tiPagamento;
		}

		public void setTiPagamento(java.lang.String tiPagamento) {
			this.tiPagamento = tiPagamento;
		}
		public java.lang.String getIdPagamento() {
			return idPagamento;
		}

		public void setIdPagamento(java.lang.String idPagamento) {
			this.idPagamento = idPagamento;
		}
		public java.lang.String getTiCip() {
			return tiCip;
		}

		public void setTiCip(java.lang.String tiCip) {
			this.tiCip = tiCip;
		}
		public java.lang.String getCoCip() {
			return coCip;
		}

		public void setCoCip(java.lang.String coCip) {
			this.coCip = coCip;
		}
		public java.sql.Date getDtScadenza() {
			return dtScadenza;
		}

		public void setDtScadenza(java.sql.Date dtScadenza) {
			this.dtScadenza = dtScadenza;
		}
		public java.sql.Date getDtInizioValidita() {
			return dtInizioValidita;
		}

		public void setDtInizioValidita(java.sql.Date dtInizioValidita) {
			this.dtInizioValidita = dtInizioValidita;
		}
		public java.sql.Date getDtFineValidita() {
			return dtFineValidita;
		}

		public void setDtFineValidita(java.sql.Date dtFineValidita) {
			this.dtFineValidita = dtFineValidita;
		}
		public java.math.BigDecimal getImTotale() {
			return imTotale;
		}

		public void setImTotale(java.math.BigDecimal imTotale) {
			this.imTotale = imTotale;
		}
		public java.lang.String getStPagamento() {
			return stPagamento;
		}

		public void setStPagamento(java.lang.String stPagamento) {
			this.stPagamento = stPagamento;
		}
		public java.sql.Date getDtPagamento() {
			return dtPagamento;
		}

		public void setDtPagamento(java.sql.Date dtPagamento) {
			this.dtPagamento = dtPagamento;
		}
		public java.lang.String getDeCanalepag() {
			return deCanalepag;
		}

		public void setDeCanalepag(java.lang.String deCanalepag) {
			this.deCanalepag = deCanalepag;
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
		public java.sql.Timestamp getTsDecorrenza() {
			return tsDecorrenza;
		}

		public void setTsDecorrenza(java.sql.Timestamp tsDecorrenza) {
			this.tsDecorrenza = tsDecorrenza;
		}
		public java.lang.String getIdEnte() {
			return idEnte;
		}

		public void setIdEnte(java.lang.String idEnte) {
			this.idEnte = idEnte;
		}
		public java.lang.String getCdTrbEnte() {
			return cdTrbEnte;
		}

		public void setCdTrbEnte(java.lang.String cdTrbEnte) {
			this.cdTrbEnte = cdTrbEnte;
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
		public java.lang.String getOpAnnullamento() {
			return opAnnullamento;
		}

		public void setOpAnnullamento(java.lang.String opAnnullamento) {
			this.opAnnullamento = opAnnullamento;
		}
		public java.sql.Timestamp getTsAnnullamento() {
			return tsAnnullamento;
		}

		public void setTsAnnullamento(java.sql.Timestamp tsAnnullamento) {
			this.tsAnnullamento = tsAnnullamento;
			// Mantiene allineato ts_annullamentoMillis con ts_annullamento
			if (this.tsAnnullamento!=null) {
				this.tsAnnullamentoMillis=this.tsAnnullamento.getTime();
			} else {
				this.tsAnnullamentoMillis=0L;
			}
		}
	
		public String toString() {
			return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
		}
		
		public java.lang.String getCodiceIBAN() {
			return codiceIBAN;
		}

		public void setCodiceIBAN(java.lang.String codiceIBAN) {
			this.codiceIBAN = codiceIBAN;
		}

		public java.lang.String getBeneficiario() {
			return beneficiario;
		}

		public void setBeneficiario(java.lang.String beneficiario) {
			this.beneficiario = beneficiario;
		}
		
		public java.lang.String getCausalePagamento() {
			return causalePagamento;
		}

		public void setCausalePagamento(java.lang.String causalePagamento) {
			this.causalePagamento = causalePagamento;
		}		

		public java.lang.String getTiOperazioneUpdate() {
			return tiOperazioneUpdate;
		}

		public void setTiOperazioneUpdate(java.lang.String tiOperazioneUpdate) {
			this.tiOperazioneUpdate = tiOperazioneUpdate;
		}

		public java.math.BigDecimal getImPagamento() {
			return imPagamento;
		}

		public java.lang.String getDeNotePagamento() {
			return deNotePagamento;
		}

		public void setImPagamento(java.math.BigDecimal imPagamento) {
			this.imPagamento = imPagamento;
		}

		public void setDeNotePagamento(java.lang.String deNotePagamento) {
			this.deNotePagamento = deNotePagamento;
		}		

		public java.lang.String getMezzoPagamento() {
			return mezzoPagamento;
		}

		public void setMezzoPagamento(java.lang.String mezzoPagamento) {
			this.mezzoPagamento = mezzoPagamento;
		}

		public Long getTsAnnullamentoMillis() {
			return tsAnnullamentoMillis;
		}

		@Deprecated  // Usare setTsAnnullamento(). Questo campo viene calcolato a partire dall'altro  
		public void setTsAnnullamentoMillis(Long tsAnnullamentoMillis) {
			this.tsAnnullamentoMillis = tsAnnullamentoMillis;
		}		
		
		
	
}