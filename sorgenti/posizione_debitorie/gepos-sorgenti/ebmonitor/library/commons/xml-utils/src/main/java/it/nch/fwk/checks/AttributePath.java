/**
 * Created on 10/gen/08
 */
package it.nch.fwk.checks;



/**
 * @author gdefacci
 */
public class AttributePath {		
	final String childXpath;
	final String attributeName;
	final IElementCursor element;
	
	AttributePath(IElementCursor element, String childXpath, String attributeName) {
		this.element = element;
		this.childXpath = childXpath;
		this.attributeName = attributeName;
	}
	
	static String stripTrailing(String str, String suffix) {
		if (str.endsWith(suffix)) {
			String res = str.substring(0, str.length() - suffix.length());
			return stripTrailing(res, suffix);
		}
		return str;
	}

	public static AttributePath create(IElementCursor element, String fullXPath) {
		if (element == null) throw new IllegalStateException();
		if (fullXPath.indexOf("@")<0) return null;	// does not indicate a attribute 
		String[] parts = fullXPath.split("@");
		
		if (parts.length==1) return new AttributePath(element, null, parts[0]);  			// the xpath simply specify an attribute
		else if (parts.length==2) {																// the xpath (probably) specify an attribute on a child
			String childPath = stripTrailing(parts[0], "/");
			if (childPath.trim().equals("")) childPath = null;
			return new AttributePath(element, childPath, parts[1]); 						
		}
		else throw new IllegalStateException();
	}
	
	public String value(boolean optional) {
		IElementCursor elem = childXpath==null ? 
				element	:
				element.optionalChild(childXpath);
		if (childXpath==null ) {
			elem = element;
		} else {
			if (optional) elem = element.optionalChild(childXpath);
			else elem = element.child(childXpath);
		}
		
//		if (elem==null || NullElementCursor.isNullElementCursor(elem)) {
//			if (!optional)
//				throw new IllegalStateException("cant find the mandatory xpath " + StringUtils.concatPaths(element.getPath(), childXpath));
//			else 
//				return null;			
//		}
		return elem.attributeStringValue(attributeName);
	}
}