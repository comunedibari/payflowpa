/**
 *
 */
package it.nch.erbweb.web.common.action;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author PazziK
 *
 */
public class ActionHelper {

	public static void createAndAddMessage(ActionMessages messages, String messageKey, String messageName, Object[] parameters){

		if(messages==null)	messages = new ActionMessages();

		ActionMessage messaggio = new ActionMessage(messageKey, parameters);

		if(messageName!=null)
			messages.add(messageName,messaggio);
		else
			messages.add("msg"+System.currentTimeMillis(),messaggio);
	}

}
