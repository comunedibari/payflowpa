/**
 * Created on 29/set/2008
 */
package it.nch.streaming.services.impl;

import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.SerializableConversionService;
import it.nch.eb.flatx.flatmodel.conversioninfo.BindingManagerEffect;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.FromXmlConversion;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.ModelLoaderBuilder;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.sax.SaxElementHandler;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.fwk.core.NamespacesInfos;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * @author gdefacci
 */
public abstract class BaseToFlatConversionService extends FromXmlConversion implements SerializableConversionService {
	
	private static final long	serialVersionUID	= -2556483728768768141L;

	private static final String	DEFAULT_LINE_TERMINATOR	= "\n";

	protected final String lineTerminator;
	
	protected BaseToFlatConversionService(String lineTerminator, 
			NamespacesInfos qnss, 
			BindingManagerEffect[] bmEffects) {
		super(qnss, bmEffects);
		this.lineTerminator = lineTerminator;
	}
	
	public BaseToFlatConversionService(String lineTerminator, NamespacesInfos qnss) {
		this(lineTerminator, qnss, null);
	}
	
	public BaseToFlatConversionService(NamespacesInfos qnss) {
		this(DEFAULT_LINE_TERMINATOR, qnss, null);
	}
	
	public String getLineTerminator() {
		return lineTerminator;
	}
	
	public final BaseToFlatConversionService create(Map map) {
		return create(createEntriesInBindingManager(map));
	}
	
	public final BaseToFlatConversionService create(BindingManagerEffect bmEffect) {
		return create(new BindingManagerEffect[] { bmEffect });	
	}
	
	public final BaseToFlatConversionService create(List/*BindingManagerEffect*/ bmEffect) {
		return create((BindingManagerEffect[]) bmEffect.toArray(new BindingManagerEffect[0]));	
	}
	
	public FromXmlConversion createNew(BindingManagerEffect[] bmEffect) {
		return create(bmEffect);
	}

	public abstract BaseToFlatConversionService create(BindingManagerEffect[] bmEffect);
	
	/**
	 * return the list of records that contribuite a line counter increment. ordering is unimportant.
	 * @return
	 */
	public abstract IRecord[] getRecords();
	
	private LineTerminatedStringAction createLineAction(final PrintWriter wr) {
		return new LineTerminatedStringAction() {
			
			public void printLineTerminator() {
				wr.print(lineTerminator);
				wr.flush();
			}
	
			public void execute(String str) {
				wr.print(str);
			}
	
		};
	}
	
	protected abstract ModelLoaderBuilder createModelLoaderBuilder(
			LineTerminatedStringAction printLineAction); 

	public void convert(Reader reader, Writer writer) {
		IBindingManager bindings = BindingManagerFactory.instance.createBindingManager();
		bindings.enterScope();
		
		applyBindingManagerEffects(bindings);
		
		LineTerminatedStringAction printLineAction = createLineAction(new PrintWriter(writer));
		
		ModelLoaderBuilder modelLoaderBuilder = createModelLoaderBuilder(printLineAction);
	
		ModelLoader leafElementHandler = modelLoaderBuilder.create(bindings);
		
		SaxElementHandler dsh = new SaxElementHandler(leafElementHandler, 
				getQueryNamespaces(), new ChildAwareXPathBuilder());
		
		saxParse(reader, dsh);
		
		ResourcesUtil.flush(writer);
		ResourcesUtil.close(writer);
		bindings.exitScope();
	}

}