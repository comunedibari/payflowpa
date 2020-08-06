package it.tasgroup.backoffice.ente.form;

import it.nch.erbweb.common.PreferenzeEsportazioneForm;
import it.nch.idp.backoffice.ente.CondizioniRicercaVO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumModalitaAnonima;
import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoPredeterminato;

import java.util.*;

public class CondizioniPagamentoForm extends PreferenzeEsportazioneForm {
		
		private Long idDistinta;
		
		//CARRELLO
		private String commandSent;
		private String idCondizione;
		private String idPendenza;
		
		protected String filtroEnte;
		protected String filtroTipoTributo;
		private String filtroStato;  // todo remove
		
		private Collection<DescrizioneCodice> listaStatiCondizione = new ArrayList<DescrizioneCodice>();
		private Collection<DescrizioneCodice> listaCircuiti = new ArrayList<DescrizioneCodice>();
		
		private String filtroModPagamento;
		private String filtroModPagamentoPsp;
		private String tipoVersamento;
		private String filtroModAnonima;
		private String filtroCodPagamento;
		private Integer flagIncasso;
		private String withQuietanza;
		private String idPagamento;
		private String iuv;
		private String codTransazionePSP;
		private String identificativoFiscaleCreditore;
		private String codiceFiscaleDebitore;
		private String tipoFile;
		private String idFlusso;
		private String trn;
		private String idPSP;
		
		private String numRisultatiQuietanzati;
	
		private String rendicontatiPagoPa;
		
		protected Collection listaEnti;
		
		protected Collection listaTributo;
		protected Collection listaStati;
		protected Collection listaModalitaPag;
		
		protected Collection listaPsp;
		protected HashMap<String, HashMap<String, String>> listaTipoVersamento;
		
		protected Collection<EnumModalitaAnonima> listaModalitaAnonima;
		protected Collection<EnumTipoPredeterminato> listaTipoSpontaneo;
		
		protected Collection<EnumStatoIncasso> listaStatoIncasso = null;
		
		protected HashMap<String, HashMap<String, String>> listaEntiMap = new HashMap<String, HashMap<String, String>>();
		
		private List<String> listaAnniRif;
		
		private CondizioniRicercaVO condizioniRicercaVO = new CondizioniRicercaVO();
		
		
		
		public CondizioniPagamentoForm() {
			
		}
		
		public Long getIdDistinta() {
				return idDistinta;
		}
		
		public void setIdDistinta(Long idDistinta) {
				this.idDistinta = idDistinta;
		}
		
		public String getFiltroStato() {
				return filtroStato;
		}
		
		public void setFiltroStato(String filtroStato) {
				this.filtroStato = filtroStato;
		}
		
		public String getCommandSent() {
				return commandSent;
		}
		
		public void setCommandSent(String commandSent) {
				this.commandSent = commandSent;
		}
		
		/**
		 * @return
		 */
		public String getFiltroEnte() {
				return filtroEnte;
		}
		
		/**
		 * @param string
		 */
		public void setFiltroEnte(String string) {
				filtroEnte = string;
		}
		
		public Collection getListaEnti() {
				return listaEnti;
		}
		
		public void setListaEnti(Collection collection) {
				listaEnti = collection;
		}
		
		public Collection getListaTipoTributo() {
				return listaTipoTributo;
		}
		
		public void setListaTipoTributo(Collection collection) {
				listaTipoTributo = collection;
		}
		
		public void setListaStati(Collection collection) {
				listaStati = collection;
		}
		
		public Collection getListaStati() {
				return listaStati;
		}
		
		public Collection getListaTributo() {
				return listaTributo;
		}
		
		public Collection getListaModalitaPag() {
				return listaModalitaPag;
		}
		
		public void setListaModalitaPag(Collection listaModalitaPag) {
				this.listaModalitaPag = listaModalitaPag;
		}
		
		public void setListaTributo(Collection collection) {
				listaTributo = collection;
		}
		
		public Collection getListaPsp() {
				return listaPsp;
		}
		
