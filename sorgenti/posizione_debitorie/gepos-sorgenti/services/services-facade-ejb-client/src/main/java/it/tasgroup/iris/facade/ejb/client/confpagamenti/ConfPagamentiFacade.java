/**
 *
 */
package it.tasgroup.iris.facade.ejb.client.confpagamenti;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiLogoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgFornitoreGatewayDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgModalitaPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgStrumentoPagamentoDTO;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;


/**readCfgFornitoreGatewayAttivoDTO
 * @author PazziK
 */
public interface ConfPagamentiFacade {

    public List<CfgModalitaPagamentoDTO> readModalitaPagamentoList();
    
    public List<CfgModalitaPagamentoDTO> readModalitaPagamentoInActiveCfgFornitore(String circuito);
    
    public List<CfgModalitaPagamentoDTO> readModalitaPagamentoAttiveList();

    public List<CfgGatewayPagamentoDTO> readCfgGatewayPagamentoList();
        
    public CfgGatewayPagamentoDTO readCfgGatewayPagamentoDettaglioBySystemId(String systemId);

    public CfgGatewayPagamentoDTO readCfgGatewayPagamentoDettaglio(Long id);

    public CfgGatewayPagamentoDTO readCfgGatewayPagamentoDettaglio(String bundelekey);

    public void updateStatoListCfgGatewayPagamento(String[] idcfggatewaypagamento, String stato) throws Exception;

    public List<CfgGatewayPagamentoDTO> getListaConfigurazioniPagamenti(String intestatario, String stato) throws Exception;

    public List<CfgGatewayPagamentoDTO> getListaConfigurazioniPagamenti(String intestatario, String stato, String sysId, String appId) throws Exception;

    public List<CfgGatewayPagamentoDTO> getConfigurazionePagamentiCommissioniCalcolate(String intestatario, String stato, BigDecimal importoTotalePagamenti) throws Exception;

    public List<CfgGatewayPagamentoDTO> getConfigurazionePagamentiCommissioniCalcolate(String intestatario, String stato, String sysId, String appId, BigDecimal importoTotalePagamenti) throws Exception;

    public List<CfgStrumentoPagamentoDTO> readCfgStrumentoPagamentoList();

    public CfgStrumentoPagamentoDTO readCfgStrumentoPagamentoDettaglio(Long id);

    public ContainerDTO readCfgGatewayPagamentoListByFilter(ContainerDTO dto);

    public List<CfgGatewayPagamentoDTO> readCfgGatewayPagamentoListDistinctSystemName() throws Exception;

    public List<CfgFornitoreGatewayDTO> readCfgFornitoreGatewayDTO() throws Exception;
    
    public List<CfgFornitoreGatewayDTO> getCfgGatewayPagamentoBOE(String idEnte) throws Exception;
    
    public List<CfgFornitoreGatewayDTO> getCfgFornitoreGatewayDistinctId();

    public List<CfgGatewayPagamentoDTO> getCfgGatewayPagamentoEnabledByTributiEnti(String idEnte, String cdTrbEnte) throws Exception;

    public List<TributoEnteDTO> getTributiEntiIbanCCpNull() throws Exception;

    public Collection<CfgGatewayPagamentoDTO> filtraConfigurazioniVisualizzate(Collection<CfgGatewayPagamentoDTO> cgfPagamentiTutti,                                                                                      List<SessionShoppingCartItemDTO> pagamentiWIP, boolean isAnonymous, boolean offLinePaymentsEnabled) throws Exception;

	public CfgEntiLogoDTO getCfgEntiLogoById(String id);
	
	public CfgEntiLogoDTO getCfgEntiLogoByIdIntestatario(String intestatario);

	public CfgEntiLogoDTO createCfgEntiLogo(CfgEntiLogoDTO entiLogoDTO);

	public CfgEntiLogoDTO updateCfgEntiLogo(CfgEntiLogoDTO entiLogoDTO);

	public Collection<CfgGatewayPagamentoDTO> readModalitaPagamentoPsp(String idEnte);

	public Collection<CfgGatewayPagamentoDTO> readModalitaPagamentoTipoVersamento(String systemId, String subsystemId);

	public List<CfgFornitoreGatewayDTO> readCfgFornitoreGatewayCircuitoDTO(String idEnte) throws Exception;
}
