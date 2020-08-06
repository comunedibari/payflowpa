/*
 * Created on 1-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.action.cm;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CMNullAdapter implements CMActionAdapter {

    public void createCMContext(HttpServletRequest request, ActionMapping mapping) {
        // TODO - NOP
    }

    public void setActionForward(HttpServletRequest request, ActionMapping mapping, ActionForward forward) {
        // TODO - NOP
    }

}
