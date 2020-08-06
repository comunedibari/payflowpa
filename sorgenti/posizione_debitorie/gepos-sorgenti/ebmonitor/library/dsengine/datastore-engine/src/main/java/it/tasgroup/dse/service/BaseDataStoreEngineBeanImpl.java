package it.tasgroup.dse.service;

import it.nch.eb.common.utils.loaders.InputStreamFactory;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandler;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;
import it.nch.fwk.xml.validation.ValidationService;
import it.tasgroup.dse.dao.StorageDAO;

import java.io.Reader;
import java.util.Set;

import org.slf4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *  
 */
public abstract class BaseDataStoreEngineBeanImpl implements DataStoreEngineService { 
	
	private final ValidationService xsdValidationService;
	private final StorageDAO storageDao;
	private final SqlMapClient sqlMapClient;
	private final TablesUidProvider tablesUidProvider;
	private final String defaultCharset;
	
	private static final Logger log = 
		ResourcesUtil.createLogger(BaseDataStoreEngineBeanImpl.class);
	
	public BaseDataStoreEngineBeanImpl(
			ValidationService xsdValidationService,
			StorageDAO storageDao, 
			SqlMapClient sqlMapClient,
			TablesUidProvider tablesUidProvider, 
			String defaultCharset) {
		super();
		this.xsdValidationService = xsdValidationService;
		this.storageDao = storageDao;
		this.sqlMapClient = sqlMapClient;
		this.tablesUidProvider = tablesUidProvider;
		this.defaultCharset = defaultCharset;
	}
	
	public StorageDAO getStorageDAO() {
		return storageDao;
	}
	

	public ValidationService getXsdValidationService() {
		return xsdValidationService;
	}

	public java.util.Set/* <QualifiedError> */validate(InputStreamFactory _input)
			throws Exception {

		log.debug("DataStoreEngineBeanImpl.validate[INIT]");
		log.debug("DataStoreEngineBeanImpl.callValidator[INIT]");
		Set out = null;
		
		StorageDAO storageDao =  getStorageDAO(); //(StorageDAO) beanFactory.getBean(BeansNames.STORAGE_DAO);
		
		try {
		
			ValidationService ssi =   getXsdValidationService();//(XsdValidationService) beanFactory.getBean(BeansNames.XSD_VALIDATION_SERVICE);
//			out = ssi.validate(new HandlerReaderFactory(_input, storageDao, getCharset()).getReader());
			out = ssi.validate(new HandlerReaderFactory(_input, getCharset()).getReader());
		
		} catch (Exception e) {
			log.error(
					"DataStoreEngineBeanImpl.callValidator() - Exception " + e);
			throw e;
		}
		
		log.debug("DataStoreEngineBeanImpl.callValidator[END]");
		log.debug("DataStoreEngineBeanImpl.validate[END]");
		return out;

	}

	private String getCharset() {
		return defaultCharset;
	}
	
	public void/* <QualifiedError> */store(InputStreamFactory _input) {
		store( _input, PendenzeInclusions.EXCLUDE_NONE, false);
	}
	
	public void/* <QualifiedError> */store(InputStreamFactory _input, boolean hidden) {
		store( _input, PendenzeInclusions.EXCLUDE_NONE, hidden);
	}	
	
	public void/* <QualifiedError> */store(InputStreamFactory _input, String[] pendenzeToExclude) {
		PendenzeInclusions pendenzeInclusions = PendenzeInclusions.excludeAll(pendenzeToExclude);
		store(_input, pendenzeInclusions, false);
	}


	protected void store(InputStreamFactory _input, PendenzeInclusions pendenzeInclusions, boolean hidden) {
		log.debug("DataStoreEngineBeanImpl.store[INIT]");

//		StorageDAO storageDao =  getStorageDAO();//(StorageDAO) beanFactory.getBean(BeansNames.STORAGE_DAO);
//		HandlerReaderFactory hrf = new HandlerReaderFactory(_input, storageDao, getCharset());
		HandlerReaderFactory hrf = new HandlerReaderFactory(_input, getCharset());
		log.debug("parsing.run() [START] ");

		InputsFactoryDispatcher xml2SqlCmd = getXml2SqlCommand(pendenzeInclusions, hidden);

		xml2SqlCmd.dispatch(hrf);

		log.debug("parsing.run[END]");
		log.debug("DataStoreEngineBeanImpl.store[END]");
	}

	protected abstract InputsFactoryDispatcher getXml2SqlCommand(PendenzeInclusions pendenzeInclusions, boolean hidden);
		
	protected TablesUidProvider getTablesUidProvider() {
		return tablesUidProvider;
	}

	protected SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	protected static void saxParse(Reader reader, DefaultHandler dsh) {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(dsh);
			xmlReader.setErrorHandler(dsh);

			xmlReader.parse(new InputSource(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}