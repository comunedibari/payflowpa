/**
 * 
 */
package it.tasgroup.iris.business.ejb.client.pagamenti;

import it.tasgroup.iris.dto.EstrattoContoDebitorioDTO;
import it.tasgroup.iris.dto.EstrattoContoDebitorioOutputDTO;



public interface EstrattoContoDebitorio
{	
     EstrattoContoDebitorioOutputDTO estrattoContoDebitorio(EstrattoContoDebitorioDTO richiestaEstrattoContoDebitorioDto);

}
