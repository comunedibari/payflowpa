package it.tasgroup.iris.dto.ddp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import it.tasgroup.report.PrintableDocument;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

public abstract class DettaglioDDPDTO extends DettaglioDTO implements PrintableDocument{
	
	private Date dataPagamento;
	private Date dtScadenzaDoc;
	private EnumStatoDDP stato;
	private String barCode;
	private String formattedBarCode;
	private String IUV;
	private String dataScadenza;
	private String GLN;
	private String idPagamento;
	

	public abstract EnumTipoDDP getTipo();
		
	public EnumStatoDDP getStato() {
		return stato;
	}
	
	public void setStato(EnumStatoDDP stato) {
		this.stato = stato;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("tipo="+this.getTipo());
		sb.append(", stato="+this.getStato());	
		sb.append(", barCode="+this.getBarCode());
		sb.append(", formattedBarCode="+this.getFormattedBarCode());
		sb.append(", dataPagamento="+this.getDataPagamento());
		sb.append(", dtScadenzaDoc="+this.getDtScadenzaDoc());
		return sb.toString();
	}

	@Override
	public Boolean needWatermark() {
		
		if (stato == null)
			return false;
		
		return !EnumStatoDDP.EMESSO.equals(stato) && !EnumStatoDDP.PAGATO.equals(stato);
	}

	@Override
	public String getWatermarkText(ResourceBundle bundle) {
		
		if (stato == null)
			return null;
		
		String bundleKey = stato.getChiaveBundle();
		return bundle.getString(bundleKey+".watermark");
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDtScadenzaDoc() {
		return dtScadenzaDoc;
	}

	public String getDtScadenzaDocFormatted() {
		if (dtScadenzaDoc==null) {
			return "n/a";
		} else {
		  SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		  return f.format(dtScadenzaDoc);		  
		}
	}
	public void setDtScadenzaDoc(Date dtScadenzaDoc) {
		this.dtScadenzaDoc = dtScadenzaDoc;
	}
	
	public String getBarCode() {
    	return barCode;
    }

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public String getFormattedBarCode() {
    	return formattedBarCode;
    }

	public void setFormattedBarCode(String formattedBarCode) {
		this.formattedBarCode = formattedBarCode;
	}

	public String getIUV() {
		return IUV;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setIUV(String iUV) {
		IUV = iUV;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getGLN() {
		return GLN;
	}

	public void setGLN(String gLN) {
		GLN = gLN;
	}

	public String getIdPagamento() {
	    return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
	    this.idPagamento = idPagamento;
	}
	
}
