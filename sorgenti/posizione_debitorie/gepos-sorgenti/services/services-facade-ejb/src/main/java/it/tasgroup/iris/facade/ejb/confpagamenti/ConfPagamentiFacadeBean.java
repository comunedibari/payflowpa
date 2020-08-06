
package it.tasgroup.iris.facade.ejb.confpagamenti;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.is.fo.profilo.Enti;
import it.nch.utility.IbisConfiguration;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.business.ejb.client.anagrafica.TributoEnteBusinessLocal;
import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.domain.CfgStrumentoPagamento;
import it.tasgroup.iris.domain.CfgTributiEntiGateway;
import it.tasgroup.iris.domain.helper.CommissioniCalculator;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiLogoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgFornitoreGatewayDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgModalitaPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgStrumentoPagamentoDTO;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacadeRemote;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.EntiDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.cfgpagamenti.builder.CfgPagamentiDTOBuilder;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import static it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.cfgpagamenti.builder.CfgPagamentiDTOBuilder.populateFornitoreGatewayDTO;

@Stateless(name = "ConfPagamentiFacade")
public 	class ConfPagamentiFacadeBean implements ConfPagamentiFacadeLocal, ConfPagamentiFacadeRemote { 

    private static final Logger LOGGER = LogManager.getLogger(ConfPagamentiFacadeBean.class);

    @EJB(name = "ConfPagamentiBusiness")
    private ConfPagamentiBusinessLocal confPagamentoBusinessBean;
    
    @EJB(name = "TributoEnteBusiness")
    private TributoEnteBusinessLocal tributoBusinessBean;
		
		@EJB(name = "IntestatariBusiness")
		private IntestatariBusinessLocal intestatariBusinessBean;


    /**
     *
     */
    @Override
    public List<CfgModalitaPagamentoDTO> readModalitaPagamentoList() {

        List<CfgModalitaPagamento> listaModalitaPagamento = confPagamentoBusinessBean.getListaAllModalitaPagamento();
        List<CfgModalitaPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListModalitaPagamentoDTO(listaModalitaPagamento);

        return dtos;
    }
    
    @Override
    public List<CfgModalitaPagamentoDTO> readModalitaPagamentoInActiveCfgFornitore(String circuito) {

        List<CfgModalitaPagamento> listaModalitaPagamento = confPagamentoBusinessBean.getListaCfgModalitaInActiveCfgFornitore(circuito);
        List<CfgModalitaPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListModalitaPagamentoDTO(listaModalitaPagamento);

        return dtos;
    }
    
    /**
    *
    */
   @Override
   public List<CfgModalitaPagamentoDTO> readModalitaPagamentoAttiveList() {
       List<CfgModalitaPagamento> listaModalitaPagamento = confPagamentoBusinessBean.getListaModalitaPagamento();
       List<CfgModalitaPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListModalitaPagamentoDTO(listaModalitaPagamento);
       return dtos;
   }
   

    /**
     * @throws Exception
     */
    @Override
    public List<CfgGatewayPagamentoDTO> getListaConfigurazioniPagamenti(String intestatario, String stato) throws Exception {
        return getListaConfigurazioniPagamenti(intestatario, stato, null, null);
    }

    /**
     * @throws Exception
     */
    @Override
    public List<CfgGatewayPagamentoDTO> getListaConfigurazioniPagamenti(String intestatario, String stato, String sysId, String appId) throws Exception {
        List<CfgGatewayPagamento> listaCfgGateway = null;

        if (sysId == null || appId == null) {
            listaCfgGateway = confPagamentoBusinessBean.getListaConfigurazioniPagamenti(intestatario, stato);
        } else {
            listaCfgGateway = confPagamentoBusinessBean.getListaConfigurazioniPagamenti(intestatario, stato, sysId, appId);
        }
        return CfgPagamentiDTOBuilder.populateListCfgGatewayPagamentoDTO(listaCfgGateway);
    }

    @Override
    public List<CfgGatewayPagamentoDTO> getConfigurazionePagamentiCommissioniCalcolate(String intestatario, String stato, BigDecimal importoTotalePagamenti) throws Exception {
        return getConfigurazionePagamentiCommissioniCalcolate(intestatario, stato, null, null, importoTotalePagamenti);
    }

