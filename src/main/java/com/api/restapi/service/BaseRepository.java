//package com.api.restapi.service;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import javax.persistence.EntityManager;
//import javax.persistence.LockModeType;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.hibernate.Criteria;
//import org.hibernate.FetchMode;
//import org.hibernate.LockMode;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.criterion.Expression;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.engine.query.spi.sql.NativeSQLQueryJoinReturn;
//import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
//import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
//import org.hibernate.engine.query.spi.sql.NativeSQLQueryScalarReturn;
//import org.hibernate.engine.spi.NamedSQLQueryDefinition;
//import org.hibernate.internal.SessionFactoryImpl;
//import org.hibernate.transform.Transformers;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import com.api.restapi.service.IBaseRepository;
//
//@Repository
//public abstract class BaseRepository implements IBaseRepository {
//
//	private static Logger logger = Logger.getLogger(BaseRepository.class);
//
//	private static final Pattern numberPattern = Pattern
//			.compile("[0-9]+\\.?[0-9]*");
//
//	// private EntityManagerFactory entityManagerFactory;
//
//	/*
//	 * @Autowired public BaseRepository(EntityManagerFactory
//	 * entityManagerFactory) { setEntityManagerFactory(entityManagerFactory); }
//	 */
//
//	/**
//	 * sets the entityManagerFactory to the jpaDaoSupport
//	 */
//	/*
//	 * @SuppressWarnings("unused")
//	 * 
//	 * @PostConstruct private void doInitialize() {
//	 * setEntityManagerFactory(entityManagerFactory); }
//	 */
//
//	@PersistenceContext(unitName="sunrayPersistenceUnit")
//	protected EntityManager entityManager;
//
//	protected Session getSession() {
//		return (Session) entityManager.getDelegate();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Object find(final Class clazz, final Serializable key,
//			final LockMode lockMode) {
//		// TestTimer timer = new TestTimer("---------- find of "+ clazz + " Key
//		// " + key );
//		if (clazz == null || key == null || lockMode == null) {
//			throw new IllegalArgumentException(
//					"Class, key and LockMode must not be null");
//		}
//
//		Criteria criteria = getSession().createCriteria(clazz).add(
//				Restrictions.idEq(key)).setLockMode(lockMode);
//		Object object = criteria.uniqueResult();
//		// timer.done();
//		return object;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Object find(Serializable key) {
//		return null;
//	}
//
//	private void printCriteria(Map map) {
//		/*
//		 * if(map != null){ for(Iterator it = map.keySet().iterator();
//		 * it.hasNext();){ Object obj = it.next(); Object value = map.get(obj);
//		 * String valueToString = null; if(value != null){ valueToString =
//		 * value.toString(); }
//		 * 
//		 * logger.info("Key : "+obj+" Value : "+valueToString); } }
//		 */
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	
//	/**
//	 * {@inheritDoc}
//	 */
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Deprecated
//	public void saveOrUpdate(Object obj) {
//		// TestTimer timer = new TestTimer("---------- saveOrUpdate of " + obj);
//		if (obj == null) {
//			throw new IllegalArgumentException(
//					"Object to insert must not be null");
//		}
//		/*
//		 * obj = setUserDetails(obj); getHibernateTemplate().saveOrUpdate(obj);
//		 * getHibernateTemplate().flush();
//		 */
//		// timer.done();
//	}
//
//
//	@Override
//	public List find(String queryKey, Object[] object) {
//		// TestTimer timer = new TestTimer("---------- find of "+ queryKey + "
//		// object --> " + object );
//		logger.debug("Executing Query name is------" + queryKey);
//		if (queryKey == null) {
//			throw new IllegalArgumentException("queryKey should not be null");
//		}
//		List list = null;
//		/*
//		 * HibernateTemplate template = getHibernateTemplate(); template
//		 * .setCacheQueries(true);
//		 * logger.info(" DONE.. setCacheQueries DONE..");
//		 */
//
//		Session session = getSession();
//		if(session.isConnected()){
//			org.hibernate.Query query = createNativeQuery(session, queryKey);
//			bindParamtersToQuery(object, query, session.getNamedQuery(queryKey) instanceof SQLQuery, null);
//			list = query.list();
//		}else{
//			Query query = entityManager.createNamedQuery(queryKey);
//			if (object != null && object.length > 0) {
//				for (int i = 0; i < object.length; i++) {
//					query.setParameter(i + 1, object[i]);
//				}
//			}
//			list = query.getResultList();
//		}
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//
//		// statistics(PurchaseItem.class);
//
//		// timer.done();
//		return list;
//	}
//
//	protected List find(String queryKey, Object[] object, LockModeType lockModeType) {
//		// TestTimer timer = new TestTimer("---------- find of "+ queryKey + "
//		// object --> " + object );
//		logger.debug("Executing Query name is------" + queryKey);
//		if (queryKey == null) {
//			throw new IllegalArgumentException("queryKey should not be null");
//		}
//		List list = null;
//		/*
//		 * HibernateTemplate template = getHibernateTemplate(); template
//		 * .setCacheQueries(true);
//		 * logger.info(" DONE.. setCacheQueries DONE..");
//		 */
//
//		Query query = entityManager.createNamedQuery(queryKey);
//		if (object != null && object.length > 0) {
//			for (int i = 0; i < object.length; i++) {
//				query.setParameter(i + 1, object[i]);
//			}
//		}
//		query.setLockMode(lockModeType);
//		list = query.getResultList();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//
//		// statistics(PurchaseItem.class);
//
//		// timer.done();
//		return list;
//	}
//	
//	public List load(final String queryKey,
//			final Map<String, Object> criteriaMap) {
//		// TestTimer timer = new TestTimer("---------- find of " + queryKey + "
//		// criteriaMap " + criteriaMap );
//		logger.debug("Executing Query name is------" + queryKey);
//		printCriteria(criteriaMap);
//
//		Session session = getSession();
//		boolean isSqlQuery = session.getNamedQuery(queryKey) instanceof SQLQuery;
//		String query = session.getNamedQuery(queryKey).getQueryString();
//		String[] queryArray = null;
//		List<String> inParameterList = new ArrayList<String>();
//		String andAppend = " and ";
//		if (query.indexOf("order by") > 0) {
//			queryArray = query.split("order by");
//		} else {
//			queryArray = new String[1];
//			queryArray[0] = query;
//		}
//
//		if (query.indexOf("where") == -1 && !criteriaMap.isEmpty()) {
//			queryArray[0] += " where ";
//			andAppend = "";
//		}
//		Iterator<String> keys = criteriaMap.keySet().iterator();
//		String key = null;
//		while (keys.hasNext()) {
//			key = keys.next();
//			if (criteriaMap.get(key) == null) {
//				queryArray[0] += andAppend + " " + key + " is null";
//				andAppend = " and ";
//			} else if (criteriaMap.get(key) instanceof Object[]) {
//				queryArray[0] += andAppend + " lower(" + key + ") in (:"
//						+ key.replaceAll("\\.", "") + ")";
//				andAppend = " and ";
//				inParameterList.add(key);
//			} else {
//				if (criteriaMap.get(key) instanceof String) {
//					queryArray[0] += andAppend + " lower(" + key + ") like '"
//							+ StringEscapeUtils.escapeSql(((String) criteriaMap.get(key)).toLowerCase())
//							+ "'";
//				} else {
//					queryArray[0] += andAppend + " " + key + " = "
//							+ criteriaMap.get(key) + "";
//				}
//				andAppend = " and ";
//			}
//		}
//		query = queryArray[0]
//				+ (queryArray.length > 1 ? " order by " + queryArray[1] : "");
//		org.hibernate.Query queryObj = null;
//		if (isSqlQuery) {
//			queryObj = session.createSQLQuery(query);
//		} else {
//			queryObj = session.createQuery(query);
//		}
//		if (!inParameterList.isEmpty()) {
//			Iterator<String> inParamIt = inParameterList.iterator();
//			while (inParamIt.hasNext()) {
//				key = inParamIt.next();
//				queryObj.setParameterList(key.replaceAll("\\.", ""),
//						(Object[]) criteriaMap.get(key));
//			}
//		}
//		List list = queryObj.list();
//
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	public int delete(final String queryKey, final Object[] params) {
//		if (queryKey == null || params == null) {
//			throw new IllegalArgumentException(
//					"QueryKey and params must not be null");
//		}
//		logger.debug("Executing Query name is------" + queryKey);
//		Query query = entityManager.createNamedQuery(queryKey);
//		bindParamtersToQuery(params, query);
//		return query.executeUpdate();
//	}
//
//	public List loadNamedParameterInQuery(final String namedQuery,
//			final Map<String, List> parametersList) {
//		logger.debug("Executing Query name is------" + namedQuery);
//		Session session = getSession();
//		org.hibernate.Query query = session.getNamedQuery(namedQuery);
//		for (Iterator iter = parametersList.entrySet().iterator(); iter
//				.hasNext();) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			logger.debug(" keyElement --> " + entry.getKey()
//					+ " parametersList.get(keyElement) " + entry.getValue());
//			query.setParameterList((String) entry.getKey(), (List) entry
//					.getValue());
//		}
//		List list = query.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//	
//	public List loadNamedParameterInQueryForSuggestion(final String namedQuery,
//			final Map<String, Object> parametersList,final int startIndex,final int maxRows) {
//		logger.debug("Executing Query name is------" + namedQuery);
//		Session session = getSession();
//		org.hibernate.Query query = session.getNamedQuery(namedQuery);
//		for (Iterator iter = parametersList.entrySet().iterator(); iter.hasNext();) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			logger.debug(" keyElement --> " + entry.getKey()
//					+ " parametersList.get(keyElement) " + entry.getValue());
//			if (entry.getValue() instanceof Collection) {
//				query.setParameterList((String) entry.getKey(),
//						(Collection) entry.getValue());
//			} else if (entry.getValue() instanceof Object[]) {
//				query.setParameterList((String) entry.getKey(),
//						(Object[]) entry.getValue());
//			} else {
//				query.setParameter((String) entry.getKey(), entry.getValue());
//			}
//		}
//		query.setFirstResult(startIndex).setMaxResults(maxRows);
//		List list = query.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	/*
//	 * public List loadUsingNamedParameters(final String namedQuery, final Map
//	 * parametersList) { Session session = getSession(); org.hibernate.Query
//	 * query = session.getNamedQuery(namedQuery); for (Iterator iter =
//	 * parametersList.entrySet().iterator(); iter .hasNext();) { Map.Entry entry
//	 * = (Map.Entry) iter.next(); logger.debug(" keyElement --> " +
//	 * entry.getKey() + " parametersList.get(keyElement) " + entry.getValue());
//	 * if(entry.getValue() instanceof List){ query.setParameterList((String)
//	 * entry.getKey(), (List) entry .getValue()); }else{
//	 * query.setParameter((String) entry.getKey(), entry.getValue()); } } return
//	 * query.list(); }
//	 */
//
//
//	/**
//	 * TODO : needs to change to normal query..
//	 */
//	@SuppressWarnings("unchecked")
//	public boolean isUnique(final Class clazz,
//			final Map<String, Serializable> criteriaMap,
//			final boolean isEditMode) {
//		boolean unique = false;
//		printCriteria(criteriaMap);
//		if (clazz != null) {
//			Session session = getSession();
//			Criteria criteria = session.createCriteria(clazz);
//
//			// DetachedCriteria detachedCriteria =
//			// DetachedCriteria.forClass(clazz);
//			int i = 0;
//			for (Iterator iter = criteriaMap.entrySet().iterator(); iter
//					.hasNext();) {
//				Map.Entry<String, Serializable> element = (Map.Entry) iter
//						.next();
//				if (isEditMode && i == 0) { // getting the primary key if edit
//					// mode
//					criteria.add(Restrictions.ne(element.getKey(), element
//							.getValue()));
//					i++;
//				} else {
//					criteria.add(Restrictions.eq(element.getKey(), element
//							.getValue()));
//				}
//			}
//			List list = criteria.list();
//			if (list.isEmpty()) {
//				unique = true;
//			}
//		}
//		return unique;
//	}
//
//
//	public List find(final String queryKey, final Object[] parameters,
//			final int startIndex, final int maxResults) {
//		/*
//		 * TestTimer timer = new TestTimer("---------- queryKey "+ queryKey + "
//		 * parameters " + parameters +" startIndex "+startIndex + " maxResults
//		 * "+ maxResults );
//		 */
//		if (queryKey == null) {
//			throw new IllegalArgumentException("queryKey should not be null");
//		} else if (startIndex < 0 || maxResults < 0) {
//			throw new IllegalArgumentException(
//					"startIndex and maxResults should be positive");
//		}
//		logger.debug("Executing Query name is------" + queryKey);
//		Query query = entityManager.createNamedQuery(queryKey);
//		bindParamtersToQuery(parameters, query);
//		query.setFirstResult(startIndex).setMaxResults(maxResults);
//		List list = query.getResultList();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//
//	protected void bindParamtersToQuery(final Object[] parameters, Query query) {
//		if (parameters != null) {
//			int i = 0;
//			while (i < parameters.length) {
//				query.setParameter(i + 1, parameters[i++]);
//			}
//		}
//	}
//	
//	protected void bindParamtersToQuery(final Object[] parameters, org.hibernate.Query query,Boolean isSQLQuery, Collection<Object> conditionsList) {
//		int i = 0;
//		if (parameters != null) {
//			if(isSQLQuery){
//				while (i < parameters.length) {
//					if(parameters[i] instanceof Date && query.getQueryString().contains("'YYYY-MM-DD HH24:MI:SS'")){
//						Date d = (Date) parameters[i];
//						String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(d);
//						query.setParameter(i++, dateString);
//					}else{
//						query.setParameter(i, parameters[i++]);
//					}
//				}
//			}else{
//				while (i < parameters.length) {
//					if(parameters[i] instanceof Date){
//						query.setParameter(i,(Date) parameters[i++]);
//					}else{
//						query.setParameter(i, parameters[i++]);
//					}
//				}
//			}
//		}
//		if(conditionsList != null && !conditionsList.isEmpty()){
//			Iterator<Object> it = conditionsList.iterator();
//			while (it.hasNext()) {
//				Object temp = it.next();
//				if(temp instanceof Date 
//						&& isSQLQuery
//						&& query.getQueryString().contains("'YYYY-MM-DD HH24:MI:SS'")){
//					Date d = (Date) temp;
//					String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(d);
//					query.setParameter(i++, dateString);
//				}else{
//					query.setParameter(i++, temp);
//				}
//			}
//		}
//	}
//
//
//
//	private Timestamp getTimeStamp(Date date, int daySession) {
//		if (date != null && daySession > 1 && daySession < 0) {
//			throw new IllegalArgumentException(
//					"Date and Days session are not valid");
//		}
//		GregorianCalendar gc = new GregorianCalendar();
//		gc.setTime(date);
//		if (daySession == 0) {
//			gc.set(Calendar.HOUR_OF_DAY, 0);
//			gc.set(Calendar.MINUTE, 0);
//			gc.set(Calendar.SECOND, 0);
//			gc.set(Calendar.MILLISECOND, 0);
//		} else if (daySession == 1) {
//			gc.set(Calendar.HOUR_OF_DAY, 23);
//			gc.set(Calendar.MINUTE, 59);
//			gc.set(Calendar.SECOND, 59);
//			gc.set(Calendar.MILLISECOND, 0);
//		}
//		return new Timestamp(gc.getTimeInMillis());
//	}
//
//	public List listForSuggesstion(final String queryKey,
//			final Map<String, Object> criteriaMap) {
//		logger.debug("Executing Query name is------" + queryKey);
//		// TestTimer timer = new TestTimer("---------- find of " + queryKey +
//		// " criteriaMap " + criteriaMap );
//		org.hibernate.Query queryObj = suggestion(queryKey, criteriaMap);
//		
//		List list = queryObj.setMaxResults(25).list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//		// timer.done();
//		// return list;
//	}
//	
//	public List<?> listForSuggesstion(final String queryKey,
//			final Map<String, Object> criteriaMap, Map<String, Object> namedParameterMap) {
//		logger.debug("Executing Query name is------" + queryKey);
//		org.hibernate.Query queryObj = suggestion(queryKey, criteriaMap);
//		for (String parameterName : namedParameterMap.keySet()) {
//			if (namedParameterMap.get(parameterName) instanceof Object[]) {
//				queryObj.setParameterList(parameterName, (Object[]) namedParameterMap
//						.get(parameterName));
//			} else if (namedParameterMap.get(parameterName) instanceof String) {
//				queryObj.setParameter(parameterName, namedParameterMap.get(parameterName));
//			} else if (namedParameterMap.get(parameterName) instanceof Date
//					&& getSession().getNamedQuery(queryKey) instanceof SQLQuery
//					&& queryObj.getQueryString().contains(
//							"'YYYY-MM-DD HH24:MI:SS'")) {
//				Date d = (Date) namedParameterMap.get(parameterName);
//				String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s")
//						.format(d);
//				queryObj.setParameter(parameterName, dateString);
//			} else {
//				queryObj.setParameter(parameterName, namedParameterMap.get(parameterName));
//			}
//		} 
//		List list = queryObj.setMaxResults(25).list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	private org.hibernate.Query suggestion(final String queryKey,
//			final Map<String, Object> criteriaMap) {
//		printCriteria(criteriaMap);
//		Session session = getSession();
//		boolean isNativeQuery = session.getNamedQuery(queryKey) instanceof SQLQuery;
//		String query = session.getNamedQuery(queryKey).getQueryString();
//		String[] queryArray = null;
//		Map<String, String> inParameterList = new HashMap<String, String>(criteriaMap.size());
//		String andAppend = " and ";
//		if (query.indexOf("order by") > 0) {
//			queryArray = query.split("order by");
//		} else {
//			queryArray = new String[1];
//			queryArray[0] = query;
//		}
//
//		if (query.indexOf("where") == -1) {
//			queryArray[0] += " where ";
//			andAppend = "";
//		}
//		Iterator<String> keys = criteriaMap.keySet().iterator();
//		String key = null;
//		while (keys.hasNext()) {
//			key = keys.next();
//			String parameterName = key.replaceAll("\\.", "");
//			if (criteriaMap.get(key) == null) {
//				queryArray[0] += andAppend + " " + key + " is null";
//				andAppend = " and ";
//			} else if (criteriaMap.get(key) instanceof Object[]) {
//				queryArray[0] += andAppend + key + " in (:" + parameterName
//						+ ")";
//				andAppend = " and ";
//				inParameterList.put(key, parameterName);
//			} else if (criteriaMap.get(key) instanceof String) {
//				// queryArray[0] += andAppend + " lower(" + key +
//				// ") like '"+criteriaMap.get(key).toString().toLowerCase()+"'";
//				queryArray[0] += andAppend + " lower(" + key + ") like :"
//						+ parameterName;
//				inParameterList.put(key, parameterName);
//				andAppend = " and ";
//			} else {
//				queryArray[0] += andAppend + " " + key + " = :"
//						+ key.replaceAll("\\.", "");
//				andAppend = " and ";
//				inParameterList.put(key, parameterName);
//			}
//
//		}
//		query = queryArray[0]
//				+ (queryArray.length > 1 ? " order by " + queryArray[1] : "");
//		
//		org.hibernate.Query queryObj = isNativeQuery ? createNativeQuery(session, query) : session.createQuery(query);
//		if (!inParameterList.isEmpty()) {
//			Iterator<String> inParamIt = inParameterList.keySet().iterator();
//			while (inParamIt.hasNext()) {
//				key = inParamIt.next();
//				String paramName = inParameterList.get(key);
//				if (criteriaMap.get(key) instanceof Object[]) {
//					queryObj.setParameterList(paramName, (Object[]) criteriaMap
//							.get(key));
//				} else if (criteriaMap.get(key) instanceof String) {
//					queryObj.setParameter(paramName, criteriaMap.get(key)
//							.toString().toLowerCase());
//				} else if(criteriaMap.get(key) instanceof Date && isNativeQuery && queryObj.getQueryString().contains("'YYYY-MM-DD HH24:MI:SS'")){
//					Date d = (Date) criteriaMap.get(key);
//					String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(d);
//					queryObj.setParameter(paramName, dateString);
//				} else {
//					queryObj.setParameter(paramName, criteriaMap.get(key));
//				}
//			}
//		}
//		return queryObj;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List listForSuggesstion(final Class clazz,
//			final Map<String, Object> criteriaMap) {
//		// TestTimer timer = new TestTimer("---------- find of " + clazz +
//		// " criteriaMap " + criteriaMap );
//		Criteria criteria = suggestion(clazz, criteriaMap);
//		return criteria.setMaxResults(25).list();
//		// timer.done();
//		// return list;
//	}
//
//	private Criteria suggestion(final Class clazz,
//			final Map<String, Object> criteriaMap) {
//		printCriteria(criteriaMap);
//		Session session = getSession();
//		Criteria criteria = session.createCriteria(clazz);
//		Iterator<String> keys = criteriaMap.keySet().iterator();
//		String key = null;
//		while (keys.hasNext()) {
//			key = keys.next();
//			Object value = criteriaMap.get(key);
//			if (value == null) {
//				criteria.add(Restrictions.isNull(key));
//			} else if (value instanceof Integer || value instanceof Long
//					|| value instanceof Float || value instanceof Double
//					|| value instanceof Boolean) {
//				criteria.add(Restrictions.eq(key, value));
//			} else if (value instanceof Map) {
//				Map<String, Object> valueMap = (Map<String, Object>) value;
//				if (valueMap.containsKey("ne")) {
//					criteria.add(Restrictions.ne(key, valueMap.get("ne")));
//				}
//			} else {
//				criteria.add(Restrictions.like(key, value.toString())
//						.ignoreCase());
//			}
//		}
//		return criteria;
//	}
//
//	public int update(final String queryKey, final Object[] params) {
//		if (queryKey == null || params == null) {
//			throw new IllegalArgumentException(
//					"Query Key or Parameters should not be null");
//		}
//		logger.debug("Executing Query name is------" + queryKey);
//		Query query = entityManager.createNamedQuery(queryKey);
//		bindParamtersToQuery(params, query);
//		int rowsEffected = query.executeUpdate();
//		logger.info(" No of rows effected for current update.................."
//				+ rowsEffected);
//		return rowsEffected;
//	}
//	
//	@Override
//	public Object findUniqueObject(final Class clazz,
//			final Map<String, Serializable> criteriaMap) {
//		printCriteria(criteriaMap);
//		Session session = getSession();
//		Criteria criteria = session.createCriteria(clazz);
//		Iterator<String> keys = criteriaMap.keySet().iterator();
//		String key = null;
//		while (keys.hasNext()) {
//			key = keys.next();
//			Object value = criteriaMap.get(key);
//			if (value == null) {
//				criteria.add(Restrictions.isNull(key));
//			} else if (value instanceof Integer || value instanceof Long
//					|| value instanceof Float || value instanceof Double) {
//				criteria.add(Restrictions.eq(key, value));
//			} else {
//				criteria.add(Restrictions.like(key, value.toString())
//						.ignoreCase());
//			}
//		}
//		return criteria.uniqueResult();
//		// return uniqueResult;
//	}
//
//
//
//
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> load(final Class clazz,
//			final List<String> queryKeys, final Serializable key) {
//		if (clazz == null || queryKeys == null || key == null) {
//			throw new IllegalArgumentException("Parameters must not be null");
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		Object obj = entityManager.find(clazz, key);
//		map.put(clazz.getName(), obj);
//		String queryKey = null;
//		for (Iterator iter = queryKeys.iterator(); iter.hasNext();) {
//			queryKey = (String) iter.next();
//			map.put(queryKey, entityManager.createNamedQuery(queryKey)
//					.setParameter(1, key).getResultList());
//		}
//		return map;
//	}
//
//
//	public Object getObjectbyQueryString(String queryString, Object[] parameters) {
//		logger.debug("Executing Query name is------" + queryString);
//		Query query = entityManager.createQuery(queryString);
//		bindParamtersToQuery(parameters, query);
//		return query.getSingleResult();
//	}
//	
//	public Object getObjectbyNamedQuery(String namedQuery, Object[] parameters) {
//		logger.debug("Executing Query name is------" + namedQuery);
//		Object object=null;
//		Query query = entityManager.createNamedQuery(namedQuery);
//		bindParamtersToQuery(parameters, query);
//		try{
//			 List list = query.getResultList();
//			 logger.info(" Result Size. " + list != null ? list.size()
//						: " empty List");
//			 if(!list.isEmpty()){
//				 object = list.get(0); 
//			 }
//		}catch (Exception e) {
//			//There is not matching records for the namedQuery
//			logger.debug("No Record",e);
//		}
//		return object;
//	}
//
//	public List getListbyQueryString(String queryString, Object[] parameters) {
//		logger.debug("Executing Query name is------" + queryString);
//		Query query = entityManager.createQuery(queryString);
//		bindParamtersToQuery(parameters, query);
//		List list = query.getResultList();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	
//	@Override
//	public List loadNamedParameterQuery(final String namedQuery,
//			final Map<String, Object> parameters,final LockMode lockMode,final String aliasName) {
//		logger.debug("Executing Query name is------" + namedQuery);
//		Session session = getSession();
//		org.hibernate.Query query = createQueryWithKey(namedQuery, session);
//		for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			logger.debug(" keyElement --> " + entry.getKey()
//					+ " parametersList.get(keyElement) " + entry.getValue());
//			if (entry.getValue() instanceof Collection) {
//				query.setParameterList((String) entry.getKey(),
//						(Collection) entry.getValue());
//			} else if (entry.getValue() instanceof Object[]) {
//				query.setParameterList((String) entry.getKey(),
//						(Object[]) entry.getValue());
//			}else if(entry.getValue() instanceof Date 
//					&& session.getNamedQuery(namedQuery) instanceof SQLQuery 
//					&& query.getQueryString().contains("'YYYY-MM-DD HH24:MI:SS'")){
//				Date d = (Date) entry.getValue();
//				String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(d);
//				query.setParameter((String) entry.getKey(), dateString);
//			} else {
//				query.setParameter((String) entry.getKey(), entry.getValue());
//			}
//		}
//		query.setLockMode(aliasName, lockMode);
//		List list = query.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	private org.hibernate.Query createNativeQuery(Session session, String query) {
//		String queryString = StringUtils.replace(query, "trunc(?)",
//				"trunc(to_date(?,'YYYY-MM-DD HH24:MI:SS'))");
//		//System.out.println("Replaced Query " + queryString);
//		String parameteredString = StringUtils.replace(queryString,
//				"(trunc\\(:)([a-zA-Z0-9]+)(\\))", "trunc(to_date(:$2,'YYYY-MM-DD HH24:MI:SS'))");
//		//System.out.println("Parameter Query " + parameteredString);
//		return session.createSQLQuery(parameteredString);
//	}
//
//	
//	private String appendQueryConditionGroupBy(String sortExpression, String query,
//			String andOrWhere, String queryCondition) {
//		
//		String[] queryArray = query.split("group by");
//		query = queryArray[0]+ (queryCondition.trim().length() > 0 ? andOrWhere
//							 + queryCondition : "") + " group by "
//							 + queryArray[1] ;
//		
//		if (query.indexOf("order by") > 0) {
//			queryArray = query.split("order by");
//			query = queryArray[0] + " order by "
//					+ (sortExpression == null ? queryArray[1] : sortExpression);
//		} 
//		else{
//			query += (queryCondition.trim().length() > 0 ? andOrWhere
//					+ queryCondition : "")
//					+ (sortExpression == null ? "" : " order by "
//							+ sortExpression);
//		}
//		
//		return query;
//		
//	}
//	private String appendQueryCondition(String sortExpression, String query,
//			String andOrWhere, String queryCondition) {
//		if (query.indexOf("order by") > 0) {
//			String[] queryArray = query.split("order by");
//			query = queryArray[0]
//					+ (queryCondition.trim().length() > 0 ? andOrWhere
//							+ queryCondition : "") + " order by "
//					+ (sortExpression == null ? queryArray[1] : sortExpression);
//		} else {
//			query += (queryCondition.trim().length() > 0 ? andOrWhere
//					+ queryCondition : "")
//					+ (sortExpression == null ? "" : " order by "
//							+ sortExpression);
//		}
//		return query;
//	}
//
//
//	public Object getObjectCollectionProperty(Class<?> clazz, String property,
//			String idName, Serializable key) {
//		Query query = entityManager.createQuery(" select child " + " from "
//				+ clazz.getName() + " parent join parent." + property
//				+ " as child where parent." + idName + " = ?");
//		bindParamtersToQuery(new Object[] { key }, query);
//		return query.getResultList();
//	}
//
//	public Object getObjectSingleProperty(Class<?> clazz, String property,
//			String idName, Serializable key) {
//		Query query = entityManager.createQuery(" select relational "
//				+ " from " + clazz.getName() + " obj join obj." + property
//				+ " as relational where obj." + idName + " = ?");
//		bindParamtersToQuery(new Object[] { key }, query);
//		return query.getSingleResult();
//	}
//
//	// FO METHODS.
//	public Object getObject(final Class clazz, Serializable id) {
//		return find(clazz, id);
//	}
//
//	public List getObjects(final Class clazz) {
//		return find(clazz);
//	}
//
//
//	
//	@Override
//	public List executeQuery(String query, Object[] params, boolean isSqlQuery) {
//		logger.debug("Executing Query name is------" + query);
//		Session session = getSession();
//		org.hibernate.Query queryObj = null;
//		if (isSqlQuery) {
//			queryObj = session.createSQLQuery(query);
//		} else {
//			queryObj = session.createQuery(query);
//		}
//		bindParamtersToQuery(params, queryObj, isSqlQuery, null);
//		List list = queryObj.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	@Override
//	public Iterator<?> getCollectionIterator(String queryKey, Object[] params) {
//		logger.debug("Executing Query name is------" + queryKey);
//		Query query = entityManager.createNamedQuery(queryKey);
//		bindParamtersToQuery(params, query);
//		return query.getResultList().iterator();
//	}
//
//
//
//	private String getQuery(String query, String splitter,
//			String queryCondition, String appendQuery, Boolean isWhere) {
//		String isAppend = null;
//		String tempAppendQuery = "";
//
//		if (isWhere == true) {
//			isAppend = " and ";
//		} else {
//			isAppend = " where ";
//		}
//
//		tempAppendQuery += isAppend;
//		if (queryCondition != null && !queryCondition.equals("")) {
//			tempAppendQuery += queryCondition;
//			if (appendQuery != null && !appendQuery.equals("")) {
//				tempAppendQuery += " and " + appendQuery;
//			}
//		} else {
//			tempAppendQuery += appendQuery;
//		}
//
//		if (splitter != null && !splitter.equals("")) {
//			String[] queryArray = query.split(splitter);
//			query = queryArray[0] + " " + tempAppendQuery + " " + splitter
//					+ " " + queryArray[1];
//		} else {
//			query += tempAppendQuery;
//		}
//
//		return query;
//	}
//
//	public int updateWithInParameter(String queryKey, Object[] lstParams) {
//		Session session = getSession();
//		if (queryKey == null || lstParams == null) {
//			throw new IllegalArgumentException(
//					"Query Key or Parameters should not be null");
//		}
//		logger.debug("Executing Query name is------" + queryKey);
//		boolean isNativeQuery = session.getNamedQuery(queryKey) instanceof SQLQuery;
//		org.hibernate.Query finalQuery = createQueryWithKey(queryKey,getSession());
//
//		bindParamtersToInQuery(lstParams, finalQuery, isNativeQuery);
//		int rowsEffected = finalQuery.executeUpdate();
//		logger.info(" No of rows effected for current update.................."
//				+ rowsEffected);
//		return rowsEffected;
//
//	}
//	
//	public int updateWithInParameter(final String namedQuery,
//			final Map<String, Object> parameters) {
//		logger.debug("Executing Query name is------" + namedQuery);
//		Session session = getSession();
//		org.hibernate.Query query = createQueryWithKey(namedQuery, session);
//		for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			logger.debug(" keyElement --> " + entry.getKey()
//					+ " parametersList.get(keyElement) " + entry.getValue());
//			if (entry.getValue() instanceof Collection) {
//				query.setParameterList((String) entry.getKey(),
//						(Collection) entry.getValue());
//			} else if (entry.getValue() instanceof Object[]) {
//				query.setParameterList((String) entry.getKey(),
//						(Object[]) entry.getValue());
//			} else if(entry.getValue() instanceof Date 
//					&& session.getNamedQuery(namedQuery) instanceof SQLQuery 
//					&& query.getQueryString().contains("'YYYY-MM-DD HH24:MI:SS'")){
//				Date d = (Date) entry.getValue();
//				String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(d);
//				query.setParameter((String) entry.getKey(), dateString);
//			} else {
//				query.setParameter((String) entry.getKey(), entry.getValue());
//			}
//		}
//		int rowsEffected = query.executeUpdate();
//		logger.info(" No of rows effected for current update.................."
//				+ rowsEffected);
//		return rowsEffected;
//	}
//
//	protected void bindParamtersToInQuery(final Object[] parameters,
//			org.hibernate.Query query, Boolean isNativeQuery) {
//		if (parameters != null) {
//			int i = 0;
//
//			while (i < parameters.length) {
//				Object objParam = parameters[i++];
//				if (objParam instanceof Map) {
//					Map<String, Collection> mapList = (HashMap<String, Collection>) objParam;
//					for (Iterator iter = mapList.entrySet().iterator(); iter
//							.hasNext();) {
//						Map.Entry entry = (Map.Entry) iter.next();
//						query.setParameterList((String) entry.getKey(),
//								(Collection) entry.getValue());
//					}
//				}else if(isNativeQuery && objParam instanceof Date && query.getQueryString().contains("'YYYY-MM-DD HH24:MI:SS'")){
//					Date d = (Date) objParam;
//					String dateString = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(d);
//					query.setParameter(i + 1, dateString);
//				} else {
//					query.setParameter(i + 1, objParam);
//				}
//			}
//		}
//	}
//
//
//	@Override
//	public Object findByClassName(String className, String idProperty,
//			Serializable key) {
//		String queryString = "select o from ".concat(className).concat(
//				" o where o.").concat(idProperty).concat(" = ? ");
//		Query query = entityManager.createQuery(queryString).setParameter(1,
//				key);
//		return query.getSingleResult();
//	}
//
//	@Override
//	public List listForSuggesstion(Class clazz,
//			Map<String, Object> criteriaMap, int startIndex, int maxRows) {
//		Criteria criteria = suggestion(clazz, criteriaMap);
//		criteria.setFirstResult(startIndex).setMaxResults(maxRows);
//		return criteria.list();
//	}
//
//	@Override
//	public List listForSuggesstion(String queryKey,
//			Map<String, Object> criteriaMap, int startIndex, int maxRows) {
//		logger.debug("Executing Query name is------" + queryKey);
//		org.hibernate.Query query = suggestion(queryKey, criteriaMap);
//		query.setFirstResult(startIndex).setMaxResults(maxRows);
//		List list = query.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//
//	
//	public List loadGroupBy(final String queryKey,
//			final Map<String, Object> criteriaMap) {
//		logger.debug("Executing Query name is------" + queryKey);
//		String queryIndex = null;
//		Session session = getSession();
//		boolean isSqlQuery = session.getNamedQuery(queryKey) instanceof SQLQuery;
//		String query = session.getNamedQuery(queryKey).getQueryString();
//		String[] queryArray = null;
//		List<String> inParameterList = new ArrayList<String>();
//		List<Object> parameterValues = new ArrayList<Object>();
//		String andAppend = " and ";
//		if (query.indexOf("order by") > 0) {
//			queryIndex = "order by";
//			queryArray = query.split("order by");
//		}
//		if (query.indexOf("group by") > 0) {
//			queryIndex = "group by";
//			queryArray = query.split("group by");
//		} else {
//			queryArray = new String[1];
//			queryArray[0] = query;
//		}
//
//		if (query.indexOf("where") == -1 && !criteriaMap.isEmpty()) {
//			queryArray[0] += " where ";
//			andAppend = "";
//		}
//		Iterator<String> keys = criteriaMap.keySet().iterator();
//		String key = null;
//		while (keys.hasNext()) {
//			key = keys.next();
//			if (criteriaMap.get(key) == null) {
//				queryArray[0] += andAppend + " " + key + " is null";
//				andAppend = " and ";
//			} else if (criteriaMap.get(key) instanceof Object[]) {
//				queryArray[0] += andAppend + " lower(" + key + ") in (:"
//						+ key.replaceAll("\\.", "") + ")";
//				andAppend = " and ";
//				inParameterList.add(key);
//			} else {
//				if (criteriaMap.get(key) instanceof String) {
//					queryArray[0] += andAppend + " lower(" + key + ") like ?";
//					parameterValues.add(((String) criteriaMap.get(key))
//							.toLowerCase());
//				} else {
//					queryArray[0] += andAppend + " " + key + " = ?";
//					parameterValues.add(criteriaMap.get(key));
//				}
//				andAppend = " and ";
//			}
//		}
//		query = queryArray[0]
//				+ (queryArray.length > 1 ? queryIndex + queryArray[1] : "");
//		org.hibernate.Query queryObj = null;
//		if (isSqlQuery) {
//			queryObj = session.createSQLQuery(query);
//		} else {
//			queryObj = session.createQuery(query);
//		}
//		if (!inParameterList.isEmpty()) {
//			Iterator<String> inParamIt = inParameterList.iterator();
//			while (inParamIt.hasNext()) {
//				key = inParamIt.next();
//				queryObj.setParameterList(key.replaceAll("\\.", ""),
//						(Object[]) criteriaMap.get(key));
//			}
//		}
//		for (int i = 0; i < parameterValues.size(); i++) {
//			queryObj.setParameter(i, parameterValues.get(i));
//		}
//		List list = queryObj.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//	
//	
//	@Override
//	public List<?> find(String queryKey, Object[] parameters,
//			Class<?> targetClass) {
//		logger.debug("Executing Query name is------" + queryKey);
//		org.hibernate.Query finalQuery = createQueryWithKey(queryKey, getSession());
//		bindParamtersToQuery(parameters, finalQuery, getSession().getNamedQuery(queryKey) instanceof SQLQuery, null);
//		if (targetClass != null) {
//			finalQuery.setResultTransformer(Transformers.aliasToBean(targetClass));
//		}
//		List list = finalQuery.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//	
//	@Override
//	public <T> List<T> findWithInParameters(String queryKey, Object[] parameters,
//			Class<T> targetClass) {
//		logger.debug("Executing Query name is------" + queryKey);
//		org.hibernate.Query finalQuery = createQueryWithKey(queryKey,getSession());
//		boolean isNativeQuery = getSession().getNamedQuery(queryKey) instanceof SQLQuery;
//		bindParamtersToInQuery(parameters, finalQuery, isNativeQuery);
//		if (targetClass != null) {
//			finalQuery.setResultTransformer(Transformers.aliasToBean(targetClass));
//		}
//		List list = finalQuery.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//
//	@Override
//	public List<?> find(String queryKey, Object[] parameters,
//			final int startIndex, final int maxResults, Class<?> targetClass) {
//		if (queryKey == null) {
//			throw new IllegalArgumentException("queryKey should not be null");
//		} else if (startIndex < 0 || maxResults < 0) {
//			throw new IllegalArgumentException(
//					"startIndex and maxResults should be positive");
//		}
//
//		org.hibernate.Query finalQuery = createQueryWithKey(queryKey,getSession());
//		bindParamtersToQuery(parameters, finalQuery, getSession().getNamedQuery(queryKey) instanceof SQLQuery, null);
//		finalQuery.setFirstResult(startIndex).setMaxResults(maxResults);
//		if (targetClass != null) {
//			finalQuery.setResultTransformer(Transformers
//					.aliasToBean(targetClass));
//		}
//		List list = finalQuery.list();
//		logger.info(" Result Size. " + list != null ? list.size()
//				: " empty List");
//		return list;
//	}
//	
//	@Override
//	public <T> List<T> find(String queryKey, List<Object> parameters, boolean shouldLock) {
//		if (shouldLock) {
//			return find(queryKey, parameters.toArray(), LockModeType.PESSIMISTIC_READ);	
//		} else {
//			return find(queryKey, parameters.toArray());
//		}
//	}
//	
//	@Override
//	public void clearQueryCacheRegion(String regionName) {
//		Session session = (Session) entityManager.getDelegate();
//		session.getSessionFactory().getCache().evictQueryRegion(regionName);
//	}
//	
//	private org.hibernate.Query createQueryWithKey(String queryKey,
//			Session session) {
//		org.hibernate.Query query = null;
//		if(session.getNamedQuery(queryKey) instanceof SQLQuery){
//			 query = createNativeQuery(session, session.getNamedQuery(queryKey).getQueryString());
//			 NamedSQLQueryDefinition sqlQDef = ((SessionFactoryImpl) getSession().getSessionFactory()).getNamedSQLQuery(queryKey);
//				NativeSQLQueryReturn[] temp =  sqlQDef.getQueryReturns();
//				for (NativeSQLQueryReturn tempObj : temp) {
//					if(tempObj instanceof NativeSQLQueryScalarReturn){
//						NativeSQLQueryScalarReturn t = (NativeSQLQueryScalarReturn) tempObj;
//						((SQLQuery)query).addScalar(t.getColumnAlias(), t.getType());
//					}else if(tempObj instanceof NativeSQLQueryRootReturn){
//						NativeSQLQueryRootReturn t = (NativeSQLQueryRootReturn) tempObj;
//						((SQLQuery)query).addEntity(t.getAlias(),t.getReturnEntityName());
//						
//					}else if(tempObj instanceof NativeSQLQueryJoinReturn){
//						NativeSQLQueryJoinReturn t = (NativeSQLQueryJoinReturn) tempObj;
//						((SQLQuery)query).addJoin(t.getAlias(),t.getOwnerAlias(),t.getOwnerProperty());
//					}
//				}
//		}else{
//			query = session.getNamedQuery(queryKey);
//		}
//		return query;
//	}
//
//	@Override
//	public <T> T find(Class<T> clazz, Serializable key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object loadUsingFetchJoin(Class clazz, Object[] properties, Serializable key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void persist(Object object) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Object update(Object object) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void persistAll(Collection collection) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateAll(Collection collection) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void saveObject(Object o) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void saveOrUpdateAll(Collection c) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeObject(Object obj) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeObject(Class clazz, Serializable id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public List<?> findByColumn(Class clazz, String searchColumn, String searchValue) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List find(String query) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<?> searchCriteria(String query, String appendQuery, List<?> searchList) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List searchCriteria(String query, String appendQuery, List searchList, Class target) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List searchCriteria(String queryKey, String appendQuery, List<?> searchList, int startIndex, int maxResults,
//			Class target) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List searchCriteria(String queryKey, String appendQuery, List<?> searchList, int startIndex,
//			int maxResults) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int searchCriteriaCount(String queryKey, String appendQuery, List<?> searchList, int startIndex,
//			int maxResults) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List executeQuery(String query, boolean isSqlQuery) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object loadUsingFetchEager(Class<?> clazz, String[] properties, Serializable key) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void updateObject(Object object) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Object getSingleObject(String queryKey, Object[] lstParams) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List loadNamedParameterQuery(String namedQuery, Map<String, Object> parameters, String appendQuery,
//			Class target) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List loadNamedParameterQuery(String namedQuery, Map<String, Object> parameters, String appendQuery,
//			Class target, int startIndex, int maxResults) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List loadGroupBy(String queryKey, Map<String, Object> criteriaMap, Class target) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int countforObjectArrayParams(String queryKey, Object[] parameters) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List listForSuggesstion(String queryKey, Object[] object) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List load(Class clazz, Map<String, Object> criteria) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer execute(String queryKey, Object[] params) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List loadNamedParameterQuery(String namedQuery, Map<String, Object> parameters) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List listForSuggesstion(String queryKey, Object[] object, int startIndex, int maxRows) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//}
//
