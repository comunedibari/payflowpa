package it.tasgroup.iris.business.ejb.flussi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.nch.idp.backoffice.tavolooperativo.TavoloOperativoConstants;
import it.nch.profile.IProfileManager;
import it.tasgroup.idp.domain.messaggi.ConfermeCart;
import it.tasgroup.idp.domain.messaggi.ConfermeCartPK;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.iris.business.ejb.client.flussi.MonitoraggioFlussiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.MonitoraggioFlussiBusinessRemote;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.CasellarioDispo;
import it.tasgroup.iris.domain.CasellarioInfo;
import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.domain.EsitiCbill;
import it.tasgroup.iris.domain.EsitiNdp;
import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.domain.IncassiBonificiRh;
import it.tasgroup.iris.domain.MovimentiAccredito;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.Rendicontazioni;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.domain.Riversamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.exception.CheckSospettoGRException;
import it.tasgroup.iris.dto.flussi.CasellarioInfoDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllineamentiElettroniciArchiviDao;
import it.tasgroup.iris.persistence.dao.interfaces.CasellarioDispoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CasellarioInfoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.ConfermeCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.ErroriCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.ErroriIdpDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiBbDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiBonificiRiaccreditoDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiCbillDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiNdpDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiRctDao;
import it.tasgroup.iris.persistence.dao.interfaces.IncassiBonificiRhDao;
import it.tasgroup.iris.persistence.dao.interfaces.MovimentiAccreditoDao;
import it.tasgroup.iris.persistence.dao.interfaces.NotificheCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiOnLineDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzeCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.RendicontazioniDao;
import it.tasgroup.iris.persistence.dao.interfaces.RidDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoConfermeCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoErroriIdpDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoEsitiCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoEsitiPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoNotificheCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoPendenzeCartDao;
import it.tasgroup.services.util.enumeration.EnumStatoAllineaPendenze;
import it.tasgroup.services.util.enumeration.EnumStatoChiusuraRiversamento;
import it.tasgroup.services.util.enumeration.EnumStatoNotifichePagamento;
import it.tasgroup.services.util.enumeration.EnumStatoRiversamento;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;
import it.tasgroup.services.util.idgenerator.IDGenerator;


@Stateless(name = "MonitoraggioFlussiBusiness")
public class MonitoraggioFlussiBusinessBean implements MonitoraggioFlussiBusinessLocal, MonitoraggioFlussiBusinessRemote {

    private static final Logger LOGGER = LogManager.getLogger(MonitoraggioFlussiBusinessBean.class);

    @EJB(name = "CasellarioInfoDaoService")
    private CasellarioInfoDao casellarioInfoDAO;

    @EJB(name = "CasellarioDispoDaoService")
    private CasellarioDispoDao casellarioDispoDAO;

    @EJB(name = "RendicontazioniDaoService")
    private RendicontazioniDao rendicontazioniDAO;

    @EJB(name = "EsitiPendenzaDao")
    private EsitiPendenzaDao esitiPendenzaDAO;
    
    @EJB(name = "StoricoEsitiPendenzaDao")
    private StoricoEsitiPendenzaDao storicoEsitiPendenzaDAO;

    @EJB(name = "EsitiBbDaoService")
    private EsitiBbDao esitiBbDAO;

    @EJB(name = "EsitiCbillDaoService")
    private EsitiCbillDao esitiCbillDAO;

    @EJB(name = "EsitiNdpDaoService")
    private EsitiNdpDao esitiNdpDao;

    @EJB(name = "PagamentiOnLineDaoService")
    private PagamentiOnLineDao pagamentiOnLineDao;

    @EJB(name = "EsitiRctDaoService")
    private EsitiRctDao esitiRctDAO;

    @EJB(name = "EsitiBonificiRiaccreditoService")
    private EsitiBonificiRiaccreditoDao esitiBonificiRiaccreditoDAO;

    @EJB(name = "IncassiBonificiRhbDaoService")
    private IncassiBonificiRhDao incassiBonificiRhDAO;

    @EJB(name = "MovimentiAccreditoDaoService")
    private MovimentiAccreditoDao movimentiAccreditoDAO;

    @EJB(name = "RidDaoService")
    private RidDao ridDAO;

    @EJB(name = "AllineamentiElettroniciArchiviDaoService")
    private AllineamentiElettroniciArchiviDao aeaDAO;

    @EJB(name= "CfgGatewayPagamentoDaoService")
    private CfgGatewayPagamentoDao cfgGatewayPagamentoDao;

	@EJB(name="PendenzeCartDaoService")
	private PendenzeCartDao pendenzeCartDao;
	
	@EJB(name="StoricoPendenzeCartDaoService")
	private StoricoPendenzeCartDao storicoPendenzeCartDao;

