package it.nch.idp.backoffice.ente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import it.tasgroup.services.util.enumeration.EnumModalitaAnonima;

public class CondizioniRicercaVO implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


		final private String IT_DATE_FORMAT = "dd/MM/yyyy";
		
		
		private String idCreditore;
		
		private String idPendenza;
		private String idPendenzaEnte	;
		private String codiceVersante;               // condizione.pendenza.destinatario.codAlternativoDebitore
		private String codiceFiscaleDebitore;        // condizione.destinatario.coDestinatario
		private String anagrafica;                   // condizione.destinatario.deDestinatario
		private String IUV;                          // condizione.idPagamento
		private String statoPagamentoCondizione;  // condizione.stPagamento + altro ...
		private EnumTipoRicercaPosizioni tipoRicerca;          // condizione.stPagamento + altro ... (combinazioni ad uso GESTES)
		private boolean sommaImporti;
		private String idLista;
		private String importoPagamentoDa;
		private String importoPagamentoA;
		private String tipoDebito;
//		private EnumStatoIncasso flagIncasso;        // condizione.pagamenti.flagIncasso
		
		private String causaleDebito;
		private String causaleVersamento;
		
		private String flagIncasso;        // condizione.pagamenti.flagIncasso
		private String dataEmissioneDa;

		private String dataEmissioneA;
		private String dataPagamentoDa;             // condizione.pagamenti.tsInserimento
		private String dataPagamentoA;
		private String dataScadenzaDa;
		private String dataScadenzaA;
		private String utenteCreatore;               // condizione.pagamenti.flussoDistinta.utentecreatore
		private String idPsp;                        // condizione.pagamenti.flussoDistinta.cfgGatewayPagamento.systemId
		private String istitutoAttestante;
		
		private String modAnonima;
		private String modPagamento;
		private String modPagamentoPsp;
		private String tipoVersamento;
		private String tipoSpontaneo;
		private String cfUtenteCreatore;
		private String emailVersante;
		
		private String identificativoMittente;
		private String riscossore;
		private String riferimento;
		
//		private String importoPagamentoDa;	
//		private String importoPagamentoA;
		private String idEnte;
		private String idIntestatarioEnte;
		//private String statoPagamentoCondizione;
//		private String flagIncasso;
		private String withQuietanza;
		private String withRiaccredito;
		private String opInserimento;
		private ArrayList<String> idTributoLista = new ArrayList<String>();
		private String idPagamento;
