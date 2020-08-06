/*
 * Created on Jul 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.profile;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

/**
 * @author MaranesiL
 * 
 *         Bean contenente le abilitazioni per le operazioni elementari di un
 *         servizio.
 * 
 */
public class FlagsAbilitazioniBean implements Serializable {

	private String funzione = null;
	// attributo "abilitazioni" privato per non far vedere l'integer
	private Integer abilitazioni = null;

	// Questa costante devinisce il valore da attribuire
	// ad un campo "abilitazione" sul DB che ritorna un null
	public static final boolean FIELD_NULL = false;
	// altri attributi privati per non mostrare che si usa una bitmask
	// per la conversione abilitazioni/Integer
	private static final HashMap maskMap = new HashMap();

	private static final int ALL_FLAGS_DISABLED = 0;
	private static final int MASK_FLAG_INSERT = 1;
	private static final int MASK_FLAG_DELETE = 2;
	private static final int MASK_FLAG_EDIT = 4;
	private static final int MASK_FLAG_MASSIVEEDIT = 8;
	private static final int MASK_FLAG_DUPLICATE = 16;
	private static final int MASK_FLAG_VIEWFLUX = 32;
	private static final int MASK_FLAG_CREATEFLUX = 64;
	private static final int MASK_FLAG_DELETEFLUX = 128;
	private static final int MASK_FLAG_DUPLICATEFLUX = 256;
	private static final int MASK_FLAG_SIGNFLUX = 512;
	private static final int MASK_FLAG_SENDFLUX = 1024;
	private static final int MASK_FLAG_VIEWDISPO = 2048;

	private static String[] ABILITAZIONI = new String[] { ProfileConstants.FLAG_INSERT, ProfileConstants.FLAG_DELETE,
			ProfileConstants.FLAG_EDIT, ProfileConstants.FLAG_MASSIVEEDIT, ProfileConstants.FLAG_DUPLICATE,
			ProfileConstants.FLAG_VIEWFLUX, ProfileConstants.FLAG_CREATEFLUX, ProfileConstants.FLAG_DELETEFLUX,
			ProfileConstants.FLAG_DUPLICATEFLUX, ProfileConstants.FLAG_SIGNFLUX, ProfileConstants.FLAG_SENDFLUX,
			ProfileConstants.FLAG_VIEWDISPO };

	static {
		maskMap.put(ProfileConstants.FLAG_INSERT, new Integer(MASK_FLAG_INSERT));
		maskMap.put(ProfileConstants.FLAG_DELETE, new Integer(MASK_FLAG_DELETE));
		maskMap.put(ProfileConstants.FLAG_EDIT, new Integer(MASK_FLAG_EDIT));
		maskMap.put(ProfileConstants.FLAG_MASSIVEEDIT, new Integer(MASK_FLAG_MASSIVEEDIT));
		maskMap.put(ProfileConstants.FLAG_DUPLICATE, new Integer(MASK_FLAG_DUPLICATE));
		maskMap.put(ProfileConstants.FLAG_VIEWFLUX, new Integer(MASK_FLAG_VIEWFLUX));
		maskMap.put(ProfileConstants.FLAG_CREATEFLUX, new Integer(MASK_FLAG_CREATEFLUX));
		maskMap.put(ProfileConstants.FLAG_DELETEFLUX, new Integer(MASK_FLAG_DELETEFLUX));
		maskMap.put(ProfileConstants.FLAG_DUPLICATEFLUX, new Integer(MASK_FLAG_DUPLICATEFLUX));
		maskMap.put(ProfileConstants.FLAG_SIGNFLUX, new Integer(MASK_FLAG_SIGNFLUX));
		maskMap.put(ProfileConstants.FLAG_SENDFLUX, new Integer(MASK_FLAG_SENDFLUX));
		maskMap.put(ProfileConstants.FLAG_VIEWDISPO, new Integer(MASK_FLAG_VIEWDISPO));
	}

	// --- COSTRUTTORI ---

