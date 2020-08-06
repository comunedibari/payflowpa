package it.tasgroup.services.dao.ejb;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The contract of EntityDAO includes the following requirements: 
 * <br />
 * Implementor is required to implement loadById, getById, create, update, save
 * and delete operations (mandatory operations). 
 * <br />
 * Implementor is not required to implement listByQuery, scrollByQuery, iterateByResult, uniqueResultByQuery, deleteAndDissociate,
 * refresh and evict (optional operations). 
 * <br />
 * Implementor is required to throw any raising exception and chain together multiple
 * exceptions. 
 * <br />
 * If the implementor doesn't implement an optional operation, she must throw an
 * UnsupportedOperationException. 
 * <br />
 * For every operation that returns T, implementor must return the persisted
 * entity. 
 * <br />
 * For every operation that doesn't require the Object parameter
 * persistentContext,implementor must open a new Hibernate Session or JPA Entity
 * Manager, execute the persistent actions and close Session/EntityManager. 
 * <br />
 * For every operation that requires the Object parameter persistentContext,
 * implementor must check the parameter's type and cast it to Hibernate Session or
 * JPA EntityManager. In case of success implementor can use Session/EntityManager to execute the
 * persistent actions, but she must not close it. 
 * <br />
 * 
 * @author follia - Andrea Folli <br>
 * @param <T> Serializable Type
 */

//@Local
public interface Dao <T extends Serializable> {

	/**
	 * Mandatory Operation.
	 * <br/>
	 * Load persistent state by Key.
	 * <br /> 
	 * For BMT implementation: open a new persistent context, load entity, 
	 * close the persistence context in a secure and reliable way before returning the loaded entity.
	 * <br />
	 * For CMT implementation: get current persistent context, load entity and return it. 
	 * <br /> 
	 * Default implementation can return null value.
	 * If there is no persistent entity with the specified key implementor must return a proxy with only key attribute. 
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that acts as Identifier
	 * @return T
	 * @throws Exception
	 */
	T loadById(Class<T> entityType, Serializable key) throws Exception; 
	
	/**
	 * Mandatory Operation. 
	 * <br />
	 * For BMT-CMT implementation: Load persistent state by Id using the persistence context passed in as
	 * parameter. 
	 * <br />
	 * Default implementation can return null value.
	 * If there is no persistent entity with the specified key implementor must return a proxy with only key attribute.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @param persistentContext Object that acts as persistence scope for entity class (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return />
	 * @throws Exception
	 */
	T loadById(Class<T> entityType, Serializable key, Object persistentContext) throws Exception; 	

	/**
	 * Mandatory Operation. 
	 * <br />
	 * For BMT-CMT implementation: Load persistent state by Id using the persistence context passed in as
	 * parameter and the fetch profile passed in as parameter. 
	 * <br />
	 * Default implementation can return null value.
	 * If there is no persistent entity with the specified key implementor must return a proxy with only key attribute.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @param fetchProfile Name of the fetch profile to use 
	 * @return />
	 * @throws Exception
	 */
	T loadById(Class<T> entityType, Serializable key, Set<String> fetchProfiles) throws Exception; 	
	
	/**
	 * Mandatory Operation. 
	 * <br />
	 * For BMT-CMT implementation: Load persistent state by Id using the persistence context passed in as
	 * parameter and the fetch profile passed in as parameter. 
	 * <br />
	 * Default implementation can return null value.
	 * If there is no persistent entity with the specified key implementor must return a proxy with only key attribute.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @param persistentContext Object that acts as persistence scope for entity class (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @param fetchProfile Name of the fetch profile to use 
	 * @return />
	 * @throws Exception
	 */
	T loadById(Class<T> entityType, Serializable key, Object persistentContext, Set<String> fetchProfiles) throws Exception; 	
	
