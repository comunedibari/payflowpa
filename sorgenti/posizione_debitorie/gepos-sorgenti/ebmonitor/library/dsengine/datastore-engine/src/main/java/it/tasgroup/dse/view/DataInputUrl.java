package it.tasgroup.dse.view;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author agostino
 */
public class DataInputUrl implements DataInput{

	private static final long serialVersionUID = -7580945108963747888L;
	
	private URL xmlurl = null;
  
	private DataInputUrl(){
	}

	/**
	 * @param xmlurl
	 */
	public DataInputUrl(URL xmlurl) {
		super();
		this.xmlurl = xmlurl;
	}
	
	
	/**
	 * @return Restituisce il valore dataRetrievingMode.
	 */
//	public int getDataRetrievingMode() {
//		return DRM_URL;
//	}

		
	/**
	 * @return Restituisce il valore xmlurl.
	 */
	public URL getXmlurl() {
		return xmlurl;
	}
	
	/* (non Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		DataInputUrl out = new DataInputUrl();
		
		if(xmlurl!=null)
			out.xmlurl = this.xmlurl;
		
		return out;
	}
	
	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DataInput[");
//		buf.append(" dataRetrievingMode: " + DRM_URL);
		buf.append(", xmlurl: " + xmlurl);
		buf.append("]");
		return buf.toString();
	}
	
	/**
	 * @return
	 */
	public URI getUri() throws URISyntaxException{
		URI out = null;
		if(getXmlurl()!=null){
			out = new URI(getXmlurl().toExternalForm());
		}else throw new URISyntaxException("", "URL nulla");
		
		return out;
	}
	
	public InputStream createStream() {
		try {
			URLConnection  conn = new URL(getUri().toString()).openConnection();
			return conn.getInputStream();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
