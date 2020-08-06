/**
 * Created on 04/apr/07
 */
package it.nch.fwk.checks.xom;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.fwk.checks.AbstractElementCursor;
import it.nch.fwk.checks.Attribute;
import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.jaxen.XomIteratorFactory;
import it.nch.fwk.core.NamespaceInfo;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.fwk.core.XOMQueriesUtils;

import java.util.Iterator;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParentNode;

/**
 * @author gdefacci
 */
public class ElementCursor extends AbstractElementCursor {
	
//	static Logger log = Logger.getLogger(ElementCursor.class);

	private static final String	EMPTY_ELEMENT_VALUE	= "";
	private static final String	PATH_SEGMENT_SEPARATOR	= "/";
	private static final int	NO_INDEX_VALUE	= -1;
	private static final String	NAME_SPACE_SEPARATOR	= ":";
	private static org.slf4j.Logger log = ResourcesUtil.createLogger(ElementCursor.class);
	
	private Element	currentElement;
	private int idx;
	
	public ElementCursor(Element element, NamespacesInfos queryNamespaces){
		this(element, NO_INDEX_VALUE, queryNamespaces);
	}
	public ElementCursor(Element element, int i, NamespacesInfos queryNamespaces){
		super(queryNamespaces);
		setCurrentElement(element);
		this.idx = i;
	}

	public ElementCursor(Element elem) {
		this(elem, null);
	}
	public Element currentElement() {
		return currentElement;
	}
	public void setCurrentElement(Element element) {
		this.currentElement = element;
	}
	private String path;
	public String getPath() {
		if (path==null) {
			path = calculateXPath(currentElement);
		}
		return path;
	}
	
	private String ixdexedPath;
	public String getIndexsPath() {
		if (ixdexedPath==null) {
			ixdexedPath = calculateIndexedXPath(currentElement);
		}
		return ixdexedPath;
	}
	
	public String getPath(String relativePath) {
		String partial = getPath();
		return partial + PATH_SEGMENT_SEPARATOR + relativePath;
	}
	
	
//	works for not detached element
	private String calculateXPathOld(Element element) {
		StringBuffer sb = new StringBuffer(getPathSegment(element));
		ParentNode parent = element.getParent();
		while ((parent!=null) && (parent instanceof Element)) {
			String pathSegment = getPathSegment( ((Element)parent) );
			sb.insert(0, pathSegment + PATH_SEGMENT_SEPARATOR);
			parent = parent.getParent();
		}
		String idxString = idx == NO_INDEX_VALUE ? EMPTY_ELEMENT_VALUE : "[" + (idx + 1 )+ "]";
		String res = sb.toString() + idxString;
		if (!res.startsWith(PATH_SEGMENT_SEPARATOR)) res = PATH_SEGMENT_SEPARATOR + res;
		return res;
	}
	
	private boolean sameName(Element el1, Element el2) {
		return el1.getLocalName().equals(el2.getLocalName())
			&& el1.getNamespaceURI().equals(el2.getNamespaceURI());
	}
	
	private String calculateXPath(Element element) {
		StringBuffer sb = new StringBuffer();
		ParentNode parent = element.getParent();
		if (parent!=null) {
			int idx = 1;
			boolean found = false;
			int chldCount = parent.getChildCount();
			for (int i = 0; i < chldCount && !found; i++) {
				Node chi = parent.getChild(i);
				if (chi instanceof Element) {
					Element eli = (Element) chi;
					if (eli == element) found = true;
					else {
						if (sameName(element, eli)) {
							idx++;
						}
					}
				}
			}
			if (parent instanceof Element) {
				sb.append(calculateXPath((Element) parent));
				sb.append("/");
				sb.append(getPathSegment(element));
				if (idx > 1) {
					sb.append("[");
					sb.append(idx);
					sb.append("]");
				}
			} else {
				sb.append("/");
				sb.append(getPathSegment(element));
			}
		}
		return sb.toString();
	}
	
	private String calculateIndexedXPath(Element element) {
		StringBuffer sb = new StringBuffer();
		ParentNode parent = element.getParent();
		String res;
		if (parent==null || parent instanceof Document) res = getPathSegment(element);
		else {
			sb.append( getIndexedPathSegment(parent, element) );
			while ((parent!=null) && (parent instanceof Element)) {
				ParentNode grParent = parent.getParent();
				String pathSegment = getIndexedPathSegment(grParent, (Element)parent);
				sb.insert(0, pathSegment + PATH_SEGMENT_SEPARATOR);
				parent = grParent;
			}
			res = sb.toString();
		}
		if (!res.startsWith(PATH_SEGMENT_SEPARATOR)) res = PATH_SEGMENT_SEPARATOR + res;
		return res;
	}
	
