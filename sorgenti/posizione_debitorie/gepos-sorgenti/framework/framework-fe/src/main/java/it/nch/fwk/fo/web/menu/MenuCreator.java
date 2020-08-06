/*
 * Creato il 25-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.menu;

import it.nch.fwk.fo.profilo.menu.MenuNode;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Classe che gestisce la creazione di un menu ad albero.
 *
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class MenuCreator {

	/**
	 * Il nodo radice del menu
	 */
	private MenuNode root;

	private Collection menuTree;

	private PathRepository path;

	private NodeDecoder nodeDecoder;

	/*public void setMenuTree(Collection menuTree){
		this.menuTree = menuTree;
	}
	*/
	public Collection getMenuTree(){
		return menuTree;
	}

	/*public void setPath(PathRepositary path){
		this.path = path;
	}
	*/
	public PathRepository getPath(){
		return path;
	}

	/*public void setNodeDecoder(NodeDecoder nodeDecoder){
		this.nodeDecoder = nodeDecoder;
	}

	public NodeDecoder getNodeDecoder(){
		return nodeDecoder;
	}
	*/

	/**
	 * Costruttore.
	 *
	 * @param root
	 * @param path
	 * @param nodeDecoder
	 */
	public MenuCreator(MenuNode root, PathRepository path, NodeDecoder nodeDecoder){

		this.path = path;
		this.root = root;
		this.nodeDecoder = nodeDecoder;
		this.menuTree = getPopulatedTree(root.getChildren(), "");
	}

	/**
	 * Costruttore.
	 *
	 * @param path
	 * @param nodeDecoder
	 */
	public MenuCreator(PathRepository path, NodeDecoder nodeDecoder){
		this.path = path;
		this.nodeDecoder = nodeDecoder;
		this.menuTree = getPopulatedTree(treeTestCreator());
	}

	/**
	 * Metodo che restituisce l'intera alberatura di navigazione del menu.
	 *
	 * @return la radice dell'intero albero di menu.
	 */
	public MenuNode getRoot(){
		return root;
	}

	/**
	 * Metodo che restituisce lo stato di un nodo a seconda che il suo
	 * codice appartenga o meno la path.
	 *
	 * @param nodeCode
	 * @param pathCode
	 * @return
	 */
	private boolean getStato(String nodeCode, String pathCode){
		if(nodeCode.equals(pathCode))
			return true;
		else
			return false;
	}

	/**
	 * Metodo che popola il l'alberatura di menu in base
	 * alle proprietà associate ad ogni codice.
	 *
	 * @param menu
	 * @return
	 */
	private Collection getPopulatedTree (Collection menu){
		Tracer.info(this.getClass().getName(),"getPopulatedTree"," Vecchia versione",null);
		MenuNode node = null;
		Iterator iterator = menu.iterator();

		while(iterator.hasNext()){
			node = (MenuNode) iterator.next();
			node.setLabel(nodeDecoder.getLabel(node.getCode()));
			node.setUrl(nodeDecoder.getUrl(node.getCode()));
			if (node.getChildren() != null)
				node.setChildren(getPopulatedTree(node.getChildren()));
		}
		return menu;
	}

	/**
	 * Metodo che popola l'alberatura di menu in base
	 * alle proprietà associate ad ogni codice e alla sua posizione nell'alberatura di menù.
	 *
	 * @param menu
	 * @param path
	 * @return
	 */
	private Collection getPopulatedTree (Collection menu, String path){
		MenuNode node = null;
		Iterator iterator = menu.iterator();
		String pathCode = path;
		while(iterator.hasNext()){
			node = (MenuNode) iterator.next();
			if (pathCode == null || pathCode.length() == 0)
				pathCode = node.getCode();
			else
				pathCode += "-" + node.getCode();
			node.setLabel(nodeDecoder.getLabel(pathCode));
			node.setUrl(nodeDecoder.getUrl(pathCode));
			if (Tracer.isDebugEnabled(this.getClass().getName())){
				Tracer.debug(this.getClass().getName(),"getPopulatedTree","Codice: " + pathCode,null);
				Tracer.debug(this.getClass().getName(),"getPopulatedTree","Label: " + node.getLabel(),null);
				Tracer.debug(this.getClass().getName(),"getPopulatedTree","Url: " + node.getUrl(),null);
			}
			if (node.getChildren() != null)
				node.setChildren(getPopulatedTree(node.getChildren(), pathCode));
			pathCode = path;
		}
		return menu;
	}

	/**
	 * Metodo che popola il livello di menu passato in base al codice di path relativo e
	 * alle proprietà associate ad ogni codice.
	 *
	 * @param menu
	 * @param pathCode
	 * @return
	 */
	private Collection getValidatedMenu (Collection menu, String pathCode){
		MenuNode node = null;
		if(menu != null){

			Iterator iterator = menu.iterator();

			while(iterator.hasNext()){
				node = (MenuNode) iterator.next();
				node.setStato(getStato(node.getCode(), pathCode));
			}
		}
		return menu;
	}

	/**
	 * Metodo che restituisce il menu della applicazioni popolato
	 *
	 * @return
	 */
	public Collection getMenuApplicazioni(){
		return getValidatedMenu(menuTree, path.getApplicationCode());
	}

	/**
	 * metodo che restituisce il menu della aree da visualizzare non popolato
	 *
	 * @return
	 */
	private Collection getMenuAreeNonPop(){
		MenuNode node = null;
		Collection menuAree = null;
		Iterator iterator = getMenuApplicazioni().iterator();

		while(iterator.hasNext()){
			node = (MenuNode) iterator.next();
			if(node.getStato())
				menuAree = node.getChildren();
		}
		return menuAree;
	}

	/**
	 * metodo che restituisce il menu della aree da visualizzare popolato.
	 *
	 * @return
	 */
	public Collection getMenuAree(){
		return getValidatedMenu(this.getMenuAreeNonPop(), path.getAreaCode());
	}

	/**
	 * metodo che restituisce il menu dei servizi e i sottomenu delle funzioni popolati
	 *
	 * @return
	 */
	public Collection getMenuServizi(){
		MenuNode node = null;
		Collection menuServizi = null;
		if (getMenuAree() == null)
			return null;
		Iterator iterator = getMenuAree().iterator();

		while(iterator.hasNext()){
			node = (MenuNode) iterator.next();
			if(node.getStato())
				menuServizi = getValidatedMenu(node.getChildren(), path.getServiceCode());
		}
		if (menuServizi != null){
			iterator = menuServizi.iterator();

			while(iterator.hasNext()){
				node = (MenuNode) iterator.next();
				getValidatedMenu(node.getChildren(), path.getFunctionCode());
			}
		}
		return menuServizi;
	}

	/**
	 * metodo che fornisce una demo di alberatura di navigazione a scopo di test.
	 *
	 * @return
	 */
	private Collection treeTestCreator(){
		Vector hsAppl = new Vector();
		Vector hsArea1 = new Vector();
		Vector hsArea2 = new Vector();
		Vector hsServ1 = new Vector();
		Vector hsServ2 = new Vector();
		Vector hsServ3 = new Vector();
		Vector hsServ4 = new Vector();
		Vector hsFunz1 = new Vector();
		Vector hsFunz2 = new Vector();
		Vector hsFunz3 = new Vector();
		Vector hsFunz4 = new Vector();
		Vector hsFunz5 = new Vector();
		Vector hsFunz6 = new Vector();
		Vector hsFunz7 = new Vector();
		Vector hsFunz8 = new Vector();

		MenuNode menuNode;

		Iterator iterator;

		String[] codici = {"AP_004",
						   "AP_005",
								"AR_025",
								"AR_028",
								"AR_032",
								"AR_020",
									"SE_001",
									"SE_079",
									"SE_096",
									"SE_012",
									"SE_046",
									"SE_014",
									"SE_013",
									"SE_044",
										"FU_001",
										"FU_002",
										"FU_003",
										"FU_004",
										"FU_005",
										"FU_006",
										"FU_007",
										"FU_008",
										"FU_009",
										"FU_010",
										"FU_011",
										"FU_012",
										"FU_013",
										"FU_014",
										"FU_015",
										"FU_016"
								};













		for(int i= 0; i < codici.length ; i++){
			menuNode = new MenuNodeTest();
			menuNode.setCode(codici[i]);
			switch(i){
				case 0: case 1:
					hsAppl.add(menuNode);
					break;
				case 2: case 3:
					hsArea1.add(menuNode);
					((MenuNode)hsAppl.firstElement()).setChildren(hsArea1);
					break;
				case 4: case 5:
					hsArea2.add(menuNode);
					((MenuNode)hsAppl.lastElement()).setChildren(hsArea2);
					break;
				case 6: case 7:
					hsServ1.add(menuNode);
					((MenuNode)hsArea1.firstElement()).setChildren(hsServ1);
					break;
				case 8: case 9:
					hsServ2.add(menuNode);
					((MenuNode)hsArea1.lastElement()).setChildren(hsServ2);
					break;
				case 10: case 11:
					hsServ3.add(menuNode);
					((MenuNode)hsArea2.firstElement()).setChildren(hsServ3);
					break;
				case 12: case 13:
					hsServ4.add(menuNode);
					((MenuNode)hsArea2.lastElement()).setChildren(hsServ4);
					break;
				case 14: case 15:
					hsFunz1.add(menuNode);
					((MenuNode)hsServ1.firstElement()).setChildren(hsFunz1);
					break;
				case 16: case 17:
					hsFunz2.add(menuNode);
					((MenuNode)hsServ1.lastElement()).setChildren(hsFunz2);
					break;
				case 18: case 19:
					hsFunz3.add(menuNode);
					((MenuNode)hsServ2.firstElement()).setChildren(hsFunz3);
					break;
				case 20: case 21:
					hsFunz4.add(menuNode);
					((MenuNode)hsServ2.lastElement()).setChildren(hsFunz4);
					break;
				case 22: case 23:
					hsFunz5.add(menuNode);
					((MenuNode)hsServ3.firstElement()).setChildren(hsFunz5);
					break;
				case 24: case 25:
					hsFunz6.add(menuNode);
					iterator =  hsServ3.iterator();
					iterator.next();
					((MenuNode)hsServ3.lastElement()).setChildren(hsFunz6);
					break;
				case 26: case 27:
					hsFunz7.add(menuNode);
					((MenuNode)hsServ4.firstElement()).setChildren(hsFunz7);
					break;
				case 28: case 29:
					hsFunz8.add(menuNode);
					((MenuNode)hsServ4.lastElement()).setChildren(hsFunz8);
					break;


			}


		}
		return hsAppl;
	}



}