    @Override
    public List<CfgGatewayPagamentoDTO> getConfigurazionePagamentiCommissioniCalcolate(String intestatario, String stato, String sysId, String appId, BigDecimal importoTotalePagamenti) throws Exception {

        List<CfgGatewayPagamentoDTO> listaCfgGatewayDTO = new ArrayList<CfgGatewayPagamentoDTO>();
        List<CfgGatewayPagamento> listaCfgGateway = null;

        if (sysId == null || appId == null) {
            listaCfgGateway = confPagamentoBusinessBean.getListaConfigurazioniPagamenti(intestatario, stato);
        } else {
            listaCfgGateway = confPagamentoBusinessBean.getListaConfigurazioniPagamenti(intestatario, stato, sysId, appId);
        }

        BigDecimal importoCommissioni = null;
        for (CfgGatewayPagamento cfgGateway : listaCfgGateway) {
            importoCommissioni = CommissioniCalculator.calcolaTotaleCommissioni(cfgGateway.getCfgCommissionePagamenti(), importoTotalePagamenti);
            CfgGatewayPagamentoDTO cfgDTO = CfgPagamentiDTOBuilder.populateCfgGatewayPagamentoDTO(cfgGateway);
            cfgDTO.getCfgModalitaPagamento().setImportoCommissioni(importoCommissioni);
            cfgDTO.getCfgModalitaPagamento().setImportoTotale(importoTotalePagamenti.add(importoCommissioni).setScale(2, RoundingMode.HALF_UP));
            listaCfgGatewayDTO.add(cfgDTO);
        }

        return listaCfgGatewayDTO;
    }

    @Override
    public List<CfgGatewayPagamentoDTO> readCfgGatewayPagamentoList() {

        List<CfgGatewayPagamento> lstaCfgGatewayPagamento = confPagamentoBusinessBean.getCfgGatewayPagamentoAll();

        List<CfgGatewayPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgGatewayPagamentoDTO(lstaCfgGatewayPagamento);

        return dtos;
    }

    @Override
    public ContainerDTO readCfgGatewayPagamentoListByFilter(ContainerDTO dto) {

        List<CfgGatewayPagamento> listaCfgGatewayPagamento = confPagamentoBusinessBean.getCfgGatewayPagamentoByFilter(dto);

        List<CfgGatewayPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgGatewayPagamentoDTO(listaCfgGatewayPagamento);

        dto.setOutputDTOList(dtos);

        return dto;
    }

    @Override
    public CfgGatewayPagamentoDTO readCfgGatewayPagamentoDettaglio(Long id) {

        CfgGatewayPagamento cfgGatewayPagamento = confPagamentoBusinessBean.getCfgGatewayPagamentoById(id);

        CfgGatewayPagamentoDTO dto = null;
        if (cfgGatewayPagamento != null) {
            dto = CfgPagamentiDTOBuilder.populateCfgGatewayPagamentoDTO(cfgGatewayPagamento);
        }
        
        return dto;
    }
    
    @Override
    public CfgGatewayPagamentoDTO readCfgGatewayPagamentoDettaglio(String bundleKey) {

    	CfgGatewayPagamento cfgGatewayPagamento = confPagamentoBusinessBean.getCfgGatewayPagamentoById(bundleKey);

        CfgGatewayPagamentoDTO dto = null;
        if (cfgGatewayPagamento != null) {
            dto = CfgPagamentiDTOBuilder.populateCfgGatewayPagamentoDTO(cfgGatewayPagamento);
        }
        
        return dto;
    }

    @Override
    public void updateStatoListCfgGatewayPagamento(String[] idcfggatewaypagamento, String stato) throws Exception {

        confPagamentoBusinessBean.updateStatoListCfgGatewayPagamento(idcfggatewaypagamento, stato);
    }


    @Override
    public List<CfgStrumentoPagamentoDTO> readCfgStrumentoPagamentoList() {

        List<CfgStrumentoPagamento> lstaCfgStrumentoPagamento = confPagamentoBusinessBean.getCfgStrumentoPagamentoAll();

        List<CfgStrumentoPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgStrumentoPagamentoDTO(lstaCfgStrumentoPagamento);

        return dtos;
    }

    @Override
    public CfgStrumentoPagamentoDTO readCfgStrumentoPagamentoDettaglio(Long id) {

        CfgStrumentoPagamento cfgStrumentoPagamento = confPagamentoBusinessBean.getCfgStrumentoPagamentoById(id);

        CfgStrumentoPagamentoDTO dto = CfgPagamentiDTOBuilder.populateStrumentoPagamentoDTO(cfgStrumentoPagamento);

        return dto;
    }

    @Override
    public List<CfgGatewayPagamentoDTO> readCfgGatewayPagamentoListDistinctSystemName() throws Exception {

        List<CfgGatewayPagamento> lstaCfgGatewayPagamento = confPagamentoBusinessBean.getCfgGatewayPagamentoListDistinctSystemName();

        List<CfgGatewayPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgGatewayPagamentoDTO(lstaCfgGatewayPagamento);

        return dtos;

    }
    
