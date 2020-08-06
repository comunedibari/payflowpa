/**
 * Created on 09/gen/09
 */
package it.nch.streaming.services.test;

import it.nch.eb.common.converter.FromFlatToXmlConverter;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record02Model;
import it.nch.eb.common.converter.pmtreq.advinf.parser.AdvinfParserFactory;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record02;
import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author gdefacci
 */
public class AdvinfToXmlTest extends TestCase {
	
	public void _testToXml() throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
//			File inpFolder = new File("C:/Documents and Settings/Amministratore/Desktop/NCH dos/star-fuck/testing/Avvisatura Bonifico/Informativi/AVV-BON-IN");
			File inpFolder = new File("C:/Documents and Settings/Amministratore/Desktop/NCH dos/star-fuck/testing/Avvisatura Bonifico/Informativi/AVV-BON-IN");
			File inF = new File(inpFolder, "source-flat/TC-4.xml.txt");
			File outFile = new File(inpFolder, "target-xml/gen-TC-4.xml");
//			File expctdFile = new File(inpFolder, "target-xml/TC-4.xml.txt.xml");
			
			fis = new FileInputStream(inF);
			fos = new FileOutputStream(outFile);
			
			ClassPathXmlApplicationContext bf = new ClassPathXmlApplicationContext("advinf-flat2xml-application-context.xml");
			FromFlatToXmlConverter cnv = (FromFlatToXmlConverter) bf.getBean("converter");
			cnv.convert(fis, fos);
		} finally {
			if (fis!=null) fis.close();
			if (fos!=null) fos.close();
		}
	}
	
	public void testParseRecord20() throws Exception {
		String strok = " 020000003SCANDINAVIAN BUNKERING ASOVRE LANGGATE 503110 TONSBERGNORGE                                                                                                                                ";
		String strnok = " 020000003BAYERISCHE MOTOREN WERKE AGBMW AG  FR-235PETUELRING 13080788 MUENCHEN";
		
		AdvinfParserFactory pf = new AdvinfParserFactory();
		
		IParser rec20 = pf.createParser(new Record02(),  Record02Model.class);
		BeanErrorCollector errorCollector = new QualifiedErrors.Base();
		Record02Model r20_a = (Record02Model) rec20.createObject(new InputFileImpl(strok), IgnoreErrorsLinesFactory.builder, errorCollector);
		Record02Model r20_b = (Record02Model) rec20.createObject(new InputFileImpl(strnok), IgnoreErrorsLinesFactory.builder, errorCollector);
		System.out.println(r20_a);		
		System.out.println(r20_b);		
	}

}
