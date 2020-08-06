/**
 * 
 */
package it.tasgroup.iris.business.ejb.pagamenti;

import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaVO;
import it.nch.is.fo.stati.pagamenti.CheckRiconciliazioneCompleta;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;
import it.tasgroup.services.util.enumeration.EnumTipoDebito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author pazzik
 *
 */
public class PaymentEntityBuilder {
	
	public static Set<Pagamenti> popolaPagamenti(List<CondizionePagamento> listaCondizioni, GestioneFlussi gestioneFlussi) {
		
		Set<Pagamenti> setPagamenti = new HashSet<Pagamenti>();

		for (CondizionePagamento condizionePagamento : listaCondizioni) {
			
			BillItemInspector.makeVisible(condizionePagamento);
			
			setPagamenti.add(popolaPagamento(condizionePagamento, gestioneFlussi));
			
		}
		
		return setPagamenti;
	}

	public static Pagamenti popolaPagamento(CondizionePagamento condizione, GestioneFlussi gestioneFlussi) {
		
		return popolaPagamento(condizione, gestioneFlussi, null);
		
	}

	public static Pagamenti popolaPagamento(CondizionePagamento condizione, GestioneFlussi gestioneFlussi, BigDecimal newImporto) {

		// PAGAMENTI
		Pagamenti pagamento = new Pagamenti();
		
		pagamento.setTsDecorrenza(gestioneFlussi.getTmbcreazione());
		pagamento.setFlussoDistinta(gestioneFlussi);
		String idPendenzaCorr = "";
		
		if (condizione.getPendenza().getIdPendenza() != null) {
			
			idPendenzaCorr = condizione.getPendenza().getIdPendenza();
			pagamento.setIdPendenza(idPendenzaCorr);
			
		}
		
		String idPendenzaEnteCorr = "";
		
		if (condizione.getPendenza().getIdPendenzaente() != null) {
			
			idPendenzaEnteCorr = condizione.getPendenza().getIdPendenzaente();
			pagamento.setIdPendenzaente(idPendenzaEnteCorr);
			
		}
		pagamento.setCondPagamento(condizione);
		pagamento.setIdTributo(condizione.getPendenza().getCategoriaTributo().getIdTributo());
		pagamento.setIdEnte(condizione.getEnte().getIdEnte());
		pagamento.setCdTrbEnte(condizione.getCdTrbEnte());
		pagamento.setCoPagante(gestioneFlussi.getUtentecreatore());
		pagamento.setDtScadenza(condizione.getDtScadenza());
		pagamento.setTiPagamento(condizione.getTiPagamento());
		// TI_PAGAMENTO Ti_pagamento della JTLCOPD
		pagamento.setStPagamento(StatiPagamentiIris.IN_CORSO.getPagaMapping());

		pagamento.setImPagato(condizione.getImTotale());

		// IM_PAGATO Importo della condizione JLTCOPD
		pagamento.setTipospontaneo(PagamentoPosizioneDebitoriaVO.TIPO_ATTESO);
		
		// TIPOSPONTANEO ?
		pagamento.setTsOrdine(gestioneFlussi.getTmbcreazione());
		pagamento.setStRiga("V");
		Long v = new Long("1");
		pagamento.setVersion(v);
		
		// TODO: DA VERIFICARE
		pagamento.setOpInserimento(gestioneFlussi.getOpInserimento());
		// OP_INSERIMENTO Codice fiscale intestatario legato
		// alle condizioni
		pagamento.setTsInserimento(gestioneFlussi.getTmbcreazione());
		// ID_PENDENZAENTE ?
		// salvo i dati da tornare in output relativi alla pendenza
        pagamento.setDistinta("02");
        
        pagamento.setTiDebito(EnumTipoDebito.PENDENZA.getDescrizione());
        
        if ("E_BOLLO".equals(pagamento.getCdTrbEnte()) || (pagamento.getFlussoDistinta().getCfgGatewayPagamento().getFlagRendRiversamento().equals("0") && 
				CheckRiconciliazioneCompleta.isEnteRiconciliazioneCompleta(gestioneFlussi.getIdentificativoFiscaleCreditore()))) {
        	pagamento.setFlagIncasso("N");	
		} else {
			pagamento.setFlagIncasso("0");
		}
		return pagamento;
	}
	
	/**
	 * 
	 * @param autorizzazioneDto
	 * @param flus
	 * @param now
	 * @param codiceFiscaleDestinatario 
	 * @return
	 * @throws Exception
	 */
	public static PagamentiOnline popolaPagamentoOnline(TestataMessaggioDTO testataIn,
			String codAutorizzazione, GestioneFlussi flus, String deOperazione, EnumOperazioniPagamento stato,
			EnumBusinessReturnCodes returnCode, String opInserimento) {
		
		
		String codiceAutorizzazione;
		String sessionIdToken = testataIn.getSession().getToken();
		
		PagamentiOnline pol = new PagamentiOnline();
		if (flus != null) {						
			pol.setFlussoDistintaOnline(flus);
			codiceAutorizzazione = flus.getIuv();
		} else {
			codiceAutorizzazione = codAutorizzazione;
		}
		
		pol.setSessionIdSistema(testataIn.getSession().getIdSistema());
		pol.setSessionIdTerminale(testataIn.getSession().getIdTerminale());
		pol.setSessionIdToken(sessionIdToken);
		
		String timbroTroncato = testataIn.getSession().getDataOraAccesso();
		
		if (timbroTroncato.length()>20)
			timbroTroncato=timbroTroncato.substring(0, 19);
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		pol.setSessionIdTimbro(timbroTroncato);
		pol.setTsOperazione(now);
		pol.setTiOperazione(stato.getChiave());
		pol.setIdOperazione(stato.getChiave());
		
		if(deOperazione != null)		
			pol.setDeOperazione(deOperazione);	
		else		
			pol.setDeOperazione(stato.getDescrizione());
		
		pol.setTsInserimento(now);
		pol.setCodAutorizzazione(codiceAutorizzazione);
		
		String esito = returnCode.isError() ? "KO" : "OK";
		
		pol.setEsito(esito);
		pol.setCodErrore(returnCode.getChiave());
		pol.setApplicationId(testataIn.getSenderSil());
		pol.setSystemId(testataIn.getSenderSys());
		pol.setOpInserimento(opInserimento);
		
		
		return pol;
	}


}
