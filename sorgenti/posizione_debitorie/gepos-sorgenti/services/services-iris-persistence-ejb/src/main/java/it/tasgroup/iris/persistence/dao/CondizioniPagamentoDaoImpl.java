package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "CondizioniPagamentoDaoService")
public class CondizioniPagamentoDaoImpl extends DaoImplJpaCmtJta<CondizionePagamento> implements CondizioniPagamentoDao {

    private static final Logger LOGGER = LogManager.getLogger(CondizioniPagamentoDaoImpl.class);

    @PersistenceContext(unitName = "IrisPU")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public CondizionePagamento getSingleCondizioneById(String idCondizione) {
        CondizionePagamento condizione = null;

        try {

            condizione = this.getById(CondizionePagamento.class, idCondizione);

        } catch (Exception e) {

            LOGGER.error("error on getSingleCondizioneById ", e);
            throw new DAORuntimeException(e);

        }
        return condizione;
    }

    @Override
    public List<CondizionePagamento> getCondizioniByIdList(List<String> idCondizioni) {

        List<CondizionePagamento> retList = null;

        try {

            Query queryCondizioniByIdList = createQueryConditionsByIdList(idCondizioni);

            retList = queryCondizioniByIdList.getResultList();

            for (CondizionePagamento cond: retList)

            	cond.updateStatoPagamentoCalcolato();

        } catch (Exception e) {

            LOGGER.error("error on getCondizioniByIdList " + idCondizioni, e);

            throw new DAORuntimeException(e);

        }

        return retList;
    }

    private Query createQueryConditionsByIdList(List<String> idCondizioni) {

        String selectFromWhere = "select * from JLTCOPD ";

        StringBuffer inCondition = new StringBuffer("where ID_CONDIZIONE IN (");

        appendInCondition(inCondition, idCondizioni);

        Query query = em.createNativeQuery(selectFromWhere + inCondition, CondizionePagamento.class);

        addInParameters(query, idCondizioni, 0);

        return query;
    }



    private Query buildJPQLQuery(String idPagamento, String codiceTributoEnte, String idEnte) {

        String queryStr = "select cond from CondizionePagamento cond where cond.idPagamento = :codiceRicercaPagamento and cond.ente.idEnte=:idEnte "
        		+ " and ST_RIGA in ('V','H')"
        		+ " order by cond.tsInserimento desc "; 

        //Nota: l'ordinamento e' ininfluente se la query torna un solo risultato.  
     	// facilita l'elaborazione successiva nel caso in cui ci siano  piu' risultati 
        
        if (codiceTributoEnte!= null)
        	queryStr += "and cond.cdTrbEnte=:codiceTributoEnte";

        Query query = em.createQuery(queryStr);

        query.setParameter("codiceRicercaPagamento", idPagamento);

        query.setParameter("idEnte", idEnte);

        if (codiceTributoEnte != null)
        	query.setParameter("codiceTributoEnte", codiceTributoEnte);

        return query;
    }


    private Query buildJPQLQueryCIP(String coCIP, String codiceTributoEnte, String idEnte) {

    	String queryStr = "select cond from CondizionePagamento cond where cond.coCip = :codiceRicercaPagamento and cond.ente.idEnte=:idEnte "
        		+ " and ST_RIGA in ('V','H') ";

        if (codiceTributoEnte!= null)
        	queryStr += "and cond.cdTrbEnte=:codiceTributoEnte";

        Query query = em.createQuery(queryStr);

        query.setParameter("codiceRicercaPagamento", coCIP);

        query.setParameter("idEnte", idEnte);

        if (codiceTributoEnte != null)
        	query.setParameter("codiceTributoEnte", codiceTributoEnte);

        return query;
	}