		public void setListaPsp(Collection listaPsp) {
				this.listaPsp = listaPsp;
		}
		
		public HashMap<String, HashMap<String, String>> getListaTipoVersamento() {
				return listaTipoVersamento;
		}
		
		public void setListaTipoVersamento(HashMap<String, HashMap<String, String>> listaPspMap) {
				this.listaTipoVersamento = listaPspMap;
		}
		
		public String getFiltroModPagamento() {
				return filtroModPagamento;
		}
		
		public void setFiltroModPagamento(String filtroModPagamento) {
				this.filtroModPagamento = filtroModPagamento;
		}
		
		public String getFiltroModPagamentoPsp() {
				return filtroModPagamentoPsp;
		}
		
		public void setFiltroModPagamentoPsp(String filtroModPagamentoPsp) {
				this.filtroModPagamentoPsp = filtroModPagamentoPsp;
		}
		
		public String getTipoVersamento() {
				return tipoVersamento;
		}
		
		public void setTipoVersamento(String tipoVersamento) {
				this.tipoVersamento = tipoVersamento;
		}
		
		public String getFiltroCodPagamento() {
				return filtroCodPagamento;
		}
		
		public void setFiltroCodPagamento(String filtroCodPagamento) {
				this.filtroCodPagamento = filtroCodPagamento;
		}
		
		public String getFiltroTipoTributo() {
				return filtroTipoTributo;
		}
		
		public void setFiltroTipoTributo(String filtroTipoTributo) {
				this.filtroTipoTributo = filtroTipoTributo;
		}
		
		public void setIdCondizione(String idCondizione) {
				this.idCondizione = idCondizione;
		}
		
		public String getIdCondizione() {
				return this.idCondizione;
		}
		
		public void setIdPendenza(String idPendenza) {
				this.idPendenza = idPendenza;
		}
		
		public String getIdPendenza() {
				return idPendenza;
		}
		
		public String getIdPagamento() {
				return idPagamento;
		}
		
		public void setIdPagamento(String idPagamento) {
				this.idPagamento = idPagamento;
		}
		
		public CondizioniRicercaVO getCondizioniRicercaVO() {
				return condizioniRicercaVO;
		}
		
		public void setCondizioniRicercaVO(CondizioniRicercaVO condizioniRicercaVO) {
				this.condizioniRicercaVO = condizioniRicercaVO;
		}
		
		public String getNumRisultatiQuietanzati() {
				return numRisultatiQuietanzati;
		}
		
		public void setNumRisultatiQuietanzati(String numRisultatiQuietanzati) {
				
				this.numRisultatiQuietanzati = numRisultatiQuietanzati;
		}
	
	
	public String getRendicontatiPagoPa() {
		return rendicontatiPagoPa;
	}
	
	public void setRendicontatiPagoPa(String rendicontatiPagoPa) {
		this.rendicontatiPagoPa = rendicontatiPagoPa;
	}
	
	public Integer getFlagIncasso() {
				return flagIncasso;
		}
		
		public void setFlagIncasso(Integer flagIncasso) {
				this.flagIncasso = flagIncasso;
		}
		
		public Collection<EnumStatoIncasso> getListaStatoIncasso() {
				if (this.listaStatoIncasso == null) {
						Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
						
						boolean CBILLSearchMode = beProperties.getProperty("iris.backoffice.cbiViewMode").equals("true");
						if (!CBILLSearchMode) {
								setListaStatoIncasso(Arrays.asList(EnumStatoIncasso.values()));
						} else {
								ArrayList<EnumStatoIncasso> a = new ArrayList<EnumStatoIncasso>();
								a.add(EnumStatoIncasso.ATTESO);
								a.add(EnumStatoIncasso.RIACCREDITATO_ENTE);
								setListaStatoIncasso(a);
						}
				}
				
				return listaStatoIncasso;
		}
		
		public void setListaStatoIncasso(Collection<EnumStatoIncasso> listaStatoIncasso) {
				this.listaStatoIncasso = listaStatoIncasso;
		}
		
		public String getFiltroModAnonima() {
				return filtroModAnonima;
		}
		
