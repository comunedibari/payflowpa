/**
 * Created on 22/apr/07
 */
package it.nch.fwk.checks.context.xom;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.context.CheckContext;
import it.nch.fwk.checks.errors.ErrorCollectorCheck;
import it.nch.fwk.core.NamespacesInfos;
import nu.xom.Element;


/**
 * @author gdefacci
 */
public class ElementCheckContext extends CheckContext {
	
	private static final long	serialVersionUID	= 7331304049569099228L;
	
	private ElementCursorFactory	elementCursorFactory;
	private ChecksFactory			checksFactory;
	private NamespacesInfos			queryNamespaces;
	
	/**
	 * @deprecated, use ElementCheckContext(CheckContext context, NamespacesInfos qnamespaceInfo) 
	 */
	public ElementCheckContext(NamespacesInfos namespaces) {
		this.queryNamespaces = namespaces;
		initContext();
	}
	
	public ElementCheckContext(CheckContext context,
			NamespacesInfos qnamespaceInfo) {
		super(context);
		this.queryNamespaces = qnamespaceInfo;
		initContext();
	}

	protected void initContext() {
		elementCursorFactory = new ElementCursorFactory(queryNamespaces);
		checksFactory = new ChecksFactory();
	}
	
	public ErrorCollectorCheck createCheck(Class klass, Element element) {
		return createCheck(getName(klass), element);
	}
	public ErrorCollectorCheck createCheck(String id, Element element) {
		ErrorCollectorCheck check = checksFactory.createCheck(id, element);
		return enhance(check);
	}
	
	public ErrorCollectorCheck createUCheck(Class klass, Element element) {
		ErrorCollectorCheck check = checksFactory.createUCheck(klass, elementCursorFactory.create(element));
		return enhance(check);
	}
	
	public ErrorCollectorCheck createUCheck(Class klass, Element element, int idx) {
		ErrorCollectorCheck check = checksFactory.createUCheck(klass, elementCursorFactory.create(element,idx));
		return enhance(check);
	}
	
	public ErrorCollectorCheck createUCheck(Class klass, IElementCursor element) {
		ErrorCollectorCheck check = checksFactory.createUCheck(klass, element);
		return enhance(check);
	}

	protected ErrorCollectorCheck enhance(ErrorCollectorCheck check) {
		check.setCheckContext(this);
		return check;
	}
	
	public void registerCheck(Class klass) {
		String id = getName(klass);
		registerCheck(klass, id);
	}
	public void registerCheck(Class klass, String id) {
		checksFactory.registercheck(id, klass);
	}
	/**
	 * strategy to customize check id
	 */
	protected String getName(Class klass) {
		return klass.getName();
	}

	public ElementCursorFactory getElementCursorFactory() {
		return elementCursorFactory;
	}

	protected NamespacesInfos getQueryNamespaces() {
		return queryNamespaces;
	}

	public void setQueryNamespaces(NamespacesInfos queryNamespaces) {
		this.queryNamespaces = queryNamespaces;
	}


}
