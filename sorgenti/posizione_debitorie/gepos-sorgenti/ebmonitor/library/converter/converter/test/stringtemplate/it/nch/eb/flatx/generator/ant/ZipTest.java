/**
 * Created on 19/mar/2009
 */
package it.nch.eb.flatx.generator.ant;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;


/**
 * @author gdefacci
 */
public class ZipTest extends TestCase {
	
	public void test1() throws Exception {
		IRecordModelsGeneratorTask tsk = new IRecordModelsGeneratorTask();
		tsk.setJarFile(new File("D:/java/projects/fm-clone/eb_flowmanager/sw/trunk/converter/converter/target/eb-common-converter-0.5.jar"));
		tsk.setRecordsPackage("it.nch.eb.common.converter.pmtreq.likepc.records");
		
		List rcs = tsk.getClasses();
		for (Iterator it = rcs.iterator(); it.hasNext();) {
			System.out.println(  it.next() );
			
		}
	}

}
