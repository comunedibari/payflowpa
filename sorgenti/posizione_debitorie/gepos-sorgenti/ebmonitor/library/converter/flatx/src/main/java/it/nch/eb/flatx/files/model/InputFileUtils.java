/**
 * Created on 07/apr/08
 */
package it.nch.eb.flatx.files.model;


/**
 * @author gdefacci
 */
public class InputFileUtils {

	public static boolean hasFinished(InputFile inputFile) {
		return !inputFile.hasNext() && inputFile.currentLine()==null;
	}
	
	public static boolean  moveNextIfNotFinished(InputFile stream) {
		boolean hasFinished = hasFinished(stream);
		if (!hasFinished) stream.nextLine();
		return hasFinished;
	}
}
