/**
 * Created on 23/feb/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordStructureEqualsHelper;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.util.Set;
import java.util.TreeSet;


/**
 * @author gdefacci
 */
public abstract class BaseParser implements IParser {
	
	private final ObjectBuilder objectBuilder;

	public BaseParser(ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
	}

	public final ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}

	public final Object createContainer() {
		return objectBuilder.create();
	}
	
	public final Class getProductClass() {
		return getObjectBuilder().getProductClass();
	}

	public boolean equals(Object obj) {
		return RecordStructureEqualsHelper.instance.areEquals(this, obj);
	}

	public int hashCode() {
		return RecordStructureEqualsHelper.instance.hashCode(this);
	}
	
	public XPathPosition getBasePath() {
		return findBaseXPath(this);
	}

	public static XPathPosition findBaseXPath(IParser parser) {
		XPathsParser xpathParser = XPathsParser.instance;
		XPathPosition res = null;
		if (parser instanceof LineParser) { 
			res = xpathParser.parseXPathPosition( ((LineParser)parser).getIRecord().getBaseXPath() );
		} else if (parser instanceof SequenceParser) { 
			 res = findBaseXPath(((SequenceParser)parser).getItemParser());
		} else if (parser instanceof BeanParser) {
			BeanParser beanParser = (BeanParser) parser;
			IParser[] innerParsers = beanParser.getParsers();
			Set/*<XPathPosition>*/ xpathSet = new TreeSet();
			for (int i = 0; i < innerParsers.length; i++) {
				IParser inrParser = innerParsers[i];
				XPathPosition itemXPath = findBaseXPath(inrParser);
				if (itemXPath==null) {
					throw new NullPointerException("cant find a base xpath for " + inrParser);
				}
				xpathSet.add(itemXPath);
			}
			res = XPathUtils.sharedInstance.commonXPathPositionPrefix(xpathSet);
		} else {
			throw new IllegalStateException("not recognized parser " + parser);
		}
		if (res == null) {
			throw new NullPointerException("cant find a base xpath for " + parser);
		}
		return res;
	}

}
