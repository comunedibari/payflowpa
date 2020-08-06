/**
 * Created on 11/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.BindingManagerFactory;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.sax.SaxElementHandler;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.fwk.core.NamespacesInfos;

import java.io.Reader;


/**
 * @author gdefacci
 */
public class FromXmlDispatcher extends FromXmlConversion {
	
	final ModelLoaderBuilderFactory modelBuilderFactory;
	
	public FromXmlDispatcher(NamespacesInfos qnss,
			ModelLoaderBuilderFactory modelBuilderFactory) {
		super(qnss, null);
		this.modelBuilderFactory = modelBuilderFactory;
	}

	public void dispatch(InputsFactory readerFactory) {
		
		IBindingManager bindings = BindingManagerFactory.instance.createBindingManager();
		
		applyBindingManagerEffects(bindings);
		
		ModelLoaderBuilder modelLoaderBuilder = modelBuilderFactory.create(readerFactory);
	
		ModelLoader leafElementHandler = modelLoaderBuilder.create(bindings);
		
		SaxElementHandler dsh = createSaxHandler(leafElementHandler);
		
		Reader reader = readerFactory.createReader();
		if (reader == null) {
			throw new NullPointerException(readerFactory.getName());
		}
		saxParse(reader, dsh);
		
	}

	protected SaxElementHandler createSaxHandler(ModelLoader leafElementHandler) {
		return new SaxElementHandler(leafElementHandler, getQueryNamespaces(), new ChildAwareXPathBuilder());
	}

}
