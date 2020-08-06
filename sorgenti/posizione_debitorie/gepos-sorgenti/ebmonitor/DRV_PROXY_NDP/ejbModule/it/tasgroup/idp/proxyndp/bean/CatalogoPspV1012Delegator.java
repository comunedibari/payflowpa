package it.tasgroup.idp.proxyndp.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta;
import it.tasgroup.idp.gateway.CfgCanalePagamento;
import it.tasgroup.idp.gateway.CfgCommissionePagamento;
import it.tasgroup.idp.gateway.CfgDocumentoPagamento;
import it.tasgroup.idp.gateway.CfgFornitoreGateway;
import it.tasgroup.idp.gateway.CfgGatewayPagamento;
import it.tasgroup.idp.gateway.CfgModalitaPagamento;
import it.tasgroup.idp.gateway.CfgStrumentoPagamento;
import it.tasgroup.idp.gateway.CfgTipoCommissione;
import it.tasgroup.idp.gateway.enums.EnumCanalePagamento;
import it.tasgroup.idp.gateway.enums.EnumDocumentoPagamento;
import it.tasgroup.idp.gateway.enums.EnumFornitorePagamento;
import it.tasgroup.idp.gateway.enums.EnumModalitaPagamento;
import it.tasgroup.idp.gateway.enums.EnumStrumentoPagamento;
import it.tasgroup.idp.gateway.enums.EnumTipoCommissione;
import it.tasgroup.idp.proxyndp.exception.NDPComunicationException;
import it.tasgroup.idp.proxyndp.utils.NDPConstants;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.CtInformativaDetail;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.CtInformativaMaster;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.CtInformativaPSP;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.CtInformazioniServizio;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.CtListaInformativePSP;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.ObjectFactory;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.StCodiceLingua;
import it.tasgroup.ndpmodel.nodopagamentispc.payloads.listainformativepsp.v1012.StTipoVersamento;

public class CatalogoPspV1012Delegator implements ICatalogoPspDelegator {