    /** 
 	 * Ritorna una lista di condizioni con st_riga 'V' o 'H' ordinata dalla piu' recente alla meno recente 
 	 */ 
	@Override
    public List<CondizionePagamento> getListCondizioniByIdPagamento(String idPagamento, String codiceTributoEnte, String idEnte) {

        List<CondizionePagamento> retList = null;

        try {

            Query query = buildJPQLQuery(idPagamento,codiceTributoEnte, idEnte);

            retList = query.getResultList();

        } catch (Exception e) {
            LOGGER.error("error on getListCondizioniByIdPagamento con  idPagamento: " + idPagamento+ ", codiceTributoEnte: " + codiceTributoEnte +", idEnte: "+ idEnte, e);
            throw new DAORuntimeException(e);
        }

        return retList;

    }

	/* (non-Javadoc)
	 * @see it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao#getListCondizioniByCIPCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal)
	 *
	 */
	@Override
    public List<CondizionePagamento> getListCondizioniByCIPCode(String codiceCIP, String codiceTributoEnte, String idEnte) {

        List<CondizionePagamento> retList = null;

        try {

            Query query = buildJPQLQueryCIP(codiceCIP, codiceTributoEnte, idEnte);

            retList = query.getResultList();

        } catch (Exception e) {
            LOGGER.error("error on getListCondizioniByCIPCode " + codiceCIP, e);
            throw new DAORuntimeException(e);
        }

        return retList;

    }


	@Override
	public List<String> getDebitoriPendenza(String idPendenza) {

		String queryStr = "select coDestinatario from DestinatariPendenza where tiDestinatario <> '" + DestinatariPendenza.TIPO_DEST_DELEGATO + "' and pendenza.idPendenza = :idPendenza";

        Query query = em.createQuery(queryStr);
        query.setParameter("idPendenza", idPendenza);

        return query.getResultList();
    }

    @Override
    public CondizionePagamento getCondizioneByCFCreditoreIDEnteImporto(String codFiscaleCreditore, String idPagamentoEnte, BigDecimal importo) {
        Query q = em.createNamedQuery("getCondizionePagamentoByCodCreditoreIdPagamentoEnteImporto");
        q.setParameter("codFiscaleCreditore", codFiscaleCreditore);
        q.setParameter("idPagamentoEnte", idPagamentoEnte);
        q.setParameter("importo", importo);
        List<CondizionePagamento> resultList = q.getResultList();
        CondizionePagamento condizionePagamento = null;
        if (!resultList.isEmpty()) {
            condizionePagamento = resultList.get(0);
        }
        return condizionePagamento;
    }

    @Override
    public CondizionePagamento getCondizioneByCFCreditoreIdPagamento(String codFiscaleCreditore, String idPagamento) {
        Query q = em.createNamedQuery("getCondizionePagamentoByCodCreditoreIdPagamento");
        q.setParameter("codFiscaleCreditore", codFiscaleCreditore);
        q.setParameter("idPagamento", idPagamento);       
        List<CondizionePagamento> resultList = q.getResultList();
        CondizionePagamento condizionePagamento = null;
        if (!resultList.isEmpty()) {
            condizionePagamento = resultList.get(0);
        }
        return condizionePagamento;
    }

    // Metodi per i cruscotti in HP BO e Ente  - START
    @Override
    public List<Object[]> riepilogoIncassi(String idEnte) {
    	return riepilogoIncassi(idEnte, null);
    }
    
