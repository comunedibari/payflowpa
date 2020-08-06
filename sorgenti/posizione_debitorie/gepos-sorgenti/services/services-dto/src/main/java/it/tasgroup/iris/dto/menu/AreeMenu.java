package it.tasgroup.iris.dto.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;

import java.util.Collection;
import java.util.LinkedHashSet;

public class AreeMenu implements MenuNode {

	private static final long serialVersionUID = 1L;

	private String code;
	private String label;
	private boolean stato;
	private String url;
	private Collection<ServiziMenu> children = new LinkedHashSet<ServiziMenu>();

	// public AreeMenu(Area areaPojo){
	// this.code = areaPojo.getAreaCode();
	// this.setAreaCode(this.code);
	// Collection coll = areaPojo.getServizi();
	// Iterator iter = coll.iterator();
	// while (iter.hasNext()){
	// children.add(new ServiziMenu((Servizi)iter.next()));
	// }
	// }

	public AreeMenu() {

	}

	public void setStatoDB(Integer stato) {
	};

	public Integer getStatoDB() {
		return null;
	};

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	public boolean getStato() {
		return this.stato;
	}

	public void setChildren(Collection children) {
		this.children = children;
	}

	public Collection<ServiziMenu> getChildren() {
		return this.children;
	}

	public String toString() {
		return "AreeMenu: " + getCode();
	}

	public Object clone() {

		AreeMenu _pojoCurrent = this;
		AreeMenu _pojo = new AreeMenu();

		_pojo.setCode(_pojoCurrent.getCode());
		_pojo.setLabel(_pojoCurrent.getLabel());
		_pojo.setStato(_pojoCurrent.getStato());
		_pojo.setStatoDB(_pojoCurrent.getStatoDB());
		_pojo.setUrl(_pojoCurrent.getUrl());

		return _pojo;
	}
}
