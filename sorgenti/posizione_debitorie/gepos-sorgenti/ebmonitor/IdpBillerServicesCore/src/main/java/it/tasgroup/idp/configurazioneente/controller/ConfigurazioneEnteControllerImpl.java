package it.tasgroup.idp.configurazioneente.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.anagrafica.Indirizzipostali;
import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.Sil;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.ParametriGenerazioneIUVTributoType;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.TipoOperazione;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.Tributo;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneTributi;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneTributiEsito;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurazioneEnteControllerImpl implements ConfigurazioneEnteController { 
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	@EJB(beanName = "ConfigurazioneEnteControllerTransactionsImpl")
	ConfigurazioneEnteControllerTransactions tx;
	
	
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	
	
	@Override
	public it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito idpConfigurazioneEnte(
			it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte idpConfigurazioneEnte) {
		ConfigurazioneEnteControllerEsitoBuilder esitoBuilder = new ConfigurazioneEnteControllerEsitoBuilder();
		TipoOperazione tipoOperazione = idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpBody().getConfigurazioneEnte().getTipoOperazione();
		
		// -------------------------------------		
		// Diagnosi messaggio in ingresso
		// -------------------------------------
//		HashMap<String, EnumReturnValues> errorsMap = new HashMap<String, EnumReturnValues>(); 
//		HashMap<String, ValidationException> validationExceptionMap = new HashMap<String, ValidationException>();
					
//		String TRTMsgId =  idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpHeader().getTRT().getMsgId();
//		String TRTSenderId =  idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpHeader().getTRT().getSender().getSenderId();
//		String TRTSenderSil =  idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpHeader().getTRT().getSender().getSenderSys();
		String codiceFiscale = idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpBody().getConfigurazioneEnte().getDatiAnagraficiEnte().getCodiceFiscale();
		List<Intestatari> intestatari = GestoreVerificheAnagrafiche.getIntestatarioByFiscalCodeAndLapl(codiceFiscale,  codiceFiscale,  em);
		String codiceEnte = idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpBody().getConfigurazioneEnte().getDatiAnagraficiEnte().getCodiceEnte();
		List<Enti> enti = GestoreVerificheAnagrafiche.getEntiByCodEnte(codiceEnte, em);
		
		// controlla che non sia già stato inserito lo stesso ente
		if (tipoOperazione.equals(TipoOperazione.INSERT)) {
			logger.info("richiesto inserimento ente - IdpConfigurazioneEnte");
			if (!intestatari.isEmpty())
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, EnumReturnValues.INTESTATARIO_DUPLICATO);
			if (!enti.isEmpty())
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, EnumReturnValues.ENTE_DUPLICATO);
			
			try {
				tx.insertEnte(idpConfigurazioneEnte);
			} catch (LoaderException e) {
				logger.error(e);
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, e.getErrorCode());
			}
		} else if (tipoOperazione.equals(TipoOperazione.UPDATE)) {
			logger.info("richiesto aggiornamento ente - IdpConfigurazioneEnte");
			if (intestatari.isEmpty())
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, EnumReturnValues.INTESTATARIO_INESISTENTE);
			if (enti.isEmpty())
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, EnumReturnValues.ENTE_INESISTENTE);
			List<Indirizzipostali> indirizzi = getIndirizzo(codiceFiscale);
			if (indirizzi.isEmpty())
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, EnumReturnValues.PARAMETRI_ERRATI);
			try {
				tx.updateEnte(idpConfigurazioneEnte, intestatari.get(0), enti.get(0), indirizzi.get(0));
			} catch (LoaderException e) {
				logger.error(e);
				return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, e.getErrorCode());
			}
		} else {
			return esitoBuilder.buildEsitoKO(idpConfigurazioneEnte, EnumReturnValues.TIPO_OPERAZIONE_NON_VALIDA);
		}
		return esitoBuilder.buildEsitoOK(idpConfigurazioneEnte);
	}
	
	
	


	@Override
	public IdpConfigurazioneTributiEsito idpConfigurazioneTributi(IdpConfigurazioneTributi idpConfigurazioneTributi) {
		ConfigurazioneTributoControllerEsitoBuilder esitoBuilder = new ConfigurazioneTributoControllerEsitoBuilder();
		TipoOperazione tipoOperazione = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getTipoOperazione();
		
		Tributo tributo = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getDatiAnagraficiTributo();
		String codiceEnte = tributo.getCodiceEnte();
		String cdTrbEnte = tributo.getCodiceTributo();
		String codiceSil = tributo.getCodiceSilEnte();
		List<Enti> enti = GestoreVerificheAnagrafiche.getEntiByCodEnte(codiceEnte, em);
		if (enti.isEmpty())
			return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, EnumReturnValues.ENTE_INESISTENTE);
		
		List<Sil> sil = getSil(codiceSil, enti.get(0).getIdEnte());
		if (sil.isEmpty())
			return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, EnumReturnValues.SIL_INESISTENTE);
		
		ParametriGenerazioneIUVTributoType parametriGenerazioneIUVTributoType = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getParametriGenerazioneIUV();
		try {
			if (parametriGenerazioneIUVTributoType != null && !StringUtils.isEmpty(parametriGenerazioneIUVTributoType.getPrefissoGenerazioneIUV())) 
				Long.decode(parametriGenerazioneIUVTributoType.getPrefissoGenerazioneIUV());
		} catch (Exception e) {
			return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, EnumReturnValues.PARAMETRI_ERRATI);
		}
			
		TributiEnti tributiEnti = null;
		try {
			tributiEnti = GestoreVerificheAnagrafiche.getTributoEnteByIdEnteCodTributo(enti.get(0).getIdEnte(), cdTrbEnte, em);
			if (tipoOperazione.equals(TipoOperazione.INSERT))
				return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, EnumReturnValues.TRIBUTO_DUPLICATO);
		} catch (LoaderException le) {
			if (tipoOperazione.equals(TipoOperazione.UPDATE))
				return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, le.getErrorCode());
		}
		
		if (tipoOperazione.equals(TipoOperazione.INSERT)) {
			logger.info("richiesto inserimento tributo - IdpConfigurazioneEnte");
			
			try {
				tx.insertTributo(idpConfigurazioneTributi, enti.get(0));
			} catch (LoaderException e) {
				logger.error(e);
				return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, e.getErrorCode());
			}
		} else if (tipoOperazione.equals(TipoOperazione.UPDATE)) {
			logger.info("richiesto aggiornamento tributo - IdpConfigurazioneEnte");
			try {
				tx.updateTributo(idpConfigurazioneTributi, tributiEnti);
			} catch (LoaderException e) {
				logger.error(e);
				return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, e.getErrorCode());
			}
		} else {
			return esitoBuilder.buildEsitoKO(idpConfigurazioneTributi, EnumReturnValues.TIPO_OPERAZIONE_NON_VALIDA);
		}
		return esitoBuilder.buildEsitoOK(idpConfigurazioneTributi);
	}	
	
	@SuppressWarnings("unchecked")
	private List<Sil> getSil(String idSystem, String cdEnte) {
		Query queryFindSIL = em.createNamedQuery("SILByPk");	
		queryFindSIL.setParameter("idSystem", idSystem);
		queryFindSIL.setParameter("idEnte", cdEnte);
	
		return queryFindSIL.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private List<Indirizzipostali> getIndirizzo(String codiceFiscale) {
		Query queryFindIndirizzo = em.createNamedQuery("IndirizzoByCodFiscale");	
		queryFindIndirizzo.setParameter("codiceFiscale", codiceFiscale);
		return queryFindIndirizzo.getResultList();
	}
	
}
