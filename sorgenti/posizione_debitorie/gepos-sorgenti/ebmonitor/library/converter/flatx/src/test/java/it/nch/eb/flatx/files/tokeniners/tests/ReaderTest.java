/**
 * Created on 03/giu/07
 */
package it.nch.eb.flatx.files.tokeniners.tests;

import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;

import java.io.InputStream;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class ReaderTest extends TestCase {
	
	private static final String	TEXT	= "ciao\nsecond..\n..line\n4thline";

	public void testLineInputFile() {
		InputFile dt = new InputFileImpl(TEXT);
		assertEquals("ciao",dt.currentLine());  
		assertEquals("second..", dt.nextLine()); 
		assertEquals("..line", dt.nextLine()); 
		assertEquals("4thline", dt.nextLine()); 
	}
	
	public void testL11() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("inp1.txt");
		InputFile dt = new InputFileImpl(is);
		assertEquals(" PCB64360200220021998                                                                                            E           ",	dt.currentLine());
		assertEquals(" 140000005101098            480000000050000001+020023246000068645230107601120156035500005  B64365432135                E",dt.nextLine());
		assertEquals(" 300000005DELTA SAS",                                                                                                     dt.nextLine());
		assertEquals(" 400000005V EUGENIO ROTA 11-B           31100TREVISO",                                                                    dt.nextLine());
		assertEquals(" 500000005BONIFICO SU ISTRUZIONI XXXXX EDITORE         EMESSO IN DATA 25-07-03",                                          dt.nextLine());
		assertEquals(" 700000005                                                                                                     ",         dt.nextLine());
		assertEquals(" EF          101098BONIFICI                       19000000000000000000000000000000     78                        E    ",      dt.nextLine());

	}

}