	/**
	 * Costruttore privato. Dipende dalla codifica interna.
	 */
	private FlagsAbilitazioniBean(String function, Integer abilitations) {
		funzione = function;
		abilitazioni = abilitations;
	}

	/**
	 * Costruisce un oggetto FlagsAbilitazioniBean sulla base di una sequenza
	 * esplicita di ablitazoni.
	 */
	public FlagsAbilitazioniBean(String function, boolean flagINSERT, boolean flagDELETE, boolean flagEDIT,
			boolean flagMASSIVEEDIT, boolean flagDUPLICATE, boolean flagVIEWFLUX, boolean flagCREATEFLUX,
			boolean flagDELETEFLUX, boolean flagDUPLICATEFLUX, boolean flagSIGNFLUX, boolean flagSENDFLUX,
			boolean flagVIEWDISPO) {

		int i = 0;

		if (flagINSERT)
			i |= MASK_FLAG_INSERT;
		if (flagDELETE)
			i |= MASK_FLAG_DELETE;
		if (flagEDIT)
			i |= MASK_FLAG_EDIT;
		if (flagMASSIVEEDIT)
			i |= MASK_FLAG_MASSIVEEDIT;
		if (flagDUPLICATE)
			i |= MASK_FLAG_DUPLICATE;
		if (flagVIEWFLUX)
			i |= MASK_FLAG_VIEWFLUX;
		if (flagCREATEFLUX)
			i |= MASK_FLAG_CREATEFLUX;
		if (flagDELETEFLUX)
			i |= MASK_FLAG_DELETEFLUX;
		if (flagDUPLICATEFLUX)
			i |= MASK_FLAG_DUPLICATEFLUX;
		if (flagSIGNFLUX)
			i |= MASK_FLAG_SIGNFLUX;
		if (flagSENDFLUX)
			i |= MASK_FLAG_SENDFLUX;
		if (flagVIEWDISPO)
			i |= MASK_FLAG_VIEWDISPO;

		funzione = function;
		abilitazioni = new Integer(i);
	}

	/**
	 * Crea un Bean di Flags abilitazione servizio CON TUTTE LE OPERAZIONI
	 * ABILITATE.
	 * 
	 * @param funzione
	 * @return
	 */
	public FlagsAbilitazioniBean(String function) {
		funzione = function;
		abilitazioni = null;
	}

	/*
	 * public FlagsAbilitazioniBean(String function, boolean[] abilitations){
	 * 
	 * this(function, abilitations[0], abilitations[1], abilitations[2],
	 * abilitations[3], abilitations[4], abilitations[5], abilitations[6],
	 * abilitations[7], abilitations[8], abilitations[9], abilitations[10]); }
	 */

	// --- GETTER ---
	private boolean getAbilitazione(int abilitazioneMask) {

		boolean flag = true;

		if (abilitazioni != null) {

			flag = (abilitazioni.intValue() & abilitazioneMask) == abilitazioneMask;
		}

		return flag;
	}

	/**
	 * Metodo che ritorna un boolean che rappresenta lo stato (true o false)
	 * dell'abilitazione richiesta (quella passata come parametro).
	 * 
	 * @param abilitazioneCode
	 *            : Il codice che rappresenta l'abilitazione richiesta.
	 * @return Un boolean che indica se l'abilitazione è attiva o meno.
	 */
	public boolean getAbilitazione(String abilitazioneCode) {

		Integer mask = (Integer) maskMap.get(abilitazioneCode);
		return getAbilitazione(mask.intValue());
	}