	/**
	 * Mandatory Operation.
	 * <br />
	 * Get persistent state by Id.
	 * <br /> 
	 * For BMT Policy: open a new persistent context, get entity, 
	 * close the persistence context in a secure and reliable way before returning the entity.
	 * <br />
	 * For CMT Policy: get current persistent context and get entity and return it. 
	 * <br />
	 * If there is no persistent entity with the specified key implementor must return null. 
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @return
	 * @throws Exception
	 *             
	 */
	T getById(Class<T> entityType, Serializable key) throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br />
	 * For BMT-CMT implementation: Get persistent state by Id using the persistence context passed in as
	 * parameter. 
	 * <br />
	 * If there is no persistent entity with the specified key implementor must return null.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @param persistentContext Object that acts as persistence scope for entity class (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return
	 * @throws Exception
	 *             
	 */
	T getById(Class<T> entityType, Serializable key, Object persistentContext) throws Exception; 	

	/**
	 * Mandatory Operation.
	 * <br />
	 * For BMT-CMT implementation: Get persistent state by Id using the persistence context passed in as
	 * parameter and the fetch profile passed in as parameter. 
	 * <br />
	 * If there is no persistent entity with the specified key implementor must return null.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @param fetchProfile Name of the fetch profile to use 
	 * @return
	 * @throws Exception
	 *             
	 */
	T getById(Class<T> entityType, Serializable key, Set<String> fetchProfiles) throws Exception; 	
	
	/**
	 * Mandatory Operation.
	 * <br />
	 * For BMT-CMT implementation: Get persistent state by Id using the persistence context passed in as
	 * parameter and the fetch profile passed in as parameter. 
	 * <br />
	 * If there is no persistent entity with the specified key implementor must return null.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entityType Entity Class
	 * @param key Serializable Object that act as Identifier
	 * @param persistentContext Object that acts as persistence scope for entity class (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @param fetchProfile Name of the fetch profile to use 
	 * @return
	 * @throws Exception
	 *             
	 */
	T getById(Class<T> entityType, Serializable key, Object persistentContext, Set<String> fetchProfiles) throws Exception; 	

	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Persist the entity state. 
	 * <br /> 
	 * For BMT Policy: open a new persistent context, start transaction, persist entity state,
	 * commit/rollback transaction, and close the persistence context in a secure and reliable way before returning the persisted entity.
	 * <br />
	 * For CMT Policy: get current persistent context, persist entity and return it. 
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @return
	 * @throws Exception
	 */
	T create(T entity)throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Persist the entity state using the persistence
	 * context passed as parameter and return the entity.
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return
	 * @throws Exception
	 */
	T create(T entity, Object persistentContext)throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Update the entity persistent state. 
	 * <br /> 
	 * For BMT Policy: open a new persistent context, start transaction, execute update,
	 * commit/rollback transaction, and close the persistence context in a secure and reliable way before returning the updated entity.
	 * <br />
	 * For CMT Policy: get current persistent context, execute update and return the updated entity. 
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @return
	 * @throws Exception
	 */
	T update(T entity) throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Update the entity persistent state using the persistence
	 * context passed as parameter and return the entity.
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return
	 * @throws Exception
	 */
	T update(T entity, Object persistentContext)throws Exception; 
		
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Save or update the entity persistent state.
	 * <br /> 
	 * For BMT Policy: open a new persistent context, start transaction, save or update entity,
	 * commit/rollback transaction, close the persistence context in a secure way before returning the updated entity.
	 * <br />
	 * For CMT Policy: get current persistent context, save or update entity and return the updated entity. 
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @return
	 * @throws Exception
	 */
	T save(T entity) throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Save or update the entity state using the persistence
	 * context passed as parameter and return the updated entity..
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return
	 * @throws Exception
	 */
	T save(T entity, Object persistentContext)throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Remove the entity persistent state.
	 * <br /> 
	 * For BMT Policy: open a new persistent context, start transaction, remove the entity,
	 * commit/rollback transaction, and close the persistence context in a secure and reliable way before returning the deleted entity.
	 * <br />
	 * For CMT Policy: get current persistent context, remove the entity and return it. 
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @return
	 * @throws Exception
	 */
	T delete(T entity) throws Exception;
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Remove the entity persistent state using the persistence
	 * context passed as parameter and return the entity.
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to persist
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return
	 * @throws Exception
	 */
	T delete(T entity, Object persistentContext)throws Exception; 
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Remove the entity persistent state by key.
	 * <br /> 
	 * For BMT Policy: open a new persistent context, start transaction, remove the entity by key,
	 * commit/rollback transaction, and close the persistence context in a secure and reliable way before returning the entity.
	 * <br />
	 * For CMT Policy: get current persistent context, remove the entity by key and return it. 
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param key Serializable object that acts as unique identifier for the entity to delete
	 * @return
	 * @throws Exception
	 */
	void deleteByKey(Class<T> entityType, Serializable key) throws Exception;
	
	/**
	 * Mandatory Operation.
	 * <br /> 
	 * Remove the entity persistent state by key using the persistence
	 * context passed as parameter and return the entity.
	 * <br />
	 * In case of error implementor must throw out the exception.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param key Serializable object that acts as unique identifier for the entity to delete
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager) 
	 * @return
	 * @throws Exception
	 */
	
	void deleteByKey(final Class<T> entityType, final Serializable key, final Object persistentContext) throws Exception;
		
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * @return
	 * @throws Exception
	 */
	T create()throws Exception; 
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to delete
	 * @return
	 * @throws Exception
	 */
	boolean deleteAndDissociate(T entity) throws Exception;
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to refresh
	 * @return
	 * @throws Exception
	 */
	boolean refresh(T entity) throws Exception;
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * @author FolliA - Andrea Folli
	 * @param entity Entity instance to evict
	 * @return
	 * @throws Exception
	 */
	boolean evict(T entity) throws Exception; 
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, set fetchSize, firstResult, maxResult, execute the statement and return 
	 * the result set as a List
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param firstResult First record to extract
	 * @param maxResult Max number of records to extract
	 * @param fetchSize Fetch size for underlying JDBC Connection
	 * @param params Map of parameter name-value pairs
	 * @return List containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	List<? extends Object> listByQuery(String query, int firstResult, int maxResult, int fetchSize, Map<String, ? extends Object> params) throws Exception; 
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, execute the statement and return 
	 * the result set as a List
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param params Map of parameter name-value pairs
	 * @return List containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	List<? extends Object> listByQuery(String query, Map<String, ? extends Object> params) throws Exception; 
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, execute the statement and return 
	 * the result set as a List
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @return List containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	List<? extends Object> listByQuery(String query) throws Exception; 
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query using the persistentContext passed in, bind parameters contained in the Map, set fetchSize, firstResult, maxResult, execute the statement and return 
	 * the result set as a List
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param firstResult First record to extract
	 * @param maxResult Max number of records to extract
	 * @param fetchSize Fetch size for underlying JDBC Connection
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return List containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	List<? extends Object> listByQuery(String query, int firstResult, int maxResult, int fetchSize, Map<String, ? extends Object> params, Object persistentContext) throws Exception;
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query using the persistentContext passed in, bind parameters contained in the Map, execute the statement and return 
	 * the result set as a List
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return List containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	List<? extends Object> listByQuery(String query, Map<String, ? extends Object> params, Object persistentContext) throws Exception;
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query using the persistentContext passed in, execute the statement and return 
	 * the result set as a List
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return List containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	List<? extends Object> listByQuery(String query, Object persistentContext) throws Exception;

	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, set fetchSize, firstResult, maxResult, execute the statement and return 
	 * an iterator for the result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param firstResult First record to extract
	 * @param maxResult Max number of records to extract
	 * @param fetchSize Fetch size for underlying JDBC Connection
	 * @param params Map of parameter name-value pairs
	 * @return Iterator on the collection containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Iterator<? extends Object> iterateByQuery(String query, int firstResult, int maxResult, int fetchSize, Map<String, ? extends Object> params) throws Exception; 
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, execute the statement and return 
	 * an iterator for the result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param params Map of parameter name-value pairs
	 * @return Iterator on the collection containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Iterator<? extends Object> iterateByQuery(String query, Map<String, ? extends Object> params) throws Exception; 
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, execute the statement and return 
	 * an iterator for the result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @return Iterator on the collection containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Iterator<? extends Object> iterateByQuery(String query) throws Exception; 
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query using the persistent context passed in, bind parameters contained in the Map, set fetchSize, firstResult, maxResult, execute the statement and return 
	 * an iterator for the result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param firstResult First record to extract
	 * @param maxResult Max number of records to extract
	 * @param fetchSize Fetch size for underlying JDBC Connection
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Iterator on the collection containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Iterator<? extends Object> iterateByQuery(String query, int firstResult, int maxResult, int fetchSize, Map<String, ? extends Object> params, Object persistentContext) throws Exception;

	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query using the persistent context passed in, bind parameters contained in the Map, execute the statement and return 
	 * an iterator for the result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Iterator on the collection containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Iterator<? extends Object> iterateByQuery(String query, Map<String, ? extends Object> params, Object persistentContext) throws Exception;
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query using the persistent context passed in, execute the statement and return 
	 * an iterator for the result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Iterator on the collection containing results of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Iterator<? extends Object> iterateByQuery(String query, Object persistentContext) throws Exception;

	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, set fetchSize, firstResult, maxResult, set scroll mode, execute the statement and return 
	 * a scrollable result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param mode Scroll mode of result set
	 * @param firstResult First record to extract
	 * @param maxResult Max number of records to extract
	 * @param fetchSize Fetch size for underlying JDBC Connection
	 * @param params Map of parameter name-value pairs
	 * @return Scrollable Result Set (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	//ScrollableResults scrollByQuery(String query, String mode, int firstResult, int maxResult, int fetchSize, Map<String, ? extends Object> params) throws Exception; 

	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, set scroll mode, execute the statement and return 
	 * a scrollable result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param mode Scroll mode of result set
	 * @param params Map of parameter name-value pairs
	 * @return Scrollable Result Set (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	//ScrollableResults scrollByQuery(String query, String mode, Map<String, ? extends Object> params) throws Exception; 

	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, set scroll mode, execute the statement and return 
	 * a scrollable result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param mode Scroll mode of result set
	 * @return Scrollable Result Set (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	//ScrollableResults scrollByQuery(String query, String mode) throws Exception; 
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query with the persistent context passed in, bind parameters contained in the Map, set fetchSize, firstResult, maxResult, set scroll mode, execute the statement and return 
	 * a scrollable result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param mode Scroll mode of result set
	 * @param firstResult First record to extract
	 * @param maxResult Max number of records to extract
	 * @param fetchSize Fetch size for underlying JDBC Connection
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Scrollable Result Set (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	//ScrollableResults scrollByQuery(String query, String mode, int firstResult, int maxResult, int fetchSize, Map<String, ? extends Object> params, Object persistentContext) throws Exception;
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query with the persistent context passed in, bind parameters contained in the Map, set scroll mode, execute the statement and return 
	 * a scrollable result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param mode Scroll mode of result set
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Scrollable Result Set (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	//ScrollableResults scrollByQuery(String query, String mode, Map<String, ? extends Object> params, Object persistentContext) throws Exception;
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query with the persistent context passed in, set scroll mode, execute the statement and return 
	 * a scrollable result set.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param mode Scroll mode of result set
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Scrollable Result Set (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	//ScrollableResults scrollByQuery(String query, String mode, Object persistentContext) throws Exception;

	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, bind parameters contained in the Map, execute the statement and return 
	 * a single result as Object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param params Map of parameter name-value pairs
	 * @return Object containing the single result of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Object uniqueResultByQuery(String query, Map<String, ? extends Object> params) throws Exception; 
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query, execute the statement and return 
	 * a single result as Object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @return Object containing the single result of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Object uniqueResultByQuery(String query) throws Exception; 
	
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query with the persistent context passed in, bind parameters contained in the Map, execute the statement and return 
	 * a single result as Object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param params Map of parameter name-value pairs
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Object containing the single result of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Object uniqueResultByQuery(String query, Map<String, ? extends Object> params, Object persistentContext) throws Exception;
	/**
	 * Optional Operation.
	 * <br /> 
	 * Get the named query with the persistent context passed in, execute the statement and return 
	 * a single result as Object.
	 * <br />
	 * @author FolliA - Andrea Folli
	 * @param query Named Query to execute (it can be a HL-Query, JPA-Query or Native SQL Query)
	 * @param persistentContext Object that acts as persistence scope (supported scopes are Hibernate Session and JPA Entity Manager)
	 * @return Object containing the single result of extraction (element can be an entity instance or a tuple of values)
	 * @throws Exception
	 */
	Object uniqueResultByQuery(String query, Object persistentContext) throws Exception;
	
}
