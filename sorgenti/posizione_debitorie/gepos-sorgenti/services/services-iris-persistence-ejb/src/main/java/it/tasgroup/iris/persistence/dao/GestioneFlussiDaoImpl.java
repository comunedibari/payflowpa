package it.tasgroup.iris.persistence.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.iris.dto.flussi.FilterDettaglioNDPDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

@Stateless(name = "GestioneFlussiDaoService")
public class GestioneFlussiDaoImpl extends DaoImplJpaCmtJta<GestioneFlussi> implements GestioneFlussiDao {

    private static final Logger LOGGER = LogManager.getLogger(GestioneFlussiDaoImpl.class);

    @PersistenceContext(unitName = "IrisPU")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @EJB(name = "CfgGatewayPagamentoDaoService")
    CfgGatewayPagamentoDao cfgDao;

    @Override
    public GestioneFlussi getDistintePagamento(String codPagamento) {

        GestioneFlussi distinta = null;

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("codPagamento", codPagamento);

        try {

            distinta = (GestioneFlussi) this.uniqueResultByQuery("getDistintaByCodPagamento", parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistintePagamento " + parameters, e);

            throw new DAORuntimeException(e);

        }

        return distinta;
    }

    @Override
    public GestioneFlussi getDistintePagamento(String codPagamento, String idFlusso, String codPagante) {

        GestioneFlussi distinta = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codPagamento", codPagamento);
        parameters.put("idFlusso", Long.parseLong(idFlusso));
        parameters.put("codPagante", codPagante);

        try {

            distinta = (GestioneFlussi) this.uniqueResultByQuery("getDistintaSecurityCheck", parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistintePagamento " + parameters, e);
            throw new DAORuntimeException(e);
        }

        return distinta;
    }

    @Override
    public GestioneFlussi getDistintaPagamento(String codPagamento, String codTransazione, Date dataCreazione) {

        GestioneFlussi distinta = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codPagamento", codPagamento);
        parameters.put("codTransazione", codTransazione);
        parameters.put("dataFrom", dataCreazione);
        parameters.put("dataTo", new Date(dataCreazione.getTime() + 24 * 60 * 60 * 1000 - 1)); // dataCreazione + 24ore

        try {

            distinta = (GestioneFlussi) this.uniqueResultByQuery("getDistintaByCodAndData", parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistintePagamento " + parameters, e);
            throw new DAORuntimeException(e);
        }

        return distinta;
    }


    @Override
    public GestioneFlussi getDistintaPagamentoByIdfiscCredIUVContpagamento(String idFiscaleCreditore, String IUV, String codContestoPagemento) {
        GestioneFlussi distinta = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("identificativoFiscaleCreditore", idFiscaleCreditore);
        parameters.put("iuv", IUV);
        parameters.put("codTransazionePSP", codContestoPagemento);

        try {

            distinta = (GestioneFlussi) this.uniqueResultByQuery("getDistintaByIUVIdFiscCredCodContestoPag", parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistintePagamento " + parameters, e);
            throw new DAORuntimeException(e);
        }

        return distinta;
    }


    /**
     *
     */
    public GestioneFlussi getDistintaByMsgId(String msgId) {

        GestioneFlussi distinta = null;

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("idTransazione", msgId);

        try {

            distinta = (GestioneFlussi) this.uniqueResultByQuery("getDistintaByMsgId", parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistintaByMsgId " + parameters, e);

            throw new DAORuntimeException(e);

        }

        return distinta;
    }

    /**
     *
     */
    public GestioneFlussi insertFlusso(GestioneFlussi gf) {
        GestioneFlussi flussoManged = null;
        try {
            flussoManged = create(gf);
        } catch (Exception e) {

            LOGGER.error("error on insertFlusso ", e);
            throw new DAORuntimeException(e);

        }
        return flussoManged;
    }

    /**
     *
     */
    public GestioneFlussi saveFlusso(GestioneFlussi gf) {
        GestioneFlussi flussoManaged = null;
        try {
            flussoManaged = save(gf);
        } catch (Exception e) {

            LOGGER.error("error on saveFlusso ", e);
            throw new DAORuntimeException(e);

        }
        return flussoManaged;
    }

    /**
     *
     */
    @Override
    public GestioneFlussi getDistintaById(Long flussoId) {

        GestioneFlussi flusso = null;


        try {

            flusso = (GestioneFlussi) getById(GestioneFlussi.class, flussoId);

        } catch (Exception e) {

            LOGGER.error("error on getDistintaById ", e);

            throw new DAORuntimeException(e);

        }

        return flusso;
    }


    @Override
    public GestioneFlussi createDP(DistintePagamentoDTO dto) {
        try {

            Timestamp now = new Timestamp(System.currentTimeMillis());
            GestioneFlussi dp = new GestioneFlussi();

            // non sono sicura che profile sia l'utente in sessione
            String operatorUsername = dto.getOperatorUsername();//profile.getCodiceFiscale();

            //vedi dto
            dp.setTmbcreazione(dto.getDataCreazione());
            dp.setDataSpedizione(dto.getDataSpedizione());
            dp.setStato(dto.getStato());
            dp.setNumeroDisposizioni(dto.getNumDisposizioni());
            dp.setTotimportipositivi(dto.getImporto().setScale(2, BigDecimal.ROUND_HALF_UP));
            dp.setImportoCommissioni(dto.getImportoCommissione().setScale(2, BigDecimal.ROUND_HALF_UP));
            dp.setUtentecreatore(dto.getUtenteCreatore());
            dp.setCodTransazione(dto.getCodTransazione());

            dp.setCodPagamento(dto.getCodPagamento());
            //da conf
            dp.setDivisa(CommonConstant.CURRENCY);
            dp.setOpInserimento(operatorUsername);
            dp.setTsInserimento(now);

            CfgGatewayPagamento cfgp = cfgDao.getCfgGatewayPagamentoById(Long.valueOf(dto.getIdCfgGateway()));
            dp.setCfgGatewayPagamento(cfgp);

            System.out.println("DistintaPagamento:\n" + dp.toString());

            //inserisci pagamentiionline
            PagamentiOnline pol = this.popolaPagamentoOnline(dto, now, dp);
            if (dp.getPagamentiOnline() != null) {
                dp.getPagamentiOnline().add(pol);
            } else {
                HashSet<PagamentiOnline> pols = new HashSet<PagamentiOnline>();
                pols.add(pol);
                dp.setPagamentiOnline(pols);
            }

            GestioneFlussi created = create(dp);

            return created;

        } catch (Exception e) {
            LOGGER.error("error on  createDP, " + dto, e);
            throw new DAORuntimeException(e);
        }
    }

    /**
     * @param autorizzazioneDto
     * @param flus
     * @param now
     * @return
     * @throws Exception
     */
    private PagamentiOnline popolaPagamentoOnline(DistintePagamentoDTO dto, Timestamp now, GestioneFlussi dp) throws Exception {
        PagamentiOnline pol = new PagamentiOnline();
        pol.setSessionIdSistema("IR1");
        pol.setSessionIdTerminale("IR2");
        if (dp != null) {
            pol.setSessionIdToken(dp.getCodTransazionePSP());
        } else {
            pol.setSessionIdToken("IR3");
        }
        pol.setSessionIdTimbro("IR4");
        pol.setTsOperazione(now);
        pol.setTiOperazione(EnumOperazioniPagamento.AUTORIZZAZIONE.getChiave());
        pol.setIdOperazione(EnumOperazioniPagamento.AUTORIZZAZIONE.getChiave());
        pol.setDeOperazione(EnumOperazioniPagamento.AUTORIZZAZIONE.getDescrizione());
        pol.setOpInserimento(dto.getUtenteCreatore());
        pol.setTsInserimento(now);
        if (dp != null) {
            pol.setCodAutorizzazione(dp.getIuv());
        } else {
            pol.setCodAutorizzazione(null);
        }
        pol.setApplicationId(dp.getCfgGatewayPagamento().getApplicationId());
        pol.setSystemId(dp.getCfgGatewayPagamento().getSystemId());
        pol.setFlussoDistintaOnline(dp);
        return pol;
    }


    @Override
    public List<GestioneFlussi> getGestioneFlussiAll(ContainerDTO containerDTO) {

        List<GestioneFlussi> retList = null;

        try {

            Map parameters = new HashMap();

            String filteringQuery = "select d.* from DISTINTE_PAGAMENTO d where IMPORTO <> 0 and ID_CFG_GATEWAY_PAGAMENTO is not null order by DATA_CREAZIONE desc";

            PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();

            PagingData pagingData = containerDTO.getPagingData();

            retList = paginateByQuery(GestioneFlussi.class, filteringQuery, pagingCriteria, pagingData, parameters);


        } catch (Exception e) {
            LOGGER.error("error on getGestioneFlussiAll ", e);
            throw new DAORuntimeException(e);
        }

        return retList;
    }

    private String buildQueryDistintaPagamento(ContainerDTO dto) {

        PosizioneDebitoriaInputVO inVo = (PosizioneDebitoriaInputVO) dto.getInputDTO();

        String query = "SELECT DISTINCT F.* FROM  DISTINTE_PAGAMENTO F " +
                " left outer join PAGAMENTI P on F.ID = P.ID_DISTINTE_PAGAMENTO " +
                "WHERE 1=1";

        StringBuffer queryBuffer = new StringBuffer(query);

        if (!StringUtils.isEmpty(inVo.getCodicePendenza()))
            queryBuffer.append(" AND P.ID_PENDENZA = :IDPENDENZA ");

        if (!StringUtils.isEmpty(inVo.getFiltroStato()))
            queryBuffer.append(" AND F.STATO = :STATO ");

        queryBuffer.append(" ORDER BY F.ID DESC");

        return queryBuffer.toString();
    }


    private String buildQueryDistinteByFilterParameters(ContainerDTO dto, Map<String, Object> parameters) {

        PosizioneDebitoriaInputVO inVo = (PosizioneDebitoriaInputVO) dto.getInputDTO();

        String query = "SELECT DISTINCT F.* FROM  DISTINTE_PAGAMENTO F " +
                " left outer join CFG_GATEWAY_PAGAMENTO G on G.ID = F.ID_CFG_GATEWAY_PAGAMENTO " +
                "WHERE 1=1";

        StringBuffer queryBuffer = new StringBuffer(query);

        if (!StringUtils.isEmpty(inVo.getFiltroStato())) {

            queryBuffer.append(" AND F.STATO = :STATO ");

            parameters.put("STATO", inVo.getFiltroStato());

        }


        if (!StringUtils.isEmpty(inVo.getIdDistinta())) {

            queryBuffer.append(" AND F.ID = :IDFLUSSO ");

            parameters.put("IDFLUSSO", inVo.getIdDistinta());
        }


        if (inVo.getFiltroTsInserimento_a() != null) {

            queryBuffer.append(" AND F.TS_INSERIMENTO < :TSINSERIMENTOA ");

            parameters.put("TSINSERIMENTOA", inVo.getFiltroTsInserimento_a());
        }


        if (inVo.getFiltroFornitore() != null) {

            queryBuffer.append(" AND G.ID_CFG_FORNITORE_GATEWAY = :IDFORNITORE ");

            parameters.put("IDFORNITORE", inVo.getFiltroFornitore());

        }


        if (inVo.getFiltroEscludiFornitore() != null) {

            queryBuffer.append(" AND G.ID_CFG_FORNITORE_GATEWAY <> :EXCLUDEIDFORNITORE ");

            parameters.put("EXCLUDEIDFORNITORE", inVo.getFiltroEscludiFornitore());
        }

        queryBuffer.append(" ORDER BY F.ID DESC");

        return queryBuffer.toString();
    }

    @Override
    public List<GestioneFlussi> getDistinteByFilterParameters(ContainerDTO containerDTO) {

        List<GestioneFlussi> gf = null;

        Map<String, Object> parameters = new HashMap<String, Object>();

        try {

            String query = buildQueryDistinteByFilterParameters(containerDTO, parameters);

            gf = (List<GestioneFlussi>) paginateByQuery(GestioneFlussi.class, query, containerDTO.getPagingCriteria(), containerDTO.getPagingData(), parameters);

            LOGGER.info("terminated getDistinteByFilterParameters " + parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistinteByFilterParameters " + parameters, e);

            throw new DAORuntimeException(e);

        }

        return gf;
    }

    @Override
    public List<GestioneFlussi> getDistintaPagamento(ContainerDTO dtoIn) {

        List<GestioneFlussi> gf = null;

        PosizioneDebitoriaInputVO inVo = (PosizioneDebitoriaInputVO) dtoIn.getInputDTO();

        Map<String, Object> parameters = new HashMap<String, Object>();

        try {

            String query = buildQueryDistintaPagamento(dtoIn);

            Query q = em.createNativeQuery(query, GestioneFlussi.class);

            if (!StringUtils.isEmpty(inVo.getCodicePendenza()))
                parameters.put("IDPENDENZA", inVo.getCodicePendenza());

            if (!StringUtils.isEmpty(inVo.getFiltroStato()))
                parameters.put("STATO", inVo.getFiltroStato());


            querySetup(q, parameters);

            gf = (List<GestioneFlussi>) q.getResultList();

            LOGGER.info("terminated getDistintaPagamento " + parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistintaPagamento " + parameters, e);

            throw new DAORuntimeException(e);

        }

        return gf;
    }

    @Override
    public List<GestioneFlussi> getDistintaByCodTrasazione(String msgId) {
        List<GestioneFlussi> gf = null;

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("idTransazione", msgId);

        try {
            gf = (List<GestioneFlussi>) listByQuery("getDistintaByMsgId", parameters);
        } catch (Exception e) {
            LOGGER.error("error on getDistintaByCodTrasazione " + parameters, e);
            throw new DAORuntimeException(e);
        }

        return gf;
    }


    @Override
    public GestioneFlussi getAnonymousPaymentByToken(String token) {

        GestioneFlussi ret = null;

        try {

            Query query = null;

            String querysql = "SELECT F.* " +
                    "FROM DISTINTE_PAGAMENTO F " +
                    "left outer join CFG_GATEWAY_PAGAMENTO G on G.ID=F.ID_CFG_GATEWAY_PAGAMENTO " +
                    "left outer join CFG_MODALITA_PAGAMENTO MP on G.ID_CFG_MODALITA_PAGAMENTO=MP.ID " +
                    "WHERE F.COD_PAGAMENTO  = :token ";

            query = em.createNativeQuery(querysql, GestioneFlussi.class);

            query.setParameter("token", token);

            List<GestioneFlussi> listRes = query.getResultList();
            if (listRes.isEmpty())
                throw new DAORuntimeException();

            if (listRes.size() > 0) {
                LOGGER.warn("warn  on getAnonymousPaymentByToken more than one payment associated to cod_pagamento ");

            }
            ret = listRes.get(0);

        } catch (Exception e) {

            LOGGER.error("error on getAnonymousPaymentByToken ", e);

            throw new DAORuntimeException(e);

        }

        return ret;
    }

    @Override
    public GestioneFlussi aggiornamentoStatoFlusso(long idFlusso, StatiPagamentiIris stato, String tranId, String deOperazione, String tipoIdentifAttestante, String identifAttestante, String descrizAttestante) {

        GestioneFlussi updated;

        try {
            Timestamp now = new Timestamp(new java.util.Date().getTime());
//			long idFlusso = Long.parseLong(distintaVO.getFlusso());
//			StatiPagamentiIris stato = distintaVO.getStatoPagamento();
//			String tranId = distintaVO.getToken();
//			String deOperazione = distintaVO.getEsitoPagamento();

            GestioneFlussi distinta = this.getDistintaById(idFlusso);

            String bundleKey = distinta.getCfgGatewayPagamento().getCfgFornitoreGateway().getBundleKey();
            String operazioneAggiornamento = "GATEWAY_RESPONSE";
            if (bundleKey.equals(EnumTipoDDP.NDP.getDescrizione()))
            	operazioneAggiornamento = "RT_NODOPAGAMENTI";
            	
            //aggiornamento distinta
            distinta.setStato(stato.getFludMapping());
            distinta.setTsUpdate(now);
            distinta.setOpAggiornamento(operazioneAggiornamento);
            if (tranId != null) {
                distinta.setCodTransazionePSP(tranId);
            }
            distinta.setTipoIdentificativoAttestante(tipoIdentifAttestante);
            distinta.setIdentificativoAttestante(identifAttestante);
            distinta.setDescrizioneAttestante(descrizAttestante);

            //aggiornamento pagamenti
            Set<Pagamenti> pagamenti = distinta.getPagamenti();
            for (Pagamenti pagamento : pagamenti) {
                pagamento.setStPagamento(stato.getPagaMapping());
                pagamento.setTsAggiornamento(now);
                pagamento.setOpAggiornamento(operazioneAggiornamento);
            }

            //aggiornamento pagamenti on_line
            PagamentiOnline pol = new PagamentiOnline();

            pol.setCodAutorizzazione(distinta.getIuv());
            pol.setDeOperazione(deOperazione);
            pol.setFlussoDistintaOnline(distinta);
            pol.setIdOperazione(tranId);
            pol.setNumOperazione("2");
            pol.setOpAggiornamento(null);
            pol.setOpInserimento(operazioneAggiornamento);
            pol.setSessionIdSistema("SYS");
            pol.setSessionIdTerminale("TERM");
            pol.setSessionIdTimbro("TIMBRO");
            pol.setSessionIdToken(distinta.getCodTransazionePSP());
            pol.setApplicationId(distinta.getCfgGatewayPagamento().getApplicationId());
            pol.setSystemId(distinta.getCfgGatewayPagamento().getSystemId());
            pol.setTiOperazione(EnumOperazioniPagamento.NOTIFICA.getChiave());
            pol.setTsAggiornamento(null);
            pol.setTsInserimento(now);
            pol.setTsOperazione(now);
            distinta.getPagamentiOnline().add(pol);

            updated = update(distinta);

        } catch (Exception e) {

            LOGGER.error("error on aggiornamentoStatoFlusso " + idFlusso, e);

            throw new DAORuntimeException(e);
        }

        return updated;
    }

    @Override
    public List<GestioneFlussi> getDistinteNDP(ContainerDTO containerDTO) {

        List<GestioneFlussi> retList = null;

        try {

            Map<String, Object> params = new HashMap<String, Object>();

            PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();

            PagingData pagingData = containerDTO.getPagingData();

            String query = "select d.* from DISTINTE_PAGAMENTO d, CFG_GATEWAY_PAGAMENTO g where d.id_cfg_gateway_pagamento = g.id ";

            FilterDettaglioNDPDTO filter = (FilterDettaglioNDPDTO) containerDTO.getInputDTO();

            String idRiconciliazione = (String) filter.getIuv();

            if (idRiconciliazione != null && !idRiconciliazione.isEmpty()) {

                if (!idRiconciliazione.contains("*")) {

                    params.put("id_riconciliazione", idRiconciliazione);

                    query = query + " and d.iuv =:id_riconciliazione ";

                } else {

                    params.put("id_riconciliazione", idRiconciliazione.replaceAll("[*]", "%"));

                    query = query + " and d.cod_transazione like :id_riconciliazione ";

                }
            }

            String idPSP = (String) filter.getIdPSP();

            if (idPSP != null && !idPSP.isEmpty()) {

                if (!idPSP.contains("*")) {

                    params.put("id_psp", idPSP);

                    query = query + " and g.system_id =:id_psp ";

                } else {

                    params.put("id_psp", idRiconciliazione.replaceAll("[*]", "%"));

                    query = query + " and g.system_id like :id_psp ";

                }
            }

            BigDecimal importoDa = filter.getImportoDa();

            if (importoDa != null) {

                params.put("importoDa", importoDa);

                query += " and d.importo >=:importoDa ";

            }

            BigDecimal importoA = filter.getImportoA();

            if (importoA != null) {

                params.put("importoA", importoA);

                query += " and d.importo <=:importoA ";

            }

            Date dataDa = filter.getDataDa();

            if (dataDa != null) {

                params.put("dataDa", dataDa);

                query += " and d.ts_inserimento >=:dataDa ";

            }

            Date dataA = filter.getDataA();

            if (dataA != null) {

                dataA.setTime(dataA.getTime() + 24 * 60 * 60 * 1000 - 1);

                params.put("dataA", dataA);

                query += " and d.ts_inserimento <=:dataA ";

            }

            query += " order by d.ts_inserimento desc ";


            retList = paginateByQuery(GestioneFlussi.class, query, pagingCriteria, pagingData, params);


        } catch (Exception e) {
            LOGGER.error("error on getNDPNonRiconciliati ", e);
            throw new DAORuntimeException(e);
        }

        return retList;
    }


    @Override
    public List<GestioneFlussi> getDistinteByIdCondizionePagamento(
            String idCondizionePagamento) {
        List<GestioneFlussi> gf = null;

        Map<String, Object> parameters = new HashMap<String, Object>();

        try {

            String query = "SELECT * FROM DISTINTE_PAGAMENTO WHERE ID IN ("
                    + "SELECT ID_DISTINTE_PAGAMENTO FROM PAGAMENTI WHERE ID_CONDIZIONE = :idCondizionePagamento "
                    + ") ORDER BY TS_INSERIMENTO DESC";

            Query q = em.createNativeQuery(query, GestioneFlussi.class);


            parameters.put("idCondizionePagamento", idCondizionePagamento);


            querySetup(q, parameters);

            gf = (List<GestioneFlussi>) q.getResultList();

            LOGGER.info("terminated getDistinteByIdCondizionePagamento " + parameters);

        } catch (Exception e) {

            LOGGER.error("error on getDistinteByIdCondizionePagamento " + parameters, e);

            throw new DAORuntimeException(e);

        }

        return gf;
    }

    @Override
    public List<GestioneFlussi> getByCodPagamentoCodiceFiscale(String codPagamento, String codFiscale) {

        List<GestioneFlussi> gf = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        try {
            
            String query = "SELECT DISTINCT D.* FROM DISTINTE_PAGAMENTO D, PAGAMENTI P WHERE "
            		+"D.COD_PAGAMENTO = :codPagamento AND P.ID_DISTINTE_PAGAMENTO=D.ID "
            		+"AND (D.UTENTE_CREATORE = :codFiscale " 
            		+"OR ( :codFiscale IN (SELECT CO_DESTINATARIO FROM JLTDEPD E WHERE "
            		+"E.CO_DESTINATARIO = :codFiscale AND  E.ID_PENDENZA = P.ID_PENDENZA " 
            		+"AND E.TI_DESTINATARIO <> 'DE')))";
      
            Query q = em.createNativeQuery(query, GestioneFlussi.class);
            parameters.put("codPagamento", codPagamento);
            parameters.put("codFiscale", codFiscale);
            
            querySetup(q, parameters);

            gf = (List<GestioneFlussi>) q.getResultList();
            LOGGER.info("terminated getByCodPagamentoCodiceFiscale " + parameters);
        	
        } catch (Exception e) {
            LOGGER.error("error on getByCodPagamentoCodiceFiscale " + parameters, e);
            throw new DAORuntimeException(e);
        }

        return gf;
    }

    @Override
    public List<GestioneFlussi> getDistinteByIdGruppo(String idGruppo) {
    	Query q = em.createNamedQuery("getDistintaByIdGruppo", GestioneFlussi.class); 
    	q.setParameter("idGruppo", idGruppo);
    	List<GestioneFlussi> flussiList = q.getResultList();
        return flussiList;
    }

	@Override
	public List<GestioneFlussi> getByCodPagamento(String codPagamento) {
		Query q = em.createNamedQuery("getDistintaByCodPagamento");
    	q.setParameter("codPagamento",codPagamento);
    	List<GestioneFlussi> flussiList = q.getResultList();
        return flussiList;
	}

	@Override
	public List<GestioneFlussi> getDistintaByIUVIdFiscCred(	String idFiscaleCreditore, String IUV) {
		Query q = em.createNamedQuery("getDistintaByIUVIdFiscCred");
		q.setParameter("identificativoFiscaleCreditore", idFiscaleCreditore);
		q.setParameter("iuv", IUV);
    	List<GestioneFlussi> flussiList = q.getResultList();
        return flussiList;
	}
		
	@Override 
	public List<Map> readAnonymousPaymentByCondizione(String idCondizione) {
				Query q = em.createNamedQuery("getPagamentiByIdCondizione",Pagamenti.class);
				q.setParameter("idCondizione", idCondizione);
				
				List<Pagamenti> resultList = q.getResultList();
				ArrayList<Map> arr = new ArrayList<Map>();
				for (Pagamenti p: resultList){
					Map t = new HashMap();
					t.put("pagamento", p);
					t.put("distinta", p.getFlussoDistinta());
					arr.add(t);					
				}
				
				return arr;
	}
	
	@Override
	public boolean annullaOperatoreByIdDistinta(Long id) throws Exception {
		
		Query q = em.createNativeQuery("UPDATE DISTINTE_PAGAMENTO SET STATO='ANNULLATO OPE' WHERE ID="+id+" AND STATO='IN CORSO'");
		int num = q.executeUpdate();
		if (num > 0){
			Query q1 = em.createNativeQuery("UPDATE PAGAMENTI SET ST_PAGAMENTO='AO' WHERE ST_PAGAMENTO='IC' AND ID_DISTINTE_PAGAMENTO="+ id+"");
			int num1 = q1.executeUpdate();
			if (num1 == 0){
				throw new RuntimeException(); // dovrebbe rollbackare...
			} else {
				return true;
			}
		} else {
			return false;
		}
		
	}
		
		
}
