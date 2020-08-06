package it.tasgroup.iris.business.ejb.pagamenti.test;

import static org.junit.Assert.assertNotNull;
import it.tasgroup.iris.business.ejb.client.pagamenti.StornoLocal;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.StornoDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StornoTest  {

	static Context context;
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception{
		System.out.println("Inizializzo context");
		
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        p.put("jdbc/iris", "new://Resource?type=DataSource");
        p.put("jdbc/iris.JdbcDriver", "com.ibm.db2.jcc.DB2Driver");
        p.put("jdbc/iris.JdbcUrl", "jdbc:db2://10.1.128.153:50000/IDPDB :currentSchema=IDP2;");
        p.put("jdbc/iris.UserName", "idp");
        p.put("jdbc/iris.Password", "vqc.3-1");

        p.put("log4j.rootLogger", "debug,C");
        // These two debug levels will get you the basic log information
        // on the deployment of applications. Good first step in troubleshooting.
        p.put("log4j.category.OpenEJB.startup", "info");
        p.put("log4j.category.OpenEJB.startup.config", "error");
        // This log category is a good way to see what "openejb.foo" options
        // and flags are available and what their default values are
        p.put("log4j.category.OpenEJB.options", "info");
        // This will output the full configuration of all containers
        // resources and other openejb.xml configurable items.  A good
        // way to see what the final configuration looks like after all
        // overriding has been applied.
        p.put("log4j.category.OpenEJB.startup.service", "info");
        p.put("log4j.category.org.hibernate", "error");
        // Want timestamps in the log output?
        p.put("log4j.appender.C.layout", "org.apache.log4j.PatternLayout");
        p.put("log4j.appender.C.layout.ConversionPattern", "%d - %-5p - %m%n");
        // Will output a generated ejb-jar.xml file that represents
        // 100% of the annotations used in the code.  This is a great
        // way to figure out how to do something in xml for overriding
        // or just to "see" all your application meta-data in one place.
        // Look for log lines like this "Dumping Generated ejb-jar.xml to"
        p.put("openejb.descriptors.output", "true");
        // Setting the validation output level to verbose results in
        // validation messages that attempt to provide explanations
        // and information on what steps can be taken to remedy failures.
        // A great tool for those learning EJB.
        p.put("openejb.validation.output.level", "verbose");        
        
        context = EJBContainer.createEJBContainer(p).getContext();
	}
	
	@AfterClass
	public static void oneTimeTearDown(){
		System.out.println("Test case chiuso");
	}
	
	@Test
    public void testStornoTransazione() throws Exception {
        StornoLocal bean = (StornoLocal) context.lookup("java:global/services-business-ejb/StornoBusiness");
        assertNotNull(bean);
        
        StornoDTO richiesta = new StornoDTO();
		TestataMessaggioDTO test = new TestataMessaggioDTO();
		SessionIdDTO sess = new SessionIdDTO();
		
		test.setSenderSil("WEB");
		test.setSenderSys("BT");
		
		sess.setDataOraAccesso("0802121107");
		sess.setIdSistema("01030");
		sess.setIdTerminale("0705");
		sess.setToken("000586543211123456041211000000");

		test.setSession(sess);
		
		richiesta.setTestata(test);
		richiesta.setCanale("ATM");
		richiesta.setCodiceTransazione("26ddf95af5ce4ca2838faedea421c243");
		
		EsitoOperazionePagamentoDTO ret = bean.storna(richiesta);
		
		assertNotNull(ret);
		
		System.out.println(ret.toString());
    }


}
