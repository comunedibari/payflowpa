/**
 * 28/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;


/**
 * @author gdefacci
 */
public class StartElementResult {

	private final String content;
	private final boolean visitChildren;
	public StartElementResult(String content) {
		this(content, true);
	}
	public StartElementResult(String content, boolean visitChildren) {
		this.content = content;
		this.visitChildren = visitChildren;
	}
	public String getContent() {
		return content;
	}
	public boolean isVisitChildren() {
		return visitChildren;
	}

	public StartElementResult withVisitChildren(boolean b) {
		return new StartElementResult(content, b);
	}
}
