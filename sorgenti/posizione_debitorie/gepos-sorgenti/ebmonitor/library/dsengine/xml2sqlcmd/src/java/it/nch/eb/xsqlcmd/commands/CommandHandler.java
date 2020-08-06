/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;

import it.nch.eb.xsqlcmd.commands.XmlCommand;


/**
 * @author gdefacci
 */
public interface CommandHandler {

	void execute(XmlCommand command, Object obj);
}
