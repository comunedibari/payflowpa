package it.tasgroup.idp.proxysiope.gdc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.proxyndp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumFlagElaborazione;
import it.tasgroup.iris2.pagamenti.CasellarioInfo;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Remote(CommandExecutor.class)
public class RichiestaGiornaleDiCassaSiope implements CommandExecutor, CommandExecutorLocal {
	

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;	
	
 	@EJB(beanName = "GiornaleDiCassaSiopeManager")
 	private ExecutorLocal giornaleDiCassaSiopeManagerProxy;
	
	private final Log logger = LogFactory.getLog(this.getClass());

	private static final String ENDPOINT_SIOPE_GDC = "iris.riconciliazioni.siope.gdc.endpoint";
	private static final String RESOURCE_SIOPE_GDC = "iris.riconciliazioni.siope.gdc.resource";
	
	public static final String ABI_TESORIERA = "05034";
	public static final String TIPO_FLUSSO = "OPI";
	public static final String PREFISSO_ID_SUPPORTO = "SIOPE-";
	
	public RichiestaGiornaleDiCassaSiope() {
		
	} 
	
	/*
	 * 1. richiesta a siope della lista dei giornali di cassa (torna al massimo una unica pagina con 50 risultati)
	 * 2. controllo dei progressivoGiornale tornati con quelli presenti sulla casellario_info
	 * 3. loop sui progressivoGiornale non presenti nella casellario_info
	 *    1. scarico del giornale di cassa
	 *    2. inserimento nella casellario info
	 * 
	 */
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MonitoringData executeApplicationTransaction() {

		// richiesta a siope della lista dei gdc disponibili
		
		String endpoint = IrisProperties.getProperty(ENDPOINT_SIOPE_GDC); // "https://pdastage.tix.it"
		if (endpoint == null || endpoint.trim().isEmpty()) {
			logger.error("Parametro " + ENDPOINT_SIOPE_GDC + " non definito nel file di properties");
			return null;
		}
		String resource = IrisProperties.getProperty(RESOURCE_SIOPE_GDC); // "/pdav4/oauth/in/RegioneToscana/GiornaliCassa/v1/giornali"
		if (resource == null || resource.trim().isEmpty()) {
			logger.error("Parametro " + RESOURCE_SIOPE_GDC + " non definito nel file di properties");
			return null;
		}
		
		String responseString = null;
		JSONArray risultati = null;
		try {
			Client client = new Client();
			byte[] response = client.send(endpoint + resource);
			responseString = new String(response, "UTF-8");
    		JSONObject jsonObject = new JSONObject(responseString);
    		risultati = jsonObject.getJSONArray("risultati");
		} catch (Exception e) {
			throw new RuntimeException("Errore get lista GdC da Siope [" + responseString + "]", e);
		}

		logger.info("Risposta Siope lista GdC [" + responseString + "]");
		
		// ciclo sulla lista dei gdc disponibili su siope
		
		logger.info("GdC disponibili su Siope: " + risultati.length());
		for (Object risultato : risultati) {
			
			String progressivoGiornale = ((JSONObject)risultato).getString("progressivoGiornale");
			String codiceEnte = ((JSONObject)risultato).getString("codiceEnte");
			
			// controllo che il gdg non sia gia' presente sulla casellario info
			
			Query listaCasellarioInfoQuery = manager.createQuery ("SELECT ci FROM CasellarioInfo ci WHERE idSupporto = :idSupporto");
			listaCasellarioInfoQuery.setParameter("idSupporto", PREFISSO_ID_SUPPORTO + progressivoGiornale);
			List<CasellarioInfo> listaCasellarioInfo =  (List) listaCasellarioInfoQuery.getResultList();
			
			if (listaCasellarioInfo.isEmpty()) { // se il gdc non era gia' stato scaricato, lo scarichiamo
				
				logger.info("GdC non presente sul casellario: progressivoGiornale [" + progressivoGiornale + "]");

				ByteArrayOutputStream gdc = new ByteArrayOutputStream();
				String zipEntryName = null;
				try {
					Client client = new Client();
					byte[] zip = client.send(endpoint + resource + "/" + progressivoGiornale + "/raw");
					ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip));
					
			        ZipEntry zipEntry = zis.getNextEntry();
			        byte[] buffer = new byte[1024];
			        int countEntry = 0;
			        while (zipEntry != null) {
			        	zipEntryName = zipEntry.getName();
			            int len;
			            while ((len = zis.read(buffer)) > 0) {
			                gdc.write(buffer, 0, len);
			            }
			            zipEntry = zis.getNextEntry();
			            countEntry++;
			        }
			        gdc.close();

			        if (countEntry != 1) {
			        	logger.error("Errore get GdC [" + progressivoGiornale + "] da Siope: trovate " + countEntry + " nello zip. Si salta al prossimo");
			        	continue;
			        }
			        
				} catch (Exception e) {
					logger.error("Errore get GdC [" + progressivoGiornale + "] da Siope: " + e.getMessage() + " - Si salta al prossimo", e);
					continue;
				}
				
				// salvataggio sul casellario
				
				CasellarioInfo casellario = new CasellarioInfo();
				casellario.setFlussoCbi(gdc.toByteArray());
				casellario.setFlagElaborazione(EnumFlagElaborazione.DA_ELABORARE.getChiave());
				casellario.setTipoFlusso(TIPO_FLUSSO);
				casellario.setIdSupporto(PREFISSO_ID_SUPPORTO + progressivoGiornale);
				if (zipEntryName != null) {
					casellario.setNomeSupporto(zipEntryName.length() > 35 ? zipEntryName.substring(0, 35) : zipEntryName);
				} else {
					casellario.setNomeSupporto(casellario.getIdSupporto());
				}
				casellario.setMittente(ABI_TESORIERA);
				casellario.setMittenteVero(ABI_TESORIERA);
				casellario.setRicevente(codiceEnte);
				casellario.setRicevemteVero(codiceEnte);
				casellario.setDataCreazione(new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis())));
				casellario.setDataElaborazione(new Timestamp(System.currentTimeMillis()));
				casellario.setDimensione(gdc.toByteArray().length);
				casellario.setOpInserimento("RichiestaGiornaleDiCassaSiope");
				casellario.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
				giornaleDiCassaSiopeManagerProxy.executeApplicationTransaction(casellario);
				
			} else {
				logger.info("GdC gia' presente sul casellario: progressivoGiornale [" + progressivoGiornale + "]");
			}
		}
		
		return null;
	}

	@Override
	public String executeHtml() throws Exception  {
		return null;
	}

	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
		return manager;
		//quando siamo su TEST JUNIT ci vuole questa riga
//			return manager;
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		// TODO Auto-generated method stub
		return null;
	}


		
	

}
