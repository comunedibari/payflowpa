/*
 * Created on 16-nov-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package it.nch.fwk.fo.core;

import it.nch.fwk.fo.common.constants.FrameworkMessage;
import it.nch.fwk.fo.core.exception.ManageFWBackEndException;
import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.util.StopWatchLogger;
import it.nch.fwk.fo.util.Tracer;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.type.Type;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * @author ffratoni, cgalli
 * 
 * E' la classe che fornisce i servizi con il broker di persistenza di secondo livello di Hibernate
 * ovvero la sessione.
 * <b>Nella sua versione definitiva questa classe all'interno dei metodi non dovrà contenere nè gestione
 * di transazioni nè chiamate esplicite ai seguenti metodi delle sessione di hibernate:
 * -connect
 * -reconnect
 * -flush
 * Questi metodi devono essere utilizzati da classi con visibilità di unità di lavoro ossia classi di business
 * e SessionManagerHibernate può soltanto esportarli ma non utilizzarli direttamente.
 * <b>
 */
public class SessionManagerHibernate implements StatelessSessionManager{
	
	
	public transient SessionFactory sef;	
	public Session se;
	
	/* ******************************************************************** */
	/* ************************** COSTRUTTORI ***************************** */
	/* ******************************************************************** */
	
	public SessionManagerHibernate() {
		
		Tracer.info(this.getClass().getName(),"costruttore vuoto","",null);
	}

	public SessionManagerHibernate(SessionFactory sf) {
		Tracer.info(this.getClass().getName(),"costruttore","SessionFactory ["+sf+"]",null);
		this.sef = sf;
	}
	
	public void connect() 
	{

		throw new UnsupportedOperationException();
		
		//		try{
//			Tracer.info(this.getClass().getName(), "connect", "connecting session to database", null);
//			ensureHibernate();
//			if(!this.se.isConnected())
//				this.se.reconnect();
//			
//		} catch (HibernateException ex) {
//		    Tracer.error(this.getClass().getName(), "Connessione fallita", "", ex);
//		    new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
//		}

	}
	
	
	public PreparedStatement prepareStatement(String sqlQuery) {

		throw new UnsupportedOperationException();
		
		//		PreparedStatement preparedStatement = null;
//		try {
//			this.ensureCurrentHibernateSession();
//			preparedStatement =  this.se.connection().prepareStatement(sqlQuery);
//        } catch (HibernateException ex) {
//            Tracer.error(this.getClass().getName(), "PrepareStatament fallito  [" + sqlQuery + "]", "", ex);
//            new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
//        } catch (SQLException ex) {
//            Tracer.error(this.getClass().getName(), "PrepareStatament fallito [" + sqlQuery + "]", "", ex);
//            new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
//        }	
//        return preparedStatement;
	}	
	
	
	public CallableStatement callableStatement(String sqlQuery) {

		throw new UnsupportedOperationException();
		
		//		CallableStatement callableStatement = null;
//		try {
//			this.ensureCurrentHibernateSession();				
//			callableStatement =  this.se.connection().prepareCall(sqlQuery);
//	       } catch (HibernateException ex) {
//	            Tracer.error(this.getClass().getName(), "Esecuzione callableStatement fallito [" + sqlQuery + "]", "", ex);
//	            new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
//	       } catch (SQLException ex) {
//	            Tracer.error(this.getClass().getName(), "Esecuzione callableStatement fallito [" + sqlQuery + "]", "", ex);
//	            new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
//	       }
//	       return callableStatement;
	}
	
