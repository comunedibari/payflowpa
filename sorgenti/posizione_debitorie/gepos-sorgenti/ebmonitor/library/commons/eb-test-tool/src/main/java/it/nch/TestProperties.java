/**
 * Created on 06/giu/08
 */
package it.nch;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.testools.FileCompareEffect;
import it.nch.testools.FilecompareEffects;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * @author gdefacci
 */
public interface TestProperties {
	
	String 	TEST_KIT_PROPERTIES = "it/nch/testkit.properties";
	String	COMPARE_APP_PROPERTY	= "compare-program";
	String	OUTPUT_FOLDER_PROPERTY	= "output-folder";
	
	File resoucesFolder = new File(".", "resources");
	TestKit kit = new TestKit(new File(resoucesFolder, "ts"), TEST_KIT_PROPERTIES);
	FileCompareEffect testCompareEffect = FilecompareEffects.filesMatchOrCompare;

	class TestKit {
		private Properties testKitProperties = null;
		
		private final String testKitPropertiesName;
		private final File kitBaseFolder;
		
		public TestKit(File kitBaseFolder, String testKitPropertiesName) {
			this.kitBaseFolder = kitBaseFolder;
			this.testKitPropertiesName = testKitPropertiesName;
		}

		private synchronized void initProperties() {
			if (testKitProperties==null) {
				try {
					testKitProperties = new Properties();
					testKitProperties.load( ResourceLoaders.CLASSPATH.loadInputStream(testKitPropertiesName) );
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		public String get(String id) { 
			if (testKitProperties==null) {
				initProperties();
			}
			return testKitProperties.getProperty(id); 
		}
		
		public synchronized File getCleanOutputFolder(String sfx) {
			File res = getOutFldr(sfx);
			if (res.exists()) {
				ResourcesUtil.deleteFolder(res);
			}
			res.mkdirs();
			return res;
		}
		
		private File getOutFldr(String sfx) {
			String outFldrPropValue = get(OUTPUT_FOLDER_PROPERTY);
			if (outFldrPropValue==null) {
				throw new IllegalStateException("the property " + OUTPUT_FOLDER_PROPERTY + 
						" should be set in the classpath location" + testKitPropertiesName);
			}
			File res = new File(outFldrPropValue + sfx);
			return res;
		}
		
		public final synchronized File getKitBaseFolder() {
			return kitBaseFolder;
		}

	}
}
