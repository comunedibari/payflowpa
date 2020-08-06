package it.nch.pagamenti;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import net.sf.beanlib.provider.replicator.BeanReplicator;



public class PagamentoSpontaneoFormImpl extends BaseForm implements IPagamentoSpontaneoForm{

	private String tipoTributo;
	private String descTipoTributo;
	private String codEnte;
	private String descEnte;
	private String codTributo;
	private String descTributo;
	private String idPagamento;
	private String notePagamento;
	private String predeterminabile;
	private String iniziativa;
	private String pagamentoFW;
	
	
	public String getPagamentoFW() {
		return pagamentoFW;
	}

	public void setPagamentoFW(String pagamentoFW) {
		this.pagamentoFW = pagamentoFW;
	}

	public String getIniziativa() {
		return iniziativa;
	}

	public void setIniziativa(String iniziativa) {
		this.iniziativa = iniziativa;
	}

	public String getPredeterminabile() {
		return predeterminabile;
	}

	public void setPredeterminabile(String predeterminabile) {
		this.predeterminabile = predeterminabile;
	}

	private String command;
	
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		return super.validate(mapping, request);
	}

	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}


	public String getCodEnte() {
          return this.codEnte;		
	}


	public String getCodTributo() {
        return this.codTributo;
	}

	public String getDescEnte() {
	     return this.descEnte;
	}

	@Override
	public String getDescTributo() {
          return this.descTributo;
	}

	@Override
	public String getDescTipoTributo() {
          return this.descTipoTributo;
	}


	@Override
	public String getTipoTributo() {
         return this.tipoTributo;
	}

	
	
	
	public String getIdPagamento() {
		return idPagamento;
	}

	public String getNotePagamento() {
		return notePagamento;
	}

	public void setCodEnte(String codEnte) {
        this.codEnte=codEnte;		
	}

	public void setCodTributo(String codTributo) {
		this.codTributo=codTributo;
	}

	public void setDescEnte(String descEnte) {
       this.descEnte = descEnte;		
	}

	public void setDescTributo(String descTributo) {
        this.descTributo=descTributo;
	}

	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo=tipoTributo;
		
	}

	public void setDescTipoTributo(String descTipoTributo) {
        this.descTipoTributo=descTipoTributo;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	
	

	
}
