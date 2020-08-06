package it.tasgroup.iris.dto.ddp;

import it.tasgroup.services.util.enumeration.EnumTipoDDP;


public class DettaglioATMDTO extends DettaglioDDPDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final EnumTipoDDP tipo = EnumTipoDDP.ATM;
	
	public String toString() {
		   StringBuffer sb = new StringBuffer();
		   sb.append("\n"+this.getClass()+"\n");
	       sb.append("[");
	       sb.append(super.toString());
	       sb.append("]\n");
	       return sb.toString();
	}

	@Override
	public EnumTipoDDP getTipo() {
		
		return tipo;
	}
	
	
	public String getFormattedID() {
		
		StringBuffer buf = new StringBuffer();
		
		String compactId = getId();
		
		for (int i=0; i<compactId.length(); i++){
			
			buf.append(compactId.charAt(i));
			
			if ((i+1)%4==0)
				buf.append(' ');
		}
			
		
		return buf.toString();
		
	}
}