//		private String idPendenza;
		
		private String idDocRepository;
		private String codPagamento; // used
		private String codFiscDebitore;
		
		private String annoRif;
		
		private String idFlussoRiversamento;
		private String trn;
		private String idPSP;
		private String idFiscaleCreditore;
		
		private String circuito;
		private String dataRegolamentoDa;
		private String dataRegolamentoA;

	public String getCircuito() {
		return circuito;
	}
	
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	
	public String getIdFiscaleCreditore() {
				return idFiscaleCreditore;
		}
		
		public void setIdFiscaleCreditore(String idFiscaleCreditore) {
				this.idFiscaleCreditore = idFiscaleCreditore;
		}
		
		public String getIdentificativoMittente() {
				return identificativoMittente;
		}
		
		public void setIdentificativoMittente(String identificativoMittente) {
				this.identificativoMittente = identificativoMittente;
		}
		
		public String getRiscossore() {
				return riscossore;
		}
		
		public void setRiscossore(String riscossore) {
				this.riscossore = riscossore;
		}
		
		public String getRiferimento() {
				return riferimento;
		}
		
		public void setRiferimento(String riferimento) {
				this.riferimento = riferimento;
		}
		
		public String getModPagamento() {
				return modPagamento;
		}
		public void setModPagamento(String modPagamento) {
				this.modPagamento = modPagamento;
		}
		public String getModPagamentoPsp() {
				return modPagamentoPsp;
		}
		public void setModPagamentoPsp(String modPagamentoPsp) {
				this.modPagamentoPsp = modPagamentoPsp;
		}
		public String getTipoVersamento() {
				return tipoVersamento;
		}
		
		public void setTipoVersamento(String tipoVersamento) {
				this.tipoVersamento = tipoVersamento;
		}
		public String getCfUtenteCreatore() {
				return cfUtenteCreatore;
		}
		public void setCfUtenteCreatore(String cfUtenteCreatore) {
				this.cfUtenteCreatore = cfUtenteCreatore;
		}
		public String getEmailVersante() {
				return emailVersante;
		}
		public void setEmailVersante(String emailVersante) {
				this.emailVersante = emailVersante;
		}
		public String getImportoPagamentoDa() {
				return importoPagamentoDa;
		}
		public void setImportoPagamentoDa(String importoPagamentoDa) {
				this.importoPagamentoDa = importoPagamentoDa;
		}
		public String getImportoPagamentoA() {
				return importoPagamentoA;
		}
		public void setImportoPagamentoA(String importoPagamentoA) {
				this.importoPagamentoA = importoPagamentoA;
		}

		public String getFlagIncasso() {
				return flagIncasso;
		}
		public void setFlagIncasso(String flagIncasso) {
				this.flagIncasso = flagIncasso;
		}
		
		public String getIdPendenzaEnte() {
				return idPendenzaEnte;
		}
		
		public void setIdPendenzaEnte(String idPendenzaEnte) {
				this.idPendenzaEnte = idPendenzaEnte;
		}
		
		public String getIdPendenza() {
				return idPendenza;
		}
		public void setIdPendenza(String idPendenza) {
				this.idPendenza = idPendenza;
		}
		public String getIdPagamento() {
				return idPagamento;
		}
		public void setIdPagamento(String idPagamento) {
				this.idPagamento = idPagamento;
		}
		public String getIdEnte() {
				return idEnte;
		}
		public void setIdEnte(String idEnte) {
				this.idEnte = idEnte;
		}
		
		public String getIdDocRepository() {
				return idDocRepository;
		}
		
		public void setIdDocRepository(String idDocRepository) {
				this.idDocRepository = idDocRepository;
		}
		
		public String getCodPagamento() {
				return codPagamento;
		}
		
		public void setCodPagamento(String codPagamento) {
				this.codPagamento = codPagamento;
		}
		
		public String getCodFiscDebitore() {
				return codFiscDebitore;
		}
		
		public void setCodFiscDebitore(String codFiscDebitore) {
				this.codFiscDebitore = codFiscDebitore;
		}
		
		public String getIdIntestatarioEnte() {
				return idIntestatarioEnte;
		}
		
		public void setIdIntestatarioEnte(String idIntestatarioEnte) {
				this.idIntestatarioEnte = idIntestatarioEnte;
		}
		
		public ArrayList<String> getIdTributoLista() {
				return idTributoLista;
		}
		
		public void setIdTributoLista(ArrayList<String> idTributoLista) {
				this.idTributoLista = idTributoLista;
		}
		
		
		public String getOpInserimento() {
				return opInserimento;
		}
		
		public void setOpInserimento(String opInserimento) {
				this.opInserimento = opInserimento;
		}
		
		public String getModAnonima() {
				return modAnonima;
		}
		
		public void setModAnonima(String modAnonima) {
				this.modAnonima = modAnonima;
		}
		
		public String getModalitaAnonima() {
				return this.opInserimento != null && EnumModalitaAnonima.ANONIMO.getChiave().equalsIgnoreCase(this.opInserimento.trim())? EnumModalitaAnonima.ANONIMO.getDescrizione() : EnumModalitaAnonima.CNS.getDescrizione();
		}
		
		public String getTipoSpontaneo() {
				return tipoSpontaneo;
		}
		
		public void setTipoSpontaneo(String tipoSpontaneo) {
				this.tipoSpontaneo = tipoSpontaneo;
		}
		
		public String getWithQuietanza() {
				return withQuietanza;
		}
		
		public void setWithQuietanza(String withQuietanza) {
				this.withQuietanza = withQuietanza;
		}
		
		public String getWithRiaccredito() {
				return withRiaccredito;
		}
		
		public void setWithRiaccredito(String withRiaccredito) {
				this.withRiaccredito = withRiaccredito;
		}
		
		public String getAnnoRif() {
				return annoRif;
		}
		
		public void setAnnoRif(String annoRif) {
				this.annoRif = annoRif;
		}
		
		
		public String getIdFlussoRiversamento() {
				return idFlussoRiversamento;
		}
		
		public void setIdFlussoRiversamento(String idFlussoRiversamento) {
				this.idFlussoRiversamento = idFlussoRiversamento;
		}
		
		public String getTrn() {
				return trn;
		}
		
		public void setTrn(String trn) {
				this.trn = trn;
		}
		
		public String getIdPSP() {
				return idPSP;
		}
		
		public void setIdPSP(String idPSP) {
				this.idPSP = idPSP;
		}
	
		public Boolean checkRequiredFields() {	
				if (StringUtils.isEmpty(IUV) && StringUtils.isEmpty(codPagamento) && 
					StringUtils.isEmpty(idPendenza)&& StringUtils.isEmpty(idPagamento) &&  
					StringUtils.isEmpty(dataEmissioneDa)&& StringUtils.isEmpty(dataEmissioneA) &&  
				    StringUtils.isEmpty(cfUtenteCreatore) && StringUtils.isEmpty(this.codiceFiscaleDebitore) && 
				    StringUtils.isEmpty(this.idPendenzaEnte) ) {
						return false;
				}
				return true;
				
	}
		
	public Boolean checkFieldRulesStorico() {	
		if (StringUtils.isEmpty(tipoDebito) && !StringUtils.isEmpty(idPendenzaEnte) || 
			!StringUtils.isEmpty(tipoDebito)&& StringUtils.isEmpty(idPendenzaEnte)) {
				return false;
		}
		return true;
	}
	
	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
		
		/** -- **/
		public String getIdCreditore() {
				return idCreditore;
		}
		
		public void setIdCreditore(String idCreditore) {
				this.idCreditore = idCreditore;
		}
		
		public String getCodiceVersante() {
				return codiceVersante;
		}
		 
		public void setCodiceVersante(String codiceVersante) {
				this.codiceVersante = codiceVersante;
		}
		
		public String getCodiceFiscaleDebitore() {
				return codiceFiscaleDebitore;
		}
		
		public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
				this.codiceFiscaleDebitore = codiceFiscaleDebitore;
		}
		
		public String getAnagrafica() {
				return anagrafica;
		}
		
		public void setAnagrafica(String anagrafica) {
				this.anagrafica = anagrafica;
		}
		
		public String getIUV() {
				return IUV;
		}
		
		public void setIUV(String IUV) {
				this.IUV = IUV;
		}
		
		public String getStatoPagamentoCondizione() {
				return statoPagamentoCondizione;
		}
		
		public void setStatoPagamentoCondizione(String statoPagamentoCondizione) {
				this.statoPagamentoCondizione = statoPagamentoCondizione;
		}
		
		public EnumTipoRicercaPosizioni getTipoRicerca() {
				return tipoRicerca;
		}
		
		public void setTipoRicerca(EnumTipoRicercaPosizioni tipoRicerca) {
				this.tipoRicerca = tipoRicerca;
		}
		
		public boolean isSommaImporti() {
				return sommaImporti;
		}
		
		public void setSommaImporti(boolean sommaImporti) {
				this.sommaImporti = sommaImporti;
		}
		
		public String getIdLista() {
				return idLista;
		}
		
		public void setIdLista(String idLista) {
				this.idLista = idLista;
		}
		
		public String getTipoDebito() {
				return tipoDebito;
		}
		
		public void setTipoDebito(String tipoDebito) {
				this.tipoDebito = tipoDebito;
		}
		
		public String getCausaleDebito() {
				return causaleDebito;
		}
		
		public void setCausaleDebito(String causaleDebito) {
				this.causaleDebito = causaleDebito;
		}
		
		public String getCausaleVersamento() {
				return causaleVersamento;
		}
		
		public void setCausaleVersamento(String causaleVersamento) {
				this.causaleVersamento = causaleVersamento;
		}
		
		//		public void setFlagIncasso(EnumStatoIncasso flagIncasso) {
