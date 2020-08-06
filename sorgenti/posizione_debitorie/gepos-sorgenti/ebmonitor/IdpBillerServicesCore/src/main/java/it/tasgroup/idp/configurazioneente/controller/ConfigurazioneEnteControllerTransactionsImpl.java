package it.tasgroup.idp.configurazioneente.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

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
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.ConfigNotifiche;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.Sil;
import it.tasgroup.idp.domain.enti.SilPK;
import it.tasgroup.idp.domain.enti.Tributi;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.iuv.IUVSequence;
import it.tasgroup.idp.domain.iuv.IUVSequencePK;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumIuvSequenceType;
import it.tasgroup.iris2.enums.EnumTipoAttivo;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.ConfigurazioneEnte;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.DatiAnagraficiEnte;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.ParametriGenerazioneIUVEnteType;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.ParametriGenerazioneIUVTributoType;
import it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.Tributo;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneTributi;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ConfigurazioneEnteControllerTransactionsImpl implements ConfigurazioneEnteControllerTransactions {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	private static String OPERATORE = "ConfigurazioneIDP";
	
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertEnte(
			it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte idpConfigurazioneEnte)
			throws LoaderException {
		ConfigurazioneEnte ce = idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpBody().getConfigurazioneEnte();
		DatiAnagraficiEnte anag = ce.getDatiAnagraficiEnte();
		ParametriGenerazioneIUVEnteType paramIuv = ce.getParametriGenerazioneIUV();
		Intestatari intestatario = new Intestatari();
		Indirizzipostali ip = new Indirizzipostali();
		intestatario.setIndirizzipostali(ip);
		Enti ente = new Enti();
		Sil sil = new Sil();
		
		try {
			intestatario.setLapl(anag.getCodiceFiscale());
			intestatario.setRagionesociale(anag.getRagioneSociale());
			intestatario.setCategoria("EN");
			intestatario.setIntestatario(GeneratoreIdUnivoci.GetCurrent().generaId());
			intestatario.setPrVersione(1);
			intestatario.setOpInserimento(OPERATORE);
			intestatario.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			ip.setFiscalCode(anag.getCodiceFiscale());
			ip.setVatCode(anag.getPartitaIVA());
			if (anag.getIndirizzo() != null) {
				ip.setAddress(anag.getIndirizzo().getIndirizzo());
				ip.setCity(anag.getIndirizzo().getComune());
				ip.setProvince(anag.getIndirizzo().getProvincia());
				ip.setCapCode(anag.getIndirizzo().getCAP());
				ip.setCountry(anag.getIndirizzo().getNazione());
				ip.setPhoneNumber(anag.getIndirizzo().getNumeroTelefono());
				ip.setFaxNumber(anag.getIndirizzo().getNumeroFAX());
				ip.setPostBox(anag.getIndirizzo().getCasellaPostale());
				ip.setEmail(anag.getIndirizzo().getEmail());
			}
			ip.setVersion(1);
			ip.setOpInserimento(OPERATORE);
			
			ente.setIdEnte(GeneratoreIdUnivoci.GetCurrent().generaId());
			ente.setCdEnte(anag.getCodiceEnte());
			ente.setDenom(anag.getRagioneSociale());
			ente.setTpEnte(anag.getTipoEnte());
			ente.setFlNdp("S");
			ente.setFlNdpModello3("S");
		    ente.setStato("A");
		    if (anag.getIndirizzo() != null) {
		    	ente.setProvincia(anag.getIndirizzo().getProvincia());
		    }
		    ente.setIntestatario(intestatario.getIntestatario());
		    if (paramIuv != null) {
				if (StringUtils.isNotEmpty(paramIuv.getAuxDigit())) ente.setNdpAuxDigit(paramIuv.getAuxDigit());
				if (StringUtils.isNotEmpty(paramIuv.getCodiceSegregazione()))  ente.setNdpCodSegr(paramIuv.getCodiceSegregazione());
				if (StringUtils.isNotEmpty(paramIuv.getCodiceStazione())) ente.setNdpCodStazPa(paramIuv.getCodiceStazione());
		    }
			ente.setPrVersione(1);
			ente.setOpInserimento(OPERATORE);
			
			SilPK pk = new SilPK();
			pk.setIdEnte(ente.getIdEnte());
	//		pk.setIdSystem(silType.getCodiceSIL());
			pk.setIdSystem("SIL_" + ente.getCdEnte());
			sil.setId(pk);
			sil.setDeSystem("SIL " + ente.getDenom());
	//		sil.setUserId(silType.getUserId());
	//		sil.setAuthId(silType.getSILCredential());
			sil.setTrtId(ente.getCdEnte());
			sil.setTrtSystem("SIL_" + ente.getCdEnte());
			sil.setsSilEnabledAsString("N");
			sil.setStato("A");
			sil.setPrVersione(1);
			sil.setOpInserimento(OPERATORE);
			em.persist(intestatario);
			em.persist(ip);
			em.persist(ente);
			em.persist(sil);
			em.flush();
		} catch (Throwable t) {
			logger.error(t);
			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateEnte(IdpConfigurazioneEnte idpConfigurazioneEnte, Intestatari intestatario, Enti ente, Indirizzipostali ip)
			throws LoaderException {
		ConfigurazioneEnte ce = idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpBody().getConfigurazioneEnte();
		DatiAnagraficiEnte anag = ce.getDatiAnagraficiEnte();
		ParametriGenerazioneIUVEnteType paramIuv = ce.getParametriGenerazioneIUV();
		if (StringUtils.isNotEmpty(anag.getRagioneSociale()) && 
				!ente.getDenom().equalsIgnoreCase(anag.getRagioneSociale()))
			throw new LoaderException(EnumReturnValues.PARAMETRI_ERRATI, "RagioneSociale");
		
		try {
			if (StringUtils.isNotEmpty(anag.getPartitaIVA())) ip.setVatCode(anag.getPartitaIVA());
			if (anag.getIndirizzo() != null) {
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getIndirizzo())) ip.setAddress(anag.getIndirizzo().getIndirizzo());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getComune())) ip.setCity(anag.getIndirizzo().getComune());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getProvincia())) ip.setProvince(anag.getIndirizzo().getProvincia());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getCAP())) ip.setCapCode(anag.getIndirizzo().getCAP());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getNazione())) ip.setCountry(anag.getIndirizzo().getNazione());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getNumeroTelefono())) ip.setPhoneNumber(anag.getIndirizzo().getNumeroTelefono());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getNumeroFAX())) ip.setFaxNumber(anag.getIndirizzo().getNumeroFAX());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getCasellaPostale())) ip.setPostBox(anag.getIndirizzo().getCasellaPostale());
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getEmail())) ip.setEmail(anag.getIndirizzo().getEmail());
			}
			ip.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			ip.setOpAggiornamento(OPERATORE);
			if (anag.getIndirizzo() != null) {
				if (StringUtils.isNotEmpty(anag.getIndirizzo().getProvincia())) ente.setProvincia(anag.getIndirizzo().getProvincia());
			}
			//ParametriGenerazioneIUVEnteType parametriGenerazioneIUV = idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpBody().getConfigurazioneEnte().getParametriGenerazioneIUV();
			if (paramIuv != null) {
				if (StringUtils.isNotEmpty(paramIuv.getAuxDigit()))  ente.setNdpAuxDigit(paramIuv.getAuxDigit());
				if (StringUtils.isNotEmpty(paramIuv.getCodiceSegregazione())) ente.setNdpCodSegr(paramIuv.getCodiceSegregazione());
				if (StringUtils.isNotEmpty(paramIuv.getCodiceStazione()))  ente.setNdpCodStazPa(paramIuv.getCodiceStazione());
			}
			if (StringUtils.isNotEmpty(anag.getTipoEnte())) ente.setTpEnte(anag.getTipoEnte());
			ente.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			ente.setOpAggiornamento(OPERATORE);
			em.merge(ip);
			em.merge(ente);
			em.flush();
		} catch (Throwable t) {
			logger.error(t);
			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertTributo(IdpConfigurazioneTributi idpConfigurazioneTributi, Enti ente) throws LoaderException {
		Tributo datiAnagraficiTributo = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getDatiAnagraficiTributo();
		ParametriGenerazioneIUVTributoType parametriGenerazioneIUVTributoType = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getParametriGenerazioneIUV();
		try {
			TributiEntiPK id = new TributiEntiPK();
			id.setCdTrbEnte(datiAnagraficiTributo.getCodiceTributo());
			id.setIdEnte(ente.getIdEnte());
			
			TributiEnti tributoEnte = new TributiEnti();
			//default
			tributoEnte.setFlAddRTVerificaPag("N");
			tributoEnte.setFlIniziativa("Y");
			tributoEnte.setFlPredeterm("Y"); // PREDETERMINATO
			tributoEnte.setFlNotificaPagamento("Y");
			tributoEnte.setFlReplaceOTF("N");
			// default notifica
			ArrayList<ConfigNotifiche> cfgNotifiche = new ArrayList<ConfigNotifiche>();
			cfgNotifiche.add(generaNotifica(tributoEnte, "PULL_WS", "CSV_BASIC_V2", "AD_EVENTO", "ESEGUITO"));
			cfgNotifiche.add(generaNotifica(tributoEnte, "PULL_WS", "CSV_BASIC_V2", "AD_EVENTO", "INCASSO"));
			cfgNotifiche.add(generaNotifica(tributoEnte, "PULL_WS", "CSV_BASIC_V2", "AD_EVENTO", "REGOLATO"));
			tributoEnte.setCfgNotifiche(cfgNotifiche);
			
			tributoEnte.setId(id);
			tributoEnte.setDeTrb(datiAnagraficiTributo.getDescrizione());
			tributoEnte.setJlttrpa(getTributo(datiAnagraficiTributo.getCategoria()));
			tributoEnte.setIdSystem(datiAnagraficiTributo.getCodiceSilEnte());
			tributoEnte.setIban(datiAnagraficiTributo.getIBANAccreditoBancario());
			tributoEnte.setIBAN_CCP(datiAnagraficiTributo.getIBANAccreditoPostale());
			tributoEnte.setStato(EnumTipoAttivo.getKeyByDescription(datiAnagraficiTributo.getStato().value()));
			tributoEnte.setFlRicevutaAnonimo("Y");
			tributoEnte.setFlNdpModello3(datiAnagraficiTributo.isPagamentoPressoPSP() ? "S" : "N");
			
			
			//-- Gestione della generazione IUV
			tributoEnte.setFlNdpIuvGenerato(datiAnagraficiTributo.isIuvGenerato() ? "1" : "0");
			if (parametriGenerazioneIUVTributoType != null && !StringUtils.isEmpty(parametriGenerazioneIUVTributoType.getPrefissoGenerazioneIUV())) 
				tributoEnte.setNdpIuvStartNum(Long.decode(parametriGenerazioneIUVTributoType.getPrefissoGenerazioneIUV()));
			String iuvSequenceType = IrisProperties.getProperty("iuv.generation.seq.type");
			// qui direi che la property DEVE essere impostata esplicitamente come
			// E = Ente
			// T = Tributo
			// Non capisco perchè il passaggio da un ENUM... 
			String iuvSequenceTypeValue = EnumIuvSequenceType.getByPropertyValue(iuvSequenceType).getChiave();
			tributoEnte.setIuvGenSeqType(iuvSequenceTypeValue); 
			// Qui la logica che inserisce sempre la SEQ_IUV indipendentemente dal IUV generato o fornito
			if (iuvSequenceTypeValue.equals(EnumIuvSequenceType.ENTE.getChiave())){
			    IUVSequenceManager.insertOrEnable(ente.getIdEnte(), "*", em);
			}else { 
			    IUVSequenceManager.insertOrEnable(ente.getIdEnte(), datiAnagraficiTributo.getCodiceTributo(), em);
			} 
	
			// ente
			tributoEnte.setNdpCodSegr(ente.getNdpCodSegr());
			tributoEnte.setNdpCodStazPa(ente.getNdpCodStazPa());
			tributoEnte.setNdpAuxDigit(ente.getNdpAuxDigit());
			
			tributoEnte.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			tributoEnte.setOpInserimento(OPERATORE);
			
			em.persist(tributoEnte);
			em.flush();
		} catch (Throwable t) {
			logger.error(t);
			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO);
		}
	}

	private ConfigNotifiche generaNotifica(TributiEnti tributoEnte, String consegnaNotifica,
			String formatoNotifica,
			String freqNotifica,
			String tipoNotifica) {
		ConfigNotifiche notificaEseguito = new ConfigNotifiche();
		notificaEseguito.setJltentr(tributoEnte);
		notificaEseguito.setConsegnaNotifica(consegnaNotifica);
		notificaEseguito.setFormatoNotifica(formatoNotifica);
		notificaEseguito.setFreqNotifica(freqNotifica);
		notificaEseguito.setTipoNotifica(tipoNotifica);
		notificaEseguito.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
		notificaEseguito.setOpAggiornamento(OPERATORE);
		return notificaEseguito;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTributo(IdpConfigurazioneTributi idpConfigurazioneTributi, TributiEnti tributoEnte)
			throws LoaderException {
		Tributo datiAnagraficiTributo = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getDatiAnagraficiTributo();
		ParametriGenerazioneIUVTributoType parametriGenerazioneIUVTributoType = idpConfigurazioneTributi.getIdpConfigurazioneTributi().getIdpBody().getConfigurazioneTributi().getParametriGenerazioneIUV();
		try {
			if (!StringUtils.isEmpty(datiAnagraficiTributo.getCategoria())) tributoEnte.setJlttrpa(getTributo(datiAnagraficiTributo.getCategoria()));
			if (!StringUtils.isEmpty(datiAnagraficiTributo.getDescrizione())) tributoEnte.setDeTrb(datiAnagraficiTributo.getDescrizione());
			if (!StringUtils.isEmpty(datiAnagraficiTributo.getCodiceSilEnte())) tributoEnte.setIdSystem(datiAnagraficiTributo.getCodiceSilEnte());
			if (!StringUtils.isEmpty(datiAnagraficiTributo.getIBANAccreditoBancario()))tributoEnte.setIban(datiAnagraficiTributo.getIBANAccreditoBancario());
			if (!StringUtils.isEmpty(datiAnagraficiTributo.getIBANAccreditoPostale()))tributoEnte.setIBAN_CCP(datiAnagraficiTributo.getIBANAccreditoPostale());
			if (parametriGenerazioneIUVTributoType != null && !StringUtils.isEmpty(parametriGenerazioneIUVTributoType.getPrefissoGenerazioneIUV())) 
				tributoEnte.setNdpIuvStartNum(Long.decode(parametriGenerazioneIUVTributoType.getPrefissoGenerazioneIUV()));
			if (datiAnagraficiTributo.getStato() != null) tributoEnte.setStato(EnumTipoAttivo.getKeyByDescription(datiAnagraficiTributo.getStato().value()));
			tributoEnte.setFlNdpModello3(datiAnagraficiTributo.isPagamentoPressoPSP() ? "S" : "N");
			tributoEnte.setFlNdpIuvGenerato(datiAnagraficiTributo.isIuvGenerato() ? "1" : "0");
			tributoEnte.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			tributoEnte.setOpAggiornamento(OPERATORE);
			em.merge(tributoEnte);
			em.flush();
		} catch (Throwable t) {
			logger.error(t);
			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO);
		}
	}
	
	private Tributi getTributo(String idTributo) {
		Query queryFindTributo = em.createNamedQuery("TributiByIdTributo");	
		queryFindTributo.setParameter("idTributo", idTributo);
		return (Tributi)queryFindTributo.getSingleResult();
	}
}
