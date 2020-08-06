package it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder;

import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.dto.ddp.DettaglioDRPDTO;
import it.tasgroup.iris.dto.ddp.DettaglioRicevutaDTO;
import it.tasgroup.services.util.enumeration.EnumStatoDRP;
import it.tasgroup.services.util.enumeration.EnumTipoDRP;

import java.sql.Timestamp;
import java.util.Date;

public class DettaglioDRPDTOFactory {

	public static DettaglioDRPDTO createSpecificDetail(EnumTipoDRP tipo, EnumStatoDRP stato, Intestatari intestatario, String id, Timestamp dataPagamento, ContoTecnico contoTecnico,String causaleRPT){
				
		DettaglioRicevutaDTO newDetail = new DettaglioRicevutaDTO();
		
		newDetail.setTipo(tipo);
		
		newDetail.setStato(stato);
		
		newDetail.setId(id);
		
		if (contoTecnico!=null) {
			
			newDetail.setBeneficiario(contoTecnico.getIntestatario().getRagionesociale());
		
			newDetail.setCoordinateBancarie(contoTecnico.getIban());
		}	
		
		String codiceTransazione = id.contains("euPay") ? id.substring(6,22) : id;
		
		newDetail.setCodiceTransazione(codiceTransazione);
		
		newDetail.setDataPagamento(dataPagamento);
		
		newDetail.setCausaleRPT(causaleRPT);
		
		return newDetail;
		
	}
}