	@EJB(name="EsitiCartDaoService")
	private EsitiCartDao esitiCartDao;
	
	@EJB(name="StoricoEsitiCartDaoService")
	private StoricoEsitiCartDao esitiCartDaoStorico;

	@EJB(name="ErroriCartDaoService")
	private ErroriCartDao erroriCartDao;

	@EJB(name="ErroriIdpDaoService")
	private ErroriIdpDao erroriIdpDao;
	
	@EJB(name="StoricoErroriIdpDaoService")
	private StoricoErroriIdpDao erroriIdpDaoStorico;

	@EJB(name="NotificheCartDaoService")
	private NotificheCartDao notificheCartDao;
	
	@EJB(name="StoricoNotificheCartDaoService")
	private StoricoNotificheCartDao storicoNotificheCartDao;

	@EJB(name="ConfermeCartDaoService")
	private ConfermeCartDao confermeCartDao;

	@EJB(name="StoricoConfermeCartDaoService")
	private StoricoConfermeCartDao confermeCartDaoStorico;

    @Override
    public List<Object[]> getListaMittenti() {

        List<Object[]> lista = casellarioInfoDAO.getListMittenti();

        return lista;
    }
    
    @Override
    public List<Object[]> getListaMittenti(String ricevente) {

        List<Object[]> lista = casellarioInfoDAO.getListMittenti(ricevente);

        return lista;
    }

    @Override
    public List<Object[]> getListaRiceventi() {

        List<Object[]> lista = casellarioInfoDAO.getListRiceventi();

        return lista;
    }

    @Override
    public List<CasellarioInfo> getListCasellarioInfoByFilterParameters(ContainerDTO dto) {

        return (List<CasellarioInfo>) casellarioInfoDAO.listCasellarioInfoByFilterParameters(dto);
    }

    @Override
    public List<CasellarioDispo> getListCasellarioDispoByFilterParameters(ContainerDTO dto) {

        return (List<CasellarioDispo>) casellarioDispoDAO.listCasellarioDispoByFilterParameters(dto);
    }


	@Override
	public CasellarioInfoDTO getCasellarioInfoByMovimentoAccreditoId(Long id) {
		MovimentiAccredito movimentiAccredito = movimentiAccreditoDAO.retrieveMovimentoAccreditoById(id);
		CasellarioInfo cinfo = movimentiAccredito.getRendicontazioni().getCasellarioInfo();
		Long progr61 = Long.valueOf(movimentiAccredito.getProgr61());
		Long progr62 = Long.valueOf(movimentiAccredito.getProgr62());
		byte[] flussoCbi = parseFlow(cinfo.getFlussoCbi(), progr61, progr62);
		CasellarioInfoDTO casellarioInfoDto = new CasellarioInfoDTO();
		casellarioInfoDto.setNomeSupporto(cinfo.getNomeSupporto());
		casellarioInfoDto.setFlussoCBI(flussoCbi);
		return casellarioInfoDto;
	}

	private byte[] parseFlow(byte[] dataBytes, Long progr61, Long progr62) {

		String progr61Str = IDGenerator.fillLeftWithDigits(7, progr61,'0');

		String progr62Str = IDGenerator.fillLeftWithDigits(3, progr62,'0');

		String startSubstr = " 62"+progr61Str+progr62Str;

		String progr62PlusOne = IDGenerator.fillLeftWithDigits(3, progr62+1,'0');

		String endSubstr = " 62"+progr61Str+progr62PlusOne;

		String flow = new String(dataBytes);

		int beginIdx = flow.indexOf(startSubstr, 0);

		int endIdx = flow.indexOf(endSubstr, 0);

		String subflow = null;

		if (endIdx < 0){

			endSubstr = "64"+progr61Str;

			endIdx = flow.indexOf(endSubstr, 0);

		}

		subflow = flow.substring(beginIdx, endIdx);

		return subflow.getBytes();
	}

    @Override
    public CasellarioInfo getCasellarioInfoById(Long id) {

        return casellarioInfoDAO.retrieveCasellarioInfoById(id);
    }

    @Override
    public CasellarioDispo getCasellarioDispoById(Long id) {

        return casellarioDispoDAO.retrieveCasellarioDispoById(id);
    }

    @Override
    public Rendicontazioni getRendicontazioniById(Long id) {

        return rendicontazioniDAO.retrieveRendicontazioniById(id);
    }

    @Override
    public List<Rendicontazioni> getRendicontazioniRiversamento(ContainerDTO containerDTO) {

        return rendicontazioniDAO.retrieveRendicontazioniRiversamento(containerDTO);
    }

    @Override
    public List<EsitiBb> getEsitiBbByIdRendicontazione(ContainerDTO containerDTO) {

        return esitiBbDAO.listEsitiBbByIdRendicontazione(containerDTO);
    }

