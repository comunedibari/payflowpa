package it.tasgroup.iris.dto.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;

import java.util.Collection;
import java.util.LinkedHashSet;

public class RootMenu implements MenuNode {

	private static final long serialVersionUID = 1L;

	private Collection children = new LinkedHashSet();

	
	public void setStatoDB(Integer stato){
	};

	public Integer getStatoDB(){
		return null;
	};

	public void setCode(String code) {
	}

	public String getCode() {
		return null;
	}

	public void setLabel(String label) {
	}

	public String getLabel() {
		return null;
	}

	public void setUrl(String url) {
	}

	public String getUrl() {
		return null;
	}

	public void setStato(boolean stato) {
	}

	public boolean getStato() {
		return false;
	}

	public void setChildren(Collection children) {
		this.children = children;
	}

	public Collection getChildren() {
		return this.children;
	}

	public String toString(){
		return "RootMenu: "+getCode();
	}

}
