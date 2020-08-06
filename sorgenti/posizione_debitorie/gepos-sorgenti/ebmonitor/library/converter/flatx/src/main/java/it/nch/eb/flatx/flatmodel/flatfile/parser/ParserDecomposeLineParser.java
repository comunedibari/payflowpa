/**
 * Created on 20/gen/09
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.RecordImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * decompose a IParser instance i a ordered array of LineParser instances
 * @author gdefacci
 */
public class ParserDecomposeLineParser {
	
	public IRecord[] flatMapRecords(IParser parser) {
		LineParser[] lpss = flatMap(parser);
		IRecord[] recs = new RecordImpl[lpss.length];
		for (int i = 0; i < lpss.length; i++) {
			recs[i] = lpss[i].getIRecord();
		}
		return recs;
	}
	
	public LineParser[] flatMap(IParser parser) {
		List lineParsers = new ArrayList();
		onParser(parser, lineParsers);
		return (LineParser[]) lineParsers.toArray(new LineParser[0]);
	}

	private void onParser(IParser parser, List lineParsers) {
		if (parser instanceof LineParser) {
			onLineParser((LineParser) parser, lineParsers);			
		} else if (parser instanceof BeanParser) {
			onBeanParser((BeanParser) parser, lineParsers);			
		} else if (parser instanceof SequenceParser) {
			onSequenceParser((SequenceParser) parser, lineParsers);
		} else {
			throw new IllegalStateException("bug, not a recognized parser kind " + parser);
		}
	}
	
	protected void onSequenceParser(SequenceParser parser, List lineParsers) {
		IParser itemParser = parser.getItemParser();
		onParser(itemParser, lineParsers);
	}

	protected void onBeanParser(BeanParser beanParser, List lineParsers) {
		for (int i = 0; i < beanParser.getParsers().length; i++) {
			IParser parser = beanParser.getParsers()[i];
			onParser(parser, lineParsers);
		}
	}

	protected void onLineParser(LineParser lp, List parsersList) {
		parsersList.add(lp);
	}

}