		public void setFiltroModAnonima(String filtroModAnonima) {
				this.filtroModAnonima = filtroModAnonima;
		}
		
		public Collection<EnumModalitaAnonima> getListaModalitaAnonima() {
				if (this.listaModalitaAnonima == null) {
						setListaModalitaAnonima(Arrays.asList(EnumModalitaAnonima.values()));
				}
				
				return listaModalitaAnonima;
		}
		
		public void setListaModalitaAnonima(Collection<EnumModalitaAnonima> listaModalitaAnonima) {
				this.listaModalitaAnonima = listaModalitaAnonima;
		}
		
		public Collection<EnumTipoPredeterminato> getListaTipoSpontaneo() {
				if (this.listaTipoSpontaneo == null) {
						setListaTipoSpontaneo(Arrays.asList(EnumTipoPredeterminato.values()));
				}
				
				return listaTipoSpontaneo;
		}
		
		public void setListaTipoSpontaneo(Collection<EnumTipoPredeterminato> listaTipoSpontaneo) {
				this.listaTipoSpontaneo = listaTipoSpontaneo;
		}
		
		public String getWithQuietanza() {
				return withQuietanza;
		}
		
		public void setWithQuietanza(String withQuietanza) {
				this.withQuietanza = withQuietanza;
		}
		
		public String getWithRiaccredito() {
				return condizioniRicercaVO.getWithRiaccredito();
		}
		
		public void setWithRiaccredito(String withRiaccredito) {
				condizioniRicercaVO.setWithRiaccredito(withRiaccredito);
		}
		
		public String getWithRiaccreditoCheck() {
				return getWithRiaccredito();
		}
		
		public void setWithRiaccreditoCheck(String withRiaccreditoCheck) {
				setWithRiaccredito(withRiaccreditoCheck);
		}
		
		public HashMap<String, HashMap<String, String>> getListaEntiMap() {
				return listaEntiMap;
		}
		
		public void setListaEntiMap(HashMap<String, HashMap<String, String>> listaEntiMap) {
				this.listaEntiMap = listaEntiMap;
		}
		
		public List<String> getListaAnniRif() {
				return listaAnniRif;
		}
		
		public void setListaAnniRif(List<String> listaAnniRif) {
				this.listaAnniRif = listaAnniRif;
		}
		
		public boolean isDataCreazioneRendered() {
				
				return getPreferenzeVo().listaCampiContains("dataCreazione");
				
		}
		
		public boolean isNomePSPRendered() {
				
				return getPreferenzeVo().listaCampiContains("nomePSP");
		}
		
		public boolean isModalitaPagamentoRendered() {
				
				return getPreferenzeVo().listaCampiContains("modalitaPagamento");
		}
		
		public boolean isImPagatoRendered() {
				
				return getPreferenzeVo().listaCampiContains("imPagato");
		}
		
		public boolean isCoPaganteRendered() {
				
				return getPreferenzeVo().listaCampiContains("coPagante");
		}
		
		public boolean isCodFiscDebitoreRendered() {
				
				return getPreferenzeVo().listaCampiContains("codFiscDebitore");
		}
		
		public boolean isCodPagamentoRendered() {
				
				return getPreferenzeVo().listaCampiContains("codPagamento");
		}
		
		public boolean isCodTransazioneRendered() {
				
				return getPreferenzeVo().listaCampiContains("codTransazione");
		}
		
		public boolean isCodTransazionePSPRendered() {
				
				return getPreferenzeVo().listaCampiContains("codTransazionePSP");
		}
		
		public boolean isEmailVersanteRendered() {
				
				return getPreferenzeVo().listaCampiContains("emailVersante");
		}
		
		public boolean isStatoPagamentoRendered() {
				
				return getPreferenzeVo().listaCampiContains("statoPagamento");
		}
		
		public boolean isIdPendenzaRendered() {
				
				return getPreferenzeVo().listaCampiContains("idPendenza");
		}
		
		public boolean isIdPagamentoRendered() {
				
				return getPreferenzeVo().listaCampiContains("idPagamento");
		}
		
