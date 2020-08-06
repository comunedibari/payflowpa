/*
 * Created on 18-lug-08
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.business.dao.util;

import java.util.List;

import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * @author simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AliasToBeanTransformer implements BeanTransformer {
	
	private AliasToBeanResultTransformer rt;
	private String[] aliases;

	public AliasToBeanTransformer(Class outClass, String[] aliases) {
		super();
		this.rt = new AliasToBeanResultTransformer(outClass);
		this.aliases = aliases; 
	}

	public Object transformTuple(Object[] obj) {
		return rt.transformTuple(obj, aliases);
	}

	public List transformList(List list) {
		return rt.transformList(list);
	}

}
