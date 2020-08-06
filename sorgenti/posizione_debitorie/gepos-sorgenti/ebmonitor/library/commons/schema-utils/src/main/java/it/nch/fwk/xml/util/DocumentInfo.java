package it.nch.fwk.xml.util;

import it.nch.fwk.core.NamespacesInfos;

import javax.xml.namespace.QName;

public class DocumentInfo {

	QName rootNode;
	NamespacesInfos namespaceInfos;
	
	public DocumentInfo(QName rootName, NamespacesInfos namespaceInfos) {
		super();
		this.rootNode = rootName;
		this.namespaceInfos = namespaceInfos;
	}
	
	
	public QName getRootNode() {
		return rootNode;
	}
	public void setRootNode(QName rootNode) {
		this.rootNode = rootNode;
	}
	public NamespacesInfos getNamespaceInfos() {
		return namespaceInfos;
	}
	public void setNamespaceInfos(NamespacesInfos namespaceInfos) {
		this.namespaceInfos = namespaceInfos;
	}

}