    @Override
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento) {
    	return listIncassiPagamenti(idEnte, true, annoRiferimento, null);
    }
    
    @Override
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk) {
    	return listIncassiPagamenti(idEnte, true, annoRiferimento, cdTrbEntePk);
    }
    
    private List<Object[]> listIncassiPagamenti(String idEnte, boolean isIncassi) {
    	return listIncassiPagamenti(idEnte, isIncassi, null, null);
    }
    
    private List<Object[]> listIncassiPagamenti(String idEnte, boolean isIncassi, String annoRiferimento, Set<String> cdTrbEntePk) {
		List<Object[]> listObj = new ArrayList<Object[]>();
		Date dataDa = null, dataA = null;
		try {
			Query queryTributiEnte = null;
			if (idEnte != null && !idEnte.isEmpty()) {
				if (cdTrbEntePk != null && !cdTrbEntePk.isEmpty()) {
					queryTributiEnte = em.createNamedQuery("allTributiEntiByEnteAndCdTrb", TributoEnte.class);
					queryTributiEnte.setParameter("cdTrbEnte", cdTrbEntePk);
				} else {
					queryTributiEnte = em.createNamedQuery("allTributiEntiByEnte", TributoEnte.class);
				}
				queryTributiEnte.setParameter("ente", idEnte);
			} else {
				if (cdTrbEntePk != null) {
					queryTributiEnte = em.createNamedQuery("allTributiEntiByCdTrb", TributoEnte.class);
					queryTributiEnte.setParameter("cdTrbEnte", cdTrbEntePk);
				} else {
					queryTributiEnte = em.createNamedQuery("allTributiEnti", TributoEnte.class);
				}
			}
			@SuppressWarnings("unchecked")
			List<TributoEnte> listaTributoEnte = (List<TributoEnte>) queryTributiEnte.getResultList();
			
			String hqlQuery = "select count(pa), sum(pa.imPagato) from Pagamenti pa " 
				 	+ " where " 
				 	+ " pa.idEnte = :id_ente and " 
				 	+ " pa.stPagamento = 'ES'  and " 
				 	+ " pa.cdTrbEnte = :cdTrbEnte " 
				 	+ (isIncassi ? " and pa.flagIncasso in ('2','N')" : ""); 
			if (annoRiferimento != null) {
				int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
				int annoSelezionato = Integer.valueOf(annoRiferimento);
				hqlQuery += " and pa.tsDecorrenza between :dataDa and :dataA"; 
				Calendar calendar = Calendar.getInstance();
				calendar.set(annoSelezionato, Calendar.JANUARY, 1);
				dataDa = calendar.getTime();
				
				calendar = Calendar.getInstance();
				if (annoSelezionato != annoCorrente)
					calendar.set(Integer.valueOf(annoRiferimento), Calendar.DECEMBER, 31);
				dataA = calendar.getTime();
			}
			
			for (TributoEnte tributoEnte : listaTributoEnte) {
				Query query = em.createQuery(hqlQuery); 
				if (annoRiferimento != null) {
					query.setParameter("dataDa", dataDa, TemporalType.DATE);
					query.setParameter("dataA", dataA, TemporalType.DATE);
				}
								
				query.setParameter("id_ente", tributoEnte.getIdEnte());
				query.setParameter("cdTrbEnte", tributoEnte.getCdTrbEnte()); 
				
			    List<Object[]> l = query.getResultList(); 
			 	Object[] vect = l.get(0); 

				if (vect[0] != null
                        && ((vect[0] instanceof Integer && (((Integer) (vect[0])).intValue() > 0)) ||  
                            (vect[0] instanceof BigDecimal && (((BigDecimal) (vect[0])).intValue() > 0)) || 
                            (vect[0] instanceof Long && (((Long) (vect[0])).longValue() > 0)) 
                 )) { 
					// se num_incassi=0
					Object[] elem = new Object[3];
					elem[0] = tributoEnte.getDeTrb();
					elem[1] = vect[0];
					elem[2] = vect[1];

					listObj.add(elem);
				}
			}
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "riepilogoIncassi",
					e.getMessage());
		}
		return listObj;
    }
    
    @Override
    public List<Object[]> riepilogoPagamenti(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk) {

    	return listIncassiPagamenti(idEnte, false, annoRiferimento, cdTrbEntePk);
    }

    @Override
    public List<Object[]> riepilogoPagamenti(String idEnte, String annoRiferimento) {

    	return listIncassiPagamenti(idEnte, false, annoRiferimento, null);
    }
    
    @Override
    public List<Object[]> riepilogoPagamenti(String idEnte) {

    	return listIncassiPagamenti(idEnte, false);
    }

    @Override
    public List<Object[]> riepilogoPosizioniAttese(String idEnte, Integer nrGiorni) {

        List<Object[]> listObj = new ArrayList<Object[]>();

        try {
            Integer nr_giorni = (nrGiorni == null? 7 : nrGiorni);
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT TE.DE_TRB AS TIPO_DEBITO, COUNT(*) AS NUM_PENDENZE, SUM(PE.IM_TOTALE) AS IMPORTO_TOTALE ")
                    .append("FROM PENDENZE_CART P ")
                    .append("LEFT OUTER JOIN ESITI_PENDENZA EP ON P.E2EMSGID = EP.E2EMSGID ")
                    .append("LEFT JOIN JLTPEND PE ON PE.ID_PENDENZA=EP.ID_PENDENZA ")
                    .append("JOIN TRIBUTIENTI TE ON  TE.CD_TRB_ENTE = PE.CD_TRB_ENTE ")
                    .append("WHERE ");

            if (idEnte != null && !idEnte.isEmpty()) {
                sqlQuery.append("P.SENDERID = (SELECT CD_ENTE FROM ENTI E WHERE E.ID_ENTE = :id_ente) AND ");
            }

            sqlQuery.append("EP.ESITO_PENDENZA='OK' ")
                    .append("AND PE.ST_RIGA = 'V' ")
                    .append("AND DAYS(CURRENT_DATE) - DAYS(DATE(P.TIMESTAMP_RICEZIONE))  <  " + nr_giorni + " ")
                    .append("GROUP BY TE.DE_TRB ");


            Query q = em.createNativeQuery(sqlQuery.toString());

            if (idEnte != null && !idEnte.isEmpty()) {
                q.setParameter("id_ente", idEnte);
            }


            String msg = "DAO - riepilogoPosizioniAttese - id_ente:" + (idEnte != null? idEnte : "nessuno") + " nr_giorni: " +  nr_giorni;

            if (Tracer.isDebugEnabled(this.getClass().getName())) {
                Tracer.debug(this.getClass().getName(), "riepilogoPosizioniAttese", msg);
                Tracer.debug(this.getClass().getName(), "riepilogoPosizioniAttese", sqlQuery.toString());
            }

            System.out.println("riepilogoPosizioniAttese - " + msg);
            System.out.println("riepilogoPosizioniAttese - " +  sqlQuery.toString());

            listObj = (List<Object[]>) q.getResultList();

        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "riepilogoPosizioniAttese", e.getMessage());
        }

        return listObj;
    }

    @Override
    public List<Object[]> riepilogoPosizioniNonAttese(String idEnte, Integer nrGiorni) {

        List<Object[]> listObj = new ArrayList<Object[]>();

        try {
            Integer nr_giorni = (nrGiorni == null? 7 : nrGiorni);
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT TE.DE_TRB AS TIPO_DEBITO, COUNT(*) AS NUM_PENDENZE, SUM(PE.IM_TOTALE) AS IMPORTO_TOTALE ")
                    .append("FROM JLTPEND PE ")
                    .append("JOIN TRIBUTIENTI TE ON  TE.CD_TRB_ENTE = PE.CD_TRB_ENTE AND TE.FL_PREDETERM='N'  ")
                    .append("WHERE ");

            if (idEnte != null && !idEnte.isEmpty()) {
                sqlQuery.append("PE.ID_ENTE = :id_ente AND ");
            }

           sqlQuery.append("PE.ID_TRIBUTO_STRUTTURATO IS NOT NULL ")
                    .append("AND PE.ST_RIGA = 'V' ")
                    .append("AND DAYS(CURRENT_DATE) - DAYS(DATE(PE.TS_INSERIMENTO))  <  " + nr_giorni + " ")
                    .append("GROUP BY TE.DE_TRB ");

            Query q = em.createNativeQuery(sqlQuery.toString());

            if (idEnte != null && !idEnte.isEmpty()) {
                q.setParameter("id_ente", idEnte);
            }
             
            String msg = "DAO - riepilogoPosizioniNonAttese - id_ente:" + (idEnte != null? idEnte : "nessuno") + " nr_giorni: " +  nr_giorni;

            if (Tracer.isDebugEnabled(this.getClass().getName())) {
                Tracer.debug(this.getClass().getName(), "riepilogoPosizioniNonAttese", msg);
                Tracer.debug(this.getClass().getName(), "riepilogoPosizioniNonAttese", sqlQuery.toString());
            }

            System.out.println("riepilogoPosizioniNonAttese - " + msg);
            System.out.println("riepilogoPosizioniNonAttese - " +  sqlQuery.toString());

            listObj = (List<Object[]>) q.getResultList();

        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "riepilogoPosizioniNonAttese", e.getMessage());
        }

        return listObj;
    }

    // Metodi per i cruscotti in HP BO e Ente  - END

    @SuppressWarnings("unchecked")
	@Override
    public List<Object[]> pagamentiInScadenzaHP(String codiceFiscale, String catTributo) {
    	
    	String bigQuery = " select distinct c.*," +
	  		     " pend.id_pendenza as pend_id_pendenza," +
	  		     " pend.id_tributo_strutturato as pend_id_tributo_strutturato, "+
	  		     " pend.de_causale as pend_de_causale, " +
	  		     " cd.id_documento as id_documento, " +
	  		     " case when pa.st_pagamento is null then 0 else 1 end as pagamentoInErrore, " +
	  		     " case when d.ti_destinatario='DE' then 1 else 0 end as pagamentoInDelega, " +
                " enti.denom as enti_denom, " +
                " tributi.de_trb as tributi_de_trb, " +
	  		     " pend.cartella_pagamento as cartellaPagamento, "+
                " tributi.url_upd_service as url_upd_service, " +
                " doc.op_inserimento as cfPaganteDDP, " +
                " doc.intestatario as intestatarioDDP, " +
                " pend.co_riscossore as pend_coriscossore, " +
                " pend.riferimento as pend_riferimento, " +
                " plugin.cd_plugin as cdPlugin, " +
                
" pend.id_pendenzaente as idPendenzaEnte, " +
" pend.note as note, " +
" pend.ts_emissioneente as tsEmissioneEnte, " +
" pend.ts_prescrizione as tsPrescrizione, " +
" pend.anno_riferimento as annoRiferimento " +
	  		  
	  		  " from JLTCOPD c"+
	             " inner join JLTPEND pend on c.id_pendenza=pend.id_pendenza and pend.st_pendenza='A' and pend.st_riga in ('V','H')"+
	             " inner join JLTDEPD d on c.id_pendenza=d.id_pendenza"+
	             " left outer join PAGAMENTI pa on c.id_condizione=pa.id_condizione"+
	             " left outer join CONDIZIONI_DOCUMENTO cd on c.id_condizione=cd.id_condizione and cd.ts_annullamento is null"+
	             " left outer join DOCUMENTI_PAGAMENTO doc on doc.id=cd.id_documento and doc.ts_annullamento is null"+
              	 " left outer join JLTENTI enti on pend.id_ente = enti.id_ente " +
             	 " left outer join JLTENTR tributi on pend.id_ente=tributi.id_ente and pend.cd_trb_ente=tributi.cd_trb_ente "+
              	 " left outer join CFG_TRIBUTIENTE_PLUGIN plugin on tributi.id_ente=plugin.id_ente and tributi.cd_trb_ente=plugin.cd_trb_ente "+
	          " where (pa.st_pagamento is null or pa.st_pagamento not in ('ES','EF','IC','ST')) "+
	             " and d.co_destinatario=:cod_fiscale" + "%%%condCatTributo%%%" +
	             " and c.st_pagamento='N'"+
	             " and (c.st_riga='V' or (c.st_riga='H' and (cd.id is not null)))"+
	             " and ( "+
	             "		    c.ti_pagamento='S' and not exists  ( "+
	             "		        select distinct co.ID_CONDIZIONE from JLTCOPD co "+
	             "		        left outer join PAGAMENTI ppend on co.id_condizione=ppend.id_condizione and  ppend.st_pagamento in ('ES','EF','IC','ST') "+
	             "		        where  "+
	             "		        co.id_pendenza=c.ID_PENDENZA  "+
	             "		        and (ppend.id is not null or co.st_pagamento='P') "+
	             "		        and (co.st_pagamento<>'E') "+
	             "		    ) "+
	             "		    or "+
	             "		    c.TI_PAGAMENTO='R' and not exists ( "+
	             "		        select distinct co.ID_CONDIZIONE from JLTCOPD co "+
	             "		        left outer join PAGAMENTI ppend on co.id_condizione=ppend.id_condizione and  ppend.st_pagamento in ('ES','EF','IC','ST') "+
	             "		        where "+
	             "		        co.id_pendenza=c.ID_PENDENZA "+
	             "		        and (ppend.id is not null or co.st_pagamento='P') "+
	             "		        and co.TI_PAGAMENTO='S' "+
	             "		        and (co.st_pagamento<>'E') "+
	             "		    )"+
	             "		 )"+
	             " and (tributi.FL_NASCOSTO_FE is NULL or tributi.FL_NASCOSTO_FE <> 'Y') " +
	             " order by pend.id_pendenza asc, c.dt_scadenza asc";

        List<Object[]> listObj = new ArrayList<Object[]>();

        if (codiceFiscale == null || codiceFiscale.isEmpty())
            return listObj;

        try {
        	if (catTributo == null || catTributo.isEmpty()) {
        		bigQuery = bigQuery.replace("%%%condCatTributo%%%", "");
        		Query q = em.createNativeQuery(bigQuery, "condizioniHomePageMapping");
        		q.setParameter("cod_fiscale", codiceFiscale);
        		String msg = "DAO - pagamentiInScadenzaHP - cod_fiscale:" + codiceFiscale;
        		if (Tracer.isDebugEnabled(this.getClass().getName())) {
        			LOGGER.info("Query condizioni pagamento:: "+bigQuery);
                    Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", msg);
                }
        		System.out.println("pagamentiInScadenzaHP - " + msg);
                listObj = (List<Object[]>) q.getResultList();
                
        	} else {
        		String condCatTributo = " and tributi.id_tributo=:cat_tributo";
        		bigQuery = bigQuery.replace("%%%condCatTributo%%%", condCatTributo);
        		Query q = em.createNativeQuery(bigQuery, "condizioniHomePageMapping");
        		q.setParameter("cat_tributo", catTributo);
        		q.setParameter("cod_fiscale", codiceFiscale);
        		String msg = "DAO - pagamentiInScadenzaHP - cod_fiscale:" + codiceFiscale + " - cat_tributo:" + catTributo;
        		if (Tracer.isDebugEnabled(this.getClass().getName())) {
                    Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", msg);
                }
        		LOGGER.info("pagamentiInScadenzaHP - " + msg);
                listObj = (List<Object[]>) q.getResultList();
        	}

//        	  VECCHIA PROCEDURA SENZA CAT TRIBUTO
//            Query q = em.createNamedQuery("condizioniPerHomePage");tributi
//            q.setParameter("cod_fiscale", codiceFiscale);
//            String msg = "DAO - pagamentiInScadenzaHP - cod_fiscale:" + codiceFiscale;
//            if (Tracer.isDebugEnabled(this.getClass().getName())) {
//                Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", msg);
//            }
//            System.out.println("pagamentiInScadenzaHP - " + msg);
//            listObj = (List<Object[]>) q.getResultList();
        	
        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "pagamentiInScadenzaHP", e.getMessage());
        }

        return listObj;
    }

    @Override
    public BigDecimal importoTotalePagatoByPendenza(String idPendenza) {

        if (idPendenza == null || idPendenza.isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            Query q2 = em.createNamedQuery("importoTotalePagatoByPendenza");
            q2.setParameter("id_pendenza", idPendenza);
            BigDecimal obj = (BigDecimal) q2.getSingleResult();

            String msg = "DAO - importoTotalePagatoByPendenza - id_pendenza:" + idPendenza;
            if (Tracer.isDebugEnabled(this.getClass().getName())) {
                Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", msg);
            }

            System.out.println("importoTotalePagatoByPendenza - " + msg);

            return obj;

        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "importoTotalePagatoByPendenza", e.getMessage());
        }

        return BigDecimal.ZERO;

    }


}
