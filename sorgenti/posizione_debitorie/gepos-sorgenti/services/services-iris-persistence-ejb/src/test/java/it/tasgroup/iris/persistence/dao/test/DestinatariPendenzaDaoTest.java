package it.tasgroup.iris.persistence.dao.test;

import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.persistence.dao.interfaces.DestinatariPendenzaDao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class DestinatariPendenzaDaoTest {


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
    public void testlistByCodiceFiscaleAndStatoPendenza() throws Exception {
    	DestinatariPendenzaDao toTest = (DestinatariPendenzaDao)context.lookup("java:global/services-iris-persistence-ejb/DestinatariPendenzaDaoService");
    	Map<String, Object> parameters = new HashMap<String, Object>();
		List result = toTest.listaDestinatariByCodiceFiscaleAndStato("MZZNDR75L06E463O","DA PAGARE");	
		System.out.println(result.size());
    }
    
    @Test
    public void testlistByCodiceFiscale() throws Exception {
    	DestinatariPendenzaDao toTest = (DestinatariPendenzaDao)context.lookup("java:global/services-iris-persistence-ejb/DestinatariPendenzaDaoService");
    	Map<String, Object> parameters = new HashMap<String, Object>();
		List result = toTest.listaDestinatariByCodiceFiscale("MZZNDR75L06E463O");	
		
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			DestinatariPendenza dp = (DestinatariPendenza) iterator.next();
            Set condizioni = dp.getPendenza().getCondizioni();
            for (Iterator iterator2 = condizioni.iterator(); iterator2
					.hasNext();) {
				CondizionePagamento cp = (CondizionePagamento) iterator2.next();
				System.out.println(cp.getPagamenti().size());
			}
		}
		System.out.println(result.size());
    }
    
    @Test
    public void testlistByCodiceFiscaleAndDate() throws Exception {
    	DestinatariPendenzaDao toTest = (DestinatariPendenzaDao)context.lookup("java:global/services-iris-persistence-ejb/DestinatariPendenzaDaoService");
    	XMLGregorianCalendar dataIni = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(2012, 01, 31, 1);
    	XMLGregorianCalendar dataFin = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(2012 ,10 ,31, 1);    	
		List result = toTest.ListaDestinatariByCodiceFiscaleStatoAndDate("MZZNDR75L06E463O", "PAGATA", dataIni, dataFin);			
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			DestinatariPendenza dp = (DestinatariPendenza) iterator.next();
            Set condizioni = dp.getPendenza().getCondizioni();
            for (Iterator iterator2 = condizioni.iterator(); iterator2
					.hasNext();) {
				CondizionePagamento cp = (CondizionePagamento) iterator2.next();
				System.out.println(cp.getPagamenti().size());
			}
		}
		System.out.println(result.size());
    }

}
