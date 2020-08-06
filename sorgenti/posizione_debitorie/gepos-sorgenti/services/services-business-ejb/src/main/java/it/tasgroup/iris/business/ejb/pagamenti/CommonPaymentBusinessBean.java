/**
 * 
 */
package it.tasgroup.iris.business.ejb.pagamenti;

import java.math.BigDecimal;
import java.util.Set;

import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessRemote;
import it.tasgroup.iris.domain.CfgCommissionePagamento;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.helper.CommissioniCalculator;
import it.tasgroup.iris.dto.IOutcomeDTO;
import it.tasgroup.iris.dto.TestataMessaggioDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiOnLineDao;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author pazzik
 *
 */
@Stateless(name = "CommonPaymentBusiness")
public class CommonPaymentBusinessBean implements CommonPaymentBusinessLocal, CommonPaymentBusinessRemote{
	
	@EJB(name = "CfgGatewayPagamentoDaoService")
	private CfgGatewayPagamentoDao cfgGatewayPagamentoDao;
	
	@EJB(name = "PagamentiOnLineDaoService")
	private PagamentiOnLineDao polDao;
		
	private static final Logger LOGGER = LogManager.getLogger(CommonPaymentBusinessBean.class);

	@Override
	public IOutcomeDTO controlliTestata(IOutcomeDTO dtoOut,TestataMessaggioDTO testataDto,String identificativoCanalePsp, String identificativoIntermediarioPsp) {

		// /////////////////////
		// Verifica chiamante
		// /////////////////////
		// /////////////////////
		// sistema
		// /////////////////////

		String systemId = testataDto.getSenderSys();
		String applicationId = testataDto.getSenderSil();
		
		CfgGatewayPagamento cfgGatewayPagamento = null;
		// N.B. vedi PSPDTOBuilder.SENDER_SIL_PSP
		if("SENDER_SIL_PSP".equals(applicationId)) {
			cfgGatewayPagamento = cfgGatewayPagamentoDao.getCfgBySystemIdModelloVersamentoCanaleIntermediario(systemId, "4", identificativoCanalePsp,identificativoIntermediarioPsp);
			if (cfgGatewayPagamento == null)  {
				LOGGER.warn("configurazionegateway non trovata : systemId=" +systemId +" modelloVersamento= 4");
				if (true){
				   cfgGatewayPagamento = cfgGatewayPagamentoDao.getCfgBySystemId("DEFAULT3XXX");
				   testataDto.setIdPspModello3(systemId);
				   testataDto.setIdIntermediarioModello3(identificativoIntermediarioPsp);
				   testataDto.setIdCanaleModello3(identificativoCanalePsp);
				} else {
				   LOGGER.error("configurazionegateway non trovata : systemId=" +systemId +" modelloVersamento= 4");
				   dtoOut.setReturnCode(EnumBusinessReturnCodes.E0000007);
				   return dtoOut;
				}
			} 

			// N.B. patch per recuperare l'application id reale dalla configurazione del gateway
			testataDto.setSenderSil(cfgGatewayPagamento.getApplicationId());
			testataDto.getSession().setIdTerminale(cfgGatewayPagamento.getApplicationId());

			
		} else {
			
			cfgGatewayPagamento = cfgGatewayPagamentoDao.getCfgBySystemIdAndApplicationId(systemId, applicationId);
			
			if (cfgGatewayPagamento != null){
				
				dtoOut.setEnumModRiversamento(cfgGatewayPagamento.getEnumModRiversamento());
				dtoOut.setFlagRendRiversamento(cfgGatewayPagamento.getFlagRendRiversamento());
				
				Set<CfgCommissionePagamento> commissioni = cfgGatewayPagamento.getCfgCommissionePagamenti();
				
				if (commissioni != null && !commissioni.isEmpty()){
					BigDecimal importoCommissioni = CommissioniCalculator.calcolaTotaleCommissioni(commissioni, new BigDecimal(0));
					dtoOut.setImportoCommissioni(importoCommissioni.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
			}
			
			if (cfgGatewayPagamento == null)
				
				if (testataDto.getSenderSil().equals(CfgGatewayPagamento.CBILL) && testataDto.getSenderSys() == null)
					
					return dtoOut;
				
				else {
				
						LOGGER.error("configurazionegateway non trovata : applicationId=" +applicationId +" systemId= "+systemId);
				
						dtoOut.setReturnCode(EnumBusinessReturnCodes.E0000001);
				
						return dtoOut;
					 }
		}
									
		return dtoOut;
	}
	
	/**
	 * @param testataIn
	 * @param dtoOut
	 * @param statoOperazione
	 * @param opInserimento
	 * @param codAut
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void manageTermination(TestataMessaggioDTO testataIn, IOutcomeDTO dtoOut, String deOperazione, EnumOperazioniPagamento statoOperazione, String opInserimento, String codAut){
		
		String logMessage = dtoOut.getLogMessage() + " - esito: " + dtoOut.getReturnCode();
		
		if (dtoOut.getReturnCode().isError() || statoOperazione.equals(EnumOperazioniPagamento.ESTRATTOCONTO)) {
						
			PagamentiOnline newPol = PaymentEntityBuilder.popolaPagamentoOnline(testataIn , codAut , null , deOperazione, statoOperazione, dtoOut.getReturnCode(), opInserimento);
			
			polDao.savePOL(newPol);
			
		}
		
		if (dtoOut.getReturnCode().isError())
			
			LOGGER.error(logMessage);
		
		else
			
			LOGGER.info(logMessage);
			
				
	}
	

}
