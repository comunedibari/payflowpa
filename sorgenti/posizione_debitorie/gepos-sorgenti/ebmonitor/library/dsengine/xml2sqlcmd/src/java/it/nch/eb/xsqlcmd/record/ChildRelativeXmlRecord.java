/**
 * 23/apr/2009
 */
package it.nch.eb.xsqlcmd.record;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.flatmodel.XmlRecord;

/**
 * @author gdefacci
 */
public class ChildRelativeXmlRecord extends XmlRecord {

	private static final long serialVersionUID = -5881301622416847951L;
	private String childPath;

	public ChildRelativeXmlRecord(String newPaths, String childPth) {
		super(newPaths);
		this.childPath = childPth;
	}
	
	public String getChildPath() {
		return childPath;
	}

	protected static String concat(String prefix, String midl, String sfx) {
		return StringUtils.concatPaths(new String[] {
			prefix, midl, sfx,
		});
	}
	
	protected static String concat(String prefix, String sfx) {
		return StringUtils.concatPaths(new String[] {
			prefix, sfx,
		});
	}

}
