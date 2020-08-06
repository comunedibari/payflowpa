package it.nch.idp.util;

import it.nch.utility.web.WebUtility;
import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class ModalitaPagamentoDecorator implements DisplaytagColumnDecorator
{
	  public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
	  {
		String valoreModalita = (String)columnValue;
		return decorateModalitaPagamentoHtml(valoreModalita, pageContext); 
	  }
      
      public static String decorateModalitaPagamentoHtml(String stato, PageContext pageContext)
      {
      	String retval = "";
      	try
      	{
	      	stato = stato.trim();
	      	if("S".equalsIgnoreCase(stato))
	      	{
				retval = getTextMsg(pageContext,"posizionedebitoria.dettaglio.modpagamentosingolo");
	      	}
	      	else if("R".equalsIgnoreCase(stato))
	      	{
				retval = getTextMsg(pageContext,"posizionedebitoria.dettaglio.modpagamentorate");
	      	}
			else if("E".equalsIgnoreCase(stato))
			{
				retval = getTextMsg(pageContext,"posizionedebitoria.dettaglio.modpagamentoqualsiasi");
			}
	      	
      	} catch(Exception ex){
      		
      		ex.printStackTrace();
      		
      		}
      	
		return retval;
		
      }
	      
	  private static String getTextMsg(PageContext pageContext, String message)
	  {
		String msg = "";
		try
		{
			msg = WebUtility.getResourceMessage(pageContext, message);
		}
		catch (Exception Ex){}
		return msg;
	  }
}
