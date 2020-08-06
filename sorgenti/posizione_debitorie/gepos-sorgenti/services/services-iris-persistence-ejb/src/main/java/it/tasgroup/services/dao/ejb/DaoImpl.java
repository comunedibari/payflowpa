package it.tasgroup.services.dao.ejb;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Default Abstract Implementation of EntityDAO contract.
 * <br /> 
 * 
 * @author follia - Andrea Folli
 *
 * @param <T> Serializable Type
 */
public abstract class DaoImpl<T extends Serializable> implements Dao<T> {
	
	private static final Logger LOGGER = LogManager.getLogger(DaoImpl.class);	
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final T loadById(Class<T> entityType, Serializable key) throws Exception {
		return this.loadById(entityType, key, null);	
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T loadById(final Class<T> entityType, final Serializable key, final Object persistentContext) throws Exception {
		return this.loadById(entityType, key, persistentContext, null);
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T loadById(final Class<T> entityType, final Serializable key, final Set<String> fetchProfiles) throws Exception {
		return this.loadById(entityType, key, null, fetchProfiles);
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T loadById(final Class<T> entityType, final Serializable key, final Object persistentContext, final Set<String> fetchProfiles) throws Exception {
		return null;
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final T getById(Class<T> entityType, Serializable key) throws Exception {
		return this.getById(entityType, key, null);	
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T getById(final Class<T> entityType, final Serializable key, final Object persistentContext) throws Exception {
		return this.getById(entityType, key, persistentContext, null);
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T getById(final Class<T> entityType, final Serializable key, final Set<String> fetchProfiles) throws Exception {
		return this.getById(entityType, key, null, fetchProfiles);
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T getById(final Class<T> entityType, final Serializable key, final Object persistentContext, final Set<String> fetchProfiles) throws Exception {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.feel.dao.EntityDAO#create()
	 */
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T create() throws Exception {
		return null;
	}

	/**
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final T create(T entity) throws Exception {	
		
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("Begin saving entity: " + entity);
		
		T savedEntity = this.create(entity, null);
		
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("Saved entity: " + savedEntity);
		
		return savedEntity;
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T create(T entity, final Object persistentContext) throws Exception {
		return null;
	}

	/**
	 * DAO Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final T update(T entity) throws Exception {		
		return this.update(entity, null);		
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T update(T entity, final Object persistentContext) throws Exception {
		return null;
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final T save(T entity) throws Exception {
		return this.save(entity, null);		
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T save(T entity, final Object persistentContext) throws Exception {
		return null;
	}	

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final T delete(T entity) throws Exception {
		return this.delete(entity, null);
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public T delete(T entity, final Object persistentContext) throws Exception {
		return null;
	}

	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final void deleteByKey(Class<T> entityType, Serializable key) throws Exception {
		
		this.deleteByKey(entityType, key, null);
		
	}
	
	/**
	 * DAO - Default implementation: simply return a null object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public void deleteByKey(Class<T> entityType, final Serializable key, final Object persistentContext) throws Exception {
		
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.feel.dao.EntityDAO#deleteAndDissociate(java.io.Serializable)
	 */
	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public boolean deleteAndDissociate(T entity) throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public boolean refresh(T entity) throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public boolean evict(T entity) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Object uniqueResultByQuery(String query) throws Exception {
		return this.uniqueResultByQuery(query, null, null);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Object uniqueResultByQuery(String query, Object persistentContext)
			throws Exception {
		return this.uniqueResultByQuery(query, null, persistentContext);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Object uniqueResultByQuery(String query,
			Map<String, ? extends Object> params) throws Exception {
		return this.uniqueResultByQuery(query, params, null);
	}
	
	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public Object uniqueResultByQuery(final String query,
			final Map<String, ? extends Object> params, final Object persistentContext)
			throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final List<? extends Object> listByQuery(String query,
			Map<String, ? extends Object> params) throws Exception {
		return this.listByQuery(query, -1, -1, -1, params, null);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final List<? extends Object> listByQuery(String query) throws Exception {
		return this.listByQuery(query, -1, -1, -1, null, null);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final List<? extends Object> listByQuery(String query,
			Map<String, ? extends Object> params, Object persistentContext)
			throws Exception {
		return this.listByQuery(query, -1, -1, -1, params, persistentContext);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final List<? extends Object> listByQuery(String query,
			Object persistentContext) throws Exception {
		return this.listByQuery(query, -1, -1, -1, null, persistentContext);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final List<? extends Object> listByQuery(String query, int firstResult,
			int maxResult, int fetchSize, Map<String, ? extends Object> params)
			throws Exception {
		return this.listByQuery(query, firstResult, maxResult, fetchSize, params, null);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public List<? extends Object> listByQuery(final String query, final int firstResult,
			final int maxResult, final int fetchSize, final Map<String, ? extends Object> params,
			final Object persistentContext) throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Iterator<? extends Object> iterateByQuery(String query,
			Map<String, ? extends Object> params) throws Exception {
		return this.iterateByQuery(query, -1, -1, -1, params, null);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Iterator<? extends Object> iterateByQuery(String query) throws Exception {
		return this.iterateByQuery(query, -1, -1, -1, null, null);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Iterator<? extends Object> iterateByQuery(String query,
			Map<String, ? extends Object> params, Object persistentContext)
			throws Exception {
		return this.iterateByQuery(query, -1, -1, -1, params, persistentContext);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Iterator<? extends Object> iterateByQuery(String query,
			Object persistentContext) throws Exception {
		return this.iterateByQuery(query, -1, -1, -1, null, persistentContext);
	}

	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public final Iterator<? extends Object> iterateByQuery(String query,
			int firstResult, int maxResult, int fetchSize,
			Map<String, ? extends Object> params) throws Exception {
		return this.iterateByQuery(query, firstResult, maxResult, fetchSize, params, null);
	}
	
	/**
	 * DAO - Default implementation: simply throw UnsupportedOperationException.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return null
	 */
	public Iterator<? extends Object> iterateByQuery(final String query,
			final int firstResult, final int maxResult, final int fetchSize,
			final Map<String, ? extends Object> params, final Object persistentContext)
			throws Exception {
		throw new UnsupportedOperationException();
	}
	
}