    @Override
    public List<EsitiRct> getEsitiRctByIdRendicontazione(ContainerDTO containerDTO) {

        return esitiRctDAO.listEsitiRctByIdRendicontazione(containerDTO);
    }

    @Override
    public List<EsitiRct> getEsitiRctByIdRendicontazioneAndTipoEsito(ContainerDTO containerDTO) {

        return esitiRctDAO.listEsitiRctByIdRendicontazioneAndTipoEsito(containerDTO);
    }

    @Override
    public List<IncassiBonificiRh> getIncassiBonificiRhByIdRendicontazione(ContainerDTO containerDTO) {

        return incassiBonificiRhDAO.listIncassiBonificiRhByIdRendicontazione(containerDTO);
    }

    @Override
    public List<EsitiBonificiRiaccredito> getEsitiBonificiRiaccreditoByIdRend(ContainerDTO containerDTO) {

        return esitiBonificiRiaccreditoDAO.listEsitiBonificiRiaccreditoByIdRend(containerDTO);
    }

    @Override
    public EsitiBb getEsitiBbById(Long id) {

        return esitiBbDAO.getEsitiBbById(id);
    }

    @Override
    public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoById(Long id) {

        return esitiBonificiRiaccreditoDAO.getEsitiBonificiRiaccreditoById(id);
    }

    @Override
    public EsitiRct getEsitiRctById(Long id) {

        return esitiRctDAO.getEsitiRctById(id);
    }

    @Override
    public IncassiBonificiRh getIncassiBonificiRhById(Long id) {

        return incassiBonificiRhDAO.getIncassiBonificiRhById(id);
    }

    @Override
    public IncassiBonificiRh saveIncassi(Long id, String note, EnumStatoChiusuraRiversamento statoChiusura) {

    	IncassiBonificiRh inc = incassiBonificiRhDAO.getIncassiBonificiRhById(id);

    	inc.getRiversamento().setNote(note);

    	inc.getRiversamento().setFlagChiusura(statoChiusura.getValue());

    	incassiBonificiRhDAO.updateIncassi(inc);

    	return inc;
    }

    @Override
    public List<Rid> getEsitiRidByIdRendicontazioneAndCausaleNotEquals(ContainerDTO containerDTO, String causale) {

        return ridDAO.listEsitiRidByRendicontazioneAndCausale(containerDTO, causale);
    }

    @Override
    public Rid getRidById(Long id) {

        return ridDAO.getRidById(id);
    }

    @Override
    public List<AllineamentiElettroniciArchivi> getEsitiAEAByIdRendicontazioneAndCausaleNotIn(ContainerDTO containerDTO) {

        return aeaDAO.listEsitiAeaByRendicontazioneAndCausale(containerDTO);
    }

    @Override
    public AllineamentiElettroniciArchivi getAEAById(Long id) {

        return aeaDAO.getAEAById(id);
    }

    @Override
    public List<MovimentiAccredito> getMovimentiDaGiornaleDiCassa(ContainerDTO containerDTO) {

    	Long idRendicontazione = (Long)containerDTO.getInputDTOList().get(0);
    	return movimentiAccreditoDAO.retrieveMovimentoAccreditoByIdRendicontazione(idRendicontazione);
    }

    @Override
    public List<EsitiCbill> getEsitiCbillByIdRendicontazione(ContainerDTO containerDTO) {
        return esitiCbillDAO.listEsitiCbillByIdRendicontazione(containerDTO);
    }

    @Override
    public EsitiCbill getEsitiCbillById(Long id) {

        return esitiCbillDAO.getEsitiCbillById(id);
    }

    @Override
    public List<EsitiNdp> getEsitiNdpByIdRendicontazione(ContainerDTO containerDTO) {
        return esitiNdpDao.listEsitiNdpByIdRendicontazione(containerDTO);
    }

    @Override
    public EsitiNdp getEsitiNdpById(Long id) {

        return esitiNdpDao.getEsitiNdpById(id);
    }

    @Override
    public List<Object[]> readSystemIdsList() {

    	List<String> l= cfgGatewayPagamentoDao.getCfGatewayPagamentoDistinctSysId();

        List<Object[]> lista = new ArrayList<Object[]>();

        Iterator<String> iter= l.iterator();
        while (iter.hasNext()) {
        	String h =iter.next();
        	Object[] obj = new Object[2];
        	obj[0]=h;
        	obj[1]=h;
        	lista.add(obj);
        }

        return lista;
    }

