//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.0/xslt/JavaClass.xsl

package it.nch.fwk.fo.web.action;


import it.nch.fwk.fo.common.constants.CommonConstants;
import it.nch.fwk.fo.action.FrameworkAction;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.criteria.DTOCriteria;
import it.nch.fwk.fo.exceptions.FrontEndException;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
//import it.nch.fwk.fo.pojo.Tbactwrk;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.resources.util.CommonContainerForm;
import it.nch.fwk.fo.web.session.WebSession;
import it.nch.fwk.fo.web.util.CachingSingleton;
import it.nch.fwk.fo.web.util.text.UtilityActWrk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

/**
 * MyEclipse Struts Creation date: 11-29-2005
 *
 * XDoclet definition:
 *
 * @struts.action
 */
public abstract class InfoAction extends CorporateAction 
{
	public final static int DISTINTA_RESULTS_PER_PAGE = 10;
	// --------------------------------------------------------- Methods

	/**
	 * Method init
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {

		actionForm.reset(actionMapping, httpServletRequest);

		return actionMapping.findForward("init");
	}

	public boolean initializeSingleAction(HttpServletRequest httpServletRequest) {
		return false;

	}

	public boolean finalizeSingleAction(HttpServletRequest httpServletRequest) {
		return false;
	}

	/**
	 * Method initializeSingleAction
	 *
	 * @param httpServletRequest
	 * @return boolean
	 */
	public boolean isPaginationExpired(HttpServletRequest httpServletRequest) {

		WebSession webSession = getWebSession(httpServletRequest);
		Tracer.info("InfoAction","isPaginationExpired","webSession=" + webSession,null);
		DTOCollection dtoCollection = webSession.getCollectionDTO(this
				.getClass().getName());
		// DTOCollection DTOCollection = webSession.getCollectionDTO();
		boolean returnValue = false;
		// se la collection associata a questa action è scaduta allora ritorno
		// false
		if ((dtoCollection  == null )   || dtoCollection.isExpired())
		{
			returnValue = true;
			dtoCollection = null;
		}
		webSession.setCollectionDTO(this.getClass().getName(), dtoCollection);
			
		return returnValue;
	
	}

	/**
	 * Method finalizeSingleAction
	 *
	 * @param httpServletRequest
	 * @param collectionDTO
	 */
	public void updatePagination(HttpServletRequest httpServletRequest,
			DTOCollection dtoCollection) {
		WebSession webSession = getWebSession(httpServletRequest);
		// WebSession.setCollectionDTO(collectionDTO);
		webSession.setCollectionDTO(this.getClass().getName(), dtoCollection);
	}
	
	protected void resetServiceCollections(HttpServletRequest httpServletRequest)
	{
		HttpSession session=httpServletRequest.getSession();
		ArrayList elements=(ArrayList)session.getAttribute("elements");
		if(elements!=null)
		{
			elements.trimToSize();
			for(int i=0;i<elements.size();i++)
				session.removeAttribute((String)elements.get(i));
			session.removeAttribute("elements");
		}
		Tracer.info("InfoAction","resetCollections","Collection di servizio cancellate",null);
	}
	
	protected void resetSession(HttpServletRequest httpServletRequest)
	{
		Tracer.info("InfoAction","resetSession","Inizio",null);
		HttpSession session=httpServletRequest.getSession();

		Enumeration enumeration = session.getAttributeNames();
		
		String attribute = null;
		while(enumeration.hasMoreElements()){
			attribute = (String) enumeration.nextElement();
			if(attribute.startsWith(FrameworkAction.STRUTS_PROPERTY) || attribute.equals(FrameworkAction.WEB_SESSION_PROPERTY))
				continue;
			session.removeAttribute(attribute);
		}
		Tracer.info("InfoAction","resetSession","Fine",null);
	}
	
