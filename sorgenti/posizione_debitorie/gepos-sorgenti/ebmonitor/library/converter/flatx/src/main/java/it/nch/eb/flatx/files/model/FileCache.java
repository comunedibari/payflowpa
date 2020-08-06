/**
 * Created on 04/mar/08
 */
package it.nch.eb.flatx.files.model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


/**
 * file cache that reads file line by line 
 * @author gdefacci
 */
public class FileCache extends AbstractFileCache {
	
	public FileCache(int sze) {
		super(sze);
	}
	
	protected String[] readLines(BufferedReader reader, int cacheSize) {
		synchronized (lock) {
			try {
				int i=0;
				boolean finished = false;
				List res = new ArrayList();
				while (!finished && i < cacheSize) {
					String nl = reader.readLine();
					if (nl!=null) {
						res.add(nl);
					} else {
						finished = setEof(true);
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
