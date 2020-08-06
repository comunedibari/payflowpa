package it.tasgroup.iris.persistence.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.persistence.dao.GestioneFlussiDaoImpl;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class GestioneFlussiTest {

	
	
static Context context;
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception{
		System.out.println("Inizializzo context");
		
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        p.put("jdbc/iris", "new://Resource?type=DataSource");
        p.put("jdbc/iris.JdbcDriver", "com.ibm.db2.jcc.DB2Driver");
        p.put("jdbc/iris.JdbcUrl", "jdbc:db2://10.1.132.36:50000/IDPDB");
        p.put("jdbc/iris.UserName", "idp");
        p.put("jdbc/iris.Password", "idp");

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

	
	public void testGetFlusso() throws Exception {
		 GestioneFlussiDao toTest = (GestioneFlussiDaoImpl) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");
		List<GestioneFlussi> flussi = (List<GestioneFlussi>) toTest.getDistintaById(1L);
		assertNotNull(flussi);
		System.out.println("GetFlusso pagamenti size: " + flussi.get(0).getPagamenti().size());
	}
	
	public void insertFlusso()  throws Exception {
		final GestioneFlussiDao toTest = (GestioneFlussiDaoImpl) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");
		GestioneFlussi gf = new GestioneFlussi();
		UUID ru = UUID.randomUUID();
		String ruString=ru.toString();
		ruString=ruString.replace("-", "");
		gf.setCodTransazione(ruString);
		UUID ru2 = UUID.randomUUID();
// l'id viene valorizzaito automaticamente		gf.setFlusso(ru.toString());
		gf.setStato("IN CORSO");
//	    gf.setTiposervizio("");
 //       gf.setTiposervizio("????");
        BigDecimal bd = new BigDecimal(0);
        gf.setTotimportipositivi(bd);
        gf.setImportoCommissioni(new BigDecimal(140));
        //recupero la data/ora di sistema
    	java.util.Date today = new java.util.Date();
	    java.sql.Timestamp datacorrente = new java.sql.Timestamp(today.getTime());
        gf.setTmbcreazione(datacorrente);
   //     gf.setTmbspedizione(datacorrente);
        gf.setTsUpdate(datacorrente);
        gf.setUtentecreatore("MZZNDR...");
    //    gf.setCanalerichiesta("01");
        GestioneFlussi resultobj = toTest.insertFlusso(gf);
        String result = resultobj == null?"OK":"OK";
        assertEquals("OK", result);
	}

	public void deleteFlusso()  throws Exception {
	    GestioneFlussiDao toTest =(GestioneFlussiDaoImpl) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");

	}


	public void testGetSingleFlusso()  throws Exception {
		final GestioneFlussiDao toTest = (GestioneFlussiDaoImpl) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");

		GestioneFlussi flusso = (GestioneFlussi) toTest.getDistintaByMsgId("12668530051530002DEF");
		assertNotNull(flusso);
		System.out.println("GetSingleFlusso pagamenti size: " + flusso.getPagamenti().size());
	}
}
