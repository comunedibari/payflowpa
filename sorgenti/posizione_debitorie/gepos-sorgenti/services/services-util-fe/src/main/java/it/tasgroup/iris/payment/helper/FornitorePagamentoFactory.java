/**
 * 
 */
package it.tasgroup.iris.payment.helper;

import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumFornitorePagamento;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

/**
 * @author pazzik
 *
 */
public class FornitorePagamentoFactory {
	
	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
	
	
	private FornitorePagamentoFactory(){
		
	}

	static FornitorePagamento getNewInstance(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) throws RuntimeException {
		
		return getNewInstance(dettDistinta.getIdFornitorePagamento(), dettDistinta.getModalitaPagamento());
	}

	static FornitorePagamento getNewInstance(Number idFornitore, String modalitaPagam) throws RuntimeException {

		if (idFornitore == null)
			throw new RuntimeException("ID Fornitore NULLO");
		
		if (modalitaPagam == null)
			throw new RuntimeException("Modalita Pagamento NULLA");
		
		EnumFornitorePagamento fornitore = EnumFornitorePagamento.getById(idFornitore.intValue());
		
		switch (fornitore) {
						
			case CBILL: case RT: // SST (SERVIZIO SANITARIO TOSCANA)				
				return new FornitorePagamentoCBILL();
				
			case NDPSPC: // NodoSPC
				
				if ((modalitaPagam.equals(EnumTipoModalitaPagamento.PSP.getDescrizione())))
						return new FornitorePagamentoNodoSPC_RT_PSP();
					
					
				   return new FornitorePagamentoNodoSPC_Carrello();
				
				//
			case POSTE:			
				if (modalitaPagam.equals(EnumTipoModalitaPagamento.FESPPI.getDescrizione()))
												return new FornitorePagamentoNodoSPC_POSTE_FESP();
				
				return new FornitorePagamentoPOSTE();
		}

		throw new RuntimeException("Fornitore con id: " + idFornitore + " NON IMPLEMENTATO");

	}

}
