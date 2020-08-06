/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import java.io.File;
import java.util.Date;


/**
 * @author gdefacci
 */
public class FindModifiedAfter {
	
	static final File f = new File("D:\\java\\projects\\flowmanager-svn\\trunk\\converter\\flatx\\src\\main\\java\\it");
//	static final File f = new File("D:\\java\\projects\\flowmanager-svn\\trunk\\converter\\converter\\src\\java");
	
	public static void main(String[] args) {
		Date d = new Date(System.currentTimeMillis() - (45 * 60 * 1000));
		System.out.println(d);
		Scaner scanner = new Scaner();
		scanner.scan(f);
		System.out.println();
		
	}
	
	static class Scaner {
		
		public void scan(File f) {
			long time = System.currentTimeMillis() - 45 * 60 * 1000;
			if (f.isDirectory()) {
				for (int i = 0; i < f.listFiles().length; i++) {
					scan(f.listFiles()[i]);	
				}
			} else {
				if (f.lastModified() > time) System.out.println(f);
			}
		}
		
	}

}
