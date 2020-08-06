package it.tasgroup.iris.persistence.dao;

import static it.tasgroup.iris.persistence.dao.PagamentiDaoImpl.PagamentiPerCanaleKey.getLogicKey;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.StaleObjectStateException;
import org.hibernate.type.StandardBasicTypes;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.backoffice.ente.CondizioniRicercaVO;
import it.nch.idp.backoffice.ente.EnumStatoCondizione;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.nch.is.fo.stati.pagamenti.CheckRiconciliazioneCompleta;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.profile.IProfileManager;
import it.nch.utility.MidnightDate;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnCheckHelper;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.creditore.StatistichePagamento;
import it.tasgroup.idp.rs.model.pagamenti.EnumTipoNotifica;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoRicercaDTO;
import it.tasgroup.iris.dto.query.support.StatistichePerCanaleStatoQueryDTO;
import it.tasgroup.iris.dto.query.support.StatisticheTestataQueryDTO;
import it.tasgroup.iris.dto.rest.filters.PagamentiFilter;
import it.tasgroup.iris.dto.rest.filters.StatistichePagamentoFilter;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.DestinatariPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiDao;
import it.tasgroup.iris.persistence.dao.util.DaoUtil;
import it.tasgroup.iris.persistence.dao.util.PaginationJPQLQuery;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumModalitaAnonima;
import it.tasgroup.services.util.enumeration.EnumStatoRevoca;
import it.tasgroup.services.util.enumeration.EnumTipoPredeterminato;
import it.tasgroup.services.util.enumeration.EnumUtils;

@Stateless(name = "PagamentiDaoService")
public class PagamentiDaoImpl extends DaoImplJpaCmtJta<Pagamenti> implements PagamentiDao {

    private static final Logger LOGGER = LogManager.getLogger(PagamentiDaoImpl.class);

    private static final String QUERYPAGAMENTIBASE = " select pa.*, " +
            " pe.id_tributo_strutturato as pe_tributoStrutturato, " +
            " co.im_Totale as co_imTotale," +
            " co.ti_pagamento as co_tiPagamento,  " +
            " case when de.co_destinatario is null then '1' else '0' end  as pagamentoContoTerzi," +
            " pe.de_causale as pe_de_causale, " +
            " pe.riferimento as pe_riferimento, " +
            " pe.co_riscossore as pe_riscossore, " +
            " enti.denom as enti_denom, " +
            " tributi.de_trb as tributi_de_trb, " +
            " d.cod_pagamento as d_cod_pagamento, d.utente_creatore as lapl_pagante, " +
            " co.causale_pagamento as causalePagamento, " +
            " plugin.cd_plugin as cdPlugin, " +
            " gat.fl_consegna_ricevuta_idp as flagConsegnaRicevuta " +
            " from PAGAMENTI pa " +
            " inner join JLTCOPD co on pa.id_condizione = co.id_condizione " +
            " inner join JLTPEND pe on pa.id_pendenza = pe.id_pendenza " +
            " inner join DISTINTE_PAGAMENTO d on pa.id_distinte_pagamento = d.id " +
            " inner join CFG_GATEWAY_PAGAMENTO gat on d.id_cfg_gateway_pagamento = gat.id " + 
            " left outer join JLTDEPD de on pa.id_pendenza = de.id_pendenza and de.co_destinatario=:cod_fiscale and de.ti_destinatario='CI'" +
            " left outer join JLTENTI enti on pa.id_ente = enti.id_ente " +
            " left outer join JLTENTR tributi on pa.id_ente=tributi.id_ente and pa.cd_trb_ente=tributi.cd_trb_ente " +
            " left outer join CFG_TRIBUTIENTE_PLUGIN plugin on tributi.id_ente=plugin.id_ente and tributi.cd_trb_ente=plugin.cd_trb_ente " +
            " where pa.co_pagante = :cod_fiscale ";

    @EJB(name = "GestioneFlussiDaoImpl")
    GestioneFlussiDao distinteDAO;

    @EJB(name = "CondizioniPagamentoDaoImpl")
    CondizioniPagamentoDao condPagamDAO;

    @EJB(name = "DestinatariPendenzaDaoService")
    DestinatariPendenzaDao destinatariPendenzaDao;

    @EJB(name = "EntiDaoService")
    EntiDao entiDao;

