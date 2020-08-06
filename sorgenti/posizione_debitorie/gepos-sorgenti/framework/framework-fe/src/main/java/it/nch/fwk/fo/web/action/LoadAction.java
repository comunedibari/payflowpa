package it.nch.fwk.fo.web.action;

import it.nch.fwk.fo.common.constants.CommonConstants;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.criteria.DTOCriteria;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

/**
 * XXX da cancellare se non utilizzata!!!
 */
public abstract class LoadAction extends CorporateAction {
//	/**
//	 * init  
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	public ActionForward init(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//
//		Tracer.debug(this.getClass().getName(), "init", "BEGIN");
//		actionForm.reset(actionMapping, httpServletRequest);
//		Tracer.debug(this.getClass().getName(), "init", "END");
//		return actionMapping.findForward("init");
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	public static DTO getAW(DTO dto, String idQuery, DTOCriteria criteria, FrontEndContext fec) throws Exception {
//
//		Tracer.debug(LoadAction.class.getName(), "getAW", "BEGIN");
//		DTO myDto = null;
//		/*
//		 Tbactwrk pojo = new Tbactwrk();
//		 pojo.setActionId(idQuery);
//		 dto.getActionWorker().setActwrkPojo(pojo);		
//		 CachingSingleton cs = CachingSingleton.getInstance();
//		 dto.setCriteria(criteria);
//		 myDto = cs.getActionWorker(fec, dto);
//		 */
//		Tracer.debug(LoadAction.class.getName(), "getAW", "END");
//		return myDto;
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	public static DTO getAW(DTO dto, String idQuery, FrontEndContext fec) throws Exception {
//		Tracer.debug(LoadAction.class.getName(), "getAW", "calling getAW whit null criteria");
//		return getAW(dto, idQuery, null, fec);
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	protected void addServiceCollection(HttpServletRequest httpServletRequest, String name, Object element) {
//		//
//		Tracer.debug(this.getClass().getName(), "addServiceCollection", "Inizio");
//		//
//		HttpSession session = httpServletRequest.getSession();
//		ArrayList elements = (ArrayList) session.getAttribute(CommonConstants.ELEMENTS);
//		if (elements == null) {
//			elements = new ArrayList();
//		}
//		Tracer.debug(this.getClass().getName(), "addServiceCollection", " adding " + name + " to elements");
//		elements.add(name);
//		Tracer.debug(this.getClass().getName(), "addServiceCollection", " setting " + element + " to " + name);
//		//
//		if (httpServletRequest.getSession().getAttribute(name) == null) {
//			httpServletRequest.getSession().setAttribute(name, element);
//			httpServletRequest.getSession().setAttribute(CommonConstants.ELEMENTS, elements);
//		}
//		// NUOVA IMPLEMENTAZIONE: PROVO A METTERE LE INFO NELLA REQUEST
//		/*
//		 ArrayList elements=(ArrayList) httpServletRequest.getAttribute(FrontEndCostant.ELEMENTS);
//		 if(elements == null){
//		 Tracer.debug(this.getClass().getName(),"addServiceCollection","elements is null: creating new elements", null);
//		 elements = new ArrayList(); 
//		 }
//		 else{
//		 Tracer.debug(this.getClass().getName(),"addServiceCollection","elements already present", null);
//		 }
//		 Tracer.debug(this.getClass().getName(),"addServiceCollection","adding " + name + " to elements",null);
//		 elements.add(name);
//		 Tracer.debug(this.getClass().getName(),"addServiceCollection","setting " + element + " to " + name,null);
//		 httpServletRequest.setAttribute(name, element);
//		 httpServletRequest.setAttribute(FrontEndCostant.ELEMENTS,elements);
//		 */
//		Tracer.debug(this.getClass().getName(), "addServiceCollection", "Fine");
//		//
//	}//end addServiceCollection
//
//	//
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 * metodo per costruire un oggetto di una collezione da visualizzare, in caso dipenda dal servizio
//	 */
//	protected LabelValueBean getLabelValueBean(HttpServletRequest httpServletRequest, String collectionName, String codice, String descrizione) {
//		String key = collectionName + "." + getFrontEndContext(httpServletRequest).getServiceCode() + "." + codice;
//		String label = getMessagesResource(httpServletRequest, key, descrizione);
//		Tracer.info(this.getClass().getName(), "getLabelValueBean", "key: " + key, null);
//		Tracer.info(this.getClass().getName(), "getLabelValueBean", "descrizione: " + descrizione, null);
//		Tracer.info(this.getClass().getName(), "getLabelValueBean", "label: " + label, null);
//
//		return new LabelValueBean(label, codice);
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 * metodo per costruire un oggetto di una collezione da visualizzare, in caso NON dipenda dal servizio
//	 */
//	protected LabelValueBean getLabelValueBeanCross(HttpServletRequest httpServletRequest, String collectionName, String codice, String descrizione) {
//		String key = collectionName + "." + codice;
//		String label = getMessagesResource(httpServletRequest, key, descrizione);
//		Tracer.info(this.getClass().getName(), "getLabelValueBeanCross", "key: " + key, null);
//		Tracer.info(this.getClass().getName(), "getLabelValueBeanCross", "descrizione: " + descrizione, null);
//		Tracer.info(this.getClass().getName(), "getLabelValueBeanCross", "label: " + label, null);
//
//		return new LabelValueBean(label, codice);
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	protected boolean isObjectInSession(HttpServletRequest httpServletRequest, String attribute) {
//
//		Tracer.debug(this.getClass().getName(), "isObjectInSession", "BEGIN", null);
//		boolean isPresent = httpServletRequest.getSession(false).getAttribute(attribute) != null ? true : false;
//		Tracer.debug(this.getClass().getName(), "isObjectInSession", "is attribute [" + attribute + "] present in session ? " + isPresent, null);
//		Tracer.debug(this.getClass().getName(), "isObjectInSession", "END", null);
//		return isPresent;
//	}

}