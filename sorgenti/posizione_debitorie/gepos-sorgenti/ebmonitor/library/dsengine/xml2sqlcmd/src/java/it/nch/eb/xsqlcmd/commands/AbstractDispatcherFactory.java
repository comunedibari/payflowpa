/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;

import java.util.ArrayList;
import java.util.List;



/**
 * @author gdefacci
 */
public abstract class AbstractDispatcherFactory {
	
	private final List/*<PredicateCommand>*/ predicateCommands = new ArrayList();
	private final CommandHandlerFaultHandler faultHandler;

	public AbstractDispatcherFactory() {
		this(new CommandHandlerFaultHandler() {

			public void onError(Exception e, XmlCommand command, Object model, CommandHandler handler) {
				throw new RuntimeException("error with command " + command + 
						" on object " + model + 
						" raise by handler " + handler, e);
			}
			
		});
	}
	
	public AbstractDispatcherFactory(CommandHandlerFaultHandler faultHandler) {
		this.faultHandler = faultHandler;
		if (this.faultHandler == null) throw new NullPointerException();
	}

	public CommandHandlerFaultHandler getFaultHandler() {
		return faultHandler;
	}

//	public void setFaultHandler(CommandHandlerFaultHandler faultHandler) {
//		this.faultHandler = faultHandler;
//	}

	public void addHandler(final XmlCommand cmd, final CommandHandler ch) {
		addHandler(cmd, new CommandHandlerProvider() {

			public CommandHandler get() {
				return ch;
			}
			
		});
	}
	public void addHandler(final XmlCommand cmd, final CommandHandlerProvider ch) {
		PredicateCommand pc = new PredicateCommand() {

			public CommandPredicate getPredicate() {
				return new CommandPredicate() {

					public boolean match(XmlCommand command, Object model) {
						return command.equals(cmd);
					}
					
				};
			}

			public CommandHandlerProvider getHandlerProvider() {
				return ch;
			}
			
		};
		this.predicateCommands.add(pc);
	}

	public Dispatcher create() {
		PredicateCommand[] pcs = (PredicateCommand[]) this.predicateCommands.toArray(new PredicateCommand[0]);
		return new BaseDispatcher(pcs, getFaultHandler());
	}

}
