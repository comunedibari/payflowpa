/**
 * Created on 11/gen/08
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.SizedConverter;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.TokenizedLine;
import it.nch.eb.flatx.flatmodel.conversioninfo.ChoiceElementToStringFunction;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.FunctionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.VoidExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.io.Serializable;


/**
 * @author gdefacci
 */
public class RecordImpl extends AbstractRecord implements IRecord, Serializable  {
	
	private static final long	serialVersionUID	= 5061276143597874896L;

	private final String basePath;
	
	private IBindingManager bindingManager = BindingManagerFactory.instance.createBindingManager();
	
	private final CounterProvider counter;
	
	private final ExpressionConversionInfoFactory expressionConversionInfoFactory = 
		ExpressionConversionInfoFactory.instance; 

	public RecordImpl(String xpath) {
		this(xpath , new CounterProvider());
	}

	private RecordImpl(String newPaths, CounterProvider conterProvider) {
		super(ExpressionConversionInfo.class);
		this.basePath = newPaths;
		this.counter = conterProvider;
	}
	
	public String getBaseXPath() {
		return basePath;
	}

	protected int getPos()  {
		return counter.getPos();
	}
	
//	public TokenizedLine tokenizeLine(String str, LinesFactoryBuilder lfBuilder) {
//		return getLinesFactory(getConversionInfos(), lfBuilder).create(str);
//	}

	public XPathConversionInfo createXPath(SizedConverter delegate, String xpath, boolean optional) {
		return createXPath(getPos(), delegate, xpath, optional);
	}

	protected XPathConversionInfo createXPath(int pos, SizedConverter delegate, String xpath, boolean optional) {
		BaseXPathPosition xpos = XPathsParser.instance.parseXPath(xpath);
		return new XPathConversionInfo(pos, delegate, xpos, optional);
	}
	
	public XPathConversionInfo createXPath(SizedConverter delegate, String xpath) {
		return createXPath(getPos(), delegate, xpath, false);
	}

	protected void bind(String name, Object value) {
		bindingManager.setOrDefine(name, value);
	}
	
	public IBindingManager getRecordBindings() {
		return bindingManager;
	}
	
	private ExpressionConversionInfo[] conversionInfoCache = null;
	public synchronized  ExpressionConversionInfo[] getConversionInfos() {
		if (conversionInfoCache == null) {
			IConversionInfo[] cinfos = getConversionInfos(this);
			ExpressionConversionInfo[] ecinfo = new ExpressionConversionInfo[cinfos.length];
			for (int i = 0; i < cinfos.length; i++) {
				ecinfo[i] = (ExpressionConversionInfo) cinfos[i]; // the cast is safe since getConversionInfos(object) conrtact
			}
			conversionInfoCache = ecinfo;
		}
		return conversionInfoCache;
	}
	
	public FunctionConversionInfo create(SizedConverter converter, XPathsMapBindinsManagerStringFunction function ) {
		return create(getPos(), converter, function);
	}
	protected FunctionConversionInfo create(int pos, SizedConverter converter, XPathsMapBindinsManagerStringFunction function ) {
		return expressionConversionInfoFactory.create(pos, converter, function);
	}
	
	public FunctionConversionInfo createFixedValue(SizedConverter fill1, String string) {
		return expressionConversionInfoFactory.createFixedValue(getPos(), fill1, string);
	}
	
	public FunctionConversionInfo createFixedValue(String string) {
		return expressionConversionInfoFactory.createFixedValue(getPos(), string);
	}
	
	public VoidExpressionConversionInfo createVoid(int len) {
		return expressionConversionInfoFactory.createVoid(getPos(), len);
	}
	
	public ExpressionConversionInfo createGetCurrentElementContent(SizedConverter converter) {
		return createXPath(converter, ".");
	}
	
	public ExpressionConversionInfo createGetBindingValue(SizedConverter fill2, String bindingName) {
		return expressionConversionInfoFactory.createGetBindingValue(getPos(), fill2, bindingName);
	}
	
	public ExpressionConversionInfo createGetBindingValue(SizedConverter fill2, String bindingName, boolean optional) {
		return expressionConversionInfoFactory.createGetBindingValue(getPos(), fill2, bindingName, optional);
	}
	
	public String getName() {
		return StringUtils.getSimpleName(getClass());
	}

	public String toString() {
		return getName() + "base path[" + this.basePath + "]";
	}
	
	protected ChoiceElementToStringFunction or() {
		return new ChoiceElementToStringFunction();
	}

	protected ChoiceElementToStringFunction or(boolean optional) {
		return new ChoiceElementToStringFunction(optional);
	}

	protected ChoiceElementToStringFunction or(String[] strings) {
		return new ChoiceElementToStringFunction(strings);
	}

	protected static class CounterProvider implements Serializable {
		private static final long	serialVersionUID	= 7055013336247207006L;
		private int pos = 0;
		public int getPos() {
			pos++;
			return pos;
		}
	}
	
}

