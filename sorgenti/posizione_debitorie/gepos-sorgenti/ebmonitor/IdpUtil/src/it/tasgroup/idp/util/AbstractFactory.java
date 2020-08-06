package it.tasgroup.idp.util;

import it.tasgroup.dse.service.DataStoreEngineBeanImpl;
import it.tasgroup.dse.service.DataStoreEngineService;

public abstract class AbstractFactory {

	public DataStoreEngineService createDSE( String className)
	{
		DataStoreEngineService bean = null;
		
		try {
			
			bean = (DataStoreEngineService) Class.forName( className ).newInstance() ;
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bean;
	}

	
}
