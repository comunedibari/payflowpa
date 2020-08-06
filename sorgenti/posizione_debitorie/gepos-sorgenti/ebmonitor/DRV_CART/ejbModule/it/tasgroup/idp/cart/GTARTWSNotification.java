package it.tasgroup.idp.cart;


import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.aci.gtart.ws.pagamento.NuovoPagamentoWS;
import it.aci.gtart.ws.pagamento.NuovoPagamentoWSService;
import it.tasgroup.idp.bean.ResultKOException;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.StatoEnum;


public class GTARTWSNotification implements ISenderNotificationQueueDemux {

	private final Log logger = LogFactory.getLog(this.getClass());	 
	private static String  GTART_WS_NOTIFICATION_URL = "iris.Gtart.notif.url";
	
	public enum EnumEsitiGTARTWS  {
		
		PAGAMENTO_OK             ( 0,"pagamento inserito con successo","OK"),
		TIPO_TARGA_ERRATA        ( 1,"tipo targa non valorizzato o valore non riconosciuto","KO"),
		TARGA_ERRATA             ( 2,"targa non valorizzata o valorizzata in modo non corretto","KO"),
		DECORRENZA_ERRATA        ( 3 ,"decorrenza non corretta o non valorizzata","KO"),
		MESI_VALIDITA_ERRATI     ( 4,"mesi validità non corretti o non valorizzati","KO"),
		DATA_PAGAMENTO_ERRATA    ( 5,"data pagamento non valorizzata","KO"),
		TIPO_INTERMEDIARIO_ERRATO( 6 ,"tipo Intermediario non valorizzato o non riconosciuto","KO"),
		TASSA_NON_VALORIZZATA    ( 7 ,"tassa non valorizzata o importo non corretto","KO"),
		SANZIONI_NON_VALORIZZATE ( 8 ,"sanzioni non valorizzate","KO"),
		INTERESSI_NON_VALORIZZATI( 9 ,"interessi non valorizzati","KO"),
		TOTALE_ERRATO            (10,"il totale non è valorizzato o l’importo non corrisponde alla somma di tassa, sanzioni e interessi","KO"),
		CODICE_FISCALE_ERRATO    (11,"codice fiscale non valorizzato o non corretto","KO"),
		IUV_NON_VALORIZZATO      (12,"IUV non valorizzato","KO"),
		MSGID_NON_VALORIZZATO    (13,"MSGID non valorizzato","KO"),
		INTERMEDIARIO_ERRATO     (14,"IDIntermediario non valorizzato o non riconosciuto","KO"),
		REGBENEFICIARIA_ERRATA   (15,"RegBeneficiaria non valorizzato o non riconosciuto","KO"),
		REGVERSAMENTO_ERRATO     (16,"RegVersamento non valorizzato o non riconosciuto","KO"),
		MODPAGAMENTO_ERRATO      (17,"ModPagamento codifica non valorizzata o non riconosciuta","KO"),
		ERRORE_GENERICO          (18,"errore generico","RETRY");
		
		private Integer key;
		private String  description;
		private String  behaviour;

		EnumEsitiGTARTWS(Integer key, String descrizione, String behavior) {
			this.key = key;
			this.description = descrizione;
			this.behaviour = behavior;
		}

		
		public Integer getKey(){
			return key;
		}
		
		public String getDescription() {
			return description;
		}

		public String getBehaviour() {
			return this.behaviour;
		}
		
