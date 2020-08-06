package it.nch.idp.util;

import it.nch.utility.web.WebUtility;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.services.util.enumeration.EnumStatoAEA;

import java.text.SimpleDateFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;

public class ElencoDelegheTableDecorator extends TableDecorator {
	  private static String erroretocolor = "testonormalerosso";
	  private static String verdecolor = "testonormaleverde";
	  private static String giallocolor = "testonormalegiallo";

	public String getDataRichiesta() {
		String dataRichiestaStr = "";
		try {
			AllineamentiElettroniciArchiviDTO ddpDTO = (AllineamentiElettroniciArchiviDTO) getCurrentRowObject();
			if(ddpDTO.getDataRichiesta() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataRichiestaStr = sdf.format(ddpDTO.getDataRichiesta());
			}
		} catch (Exception e) {
			return "";
		} finally{
			return dataRichiestaStr;
		}
	}

	public String getDataCessazione() {
		String dataCessazioneStr = "";
		try {
			AllineamentiElettroniciArchiviDTO ddpDTO = (AllineamentiElettroniciArchiviDTO) getCurrentRowObject();
			if(ddpDTO.getDataCessazione() != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dataCessazioneStr = sdf.format(ddpDTO.getDataCessazione());
			}
		} catch (Exception e) {
			return "";
		} finally{
			return dataCessazioneStr;
		}
	}
	
	public String getDataAttivazione() {
		String dataAttivazioneStr = "";
		try {
			AllineamentiElettroniciArchiviDTO ddpDTO = (AllineamentiElettroniciArchiviDTO) getCurrentRowObject();
			if(ddpDTO.getDataAttivazione() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataAttivazioneStr = sdf.format(ddpDTO.getDataAttivazione());
			}
		} catch (Exception e) {
			return "";
		} finally{
			return dataAttivazioneStr;
		}
	}

	public String getIdRevoca()
	  {
	  	String ret = "#";
	  	AllineamentiElettroniciArchiviDTO tmp_dist = (AllineamentiElettroniciArchiviDTO) getCurrentRowObject();
		if(tmp_dist!=null)
		{
			String stato = tmp_dist.getStato();
			String statoRevocata = tmp_dist.getStatoRevoca();
			System.out.println("statoRevoca: " + statoRevocata);
			Long id = tmp_dist.getId();
			ret = getRevocaHtml(stato,statoRevocata, super.getPageContext(),id);
		}
	  	return ret;
	  }

	public String getStato()
	{
		String ret = "#";
		AllineamentiElettroniciArchiviDTO tmp_dist = (AllineamentiElettroniciArchiviDTO) getCurrentRowObject();
		if(tmp_dist!=null)
		{
			String stato = tmp_dist.getStato();
			String statoRevocata = tmp_dist.getStatoRevoca();
			ret = getStatoHtml(stato, statoRevocata,super.getPageContext());
		}
		return ret;
	}

	  
	  public static String getRevocaHtml(String stato, String statoRevoca, PageContext pageConext, Long id){
		  String ret = "<a href=\"javascript:confermaRevoca("+ id +");\"  name=\"checkboxelenco\"><img src=\"/images/trash.png\" alt=\"Revoca Delega\"></a>";
//		  String ret = "<a href=\"javascript:submitOperation('delegheRid.do?method=revocaDelega&amp;idDelega="+ id +"','delegheForm');\"  name=\"checkboxelenco\"><img src=\"/images/trash.png\" alt=\"Revoca Delega\"></a>";

		  if (statoRevoca != null){
			  if(statoRevoca.trim().equals(EnumStatoAEA.ACCETTATA.getChiave() ) )
				  ret = "<a href=\"javascript:confermaRevoca("+ id +");\"  name=\"checkboxelenco\"><img src=\"/images/trash.png\" alt=\"Revoca Delega\"></a>";
		  
			  else if (statoRevoca.trim().equals(EnumStatoAEA.IN_CORSO.getChiave() ) ||  
					  statoRevoca.trim().equals(EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave() ) ||  
					  statoRevoca.trim().equals(EnumStatoAEA.IN_CORSO_REVOCA.getChiave()))
				  ret = "<img src=\"/images/trash_yellow.png\" alt=\"Revoca Delega in corso\"/>";
			  else if(statoRevoca.trim().equals(EnumStatoAEA.RIFIUTATA.getChiave() ) ||  
					  statoRevoca.trim().equals(EnumStatoAEA.REVOCATA.getChiave()))
				  ret = "<img src=\"/images/trash_disabled.png\" alt=\"Revoca Delega inattivo\"/>";
		  }
		  return  ret;
		  
	  }	
	  
	  public static String getStatoHtml(String stato, String statoRevocata,PageContext pageConext)
	  {
		if(stato==null || stato.trim().equals(""))
		{
			return "<span>"+stato+"</span>";
		}
		else if(stato.trim().equals(EnumStatoAEA.ACCETTATA.getChiave()))
		{
			if(statoRevocata != null)
				if(statoRevocata.trim().equals(EnumStatoAEA.IN_CORSO.getChiave()) ||
						statoRevocata.trim().equals(EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave()) ||
						statoRevocata.trim().equals(EnumStatoAEA.IN_CORSO_REVOCA.getChiave()))
					return "<span class='"+giallocolor+"'>"+stato+"</span>";
				
			return "<span class='"+verdecolor+"'>"+stato+"</span>";
		}
		else if(stato.trim().equals(EnumStatoAEA.IN_CORSO.getChiave()))
		{
			return "<span class='"+giallocolor+"'>"+stato+"</span>";
		}
		else if(stato.trim().equals(EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave()))
		{
			return "<span class='"+giallocolor+"'>"+stato+"</span>";
		}
		else if(stato.trim().equals(EnumStatoAEA.IN_CORSO_REVOCA.getChiave()))
		{
			return "<span class='"+giallocolor+"'>"+stato+"</span>";
		}
		else if(stato.trim().equals(EnumStatoAEA.REVOCATA.getChiave()))
		{
			return "<span class='"+erroretocolor+"'>"+stato+"</span>";
		}
		else if(stato.trim().equals(EnumStatoAEA.RIFIUTATA.getChiave()))
		{
			return "<span class='"+erroretocolor+"'>"+stato+"</span>";
		}
		else
		{
			return "<span>"+stato+"</span>";
		}
		
	  }
	  

	

	private static String getTextMsg(PageContext pageContext, String message) {
		String msg = "";
		try {
			msg = WebUtility.getResourceMessage(pageContext, message);
		} catch (Exception Ex) {
		}
		return msg;
	}
}
