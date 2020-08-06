package it.tasgroup.iris.util;


import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.dto.exception.FileUploadException;
import it.tasgroup.iris.dto.exception.SmartProxyException;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author pazzik
 *
 */
public class SmartProxyInvoker {
	
	private static final ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("SmartProxy.properties");
	
	private static final String getEsitoTrasmissioneURL = props.getProperty("iris.smartproxy.get.esito.trasmissione.url");

	private static final String getStatoTrasmissioneURL = props.getProperty("iris.smartproxy.get.stato.trasmissione.url");

	private static final String uploadFileURL = props.getProperty("iris.smartproxy.upload.file.url");	
	
	
	public static String getEsitoTrasmissione(String idTrasmissione, String principal) throws SmartProxyException {

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String esitoTrasmissione = null;

		try {

			HttpGet httpGet = new HttpGet(getEsitoTrasmissioneURL + "?idTrasmissione=" + idTrasmissione + "&principalUtente=" + principal);

			HttpResponse response = httpClient.execute(httpGet);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			response.getEntity().writeTo(bos);

			esitoTrasmissione = bos.toString();

			Tracer.debug(SmartProxyInvoker.class.getName(), "getEsitoTrasmissione", "RESPONSE: " + esitoTrasmissione);

			System.out.println(esitoTrasmissione);

		} catch (Exception e) {

			Tracer.error(SmartProxyInvoker.class.getName(), "getEsitoTrasmissione", "Eccezione: " + e.getMessage());

			e.printStackTrace();

			throw new SmartProxyException();

		} finally {

			close(httpClient);

		}

		return esitoTrasmissione;

	}
	
	public static String getStatoTrasmissione(String idTrasmissione, String principal) throws SmartProxyException{
		
		CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
		
		String statoTrasmissione = null;
		
		try{
			
			HttpGet httpGet = new HttpGet(getStatoTrasmissioneURL + "?idTrasmissione=" + idTrasmissione + "&principalUtente=" + principal);
					
			HttpResponse response = httpClient.execute(httpGet);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	
	    	response.getEntity().writeTo(bos); 
	    	
	    	statoTrasmissione = bos.toString();
			
	    	Tracer.debug(SmartProxyInvoker.class.getName(), "getStatoTrasmissione", "RESPONSE: " + statoTrasmissione);
			
	    	System.out.println(statoTrasmissione);
	    	
		} catch(Exception e){
			
			 Tracer.error(SmartProxyInvoker.class.getName(), "getStatoTrasmissione", "Eccezione: " + e.getMessage());
				    	 
	    	 e.printStackTrace();
	    	 
	    	 throw new SmartProxyException();
	    	 
	     } finally {
	    	 
	    	 close(httpClient);
	    	 
	     }
		
		return statoTrasmissione;
		
	}
	
	public static String uploadFileToSmartProxy(File fileToUpload, String principal) throws FileUploadException{
		 
		 CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		 
		 String idTrasmissione = null;
		 
	     try{	
	    	 	     
	    	MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT);
	    	
	    	FileBody fileBody = new FileBody(fileToUpload);
	    	
	    	entity.addPart("flusso", fileBody);
	    	entity.addPart("principalUtente", new StringBody(principal, "text/plain", Charset.forName( "UTF-8" )));
	    	
	    	HttpPost httpPost = new HttpPost(uploadFileURL);
	    	httpPost.setEntity(entity);
	    	
	    	System.out.println("REQUEST");
	    	httpPost.getEntity().writeTo(System.out); 
	    	
	    	HttpResponse response = httpClient.execute(httpPost);
	    	
	    	System.out.println();
	    	System.out.println("RESPONSE");
	    	
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	
	    	response.getEntity().writeTo(bos); 
	    	
	    	System.out.println();
	    	
	    	idTrasmissione = bos.toString();
	    	
	    	//throw new IllegalStateException();
	    	
	     } catch(Exception e){
	    	 
	    	 Tracer.error(SmartProxyInvoker.class.getName(), "uploadFileToSmartProxy", "Eccezione: " + e.getMessage());
	    	 
	    	 e.printStackTrace();
	    	 
	    	 throw new FileUploadException();
	    	 
	     } finally{
	    	 	 
	    	 close(httpClient);
	     }
	     
	     
		return idTrasmissione;
		 
	}
	
	private static void close(CloseableHttpClient httpClient){
		
		try {
   		 
			httpClient.close();
			
		} catch (IOException e) {
						
			Tracer.error(SmartProxyInvoker.class.getName(), "close", "Eccezione: " + e.getMessage());
	    	 		
		} 
		
	}

	public static void main(String[] args) {
		
		String strXMLFilename = "D:\\TAS\\MY\\DOC\\BANCA_ETRURIA\\insertNew.csv";
		
		File fileToUpload = new File(strXMLFilename);
		
	    //uploadFileToSmartProxy(fileToUpload);
	}

	/** 
     * method to convert an InputStream to a string using the BufferedReader.readLine() method 
     * this methods reads the InputStream line by line until the null line is encountered 
    * it appends each line to a StringBuilder object for optimal performance  
     * @param is 
     * @return 
     * @throws IOException 
     */  
    public static String convertInputStreamToString(InputStream inputStream) throws IOException   
    {  
        if (inputStream != null){ 
        	
            StringBuilder stringBuilder = new StringBuilder();  
            
            String line;  
  
           try {
        	   
               BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
                
               while ((line = reader.readLine()) != null)   
                
                    stringBuilder.append(line).append("\n"); 
               
            } finally {  
            	
                inputStream.close();  
                
           }  
 
            return stringBuilder.toString();  
        }  
               
        return null;  
        
    }     
}     
