/**
 * Created on 07/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileUtils;

/**
 * @author gdefacci
 */
public class ParserMatcher {

	private final LinesFactoryBuilder	linesFactoryBuilder;

	public ParserMatcher(LinesFactoryBuilder lfBuilder) {
		this.linesFactoryBuilder = lfBuilder;
	}
	
	/**
	 * verify if it's possible to consume inputfile using parser <code>pi</code>
	 */
	public boolean match(InputFile inputFile, IParser pi) {
		if (pi instanceof LineParser) {
			return match(inputFile, (LineParser) pi);
		} else if (pi instanceof BeanParser) {
			return match(inputFile, (BeanParser) pi);
		} else if (pi instanceof SequenceParser) {
			return match(inputFile, (SequenceParser) pi);
		}
		throw new IllegalStateException();
	}
	
	protected boolean match(InputFile inputFile, BeanParser pi) {
		IParser[] parsers = pi.parsers;
		boolean finished = InputFileUtils.hasFinished(inputFile) && parsers.length>0;
		int i = 0;
		while(!finished) {
			IParser parserInfo = parsers[i];
			boolean doesMatch = match(inputFile, parserInfo);
			if (!doesMatch && !parserInfo.isOptional()) return false;
			
			i++;
			finished = InputFileUtils.hasFinished(inputFile) || !(i < parsers.length);
		}
		return true;
	}

	protected boolean match(InputFile inputFile, LineParser pi) {
		boolean res = pi.match(inputFile, linesFactoryBuilder);
		if (res) InputFileUtils.moveNextIfNotFinished(inputFile);
		return res;
	}

	protected boolean match(InputFile inputFile, SequenceParser pi) {
		int matchNumber = 0;
		boolean hasFinished = InputFileUtils.hasFinished(inputFile);
		while (!hasFinished) {
			boolean doesMatch;
			try {
				doesMatch = match(inputFile, pi.getItemParser());
			} catch (Exception e) {
				doesMatch = false;
			}
			if (!doesMatch) hasFinished = true;
			else matchNumber++;
		}
		return pi.getQuantifier().isIn(matchNumber);
	}

	
	public LinesFactoryBuilder getLinesFactoryBuilder() {
		return linesFactoryBuilder;
	}
	
}