    @PersistenceContext(unitName = "IrisPU")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public Pagamenti salvaPagamento(String op, String idPendenzaEnte, SessionShoppingCartItemDTO pvo, Long idDistintaPagamento) {
        Pagamenti pagam = new Pagamenti();
        try {

            pagam.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
            pagam.setDtScadenza(new Date(System.currentTimeMillis()));
            pagam.setTsOrdine(new Timestamp(System.currentTimeMillis()));

            pagam.setCondPagamento(condPagamDAO.getById(CondizionePagamento.class, pvo.getIdCondizione()));
            pagam.setIdEnte(pvo.getIdEnte());
            pagam.setIdTributo(pvo.getIdTributo());
            pagam.setCdTrbEnte(pvo.getIdTributoEnte());
            pagam.setFlussoDistinta(distinteDAO.getById(GestioneFlussi.class, idDistintaPagamento));
            pagam.setImPagato(pvo.getImporto());
            pagam.setDistinta("distinta");

            pagam.setCoPagante(op);//cf_utente in sessione
            pagam.setTiPagamento(pvo.getTipoPagamento());//tipoPagamento
            pagam.setTiDebito(pvo.getTiDebito());//tipodebito
            pagam.setStPagamento(StatiPagamentiIris.IN_CORSO.getPagaMapping());//IC
            pagam.setStRiga("V");//V

            pagam.setFlagOpposizione730(pvo.getItemOpposizione730());

            pagam.setOpInserimento(op);
            pagam.setTsInserimento(new Timestamp(System.currentTimeMillis()));

            pagam.setIdPendenza(pvo.getIdPendenza());
            pagam.setIdPendenzaente(idPendenzaEnte);
            
            if ("E_BOLLO".equals(pagam.getCdTrbEnte()) || (pagam.getFlussoDistinta().getCfgGatewayPagamento().getFlagRendRiversamento().equals("0") && 
					CheckRiconciliazioneCompleta.isEnteRiconciliazioneCompleta(pagam.getFlussoDistinta().getIdentificativoFiscaleCreditore()))) {
			   pagam.setFlagIncasso("N");	
			} else {
			   pagam.setFlagIncasso("0");
			}

            //		em.persist(distPag);
            em.persist(pagam);
            return pagam;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("salvaPagamento - eccezione", e);
        }

        return null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<PagamentoPosizioneDebitoriaForHomePageVO> ultimiPagamentiEffettuatiHP(String codiceFiscale) {
        List<Object[]> listObj = new ArrayList<Object[]>();

        List<PagamentoPosizioneDebitoriaForHomePageVO> result = new ArrayList<PagamentoPosizioneDebitoriaForHomePageVO>();

        if (codiceFiscale == null || codiceFiscale.isEmpty()) {
            return result;
        }

        try {

            Map<String, Object> params = new HashMap<String, Object>();

            String filteringQuery = buildQueryAndParametersForUltimiPagamentiHomePage(codiceFiscale, params);
            Query q = querySetup(em.createNativeQuery(filteringQuery, "pagamentiEffettuatiHomePageMapping"), 5, 0, 5, params);

            listObj = (List<Object[]>) q.getResultList();
            if (listObj != null) {
                for (Object[] row : listObj) {
                    PagamentoPosizioneDebitoriaForHomePageVO item = populatePagamentoPosizioneDebitoriaForHomePageVO(row);
                    result.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("ultimiPagamentiEffettuatiHP", e);
            throw new DAORuntimeException(e);
        }

        return result;
    }

    private String buildQueryAndParametersForUltimiPagamentiHomePage(String codFiscale, Map<String, Object> parameters) {

        String selectFromWhere = QUERYPAGAMENTIBASE;
        StringBuffer whereConditions = new StringBuffer("");

        parameters.put("cod_fiscale", codFiscale);

        whereConditions.append(" AND pa.st_pagamento in ('ES','EF','IC','ST') ");

        String orderingCondition = " ORDER BY pa.ts_decorrenza desc ";

        String queryString = selectFromWhere + whereConditions + orderingCondition;

        return queryString;
    }


    @Override
    public List<PagamentoPosizioneDebitoriaForHomePageVO> storicoPagamenti(IProfileManager profile, ContainerDTO containerDTO) {

        PosizioneDebitoriaInputVO inputDTO = (PosizioneDebitoriaInputVO) containerDTO.getInputDTO();

        List<PagamentoPosizioneDebitoriaForHomePageVO> result = new ArrayList<PagamentoPosizioneDebitoriaForHomePageVO>();

        try {

            Map<String, Object> parameters = new HashMap<String, Object>();

            String filteringQuery = buildQueryAndParametersForStoricoPagamenti(profile, inputDTO, parameters);

            PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();

            PagingData pagingData = containerDTO.getPagingData();

            @SuppressWarnings("unchecked")
            List<Object[]> listObj = (List<Object[]>) paginateByQueryWithResultsetMapping(filteringQuery, pagingCriteria, pagingData, parameters, "pagamentiEffettuatiHomePageMapping");

            if (listObj != null) {
                for (Object[] row : listObj) {
                    PagamentoPosizioneDebitoriaForHomePageVO item = populatePagamentoPosizioneDebitoriaForHomePageVO(row);
                    result.add(item);
                }
            }

        } catch (Exception e) {
            LOGGER.error("error on storicoPagamenti " + containerDTO, e);
            throw new DAORuntimeException(e);
        }
        return result;
    }


    private String buildQueryAndParametersForStoricoPagamenti(IProfileManager profile, PosizioneDebitoriaInputVO dto, Map<String, Object> parameters) {

        String selectFromWhere = QUERYPAGAMENTIBASE;
        StringBuffer whereConditions = new StringBuffer("");

        parameters.put("cod_fiscale", profile.getCodiceFiscale());

        // Aggiunta di filtraggi opzionali
        String codPagamento = dto.getFiltroCodPagamento();
        String idEnte = dto.getFiltroEnte();
        String idTributoEnte = dto.getFiltroTipoTributo();
        Date dataPagamentoDa = dto.getDataPagamento_da();
        Date dataPagamentoA = dto.getDataPagamento_a();
        String iuv = dto.getIuv();

        Boolean includiInErrore = dto.getFiltroIncludiPagamentiInErrore();

        if (dataPagamentoDa != null) {

            whereConditions.append(" AND d.DATA_CREAZIONE >= :data_creazione_da ");

            parameters.put("data_creazione_da", dataPagamentoDa);

        }

        if (dataPagamentoA != null) {

            whereConditions.append(" AND d.DATA_CREAZIONE < :data_creazione_a ");

            parameters.put("data_creazione_a", DaoUtil.addDays(dataPagamentoA, 1));

        }

        if (codPagamento != null && codPagamento.trim().length() > 0) {

            whereConditions.append(" AND d.cod_pagamento =:cod_pagamento");

            parameters.put("cod_pagamento", codPagamento);

        }

        if (!includiInErrore) {
            whereConditions.append(" AND pa.st_pagamento in ('ES','EF','IC','ST') ");
        }

        if (idEnte != null && idEnte.trim().length() > 0) {
        	 whereConditions.append(" AND pa.id_ente =:id_ente");
             parameters.put("id_ente", idEnte);
        }
        
        if (idTributoEnte != null && idTributoEnte.trim().length() > 0) {
        	 whereConditions.append(" AND pa.cd_trb_ente=:id_tributo_ente");
        	 parameters.put("id_tributo_ente", idTributoEnte);
        }
        
        if (iuv != null && iuv.trim().length() > 0) {
        	whereConditions.append(" AND co.ID_PAGAMENTO =:id_pagamento");
        	parameters.put("id_pagamento", iuv);
        }
        
        if (isNotEmptyProperty(dto.getFiltroModPagamento())) {
            parameters.put("modPagamento", dto.getFiltroModPagamento().trim().toUpperCase());
            // DISTINTE_PAGAMENTO d
            //whereClause += " and m.ID = :modPagamento ";
            whereConditions.append(" and d.ID_CFG_GATEWAY_PAGAMENTO = :modPagamento ");
        }

        String orderingCondition = " ORDER BY pa.ts_decorrenza desc ";

        String queryString = selectFromWhere + whereConditions + orderingCondition;

        return queryString;
    }

    /**
     * Popolamento del Vo di uscita
     *
     * @param row
     * @return
     */
    private PagamentoPosizioneDebitoriaForHomePageVO populatePagamentoPosizioneDebitoriaForHomePageVO(Object[] row) {
        PagamentoPosizioneDebitoriaForHomePageVO ppdVO = new PagamentoPosizioneDebitoriaForHomePageVO();

        Pagamenti pag = (Pagamenti) row[0];
        Integer tributoStrutturato = null;
        if (row[1] != null)
            tributoStrutturato = ((Number) row[1]).intValue();
        BigDecimal importoCondizione = (BigDecimal) row[2];
        String tipoPagamento = (String) row[3];
        String flagContoTerzi = row[4] != null ? row[4].toString() : "";   // Oracle ritorna un Char, gli altri db una String, cos� funziona sempre.
        String deCausale = (String) row[5];
        String denominazioneEnte = (String) row[6];
        String descrizioneTributo = (String) row[7];
        String codicePagamento = (String) row[8];
        String laplPagante = (String) row[9];
        String causalePagamento = (String) row[10];
        String riscossore = (String) row[11];
		String riferimento = (String) row[12];
        String cdPlugin = (String) row[13];
        String flagConsegnaRicevutaIdp = row[14] != null ? row[14].toString() : ""; 

        ppdVO.setDecorrenza(pag.getTsDecorrenza());

        ppdVO.setTsInserimento(pag.getTsInserimento());

        ppdVO.setIdPendenza(pag.getIdPendenza());
        ppdVO.setTipoPagamento(tipoPagamento);
        ppdVO.setFlagContoTerzi(flagContoTerzi != null && flagContoTerzi.equals("1") ? Boolean.TRUE : Boolean.FALSE);
        ppdVO.setImporto(pag.getImPagato());
        ppdVO.setIdPagamento(pag.getId().intValue());
        ppdVO.setStato(pag.getStPagamento()); //Stato pagamento
        ppdVO.setIdPagamento(pag.getId().intValue());
        ppdVO.setIdCondizione(pag.getCondPagamento().getIdCondizione());
        ppdVO.setIdPendenza(pag.getIdPendenza());
        ppdVO.setTipoSpontaneo(pag.getTipospontaneo());
        ppdVO.setFlagIncasso(pag.getFlagIncasso() == null || pag.getFlagIncasso().isEmpty() || pag.getFlagIncasso().equals("0") ? Boolean.FALSE : Boolean.TRUE);
        ppdVO.setFlusso(pag.getFlussoDistinta().getId() + "");
        ppdVO.setImportoTotCondizione(importoCondizione);
        ppdVO.setSpontaneo(tributoStrutturato != null);
        ppdVO.setCodPagamento(codicePagamento);

        ppdVO.setIdEnte(pag.getIdEnte());
        ppdVO.setIdTributoEnte(pag.getCdTrbEnte());
        ppdVO.setCdPlugin(cdPlugin);
        ppdVO.setFlagConsegnaRicevutaIdp(flagConsegnaRicevutaIdp);

        ppdVO.setAssociatedDocAvailable(pag.isAssociatedDocumentAvailable());
        ppdVO.setEnte(denominazioneEnte);
        ppdVO.setTipoTributo(descrizioneTributo);
        ppdVO.setCausale(deCausale);
        ppdVO.setLaplPagante(laplPagante);
        ppdVO.setCausalePagamento(causalePagamento);
        ppdVO.setRiscossore(riscossore);
        ppdVO.setRiferimento(riferimento);

        if (ppdVO.getFlagContoTerzi()) {

            StringBuilder sbDebitori = new StringBuilder();
            List<String> debitoriPendenza = destinatariPendenzaDao.listaDebitoriByIdPendenza(pag.getIdPendenza());
            for (String dp : debitoriPendenza) {
                if (sbDebitori.length() != 0) {
                    sbDebitori.append(", ");
                }
                sbDebitori.append(dp);
            }
            ppdVO.setDebitore(sbDebitori.toString());
        }


        return ppdVO;
    }

    @Override
    public List<Pagamenti> getPagamentiFull(ContainerDTO inputDTO) {
        List<Pagamenti> retList = new ArrayList<Pagamenti>();
        LOGGER.debug("getPagamentiFull - start");

        DistintePagamentoRicercaDTO dto = (DistintePagamentoRicercaDTO) inputDTO.getInputDTOList().get(3);

        try {

            Map<String, Object> parameters = new HashMap<String, Object>();

            String query = getQueryPagamenti(dto, parameters);

            Query q = em.createNativeQuery(query);

            for (Object key : parameters.keySet()) {
                q.setParameter((String) key, parameters.get(key));
            }

            q.unwrap(SQLQuery.class).addEntity("p", Pagamenti.class);
            q.unwrap(SQLQuery.class).addScalar("data_ordinamento", StandardBasicTypes.DATE);


            @SuppressWarnings("unchecked")
            List<Object[]> r = (List<Object[]>) q.getResultList();

            for (Object[] o : r) {
                retList.add((Pagamenti) o[0]);
            }

        } catch (Exception e) {
            LOGGER.error("error on getPagamentiFull ", e);
            throw new DAORuntimeException(e);
        }

        LOGGER.debug("getPagamentiFull - n. risultati: " + retList.size());
        return retList;
    }
    
    
    @Override
    public List<CondizionePagamento> getCondizioniCreditoreFull(ContainerDTO containerDTO) {
        List<Pagamenti> retList = new ArrayList<Pagamenti>();
        LOGGER.debug("getPagamentiFull - start");
        
        CondizioniRicercaVO inputDTO = (CondizioniRicercaVO) containerDTO.getInputDTOList().get(3);
        
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            
            // CondizioniRicercaVO inputDTO = (CondizioniRicercaVO) containerDTO.getInputDTO();
            
            PaginationJPQLQuery paginationJPQLQuery = buildJPQLQueryCondizioni(inputDTO, parameters);
    
            
            Query query = em.createQuery(paginationJPQLQuery.getQuery());
    
            // handleAggregateData(queries, containerDTO, parameters);

            for (Object key : parameters.keySet()) {
                query.setParameter((String) key, parameters.get(key));
            }
            
            return query.getResultList();
        
        } catch (Exception e) {
            LOGGER.error("error on getPagamentiFull ", e);
            throw new DAORuntimeException(e);
        }
        
    }
    
    @Override
    public List<Pagamenti> getPagamentiFullRT(ContainerDTO containerDTO) {
        LOGGER.debug("getPagamentiFullRT - start");
        CondizioniRicercaVO inputDTO = (CondizioniRicercaVO) containerDTO.getInputDTOList().get(0);
        List<Pagamenti> listaPagamenti = new ArrayList<Pagamenti>();
        try {
        	if (isNotEmptyProperty(inputDTO.getCircuito())) {
        		if (Long.valueOf(inputDTO.getCircuito()) != 4)
        			return listaPagamenti; // lista vuota
        	}
            if (isNotEmptyProperty(inputDTO.getStatoPagamentoCondizione())) {
            	EnumStatoCondizione statoPagamentoCondizione = EnumUtils.findByChiave(inputDTO.getStatoPagamentoCondizione(), EnumStatoCondizione.class);
            	if (statoPagamentoCondizione != EnumStatoCondizione.PAGATA) {
            		return listaPagamenti; // lista vuota
            	}
            }
            		
            Map<String, Object> parameters = new HashMap<String, Object>();
            PaginationJPQLQuery paginationJPQLQuery = buildJPQLQueryCondizioniRT(inputDTO, parameters);
			Query query = em.createQuery(paginationJPQLQuery.getQuery());
            for (Object key : parameters.keySet()) {
                query.setParameter((String) key, parameters.get(key));
            }
    		List<CondizionePagamento> listaCondizioni = query.getResultList();
            for (CondizionePagamento condizionePagamento : listaCondizioni) {
    			List<Pagamenti> pagamentiEseguiti = BillItemInspector.getPagamentiEseguiti(condizionePagamento);
    			listaPagamenti.addAll(pagamentiEseguiti);
    		}
        } catch (Exception e) {
            LOGGER.error("error on getPagamentiFullRT ", e);
            throw new DAORuntimeException(e);
        }
        return listaPagamenti;
    }
    
    
    
    @Override
	public List<Pagamenti> getPagamentiByCodFiscale(ContainerDTO inputDTO) {
		List<Pagamenti> retList = null;
		LOGGER.debug("getPagamentiByCodFiscale - start");
		
		DistintePagamentoRicercaDTO dto = (DistintePagamentoRicercaDTO) inputDTO.getInputDTOList().get(1);
		String codFisc = dto.getCodFiscDebitore();
		
		Query q = em.createNamedQuery("getPagamentiByCodFiscale");
        q.setParameter("codFiscale", codFisc);

        retList = q.getResultList();
		
		return retList;
	}
    
    @Override
    public List<Pagamenti> getPagamentiEseguiti(ContainerDTO containerDTO, String flagInCorso, String idCondizione) {
        List retList = null;
        String query = "select p from Pagamenti p where " + "%%%flagInCorso%%%" + " and p.coPagante = :codFiscale" + "%%%condCatTributo%%%" + "%%%idCondizione%%%" + " order by tsInserimento desc";

        try {
            PagamentiFilter inputDTO = (PagamentiFilter) containerDTO.getInputDTO();
            String codFisc = inputDTO.getCodiceFiscaleDebitore();
            String catTributo = inputDTO.getCatTributo();
            
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("codFiscale", codFisc);
            
            if(flagInCorso == null || flagInCorso.isEmpty()) {
            	query = query.replace("%%%flagInCorso%%%", "(p.stPagamento = 'ES' or p.stPagamento = 'IC')");
            }else {
            	query = query.replace("%%%flagInCorso%%%", "p.stPagamento = 'IC'");
            }
            
            if(catTributo == null || catTributo.isEmpty()) {
            	query = query.replace("%%%condCatTributo%%%", "");
//            	Query q = em.createQuery(query, Pagamenti.class);
//                q.setParameter("codFiscale", codFisc);
//                retList = q.getResultList();
//                Tracer.debug(getClass().getName(), " - cod. fiscale utente=" + codFisc, " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + retList.size());
//                System.out.println(getClass().getName() + " - cod. fiscale utente=" + codFisc + " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + retList.size());
            }
            else {
            	String condCatTributo = " and p.idTributo=:cat_tributo ";
            	query = query.replace("%%%condCatTributo%%%", condCatTributo);
            	paramMap.put("cat_tributo", catTributo);
//            	Query q = em.createQuery(query, Pagamenti.class);
//                q.setParameter("codFiscale", codFisc);
//                q.setParameter("cat_tributo", catTributo);
//                retList = q.getResultList();
//                Tracer.debug(getClass().getName(), " - cod. fiscale utente=" + codFisc + " - categoria tributo=" + catTributo, " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + retList.size());
//                System.out.println(getClass().getName() + " - cod. fiscale utente=" + codFisc + " - categoria tributo=" + catTributo + " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + retList.size());
            }
            
            if (idCondizione == null || idCondizione.isEmpty()) {
            	query = query.replace("%%%idCondizione%%%", "");
            } else {
            	String condIdCondizione = " and p.condPagamento.idCondizione =:idCondizione ";
            	query = query.replace("%%%idCondizione%%%", condIdCondizione);
            	paramMap.put("idCondizione", idCondizione);
            }
            
        	Query q = em.createQuery(query, Pagamenti.class);
        	
        	for (Map.Entry<String, String> param : paramMap.entrySet()) {
        		q.setParameter(param.getKey(), param.getValue());
        	}
        	
       		retList = q.getResultList();
    		Tracer.debug(getClass().getName(), " - cod. fiscale utente=" + codFisc + " - categoria tributo=" + catTributo + " - id condizione=" + idCondizione, " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + retList.size());
    		System.out.println(getClass().getName() + " - cod. fiscale utente=" + codFisc + " - categoria tributo=" + catTributo + " - id condizione=" + idCondizione + " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + retList.size());
            
            return retList;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new DAORuntimeException(e);
        }
    }
    
    @Override
    public List<Pagamenti> getPagamentoEseguito(String idCondizione) {
        List p = null;
        String query = "select p from Pagamenti p where p.condPagamento.idCondizione = :idCondizione order by tsInserimento desc";

        try {
            //Query solo con codice pagamento
            Query q = em.createQuery(query, Pagamenti.class);
            q.setParameter("idCondizione", idCondizione);
            p = q.getResultList();
            Tracer.debug(getClass().getName(), " - cod. pagamento=" + idCondizione, " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + p.size());
            System.out.println(getClass().getName() + " - cod. pagamento=" + idCondizione + " - lista pagamenti effettuati nuovo HP - LISTA SIZE: " + p.size());
        } catch (Exception e) {
            LOGGER.error(e);
            throw new DAORuntimeException(e);
        }
        return p;
    }

    @Override
    public List<Pagamenti> getPagamenti(ContainerDTO inputDTO) {
        List<Pagamenti> retList = new ArrayList<Pagamenti>();
        List<Object[]> retList2 = null;
        LOGGER.debug("getPagamenti - start");

        DistintePagamentoRicercaDTO dto = (DistintePagamentoRicercaDTO) inputDTO.getInputDTOList().get(1);

        try {

            Map<String, Object> parameters = new HashMap<String, Object>();
            // #1049 Export Pagamento

            String queryToUse = getQueryPagamenti(dto, parameters);

            PagingCriteria pagingCriteria = inputDTO.getPagingCriteria();

            PagingData pagingData = inputDTO.getPagingData();

            LOGGER.debug("------------------------------------------------");
            LOGGER.debug("query: " + queryToUse);
            LOGGER.debug("------------------------------------------------");

            //retList = paginateByQuery(Pagamenti.class, query, pagingCriteria, pagingData, parameters);
            
            
            managePagingData(queryToUse, pagingCriteria, pagingData, parameters);

			Query query = em.createNativeQuery(queryToUse);			
			query.unwrap(SQLQuery.class).addEntity("p", Pagamenti.class);
			query.unwrap(SQLQuery.class).addScalar("data_ordinamento", StandardBasicTypes.DATE);
			query.unwrap(SQLQuery.class).addScalar("num_revoche", StandardBasicTypes.INTEGER);
			query.unwrap(SQLQuery.class).addScalar("num_revoche_w", StandardBasicTypes.INTEGER);

			retList2 = this.querySetup(query, 0, pagingCriteria.getRecordPosition(), pagingCriteria.getResultsPerPage(), parameters).getResultList();
			
            for (Object[] o : retList2) {
            	Pagamenti p = (Pagamenti) o[0];            	
            	p.setNumeroRevoche((Integer)o[2]);
            	p.setNumeroRevocheDaValutare((Integer)o[3]);
                retList.add((Pagamenti) o[0]);
            }

        } catch (Exception e) {
            LOGGER.error("error on getPagamenti ", e);
            throw new DAORuntimeException(e);
        }

        if (retList != null) LOGGER.debug("getPagamenti - n. risultati: " + retList.size());
        else LOGGER.debug("getPagamenti - nessun risultato");
        return retList;
    }

    private String getQueryPagamenti(DistintePagamentoRicercaDTO dto, Map<String, Object> parameters) {
        return getQueryPagamenti(dto, false, parameters);
    }

    private String getQueryPagamentiConta(DistintePagamentoRicercaDTO dto, Map<String, Object> parameters) {
        return getQueryPagamenti(dto, true, parameters);
    }
    
    public String getQueryContaRicevuteTelematiche(CondizioniRicercaVO dto, Map<String, Object> parameters) throws ParseException {
    	return getQueryRicevuteTelematiche(dto, true, parameters);
    }

    public String getQueryRicevuteTelematiche(CondizioniRicercaVO dto, Map<String, Object> parameters) throws ParseException {
    	return getQueryRicevuteTelematiche(dto, false, parameters);
    }
    
    private String getQueryRicevuteTelematiche(CondizioniRicercaVO dto, boolean conta, Map<String, Object> parameters) throws ParseException {
    	PaginationJPQLQuery jpqlQuery = this.buildJPQLQueryCondizioniRT(dto, parameters);
    	if (conta)
    		return jpqlQuery.getCountQuery();
    	else 
    		return jpqlQuery.getQuery();
    }


    private String getQueryPagamenti(DistintePagamentoRicercaDTO dto, boolean count, Map<String, Object> parameters) {

        //String filteringQuery = " from DISTINTE_PAGAMENTO d , PAGAMENTI p , JLTENTI e ";
    	String filteringQuery = " from PAGAMENTI p left outer join REVOCHE_PAGAMENTO rp on p.ID = rp.ID_PAGAMENTI, ENTI e , "
        		+ "DISTINTE_PAGAMENTO d  ";

        if (count) {
            filteringQuery = " select count(distinct p.ID) num " + filteringQuery;
        } else {
            filteringQuery = " select distinct p.*, d.DATA_CREAZIONE as data_ordinamento, "
            		+ "(select count(*) from REVOCHE_PAGAMENTO r where r.ID_PAGAMENTI=p.ID) as num_revoche, "
            		+ "(select count(*) from REVOCHE_PAGAMENTO r where r.ID_PAGAMENTI=p.ID AND r.STATO_REVOCA = '" + EnumStatoRevoca.DA_VALUTARE.getChiave() + "') as num_revoche_w " 
            		+ filteringQuery;
        }
        
        parameters.put("statoRiga", "V");
        String whereClause = " where p.ID_DISTINTE_PAGAMENTO = d.ID " +
                " and p.ST_RIGA= :statoRiga " +
                " and p.ID_ENTE = e.ID_ENTE ";

        String orderClause = " order by d.DATA_CREAZIONE desc ";

        boolean addModalitaPagamenti = false;
        
        boolean addCondizioni = false;
        boolean addDestinatari = false;
        boolean addPendenze = false;

        if (isNotEmptyProperty(dto.getDataScadenzaGG()) && isNotEmptyProperty(dto.getDataScadenzaMM()) && isNotEmptyProperty(dto.getDataScadenzaYY())) {
            System.out.println("dto.getDataScadenzaYY(): " + dto.getDataScadenzaYY());
            System.out.println("dto.getDataScadenzaMM(): " + dto.getDataScadenzaMM());
            System.out.println("dto.getDataScadenzaGG(): " + dto.getDataScadenzaGG());
            Timestamp data = DaoUtil.getTimestampDa(dto.getDataScadenzaYY(), dto.getDataScadenzaMM(), dto.getDataScadenzaGG());
            System.out.println("Data scadenza: " + new java.sql.Date(data.getTime()));
            parameters.put("data_scadenza", new java.sql.Date(data.getTime()));
            whereClause += " and c.DT_SCADENZA = :data_scadenza ";
            addCondizioni = true;

        }

        if (isNotEmptyProperty(dto.getCfUtenteCreatore())) {
            String cfVersante = dto.getCfUtenteCreatore().trim().toUpperCase();
            parameters.put("idUtenteCreatore", "%" + cfVersante.replace("*", "") + "%");
            whereClause += " and d.UTENTE_CREATORE like :idUtenteCreatore ";

        }
        
        //TODO rivedere la gestione della ricerca in like case insensitive
        if (isNotEmptyProperty(dto.getIstitutoAttestante())) {
            String istitutoAttestante = dto.getIstitutoAttestante().trim().toUpperCase();
            String tmp = "=";
            if (istitutoAttestante.startsWith("*")) {
            	istitutoAttestante = "%" + istitutoAttestante.substring(1);
            	tmp = "like";
            }
            if (istitutoAttestante.endsWith("*")) {
            	istitutoAttestante = istitutoAttestante.substring(0, istitutoAttestante.length()-1) + "%";
            	tmp = "like";
            }
            parameters.put("istitutoAttestante", istitutoAttestante.replace("*", ""));
            whereClause += " and UPPER(d.DESCRIZIONE_ATTESTANTE) " + tmp + " :istitutoAttestante ";
        }
        
        if (isNotEmptyProperty(dto.getCircuito())) {
            Integer circuito = Integer.parseInt(dto.getCircuito());
            parameters.put("circuito", circuito);
            whereClause += "and  d.ID_CFG_GATEWAY_PAGAMENTO IN " 
            		+ "(SELECT ID FROM CFG_GATEWAY_PAGAMENTO WHERE ID_CFG_FORNITORE_GATEWAY = :circuito) ";
        }
        
        if (isNotEmptyProperty(dto.getEmailVersante())) {
            String emailVersante = dto.getEmailVersante().trim();
            parameters.put("emailVersante", "%" + emailVersante.replace("*", "") + "%");
            whereClause += " and d.EMAIL_VERSANTE like :emailVersante ";
        }

        if (isNotEmptyProperty(dto.getDataCreazioneAGG())) {
            // formato della data atteso:1999-01-01 00:00:00.000000
            Timestamp data = DaoUtil.getTimestampA(dto.getDataCreazioneAYY(), dto.getDataCreazioneAMM(), dto.getDataCreazioneAGG());
            parameters.put("dataA", data);
            whereClause += "AND d.DATA_CREAZIONE < :dataA ";
        }
        
        
        
        if (isNotEmptyProperty(dto.getDataCreazioneDaGG())) {
            // formato della data atteso:1999-01-01 00:00:00.000000
            Timestamp data = DaoUtil.getTimestampDa(dto.getDataCreazioneDaYY(), dto.getDataCreazioneDaMM(), dto.getDataCreazioneDaGG());
            parameters.put("dataDa", data);
            whereClause += "AND d.DATA_CREAZIONE >= :dataDa ";
        }

        if (isNotEmptyProperty(dto.getFlagIncasso()) &&
                (dto.getFlagIncasso().toUpperCase().equals("0") || dto.getFlagIncasso().equals("1") || dto.getFlagIncasso().toUpperCase().equals("2") || dto.getFlagIncasso().equals("N"))) {
            parameters.put("flagIncasso", dto.getFlagIncasso());
            whereClause += " and p.FLAG_INCASSO = :flagIncasso ";
        }
        
        if (isNotEmptyProperty(dto.getRichiestaRevoca())) {
        	
        	if(dto.getRichiestaRevoca().equals(EnumStatoRevoca.RICHIESTA)) {
        		whereClause += " and rp.STATO_REVOCA IN ('A','N','W') ";
        	} else {
        		whereClause += " and rp.STATO_REVOCA = :richiestaRevoca  ";
        	}
            parameters.put("richiestaRevoca", dto.getRichiestaRevoca());
        }

        if (isNotEmptyProperty(dto.getIdFlusso())) {
            String idFlusso = dto.getIdFlusso().trim();
            parameters.put("idFlusso", "%" + idFlusso.replace("*", "") + "%");
            whereClause += " and p.IDENTIFICATIVO_FLUSSO like :idFlusso ";
        }
        
        if (isNotEmptyProperty(dto.getTrn())) {
        	String trn = dto.getTrn().trim();
        	parameters.put("trn", "%" + trn.replace("*", "") + "%");
            whereClause += " and p.TRN like :trn ";
        }
        
        if (isNotEmptyProperty(dto.getDataRegolamentoAGG())) {
            // formato della data atteso:1999-01-01 00:00:00.000000
            Timestamp data = DaoUtil.getTimestampA(dto.getDataRegolamentoAYY(), dto.getDataRegolamentoAMM(), dto.getDataRegolamentoAGG());
            parameters.put("dataRegolamentoA", new java.sql.Date(data.getTime()));
            whereClause += "AND p.DATA_REGOLAMENTO < :dataRegolamentoA ";
        }

        if (isNotEmptyProperty(dto.getDataRegolamentoDaGG())) {
            // formato della data atteso:1999-01-01 00:00:00.000000
            Timestamp data = DaoUtil.getTimestampDa(dto.getDataRegolamentoDaYY(), dto.getDataRegolamentoDaMM(), dto.getDataRegolamentoDaGG());
            parameters.put("dataRegolamentoDa", new java.sql.Date(data.getTime()));
            whereClause += "AND p.DATA_REGOLAMENTO >= :dataRegolamentoDa ";
        }

        if (isNotEmptyProperty(dto.getModAnonima())) {
            boolean isSearchAnonymous = EnumModalitaAnonima.ANONIMO.getChiave().equals(dto.getModAnonima());

            StringBuilder cond = new StringBuilder(" and p.OP_INSERIMENTO ");

            if (isSearchAnonymous) {
                cond.append("= ");
            } else {
                cond.append("!= ");
            }

            cond.append("'").append(EnumModalitaAnonima.ANONIMO.getChiave()).append("' ");

            whereClause += cond.toString();
        }

        if (isNotEmptyProperty(dto.getTipoSpontaneo())) {
//            pagamenti.getCondPagamento().getPendenza().getTributoEnte().getFlPredeterm())
            addCondizioni = true;
            addPendenze = true;

            filteringQuery += ", JLTPEND pdz, JLTENTR tt ";
            StringBuilder cond = new StringBuilder(" and p.ID_PENDENZA = pdz.ID_PENDENZA and (pdz.ID_ENTE = tt.ID_ENTE and pdz.CD_TRB_ENTE = tt.CD_TRB_ENTE) and tt.FL_PREDETERM = ");

            cond.append("'");

            if (EnumTipoPredeterminato.NON_PREDETERMINATO.getChiave().equals(dto.getTipoSpontaneo())) {
                cond.append(EnumTipoPredeterminato.NON_PREDETERMINATO.getChiave());
            } else {
                cond.append(EnumTipoPredeterminato.PREDEDERMINATO.getChiave());
            }

            cond.append("'");

            whereClause += cond.toString();
        }

        if (isNotEmptyProperty(dto.getIdEnte())) {
            parameters.put("idEnte", dto.getIdEnte().toUpperCase());
            whereClause += " and e.ID_ENTE = :idEnte ";
        }
        if (isNotEmptyProperty(dto.getIdIntestatarioEnte())) {
            parameters.put("idIntestatarioEnte", dto.getIdIntestatarioEnte());
            whereClause += " and e.INTESTATARIO = :idIntestatarioEnte ";
        }
        
        if (isNotEmptyProperty(dto.getIdPagamento())) {
        	addCondizioni = true;
            whereClause += " and c.ID_PAGAMENTO = :idPagamento ";
            parameters.put("idPagamento", dto.getIdPagamento());
        }
        if (isNotEmptyProperty(dto.getIuv())) {
        	addCondizioni = true;
            whereClause += " and d.IUV = :iuv ";
            parameters.put("iuv", dto.getIuv());
        }
        if (isNotEmptyProperty(dto.getCodPagamento())) {
            parameters.put("codPagamento", dto.getCodPagamento());
            whereClause += " and d.COD_PAGAMENTO = :codPagamento ";
        }

        if (isNotEmptyProperty(dto.getCodFiscDebitore())) {
            addDestinatari = true;
            addCondizioni = true;
            String cfDebitore = dto.getCodFiscDebitore().trim().toUpperCase();
            parameters.put("codFiscDebitore", "%" + cfDebitore.replace("*", "") + "%");
            whereClause += " and dest.CO_DESTINATARIO like :codFiscDebitore ";
        }
        if (isNotEmptyProperty(dto.getIdPendenza())) {
            parameters.put("idPendenza", dto.getIdPendenza());
            whereClause += " and p.ID_PENDENZAENTE = :idPendenza ";

        }
        if (isNotEmptyProperty(dto.getImportoPagamentoA())) {
            parameters.put("impPagatoA", dto.getImportoPagamentoA());
            whereClause += " and p.IM_PAGATO <= :impPagatoA ";

        }
        if (isNotEmptyProperty(dto.getImportoPagamentoDa())) {
            parameters.put("impPagatoDa", dto.getImportoPagamentoDa());
            whereClause += " and p.IM_PAGATO >= :impPagatoDa ";

        }
        if (isNotEmptyProperty(dto.getModPagamento())) {
            parameters.put("modPagamento", dto.getModPagamento().trim().toUpperCase());
            addModalitaPagamenti = true;
            //whereClause += " and m.ID = :modPagamento ";
            whereClause += " and d.ID_CFG_GATEWAY_PAGAMENTO = :modPagamento ";
        }
        if (isNotEmptyProperty(dto.getModPagamentoAF())) {
        	parameters.put("modPagamentoAF", dto.getModPagamentoAF());
        	addModalitaPagamenti = true;
        	whereClause += " and d.ID_CFG_GATEWAY_PAGAMENTO IN "
        			+ "(SELECT ID FROM CFG_GATEWAY_PAGAMENTO WHERE ID_CFG_MODALITA_PAGAMENTO = :modPagamentoAF) ";
        }
        
        if (dto.getModelloVersamento() != null && !dto.getModelloVersamento().isEmpty()) {            
        	addModalitaPagamenti = true;           
        	parameters.put("modVersamento", dto.getModelloVersamento());
            whereClause += " and d.ID_CFG_GATEWAY_PAGAMENTO IN (select ID FROM CFG_GATEWAY_PAGAMENTO WHERE MODELLO_VERSAMENTO IN (:modVersamento)) ";
        }
        if (isNotEmptyProperty(dto.getModPagamentoPsp())) {
            parameters.put("sysId", dto.getModPagamentoPsp().substring(0, dto.getModPagamentoPsp().indexOf(",")));
            parameters.put("subSysId", dto.getModPagamentoPsp().substring(dto.getModPagamentoPsp().indexOf(",")+1));
            addModalitaPagamenti = true;
            whereClause += " and cfg.SYSTEM_ID = :sysId and cfg.SUBSYSTEM_ID = :subSysId ";
            if (isNotEmptyProperty(dto.getTipoVersamento())) {
            	parameters.put("tipoVersamento", dto.getTipoVersamento());
            	whereClause += " and cfg.TIPO_VERSAMENTO = :tipoVersamento ";
            }
        }        
        if (isNotEmptyProperty(dto.getIdPSP())) {
        	parameters.put("idPSP", dto.getIdPSP());
        	addModalitaPagamenti = true;
        	whereClause += " and cfg.SYSTEM_ID = :idPSP ";
        }
        if (isNotEmptyProperty(dto.getStatoPagamento())) {
            parameters.put("statoPagamento", dto.getStatoPagamento().trim().toUpperCase());
            whereClause += " and p.ST_PAGAMENTO = :statoPagamento ";
        }

        if (isNotEmptyProperty(dto.getIdTributo())) {
            parameters.put("idTributo", dto.getIdTributo());
            whereClause += " and p.CD_TRB_ENTE = :idTributo ";
        } else {
        	 if (!dto.getIdTributoLista().isEmpty()) {
        		 parameters.put("idTributoLista", dto.getIdTributoLista());
                 whereClause += " and p.CD_TRB_ENTE in (:idTributoLista) ";
        	 }
        }

        if (isNotEmptyProperty(dto.getAnnoRif())) {
            if (!addPendenze) {
                whereClause += "  and p.ID_PENDENZA = pdz.ID_PENDENZA ";
                filteringQuery += ", JLTPEND pdz ";
                addPendenze = true;
            }
            parameters.put("annoRif", dto.getAnnoRif());
            whereClause += " and pdz.ANNO_RIFERIMENTO = :annoRif ";
        }

        if (addModalitaPagamenti) {
            filteringQuery += ", CFG_GATEWAY_PAGAMENTO cfg, CFG_MODALITA_PAGAMENTO m ";
            // whereClause += " and cfg.ID=d.ID_CFG_GATEWAY_PAGAMENTO and cfg.ID_CFG_MODALITA_PAGAMENTO = m.ID ";
            whereClause += " and cfg.ID=d.ID_CFG_GATEWAY_PAGAMENTO and cfg.ID_CFG_MODALITA_PAGAMENTO = m.ID ";
        }

        if (addCondizioni) {
            whereClause += " and p.ID_CONDIZIONE = c.ID_CONDIZIONE ";
            filteringQuery += ", CONDIZIONI c ";
        }
        if (addDestinatari) {
            whereClause += " and dest.ID_PENDENZA = pdz.ID_PENDENZA ";
            filteringQuery += ", JLTDEPD dest ";
            if (!addPendenze) {
                whereClause += "  and p.ID_PENDENZA = pdz.ID_PENDENZA ";
                filteringQuery += ", JLTPEND pdz ";
                addPendenze = true;
            }
        }
        
        
        if ("true".equalsIgnoreCase(dto.getWithRiaccredito())) { 
        	
        	whereClause += " and p.FLAG_INCASSO = '2' ";
        	
        } else
        if ("true".equalsIgnoreCase(dto.getWithQuietanza())) {
        	
        	//ora l'attesatazione del pagamento viene rilasciata 
        	//a fronte del pagamento in stato ES
        	whereClause += " and p.ST_PAGAMENTO='ES' ";
        	
        }

        String q = filteringQuery + whereClause + orderClause;

        LOGGER.debug("[getQueryPagamenti] - query: " + q);

        return q;


    }


    @Override
    public Long contaPagamentiQuietanzati(ContainerDTO inputDTO) {
        Long retList = null;
        LOGGER.debug("contaPagamentiQuietanzati - start");

        DistintePagamentoRicercaDTO dto = (DistintePagamentoRicercaDTO) inputDTO.getInputDTOList().get(1);
        dto.setWithQuietanza("true");
        
        try {

            Map<String, Object> parameters = new HashMap<String, Object>();

            String query = getQueryPagamentiConta(dto, parameters);

            System.out.println("------------------------------------------------");
            System.out.println("query: " + query);
            System.out.println("parameters keys: " + StringUtils.join(parameters.keySet(), ","));
            System.out.println("parameters values: " + StringUtils.join(parameters.values(), ","));
            System.out.println("------------------------------------------------");
            Query q = em.createNativeQuery(query);

            for (String param : parameters.keySet()) {
                q.setParameter(param, parameters.get(param));
            }

            retList = ((Number) q.getSingleResult()).longValue();//888


        } catch (Exception e) {
            LOGGER.error("error on contaPagamentiQuietanzati ", e);
            e.printStackTrace();
            throw new DAORuntimeException(e);
        }
        System.out.println("contaPagamentiQuietanzati - n. risultati: " + retList);
        LOGGER.debug("contaPagamentiQuietanzati - n. risultati: " + retList);
        return retList;
    }
    
    @Override
    public Long contaRicevuteTelematiche(ContainerDTO containerDTO) {
    	Long numRicevute = null;
        LOGGER.debug(this.getClass().getName() + "::contaRicevuteTelematiche::START");
		try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            CondizioniRicercaVO condizioniRicercaVO = (CondizioniRicercaVO)containerDTO.getInputDTO();
            
            String query = getQueryRicevuteTelematiche(condizioniRicercaVO, true, parameters);
            LOGGER.info("------------------------------------------------");
            LOGGER.info("query: " + query);
            LOGGER.info("parameters keys: " + StringUtils.join(parameters.keySet(), ","));
            LOGGER.info("parameters values: " + StringUtils.join(parameters.values(), ","));
            LOGGER.info("------------------------------------------------");
            
            Query countQuery = em.createQuery(query);
			Number totalRowsNumber = (Number) querySetup(countQuery, parameters).getSingleResult();
			numRicevute = totalRowsNumber.longValue();

        } catch (Exception e) {
            LOGGER.debug(this.getClass().getName() + "::contaRicevuteTelematiche::ERROR", e);
            e.printStackTrace();
            throw new DAORuntimeException(e);
        }
        LOGGER.debug(this.getClass().getName() + "::contaRicevuteTelematiche::num risultati: " + numRicevute);
        return numRicevute;
    }


    @Override
    public <T extends TributoStrutturato> List<Pagamenti> getExistingPayments(T tributo, String cdTrbEnte) {

        List<Pagamenti> exsistingPayments = new ArrayList<Pagamenti>();

        //
        // Recupero la lista dei tributi strutturati che soddisfano i criteri di
        // ricerca. La query specifica � definita a livello di addon.
        //
        AddOnManager<T> addOnManager = AddOnManagerFactory.getAddOnManager(tributo.getDettaglioStrutturato().getIdEnte(), cdTrbEnte, tributo.getTipoTributo());
        AddOnCheckHelper<T> searchHelper = addOnManager.getCheckHelper();
        List<T> rawList = searchHelper.checkExistingPayments(tributo, em);
        if (rawList != null) {
            //
            // Per ogni tributo recupero i pagamenti
            // ricerca. La query specifica � definita a livello di addon.
            //
            for (T t : rawList) {
                List<Pagamenti> pagamentiTrb = getPaymentsFromTributo(t);
                if (pagamentiTrb != null) {
                    exsistingPayments.addAll(pagamentiTrb);
                }
            }
        }
        return exsistingPayments;
    }

    private List<Pagamenti> getPaymentsFromTributo(TributoStrutturato tributo) {
//
// vecchia query problematica ... (nessun indice su ID_TRIBUTO_STRUTTURATO della JLTPEND)
//
//    	String theQuery = "from Pagamenti p "
//    			+ "where p.cdTrbEnte = :cdTrbEnte "
//    			+ "  and p.idEnte = :idEnte "
////    			+ "  and p.idEnte = :idEnte "
////    			+ "  and p.stPagamento = 'ES' "
////    			+ "  and p.stPagamento = 'EF' "
////    			+ "  and p.stPagamento = 'IC' "
//    			+ "  and p.idPendenzaente in ( "
//    			+ "        select idPendenzaente from Pendenza pend "
//    			+ "         where pend.tributoStrutturato = :tributo"
//    			+ "      ) ";
//
//    	Query q = em.createQuery(theQuery);
//    	q.setParameter("cdTrbEnte", tributo.getTipoTributo());
//    	q.setParameter("idEnte", tributo.getDettaglioStrutturato().getIdEnte());
//    	q.setParameter("tributo", tributo);
//
//    	List<Pagamenti> pagamenti = q.getResultList();
//    	return pagamenti;

        List<Pagamenti> pagamenti = new ArrayList<Pagamenti>();

        Pendenza pend = em.find(Pendenza.class, tributo.getIdPendenza());
        Set<CondizionePagamento> condizioni = pend.getCondizioni();
        if (condizioni != null) {
            for (CondizionePagamento condizionePagamento : condizioni) {
                Set<Pagamenti> pgmts = condizionePagamento.getPagamenti();
                if (pgmts != null) {
                    pagamenti.addAll(pgmts);
                }
            }
        }
        return pagamenti;
    }

    @Override
    public Pagamenti updatePagamentoIdRepository(Long idPagamento, Long repoId, String idDocumentoExt) {

        Pagamenti pagamento = null;

        try {

            pagamento = getById(Pagamenti.class, idPagamento);

            pagamento.setIdDocumentoRepository(repoId);

            pagamento.setIdDocumentoExt(idDocumentoExt);

            pagamento = this.update(pagamento);


        } catch (StaleObjectStateException sose) {

            updatePagamentoIdRepository(idPagamento, repoId, idDocumentoExt);

        } catch (OptimisticLockException ole) {

            updatePagamentoIdRepository(idPagamento, repoId, idDocumentoExt);

        } catch (Exception e) {

            LOGGER.error("error saving pagamento, ID = " + idPagamento, e);

            throw new DAORuntimeException(e);
        }

        return pagamento;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pagamenti> getPagamentiByIdDistinta(Long idDistinta) {

        Query q = em.createNamedQuery("getPagamentiByIdDistinta");
        q.setParameter("idDistinta", idDistinta);

        return q.getResultList();
    }


    @Override
    public List<Pagamenti> getPagamentiCreditore(ContainerDTO containerDTO) {
        List retList = null;

        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            PagamentiFilter inputDTO = (PagamentiFilter) containerDTO.getInputDTO();
            PaginationJPQLQuery queries = buildJPQLQueryCondizioneCreditore(inputDTO, parameters);

            PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();

            PagingData pagingData = containerDTO.getPagingData();

            retList = paginateByJPQLQuery(queries, pagingCriteria, pagingData, parameters);

            return retList;

        } catch (Exception e) {
            LOGGER.error(e);
            throw new DAORuntimeException(e);
        }


    }


    private PaginationJPQLQuery buildJPQLQueryCondizioneCreditore(PagamentiFilter filter, Map<String, Object> parameters) {

        final String WHERE = " where ", AND = " and ";
        final String orderBy = " order by pagamento.tsInserimento desc ";
        parameters.put("idCreditore", filter.getIdCreditore());

        StringBuilder from = new StringBuilder(" from Pagamenti as pagamento join pagamento.condPagamento as condizione");
        String selectExpression = " distinct pagamento ";
        String selectCtExpression = " count(distinct pagamento) ";

        List<String> conditions = new ArrayList<String>();
        Map<String, String> joins = new LinkedHashMap<String, String>();

        conditions.add(" condizione.ente.idEnte = :idCreditore "); // mandatory

        final String joinPendenza = " join condizione.pendenza as pendenza ";
        if (isNotEmptyProperty(filter.getTipoDebito())) {
            conditions.add("pagamento.cdTrbEnte = :tipoDebito");
            parameters.put("tipoDebito", filter.getTipoDebito());
        }

        if (isNotEmptyProperty(filter.getIdDebito())) {
            joins.put("pendenza", joinPendenza);
            conditions.add(" pendenza.idPendenzaente = :idDebito ");
            parameters.put("idDebito", filter.getIdDebito());
        }


        if (isNotEmptyProperty(filter.getCodiceFiscaleDebitore())) {
            joins.put("pendenza", joinPendenza);
            joins.put("destinatario", " join pendenza.destinatari as destinatario ");
            conditions.add("destinatario.coDestinatario = :codiceFiscaleDebitore");
            parameters.put("codiceFiscaleDebitore", filter.getCodiceFiscaleDebitore());
        }

        if (isNotEmptyProperty(filter.getIdentificativoUnivocoVersamento())) {
            joins.put("distinta", " join pagamento.flussoDistinta as distinta ");
            conditions.add(" distinta.iuv = :identificativoUnivocoPagamento ");
            parameters.put("identificativoUnivocoPagamento", filter.getIdentificativoUnivocoVersamento());
        }

        if (filter.getDataOraPagamentoDa() != null) {
            conditions.add(" pagamento.tsInserimento >= :dataOraPagamentoDa ");
            parameters.put("dataOraPagamentoDa", filter.getDataOraPagamentoDa());
        }

        if (filter.getDataOraPagamentoA() != null) {
            conditions.add(" pagamento.tsInserimento <= :dataOraPagamentoA ");
            parameters.put("dataOraPagamentoA", filter.getDataOraPagamentoA());
        }
        if (isNotEmptyProperty(filter.getIdPagamento())) {
            conditions.add(" condizione.idPagamento = :idPagamento ");
            parameters.put("idPagamento", filter.getIdPagamento());
        }

        if (filter.getEnumStatoPagamento() != null) {
            conditions.add(" pagamento.stPagamento = :statoPagamento ");
            parameters.put("statoPagamento", filter.getEnumStatoPagamento().getChiave());
        }

        if (filter.getFlagIncasso() != null) {
            conditions.add(" pagamento.flagIncasso = :flagIncasso ");
            parameters.put("flagIncasso", filter.getFlagIncasso().getChiave());
        }


        StringBuilder conditionsBuilder = new StringBuilder();

        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                conditionsBuilder.append(AND);
            }
            conditionsBuilder.append(conditions.get(i));
        }


        for (String join : joins.values()) {
            from.append(join);
        }


        String jpqlQuery = "select " + selectExpression + from + WHERE + conditionsBuilder + orderBy;

        LOGGER.info(" JPQL query : " + jpqlQuery);

//        String countJPQLQuery = "select " + selectCtExpression + from.toString().replace("fetch", " ") + WHERE + conditionsBuilder;
        String countJPQLQuery = "select " + selectCtExpression + from.toString() + WHERE + conditionsBuilder; // TODO da rivedere, trovare metodo generico

        return new PaginationJPQLQuery(jpqlQuery, countJPQLQuery);
    }

    @Override
    public StatistichePagamento getStatisticheCreditore(StatistichePagamentoFilter statistichePagamentoFilters) {

        {
            List<StatisticheTestataQueryDTO> statisticheTestataQueryDTO = null;
            try {
                final Enti ente = entiDao.readEnte(statistichePagamentoFilters.getCreditore());
                statistichePagamentoFilters.setIdEnte(ente.getIdEnte());

                Map<String, Object> parameters = new HashMap<String, Object>();

                /**
                 *  query nativa SQL ha problemi con i CHAR(n) (stPagamento) su Oracle, quindi si è optato per query JPQL
                 *  vedi http://stackoverflow.com/questions/4873201/hibernate-native-query-char3-column
                 */
                String statTestataQrString = getStatistichePagamentoJPQL(statistichePagamentoFilters, parameters);

                final Query statisticheTestataQuery = em.createQuery(statTestataQrString);

                for (String param : parameters.keySet()) {
                    statisticheTestataQuery.setParameter(param, parameters.get(param));
                }

                statisticheTestataQueryDTO = statisticheTestataQuery.getResultList();
                StatistichePagamento statistichePagamento = new StatistichePagamento();
                statistichePagamento.setCreditore(ente.getCodiceEnte());
                statistichePagamento.setDataOraPagamentoA(statistichePagamentoFilters.getDataOraPagamentoA());
                statistichePagamento.setDataOraPagamentoDa(statistichePagamentoFilters.getDataOraPagamentoDa());
                statistichePagamento.setDescrizioneCreditore(ente.getDenominazione());
                statistichePagamento.setTipoDebito(statistichePagamentoFilters.getTipoDebito());

                BigDecimal totPagamentiEseguiti = BigDecimal.ZERO;
                BigDecimal totPagamentiIncassati = BigDecimal.ZERO;
                long numPagamentiEseguiti = 0, numPagamentiIncassati = 0;


                final ArrayList<StatistichePagamento.StatistichePagamentiPerStato> statistichePagamentiPerStatoList = new ArrayList<StatistichePagamento.StatistichePagamentiPerStato>();

                StatisticheTestataQueryDTO prevRecord = null;
                StatistichePagamento.StatistichePagamentiPerStato statistichePagamentiPerStato = null;
                for (StatisticheTestataQueryDTO record : statisticheTestataQueryDTO) {
                    EnumStatoPagamento statoPagamento = record.getStatoPagamento();

                    if (isEseguito(statoPagamento)) {
                        totPagamentiEseguiti = totPagamentiEseguiti.add(record.getTotale());
                        numPagamentiEseguiti += record.getNumeroPagamenti();
                        if (isIncassato(statoPagamento, record.getStatoIncasso())) {
                            totPagamentiIncassati = totPagamentiIncassati.add(record.getTotale());
                            numPagamentiIncassati += record.getNumeroPagamenti();
                        }
                    }

                    if (prevRecord == null || record.getStatoPagamento() != prevRecord.getStatoPagamento()) {
                        statistichePagamentiPerStato = new StatistichePagamento.StatistichePagamentiPerStato();
                        statistichePagamentiPerStatoList.add(statistichePagamentiPerStato);
                        statistichePagamentiPerStato.setStatoPagamento(record.getStatoPagamento());
                    }

                    Long numPagamenti = statistichePagamentiPerStato.getNumPagamenti() == null ? 0 : statistichePagamentiPerStato.getNumPagamenti();
                    statistichePagamentiPerStato.setNumPagamenti(numPagamenti + record.getNumeroPagamenti());

                    prevRecord = record;
                }


                statistichePagamento.setNumPagamentiEseguiti(numPagamentiEseguiti);
                statistichePagamento.setNumPagamentiIncassati(numPagamentiIncassati);
                statistichePagamento.setTotPagamentiEseguiti(totPagamentiEseguiti);
                statistichePagamento.setTotPagamentiIncassati(totPagamentiIncassati);
                statistichePagamento.setTipoDebito(statistichePagamentoFilters.getTipoDebito());

                IntestatariCommon intestatario = ente.getIntestatarioobj();
                statistichePagamento.setCodFiscaleCreditore(intestatario.getLaplIForm());

                if (statistichePagamentoFilters.isStatistichePerStato()) {
                    statistichePagamento.setStatistichePerStato(statistichePagamentiPerStatoList);
                }


                if (statistichePagamentoFilters.isStatistichePerCanale()) {
                    populateStatisticheWithPagamentiEseguitiPerCanaleStato(statistichePagamento, statistichePagamentoFilters, parameters);
                }


                return statistichePagamento;
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero delle statistiche ", e);
                throw new DAORuntimeException(e);
            }


        }
    }

    @Override
    public Long aggiornaInformazioniTransazioneIncasso(Long idFisico, String iuvPagamento, String codiceContestoPagamento, Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso) {
        Query getPagamentoByDistintaContesto = em.createNamedQuery("getPagamentoByDistintaContesto");

        getPagamentoByDistintaContesto.setParameter("idFisico", idFisico);
        getPagamentoByDistintaContesto.setParameter("iuv", iuvPagamento);
        getPagamentoByDistintaContesto.setParameter("codiceContestoPagamento", codiceContestoPagamento);

        List results = getPagamentoByDistintaContesto.getResultList();

        Pagamenti pagamento = results.isEmpty() ? null : (Pagamenti) results.get(0);

        Long updatedId = -1L;
        if (pagamento != null) {
            updatedId = pagamento.getId();
            LOGGER.debug("Updating pagamento " + pagamento.getId());
            pagamento.setMittRendicontazioneIncasso(informazioniTransazioneIncasso.getIdentiticativoPspIncasso());
            pagamento.setDataRegolamento(informazioniTransazioneIncasso.getDataEsecuzioneIncasso());
            pagamento.setTRN(informazioniTransazioneIncasso.getTrnIncasso());
            pagamento.setTotaleRendicontazioneIncasso(informazioniTransazioneIncasso.getImportoTotaleTransazioneIncasso());
            pagamento.setCodRendicontazioneIncasso(informazioniTransazioneIncasso.getTipoFlussoIncasso());
            pagamento.setIdentificativoFlusso(informazioniTransazioneIncasso.getIdentificativoFlussoIncasso());
        }



        return updatedId;
    }

    static class PagamentiPerCanaleKey {
        private String applicationId;
        private String getSystemId;
        private EnumTipoVersamento tipoVersamento;

        static PagamentiPerCanaleKey getLogicKey(StatistichePerCanaleStatoQueryDTO record) {
            return new PagamentiPerCanaleKey(record);
        }

        PagamentiPerCanaleKey(StatistichePerCanaleStatoQueryDTO record) {
            this(record.getApplicationId(),record.getSystemId(),record.getTipoVersamento());
        }

        PagamentiPerCanaleKey(String applicationId, String getSystemId, EnumTipoVersamento tipoVersamento) {
            this.applicationId = applicationId;
            this.getSystemId = getSystemId;
            this.tipoVersamento = tipoVersamento;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PagamentiPerCanaleKey that = (PagamentiPerCanaleKey) o;

            if (!applicationId.equals(that.applicationId)) return false;
            if (!getSystemId.equals(that.getSystemId)) return false;
            return tipoVersamento == that.tipoVersamento;

        }

        @Override
        public int hashCode() {
            int result = applicationId.hashCode();
            result = 31 * result + getSystemId.hashCode();
            result = 31 * result + (tipoVersamento != null ? tipoVersamento.hashCode() : 0);
            return result;
        }
    }


    private void populateStatisticheWithPagamentiEseguitiPerCanaleStato(StatistichePagamento statistichePagamento, StatistichePagamentoFilter statistichePagamentoFilters, Map<String, Object> parameters) {
        String queryStringCanaleStato = getStatistichePagamentoPerCanaleEStatoJPQL(statistichePagamentoFilters, parameters);
        final Query statoCanaleQuery = em.createQuery(queryStringCanaleStato);

        for (String param : parameters.keySet()) {
            statoCanaleQuery.setParameter(param, parameters.get(param));
        }

        List<StatistichePerCanaleStatoQueryDTO> statistichePerCanaleStatoQueryResultSet = statoCanaleQuery.getResultList();


        StatistichePerCanaleStatoQueryDTO prevRecord = null;
        StatistichePagamento.StatistichePagamentiPerCanalePagamento statPagPerCanale = null;
        StatistichePagamento.StatistichePagamentiPerStato statistichePagamentiPerStato = null;
        final List<StatistichePagamento.StatistichePagamentiPerCanalePagamento> statistichePerCanalePagamentoList = new ArrayList<StatistichePagamento.StatistichePagamentiPerCanalePagamento>();
        for (StatistichePerCanaleStatoQueryDTO record : statistichePerCanaleStatoQueryResultSet) {
            if (prevRecord == null || !getLogicKey(record).equals(getLogicKey(prevRecord))) {
                statPagPerCanale = new StatistichePagamento.StatistichePagamentiPerCanalePagamento(
                        record.getSystemId(),
                        record.getSystemName(),
                        record.getApplicationId(),
                        record.getTipoVersamento(),
                        0L,
                        BigDecimal.ZERO,
                        0L,
                        BigDecimal.ZERO
                );
                statPagPerCanale.setStatistichePerStato(new ArrayList<StatistichePagamento.StatistichePagamentiPerStato>());
                statistichePerCanalePagamentoList.add(statPagPerCanale);
                prevRecord = null; // force record change on all sub groups
            }

            if (isEseguito(record.getStatoPagamento())) {
                Long numPagamentiEseguiti =  statPagPerCanale.getNumPagamentiEseguiti() == null ? 0 : statPagPerCanale.getNumPagamentiEseguiti();
                statPagPerCanale.setNumPagamentiEseguiti(record.getNumeroPagamenti() + numPagamentiEseguiti);
                statPagPerCanale.setTotPagamentiEseguiti(statPagPerCanale.getTotPagamentiEseguiti().add(record.getTotale()));
                if (isIncassato(record.getStatoPagamento(), record.getStatoIncasso())) {
                    Long numPagamentiIncassati = statPagPerCanale.getNumPagamentiIncassati() == null ? 0 : statPagPerCanale.getNumPagamentiIncassati();
                    statPagPerCanale.setNumPagamentiIncassati(record.getNumeroPagamenti() + numPagamentiIncassati);
                    statPagPerCanale.setTotPagamentiIncassati(statPagPerCanale.getTotPagamentiIncassati().add(record.getTotale()));
                }
            }


            if (statistichePagamentoFilters.isStatistichePerStato()) {
                if (prevRecord == null || record.getStatoPagamento() != prevRecord.getStatoPagamento()) {
                    statistichePagamentiPerStato = new StatistichePagamento.StatistichePagamentiPerStato();
                    List<StatistichePagamento.StatistichePagamentiPerStato> statistichePagamentiPerStatoList = statPagPerCanale.getStatistichePerStato();
                    statistichePagamentiPerStatoList.add(statistichePagamentiPerStato);
                    statistichePagamentiPerStato.setStatoPagamento(record.getStatoPagamento());
                    statPagPerCanale.setStatistichePerStato(statistichePagamentiPerStatoList);
                }

                Long numPagamenti = statistichePagamentiPerStato.getNumPagamenti() == null ? 0 : statistichePagamentiPerStato.getNumPagamenti();
                statistichePagamentiPerStato.setNumPagamenti(numPagamenti + record.getNumeroPagamenti());
            }

            prevRecord = record;
        }
        statistichePagamento.setStatistichePerCanalePagamento(statistichePerCanalePagamentoList);
    }


    private String getStatistichePagamentoPerCanaleEStatoJPQL(StatistichePagamentoFilter statistichePagamentoFilters, Map<String, Object> parameters) {
        final String groupBy = " GROUP BY g.systemId, g.systemName, g.applicationId, g.tipoVersamento, p.stPagamento, p.flagIncasso, g.descGateway, g.systemName ";
        final String orderBy = " ORDER BY g.systemId, g.applicationId, g.tipoVersamento, p.stPagamento, p.flagIncasso ";


        String tipoDebitoCriteria = "";
        if (isNotEmptyProperty(statistichePagamentoFilters.getTipoDebito())) {
            tipoDebitoCriteria = " AND p.cdTrbEnte = :tipoDebito ";
            parameters.put("tipoDebito", statistichePagamentoFilters.getTipoDebito());
        }

        //language=JPAQL
        String query = "SELECT new it.tasgroup.iris.dto.query.support.StatistichePerCanaleStatoQueryDTO (g.systemId, g.systemName, g.applicationId, g.tipoVersamento, p.stPagamento, p.flagIncasso, count(p), sum(p.imPagato))  \n" +
                "                from Pagamenti p join p.flussoDistinta distinta join distinta.cfgGatewayPagamento as g \n" +
                "                where p.idEnte= :idEnte and p.tsInserimento >= :dataOraDa and p.tsInserimento <= :dataOraA\n" + tipoDebitoCriteria +
                groupBy + orderBy;


        return query;
    }

    private boolean isIncassato(EnumStatoPagamento stato, EnumStatoIncasso statoIncasso) {
        return stato == EnumStatoPagamento.ESEGUITO && statoIncasso == EnumStatoIncasso.RIVERSATO;
    }

    private boolean isEseguito(EnumStatoPagamento statoPagamento) {
        return statoPagamento == EnumStatoPagamento.ESEGUITO;
    }

    private String getStatistichePagamentoJPQL(StatistichePagamentoFilter statistichePagamentoFilters, Map<String, Object> parameters) {
        final String groupBy = " GROUP BY p.stPagamento, p.flagIncasso";

        String tipoDebitoCriteria = "";
        if (isNotEmptyProperty(statistichePagamentoFilters.getTipoDebito())) {
            tipoDebitoCriteria = " AND p.cdTrbEnte = :tipoDebito ";
            parameters.put("tipoDebito", statistichePagamentoFilters.getTipoDebito());
        }
        String orderBy = " order by p.stPagamento ";
        //language=JPAQL
        String statisticheTestataQueryDTO = "select new it.tasgroup.iris.dto.query.support.StatisticheTestataQueryDTO(p.stPagamento,p.flagIncasso, count(p), sum(p.imPagato)) from Pagamenti p join p.flussoDistinta d where p.idEnte = :idEnte and p.tsInserimento >= :dataOraDa and p.tsInserimento <= :dataOraA " + tipoDebitoCriteria + groupBy + orderBy;


        parameters.put("dataOraDa", statistichePagamentoFilters.getDataOraPagamentoDa());
        parameters.put("dataOraA", statistichePagamentoFilters.getDataOraPagamentoA());
        parameters.put("idEnte", statistichePagamentoFilters.getIdEnte());

        LOGGER.debug(statisticheTestataQueryDTO);
        return statisticheTestataQueryDTO;
    }
		
		@Override
        public List<CondizionePagamento> getCondizioniCreditore(ContainerDTO containerDTO) {
				List retList = null;
				
				try {
						Map<String, Object> parameters = new HashMap<String, Object>();
						CondizioniRicercaVO inputDTO = (CondizioniRicercaVO) containerDTO.getInputDTO();
						PaginationJPQLQuery queries = buildJPQLQueryCondizioni(inputDTO, parameters);
						
						PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
						
						PagingData pagingData = containerDTO.getPagingData();
						
						retList = paginateByJPQLQuery(queries, pagingCriteria, pagingData, parameters);
						
						handleAggregateData(queries, containerDTO, parameters);
						
						return retList;
						
				} catch (Exception e) {
						LOGGER.error(e);
						throw new DAORuntimeException(e);
				}
				
		}
    
		private PaginationJPQLQuery buildJPQLQueryCondizioni(CondizioniRicercaVO filter, Map<String, Object> parameters) throws ParseException {
			return buildJPQLQueryCondizioni(filter, parameters, false);
		}
		
		private PaginationJPQLQuery buildJPQLQueryCondizioniRT(CondizioniRicercaVO filter, Map<String, Object> parameters) throws ParseException {
			return buildJPQLQueryCondizioni(filter, parameters, true);
		}
    
    private PaginationJPQLQuery buildJPQLQueryCondizioni(CondizioniRicercaVO filter, Map<String, Object> parameters, boolean isRT) throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(filter.getDateFormat());
        
        final String statoPagamentoCondizioneStr = filter.getStatoPagamentoCondizione();
        final EnumStatoCondizione statoPagamentoCondizione = EnumUtils.findByChiave(statoPagamentoCondizioneStr, EnumStatoCondizione.class);
        boolean isPagato = statoPagamentoCondizione == EnumStatoCondizione.PAGATA;
        boolean isDaPagare = statoPagamentoCondizione == EnumStatoCondizione.DA_PAGARE;
        
        List<String> conditions = new ArrayList<String>();
        Map<String, String> joins = new LinkedHashMap<String, String>();
        
        conditions.add("condizione.stRiga = 'V'");

        if (isPagato) {
			joinPagamenti(joins, true);
//			conditions.add("pagamenti.idEnte = :idCreditore");
        } else {
        	joinPendenza(joins);
//        	conditions.add("pendenza.idEnte = :idCreditore");
        }
        conditions.add("condizione.ente.idEnte = :idCreditore");
        parameters.put("idCreditore", filter.getIdCreditore());

        
        // **********************
        final String idPendenza = filter.getIdPendenza(); // ATTENZIONE: questo e' il campo che distingue la ricercaCondizioni dalla visualizzaDettaglioCondizionePagamento
        if (isNotEmptyProperty(idPendenza)) {
        	joinPendenza(joins);
            addLikeCondition(idPendenza, "pendenza.idPendenza", "idPendenza", conditions, parameters);
        }
        // **********************

        
        final String idPendenzaEnte = filter.getIdPendenzaEnte();
        if (isNotEmptyProperty(idPendenzaEnte)) {
        	joinPendenza(joins);
            addLikeCondition(idPendenzaEnte, "pendenza.idPendenzaente", "idPendenzaEnte", conditions, parameters);
        }
    
        final String deCausale = filter.getCausaleDebito();
        if (isNotEmptyProperty(deCausale)) {
        	joinPendenza(joins);
            addLikeCondition(deCausale, "pendenza.deCausale", "deCausale", conditions, parameters);
        }
    
    
        final String idMittente = filter.getIdentificativoMittente();
        if (isNotEmptyProperty(idMittente)) {
        	joinPendenza(joins);
            addLikeCondition(idMittente, "pendenza.idMittente", "idMittente", conditions, parameters);
        }
    
        final String riscossore = filter.getRiscossore();
        if (isNotEmptyProperty(riscossore)) {
        	joinPendenza(joins);
            addLikeCondition(riscossore, "pendenza.coRiscossore", "coRiscossore", conditions, parameters);
        }
    
        final String riferimento = filter.getRiferimento();
        if (isNotEmptyProperty(riferimento)) {
        	joinPendenza(joins);
            addLikeCondition(riferimento, "pendenza.riferimento", "riferimento", conditions, parameters);
        }
	
		if (isNotEmptyProperty(filter.getDataEmissioneDa())) {
			joinPendenza(joins);
            conditions.add("pendenza.tsInserimento >= :dataInserimentoDa");
            final Date before = new MidnightDate(dateFormat.parse(filter.getDataEmissioneDa())).early();
            parameters.put("dataInserimentoDa", before);
		}
	
		if (isNotEmptyProperty(filter.getDataEmissioneA())) {
			joinPendenza(joins);
			conditions.add("pendenza.tsInserimento < :dataInserimentoA");
            final Date after = new MidnightDate(dateFormat.parse(filter.getDataEmissioneA())).late();
			parameters.put("dataInserimentoA", after);
		}
    
        if (isNotEmptyProperty(filter.getAnnoRif())) {
        	joinPendenza(joins);
            conditions.add("pendenza.annoRiferimento = :annoRif");
            parameters.put("annoRif", Integer.valueOf(filter.getAnnoRif()));
        }
    
    
        if (!filter.getIdTributoLista().isEmpty()) {
        	joinPendenza(joins);
			conditions.add("pendenza.tributoEnte.cdTrbEnte IN (:tributiLista)");
			parameters.put("tributiLista", filter.getIdTributoLista());
		}
    
        if (!filter.getTipoDebito().isEmpty()) {
			if (isPagato) {
				joinPagamenti(joins, true);
				conditions.add("pagamenti.cdTrbEnte = :tipoDebito");
			} else {
				joinPendenza(joins);
				conditions.add("pendenza.tipoDebito = :tipoDebito");
			}
			parameters.put("tipoDebito", filter.getTipoDebito());
		}
    
    
        final String anagrafica = filter.getAnagrafica();
        if (isNotEmptyProperty(anagrafica)) {
            joinPendenzaDestinatari(joins);
            addLikeCondition(anagrafica, "destinatario.deDestinatario", "anagrafica", conditions, parameters);
        }
    
        final String codiceFiscaleDebitore = filter.getCodiceFiscaleDebitore();
        if (isNotEmptyProperty(codiceFiscaleDebitore)) {
            joinPendenzaDestinatari(joins);
            addLikeCondition(codiceFiscaleDebitore.trim().toUpperCase(), "destinatario.coDestinatario", "codiceFiscaleDebitore", conditions, parameters);
        }
    
        final String versante = filter.getCodiceVersante();
        if (isNotEmptyProperty(versante)) {
            joinPendenzaDestinatari(joins);
            addLikeCondition(versante.trim().toUpperCase(), "destinatario.codAlternativoDebitore", "versante", conditions, parameters);
        }
        
        final String causaleVersamento = filter.getCausaleVersamento();
        if (isNotEmptyProperty(causaleVersamento)) {
        	addLikeCondition(causaleVersamento, "condizione.causalePagamento", "causalePagamento", conditions, parameters);
        }
    
        
        if (isNotEmptyProperty(filter.getImportoPagamentoDa())) {
            conditions.add("condizione.imTotale >= :importoDa");
            parameters.put("importoDa", new BigDecimal(filter.getImportoPagamentoDa()));
        }
        
        if (isNotEmptyProperty(filter.getImportoPagamentoA())) {
            conditions.add("condizione.imTotale <= :importoA");
            parameters.put("importoA", new BigDecimal(filter.getImportoPagamentoA()));
        }
    
    
        final String iuv = filter.getIUV();
        if (isNotEmptyProperty(iuv)) {
            addLikeCondition(iuv, "condizione.idPagamento", "idPagamento", conditions, parameters);
        }
        
        if (isNotEmptyProperty(filter.getDataScadenzaDa())) {
            conditions.add("condizione.dtScadenza  >= :dataScadenzaDa");
            parameters.put("dataScadenzaDa", dateFormat.parse(filter.getDataScadenzaDa()));
        }
        
        if (isNotEmptyProperty(filter.getDataScadenzaA())) {
            conditions.add("condizione.dtScadenza <= :dataScadenzaA");
            parameters.put("dataScadenzaA", dateFormat.parse(filter.getDataScadenzaA()));
        }
        
        
        if (isNotEmptyProperty(filter.getDataPagamentoDa())) {
            joinPagamenti(joins, isPagato);
            conditions.add("pagamenti.tsDecorrenza >= :dataOraPagamentoDa");
            parameters.put("dataOraPagamentoDa", new MidnightDate(dateFormat.parse(filter.getDataPagamentoDa())).early());
        }
    
        if (isNotEmptyProperty(filter.getDataPagamentoA())) {
            joinPagamenti(joins, isPagato);
            conditions.add("pagamenti.tsDecorrenza < :dataOraPagamentoA");
            parameters.put("dataOraPagamentoA", new MidnightDate(dateFormat.parse(filter.getDataPagamentoA())).late());
        }
    
    
        String trn = filter.getTrn();
        if (isNotEmptyProperty(trn)) {
            joinPagamenti(joins, isPagato);
            addLikeCondition(trn, "pagamenti.TRN", "trn", conditions, parameters);
        }
    
        final String idFlussoRiversamento = filter.getIdFlussoRiversamento();
        if (isNotEmptyProperty(idFlussoRiversamento)) {
            joinPagamenti(joins, isPagato);
            addLikeCondition(idFlussoRiversamento, "pagamenti.identificativoFlusso", "idFlussoRiversamento", conditions, parameters);
        }
    
        if (isNotEmptyProperty(filter.getDataRegolamentoDa())) {
            joinPagamenti(joins, isPagato);
            conditions.add("pagamenti.dataRegolamento >= :dataRegolamentoDa");
            parameters.put("dataRegolamentoDa", new MidnightDate(dateFormat.parse(filter.getDataRegolamentoDa())).early());
        }
    
        if (isNotEmptyProperty(filter.getDataRegolamentoA())) {
            joinPagamenti(joins, isPagato);
            conditions.add("pagamenti.dataRegolamento < :dataRegolamentoA");
            parameters.put("dataRegolamentoA", new MidnightDate(dateFormat.parse(filter.getDataRegolamentoA())).late());
        }
        
        if(isRT)  {
        	addForStatoCondizioneRT(conditions, joins);
        } else {
            if (isDaPagare) {
                joinPagamenti(joins, false);
                conditions.add("condizione.stPagamento = 'N'");
                conditions.add("(pagamenti.id is null or 0 = (select count(pagamenti.id) from pagamenti p where p.condPagamento.idCondizione = condizione.id and p.stPagamento = 'ES') or pagamenti.id is null)"); 
            } else if (isPagato) {
                joinPagamenti(joins, true);
                conditions.add("pagamenti.stPagamento = 'ES'");
            }
        }
        
        if (filter.getFlagIncasso() != null) {
            joinPagamenti(joins, isPagato);
            conditions.add("pagamenti.flagIncasso = :flagIncasso");
            parameters.put("flagIncasso", filter.getFlagIncasso());
        }
        
        if (isNotEmptyProperty(filter.getUtenteCreatore())) {
            joinDistinta(joins, isPagato);
            final String utenteCreatore = filter.getUtenteCreatore();
            addLikeCondition(utenteCreatore, "distinta.utentecreatore", "utenteCreatore", conditions, parameters);
        }
    
        final String istitutoAttestante = filter.getIstitutoAttestante();
        if (isNotEmptyProperty(istitutoAttestante)) {
            joinDistinta(joins, isPagato);
            addLikeCondition(istitutoAttestante, "distinta.descrizioneAttestante", "descrizioneAttestante", conditions, parameters);
        }
    
    
        final String emailVersante = filter.getEmailVersante();
        if (isNotEmptyProperty(emailVersante)) {
            joinDistinta(joins, isPagato);
            addLikeCondition(emailVersante, "distinta.emailVersante", "emailVersante", conditions, parameters);
        }
    
        final String idFiscaleCreditore = filter.getIdFiscaleCreditore();
        if (isNotEmptyProperty(idFiscaleCreditore)) {
            joinDistinta(joins, isPagato);
            addLikeCondition(idFiscaleCreditore, "distinta.idFiscaleCreditore", "idFiscaleCreditore", conditions, parameters);
        }
    
        final String codPagamentoIris = filter.getCodPagamento();
        if (isNotEmptyProperty(codPagamentoIris)) {
            joinDistinta(joins, isPagato);
            addLikeCondition(codPagamentoIris, "distinta.codPagamento", "codPagamentoIris", conditions, parameters);
        }
        if (isRT) {
            addForCircuitoRT(conditions, joins);
        } else {
        	addForCircuito(filter, parameters, conditions, joins);
        }
        
        final String idPsp = filter.getIdPsp();
        if (isNotEmptyProperty(idPsp)) {
            joinPagamenti(joins, isPagato);
            addLikeCondition(idPsp, "distinta.cfgGatewayPagamento.systemId", "idPsp", conditions, parameters);
        }
        
        PaginationJPQLQuery paginationJPQLQuery = null;
        		
        StringBuilder conditionsBuilder = new StringBuilder();
        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                conditionsBuilder.append(" and ");
            }
            conditionsBuilder.append(conditions.get(i));
        }
        
