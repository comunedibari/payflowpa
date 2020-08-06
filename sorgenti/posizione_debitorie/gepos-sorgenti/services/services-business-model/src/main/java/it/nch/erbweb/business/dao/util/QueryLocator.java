/*
 * Created on Jun 18, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.business.dao.util;

import org.hibernate.SQLQuery;

/**
 * @author Battaglil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface QueryLocator {
	
	/**
	 * ritorna la query in formato SQL
	 * 
	 * @param nomeQuery
	 * @return
	 */
	public String getQuery(String nomeQuery);
	
	/**
	 * 
	 * aggiunge alla query costruita con SQL puro la lista delle colonne 
	 * ed i rispettivi tipi di Output 
	 * 
	 * @param nomeQuery
	 * @param oggettoQuery
	 * @return
	 */
	public SQLQuery fillQuery(String nomeQuery, SQLQuery oggettoQuery);
	
	/**
	 * ritorna un array di stringhe contenenti i nomi degli alias di ritorno
	 * 
	 * @param nomeQuery
	 * @return
	 */
	public String[] getReturnAliases(String nomeQuery);

	/**
	 * di default 50
	 * 
	 * @return
	 */
	public String getFetchSize();
	
	/**
	 * ritorna schema DB
	 * 
	 * @return
	 */
	public String getDbOwner();

}
