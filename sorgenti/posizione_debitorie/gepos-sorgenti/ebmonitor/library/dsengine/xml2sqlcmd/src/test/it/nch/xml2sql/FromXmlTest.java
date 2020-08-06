/**
 * Created on 11/mar/2009
 */
package it.nch.xml2sql;

import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.common.utils.loaders.ReaderLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FromXmlToDbPendenze;
import it.nch.eb.xsqlcmd.dbtrpos.dao.HeaderEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.OtfEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzaXPathValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelExecutionDispatcherFactory;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandlers;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandlers.Simple;
import it.nch.eb.xsqlcmd.dbtrpos.operations.IBatisPendenzeErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.operations.OtfModelExecutionDispatcher;
import it.nch.eb.xsqlcmd.dbtrpos.operations.PendenzeModelExecutionDispatcher;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.xml2sql.test.PendenzeTestConfiguration;
import it.nch.xml2sql.test.TestEnvironments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import junit.framework.TestCase;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class FromXmlTest extends TestCase {
	
//	String fsName = "tests/otf/abc.xml";
//	String fsName = "tests/otf/Elena104.xml";
	String fsName = "tests/otf/Elena0001.xml";
//	String fsName = "tests/otf/elana001.xml";
//	String fsName = "tests/otf/elana001LB.xml";	
//	String fsName = "tests/otf/inp_rewr.xml";
//	String fsName = "testsv3/ins1/inp_rewr.xml";	
//	testsv3\ins3\inp_rewr.xml
	
	static NamespacesInfos	nss	= new NamespacesInfos(new String[][] { 
			{ "", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze" },
			{ "h", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader" },
			{ "i", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude" },
	 });	
	

	
	PendenzeModelExecutionDispatcherFactory serviceFactory =
		new PendenzeModelExecutionDispatcherFactory();
	
	PendenzeTestConfiguration testConfiguration = TestEnvironments.colltas().getTestConfiguration();
	
	
	static void showErrs(List/*<DBError>*/ errors, String title) {
		System.out.println(title);
		for (Iterator it = errors.iterator(); it.hasNext();) {
			DBError err = (DBError) it.next();
			System.out.println(err);
		}
	}
	
	static File bigXmlFolder = new File("big-xmls");
	
	public void _testseqs() {
		testConfiguration.initializeDB();
//		InputsFactory i1 = ResourceLoaders.FILESYSTEM.readerFactory(new File(bigXmlFolder,  "v3_5.xml").getAbsolutePath());
//		InputsFactory i1 = ResourceLoaders.CLASSPATH.readerFactory("tests/ins1.xml");
		InputsFactory i1 = ResourceLoaders.CLASSPATH.readerFactory("tests/ins21.xml");
		importXml(i1, serviceFactory, testConfiguration);
	}
	
	public void _testseqs2() {
		File contFolder = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/big-xmls");
		File inpFile = new File(contFolder, "v3_30-isolated.xml");
		String fn = inpFile.getAbsolutePath();
		testConfiguration.initializeDB();
		testFileFS(fn, serviceFactory, testConfiguration);
	}
	
	public void _test2() {
		String[] classpathNames = new String[] {
				"tests/v03/del1.xml"  ,
				"tests/v03/del2.xml"  ,
				"tests/v03/ins1.xml"  ,
				"tests/v03/ins2.xml"  ,
				"tests/v03/ins3.xml"  ,
				"tests/v03/repl1.xml" ,
				"tests/v03/repl2.xml" ,
				"tests/v03/upd2.xml"  ,
				"tests/v03/updt1.xml" ,
		};
//		List/*<NameAndExceptionPair>*/ errs = new ArrayList();
		for (int i = 0; i < classpathNames.length; i++) {
			String classpathNm = classpathNames[i];
			testConfiguration.initializeDB();
			testFile(classpathNm);
		}
	}
	
	public void _test1() throws Exception {
//		CleanDb.main(null);
		testConfiguration.initializeDB();
		testFile("tests/v03/del2.xml");
		
	}

	public void testFile(String classpathName) {
		testFile(classpathName, serviceFactory, testConfiguration);		
	}
	
	/**
	 * 
	 * @param fsName
	 * @param serviceFactory
	 * @param testConfiguration
	 */
	public void testFileFS(String fsName, PendenzeModelExecutionDispatcherFactory serviceFactory, PendenzeTestConfiguration testConfiguration) {
		long start = System.currentTimeMillis();
		List errs = importXml(ResourceLoaders.FILESYSTEM, fsName, serviceFactory,
				testConfiguration);
		
		long finished = System.currentTimeMillis();
		
		long duration = finished - start;
		System.out.println("\n****\n");
		System.out.println("file : " + fsName );
		System.out.println("spent " + duration + ".ms" );
		
		for (Iterator it = errs.iterator(); it.hasNext();) {
			DBError err = (DBError) it.next();
			System.out.println(err);
		}
	}
	
	/**
	 * 
	 */
	public void test() {
		System.out.print("It works");
		
		String fsName = this.fsName;
		
		PendenzeModelExecutionDispatcherFactory serviceFactory = this.serviceFactory;
		PendenzeTestConfiguration testConfiguration = TestEnvironments.colltas().getTestConfiguration();
		
		System.out.print("fileName = " + fsName);
		
		List errs = importXml(ResourceLoaders.FILESYSTEM, fsName, serviceFactory,
				testConfiguration);
		
		System.out.print("Finished" );
	}	
	
	
	/**
	 *
	 * @param contents
	 * @param message 
	 * @throws FactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	private  String[] readXml(String idMsgTrtTag1,String idMsgTrtTag2,String idMsgTrtTag3,String idMsgTrtTag4,String idMsgTrtTag5, String idMsgTrtTag6, String contents) throws FactoryConfigurationError,
			FileNotFoundException, XMLStreamException {

		String[] tags = new String[6];
		tags[5] = "NOT FOUND";

		//Creo input factory
		XMLInputFactory factory = XMLInputFactory.newInstance();
	    Reader fileReader = new StringReader(contents);
	    XMLEventReader reader = factory.createXMLEventReader(fileReader);

	    //lista tag necessari
	    boolean idMsgTrtFound1 = false;
	    boolean idMsgTrtFound2 = false;
	    boolean idMsgTrtFound3 = false;
	    boolean idMsgTrtFound4 = false;
	    boolean idMsgTrtFound5 = false;
	    boolean idMsgTrtFound6 = false;

	    //bean di uscita
	    String dati = "";

	    //leggo dal reader
	    while (reader.hasNext()) {
	      //evento
	      XMLEvent event = reader.nextEvent();
	      //entro se è l'apertura di un elemento
	      if (event.isStartElement()) {
	        StartElement element = (StartElement) event;
//	        logger.info("Start Element: " + element.getSimpleName().getLocalPart());

	        //cerco il tag che mi interessa
	        idMsgTrtFound1 = findTag(idMsgTrtTag1, idMsgTrtFound1, element);
	        idMsgTrtFound2 = findTag(idMsgTrtTag2, idMsgTrtFound2, element);
	        idMsgTrtFound3 = findTag(idMsgTrtTag3, idMsgTrtFound3, element);
	        idMsgTrtFound4 = findTag(idMsgTrtTag4, idMsgTrtFound4, element);
	        idMsgTrtFound5 = findTag(idMsgTrtTag5, idMsgTrtFound5, element);
	        idMsgTrtFound6 = findTag(idMsgTrtTag6, idMsgTrtFound6, element);
	        //stampo gli attributi del tag
	        Iterator iterator = element.getAttributes();
	        while (iterator.hasNext()) {
	          Attribute attribute = (Attribute) iterator.next();
	          QName name = attribute.getName();
	          String value = attribute.getValue();
	        }
	      }
	      //entro se è la chiusura di un elemento
	      if (event.isEndElement()) {
	        EndElement element = (EndElement) event;
	      }
	      //entro e stampo il contenuto di un tag
	      if (event.isCharacters()) {
	        Characters characters = (Characters) event;
	        idMsgTrtFound1 = getTagValue(tags, 0,idMsgTrtFound1, characters);
	        idMsgTrtFound2 = getTagValue(tags, 1,idMsgTrtFound2, characters);
	        idMsgTrtFound3 = getTagValue(tags, 2,idMsgTrtFound3, characters);
	        idMsgTrtFound4 = getTagValue(tags, 3,idMsgTrtFound4, characters);
	        idMsgTrtFound5 = getTagValue(tags, 4,idMsgTrtFound5, characters);
	        idMsgTrtFound6 = getTagValue(tags, 5,idMsgTrtFound6, characters);
	      }
	    }

	    System.out.println(" DataStorageManager, trovati tags = " + tags[0] + " - " + tags[1] + " - " + tags[2] 
	    		+ " - " + tags[3] + " - " + tags[4]  + " - " + tags[5] );
	    return tags;
	}
	
	
	/**
	 * 
	 * @param nomeFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public String readXmlFile(String nomeFile) throws FileNotFoundException {
	    File file = new File(nomeFile);

	    StringBuilder contents = new StringBuilder();
	    //use buffering, reading one line at a time
	    //FileReader always assumes default encoding is OK!
	    BufferedReader input =  new BufferedReader(new FileReader(file));
	    String line = null; //not declared within while loop
	    /*
	    * readLine is a bit quirky :
	    * it returns the content of a line MINUS the newline.
	    * it returns null only for the END of the stream.
	    * it returns an empty String if two newlines appear in a row.
	    */
	    try {
			while (( line = input.readLine()) != null){
			  contents.append(line);
			  contents.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents.toString();
	}
	
	
	/**
	 *
	 * @param tags
	 * @param pos
	 * @param idMsgTrtFound1
	 * @param characters
	 * @return
	 */
	private boolean getTagValue(String[] tags,int pos, boolean idMsgTrtFound,
			Characters characters) {
		if (idMsgTrtFound) {
			tags[pos] = characters.getData().trim();
			idMsgTrtFound = false;
		}
		return idMsgTrtFound;
	}
	
	
	/**
	 * 
	 * @param classpathName
	 * @param serviceFactory
	 * @param testConfiguration
	 */
	public void testFile(String classpathName, PendenzeModelExecutionDispatcherFactory serviceFactory, PendenzeTestConfiguration testConfiguration) {
		long start = System.currentTimeMillis();
		List errs = importXml(classpathName, serviceFactory,
				testConfiguration);
		
		long finished = System.currentTimeMillis();
		
		long duration = finished - start;
		System.out.println("\n****\n");
		System.out.println("file : " + classpathName );
		System.out.println("spent " + duration + ".ms" );
		
		for (Iterator it = errs.iterator(); it.hasNext();) {
			DBError err = (DBError) it.next();
			System.out.println(err);
		}		
	}
	

	/**
	 *
	 * @param idMsgTrtTag1
	 * @param idMsgTrtFound1
	 * @param element
	 * @return
	 */
	private boolean findTag(String idMsgTrtTag1, boolean idMsgTrtFound1,
			StartElement element) {
		if (element.getName().getLocalPart().equals(idMsgTrtTag1)) {
			idMsgTrtFound1 = true;
		}
		return idMsgTrtFound1;
	}
	
	
	public static List/*<DBError>*/ importXml(String classpathName,
			PendenzeModelExecutionDispatcherFactory serviceFactory,
			PendenzeTestConfiguration testConfiguration) {
		return importXml(ResourceLoaders.CLASSPATH, classpathName, serviceFactory, testConfiguration);
	}

	public static List/*<DBError>*/ importXml(ReaderLoader inputsFactory, String resName,
			PendenzeModelExecutionDispatcherFactory serviceFactory,
			PendenzeTestConfiguration testConfiguration) {
		InputsFactory impRes = inputsFactory.readerFactory(resName);
		return importXml(impRes, serviceFactory, testConfiguration);
	}

	/**
	 * 
	 * @param impRes
	 * @param serviceFactory
	 * @param testConfiguration
	 * @return
	 */
	public static List importXml(InputsFactory impRes,
			PendenzeModelExecutionDispatcherFactory serviceFactory,
			PendenzeTestConfiguration testConfiguration) {
		
		SqlMapClient sqlMapClient = testConfiguration.getSqlMapClient();
		EsitiErrorHandler ibatisErrHandler = new EsitiErrorHandler( sqlMapClient, testConfiguration.getTablesUidProvider() );
		Simple hndlr = new DBErrorsHandlers.Simple(ibatisErrHandler);
		
		PendenzeModelExecutionDispatcher dispatcher = 
			serviceFactory.create(sqlMapClient, testConfiguration.getTablesUidProvider(), hndlr);
		
		HeaderEffect headerEffect = null;
		OtfEffect otfEffect = new OtfModelExecutionDispatcher(null);
//		InsertPendenzeCartHeaderEffect headerEffect = 
//			new InsertPendenzeCartHeaderEffect(testConfiguration, testConfiguration.getTableNames().getPendenzeCart());
		FromXmlToDbPendenze xml2SqlCmd = newFromXml2Sql(headerEffect, otfEffect, dispatcher, ibatisErrHandler, serviceFactory.getErrorsFactory());
		
		xml2SqlCmd.dispatch(impRes);
		return hndlr.getAllErrors();
	}
	
	/**
	 * 
	 * @param headerEffect
	 * @param otfEffect
	 * @param execProvider
	 * @param errHandler
	 * @param errsFactory
	 * @return
	 */
	private static FromXmlToDbPendenze newFromXml2Sql(
			HeaderEffect headerEffect,
			OtfEffect otfEffect,
			PendenzeModelEffect execProvider,
			IBatisPendenzeErrorHandler errHandler,
			PendenzeDBErrorsFactory errsFactory) {
		
		PendenzaXPathValidator xpathValidator = new PendenzaXPathValidator(errHandler, errsFactory);
		FromXmlToDbPendenze xml2SqlCmd = new FromXmlToDbPendenze(headerEffect, otfEffect, execProvider, xpathValidator, nss);
		
		return xml2SqlCmd;
	}

}