    @Override
    public List<CfgFornitoreGatewayDTO> getCfgFornitoreGatewayDistinctId() {
    	List<CfgFornitoreGateway> lstaCfgFornitoreGateway = confPagamentoBusinessBean.getCfgFornitoreGatewayDistinctId();
    	List<CfgFornitoreGatewayDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgFornitoreGatewayDTO(lstaCfgFornitoreGateway);
    	return dtos;
    }

    @Override
    public List<CfgFornitoreGatewayDTO> readCfgFornitoreGatewayDTO()
            throws Exception {
        List<CfgFornitoreGateway> lstaCfgFornitoreGateway = confPagamentoBusinessBean.getCfgFornitoreGatewayAll();

        List<CfgFornitoreGatewayDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgFornitoreGatewayDTO(lstaCfgFornitoreGateway);

        return dtos;

    }
		
		@Override public List<CfgFornitoreGatewayDTO> getCfgGatewayPagamentoBOE(String idEnte) throws Exception {
			final List<CfgFornitoreGateway> cfgFornitoreGatewayBoe = confPagamentoBusinessBean.getCfgFornitoreGatewayBoe(idEnte);
			
			List<CfgFornitoreGatewayDTO> gateways = new ArrayList<CfgFornitoreGatewayDTO>();
 			for (CfgFornitoreGateway cfg : cfgFornitoreGatewayBoe) {
				final CfgFornitoreGatewayDTO cfgFornitoreGatewayDTO = populateFornitoreGatewayDTO(cfg);
				gateways.add(cfgFornitoreGatewayDTO);
			}
			return gateways;
		}
		
		@Override
    public List<CfgFornitoreGatewayDTO> readCfgFornitoreGatewayCircuitoDTO(String idEnte) throws Exception {
        List<CfgFornitoreGateway> lstaCfgFornitoreGateway = confPagamentoBusinessBean.readCfgFornitoreGatewayCircuito(idEnte);
        List<CfgFornitoreGatewayDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgFornitoreGatewayDTO(lstaCfgFornitoreGateway);
        return dtos;
    }
    
    @Override
    public List<CfgGatewayPagamentoDTO> getCfgGatewayPagamentoEnabledByTributiEnti(String idEnte, String cdTrbEnte) throws Exception {
        List<CfgGatewayPagamento> lstaCfgGatewayPagamento = confPagamentoBusinessBean.getCfgGatewayPagamentoEnabledByTributiEnti(idEnte, cdTrbEnte);

        List<CfgGatewayPagamentoDTO> dtos = CfgPagamentiDTOBuilder.populateListCfgGatewayPagamentoDTO(lstaCfgGatewayPagamento);

        return dtos;
    }

    @Override
    public List<TributoEnteDTO> getTributiEntiIbanCCpNull() throws Exception {

        List<TributoEnte> l = tributoBusinessBean.getTributiEntiIbanCCpNull();
        return EntiDTOBuilder.fillListEntiTributiDTOFull(l);

    }

    // BEGIN :  metodi scopiazzati da GatewayPaymentAction eventualmente da rifattorizzare per uso generico
    private Hashtable<String, String> getTributiEntiNoPosteKeys(List<TributoEnteDTO> listaTribEnti) {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        Iterator<TributoEnteDTO> iter = listaTribEnti.iterator();
        while (iter.hasNext()) {
            TributoEnteDTO trbEnte = iter.next();
            String key = trbEnte.getIdEnte() + "#" + trbEnte.getCdTrbEnte();
            ht.put(key, key);
        }

        return ht;

    }

    private Hashtable<Long, Long> getCfgGatewayPagamentoKeys(List<CfgGatewayPagamentoDTO> listaCfgPagamenti) {
        Hashtable<Long, Long> ht = new Hashtable<Long, Long>();
        Iterator<CfgGatewayPagamentoDTO> iter = listaCfgPagamenti.iterator();
        while (iter.hasNext()) {
            CfgGatewayPagamentoDTO cfgGP = iter.next();
            ht.put(cfgGP.getId(),cfgGP.getId());
        }

        return ht;

    }
    
    private Hashtable<Long, Long> getIdCfgGatewayPagamentoKeys(List<Long> listaIdCfgPagamenti) {
        Hashtable<Long, Long> ht = new Hashtable<Long, Long>();
        Iterator<Long> iter = listaIdCfgPagamenti.iterator();
        while (iter.hasNext()) {
            Long cfgGP = iter.next();
            ht.put(cfgGP,cfgGP);
        }

        return ht;

    }
    
