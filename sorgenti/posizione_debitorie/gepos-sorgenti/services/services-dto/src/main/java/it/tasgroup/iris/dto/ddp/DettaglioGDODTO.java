package it.tasgroup.iris.dto.ddp;

import it.tasgroup.services.util.enumeration.EnumTipoDDP;


public class DettaglioGDODTO extends DettaglioDDPDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final EnumTipoDDP tipo = EnumTipoDDP.GDO;

	@Override
	public EnumTipoDDP getTipo() {
		
		return tipo;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass()+"\n");
		sb.append("[");
	    sb.append(super.toString());
	    sb.append("]\n");
	    return sb.toString();
	}

	@Override
	public String getFormattedID() {
		return null;
	}
}
