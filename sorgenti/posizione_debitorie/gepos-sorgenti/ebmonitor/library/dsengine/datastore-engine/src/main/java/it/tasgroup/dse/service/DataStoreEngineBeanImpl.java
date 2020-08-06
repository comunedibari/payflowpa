package it.tasgroup.dse.service;

import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FromXmlToDbPendenze;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzaXPathValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelExecutionDispatcherFactory;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandler;
import it.nch.eb.xsqlcmd.dbtrpos.operations.IBatisPendenzeErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.operations.PendenzeModelExecutionDispatcher;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.fwk.xml.validation.ValidationService;
import it.tasgroup.dse.dao.StorageDAO;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *  
 */
public class DataStoreEngineBeanImpl extends BaseDataStoreEngineBeanImpl implements DataStoreEngineService { 
	
	private PendenzeModelExecutionDispatcherFactory dispatcherFactory;
	private NamespacesInfos nss;
	
	public DataStoreEngineBeanImpl(
			ValidationService xsdValidationService,
			StorageDAO storageDao, 
			SqlMapClient sqlMapClient,
			PendenzeModelExecutionDispatcherFactory dispatcherFactory,
			TablesUidProvider tablesUidProvider, 
			String defaultCharset,
			NamespacesInfos nss) {
		super(xsdValidationService, storageDao, sqlMapClient, tablesUidProvider, defaultCharset );
		this.dispatcherFactory = dispatcherFactory;
		this.nss = nss;
	}
	
	protected InputsFactoryDispatcher getXml2SqlCommand(PendenzeInclusions pendenzeInclusions, boolean hidden) {
		
		SqlMapClient sqlMapClient = getSqlMapClient();
		
		TablesUidProvider keyProvider= getTablesUidProvider();
		IBatisPendenzeErrorHandler errHandler = new EsitiErrorHandler(sqlMapClient, keyProvider);
		
		PendenzeModelExecutionDispatcher execProvvider=dispatcherFactory.create(sqlMapClient,keyProvider,errHandler);
		
		//nuovo set hidden
		execProvvider.setHidden(hidden);
		
		PendenzaXPathValidator xpathValidator = 
			new PendenzaXPathValidator(errHandler, dispatcherFactory.getErrorsFactory());
		
		FromXmlToDbPendenze xml2SqlCmd = 
			new FromXmlToDbPendenze(null, null, execProvvider, xpathValidator, pendenzeInclusions, nss);
		
		return xml2SqlCmd;
	}
	
}