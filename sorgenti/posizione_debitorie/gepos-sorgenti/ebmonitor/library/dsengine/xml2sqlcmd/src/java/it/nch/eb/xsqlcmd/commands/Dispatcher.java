/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;


/**
 * @author gdefacci
 */
public interface Dispatcher {
	void send(XmlCommand cmd, Object obj);
}