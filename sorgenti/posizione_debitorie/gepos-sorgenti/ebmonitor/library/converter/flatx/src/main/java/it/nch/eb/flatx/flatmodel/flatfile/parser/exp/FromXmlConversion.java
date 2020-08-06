/**
 * Created on 29/set/2008
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.BindingManagerEffect;
import it.nch.fwk.core.NamespacesInfos;

import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author gdefacci
 */
public class FromXmlConversion {
	
	private static final long	serialVersionUID	= -2556483728768768141L;

	private final BindingManagerEffect[] bindingManagerEffects;
	protected final NamespacesInfos	queryNss;
	
	protected FromXmlConversion(NamespacesInfos qnss, 
			BindingManagerEffect[] bmEffects) {
		super();
		this.queryNss = qnss;
		if (bmEffects!=null) this.bindingManagerEffects = bmEffects;
		else this.bindingManagerEffects = new BindingManagerEffect[0];
	}
	
	protected BindingManagerEffect[] mergedEffects(BindingManagerEffect[] bms)  {
		BindingManagerEffect[] res = new BindingManagerEffect[bms.length + bindingManagerEffects.length];
		System.arraycopy(this.bindingManagerEffects, 0, res, 0, this.bindingManagerEffects.length);
		System.arraycopy(bms, 0, res, this.bindingManagerEffects.length, bms.length );
		return res;
	}

//	public final FromXmlConversion createNew(Map map) {
//		return createNew(createEntriesInBindingManager(map));
//	}
//	
//	public final FromXmlConversion createNew(BindingManagerEffect bmEffect) {
//		return createNew(new BindingManagerEffect[] { bmEffect });	
//	}
//	
//	public final FromXmlConversion createNew(List/*BindingManagerEffect*/ bmEffect) {
//		return createNew((BindingManagerEffect[]) bmEffect.toArray(new BindingManagerEffect[0]));	
//	}
//	
//	public abstract FromXmlConversion createNew(BindingManagerEffect[] bmEffect);
	
	public NamespacesInfos getQueryNamespaces() {
		return queryNss; 
	}
	
	public static BindingManagerEffect createEntriesInBindingManager(final Map map) {
		return new BindingManagerEffect()  {
			
			public void apply(IBindingManager bm) {
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					Entry entry = (Entry) it.next();
					Object key = entry.getKey();
					if (!(key instanceof String)) 
						throw new IllegalStateException("only string keys are allowed in this map");
					bm.setOrDefine((String) key, entry.getValue());
				}
			}
			
		};
	}

	protected void saxParse(Reader reader, DefaultHandler dsh) {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(dsh);
			xmlReader.setErrorHandler(dsh);
			
			xmlReader.parse(new InputSource(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	

	protected void applyBindingManagerEffects(IBindingManager bindings) {
		for (int i = 0; i < this.bindingManagerEffects.length; i++) {
			BindingManagerEffect bmEffect = this.bindingManagerEffects[i];
			bmEffect.apply(bindings);
		}
	}

}