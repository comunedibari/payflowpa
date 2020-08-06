package it.tasgroup.idp.cart3;

import it.tasgroup.idp.bean.IVerificaStatoPagamento;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;

public class CommonOTFWs {
	
	private IVerificaStatoPagamento verificaStatoPagamentoBean;
	
	
	protected void buildHeaderForResponse(IdpHeader header) {
		String temp = header.getE2E().getSender().getE2ESndrId();
		header.getE2E().getSender().setE2ESndrId(header.getE2E().getReceiver().getE2ERcvrId());
		header.getE2E().getReceiver().setE2ERcvrId(temp);
		
		temp = header.getE2E().getSender().getE2ESndrSys();
		header.getE2E().getSender().setE2ESndrSys(header.getE2E().getReceiver().getE2ERcvrSys());
		header.getE2E().getReceiver().setE2ERcvrSys(temp);
		
		temp = header.getTRT().getSender().getSenderId();
		header.getTRT().getSender().setSenderId(header.getTRT().getReceiver().getReceiverId());
		header.getTRT().getReceiver().setReceiverId(temp);
		
		temp = header.getTRT().getSender().getSenderSys();
		header.getTRT().getSender().setSenderSys(header.getTRT().getReceiver().getReceiverSys());
		header.getTRT().getReceiver().setReceiverSys(temp);
	}


	protected IVerificaStatoPagamento getVerificaStatoPagamentoBean() {
		if (verificaStatoPagamentoBean == null) { 
			return (IVerificaStatoPagamento) IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.VerificaStatoPagamento);
		}
		return verificaStatoPagamentoBean;
	}

}
