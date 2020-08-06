
package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import it.nch.eb.common.utils.StringUtils;


public class PendenzeModel  implements it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps, java.io.Serializable   {
	private static final long	serialVersionUID	= -666L;

		private java.lang.String cdTrbEnte;
		private java.lang.String idEnte;
		private java.lang.String idSystem;
		private java.lang.String idMittente;
		private java.lang.String deMittente;
		private java.lang.String idPendenzaEnte;
		private java.lang.String note;
		private java.lang.String deCausale;
		private java.lang.String coRiscossore;
		private java.lang.String riferimento;
		private java.sql.Timestamp tsCreazioneEnte;
		private java.sql.Timestamp tsEmissioneEnte;
		private java.sql.Timestamp tsPrescrizione;
		private java.lang.Integer annoRiferimento;
		private java.sql.Timestamp tsModificaEnte;
		private java.lang.String stPendenza;
		private java.math.BigDecimal imTotale;
		private java.lang.String dvRiferimento;
		private java.lang.String flModPagamento;
		private java.lang.Integer prVersione;
		private java.lang.String idPendenza;
		private java.sql.Timestamp tsDecorrenza;
		private java.lang.String idTributo;
		private java.lang.String coAbi;
		private java.lang.String stRiga;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
		private java.lang.String opAnnullamento;
		private java.sql.Timestamp tsAnnullamento;
		private java.lang.Long tsAnnullamentoMillis = 0L;
		private java.lang.Integer idTributoStrutturato;
		private java.lang.Boolean spontaneo; // Volatile 
		
		private java.lang.String cartellaDiPagamento = "0";
		//header di tratta
		private java.lang.String idMittenteTrt;
		private java.lang.String idSystemTrt;
	

			public java.lang.String getCdTrbEnte() {
				return cdTrbEnte;
			}

			public void setCdTrbEnte(java.lang.String cdTrbEnte) {
				this.cdTrbEnte = cdTrbEnte;
			}
			public java.lang.String getIdEnte() {
				return idEnte;
			}

			public void setIdEnte(java.lang.String idEnte) {
				this.idEnte = idEnte;
			}
			public java.lang.String getIdSystem() {
				return idSystem;
			}

			public void setIdSystem(java.lang.String idSystem) {
				this.idSystem = idSystem;
			}
			public java.lang.String getIdMittente() {
				return idMittente;
			}

			public void setIdMittente(java.lang.String idMittente) {
				this.idMittente = idMittente;
			}
			public java.lang.String getDeMittente() {
				return deMittente;
			}

			public void setDeMittente(java.lang.String deMittente) {
				this.deMittente = deMittente;
			}
			public java.lang.String getIdPendenzaEnte() {
				return idPendenzaEnte;
			}

			public void setIdPendenzaEnte(java.lang.String idPendenzaEnte) {
				this.idPendenzaEnte = idPendenzaEnte;
			}
			public java.lang.String getNote() {
				return note;
			}

			public void setNote(java.lang.String note) {
				this.note = note;
			}
			public java.lang.String getDeCausale() {
				return deCausale;
			}

			public void setDeCausale(java.lang.String deCausale) {
				this.deCausale = deCausale;
			}
			public java.lang.String getCoRiscossore() {
				return coRiscossore;
			}

			public void setCoRiscossore(java.lang.String coRiscossore) {
				this.coRiscossore = coRiscossore;
			}
			public java.lang.String getRiferimento() {
				return riferimento;
			}

			public void setRiferimento(java.lang.String riferimento) {
				this.riferimento = riferimento;
			}
			public java.sql.Timestamp getTsCreazioneEnte() {
				return tsCreazioneEnte;
			}

			public void setTsCreazioneEnte(java.sql.Timestamp tsCreazioneEnte) {
				this.tsCreazioneEnte = tsCreazioneEnte;
			}
			public java.sql.Timestamp getTsEmissioneEnte() {
				return tsEmissioneEnte;
			}

