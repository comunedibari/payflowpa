/**
 * Created on 04/gen/08
 */
package it.nch.eb.flatx.generator;


import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.loaders.ReaderFactory;

import java.io.File;


/**
 * @author gdefacci
 */
public class JavaGenerator extends Generator {
	
	public JavaGenerator(ReaderFactory readrFactory) {
		super(readrFactory);
	}

	public static File createPackageFolder(File folder, String packageName) {
		String fldrPkg = ReflectionUtils.getFolderNameFromPackage(packageName);
		String realPath = StringUtils.concatPaths(folder.getAbsolutePath(), fldrPkg);
		return createFolder(realPath);
	}
	
	

	public String getTypeName(Class klass) {
		if (klass.isArray()) {
			String ikln = klass.getName().substring(1);
			if (ikln.equals("B")) return "byte[]";   
			else if (ikln.equals("C")) return "char[]"    ;
			else if (ikln.equals("D")) return "double[]"  ;
			else if (ikln.equals("F")) return "float[]"   ;
			else if (ikln.equals("I")) return "int[]"     ;
			else if (ikln.equals("J")) return "long[]"    ;
			else if (ikln.equals("S")) return "short[]"   ;
			else if (ikln.equals("Z")) return "boolean[]" ;
			else throw new UnsupportedOperationException("unsupported array type " + klass.getName());
		} else {
			return klass.getName();
		}
	}
}
