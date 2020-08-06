package it.tasgroup.iris.dto.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;

import java.util.Collection;
import java.util.LinkedHashSet;

public class FunzioniMenu implements MenuNode {

	private static final long serialVersionUID = 1L;

	private String code;
	private String label;
	private boolean stato;
	private String url;
	private Collection children = new LinkedHashSet();
	private String tipoOper;
	private String accessed;

	public void setStatoDB(Integer stato) {
	};

	public Integer getStatoDB() {
		return null;
	};

	// public FunzioniMenu(Funzioni funzPojo){
	// this.code = funzPojo.getFunctionCode();
	// this.setFunctionCode(this.code);
	// this.setAccessed(funzPojo.getAccessed());
	// this.setTipoOper(funzPojo.getOperatorType());
	// }

	public FunzioniMenu() {

	}

	public Collection getChildren() {
		return children;
	}

	public void setChildren(Collection children) {
		this.children = children;
	}

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

	public String getTipoOper() {
		return tipoOper;
	}

	public void setTipoOper(String tipoOper) {
		this.tipoOper = tipoOper;
	}

	public String getAccessed() {
		return accessed;
	}

	public void setAccessed(String accessed) {
		this.accessed = accessed;
	}

	public String toString() {
		return "FunzioniMenu: " + getCode();
	}

	public Object clone() {

		FunzioniMenu _pojoCurrent = this;
		FunzioniMenu _pojo = new FunzioniMenu();

		_pojo.setAccessed(_pojoCurrent.getAccessed());
		_pojo.setTipoOper(_pojoCurrent.getTipoOper());

		_pojo.setCode(_pojoCurrent.getCode());
		_pojo.setLabel(_pojoCurrent.getLabel());
		_pojo.setStato(_pojoCurrent.getStato());
		_pojo.setStatoDB(_pojoCurrent.getStatoDB());
		_pojo.setUrl(_pojoCurrent.getUrl());

		return _pojo;
	}
}
