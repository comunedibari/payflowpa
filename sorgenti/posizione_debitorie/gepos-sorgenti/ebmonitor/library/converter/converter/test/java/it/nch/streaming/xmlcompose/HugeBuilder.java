/**
 * Created on 16/lug/2008
 */
package it.nch.streaming.xmlcompose;

import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class HugeBuilder extends TestCase{
	
	InputStream top =  ResourceLoaders.CLASSPATH.loadInputStream( "xml/large/composer/top" );
	InputStream bottom = ResourceLoaders.CLASSPATH.loadInputStream( "xml/large/composer/bottom" );
	
	String folderName = "D:/java/projects/flattener/conversions-project/test/resources/xml/large/";

	int hugeness = 80;
	
	static OutputStream fsOutputStream(String nm) {
		try {
			return new FileOutputStream(nm);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("error creating outputStream : " + nm ,e);
		}
	}
	
	OutputStream destination = fsOutputStream(folderName + "pmtreq-" + hugeness + ".xml");

	
	int buffer_size = 4096;
	
	void writeTo(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[buffer_size];
		int ch = is.read(buf);
		boolean finished = ch < buffer_size;
		while (!finished) {
			if (ch > 0) os.write(buf, 0, ch);
			ch = is.read(buf);
			finished = ch < buffer_size;
		} 
		if (ch > 0) os.write(buf, 0, ch);
	}
	
	public void testGen() throws Exception {
		
		writeTo(top, destination);	
		
		for (int i=0; i < hugeness; i++) {
			InputStream center = ResourceLoaders.CLASSPATH.loadInputStream( "xml/large/composer/center" );
			writeTo(center, destination);
			center.close();
		}
		
		writeTo(bottom, destination);
		destination.close();
	}
	
}
