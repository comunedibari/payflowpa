package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgTributiEntiGateway;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
@Local
public interface CfgGatewayPagamentoDao extends Dao<CfgGatewayPagamento>{

	public CfgGatewayPagamento getCfgByBundleKey(String key);
	
	public List<CfgGatewayPagamento> getListCfgByStato(String stato);
	
	public List<CfgGatewayPagamento> getListCfgByStatoAndSysIdAndAppId(String stato,String sysId, String appId);
	
	public CfgGatewayPagamento getCfgBySystemIdAndApplicationId(String systemId,String applicationId);
	
	public CfgGatewayPagamento getCfgBySystemIdModelloVersamentoCanaleIntermediario(String systemId,String modelloVersamento,String identificativoCanalePSP, String identificativoIntermediarioPSP);
	
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoAll();
	
	public CfgGatewayPagamento getCfgGatewayPagamentoById(Long id);
	
	public void updateStatoCfgGatewayPagamento(String[] idcfgGatewayPagamento, String stato);
	
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoByFilter(ContainerDTO containerDTO);
	
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoDistinctSystemName();

	public List<String> getCfGatewayPagamentoDistinctSysId();

	public List<String> getCfGatewayPagamentoDistinctApplicationId();
	
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoTributiEnteAllFornitore(String idEnte, String cdTrbEnte);
		
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoTributiEnteSpecificGtw(String idEnte, String cdTrbEnte);

	public List<Long> getIdCfgGatewayPagamentoTributiEnteAllFornitore(String idEnte,String cdTrbEnte);

	public List<Long> getIdCfgGatewayPagamentoTributiEnteSpecificGtw(String idEnte,String cdTrbEnte);
		
	public List<CfgTributiEntiGateway> getListaCfgGatewayPagamentoTributiEnteFornitore(String idEnte, String cdTrbEnte);

	public List<Object[]> readModalitaPagamentoPsp(String idEnte);

	public List<Object[]> readModalitaPagamentoTipoVersamento(String sys, String sub); 
	
	public CfgGatewayPagamento getCfgGatewayPagamentoNdpByDatiRT(String tipoIdentificativoPSP, String codiceIdentificativoUnivocoPSP);

	public CfgGatewayPagamento getCfgBySystemId(String systemId);
	
	
}
