/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.flatmodel.IXmlRecord;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathToObjectConversionInfoEqualsHelper;
import it.nch.eb.flatx.flatmodel.conversioninfo.XmlRecordIXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.OrXPathPositionPredicate;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPositionPredicate;
import it.nch.eb.flatx.flatmodel.xpath.XPathStartsWith;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope.Entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author gdefacci
 */
public class RecordSeqXPathConversionInfo implements XmlRecordIXPathToObjectConversionInfo{
	
	private static final long serialVersionUID = 127674503232157755L;

	private static final NewInstanceObjectBuilder	SEQ_OBJECT_BUILDER	= new NewInstanceObjectBuilder(Collection.class, ArrayList.class);
	
	private final XmlRecord itemRecord;
	private final ObjectBuilder itemObjectBuilder;
	private String name;
	private final int	position;
	private final ObjectBuilder	seqObjectBuilder;

	public RecordSeqXPathConversionInfo(int pos, XmlRecord itemRecord, ObjectBuilder itemObjectBuilder) {
		this(pos, itemRecord, itemObjectBuilder, SEQ_OBJECT_BUILDER);
	}
	
	public RecordSeqXPathConversionInfo(int pos, XmlRecord itemRecord, ObjectBuilder itemObjectBuilder, ObjectBuilder seqObjectBuilder) {
		this.itemRecord = itemRecord;
		this.position = pos;
		this.itemObjectBuilder = itemObjectBuilder;
		this.seqObjectBuilder = seqObjectBuilder;
	}

	public Class getTargetClass() {
		return seqObjectBuilder.getProductClass();
	}
	
	public static XPathPositionPredicate startsWithOrIsDefinedXPathPositionPredicate(XPathPosition ipos, 
			final IXPathMapScope elem) {
		return new OrXPathPositionPredicate (new XPathPositionPredicate[] {
				new XPathStartsWith(ipos),
				new XPathPositionPredicate() {

					public boolean match(BaseXPathPosition pos) {
						return elem.getDefitions().contains(pos);
					}
					
				},
		}) ;
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elem) {
		XPathPosition baseXPthOrig = XPathsParser.instance.parseXPathPosition(  itemRecord.getBaseXPath() );
		
		XPathPosition rpos; 
		if (XPathUtils.sharedInstance.isRelativePosition( baseXPthOrig )) {
			rpos = pos.concat(baseXPthOrig);
		} else {
			rpos = baseXPthOrig;
		}
		
		Set xpathsDefinited = new TreeSet();
		
		IXPathToObjectConversionInfo[] cis = itemRecord.getConversionInfos();
		for (int i = 0; i < cis.length; i++) {
			IXPathToObjectConversionInfo ci = cis[i];
			if (ci instanceof XPathToObjectConversionInfo) {
				XPathToObjectConversionInfo xci = (XPathToObjectConversionInfo) ci;
				if (XPathUtils.sharedInstance.isRelativePosition( xci.getXPath()) ) {
					BaseXPathPosition ps = rpos.concat( xci.getXPath() );
					xpathsDefinited.add(ps);
					elem.define(ps);
				}
			}
		}
		
		Iterator entries = elem.entries();
		Set basePaths = new TreeSet();
		XPathPosition unindexdRpos = (XPathPosition) rpos.getUnindexed();
		for (Iterator it = entries; it.hasNext();) {
			IXPathMapScope.Entry entry = (Entry) it.next();
			if (entry.getPosition().getUnindexed().equals(unindexdRpos)) {
				basePaths.add(entry.getPosition());
			}
		}
		
		Collection resColl = null;
		if (!basePaths.isEmpty()) {
			resColl = (Collection) seqObjectBuilder.create();
			for (Iterator it = basePaths.iterator(); it.hasNext();) {
				XPathPosition baseXPth = (XPathPosition) it.next();
				IXPathMapScope vw = elem.view(new XPathStartsWith(baseXPth));
				Object item = itemRecord.get(itemObjectBuilder, baseXPth, vw);
				if (item!=null) resColl.add(item);
			}
		}
		
		for (Iterator it = xpathsDefinited.iterator(); it.hasNext();) {
			BaseXPathPosition ps = (BaseXPathPosition) it.next();
			elem.undefine(ps);
		}
		
		return resColl;
		
		/*
		XPathPosition baseXPth = XPathUtils.sharedInstance.adaptIndexes(pos, rpos);
		
		int idx = 1;
		XPathPosition ipos = baseXPth.withIndex(idx);
		IXPathMapScope vw = elem.view(new XPathStartsWith(ipos));
		boolean finished = vw.isEmpty();
		
		if (finished) return null;
		else {
			Collection resColl = (Collection) seqObjectBuilder.create();
			
			while (!finished) {
				Object item = itemRecord.get(itemObjectBuilder, ipos, elem);
				if (item!=null) resColl.add(item);
				
				idx ++;
				ipos = baseXPth.withIndex(idx);
				IXPathMapScope vw1 = elem.view(new XPathStartsWith(ipos));
				vw = elem.view(startsWithOrIsDefinedXPathPositionPredicate(ipos, elem));
				finished = vw1.isEmpty();
			}
			
			return resColl;
		}
		*/
	}
	
	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int hashCode() {
		return XPathToObjectConversionInfoEqualsHelper.instance.hashCode(this);
	}

	public int compareTo(Object o) {
		return XPathToObjectConversionInfoEqualsHelper.instance.compare(this, o);
	}
	
	public IXmlRecord getXmlRecord() {
		return itemRecord;
	}
	
	public ObjectBuilder getItemObjectBuilder() {
		return itemObjectBuilder;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("RecordSeqXPathConversionInfo(");
		sb.append("name:");
		sb.append(getName());
		sb.append(", position:");
		sb.append(getPosition());
		sb.append(", targetClass:");
		sb.append(getTargetClass());
		sb.append(")");
		return sb.toString();
	}
	
	

}
