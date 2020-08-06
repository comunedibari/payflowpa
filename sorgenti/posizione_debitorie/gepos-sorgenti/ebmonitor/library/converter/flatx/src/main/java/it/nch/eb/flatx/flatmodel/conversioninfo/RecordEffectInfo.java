/**
 * Created on 02/set/2008
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BaseParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.LineParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.SequenceParser;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class RecordEffectInfo {
	
	private final IRecord		record;
	private final ObjectBuilder	objectBuilder;
	private final boolean 		collectList;
	private final String		nameInMap;
	private final String 		propertyName;
	private final XPathPosition	pos;
	
	public RecordEffectInfo(XPathPosition pos,
			ObjectBuilder objectBuilder, 
			String nameInMap, 
			String pname, 
			boolean collectList) {
		this.record = null;
		this.objectBuilder = objectBuilder;
		this.collectList = collectList;
		this.nameInMap = nameInMap;
		this.propertyName = pname;
		this.pos = pos;
	}
	
//	public RecordEffectInfo(IRecord record, 
//			ObjectBuilder objectBuilder, 
//			String nameInMap, 
//			String pname, 
//			boolean collectList) {
//		super();
//		this.record = record;
//		this.objectBuilder = objectBuilder;
//		this.collectList = collectList;
//		this.nameInMap = nameInMap;
//		this.propertyName = pname;
//		this.pos = XPathsParser.instance.parseXPathPosition( record.getBaseXPath() );
//	}
	
	public RecordEffectInfo(IParser parser) {
		if (parser instanceof SequenceParser) {
			this.collectList = true;
			IParser itemParser = ((SequenceParser)parser).getItemParser();
			this.objectBuilder = itemParser.getObjectBuilder();
			if (itemParser instanceof LineParser) {
				this.record = ((LineParser)itemParser).getIRecord();
			} else {
				this.record = null;
			}
		} else {
			this.collectList = false;
			this.objectBuilder = parser.getObjectBuilder();
			if (parser instanceof LineParser) {
				this.record = ((LineParser)parser).getIRecord();
			} else {
				this.record = null;
			}
		}
		
		this.nameInMap = this.propertyName = parser.getName();
		this.pos = BaseParser.findBaseXPath(parser);
	}
	
	public RecordEffectInfo(XPathPosition xpthPos, IParser parser) {
		if (parser instanceof SequenceParser) {
			this.collectList = true;
			IParser itemParser = ((SequenceParser)parser).getItemParser();
			this.objectBuilder = itemParser.getObjectBuilder();
			if (itemParser instanceof LineParser) {
				this.record = ((LineParser)itemParser).getIRecord();
			} else {
				this.record = null;
			}
		} else {
			this.collectList = false;
			this.objectBuilder = parser.getObjectBuilder();
			if (parser instanceof LineParser) {
				this.record = ((LineParser)parser).getIRecord();
			} else {
				this.record = null;
			}
		}
		
		this.nameInMap = this.propertyName = parser.getName();
		this.pos = xpthPos;
	}
	
	public IRecord getRecord() {
		return record;
	}
	public ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}
	public boolean isCollectList() {
		return collectList;
	}
	public String getNameInMap() {
		return nameInMap;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public XPathPosition getPosition() {
		return pos;
	}
	
}