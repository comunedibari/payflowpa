/*
 * Created on 18-lug-08
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.business.dao.util;

import java.io.Serializable;
import java.util.List;

/**
 * @author simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface BeanTransformer extends Serializable {
	
	public abstract Object transformTuple(Object aobj[]);
	public abstract List transformList(List list);

}
