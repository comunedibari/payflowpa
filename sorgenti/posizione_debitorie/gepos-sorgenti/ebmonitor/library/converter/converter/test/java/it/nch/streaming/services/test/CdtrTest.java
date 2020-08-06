/**
 * Created on 02/apr/2009
 */
package it.nch.streaming.services.test;

import java.io.InputStream;

import it.nch.eb.common.converter.FromFlatToXmlConverter;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record60Model;
import it.nch.eb.common.converter.pmtreq.cdtr.parser.PaymentRequestCdtrParsersFactory;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record60;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDsl;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class CdtrTest extends TestCase {
	
	PaymentRequestCdtrParsersFactory pf = new PaymentRequestCdtrParsersFactory();
	
	public void _test2() throws Exception {
		IParser recBody = pf.createBodiesParser();
		BeanErrorCollector bf = new QualifiedErrors.Base();
		InputFile inp = new InputFileImpl(ResourceLoaders.CLASSPATH.loadReader("esiti/ESITI_CDTR_4_40.txt"));
		Object res = recBody.createObject(inp, IgnoreErrorsLinesFactory.builder, bf);
		System.out.println(res);
	}
	
	public void _test1() throws Exception {
		BeanFactory bf = new ClassPathXmlApplicationContext("pmtreq-cdtr-flat2xml-application-context.xml");
		
		FromFlatToXmlConverter converter = (FromFlatToXmlConverter) bf.getBean("converter");
		converter.convert(ResourceLoaders.CLASSPATH.loadInputStream("esiti/ESITI_CDTR_4.txt"), System.out);
	}
	
	public void _testParseFlatCdtr() throws Exception {
		String loc = "D:/java/projects/flowmanager-svn/flowmanager/flowmanager-functional-tests/resources/ts/Disposizioni Pagamento XML/Esiti/CDTR/source-flat/ESITO.txt";
		BeanFactory bf = new ClassPathXmlApplicationContext("pmtreq-cdtr-flat2xml-application-context.xml");
		
		PaymentRequestCdtrParsersFactory pf = (PaymentRequestCdtrParsersFactory) bf.getBean("parsersFactory");
		InputFile inp = new InputFileImpl(ResourceLoaders.FILESYSTEM.loadReader(loc));
		
		BeanErrorCollector errColl = new QualifiedErrors.Base();
		Object res = pf.createParser().createObject(inp, IgnoreErrorsLinesFactory.builder, errColl);
		System.out.println(res);
	}
	
	public void testToXml() throws Exception {
//		it.nch.eb.common.converter.FromFlatToXmlConverter
		
		BeanFactory bf = new ClassPathXmlApplicationContext("pmtreq-cdtr-flat2xml-application-context.xml");
		FromFlatToXmlConverter cnv = (FromFlatToXmlConverter) bf.getBean("converter");
//		String loc = "D:/java/projects/flowmanager-svn/flowmanager/flowmanager-functional-tests/resources/ts/Disposizioni Pagamento XML/Esiti/CDTR/source-flat/ESITO.txt";
		InputStream is = ResourceLoaders.CLASSPATH.loadInputStream("xml/cdtr/esito_ok.txt");
		cnv.convert(is, System.out);
	}
	
	public void _testparser60() throws Exception {
//		BeanFactory bf = new ClassPathXmlApplicationContext("pmtreq-cdtr-flat2xml-application-context.xml");
//		PaymentRequestCdtrParsersFactory pf = (PaymentRequestCdtrParsersFactory) bf.getBean("parsersFactory");
		
		ParserDsl pdsl = new ParserDsl();
		IParser rec60 = pdsl.createParser( new Record60(), Record60Model.class );
		
		BeanErrorCollector errColl = new QualifiedErrors.Base();
		InputFile inp = new InputFileImpl(" 600000019//MIP/E51I04000010007/02/STIPENDIO MESE DI OTTOBRE 2009");
		Object res = rec60.createObject(inp, IgnoreErrorsLinesFactory.builder, errColl);
		System.out.println(res);
	} 
	

}
