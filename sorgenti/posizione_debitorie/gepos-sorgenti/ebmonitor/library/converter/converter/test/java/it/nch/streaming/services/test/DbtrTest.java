/**
 * Created on 09/mar/2009
 */
package it.nch.streaming.services.test;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;

import it.nch.eb.common.converter.FromFlatToXmlConverter;
import it.nch.eb.common.converter.common.models.RecordTestaModel;
import it.nch.eb.common.converter.common.records.RecordTesta;
import it.nch.eb.common.converter.pmtreq.dbtr.parser.PaymentRequestDbtrParsersFactory;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.bean.types.DateValidateConverter;
import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDsl;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;
import it.nch.streaming.services.impl.TotalLineNumberPrevisitToFlatConversion;
import it.nch.streaming.services.impl.exp.PmtreqToFlatConversionServiceNew;
import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author gdefacci
 */
public class DbtrTest extends TestCase {
	
	BeanFactory bf = new ClassPathXmlApplicationContext("pmtreq-dbtr-flat2xml-application-context.xml");
	
	public void _testToXml1() throws Exception {
		FromFlatToXmlConverter cnv = (FromFlatToXmlConverter) bf.getBean("converter");
		cnv.convert(ResourceLoaders.CLASSPATH.loadInputStream("esiti/ESITO_BONIFICO_DA_REBA1.txt") , System.out);
	}
	
	BeanFactory bfToFlat = new ClassPathXmlApplicationContext("pmtreq-xml2flat-application-context.xml");
	
	public void _testRepe1() throws Exception {
		TotalLineNumberPrevisitToFlatConversion cnv = (TotalLineNumberPrevisitToFlatConversion) bfToFlat.getBean("countConverter");
		Writer out = new PrintWriter(System.out);
		cnv.convert(ResourceLoaders.CLASSPATH.readerFactory("repe/SEPACONCVS.txt.xml") , out);
	}
	
	public void _testRecord80() throws Exception {
		String leb = " 800000005BERTOLI FABIO                                                                                                                                                                                                                                                                                                                                                                                        a333llll                                                                                                                                                     IT36T0350011200000000077745";
		PaymentRequestDbtrParsersFactory pf = new PaymentRequestDbtrParsersFactory();
		IParser ebp = pf.createRecord80Parser();
		BeanErrorCollector ec = new QualifiedErrors.Base();
		Object res = ebp.createObject(new InputFileImpl(leb), IgnoreErrorsLinesFactory.builder, ec);
		System.out.println(res);
	}
	
	public void _testRecord36() throws Exception {
		String leb = " EB0000006FLUSSO ESITI BANCA UBISS           0000085Y0000124N0000649Q11826108800118261222092009-03-02T18:21:340760532S                           0000006";
		PaymentRequestDbtrParsersFactory pf = new PaymentRequestDbtrParsersFactory();
		IParser ebp = pf.createRecordCodaBodyParser();
		BeanErrorCollector ec = new QualifiedErrors.Base();
		Object res = ebp.createObject(new InputFileImpl(leb), IgnoreErrorsLinesFactory.builder, ec);
		System.out.println(res);
	}
	
	public void testRecordTesta() throws Exception {
		String str = " SHAVV-BON-IN                    0000129Z0000656Laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa2008-06-04T13:00:25AAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa2008-06-04T13:00:450000129Z0000655J0000078D0.0                                0000078D2008-06-04T13:00:250001                                   000001512935NCB13PRGPA                                0000655JGPA                                0000655J";
		RecordTesta rt = new RecordTesta();
		ParserDsl parserDsl = new ParserDsl();
		IParser parser = parserDsl.createParser(rt, RecordTestaModel.class);
		BeanErrorCollector errs = new QualifiedErrors.Base();
		RecordTestaModel tm = (RecordTestaModel) parser.createObject(new InputFileImpl(str), IgnoreErrorsLinesFactory.builder, errs);
		System.out.println("'" + tm.getChkDt() + "'\n'" + tm.getVersion() + "'");
	}
	
	public void _testDate() throws Exception {
		String in1 = "2009-03-02T18.21.34";
//		String in1 = "2009-03-02T";
		DateValidateConverter cnv = new DateValidateConverter("yyyy'-'MM'-'dd'T'HH'.'mm'.'ss");
		String res = cnv.encode(in1);
		System.out.println( res );
	}

}
