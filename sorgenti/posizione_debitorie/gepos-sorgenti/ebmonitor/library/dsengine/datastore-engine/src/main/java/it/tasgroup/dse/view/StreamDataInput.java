/**
 * 28/ago/2009
 */
package it.tasgroup.dse.view;

import it.nch.eb.common.utils.loaders.StreamsResourcesLoader;

import java.io.InputStream;

/**
 * @author gdefacci
 */
public class StreamDataInput implements DataInput {

	private final StreamsResourcesLoader loader;
	private final String name;
	public StreamDataInput(StreamsResourcesLoader loader, String name) {
		this.loader = loader;
		this.name = name;
	}

	public InputStream createStream() {
		return loader.loadInputStream(name);
	}

}
