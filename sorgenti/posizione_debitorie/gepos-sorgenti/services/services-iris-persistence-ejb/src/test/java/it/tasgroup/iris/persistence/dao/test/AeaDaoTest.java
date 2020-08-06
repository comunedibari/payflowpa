package it.tasgroup.iris.persistence.dao.test;

import it.nch.is.fo.CommonConstant;
import it.nch.utility.iban.IbanHelper;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllineamentiElettroniciArchiviDao;
import it.tasgroup.services.util.enumeration.EnumCausale;
import it.tasgroup.services.util.enumeration.EnumStatoAEA;

import java.sql.Timestamp;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AeaDaoTest  {
	static Context context;
	@BeforeClass
	public static void oneTimeSetUp() throws Exception{
		System.out.println("Inizializzo context");
		
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        p.put("jdbc/iris", "new://Resource?type=DataSource");
        p.put("jdbc/iris.JdbcDriver", "com.ibm.db2.jcc.DB2Driver");
        p.put("jdbc/iris.JdbcUrl", "jdbc:db2://10.1.128.153:50000/IDPDB:currentSchema=IDP2;");
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
	public void testCreaDelegaCampiObbligatori() {
	
		try {
			AllineamentiElettroniciArchiviDao toTest = (AllineamentiElettroniciArchiviDao) context.lookup("java:global/services-iris-persistence-ejb/AllineamentiElettroniciArchiviDaoService");
			AllineamentiElettroniciArchiviDTO dto = new AllineamentiElettroniciArchiviDTO();
			
			// campi obbligatori
			
			dto.setIbanAddebito("123456789012345678901234567");
			dto.setCodificaFiscaleSottoscrittore("FBRMNL75M45H901U");
			dto.setProgressivo(new Integer(0));
			dto.setCittadino(true);
			dto.setDataRichiesta(new Timestamp(System.currentTimeMillis()));
			
			dto.setTipoCodIndividuale("T");
			
//			dto.setDataAttivazione(new Timestamp(System.currentTimeMillis()));
//			dto.setDataCessazione(new Timestamp(System.currentTimeMillis()));
			
			dto.setCausale(EnumCausale.C_90211.getChiave());
			dto.setStato(EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave());
			dto.setDescrizioneStato(EnumStatoAEA.IN_CORSO_APPROVAZIONE.getDescrizione());
			dto.setCodificaFiscaleSottoscrittore("FBRMNL75M45H901U");
			dto.setRagSocSottoscrittore("Ema Fab");
			dto.setIntestatario("00000000000000000001");
			IbanHelper ibanHelper = new IbanHelper("IT22T0103002818000008600053");
			dto.setAbiBancaAllineamento(ibanHelper.getAbi());
			
		    AllineamentiElettroniciArchivi delega = toTest.createAEA(dto);
		    System.out.println("test - createAEA: id: "+delega.getId());
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
    @Test	
   	public void testUpdateDelegaRevoca() {
		try {
			AllineamentiElettroniciArchiviDao toTest = (AllineamentiElettroniciArchiviDao) context.lookup("java:global/services-iris-persistence-ejb/AllineamentiElettroniciArchiviDaoService");
			AllineamentiElettroniciArchivi aea = toTest.getById(AllineamentiElettroniciArchivi.class, new Long(6));
			AllineamentiElettroniciArchivi revoca = toTest.getById(AllineamentiElettroniciArchivi.class, new Long(5));
			
			aea.setStato(EnumStatoAEA.IN_CORSO_REVOCA.getChiave());
			aea.setDescrizioneStato(EnumStatoAEA.IN_CORSO_REVOCA.getDescrizione());
			aea.setRevoca(revoca);
			
		    AllineamentiElettroniciArchivi delega = toTest.updateAEA(aea);
		    System.out.println("test - updateAEA: id: "+delega.getId());
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}

   
}
