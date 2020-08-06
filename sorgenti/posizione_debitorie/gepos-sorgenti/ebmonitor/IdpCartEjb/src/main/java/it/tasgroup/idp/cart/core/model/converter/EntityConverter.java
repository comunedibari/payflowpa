package it.tasgroup.idp.cart.core.model.converter;

import java.sql.Timestamp;
import java.util.Date;

import it.tasgroup.idp.cart.core.model.ErroreComponenteModel;
import it.tasgroup.idp.cart.core.model.GestioneModel;
import it.tasgroup.idp.cart.core.model.MessaggioModel;
import it.tasgroup.idp.cart.core.model.MessaggioNonGestitoModel;
import it.tasgroup.idp.cart.core.model.TipoGestione;
import it.tasgroup.idp.cart.core.model.TipoMessaggio;
import it.tasgroup.idp.cart.orm.monitoraggio.Gestioni;
import it.tasgroup.idp.cart.orm.monitoraggio.Messaggi;
import it.tasgroup.idp.cart.orm.monitoraggio.MessaggiNonGestiti;
import it.tasgroup.idp.cart.orm.monitoraggio.enums.EnumTipoGestione;
import it.tasgroup.idp.cart.orm.monitoraggio.enums.EnumTipoMessaggio;

public class EntityConverter {


	public static Messaggi getMessaggioVo(MessaggioModel dto){
		Messaggi vo = new Messaggi();
		if(dto.getDataCreazione() != null)
			vo.setDataCreazione(new Timestamp(dto.getDataCreazione().getTime()));
		if(dto.getDataUltimaConsegnaEsito() != null)
			vo.setDataUltimaConsegnaEsito(new Timestamp(dto.getDataUltimaConsegnaEsito().getTime()));
		if(dto.getDataUltimaConsegnaRichiesta() != null)
			vo.setDataUltimaConsegnaRichiesta(new Timestamp(dto.getDataUltimaConsegnaRichiesta().getTime()));
		vo.setFlEsitoConsegnato(dto.isEsitoConsegnato());
		vo.setId(dto.getId());
		vo.setMsgId(dto.getMsgId());
		vo.setFlRichiestaConsegnata(dto.isRichiestaConsegnata());
		vo.setSil(dto.getSil());
		vo.setSoggetto(dto.getSoggetto());
		vo.setTipoMessaggio(getTipoMessaggioVO(dto.getTipo())); 

		return vo;
	}

	public static EnumTipoMessaggio getTipoMessaggioVO(TipoMessaggio dto){
		if(dto == null) return null;

		switch (dto) {
		case ALLINEAMENTO_PENDENZE:
			return EnumTipoMessaggio.AP;
		case INFORMATIVA_PAGAMENTO:
		default:
			return EnumTipoMessaggio.IP;
		}
	}

	public static Gestioni getGestioneVO(GestioneModel dto){
		Gestioni vo = new Gestioni();

		if(dto.getErroreComponente() != null){
			vo.setCodErrore(dto.getErroreComponente().getCodErrore());
			vo.setComponenteResponsabile(dto.getErroreComponente().getCodiceComponente());
			vo.setDescrErrore(dto.getErroreComponente().getDescrErrore());
		}
		if(dto.getInizioGestione() != null)
			vo.setDataInizioGestione(new Timestamp(dto.getInizioGestione().getTime()));   
		vo.setId(dto.getId());
		vo.setIdEgov(dto.getIdEgov());
		vo.setTempoGestione(dto.getTempoGestione());
		vo.setTipoGestione(getTipoGestioneVO(dto.getTipo()));
		vo.setHttpHeaders(dto.getHttpHeaders());
		vo.setHttpResponseCode(dto.getHttpResponseCode()); 
		return vo;
	}

	public static EnumTipoGestione getTipoGestioneVO(TipoGestione dto){
		if(dto == null) return null;

		switch (dto) {
		case INBOUND:
			return EnumTipoGestione.INBOUND;
		case OUTBOUND:
		default:
			return EnumTipoGestione.OUTBOUND; 
		}
	}