		public boolean isIuvRendered() {
				
				return getPreferenzeVo().listaCampiContains("iuv");
		}
		
		public boolean isIncassoRendered() {
				
				return getPreferenzeVo().listaCampiContains("incasso");
		}
		
		public boolean isDesTributoRendered() {
				
				return getPreferenzeVo().listaCampiContains("desTributo");
		}
		
		public boolean isModalitaAnonimaRendered() {
				
				return getPreferenzeVo().listaCampiContains("modalitaAnonima");
		}
		
		public boolean isTipoSpontaneoRendered() {
				
				return getPreferenzeVo().listaCampiContains("tipoSpontaneo");
		}
		
		public boolean isDenomEnteRendered() {
				
				return getPreferenzeVo().listaCampiContains("denomEnte");
		}
		
		public boolean isCausalePagamentoRendered() {
				
				return getPreferenzeVo().listaCampiContains("causalePagamento");
		}
		
		public boolean isCausalePagDescrRendered() {
				
				return getPreferenzeVo().listaCampiContains("causalePagDescr");
		}
		
		public boolean isNoteRendered() {
				
				return getPreferenzeVo().listaCampiContains("note");
		}
		
		public boolean isAnnoRifRendered() {
				
				return getPreferenzeVo().listaCampiContains("annoRif");
		}
		
		public boolean isOpInserimentoRendered() {
				
				return getPreferenzeVo().listaCampiContains("opInserimento");
		}
		
		public boolean isIdRiscossionePSPRendered() {
				
				return getPreferenzeVo().listaCampiContains("idRiscossionePSP");
		}
		
		public boolean getIuvRendered() {
				return getPreferenzeVo().listaCampiContains("iuv");
		}
		
		public boolean getIdentificativoFiscaleCreditoreRendered() {
				return getPreferenzeVo().listaCampiContains("identificativoFiscaleCreditore");
		}
		
		public String getIuv() {
				return iuv;
		}
		
		public void setIuv(String iuv) {
				this.iuv = iuv;
		}
		
		public String getCodTransazionePSP() {
				return codTransazionePSP;
		}
		
		public void setCodTransazionePSP(String codTransazionePSP) {
				this.codTransazionePSP = codTransazionePSP;
		}
		
		public String getIdentificativoFiscaleCreditore() {
				return identificativoFiscaleCreditore;
		}
		
		public String getCodiceFiscaleDebitore() {
				return codiceFiscaleDebitore;
		}
		
		public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
				this.codiceFiscaleDebitore = codiceFiscaleDebitore;
		}
		
		public void setIdentificativoFiscaleCreditore(String identificativoFiscaleCreditore) {
				this.identificativoFiscaleCreditore = identificativoFiscaleCreditore;
		}
		
		public String getTipoFile() {
				return tipoFile;
		}
		
		public void setTipoFile(String tipoFile) {
				this.tipoFile = tipoFile;
		}
		
		public String getIdFlusso() {
				return idFlusso;
		}
		
		public void setIdFlusso(String idFlusso) {
				this.idFlusso = idFlusso;
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
		
		public void setTributiAmmessi(String tributiAmmessi) {
				ArrayList<String> lista = new ArrayList<String>();
				String[] tributi = tributiAmmessi.split(";");
				for (int i = 1; i < tributi.length; i++) {
						lista.add(tributi[i]);
				}
				condizioniRicercaVO.setIdTributoLista(lista);
		}
		
		public String getTributiAmmessi() {
				return null;
		}
		
		
		public Collection<DescrizioneCodice> getListaStatiCondizione() {
				return listaStatiCondizione;
		}
		
		public void setListaStatiCondizione(Collection listaStatiCondizione) {
				this.listaStatiCondizione = listaStatiCondizione;
		}
	
	public Collection<DescrizioneCodice> getListaCircuiti() {
		return listaCircuiti;
	}
	
	public void setListaCircuiti(Collection<DescrizioneCodice> listaCircuiti) {
		this.listaCircuiti = listaCircuiti;
	}
}
