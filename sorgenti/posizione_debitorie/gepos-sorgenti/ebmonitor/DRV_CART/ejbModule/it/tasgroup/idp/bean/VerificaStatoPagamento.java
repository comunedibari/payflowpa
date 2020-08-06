/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMap;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.gateway.CfgGatewayPagamento;
import it.tasgroup.idp.notifiche.PagamentoModelTyped;
import it.tasgroup.idp.notifiche.PagamentoModelTypedBuilder;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel.EnumDescrizioneStato;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel.EnumStatoPagamentoDettagliato;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.pagamenti.CondizioniDocumento;
import it.tasgroup.iris2.pagamenti.Pagamenti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@Interceptors(MonitoringInterceptor.class)
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
public class VerificaStatoPagamento implements IVerificaStatoPagamento  {

	@PersistenceContext(unitName = ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;

	private final Log logger = LogFactory.getLog(this.getClass());	

	/**
	 * 
	 * @param em
	 * @param idEnte
	 * @param data
	 * @return
	 */
	private TributiEnti getTributoEnte(EntityManager em, String idEnte, VerificaStatoPagamentoInput data) throws VerificaPagamentoModelException {
		Query query = em.createQuery("SELECT tributiEnti FROM TributiEnti tributiEnti " +
				" WHERE tributiEnti.id.idEnte = :idEnte " +
				"   AND tributiEnti.id.cdTrbEnte = :cdTrbEnte " +
				"   AND tributiEnti.idSystem = :sil " +
				"   AND tributiEnti.stato = :stato ");
		query.setParameter("idEnte", idEnte);
		query.setParameter("cdTrbEnte", data.getTipoPendenza());
		query.setParameter("sil", data.getSil());
		query.setParameter("stato", "A");

		List<TributiEnti> tributi = query.getResultList();
		if (tributi == null || tributi.isEmpty()) {
			throw new VerificaPagamentoModelException("errors.pendenza.decode.tributoid");
		} else if(tributi.size() > 1) {
			throw new VerificaPagamentoModelException("db.exceptions");
		}
		return tributi.get(0);
	}



	/**
	 * 
	 * @param em
	 * @param cdEnte
	 * @return
	 */
	private Enti getEnte(EntityManager em, String cdEnte) throws VerificaPagamentoModelException {
		Query query = em.createQuery("SELECT e FROM Enti e WHERE e.cdEnte = :cdEnte AND e.stato = :stato ");
		query.setParameter("cdEnte", cdEnte);
		query.setParameter("stato", "A");
		
		List<Enti> enti = query.getResultList();
		if (enti == null || enti.isEmpty()) {
			throw new VerificaPagamentoModelException("errors.pendenza.decode.idente");
		} else if(enti.size() > 1) {
			throw new VerificaPagamentoModelException("db.exceptions");
		}
		return enti.get(0);
	}
	
	
	/**
	 * 
	 * @param em
	 * @param idPendenza
	 * @param idCondizione
	 * @return
	 */
	private List<Pagamenti> getPagamentoFromIdPendenza(EntityManager em, String idPendenza, String idCondizione) {
		Query query = em.createQuery("SELECT pagam FROM Pagamenti pagam " +
				" WHERE pagam.idPendenza = :idPendenza " +
				"   AND pagam.idCondizione = :idCondizione");
		query.setParameter("idPendenza", idPendenza);
		query.setParameter("idCondizione", idCondizione);
		
		return (List<Pagamenti>) query.getResultList();
	}
	
	/**
	 * 
	 * @param em
	 * @param idEnte
	 * @param data
	 * @return
	 */
	private List<CondizioniPagamento> getCondizionePagamFromIdPagamento(EntityManager em, String idEnte, VerificaStatoPagamentoInput data,TributiEnti tributo ) {
		
		 String idPagamento =  data.getIdPagamento();
		 if (!tributo.getFlNdpIuvGenerato().equals("0")) {
	    	    Query q1=em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIdPagamento");
			    q1.setParameter("idPagamento", data.getIdPagamento());
			    q1.setParameter("idEnte", tributo.getId().getIdEnte());
			    List<IUVPosizEnteMap> lIuvMapList = ( List<IUVPosizEnteMap>) q1.getResultList();
			    if (lIuvMapList.size()>0) {		    	
			    	idPagamento=lIuvMapList.get(0).getId().getIuv();
			    } else {
			    	// 
			    }
	     
	     } 		
		
		// inserire qui la select su iuvmap
		Query query = em.createQuery("SELECT cond FROM CondizioniPagamento cond " +
				" WHERE cond.idPagamento = :idPagamento " +
				"   AND cond.idEnte = :idEnte " +
				"   AND cond.cdTrbEnte = :cdTrbEnte order by cond.tsInserimento desc");
		
		query.setParameter("idPagamento",idPagamento);
		query.setParameter("idEnte", idEnte);
		query.setParameter("cdTrbEnte", data.getTipoPendenza());
		
		logger.info("Searching condition, idPagamento " +  idPagamento + " idEnte " + idEnte + " tributo " + data.getTipoPendenza() + " -" );

		List<CondizioniPagamento> condizioni = (List<CondizioniPagamento>) query.getResultList(); 
		
		logger.info("Found " + condizioni.size() + " condizioni " );
		
		return condizioni;
	}
	

	private boolean isDocumentoPagamentoEmessoPresent(EntityManager em, String idCondizione) {
		Query query = em.createQuery("SELECT count(condDoc.id) FROM CondizioniDocumento condDoc " +
				" WHERE condDoc.idCondizione = :idCondizione " +
				"   AND condDoc.documentiPagamento.stato = :statoDocumento ");
		
		query.setParameter("idCondizione", idCondizione);
		query.setParameter("statoDocumento", "E");

		List<Long> l= (List<Long>)query.getResultList();
		Long num =l.get(0);
		return  num != null && num > 0;
	}

	private String tipoDocumentoEmesso (EntityManager em, String idCondizione) {
		Query query = em.createQuery(" from CondizioniDocumento condDoc " +
				" WHERE condDoc.idCondizione = :idCondizione " +
				"   AND condDoc.documentiPagamento.stato = :statoDocumento order by tsInserimento desc");
		
		query.setParameter("idCondizione", idCondizione);
		query.setParameter("statoDocumento", "E");
		
		List<CondizioniDocumento> cds = (List<CondizioniDocumento>)query.getResultList();
		if (cds.size()>0) {
			CondizioniDocumento cd = cds.get(0);
			Long idGateway = cd.getDocumentiPagamento().getIdCfgGatewayPagamento();
			CfgGatewayPagamento gateway = em.find(CfgGatewayPagamento.class, idGateway);
			return gateway.getCfgDocumentoPagamento().getDescrizione();
		} else {
			return null;
		}
		
		
	}
	
	private String descrizioneModalitaPagamento(EntityManager em, Long idGateway) {
		CfgGatewayPagamento gateway = em.find(CfgGatewayPagamento.class, idGateway);
		return gateway.getCfgModalitaPagamento().getDescrizione();
	}
	
	/**
	 * 
	 * @param idDistinta
	 * @return
	 */
	private boolean aggiornaStatoDistinta(String idDistinta) {
		String enableUpd = IrisProperties.getProperty("verificaStatoPagamento.updatePaymentStatus.enable", "false");
		logger.info("aggiornaStatoDistinta enabled: " + enableUpd);

		if("true".equals(enableUpd)) {
			try {
				String urlBase = IrisProperties.getProperty("verificaStatoPagamento.updatePaymentStatus.servlet.url");
				String urlUpdatePaymentStatus = urlBase + "?idDistinta=" + idDistinta;
				String updatePaymentStatusResponse = sendGet(urlUpdatePaymentStatus);
				return "OK".equals(updatePaymentStatusResponse);
			} catch (IOException e) {
				logger.error("Errore IO durante la chiamata all'UpdatePaymentStatusServlet", e);
				return false;
			} catch (Exception e) {
				logger.error("Errore durante la chiamata all'UpdatePaymentStatusServlet", e);
				return false;
			}
		}
		logger.debug("aggiornaStatoDistinta disabilitato da properties");
		return false;
	}
	
	/**
	 * 
	 * @param urlToCall
	 * @return
	 * @throws Exception
	 */
	private String sendGet(String urlToCall) throws Exception {
		logger.debug("Sending 'GET' request to URL : " + urlToCall);
		URL urlObj = new URL(urlToCall);
		int responseCode =0;
		BufferedReader in = null;
		String tmpUrl = new String(urlToCall.toUpperCase());
		if (tmpUrl.startsWith("HTTPS")){
			HttpsURLConnection con = (HttpsURLConnection)urlObj.openConnection();
			responseCode= con.getResponseCode();
			logger.debug("Response Code : " + responseCode);
			logger.info("Sent 'GET' in HTTPS request to URL : " + urlToCall + " and received " + responseCode);
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}
		else{
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			responseCode= con.getResponseCode();
			logger.debug("Response Code : " + responseCode);
			logger.info("Sent 'GET' in HTTP request to URL : " + urlToCall + " and received " + responseCode);
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}
		
		
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		logger.debug("Response:");
		logger.debug(response.toString());
		return response.toString();
	}
	
	
	@Override
	public VerificaPagamentoModel verificaPagamentoModel(VerificaStatoPagamentoInput input, boolean includeDetails) throws VerificaPagamentoModelException {
		
		VerificaPagamentoModel  result = new VerificaPagamentoModel();
		result.setIdPagamento(input.getIdPagamento());
		result.setTipoPendenza(input.getTipoPendenza());
		
		// Recupero l'id ente a partire da CD_ENTE
		Enti ente = getEnte(manager, input.getCdEnte());
		
		// Verifico che eista l'anagrafica del tributo per l'ente
		TributiEnti tributo = getTributoEnte(manager, ente.getIdEnte(), input);
		
		// Recupero l'ID condizione
		List<CondizioniPagamento> condList = getCondizionePagamFromIdPagamento(manager, ente.getIdEnte(), input,tributo);		
	
		if(condList == null || condList.isEmpty()) {
			logger.info(this.getClass().getSimpleName() + " NON TROVO LA CONDIZIONE");
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PRESENTE);
			result.setDescrizioneStato(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PRESENTE.toString());
			return result;
			
		} 
			
		// recupero la condizione pagamento dall'Array.
		CondizioniPagamento cond = null;
		for(CondizioniPagamento item : condList) {
			if("V".equals(item.getStRiga()) && item.getTsAnnullamento() == null) {
				cond = item;
				break;
			}
		}
		if (cond == null) {
			// cerco la condizione Hidden con tsInserimento più recente
			// N.B. le cond sono già ordinate per tsInserimento desc. la prima hidden è quella giusta
			for(CondizioniPagamento item : condList) {
				if("H".equals(item.getStRiga())) {
					cond = item;
					break;
				}
			}
		}
		if (cond == null) {
			logger.info(this.getClass().getSimpleName() + " NON TROVO CONDIZIONI con statoriga V o H");
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PRESENTE);
			result.setDescrizioneStato(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PRESENTE.toString());
			return result;
		} 

		
		// Arrivati in questo punto abbiamo individuato la condizione di pagamento da analizzare
			
