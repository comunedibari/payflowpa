package it.nch.is.fo.web.menu;

import it.tasgroup.iris.dto.menu.ApplicazioniMenu;
import it.tasgroup.iris.dto.menu.AreeMenu;
import it.tasgroup.iris.dto.menu.FunzioniMenu;
import it.tasgroup.iris.dto.menu.RootMenu;
import it.tasgroup.iris.dto.menu.ServiziMenu;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.profilo.menu.MenuNode;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.profilo.FunzionioperatoriFormImpl;
import it.nch.is.fo.web.cache.IrisCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Classe di utilità per il filtraggio del Menu ad albero ottenuto raccogliendo su DB tutte le funzioni
 * associate in servizi, a loro volta raggruppati in aree, che si associano nell'unica applicazione legata univocamente alla
 * categoria dell'intestatario.
 * Il filtraggio di questo albero avviene in base alle abilitazioni presenti sul file webapp.properties e in
 * base alle abilitazioni della coppia intestatario/operatore.
 *
 * @author
 *
 */
public class MenuUtil {

	public static final String APPL_CODE_CITTADINO = "APL_0001";
	public static final String APPL_CODE_BACKOFFICE = "APL_0002";
	public static final String APPL_CODE_ENTE="APL_0003";
	public static final String APPL_CODE_AZIENDA="APL_0004";