	/**
	 * Metodo che ritorna un nodo "Element" con le abilitazioni definite.
	 * 
	 * @return
	 */
	// Modello XML
	// <title>
	// <abilitazioni>
	// ---------------------------------------
	// <funzione servizio="...">
	// <viewflux abilitato="..."/>
	// <createflux abilitato="..."/>
	// <delete abilitato="..."/>
	// <deleteflux abilitato="..."/>
	// <duplicate abilitato="..."/>
	// <duplicateflux abilitato="..."/>
	// <edit abilitato="..."/>
	// <insert abilitato="..."/>
	// <massiveedit abilitato="..."/>
	// <sendflux abilitato="..."/>
	// <signflux abilitato="..."/>
	// </Funzione>
	// ---------------------------------------
	// </abilitazioni>
	// </title>
	public Element getDOMElement() {

		Document doc = new DocumentImpl();

		Element nFunzione = doc.createElement(ProfileConstants.XML_NODE_FUNZIONE);
		nFunzione.setAttribute(ProfileConstants.XML_ATTR_SERVIZIO, funzione);

		if (abilitazioni != null) {

			for (int i = 0; i < ABILITAZIONI.length; i++) {

				String key = ABILITAZIONI[i];
				String val = "";

				if (getAbilitazione(key)) {

					val = "true";
				} else {

					val = "false";
				}

				Element child = doc.createElement(key);
				child.setAttribute(ProfileConstants.XML_ATTR_ABILITATO, val);
				nFunzione.appendChild(child);
			}
		} else {

			String val = "true";

			for (int i = 0; i < ABILITAZIONI.length; i++) {

				Element child = doc.createElement(ABILITAZIONI[i]);
				child.setAttribute(ProfileConstants.XML_ATTR_ABILITATO, val);
				nFunzione.appendChild(child);
			}
		}

		return nFunzione;
	}

	/**
	 * Metodo che ritorna una String dal valore "DISABLED" quando non si e
	 * abilitati e dal valore "" quando c'è l'abilitazione. Il metodo non fa
	 * altro che chiamare getAbilitazione ed in base al boolean ritornato
	 * propone una stringa. La "necessita" di questo medoto e dovuta al fatto
	 * che nei tag JSP che definiscono un bottone i modi per disabilitare il
	 * bottone sono: - aggiungere la proprieta "disabled" - oppure la proprieta
	 * "disabled='true'" "disabled='false' ha come effetto un bottone comunque
	 * disabilitato
	 * 
	 * @param flag
	 * @return
	 */
	// Ho preferito scrivere un metodo qui piuttosto che inserire del codice
	// nella JSP,
	// ma qualsia altra soluzione va bene.
	public String getDisableAttribute(String flag) {

		String attr = "";

		boolean enabled = getAbilitazione(flag);

		if (!enabled) {

			attr = " DISABLED ";
		}

		return attr;
	}

	public boolean getDisableAttributeAsBoolean(String flag) {

		boolean attr = false;

		boolean enabled = getAbilitazione(flag);

		if (!enabled) {

			attr = true;
		}

		return attr;
	}

	// --- METODI OBJ to SERIALIZABLE e SERIALIZABLE to OBJ ---

	/**
	 * Metodo utilizzato dall'oggetto Sessione per deserializzare dalla Sessione
	 * Oracle i valori che codificano i flag e creare un oggetto
	 * FlagsAbilitazioniBean.
	 */
	public static FlagsAbilitazioniBean createFromSerializable(String function, Serializable s) {

		FlagsAbilitazioniBean fab = null;

		if (s != null) {
			if (s instanceof Integer) {

				fab = new FlagsAbilitazioniBean(function, (Integer) s);
			}
		} else {
			fab = new FlagsAbilitazioniBean(function);
		}

		return fab;
	}

	/**
	 * Metodo utilizzato dall'oggetto Sessione per serializzare nella Sessione
	 * Oracle i valori che codificano i flag di un oggetto
	 * FlagsAbilitazioniBean.
	 */
	public Serializable serialize() {

		return abilitazioni;
	}

	/**
	 * Crea un Bean di Flags abilitazione servizio tutto disabilitato.
	 * 
	 * @param funzione
	 * @return
	 */
	public static FlagsAbilitazioniBean createAllDisabled(String funzione) {
		return new FlagsAbilitazioniBean(funzione, new Integer(ALL_FLAGS_DISABLED));
	}
}