    @Override
    public List<Object[]> readApplicationIdsList() {

        List<String> l = cfgGatewayPagamentoDao.getCfGatewayPagamentoDistinctApplicationId();

        List<Object[]> lista = new ArrayList<Object[]>();

        Iterator<String> iter= l.iterator();
        while (iter.hasNext()) {
        	String h =iter.next();
        	Object[] obj = new Object[2];
        	obj[0]=h;
        	obj[1]=h;
        	lista.add(obj);
        }
        return lista;
    }

    @Override
    public List<Object[]> readTiOperationsList() {

        List<Object[]> lista = pagamentiOnLineDao.readTiOperationsList();

        return lista;
    }

    @Override
    public List<Object[]> readListaOperazioniEsitiByFilters(ContainerDTO containerDTO) {
        return pagamentiOnLineDao.listaOperazioniEsitiByFilters(containerDTO);
    }

    @Override
    public List<PagamentiOnline> readListaDettaglioOperazioniEsitiByFilters(ContainerDTO containerDTO) {
        return pagamentiOnLineDao.listaDettaglioOperazioniEsitiByFilters(containerDTO);
    }

    @Override
    public  List<IncassiBonificiRh> getListBFLNonRiconciliati(ContainerDTO dto){

    	return incassiBonificiRhDAO.getBFLNonRiconciliati(dto);
    }

    @Override
    public  List<MovimentiAccredito> getListNDPNonRiconciliati(ContainerDTO dto){

    	return movimentiAccreditoDAO.getNDPNonRiconciliati(dto);
    }

    @Override
    public void riversa(IProfileManager profile, String selectedIBAN, String[] daRiversare, boolean isGiaRiversato) throws CheckSospettoGRException{

    	for(String id : daRiversare) {

    		IncassiBonificiRh inc = incassiBonificiRhDAO.getIncassiBonificiRhById(Long.valueOf(id));

    		Object[] parameters= new Object[1];

    		parameters[0] = id;

    		if (EnumTipoAnomaliaIncasso.SOSPETTO_GR.name().equals(inc.getAnomalia()) && daRiversare.length>1)
    			throw new CheckSospettoGRException(parameters);

    		Riversamento riv = new Riversamento();

    		if (isGiaRiversato){

    			riv.setStato(EnumStatoRiversamento.GIA_RIVERSATO.name());
    			riv.setFlagChiusura(EnumStatoChiusuraRiversamento.CHIUSA.getValue());

    		} else {

    			String[] selected = selectedIBAN.split(":");

    			riv.setCdTrbEnte(selected[1]);
    			riv.setIban(selected[2]);
    			riv.setStato(EnumStatoRiversamento.DA_RIVERSARE.name());
    			riv.setFlagChiusura(EnumStatoChiusuraRiversamento.APERTA.getValue());
    		}

    		riv.setOpInserimento(profile.getAzienda());

    		riv.setTsInserimento(new Timestamp(System.currentTimeMillis()));

        	inc.setRiversamento(riv);

        	riv.setIncassiBonificiRh(inc);

        	incassiBonificiRhDAO.updateIncassi(inc);
    	}

    }

	@Override
	public List<EsitiNdp> getEsitiNdpByIdFlusso(String idRiconciliazione) {

		return esitiNdpDao.getEsitiNdpByIdFlusso(idRiconciliazione);
	}

	@Override
	public MovimentiAccredito riconciliaMovimentoAccredito(EnumTipoAccredito tipoAccredito, String idRiconciliazione, Long idMov) {

		MovimentiAccredito mov = movimentiAccreditoDAO.retrieveMovimentoAccreditoById(idMov);

		mov.setIdRiconciliazione(idRiconciliazione);

		mov.setFlagRiconciliazione((short)0);

		mov.setNumElaborazioni((short)0);

		mov.setCodAnomalia(EnumTipoAnomaliaNDP.I_ABBINAMENTO_MANUALE.getChiave());

		if (EnumTipoAccredito.NON_DEFINITO.getChiave().equals(mov.getTipoAccredito()))

			mov.setTipoAccredito(tipoAccredito.getChiave());

		MovimentiAccredito updated = movimentiAccreditoDAO.updateMovimentoAccredito(mov);

		return updated;
	}

	@Override
	public MovimentiAccredito riconciliaMovimentoAccredito(Long idMov) {

		MovimentiAccredito mov = movimentiAccreditoDAO.retrieveMovimentoAccreditoById(idMov);

		if (mov != null) {
			mov.setFlagRiconciliazione((short) 3);

			mov.setNumElaborazioni((short) (mov.getNumElaborazioni() - 1));

			mov.setCodAnomalia(EnumTipoAnomaliaNDP.I_ABBINAMENTO_MANUALE.getChiave());

			mov = movimentiAccreditoDAO.updateMovimentoAccredito(mov);
		}

		return mov;
	}

