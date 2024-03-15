//package com.api.restapi.service;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.hibernate.LockMode;
//
//
//public interface IBaseRepository {
//	
//	/**
//	 * This calls the get method of hibernate to fetch the details based on the
//	 * key.
//	 * 
//	 * @param clazz
//	 *            the Class, details of which are to be fetched.
//	 * @param key
//	 *            the id used to fetch the details.
//	 * @return Object of type Class, which matched the key.
//	 * @throw IllegalArgumentException if null.
//	 */
//	<T> T find(Class<T> clazz, Serializable key);
//
//	/**
//	 * This calls the loadAll method of hibernate to fetch all the details of
//	 * specified class.
//	 * 
//	 * @param clazz
//	 *            the Class, details of which are to be fetched.
//	 * @return List of objects of type clazz
//	 * @throw IllegalArgumentException if null.
//	 */
//	List find(Class clazz);
//
//	Object find(Serializable key);
//
//	/**
//	 * Returns the collection based on the criteria passed.
//	 * 
//	 * @param clazz
//	 *            the clazz, whose details to be fetched
//	 * @param criteriaMap
//	 *            the criteriaMap, containing the <code>key</code> as on what
//	 *            search to be performed and <code>value</code> the value to
//	 *            be compared.
//	 * @return the list satisfying the criteria passed.
//	 */
//	@Deprecated
//	List find(Class clazz, Map<String, Object> criteriaMap);
//
//	/**
//	 * Returns the collection based on the criteria passed. 
//	 * 
//	 * @param clazz
//	 *            the clazz, whose details to be fetched
//	 * @param criteriaMap
//	 *            the criteriaMap, containing the <code>key</code> as on what
//	 *            search to be performed and <code>value</code> the value to
//	 *            be compared.
//	 *            orderBy map implementation having propertyName as key and value as desc or asc. Use the LinkedHashMap for specific order by clause 
//	 * @return the list satisfying the criteria passed.
//	 */
//	<T> List<T> find(Class<T> clazz, Map<String, Object> criteriaMap, Map<String, String> orderBy);
//	
//	/**
//	 * Persist an Object or the changes that were made to a already persisted
//	 * Object.
//	 * 
//	 * @param obj
//	 *            The object to persist. Not null.
//	 */
//	@Deprecated
//	void saveOrUpdate(Object obj);
//
//	// @Deprecated
//	// void saveOrUpdateAll(Collection collection);
//
//	/**
//	 * This calls the delete method of hibernate.
//	 * 
//	 * @param obj
//	 *            the Object to be deleted.
//	 * @return none
//	 * @throw IllegalArgumentException if null.
//	 */
//	void delete(Object obj);
//
//	/**
//	 * This calls the delete method of hibernate.
//	 * 
//	 * @param obj
//	 *            the Object to be deleted.
//	 * @return none
//	 * @throw IllegalArgumentException if null.
//	 */
//	int delete(String queryKey, Object[] params);
//
//	/**
//	 * To fetch the list based on the queryKey.
//	 * 
//	 * @param queryKey
//	 *            the queryKey, for fetching the query
//	 * @param object
//	 *            the object, array of parameters to be mapped to query, pass
//	 *            null if not required
//	 * @return the list object
//	 */
//	List find(String queryKey, Object[] object);
//
//	/**
//	 * method to be used to fetch the object which has lazy association
//	 * mappings(collections) to be fetched while loading the object itself.
//	 * 
//	 * @param clazz
//	 * @param properties
//	 * @param key
//	 * @return
//	 */
//	Object loadUsingFetchJoin(Class clazz, Object[] properties, Serializable key);
//
//	/**
//	 * Executes the NamedQuery with In option.
//	 * 
//	 * @param namedQuery
//	 *            the queryKey, for which the query needs to be fetched
//	 * @param parametersList
//	 *            values to be passed to the In condition
//	 * @return list
//	 */
//	List loadNamedParameterInQuery(final String namedQuery,
//			final Map<String, List> parametersList);
//
//	/**
//	 * used to find the value is already there in the table
//	 * 
//	 * @param clazz
//	 *            Name of the class
//	 * @param criteriaMap
//	 *            if isEditMode is true then first Key,Value in the map should
//	 *            be the Primary Column and Value
//	 * @param isEditMode
//	 *            says finding unique in Add mode or Editmode
//	 * @return boolean
//	 */
//	boolean isUnique(final Class clazz,
//			final Map<String, Serializable> criteriaMap,
//			final boolean isEditMode);
//
//	/**
//	 * Returns the list of objects based on passed queryKey, starting from the
//	 * startIndex and list size equals to maxResults.
//	 * 
//	 * @param queryKey
//	 *            the queryKey, for which the query needs to be fetched
//	 * @param object
//	 *            the parameters, array of parameters to be set to query
//	 * @param startIndex
//	 *            the startIndex, starting position of result
//	 * @param maxResults
//	 *            the maxResults, the no of objects to be fetched
//	 * @return the list
//	 */
//	List find(String queryKey, Object[] parameters, int startIndex,
//			int maxResults);
//
//	/**
//	 * Returns the collection based on the criteria map passed for the querykey.
//	 * 
//	 * @param queryKey
//	 * @param searchCriteriaMap
//	 * @return
//	 */
//
//	/**
//	 * returns the count of Rows returned for the passed query and parameters.
//	 * 
//	 * @param queryKey
//	 * @param parameters
//	 * @return
//	 */
//	int countforObjectArrayParams(String queryKey, Object[] parameters);
//
//	/**
//	 * returns the count of Rows returned for the passed query and parameters.
//	 * 
//	 * @param queryKey
//	 * @param parameters
//	 * @return
//	 */
//
//	/**
//	 * Mehtod only for suggesstion box implementation
//	 * 
//	 * @param queryKey
//	 * @param criteriaMap
//	 * @return
//	 */
//	List listForSuggesstion(String queryKey, Map<String, Object> criteriaMap);
//
//	/**
//	 * Mehtod only for suggesstion box implementation
//	 * 
//	 * @param clazz
//	 * @param criteriaMap
//	 * @return
//	 */
//	List listForSuggesstion(Class clazz, Map<String, Object> criteriaMap);
//
//	List listForSuggesstion(String queryKey, Object[] object);
//
//	/**
//	 * For updating using a query
//	 * 
//	 * @param queryKey
//	 * @param params
//	 */
//	int update(String queryKey, Object[] params);
//
//	List load(final Class clazz, final Map<String, Object> criteria);
//
//	/**
//	 * Returns the collection based on the querykey passed that matches the
//	 * passed criteriaMap.
//	 * 
//	 * @param queryKey
//	 * @param criteriaMap
//	 * @return
//	 */
//	List load(String queryKey, Map<String, Object> criteriaMap);
//
//	Map<String, Object> load(final Class clazz, final List<String> queryKeys,
//			final Serializable key);
//
//	/**
//	 * 
//	 * @param queryKey -
//	 *            The named hibernate query key to use.
//	 * @param params -
//	 *            The parameters to be substituted, in the order they appear in
//	 *            the query.
//	 * @return The number of the records affected by the operation.
//	 */
//	Integer execute(String queryKey, Object[] params);
//
//	void persist(Object object);
//
//	Object update(Object object);
//
//	void persistAll(Collection collection);
//
//	void updateAll(Collection collection);
//
//	Object getObjectbyQueryString(String queryString, Object[] parameters);
//	public Object getObjectbyNamedQuery(String namedQuery, Object[] parameters);
//
//	List getListbyQueryString(String queryString, Object[] parameters);
//
//	/**
//	 * Returns the list. Needs to be used in cases having named parameters to be
//	 * set for queries and can be of type <code>java.util.Collection</code>,
//	 * <code>Object[]</code> and normal parameters also. <code>Key</code>
//	 * will be the name of the parameter to set.
//	 * 
//	 * @param namedQuery
//	 * @param parameters
//	 * @return
//	 */
//	List loadNamedParameterQuery(String namedQuery,
//			Map<String, Object> parameters);
//	
//	/*public List loadUsingNamedParameters(final String namedQuery,
//			final Map parametersList);*/
//
//	void delete(Class clazz, Serializable key);
//
//
//	Object getObjectCollectionProperty(Class<?> clazz, String property,
//			String idName, Serializable key);
//
//	Object getObjectSingleProperty(Class<?> clazz, String property,
//			String idName, Serializable key);
//
//	// FO METHODS
//
//	public List getObjects(Class clazz);
//
//	public Object getObject(Class clazz, Serializable id);
//
//	@Deprecated
//	public void saveObject(Object o);
//
//	@Deprecated
//	public void saveOrUpdateAll(Collection c);
//
//	void removeObject(Object obj);
//
//	public void removeObject(Class clazz, Serializable id);
//
//	// public Object find(Class clazz, Serializable serializableId);
//	// public List find(String query, Object[] object);
//
//	public List<?> findByColumn(Class clazz, String searchColumn,
//			String searchValue);
//
//	public List find(String query);
//
//	// List find(String queryKey, Object[] parameters, int startIndex,
//	// int maxResults);
//
//	// int countforObjectArrayParams(String queryKey, Object[] object);
//
//	// public void update(final String queryKey, final Object[] params);
//
//	public List<?> searchCriteria(final String query, final String appendQuery,
//			final List<?> searchList);
//
//	public List searchCriteria(final String query, final String appendQuery,
//			final List searchList, final Class target);
//	
//	public List searchCriteria(final String queryKey, final String appendQuery,
//			final List<?> searchList, final int startIndex, final int maxResults,final Class target) ;
//	
//	List  searchCriteria(final String queryKey, final String appendQuery,
//			final List<?> searchList, final int startIndex, final int maxResults);
//	
//	int  searchCriteriaCount(final String queryKey, final String appendQuery,
//			final List<?> searchList, final int startIndex, final int maxResults);
//			
//	/*
//	 * int countforSearch(String queryKey, Object[] params, List<SearchCriteriaDetail>
//	 * searchCriteriaDetails);
//	 */
//
//	/*
//	 * List<?> search(String queryKey, Object[] params, List<SearchCriteriaDetail>
//	 * searchCriteriaDetails, String sortExpression, int startIndex, int
//	 * maxRows);
//	 */
//
//	// Object loadUsingFetchJoin(Class clazz, Object[] properties, Serializable
//	// key);
//	/**
//	 * Returns the collection based on the criteria map passed for the querykey.
//	 * 
//	 * @param queryKey
//	 * @param searchCriteriaMap
//	 * @return
//	 */
//	// List search(String queryKey, Map<String, SearchCriteria>
//	// searchCriteriaMap, int startIndex, int maxRows);
//	/**
//	 * returns the count of Rows returned for the passed query and parameters.
//	 * 
//	 * @param queryKey
//	 * @param parameters
//	 * @return
//	 */
//	// int countforSearchCriteraiMap(String queryKey, Map<String,
//	// SearchCriteria> searchCriteriaMap);
//	List executeQuery(String query, boolean isSqlQuery);
//
//	Object loadUsingFetchEager(Class<?> clazz,
//			String[] properties, Serializable key) throws Exception;	
//	
//	void updateObject(Object object); 
//	
//	Iterator<?> getCollectionIterator(String queryKey, Object[] params);
//	Object getSingleObject(String queryKey, Object[] lstParams);
//	 int updateWithInParameter(String queryKey, Object[] lstParams);
//
//	Object findByClassName(String className, String idProperty, Serializable key);
//	
//	/**
//	 * Mehtod only for suggesstion box implementation
//	 * 
//	 * @param queryKey
//	 * @param criteriaMap
//	 * @return
//	 */
//	List listForSuggesstion(String queryKey, Map<String, Object> criteriaMap, int startIndex, int maxRows);
//
//	/**
//	 * Mehtod only for suggesstion box implementation
//	 * 
//	 * @param clazz
//	 * @param criteriaMap
//	 * @return
//	 */
//	List listForSuggesstion(Class clazz, Map<String, Object> criteriaMap, int startIndex, int maxRows);
//
//	List listForSuggesstion(String queryKey, Object[] object, int startIndex, int maxRows);
//
//	
//	/*public List loadNamedParameterInQuery(final String namedQuery,
//			final Map<String, List> parametersList, Class target);*/
//	List loadNamedParameterQuery(String namedQuery,
//			Map<String, Object> parameters,String appendQuery,Class target);
//	List loadNamedParameterQuery(String namedQuery,
//			Map<String, Object> parameters,String appendQuery,Class target,int startIndex,int maxResults);
//	
//	public List loadGroupBy(final String queryKey,
//			final Map<String, Object> criteriaMap);
//	
//	public List loadGroupBy(final String queryKey,
//			final Map<String, Object> criteriaMap,Class target);
//
//	List<?> find(String queryKey, Object[] parameters, Class<?> targetClass);
//
//	<T> List<T> findWithInParameters(String queryKey, Object[] parameters,
//			Class<T> targetClass);
//
//	List<?> listForSuggesstion(String queryKey,
//			Map<String, Object> criteriaMap,
//			Map<String, Object> namedParameterMap);
//	
//	Object findUniqueObject(final Class clazz,
//			final Map<String, Serializable> criteriaMap);
//
//	List<?> find(String queryKey, Object[] parameters, int startIndex,
//			int maxResults, Class<?> targetClass);
//	public int updateWithInParameter(final String namedQuery,
//			final Map<String, Object> parameters);
//	
//	
//	
//	/**
//	 * For suggestion boxes
//	 * @param namedQuery
//	 * @param parametersList
//	 * @param startIndex
//	 * @param maxRows
//	 * @return
//	 */
//	List loadNamedParameterInQueryForSuggestion(final String namedQuery,
//			final Map<String, Object> parametersList,final int startIndex,final int maxRows);
//
//	List executeQuery(String query, Object[] params, boolean isSqlQuery);
//
//	List loadNamedParameterQuery(String namedQuery,
//			Map<String, Object> parameters, LockMode lockMode,final String aliasName);
//
//	<T> List<T> find(String queryKey, List<Object> parameters, boolean shouldLock);
//
//	void clearQueryCacheRegion(String regionName);
//}
//
//
//
