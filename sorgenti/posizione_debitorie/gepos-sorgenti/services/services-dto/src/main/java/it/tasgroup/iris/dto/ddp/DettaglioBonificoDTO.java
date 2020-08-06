package it.tasgroup.iris.dto.ddp;

import it.tasgroup.services.util.enumeration.EnumTipoDDP;


public class DettaglioBonificoDTO extends DettaglioDDPBancarioDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final EnumTipoDDP tipo = EnumTipoDDP.BONIFICO;

	@Override
	public EnumTipoDDP getTipo() {
		
		return tipo;
	}

	@Override
	public String getFormattedID() {
		return null;
	}
}