	private final Log logger = LogFactory.getLog(this.getClass());
	private int catSize;
	
	
/* (non-Javadoc)
 * @see it.tasgroup.idp.proxyndp.bean.ICatalogoPspDelegator#aggiornaGatewayPagamento(java.util.List, java.lang.Object, javax.persistence.EntityManager)
 */
@Override
public void aggiornaGatewayPagamento(List<CfgGatewayPagamento> gatewayAttuali, Object  listaInformativePSPObj,EntityManager manager) {
		
	    CtListaInformativePSP listaInformativePSPType = (CtListaInformativePSP) listaInformativePSPObj;
		boolean isGestioneAutomatica = Boolean.valueOf(IrisProperties.getProperty("iris.catalogoPsp.ndp.gestioneAutomatica", "false"));
		
		Map<String, CfgGatewayPagamento> mappaGateway = new HashMap<String, CfgGatewayPagamento>();
		for (CfgGatewayPagamento c : gatewayAttuali) {
			mappaGateway.put(c.getBundleKey(), c);
		}
		logger.info("mappaGateway prima: " + mappaGateway.size());

		for (CtInformativaPSP infoPSP : listaInformativePSPType.getInformativaPSP()) {
			for (CtInformativaDetail informativaPspDetail : infoPSP.getListaInformativaDetail().getInformativaDetail()) {

				String bundleKey = infoPSP.getIdentificativoPSP() + "-"
						+ informativaPspDetail.getIdentificativoCanale() + "-"
						+ informativaPspDetail.getTipoVersamento();
				
				// N.B. uso remove cos� i rimanenti della map sono tutti i
				// gateway non contenuti nell'informativa del nodo
				// che vanno disattivati
				CfgGatewayPagamento cfgGateway = mappaGateway.remove(bundleKey);
				
				if (cfgGateway == null) {
					//
					// Gateway non presente --> INSERT
					//
					cfgGateway = new CfgGatewayPagamento();
					mergeCfgGatewayPagamento(cfgGateway, infoPSP, informativaPspDetail, manager);
					logger.info("Psp non presente in banca dati. INSERT " + cfgGateway.getBundleKey() + "::" + cfgGateway.getApplicationId() + ":" + cfgGateway.getSystemId());

					if(isGestioneAutomatica) {
						Timestamp dataInizioValidita = new Timestamp(infoPSP.getInformativaMaster().getDataInizioValidita().toGregorianCalendar().getTimeInMillis());
						cfgGateway.setStato(NDPConstants.ACTIVE);//setStato(dataInizioValidita.after(NodoPagamentiUtils.now()) ? NDPConstants.TO_ACTIVATE	: NDPConstants.ACTIVE);
					} else {
						// intanto inserisco il record disattivo. Lo attiver� in seguito MANUALMENTE
						cfgGateway.setStato(NDPConstants.DEACTIVE);
					}
					cfgGateway.setStRiga(NDPConstants.ST_RIGA_V);
					cfgGateway.setOpInserimento(NDPConstants.OPERATOR_NODO_CHIEDI_INFO_PSP);
					cfgGateway.setTipoVersamento(informativaPspDetail.getTipoVersamento().name());
					manager.persist(cfgGateway);

				} else {
					//
					// Gateway presente --> UPDATE
					//
					mergeCfgGatewayPagamento(cfgGateway, infoPSP, informativaPspDetail,manager);
					logger.info("Psp gia' presente in banca dati. UPDATE " + cfgGateway.getBundleKey() + "::" + cfgGateway.getApplicationId() + ":" + cfgGateway.getSystemId() + " - id: " + cfgGateway.getId() );

					if(isGestioneAutomatica) {
						Timestamp dataInizioValidita = new Timestamp(infoPSP.getInformativaMaster().getDataInizioValidita().toGregorianCalendar().getTimeInMillis());
						cfgGateway.setStato(NDPConstants.ACTIVE);//setStato(dataInizioValidita.after(NodoPagamentiUtils.now()) ? NDPConstants.TO_ACTIVATE	: NDPConstants.ACTIVE);
					} else {
						// nada. infatti la gestione � manuale.
					}
					cfgGateway.setStRiga(NDPConstants.ST_RIGA_V);
					cfgGateway.setOpAggiornamento(NDPConstants.OPERATOR_NODO_CHIEDI_INFO_PSP);
					cfgGateway.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
				}

			}
		}
		
		//
		// DISATTIVAZIONE GATEWAY NON PRESENTI NELL'INFORMATIVA DEL NODO
		//
		logger.info("mappaGateway dopo: " + mappaGateway.size());
		
		for (String key : mappaGateway.keySet()) {
			CfgGatewayPagamento cfgGateway = mappaGateway.get(key);

			if ("AGID_01".equals(cfgGateway.getSystemId())) { 
				logger.info("DISATTIVAZIONE GATEWAY. NON DISATTIVO IL GATEWAY AGID_01 " + cfgGateway.getBundleKey() + "::" + cfgGateway.getApplicationId() + ":" + cfgGateway.getSystemId() + " - id: " + cfgGateway.getId() );                          
			} else if ("DEFAULT3XXX".equals(cfgGateway.getSystemId())) { 
				logger.info("DISATTIVAZIONE GATEWAY. NON DISATTIVO IL GATEWAY DEFAULT3XXX " + cfgGateway.getBundleKey() + "::" + cfgGateway.getApplicationId() + ":" + cfgGateway.getSystemId() + " - id: " + cfgGateway.getId() );                          
			} else { 
				logger.info("DISATTIVAZIONE GATEWAY. UPDATE " + cfgGateway.getBundleKey() + "::" + cfgGateway.getApplicationId() + ":" + cfgGateway.getSystemId() + " - id: " + cfgGateway.getId() );
				cfgGateway.setStato(NDPConstants.DEACTIVE);
				cfgGateway.setStRiga(NDPConstants.ST_RIGA_N);
				cfgGateway.setOpAggiornamento(NDPConstants.OPERATOR_NODO_CHIEDI_INFO_PSP);
			}

		}
		
		
	
	}

	
	
	

	/* (non-Javadoc)
	 * @see it.tasgroup.idp.proxyndp.bean.ICatalogoPspDelegator#readResult(gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta)
	 */
	@Override
	public Object readResult(NodoChiediInformativaPSPRisposta risposta)
			throws IOException,	JAXBException, NDPComunicationException {
		
		if (risposta.getFault() != null) {
			throw new NDPComunicationException(risposta.getFault());
		}
		
		InputStream isMsgNonEncoded = (ByteArrayInputStream) risposta.getXmlInformativa().getContent();
		
		
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller um = context.createUnmarshaller();
		um.setEventHandler (new ValidationEventHandler() {
		    @Override
		    public boolean handleEvent(ValidationEvent event) {
			   return false;
		    }
	    });

		JAXBElement<CtListaInformativePSP> unmarshal = (JAXBElement<CtListaInformativePSP>)um.unmarshal(isMsgNonEncoded);
		CtListaInformativePSP result = unmarshal.getValue();
		catSize=result.getInformativaPSP().size();
		return result;
	}
	
	
	



