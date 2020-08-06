package it.tasgroup.iris.payment.helper;

import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FornitorePagamentoCBILL extends FornitorePagamento {
	
	// intervallo in millisecondi dopo il quale la distinta viene considerata
	// da annullare
	private static final int DELAY = 300000;
	private boolean annullaPagamentoInCorsoPuntoSI = false;
	
	FornitorePagamentoCBILL() {
		ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		annullaPagamentoInCorsoPuntoSI= props.getBooleanProperty("annulla.pagamenti.incorso.puntosi");
		
	}

	@Override
	protected String preparaRichiesta(HttpServletRequest request, HttpServletResponse response) throws PagamentoException {

		return null;
	}
	
	@Override
	protected boolean aggiornaStatoDistintaInCorso(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) throws Exception {
		
		String modalitaPagamento = dettDistinta.getModalitaPagamento();
		
		EnumTipoModalitaPagamento enumModalitaPagamento = EnumTipoModalitaPagamento.getByDescrizione(modalitaPagamento);

		switch (enumModalitaPagamento) {

			case CBILL:
				return updatePaymentStatusForCBILL(dettDistinta);
	
			case TTMSST:
				if (annullaPagamentoInCorsoPuntoSI) {
				    return updatePaymentStatusForCBILL(dettDistinta);
				} else {
					return false;
				}
			default:
				return false;

		}
	}

	private boolean updatePaymentStatusForCBILL(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) throws Exception {

		Tracer.info(className, "updatePaymentStatusForCBILL", "START");
			
		StatiPagamentiIris statoIrisObj = StatiPagamentiIris.ANNULLATO;
		String deOperazione = "STATUS UPDATED from " + dettDistinta.getStato() + " to " + statoIrisObj.getFludMapping();

		// recupero tramite mapper lo stato della distinta
		// (JLTFLUD) e lo stato del pagamento (JLTPAGA)
		dettDistinta.setStato(statoIrisObj.getFludMapping());
		dettDistinta.setStPagamento(statoIrisObj.getPagaMapping());
		dettDistinta.setTranId(null);
		dettDistinta.setDeOperazione(deOperazione);
		
//		DTO dto = new DTOImpl();
//		dto.setVO(dettDistinta);
//
//		bxbd.execute(PosizioneDebitoriaService.AGGIORNA_STATO_DISTINTA, profileManager, dto);

//		try{	
//		
		aggiornaEsito(dettDistinta);
			
//		} catch (Exception e) {

		
		
		Tracer.debug(className, "updatePaymentStatusForCBILL", "Terminato con stato " + statoIrisObj.getFludMapping());
		Tracer.debug(className, "updatePaymentStatusForCBILL", "END");
		
		return true;
	}

}
