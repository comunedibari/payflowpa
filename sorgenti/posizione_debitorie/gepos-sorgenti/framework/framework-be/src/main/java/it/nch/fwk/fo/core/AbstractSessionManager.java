 /*
 * Created on 16-nov-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package it.nch.fwk.fo.core;
import it.nch.fwk.fo.das.exception.DasUncheckedException;
import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.util.Tracer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * @author ffratoni, cgalli
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public abstract class AbstractSessionManager implements StatelessSessionManager{
//implements StatefulSessionManager, StatelessSessionManager {
	
	public transient SessionFactory sef;	
	public Session se;
		
	/* ********************************************************************* */
	/* ***************** STATEFUL SESSION MANAGER ************************** */
	/* ********************************************************************* */
	
	//public abstract LinkedHashSet estraiMenu(String username) throws RemoteException, CloneNotSupportedException;
	//public abstract Object aziendaCorrente(String username) throws RemoteException, CloneNotSupportedException;
	//public abstract LinkedHashSet listaRapporti(String username, String codServizio) throws RemoteException, CloneNotSupportedException;
	
	/* ********************************************************************* */
	/* ***************** STATELESS SESSION MANAGER ************************* */
	/* ********************************************************************* */
	
	public Object getFromChache(String key) throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo getFromChache non supportato");
	}	

	public int getNBEntries() throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo getNBEntries non supportato");
	}	

	public void putInCache(String key, Object content) throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo putInCache non supportato");
	}	

	public void removeEntry(String key) throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo removeEntry non supportato");
	}
		
	// Operazioni puntuali su oggetti 
	
	public Object get(Class entityClass, Serializable id)throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo get non supportato");
	}
		
	public void replicate(Object object, ReplicationMode replicationMode) throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo replicate non supportato");
	}
		
	public Query createFilter(Object collection, String queryString)throws DasUncheckedException {
		throw new UnsupportedOperationException(this.getClass().getName() + " metodo createFilter non supportato");
	}
	
	/* ******************************************************************** */
	/* ****************** IMPLEMENTO STATEFUL SESSION MANAGER ************* */
	/* ******************************************************************** */
	
	/*
	public void connect() throws RemoteException 
	{
		Tracer.info(this.getClass().getName(), "connect", "connecting session to database", null);
		ensureHibernate();
		
		if(!this.se.isConnected())
			this.se.reconnect();		
	}
	*/
	
	
	public PreparedStatement prepareStatement(String sqlQuery) throws DasUncheckedException {

	throw new UnsupportedOperationException();	
		
//		try {
//			
//			this.ensureCurrentHibernateSession();
//			return this.se.connection().prepareStatement(sqlQuery);
//		
//		} catch (HibernateException e) {
//			// TODO Blocco catch generato automaticamente
//			e.printStackTrace();
//			throw new DasUncheckedException(e.getMessage());
//		} catch (SQLException e) {
//			// TODO Blocco catch generato automaticamente
//			throw new DasUncheckedException(e.getMessage());
//		}		
	}	
	
	
	public CallableStatement callableStatement(String sqlQuery) throws DasUncheckedException {

		
		throw new UnsupportedOperationException();
		
//		try {
//			
//			this.ensureCurrentHibernateSession();				
//			return this.se.connection().prepareCall(sqlQuery);
//		
//		} catch (HibernateException e) {
//			// TODO Blocco catch generato automaticamente
//			e.printStackTrace();
//			throw new DasUncheckedException(e.getMessage());
//		} catch (SQLException e) {
//			// TODO Blocco catch generato automaticamente
//			throw new DasUncheckedException(e.getMessage());
//		}		
	}
	
	
	/*
	public void disconnect() throws RemoteException 
	{
		Tracer.info(this.getClass().getName(), "disconnect", "disconnecting session from database", null);
		
		if(this.se.isConnected()) 
			this.se.disconnect();
	}
	
	public void clear() 
	{
		Tracer.info(this.getClass().getName(), "clear", "clear session", null);
		ensureHibernate();
		
		this.se.clear();						
	}
	
	public void flush() throws DasUncheckedException 
	{
		Tracer.info(this.getClass().getName(), "flush", "flush session", null);
		
		this.se.flush();
	}
	
	public String testSM() throws DasUncheckedException 
	{
		return "HELLO DA SESSION MANAGER";
	}
	*/	
	
	/* ******************************************************************** */
	/* ***************** IMPLEMENTO STATELESS SESSION MANAGER ************* */
	/* ******************************************************************** */
	
	public Collection createQuery(String queryString) throws DasUncheckedException 
	{			
		return createQuery(queryString, -1, -1);
	}
	
	public Query createHibernateQuery(String queryString) throws DasUncheckedException{			

		this.ensureCurrentHibernateSession();
		
		return se.createQuery(queryString);
	}
	
	public Criteria createHibernateCriteria(Class cls) throws DasUncheckedException{			

		this.ensureCurrentHibernateSession();
		
		return se.createCriteria(cls);
	}
	
	public Collection createQuery(String queryString, int firstResult, int maxResults) throws DasUncheckedException
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "createQuery", "",null);
			Tracer.debug(this.getClass().getName(), "createQuery", "queryString["+queryString+"]",null);
		}
		
		this.ensureCurrentHibernateSession();
				
		List list=null;
		try {			
			Query q = this.se.createQuery(queryString);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "createQuery", "q["+q+"]",null);
			if (firstResult > -1)
				q.setFirstResult(firstResult);
			if (maxResults > -1)
				q.setMaxResults(maxResults);
			list = q.list();	
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
		return list;
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
		
		SQLQuery sqlq;
		try {
			sqlq = this.se.createSQLQuery(queryString);			
		} catch (HibernateException e) {
			DasUncheckedException due = new DasUncheckedException(null, e);
			throw due;		
		}
		return sqlq.iterate();
	}
	
	public Collection createSQLQuery(String queryString, HashMap params) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "createSQLQuery", "", null);
			Tracer.debug(this.getClass().getName(), "createSQLQuery", "queryString["+queryString+"] params["+params+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		SQLQuery sqlQuery;
		try {
			sqlQuery = this.se.createSQLQuery(queryString);
			Iterator it=params.keySet().iterator();
			Object currentKey;
			while(it.hasNext())
			{
				currentKey=it.next();
				sqlQuery.setParameter(currentKey.toString(),params.get(currentKey));
			}
		} catch (HibernateException e) {
			DasUncheckedException due = new DasUncheckedException(null, e);
			throw due;		
		}
		
		return sqlQuery.list();
	}
	
	public void delete(Object object) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "delete", "", null);
			Tracer.debug(this.getClass().getName(), "delete", "object["+object+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		try {
			se.delete(object);
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
	}
				
	public Object load(Class object, Serializable id) throws DasUncheckedException 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "load (riceve Class)", "", null);
			Tracer.debug(this.getClass().getName(), "load (riceve Class)", "Class["+object+"] serializable["+id+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		try {		   
			return this.se.load(object, id);
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
	}
	
	public void refresh(Object object) throws DasUncheckedException {
		this.se.refresh(object);
	}
	
	public Object merge(Object object) throws DasUncheckedException {
		this.ensureCurrentHibernateSession();
		try {	
			return this.se.merge(object);
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
	}
	
	public void update(Object object) 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "update", "", null);
			Tracer.debug(this.getClass().getName(), "update", "object["+object+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		try {				
			this.se.update(object);
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
	}
	
	/**
	 * @deprecated
	 */
	public Serializable save(Object object, boolean b) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "save", "", null);
			Tracer.debug(this.getClass().getName(), "save", "object["+object+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		try {
			Serializable s = this.se.save(object);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "save", "object "+object.getClass().getName()+"-id "+((Pojo)object).getId(), null);
			return s;			
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);			
		}
	}
	
	public Serializable save(Object object) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "save", "", null);
			Tracer.debug(this.getClass().getName(), "save", "object["+object+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		try {
			Serializable s = this.se.save(object);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "save", "object "+object.getClass().getName()+"-id "+((Pojo)object).getId(), null);
			return s;			
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);			
		}
	}
	
	public void saveOrUpdate(Object object){		
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "save", "", null);
		this.ensureCurrentHibernateSession();
		
		try {
			this.se.saveOrUpdate(object);
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "save", "object "+object.getClass().getName()+"-id "+((Pojo)object).getId(), null);
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);			
		}
	}		
	
	public void evict(Object object) throws DasUncheckedException 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "evict", "",null);
			Tracer.debug(this.getClass().getName(), "evict", "object["+object+"]", null);
		}
		
		this.ensureCurrentHibernateSession();
		
		try {
			this.se.evict(object);
		} catch (HibernateException e) {
			DasUncheckedException due = new DasUncheckedException(null, e);
			throw due;
		}
	}
	
	/**
	 * Non persiste oggetti detached (e non si è ben capito quali siano gli oggetti detached)
	 */
	public void persist(Object object) 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "persist", "",null);
			Tracer.debug(this.getClass().getName(), "persist", "object["+object+"]", null);
		}
		this.ensureCurrentHibernateSession();
		
		this.se.persist(object);
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
		this.ensureCurrentHibernateSession();		
		return this.se.getNamedQuery(namedQuery);
	}
	
	/**
	 * Restituisce la collezione di elementi restituiti dalla query, con i parametri valorizzati.
	 * Funziona per query in cui i parametri sono nominali e non posizionali.
	 *@param queryName nome della query
	 *@param params parametri "nominali" della query. 
	 */
	public Collection getNamedQuery(String queryName, HashMap params) throws DasUncheckedException 
	{		
		return getNamedQuery(queryName, params, -1, -1);
	}
	
	public Collection getNamedQuery(String queryName, HashMap params, int firstResult, int maxResults) throws DasUncheckedException 
	{		
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "getNamedQuery", "",null);
			Tracer.debug(this.getClass().getName(), "getNamedQuery", "queryName["+queryName+"] params["+params+"]", null);
		}
		this.ensureCurrentHibernateSession();
		
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
			//System.err.println("[getNamedQuery]  [NamedQuery HA PARAMETRI VUOTI] ="+q.getQueryString());
			Tracer.warn(this.getClass().getName(),"getNamedQuery","[NamedQuery HA PARAMETRI VUOTI] ="+q.getQueryString(),null);
		}
		
		return q.list();
	}
	
	public int count(String hqlQuery) 
	{
		Collection c = this.createQuery(hqlQuery);
		Integer integer = (Integer)(c.iterator().next());
		return integer.intValue();
	}
	
	/* ******************************************************************** */
	/* *********************** METODI INTERNI ***************************** */
	/* ******************************************************************** */
	
	private void ensureHibernate() {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "ensureHibernate", "", null);
		obtainSessionFactory(sef);
		obtainSession(sef);
	}
	
	private void obtainSession(SessionFactory sef) {		
		if (se == null) {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(),"obtainSession", "Sessione NULLA la apro da sef ["+sef+"])",null);
			se = sef.openSession();					
		} else{
			if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(),"obtainSession", "Sessione PRESENTE ["+se+"]",null);		
		}
	}

	private void obtainSessionFactory(SessionFactory sef) {		
		if (sef == null) 
		{
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(),"obtainSessionFactory", "SessionFactory NULLA la recupero da SPRING",null);			
			BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
			BeanFactoryReference bfr = bfl.useBeanFactory("it.nch.fwk.fo");
			this.sef = (SessionFactory) bfr.getFactory().getBean("hibernateSessionFactory");
		} 
		else	
		{
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(),"obtainSessionFactory", "SessionFactory PRESENTE ["+sef+"]",null);	
		}
	}
	
	private void ensureCurrentHibernateSession() {
	 	this.obtainSessionFactory(this.sef);
		this.se=this.sef.getCurrentSession();
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "ensureCurrentHibernateSession", "recuperata sessione corrente ["+se+"]", null);
	}
	
	public Object load(String entityName, Serializable id) throws RemoteException 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "load (riceve String)", "", null);
			Tracer.debug(this.getClass().getName(), "load (riceve String)", "entityName["+entityName+"] serializable["+id+"]", null);
		}
		ensureHibernate();
		
		try {		    
			return this.se.load(entityName, id);
		} catch (HibernateException e) {
			se.disconnect();
			DasUncheckedException due = new DasUncheckedException(null, e);
			throw due;
		}
	}	

	public void load(Object object, Serializable id) throws RemoteException 
	{
		if (Tracer.isDebugEnabled(getClass().getName())){
			Tracer.debug(this.getClass().getName(), "load (riceve Object)", "", null);
			Tracer.debug(this.getClass().getName(), "load (riceve Object)", "object["+object+"] serializable["+id+"]", null);
		}
		ensureHibernate();
		
		try {		    
			this.se.load(object, id);
		} catch (HibernateException e) {
			se.disconnect();		
			DasUncheckedException due = new DasUncheckedException(null, e);
			throw due;
		}
	}	
}