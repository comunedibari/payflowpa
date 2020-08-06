/**
 * Created on 12/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;

import it.nch.eb.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * @author gdefacci
 */
public class CommandsContainer {

	private List/*<BaseXmlCommand>*/ allCommands = new ArrayList();
	
	public List/*<BaseXmlCommand>*/ getCommands() {
		return Collections.unmodifiableList( allCommands );
	}
	
	protected BaseXmlCommand createCommand(String cmndId) {
		return new BaseXmlCommand(cmndId);
	}
	
	public BaseXmlCommand getCommand(String cmndId) {
		for (Iterator it = allCommands.iterator(); it.hasNext();) {
			BaseXmlCommand cmd = (BaseXmlCommand) it.next();
			if (cmd.getName().equals(cmndId)) return cmd;
		}
		return null;
	}
	
	private void checkUniqueName(BaseXmlCommand baseXmlCommand) {
		String newNm = baseXmlCommand.getName();
		for (Iterator it = allCommands.iterator(); it.hasNext();) {
			XmlCommand xmlCmd = (XmlCommand) it.next();
			if (xmlCmd.getName().equals(newNm)) {
				throw new IllegalStateException("a command with name " + newNm + " has already been created");
			}
		}
	}
	
	public class BaseXmlCommand implements XmlCommand {

		private final int	ordValue;
		private final String	name;
		
		private BaseXmlCommand(String name) {
			this.ordValue = allCommands.size();
			this.name = name;
			checkUniqueName(this);
			allCommands.add(this);
		}

		public String getName() {
			return name;
		}

		public int ordinal() {
			return ordValue;
		}

		public boolean equals(Object obj) {
			if (obj instanceof BaseXmlCommand) {
				BaseXmlCommand othr = (BaseXmlCommand) obj;
				if (othr.getName().equals(getName()) && othr.ordinal() == ordinal()) {
					return true;
				}
			}
			return false;
		}

		public int hashCode() {
			return (ordinal() * 11) + getName().hashCode();
		}

		public String toString() {
			return StringUtils.getSimpleName(getClass()) + "(name: " + getName() + ", ordinal" + ordinal() + ")";
		}
		
	}
	
	
}