    private Collection<CfgGatewayPagamentoDTO> filtraGatewayPagamento(Collection<CfgGatewayPagamentoDTO> cgfPagamentiTutti, boolean excludeBFL, 
    		boolean excludeGatewayPoste, boolean excludeNdpMyBank, String enteNoPoste, String cdTrbNoPoste, String ibanNoPoste, boolean excludeNdp, boolean excludeBancaTesoriera, boolean excludePsp) {

        if (!excludeBFL && !excludeGatewayPoste && !excludeNdpMyBank && !excludeNdp && !excludeBancaTesoriera && !excludePsp)
            return cgfPagamentiTutti;

        Iterator<CfgGatewayPagamentoDTO> iter = cgfPagamentiTutti.iterator();
        while (iter.hasNext()) {
            CfgGatewayPagamentoDTO cfgGP = iter.next();
            // escludo Gateway Poste
            if (excludeGatewayPoste && cfgGP.getSystemId().startsWith("BPPIITRR")) {
                cfgGP.setDisponibilePerPagamentoCorrente(false);
                if (ibanNoPoste != null)  {
										cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente(cfgGP.getDescrizioneIndisponibilitaPerPagamentoCorrente() + " " + ibanNoPoste + " IBAN BENEFICIARIO CONDIZIONE NON RICONOSCIUTO ");
								}else {
										cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente(cfgGP.getDescrizioneIndisponibilitaPerPagamentoCorrente() + " " + enteNoPoste + " " + cdTrbNoPoste + "  NON_CONVENZIONATO_POSTE ");
					}
            }
            // escludo BFL
            if (excludeBFL && "GCBIRH".equals(cfgGP.getBundleKey())) { // TRIBUTI_NON_OMOGENEI
                cfgGP.setDisponibilePerPagamentoCorrente(false);
                cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente(cfgGP.getDescrizioneIndisponibilitaPerPagamentoCorrente()+"  BONIFICO FUORI LINEA NON ABILITATO ");
            }
            // escludo NDP MyBank
            if (excludeNdpMyBank && "NDP".equals(cfgGP.getCfgFornitoreGateway().getBundleKey()) && "OBEP".equals(cfgGP.getCfgModalitaPagamento().getBundleKey())) { // MYBANK NDP non abilitato
                cfgGP.setDisponibilePerPagamentoCorrente(false);
                cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente("MYBANK_NDP_NON_ABILITATO");
            }
            
            // escludo NDP 
            if (excludeNdp && "NDP".equals(cfgGP.getCfgFornitoreGateway().getBundleKey()) ) { // NODO DEI PAGAMENTI non abilitato
                cfgGP.setDisponibilePerPagamentoCorrente(false);
                cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente("NDP_NON_ABILITATO");
            }
            
         // escludo BANCA TESORIERA
            if (excludeBancaTesoriera && "01030".equals(cfgGP.getCfgFornitoreGateway().getBundleKey())) { // BANCA TESORIERA non abilitato
                cfgGP.setDisponibilePerPagamentoCorrente(false);
                cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente("BANCA_TESORIERA_NON_ABILITATO");
            }
            
         // escludo Pago Psp
            if (excludePsp && "NDP".equals(cfgGP.getCfgFornitoreGateway().getBundleKey())) { // PSP non abilitato
            	if (cfgGP.getTipoVersamento()!=null && cfgGP.getTipoVersamento().equals("PO")){
            		cfgGP.setDisponibilePerPagamentoCorrente(false);
            		cfgGP.setDescrizioneIndisponibilitaPerPagamentoCorrente("PSP_NON_ABILITATO");
            	}
            }
            
        }

        return cgfPagamentiTutti;
    }

