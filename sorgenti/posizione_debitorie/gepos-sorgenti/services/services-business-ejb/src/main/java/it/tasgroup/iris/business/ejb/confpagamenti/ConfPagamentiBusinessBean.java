package it.tasgroup.iris.business.ejb.confpagamenti;


import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessRemote;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.CfgEntiTema;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgModalitaPagamento;
import it.tasgroup.iris.domain.CfgStrumentoPagamento;
import it.tasgroup.iris.domain.CfgTributiEntiGateway;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllineamentiElettroniciArchiviDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgEntiLogoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgEntiTemaDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgFornitoreGatewayDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgModalitaPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgStrumentoPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.ContoTecnicoDAO;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

@Stateless(name = "ConfPagamentiBusiness")
public class ConfPagamentiBusinessBean implements ConfPagamentiBusinessLocal, ConfPagamentiBusinessRemote {

	private static final Logger LOGGER = LogManager.getLogger(ConfPagamentiBusinessBean.class);

	@EJB(name = "ContoTecnicoDaoImpl")
	ContoTecnicoDAO contoTecnicoDAO;

	@EJB(name = "AllineamentiElettroniciArchiviDaoService")
	AllineamentiElettroniciArchiviDao aeaDao;

	@EJB(name = "CfgGatewayPagamentoDao")
	CfgGatewayPagamentoDao cfgGatewayPagamentoDao;

	@EJB(name = "CfgGatewayPagamentoDaoService")
	CfgGatewayPagamentoDao cfgGatewayDao;

	@EJB(name = "CfgModalitaPagamentoDaoService")
	CfgModalitaPagamentoDao cfgModalitaDao;

	@EJB(name = "CfgStrumentoPagamentoDao")
	CfgStrumentoPagamentoDao cfgStrumentoDao;

	@EJB(name = "CfgFornitoreGatewayDao")
	CfgFornitoreGatewayDao cfgFornitoreGatewayDao;

	@EJB(name = "CfgEntiLogoDao")
	CfgEntiLogoDao cfgEntiLogoDao;

	@EJB(name = "CfgEntiTemaDao")
	CfgEntiTemaDao cfgEntiTemaDao;


	@Override
	public List<CfgModalitaPagamento> getListaModalitaPagamento() {
		return cfgModalitaDao.getListaModalitaPagamentoAttive();
	}
	
	@Override
	public List<CfgModalitaPagamento> getListaAllModalitaPagamento() {
		return cfgModalitaDao.getListaAllModalitaPagamento();
	}
	
	@Override
	public List<CfgModalitaPagamento> getListaCfgModalitaInActiveCfgFornitore(String circuito) {
		return cfgModalitaDao.getListaCfgModalitaInActiveCfgFornitore(circuito);
	}


	/**
	 *
	 * @param deleghe
	 * @param lista
	 */
	private void setEnabled(List<AllineamentiElettroniciArchivi> deleghe, List<CfgGatewayPagamento> lista, String intestatario) {

		 {
			for (CfgGatewayPagamento cfg : lista) {

				if (deleghe.isEmpty() && cfg.getCfgModalitaPagamento().getId() == Long.parseLong(EnumTipoModalitaPagamento.RIDONLINE.getChiave())
						|| intestatario.equals("ANONYMOUS") && (cfg.getCfgModalitaPagamento().getId() == Long.parseLong(EnumTipoModalitaPagamento.BOLLETTINOFRECCIA.getChiave()) || cfg.getCfgModalitaPagamento().getId() == Long.parseLong(EnumTipoModalitaPagamento.FESPPI.getChiave())))

						cfg.getCfgModalitaPagamento().setEnabled(false);

			}
		}
	}


