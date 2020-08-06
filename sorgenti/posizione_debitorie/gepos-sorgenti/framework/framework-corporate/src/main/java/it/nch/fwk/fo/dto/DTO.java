/*
 * Creato il 22-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

//import it.nch.fwk.fo.actionworker.ActionWorker;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.dto.criteria.DTOCriteria;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;

/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface DTO<P extends Pojo, BO extends CommonBusinessObject, VO> extends FrameworkDTOInterface {


	/**
	 * @deprecated
	 * Still used just in Login and BE to BE services, where the javabean copy
	 * mechanism is not needed. For standard services use @link getBusinessObject() instead
	 */
	public P getPojo();

	/**
	 * @deprecated
	 * @see @link getPojo()
	 */
	public P setPojo(P pojo);

	public BO getBusinessObject();

	public void setBusinessObject(BO businessObject);

	public DTOCriteria getCriteria();

	public void setCriteria(DTOCriteria criteria);

	public boolean hasInfo();

	public boolean hasWarning();

	public boolean hasError();

	public PagingCriteria getPagingCriteria();

	public void setPagingCriteria(PagingCriteria pagingCriteria);

	public PagingData getPagingData();

	public void setPagingData(PagingData pagingData);

	public void  setVO(VO valueObject);

	public VO  getVO();

}