	@Override
	public EsitiNdp riconciliaEsitiNDP(String codTransazione, Long id) {

		EsitiNdp esito = esitiNdpDao.retrieveEsitiNdpById(id);

		esito.setIdRiconciliazione(codTransazione);

		esito.setFlagRiconciliazione(0);

		esito.setCodAnomalia(EnumTipoAnomaliaNDP.I_ABBINAMENTO_MANUALE.getChiave());

		EsitiNdp updated = esitiNdpDao.updateEsitiNdp(esito);

		return updated;
	}

	public MovimentiAccredito readMovimentoAccredito (Long idMov) {
		MovimentiAccredito mov = movimentiAccreditoDAO.retrieveMovimentoAccreditoById(idMov);
		return mov;
	}

	@Override
	public List<Object[]> groupByDescrizioneEsitoPendenza(ContainerDTO dtoIn){

		return esitiPendenzaDAO.groupByDescrizioneEsito(dtoIn);

	}

	/**
	 * Metodi per flussi Allineamento Pendenze / Notifiche pagamento (Tavolo Operativo)
	 */


	@Override
	public List<PendenzeCart> getListaMessaggiAllineamentoPendenzeCaricati(ContainerDTO dtoIn) {
		FilterVO filter = (FilterVO)dtoIn.getInputDTO();
		return pendenzeCartDao.getByFilterParametersPaginated(filter, dtoIn.getPagingCriteria(),dtoIn.getPagingData());
	}
	
	@Override
	public List<PendenzeCart> getListaMessaggiAllineamentoPendenzeCaricatiStorico(ContainerDTO dtoIn) {
		FilterVO filter = (FilterVO)dtoIn.getInputDTO();
		return storicoPendenzeCartDao.getByFilterParametersPaginated(filter, dtoIn.getPagingCriteria(),dtoIn.getPagingData());
	}

