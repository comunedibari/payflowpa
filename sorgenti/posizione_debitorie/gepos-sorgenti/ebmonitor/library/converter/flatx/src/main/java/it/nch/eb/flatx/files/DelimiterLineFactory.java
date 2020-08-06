/**
 * Created on 05/giu/07
 */
package it.nch.eb.flatx.files;

import it.nch.eb.flatx.files.model.LinesFactory;
import it.nch.eb.flatx.files.model.TokenizedLine;


/**
 * @author gdefacci
 */
public class DelimiterLineFactory implements LinesFactory {

	private String	separator;

	public DelimiterLineFactory(String separator) {
		this.separator = separator;
	}

	public TokenizedLine create(String iineContent) {
		return TokenizationUtil.createDelimiterTokenizer(iineContent, separator);
	}

}
