/**
 * Created on 03/ott/08
 */
package it.nch.testools;

import java.io.File;


/**
 * @author gdefacci
 */
public class FileNameStartFilePredicate implements FilePredicate {
	
	private final String[] forbiddenPrefixes;
	private final boolean caseSensitive;
	private final boolean include;

	protected FileNameStartFilePredicate(final String[] forbiddenPrefixes, final boolean caseSensitive, final boolean include) {
		this.forbiddenPrefixes = forbiddenPrefixes;
		this.caseSensitive = caseSensitive;
		this.include = include;
	}
	
	public static FilePredicate exclude(String[] prefixes) {
		return exclude(prefixes, false);
	}
	public static FilePredicate exclude(String[] prefixes, boolean caseSensitive) {
		return new FileNameStartFilePredicate(prefixes, caseSensitive, false); 
	}
	
	public static FilePredicate include(String[] prefixes) {
		return include(prefixes, true);
	}
	
	public static FilePredicate include(String[] prefixes, boolean caseSensitive) {
		return new FileNameStartFilePredicate(prefixes, caseSensitive, true); 
	}

	public boolean match(File f) {
		for (int i = 0; i < forbiddenPrefixes.length; i++) {
			String prfx;
			if (caseSensitive) prfx = forbiddenPrefixes[i];
			else prfx = forbiddenPrefixes[i].toLowerCase(); 
			if (prfx!=null) {
				String name;
				if (caseSensitive) name = f.getName();
				else name = f.getName().toLowerCase();
				if (name.startsWith(prfx)) {
					return include;
				}
			}
		}
		return !include;
	}

}
