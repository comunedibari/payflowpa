package it.tasgroup.idp.notifiche;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.nch.eb.common.utils.map.DataTypesUtils;
import it.tasgroup.gde.GiornaleEventiDocumentiNDP;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMap;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.gateway.CfgCanalePagamento;
import it.tasgroup.idp.gateway.CfgFornitoreGateway;
import it.tasgroup.idp.gateway.CfgGatewayPagamento;
import it.tasgroup.idp.gateway.CfgModalitaPagamento;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.iris2.pagamenti.DistintePagamento;
import it.tasgroup.iris2.pagamenti.Pagamenti;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.ContentType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.MIMETypeCode;

public class PagamentoModelTypedBuilder {

	/**
	 * 
	 * @param pagamento
	 * @param em
	 * @param idNotifica
	 * @param tipoNotifica
	 * @return
	 */
	public static PagamentoModelTyped buildPagamentoModel(Pagamenti pagamento, EntityManager em, String idNotifica, String tipoNotifica) {
		
		//query I, recupero pendenza
		Pendenze pend = em.find(Pendenze.class, pagamento.getIdPendenza());
		//query II, recupero condizione
		CondizioniPagamento cond = em.find(CondizioniPagamento.class, pagamento.getIdCondizione());
		//query III, recupero distinta
		DistintePagamento dist = pagamento.getDistintePagamento();
		
		String idPagante = "";
		//query IV, recupero destinatari
		Query queryDest =  em.createNamedQuery("DestinatariByIdPend");
		queryDest.setParameter("idPendenza", pagamento.getIdPendenza());
		List<DestinatariPendenze> listDestinat = queryDest.getResultList();
		
		for (Iterator iterator = listDestinat.iterator(); iterator.hasNext();) {
			DestinatariPendenze destinatariPendenze = (DestinatariPendenze) iterator
					.next();
			//recupero ultimo CF destinatario
			idPagante=destinatariPendenze.getCoDestinatario();
		}
		
		PagamentoModelTyped p = new PagamentoModelTyped();
		
		p.setIdNotifica(idNotifica);
		p.setDeCausale(pend.getDeCausale());
		
		TributiEntiPK pk = new TributiEntiPK();
		pk.setIdEnte(cond.getIdEnte());
		pk.setCdTrbEnte(cond.getCdTrbEnte());
		//query V, recupero tributo
		TributiEnti tribEnti = em.find(TributiEnti.class, pk);
		
		//--- BEGIN: gestione del id_pagamento nel caso in cui lo iuv sia generato da piattaforma
		//query VI, recupero IUV se necessario
		manageExternalIUV(em, cond, p, tribEnti);
		//-- END: gestione del id_pagamento nel caso in cui lo iuv sia generato da piattaforma		
		
		p.setDataOraPagamento(pagamento.getTsDecorrenza());
		p.setDataScadenzaPagamento(cond.getDtScadenza());
		p.setImporto(pagamento.getImPagato());
		
		p.setEsito(tipoNotifica);
		p.setIdPagante(idPagante);	
		
		Long idCfgGateway = dist.getIdCfgGatewayPagamento();
		//query VII, recupero cfg gateway
		CfgGatewayPagamento gateway = em.find(CfgGatewayPagamento.class, idCfgGateway);
		
		CfgModalitaPagamento modalita = gateway.getCfgModalitaPagamento();
		p.setMezzoPagamento(modalita.getDescrizione());
	
		CfgCanalePagamento canale = gateway.getCfgCanalePagamento();		
		p.setTipoCanalePagamento(canale.getDescrizione());
		
		CfgFornitoreGateway fornitore = gateway.getCfgFornitoreGateway();
		
		p.setIdTransazione(!"0".equals(dist.getCodTransazionePSP()) ? dist.getCodTransazionePSP(): dist.getCodTransazione());
		//#2653 aggiungo cod.pagamento (nel campo sbagliato, ma lo aggiungo)
		p.setCodiceAutorizzazione(dist.getCodPagamento());
		p.setDataOraAutorizzazione(dist.getTsInserimento());
		p.setImportoTransato(dist.getImporto());
		p.setImportoVoce(dist.getImporto().subtract(dist.getImportoCommissioni()));
		p.setImportoVoceCommissioni(dist.getImportoCommissioni());
		
		//query VIII, recupero allegato ma solo per PagoPa (poco chiaro)
		if (fornitore!=null && "NDP".equals(fornitore.getBundleKey())) {
			p.setIuv(dist.getIuv());
			p.setIdTransazione(pagamento.getIdRiscossionePsp()); // lo setto a questo valore solo in caso di NDP
			
			if (tipoNotifica.equals("ESEGUITO") 
					&& DataTypesUtils.isTrueValue(tribEnti.getFlAddRTVerificaPag())) { 
				Query q = em.createNamedQuery("getDocumentiNDP");
				q.setParameter("ID_UNIVOCO_VERSAMENTO", dist.getIuv());
				q.setParameter("COD_CONTESTO_PAGAMENTO", dist.getCodTransazionePSP());
				q.setParameter("ID_DOMINIO", dist.getIdentificativoFiscaleCreditore());
				q.setParameter("TIPO", "RT");
				List<GiornaleEventiDocumentiNDP> lGiornaleEventiList = (List<GiornaleEventiDocumentiNDP>) q.getResultList();

				if (lGiornaleEventiList.size() > 0) {		    	
					byte[] documento = lGiornaleEventiList.get(0).getDocumento();
					Allegato allegato = new Allegato();
					allegato.setContenuto(documento);
					allegato.setTipo(ContentType.RICEVUTA);
					allegato.setCodifica(MIMETypeCode.XML);
					p.setAllegato(allegato);
				} 
			}
		} else {
			p.setIuv("non.applicabile");
		}
		//#1394
		//la data viene valorizzata diversamente in funzione dello 
		//stato della notifica. 	
		if (StatoEnum.NOTIFICHE_ESEGUITO.equalsIgnoreCase(p.getEsito())) {
			p.setDataOraTransazione(pagamento.getTsDecorrenza());	
		} else if (StatoEnum.NOTIFICHE_REGOLATO.equalsIgnoreCase(p.getEsito())) {
			if (pagamento.getDataAccreditoContoTecnico()==null) {
				p.setDataOraTransazione(pagamento.getTsDecorrenza());					
			} else {
				p.setDataOraTransazione(new Timestamp(pagamento.getDataAccreditoContoTecnico().getTime()));
			}
		} if (StatoEnum.NOTIFICHE_INCASSO.equalsIgnoreCase(p.getEsito())) {	
			if (pagamento.getDataAccreditoEnte()==null) {
				p.setDataOraTransazione(pagamento.getTsDecorrenza());
			} else {
				p.setDataOraTransazione(new Timestamp(pagamento.getDataAccreditoEnte().getTime()));
			}						
		} else {
			p.setDataOraTransazione(pagamento.getTsDecorrenza());
		}

		p.setIdPendenza(pend.getIdPendenzaente());
		p.setTipoDebito(pend.getCdTrbEnte());
		p.setIdPendenzaEnte(pend.getIdPendenzaente());
	
		p.setFlagCausale(true); //TODO: valorizzare a modo.
		p.setTipoPagamento(cond.getTiPagamento());
		p.setTipoVersante("Codice Fiscale");
		p.setIdVersante(dist.getUtenteCreatore());
		p.setDescrizioneVersante(dist.getEmailVersante()!=null && !dist.getEmailVersante().trim().equals("") ? dist.getEmailVersante() : dist.getUtenteCreatore());
		p.setEmailVersante(dist.getEmailVersante()!=null && !dist.getEmailVersante().trim().equals("") ? dist.getEmailVersante() : null);
        //***** setto valori di default per campi codiceAutorizzazione e Descriz canale pagamento
		//per il cod.autorizzazione setto invece il valore di cod.pagamento
		//p.setCodiceAutorizzazione(dist.getCodTransazionePSP());
		p.setDescrizioneCanalePagamento(gateway.getSystemName()+ "("+ gateway.getSystemId()+")");
		//********* sovrascrittura dati  notifiche incasso in caso di assenza di flusso RH *********************
		if ("INCASSO".equals(p.getEsito()) && pagamento.getCodRendicontazioneIncasso()!=null && !"".equals(pagamento.getCodRendicontazioneIncasso())) {
		  
		   p.setDescrizioneCanalePagamento(pagamento.getMittenteRendicontazioneIncasso());
		   if (pagamento.getCodRendicontazioneIncasso().equals("FR")) {
			   p.setTipoCanalePagamento("SEPA");	
			   p.setMezzoPagamento("BonificoSCT"); 
		   } else {
			   if (pagamento.getCodRendicontazioneIncasso().equals("057")) {
				   p.setTipoCanalePagamento("SETIF");	
				   p.setMezzoPagamento("Bonifico");
			   } else {
				   if (pagamento.getCodRendicontazioneIncasso().equals("EP")) {
					   p.setTipoCanalePagamento("CBI");	
					   p.setMezzoPagamento("BonificoSCT");
				   } 
			   }
		   }		   
		   p.setIdTransazione(pagamento.getIdentificativoFlusso());		
		   p.setDataOraTransazione(new Timestamp(pagamento.getDataRegolamento().getTime()));
		   p.setCodiceAutorizzazione(pagamento.getTrn());
		   p.setImportoTransato(pagamento.getTotaleTransazioneIncasso());		     
		}		
		
	   p.setIdPsp(gateway.getSystemId());
	   p.setIdCanale(gateway.getApplicationId());
	   p.setDescrizionePsp(gateway.getSystemName());
	   p.setTipoVersamento(gateway.getTipoVersamento());

	   // Dati incasso (se disponibili)
	   p.setFlagIncasso(pagamento.getFlagIncasso());
	   p.setIdPspTransazioneIncasso(pagamento.getMittenteRendicontazioneIncasso());
	   p.setIdFlussoIncasso(pagamento.getIdentificativoFlusso());	   
	   if (pagamento.getDataRegolamento()!=null) {
		   p.setDataEsecuzioneIncasso(new Timestamp(pagamento.getDataRegolamento().getTime()));
	   }	  
	   p.setTotaleTransazioneIncasso(pagamento.getTotaleTransazioneIncasso());
	   p.setTrnIncasso(pagamento.getTrn());

	   // Dati transazione pagamento	   
		p.setIdPspTransazionePagamento(gateway.getSystemId());
		p.setIdCanaleTransazionePagamento(gateway.getApplicationId());
		p.setTipoVersamentoTransazionePagamento(gateway.getTipoVersamento());
		p.setDescrizioneCanaleTransazionePagamento(gateway.getSystemName());
		p.setIurTransazionePagamento(pagamento.getIdRiscossionePsp());
		p.setDataOraTransazionePagamento(pagamento.getTsDecorrenza());

		//query IX, estratto le distinte in caso di carrello RPT
		if (dist.getIdGruppo()!=null && !"".equals(dist.getIdGruppo())) {
			// Carrello RPT
			// Estraggo tutte le distinte per Id Gruppo
			Query q = em.createNamedQuery("DistintaByIdGruppo");
			q.setParameter("idGruppo", dist.getIdGruppo());
			List<DistintePagamento> listaDistinteGroup = (List<DistintePagamento>)q.getResultList();
			
			BigDecimal totTrans = BigDecimal.ZERO;
			BigDecimal totCommissioniTrans = BigDecimal.ZERO;
			Long numPagamenti = 0L;
			
			for (DistintePagamento d:listaDistinteGroup) {
				totTrans=totTrans.add(d.getImporto());
				totCommissioniTrans=totCommissioniTrans.add(d.getImportoCommissioni());
				numPagamenti = numPagamenti+d.getPagamentis().size();
			}			
			p.setImportoTransazionePagamento(totTrans);
			p.setImportoCommissioniTransazionePagamento(totCommissioniTrans);
			p.setNumeroPagamentiTransazionePagamento(numPagamenti);									
		} else {				
			p.setImportoTransazionePagamento(dist.getImporto());
			p.setImportoCommissioniTransazionePagamento(dist.getImportoCommissioni());
			p.setNumeroPagamentiTransazionePagamento(new Long(dist.getPagamentis().size()));						
		}		
		p.setDataOraInizioPagamento(pagamento.getTsInserimento());
		p.setNotePagamento(pagamento.getNotePagamento());
	    p.setRiferimentoDebito(pend.getRiferimento());
		//****************************************************************************************************
		return p;
		
	}

	/**
	 * 
	 * @param em
	 * @param cond
	 * @param p
	 * @param tribEnti2 
	 */
	private static void manageExternalIUV(EntityManager em,
			CondizioniPagamento cond, PagamentoModelTyped p, TributiEnti tribEnti) {
		if (tribEnti.getFlNdpIuvGenerato().equals("0")) {
			p.setIdPagamento(cond.getIdPagamento());
		} else {
			Query q= em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIuv");
			q.setParameter("iuv",cond.getIdPagamento());
			q.setParameter("idEnte", cond.getIdEnte());
			List<IUVPosizEnteMap> lIuvMapList = (List<IUVPosizEnteMap>) q.getResultList();
			if (lIuvMapList.size()>0) {		    	
				p.setIdPagamento(lIuvMapList.get(0).getIdPagamento());
			} else {
			    // Se non lo trovo metto quello della condizione (per gestire il transitorio)
				p.setIdPagamento(cond.getIdPagamento());
			}
		}
	}
	
	
}
