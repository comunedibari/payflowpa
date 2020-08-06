package it.tasgroup.iris.persistence.dao.test;

import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;

import java.sql.Timestamp;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class PendenzaDaoTest {


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

        // p.put("jdbc/iris.JdbcUrl", "jdbc:db2://10.1.128.153:50000/IDPDB:currentSchema=IRIS2;");
        // p.put("jdbc/iris.UserName", "idp");
        // p.put("jdbc/iris.Password", "vqc.3-1");
        
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
	public void testGetByIDPendenza() throws Exception {
		try {	
			Pendenza result = getPendenza("454"); 
            int nDest = result.getDestinatari().size();
            System.out.println("pendenza:"+result);
		} catch (Exception e) {
			String c = "www";
			// TODO: handle exception
		}
	}

	private Pendenza getPendenza(String idPendenza) throws NamingException, Exception {
		PendenzaDao toTest =  (PendenzaDao) context.lookup("java:global/services-iris-persistence-ejb/PendenzaDaoService");
		Pendenza result = toTest.getById(Pendenza.class, idPendenza);
		return result;
	}

	@Test
	public void insertPendenzaFromTributo() throws Exception {
	
			PendenzaDao toTest =  (PendenzaDao) context.lookup("java:global/services-iris-persistence-ejb/PendenzaDaoService");
			/*
			PendenzaStrutturata<TributoConferimento> datiPendenza = new PendenzaStrutturata<TributoConferimento>();
			TributoConferimento tributo = new TributoConferimento();
			tributo.setCfSoggettoVersante("");
			tributo.setTsInserimento(getTime());
			DettaglioConferimento dettaglio = new DettaglioConferimento();
			Random r = new Random();
			char c2 = (char)(r.nextInt(26) + 'A');
			char c16 = (char)(r.nextInt(26) + 'A');
			String cfPassivo = "B" + c2 + "BBCL68D15F205"+c16;
			String cfVersante = "";
			String email = "test@testaroli.it";
			dettaglio.setAnnoRiferimento(2012);
			dettaglio.setCfSoggettoPassivo(cfPassivo);
			dettaglio.setEmail(email);
			dettaglio.setTsInserimento(getTime());
			dettaglio.setOpInserimento("IdP");
			dettaglio.setTrimestre("1");
			dettaglio.setDenominazioneImpianto("impianto di Milano");
			tributo.setNoteVersante("Note di test");

			dettaglio.setImporto(new BigDecimal("1.90"));
			tributo.setDettaglioStrutturato(dettaglio);
			datiPendenza.setTributoStrutturato(tributo);		
			// receupero dalla sessione
			datiPendenza.setCodiceTributo("CONFERIMENTI_DISCARICA");
			datiPendenza.setIdEnte("00000000000000000000");

			datiPendenza.setCodiceFiscaleIntestatario(dettaglio.getCfSoggettoPassivo());
			
		
			datiPendenza.setAnnoRiferimento(dettaglio.getAnnoRiferimento());
			// to complete
			datiPendenza.setCodiceFiscalePagante(tributo.getCfSoggettoVersante());
			datiPendenza.setImporto(dettaglio.getImporto());

			
			
		
            List<String> ids = toTest.createNewPendenza(datiPendenza);
            String idCondizione = ids.get(0);
            assertFalse("Non è stata ritornata la condizione", ids.isEmpty());	
            System.out.println("Id condizione : " + idCondizione);	*/
	}

	private Timestamp getTime() {
		return new Timestamp(System.currentTimeMillis());
	}

}