		public static EnumEsitiGTARTWS getByKey(Integer chiave) {
			EnumEsitiGTARTWS desiredItem = null; // Default
			for (EnumEsitiGTARTWS item : EnumEsitiGTARTWS.values()) {
				if (item.getKey().equals(chiave)) {
					desiredItem = item;
					break;
				}
			}
			return desiredItem;
		}
		@Override
		public String toString() {
			return key +" " + description + " " +behaviour;
		}

	}
	
	
	@Override 
	public Object delivery(NotificheCart nc,EntityManager manager) { 
		
		logger.info(this.getClass().getSimpleName() + " delivery() BEGIN " );
		try {
			String info4WS = new String(nc.getNotificaXml());
			
			logger.info(this.getClass().getSimpleName() + " stringa di notifica: "+info4WS);
			// estraggo le informazioni che poi vengono utilizzate per essere 
			// inviate al WS
			Map<String,String> m = tokenize(info4WS);
			
			NuovoPagamentoWSService ndp = new NuovoPagamentoWSService();
			NuovoPagamentoWS port = ndp.getNuovoPagamentoWSPort();
		
	        BindingProvider bindingProvider = (BindingProvider) port;
	        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,IrisProperties.getProperty(GTART_WS_NOTIFICATION_URL));	        
				        
	        String dataPagamentoStr = m.get("DATA_PAGAMENTO");
	        int yyyy = Integer.parseInt(dataPagamentoStr.substring(6));
	        int MM   = Integer.parseInt(dataPagamentoStr.substring(3, 5));
	        int dd   = Integer.parseInt(dataPagamentoStr.substring(0,2));
	        DatatypeFactory df = DatatypeFactory.newInstance();
			GregorianCalendar gc = new GregorianCalendar(yyyy, MM, dd);
			XMLGregorianCalendar dataPagamentoGregCal = df.newXMLGregorianCalendar(gc);
	        //
			String decorrenza = m.get("DECORRENZA");
			String dataDecorrenzaFormatted = null;
			if (decorrenza!=null) {
				dataDecorrenzaFormatted =  decorrenza.substring(6)+decorrenza.substring(3,5);
			}
			
			logger.info(" ========== Elaboro il flusso, idFlusso ======== ");
			logger.info("CB");
			logger.info("IRIS");
            logger.info(m.get("MSG_ID")); //msgid
            logger.info(m.get("ID_BOLLO")); //idBollo
            logger.info(m.get("IUV")); // iuv //PATCH
           	logger.info(new Integer(m.get("TIPO_VEICOLO"))); //tipoVeicolo,
           	logger.info(m.get("TARGA"));// targa,
           	logger.info(dataDecorrenzaFormatted);//decorrenza,
           	logger.info(new Integer(m.get("MESIVALIDITA"))); //,mesiValidita,
           	logger.info(dataPagamentoGregCal);//dataPagamento,
           	logger.info((new BigDecimal(m.get("TASSA"))));//,tassa,
           	logger.info((new BigDecimal(m.get("SANZIONI"))));//sanzioni,
           	logger.info((new BigDecimal(m.get("INTERESSI"))));//interessi,
           	logger.info((new BigDecimal(m.get("TOTALE"))));//totale,
           	logger.info(17);// regBeneficiaria,
           	logger.info(17);//regVersamento,
           	logger.info(m.get("CF_SOGG_PASSIVO")); //,codiceFiscale,
           	logger.info(new Integer(m.get("MODALITA_PAG")));//,modPagamento,
           	logger.info("NOTE");
			
			Integer risposta =                 port.nuovoPagamento("CB",
					                                              "IRIS",
					                                              m.get("MSG_ID"), //msgid
					                                              m.get("ID_BOLLO"), //idBollo
					                                              m.get("IUV"), // iuv //PATCH
					                                              new Integer(m.get("TIPO_VEICOLO")), //tipoVeicolo,
					                                              m.get("TARGA"),// targa,
					                                              dataDecorrenzaFormatted,//decorrenza,
					                                              new Integer(m.get("MESIVALIDITA")), //,mesiValidita,
					                                              dataPagamentoGregCal,//dataPagamento,
					                                              (new BigDecimal(m.get("TASSA"))),//,tassa,
					                                              (new BigDecimal(m.get("SANZIONI"))),//sanzioni,
					                                              (new BigDecimal(m.get("INTERESSI"))),//interessi,
					                                              (new BigDecimal(m.get("TOTALE"))),//totale,
					                                              17,// regBeneficiaria,
					                                              17,//regVersamento,
					                                              m.get("CF_SOGG_PASSIVO"), //,codiceFiscale,
					                                              new Integer(m.get("MODALITA_PAG")),//,modPagamento,
					                                              "NOTE"); // note
			logger.info(" !! Webservice NuovoPagamentoResponse called !! ");
			
			logger.info(" Result Value = " +risposta.intValue());
			
			EnumEsitiGTARTWS esito=EnumEsitiGTARTWS.getByKey(risposta);
			if (esito== null) {
				logger.error("Valore di ritorno non previsto...rollback!!!!");
				throw new RuntimeException();
			} else {
				logger.info(this.getClass().getSimpleName() + " delivery() Descrizione = "+ esito.getDescription());
				logger.info(this.getClass().getSimpleName() + " delivery() Comportamento previsto = " + esito.getBehaviour());
				logger.info(this.getClass().getSimpleName() + " delivery() esito = "+esito );
				// 
				if (esito.getBehaviour().equals("KO")) {
					logger.info("Stato finale KO..");
					throw new ResultKOException(esito);
				}
				if (esito.getBehaviour().equals("RETRY"))
					throw new RuntimeException();
				return esito;
			}			
		} catch (Exception e){
			logger.info("Errore di sistema ... rollback...");
			if (e instanceof RuntimeException) throw (RuntimeException)e;
			throw new RuntimeException(e);   // Rollback and propaga.
		} finally {
		    logger.info(this.getClass().getSimpleName() + " delivery() END " );		   
		}
		
	}
    
	/**
    *
    * @param input nel formato: "KEY1=value1; KEY2=value2; "
    * @return
    * @throws IllegalArgumentException
    */
    private  Map<String, String> tokenize(String input) throws Exception {
           Map<String, String> returnValue = new HashMap<String, String>();

           try {
                   StringTokenizer st = new StringTokenizer(input, ";");
                   while (st.hasMoreTokens()) {
                           StringTokenizer st2 = new StringTokenizer(st.nextToken(), "=");
                           String key = st2.nextToken().toUpperCase().trim();
                           String value = st2.nextToken().toUpperCase().trim();
                           returnValue.put(key, value);
                   }
           } catch (Exception e) {
                   throw new Exception("Input non formattato correttamente", e);
           }
           return returnValue;
   }
   	
}