	public static MessaggioModel getMessaggioDTO(Messaggi vo){
		MessaggioModel dto = new MessaggioModel();
		if(vo.getDataCreazione() != null)
			dto.setDataCreazione(new Date(vo.getDataCreazione().getTime()));
		if(vo.getDataUltimaConsegnaEsito() != null)
			dto.setDataUltimaConsegnaEsito(new Date(vo.getDataUltimaConsegnaEsito().getTime()));
		if(vo.getDataUltimaConsegnaRichiesta() != null)
			dto.setDataUltimaConsegnaRichiesta(new Date(vo.getDataUltimaConsegnaRichiesta().getTime()));
		dto.setEsitoConsegnato(vo.isFlEsitoConsegnato());
		dto.setId(vo.getId());
		dto.setMsgId(vo.getMsgId());
		dto.setRichiestaConsegnata(vo.isFlRichiestaConsegnata());
		dto.setSil(vo.getSil());
		dto.setSoggetto(vo.getSoggetto());
		dto.setTipo(getTipoMessaggioDTO(vo.getTipoMessaggio())); 

		return dto;
	}

	public static TipoMessaggio getTipoMessaggioDTO(EnumTipoMessaggio vo){
		if(vo == null) return null;

		switch (vo) {
		case AP:
			return TipoMessaggio.ALLINEAMENTO_PENDENZE;
		case IP:
		default:
			return TipoMessaggio.INFORMATIVA_PAGAMENTO;
		}
	}

	public static GestioneModel getGestioneDTO(Gestioni vo){
		GestioneModel dto = new GestioneModel();

		if(vo.getCodErrore() != null || vo.getComponenteResponsabile()  != null || vo.getDescrErrore() != null){
			ErroreComponenteModel err = new ErroreComponenteModel();
			err.setCodErrore(vo.getCodErrore());
			err.setCodiceComponente(vo.getComponenteResponsabile());
			err.setDescrErrore(vo.getDescrErrore());
			dto.setErroreComponente(err);
		}

		if(vo.getDataInizioGestione() != null)
			dto.setInizioGestione(new Date(vo.getDataInizioGestione().getTime()));   
		dto.setId(vo.getId());
		dto.setIdEgov(vo.getIdEgov());
		dto.setTempoGestione(vo.getTempoGestione());
		dto.setTipo(getTipoGestioneDTO(vo.getTipoGestione()));
		if(vo.getMessaggi() != null)
			dto.setIdMessaggio(vo.getMessaggi().getId()); 
		dto.setHttpHeaders(vo.getHttpHeaders());
		dto.setHttpResponseCode(vo.getHttpResponseCode());
		dto.setMessaggio(EntityConverter.getMessaggioDTO(vo.getMessaggi()));  
		return dto;
	}

	public static TipoGestione getTipoGestioneDTO(EnumTipoGestione vo){
		if(vo == null) return null;

		switch (vo) {
		case INBOUND:
			return TipoGestione.INBOUND;
		case OUTBOUND:
		default:
			return TipoGestione.OUTBOUND; 
		}
	}


	public static MessaggiNonGestiti getMessaggioNonGestitoVO(MessaggioNonGestitoModel dto){
		MessaggiNonGestiti vo = new MessaggiNonGestiti();
		if(dto.getDataRicezione() != null)
			vo.setDataRicezione(new Timestamp(dto.getDataRicezione().getTime()));
		vo.setCodErrore(dto.getCodErrore());
		String descr = dto.getDescrErrore();
		if(descr != null && descr.length() > 254)
			descr = descr.substring(0, 254);

		vo.setDescrErrore(descr);
		vo.setIdEgov(dto.getIdEgov());
		vo.setMittente(dto.getMittente());
		vo.setTipo(getTipoMessaggioVO(dto.getTipo()));
		vo.setTipoMittente(dto.getTipoMittente());

		return vo;
	}
}
