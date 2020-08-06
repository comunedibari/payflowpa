/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;


/**
 * @author gdefacci
 */
public class ReplaceElemText extends DefaultElementPrinterEffect {
	private final String content;
	
	public ReplaceElemText(String content) {
		this.content = content;
	}
	
	public ReplaceElemText(String content, AttributesDescriber attributesDescriber) {
		super(attributesDescriber);
		this.content = content;
	}

	protected String getContent(String cnt) {
		return this.content;
	}

}