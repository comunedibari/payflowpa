package it.nch.eb.common.utils;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author FG@2007
 */
public class ResourceManager {

	private static final String	DEFAULT_ENCODING	= null;
	public static InputStream getResourceAsStream(String resourceUrl) throws IOException{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceUrl); 
		if( is == null)
			throw new IOException(resourceUrl);
		return is;
	}
	
	public static Properties getResourceAsProperty(String resourceUrl) throws IOException{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceUrl); 
		if( is == null)
			throw new IOException(resourceUrl);
		Properties p = new Properties();
		p.load(is);
		return p;
	}

	public static String getResourceAsString(String resourceUrl) throws Exception {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceUrl); 
		if( is == null)
			throw new IOException(resourceUrl);
		
		return convertStreamToString(is);
	}
	
	public static byte[] getResourceAsByteArray(String resourceUrl) throws Exception {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceUrl); 
		if( is == null)
			throw new IOException(resourceUrl);
		
		return convertToByteArray(is);
	}
	
	public static String getFileAsString1(String filePath) throws Exception {
		return getFileAsString(filePath, DEFAULT_ENCODING);
	}

	public static String getFileAsString(String filePath, String charsetName) {
		return getFileAsString(new File(filePath), charsetName);
	}

	public static String getFileAsString(File f) {
		return getFileAsString(f, null);
	}

	public static String getFileAsString(File f, String charsetName) {
		if (!f.exists()) throw new IllegalArgumentException("the file " + f + " does not exists");
		if (f.isDirectory()) throw new IllegalArgumentException("the file " + f + " is a directory");
		
		InputStream fis = null;
		try {
			fis = new FileInputStream(f);
			return getStreamAsString(fis, charsetName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ResourcesUtil.close(fis);
		}
	}
	
	public static String getStreamAsString(InputStream fis) {
		return getStreamAsString(fis, null);
	}

	public static String getStreamAsString(InputStream fis, String charsetName) {
		try {
			byte[] buf = new byte[fis.available()];
			new DataInputStream(fis).readFully(buf);
			if (charsetName!=null) {
				return new String(buf, charsetName);
			} else {
				return new String(buf);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getFileAsString(String filePath) throws Exception {
		return ResourcesUtil.asString(new FileReader(new File(filePath)));
	}
	
	private static byte[] convertToByteArray(InputStream is) throws Exception {
		return convertStreamToString(is).getBytes();
	}

	public static String convertStreamToString(InputStream is) throws Exception {

		int nRead=0;
		char[] buff=new char[100];
		int max = 100;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		int n = 0;
		try{
			//open the stream
			br = new BufferedReader(new InputStreamReader(is));//TODO: ENCODING!

			//read len characters
			while ( (n = br.read(buff, 0, max)) != -1 ){
				nRead = nRead + n;
				if(n < max)
					sb.append(buff, 0 , n);
				else
					sb.append(buff, 0 , max);
			}

			//close the stream
			br.close();
			
		}catch(Exception ex){
			throw ex;
		}finally{
			if( br != null )
				try{br.close();}catch(Exception e){;}
		}
		return sb.toString();
	}

	public static InputStream convertStingToInputStream(String data) {
		return new ByteArrayInputStream(data.getBytes());
	}
	public static OutputStream convertStingToOutputStream(String data) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(data.getBytes());
		baos.flush();
		return baos;
	}

	

	
	
}
