/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.ReflectionUtils.Setter;
import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.StableXPathMapFunction;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.objectconverters.XPathToObjectConvertBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.io.Serializable;


/**
 * @author gdefacci
 */
public class XmlRecord extends AbstractRecord implements IXmlRecord, Serializable{
	
	private static final long	serialVersionUID	= 7335865350014071637L;
	
	protected final XPathToObjectConvertBuilder	builder	= new XPathToObjectConvertBuilder();
	private final String basePath;
	
	private IBindingManager bindingManager = BindingManagerFactory.instance.createBindingManager();
	
	private IXPathToObjectConversionInfo[]	lazyXPathConversionInfos = null;

	private XPathPosition baseXPathPosition;

	public XmlRecord(String newPaths) {
		super(IXPathToObjectConversionInfo.class);
		this.basePath = newPaths;
	}

	public String getBaseXPath() {
		return basePath;
	}
	
	public synchronized XPathPosition getBaseXPathPosition() {
		if (baseXPathPosition == null) {
			this.baseXPathPosition = XPathsParser.instance.parseXPathPosition(getBaseXPath());
		}
		return baseXPathPosition;
	}

	public synchronized IXPathToObjectConversionInfo[] getConversionInfos() {
		if (lazyXPathConversionInfos==null) {
			IConversionInfo[] cis = getConversionInfos(this);
			lazyXPathConversionInfos = new IXPathToObjectConversionInfo[cis.length];
			for (int i = 0; i < cis.length; i++) {
				lazyXPathConversionInfos[i] = ((IXPathToObjectConversionInfo) cis[i]);
			}
		}
		return lazyXPathConversionInfos ;
	}

	public String getName() {
		return StringUtils.getSimpleName(getClass());
	}

	public IBindingManager getRecordBindings() {
		return bindingManager;
	}
	
	public Object get(ObjectBuilder objBldr, XPathPosition pos, IXPathMapScope elem) {
//		System.out.println("ELEM NAME = " + elem.toString());
//		if (this.getName().equals("PendenzeRecord")) {
//			System.out.println(elem);
//		}
		
		IXPathToObjectConversionInfo[] xcis = getConversionInfos();
		Object res = objBldr.create();
		boolean almostOne = false;
		for (int i = 0; i < xcis.length; i++) {
			IXPathToObjectConversionInfo xci = xcis[i];
			try {
				Object pv = xci.getValue(pos, elem);
				if (pv!=null) {
					if (!almostOne && !(xci instanceof StableXPathMapFunction)) {
						almostOne = true;
					}
					set(res, xci, pv);
				}
			} catch (Exception e) {
				throw new RuntimeException("error setting IXPathToObjectConversionInfo: " + xci + "\nof record " + this, e);
			}
		}
		if (almostOne) return res;
		else return null;
	}
	
	private void set(Object obj, IXPathToObjectConversionInfo xci, Object value) {
		Setter setter = ReflectionUtils.getSetterForProperty(obj, xci.getName(), xci.getTargetClass());
		setter.setValue(value);
	}
	
}