	private void mergeCfgGatewayPagamento (
			CfgGatewayPagamento cfgGateway,
			CtInformativaPSP informativaPsp,
			CtInformativaDetail informativaPspDetail,
			EntityManager manager) {

		
		
		logger.info("Recuperato il psp " + informativaPsp.getIdentificativoPSP() + "-"
				+ informativaPspDetail.getIdentificativoCanale() + "-" + informativaPspDetail.getTipoVersamento());
		CtInformativaMaster informativaMaster = informativaPsp.getInformativaMaster();

		CtInformazioniServizio infoServizioByLanguage = getInformazioniServizioByLanguage(informativaPspDetail,StCodiceLingua.IT);
		
		Timestamp dataInizioValidita = new Timestamp(informativaMaster.getDataInizioValidita().toGregorianCalendar().getTimeInMillis());
		Timestamp dataFineValidita = new Timestamp(new GregorianCalendar(2099, 1, 1, 0, 0).getTimeInMillis());
		Timestamp dataPubblicazione = new Timestamp(informativaPsp.getInformativaMaster().getDataPubblicazione().toGregorianCalendar().getTimeInMillis());

		cfgGateway.setBundleKey(informativaPsp.getIdentificativoPSP() + "-" + informativaPspDetail.getIdentificativoCanale()
				+ "-" + informativaPspDetail.getTipoVersamento());
		cfgGateway.setDescrizione(infoServizioByLanguage.getDescrizioneServizio());
		// N.B. guarda metodo chiamante. lo stato viene settato in modo differente a seconda che si decida o meno
		// una gestione "manuale" dello stato
		// cfgGateway.setStato(dataInizioValidita.after(NodoPagamentiUtils.today()) ? NDPConstants.TO_ACTIVATE	: NDPConstants.ACTIVE);
		cfgGateway.setStRiga(NDPConstants.ST_RIGA_V);
		cfgGateway.setDataInizioValidita(dataInizioValidita);
		cfgGateway.setDataFineValidita(dataFineValidita);
		cfgGateway.setSystemId(informativaPsp.getIdentificativoPSP());
		cfgGateway.setApplicationId(informativaPspDetail.getIdentificativoCanale() + "-"
				+ informativaPspDetail.getTipoVersamento());
		cfgGateway.setDataPubblicazione(dataPubblicazione);
		cfgGateway.setSystemName(informativaPsp.getRagioneSociale());
		cfgGateway.setSubsystemId(informativaPspDetail.getIdentificativoIntermediario());
		cfgGateway.setPriorita("" + informativaPspDetail.getPriorita());
		cfgGateway.setDisponibilitaServizio(infoServizioByLanguage.getDisponibilitaServizio());
		cfgGateway.setDescGateway(informativaPsp.getRagioneSociale());
		
		cfgGateway.setTipoVersamento(informativaPspDetail.getTipoVersamento().name());

		cfgGateway.setFlStornoGestito(informativaPsp.getInformativaMaster().getStornoPagamento() != 0 ? "Y" : "N");

		cfgGateway.setFlMdbGestito(informativaPsp.getInformativaMaster().getMarcaBolloDigitale()+"");
		
		if (StTipoVersamento.OBEP.equals(informativaPspDetail.getTipoVersamento())) {
			cfgGateway.setFlagRendVersamento("0");
		} else {
			cfgGateway.setFlagRendVersamento("1");
		}
		if(StTipoVersamento.PO.equals(informativaPspDetail.getTipoVersamento())) {
			cfgGateway.setMolteplicita("1");
		} else {
			cfgGateway.setMolteplicita("5");
		}
		
		
		try {
			new URL(informativaMaster.getUrlInformazioniPSP());
			cfgGateway.setUrlInfoPsp(informativaMaster.getUrlInformazioniPSP());
		} catch (MalformedURLException e) {
			// Non e' una URL. non ci metto niente.
		}
		try {
			new URL(infoServizioByLanguage.getUrlInformazioniCanale());
			cfgGateway.setUrlInfoCanale(infoServizioByLanguage.getUrlInformazioniCanale());
		} catch (MalformedURLException e) {
			// Non e' una URL. non ci metto niente.
		}
		cfgGateway.setModelloVersamento("" + informativaPspDetail.getModelloPagamento());


		Long idCfgModalitaPagamento  = null;
		Long idCfgStrumentoPagamento  = null;
		Long idCfgCanalePagamento  = null;
		
		switch (informativaPspDetail.getTipoVersamento()) {
		case AD:
			idCfgModalitaPagamento = EnumModalitaPagamento.ADDEBITO_DIRETTO.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.ADDEBITO_DIRETTO.getKey();
			idCfgCanalePagamento  = EnumCanalePagamento.BANCA.getKey();
			break;
		case BBT:
			idCfgModalitaPagamento = EnumModalitaPagamento.BONIFICO_BANCARIO_TESORERIA.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.BONIFICO.getKey();
			idCfgCanalePagamento  = EnumCanalePagamento.BANCA.getKey();
			break;
		case BP:
			idCfgModalitaPagamento = EnumModalitaPagamento.BOLLETTINO_POSTALE.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.BONIFICO.getKey();;
			idCfgCanalePagamento  = EnumCanalePagamento.POSTE.getKey();
			break;
		case CP:
			idCfgModalitaPagamento = EnumModalitaPagamento.CARTA_PAGAMENTO.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.CARTA_PAGAMENTO.getKey();
			idCfgCanalePagamento  = EnumCanalePagamento.WEB.getKey();
			break;
		case PO:
			idCfgModalitaPagamento = EnumModalitaPagamento.ATTIVATO_PRESSO_PSP.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.DOCUMENTO_PAGAMENTO.getKey();
			idCfgCanalePagamento  = EnumCanalePagamento.PSP.getKey();
			break;
		case OBEP:
			idCfgModalitaPagamento = EnumModalitaPagamento.MYBANK_NDP.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.BONIFICO.getKey();
			idCfgCanalePagamento  = EnumCanalePagamento.WEB.getKey();
			break;
		case JIF:
			idCfgModalitaPagamento = EnumModalitaPagamento.BANCOMAT_PAY.getKey();
			idCfgStrumentoPagamento = EnumStrumentoPagamento.CARTA_PAGAMENTO.getKey();
			idCfgCanalePagamento  = EnumCanalePagamento.WEB.getKey();			
			break;
		}
		if (idCfgModalitaPagamento != null)
			cfgGateway.setCfgModalitaPagamento(manager.find(CfgModalitaPagamento.class, idCfgModalitaPagamento));
		if (idCfgStrumentoPagamento != null)
			cfgGateway.setCfgStrumentoPagamento(manager.find(CfgStrumentoPagamento.class, idCfgStrumentoPagamento));
		if (idCfgCanalePagamento != null)
			cfgGateway.setCfgCanalePagamento(manager.find(CfgCanalePagamento.class, idCfgCanalePagamento));

		cfgGateway.setCfgFornitoreGateway(manager.find(CfgFornitoreGateway.class, EnumFornitorePagamento.NODO_PAGAMENTI_SPC.getKey()));
		
		
		if(informativaPspDetail.getModelloPagamento() == 4) {
			cfgGateway.setCfgDocumentoPagamento(manager.find(CfgDocumentoPagamento.class, EnumDocumentoPagamento.BOLLETTINO_NDP.getKey()));
		} else {
			cfgGateway.setCfgDocumentoPagamento(null);
		}

		
		List<CfgCommissionePagamento> listaCfgCommissioni = cfgGateway.getCfgCommissionePagamentos();
		
		if (listaCfgCommissioni != null && listaCfgCommissioni.size() > 0) {

			CfgCommissionePagamento cfgCommissione = listaCfgCommissioni.iterator().next();  // Assert: Ci deve essere sempre uno ed un solo record nella tabella commissioni
			cfgCommissione.setOpAggiornamento(NDPConstants.OPERATOR_NODO_CHIEDI_INFO_PSP);
			//cfgCommissione.setDescrizione(infoServizioByLanguage.getCondizioniEconomicheMassime());

		} else {
			listaCfgCommissioni = new ArrayList<CfgCommissionePagamento>();

			CfgCommissionePagamento nuovaCfgCommissione = new CfgCommissionePagamento();
			nuovaCfgCommissione.setCfgGatewayPagamento(cfgGateway);
			nuovaCfgCommissione.setCfgTipoCommissione(manager.find(CfgTipoCommissione.class, EnumTipoCommissione.GESTIONE_ESTERNA.getKey()));
			nuovaCfgCommissione.setDivisa(NDPConstants.CURRENCY);
			nuovaCfgCommissione.setStRiga(NDPConstants.ST_RIGA_V);
			nuovaCfgCommissione.setValore(new BigDecimal(0));
			nuovaCfgCommissione.setOpInserimento(NDPConstants.OPERATOR_NODO_CHIEDI_INFO_PSP);
			//nuovaCfgCommissione.setDescrizione(infoServizioByLanguage.getCondizioniEconomicheMassime());
			manager.persist(nuovaCfgCommissione);
			
			listaCfgCommissioni.add(nuovaCfgCommissione);
		}
		
	}

	private CtInformazioniServizio getInformazioniServizioByLanguage(CtInformativaDetail infoPspDetail,StCodiceLingua language){
		for (CtInformazioniServizio infoServ: infoPspDetail.getListaInformazioniServizio().getInformazioniServizio()) {
			if (infoServ.getCodiceLingua().equals(language)){
				return infoServ;
			}
		}
		return null;
	}





	@Override
	public int catalogoSize() {
		return catSize;
	}
}
