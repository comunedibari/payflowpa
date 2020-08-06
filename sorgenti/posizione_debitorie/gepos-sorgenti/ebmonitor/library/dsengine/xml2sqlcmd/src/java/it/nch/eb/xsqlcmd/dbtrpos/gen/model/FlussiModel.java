
package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import it.nch.eb.common.utils.StringUtils;


public class FlussiModel  implements it.nch.eb.xsqlcmd.dbtrpos.model.TableTimestamps, java.io.Serializable   {
	private static final long	serialVersionUID	= -666L;


		private java.sql.Date dataCreazione;
		private java.sql.Timestamp oraCreazione;
		private java.lang.String utenteCreatore;
		private java.lang.String totImportiPos;
		private java.lang.String divisa;
		private java.lang.String tipoServizio;
		private java.lang.String prodotto;
		private java.lang.String msgid;
		private java.lang.String idMittente;
		private java.lang.String idSystem;
		private java.lang.String idMittenteTrt;
		private java.lang.String idSystemTrt;
		private java.lang.String coVersione;
		private java.sql.Timestamp flusso;
		private java.lang.String nomeSupporto;
		private java.lang.String quantSicurezza;
		private java.lang.Integer numDisposizioni;
		private java.lang.String totImportiNeg;
		private java.lang.Integer dimFlussoFirmato;
		private java.lang.String firmatario1;
		private java.lang.String firmatario2;
		private java.lang.String mac;
		private java.lang.String anticipoIncassi;
		private java.lang.String segnoImpPresent;
		private java.lang.String tracciato;
		private java.lang.String siaMittente;
		private java.lang.String abiRicevente;
		private java.lang.String adisposizTesta;
		private java.lang.String adisposizCoda;
		private java.lang.String divisaContoOrd;
		private java.lang.String partizionamento;
		private java.lang.String soggVeic;
		private java.lang.String ccF24;
		private java.lang.Integer numRecord;
		private java.lang.String abiAccentratore;
		private java.lang.String tipoIncassoRid;
		private java.lang.String cucRicevente;
		private java.lang.String cucBancamittente;
		private java.lang.String ide2e;
		private java.lang.String qualifMsg;
		private java.sql.Timestamp credtTm;
		private java.lang.String bicRicevente;
		private java.lang.String orgnlMsgId;
		private java.sql.Timestamp orgnlCredtTm;
		private java.lang.String grpStatus;
		private java.lang.String stRiga;
		private java.lang.Integer prVersione;
		private java.lang.String opInserimento;
		private java.sql.Timestamp tsInserimento;
		private java.lang.String opAggiornamento;
		private java.sql.Timestamp tsAggiornamento;
	

			public java.sql.Date getDataCreazione() {
				return dataCreazione;
			}

			public void setDataCreazione(java.sql.Date dataCreazione) {
				this.dataCreazione = dataCreazione;
			}
			public java.sql.Timestamp getOraCreazione() {
				return oraCreazione;
			}

			public void setOraCreazione(java.sql.Timestamp oraCreazione) {
				this.oraCreazione = oraCreazione;
			}
			public java.lang.String getUtenteCreatore() {
				return utenteCreatore;
			}

			public void setUtenteCreatore(java.lang.String utenteCreatore) {
				this.utenteCreatore = utenteCreatore;
			}
			public java.lang.String getTotImportiPos() {
				return totImportiPos;
			}

			public void setTotImportiPos(java.lang.String totImportiPos) {
				this.totImportiPos = totImportiPos;
			}
			public java.lang.String getDivisa() {
				return divisa;
			}

			public void setDivisa(java.lang.String divisa) {
				this.divisa = divisa;
			}
			public java.lang.String getTipoServizio() {
				return tipoServizio;
			}

			public void setTipoServizio(java.lang.String tipoServizio) {
				this.tipoServizio = tipoServizio;
			}
			public java.lang.String getProdotto() {
				return prodotto;
			}

			public void setProdotto(java.lang.String prodotto) {
				this.prodotto = prodotto;
			}
			public java.lang.String getMsgid() {
				return msgid;
			}

