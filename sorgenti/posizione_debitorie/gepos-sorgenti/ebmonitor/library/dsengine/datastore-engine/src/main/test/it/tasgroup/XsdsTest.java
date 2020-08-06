/**
 * 05/ago/2009
 */
package it.tasgroup;

import it.nch.eb.common.utils.loaders.ReadersFactories;
import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.eb.xsqlcmd.utils.Environments;
import it.nch.fwk.checks.errors.QualifiedError;
import it.nch.fwk.xml.validation.ValidationService;
import it.nch.fwk.xml.validation.XsdValidationService;

import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author gdefacci
 */
public class XsdsTest extends TestCase {
	
	public void _testXsd1() throws Exception {
		String fileName = "tests/insert1/inp.xml";
		showValidation(fileName);
	}
	
	public void _testXsd2() throws Exception {
		String fileName = "tests/insert1_id_ente_wrong/inp.xml";
		showValidation(fileName);
	}
	
	public void testXsdV03() throws Exception {
		String fileName = "inputs/v03/inp.xml";
		showValidation(fileName);
	}

	private void showValidation(String fileName) {
		Environment env = Environments.h2.withApplicationContextNamed("test-application-context.xml");
		ValidationService srvc = (ValidationService) env.getBeanFactory().getBean("idpXsdValidator");
		Set/*<QualifiedError>*/ errs = srvc.validate(ReadersFactories.instance.classpath(fileName).createReader());
		System.out.println(fileName + " produced");
		if (errs.isEmpty()) {
			System.out.println("no errors");
		} else {
			for (Iterator iterator = errs.iterator(); iterator.hasNext();) {
				QualifiedError qe = (QualifiedError) iterator.next();
				System.out.println(qe.getErrorId());
			}
		}
	}

}