    public Collection<CfgGatewayPagamentoDTO> filtraConfigurazioniVisualizzate(Collection<CfgGatewayPagamentoDTO> cgfPagamentiTutti,
                                                                                       List<SessionShoppingCartItemDTO> pagamentiWIP,
                                                                                       boolean isAnonymous,
                                                                                       boolean offLinePaymentsEnabled) throws Exception {

        // analizzo tutti i gateway in lista sulla base del valore di modelloVersamento
        // variabili di controllo: carrello (numero pagamenti > 1), anonimo/autenticato, incluse modalità pagamento offline
    	boolean containsMarcaBollo = containsMarcaBolloDigitale(pagamentiWIP);
        boolean carrelloPagam = pagamentiWIP.size() > 1;

        Iterator<CfgGatewayPagamentoDTO> iter = cgfPagamentiTutti.iterator();
        while (iter.hasNext()) {
            CfgGatewayPagamentoDTO cfgGtw = iter.next();
            char modVers = (cfgGtw.getModelloVersamento() != null && !"".equals(cfgGtw.getModelloVersamento()))? cfgGtw.getModelloVersamento().charAt(0) : '-';

            String descr = "";
            boolean abilitato = true;
            switch (modVers) {
                case '0':
                    if (carrelloPagam) {
                        descr = "CARRELLO_NON_SUPPORTATO;";
                        abilitato = false;
                    }
                    break;
                case '1':

                    break;
                case '2':

                    if (isAnonymous) {
                        descr = "MODALITA_ANONIMA_NON_SUPPORTATA;";
                        abilitato = false;
                    }
                    if (carrelloPagam) {
                        descr = descr + "CARRELLO_NON_SUPPORTATO;";
                        abilitato = false;
                    }
                    if (!offLinePaymentsEnabled) {
                        descr = descr + "OFFLINE_NON_AMMESSO;";
                        abilitato = false;
                    }
                    break;
                case '4':
                    if (carrelloPagam) {
                        descr = "CARRELLO_NON_SUPPORTATO;";
                        abilitato = false;
                    }
                    if (!offLinePaymentsEnabled) {
                        descr = descr + "OFFLINE_NON_AMMESSO;";
                        abilitato = false;
                    }
                    break;
                case 'A':

                    break;
                case 'B':

                    if (isAnonymous) {
                        descr = "MODALITA_ANONIMA_NON_SUPPORTATA;";
                        abilitato = false;
                    }
                    if (!offLinePaymentsEnabled) {
                        descr = descr + "OFFLINE_NON_AMMESSO;";
                        abilitato = false;
                    }
                    break;
                case 'D':
                    if (!offLinePaymentsEnabled) {
                        descr = "OFFLINE_NON_AMMESSO;";
                        abilitato = false;
                    }
                    break;
                default:
                    LOGGER.warn("Modello Versamento non previsto");
            }
            cfgGtw.setDisponibilePerPagamentoCorrente(abilitato);
            cfgGtw.setDescrizioneIndisponibilitaPerPagamentoCorrente(descr);
            if (containsMarcaBollo && abilitato){
            	// se abilitato e stiamo analizzando un carrello con marca da bollo
            	EnumTipoModalitaPagamento enumModalitaPagamento=EnumTipoModalitaPagamento.getByChiaveBundle(cfgGtw.getCfgModalitaPagamento().getBundleKey());
            	boolean isPagamentoImmediato = EnumTipoModalitaPagamento.CARTADICREDITO
        				.equals(enumModalitaPagamento)
        				|| EnumTipoModalitaPagamento.CP.equals(enumModalitaPagamento)
        				|| EnumTipoModalitaPagamento.BPT.equals(enumModalitaPagamento)
        				|| EnumTipoModalitaPagamento.PSP.equals(enumModalitaPagamento)
        		        || (EnumTipoModalitaPagamento.BBT.equals(enumModalitaPagamento) && ("0".equals(cfgGtw.getModelloVersamento()) ||
        		        		                                                           "1".equals(cfgGtw.getModelloVersamento())));

            	if ("0".equals(cfgGtw.getFlMdbGestito()) || !isPagamentoImmediato) {
            		cfgGtw.setDisponibilePerPagamentoCorrente(false);
                    cfgGtw.setDescrizioneIndisponibilitaPerPagamentoCorrente("NON_ABILITATO_MARCA_BOLLO_DIGITALE");
            	}
            }

        }

        try {
            boolean excludeBFL = false;
            boolean excludeGatewayPoste = false;
            
            ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");        	
            boolean excludeNdpMyBank=props.getBooleanProperty("gateway.list.excludeNdpMyBank");
            
            boolean excludeNdp = false;
            boolean excludeBancaTesoriera = false;
            boolean excludePsp = false;
		
			String ibanNoPoste = null;
            String enteNoPoste = null;
            String cdTrbNoPoste = null;
            //
            String idEnteBFL = null;
            String cdTrbEnteBFL = null;
            
            
            
            		
            //
//            ConfPagamentiFacade cfgBean = (ConfPagamentiFacade) ServiceLocator.getSLSBProxy("confPagamentiFacadeBean");
            //List<CfgGatewayPagamentoDTO> listaCfgPagamenti = null;
            List<Long> listaIdCfgPagamenti = null;
            Iterator<SessionShoppingCartItemDTO> sscIter = pagamentiWIP.iterator();
            // scorro tutto il carrello
            while (sscIter.hasNext()) {
                SessionShoppingCartItemDTO ssCI = sscIter.next();
                listaIdCfgPagamenti = confPagamentoBusinessBean.getIdCfgGatewayPagamentoEnabledByTributiEnti(ssCI.getIdEnte(), ssCI.getIdTributoEnte()); //
                
             
                
                //*********************************************
                // calcolo se devo escludere bonifico fuori linea
                if (idEnteBFL == null && cdTrbEnteBFL == null) {
                    idEnteBFL = ssCI.getIdEnte();  // inizializzo i valori per confronti successivi
                    cdTrbEnteBFL = ssCI.getIdTributoEnte();
                } else {
                    if (!(idEnteBFL.equals(ssCI.getIdEnte()) && cdTrbEnteBFL.equals(ssCI.getIdTributoEnte()))) {
                        excludeBFL = true; // tributi in carrello non omogenei
                    }
                }
                //********************************************
                // calcolo se devo escludere gateway di poste                
								if (!excludeGatewayPoste) {
										final String ibanBeneficiario = ssCI.getIbanBeneficiario();
				
										if (ibanBeneficiario != null && ssCI.isIbanBeneficiarioCondizione() && !isIbanPosteItaliane(ibanBeneficiario)) {
												excludeGatewayPoste = true;
												ibanNoPoste = ibanBeneficiario;
										}
				
										int num = tributoBusinessBean.countTributiEntiIbanCCpNull(ssCI.getIdEnte(), ssCI.getIdTributoEnte());
										if (num > 0) {
												excludeGatewayPoste = true; // esiste un tributo contenuto nel carrello che non puo essere pagato presso gateway
												// di poste (ibanccp = null)
												enteNoPoste = ssCI.getIdEnte();
												cdTrbNoPoste = ssCI.getIdTributoEnte();
										}
								}
                
                //*******************************************
                // check MyBank
                if (!excludeNdpMyBank && !carrelloPagam) {
                	int num=tributoBusinessBean.countTributiEntiIbanMyBankNull(ssCI.getIdEnte(), ssCI.getIdTributoEnte());
                    if (num>0){
                    	excludeNdpMyBank = true; // il tributo contenuto nel carrello che non puo essere pagato presso gateway
                    } else {
                       excludeNdpMyBank = false;
                    }
                } else {
                	excludeNdpMyBank = true;
                }
                
                
                //*******************************************
                // controllo su quali canali escludere
                
                // recupero il tributoente con i relativi flag per configurare i canali di pagamento
                TributoEnte currentTrib = tributoBusinessBean.getTributiEntiByKey(ssCI.getIdEnte(), ssCI.getIdTributoEnte());
               
                if (currentTrib !=null){
                	if (currentTrib.getFlNdp()!=null && currentTrib.getFlNdp().equals("N"))
                		excludeNdp = true;
                	if (currentTrib.getFlBancaTesoriera()!=null && currentTrib.getFlBancaTesoriera().equals("N"))
                		excludeBancaTesoriera = true;
                
                	if (currentTrib.getFlBlf()!=null && currentTrib.getFlBlf().equals("N"))
                		excludeBFL = true;
                	
                	if (currentTrib.getFlNdpModello3()!=null && currentTrib.getFlNdpModello3().equals("N"))
                		excludePsp = true;
                	
                }
                List<CfgTributiEntiGateway> listaCfgGtwPagamentoPerTipoEModelloVersamento = null;
                listaCfgGtwPagamentoPerTipoEModelloVersamento = confPagamentoBusinessBean.getListaCfgGatewayPagamentoTributiEnteFornitore(ssCI.getIdEnte(), ssCI.getIdTributoEnte());
                
                //*******************************************
                if (listaIdCfgPagamenti.size() != 0) {
                    Hashtable<Long, Long> ht = getIdCfgGatewayPagamentoKeys(listaIdCfgPagamenti);
                    // confronto ogni gateway presente in lista in listaCfgPagamenti e verifico se presente nella lista dei gateway abilitati per quel tributo
                    // se  presente abilitato= false e descrizione=descrizione+ motivo
                    iter = cgfPagamentiTutti.iterator();
                    while (iter.hasNext()) {
                        CfgGatewayPagamentoDTO cfgGtw = iter.next();
                        if (ht.containsKey(cfgGtw.getId())) {
                            cfgGtw.setDisponibilePerPagamentoCorrente(false);
                            cfgGtw.setDescrizioneIndisponibilitaPerPagamentoCorrente(cfgGtw.getDescrizioneIndisponibilitaPerPagamentoCorrente() + " " + ssCI.getIdEnte() + " " + ssCI.getTributo() + " " + " NON_CONVENZIONATO ");
                        }

                    }
                }
                
                // scorro di nuovo tutti i gateway di pagamento per togliere quelli con un particolare tipo versamento o modello versamento
                // opportunamente configurati sulla tabella CFG_TRIBUTIENTI_GATEWAY 
                // per ogni gateway vado a controllare se esistono sulla CFG_TRIBUTIENTI_GATEWAY uno o piu' record per idEnte, idTributo, idFornitoreGateway e con 
                // ID_CFG_GATEWAY_PAGAMENTO a null
                
                
              
                Iterator<CfgTributiEntiGateway> iterCfgTributo = null;
                CfgTributiEntiGateway currentCfgTributoEnteGateway = null;
                
                
                if (listaCfgGtwPagamentoPerTipoEModelloVersamento !=null && !listaCfgGtwPagamentoPerTipoEModelloVersamento.isEmpty()){
                	iter = cgfPagamentiTutti.iterator();
	                while (iter.hasNext()) {
	                    CfgGatewayPagamentoDTO cfgGtw = iter.next();
	                    iterCfgTributo = listaCfgGtwPagamentoPerTipoEModelloVersamento.iterator();
	                    while (iterCfgTributo.hasNext()) {
	                    	currentCfgTributoEnteGateway= iterCfgTributo.next();
	                		if (currentCfgTributoEnteGateway.getCfgFornitoreGateway()!=null && cfgGtw.getCfgFornitoreGateway()!=null){
	                    		 if (currentCfgTributoEnteGateway.getCfgFornitoreGateway().getId().equals(cfgGtw.getCfgFornitoreGateway().getId())){
		                    		 if (currentCfgTributoEnteGateway.getModelloVersamento()!=null && cfgGtw.getModelloVersamento()!=null && currentCfgTributoEnteGateway.getModelloVersamento().equals(cfgGtw.getModelloVersamento())){
		                    			 cfgGtw.setDisponibilePerPagamentoCorrente(false);
		                                 cfgGtw.setDescrizioneIndisponibilitaPerPagamentoCorrente(cfgGtw.getDescrizioneIndisponibilitaPerPagamentoCorrente() + " " + ssCI.getIdEnte() + " " + ssCI.getTributo() + " " + " DISABILITATO_PER_MODELLO_VERSAMENTO ");
		                    		 }
		                    		 if (currentCfgTributoEnteGateway.getTipoVersamento()!=null && cfgGtw.getTipoVersamento()!=null && currentCfgTributoEnteGateway.getTipoVersamento().equals(cfgGtw.getTipoVersamento())){
		                    			 cfgGtw.setDisponibilePerPagamentoCorrente(false);
		                                 cfgGtw.setDescrizioneIndisponibilitaPerPagamentoCorrente(cfgGtw.getDescrizioneIndisponibilitaPerPagamentoCorrente() + " " + ssCI.getIdEnte() + " " + ssCI.getTributo() + " " + " DISABILITATO_PER_TIPO_VERSAMENTO ");
		                    		 }
	                    		 }
	                		}
	                    }
	                }
                }
                
            }
            
            

            Collection<CfgGatewayPagamentoDTO> returnList = filtraGatewayPagamento(cgfPagamentiTutti, excludeBFL, excludeGatewayPoste, excludeNdpMyBank, enteNoPoste, cdTrbNoPoste, ibanNoPoste, excludeNdp,excludeBancaTesoriera,excludePsp);
            //-----
            ArrayList<CfgGatewayPagamentoDTO> arrayList = new ArrayList<CfgGatewayPagamentoDTO>();
            for (CfgGatewayPagamentoDTO c : returnList) {
                arrayList.add(c);
            }
            Collections.shuffle(arrayList);
            returnList = arrayList;
            //----
            System.out.println("--------");
            for (CfgGatewayPagamentoDTO g : returnList) {
                System.out.println("ID= "+ g.getId()+"  "+ g.getBundleKey() +  " " + g.getCfgFornitoreGateway().getBundleKey() + " "+ g.getCfgModalitaPagamento().getBundleKey() + " =" + g.isDisponibilePerPagamentoCorrente() + "-" + g.getDescrizioneIndisponibilitaPerPagamentoCorrente());
            }
            System.out.println("--------");


            return returnList;

        } catch (Throwable t) {
            LOGGER.error("Error elaborating");
            return cgfPagamentiTutti;
        }

    }
		
