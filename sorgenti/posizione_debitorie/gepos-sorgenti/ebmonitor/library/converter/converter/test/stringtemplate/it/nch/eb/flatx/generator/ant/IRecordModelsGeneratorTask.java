/**
 * Created on 19/mar/2009
 */
package it.nch.eb.flatx.generator.ant;

import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.records.ClassesGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.Project;


/**
 * @author gdefacci
 */
public class IRecordModelsGeneratorTask extends ModelsGenratorTask {
	
	public IRecordModelsGeneratorTask() {
		super(IRecord.class);
	}
	
	protected IRecord[] getRecords() {
		List recClasses = getClasses();
		if (recClasses.isEmpty()) return new IRecord[0];
		List records = new ArrayList();
		for (Iterator it = recClasses.iterator(); it.hasNext();) {
			Class recClass = (Class) it.next();
			try {
				Object rec = recClass.newInstance();
				records.add(rec);
			} catch (Exception e) {
				log("could not instantiate " + recClass);
			}
		}
		return (IRecord[]) records.toArray(new IRecord[0]);
	}

	protected void safeExecute() {
		ClassesGenerator generator = new ClassesGenerator(getSourceFolder(), getTargetPackageName());
		
		IRecord[] recs = getRecords();
		if (recs.length>0) {
			generator.generateAll(recs, getInterfacesToImplement());
		}
		ParsersFactory pf = getParserFactory();
		if (pf!=null) {
			IParser[] prsrs = getNoArgCreateMethodsReturningIParser(pf);
			for (int i = 0; i < prsrs.length; i++) {
				IParser parser = prsrs[i];
				try {
					generator.generateFromParser(parser.getName(), parser, getInterfacesToImplementNames());
				} catch (Exception e) {
					e.printStackTrace();
					log("error generating models from parser " + parser, e , Project.MSG_ERR);
				}
			}	
		}
	}

}
