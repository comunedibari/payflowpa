/**
 * Created on 13/feb/2009
 */
package it.nch.testools;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.File;


/**
 * @author gdefacci
 */
public class FilecompareEffects {
	
	private static org.slf4j.Logger log = ResourcesUtil.createLogger(FilecompareEffects.class);
	
	public static final FileCompareEffect visualCompareFiles = new VisualCompareFile();
	public static final FileCompareEffect filesMatch = new FilesMatch();
	public static final FileCompareEffect filesMatchOrCompare = new FilesMatchOrCompare();
	public static final FileCompareEffect freezeResult = new FreezeResult();
	
	public static final class FilesMatch implements FileCompareEffect {

		public void compare(File a, File b) {
			boolean res = ResourcesUtil.fileCompare(a, b);
			if (!res) {
				String message = "the files \n1)" + a.getAbsolutePath() + "\n2)" + b.getAbsolutePath()
						+ "\ndo not match ";
				log.error(message);
				throw new RuntimeException(message);
			} else {
				String message = a.getAbsolutePath() + " == " + b.getAbsolutePath();
				log.info(message);
			}
		}

	}
	
	public static final class FreezeResult implements FileCompareEffect {
		public void compare(File a, File b) {
			System.out.println("coping from " + b.getAbsolutePath() + " to " + a.getAbsolutePath());
			ResourcesUtil.copyFile(b, a);
		}
	}
	
	public static final class FilesMatchOrCompare implements FileCompareEffect {
		
		private VisualCompareFile visual = new VisualCompareFile();

		public void compare(File a, File b) {
			boolean res = ResourcesUtil.fileCompare(a, b);
			if (!res) {
				String message = "the files \n1)" + a.getAbsolutePath() + "\n2)" + b.getAbsolutePath()
						+ "\ndo not match ";
				log.error(message);
				visual.compare(a, b);
				throw new RuntimeException(message);
			} else {
				String message = a.getAbsolutePath() + " == " + b.getAbsolutePath();
				log.info(message);
			}
		}

	}


	public static final class VisualCompareFile implements FileCompareEffect {

		public void compare(File a, File b) {
			new CompareLaunch(a, b).exec();
		}

	}

	

}
