package it.tasgroup.idp.cart.core.quadratura;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.cart.core.model.ErroreComponenteModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.core.model.converter.EntityConverter;
import it.tasgroup.idp.cart.core.quadratura.DatiRiepilogo.StatoServizio;
import it.tasgroup.idp.cart.orm.monitoraggio.Gestioni;
import it.tasgroup.idp.cart.orm.monitoraggio.Messaggi;
import it.tasgroup.idp.cart.orm.monitoraggio.enums.EnumTipoGestione;
import it.tasgroup.idp.cart.orm.monitoraggio.enums.EnumTipoMessaggio;

@Stateless
public class DatiQuadraturaBuilder implements IDatiQuadratura{

	@PersistenceContext(unitName="dscmtCart")
	private EntityManager entityManager;

	private final Log log = LogFactory.getLog(this.getClass());

	@PostConstruct
	public void init(){

	}

	@Override
	public void creaDatiQuadratura(DatiQuadratura datiQuadratura) throws Exception{

		//1 Report AP
		this.log.debug("Creazione del report AP in corso...");
		Report reportAP = new Report();
		TipoMessaggio tipoMessaggioAP = TipoMessaggio.ALLINEAMENTO_PENDENZE;
		reportAP.setTipo(tipoMessaggioAP);

		TipoGestione tipoGestioneInbound = TipoGestione.INBOUND;
		TipoGestione tipoGestioneOutbound = TipoGestione.OUTBOUND;

		EnumTipoMessaggio enumTipoMessaggioAP = EntityConverter.getTipoMessaggioVO(tipoMessaggioAP);
		EnumTipoGestione enumTipoGestioneInbound = EntityConverter.getTipoGestioneVO(tipoGestioneInbound);
		EnumTipoGestione enumTipoGestioneOutbound = EntityConverter.getTipoGestioneVO(tipoGestioneOutbound);

		// Asincrono
		// Richieste ricevute = AP + INBOUND + NonOTF
		reportAP.setRichiesteRicevute(this.getNumeroRichiesteRicevute(datiQuadratura,enumTipoMessaggioAP,enumTipoGestioneInbound,false));
		// Richieste Acquisite = AP + nonOTF + Consegnato = true
		reportAP.setRichiesteAcquisite(this.getNumeroRichiesteConsegnate(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound,false));

		// Richieste non consegnate  = AP + nonOTF + Consegnato = false
		reportAP.setRichiesteNonConsegnate(this.getNumeroRichiesteNonConsegnate(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound,false));
		
		// esiti non comprendono gli OTF
		reportAP.setEsitiInviati(this.getNumeroEsitiInviati(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneOutbound,false));
		// Richieste da esitare = AP + Consegnato = true + Esitato = true + nonOTF
		reportAP.setRichiesteEsitate(this.getNumeroEsiti(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneOutbound,false));	
		// Richieste da esitare = AP + Consegnato = true + Esitato = false + nonOTF
		reportAP.setRichiesteDaEsitare(this.getNumeroRichiesteDaEsitare(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneOutbound,false));

		// OTF tutte gestioni INBOUND + OTF
		reportAP.setRichiesteRicevuteOTF(this.getNumeroRichiesteRicevute(datiQuadratura,enumTipoMessaggioAP,enumTipoGestioneInbound,true));
		// Richieste Acquisite  = AP + OTF
		reportAP.setRichiesteAcquisiteOTF(this.getNumeroRichiesteConsegnate(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound,true));
		
		// Richieste non consegnate = AP + OTF  
		reportAP.setRichiesteNonConsegnateOTF(this.getNumeroRichiesteNonConsegnate(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound,true));

		List<MessaggioModel> messaggiNonEsitati = this.getMessaggiNonEsitati(datiQuadratura, enumTipoMessaggioAP);
		reportAP.setMessaggiNonEsitati(messaggiNonEsitati);
		
		List<MessaggioModel> messaggiNonConsegnati = this.getMessaggiNonConsegnati(datiQuadratura, enumTipoMessaggioAP);
		reportAP.setMessaggiNonConsegnati(messaggiNonConsegnati);

		// Distribuzioni
		// Richieste Ricevute = AP + Inbound
//		List<Res> distribuzioneRichiesteRicevute = getDistribuizioneNumeroGestioniAP(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound);
//		reportAP.setDistribuzioneRichiesteRicevute(distribuzioneRichiesteRicevute);

		// Richieste Acquisite = AP + consegnato = true
//		List<Res> numeroRichieste = getDistribuizioneRichiesteAcquisite(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound);
//		reportAP.setDistribuzioneRichiesteAcquisite(numeroRichieste );

		// Esiti Inviati = AP + Outbound
//		List<Res> distribuzioneEsitiRicevuti = getDistribuizioneNumeroEsiti(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneOutbound,null);
//		reportAP.setDistribuzioneEsitiInviati(distribuzioneEsitiRicevuti);

		// Esiti consegnati = AP + consegnato = true + esitato = true
//		List<Res> numeroEsiti = getDistribuizioneRichiesteEsitate(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneOutbound);
//		reportAP.setDistribuzioneEsitiConsegnati(numeroEsiti);

		// Tempo medio richieste = AP + Inbound
//		List<Res> tempoMedioGestioneRichieste = getTempoMedioRichiesteAP(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneInbound,null);
//		reportAP.setTempoMedioGestioneRichieste(tempoMedioGestioneRichieste);

		// Tempo medio esiti = AP + outbound
//		List<Res> tempoMedioGestioneEsiti = getTempoMedioEsiti(datiQuadratura, enumTipoMessaggioAP, enumTipoGestioneOutbound,null); 
//		reportAP.setTempoMedioGestioneEsiti(tempoMedioGestioneEsiti);

		List<Err> listaErroriAP = getListaErrori(datiQuadratura, enumTipoMessaggioAP);
		reportAP.setErroriFrequenti(listaErroriAP);


		datiQuadratura.setReportAP(reportAP);
		this.log.debug("Creazione del report AP completata.");


		// Report IP
		this.log.debug("Creazione del report IP in corso...");
		Report reportIP = new Report();
		TipoMessaggio tipoMessaggioIP = TipoMessaggio.INFORMATIVA_PAGAMENTO;
		reportIP.setTipo(tipoMessaggioIP);

		EnumTipoMessaggio enumTipoMessaggioIP = EntityConverter.getTipoMessaggioVO(tipoMessaggioIP);

		// asincrono
		// Notifiche Inviate: IP + OUTBOUND + noOTF
		reportIP.setRichiesteRicevute(this.getNumeroRichiesteRicevute(datiQuadratura,enumTipoMessaggioIP, enumTipoGestioneOutbound,false));
		// Notifiche Acquisite: IP + nonOTF + consegnato = true
		reportIP.setRichiesteAcquisite(this.getNumeroRichiesteConsegnate(datiQuadratura, enumTipoMessaggioIP,  enumTipoGestioneOutbound,false));
		
		// Richieste non consegnate  = IP + nonOTF + Consegnato = false
		reportIP.setRichiesteNonConsegnate(this.getNumeroRichiesteNonConsegnate(datiQuadratura, enumTipoMessaggioIP,  enumTipoGestioneOutbound,false));

		// esiti non comprendono gli OTF
		// Conferme  Ricevute : IP + INBOUND + noOTF
		reportIP.setEsitiInviati(this.getNumeroEsitiInviati(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound,false));
		// Conferme  Acquisite: IP + consegnato = true  + esitato = true + noOTF
		reportIP.setRichiesteEsitate(this.getNumeroEsiti(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound,false));	
		// Conferme  Acquisite: IP + consegnato = true  + esitato = false + noOTF
		reportIP.setRichiesteDaEsitare(getNumeroRichiesteDaEsitare(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound,false));

		// OTF tutte gestioni INBOUND
		// Notifiche Inviate: IP + INBOUND + OTF
		reportIP.setRichiesteRicevuteOTF(this.getNumeroRichiesteRicevute(datiQuadratura,enumTipoMessaggioIP, enumTipoGestioneInbound,true));
		// Notifiche Acquisite: IP +  OTF + consegnato = true
		reportIP.setRichiesteAcquisiteOTF(this.getNumeroRichiesteConsegnate(datiQuadratura, enumTipoMessaggioIP,  enumTipoGestioneInbound,true));
		
		// Richieste non consegnate = IP + OTF  
		reportIP.setRichiesteNonConsegnateOTF(this.getNumeroRichiesteNonConsegnate(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound,true));

		List<MessaggioModel> messaggiNonEsitatiIP = getMessaggiNonEsitati(datiQuadratura, enumTipoMessaggioIP);
		reportIP.setMessaggiNonEsitati(messaggiNonEsitatiIP);
		
		List<MessaggioModel> messaggiNonConsegnatiIP = this.getMessaggiNonConsegnati(datiQuadratura, enumTipoMessaggioIP);
		reportIP.setMessaggiNonConsegnati(messaggiNonConsegnatiIP);

		// Distribuzioni
		// Notifiche Inviate = IP + ( OUTBOUND + nonOTF) OR ( INBOUND + OTF)
//		List<Res> distribuzioneRichiesteRicevuteIP = getDistribuizioneNumeroGestioniIP(datiQuadratura);
//		reportIP.setDistribuzioneRichiesteRicevute(distribuzioneRichiesteRicevuteIP);

		// Notifiche Consegnate = IP + consegnato = true
//		List<Res> numeroRichiesteIP =  getDistribuizioneRichiesteAcquisite(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneOutbound);
//		reportIP.setDistribuzioneRichiesteAcquisite(numeroRichiesteIP );

		// Conferme Ricevute = IP + INBOUND + nonOTF
//		List<Res> distribuzioneEsitiRicevutiIP = getDistribuizioneNumeroEsiti(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound,false);
//		reportIP.setDistribuzioneEsitiInviati(distribuzioneEsitiRicevutiIP);

		// Notifiche Acquisite: IP +  consegnato = true  + esito = true 
//		List<Res> numeroEsitiIP = getDistribuizioneRichiesteEsitate(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound);
//		reportIP.setDistribuzioneEsitiConsegnati(numeroEsitiIP);

		// Tempo medio richieste = IP + ( OUTBOUND + nonOTF) OR ( INBOUND + OTF)
//		List<Res> tempoMedioGestioneRichiesteIP = getTempoMedioRichiesteIP(datiQuadratura);
//		reportIP.setTempoMedioGestioneRichieste(tempoMedioGestioneRichiesteIP);

		// Tempo medio esiti = IP + INBOUND + nonOTF
//		List<Res> tempoMedioGestioneEsitiIP = getTempoMedioEsiti(datiQuadratura, enumTipoMessaggioIP, enumTipoGestioneInbound, false); 
//		reportIP.setTempoMedioGestioneEsiti(tempoMedioGestioneEsitiIP);

		List<Err> listaErroriIP = getListaErrori(datiQuadratura, enumTipoMessaggioIP);
		reportIP.setErroriFrequenti(listaErroriIP);

		datiQuadratura.setReportIP(reportIP);
		this.log.debug("Creazione del report IP completata.");
	}

