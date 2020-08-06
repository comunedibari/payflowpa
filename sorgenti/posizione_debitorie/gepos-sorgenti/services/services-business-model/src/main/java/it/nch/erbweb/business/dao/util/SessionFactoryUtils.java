/*
 * Created on Sep 5, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.business.dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Matteo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SessionFactoryUtils {

	public static Session getSession(SessionFactory sessionFactory) {
		
		return sessionFactory.getCurrentSession();
	}
}