	@Override
	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String intestatario, String stato) throws Exception {

		List<CfgGatewayPagamento> lista = cfgGatewayDao.getListCfgByStato(stato);

		if (intestatario != null){
			List<AllineamentiElettroniciArchivi> deleghe = aeaDao.listAEAAccettateByIntestatario(intestatario);
			setEnabled(deleghe, lista, intestatario);
		}

		return lista;
	}

	@Override
	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String intestatario, String stato, String sysId, String appId) throws Exception {

		List<CfgGatewayPagamento> lista = cfgGatewayDao.getListCfgByStatoAndSysIdAndAppId(stato, sysId, appId);

		if (intestatario != null){
			List<AllineamentiElettroniciArchivi> deleghe = aeaDao.listAEAAccettateByIntestatario(intestatario);
			setEnabled(deleghe, lista,intestatario);
		}

		return lista;
	}



	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoAll() {
		return (List<CfgGatewayPagamento>) cfgGatewayPagamentoDao.getCfgGatewayPagamentoAll();

	}

	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoByFilter(ContainerDTO dto) {
		try {
			return (List<CfgGatewayPagamento>) cfgGatewayPagamentoDao.getCfgGatewayPagamentoByFilter(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public CfgGatewayPagamento getCfgGatewayPagamentoById(Long id) {
		return (CfgGatewayPagamento) cfgGatewayPagamentoDao.getCfgGatewayPagamentoById(id);
	}
	
	@Override
	public CfgGatewayPagamento getCfgGatewayPagamentoById(String bundleKey) {
		return (CfgGatewayPagamento) cfgGatewayPagamentoDao.getCfgByBundleKey(bundleKey);
	}

	// TODO
	@Override
	public void updateStatoListCfgGatewayPagamento(String[] idcfggatewaypagamento, String stato) throws Exception {
		cfgGatewayPagamentoDao.updateStatoCfgGatewayPagamento(idcfggatewaypagamento, stato);
	}

	@Override
	public List<CfgStrumentoPagamento> getCfgStrumentoPagamentoAll(){
		return (List<CfgStrumentoPagamento>) cfgStrumentoDao.getCfgStrumentoPagamentoAll();
	}

	@Override
	public CfgStrumentoPagamento getCfgStrumentoPagamentoById(Long id){
		return (CfgStrumentoPagamento) cfgStrumentoDao.getCfgStrumentoPagamentoById(id);
	}

	@Override
	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String stato) throws Exception {
		return getListaConfigurazioniPagamenti(null, stato);
	}

	@Override
	public List<CfgGatewayPagamento> getListaConfigurazioniPagamenti(String stato, String sysId, String appId) throws Exception {
		return getListaConfigurazioniPagamenti(null, stato, sysId, appId);
	}

	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoListDistinctSystemName()
			throws Exception {
		return (List<CfgGatewayPagamento>) cfgGatewayPagamentoDao.getCfgGatewayPagamentoDistinctSystemName();
	}

	@Override
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayAll()
			throws Exception {
		return (List<CfgFornitoreGateway>) cfgFornitoreGatewayDao.getCfgFornitoreGatewayAll();
	}
		
	@Override public List<CfgFornitoreGateway> getCfgFornitoreGatewayBoe(String idEnteCreditore) {
			return cfgFornitoreGatewayDao.getCfgFornitoreGatewayBoe(idEnteCreditore);
	}
		
		@Override
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayDistinctId() {
		return (List<CfgFornitoreGateway>) cfgFornitoreGatewayDao.getCfgFornitoreGatewayDistinctId();
	}

	@Override
	public CfgGatewayPagamento getCfgGatewayPagamento(String bundleKey) {

		return (CfgGatewayPagamento) cfgGatewayPagamentoDao.getCfgByBundleKey(bundleKey);
	}

	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoEnabledByTributiEnti(String idEnte, String cdTrbEnte) {
		// recupero i gateway di pagamento compatibili per ente, tributo (abilitazione per fornitore)
		List<CfgGatewayPagamento> list =  cfgGatewayPagamentoDao.getCfgGatewayPagamentoTributiEnteAllFornitore(idEnte, cdTrbEnte);

		// recupero i gateway di pagamento compatibili per ente, tributo (abilitazione granulare per gateway pagamento)
		List<CfgGatewayPagamento> list2 = cfgGatewayPagamentoDao.getCfgGatewayPagamentoTributiEnteSpecificGtw(idEnte, cdTrbEnte);

		list.addAll(list2);
		return list;
	}

	@Override
	public List<Long> getIdCfgGatewayPagamentoEnabledByTributiEnti(String idEnte, String cdTrbEnte) {
		// recupero i gateway di pagamento compatibili per ente, tributo (abilitazione per fornitore)
		List<Long> list =  cfgGatewayPagamentoDao.getIdCfgGatewayPagamentoTributiEnteAllFornitore(idEnte, cdTrbEnte);

		// recupero i gateway di pagamento compatibili per ente, tributo (abilitazione granulare per gateway pagamento)
		List<Long> list2 = cfgGatewayPagamentoDao.getIdCfgGatewayPagamentoTributiEnteSpecificGtw(idEnte, cdTrbEnte);

		list.addAll(list2);
		return list;
	}

	@Override
	public CfgEntiLogo getCfgEntiLogoById(String id) {
		return (CfgEntiLogo) cfgEntiLogoDao.retrieveById(id);
	}

	@Override
	public CfgEntiLogo createCfgEntiLogo(CfgEntiLogo entiLogo) {
		return (CfgEntiLogo) cfgEntiLogoDao.createCfgEntiLogo(entiLogo);
	}

	@Override
	public CfgEntiLogo updateCfgEntiLogo(CfgEntiLogo entiLogo) {
		return (CfgEntiLogo) cfgEntiLogoDao.updateCfgEntiLogo(entiLogo);
	}

	@Override
	public CfgEntiTema getCfgEntiTemaById(String cdEnte) {
		return cfgEntiTemaDao.retrieveById(cdEnte);
	}

	@Override
	public CfgEntiTema getCfgEntiTemaLiteById(String cdEnte) {
		return cfgEntiTemaDao.retrieveLiteById(cdEnte);
	}

	@Override
	public CfgEntiTema createCfgEntiTema(CfgEntiTema entiTema) {
		return cfgEntiTemaDao.createCfgEntiTema(entiTema);
	}

	@Override
	public CfgEntiTema updateCfgEntiTema(CfgEntiTema entiTema) {
		return  cfgEntiTemaDao.updateCfgEntiTema(entiTema);
	}

	@Override
	public Date getCfgEntiTemLastModified(String cdEnte) {
		return cfgEntiTemaDao.retrieveLastModified(cdEnte);
	}
	
	
	@Override
	public List<CfgTributiEntiGateway> getListaCfgGatewayPagamentoTributiEnteFornitore(String idEnte, String cdTrbEnte) {
		return  cfgGatewayPagamentoDao.getListaCfgGatewayPagamentoTributiEnteFornitore(idEnte, cdTrbEnte);

		
	}
	
	@Override
	public List<Object[]> readModalitaPagamentoPsp(String idEnte) {
		return cfgGatewayPagamentoDao.readModalitaPagamentoPsp(idEnte);
	}
	
	@Override
	public List<Object[]> readModalitaPagamentoTipoVersamento(String sys, String sub) {
		return cfgGatewayPagamentoDao.readModalitaPagamentoTipoVersamento(sys,sub);
	}

	@Override
	public CfgGatewayPagamento readCfgGatewayPagamentoDettaglioBySystemId(String systemId) {
		return cfgGatewayPagamentoDao.getCfgBySystemId(systemId);
		
	}
	
	@Override
	public List<CfgFornitoreGateway> readCfgFornitoreGatewayCircuito(String idEnte) throws Exception {
		return (List<CfgFornitoreGateway>) cfgFornitoreGatewayDao.readCfgFornitoreGatewayCircuito(idEnte);
	}

}
