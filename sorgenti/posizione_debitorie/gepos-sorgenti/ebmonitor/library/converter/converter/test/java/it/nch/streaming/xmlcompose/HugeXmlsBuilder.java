/**
 * Created on 20/ago/2008
 */
package it.nch.streaming.xmlcompose;

import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author gdefacci
 */
public class HugeXmlsBuilder {
	
	int hugeness = 6666;
	String folderName = "D:/java/projects/flattener/conversions-project/test/resources/xml/large/";
	
	static OutputStream fsOutputStream(String nm) {
		try {
			return new FileOutputStream(nm);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("error creating outputStream : " + nm ,e);
		}
	}
	
	OutputStream os = fsOutputStream(folderName + "pmtreq-" + hugeness + ".xml");
	
	XmlFileBuilder composer = new XmlFileBuilder(hugeness, os) {

		public InputStream body() {
			return ResourceLoaders.CLASSPATH.loadInputStream( "xml/large/composer/center" );
		}

		public InputStream head() {
			return ResourceLoaders.CLASSPATH.loadInputStream( "xml/large/composer/top" );
		}

		public InputStream tail() {
			return ResourceLoaders.CLASSPATH.loadInputStream( "xml/large/composer/bottom" );
		}
		
	};
	
	public static void main(String[] args) {
		HugeXmlsBuilder bldr = new HugeXmlsBuilder();
		try {
			bldr.composer.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
