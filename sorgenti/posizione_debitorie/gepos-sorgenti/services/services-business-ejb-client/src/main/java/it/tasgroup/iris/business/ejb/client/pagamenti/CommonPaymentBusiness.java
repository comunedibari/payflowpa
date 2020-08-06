/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.pagamenti;

import it.tasgroup.iris.dto.IOutcomeDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;

import java.io.Serializable;


public interface CommonPaymentBusiness extends Serializable{
	
	public void manageTermination(TestataMessaggioDTO testataIn, IOutcomeDTO dtoOut, String deOperazione, EnumOperazioniPagamento statoOperazione, String opInserimento, String codAut);
	
	public IOutcomeDTO controlliTestata(IOutcomeDTO dtoOut,TestataMessaggioDTO testataDto,String identificativoCanalePsp, String identificativoIntermediarioPsp);

	}