		private boolean isIbanPosteItaliane(String ibanBeneficiario) {
				return ibanBeneficiario.substring(5,10).equals(IbisConfiguration.ABI_POSTE);
		}
		// END :  metodi scopiazzati da GatewayPaymentAction eventualmente da rifattorizzare per uso generico
    
    private boolean containsMarcaBolloDigitale(List<SessionShoppingCartItemDTO> carrello){
    	for (SessionShoppingCartItemDTO cartItem:carrello){
    		if ("E_BOLLO".equals(cartItem.getIdTributoEnte()))
    			return true;
    	}
    	
    	return false;
    }
    

    @Override
	public CfgEntiLogoDTO getCfgEntiLogoById(String id) {
    	
    	CfgEntiLogo logo = (CfgEntiLogo) confPagamentoBusinessBean.getCfgEntiLogoById(id);
		
    	if (logo == null)
    				return null;
    	
		CfgEntiLogoDTO dto = EntiDTOBuilder.fillCfgEntiLogoDTO(logo);
				
		return dto;
	}
		
		@Override public CfgEntiLogoDTO getCfgEntiLogoByIdIntestatario(String intestatario) {
				final Enti enti = intestatariBusinessBean.readEnteByIntestatario(intestatario);
				return getCfgEntiLogoById(enti.getIdEnte());
		}
		