	@Override
	public PendenzeCart getMessaggioAllineamentoPendenzeByKey(String e2emsgid,
			String senderid, String sendersys) {
		PendenzeCartPK pk = new PendenzeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		PendenzeCart p;
		try {
			p = pendenzeCartDao.getById(PendenzeCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return p;
	}
	
	@Override
	public PendenzeCart getMessaggioAllineamentoPendenzeByKeyStorico(String e2emsgid,
			String senderid, String sendersys) {
		PendenzeCartPK pk = new PendenzeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		PendenzeCart p;
		try {
			p = storicoPendenzeCartDao.getById(PendenzeCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return p;
	}
	
	@Override
	public EsitiCart getMessaggioEsitoAllineamentoPendenzeByKey(
			String e2emsgid, String senderid, String sendersys) {
		return getMessaggioEsitoAllineamentoPendenzeByKey(e2emsgid, senderid, sendersys, false);
	}

	@Override
	public EsitiCart getMessaggioEsitoAllineamentoPendenzeByKey(
			String e2emsgid, String senderid, String sendersys, boolean isStorico) {

		EsitiCartPK pk = new EsitiCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		EsitiCart ec;
		try {
			if (isStorico)
				ec = esitiCartDaoStorico.getById(EsitiCart.class, pk);
			else
				ec = esitiCartDao.getById(EsitiCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return ec;

	}
	
	@Override
	public EsitiCart getMessaggioEsitoAllineamentoPendenzeByKeyStorico(
			String e2emsgid, String senderid, String sendersys) {

		return getMessaggioEsitoAllineamentoPendenzeByKey(e2emsgid, senderid, sendersys, true);

	}

	@Override
	public Long countPendenzeElaborate(String e2emsgid, String senderid, String sendersys, boolean isStorico) {
		Long res;
		if (isStorico)
			res = storicoEsitiPendenzaDAO.countPendenzeCaricate(e2emsgid, senderid, sendersys);
		else
			res = esitiPendenzaDAO.countPendenzeCaricate(e2emsgid, senderid, sendersys);
		return res;
	}

	@Override
	public Long countPendenzeElaborate(String e2emsgid, String senderid, String sendersys) {
		return countPendenzeElaborate(e2emsgid, senderid, sendersys, false);
	}
	
	@Override
	public Long countPendenzeElaborateStorico(String e2emsgid, String senderid, String sendersys) {
		return countPendenzeElaborate(e2emsgid, senderid, sendersys, true);
	}

	@Override
	public Long countPendenze(String e2emsgid, String senderid, String sendersys) {
		return countPendenze(e2emsgid, senderid, sendersys, false);
	}
	
	@Override
	public Long countPendenzeStorico(String e2emsgid, String senderid, String sendersys) {
		return countPendenze(e2emsgid, senderid, sendersys, true);
	}

	@Override
	public Long countPendenze(String e2emsgid, String senderid, String sendersys, boolean isStorico) {
		Long res;
		if (isStorico)
			res = storicoEsitiPendenzaDAO.countPendenze(e2emsgid, senderid, sendersys);
		else
			res = esitiPendenzaDAO.countPendenze(e2emsgid, senderid, sendersys);
		return res;
	}

	@Override
	public List<EsitiPendenza> listPendenzeErrate(String e2emsgid,
			String senderid, String sendersys) {
		return esitiPendenzaDAO.listPendenzeErrate(e2emsgid, senderid, sendersys);
	}

	@Override
	public List<EsitiPendenza> listPendenzeErrateStorico(String e2emsgid,
			String senderid, String sendersys) {
		return storicoEsitiPendenzaDAO.listPendenzeErrate(e2emsgid, senderid, sendersys);
	}

	@Override
	public byte[] downloadMessaggioAllineamentoPendenze(String e2emsgid, String senderid, String sendersys) {

		byte[] result = null;

		PendenzeCartPK pk = new PendenzeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		PendenzeCart p;
		try {
			p = pendenzeCartDao.getById(PendenzeCart.class, pk);
			result = p.getMessaggioXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;

	}
	
	@Override
	public byte[] downloadMessaggioAllineamentoPendenzeStorico(String e2emsgid, String senderid, String sendersys) {

		byte[] result = null;

		PendenzeCartPK pk = new PendenzeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		PendenzeCart p;
		try {
			p = storicoPendenzeCartDao.getById(PendenzeCart.class, pk);
			result = p.getMessaggioXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;

	}

	@Override
	public byte[] downloadMessaggioEsitoAllineamentoPendenze(String e2emsgid, String senderid, String sendersys) {

		byte[] result = null;

		EsitiCartPK pk = new EsitiCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		EsitiCart ec;
		try {
			ec = esitiCartDao.getById(EsitiCart.class, pk);
			result=ec.getEsitoXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;

	}
	
	@Override
	public byte[] downloadMessaggioEsitoAllineamentoPendenzeStorico(String e2emsgid, String senderid, String sendersys) {

		byte[] result = null;

		EsitiCartPK pk = new EsitiCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		EsitiCart ec;
		try {
			ec = esitiCartDaoStorico.getById(EsitiCart.class, pk);
			result=ec.getEsitoXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;

	}

	@Override
	public List<ErroriCart> getListaMessaggiAllineamentoPendenzeScartati(
			ContainerDTO dtoIn) {
		FilterVO filter = (FilterVO)dtoIn.getInputDTO();
		return erroriCartDao.getByFilterParametersPaginated(filter, dtoIn.getPagingCriteria(),dtoIn.getPagingData());
	}

	@Override
	public byte[] downloadMessaggioAllineamentoPendenzeScartato(
			String idMessaggio) {

		byte[] result = null;

		ErroriCart ec;
		try {
			ec = erroriCartDao.getById(ErroriCart.class, idMessaggio);
			result=ec.getMessaggioXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;
	}

	@Override
	public List<ErroriIdp> listEsitiScartati(String e2emsgid,
			String senderid, String sendersys) {
		FilterVO fvo = new FilterVO();
		fvo.setE2emsgid(e2emsgid);
		fvo.setSenderId(senderid);
		fvo.setSenderSys(sendersys);
		String tipoMessaggio = TavoloOperativoConstants.TIPO_ERRORE_IDP_MESSAGGI;

		return erroriIdpDao.getByFilterParametersPaginated(fvo, tipoMessaggio, null, null );
	}
	
	@Override
	public List<ErroriIdp> listEsitiScartatiStorico(String e2emsgid,
			String senderid, String sendersys) {
		FilterVO fvo = new FilterVO();
		fvo.setE2emsgid(e2emsgid);
		fvo.setSenderId(senderid);
		fvo.setSenderSys(sendersys);
		String tipoMessaggio = TavoloOperativoConstants.TIPO_ERRORE_IDP_MESSAGGI;

		return erroriIdpDaoStorico.getByFilterParametersPaginated(fvo, tipoMessaggio, null, null );
	}

	@Override
	public List<ErroriIdp> listEsitiScartatiPaginated(ContainerDTO dtoIn) {
		FilterVO filter = (FilterVO)dtoIn.getInputDTO();
		String tipoMessaggio = TavoloOperativoConstants.TIPO_ERRORE_IDP_NOTIFICHE;
		return erroriIdpDao.getByFilterParametersPaginated(filter, tipoMessaggio, dtoIn.getPagingCriteria(),dtoIn.getPagingData());
	}

	@Override
	public byte[] downloadMessaggioNotificaScartato(String e2emsgid) {

		byte[] result = null;

		ErroriIdp eidp = erroriIdpDao.getNotificaScartataByE2eMsgId(e2emsgid);
		result =  eidp.getEsitoXml();

		return result;
	}

	@Override
	public List<NotificheCart> getListaMessaggiNotificaCaricati(
			ContainerDTO dtoIn) {
		FilterVO filter = (FilterVO)dtoIn.getInputDTO();
		List<NotificheCart> notificheCarts = notificheCartDao.getByFilterParametersPaginated(filter, dtoIn.getPagingCriteria(), dtoIn.getPagingData());
		return notificheCarts;
	}
	
	@Override
	public List<NotificheCart> getListaMessaggiNotificaCaricatiStorico(
			ContainerDTO dtoIn) {
		FilterVO filter = (FilterVO)dtoIn.getInputDTO();
		List<NotificheCart> notificheCarts = storicoNotificheCartDao.getByFilterParametersPaginated(filter, dtoIn.getPagingCriteria(), dtoIn.getPagingData());
		return notificheCarts;
	}

	@Override
	public NotificheCart getMessaggioNotificaByKey(String e2emsgid, String receiverid, String receiversys) {

		NotificheCartPK pk = new NotificheCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setReceiverid(receiverid);
		pk.setReceiversys(receiversys);

		NotificheCart nc;
		try {
			nc = notificheCartDao.getById(NotificheCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return nc;
	}
	
	@Override
	public NotificheCart getMessaggioNotificaByKeyStorico(String e2emsgid, String receiverid, String receiversys) {

		NotificheCartPK pk = new NotificheCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setReceiverid(receiverid);
		pk.setReceiversys(receiversys);

		NotificheCart nc;
		try {
			nc = storicoNotificheCartDao.getById(NotificheCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return nc;
	}

	@Override
	public ConfermeCart getMessaggioConfermaNotificaByKey(String e2emsgid,
			String receiverid, String receiversys) {

		ConfermeCartPK pk = new ConfermeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(receiverid);
		pk.setSendersys(receiversys);

		ConfermeCart cc;
		try {
			cc = confermeCartDao.getById(ConfermeCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return cc;

	}
	
	@Override
	public ConfermeCart getMessaggioConfermaNotificaByKeyStorico(String e2emsgid,
			String receiverid, String receiversys) {

		ConfermeCartPK pk = new ConfermeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(receiverid);
		pk.setSendersys(receiversys);

		ConfermeCart cc;
		try {
			cc = confermeCartDaoStorico.getById(ConfermeCart.class, pk);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return cc;

	}

	@Override
	public byte[] downloadMessaggioNotificaPagamento(String e2emsgid, String receiverid, String receiversys) {

		byte[] result = null;

		NotificheCartPK  pk = new NotificheCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setReceiverid(receiverid);
		pk.setReceiversys(receiversys);

		NotificheCart nc;
		try {
			nc = notificheCartDao.getById(NotificheCart.class, pk);
			result = nc.getNotificaXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;
	}
	
	@Override
	public byte[] downloadMessaggioNotificaPagamentoStorico(String e2emsgid, String receiverid, String receiversys) {

		byte[] result = null;

		NotificheCartPK  pk = new NotificheCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setReceiverid(receiverid);
		pk.setReceiversys(receiversys);

		NotificheCart nc;
		try {
			nc = storicoNotificheCartDao.getById(NotificheCart.class, pk);
			result = nc.getNotificaXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;
	}

	@Override
	public byte[] downloadMessaggioConfermaNotificaPagamento(String e2emsgid, String senderid, String sendersys) {

		byte[] result = null;

		ConfermeCartPK pk = new ConfermeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		ConfermeCart cc;
		try {
			cc = confermeCartDao.getById(ConfermeCart.class, pk);
			result=cc.getMessaggioXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;
	}
	
	@Override
	public byte[] downloadMessaggioConfermaNotificaPagamentoStorico(String e2emsgid, String senderid, String sendersys) {

		byte[] result = null;

		ConfermeCartPK pk = new ConfermeCartPK();
		pk.setE2emsgid(e2emsgid);
		pk.setSenderid(senderid);
		pk.setSendersys(sendersys);

		ConfermeCart cc;
		try {
			cc = confermeCartDaoStorico.getById(ConfermeCart.class, pk);
			result=cc.getMessaggioXml();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}

		return result;
	}
	
	public void updateMessageNotSent(String[] messaggi) {
		updateMessageNotSent(messaggi, false );
	}
	
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateMessageNotSent(String[] messaggi, boolean isStorico) {
		List<PendenzeCartPK> listPendenzePK = new ArrayList<PendenzeCartPK>();
		List<EsitiCartPK> listEsitiCartPK = new ArrayList<EsitiCartPK>();
		List<PendenzeCart> listaPendenze = new ArrayList<PendenzeCart>();
		for (String messaggio : messaggi) {
			String[] messaggioInfo = messaggio.split("#");
			String e2emsgid = messaggioInfo[0];
			String senderId = messaggioInfo[1];
			String senderSys = messaggioInfo[2];
			PendenzeCart pc = new PendenzeCart();
			PendenzeCartPK pk = new PendenzeCartPK();
			pk.setE2emsgid(e2emsgid);
			pk.setSenderid(senderId);
			pk.setSendersys(senderSys);
			pc.setPk(pk);
			EsitiCartPK esitiPK = new EsitiCartPK();
			esitiPK.setE2emsgid(e2emsgid);
			esitiPK.setSenderid(senderId);
			esitiPK.setSendersys(senderSys);
			listEsitiCartPK.add(esitiPK);
			listPendenzePK.add(pk);
			listaPendenze.add(pc);
		}
		try {
			if (isStorico) {
				storicoPendenzeCartDao.updatePendenzeCartStato(listPendenzePK,EnumStatoAllineaPendenze.RISPOSTA_NON_INVIATA.getDescrizione(),EnumStatoAllineaPendenze.IN_SPEDIZIONE.getDescrizione());
				esitiCartDaoStorico.updateEsitiCartStato(listEsitiCartPK,"NON_INVIATO", "DA SPEDIRE");
				storicoEsitiPendenzaDAO.updateEsitiPendenzaStato(listaPendenze,"NON_INVIATO","IN_SPEDIZIONE");
			} else{
				pendenzeCartDao.updatePendenzeCartStato(listPendenzePK,EnumStatoAllineaPendenze.RISPOSTA_NON_INVIATA.getDescrizione(),EnumStatoAllineaPendenze.IN_SPEDIZIONE.getDescrizione());
				esitiCartDao.updateEsitiCartStato(listEsitiCartPK,"NON_INVIATO", "DA SPEDIRE");
				esitiPendenzaDAO.updateEsitiPendenzaStato(listaPendenze,"NON_INVIATO","IN_SPEDIZIONE");
			}
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateNotificheNotSent(String[] notifiche) {
		List<NotificheCartPK> listNotifichePK = new ArrayList<NotificheCartPK>();
		for (String notifica : notifiche) {
			String[] notificaInfo = notifica.split("#");
			String e2emsgid = notificaInfo[0];
			String receiverId = notificaInfo[1];
			String receiverSys = notificaInfo[2];
			NotificheCartPK pk = new NotificheCartPK();
			pk.setE2emsgid(e2emsgid);
			pk.setReceiverid(receiverId);
			pk.setReceiversys(receiverSys);
			listNotifichePK.add(pk);
		}
		try {
			List<String> stateList = new ArrayList<String>();
			stateList.add(EnumStatoNotifichePagamento.NON_INVIATO.getChiave());
			stateList.add(EnumStatoNotifichePagamento.ELABORATO_KO.getChiave());
			notificheCartDao.updateMoreNotificheStato(listNotifichePK,stateList,EnumStatoNotifichePagamento.DA_SPEDIRE.getDescrizione());
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public int updateAllNotificheNotSent(FilterVO filter) {
		return notificheCartDao.updateAllNotificheStato(filter,EnumStatoNotifichePagamento.DA_SPEDIRE.getDescrizione());
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int updateAllMessageNotSent(FilterVO filter) {
		try {
			int esitiCartUpdated = esitiCartDao.updateAllEsitiCartStato(filter,"DA SPEDIRE");
			int esitiPendenzaUpdated = esitiPendenzaDAO.updateAllEsitiPendenzaStato(filter, "IN SPEDIZIONE");
			int pendenzeUpdated = pendenzeCartDao.updateAllPendenzeCartStato(filter,EnumStatoAllineaPendenze.IN_SPEDIZIONE.getDescrizione());
			if ((pendenzeUpdated != esitiCartUpdated) && (esitiPendenzaUpdated != pendenzeUpdated)
					&& (esitiCartUpdated != esitiPendenzaUpdated)) {
				LOGGER.info("Attenzione. Fatti " + pendenzeUpdated + " update su pendenzeCart, " + esitiCartUpdated
						+ " update su esitiCart, " + esitiPendenzaUpdated
						+ " update su esitiPendenza. Esiste un qualche errore sull'applicazione");
			}
			return pendenzeUpdated;
		} catch (Exception e) {
			LOGGER.error("ERROR db",e);
			throw new DAORuntimeException(e);
		}
	}

	
	@Override
	public List<String> listIbanAccredito() {
		try {
			return incassiBonificiRhDAO.listIbanAccredito();
		} catch (Exception e) {
			LOGGER.error("ERROR db",e);
			throw new DAORuntimeException(e);
		}
	}

	
}
