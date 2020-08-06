/**
 * Created on 20/ago/2008
 */
package it.nch.streaming.xmlcompose;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author gdefacci
 */
public abstract class XmlFileBuilder {
	
	private final OutputStream output;
	private final int hugeness;
	private final int buffer_size = 4096;	
	
	public XmlFileBuilder(int hugeness, OutputStream output) {
		this.hugeness = hugeness;
		this.output = output;
	}
	
	public abstract InputStream head();
	public abstract InputStream body();
	public abstract InputStream tail();
	
	public void writeTo(InputStream is, OutputStream os) throws IOException {
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
	
	public void create() throws Exception {
		
		OutputStream destination = output;
		InputStream hd = head();
		writeTo(hd, destination);	
		hd.close();
		
		for (int i=0; i < hugeness; i++) {
			InputStream center = body();
			writeTo(center, destination);
			center.close();
		}
		
		InputStream tail = tail();
		writeTo(tail, destination);
		tail.close();
		destination.close();
	}
}
