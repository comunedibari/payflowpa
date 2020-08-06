package it.tasgroup.services.dao.ejb;

import it.tasgroup.services.dao.util.DaoTransactionAction;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 * Default JPA Implementation of EntityDAO contract.
 * <br /> 
 * 
 * @author FolliA - Andrea Folli
 *
 * @param <T> Serializable Type
 */
public abstract class DaoImplJpa<T extends Serializable> extends DaoImpl<T> {

	//protected static final Log LOGGER = LogFactory.getLog(EntityDAOJPAAbstractImpl.class); 
	//private static final Logger LOGGER = LogManager.getLogger(DaoImplJpa.class);
		
	/**
	 * DAO - JPA implementation to set the properties of a Query object and bind the parameters.
	 * <br />
	 * The fetchSize argument is not associated to any action, because JPA doesn't support the fetch size of underlying JDBC Connections
	 * Set firstResult property on the Query object, only if firstResult argument is > 0 
	 * Set maxResult  property on the Query object, only if maxResult argument is > 0
	 * <br />
	 * Bind query parameters with name-value pairs of the Map passed in as argument, only if params argument is not null
	 * <br />
	 * Return the JPA Query object
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @return The JPA Query object.
	 * @throws Exception
	 *
	 **/	
	protected final Query querySetup(final Query queryObj, final int fetchSize, final int firstResult, final int maxResult, final Map<String, ? extends Object> params) {			
		/** Check arguments for future use***/
		//LOGGER.debug(fetchSize);		
		if (firstResult>=0)
			queryObj.setFirstResult(firstResult);
		if (maxResult>0)
			queryObj.setMaxResults(maxResult);	
		if (params!=null) {
			for (String paramName : params.keySet()) {
				queryObj.setParameter(paramName, params.get(paramName));
			}
			//if (LOGGER.isInfoEnabled()) {
			//	LOGGER.info("Query to execute: " + queryObj);
			//}
			//System.out.println("Query to execute: " + queryObj);
		}
		return queryObj;
	}
	
	protected final Query querySetup(final Query queryObj, final Map<String, ? extends Object> params){
		
		return querySetup(queryObj, 0, -1, -1, params);
		
	}

	
	/**
	 * DAO - JPA Method signature for subclass implementation
	 * <br />
	 * @param persistentContext The current persistent context (Supported implementation is JPA EntitiManager)
	 * @return The current JPA EntitiManager
	 */
	protected EntityManager entityManagerBind(final Object persistentContext) throws Exception {
		return null;
	}
	/**
	 * DAO - JPA Method signature for subclass implementation
	 * <br />
	 * @param em The current JPA EntitiManager
	 * @param persistentContext The current persistent context (Supported implementation is JPA EntitiManager)
	 * @return The current Entity Transaction
	 */
	protected EntityTransaction entityTransactionBind(final EntityManager em, final Object persistentContext) throws Exception {
		return null;
	}
	/**
	 * DAO - JPA Method signature for subclass implementation
	 * <br />
	 * @param tx The current Entity Transaction
	 * @param action The control action to execute on current transaction (COMMIT/ROLLBACK)
	 * @param persistentContext The current persistent context (Supported implementation is JPA EntitiManager)
	 */
	protected void entityTransactionHandle(final EntityTransaction tx, final DaoTransactionAction action, final Object persistentContext) throws Exception {}
	/**
	 * DAO - JPA Method signature for subclass implementation
	 * <br />
	 * @param em The current JTA EntitiManager
	 * @param persistentContext The current persistent context (Supported implementation is JPA EntitiManager)
	 * @return The current User Transaction
	 */
	protected UserTransaction entityJtaTransactionBind(final EntityManager em, final Object persistentContext) throws Exception {
		return null;
	}
	/**
	 * DAO - JPA Method signature for subclass implementation
	 * <br />
	 * @param tx The current JTA User Transaction
	 * @param action The control action to execute on current transaction (COMMIT/ROLLBACK)
	 * @param persistentContext The current persistent context (Supported implementation is JPA EntitiManager)
	 */
	protected void entityJtaTransactionHandle(final UserTransaction tx, final DaoTransactionAction action, final Object persistentContext) throws Exception {}
	
	/**
	 * DAO - JPA Method signature for subclass implementation
	 * <br />
	 * @param em The current JPA EntitiManager
	 * @param persistentContext The current persistent context (Supported implementation is JPA EntitiManager)
	 */
	protected void entityManagerCleanup(final EntityManager em, final Object persistentContext) throws Exception {} 
}