	private long getNumeroRichiesteRicevute(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Numero Richieste Ricevute | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		String sqlQuery = "select count(*) from Gestioni g join g.messaggi m "
				+ " where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";

		if(sincrono != null){
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";
		}


		Query query = entityManager.createQuery(sqlQuery); 

		query.setParameter("tipoGestione", tipoGestione);
		query.setParameter("soggetto", datiQuadratura.getSoggetto());
		query.setParameter("sil", datiQuadratura.getSil());
		query.setParameter("tipoMessaggio", tipoMessaggio);
		query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
		query.setParameter("dataFine", datiQuadratura.getDataFine());
		if(sincrono != null)
			query.setParameter("opInserimento", "%OTF");

		Long count = (Long)query.getSingleResult();

		log.info("Get Numero Richieste Ricevute | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"], Trovate["+count+"]");
		return count;
	}

	private long getNumeroEsitiInviati(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Numero Esiti Inviati| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		String sqlQuery = "select count(*) from Gestioni g join g.messaggi m "
				+ " where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";

		if(sincrono != null){
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";
		}


		Query query = entityManager.createQuery(sqlQuery); 

		query.setParameter("tipoGestione", tipoGestione);
		query.setParameter("soggetto", datiQuadratura.getSoggetto());
		query.setParameter("sil", datiQuadratura.getSil());
		query.setParameter("tipoMessaggio", tipoMessaggio);
		query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
		query.setParameter("dataFine", datiQuadratura.getDataFine());
		if(sincrono != null)
			query.setParameter("opInserimento", "%OTF");

		Long count = (Long)query.getSingleResult();

		log.info("Get Numero Esiti Inviati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"], Trovate["+count+"]");
		return count;
	}

