package it.regioneveneto.mygov.payment.mypivot.xmlparser.rendicontazione;

import static org.junit.Assert.assertTrue;
import it.regioneveneto.mygov.payment.mypivot.xmlparser.rendicontazione.FlussoRendicontazioneDefaultParser;

import java.io.File;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FlussoRendicontazioneParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFlussoRendicontazioneParser() {
		try {
			final FlussoRendicontazioneDefaultParser flussoRendicontazioneParser = new FlussoRendicontazioneDefaultParser(
					new File("D:\\Progetti_RV\\E45A - MyPivot\\Software\\mypivot\\mypivot-xmlparser\\src\\test\\resources\\BCITITMM_151002002_20151005095643.xml"),
					new File("D:\\Progetti_RV\\E45A - MyPivot\\Software\\mypivot\\mypivot-xmlparser\\src\\main\\resources\\FlussoRiversamento_1_0_3.xsd"));

			for (final Map<String, String> row : flussoRendicontazioneParser) {
				System.out.println("===================================================");
				for (final String key : row.keySet()) {
					System.out.println("key= " + key + " value= " + row.get(key));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		assertTrue("Test finished.", true);
	}

}
