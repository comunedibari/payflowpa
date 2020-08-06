/**
 * 
 */
package it.tasgroup.iris.business.ejb.pagamenti.dto.builder;

import it.nch.fwk.fo.async.Advice;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.stati.pagamenti.data.AutorizzaPagamentoResponse;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.dto.AllegatiPendenzaDTO;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;

/**
 * @author pazzik
 *
 */
public class PAIBusinessDTOBuilder {
	
	public static AllegatiPendenzaDTO fillAllegatoPendenzaDTO(AllegatiPendenza allegato){
		
		AllegatiPendenzaDTO allegatoDTO = new AllegatiPendenzaDTO();
		
		allegatoDTO.setDatiBody(allegato.getDatiBody());
		
		allegatoDTO.setFlContesto(allegato.getFlContesto());
		
		allegatoDTO.setIdAllegato(allegato.getIdAllegato());
		
		allegatoDTO.setIdAntifalsific(allegato.getIdAntifalsific());
		
		allegatoDTO.setIdCondizione(allegato.getIdCondizione());
		
		allegatoDTO.setIdPendenza(allegato.getIdPendenza());
		
		allegatoDTO.setStRiga(allegato.getStRiga());
		
		allegatoDTO.setTiAllegato(EnumTipoAllegato.valueOf(allegato.getTiAllegato().toUpperCase()));
		
		allegatoDTO.setTiCodificaBody(allegato.getTiCodificaBody());
		
		allegatoDTO.setTitolo(allegato.getTitolo());
		
		allegatoDTO.setTsDecorrenza(allegato.getTsDecorrenza());
		
		return allegatoDTO;
		
	}
	