	private long getNumeroRichiesteConsegnate(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Numero Richieste Consegnate | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y'";

		if(sincrono != null)
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";

		Query query = entityManager
				.createQuery(sqlQuery);

		query.setParameter("soggetto", datiQuadratura.getSoggetto());
		query.setParameter("sil", datiQuadratura.getSil());
		query.setParameter("tipoMessaggio", tipoMessaggio);
		query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
		query.setParameter("dataFine", datiQuadratura.getDataFine());
		if(sincrono != null)
			query.setParameter("opInserimento", "%OTF");

		Long count = (Long)query.getSingleResult();

		log.info("Get Numero Richieste Consegnate | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"], Trovate["+count+"]");
		return count;
	}
	
	private long getNumeroRichiesteNonConsegnate(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Numero Richieste Non Consegnate | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'N'";

		if(sincrono != null)
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";

		Query query = entityManager
				.createQuery(sqlQuery);

		query.setParameter("soggetto", datiQuadratura.getSoggetto());
		query.setParameter("sil", datiQuadratura.getSil());
		query.setParameter("tipoMessaggio", tipoMessaggio);
		query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
		query.setParameter("dataFine", datiQuadratura.getDataFine());
		if(sincrono != null)
			query.setParameter("opInserimento", "%OTF");

		Long count = (Long)query.getSingleResult();

		log.info("Get Numero Richieste Non Consegnate | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"], Trovate["+count+"]");
		return count;
	}

