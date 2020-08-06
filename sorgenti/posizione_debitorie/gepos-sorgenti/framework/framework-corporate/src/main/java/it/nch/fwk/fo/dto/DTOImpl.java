/*
 * Creato il 1-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.dto.criteria.DTOCriteria;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;

/**
 * @author EE10598
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class DTOImpl extends FrameworkDTO implements DTO {

	/**
	 * variabili
	 */
	protected Pojo          pojo = null;
	protected CommonBusinessObject businessObject = null;
	protected DTOCriteria   criteria = null;
	protected PagingCriteria pagingCriteria;
	protected PagingData pagingData;
	protected Object        valueObject = null;

	/**
	 * costruttori
	 */
	public DTOImpl() {}

	public DTOImpl(CommonBusinessObject businessObject) {
		this.businessObject = businessObject;
	}

	public DTOImpl(Pojo pojo) {
		this.pojo = pojo;
	}

	public DTOImpl(boolean newInfoList, boolean newCriteria) {
		if (newInfoList)
			infoList = new DTOInfoList();
		if (newCriteria)
			criteria = new DTOCriteria();
	}



	/*public void setCriteria(DTOCriteria criteria) {
		this.criteria = criteria;
	}*/

	public Pojo getPojo() {
		return pojo;
	}

	public Pojo setPojo(Pojo pojo) {
		this.pojo = pojo;
		return pojo;
	}

	private boolean searchSeverity() {
		if (infoList==null || infoList.size() == 0)
			return false;
		else
		{
			for (int i = 0; i < infoList.size(); i++) {
				DTOInfo di = infoList.getInfo(i);
				if (di.getSeverity() == DTOInfoInterface.SEVERITY_ERROR || di.getSeverity() == DTOInfoInterface.SEVERITY_GENERIC)
					return true;
			}
			return false;
		}
	}

	/**
	 * @deprecated In alternativa sarà fornito un metodo searchSeverity
	 * @param infoType
	 * @return
	 */
	private boolean searchInfoType(short infoType) {
		if (infoList.size() == 0)
			return false;
		else
		{
			for (int i = 0; i < infoList.size(); i++) {
				DTOInfo di = infoList.getInfo(i);
				if (di.getInfoType() == infoType)
					return true;
			}
			return false;
		}
	}

	public boolean hasInfo() {
		return searchInfoType(DTOInfo.INFO);
	}

	public boolean hasWarning() {
		return searchInfoType(DTOInfo.WARNING);
	}

	public boolean hasError() {
		return searchSeverity();
	}

	/* (non Javadoc)
	 * @see it.nch.fwk.fo.dto.FrameworkDTOInterface#getDTOInfo()
	 */
	public DTOInfoInterface getDTOInfo() {
		// TODO Stub di metodo generato automaticamente
		return null;
	}

	/* (non Javadoc)
	 * @see it.nch.fwk.fo.dto.DTO#setCriteria(it.nch.fwk.fo.dto.criteria.DTOCriteria)
	 */
	public void setCriteria(DTOCriteria criteria) {
		this.criteria=criteria;

	}


	public PagingCriteria getPagingCriteria()
	{
		return pagingCriteria;
	}

	public void setPagingCriteria(PagingCriteria pagingCriteria)
	{
		this.pagingCriteria = pagingCriteria;
	}

	public PagingData getPagingData()
	{
		return pagingData;
	}

	public void setPagingData(PagingData pagingData)
	{
		this.pagingData = pagingData;
	}
	/**
	 * @return Restituisce il valore businessObject.
	 */
	public CommonBusinessObject getBusinessObject() {
		return businessObject;
	}
	/**
	 * @param businessObject Il valore businessObject da impostare.
	 */
	public void setBusinessObject(CommonBusinessObject businessObject) {
		this.businessObject = businessObject;
	}

	/* (non Javadoc)
	 * @see it.nch.fwk.fo.dto.DTO#getCriteria()
	 */
	public DTOCriteria getCriteria() {
		// TODO Stub di metodo generato automaticamente
		return this.criteria;
	}

	public Object getVO(){
		return valueObject;
	}

	public void setVO(Object valueObject) {
		this.valueObject = valueObject;
	}

	public String toString(){
		StringBuffer descrizione = new StringBuffer("****DTOImpl****\n");
		String pojoString = (getPojo() == null ? "" : getPojo().toString());
		descrizione.append("Pojo: ").append(pojoString).append("\n");
		String boString = (getBusinessObject() == null ? "" : getBusinessObject().toString());
		descrizione.append("BusinesObject: ").append(boString).append("\n");
		String voString = (getVO() == null ? "" : getVO().toString());
		descrizione.append("ValueObject: ").append(voString).append("\n");
		descrizione.append("***************\n");
		return descrizione.toString();

	}


}