	public static CondizionePagamentoDTO fillCondizionePagamentoDTO(AutorizzaPagamentoResponse apv, TributoEnte te,CondizionePagamento condizione){
		
		CondizionePagamentoDTO condDTO = new  CondizionePagamentoDTO();
		
		EnteDTO enteDTO = new EnteDTO();
		
		IndirizzoDTO indirizzoDTO = new IndirizzoDTO();
		
		
		condDTO.setCausalePagamento(apv.getCausaleVersamento());
		
		condDTO.setCdTrbEnte(te.getCdTrbEnte());
		
//		condDTO.setCoCip(condizione.getCoCip());
//		
//		condDTO.setDeCanalepag(condizione.getDeCanalepag());
//		
//		condDTO.setDeMezzoPagamento(condizione.getDeMezzoPagamento());
//		
//		condDTO.setDeNotePagamento(condizione.getDeNotePagamento());
//		
		condDTO.setIdPagamento(apv.getCodiceIdentificativoUnivocoDebitore());
		
		condDTO.setImTotale(apv.getImportoPagamento());
		
//		condDTO.setStPagamento(condizione.getStPagamento());
		
//		condDTO.setStatoPagamentoCalcolato(condizione.getStatoPagamentoCalcolato());
		
//		condDTO.setCodiceFiscaleDebitore(condizione.getPendenza().getDestinatari().iterator().next().getCoDestinatario());
		
		condDTO.setDescrizioneCreditore(te.getEntiobj().getIntestatarioobj().getRagionesocialeIForm());
		
		condDTO.setDescrizioneDebito(te.getDeTrb());
		
		condDTO.setCausalePendenza(apv.getCausaleVersamento());
		
//		condDTO.setIdPendenza(condizione.getPendenza().getIdPendenza());
		
//		condDTO.setIdPendenzaEnte(condizione.getPendenza().getIdPendenzaente());

//		condDTO.setDeNotePendenza(condizione.getPendenza().getNote());
		
		// Inserimento dell'Iban di accredito
		if (te.getIBAN_CCP()!=null && !"".equals(te.getIBAN_CCP())) {
			condDTO.setIbanBeneficiario(te.getIBAN_CCP());
//		} else if (condizione.getIbanBeneficiario()!=null && !"".equals(condizione.getIbanBeneficiario().trim())) {
//			condDTO.setIbanBeneficiario(condizione.getIbanBeneficiario());
		} else {
			condDTO.setIbanBeneficiario(te.getIBAN());
		}
		
		
		Enti ente = te.getEntiobj();
		
		enteDTO.setCodice(ente.getCodiceEnte());
		
		enteDTO.setDenominazione(ente.getDenominazione());
		
		IndirizzipostaliCommon indirizzo = ente.getIntestatarioobj().getIndirizzipostaliobjIForm();
		
		indirizzoDTO.setCap(indirizzo.getCapCodeIForm());
		
		indirizzoDTO.setNazione(indirizzo.getCountryIForm());
		
		indirizzoDTO.setProvincia(indirizzo.getProvinceIForm());
		
		indirizzoDTO.setVia(indirizzo.getAddressIForm());
		
		indirizzoDTO.setNumeroCivico(indirizzo.getNumeroCivicoIForm());
		
		enteDTO.getIntestatario().setIndirizzo(indirizzoDTO);
		
		condDTO.setEnte(enteDTO);
		
//		condDTO.setDtScadenza(condizione.getDtScadenza());
				
		return condDTO;
	}

	
	public static CondizionePagamentoDTO fillCondizionePagamentoDTO(CondizionePagamento condizione, AllegatiPendenza allegato){
		
		CondizionePagamentoDTO condDTO = new  CondizionePagamentoDTO();
		
		EnteDTO enteDTO = new EnteDTO();
		
		IndirizzoDTO indirizzoDTO = new IndirizzoDTO();
		
		if (allegato != null) { 
					
				AllegatiPendenzaDTO allegatoDTO = fillAllegatoPendenzaDTO(allegato);
			
				condDTO.setAllegato(allegatoDTO);
				
		}
		
		condDTO.setCausalePagamento(condizione.getCausalePagamento());
		
		condDTO.setCdTrbEnte(condizione.getCdTrbEnte());
		
		condDTO.setCoCip(condizione.getCoCip());
		
		condDTO.setDeCanalepag(condizione.getDeCanalepag());
		
		condDTO.setDeMezzoPagamento(condizione.getDeMezzoPagamento());
		
		condDTO.setDeNotePagamento(condizione.getDeNotePagamento());
		
		condDTO.setIdPagamento(condizione.getIdPagamento());
		
		condDTO.setImTotale(condizione.getImTotale());
		
		condDTO.setStPagamento(condizione.getStPagamento());
		
		condDTO.setStatoPagamentoCalcolato(condizione.getStatoPagamentoCalcolato());
		
		condDTO.setCodiceFiscaleDebitore(condizione.getPendenza().getDestinatari().iterator().next().getCoDestinatario());
		
		condDTO.setDescrizioneCreditore(condizione.getEnte().getIntestatarioobj().getRagionesocialeIForm());
		
		condDTO.setDescrizioneDebito(condizione.getPendenza().getTributoEnte().getDeTrb());
		
		condDTO.setCausalePendenza(condizione.getPendenza().getDeCausale());
		
		condDTO.setIdPendenza(condizione.getPendenza().getIdPendenza());
		
		condDTO.setIdPendenzaEnte(condizione.getPendenza().getIdPendenzaente());

		condDTO.setDeNotePendenza(condizione.getPendenza().getNote());
		
		// Inserimento dell'Iban di accredito
		if (condizione.getPendenza().getTributoEnte().getIBAN_CCP()!=null && !"".equals(condizione.getPendenza().getTributoEnte().getIBAN_CCP())) {
			condDTO.setIbanBeneficiario(condizione.getPendenza().getTributoEnte().getIBAN_CCP());
		} else if (condizione.getIbanBeneficiario()!=null && !"".equals(condizione.getIbanBeneficiario().trim())) {
			condDTO.setIbanBeneficiario(condizione.getIbanBeneficiario());
		} else {
			condDTO.setIbanBeneficiario(condizione.getPendenza().getTributoEnte().getIBAN());
		}
		
		
		Enti ente = condizione.getPendenza().getTributoEnte().getEntiobj();
		
		enteDTO.setCodice(ente.getCodiceEnte());
		
		enteDTO.setId(ente.getIdEnte());
		
		enteDTO.setDenominazione(ente.getDenominazione());
		
		IndirizzipostaliCommon indirizzo = ente.getIntestatarioobj().getIndirizzipostaliobjIForm();
		
		indirizzoDTO.setCap(indirizzo.getCapCodeIForm());
		
		indirizzoDTO.setNazione(indirizzo.getCountryIForm());
		
		indirizzoDTO.setProvincia(indirizzo.getProvinceIForm());
		
		indirizzoDTO.setVia(indirizzo.getAddressIForm());
		
		indirizzoDTO.setNumeroCivico(indirizzo.getNumeroCivicoIForm());
		
		enteDTO.getIntestatario().setIndirizzo(indirizzoDTO);
		
		condDTO.setEnte(enteDTO);
		
		condDTO.setDtScadenza(condizione.getDtScadenza());
				
		return condDTO;
	}

	
}