        if (isRT) {
        	if (conditions.size() > 0) {
            	conditionsBuilder.append(" and ");
            }	
        	conditionsBuilder.append(addForDocumentiNDP(joins));
        }
        
        StringBuilder fromJoin = new StringBuilder();
        for (String join : joins.values()) {
        	fromJoin.append(join);
        }
        
        String countJPQLQuery = "select count(distinct condizione.id) from CondizionePagamento as condizione " + fromJoin +  "where " + conditionsBuilder;
        LOGGER.info(" JPQL COUNT query : " + countJPQLQuery);
        String jpqlQuery = "select distinct condizione from CondizionePagamento as condizione " + fromJoin + "where " + conditionsBuilder + " order by condizione.tsInserimento desc";
        LOGGER.info(" JPQL query...... : " + jpqlQuery);
        
        paginationJPQLQuery = new PaginationJPQLQuery(jpqlQuery, countJPQLQuery);
        if (!isRT && filter.isSommaImporti()) {
            String sumJPQLQuery = "select sum(condizione.imTotale) from CondizionePagamento as condizione " + fromJoin + "where " + conditionsBuilder;
            LOGGER.info(" JPQL SUM query.. : " + sumJPQLQuery);
            paginationJPQLQuery.setSumQuery(sumJPQLQuery);
        }

