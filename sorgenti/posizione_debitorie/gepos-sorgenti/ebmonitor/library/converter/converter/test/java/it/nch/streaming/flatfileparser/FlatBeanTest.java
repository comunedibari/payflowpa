package it.nch.streaming.flatfileparser;

import it.nch.eb.common.converter.pmtreq.models.PmtreqDocument;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.flatx.files.IgnoreErrorsLinesFactory;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileImpl;
import it.nch.eb.flatx.flatmodel.flatfile.IRecordWriter;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.FlattenModelNew;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.QualifiedErrors;
import junit.framework.TestCase;

public class FlatBeanTest extends TestCase {
	
	public void testFlat() throws Exception {
		PmtreqParsersFactory pf = new PmtreqParsersFactory();
		BeanErrorCollector errs = new QualifiedErrors.Base();
		InputFile inpFile = new InputFileImpl(ResourceLoaders.CLASSPATH.loadInputStream("expected/pmtreq/e1.txt"));
		IParser docParser = pf.createParser();
		PmtreqDocument obj = (PmtreqDocument) docParser.createObject(inpFile, IgnoreErrorsLinesFactory.builder, errs);
		
		IRecordWriter flattener = FlattenModelNew.createRecordWriter(docParser, 
				new LineTerminatedStringAction() {

			public void printLineTerminator() {
//				each produced line ends with an '*' and a return
				System.out.print("|\n");
				
			}

			public void execute(String str) {
				System.out.print("|" + str);
			}
			
		});
		flattener.write(obj);
		
		
		
	}

}
