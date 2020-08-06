package it.nch.fwk.xml.validation;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

/**
 * @author FG@2007
 */
public class SchemaElement {
	
	private static final Logger log = Logger.getLogger(SchemaElement.class);
	
	public static final String QUALIFIED_NAME_SEPARATOR = ":";
	public static final String EMPTY_NS_PREFIX = "";
	
	//THIS IS THE PREFIX FOR DEFAULT NAMESPACE
	//FIXME: PASS QUERY NAMESPACES BY CONSTRUCTOR FROM CONTEXT (DEFINE SCHEMACHECKCONTEXT...)
	public static final String DEFAULT_NS_PREFIX = "ROOT";

	private static final String	PATH_SEGMENT_SEPARATOR	= "/";

	private String qualifiedName;
	private String  localName;
	private String  prefix;
	private String uri;
	private boolean start;
	private String  xPath;
	private String  value ;
	private Attributes attributes;
	
	public SchemaElement(String uri, String localName, String qName,
			Attributes attributes, String currentPath, boolean start) {
		super();
		this.start = start;
		this.qualifiedName = qName;
		this.localName =  localName;
		this.prefix = getPrefixFromQualifiedName();
		this.uri = uri;
		this.xPath = calculateXPath(currentPath);
		this.value = "NO_VALUE_SPECIFIED";
		this.attributes = attributes;
	}
	
	private String getPrefixFromQualifiedName() {
		if(this.qualifiedName.indexOf(SchemaElement.QUALIFIED_NAME_SEPARATOR) != -1)
			return this.qualifiedName.split(SchemaElement.QUALIFIED_NAME_SEPARATOR)[0];
		else
			return SchemaElement.EMPTY_NS_PREFIX;
	}
	private String getLocalNameFromQualifiedName() {
		if(this.qualifiedName.indexOf(SchemaElement.QUALIFIED_NAME_SEPARATOR) != -1)
			return this.qualifiedName.split(SchemaElement.QUALIFIED_NAME_SEPARATOR)[1];
		else
			return this.qualifiedName;
	}
	private String calculateXPath(String parentPath) {
		String elementName = (prefix.equalsIgnoreCase(EMPTY_NS_PREFIX)) ? DEFAULT_NS_PREFIX + QUALIFIED_NAME_SEPARATOR + localName : prefix + QUALIFIED_NAME_SEPARATOR + localName;
		if(this.start)
			xPath = (parentPath == null) ? "/"+elementName : parentPath + PATH_SEGMENT_SEPARATOR + elementName;
		else{
			xPath = (parentPath.lastIndexOf(elementName) > 0) ? parentPath.substring(0, parentPath.lastIndexOf(elementName)-1) : parentPath;
			qualifiedName = xPath.substring(xPath.lastIndexOf(PATH_SEGMENT_SEPARATOR)+1,xPath.length());
			localName = getLocalNameFromQualifiedName();
			prefix = getPrefixFromQualifiedName();
		}			
		return 	xPath;
	}
	
	public boolean equalsLocalName(String elementLocalName){
		return this.localName.equalsIgnoreCase(elementLocalName);
	}
	
	public void logAttributes(){
		Attributes attributes = this.getAttributes();
		for(int a=0; attributes!=null && a < attributes.getLength(); a++){
			log.debug("ATTRIBUTE_QUALIFIED_NAME="+attributes.getQName(a));
			log.debug("ATTRIBUTE_VALUE="+attributes.getValue(a));
			log.debug("ATTRIBUTE_URI="+attributes.getURI(a));
			log.debug("ATTRIBUTE_LOCALNAME="+attributes.getLocalName(a));
			log.debug("ATTRIBUTE_TYPE="+attributes.getType(a));			
		}
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public String getXPath() {
		return xPath;
	}

	public void setXPath(String path) {
		xPath = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

//	public static String getEMPTY_PREFIX() {
//		return EMPTY_NS_PREFIX;
//	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}
}
