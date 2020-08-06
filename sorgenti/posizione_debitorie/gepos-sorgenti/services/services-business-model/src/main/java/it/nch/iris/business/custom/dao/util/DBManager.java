package it.nch.iris.business.custom.dao.util;

import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.dto.business.Pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

public class DBManager {

	public static List selectDBObjects(Class aClass, ArrayList criteriaIn, BackEndContext bec) throws Exception {
		List list = null;
		try {
			String className = aClass.getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			StatelessSessionManager sm = bec.getStatelessSessionManager();
			Criteria criteria = sm.createHibernateCriteria(aClass);
			
			String logC = "";
			String logO = "";
			for (int i = 0; i < criteriaIn.size(); i++) {
				Object tmp = criteriaIn.get(i);
				if (tmp instanceof Criterion) {
					criteria.add((Criterion) tmp);
					logC += tmp.toString() + " ";
				}
				if (tmp instanceof Order) {
					criteria.addOrder((Order) tmp);
					logO += tmp.toString() + " ";
				}
			}
			list = criteria.list();
		} catch (Exception e) {
			throw new Exception(e);
		}
		return list;
	}	
	
	public static Integer selectCountDBObjects(Class aClass, ArrayList criteriaIn, BackEndContext bec) throws Exception {
		Integer count = null;
		try {
			String className = aClass.getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			StatelessSessionManager sm = bec.getStatelessSessionManager();
			Criteria criteria = sm.createHibernateCriteria(aClass);
			
			for (int i = 0; i < criteriaIn.size(); i++) {
				Object tmp = criteriaIn.get(i);
				if (tmp instanceof Criterion) {
					criteria.add((Criterion) tmp);
				}
			}
			criteria.setProjection(Projections.rowCount()); 
			List listcount = criteria.list();
			Object obj = listcount.get(0);
			count = (Integer)obj;

		} catch (Exception e) {
			throw new Exception(e);
		}
		return count;
	}	

	

	public static void deleteObjectsByCriterion(Class aClass, ArrayList criteriaIn, BackEndContext bec) throws Exception {
		List list = null;
		try {
			String className = aClass.getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			StatelessSessionManager sm = bec.getStatelessSessionManager();
			String logC = "";
			Criteria criteria = sm.createHibernateCriteria(aClass);
			for (int i = 0; i < criteriaIn.size(); i++) {
				Criterion tmp = (Criterion) criteriaIn.get(i);
				criteria.add(tmp);
				logC += tmp.toString() + " ";
			}

			list = criteria.list();
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Pojo objTmp = (Pojo) iter.next();
				sm.delete(objTmp);
				sm.flush();
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
	}	
	

	public static List selectDBObjects(Class aClass, ArrayList criteriaIn, String[] groupby, BackEndContext bec) throws Exception {
		List list = null;
		try {
			String className = aClass.getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			StatelessSessionManager sm = bec.getStatelessSessionManager();
			Criteria criteria = sm.createHibernateCriteria(aClass);
			
			String logC = "";
			String logO = "";
			for (int i = 0; i < criteriaIn.size(); i++) {
				Object tmp = criteriaIn.get(i);
				if (tmp instanceof Criterion) {
					criteria.add((Criterion) tmp);
					logC += tmp.toString() + " ";
				}
				if (tmp instanceof Order) {
					criteria.addOrder((Order) tmp);
					logO += tmp.toString() + " ";
				}
			}
			
			ProjectionList pList = Projections.projectionList();
			for (int i = 0; i < groupby.length; i++) {
				pList.add(Projections.groupProperty(groupby[i]));
			}
			criteria.setProjection(pList);

			list = criteria.list();			
		} catch (Exception e) {
			throw new Exception(e);
		}
		return list;
	}	


	public static void deleteObject(Pojo pojo, BackEndContext bec) throws Exception {
		StatelessSessionManager sm = bec.getStatelessSessionManager();		
		try {
			sm.delete(pojo);
			sm.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	
	

	public static void insertObject(Pojo pojo, BackEndContext bec) throws Exception {
		StatelessSessionManager sm = bec.getStatelessSessionManager();
		try {
			sm.save(pojo);
			sm.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	
	

	public static void insertUpdateObjects(Collection elements, BackEndContext bec) throws Exception {
		StatelessSessionManager sm = bec.getStatelessSessionManager();
		try {
			Pojo obj = null;
			Iterator iter = elements.iterator();
			while (iter.hasNext()) {
				obj = (Pojo) iter.next();
				sm.saveOrUpdate(obj);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	
	
	public static void updateObject(Pojo pojo, BackEndContext bec) throws Exception {
		StatelessSessionManager sm = bec.getStatelessSessionManager();
		try {
			sm.update(pojo);
			sm.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	
	
	public static void insertUpdateObject(Pojo pojo, BackEndContext bec) throws Exception {
		StatelessSessionManager sm = bec.getStatelessSessionManager();
		try {
			sm.saveOrUpdate(pojo);
			sm.flush();
			sm.refresh(pojo);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	


	public static Pojo selectSingleDBObjects(Class aClass, Serializable serialized, BackEndContext bec) throws Exception {
		Object objOut = null;
		try {
			StatelessSessionManager sm = bec.getStatelessSessionManager();
			objOut = sm.load(aClass, serialized);
		
		} catch (Exception e) {
			throw new Exception(e);
		}
		return (Pojo) objOut;
	}	

	
	public static Collection executeUpdateQuery(BackEndContext bec, Query query) throws Exception {
		try {
			query.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}
		return new ArrayList();
	}
	
}