		@Override
	public CfgEntiLogoDTO createCfgEntiLogo(CfgEntiLogoDTO entiLogoDTO) {
		
		CfgEntiLogo entiLogo = EntiDTOBuilder.fillCfgEntiLogoEntity(entiLogoDTO);
		
		CfgEntiLogo newLogo = (CfgEntiLogo) confPagamentoBusinessBean.createCfgEntiLogo(entiLogo);
		
		CfgEntiLogoDTO newLogoDTO = EntiDTOBuilder.fillCfgEntiLogoDTO(newLogo);
		
		return newLogoDTO;
	}
	
	@Override
	public CfgEntiLogoDTO updateCfgEntiLogo(CfgEntiLogoDTO entiLogoDTO) {
		
		CfgEntiLogo entiLogo = EntiDTOBuilder.fillCfgEntiLogoEntity(entiLogoDTO);
		
		CfgEntiLogo updatedLogo = (CfgEntiLogo) confPagamentoBusinessBean.updateCfgEntiLogo(entiLogo);
		
		CfgEntiLogoDTO updatedLogoDTO = EntiDTOBuilder.fillCfgEntiLogoDTO(updatedLogo);
		
		return updatedLogoDTO;
	} 
	
    /**
    *
    */
   @Override
   public Collection<CfgGatewayPagamentoDTO> readModalitaPagamentoPsp(String idEnte) {
	   List<Object[]> l = confPagamentoBusinessBean.readModalitaPagamentoPsp(idEnte);
	   Collection<CfgGatewayPagamentoDTO> listaModalitaPagamentoPSP = new ArrayList<CfgGatewayPagamentoDTO>();
       Iterator<Object[]> iter= l.iterator();
       while (iter.hasNext()) {
    	    CfgGatewayPagamentoDTO cfgDto = new CfgGatewayPagamentoDTO();
    	    Object[] as =iter.next();
       		String nome = (String)as[0];
       		String id = (String)as[1];
       		String sub = (String)as[2];
       		cfgDto.setSystemName(nome);
       		cfgDto.setSystemId(id);
       		cfgDto.setSubsystemId(sub);
       		cfgDto.setDescrizione(nome + " (" + sub + ")");
       		cfgDto.setUrlInfoPsp(id + "," + sub);
       		listaModalitaPagamentoPSP.add(cfgDto);
       }
       return listaModalitaPagamentoPSP;
   }
   
