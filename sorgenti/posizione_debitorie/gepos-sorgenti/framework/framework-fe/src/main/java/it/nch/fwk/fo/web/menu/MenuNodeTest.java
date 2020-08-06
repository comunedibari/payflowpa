/*
 * Creato il 27-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;

import java.util.Collection;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class MenuNodeTest implements MenuNode {



		private String code;

		private String label;

		private String url;

		private boolean stato;

		private Collection children;

		public void setStatoDB(Integer stato){
		}


		public Integer getStatoDB(){
			return null;
		}

		public void setCode(String code){
			this.code = code;
		}

		public String getCode(){
			return code;
		}

		public void setLabel(String label){
			this.label = label;
		}

		public String getLabel(){
			return label;
		}

		public void setUrl(String url){
			this.url = url;
		}

		public String getUrl(){
			return url;
		}

		public void setStato(boolean stato){
			this.stato = stato;
		}

		public boolean getStato(){
			return stato;
		}

		public void setChildren(Collection children){
			this.children = children;
		}

		public Collection getChildren(){
			return children;
		}

		public String toString(){
			return "MenuNodeTest: "+getCode();
		}

}
