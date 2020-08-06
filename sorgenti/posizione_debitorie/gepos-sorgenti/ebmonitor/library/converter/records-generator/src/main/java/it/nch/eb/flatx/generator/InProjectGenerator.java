/**
 * Created on 03/dic/08
 */
package it.nch.eb.flatx.generator;

import java.io.File;


/**
 * @author gdefacci
 */
public class InProjectGenerator {
	
	public static final InProjectGenerator instance = new InProjectGenerator();
	
	private InProjectGenerator() {}
	
	private final File root = new File(".");
	
	public File location() {
		return root;
	}
	public File folder(String fldrName) {
		File res = new File(root, fldrName);
		if (res.isDirectory()) 	return res;
		else throw new IllegalStateException(res + " is not a directory");
	}

}
