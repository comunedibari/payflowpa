package it.nch.fwk.fo.web.resources.util;

import java.util.ArrayList;

public class MessageNode {

	private MessageNode parent;

	private MessageNode[] children;

	private String value;

	public MessageNode(String value, MessageNode parent, MessageNode[] children) {
		this.value = value;
		this.parent = parent;
		this.children = children;
		if (parent != null) {
			parent.addChild(this);
		}
	}

	public MessageNode(String value, MessageNode parent) {
		this(value, parent, null);
	}

	public MessageNode(String value) {
		this(value, null, null);
	}

	public MessageNode[] getChildren() {
		return children;
	}

	public void removeChild(MessageNode node) {
		ArrayList list = new ArrayList();
		if (children != null && children.length > 0) {
			for (int i = 0; i < children.length; i++) {
				if (children[i].getValue().equals(node.getValue())) {
				}
				list.add(children[i]);
			}
		}
		children = (MessageNode[]) list.toArray(new MessageNode[0]);
	}

	public void addChild(MessageNode node) {
		ArrayList list = new ArrayList();
		boolean add = true;
		if (children != null && children.length > 0) {
			for (int i = 0; i < children.length; i++) {
				if (children[i].getValue().equals(node.getValue())) {
					add = false;
				}
				list.add(children[i]);
			}
		}
		if (add) {
			list.add(node);
		}
		children = (MessageNode[]) list.toArray(new MessageNode[0]);
	}

	public MessageNode getRoot() {
		boolean haveParent = true;
		MessageNode node = this;
		while (haveParent) {
			if (!node.hasParent()) {
				haveParent = false;
			} else {
				node = node.getParent();
			}
		}
		return node;
	}

	public boolean hasParent() {
		return (this.parent == null ? false : true);
	}

	public String getValue() {
		return value;
	}

	public MessageNode getParent() {
		return parent;
	}

	public void setChildren(MessageNode[] children) {
		this.children = children;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setParent(MessageNode parent) {
		parent.removeChild(this);
		this.parent = parent;
		parent.addChild(this);
	}

	public boolean isCircular() {
		boolean status = false;
		if (children != null && children.length > 0) {
			MessageNode[] parents = getTestableNodes();
			MessageNode[] nodes = this.getChildren();
			for (int c = 0; c < nodes.length; c++) {
				MessageNode child = nodes[c];
				// if any child refers to myself.... im circular
				if (child.getValue().equals(this.getValue())) {
					status = true;
				}
				// if any child refers to a parent... im circular
				for (int p = 0; p < parents.length; p++) {
					MessageNode parent = parents[p];
					if (parent.getValue().equals(child.getValue())) {
						status = true;
					}
				}
			}
		}
		return status;
	}

	private MessageNode[] getTestableNodes() {
		boolean haveParent = true;
		MessageNode node = this;
		ArrayList parents = new ArrayList();
		while (haveParent) {
			node = node.getParent();
			if (node == null) {
				haveParent = false;
			} else {
				parents.add(node);
			}
		}
		MessageNode[] nodes = (MessageNode[]) parents
				.toArray(new MessageNode[0]);
		return nodes;
	}
}