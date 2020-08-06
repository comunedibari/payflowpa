package it.tasgroup.iris.business.ejb.authentication.dto.builder;

import it.nch.is.fo.profilo.CurrentCorporateVOImpl;
import it.nch.is.fo.profilo.CurrentCorporateVOPojo;
import it.nch.is.fo.profilo.EntiCommon;
import it.nch.is.fo.profilo.Intestatari;

public class CurrentCorporateVoBuilder {

	
	public static CurrentCorporateVOPojo fromIntestatari(Intestatari intestatario) {
		
		CurrentCorporateVOPojo intestatarioCorrente = new CurrentCorporateVOImpl();

		intestatarioCorrente.setCorporate(intestatario.getCorporate());
		intestatarioCorrente.setName(intestatario.getRagionesociale());
		intestatarioCorrente.setSiaCode(intestatario.getSia());
		intestatarioCorrente.setSiaCbiCode(intestatario.getSiaCbi());
		intestatarioCorrente.setAbiCode(intestatario.getAbi());
		intestatarioCorrente.setCabCode(intestatario.getCab());
		intestatarioCorrente.setRaplCode(intestatario.getRapl());
		intestatarioCorrente.setLaplCode(intestatario.getLapl());
		intestatarioCorrente.setTipointestatario(intestatario.getTipointestatario());
		intestatarioCorrente.setAbiaccentratore(intestatario.getAbiaccentratore());
		intestatarioCorrente.setDenominazione(intestatario.getDenominazione());
		intestatarioCorrente.setCategoria(intestatario.getCategoriaEnum().getChiave());
		intestatarioCorrente.setSottoCategoria(intestatario.getSottoCategoria());
		
		if(intestatario.getIndirizzipostaliobj() != null) {
			intestatarioCorrente.setFiscalCode(intestatario.getIndirizzipostaliobj().getFiscalCodeIForm());
			intestatarioCorrente.setAddress(intestatario.getIndirizzipostaliobj().getAddressIForm());
			intestatarioCorrente.setCity(intestatario.getIndirizzipostaliobj().getCityIForm());
			intestatarioCorrente.setCapCode(intestatario.getIndirizzipostaliobj().getCapCodeIForm());
			intestatarioCorrente.setProvince(intestatario.getIndirizzipostaliobj().getProvinceIForm());
			intestatarioCorrente.setFlagResidence(intestatario.getIndirizzipostaliobj().getFlagResidenceIForm());
			intestatarioCorrente.setPartitaIva(intestatario.getIndirizzipostaliobj().getVatCodeIForm());
		}

		
		if (intestatario.getEnti()!=null && !intestatario.getEnti().isEmpty()) {
			for (EntiCommon ente: intestatario.getEnti()) {
				intestatarioCorrente.setIdEnte(ente.getIdEnteIForm());
				intestatarioCorrente.setCdEnte(ente.getCodiceEnteIForm());
			}
		}
		
		return intestatarioCorrente;
		
	}

}
