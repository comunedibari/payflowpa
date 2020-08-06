/**
 * Created on 21/feb/08
 */
package it.nch.streaming.test;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.pmtreq.dbtr.models.Record80Model;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record80;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;
import it.nch.eb.stringtemplate.TemplateWriter;

import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class StgTest extends TestCase {
	
	public void testRecord80Stg() throws Exception {
		String line = " 800000037String                                                                ADDRString                                                                                                                                      String                                                                                                                                      String                                                                String          String          String                             String                             AAString                                                                                                                                      String          aa00a"
		;
		IParser parser = RecordImplParsersFactory.create(new Record80(), Record80Model.class);
		
		InputFile stream = new InputFileImpl(line);
		BeanErrorCollector errorCollector = new QualifiedErrors.Base();
		Object obj = parser.createObject(stream, IgnoreErrorsLinesFactory.builder, errorCollector);
		
		Reader templ = new InputStreamReader(
//				ResourceLoaders.CLASSPATH.loadInputStream("it/nch/eb/common/converter/pmtreq/dbtr/records/parser/xml-esiti-dbtr.stg"));
				ResourceLoaders.CLASSPATH.loadInputStream("it/nch/eb/common/converter/pmtreq/dbtr/parser/xml-esiti-dbtr.stg"));
		TemplateWriter tw = new TemplateWriter(templ, System.out);
		tw.write("templateRecord80", "rec80", obj);
		
		System.out.println(obj);
		Assert.assertNotNull(obj);
	}
	
	

}
