/**
 *
 */
package it.tasgroup.iris.business.ejb.client.confpagamenti;

import it.tasgroup.iris.domain.*;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * @author PazziK
 *
 */
public interface ConfPagamentiBusiness
{
	//public List<CommissionePagamento> readCommissioniPagamentoListByTipoDDP(DocumentoDiPagamento ddp);

	public List<CfgModalitaPagamento> getListaModalitaPagamento();
	
	public List<CfgModalitaPagamento> getListaAllModalitaPagamento();
	
	public List<CfgModalitaPagamento> getListaCfgModalitaInActiveCfgFornitore(String circuito);

	public List<CfgGatewayPagamento> getCfgGatewayPagamentoAll();

	public CfgGatewayPagamento getCfgGatewayPagamentoById(Long id);

	public void updateStatoListCfgGatewayPagamento(String[] idcfggatewaypagamento, String stato) throws Exception;

	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String stato) throws Exception ;

	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String intestatario,String stato) throws Exception ;

	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String stato, String sysId, String appId) throws Exception ;

	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String intestatario,String stato, String sysId, String appId) throws Exception;

	public List<CfgStrumentoPagamento> getCfgStrumentoPagamentoAll();

	public CfgStrumentoPagamento getCfgStrumentoPagamentoById(Long id);

	public List<CfgGatewayPagamento> getCfgGatewayPagamentoByFilter(ContainerDTO dto);

	public List<CfgGatewayPagamento> getCfgGatewayPagamentoListDistinctSystemName() throws Exception;

	public List<CfgFornitoreGateway> getCfgFornitoreGatewayAll() throws Exception;
	
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayBoe(String idEnteCreditore) throws Exception;
	
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayDistinctId();

	public CfgGatewayPagamento getCfgGatewayPagamento(String bundleKey);

	public List<CfgGatewayPagamento> getCfgGatewayPagamentoEnabledByTributiEnti(String idEnte, String cdTrbEnte);

	public CfgEntiLogo getCfgEntiLogoById(String id);

	public CfgEntiLogo createCfgEntiLogo(CfgEntiLogo entiLogo);

	public CfgEntiLogo updateCfgEntiLogo(CfgEntiLogo entiLogo);

	public CfgEntiTema getCfgEntiTemaById(String cdEnte);
	
	public CfgEntiTema getCfgEntiTemaLiteById(String cdEnte);
	
	Date getCfgEntiTemLastModified(String cdEnte);

	public CfgEntiTema createCfgEntiTema (CfgEntiTema entiTema);

	public CfgEntiTema updateCfgEntiTema(CfgEntiTema entiTema);

	public List<Long> getIdCfgGatewayPagamentoEnabledByTributiEnti(String idEnte,String cdTrbEnte);
	
	public List<CfgTributiEntiGateway> getListaCfgGatewayPagamentoTributiEnteFornitore(String idEnte, String cdTrbEnte);
	
	public List<Object[]> readModalitaPagamentoPsp(String idEnte);
	
	public List<Object[]> readModalitaPagamentoTipoVersamento(String sys, String sub);
		
	public CfgGatewayPagamento readCfgGatewayPagamentoDettaglioBySystemId(String systemId);
	
	public List<CfgFornitoreGateway> readCfgFornitoreGatewayCircuito(String idEnte) throws Exception;

	public CfgGatewayPagamento getCfgGatewayPagamentoById(String bundleKey);
}
