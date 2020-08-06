package it.nch.pagamenti;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;



public class MulteFormImpl extends BaseForm implements IMulteForm{

	private String cdEnte;
	private String deEnte;
	private String cdTributo;
	private String deTributo;
    private String idPagamento;
    private String targa;
	private String numeroVerbale;
	private String dataVerbaleYY;
	private String dataVerbaleMM;
	private String dataVerbaleGG;
    private String importoSanzione;	
    private String serie;
    private String annoVerbale;
    private String note;
    private String tipoVerbale;
    private String tipoTributo;
    
    public String getTipoTributo() {
		return tipoTributo;
	}
	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}
	public String getTipoVerbale() {
		return tipoVerbale;
	}
	public void setTipoVerbale(String tipoVerbale) {
		this.tipoVerbale = tipoVerbale;
	}

	private String command;
    
    
    
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getAnnoVerbale() {
		return annoVerbale;
	}
	public void setAnnoVerbale(String annoVerbale) {
		this.annoVerbale = annoVerbale;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getCdEnte() {
		return cdEnte;
	}
	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}
	public String getDeEnte() {
		return deEnte;
	}
	public void setDeEnte(String deEnte) {
		this.deEnte = deEnte;
	}
	public String getCdTributo() {
		return cdTributo;
	}
	public void setCdTributo(String cdTributo) {
		this.cdTributo = cdTributo;
	}
	public String getDeTributo() {
		return deTributo;
	}
	public void setDeTributo(String deTributo) {
		this.deTributo = deTributo;
	}

	
	
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getNumeroVerbale() {
		return numeroVerbale;
	}
	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public String getImportoSanzione() {
		return importoSanzione;
	}
	public void setImportoSanzione(String importoSanzione) {
		   this.importoSanzione = importoSanzione;
	}
    
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}
	
	public String getDataVerbaleYY() {
		return dataVerbaleYY;
	}
	public void setDataVerbaleYY(String dataVerbaleYY) {
		this.dataVerbaleYY = dataVerbaleYY;
	}
	public String getDataVerbaleMM() {
		return dataVerbaleMM;
	}
	public void setDataVerbaleMM(String dataVerbaleMM) {
		this.dataVerbaleMM = dataVerbaleMM;
	}
	public String getDataVerbaleGG() {
		return dataVerbaleGG;
	}
	public void setDataVerbaleGG(String dataVerbaleGG) {
		this.dataVerbaleGG = dataVerbaleGG;
	}
	
	public String getDataVerbale(){
		return dataVerbaleGG + "/" + dataVerbaleMM + "/" +dataVerbaleYY;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionMessages messaggi = new ActionMessages();
		ActionErrors ar = new ActionErrors();
		ar = super.validate(mapping, request);
		

		String dataVerbale = dataVerbaleGG + dataVerbaleMM + dataVerbaleYY;

		//controllo dataVerbale oppure annoVerbale
		if ((dataVerbale.trim().length() == 0 && annoVerbale.trim().length() == 0) || 
				(dataVerbale.trim().length() != 0 && annoVerbale.trim().length() != 0)){
			ar.add("dataVerbaleYY",new ActionMessage("errors.requiredwhen","Data Verbale", "Anno Verbale"));
			ar.add("annoVerbale",new ActionMessage("errors.requiredwhen","Anno Verbale", "Data Verbale"));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setLenient(false);

		
		//validazione data verbale
		if (dataVerbale.trim().length() > 0){
			try {
				Date d = sdf.parse(dataVerbale);
			} catch (ParseException e) {
				ar.add("dataVerbaleYY",new ActionMessage("errors.date","Data Verbale"));
			}
		}
		
		//validazione anno verbale
		if (annoVerbale.trim().length() > 0){
			try {
				Date d = sdf.parse("01"+"01"+annoVerbale);
			} catch (ParseException e) {
				ar.add("annoVerbale",new ActionMessage("errors.date","Anno Verbale"));
			}
		}

		//copia errori 
		for (Iterator iterator = ar.get(); iterator.hasNext();) {
			messaggi.add("ERRORI",(ActionMessage)iterator.next());
		}
		
		request.setAttribute(Globals.MESSAGE_KEY,messaggi );
		
		return ar;
	}
	
}
