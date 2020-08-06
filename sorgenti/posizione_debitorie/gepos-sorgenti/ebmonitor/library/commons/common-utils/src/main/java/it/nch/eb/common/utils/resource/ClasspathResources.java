/**
 * 09/giu/2009
 */
package it.nch.eb.common.utils.resource;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author gdefacci
 */
public class ClasspathResources {
	
	private StringPredicate fileNamePredicate;
	
	public ClasspathResources(StringPredicate fileNamePredicate) {
		this.fileNamePredicate = fileNamePredicate;
	}

	public Collection/* <String> */getResources() {
		ArrayList/* <String> */retval = new ArrayList();
		String classPath = System.getProperty("java.class.path", ".");
		String[] classPathElements = classPath.split(";");
		for (int i = 0; i < classPathElements.length; i++) {
			String element = classPathElements[i];
			Collection elements = getResources(element);
			if (elements.size() > 0) {
//				System.out.println("under " + element + "found ");
//				for (Iterator it = elements.iterator(); it
//						.hasNext();) {
//					System.out.println( it.next() );
//					
//				}
				retval.addAll(elements);
			}
		}
		return retval;
	}

	private Collection/* <String> */getResources(String element) {
		ArrayList/* <String> */retval = new ArrayList();
		File file = new File(element);
//		System.out.println("f : " + file.getAbsolutePath());
		if (file.isDirectory()) {
			retval.addAll(getResourcesFromDirectory(file, file));
		} else {
			retval.addAll(getResourcesFromJarFile(file));
		}
		return retval;
	}

	private Collection/* <String> */getResourcesFromJarFile(File file) {
		ArrayList/* <String> */retval = new ArrayList();
		ZipFile zf;
		try {
			zf = new ZipFile(file);
		} catch (Exception e) {
			throw new RuntimeException("cant find file " + file, e);
		}
		Enumeration e = zf.entries();
		while (e.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) e.nextElement();
			String fileName = ze.getName();
			boolean accept = this.fileNamePredicate.match(fileName);
			if (accept) {
				retval.add(fileName);
			}
		}
		try {
			zf.close();
		} catch (IOException e1) {
			throw new Error(e1);
		}
		return retval;
	}
	
	private boolean alwaysExclude(File f) {
		if (f.getName().startsWith(".")) {
			return true;
		} else {
			return false;
		}
	}
	
	private String nameFolderRelative(File foldr, File theFile) {
		String prfx = foldr.getAbsolutePath();
		String fn = theFile.getAbsolutePath();
		if (fn.startsWith(prfx)) {
			String res = fn.substring(prfx.length());
			if (res.startsWith("/") || res.startsWith("\\")) res = res.substring(1);
			return res.replaceAll("\\\\", "/");
		} else return null;
	}

	private Collection/* <String> */getResourcesFromDirectory(File directory,
			File basePkgFolder) {
		if (!alwaysExclude(directory)) {
			ArrayList/* <String> */retval = new ArrayList();
			File[] fileList = directory.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				if (!alwaysExclude(file)) {
					if (file.isDirectory()) {

						retval.addAll(getResourcesFromDirectory(file,
								basePkgFolder));
					} else {
						String fileName = nameFolderRelative(basePkgFolder,
								file);
						boolean accept = fileNamePredicate.match(fileName);
						if (accept) {
							retval.add(fileName);
						}
					}
				}
			}

			return retval;
		} else {
			ArrayList res = new ArrayList();
			return res;
		}
	}

}
