/**
 * 30/lug/2009
 */
package it.tasgroup.dse.service;

import it.nch.eb.common.utils.loaders.InputStreamFactory;

/**
 * @author gdefacci
 */
public interface DataStoreEngineService {

	java.util.Set/* <QualifiedError> */validate(InputStreamFactory _input)
			throws Exception;

	void/* <QualifiedError> */store(InputStreamFactory _input) throws Exception;
	
	void/* <QualifiedError> */store(InputStreamFactory _input, boolean hidden) throws Exception;

	void/* <QualifiedError> */store(InputStreamFactory _input,
			String[] pendenzeToExclude) throws Exception;

}