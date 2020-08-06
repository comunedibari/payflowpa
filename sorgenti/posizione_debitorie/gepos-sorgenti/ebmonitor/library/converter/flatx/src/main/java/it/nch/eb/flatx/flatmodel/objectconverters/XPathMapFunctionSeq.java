/**
 * 29/apr/2009
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.flatmodel.conversioninfo.TypedXPathMapFunction;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapFunction;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathStartsWith;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author gdefacci
 */
public class XPathMapFunctionSeq implements TypedXPathMapFunction {
	
	private static final NewInstanceObjectBuilder	SEQ_OBJECT_BUILDER	= new NewInstanceObjectBuilder(Collection.class, ArrayList.class);
	
	private XPathPosition baseItemXPath;
	private XPathMapFunction itemFunction;
	private ObjectBuilder	seqObjectBuilder;

	public XPathMapFunctionSeq(XPathPosition baseItemXPath,
			XPathMapFunction itemFunction) {
		this(baseItemXPath, itemFunction, SEQ_OBJECT_BUILDER);
	}
	
	public XPathMapFunctionSeq(XPathPosition baseItemXPath,
			XPathMapFunction itemFunction, ObjectBuilder seqObjectBuilder) {
		this.baseItemXPath = baseItemXPath;
		this.itemFunction = itemFunction;
		this.seqObjectBuilder = seqObjectBuilder;
	}

	public Class getTargetClass() {
		return seqObjectBuilder.getProductClass();
	}
	
	public XPathMapFunction getXpathMapFunction() {
		return this.itemFunction;
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elem) {
		XPathPosition baseXPthOrig = baseItemXPath;
		XPathPosition baseXPth = XPathUtils.sharedInstance.adaptIndexes(pos, baseXPthOrig);
		
		int idx = 1;
		XPathPosition ipos = baseXPth.withIndex(idx);
		IXPathMapScope vw = elem.view(RecordSeqXPathConversionInfo.startsWithOrIsDefinedXPathPositionPredicate(ipos, elem));
		boolean finished = vw.isEmpty();
		
		if (finished) return null;
		else {
			Collection resColl = (Collection) seqObjectBuilder.create();
			
			while (!finished) {
				Object item = itemFunction.getValue(ipos, vw);
				if (item!=null) resColl.add(item);
				
				idx ++;
				ipos = baseXPth.withIndex(idx);
				IXPathMapScope vw1 = elem.view(new XPathStartsWith(ipos));
				vw = elem.view(RecordSeqXPathConversionInfo.startsWithOrIsDefinedXPathPositionPredicate(ipos, elem));
				finished = vw1.isEmpty();
			}
			
			return resColl;
		}
	}

	
}
