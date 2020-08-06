/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;

import it.nch.eb.xsqlcmd.commands.XmlCommand;


/**
 * @author gdefacci
 */
public interface CommandHandlerFaultHandler {
	
	void onError(Exception e, XmlCommand command, Object model, CommandHandler handler);

}