	public void disconnect()  {
		Tracer.info(this.getClass().getName(), "disconnect",
				"disconnecting session from database", null);
		try{
			if (this.se.isConnected())
				this.se.disconnect();
	    } catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione disconnect fallito ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
	    }    
	}
	
	public void clear() {
		Tracer.info(this.getClass().getName(), "clear", "clear session", null);
		try{
			ensureHibernate();
			this.se.clear();
	    } catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione clear fallito ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
	    }
	}
	
	public void flush() {
		if (Tracer.isInfoEnabled(getClass().getName()))
			Tracer.info(this.getClass().getName(), "flush", "flush session", null);
		try{	
			this.se.flush();
	    } catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione flush fallito ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
	    }
	}

	
	/* ******************************************************************** */
	/* ***************** IMPLEMENTO STATELESS SESSION MANAGER ************* */
	/* ******************************************************************** */

	public Connection getJdbcConnection(){

		throw new UnsupportedOperationException();
		
		//		Connection conn = null;
//		try{
//			Tracer.info(this.getClass().getName(), "getConnection", "get jbdc sql connection", null);
//			ensureHibernate();
//			conn = this.se.connection();
//		} catch (HibernateException ex) {
//		    Tracer.error(this.getClass().getName(), "Connessione fallita", "", ex);
//		    new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
//		}
//		return conn;
	}

	public void log(Query query){
		new HibernateQueryLogger().debugSQL(this.se.getSessionFactory(), query);
		
	}
	
	public Collection createQuery(String queryString)  
	{			
		return createQuery(queryString, -1, -1);
	}
	
	public Collection createQuery(String queryString, int firstResult, int maxResults)
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "createQuery", "",null);
			Tracer.debug(this.getClass().getName(), "createQuery", "queryString["+queryString+"]",null);
		}
		this.ensureCurrentHibernateSession();
				
		List list=null;
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"createQuery",queryString);
		try {			
			
			st.start();
			Query q = this.se.createQuery(queryString);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "createQuery", "q["+q+"]",null);
			if (firstResult > -1)
				q.setFirstResult(firstResult);
			if (maxResults > -1)
				q.setMaxResults(maxResults);
			list = q.list();	
			new HibernateQueryLogger().debugSQL(this.se.getSessionFactory(), q);
	     } catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione query fallita [" + queryString + "]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
	     }
	     finally{
	    	 st.stop();
	     }
	     return list;
	}
	
	public Query createHibernateQuery(String queryString){
		
		NchQueryImpl q  = null;
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"createHibernateQuery",queryString);
		this.ensureCurrentHibernateSession();
		
		try {		
			st.start();
			q = new NchQueryImpl(this.se.createQuery(queryString));
			//q = this.se.createQuery(queryString);	
			//new HibernateQueryLogger().debugSQL(this.se.getSessionFactory(), q);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero oggetto query fallito [" + queryString + "]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
		return q;
	}
	
	public Criteria createHibernateCriteria(Class cls) {			

		Criteria criteria = null;
		this.ensureCurrentHibernateSession();
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"createHibernateCriteria","");
		try {
			st.start();
			criteria =  se.createCriteria(cls);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero oggetto criteria fallito ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
		return criteria;
	}
	
	/**
	 * @deprecated 
	 * sarà disponibile un metodo createSqlQuery che ritorna una Collection
	 */
	public Iterator createSQLQuery(String queryString) 
	{
		//TODO correggere tipo di ritorno a Collection!!!
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "createSQLQuery", "", null);
			Tracer.debug(this.getClass().getName(), "createSQLQuery", "queryString["+queryString+"]", null);
		}
		this.ensureCurrentHibernateSession();
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"createSQLQuery",queryString);
		SQLQuery sqlq = null;
		try {
			st.start();
			sqlq = this.se.createSQLQuery(queryString);			
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero oggetto sqlQuery fallito [" + queryString + "]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
		return sqlq.iterate();
	}
	
	public Collection createSQLQuery(String queryString, HashMap params) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "createSQLQuery", "", null);
			Tracer.debug(this.getClass().getName(), "createSQLQuery", "queryString["+queryString+"] params["+params+"]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"createSQLQuery",queryString);
		this.ensureCurrentHibernateSession();
		List list=null;
		SQLQuery sqlQuery = null;;
		try {
			st.start();
			sqlQuery = this.se.createSQLQuery(queryString);
			Iterator it=params.keySet().iterator();
			Object currentKey;
			while(it.hasNext())
			{
				currentKey=it.next();
				sqlQuery.setParameter(currentKey.toString(),params.get(currentKey));
				
			}
			list = sqlQuery.list();
			
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero oggetto sqlQuery fallito [" + queryString + "]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
		return list;
	}
	
	public Query getSQLQuery(String queryString, HashMap params) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "getSQLQuery", "", null);
			Tracer.debug(this.getClass().getName(), "getSQLQuery", "queryString["+queryString+"] params["+params+"]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"getSQLQuery",queryString);
		this.ensureCurrentHibernateSession();
		
		SQLQuery sqlQuery = null;
		try {
			st.start();
			sqlQuery = this.se.createSQLQuery(queryString);
			Iterator it=params.keySet().iterator();
			Object currentKey;
			while(it.hasNext())
			{
				currentKey=it.next();
				sqlQuery.setParameter(currentKey.toString(),params.get(currentKey));
				
			}
			
			
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero oggetto sqlQuery fallito [" + queryString + "]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
		return sqlQuery;
	}
	
	public void delete(Object object) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "delete", "", null);
			Tracer.debug(this.getClass().getName(), "delete", "object[]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"delete","");
		this.ensureCurrentHibernateSession();
		
		try {
			st.start();
			se.delete(object);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione cancellazione  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	     }
	}
				
	public Object load(Class object, Serializable id) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "load (riceve Class)", "", null);
			Tracer.debug(this.getClass().getName(), "load (riceve Class)", "Class["+object+"] serializable["+id+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		Object result = null;
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"load","id="+id);
		try {		   
			st.start();
			result =  this.se.load(object, id);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione caricamento oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
		return result;
	}
	
	public void refresh(Object object) {
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"refresh","");
		try{
			st.start();
			this.se.refresh(object);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione refresh oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	     }
	}
	
	public Object merge(Object object)  {
		this.ensureCurrentHibernateSession();
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"merge","");
		Object result = null;
		try {
			st.start();
			result = this.se.merge(object);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione merge oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
		return result;
	}
	
	public void update(Object object) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "update", "", null);
			Tracer.debug(this.getClass().getName(), "update", "object[]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"update","");
		this.ensureCurrentHibernateSession();
		
		try {	
			st.start();
			this.se.update(object);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione update oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
	}
	
	/**
	 * @deprecated
	 */
	public Serializable save(Object object, boolean b) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "save", "", null);
			Tracer.debug(this.getClass().getName(), "save", "object[]", null);
		}
		
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"save","");
		Serializable s = null;
		this.ensureCurrentHibernateSession();
		try {
			st.start();
			
			s = this.se.save(object);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "save", "object "+object.getClass().getName()+"-id "+((Pojo)object).getId(), null);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione save oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
		return s;
	}
	
	public Serializable save(Object object) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "save", "", null);
			Tracer.debug(this.getClass().getName(), "save", "object[]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"save","");
		this.ensureCurrentHibernateSession();
		Serializable s = null;
		try {
			st.start();
			
			s = this.se.save(object);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "save", "object "+object.getClass().getName()+"-id null", null);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione save oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
		return s;			
	}
	
	public void saveOrUpdate(Object object){		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "saveOrUpdate", "", null);
			Tracer.debug(this.getClass().getName(), "saveOrUpdate", "object[]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"saveOrUpdate","");
		this.ensureCurrentHibernateSession();
		
		try {
			st.start();
			
			this.se.saveOrUpdate(object);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "saveOrUpdate", "object "+object.getClass().getName()+"-id null", null);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione saveOrUpdate oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
	}		
	
	public void evict(Object object) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "evict", "",null);
			Tracer.debug(this.getClass().getName(), "evict", "object[]", null);
		}
		
		this.ensureCurrentHibernateSession();
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"evict","");
		try {
			st.start();
			
			this.se.evict(object);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione evict oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
	}
	
	/**
	 * Non persiste oggetti detached (e non si è ben capito quali siano gli oggetti detached)
	 */
	public void persist(Object object) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "persist", "",null);
			Tracer.debug(this.getClass().getName(), "persist", "object[]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"persist","");
		this.ensureCurrentHibernateSession();
		try{
			st.start();
		
			
			this.se.persist(object);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Esecuzione persint oggetto  fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0003);
		}
		finally{
	    	 st.stop();
	    }
	}
	
	/**
	 * @deprecated
	 * USARE Collection getNamedQuery(String queryName, HashMap params) throws DasUncheckedException
	 */
	public Query getNamedQuery(String namedQuery)
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "getNamedQuery", "",null);
			Tracer.debug(this.getClass().getName(), "getNamedQuery", "namedQuery["+namedQuery+"]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"persist",namedQuery);
		Query query = null;
		this.ensureCurrentHibernateSession();	
		try{
			st.start();
			
			
			query = this.se.getNamedQuery(namedQuery);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero query fallita ["+ namedQuery +"]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	    }
		return query;

	}
	
	/**
	 * Restituisce la collezione di elementi restituiti dalla query, con i parametri valorizzati.
	 * Funziona per query in cui i parametri sono nominali e non posizionali.
	 *@param queryName nome della query
	 *@param params parametri "nominali" della query. 
	 */
	public Collection getNamedQuery(String queryName, HashMap params) 
	{		
		return getNamedQuery(queryName, params, -1, -1);
	}
	
	public Collection getNamedQuery(String queryName, HashMap params, int firstResult, int maxResults)  
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "getNamedQuery", "",null);
			Tracer.debug(this.getClass().getName(), "getNamedQuery", "queryName["+queryName+"] params["+params+"]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"getNamedQuery",queryName);
	
		Collection result = null;
		this.ensureCurrentHibernateSession();
		try{
			
			st.start();
			
			Query q=this.se.getNamedQuery(queryName);	
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "getNamedQuery","Q["+q+"]",null);
			if (firstResult > -1)
				q.setFirstResult(firstResult);
			if (maxResults > -1)
				q.setMaxResults(maxResults);
			
			if (params != null) {
				Iterator iter = params.keySet().iterator();
				Object currentKey;
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(),"getNamedQuery","it["+iter+"]", null);
				while(iter.hasNext()) {
					currentKey = iter.next();
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(),"getNamedQuery", "currentKey["+currentKey+"]",null);
					q.setParameter(currentKey.toString(),params.get(currentKey));
				}
			} else {
				Tracer.warn(this.getClass().getName(),"getNamedQuery","[NamedQuery HA PARAMETRI VUOTI] ="+q.getQueryString(),null);
			}
			new HibernateQueryLogger().debugSQL(this.se.getSessionFactory(), q);
			result = q.list(); 
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero query fallita ["+ queryName +"]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	    }
		return result;

	}
	
	public int count(String hqlQuery) 
	{
		Collection c = this.createQuery(hqlQuery);
		Integer integer = (Integer)(c.iterator().next());
		return integer.intValue();
	}
	public Query retrieveQuery(String queryString){
		Query query = null;
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"retrieveQuery",queryString);
		this.ensureCurrentHibernateSession();
		try{
			st.start();
			
			
	        query = this.se.createQuery(queryString);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero query fallita ["+ queryString +"]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	    }
        return query;
	}

    public Query retrieveQuery(String queryString, QueryParams queryParams) {
    	Query query = null;
    	StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"retrieveQuery",queryString);
    	this.ensureCurrentHibernateSession();
    	try{
    		st.start();
	    	
	    	query = this.se.createQuery(queryString);
	
	    	for (Iterator param = queryParams.paramSet().iterator(); param.hasNext();) {
	    		String paramName = (String) param.next();
	    		Object paramValue = queryParams.getValue(paramName);
	    		
	    		Type paramType = queryParams.getType(paramName);
	    		if(paramType!=null) {
	    			if  (paramValue instanceof java.util.List) {
	    				query.setParameterList(paramName,(List) paramValue);
	    			} else if  (paramValue instanceof java.util.Collection) {
	    				query.setParameterList(paramName,(Collection) paramValue);		
	    			} else {					
	    				query.setParameter(paramName, paramValue, paramType);
	    			}
	    		} else {
	    			if  (paramValue instanceof java.util.List) {
	    				query.setParameterList(paramName,(List) paramValue);		
	    			} else if  (paramValue instanceof java.util.Collection) {
	    				query.setParameterList(paramName,(Collection) paramValue);		
	    			} else {					
	    				query.setParameter(paramName, paramValue);
	    			}
	    		}
	    	}
	    	new HibernateQueryLogger().debugSQL(this.se.getSessionFactory(), query);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero query fallita "+ queryString +"]", "", ex);
	        new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		finally{
	    	 st.stop();
	    }
    	return query;
    }

    public int count(Query  hqlQuery) {
        Integer  count = null;
        StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"count",hqlQuery.getQueryString());
        try {
        	st.start();
            count = (Integer) hqlQuery.uniqueResult();
           // new HibernateQueryLogger().debugSQL(this.se.getSessionFactory(), hqlQuery);
    	} catch (HibernateException ex) {
            Tracer.error(this.getClass().getName(), "Esecuzione count fallita ", "", ex);
            new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
    	}  
    	finally{
	    	 st.stop();
	    }
    	if (count ==null)
        	return 0;
        else
        	return count.intValue();
    }
	/* ******************************************************************** */
	/* *********************** METODI INTERNI ***************************** */
	/* ******************************************************************** */
	
	private void ensureHibernate() {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "ensureHibernate", "", null);
		try{
			obtainSessionFactory(sef);
			obtainSession(sef);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero sessione hibernate fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0001);
		}
	}
	
	private void obtainSession(SessionFactory sef) {		
		try{
			if (se == null) {
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(),"obtainSession", "Sessione NULLA la apro da sef ["+sef+"])",null);
				se = sef.openSession();					
			} 
			else{
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(),"obtainSession", "Sessione PRESENTE ["+se+"]",null);	
			}
						
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero sessione  hibernate fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0001);
		}
	}

	private void obtainSessionFactory(SessionFactory sef) {		
		try{
			if (sef == null) 
			{
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(),"obtainSessionFactory", "SessionFactory NULLA la recupero da SPRING",null);			
				BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance();
				BeanFactoryReference bfr = bfl.useBeanFactory("it.nch.fwk.fo");
				this.sef = (SessionFactory) bfr.getFactory().getBean("hibernateSessionFactory");
			} 
			else{
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(),"obtainSessionFactory", "SessionFactory PRESENTE ["+sef+"]",null);	
			}
						
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero sessione hibernate fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0001);
		}
	}
	
	private void ensureCurrentHibernateSession() {
		try{
			this.obtainSessionFactory(this.sef);
			this.se=this.sef.getCurrentSession();
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "ensureCurrentHibernateSession", "recuperata sessione corrente ["+se+"]", null);
		} catch (HibernateException ex) {
	        Tracer.error(this.getClass().getName(), "Recupero sessione  hibernate fallita ", "", ex);
	        new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0001);
		}
	}
	
	public Object load(String entityName, Serializable id) {
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "load (riceve String)", "", null);
			Tracer.debug(this.getClass().getName(), "load (riceve String)", "entityName["+entityName+"] serializable["+id+"]", null);
		}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"load","entityName["+entityName+"] serializable["+id+"]");
		//this.getCurrentHibernateSession();
		Object result = null;
		ensureHibernate();
		try {		  
			st.start();
			
			result =  this.se.load(entityName, id);
		} catch (HibernateException ex) {
			se.disconnect();
			Tracer.error(this.getClass().getName(), "Recupero oggetto hibernate  fallito ", "", ex);
			 new ManageFWBackEndException().processDAOException(ex,FrameworkMessage.DAO_0001);
		}
		finally{
	    	 st.stop();
	    }
		return result;
	}	

	public void load(Object object, Serializable id) {
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "load (riceve Object)", "", null);
			Tracer.debug(this.getClass().getName(), "load (riceve Object)", "object["+object+"] serializable["+id+"]", null);
	}
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"load","object["+object+"] serializable["+id+"]");
	
		ensureHibernate();
		try {		
			st.start();
			
			this.se.load(object, id);
		} catch (HibernateException ex) {
		    se.disconnect();		
			Tracer.error(this.getClass().getName(), "Recupero oggetto hibernate  fallito", "", ex);
			 new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0001);
		}
		finally{
	    	 st.stop();
	    }
	}
	public Object getFromChache(String key) {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo getFromChache non supportato");
	}	

	public int getNBEntries() {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo getNBEntries non supportato");
	}	

	public void putInCache(String key, Object content) {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo putInCache non supportato");
	}	

	public void removeEntry(String key)  {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo removeEntry non supportato");
	}
		
	// Operazioni puntuali su oggetti 
	
	public Object get(Class entityClass, Serializable id) {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo get non supportato");
	}
		
	public void replicate(Object object, ReplicationMode replicationMode)  {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo replicate non supportato");
	}
		
	public Query createFilter(Object collection, String queryString){
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo createFilter non supportato");
	}
}
