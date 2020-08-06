/**
 * Created on 08/ott/08
 */
package it.nch.eb.flatx.flatmodel.sax;


/**
 * @author gdefacci
 */
public class SaxTextCollector {
	
	private final SaxTextCollector parent;
	private final StringBuffer buffer = new StringBuffer();
	
	public SaxTextCollector(SaxTextCollector parent) {
		this.parent = parent;
	}

	public StringBuffer getBuffer() {
		return buffer;
	}
	
	public SaxTextCollector getParent() {
		return parent;
	}

	public void append(char[] ch, int start, int length) {
		for (int i = 0; i < length; i++) {
			buffer.append(ch[start + i]);
		}
	}
	
}