   /**
   *
   */
  @Override
  public Collection<CfgGatewayPagamentoDTO> readModalitaPagamentoTipoVersamento(String sys, String sub) {
	   List<Object[]> l = confPagamentoBusinessBean.readModalitaPagamentoTipoVersamento(sys,sub);
	   Collection<CfgGatewayPagamentoDTO> listaModalitaPagamentoPSP = new ArrayList<CfgGatewayPagamentoDTO>();
       Iterator<Object[]> iter= l.iterator();
       while (iter.hasNext()) {
    	    CfgGatewayPagamentoDTO cfgDto = new CfgGatewayPagamentoDTO();
   	        Object[] as =iter.next();
      		String tp = (String)as[0];
      		String desM = (String)as[1];
      		String desF = (String)as[2];
      		cfgDto.setSystemId(sys);
      		cfgDto.setSubsystemId(sub);
      		cfgDto.setDescrizione(desM + " - " + desF);
      		cfgDto.setTipoVersamento(tp);
      		listaModalitaPagamentoPSP.add(cfgDto);
      }
      return listaModalitaPagamentoPSP;
  }

  
	@Override
	public CfgGatewayPagamentoDTO readCfgGatewayPagamentoDettaglioBySystemId(String systemId) {
		CfgGatewayPagamento cfg = confPagamentoBusinessBean.readCfgGatewayPagamentoDettaglioBySystemId(systemId);
		return CfgPagamentiDTOBuilder.populateCfgGatewayPagamentoDTO(cfg);
		
	}
}
