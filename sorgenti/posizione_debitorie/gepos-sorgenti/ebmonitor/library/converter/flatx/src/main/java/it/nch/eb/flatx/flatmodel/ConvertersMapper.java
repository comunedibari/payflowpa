/**
 * Created on 28/apr/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.flatx.bean.types.Converter;

import java.io.Serializable;
import java.util.HashMap;


/**
 * @author gdefacci
 */
public class ConvertersMapper implements Serializable {
	
	private static final long	serialVersionUID	= -479543275639339393L;

	private static final class SingletonConverterFactory implements ConverterFactory {

		private static final long	serialVersionUID	= 1L;
		private final Converter	mapped;

		private SingletonConverterFactory(Converter mapped) {
			this.mapped = mapped;
		}

		public Converter createConverter(Converter converter) {
			return mapped;
		}
	}

	private java.util.Map/*<Converter,ConverterFactory>*/ convertersFactoryMap = new HashMap();
	private java.util.Map/*<String,ConverterFactory>*/ convertersClassFactoryMap = new HashMap();
	
	public void put(Converter converterIdx, ConverterFactory converterFactory) {
		convertersFactoryMap.put(converterIdx, converterFactory);
	}
	
	public void put(Class klass, ConverterFactory converterFactory) {
		convertersClassFactoryMap.put(klass.getName(), converterFactory);
	}
	
	/**
	 * share the same converter (used as a singleton ConvertersFactory.createConverter return always the same instance)
	 */
	public void put(Converter converterKey, final Converter mapped) {
		convertersFactoryMap.put(converterKey, new SingletonConverterFactory(mapped));
	}
	
	public Converter getMappedConverter(it.nch.eb.flatx.bean.types.Converter converter) {
		ConverterFactory cnvFactory = (ConverterFactory) convertersFactoryMap.get(converter);
		Converter mapped = null;
		if (cnvFactory!=null) {
			mapped = cnvFactory.createConverter(converter);
		} else {
			cnvFactory = (ConverterFactory) convertersClassFactoryMap.get(converter.getClass().getName());
			if (cnvFactory!=null) mapped = cnvFactory.createConverter(converter);
		}
		return mapped;
	}
	
	public boolean isEmpty() {
		return this.convertersClassFactoryMap.isEmpty() && this.convertersFactoryMap.isEmpty();
	}

}
