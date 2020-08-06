/**
 * Created on 02/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;

/**
 * @author gdefacci
 */
public interface ModelLoaderBuilder {

	ModelLoader create(IBindingManager bm);

}