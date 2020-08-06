package it.tasgroup.iris.servlet.ddp;

import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.dto.builder.DDPDTOCommonBuilder;
import it.tasgroup.iris.dto.ddp.BollettinoFrecciaDTO;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.dto.exception.BollettinoFrecciaException;
import it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacade;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.report.servlet.DownloadServlet;
import it.tasgroup.report.servlet.ReportServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author pazzik
 *
 */
public class BollettinoFrecciaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static final String EXOLAB_INCAS_URL_KEY = "iris.bollettino.freccia.url";
	
	private static final String exolab_incas_url = getProperty(EXOLAB_INCAS_URL_KEY);
	
	private static Logger LOGGER = Logger.getLogger(BollettinoFrecciaServlet.class.getName());

	public void init() throws ServletException {
		LOGGER.debug("init[0]");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			LOGGER.debug("doGet[0]");
			getBollettinoFrecciaBytes(req,resp);
			LOGGER.debug("doGet[1]");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			LOGGER.debug("doPost[0]");
			getBollettinoFrecciaBytes(req,resp);
			LOGGER.debug("doPost[1]");
	}
	
	/**
	 * @param toEncode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeURLUTF8(String toEncode) throws UnsupportedEncodingException{
		
		return toEncode!=null?URLEncoder.encode(toEncode,"UTF-8"):"";
		
	}
	
	/**
	 * @param date
	 * @return
	 */
	private String getExolabDateFormat(java.util.Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	
	
	
	/**
	 * @param ddp
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String populateQueryStringWithParameters(DocumentoDiPagamentoDTO ddp) throws UnsupportedEncodingException{
		//String queryStringWithParameters= exolab_incas_address+"?csia=59470&iban=IT22T0103002818000008600053&deb=Maria%20Rossini&ind=Via%20Roma%2015&citta=Napoli&cap=00100&prov=RM&idboll=1234567891234567&motivo=seconda%20rata%202012&desc=Pagamento%20IMU&scadenza=01/12/2012&importo=200";
		
		BollettinoFrecciaDTO bfDTO = (BollettinoFrecciaDTO) ddp.getSpecificDetail();
		
		List<CondizioneDDPDTO> carrello = ddp.getCarrello();
		
		CondizioneDDPDTO condizione = carrello.get(0);
		
		StringBuffer buf = new StringBuffer(exolab_incas_url);
		buf.append("?csia="+bfDTO.getCsia());
		buf.append("&iban="+bfDTO.getCoordinateBancarie());
		buf.append("&deb="+encodeURLUTF8(bfDTO.getNomeOrdinante()));
		String indirizzo = bfDTO.getInd();
		
		int minlength = 1;
		
		if (indirizzo==null)
				indirizzo = "";
		else {
			
			int addressLength = indirizzo.length();
			if (addressLength > 0)
				minlength = Math.min(addressLength, 90);
		}
		
		buf.append("&ind="+encodeURLUTF8(indirizzo.substring(0,minlength)));
		buf.append("&citta="+encodeURLUTF8(bfDTO.getCitta()));
		buf.append("&cap="+bfDTO.getCap());
		buf.append("&prov="+bfDTO.getProv());
		buf.append("&idboll="+bfDTO.getId());
		buf.append("&motivo="+encodeURLUTF8(condizione.getTributo()));
		buf.append("&desc="+encodeURLUTF8(condizione.getDescrizioneFreccia()));
		buf.append("&scadenza="+getExolabDateFormat(condizione.getScadenza()));
		
		// FORZO IL LOCALE IT
		// PER USARE LA VIRGOLA COME SEPARATORE DEI DECIMALI
		// COME IMPOSTO DA SPECIFICHE EXOLAB INCAS

		NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN);
		
		String formattedAmount = nf.format(ddp.getImporto().add(ddp.getImportoCommissioni()));

		buf.append("&importo="+ formattedAmount);
		
		
		buf.append("&mail="+bfDTO.getMail());
		
		// SOLO PER FINI DI TEST
		// buf.append("&mail="+"katia.pazzi@tasgroup.it");
		
		return buf.toString();
	}
	
	/**
	 * Controlla che la risposta ottenuta dal servizio dei Bollettini Freccia non contenga una segnalazione di errore.
	 * 
	 * Se contiene un errore lancia una BollettinoFrecciaException (runtime exception) che viene gestita dal container
	 * 
	 * ridirigendo ad una pagina di errore dedicata.
	 * 
	 * @param tmpOutBytes
	 */
	private void parseErrorMessage(byte[] tmpOutBytes) {
			        
	        String toParse = new String(tmpOutBytes);
	            
	        if (toParse.contains("<div class=\"msg\">")){
 			
 			int indexOfMsg = toParse.indexOf("<div class=\"msg\">")+17;
 			
 			int indexOfMsgEnd = toParse.substring(indexOfMsg).indexOf("</div>");
 			
 			String msg = toParse.substring(indexOfMsg,indexOfMsg+indexOfMsgEnd);
 			
 			if (msg.trim().length()>0)
 				throw new BollettinoFrecciaException("Errore Exolab:" + msg);
 		}
	        
		
	}
	
	
	/**
	 * @param req
	 * @param ddp
	 * @return
	 */
	private byte[] getBytesFromRemoteHost(DocumentoDiPagamentoDTO ddp){
		
		URL exolabURL = null;
		
		InputStream inputStream = null;
		
		HttpURLConnection urlConn = null;	
		
		byte[] tmpOutBytes = null;
		
		try {			
			
			String queryStringWithParameters = populateQueryStringWithParameters(ddp);
			
			LOGGER.debug("URL BOLLETTINO FRECCIA: "+queryStringWithParameters);
			
			exolabURL = new URL(queryStringWithParameters);				
		
			urlConn = (HttpURLConnection) exolabURL.openConnection();
			
			inputStream = urlConn.getInputStream();
      		
			// SOLO PER FINI DI TEST
			//System.out.println("INPUT STREAM "+inputStream);
			
		    ByteArrayOutputStream tmpOut = new ByteArrayOutputStream(); 			    
			
			byte[] buf = new byte[512];
			
		    int len; 
		    
	        while ((len = inputStream.read(buf))!=-1)			         
	  
	            tmpOut.write(buf, 0, len); 
	        
	        tmpOutBytes = tmpOut.toByteArray();
	        
	        parseErrorMessage(tmpOutBytes);
	        
	        // SOLO PER FINI DI TEST
	        //tmpOut.writeTo(System.out);		        
				
        
		}  catch (MalformedURLException e) {
			
			LOGGER.error("Error on downloadBollettinoFreccia", e);
							
		} catch (IOException e) {
			
			LOGGER.error("Error on downloadBollettinoFreccia", e);			
			
		} catch (BollettinoFrecciaException e) {
			
			LOGGER.error("Error on downloadBollettinoFreccia", e);
			
			throw new BollettinoFrecciaException("Errore nel servizio di gestione dei Bollettini Freccia. Si prega di riprovare piu' tardi.");		
			
		} finally {
			
				try {
					if (inputStream!=null)
							inputStream.close();
					
					if (urlConn!=null)
						urlConn.disconnect();					
					
				} catch (IOException e) {
					
					LOGGER.error("Error on downloadBollettinoFreccia", e);
				} 
		}
		return tmpOutBytes;
		
	}
	
	
	/**
	 * @param req
	 * @param resp
	 */
	private void getBollettinoFrecciaBytes(HttpServletRequest req, HttpServletResponse resp) {
		
		try{
			
			DDPFacade ddpBean = (DDPFacade) ServiceLocator.getSLSBProxy("ddpFacadeBean");
		
			List<DocumentoDiPagamentoDTO> reportData = (List<DocumentoDiPagamentoDTO>)req.getAttribute(ReportServlet.REPORT_DATA_ATTR_NAME);
			
			byte[] reportByteStream = (byte[])req.getAttribute(DownloadServlet.DOWNLOAD_DATA);
									
			DocumentoDiPagamentoDTO ddp = reportData.get(0);
			
			BollettinoFrecciaDTO detailDTO = (BollettinoFrecciaDTO) ddp.getSpecificDetail();
			
			
			// PAZZIK 12-07-2012
			// a causa dei malfunzionamenti del servizio Exolab
			// ad ogni download si verifica se il bollettino � stato caricato in forma di blob su DB, altrimenti va scaricato da Exolab
			if (detailDTO.getIdDocRepository() == null && reportByteStream == null){
			
				reportByteStream = getBytesFromRemoteHost(ddp);
				
				DocumentoRepositoryDTO docRepoDTO = DDPDTOCommonBuilder.populateDocumentoRepositoryDTO(reportByteStream, ddp);
				
//				IProfileManager profile = FactoryProfileManagerClient.getProfileManager(req, WebUtility.getCookieValueByName(FrontEndConstant.SESSION_ID, req));

				ddpBean.createNewDocumentiRepository(ddp.getCfIntestatarioPendenza(), docRepoDTO,detailDTO.getId());
				
				req.setAttribute(DownloadServlet.DOWNLOAD_DATA,reportByteStream);
				
				req.setAttribute(ReportServlet.NOME_FILE_DOWNLOAD_ATTR_NAME, docRepoDTO.getFileName());
			
			} 		    
				
			req.getRequestDispatcher("ReportDecorator").forward(req, resp);
			
			
		} catch (ServletException e) {
				
				LOGGER.error("Error on downloadBollettinoFreccia", e);
				
		} catch (ServiceLocatorException e) {
			
			LOGGER.error("Error on downloadBollettinoFreccia - getBytesFromDB ", e);
			
		} catch (IOException e) {
			
			LOGGER.error("Error on downloadBollettinoFreccia", e);
			
		} catch (Exception e) {
			
			LOGGER.error("Error on downloadBollettinoFreccia", e);
			
		}  
		
	}

	/**
	 * Legge le properties da iris-fe.properties
	 *
	 * @param name il nome della property
	 * @return il valore della property di nome name su iris-fe.properties
	 */
	private static String getProperty(String name) {

		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");

		String value = cpl.getProperty(name);
		
		if (Tracer.isDebugEnabled(BollettinoFrecciaServlet.class.getName()))
			Tracer.debug("LoginAction", "getProperty", name + " = " + value);
		
		return value;
	}

}
