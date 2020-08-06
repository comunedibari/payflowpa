/**
 * Created on 01/apr/2009
 */
package it.nch.streaming.flatfileparser;

import junit.framework.TestCase;
import it.nch.eb.common.converter.common.records.RecordTesta;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BeanParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.LineParser;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;
import it.nch.eb.ubi.converter.pmtreq.models.RecorddiTestaModel;


/**
 * @author gdefacci
 */
public class FlatParsersTest extends TestCase {

	public void test1() throws Exception {
		InputFileImpl inpF = 
			new InputFileImpl(ResourceLoaders.CLASSPATH.loadReader("pmtreq/err/TC12.xml.txt"));
		
		PmtreqParsersFactory pf = new PmtreqParsersFactory();
		
		BeanParser parser =  (BeanParser) pf.createParser();
		BeanErrorCollector be = new QualifiedErrors.Base();
		Object res = parser.createObject(inpF, IgnoreErrorsLinesFactory.builder, be);
		System.out.println(res);
	}
	
	
}
