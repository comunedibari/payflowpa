/**
 * Created on 23/gen/09
 */
package it.nch.testools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author gdefacci
 */
public class SerializationHelper {

	public static final SerializationHelper instance = new SerializationHelper(new File("d:/temp/ser"));
	
	private final File serFolder;
	
	public SerializationHelper(final File serFolder) {
		this.serFolder = serFolder;
		if (!serFolder.exists()) serFolder.mkdirs();
		else if (serFolder.exists() && !serFolder.isDirectory()) {
			throw new IllegalStateException(serFolder + " is not a folder ");
		}
	}

	public File serializeObject(Object conversionService) {
		return serializeObject(String.valueOf(System.currentTimeMillis()), conversionService);
	}
	
	public File serializeObject(String fname, Object conversionService) {
		try {
			File serFile = new File(serFolder, fname);
			ObjectOutputStream serializationStream = new ObjectOutputStream(new FileOutputStream(serFile));
			serializationStream.writeObject(conversionService);
			serializationStream.close();
			return serFile;
		} catch (Exception e) {
			throw new RuntimeException("error serializing " + conversionService.getClass(), e);
		}
	}
	
	public Object deserialize(File serfile)  {
		try {
			InputStream is = new FileInputStream(serfile);
			ObjectInput oi = new ObjectInputStream(is);
			Object newObj = oi.readObject();
			oi.close();
			return newObj;
		} catch (Exception e) {
			throw new RuntimeException("erro deserializinf from " + serfile, e);
		} 
	}
}
