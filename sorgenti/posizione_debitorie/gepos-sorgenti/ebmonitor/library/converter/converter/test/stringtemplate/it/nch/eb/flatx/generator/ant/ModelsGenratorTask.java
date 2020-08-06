/**
 * Created on 19/mar/2009
 */
package it.nch.eb.flatx.generator.ant;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.SequenceParser;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;


/**
 * @author gdefacci
 */
public abstract class ModelsGenratorTask extends Task {
	
	private final Class recordClass;
	
	private File jarFile;
	private String recordsPackage;
	private String parserFactoryClass;
	private File sourceFolder; 
	private String targetPackageName;
	
	private String excludeNames;
	private String includeNames;
	
	private String excludePaserMethods;
	private String includePaserMethods;
	
	private String extendedInterfaces;

	private Set	namesToExclude;
	private Set	namesToInclude;
	
	private Set	parserMethodsToExclude;
	private Set	parserMethodsToInclude;
	
	public ModelsGenratorTask(Class recordClass) {
		this.recordClass = recordClass;
	}

	public void execute() throws BuildException {
		if (jarFile==null) throw new NullPointerException("jarFile");
		
		if (!jarFile.exists()) throw new RuntimeException(jarFile.getAbsolutePath() + " does not exists");
		if (jarFile.exists() && !jarFile.isFile()) throw new RuntimeException(jarFile.getAbsolutePath() + " is not a file");

		if (sourceFolder==null) throw new NullPointerException("sourceFolder");
		if (targetPackageName==null) throw new NullPointerException("targetPackageName");
		
		if (isEmpty(parserFactoryClass) && isEmpty(recordsPackage))
			throw new IllegalArgumentException("one of parserFactoryClass or recordsPackage should be specified");
		
		try {
			safeExecute();
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof RuntimeException) throw (RuntimeException)e;
			else throw new RuntimeException(e);
		} 
		
	}

	private boolean isEmpty(String string) {
		return string==null || string.trim().length() == 0;
	}
	
	protected abstract void safeExecute();

	protected IParser[] getNoArgCreateMethodsReturningIParser(ParsersFactory pf) {
		Class pfClass = pf.getClass();
		Method[] mthds = pfClass.getMethods();
		List resParsers = new ArrayList();
		for (int i = 0; i < mthds.length; i++) {
			Method method = mthds[i];
			Class mthdReturnType = method.getReturnType();
			if (IParser.class.isAssignableFrom( mthdReturnType ) &&	
					method.getParameterTypes().length == 0) {
				try {
					IParser res = (IParser) method.invoke(pf, new Object[] {});
					
					if (isParserMethodToInclude(method)) {
						if (!SequenceParser.class.isAssignableFrom(res.getClass()) ) {
							resParsers.add(res);
						}
					}
				} catch (Exception e) {
					log("error invoking method " + method, e, Project.MSG_WARN);
				}
			}
		}
		return (IParser[]) resParsers.toArray(new IParser[0]);
	}
	
	private boolean isParserMethodToInclude(Method method) {
		Set incl = getParserMethodsToInclude();
		String mthdNm = method.getName();
		if (incl.isEmpty()) {
			return !getParserMethodsToExclude().contains(mthdNm);	
		} else {
			return getParserMethodsToInclude().contains(mthdNm) &&  
			  !getParserMethodsToExclude().contains(mthdNm);
		}
	}

	public ParsersFactory getParserFactory() {
		if (parserFactoryClass==null) return null;
		try {
			Class pfClass = Class.forName(parserFactoryClass);
			ParsersFactory pf = (ParsersFactory) pfClass.newInstance();
			return pf;
		} catch (ClassNotFoundException e) {
			log("could not find class " + parserFactoryClass);
		} catch (InstantiationException e) {
			log("could not instantiate class " + parserFactoryClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public List getClasses() {
		List classes = new ArrayList();
		if (recordsPackage!=null) 
			try {
				classes = new ArrayList();
				String zipFsPkgNm = recordsPackage.replaceAll("\\." , "/");
				ZipFile jar = new ZipFile(jarFile);
				Enumeration entries = jar.entries();
				while (entries.hasMoreElements()) {
					ZipEntry ze = (ZipEntry) entries.nextElement();
					if (ze.getName().startsWith(zipFsPkgNm) && ze.getName().endsWith(".class")) {
						String clsNm = ze.getName().replaceFirst("\\.class", "").replaceAll("/", "\\.");
						try {
							Class kl1 = Class.forName(clsNm);
							if (recordClass.isAssignableFrom(kl1)) {
								if (isIncluded(kl1)) classes.add(kl1);
							}
						} catch (Exception e) {
							e.printStackTrace();
							log("could not create class " + clsNm, e, Project.MSG_WARN);
						}
					} 
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return classes;
	}

	protected boolean isIncluded(Class kl1) {
		Set incl = getNamesToInclude();
		if (incl.isEmpty()) {
			return !getNamesToExclude().contains(StringUtils.getSimpleName(kl1));	
		} else {
			return getNamesToInclude().contains(StringUtils.getSimpleName(kl1)) &&  
			  !getNamesToExclude().contains(StringUtils.getSimpleName(kl1));
		}
	}
	
	
	protected Class[] getInterfacesToImplement() {
		if (isEmpty(extendedInterfaces)) return new Class[0];
		else {
			String[] interfaceNames = getInterfacesToImplementNames();
			List/*<Class<?>*/ interfaceToImpl = new ArrayList();
			for (int i = 0; i < interfaceNames.length; i++) {
				String interfaceNm = interfaceNames[i];
				Class kl1;
				try {
					kl1 = Class.forName(interfaceNm);
					interfaceToImpl.add(kl1);
				} catch (Exception e) {
					log("could not find class " + interfaceNm);
				}
			}
			return (Class[]) interfaceToImpl.toArray(new Class[0]);
		}
	}

	protected String[] getInterfacesToImplementNames() {
		String[] interfaceNames = extendedInterfaces.split(";");
		List/**/ res = new ArrayList();
		for (int i = 0; i < interfaceNames.length; i++) {
			String intrfcNm = interfaceNames[i].trim();
			if (intrfcNm.length()>0) {
				res.add(intrfcNm);
			}
		}
		return (String[]) res.toArray(new String[0]);
	}
	
	protected Set getNamesToExclude() {
		if (namesToExclude==null) {
			namesToExclude = getNamesSet(excludeNames);;
		}
		return namesToExclude;
	}
	
	protected Set getNamesToInclude() {
		if (namesToInclude==null) {
			namesToInclude = getNamesSet(includeNames);;
		}
		return namesToInclude;
	}
	
	protected Set getParserMethodsToExclude() {
		if (parserMethodsToExclude==null) {
			parserMethodsToExclude = getNamesSet(excludePaserMethods);;
		}
		return parserMethodsToExclude;
	}
	
	protected Set getParserMethodsToInclude() {
		if (parserMethodsToInclude==null) {
			parserMethodsToInclude = getNamesSet(includePaserMethods);;
		}
		return parserMethodsToInclude;
	}
	
	protected Set getNamesSet(String names) {
		Set res = new HashSet();
		if (names==null) return res;
		else {
			String[] spNms = names.split(";");
			for (int i = 0; i < spNms.length; i++) {
				String nm = spNms[i].trim();
				if (nm.length()>0) res.add(nm);
			}
			return res;
		}
	}

	public File getJarFile() {
		return jarFile;
	}
	
	public void setJarFile(File jarFile) {
		this.jarFile = jarFile;
	}
	
	public String getRecordsPackage() {
		return recordsPackage;
	}
	
	public void setRecordsPackage(String recordsPackage) {
		this.recordsPackage = recordsPackage;
	}
	
	public String getParserFactoryClass() {
		return parserFactoryClass;
	}
	
	public void setParserFactoryClass(String parserFactoryClass) {
		this.parserFactoryClass = parserFactoryClass;
	}

	public File getSourceFolder() {
		return sourceFolder;
	}
	
	public void setSourceFolder(File sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	
	public String getTargetPackageName() {
		return targetPackageName;
	}
	
	public void setTargetPackageName(String targetPackageName) {
		this.targetPackageName = targetPackageName;
	}
	
	public String getExtendedInterfaces() {
		return extendedInterfaces;
	}
	
	public void setExtendedInterfaces(String extendedInterfaces) {
		this.extendedInterfaces = extendedInterfaces;
	}

	public String getExcludeNames() {
		return excludeNames;
	}
	
	public void setExcludeNames(String excludeNames) {
		this.excludeNames = excludeNames;
	}

	public String getIncludeNames() {
		return includeNames;
	}
	
	public void setIncludeNames(String includeNames) {
		this.includeNames = includeNames;
	}

	public String getExcludePaserMethods() {
		return excludePaserMethods;
	}

	public void setExcludePaserMethods(String excludePaserMethods) {
		this.excludePaserMethods = excludePaserMethods;
	}
	
	public String getIncludePaserMethods() {
		return includePaserMethods;
	}
	
	public void setIncludePaserMethods(String includePaserMethods) {
		this.includePaserMethods = includePaserMethods;
	}
	
}
