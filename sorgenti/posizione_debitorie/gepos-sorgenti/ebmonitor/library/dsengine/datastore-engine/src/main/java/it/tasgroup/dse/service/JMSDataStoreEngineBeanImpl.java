package it.tasgroup.dse.service;

import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.jms.IJMSStreamDispatcherFactory;
import it.nch.eb.xsqlcmd.jms.JMSStreamDispatcher;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;
import it.nch.fwk.xml.validation.ValidationService;
import it.nch.fwk.xml.validation.XsdValidationService;
import it.tasgroup.dse.dao.StorageDAO;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *  
 */
public class JMSDataStoreEngineBeanImpl extends BaseDataStoreEngineBeanImpl {

	private IJMSStreamDispatcherFactory dispatcherFactory;

	public JMSDataStoreEngineBeanImpl(
			ValidationService xsdValidationService, 
			StorageDAO storageDao,
			SqlMapClient sqlMapClient,
			IJMSStreamDispatcherFactory dispatcherFactory,
			TablesUidProvider tablesUidProvider, 
			String defaultCharset) {
		super(xsdValidationService, storageDao, sqlMapClient, tablesUidProvider, defaultCharset);
		this.dispatcherFactory = dispatcherFactory;
	}
	
	protected InputsFactoryDispatcher getXml2SqlCommand(PendenzeInclusions pendenzeInclusions, boolean hidden) {
		InputsFactoryDispatcher xml2SqlCmd = dispatcherFactory.create(pendenzeInclusions);
		return xml2SqlCmd;
	}
	

}