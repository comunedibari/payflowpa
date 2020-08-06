/*******************************************************************************
 * Copyright (c) 2016 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/

package it.tasgroup.idp.billerservices.api.plugin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.LoaderException;


/**
 * Questo componente e' responsabile dell'acquisione dei plugin gestiti da Paytas.
 * I plugin acquisiti sono definiti nel file indirizzato dalla costante CONFIGURATION_FILE
 * ed implementano l'interfaccia ILoaderPlugin.
 * 
 * Qui i plugin sono salvati in una cache locale, in modo che siano rapidamente disponibili
 * quando invocati. 
 * 
 * Riguardo a questa cache, per compatibilita' con il codice esistente, si e' fatto la scelta
 * di non salvare l'istanza della classe, ma la classe vera e propria. Cio' comporta a run-time
 * l'aggravio di dover istanziare una classe via reflection. Viceversa, questo penalty non
 * si sarebbe pagato, a scapito pero' di istanziare una sola classe per plugin e JVM. 
 * 
 * @author tasgroup
 *
 */

public class GestorePlugin {
	private static final String CONFIGURATION_FILE = "plugins.properties";
	private static final Log logger = LogFactory.getLog(GestorePlugin.class);
	private static Map<String, Class<? extends ILoaderPlugin>> pluginMap;
	
	static {
		// Caricamento file di properties
		Properties availablePlugins;
		try {
			availablePlugins = new Properties();
			availablePlugins.load(GestorePlugin.class.getResourceAsStream(CONFIGURATION_FILE));
		} catch (Exception e) {
			logger.error("Errore in caricamento file di configurazione " + CONFIGURATION_FILE, e);
			if (e instanceof RuntimeException) throw (RuntimeException)e;
			throw new RuntimeException(e);
		}
		// Early binding sulle classi di plugin
		// Viene fatta la cache della sola classe del plugin, non della sua istanza, per mantenere una compatibilita' con il codice gia' scritto
		// TODO fare cache dell'istanza cosi' da essere piu' efficienti  
		String className = null;
		pluginMap = new HashMap<String, Class<? extends ILoaderPlugin>>();
		try {
			for (Object propertyNameAsObject : availablePlugins.keySet()) {
				className = availablePlugins.getProperty((String) propertyNameAsObject);
				Class<?> pluginClass = Class.forName(className);
				if (!ILoaderPlugin.class.isAssignableFrom(pluginClass)) throw new RuntimeException("La classe " + className + " non implementa l'interfaccia " + ILoaderPlugin.class.getSimpleName());
				pluginMap.put((String) propertyNameAsObject, (Class<? extends ILoaderPlugin>) pluginClass);
			}
		} catch (Exception e) {
			logger.error("Errore in istanziazione classe " + className + " definita nel file di configurazione " + CONFIGURATION_FILE, e);
			if (e instanceof RuntimeException) throw (RuntimeException)e;
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Dato un tipo di file e un InputStream, viene restituita una classe che può gestire quel tipo file e input stream
	 * 
	 * @param input InputStream sul quale lavorare. Il plugin non e' dipendente dall'InputStream ma dal tipo file. 
	 *        Questo parametro e' stato tenuto per compatibilita' con il passato 
	 * @param tipoFile tipo file tra quelli riconosciuti (riportati nel file indirizzato da CONFIGURATION_FILE
	 * @return plugin che puo' trattare i file del tipo specificato
	 * @throws LoaderException in caso di errore (plugin sconosciuto, errore in costruzione plugin, ecc.)
	 */
	public static ILoaderPlugin creaPlugin(InputStream input, String tipoFile) throws LoaderException {
		// Tenuta questa firma per compiatibilita' con quanto sviluppato in precedenza
		// TODO Il plugin deve essere in funzione solo del tipo file, non del tipo file e InputStream !
		if (!pluginMap.containsKey(tipoFile)) throw new LoaderException(EnumReturnValues.PLUGIN_SCONOSCIUTO, "Plugin sconosciuto: " + tipoFile); 
		Class<? extends ILoaderPlugin> pluginClass = pluginMap.get(tipoFile);
		try {
			// Istanziazione via reflection
			// TODO fare cache dell'istanza in modo da essere piu' efficienti
			return pluginClass.getConstructor(InputStream.class).newInstance(input);
		} catch (Exception e) {
			if (e instanceof LoaderException) throw (LoaderException) e;
			logger.error(("Errore in costruzione plugin definito per il tipo file " + tipoFile + " classe " + pluginMap.get(tipoFile).getName()));
			throw new LoaderException(EnumReturnValues.ERRORE_IN_COSTRUZIONE_PLUGIN, "Tipo file: " + tipoFile + " classe " + pluginMap.get(tipoFile).getName());
		}
	}

	private GestorePlugin() {}
}