	/**
	 * Prende in ingresso un oggetto Operatori e un oggetto ApplicazioniMenu e in base all'applicazione
	 * e al fatto che l'operatore sia abilitato o meno a fare l'amministratore costruisce una chiave
	 * con cui ricerca una entry nel file webapp.properties. Questa entry contiene tutte le aree a cui l'operatore
	 * o amministratore è abilitato per quella applicazione. Restituisce in un set di stringhe tutte le aree
	 * trovate su questa entry del file webapp.properties.
	 *
	 * @param operatorType stringa indicante il tipo dell'operatore.
	 * @param appl oggetto ApplicazioniMenu
	 * @return un Set di stringhe di stringhe indicanti tutte le aree trovate sulla entry del file webapp.properties
	 * corrispondente all'applicazione e al FlagOperatorType dell'operatore che si è autenticato.
	 */
	private Set<String> getAreaListForOperatorTypeAndAppl(String operatorType, ApplicazioniMenu appl) {
		Set<String> s = new LinkedHashSet<String>();
		try {
			String applCode = appl.getCode();
			String keyValueName = "";

			if ((applCode != null) && operatorType != null)
						keyValueName = "area."+applCode.trim()+"."+operatorType;

			ResourceBundle bundle = ResourceBundle.getBundle("webapp");
			String valueList = bundle.getString(keyValueName);
			if (valueList != null) {
				String[] valueArray = valueList.split(",");
				for (int i = 0; i < valueArray.length; i++) {
					s.add(valueArray[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * Indica se l'area del menu in cache viene trovata tra quelle indicate nel file webapp.properties
	 * per l'operatore che si logga e la sua applicazione.
	 *
	 * @param areeSet Set dei codici delle AreeMenu trovate nel file webapp.properties per l'operatore che si logga e
	 * la sua applicazione
	 * @param area oggetto AreeMenu trovato nel Menu in cache di cui voglio sapere se va conservato o meno
	 * @return true se l'area del menu in cache viene trovata tra quelle indicate nel file webapp.properties
	 * per l'operatore che si logga e la sua applicazione, false altrimenti.
	 */
	private boolean isValidArea(Set<String> areeSet, AreeMenu area) {
		boolean found = false;

		try {
			if (areeSet != null && area != null && area.getCode() != null) {
				String areaCode = area.getCode();
				for (String areaCodeFromSet : areeSet){
					if (areaCodeFromSet != null && areaCodeFromSet.trim().equals(areaCode.trim())) {
						found = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			found = false;
		}
		return found;
	}

	/**
	 * Estrae dalla IrisCache un menu popolato con tutte le Aree, Servizi e Menu previsti per l'applicazione.
	 * Estrae da webapp.properties un set con tutte le AreeMenu per l'operatore che si logga e
	 * la sua applicazione.
	 * Restituisce un clone del menu estratto dalla cache filtrato in base alle sole aree
	 * registrate su webapp.properties per l'operatore che si è loggato e la sua applicazione
	 * e in base alle sole funzioni a cui è abilitata la coppia intestatario/operatore.
	 *
	 * @param fec il FrontEndContext
	 * @param funOper Collection delle funzioni a cui è abilitata la coppia intestatario/operatore
	 * @param applCode Stringa indicante il codice dell'applicazione
	 * @return il Menu filtrato in base alle sole aree registrate su webapp.properties per l'operatore
	 * che si è loggato e la sua applicazione.
	 * @throws Exception
	 */
	public MenuNode filterMenu(FrontEndContext fec, Collection<String> funOper, String applCode) throws Exception {
		if (Tracer.isInfoEnabled(getClass().getName()))
			Tracer.info(getClass().getName(), "filterMenu", "inizio", null);

		IrisCache bCache = new IrisCache();
		ApplicazioniMenu apl = bCache.getMenu(fec, applCode);

		Collection<ApplicazioniMenu> applicazioniResult = new ArrayList<ApplicazioniMenu>();

		Collection<AreeMenu> listaAree = new ArrayList<AreeMenu>();
		MenuNode menu = null;

		if (apl != null && apl.getChildren() != null) {

			// estraggo dal file webapp.properties l'elenco delle aree visibili per il tipo di operatore e l'applicazione
			String opType = fec.getTipoIntestatarioperatore();
			Set<String> areaSet = getAreaListForOperatorTypeAndAppl(opType, apl);

			// ciclo sulle AreeMenu trovate nel menu in cache e le confronto con quelle trovate
			// nel file webapp.properties per l'operatore che si logga e la sua applicazione
			Collection<AreeMenu> areeList = apl.getChildren();
			Collection<ServiziMenu> listaServizi = null;
			boolean appendArea = false;
			for (AreeMenu area : areeList) {
				appendArea = isValidArea(areaSet, area);
				// se l'area del menu in cache è accettabile per quanto indicato sul file webapp.properties
				// la seleziono coi suoi discendenti per metterla nel clone del Menu estratto dalla cache
				if (appendArea) {
					listaServizi = new ArrayList<ServiziMenu>();
					Collection<ServiziMenu> servList = area.getChildren();
					Collection<FunzioniMenu> listaFunzioni = null;
					for (ServiziMenu serv: servList) {
						listaFunzioni = new ArrayList<FunzioniMenu>();
						Collection<FunzioniMenu> funzList = serv.getChildren();
						boolean abilitata;
						for (FunzioniMenu funzione : funzList) {
							funzione = (FunzioniMenu) funzione.clone();
							if (funzione.getTipoOper() != null && funzione.getTipoOper().contains(opType)) {
								abilitata = verificaAbilitazione(funzione, funOper);
								if (abilitata) {
									funzione.setAccessed("1");
									if (Tracer.isDebugEnabled(getClass().getName()))
										Tracer.debug(getClass().getName(), "filterMenu", "Funzione con codice: " + funzione.getCode() + " abilitata");
								} else
									funzione.setAccessed(null);
								listaFunzioni.add(funzione);
							}
						}
						serv = (ServiziMenu) serv.clone();
						serv.setChildren(listaFunzioni);
						listaServizi.add(serv);
					}
					area = (AreeMenu) area.clone();
					area.setChildren(listaServizi);
					listaAree.add(area);
				}
			}
			ApplicazioniMenu newAppl = (ApplicazioniMenu) apl.clone();
			newAppl.setChildren(listaAree);
			applicazioniResult.add(newAppl);
			menu = new RootMenu();
			menu.setChildren(applicazioniResult);
			if (Tracer.isInfoEnabled(getClass().getName()))
				Tracer.info(getClass().getName(), "filterMenu", "fine", null);
		}
		return menu;
	}

	/**
	 * Verifica se la funzioneCorrente in ingresso appartiene all'elenco delle funzioni funOper
	 * in ingresso.
	 *
	 * @param funzioneCorrente FunzioniMenu di cui si vuole verificare la presenza nella lista.
	 * @param funOper lista in cui si vuole cercare la funzioneCorrente in ingresso
	 * @return true se la funzioneCorrente ha lo stesso codice di una della lista, false altrimenti.
	 */
	private boolean verificaAbilitazione(FunzioniMenu funzioneCorrente, Collection<String> funOper) {
		
		if (funOper != null) {
			for(String funOp : funOper){
				if (funOp != null &&  funOp.trim().equals(funzioneCorrente.getCode().trim()))
					return true;
			}
		}
		return false;
	}
}
