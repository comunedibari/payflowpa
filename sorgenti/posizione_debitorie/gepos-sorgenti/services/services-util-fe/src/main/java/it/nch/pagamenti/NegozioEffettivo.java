package it.nch.pagamenti;

import javax.servlet.http.HttpServletRequest;

public class NegozioEffettivo extends Negozio {
	private HttpServletRequest request;
	
	public NegozioEffettivo(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public String getOpEsito() {
		return request.getParameter("op_esito");	
	}

	@Override
	public String getOpStatoOp() {
		return request.getParameter("op_stato_op");
	}

	@Override
	public String getOpAutorizzazione() {
		return request.getParameter("op_autorizzazione");
	}

	@Override
	public String getOpData() {
		return request.getParameter("op_stime");
	}

	@Override
	public String getOpTokenGuidOtp() {
		return request.getParameter("op_token_guid_otp");
	}

	@Override
	public String getOpFirma() {
		return request.getParameter("op_firma");
	}

	@Override
	public String getOpTimeOut() {
		return request.getParameter("op_time_out");
	}

	@Override
	public String getOpUrlPagamento() {
		return request.getParameter("op_url_pagamento");
	}

	@Override
	public String getNmsg() {
		return request.getParameter("op_nmsg");
	}

}
