package it.nch.iris.business.custom.dao.util;

import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.dto.business.Pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;

public class DataAccess {



	public Collection selectCollectionFromDB(Class aPojoClass, ArrayList criterion, String[] groupby, BackEndContext bec) throws Exception {
		List list = DBManager.selectDBObjects(aPojoClass, criterion, groupby, bec);
        //evictFromCurrentSession(list);
        return list;
	}	

	
	public Collection selectCollectionFromDB(Class pojoClass, ArrayList criterion, BackEndContext bec) throws Exception {
		List list = DBManager.selectDBObjects(pojoClass, criterion, bec);
        return list;
	}
	
	public Integer selectCountFromDB(Class pojoClass, ArrayList criterion, BackEndContext bec) throws Exception {
		Integer count = DBManager.selectCountDBObjects(pojoClass, criterion, bec);
		return count;
	}	


	
	public void insertPojoToDB(Pojo pojo, BackEndContext bec) throws Exception {
		DBManager.insertObject(pojo, bec);
}	
	

	public void updatePojoToDB(Pojo pojo, BackEndContext bec) throws Exception {
		DBManager.updateObject(pojo, bec);
	}	

	public void insertUpdatePojoToDB(Pojo pojo, BackEndContext bec) throws Exception {
		DBManager.insertUpdateObject(pojo, bec);
	}
	


	public void insertUpdatePojosToDB(Collection pojos, BackEndContext bec) throws Exception {
		DBManager.insertUpdateObjects(pojos, bec);
	}	

	public void deletePojoFromDB(Pojo pojo, BackEndContext bec) throws Exception {
		DBManager.deleteObject(pojo, bec);
	}	
    
    public Pojo selectSinglePojoFromDB(Class pojoClass, Serializable serializedKey, BackEndContext bec) throws Exception {
		return DBManager.selectSingleDBObjects(pojoClass, serializedKey, bec);
	}

	public void deleteMultiplo(Pojo pojo, ArrayList criterion, BackEndContext bec) throws Exception {
		DBManager.deleteObjectsByCriterion(pojo.getClass(), criterion, bec);
	}
    
	public Collection executeUpdateQuery(BackEndContext bec, Query query) throws Exception {
		return DBManager.executeUpdateQuery( bec, query);
	}
	
	public void close() {
	}



}