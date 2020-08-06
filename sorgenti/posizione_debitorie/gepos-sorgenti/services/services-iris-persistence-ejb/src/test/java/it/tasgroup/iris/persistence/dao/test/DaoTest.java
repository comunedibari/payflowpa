package it.tasgroup.iris.persistence.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.dto.ListaDocumentiInputDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.persistence.dao.interfaces.DDPDAO;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class DaoTest {
	
	static Context context;
	@BeforeClass
	public static void oneTimeSetUp() throws Exception{
		System.out.println("Inizializzo context");
		
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        p.put("jdbc/iris", "new://Resource?type=DataSource");
        p.put("jdbc/iris.JdbcDriver", "com.ibm.db2.jcc.DB2Driver");
        p.put("jdbc/iris.JdbcUrl", "jdbc:db2://10.1.128.153:50000/IDPDB:currentSchema=IRIS2;");
        p.put("jdbc/iris.UserName", "idpfe");
        p.put("jdbc/iris.Password", "idpfe");

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
	public void testFilterDDPByParameters() throws Exception{
		DDPDAO toTest =(DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		Timestamp tomorrow = new Timestamp(111, 11, 23, 24, 0, 0, 0);
		
		Timestamp yesterday = new Timestamp(111, 11, 20, 1, 0, 0, 0);	
		
		ListaDocumentiInputDTO dto = new ListaDocumentiInputDTO();
		dto.setDocStateFilter(EnumStatoDDP.PAGATO);
		dto.setDocTypeFilter(EnumTipoDDP.ATM);
		dto.setStartDateFilter(yesterday);
		dto.setEndDateFilter(tomorrow);
		
		List<DocumentoDiPagamento> resultList = null;
		//TODO PAZZIK aggiungere il profilo (MOCK?) ai parametri di ingresso
				//toTest.listDDPByFilterParameters(dto);
		
		assertNotNull(resultList);
		
		for (DocumentoDiPagamento ddp : resultList){
			assertTrue(ddp.getStato().equals(EnumStatoDDP.PAGATO.getChiave()));
			assertTrue(ddp.getTipoDocumentoEnum().equals(EnumTipoDDP.ATM));
		}
		
	}
	
	@Test
	public void testFilterDDPByIdCondizione() throws Exception{
		DDPDAO toTest = (DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;
		
		String idCondizione = "17222";
		
		List<DocumentoDiPagamento> resultList = toTest.listDDPByIdCondizione(idCondizione);
		
		assertNotNull(resultList);
		
		for (DocumentoDiPagamento ddp : resultList){
			Set<CondizioneDocumento> cond =  ddp.getCondizioni();
			for (Iterator iterator = cond.iterator(); iterator.hasNext();) {
				CondizioneDocumento condizioneDocumento = (CondizioneDocumento) iterator.next();
			    CondizionePagamento cp = condizioneDocumento.getCondizionePagamento();
			    Pendenza pend = cp.getPendenza();
			    Set<DestinatariPendenza> dest = pend.getDestinatari();
                for (Iterator iterator2 = dest.iterator(); iterator2.hasNext();) {
					DestinatariPendenza destinatariPendenza = (DestinatariPendenza) iterator2
							.next();
					System.out.println(destinatariPendenza.getCoDestinatario());
				}
			}
		}
		
	}
	
	@Test
	public void testCountDDPByIdCondizione() throws Exception{
		
		DDPDAO toTest = (DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;
		
		String idCondizione = "17222";
		
		Integer counter = toTest.countDDPByIdCondizione(idCondizione);
		
		assertTrue(counter == 1);
		
	}
	
	@Test
	public void testFilterDDPById() throws Exception{
		DDPDAO toTest = (DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;
		
		DocumentoDiPagamento result = toTest.retrieveDDPById("RT3348492080420133");
		
		assertNotNull(result);
		//assertTrue(result.getCondizioni().size()!=0);
		
	}
	
	@Test
	public void testSaveDDP() throws Exception{
		
		EnumStatoDDP statoTest = EnumStatoDDP.ANNULLATO_UTENTE;
		EnumTipoDDP	 tipoTest = EnumTipoDDP.BONIFICO;
		 
		DDPDAO toTest =(DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;

	    
	    DDPInputDTO ddp = new DDPInputDTO();		
		ddp.setTipo(tipoTest);
		List condizioniCarrello = new ArrayList<String>();
        ddp.setCondizioniCarrello(condizioniCarrello);
        
/*	//	DocumentoDiPagamento ddpSaved = toTest.createDDP(ddp);
		

		
		assertNotNull(ddpSaved);
		assertNotNull(ddpSaved.getId());
		assertTrue(ddpSaved.getId().length()>=16);

		DocumentoDiPagamento result = toTest.retrieveDDPById(ddpSaved.getId());
		
		assertTrue(result.getTsEmissione().equals(ddpSaved.getTsEmissione()));
		assertTrue(result.getTipoEnum().equals(tipoTest));
		assertTrue(result.getStatoEnum().equals(statoTest));*/
		
	}
	
	@Test	
	public void testUpdateDDP()throws Exception{
		
		DDPDAO toTest = (DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;
		
		EnumStatoDDP statoTest = EnumStatoDDP.ANNULLATO_UTENTE;
		EnumTipoDDP	 tipoTest = EnumTipoDDP.BONIFICO;
		

	    
	/*    DocumentoDiPagamento ddp = toTest.retrieveDDPById("0000000000000001");
		
		ddp.setStatoEnum(statoTest);
		ddp.setTipoEnum(tipoTest);
		DocumentoDiPagamento ddpUpdated = toTest.updateDDP(ddp);

		
		assertNotNull(ddpUpdated);
		assertNotNull(ddpUpdated.getId());
		assertTrue(ddpUpdated.getId().equals("0000000000000001"));

		DocumentoDiPagamento result = toTest.retrieveDDPById("0000000000000001");
		assertTrue(result.getTsEmissione().equals(ddpUpdated.getTsEmissione()));
		assertTrue(result.getTipoEnum().equals(tipoTest));
		assertTrue(result.getStatoEnum().equals(statoTest));*/
		
	}
	
	@Test
	public void testListCondizioniDocumento() throws Exception{
		DDPDAO toTest = (DDPDAO) context.lookup("java:global/services-iris-persistence-ejb/DocumentoDiPagamentoDao");;
		Map <String, Object> parameters = new HashMap<String, Object>();
		parameters = new HashMap<String, Object>();
		parameters.put("codiceAutorizzazione", "0000000000000001");

		List<DocumentoDiPagamento> resultList;
		int ioi = 0;
		try {
			resultList = (List<DocumentoDiPagamento>) toTest.listByQuery("getDocumentoDiPagamentoByIdDocumento", parameters);
			ioi = resultList.get(0).getCondizioni().iterator().next().getCondizionePagamento().getPagamenti().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertTrue(ioi>0);
	}
	

	
	
}