	protected void addServiceCollection(HttpServletRequest httpServletRequest, String name, Object element)
	{
		HttpSession session=httpServletRequest.getSession();
		ArrayList elements=(ArrayList)session.getAttribute("elements");
		if(elements==null)
		{
			elements = new ArrayList(); 
		}

	    elements.add(name);
		httpServletRequest.getSession().setAttribute(name,element);
	    httpServletRequest.getSession().setAttribute("elements",elements);
	}

	public void resetPagination(HttpServletRequest httpServletRequest) {
		WebSession webSession = getWebSession(httpServletRequest);
		// WebSession.setCollectionDTO(collectionDTO);
		webSession.resetCollectionDTO (this.getClass().getName());
	}

	// public ActionForward execute(ActionMapping actionMapping,
	// ActionForm actionForm, HttpServletRequest httpServletRequest,
	// HttpServletResponse httpServletResponse) throws Exception {
	//
	// if (initializeSingleAction(httpServletRequest)) {
	// // /*
	// // UtilitySupplier utilitySupplier = new UtilitySupplier();
	// // CollectionDTO collectionDTO = null;
	// //
	// // try {
	// // collectionDTO = utilitySupplier.getBonifici();
	// // } catch (GenericException e) {
	// // e.printStackTrace();
	// // throw new Exception(e);
	// // }
	// //
	// // setCollectionDTO(httpServletRequest, collectionDTO);
	// // */
	//
	// }
	// ActionForward actFwd = super.execute(actionMapping, actionForm,
	// httpServletRequest, httpServletResponse);
	//
	// finalizeSingleAction(httpServletRequest);
	//
	// httpServletRequest.setAttribute("list", "true");
	// return actFwd;
	// }

	/**
	 * Method finalizeSingleAction
	 *
	 * @param httpServletRequest
	 * @return boolean
	 */
	// public boolean finalizeSingleAction(HttpServletRequest
	// httpServletRequest) {
	//
	// WebSession webSession = getWebSession(httpServletRequest);
	// CollectionDTO collectionDTO = webSession.getCollectionDTO();
	// boolean returnValue = false;
	//
	// if (collectionDTO != null && !collectionDTO.isExpired()) {
	// httpServletRequest.getSession().setAttribute(
	// InterfaceUtil.WEB_SESSION, webSession);
	// returnValue = true;
	// }
	//
	// return returnValue;
	// }
	/*
	public static DTO getAW(DTO dto, String idQuery, DTOCriteria criteria,FrontEndContext fec)throws Exception {
		
		//DTO dto = DTOFactory.getInstance().getDTO("Actionwrk");
		/*
		Tbactwrk pojo = new Tbactwrk();
		
		pojo.setActionId(idQuery);
		dto.getActionWorker().setActwrkPojo(pojo);		
		CachingSingleton cs = CachingSingleton.getInstance();
		dto.setCriteria(criteria);
		
		return cs.getActionWorker(fec, dto);
		
	}
	*/
	/*
	public static DTO getAW(DTO dto, String idQuery, FrontEndContext fec)throws Exception {
		
		//DTO dto = DTOFactory.getInstance().getDTO("Actionwrk");
		
		Tbactwrk pojo = new Tbactwrk();
		
		pojo.setActionId(idQuery);
		dto.getActionWorker().setActwrkPojo(pojo);		
		CachingSingleton cs = CachingSingleton.getInstance();
		dto.setCriteria(null);
		
		return cs.getActionWorker(fec, dto);
		
	}
	*/
	/* protected PagingCriteria getPagingCriteriaFilled(CommonContainerForm container)
	{
		PagingCriteria pagingCriteria = new PagingCriteria();
		pagingCriteria.setResultsPerPage(FrontEndCostant.DISTINTA_RESULTS_PER_PAGE);
		pagingCriteria.setDirection(container.getPagingDirection());
		pagingCriteria.setRecordPosition(container.getPagingRecordPosition());
		
		return pagingCriteria;
	} */
	
	protected PagingCriteria getPagingCriteriaFilled(CommonContainerForm container)
	{
		return getPagingCriteriaFilled(container, DISTINTA_RESULTS_PER_PAGE);
	}