        return paginationJPQLQuery;
    }
    
    private String addForDocumentiNDP(Map<String, String> joins) {
    	joinDistinta(joins, true); // questo metodo e' chiamato solo ed esclusivamente per il pagato
    	return "EXISTS ( select doc.id from GiornaleEventiDocumentiNDP doc "
		+ "where doc.idUnivocoVersamento=distinta.iuv "
		+ "AND doc.idDominio = distinta.identificativoFiscaleCreditore "
		+ "AND doc.codiceContestoPagamento=distinta.codTransazionePSP "
		+ "AND doc.tipo='RT')";
 
    }

	private void addForCircuito(CondizioniRicercaVO filter, Map<String, Object> parameters, List<String> conditions,
			Map<String, String> joins) {
		if (isNotEmptyProperty(filter.getCircuito())) {
	        joinDistinta(joins, true); // questo metodo e' chiamato solo ed esclusivamente per il pagato
            conditions.add(" distinta.cfgGatewayPagamento.id in (select id from CfgGatewayPagamento c where c.cfgFornitoreGateway.id = :cfgGatewayPagamentoId)");
            final String circuito = filter.getCircuito();
            parameters.put("cfgGatewayPagamentoId", Long.valueOf(circuito));
        }
	}

	private void addForCircuitoRT(List<String> conditions, Map<String, String> joins) {
        joinDistinta(joins, true); // questo metodo e' chiamato solo ed esclusivamente per il pagato
        conditions.add("distinta.cfgGatewayPagamento.id in (select id from CfgGatewayPagamento c where c.cfgFornitoreGateway.id = 4)");
	}
	
	private void addForStatoCondizioneRT(List<String> conditions, Map<String, String> joins) {
	    joinPagamenti(joins, true); // questo metodo e' chiamato solo ed esclusivamente per il pagato
	    conditions.add("pagamenti.stPagamento = 'ES'");
	}

    private void addLikeCondition(String value, String sqlFieldRef, String parameter, List<String> conditions, Map<String, Object> parameters) {
        String replacedFieldValue = value.replaceAll("\\*", "%");
        final String sqlWhereCondition;
        if (replacedFieldValue.contains("%")) {
            sqlWhereCondition = "UPPER(" + sqlFieldRef + ") like UPPER(:" + parameter + ")";
        } else {
            sqlWhereCondition = sqlFieldRef + " = :" + parameter;
        }
        conditions.add(sqlWhereCondition);
        parameters.put(parameter, replacedFieldValue);
    }
    
		
		private String joinDistinta(Map<String, String> joins, boolean isPagato) {
                joinPagamenti(joins, isPagato);
				return joins.put("distinta", "join pagamenti.flussoDistinta as distinta ");
		}
		
		boolean isNotEmptyProperty(String property){
				return property != null && !property.trim().equals("");
		}
		
		private void joinPendenzaDestinatari(Map<String, String> joins) {
				joins.put("destinatario", "join pendenza.destinatari as destinatario ");
		}
		
		private void joinPendenza(Map<String, String> joins) {
			joins.put("pendenza", "join condizione.pendenza as pendenza ");
		}
	
		private void joinPagamenti(Map<String, String> joins, boolean isPagato) {
			if (isPagato) {	// pagato e tutti (pagato)
				joins.put("pagamento", "join condizione.pagamenti as pagamenti ");
			} else { // da pagare e tutti (da pagare)
				joins.put("pagamento", "left join condizione.pagamenti as pagamenti ");
			}
		}

		@Override
		public EsitoNDP notificaPagamento(RichiestaNotificaPagamento request) {
			Query q= em.createNamedQuery("checkIdFiscaleEnte");
			q.setParameter("LAPL", request.getIdentificativoDominio());
			List<Intestatari> listaIntestatari=(List<Intestatari>)q.getResultList();
			if (listaIntestatari.isEmpty()) {
				EsitoNDP esito = new EsitoNDP();
				esito.setEsito("KO");
				esito.setFaultCode("00");
				esito.setFaultString("Id Dominio non censito");
				return esito;
			} 
			Intestatari i = listaIntestatari.get(0);
			String idEnte= i.getEntiobj().getIdEnteIForm();
			Query q1 = em.createNamedQuery("getDistintaByIUVIdFiscCred");
			q1.setParameter("iuv", request.getIdentificativoUnivocoVersamento());
			q1.setParameter("identificativoFiscaleCreditore", request.getIdentificativoDominio());
			List<GestioneFlussi> l2 =q1.getResultList();
			if (l2.isEmpty()) {
				EsitoNDP esito = new EsitoNDP();
				esito.setEsito("KO");
				esito.setFaultCode("00");
				esito.setFaultString("Pagamento non trovato");
				return esito;
			}
			
			GestioneFlussi g0 = l2.get(0);
			Pagamenti pagamento =g0.getPagamenti().iterator().next();
			//----
			
            // Questi dati potrebbero servire per fare dei controlli: 
			//al momento se presenti li salviamo nelle note 			
			java.lang.StringBuffer note = new java.lang.StringBuffer();
			if (request.getIndiceVersamento() != null)
				note.append("indiceDatiSingoloPagamento="+request.getIndiceVersamento()+";");
			if (request.getIdentificativoUnivocoRiscossione() != null)
				note.append("identificativoUnivocoRiscossione="+request.getIdentificativoUnivocoRiscossione()+";");
			if (request.getImportoPagamento() != null)
				note.append("singoloImportoPagato="+request.getImportoPagamento().toString());
			pagamento.setNotePagamento(note.toString());
            
			// Se presente questo CF aggiorna quello del pagamento che viene
            // valorizzato inizialmente con il CF del debitore			
			if (request.getCodiceFiscaleVersante() != null)
				pagamento.setCoPagante(request.getCodiceFiscaleVersante());

            if (request.getTipoNotifica().compareTo(EnumTipoNotifica.I)==0){
            	pagamento.setCodRendicontazioneIncasso("GDC");
            	pagamento.setFlagIncasso("2");  
            } else {
            	pagamento.setCodRendicontazioneIncasso("FR");
            	pagamento.setFlagIncasso("1");
            }
            pagamento.setIdentificativoFlusso(request.getIdentificativoFlussoRiversamento());
            pagamento.setDataRegolamento(request.getDataRegolamento());
			pagamento.setDataEsecuzione(request.getDataPagamento());
            pagamento.setTRN(request.getTRN());
            pagamento.setTotaleRendicontazioneIncasso(request.getImportoRegolamento());
            pagamento.setRiferimentoContabile(request.getRiferimentoContabile());
            pagamento.setDataContabile(request.getDataContabile());
            pagamento.setImportoContabile(request.getImportoMovimentoContabile());           
  
 			//----
            EsitoNDP esito = new EsitoNDP();
			esito.setEsito("OK");
			return esito;
		}
		
}

