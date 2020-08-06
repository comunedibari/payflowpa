/*
 * File: DataSorter.java
 * Package: com.etnoteam.service.text
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 26-ott-03 - 1.04.40
 * Created by: finsaccanebbia (Etnoteam)
 */
package it.nch.fwk.fo.base.text;

import it.nch.fwk.fo.dto.FrameworkDTOInterface;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @author finsaccanebbia
 *
 * <br>
 * La classe DataSorter e' una classe astratta che espone il metodo getColumnValue
 * tramite il quale viene effettuato l'ordinamento parametrico.
 *
 *
**/
public abstract class DataSorter implements Serializable, DataSorterInterface {

	/** Logger privato alla classe. */
    private static Logger logger = Logger.getLogger(DataSorter.class);

	/* hashtable contenente i parametri di configurazione */
	private static Map sorterConfiguration = new Hashtable();

	protected String bank;
	protected String channel;
	protected String language;
	private static Map registry=null;

	/* prefisso di ricerca nella struttura hash */
	private String prefix = null;

	/* Locale corrente */
	private Locale currentLocale = null;

	/* costruttore protetto */
	protected DataSorter(String prefix, Locale loc, String bank, String channel, String language) {
		this.prefix = prefix;
		this.currentLocale = loc;

		this.bank=bank;
		this.channel=channel;
		this.language=language;
		this.currentLocale=Locale.getDefault();

		logger.info(this + " - " + this.currentLocale.toString());
	}


	/**
	 * E' un metodo che deve essere implementato dai vari DataSorter che preso un DTO,
	 * una colonna ed un array di specifiche di ordinamento restituisce un valore di ordinamento
	 * corrispondente al valore del DTO.
	 *
	 * L'implementazione di questo metdodo nei DataSorter puo' essere basato su Reflection
	 * come nel DataSorterInweb oppure specifico
	 *
	 * @param dto il dto da cui recuperare il valore "vero"
	 * @param column la colonna da interrogare
	 * @param sortSpecs le stringhe che specificano le logiche di ordinamento
	 * @return Comparable torna un oggetto su cui si puo' effettuare una comparazione. Se c'e' un logica particolare viene restituito un oggetto di ordinamento, altrimento il valore "vero"
	 */
    public abstract Comparable getColumnValue(FrameworkDTOInterface dto,String column, String[] sortSpecs);

	/* recupero valore da hash di configurazione */
	protected static Object getConfValue(String prefix, String sortSpec, String tag, String attribute) {
		StringBuffer key = new StringBuffer();
		key.append(prefix);
        if (sortSpec!=null) {
			key.append(sortSpec);
			key.append(SEP);
        }
		key.append(tag);
		key.append(SEP);
		key.append(attribute);
		return sorterConfiguration.get(key.toString());
	}

	/* recupero valore da hash di configurazione */
	protected static Object getConfValue(String prefix, String[] sortSpecs, String tag, String attribute) {
		Object result=null;
		String sortSpec=null;
		for(int i=0;i<sortSpecs.length;i++) {
			Object tmpResult=null;
			tmpResult=getConfValue(prefix,sortSpecs[i],tag,attribute);
			if (tmpResult!=null) {
				if (result == null) {
					result = tmpResult;
					sortSpec = sortSpecs[i];
				} else {
					// che succede se 2 specifiche di criteri hanno entrambe
					//la stessa colonna?
					// viene tenuta la prima incontrata e dato un warning.
					logger.warn("getConfValue: il campo '"+tag+"' appare nella spec '"+sortSpec+"' e '"+sortSpecs[i]);
				}
			}
		}
		return result;
	}

	/**
	 * Method getSorterPrefix.
	 * @return String
	 */
	protected String getSorterPrefix() {
		return prefix;
	}

	/**
	 * Method getInstance.
	 * @param lang codice ISO locale di riferimento utente
	 * @param ch codice canale
	 * @param bank codice banca
	 * @return DataSorter Istanza dell'oggetto di classe specifica
	 * @throws Exception Sollevata in cui non vi e' una istanza di ordinamento per i parametri specificati
	 */
	public static DataSorter getInstance(
		String bank,
		String ch,
		String lang)
		throws Exception {

        StringBuffer prefix = new StringBuffer();
        prefix.append(bank.toUpperCase());
		prefix.append(SEP);
		prefix.append(ch.toUpperCase());
		prefix.append(SEP);
		prefix.append(lang.toUpperCase());
		prefix.append(SEP);

		Class classFactory = null;
		Constructor constructor;
		Locale loc = null;
		DataSorter ds = null;

        String prfxStr=prefix.toString();

		synchronized (sorterConfiguration) {
			if (sorterConfiguration.isEmpty()) {
				try {
					sorterConfiguration = DataSorterHandler.reload();
				} catch (Exception e) {
					logger.fatal("Parsing error:" + e.getMessage(), e);
					/* in caso di eccezione l'oggetto rimane comunque inizializzato
					sorterConfiguration = null;
					*/
				}
			}
		}

		/* controlla se e' configurata una factory per i parametri specificati */
		if(sorterConfiguration.containsKey(prfxStr)) {

			/* get factory */
			classFactory = (Class)getConfValue(prfxStr,(String)null,TAG_CLASS,ATTRIB_TYPE);
			loc = (Locale) getConfValue(prfxStr,(String)null,TAG_CLASS,KEY_LOCALE);

			/* invocazione metodo di costruzione istanza della classe */

            if (registry == null) {
            	registry=new Hashtable();
            }
            String registerKey=prfxStr;
            ds=(DataSorter)registry.get(registerKey);
            if (ds == null) {
				constructor = classFactory.getDeclaredConstructor(
					new Class[] {String.class, Locale.class, String.class, String.class, String.class });

				ds = (DataSorter)constructor.newInstance(
					new Object[] {prfxStr, loc, bank, ch, lang});

                registry.put(registerKey,ds);
            } else {
            	logger.info("getInstance(): recuperato DataSorter da cache");
            }

		} else {
			/* ECCEZIONE: parametri di Locale/Canale non supportati */
			logger.fatal("Unable to build DataSorter for LANG:"+ lang+ " CHANNEL:"+ ch+ " BANK:"+ bank);
			throw new Exception("Unable to build DataSorter for LANG:"+ lang+ " CHANNEL:"+ ch+ " BANK:"+ bank);
		}

		return ds;
	}



}
