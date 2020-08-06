/**
 * Created on 03/giu/08
 */
package it.nch.eb.flatx.files.model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @author gdefacci
 */
public class FixedWidthFileCache extends AbstractFileCache {
	
	private final int lineSize;
	
	public FixedWidthFileCache(int sze, int lineSize) {
		super(sze);
		this.lineSize = lineSize;
	}
	
	protected String[] readLines(BufferedReader reader, int cacheSize) {
		synchronized (lock) {
			try {
				int i=0;
				boolean finished = false;
				List res = new ArrayList();
				while (!finished && i < cacheSize) {
					char[] buff = new char[lineSize];
					String nl = null;
					int numberOfCharsRead = reader.read(buff,0,lineSize);
					if (numberOfCharsRead < lineSize) {
						finished = setEof(true);
						if (numberOfCharsRead>0) {
							char[] readBuff = new char[numberOfCharsRead];
							System.arraycopy(buff, 0, readBuff, 0, numberOfCharsRead);
							nl = new String(readBuff);
						}
					} else {
						nl = new String(buff);
					}
					
					if (nl!=null) {
						res.add(nl);
					} 
					i++;
				}
				return (String[]) res.toArray(new String[0]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