	private long getNumeroRichiesteDaEsitare(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Numero Richieste da Esitare | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y' and m.flEsitoConsegnatoAsChar = 'N'";

		if(sincrono != null)
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";
		
		Query query = entityManager.createQuery(sqlQuery); 

		query.setParameter("soggetto", datiQuadratura.getSoggetto());
		query.setParameter("sil", datiQuadratura.getSil());
		query.setParameter("tipoMessaggio", tipoMessaggio);
		query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
		query.setParameter("dataFine", datiQuadratura.getDataFine()); 
		if(sincrono != null)
			query.setParameter("opInserimento", "%OTF");

		Long count = (Long)query.getSingleResult();

		log.info("Get Numero Richieste da Esitare | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"], Trovati["+count+"]");

		return count;
	}

	private long getNumeroEsiti(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Numero Esiti| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"]");

		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y' and m.flEsitoConsegnatoAsChar = 'Y'";

		if(sincrono != null)
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";
		
		Query query = entityManager.createQuery(sqlQuery); 

		query.setParameter("soggetto", datiQuadratura.getSoggetto());
		query.setParameter("sil", datiQuadratura.getSil());
		query.setParameter("tipoMessaggio", tipoMessaggio);
		query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
		query.setParameter("dataFine", datiQuadratura.getDataFine()); 
		if(sincrono != null)
			query.setParameter("opInserimento", "%OTF");

		Long count = (Long)query.getSingleResult();

		log.info("Get Numero Esiti| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"], Trovati["+count+"]");

