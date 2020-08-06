/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;

import it.nch.eb.xsqlcmd.commands.XmlCommand;


/**
 * @author gdefacci
 */
public interface CommandPredicate {

	boolean match(XmlCommand command, Object model);
}
