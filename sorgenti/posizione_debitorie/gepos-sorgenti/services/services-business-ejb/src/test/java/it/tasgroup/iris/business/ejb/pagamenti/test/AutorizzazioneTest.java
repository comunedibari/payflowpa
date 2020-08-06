package it.tasgroup.iris.business.ejb.pagamenti.test;

import static org.junit.Assert.*;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneLocal;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.MultaDTO;
import it.tasgroup.iris.dto.PendenzaDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutorizzazioneTest {
	static Context context;
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception{
		System.out.println("Inizializzo context");
		
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        p.put("jdbc/iris", "new://Resource?type=DataSource");
        p.put("jdbc/iris.JdbcDriver", "com.ibm.db2.jcc.DB2Driver");
        p.put("jdbc/iris.JdbcUrl", "jdbc:db2://10.1.128.153:50000/IDPDB:currentSchema=IRIS2;");
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
	public void testAutorizzaPagamento() throws Exception {
		AutorizzazioneLocal bean = (AutorizzazioneLocal) context.lookup("java:global/services-business-ejb/AutorizzazioneBusiness");
		
		RichiestaAutorizzazioneDTO dto = new RichiestaAutorizzazioneDTO();
		TestataMessaggioDTO testata = new TestataMessaggioDTO();
		testata.setSenderSys("01030");
		testata.setSenderSil("GMC-ATM");
        SessionIdDTO dtoSess = new SessionIdDTO();
        dtoSess.setIdSistema("AAA");
        dtoSess.setIdTerminale("AAA");
        dtoSess.setToken("AAA");
        dtoSess.setDataOraAccesso("AAA");
        testata.setSession(dtoSess);
		dto.setTestata(testata);
		dto.setCodicePagamentoIris("3359617698400001");
		dto.setCanale("ATM");
		
		EsitoOperazionePagamentoDTO  result = bean.autorizzaPagamento(dto);
		
		System.out.println("" + result.getDescrizione());
		
		String codiceResult = result.getCodice();
		String descrizioneResult = result.getDescrizione();
		//testo se autorizzato
		assertTrue(codiceResult.equals(EnumBusinessReturnCodes.OK.getChiave()));
	}
	
	@Test
	public void testAutorizzaMulta() throws NamingException{
		AutorizzazioneLocal bean = (AutorizzazioneLocal) context.lookup("java:global/services-business-ejb/AutorizzazioneBusiness");
		RichiestaAutorizzazioneDTO dto = new RichiestaAutorizzazioneDTO();
		TestataMessaggioDTO testata = new TestataMessaggioDTO();
		testata.setSenderSys("01030");
		testata.setSenderSil("GMC-ATM");
        
		SessionIdDTO dtoSess = new SessionIdDTO();
        dtoSess.setIdSistema("AAA");
        dtoSess.setIdTerminale("AAA");
        dtoSess.setToken("AAA");
        dtoSess.setDataOraAccesso("AAA");
        testata.setSession(dtoSess);
		dto.setTestata(testata);
        
		MultaDTO multadto = new MultaDTO();
		multadto.setTarga("AX106AA");
		multadto.setNumVerbale("8");
		multadto.setImporto("0");
		multadto.setData("020312");
		multadto.setSerie("A");
		dto.setMulta(multadto);
		
		dto.setCanale("ATM");
        
		EsitoOperazionePagamentoDTO result = bean.autorizzaPagamento(dto);
		
		System.out.println(result.toString());
		
		String codiceResult = result.getCodice();
		//testo se autorizzato
		assertEquals(EnumBusinessReturnCodes.OK.getChiave(), codiceResult);
		
	}
	
	@Test
	public void testAutorizzaCarrello() throws Exception {
		AutorizzazioneLocal bean = (AutorizzazioneLocal) context.lookup("java:global/services-business-ejb/AutorizzazioneBusiness");
		
		RichiestaAutorizzazioneDTO dto = new RichiestaAutorizzazioneDTO();
		TestataMessaggioDTO testata = new TestataMessaggioDTO();
		testata.setSenderSil("WEB");
		testata.setSenderSys("BT");
        SessionIdDTO dtoSess = new SessionIdDTO();
        dtoSess.setIdSistema("idSys");
        dtoSess.setIdTerminale("idterm");
        dtoSess.setToken("token");
        dtoSess.setDataOraAccesso("TODAY THIS TIME");
        testata.setSession(dtoSess);
		dto.setTestata(testata); 
		PendenzaDTO pendenza1 = new PendenzaDTO();
		List<PendenzaDTO> pendenze= new ArrayList<PendenzaDTO>();
		pendenze.add(pendenza1);
		List<String> listaCondizioni = new ArrayList();
		listaCondizioni.add("17223               ");
		listaCondizioni.add("17224               ");
		pendenza1.setCondizioni(listaCondizioni);
        dto.setPendenze(pendenze);
        dto.setCanale("ATM");
		EsitoOperazionePagamentoDTO  result = bean.autorizzaPagamento(dto);
		
		System.out.println("" + result.getDescrizione());
		
		String codiceResult = result.getCodice();
		String descrizioneResult = result.getDescrizione();
		//testo se autorizzato
		assertTrue(codiceResult.equals(EnumBusinessReturnCodes.OK.getChiave()));
	}
	
	

}
