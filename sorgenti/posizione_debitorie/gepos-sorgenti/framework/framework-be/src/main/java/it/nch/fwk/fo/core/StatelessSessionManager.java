/*
 * Created on 9-feb-2006
 */
package it.nch.fwk.fo.core;

import it.nch.fwk.fo.das.exception.DasCheckedException;
import it.nch.fwk.fo.das.exception.DasUncheckedException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;

/**
 * Definisce una serie di operazioni verso la persistenza che si appoggiano su una "sessione di persistenza"
 * implementata attraverso il pattern "session per request" o "session per transaction"
 */
public interface StatelessSessionManager extends Serializable {
		
	//Operazioni esplicite sulla chache
	
	public Object getFromChache(String key) throws DasUncheckedException;	

	public int getNBEntries() throws DasUncheckedException;	

	public void putInCache(String key, Object content) throws DasUncheckedException;	

	public void removeEntry(String key) throws DasUncheckedException;	
	
	/**
	 * standard Hibernate createQuery statement
	 * @param queryString
	 * @return org.hibernate.Query
	 * @throws DasUncheckedException
	 */
	public Query createHibernateQuery(String queryString) throws DasUncheckedException;
	
	/**
	 * standard Hibernate Criteria
	 * @param cls
	 * @return org.hibernate.Criteria
	 * @throws DasUncheckedException
	 */
	public Criteria createHibernateCriteria(Class cls) throws DasUncheckedException;
	
	public Collection createQuery(String queryString) throws DasUncheckedException;
	
	public Collection createQuery(String queryString, int firstResult, int maxResults) throws DasUncheckedException;
	
	public Query retrieveQuery(String queryString) throws DasUncheckedException;

	public Query retrieveQuery(String queryString, QueryParams queryParams) throws DasUncheckedException;
	
	public void log(Query query);	
	   
	/**
	 * @deprecated 
	 * sarà disponibile un metodo createSqlQuery che ritorna una Collection
	 *
	 * Esegue una query SQL Diretta. I livelli di cache di primo e
	 * secondo livello di hibernate non sono coinvolti 
	 * @param queryString
	 * @return
	 * @throws DasCheckedException
	 */
	public Iterator createSQLQuery(String queryString)throws RemoteException;
	
	public Collection createSQLQuery(String queryString, HashMap params);
	
	public Query getSQLQuery(String queryString, HashMap params);
	
	public void delete(Object object) throws DasUncheckedException;		
	
	//Operazioni puntuali su oggetti 
	
	public Object get(Class entityClass, Serializable id)throws DasUncheckedException;
    	
	public Object load(Class object, Serializable id) throws DasUncheckedException;
	
	public Object merge(Object object) throws DasUncheckedException;	
	
	public void refresh(Object object) throws DasUncheckedException;
	
	/**
	 * Esegue l'update dell'oggetto specificato
	 * @param object
	 * @throws DasUncheckedException
	 */
	public void update(Object object) throws DasUncheckedException;
	
	/**
	 * @deprecated
	 */
	public Serializable save(Object object, boolean b) throws DasUncheckedException;
	
	public Serializable save(Object object) throws DasUncheckedException;
	
	public void saveOrUpdate(Object object) throws DasUncheckedException;
	
	/*
	 * @deprecated
	 * USARE public Serializable save(Object object, boolean b)
	 * x avere un tipo di ritorno compatibile con save di Hibernate 
	 * dove boolean b è (per ora) solo un banfo 
	 * e presto verrà tolto dalla signature del metodo... 
	 *
	public boolean save(Object object) throws DasUncheckedException; */
	
	public void evict(Object object) throws DasUncheckedException;
	
	public void flush() throws DasUncheckedException;
	
	public void persist(Object object) throws DasUncheckedException;
	
	public void replicate(Object object, ReplicationMode replicationMode) throws DasUncheckedException;
				
	/**
	 * Applica un filtro ad una collezione di oggetti secondo le condizioni specificate in queryString
	 * @deprecated
	 * @param collection
	 * @param queryString
	 * @return
	 */
	public Query createFilter(Object collection, String queryString)throws DasUncheckedException;
	
	/**
	 * Restituisce l'oggetto Query associato alla query mappata nel file xml di configurazione dell'applicazione
	 * @deprecated
	 * @param queryName
	 * @return
	 */
	public Query getNamedQuery(String queryName) throws DasUncheckedException;	
	
	/**
	 * Restituisce l'oggetto Query associato alla query mappata nel file xml di configurazione dell'applicazione
	 * @param queryName
	 * @return
	 */
	public Collection getNamedQuery(String queryName,HashMap params) throws DasUncheckedException;		
	
	/**
	 * Restituisce l'oggetto Query con i parametri firstResult e maxResults
	 * @param queryName
	 * @return
	 */
	public Collection getNamedQuery(String queryName, HashMap params, int firstResult, int maxResults) throws DasUncheckedException;
	
	public int count(String hqlQuery);
	
	public int count(Query  hqlQuery);

	public Connection getJdbcConnection();
	
	public void disconnect();
}
