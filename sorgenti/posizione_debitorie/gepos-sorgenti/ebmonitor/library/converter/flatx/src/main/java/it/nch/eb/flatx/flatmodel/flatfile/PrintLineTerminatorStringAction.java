/**
 * Created on 22/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;


/**
 * @author gdefacci
 */
public class PrintLineTerminatorStringAction implements LineTerminatedStringAction {

	private StringBuffer	sb	= new StringBuffer();
	private final String	lineTerminator;

	public PrintLineTerminatorStringAction(String lineTerminator) {
		this.lineTerminator = lineTerminator;
	}

	public void execute(String str) {
		sb.append(str);
	}

	public String getContent() {
		return sb.toString();
	}

	public void printLineTerminator() {
		sb.append(lineTerminator);
	}
}