package it.tasgroup.dse.view;

import java.io.InputStream;

import it.nch.eb.common.utils.loaders.InputStreamFactory;

/**
 * @author agostino
 * @deprecated use InputStreamFactory instead
 */
public abstract interface DataInput extends InputStreamFactory{

//	private static final long serialVersionUID = 132218636006623947L;
	
//	public static final int DRM_MEMORY = 0; // in memoria (fornita buffer)
//	public static final int DRM_DB = 1; // su db ed accesso in streaming (o anche no per db vecchi)
//	public static final int DRM_URL = 2;  // dati forniti mediante URL
	
	InputStream createStream();
	
	
}
