/**
 * Created on 02/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.loaders.InputsFactory;

/**
 * @author gdefacci
 */
public interface ModelLoaderBuilderFactory {

	ModelLoaderBuilder create(InputsFactory readerFactory);

}