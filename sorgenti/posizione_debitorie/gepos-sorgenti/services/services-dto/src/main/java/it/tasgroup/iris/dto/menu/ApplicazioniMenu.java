package it.tasgroup.iris.dto.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;

import java.util.Collection;
import java.util.LinkedHashSet;

public class ApplicazioniMenu implements MenuNode {

	private static final long serialVersionUID = 1L;

	private String code;
	private String label;
	private boolean stato;
	private String url;
	private Collection<AreeMenu> children = new LinkedHashSet<AreeMenu>();

	// public ApplicazioniMenu(Applicazioni applPojo){
	// this.code = applPojo.getApplicationCode();
	// this.setApplicationCode(this.code);
	// Collection coll = applPojo.getAree();
	// if (coll!=null){
	// Iterator iter = coll.iterator();
	// while(iter.hasNext())
	// {
	// children.add(new AreeMenu((Area)iter.next()));
	// }
	// }
	// }
	public ApplicazioniMenu() {

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

	public Collection<AreeMenu> getChildren() {
		return this.children;
	}

	public String toString() {
		return "ApplicazioniMenu: " + getCode();
	}

	public Object clone() {

		ApplicazioniMenu _pojoCurrent = this;
		ApplicazioniMenu _pojo = new ApplicazioniMenu();

		_pojo.setCode(_pojoCurrent.getCode());
		_pojo.setLabel(_pojoCurrent.getLabel());
		_pojo.setStato(_pojoCurrent.getStato());
		_pojo.setStatoDB(_pojoCurrent.getStatoDB());
		_pojo.setUrl(_pojoCurrent.getUrl());

		return _pojo;
	}

}