		List<Pagamenti> pagaList = getPagamentoFromIdPendenza(manager, cond.getPendenze().getIdPendenza(), cond.getIdCondizione());
		if (pagaList == null || pagaList.isEmpty()) {
		
			if ("P".equals(cond.getStPagamento())) {
				logger.info(this.getClass().getSimpleName() + " TROVATO CONDIZIONE PAGATA FUORI DA IDP = " + cond.getIdCondizione());
				result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA);
				result.setDescrizioneStato(EnumDescrizioneStato.PAG_ESEGUITO_OTH.toString());
				return result;
			}
			
			if ("R".equals(cond.getStPagamento())) {
				logger.info(this.getClass().getSimpleName() + " TROVATO CONDIZIONE RIMBORSATA FUORI DA IDP = " + cond.getIdCondizione());
				result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PAGABILE);
				result.setDescrizioneStato(EnumDescrizioneStato.PAG_RIMBORSATO.toString());
				return result;
			}

			
			// Verififica della presenza di un documento emesso associato
			if(isDocumentoPagamentoEmessoPresent(manager, cond.getIdCondizione())) {
				logger.info(this.getClass().getSimpleName() + " TROVATO DOCUMENTO ASSOCIATO ALL'IDCONDIZIONE = " + cond.getIdCondizione());
				result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_CON_DOC_EMESSO);
				result.setDescrizioneStato(tipoDocumentoEmesso(manager,cond.getIdCondizione()));
				return result;				
			} 
		
			// Verififica della pagabilità
			if (!PosizioneDebitoriaHelper.isCondizionePagabile(cond, null)) {
				logger.info(this.getClass().getSimpleName() + " NESSUN DOCUMENTO ASSOCIATO ALL'IDCONDIZIONE - POSIZIONE non PAGABILE= " + cond.getIdCondizione());
				result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PAGABILE);
				result.setDescrizioneStato(EnumDescrizioneStato.NON_PAGABILE_TERMINI.toString());
				return result;
			}
			
			logger.info(this.getClass().getSimpleName() + " NESSUN PAGAMENTO ASSOCIATO ALL'IDCONDIZIONE - POSIZIONE PAGABILE= " + cond.getIdCondizione());
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PAGATA);
			result.setDescrizioneStato(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PAGATA.toString());
			return result;			
			

		}
		
		
		Iterator<Pagamenti> listPaga = pagaList.iterator();
		
		boolean eseguito = false;
		boolean eseguitoSbf = false;
		boolean inCorso = false;
		boolean nonEseguito = false;
		boolean nonPagabile = false;
		
		String idDistintaInCorso = null;
		
		Pagamenti pagamentoEseguito = null;
		Pagamenti pagamentoEseguitoSbf = null;
		Pagamenti pagamentoInCorso=null;
		Pagamenti pagamentoNonEseguito=null;
		
		while (listPaga.hasNext()) {
			Pagamenti paga = (Pagamenti) listPaga.next();
			logger.info(this.getClass().getSimpleName() + "- PAGAMENTO : ST_PAGAMENTO = " + paga.getStPagamento());
			
			if ("ES".equals(paga.getStPagamento().trim())) {
				eseguito = true;
				pagamentoEseguito = paga;
				break;
			}  else if ("EF".equals(paga.getStPagamento().trim())) {
				eseguitoSbf = true;
				idDistintaInCorso = "" + paga.getDistintePagamento().getId();	
				if (pagamentoEseguitoSbf==null) {
					pagamentoEseguitoSbf = paga;
				}	
			} else if ("IC".equals(paga.getStPagamento().trim())) {
				inCorso = true;
				idDistintaInCorso = "" + paga.getDistintePagamento().getId();
				if (pagamentoInCorso==null) {
					pagamentoInCorso = paga;
				}	
			} else {
				if (PosizioneDebitoriaHelper.isCondizionePagabile(cond, paga.getStPagamento())) {
					nonEseguito = true;
					if (pagamentoNonEseguito==null) {
						pagamentoNonEseguito = paga;
					}	
				} else {
					nonPagabile = true;
				}
			}					
		}
							
		//setto il valore finale
		if (eseguito) {
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA);
			if ("R".equals(cond.getStPagamento())) 
				result.setDescrizioneStato(EnumDescrizioneStato.PAG_RIMBORSATO.toString());
			else
				result.setDescrizioneStato(EnumDescrizioneStato.PAG_ESEGUITO_IDP.toString());
			if (includeDetails) {
				PagamentoModelTyped pm = PagamentoModelTypedBuilder.buildPagamentoModel(pagamentoEseguito, manager, null, "ESEGUITO"); 
				result.setPagamento(pm);
			}	
			return result;
		} else if (inCorso) {
			
			//BUSINESS LOGIC DI AGGIORNAMENTO STATO DISTINTA
			if (aggiornaStatoDistinta(idDistintaInCorso)) {
				result.setRefreshData(true);
				return result;
			}
			
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_CON_PAG_IN_CORSO);
			result.setDescrizioneStato(descrizioneModalitaPagamento(manager,pagamentoInCorso.getDistintePagamento().getIdCfgGatewayPagamento()));
			return result;
		} else if (eseguitoSbf) {
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA_SBF);
			result.setDescrizioneStato(descrizioneModalitaPagamento(manager,pagamentoEseguitoSbf.getDistintePagamento().getIdCfgGatewayPagamento()));
			return result;
		} else if (nonPagabile) {
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PAGABILE);
			if ("R".equals(cond.getStPagamento())) 
				result.setDescrizioneStato(EnumDescrizioneStato.PAG_RIMBORSATO.toString());
			else
				result.setDescrizioneStato(EnumDescrizioneStato.NON_PAGABILE_RATE.toString());
			return result;
		} else if (nonEseguito) {
			result.setStatoPagamento(EnumStatoPagamentoDettagliato.POSIZIONE_NON_PAGATA);
			String statoPagamento = pagamentoNonEseguito.getStPagamento();
			
			EnumDescrizioneStato ds = EnumDescrizioneStato.PAG_NON_ESEGUITO;
			
			if ("AO".equals(statoPagamento)) ds=EnumDescrizioneStato.PAG_ANNULLATO_OPER;
			if ("AN".equals(statoPagamento)) ds=EnumDescrizioneStato.PAG_ANNULLATO;
			if ("IE".equals(statoPagamento)) ds=EnumDescrizioneStato.PAG_IN_ERRORE;
			if ("NE".equals(statoPagamento)) ds=EnumDescrizioneStato.PAG_NON_ESEGUITO;
			if ("ST".equals(statoPagamento)) ds=EnumDescrizioneStato.PAG_STORNATO;
				
			result.setDescrizioneStato(ds.toString());
			
			return result;
		} 					
		
		return result;
	}
}