			public void setTsEmissioneEnte(java.sql.Timestamp tsEmissioneEnte) {
				this.tsEmissioneEnte = tsEmissioneEnte;
			}
			public java.sql.Timestamp getTsPrescrizione() {
				return tsPrescrizione;
			}

			public void setTsPrescrizione(java.sql.Timestamp tsPrescrizione) {
				this.tsPrescrizione = tsPrescrizione;
			}
			public java.lang.Integer getAnnoRiferimento() {
				return annoRiferimento;
			}

			public void setAnnoRiferimento(java.lang.Integer annoRiferimento) {
				this.annoRiferimento = annoRiferimento;
			}
			public java.sql.Timestamp getTsModificaEnte() {
				return tsModificaEnte;
			}

			public void setTsModificaEnte(java.sql.Timestamp tsModificaEnte) {
				this.tsModificaEnte = tsModificaEnte;
			}
			public java.lang.String getStPendenza() {
				return stPendenza;
			}

			public void setStPendenza(java.lang.String stPendenza) {
				this.stPendenza = stPendenza;
			}
			public java.math.BigDecimal getImTotale() {
				return imTotale;
			}

			public void setImTotale(java.math.BigDecimal imTotale) {
				this.imTotale = imTotale;
			}
			public java.lang.String getDvRiferimento() {
				return dvRiferimento;
			}

			public void setDvRiferimento(java.lang.String dvRiferimento) {
				this.dvRiferimento = dvRiferimento;
			}
			public java.lang.String getFlModPagamento() {
				return flModPagamento;
			}

			public void setFlModPagamento(java.lang.String flModPagamento) {
				this.flModPagamento = flModPagamento;
			}
			public java.lang.Integer getPrVersione() {
				return prVersione;
			}

			public void setPrVersione(java.lang.Integer prVersione) {
				this.prVersione = prVersione;
			}
			public java.lang.String getIdPendenza() {
				return idPendenza;
			}

			public void setIdPendenza(java.lang.String idPendenza) {
				this.idPendenza = idPendenza;
			}
			public java.sql.Timestamp getTsDecorrenza() {
				return tsDecorrenza;
			}

			public void setTsDecorrenza(java.sql.Timestamp tsDecorrenza) {
				this.tsDecorrenza = tsDecorrenza;
			}
			public java.lang.String getIdTributo() {
				return idTributo;
			}

			public void setIdTributo(java.lang.String idTributo) {
				this.idTributo = idTributo;
			}
			public java.lang.String getCoAbi() {
				return coAbi;
			}

			public void setCoAbi(java.lang.String coAbi) {
				this.coAbi = coAbi;
			}
			public java.lang.String getStRiga() {
				return stRiga;
			}

			public void setStRiga(java.lang.String stRiga) {
				this.stRiga = stRiga;
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
	
			public java.lang.Integer getIdTributoStrutturato() {
				return idTributoStrutturato;
			}

			public void setIdTributoStrutturato(java.lang.Integer id_tributo_strutturato) {
				this.idTributoStrutturato = id_tributo_strutturato;
			}

			
			
			public java.lang.Boolean isSpontaneo() {
				return spontaneo;
			}

			public void setSpontaneo(java.lang.Boolean isSpontaneo) {
				this.spontaneo = isSpontaneo;
			}
			
			public java.lang.String getCartellaDiPagamento() {
				return cartellaDiPagamento;
			}

			public void setCartellaDiPagamento(java.lang.String cartellaDiPagamento) {
				this.cartellaDiPagamento = cartellaDiPagamento;
			}		
			
			public java.lang.Long getTsAnnullamentoMillis() {
				return tsAnnullamentoMillis;
			}

			@Deprecated  // Usare setTsAnnullamento(). Questo campo viene calcolato a partire dall'altro  
			public void setTsAnnullamentoMillis(java.lang.Long tsAnnullamentoMillis) {
				this.tsAnnullamentoMillis = tsAnnullamentoMillis;
			}

			public String toString() {
				return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
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
			
			
	
}