		return count;
	}

	@SuppressWarnings("unused")
	private List<Res> getTempoMedioEsiti(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Tempo Medio Esiti| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = getIncremento(datiQuadratura.getDataInizio(), datiQuadratura.getDataFine()); 

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String sqlQuery = "select avg(g.tempoGestione) from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";

		if(sincrono != null)
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";

		while (tempo < totaleTempo){
			dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);

			Query query = entityManager.createQuery(sqlQuery); 

			query.setParameter("tipoGestione", tipoGestione);
			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query.setParameter("dataFine", new Timestamp(dateF.getTime())); 
			if(sincrono != null)
				query.setParameter("opInserimento", "%OTF");

			Double count = (Double) query.getSingleResult();

			if(count == null)
				count = 0D;

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

			Res res = new Res();
			res.setId(dateI.getTime());
			res.setData(dateI);
			res.setValore(count);

			list.add(res);

			tempo += incremento;
			dateI = getDataIncremento(dateI, incremento);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getTempoMedioRichiesteAP(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Tempo Medio Richieste | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = getIncremento(datiQuadratura.getDataInizio(), datiQuadratura.getDataFine()); 

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String sqlQuery = "select avg(g.tempoGestione) from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";
		
		if(sincrono != null)
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";
		
		while (tempo < totaleTempo){
			dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);


			
			Query query = entityManager.createQuery(sqlQuery); 

			query.setParameter("tipoGestione", tipoGestione);
			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query.setParameter("dataFine", new Timestamp(dateF.getTime())); 
			if(sincrono != null)
				query.setParameter("opInserimento", "%OTF");

			Double count = (Double) query.getSingleResult();

			if(count == null)
				count = 0D;

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

			Res res = new Res();
			res.setId(dateI.getTime());
			res.setData(dateI);
			res.setValore(count);

			list.add(res);

			tempo += incremento;
			dateI = getDataIncremento(dateI, incremento);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getTempoMedioRichiesteIP(DatiQuadratura datiQuadratura) {
		EnumTipoMessaggio tipoMessaggio =EnumTipoMessaggio.IP;
		log.info("Get Tempo Medio Richieste IP | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = getIncremento(datiQuadratura.getDataInizio(), datiQuadratura.getDataFine()); 

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		while (tempo < totaleTempo){
			dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);

			String string = "select avg(g.tempoGestione) from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";

			// Query 1 = IP + OUTBOUND + nonOTF

			Query query = entityManager.createQuery(string + " and m.opInserimento not like :opInserimento"); 

			query.setParameter("tipoGestione", EnumTipoGestione.OUTBOUND);
			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query.setParameter("dataFine", new Timestamp(dateF.getTime())); 
			query.setParameter("opInserimento", "%OTF");

			Double count = (Double) query.getSingleResult();

			if(count == null)
				count = 0D;

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato IP/OUT/nonOTF ["+count+"]");

			// Query 1 = IP + INBOUND + OTF
			Query query2 = entityManager.createQuery(string+ " and m.opInserimento like :opInserimento"); 

			query2.setParameter("tipoGestione", EnumTipoGestione.INBOUND);
			query2.setParameter("soggetto", datiQuadratura.getSoggetto());
			query2.setParameter("sil", datiQuadratura.getSil());
			query2.setParameter("tipoMessaggio", tipoMessaggio);
			query2.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query2.setParameter("dataFine", new Timestamp(dateF.getTime())); 
			query2.setParameter("opInserimento", "%OTF");

			Double count2 = (Double) query.getSingleResult();

			if(count2 == null)
				count2 = 0D;

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato  IP/IN/OTF ["+count2+"]");

			Res res = new Res();
			res.setId(dateI.getTime());
			res.setData(dateI);
			res.setValore((count+count2));

			list.add(res);

			tempo += incremento;
			dateI = getDataIncremento(dateI, incremento);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getDistribuizioneNumeroGestioniAP(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione) {
		log.info("Get Distribuzione Numero Gestioni | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = 3600000 * 24;

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();

		try{
			while (tempo < totaleTempo){
				dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);

				Query query = entityManager.createQuery("select count(*) from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine"); 

				query.setParameter("tipoGestione", tipoGestione);
				query.setParameter("soggetto", datiQuadratura.getSoggetto());
				query.setParameter("sil", datiQuadratura.getSil());
				query.setParameter("tipoMessaggio", tipoMessaggio);
				query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
				query.setParameter("dataFine", new Timestamp(dateF.getTime())); 

				Long count = (Long)query.getSingleResult();

				log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

				Res res = new Res();
				res.setId(dateI.getTime());
				res.setData(dateI);
				res.setValore(count);

				list.add(res);

				tempo += incremento;
				dateI = getDataIncremento(dateI, incremento);
			}
		}catch(Exception e){
			this.log.error("Errore durante il calcolo Distribuzione Numero Gestioni | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio
					+"] Tipo Gestione ["+tipoGestione+"]: " + e.getMessage(),e);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getDistribuizioneNumeroGestioniIP(DatiQuadratura datiQuadratura) {
		EnumTipoMessaggio tipoMessaggio = EnumTipoMessaggio.IP;
		log.info("Get Distribuzione Numero Gestioni IP | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = 3600000 * 24;

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String string = "select count(*) from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";

		try{
			while (tempo < totaleTempo){
				dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);

				// Query 1 = IP + OUTBOUND + nonOTF

				Query query = entityManager.createQuery(string + " and m.opInserimento not like :opInserimento"); 

				query.setParameter("tipoGestione", EnumTipoGestione.OUTBOUND);
				query.setParameter("soggetto", datiQuadratura.getSoggetto());
				query.setParameter("sil", datiQuadratura.getSil());
				query.setParameter("tipoMessaggio", tipoMessaggio);
				query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
				query.setParameter("dataFine", new Timestamp(dateF.getTime())); 
				query.setParameter("opInserimento", "%OTF");

				Long count = (Long)query.getSingleResult();

				log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato IP/OUT/nonOTF ["+count+"]");

				// Query 1 = IP + INBOUND + OTF
				Query query2 = entityManager.createQuery(string+ " and m.opInserimento like :opInserimento"); 

				query2.setParameter("tipoGestione", EnumTipoGestione.INBOUND);
				query2.setParameter("soggetto", datiQuadratura.getSoggetto());
				query2.setParameter("sil", datiQuadratura.getSil());
				query2.setParameter("tipoMessaggio", tipoMessaggio);
				query2.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
				query2.setParameter("dataFine", new Timestamp(dateF.getTime())); 
				query2.setParameter("opInserimento", "%OTF");

				Long count2 = (Long)query2.getSingleResult();

				log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato  IP/IN/OTF ["+count2+"]");

				Res res = new Res();
				res.setId(dateI.getTime());
				res.setData(dateI);
				res.setValore((count+count2));

				list.add(res);

				tempo += incremento;
				dateI = getDataIncremento(dateI, incremento);
			}
		}catch(Exception e){
			this.log.error("Errore durante il calcolo Distribuzione Numero Gestioni IP | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]: " + e.getMessage(),e);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getDistribuizioneNumeroEsiti(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione, Boolean sincrono) {
		log.info("Get Distribuzione Numero Esiti | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = 3600000 * 24;

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String sqlQuery = "select count(*) from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine";
		
		if(sincrono != null){
			if(sincrono)
				sqlQuery +=" and m.opInserimento like :opInserimento";
			else
				sqlQuery +=" and m.opInserimento not like :opInserimento";
		}
		
		try{
			while (tempo < totaleTempo){
				dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);

				Query query = entityManager.createQuery(sqlQuery); 

				query.setParameter("tipoGestione", tipoGestione);
				query.setParameter("soggetto", datiQuadratura.getSoggetto());
				query.setParameter("sil", datiQuadratura.getSil());
				query.setParameter("tipoMessaggio", tipoMessaggio);
				query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
				query.setParameter("dataFine", new Timestamp(dateF.getTime())); 
				if(sincrono != null)
					query.setParameter("opInserimento", "%OTF");

				Long count = (Long)query.getSingleResult();

				log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

				Res res = new Res();
				res.setId(dateI.getTime());
				res.setData(dateI);
				res.setValore(count);

				list.add(res);

				tempo += incremento;
				dateI = getDataIncremento(dateI, incremento);
			}
		}catch(Exception e){
			this.log.error("Errore durante il calcolo Distribuzione Numero Esiti | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"] Sincrono ["+sincrono+"]: " + e.getMessage(),e);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getDistribuizioneRichiesteAcquisite(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione) {
		log.info("Get Distribuzione Numero Richieste Acquisite| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = 3600000 * 24;

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y'";
		while (tempo < totaleTempo){
			dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);


			Query query = entityManager.createQuery(sqlQuery); 

			//	query.setParameter("tipoGestione", tipoGestione);
			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query.setParameter("dataFine", new Timestamp(dateF.getTime())); 

			Long count = (Long)query.getSingleResult();

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

			Res res = new Res();
			res.setId(dateI.getTime());
			res.setData(dateI);
			res.setValore(count);

			list.add(res);

			tempo += incremento;
			dateI = getDataIncremento(dateI, incremento);
		}
		return list;
	}

	@SuppressWarnings("unused")
	private List<Res> getDistribuizioneRichiesteEsitate(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione) {
		log.info("Get Distribuzione Numero Richieste Esitate| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = 3600000 * 24;

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y' and m.flEsitoConsegnatoAsChar = 'Y'";
		while (tempo < totaleTempo){
			dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);

			Query query = entityManager.createQuery(sqlQuery); 

			//	query.setParameter("tipoGestione", tipoGestione);
			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query.setParameter("dataFine", new Timestamp(dateF.getTime())); 

			Long count = (Long)query.getSingleResult();

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

			Res res = new Res();
			res.setId(dateI.getTime());
			res.setData(dateI);
			res.setValore(count);

			list.add(res);

			tempo += incremento;
			dateI = getDataIncremento(dateI, incremento);
		}
		return list;
	}


	@SuppressWarnings("unused")
	private List<Res> getDistribuizioneRichiesteDaEsitare(DatiQuadratura datiQuadratura, EnumTipoMessaggio tipoMessaggio, EnumTipoGestione tipoGestione) {
		log.info("Get Distribuzione Numero Richieste Da Esitare| Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] Tipo Gestione ["+tipoGestione+"]");

		long totaleTempo = datiQuadratura.getDataFine().getTime() - datiQuadratura.getDataInizio().getTime();
		long tempo = 0;
		long incremento = 3600000 * 24;

		Calendar dI = truncate(datiQuadratura.getDataInizio(),incremento);
		Date dateI = dI.getTime();
		Date dateF = null;
		List<Res> list = new ArrayList<Res>();
		String sqlQuery = "select count(*) from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y' and m.flEsitoConsegnatoAsChar = 'N'";
		while (tempo < totaleTempo){
			dateF = new Date(getDataIncremento(dateI, incremento).getTime() -1);
			
			Query query = entityManager.createQuery(sqlQuery); 

			//	query.setParameter("tipoGestione", tipoGestione);
			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", new Timestamp(dateI.getTime())); 
			query.setParameter("dataFine", new Timestamp(dateF.getTime())); 

			Long count = (Long)query.getSingleResult();

			log.debug("Intervallo ["+dateI+"] / ["+dateF+"] Risultato ["+count+"]");

			Res res = new Res();
			res.setId(dateI.getTime());
			res.setData(dateI);
			res.setValore(count);

			list.add(res);

			tempo += incremento;
			dateI = getDataIncremento(dateI, incremento);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<MessaggioModel> getMessaggiNonEsitati(DatiQuadratura datiQuadratura,EnumTipoMessaggio tipoMessaggio) throws Exception{
		List<MessaggioModel> list = new ArrayList<MessaggioModel>();
		log.info("Get Lista Messaggi Non Esitati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]");

		try{
			Query query = entityManager.createQuery("select m from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'Y' and m.flEsitoConsegnatoAsChar = 'N' order by m.dataCreazione desc"); 

			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
			query.setParameter("dataFine", datiQuadratura.getDataFine());

			List<Messaggi> resultList = query.getResultList();  

			if(resultList != null && resultList.size() >0){
				for (Messaggi vo : resultList) {
					list.add(EntityConverter.getMessaggioDTO(vo));
				}
			}

		}catch(Exception e){
			log.error("Si e' verificato un errore durante Get Lista Messaggi Non Esitati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]: "+e.getMessage(),e);

			throw e;
		}
		log.info("Get Lista Messaggi Non Esitati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] completata, trovati ["+list.size()+"]"); 
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<MessaggioModel> getMessaggiNonConsegnati(DatiQuadratura datiQuadratura,EnumTipoMessaggio tipoMessaggio) throws Exception{
		List<MessaggioModel> list = new ArrayList<MessaggioModel>();
		log.info("Get Lista Messaggi Non Consegnati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]");

		try{
			Query query = entityManager.createQuery("select m from Messaggi m where m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and m.dataCreazione >= :dataInizio and m.dataCreazione <= :dataFine and m.flRichiestaConsegnataAsChar = 'N' order by m.dataCreazione desc"); 

			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
			query.setParameter("dataFine", datiQuadratura.getDataFine());

			List<Messaggi> resultList = query.getResultList();  

			if(resultList != null && resultList.size() >0){
				for (Messaggi vo : resultList) {
					list.add(EntityConverter.getMessaggioDTO(vo));
				}
			}

		}catch(Exception e){
			log.error("Si e' verificato un errore durante Get Lista Messaggi Non Consegnati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]: "+e.getMessage(),e);

			throw e;
		}
		log.info("Get Lista Messaggi Non Consegnati | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] completata, trovati ["+list.size()+"]"); 
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<Err> getListaErrori(DatiQuadratura datiQuadratura,EnumTipoMessaggio tipoMessaggio) throws Exception{
		List<Err> list = new ArrayList<Err>();

		log.info("Get Lista Errori piu' frequenti | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]");

		try{
			Query query = entityManager.createQuery("select g.codErrore, g.componenteResponsabile, count(g) from Gestioni g join g.messaggi m where g.codErrore IS NOT NULL and g.descrErrore IS NOT NULL and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine group by g.codErrore, g.componenteResponsabile order by count(g) desc"); 

			query.setParameter("soggetto", datiQuadratura.getSoggetto());
			query.setParameter("sil", datiQuadratura.getSil());
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", datiQuadratura.getDataInizio()); 
			query.setParameter("dataFine", datiQuadratura.getDataFine());

			List<Object[]> resultList = query.getResultList();

			if(resultList != null && resultList.size() >0){
				for (Object[] vo : resultList) {
					Err err = new Err();
					ErroreComponenteModel errComp = new ErroreComponenteModel();
					errComp.setCodErrore((String) vo[0]);
				//	errComp.setDescrErrore((String) vo[1]);
					errComp.setCodiceComponente((String) vo[1]);

					err.setOccorenze((Long) vo[2]);
					err.setTipoErrore(errComp);
					list.add(err);
				}
			}


		}catch(Exception e){
			log.error("Si e' verificato un errore durante Get Lista Errori piu' frequenti | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"]: "+e.getMessage(),e);
			throw e;
		}

		log.info("Get Lista Errori piu' frequenti | Parametri -> "+datiQuadratura.getElencoParametriAsString() + " Tipo Messaggio ["+tipoMessaggio+"] completata, trovati ["+list.size()+"]");

		return list;
	}

	private static Calendar truncDateGiorno(Date date){
		Calendar cTmp = Calendar.getInstance();
		cTmp.setTime(date);
		cTmp.set(Calendar.HOUR_OF_DAY, 0);
		cTmp.set(Calendar.MINUTE, 0);
		cTmp.set(Calendar.SECOND, 0);
		cTmp.set(Calendar.MILLISECOND, 0);
		return cTmp;
	}

	private static Calendar truncDateOra(Date date){
		Calendar cTmp = Calendar.getInstance();
		cTmp.setTime(date);
		cTmp.set(Calendar.MINUTE, 0);
		cTmp.set(Calendar.SECOND, 0);
		cTmp.set(Calendar.MILLISECOND, 0);
		return cTmp;
	}

	private static Date incrementDate(Date date,int field){
		return operation(date, field, +1);
	}

	//	private Date decrementDate(Date date,int field){
	//		return operation(date, field, -1);
	//	}

	private static Date operation(Date date,int field, int value){
		Calendar cTmp = Calendar.getInstance();
		cTmp.setTime(date);
		//	int field = Calendar.DAY_OF_YEAR;
		cTmp.add(field, value); 
		return cTmp.getTime();
	}


	@SuppressWarnings("unchecked")
	@Override
	public DatiRiepilogo getRiepilogo(String soggetto, String sil, long tempoInattivitaAP, int sogliaFallimentiAPE,
			int sogliaFallimentiIP) throws Exception{
		DatiRiepilogo dati = new DatiRiepilogo();
		dati.setSil(sil);
		dati.setSoggetto(soggetto);

		log.info("Creo dati di riepilogo per Soggetto ["+soggetto+"] Sil ["+sil+"] TempoInattivitaAP ["+tempoInattivitaAP+"] SogliaFallimentiAPE ["+sogliaFallimentiAPE+"] SogliaFallimentiIP ["+sogliaFallimentiIP+"]" );
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dataFine = new Date();
			Date dataInizio = new Date(dataFine.getTime() - tempoInattivitaAP); 

			Query query = entityManager.createQuery("select count(*) from Gestioni g join g.messaggi m where g.codErrore IS NULL and g.descrErrore IS NULL and g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio and g.dataInizioGestione >= :dataInizio and g.dataInizioGestione <= :dataFine"); 
			EnumTipoMessaggio tipoMessaggio = EnumTipoMessaggio.AP;
			EnumTipoGestione tipoGestione = EnumTipoGestione.INBOUND;
			query.setParameter("tipoGestione", tipoGestione);
			query.setParameter("soggetto", soggetto);
			query.setParameter("sil", sil);
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setParameter("dataInizio", dataInizio); 
			query.setParameter("dataFine", dataFine); 

			Long count = (Long)query.getSingleResult();

			// Se c'e' almeno una gestione Ok allora lo stato e' ok
			if(count > 0){
				dati.setMessaggioAP("Servizio AllineamentoPendenze e' OK");
				dati.setStatoServizioAP(StatoServizio.OK);
			} else {

				dati.setMessaggioAP("Servizio AllineamentoPendenze non ha gestioni andate a buon fine dal "+sdf.format(dataInizio) + " al "+sdf.format(dataFine)+".");
				dati.setStatoServizioAP(StatoServizio.FAIL);
			}

			tipoGestione = EnumTipoGestione.OUTBOUND;
			query = entityManager.createQuery("select g from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio order by g.dataInizioGestione desc"); 

			query.setParameter("tipoGestione", tipoGestione );
			query.setParameter("soggetto", soggetto);
			query.setParameter("sil", sil);
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setMaxResults(sogliaFallimentiAPE);

			List<Gestioni> list = query.getResultList();

			boolean found = false;
			found = (list.size() != sogliaFallimentiAPE);   
			for (Gestioni gestione : list) {
				// appena trovo un caso ok va bene
				if(gestione.getCodErrore() == null && gestione.getDescrErrore() == null){
					found = true;
					break;
				}
			}

			if(found){
				dati.setMessaggioAPE("Servizio AllineamentoPendenzeEsito e' OK");
				dati.setStatoServizioAPE(StatoServizio.OK);
			} else {

				dati.setMessaggioAPE("Servizio AllineamentoPendenzeEsito e' KO: superata la soglia di "+sogliaFallimentiAPE+" tentativi di spedizione in errore.");
				dati.setStatoServizioAPE(StatoServizio.FAIL);
			}


			tipoMessaggio = EnumTipoMessaggio.IP;

			query = entityManager.createQuery("select g from Gestioni g join g.messaggi m where g.tipoGestione = :tipoGestione and m.soggetto = :soggetto and m.sil = :sil and m.tipoMessaggio = :tipoMessaggio order by g.dataInizioGestione desc"); 

			query.setParameter("tipoGestione", tipoGestione );
			query.setParameter("soggetto", soggetto);
			query.setParameter("sil", sil);
			query.setParameter("tipoMessaggio", tipoMessaggio);
			query.setMaxResults(sogliaFallimentiIP);

			list = query.getResultList();
			found = false;
			found = (list.size() != sogliaFallimentiIP); 
			for (Gestioni gestione : list) {
				// appena trovo un caso ok va bene
				if(gestione.getCodErrore() == null && gestione.getDescrErrore() == null){
					found = true;
					break;
				}
			}

			if(found){
				dati.setMessaggioIP("Servizio InformativaPagamento e' OK");
				dati.setStatoServizioIP(StatoServizio.OK);
			} else {

				dati.setMessaggioIP("Servizio InformativaPagamento e' KO: superata la soglia di "+sogliaFallimentiIP+" tentativi di spedizione in errore.");
				dati.setStatoServizioIP(StatoServizio.FAIL);
			}

		}catch (Exception e){
			log.error("Errore durante la creazione dati di riepilogo per Soggetto ["+soggetto+"] Sil ["+sil+"] TempoInattivitaAP ["+tempoInattivitaAP+"] SogliaFallimentiAPE ["+sogliaFallimentiAPE+"] SogliaFallimentiIP ["+sogliaFallimentiIP+"]: "+ e.getMessage(),e );
			throw e;
		}
		return dati;
	}

	public static long getIncremento(Date datainizio, Date datafine){
		long ret = 0;

		long totaleTempo = datafine.getTime() - datainizio.getTime();

		// intervallo orario
		if(totaleTempo <= 86400001)
			ret = 3600000;
		else  // intervallo giornaliero
			ret = 86400000;

		return ret;

	}

	public static Date getDataIncremento(Date data, long incremento){
		if(incremento > 3600000)
			return incrementDate(data, Calendar.DAY_OF_YEAR);
		else 
			return incrementDate(data, Calendar.HOUR_OF_DAY);
	}

	public static Calendar truncate(Date data , long incremento){
		if(incremento > 3600000)
			return truncDateGiorno(data);
		else
			return truncDateOra(data);

	}
}