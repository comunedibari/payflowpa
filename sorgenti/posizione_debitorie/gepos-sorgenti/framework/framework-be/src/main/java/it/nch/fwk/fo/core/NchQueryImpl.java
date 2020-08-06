package it.nch.fwk.fo.core;

import it.nch.fwk.fo.util.StopWatchLogger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;


public class NchQueryImpl implements Query {
	
	public Query setProperties(Map arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Query setResultTransformer(ResultTransformer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private Query q ;
	
	public NchQueryImpl(Query q) {
		super();
		
		this.q = q;
	}
	
	public String getQueryString() {
		
		return q.getQueryString();
	}
	
	public Type[] getReturnTypes() throws HibernateException {
		
		return q.getReturnTypes();
	}
	
	public String[] getReturnAliases() throws HibernateException {
		return q.getReturnAliases();
	}
	
	public String[] getNamedParameters() throws HibernateException {
		return q.getNamedParameters();
	}
	
	public Iterator iterate() throws HibernateException {
		return q.iterate();
	}
	
	public ScrollableResults scroll() throws HibernateException {
		return q.scroll();
	}
	
	public ScrollableResults scroll(ScrollMode arg0) throws HibernateException {
		return q.scroll();
	}
	
	public List list() throws HibernateException {
		StopWatchLogger st = new StopWatchLogger(this.getClass().getName(),"list",this.getQueryString());
		st.start();
		List l =  q.list();
		st.stop();
		return l;
	}
	
	public Object uniqueResult() throws HibernateException {
		return q.uniqueResult();
	}
	
	public int executeUpdate() throws HibernateException {
		return q.executeUpdate();
	}
	
	public Query setMaxResults(int arg0) {
		return q.setMaxResults(arg0);
	}
	
	public Query setFirstResult(int arg0) {
		return q.setFirstResult(arg0);
	}
	
	public Query setReadOnly(boolean arg0) {
		return q.setReadOnly(arg0);
	}
	
	public Query setCacheable(boolean arg0) {
		return q.setCacheable(arg0);
	}
	
	public Query setCacheRegion(String arg0) {
		return q.setCacheRegion(arg0);
	}
	
	public Query setTimeout(int arg0) {
		return q.setTimeout(arg0);
	}
	
	public Query setFetchSize(int arg0) {
		return q.setFetchSize(arg0);
	}
	
	public Query setLockMode(String arg0, LockMode arg1) {
		return q.setLockMode(arg0,arg1);
	}
	
	public Query setComment(String arg0) {
		return q.setComment(arg0);
	}
	
	public Query setFlushMode(FlushMode arg0) {
		return q.setFlushMode(arg0);
	}
	
	public Query setCacheMode(CacheMode arg0) {
		return q.setCacheMode(arg0);
	}
	
	public Query setParameter(int arg0, Object arg1, Type arg2) {
		return q.setParameter(arg0,arg1,arg2);
	}
	
	public Query setParameter(String arg0, Object arg1, Type arg2) {
		return q.setParameter(arg0,arg1,arg2);
	}
	
	public Query setParameter(int arg0, Object arg1) throws HibernateException {
		return q.setParameter(arg0,arg1);
	}
	
	public Query setParameter(String arg0, Object arg1) throws HibernateException {
		return q.setParameter(arg0,arg1);
	}
	
	public Query setParameters(Object[] arg0, Type[] arg1) throws HibernateException {
		return q.setParameters(arg0,arg1);
	}
	
	public Query setParameterList(String arg0, Collection arg1, Type arg2) throws HibernateException {
		return q.setParameterList(arg0,arg1,arg2);
	}
	
	public Query setParameterList(String arg0, Collection arg1) throws HibernateException {
		return q.setParameterList(arg0,arg1);
	}
	
	public Query setParameterList(String arg0, Object[] arg1, Type arg2) throws HibernateException {
		return q.setParameterList(arg0,arg1,arg2);
	}
	
	public Query setParameterList(String arg0, Object[] arg1) throws HibernateException {
		return q.setParameterList(arg0,arg1);
	}
	
	public Query setProperties(Object arg0) throws HibernateException {
		return q.setProperties(arg0);
	}
	
	public Query setString(int arg0, String arg1) {
		return q.setString(arg0,arg1);
	}
	
	public Query setCharacter(int arg0, char arg1) {
		return q.setCharacter(arg0,arg1);
	}
	
	public Query setBoolean(int arg0, boolean arg1) {
		return q.setBoolean(arg0,arg1);
	}
	
	public Query setByte(int arg0, byte arg1) {
		return q.setByte(arg0,arg1);
	}
	
	public Query setShort(int arg0, short arg1) {
		return q.setShort(arg0,arg1);
	}
	
	public Query setInteger(int arg0, int arg1) {
		return q.setInteger(arg0,arg1);
	}
	
	public Query setLong(int arg0, long arg1) {
		return q.setLong(arg0,arg1);
	}
	
	public Query setFloat(int arg0, float arg1) {
		return q.setFloat(arg0,arg1);
	}
	
	public Query setDouble(int arg0, double arg1) {
		return q.setDouble(arg0,arg1);
	}
	
	public Query setBinary(int arg0, byte[] arg1) {
		return q.setBinary(arg0,arg1);
	}
	
	public Query setText(int arg0, String arg1) {
		return q.setText(arg0,arg1);
	}
	
	public Query setSerializable(int arg0, Serializable arg1) {
		return q.setSerializable(arg0,arg1);
	}
	
	public Query setLocale(int arg0, Locale arg1) {
		return q.setLocale(arg0,arg1);
	}
	
	public Query setBigDecimal(int arg0, BigDecimal arg1) {
		return q.setBigDecimal(arg0,arg1);
	}
	
	public Query setBigInteger(int arg0, BigInteger arg1) {
		return q.setBigInteger(arg0,arg1);
	}
	
	public Query setDate(int arg0, Date arg1) {
		return q.setDate(arg0,arg1);
	}
	
	public Query setTime(int arg0, Date arg1) {
		return q.setTime(arg0,arg1);
	}
	
	public Query setTimestamp(int arg0, Date arg1) {
		return q.setTimestamp(arg0,arg1);
	}
	
	public Query setCalendar(int arg0, Calendar arg1) {
		return q.setCalendar(arg0,arg1);
	}
	
	public Query setCalendarDate(int arg0, Calendar arg1) {
		return q.setCalendarDate(arg0,arg1);
	}
	
	public Query setString(String arg0, String arg1) {
		return q.setString(arg0,arg1);
	}
	
	public Query setCharacter(String arg0, char arg1) {
		return q.setCharacter(arg0,arg1);
	}
	
	public Query setBoolean(String arg0, boolean arg1) {
		return q.setBoolean(arg0,arg1);
	}
	
	public Query setByte(String arg0, byte arg1) {
		return q.setByte(arg0,arg1);
	}
	
	public Query setShort(String arg0, short arg1) {
		return q.setShort(arg0,arg1);
	}
	
	public Query setInteger(String arg0, int arg1) {
		return q.setInteger(arg0,arg1);
	}
	
	public Query setLong(String arg0, long arg1) {
		return q.setLong(arg0,arg1);
	}
	
	public Query setFloat(String arg0, float arg1) {
		return q.setFloat(arg0,arg1);
	}
	
	public Query setDouble(String arg0, double arg1) {
		return q.setDouble(arg0,arg1);
	}
	
	public Query setBinary(String arg0, byte[] arg1) {
		return q.setBinary(arg0,arg1);
	}
	
	public Query setText(String arg0, String arg1) {
		return q.setText(arg0,arg1);
	}
	
	public Query setSerializable(String arg0, Serializable arg1) {
		return q.setSerializable(arg0,arg1);
	}
	
	public Query setLocale(String arg0, Locale arg1) {
		return q.setLocale(arg0,arg1);
	}
	
	public Query setBigDecimal(String arg0, BigDecimal arg1) {
		return q.setBigDecimal(arg0,arg1);
	}
	
	public Query setBigInteger(String arg0, BigInteger arg1) {
		return q.setBigInteger(arg0,arg1);
	}
	
	public Query setDate(String arg0, Date arg1) {
		return q.setDate(arg0,arg1);
	}
	
	public Query setTime(String arg0, Date arg1) {
		return q.setTime(arg0,arg1);
	}
	
	public Query setTimestamp(String arg0, Date arg1) {
		return q.setTimestamp(arg0,arg1);
	}
	
	public Query setCalendar(String arg0, Calendar arg1) {
		return q.setCalendar(arg0,arg1);
	}
	
	public Query setCalendarDate(String arg0, Calendar arg1) {
		return q.setCalendarDate(arg0,arg1);
	}
	
	public Query setEntity(int arg0, Object arg1) {
		return q.setEntity(arg0,arg1);
	}
	
	public Query setEntity(String arg0, Object arg1) {
		return q.setEntity(arg0,arg1);
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Query setLockOptions(LockOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockOptions getLockOptions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}