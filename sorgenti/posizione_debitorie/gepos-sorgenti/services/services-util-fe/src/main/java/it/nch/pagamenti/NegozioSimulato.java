package it.nch.pagamenti;

import it.nch.erbweb.client.ServiceSessionConstants;
import it.nch.is.fo.stati.pagamenti.StatiNegozioPPG;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.utility.GeneratoreIdUnivoci;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class NegozioSimulato extends Negozio {
	private HttpServletRequest request;
	
	public NegozioSimulato(HttpServletRequest request) {
		this.request = request;
	}

	public String getOpEsito() {
		return "0";
	}

	public String getOpStatoOp() {
		 // StatiNegozioPPG.ESITO_STATO_5.getStato() = ESEGUITO 
		String txtNumCarta = request.getParameter("txtNumCarta");	
		StatiNegozioPPG esito = StatiNegozioPPG.ESITO_STATO_10;
		DistintaCartaCreditoVO distinta = (DistintaCartaCreditoVO) request.getSession().getAttribute(ServiceSessionConstants.distintaPagamento);
		
		if ((txtNumCarta != null && !txtNumCarta.endsWith("0")) || (distinta != null && EnumTipoModalitaPagamento.BONIFICOONLINE.getChiave().equals(distinta.getMezzoPagamento()))) {
			esito = StatiNegozioPPG.ESITO_STATO_5;
		} else {
			esito = StatiNegozioPPG.ESITO_STATO_10;
		}
		
		return String.valueOf(esito.getStato());
		
	}

	public  String getOpAutorizzazione() {
		String txtNumCarta = request.getParameter("txtNumCarta");	
		DistintaCartaCreditoVO distinta = (DistintaCartaCreditoVO) request.getSession().getAttribute(ServiceSessionConstants.distintaPagamento);
		if ((txtNumCarta != null && !txtNumCarta.endsWith("0")) || (distinta != null && EnumTipoModalitaPagamento.BONIFICOONLINE.getChiave().equals(distinta.getMezzoPagamento()))) {
			return "opAutorizzazione demo ok";
		} else {
			return "opAutorizzazione demo ko";
		}

	}
	
	public  String getOpData() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return format.format(new Date());
	}


	public String getOpTokenGuidOtp(boolean generateNew) {
		return GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
	}

	public String getOpFirma() {
		return "firmaDemo";
	}

	public String getOpTimeOut() {
		return "0";
	}

	public String getOpUrlPagamento() {
		return  "http://cardsim";
	}

	public String getNmsg() {
		return "op_nmsg_simulatore";
	}

	public String getOpTokenGuidOtp() {
		return GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
	}
}