			public void setMsgid(java.lang.String msgid) {
				this.msgid = msgid;
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
			public java.sql.Timestamp getFlusso() {
				return flusso;
			}

			public void setFlusso(java.sql.Timestamp flusso) {
				this.flusso = flusso;
			}
			public java.lang.String getNomeSupporto() {
				return nomeSupporto;
			}

			public void setNomeSupporto(java.lang.String nomeSupporto) {
				this.nomeSupporto = nomeSupporto;
			}
			public java.lang.String getQuantSicurezza() {
				return quantSicurezza;
			}

			public void setQuantSicurezza(java.lang.String quantSicurezza) {
				this.quantSicurezza = quantSicurezza;
			}
			public java.lang.Integer getNumDisposizioni() {
				return numDisposizioni;
			}

			public void setNumDisposizioni(java.lang.Integer numDisposizioni) {
				this.numDisposizioni = numDisposizioni;
			}
			public java.lang.String getTotImportiNeg() {
				return totImportiNeg;
			}

			public void setTotImportiNeg(java.lang.String totImportiNeg) {
				this.totImportiNeg = totImportiNeg;
			}
			public java.lang.Integer getDimFlussoFirmato() {
				return dimFlussoFirmato;
			}

			public void setDimFlussoFirmato(java.lang.Integer dimFlussoFirmato) {
				this.dimFlussoFirmato = dimFlussoFirmato;
			}
			public java.lang.String getFirmatario1() {
				return firmatario1;
			}

			public void setFirmatario1(java.lang.String firmatario1) {
				this.firmatario1 = firmatario1;
			}
			public java.lang.String getFirmatario2() {
				return firmatario2;
			}

			public void setFirmatario2(java.lang.String firmatario2) {
				this.firmatario2 = firmatario2;
			}
			public java.lang.String getMac() {
				return mac;
			}

			public void setMac(java.lang.String mac) {
				this.mac = mac;
			}
			public java.lang.String getAnticipoIncassi() {
				return anticipoIncassi;
			}

			public void setAnticipoIncassi(java.lang.String anticipoIncassi) {
				this.anticipoIncassi = anticipoIncassi;
			}
			public java.lang.String getSegnoImpPresent() {
				return segnoImpPresent;
			}

			public void setSegnoImpPresent(java.lang.String segnoImpPresent) {
				this.segnoImpPresent = segnoImpPresent;
			}
			public java.lang.String getTracciato() {
				return tracciato;
			}

			public void setTracciato(java.lang.String tracciato) {
				this.tracciato = tracciato;
			}
			public java.lang.String getSiaMittente() {
				return siaMittente;
			}

			public void setSiaMittente(java.lang.String siaMittente) {
				this.siaMittente = siaMittente;
			}
			public java.lang.String getAbiRicevente() {
				return abiRicevente;
			}

			public void setAbiRicevente(java.lang.String abiRicevente) {
				this.abiRicevente = abiRicevente;
			}
			public java.lang.String getAdisposizTesta() {
				return adisposizTesta;
			}

			public void setAdisposizTesta(java.lang.String adisposizTesta) {
				this.adisposizTesta = adisposizTesta;
			}
			public java.lang.String getAdisposizCoda() {
				return adisposizCoda;
			}

			public void setAdisposizCoda(java.lang.String adisposizCoda) {
				this.adisposizCoda = adisposizCoda;
			}
			public java.lang.String getDivisaContoOrd() {
				return divisaContoOrd;
			}

			public void setDivisaContoOrd(java.lang.String divisaContoOrd) {
				this.divisaContoOrd = divisaContoOrd;
			}
			public java.lang.String getPartizionamento() {
				return partizionamento;
			}

			public void setPartizionamento(java.lang.String partizionamento) {
				this.partizionamento = partizionamento;
			}
			public java.lang.String getSoggVeic() {
				return soggVeic;
			}

			public void setSoggVeic(java.lang.String soggVeic) {
				this.soggVeic = soggVeic;
			}
			public java.lang.String getCcF24() {
				return ccF24;
			}

			public void setCcF24(java.lang.String ccF24) {
				this.ccF24 = ccF24;
			}
			public java.lang.Integer getNumRecord() {
				return numRecord;
			}

			public void setNumRecord(java.lang.Integer numRecord) {
				this.numRecord = numRecord;
			}
			public java.lang.String getAbiAccentratore() {
				return abiAccentratore;
			}

			public void setAbiAccentratore(java.lang.String abiAccentratore) {
				this.abiAccentratore = abiAccentratore;
			}
			public java.lang.String getTipoIncassoRid() {
				return tipoIncassoRid;
			}

			public void setTipoIncassoRid(java.lang.String tipoIncassoRid) {
				this.tipoIncassoRid = tipoIncassoRid;
			}
			public java.lang.String getCucRicevente() {
				return cucRicevente;
			}

			public void setCucRicevente(java.lang.String cucRicevente) {
				this.cucRicevente = cucRicevente;
			}
			public java.lang.String getCucBancamittente() {
				return cucBancamittente;
			}

			public void setCucBancamittente(java.lang.String cucBancamittente) {
				this.cucBancamittente = cucBancamittente;
			}
			public java.lang.String getIde2e() {
				return ide2e;
			}

			public void setIde2e(java.lang.String ide2e) {
				this.ide2e = ide2e;
			}
			public java.lang.String getQualifMsg() {
				return qualifMsg;
			}

			public void setQualifMsg(java.lang.String qualifMsg) {
				this.qualifMsg = qualifMsg;
			}
			public java.sql.Timestamp getCredtTm() {
				return credtTm;
			}

			public void setCredtTm(java.sql.Timestamp credtTm) {
				this.credtTm = credtTm;
			}
			public java.lang.String getBicRicevente() {
				return bicRicevente;
			}

			public void setBicRicevente(java.lang.String bicRicevente) {
				this.bicRicevente = bicRicevente;
			}
			public java.lang.String getOrgnlMsgId() {
				return orgnlMsgId;
			}

			public void setOrgnlMsgId(java.lang.String orgnlMsgId) {
				this.orgnlMsgId = orgnlMsgId;
			}
			public java.sql.Timestamp getOrgnlCredtTm() {
				return orgnlCredtTm;
			}

			public void setOrgnlCredtTm(java.sql.Timestamp orgnlCredtTm) {
				this.orgnlCredtTm = orgnlCredtTm;
			}
			public java.lang.String getGrpStatus() {
				return grpStatus;
			}

			public void setGrpStatus(java.lang.String grpStatus) {
				this.grpStatus = grpStatus;
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