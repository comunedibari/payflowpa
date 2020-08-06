/**
 * Created on 16/ott/08
 */
package it.nch.streaming.services.impl;

import java.util.Map;

import it.nch.eb.flatx.flatmodel.sax.ActivableLeafElementHandler;


/**
 * @author gdefacci
 */
public interface MapCollectorActivableLeafElementHandler extends ActivableLeafElementHandler {

	Map/*<String, Object>*/ getState();

}
