package it.tasgroup.services.dao.ejb;

import static it.tasgroup.services.dao.util.DaoTransactionAction.BEGIN;
import static it.tasgroup.services.dao.util.DaoTransactionAction.COMMIT;
import static it.tasgroup.services.dao.util.DaoTransactionAction.ROLLBACK;
import it.tasgroup.services.dao.util.DaoPersistenceProvider;
import it.tasgroup.services.dao.util.DaoTransactionAction;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * JPA Implementation for BMT paradigm.
 * <br /> 
 * 
 * @author follia - Andrea Folli
 *
 * @param <T>
 */
public class DaoImplJpaBmtJta<T extends Serializable> extends DaoImplJpa<T> {

	//protected static final Log LOGGER = LogFactory.getLog(EntityDAOJPABMTImpl.class); 
	private static final Logger LOGGER = LogManager.getLogger(DaoImplJpaBmtJta.class);

	@Resource
	private UserTransaction utx;
	
	//@PersistenceUnit(name="persistenceUnitName") protected EntityManagerFactory emf;		
	private String persistenceUnitName;

	/*** Constructor for persistence unit setting ***/
	public DaoImplJpaBmtJta(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	@Override
	public final T loadById(final Class<T> entityType, final Serializable key, final Object persistentContext, final Set<String> fetchProfiles) throws Exception {	
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Bean to find has key: " + key);
			}
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			T entity = em.find(entityType, key);
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return entity;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}				
	}

	@Override
	public final T getById(final Class<T> entityType, final Serializable key, final Object persistentContext, final Set<String> fetchProfiles) throws Exception {		
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Bean to find has key: " + key);
			}	
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			T entity = em.find(entityType, key);
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return entity;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}			
	}
	
	@Override
	public T create(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Bean to persist is: " + entity);
			}
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			em.persist(entity);
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return entity;
		} catch (Exception e) {			
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}	 		
	}
	
	@Override
	public T update(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Bean to merge is: " + entity);
			}
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			em.merge(entity);
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return entity;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}	 		
	}
	
	@Override
	public T save(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Bean to merge is: " + entity);
			}
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			em.merge(entity);
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return entity;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}	 
	}
	
	@Override
	public T delete(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;
		UserTransaction tx = null;
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Bean to delete is: " + entity);
			}
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			em.remove(em.merge(entity));
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return entity;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}	 	
	}

	@Override
	public List<? extends Object> listByQuery(final String query, final int firstResult,
			final int maxResult, final int fetchSize, final Map<String, ? extends Object> params,
			final Object persistentContext) throws Exception {
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			List<? extends Object> result = 
				this.querySetup(em.createNamedQuery(query), fetchSize,
					firstResult, maxResult, params).getResultList();
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}				
	}
	
	@Override
	public Iterator<? extends Object> iterateByQuery(final String query,
			final int firstResult, final int maxResult, final int fetchSize,
			final Map<String, ? extends Object> params, final Object persistentContext)
			throws Exception {
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			Iterator<? extends Object> result =
				this.querySetup(em.createNamedQuery(query), fetchSize,
					firstResult, maxResult, params).getResultList().iterator();
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return result;
		} catch (Exception e) {			
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}			
	}

	@Override
	public Object uniqueResultByQuery(final String query,
			final Map<String, ? extends Object> params, final Object persistentContext)
			throws Exception {
		EntityManager em = null;	
		UserTransaction tx = null;
		try {
			em = this.entityManagerBind(persistentContext);
			tx = this.entityJtaTransactionBind(em, persistentContext);
			this.entityJtaTransactionHandle(tx, BEGIN, persistentContext);
			Object result =
				this.querySetup(em.createNamedQuery(query), -1, -1, -1, params).getSingleResult();
			this.entityJtaTransactionHandle(tx, COMMIT, persistentContext);
			return result;
		} catch (NoResultException nre) {
			LOGGER.error(nre.getMessage(), nre);
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.entityJtaTransactionHandle(tx, ROLLBACK, persistentContext);
			throw e;
		} finally {
			this.entityManagerCleanup(em, persistentContext);
		}			
	}
	
	/**
	 * @param persistentContext
	 * @return
	 */
	/**
	 * DAO BMT- JPA implementation to bind a session.
	 * <br />
	 * If the persistent context passed in as argument is null, create entity manager from underlying Entity Manager Factory.
	 * <br /> 
	 * If the persistent context passed in as argument is not null, execute the following steps:. 
	 * <br />
	 * If the persistent context is not an instance of EntityManager, throw an UnsupportedOperationException  
	 * <br />
	 * If the persistent context is an instance of EntityManager, cast it to EntityManager and return the casted object
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param persistentContext The current persistent context
	 * @return The active entity manager  
	 * @throws Exception
	 *
	 **/
	@Override
	protected final EntityManager entityManagerBind(final Object persistentContext) {
		if (persistentContext == null) {
			return DaoPersistenceProvider.getEntityManager(this.persistenceUnitName); 	
		}
		
		if (!(persistentContext instanceof EntityManager)) {
			LOGGER.error("Persistent Context is not an instance of JPA EntityManager");
			throw new UnsupportedOperationException(
					"Persistent Context is not an instance of JPA EntityManager");			
		}
		return (EntityManager) persistentContext;		
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.feel.dao.EntityDAOJPAAbstractImpl#entityTransactionBind(java.lang.Object)
	 */
	/**
	 * DAO BMT- JPA implementation to bind a transaction.
	 * <br />
	 * If the persistent context passed in as argument is null, begin a new Entity transaction on the active entity manager.
	 * <br /> 
	 * If the persistent context passed in as argument is not null, execute the following steps:. 
	 * <br />
	 * If the persistent context is not an instance of EntityManager, throw an UnsupportedOperationException  
	 * <br />
	 * If the persistent context is an instance of EntityManager, return null
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param em The current entity manager
	 * @param persistentContext The current persistent context
	 * @return The active entity transaction  
	 * @throws Exception
	 *
	 **/	
//	@Override
//	protected final EntityTransaction entityTransactionBind(final EntityManager em, final Object persistentContext) {
//		LOGGER.error("EntityTransaction is not supported in JTA transactions");
//		throw new UnsupportedOperationException("EntityTransaction is not supported in JTA transactions");
//	}

	@Override
	protected UserTransaction entityJtaTransactionBind(final EntityManager em, final Object persistentContext) {
		if (persistentContext == null) {
			return this.utx;
		} 		
		if (!(persistentContext instanceof EntityManager)) {
			LOGGER.error("Persistent Context is not an instance of JPA EntityManager");
			throw new UnsupportedOperationException("Persistent Context is not an instance of JPA EntityManager");
		}					
//		LOGGER.error("User Transaction is not accesible from JTA EntityManager");
//		throw new UnsupportedOperationException("Persistent Context is not an instance of JPA EntityManager");	
		return null;
	}

	
	/* (non-Javadoc)
	 * @see it.tasgroup.feel.dao.EntityDAOJPAAbstractImpl#entityTransactionHandler(javax.persistence.EntityTransaction, java.lang.String)
	 */
	/**
	 * DAO BMT- JPA implementation to execute a control action on the active transaction.
	 * <br />
	 * If the persistent context passed in as argument is null, execute the specified action on the active transaction
	 * <br /> 
	 * If the persistent context passed in as argument is not null, execute the following steps:. 
	 * <br />
	 * If the persistent context is not an instance of EntityManager, throw an UnsupportedOperationException  
	 * <br />
	 * If the persistent context passed in as argument is an instance of EntityManager, do nothing 
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param tx The current transaction
	 * @param action The action to execute on current transaction
	 * @param persistentContext The current persistent context
	 * @throws NotSupportedException 
	 * @throws Exception
	 *
	 **/	
//	@Override
//	protected final void entityTransactionHandler(final EntityTransaction tx, final DaoTransactionAction action, final Object persistentContext) {
//		LOGGER.error("EntityTransaction is not supported in JTA transactions");
//		throw new UnsupportedOperationException("EntityTransaction is not supported in JTA transactions");
//	}

	@Override
	protected void entityJtaTransactionHandle(final UserTransaction tx, final DaoTransactionAction action, final Object persistentContext) 
			throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, NotSupportedException {
		if (persistentContext == null) {		
			if (action.equals(BEGIN)) {
				tx.begin();
			} else if (action.equals(COMMIT)) {
				tx.commit();
			} else {
				tx.rollback();
			}
		} else {		
			if (!(persistentContext instanceof EntityManager)) {
				LOGGER.error("Persistent Context is not an instance of JPA EntityManager");
				throw new UnsupportedOperationException("Persistent Context is not an instance of JPA EntityManager");
			}
		} 		
	}
	
	/**
	 * DAO BMT- JPA implementation to close the active entity manager.
	 * <br />
	 * If the persistent context passed in as argument is null, close the active entity manager
	 * <br /> 
	 * If the persistent context passed in as argument is not null, execute the following steps:. 
	 * <br />
	 * If the persistent context is not an instance of EntityManager, throw an UnsupportedOperationException  
	 * <br />
	 * If the persistent context passed in as argument is an instance of EntityManager, do nothing 
	 * <br />
	 * @author FolliA - Andrea Folli  
	 * @param em The current entity manager
	 * @param persistentContext The current persistent context
	 * @throws Exception
	 *
	 **/	
	@Override
	protected final void entityManagerCleanup(final EntityManager em, final Object persistentContext) {
		if (persistentContext == null) {
			if (em!=null) em.close();
		} else {		
			if (!(persistentContext instanceof EntityManager)) {
				LOGGER.error("Persistent Context is not an instance of JPA EntityManager");
				throw new UnsupportedOperationException("Persistent Context is not an instance of JPA EntityManager");
			}
			//((EntityManager) persistentContext).close();
		} 
	}
	
	
}
