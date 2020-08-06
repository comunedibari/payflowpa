package it.tasgroup.iris.dto.ddp;

import it.tasgroup.report.PrintableDocument;
import it.tasgroup.services.util.enumeration.EnumStatoDRP;
import it.tasgroup.services.util.enumeration.EnumTipoDRP;

import java.util.ResourceBundle;

/**
 * @author PazziK
 */
public class DettaglioDRPDTO extends DettaglioDTO implements PrintableDocument{
	
	private EnumTipoDRP tipo;
	private EnumStatoDRP stato;
	
	public EnumTipoDRP getTipo() {
		return tipo;
	}
	public void setTipo(EnumTipoDRP tipo) {
		this.tipo = tipo;
	}
	public EnumStatoDRP getStato() {
		return stato;
	}
	public void setStato(EnumStatoDRP stato) {
		this.stato = stato;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("tipo="+this.getTipo());
		sb.append(", stato="+this.getStato());		
		return sb.toString();
	}
	
	@Override
	public Boolean needWatermark() {
		
		if (stato == null)
			return false;
		
		return stato.equals(EnumStatoDRP.INCASSATO);
		
	}
	
	@Override
	public String getWatermarkText(ResourceBundle bundle) {
		
		if (stato == null)
			return null;
		
		String bundleKey = stato.getChiaveBundle();
		return bundle.getString(bundleKey+".watermark");
	}
	@Override
	public String getFormattedID() {
		return null;
	}
	
	
}
