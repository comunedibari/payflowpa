/**
 * Created on 16/ott/08
 */
package it.nch.streaming.services.impl;

import it.nch.eb.common.utils.loaders.ReaderFactory;
import it.nch.eb.flatx.flatmodel.ConversionService;
import it.nch.eb.flatx.flatmodel.SerializableMultiPassConversionService;
import it.nch.eb.flatx.flatmodel.conversioninfo.BindingManagerEffect;
import it.nch.eb.flatx.flatmodel.sax.ActivableLeafElementHandler;
import it.nch.eb.flatx.flatmodel.sax.SaxElementHandler;
import it.nch.fwk.core.NamespacesInfos;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author gdefacci
 */
public abstract class PrevisitToFlatConversion implements SerializableMultiPassConversionService {

	private static final long	serialVersionUID	= -8117248912242012060L;
	
	private final BaseToFlatConversionService	conversionService;
	private final NamespacesInfos	namespaces;
	private final BindingManagerEffect[] bmEffects;
	
	public PrevisitToFlatConversion(BaseToFlatConversionService conversionService, 
			NamespacesInfos nss, BindingManagerEffect[] bmEffects) {
		this.conversionService = conversionService;
		this.namespaces = nss;
		this.bmEffects = bmEffects;
	}
	public PrevisitToFlatConversion(BaseToFlatConversionService conversionService, NamespacesInfos nss) {
		this(conversionService, nss, new BindingManagerEffect[] {});
	}
	
	protected BindingManagerEffect[] mergedEffects(BindingManagerEffect[] bms)  {
		BindingManagerEffect[] res = new BindingManagerEffect[bms.length + bmEffects.length];
		System.arraycopy(this.bmEffects, 0, res, 0, this.bmEffects.length);
		System.arraycopy(bms, 0, res, this.bmEffects.length, bms.length );
		return res;
	}
	
	public NamespacesInfos getNamespaces() {
		return namespaces;
	}
	public final PrevisitToFlatConversion create(Map map) {
		return create(BaseToFlatConversionService.createEntriesInBindingManager(map));
	}
	
	public final PrevisitToFlatConversion create(BindingManagerEffect bmEffect) {
		return create(new BindingManagerEffect[] { bmEffect });	
	}
	
	public final PrevisitToFlatConversion create(List/*BindingManagerEffect*/ bmEffect) {
		return create((BindingManagerEffect[]) bmEffect.toArray(new BindingManagerEffect[0]));	
	}
	
	public abstract PrevisitToFlatConversion create(BindingManagerEffect[] bmEffect);

	public void convert(ReaderFactory readerFactory, Writer wr) {
		MapCollectorActivableLeafElementHandler elementHandler = createPrevisitElementHandler();
		
		Reader reader = readerFactory.createReader();
		
		Map previsitState = saxParse(reader, elementHandler);
		
		BaseToFlatConversionService cs = (bmEffects==null || bmEffects.length == 0) ?
				conversionService : conversionService.create(this.bmEffects);
		if (!previsitState.isEmpty()) { 
			cs = cs.create(previsitState); 
		} 

		reader = readerFactory.createReader();
		cs.convert(reader, wr);
		
	}
	
	protected Map saxParse(Reader reader, MapCollectorActivableLeafElementHandler elementHandler) {
		SaxElementHandler saxHandler = new SaxElementHandler(elementHandler, namespaces);
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(saxHandler);
			xmlReader.setErrorHandler(saxHandler);
			
			xmlReader.parse(new InputSource(reader));
			return elementHandler.getState();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected SaxElementHandler createSaxHandler(ActivableLeafElementHandler elementHandler, 
			NamespacesInfos nss2) {
		return new SaxElementHandler(elementHandler, nss2);
	}
	
	public ConversionService getConversionService() {
		return conversionService;
	}

	protected abstract MapCollectorActivableLeafElementHandler createPrevisitElementHandler();

}
