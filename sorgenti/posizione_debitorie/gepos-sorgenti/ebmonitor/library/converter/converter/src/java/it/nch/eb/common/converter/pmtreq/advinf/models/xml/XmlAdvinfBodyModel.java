/*
 * Created on Aug 1, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.eb.common.converter.pmtreq.advinf.models.xml;

import it.nch.eb.common.converter.pmtreq.advinf.models.AdvinfBodyModel;

/**
 * @author bastiap
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlAdvinfBodyModel extends AdvinfBodyModel {
	public boolean getShowAdvInstrs(){				
		
		return (getRecord06().getTxRefNb()!=null && !"".equals(getRecord06().getTxRefNb()));   

	}
	                 
	public boolean isShowAdvInstrs(){					 
		return getShowAdvInstrs();

	}

}