	protected PagingCriteria getPagingCriteriaFilled(CommonContainerForm container, int resultPerPage)
	{
		PagingCriteria pagingCriteria = new PagingCriteria();
		if (container.getPaginationSmart() != null && container.getPaginationSmart().trim().equals("true")){
			pagingCriteria.setResultsPerPage(resultPerPage);
			pagingCriteria.setDirection(container.getPagingDirection());
			pagingCriteria.setNumCurrentPage(container.getNumCurrentPage());
			pagingCriteria.setNumNextPage(container.getNumNextPage());
			pagingCriteria.setPaginationSmart("true");
		}else{
			pagingCriteria.setResultsPerPage(resultPerPage);
			pagingCriteria.setDirection(container.getPagingDirection());
			pagingCriteria.setRecordPosition(container.getPagingRecordPosition());
			pagingCriteria.setPaginationSmart("false");
		}
		return pagingCriteria;
	}


	/*protected void setPaginationToContainerForm(CommonContainerForm container, DTOCollection dtoCol)
	{
		container.setPagingTotPages(dtoCol.getPagingData().getNumTotalPages());
		container.setPagingTotRecords(dtoCol.getPagingData().getNumTotalRecords());			
		container.setPagingNumPagesNext(dtoCol.getPagingData().getNumPagesNext());
		container.setPagingNumRecordsNext(dtoCol.getPagingData().getNumRecordsNext());
		container.setPagingNumPagesPrev(dtoCol.getPagingData().getNumPagesPrev());
		container.setPagingNumRecordsPrev(dtoCol.getPagingData().getNumRecordsPrev());
		container.setPagingRecordPosition(dtoCol.getPagingData().getRecordPosition());
		container.setPageNumbers(dtoCol.getPagingData().getPageNumbers());
		container.setPagingLastRecordPosition((dtoCol.getPagingData().getNumTotalPages() - 1)* FrontEndCostant.DISTINTA_RESULTS_PER_PAGE);

		if(dtoCol.getPagingData().getNumPagesPrev()==0)
			container.setPagingSavingRecordPosition(-1);
		else
			container.setPagingSavingRecordPosition(dtoCol.getPagingData().getRecordPosition() - FrontEndCostant.DISTINTA_RESULTS_PER_PAGE);
	}*/
	
	protected void setPaginationToContainerForm(CommonContainerForm container, DTOCollection dtoCol)
	{
		setPaginationToContainerForm(container, dtoCol, DISTINTA_RESULTS_PER_PAGE);
	}
	
	protected void setPaginationToContainerForm(CommonContainerForm container, DTOCollection dtoCol, int resultPerPage)
	{
			this.setPaginationToContainerForm(container,dtoCol.getPagingData(), resultPerPage);
	}

	protected void setPaginationToContainerForm(CommonContainerForm container,PagingData pagingData){
		this.setPaginationToContainerForm(container,pagingData, DISTINTA_RESULTS_PER_PAGE);
	}

	protected void setPaginationToContainerForm(CommonContainerForm container,PagingData pagingData, int resultPerPage)
	{
		if (pagingData == null)
			return;
		container.setPagingTotPages(pagingData.getNumTotalPages());
		container.setPagingTotRecords(pagingData.getNumTotalRecords());			
		container.setPagingNumPagesNext(pagingData.getNumPagesNext());
		container.setPagingNumRecordsNext(pagingData.getNumRecordsNext());
		container.setPagingNumPagesPrev(pagingData.getNumPagesPrev());
		container.setPagingNumRecordsPrev(pagingData.getNumRecordsPrev());
		container.setPagingRecordPosition(pagingData.getRecordPosition());
		container.setPageNumbers(pagingData.getPageNumbers());
		container.setPagingLastRecordPosition((pagingData.getNumTotalPages() - 1)* resultPerPage);
		container.setNumCurrentPage(pagingData.getPageNumber());
		
		if(container.getPagingNumPagesNext() != 0)
			container.setPagingCurrentPageRecords(resultPerPage);
		else {
			if (container.getPagingTotRecords() % resultPerPage==0)
				container.setPagingCurrentPageRecords(resultPerPage);
			else
				container.setPagingCurrentPageRecords(container.getPagingTotRecords() % resultPerPage);
		}	

		container.setLastResultOfPage(container.getPagingTotRecords()-container.getPagingNumRecordsNext());		
		
		
		if(pagingData.getNumPagesPrev()==0)
			container.setPagingSavingRecordPosition(-1);
		else
			container.setPagingSavingRecordPosition(pagingData.getRecordPosition() - resultPerPage);
	}
	

