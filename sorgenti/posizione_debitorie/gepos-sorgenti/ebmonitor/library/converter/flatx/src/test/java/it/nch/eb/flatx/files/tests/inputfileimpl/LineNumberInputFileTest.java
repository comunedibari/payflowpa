/**
 * Created on 04/mar/08
 */
package it.nch.eb.flatx.files.tests.inputfileimpl;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.files.model.AbstractFileCache;
import it.nch.eb.flatx.files.model.InputFileImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class LineNumberInputFileTest extends TestCase {
	
	public void testInputFile1() throws Exception {
		BufferedReader rdr = new BufferedReader( 
				new InputStreamReader(
				ResourceLoaders.CLASSPATH.loadInputStream("bon/BON DOM BIG.txt") ) );
		
		InputStream is = ResourceLoaders.CLASSPATH.loadInputStream("bon/BON DOM BIG.txt");
		InputFileImpl f1 = new InputFileImpl(is);
		int cnt = 0;
		String current = rdr.readLine();
		AbstractFileCache fcache = f1.getCache();
		int cacheSze = fcache.getFullCacheSize();
		while (f1.hasNext()) {
			Assert.assertEquals(cnt, f1.getLineNumber());
			Assert.assertEquals(current, f1.currentLine());
			if (fcache.getFullCacheSize() != cacheSze) {
				cacheSze = fcache.getFullCacheSize();
				System.out.println(f1.getLineNumber());
				System.out.println(fcache);
			}
			cnt++;
			f1.nextLine();
			current = rdr.readLine();
		}
		
		rdr.close();
	}
	
	public void _testInputFileLinesNumber() throws Exception {
		BufferedReader rdr = new BufferedReader( 
				new InputStreamReader(
				ResourceLoaders.CLASSPATH.loadInputStream("bon/BON DOM BIG.txt") ) );
		
		InputStream is = ResourceLoaders.CLASSPATH.loadInputStream("bon/BON DOM BIG.txt");
		InputFileImpl f1 = new InputFileImpl(is);
		int cnt = 0;
		String current = rdr.readLine();
		while (f1.hasNext()) {
			System.out.println(f1.getLineNumber());
			Assert.assertEquals(cnt, f1.getLineNumber());
			Assert.assertEquals(current, f1.currentLine());
			cnt++;
			f1.nextLine();
			current = rdr.readLine();
		}
		
		rdr.close();
	}

}
