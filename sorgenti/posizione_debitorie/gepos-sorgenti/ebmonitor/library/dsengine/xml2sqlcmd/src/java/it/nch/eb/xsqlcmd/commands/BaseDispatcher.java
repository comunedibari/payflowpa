/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.commands;


/**
 * @author gdefacci
 */
public class BaseDispatcher implements Dispatcher {
	
	private CommandHandlerFaultHandler faultHandler;
	
	private final PredicateCommand[] handlers;
	public BaseDispatcher(PredicateCommand[] handlers, CommandHandlerFaultHandler faultHandler) {
		this.handlers = handlers;
		this.faultHandler = faultHandler;
	}

	public void send(XmlCommand command, Object model) {
		for (int i = 0; i < handlers.length; i++) {
			CommandHandler ch = handlers[i].getHandlerProvider().get();
			CommandPredicate pred = handlers[i].getPredicate();
			if (pred.match(command, model)) {
				try {
					ch.execute(command, model);
				} catch (Exception e) {
					faultHandler.onError(e, command, model, ch);
				}
			}
		}
	}

}
