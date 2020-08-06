package it.tasgroup.iris.dto.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;

import java.util.Collection;
import java.util.LinkedHashSet;

public class ServiziMenu implements MenuNode {

	private static final long serialVersionUID = 1L;

	private String code;
	private String label;
	private boolean stato;
	private String url;
	private Collection<FunzioniMenu> children = new LinkedHashSet<FunzioniMenu>();

	// public ServiziMenu(Servizi servPojo){
	// this.code = servPojo.getServiceCode();
	// this.setServiceCode(this.code);
	// Collection coll = servPojo.getFunzioni();
	// Iterator iter = coll.iterator();
	// if(iter != null)
	// while (iter.hasNext()){
	// children.add(new FunzioniMenu((Funzioni)iter.next()));
	// }
	// }

	public ServiziMenu() {

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

	public Collection<FunzioniMenu> getChildren() {
		return this.children;
	}

	public String toString() {
		return "ServiziMenu: " + getCode();
	}

	public Object clone() {

		ServiziMenu _pojoCurrent = this;
		ServiziMenu _pojo = new ServiziMenu();

		_pojo.setCode(_pojoCurrent.getCode());
		_pojo.setLabel(_pojoCurrent.getLabel());
		_pojo.setStato(_pojoCurrent.getStato());
		_pojo.setStatoDB(_pojoCurrent.getStatoDB());
		_pojo.setUrl(_pojoCurrent.getUrl());

		return _pojo;
	}

}