	private String getIndexedPathSegment(ParentNode parent, Element element) {
		String pathSegment = getPathSegment(element);
		Nodes nodes = XOMQueriesUtils.xquery(parent, pathSegment, getNamespaces());
		if (nodes.size()==0) 
			throw new IllegalStateException("cant find child named " + pathSegment + " inside " + parent );
		else if (nodes.size() > 1) {
			int cnt = 1;
			while (cnt <= nodes.size() ) {
				String pseg = pathSegment + "[" + cnt + "]";
				Nodes nds = XOMQueriesUtils.xquery(parent, pseg, getNamespaces());
				if (nds.size()!=1) throw new IllegalStateException();
				else {
					Node chld = nds.get(0);
					if (element.equals(chld)) return pseg;
				}
				cnt++;
			}
			throw new IllegalStateException();
		} else return pathSegment;
	}
	private String getPathSegment(Element element) {
		if (getNamespaces()==null) return element.getQualifiedName();
		String nsUri = element.getNamespaceURI();
		NamespaceInfo ni = getNamespaces().getNamespaceInfoByUri(nsUri);
		if (ni==null) {
			throw new IllegalStateException("can get a binding for namespace uri " + nsUri
					+ "while parsing element " + element);
		}
				
		return ni.prefix + NAME_SPACE_SEPARATOR + element.getLocalName();
	}
	
	public IElementCursor getParent() {
		ParentNode parent = this.currentElement.getParent();
		if (parent==null || parent instanceof Document) return null;
		Element parentElement = (Element)parent;
		return new ElementCursor(parentElement, getNamespaces());
	}
	
	//	** nu.xom.Element utils
	public Element childElement(String name, int idx) {
		Nodes children = childrenNodes(name);
		if ((children==null) || !(children.size()>idx)) return null;
		return (Element)children.get(idx);
	}
	public Element childElement(String name) {
		return childElement(name,0);
	}
//	nu.xom.Element's and nu.xom.Attribute's are both nu.xom.Node's
	public Node childNode(String name, int idx) {
		Nodes children = childrenNodes(name);
		if ((children==null) || !(children.size()>idx)) return null;
		return children.get(idx);
	}
	public Node childNode(String name) {
		return childNode(name,0);
	}

	//	attributes utils
	public Attribute attribute(String name) {
		nu.xom.Attribute attr = currentElement.getAttribute(name);
		if (attr==null) return null;
		return new XomAttribute(attr);
	}
	//	children utils
	public Nodes childrenNodes(final String xquery) {
		Nodes nodes = optionalChildren(xquery);
		if ((nodes==null) || (nodes.size()<1)) {
			String currentValue = currentElement == null ? "null" : currentElement.toXML();
			String msg = "children named : " + xquery + " \ndoesnt exist for element\n" + currentValue;
			log.error( msg );
//			new Exception(msg).printStackTrace();
//			throw new RuntimeException(msg);
		}
		return nodes;
	}
	
	public IElementCursor[] chidren(final String xquery) {
		Nodes nodes = optionalChildren(xquery);
		if ((nodes==null) || (nodes.size()<1)) {
			return new IElementCursor[0];
		} else {
			IElementCursor[] res = new IElementCursor[nodes.size()];
			for (int i=0; i < nodes.size(); i++) {
				res[i] = new ElementCursor((Element) nodes.get(i), i, this.getNamespaces());
			}
			return res;
		}
	}
	
	public Nodes optionalChildren(final String xquery) {

		Nodes nodes = XOMQueriesUtils.xquery(currentElement, xquery , getNamespaces());
		return nodes;
	}
	
	public IElementCursor optionalChild(String xpath) {
		Nodes children = optionalChildren(xpath);
		if ((children==null) || !(children.size()>0)) return new NullElementCursor(this.getNamespaces(), xpath);
		return new ElementCursor( (Element)children.get(0), 0, getNamespaces());
	}
	
	public IElementCursor child(String name, int idx) {
		if (name.equals("..")) return this.getParent();
		if (name.equals(".")) return this;
		Nodes children = childrenNodes(name);
		if ((children==null) || !(children.size()>idx)) return null;
		Node node = children.get(idx);

		return new ElementCursor( (Element)node, idx, getNamespaces());
	}
	
	public String getName() {
		return currentElement().getQualifiedName();
	}
	
	public String getValue() {
		return currentElement().getValue().trim();
	}
	
	public String getPrefix() {
		if (getName().indexOf(NAME_SPACE_SEPARATOR)>-1) {
			return getName().split(NAME_SPACE_SEPARATOR)[0];
		}
		return EMPTY_ELEMENT_VALUE;
	}
	
	public Iterator xpathIterator(String xpath) {
		return new XomIteratorFactory(this.getNamespaces()).xpathIterator(this, xpath);
	}
	
	public String toString() {
		if (currentElement==null) return "NULL";
		return currentElement.toXML();
	}
	
	public Iterator iterator(String name) {
		return new XomIteratorFactory(this.getNamespaces()).getIterator(name, this);
	}
	
}