	/*protected void setPaginationToContainerForm(CommonContainerForm container, DTOCollection dtoCol)
	{
		container.setPagingTotPages(dtoCol.getPagingData().getNumTotalPages());
		container.setPagingTotRecords(dtoCol.getPagingData().getNumTotalRecords());			
		container.setPagingNumPagesNext(dtoCol.getPagingData().getNumPagesNext());
		container.setPagingNumRecordsNext(dtoCol.getPagingData().getNumRecordsNext());
		container.setPagingNumPagesPrev(dtoCol.getPagingData().getNumPagesPrev());
		container.setPagingNumRecordsPrev(dtoCol.getPagingData().getNumRecordsPrev());
		container.setPagingRecordPosition(dtoCol.getPagingData().getRecordPosition());
		container.setPageNumbers(dtoCol.getPagingData().getPageNumbers());
		container.setPagingLastRecordPosition((dtoCol.getPagingData().getNumTotalPages() - 1)* FrontEndCostant.DISTINTA_RESULTS_PER_PAGE);

		if(dtoCol.getPagingData().getNumPagesPrev()==0)
			container.setPagingSavingRecordPosition(-1);
		else
			container.setPagingSavingRecordPosition(dtoCol.getPagingData().getRecordPosition() - FrontEndCostant.DISTINTA_RESULTS_PER_PAGE);
	}*/
	
	
	
	
	protected HashMap popolaCausaliEsito(HttpServletRequest httpServletRequest)
	{		
	
		HashMap causaliEsito = new HashMap();
		// TODO
	    Tracer.warn(getClass().toString(),"loadCausaliEsito","attualmente cablato, da implementare, servizio: " + getFrontEndContext(httpServletRequest).getServiceCode(),null);
	    if ("SE_118".equals(getFrontEndContext(httpServletRequest).getServiceCode()))
    	{
	    	//Su richiesta di Talotta si carica la collection di causali esiti rid recuperandola da loadCausaliEsito dove è beceramente cablata
	    	causaliEsito.put("50001", "Disposizione stornata per conto estinto"); 
	    	causaliEsito.put("50003", "Disposizione stornata per insufficienza fondi"); 
	    	causaliEsito.put("50004", "Disposizione stornata per contestazione del debitore"); 
	    	causaliEsito.put("50006", "Disposizione resa al carico");
	    	causaliEsito.put("50007", "Disposizione stornata per motivi tecnici mancato allineamento archivi");
	    	causaliEsito.put("50008", "Disposizione richiamata");
	    	causaliEsito.put("50009", "Disposizione stornata per motivi generici");
	    	causaliEsito.put("50010", "Disposizione pagata");
	    	
    	}		    	
		return causaliEsito;		    
		
	}
	
	protected LabelValueBean getLabelValueBean(HttpServletRequest httpServletRequest, String collectionName,  String codice , String descrizione)
	{
		String key = collectionName + "." +getFrontEndContext(httpServletRequest).getServiceCode() + "." + codice;
		String label = getMessagesResource(httpServletRequest, key, descrizione);
		//Tracer.info(this.getClass().getName(),"getLabelValueBean","key: " + key,null);
		//Tracer.info(this.getClass().getName(),"getLabelValueBean","descrizione: " + descrizione,null);
		//Tracer.info(this.getClass().getName(),"getLabelValueBean","label: " + label,null);
		
		return new LabelValueBean(label,codice);
	}
}