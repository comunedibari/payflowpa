package it.tasgroup.iris.persistence.dao.util;

import it.tasgroup.iris.domain.CfgCommissionePagamento;
import it.tasgroup.iris.domain.CfgDocumentoPagamento;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgTipoCommissione;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.helper.CommissioniCalculator;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;
import it.tasgroup.services.util.idgenerator.IDGenerator;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DDP_IDGenerator {
	
	private static String IRIS_PREFIX = "RT";
	
	// lunghezze degli ID non comprensive del CRC
	private static int ID_BON_LENGTH = 15;
	private static int ID_ATM_LENGTH = 15;
	private static int ID_GDO_LENGTH = 14;
	
		public static String Generate_DDP_ID(DocumentoDiPagamento ddp){
			
			switch(ddp.getTipoDocumentoEnum()){
			
				case ATM:
					return Generate_DDP_ID_ATM();
					
				case BONIFICO:
					return Generate_DDP_ID_BON();
					
				case GDO:
					return Generate_DDP_ID_GDO(ddp);
					
				case FRECCIA:
					return Generate_DDP_ID_ATM();
					
				case NDP:
					return Generate_DDP_ID_ATM(); // TODO: manca implementazione per NDP

				default:	return null;
			
			}
		}

		private static String Generate_DDP_ID_ATM(){
			
			String key = IDGenerator.generateID(ID_ATM_LENGTH);
			
			String keyWithCRC = key + IDGenerator.calculateCRC(key);
			
			return keyWithCRC;
		}
		
		private static String Generate_DDP_ID_BON(){
			
			String key = IDGenerator.generateID(ID_BON_LENGTH);
			
			String keyWithCRC = key + IDGenerator.calculateCRC(key);
			
			return IRIS_PREFIX + keyWithCRC ;	
			
		}
		
		private static Date calculateMinDate(Set<CondizioneDocumento> carrello){
			
			Date minScadenza = null;
			
			for (CondizioneDocumento condDoc : carrello){
				
				CondizionePagamento condPagamento = condDoc.getCondizionePagamento();
				
				Date currScadenza = condPagamento.getDtScadenza();
				
				if (minScadenza==null || minScadenza.after(currScadenza))
					minScadenza = currScadenza;
			}
			
			return minScadenza;
		}
	
		private static String Generate_DDP_ID_GDO(DocumentoDiPagamento ddp){
			
			String irisCode = "99998";
			String cartCode = "000";
			String irisGeneratedId = IDGenerator.generateID(ID_GDO_LENGTH);
			System.out.println("IRIS GENERATED ID: "+ irisGeneratedId);
			System.out.println("IRIS GENERATED ID LENGTH: "+ irisGeneratedId.length());
			String currencyCode = "978";
			String amountDigitsNumber = "2";
			
			Set<CondizioneDocumento> carrello = ddp.getCondizioni();		
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			
			Date minScadenza = calculateMinDate(carrello);
			
			String minScadString = df.format(minScadenza).substring(1);
			
			String totaleCarrelloCommissioni = IDGenerator.fillLeftWithDigits(7,CommissioniCalculator.calculateTotalAmountConCommissioni(ddp).multiply(new BigDecimal(100)).longValue(),'0');
			
			String key = irisCode+cartCode+irisGeneratedId+minScadString+currencyCode+totaleCarrelloCommissioni+amountDigitsNumber;
			
			String keyWithCRC = key + IDGenerator.calculateCRC(key);
			
			return keyWithCRC;
		}

		
		/**
		 * Metodo di utilità che estrae la parte interna IRIS da un codice EUPAY.
		 * Messo qui per centralizzare la conoscenza di come è fatto un codice EUPAY
		 * @param gdoIdentifier
		 * @return
		 */
		public static String extractCodPagamentoFromGDOIdentifier(String gdoIdentifier) {
			if (gdoIdentifier == null || gdoIdentifier.length()<41) {
				throw new InvalidParameterException(gdoIdentifier+" is not a valid GDO identifier.");
			}
			
			// Estraggo due cifre del cart code + 14 cifre dell'Identificativo interno iris (16 cifre di payment code).
			return gdoIdentifier.substring(6,22);
		}
		
		
		public static void main(String[] args){

			System.out.println(extractCodPagamentoFromGDOIdentifier("99998000509539577280000131023978000025827"));
			
			
			for (int j=0; j<1000; j++){
				
				for(EnumTipoDDP tipo: EnumTipoDDP.values()){
			
					DocumentoDiPagamento ddp = new DocumentoDiPagamento();
					ddp.setId("123");
					
					CfgGatewayPagamento cfgGatewayPagamento = new CfgGatewayPagamento();
					CfgDocumentoPagamento cfgDocumentoPagamento = new CfgDocumentoPagamento();
					cfgDocumentoPagamento.setId(new Long(tipo.getChiave()));
					cfgGatewayPagamento.setCfgDocumentoPagamento(cfgDocumentoPagamento);
					Set<CfgCommissionePagamento> cfgCommissionePagamenti = new HashSet<CfgCommissionePagamento>();
					CfgCommissionePagamento commissione = new CfgCommissionePagamento();
					CfgTipoCommissione tipoCommissione = new CfgTipoCommissione();
					tipoCommissione.setId(1L);
					commissione.setValore(new BigDecimal(1));
					commissione.setId(1L);
					commissione.setCfgTipoCommissione(tipoCommissione);
					cfgGatewayPagamento.setCfgCommissionePagamenti(cfgCommissionePagamenti);
					ddp.setCfgGatewayPagamento(cfgGatewayPagamento);
					
					
					
					Set<CondizioneDocumento> condizioni = new HashSet<CondizioneDocumento>();
					for(int i=0; i<3;i++){
						CondizioneDocumento condDoc = new CondizioneDocumento();
						condDoc.setDocumento(ddp);
						condDoc.setId(new Long(i));
						CondizionePagamento condPag = new CondizionePagamento();
						condPag.setIdCondizione(""+i);
						condDoc.setCondizionePagamento(condPag);
						Date today = new Date();				
						condPag.setDtScadenza(new Date(today.getTime()+i*10000));
						condPag.setImTotale(new BigDecimal(1200.78));
						condizioni.add(condDoc);
					}
					
					ddp.setCondizioni(condizioni);
					String generatedId = Generate_DDP_ID(ddp);
					System.out.println("\nTIPO DDP: \""+ddp.getTipoDocumentoEnum()+"\"");
					System.out.println("ID: \""+generatedId+"\"");
					System.out.println("LENGTH: "+generatedId.length());
				}
					
					
					
			}
		}
		
		public static void checkCRC(String[] ddpIds) throws SecurityException {
			
			for(String id :ddpIds)
				checkCRC(id);
			
		}
		
		public static void checkCRC(String docId) {
			
			String docIdPure = docId.toUpperCase();
			
			if (docId.startsWith(IRIS_PREFIX))
				docIdPure = docId.substring(2, docId.length());
			
			IDGenerator.checkCRC(docIdPure);
			
		}
	}