//				this.flagIncasso = flagIncasso;
//		}
//		
		
		public String getDataPagamentoDa() {
				return dataPagamentoDa;
		}
		
		public void setDataPagamentoDa(String dataPagamentoDa) {
				this.dataPagamentoDa = dataPagamentoDa;
		}
		
		public String getDataPagamentoA() {
				return dataPagamentoA;
		}
		
		public void setDataPagamentoA(String dataPagamentoA) {
				this.dataPagamentoA = dataPagamentoA;
		}
		
		public String getDataScadenzaDa() {
				return dataScadenzaDa;
		}
		
		public void setDataScadenzaDa(String dataScadenzaDa) {
				this.dataScadenzaDa = dataScadenzaDa;
		}
		
		public String getDataScadenzaA() {
				return dataScadenzaA;
		}
		
		public void setDataScadenzaA(String dataScadenzaA) {
				this.dataScadenzaA = dataScadenzaA;
		}
		
		/**
		 * id fiscale versante	
		 * @return
		 */
		public String getUtenteCreatore() {
				return utenteCreatore;
		}
		
		public void setUtenteCreatore(String utenteCreatore) {
				this.utenteCreatore = utenteCreatore;
		}
		
		public String getIdPsp() {
				return idPsp;
		}
		
		public void setIdPsp(String idPsp) {
				this.idPsp = idPsp;
		}
		
		
		public String getDateFormat() {
				return IT_DATE_FORMAT;
		}
		
		public String getIstitutoAttestante() {
				return istitutoAttestante;
		}
		
		public void setIstitutoAttestante(String istitutoAttestante) {
				this.istitutoAttestante = istitutoAttestante;
		}
	
	public String getDataRegolamentoDa() {
		return dataRegolamentoDa;
	}
	
	public void setDataRegolamentoDa(String dataRegolamentoDa) {
		this.dataRegolamentoDa = dataRegolamentoDa;
	}
	
	public String getDataRegolamentoA() {
		return dataRegolamentoA;
	}
	
	public void setDataRegolamentoA(String dataRegolamentoA) {
		this.dataRegolamentoA = dataRegolamentoA;
	}
	
	public void addTipoTributoToList(String cdTribEnte) {
		idTributoLista.add(cdTribEnte);
	}
	
	public String getDataEmissioneDa() {
		return dataEmissioneDa;
	}

	public void setDataEmissioneDa(String dataEmissioneDa) {
		this.dataEmissioneDa = dataEmissioneDa;
	}

	public String getDataEmissioneA() {
		return dataEmissioneA;
	}

	public void setDataEmissioneA(String dataEmissioneA) {
		this.dataEmissioneA = dataEmissioneA;
	}


}
