/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.client.utility;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Igor Tamiazzo
 *
 */
public class FetchFile {
	
	private URL url;
	private File file;
	
	public FetchFile(String urlString, String fileString) throws MalformedURLException {
		this.url = new URL(urlString);
		this.file = new File(fileString);
	}
	
	public void retriveFile() throws IOException{
		org.apache.commons.io.FileUtils.copyURLToFile(url, file);
	}

}
