/**
 * Created on 01/apr/07
 */
package it.nch.fwk.core;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.jaxen.NamespaceContext;
import org.jaxen.Navigator;
import org.jaxen.xom.DocumentNavigator;

import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.XPathContext;


/**
 * @author gdefacci
 */
public class XOMQueriesUtils {
	
//	private static final String	XQUERY_EMPTY_NODE_SEQUENCE	= "()";

//	public static Node stripElements(Node doc, String xpath) {
//		XQueryUtil.update(doc, xpath, XQUERY_EMPTY_NODE_SEQUENCE );
//		return doc;
//	}
//	
//	public static Node replaceElementsContent(Node doc, String xpath, String replacingExpression) {
//		XQueryUtil.update(doc, xpath, replacingExpression );
//		return doc;
//	}
	
	public static Nodes xquery(Node node, String xquery) {
		return xquery(node, xquery);
	}
	
//	TODO: to test
//	public static Node replaceElementsContent(Node node, String xquery, String replacingExpression, NamespacesInfos namespaces) {
//		Nodes nodes = null;
//		XQuery morpher;
//		StaticQueryContext queryContext = new StaticQueryContext(createConfiguration());
//		declareNamespacesInContext(queryContext, namespaces);
//		try {
//				
//			URI baseUri = null;
//			XQuery select = new XQuery(xquery,
//					baseUri ,
//					queryContext,
//					null);
//			
//			morpher = new XQuery(replacingExpression,
//					baseUri ,
//					queryContext,
//					null);
// 
//			
//			nodes = select.execute(node).toNodes();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		
//		XQueryUtil.update(nodes, morpher, null);
//		
//		return node;
//	}
	
//	public static Nodes xquery(Node node, String xquery, NamespacesInfos namespaces) {
//		Nodes nodes = null;
//		StaticQueryContext queryContext = new StaticQueryContext(createConfiguration());
//		if (namespaces!=null) declareNamespacesInContext(queryContext, namespaces);
//		try {
////			node.toXML()
//			URI baseUri = null;
//			XQuery query = new XQuery(xquery,
//					baseUri ,
//					queryContext,
//					null);
//			
//			nodes = query.execute(node).toNodes();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return nodes;
//	}
	
	public static Nodes xquery(Node node, String xquery, NamespacesInfos namespaces) {
		XPathContext nodeContext = null;
		if (namespaces!=null) {
			nodeContext = new XPathContext();
			Iterator it = namespaces.namespacesIterator();
			while (it.hasNext()) {
				NamespaceInfo ns = (NamespaceInfo) it.next();
				nodeContext.addNamespace(ns.getPrefix(), ns.getUri());
			}
		}
		Nodes res;
		if (nodeContext!=null) res = node.query(xquery, nodeContext );
		else res = node.query(xquery);
		return res;
	}
	
	public static String toString(Object bean) {
		return toString(bean, null);
	}
	
	/*
	 * FIXME should be moved from  here (to StringUtils, CommonUtils or the like)
	 */
	public static String toString(Object bean, String[] excludes) {
		StringBuffer result = new StringBuffer();
		Set exclSet = new TreeSet();
		if ((excludes!=null) && (excludes.length>0)) {
			for (int i = 0; i < excludes.length; i++)  exclSet.add( excludes[i] );
		}
		try {
			BeanInfo infos = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = infos.getPropertyDescriptors();
			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor descriptor = descriptors[i];
				String propertyName = descriptor.getName();
				if (!exclSet.contains(propertyName)) {
					String value;
					try {
						Object val = descriptor.getReadMethod().invoke(bean, new Object[] {});
						if (val!=null) {
							value = val.toString();
						} else {
							value = "NULL";
						}
					} catch (Exception e) {
						value = "COULD-NOT-COMPUTE" ;
					}
					result.append( propertyName +  "[" + value + "]\n" );
				}
			}
		} catch (Exception e) { /*IGNORE*/ }
		return result.toString();
	}
	
	
	

}
