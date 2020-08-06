package it.tasgroup.idp.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.pojo.DateUtils;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.pagamenti.LogElaborazioneBatch;
import it.tasgroup.iris2.pagamenti.StatistichePagamentiMese;
import it.tasgroup.iris2.pagamenti.StatistichePagamentiMesePK;
import it.tasgroup.iris2.pagamenti.StatistichePosizioniMese;
import it.tasgroup.iris2.pagamenti.StatistichePosizioniMesePK;

@Stateless
public class ReportStatistiche {
	private final Log logger = LogFactory.getLog(ReportStatistiche.class);
	private static final String OP_INSERIMENTO = "CM-IRIS";

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> buildReportPagamenti() {
		String theQuery = " SELECT  " +
				" 	DATE_FORMAT(DATA_ESTRAZIONE,'%Y-%m-01') AS MESE_ESTRAZIONE,  " +
				" 	ID_ENTE,  " +
				" 	ID_SYSTEM,  " +
				" 	ID_TRIBUTO,  " +
				" 	CD_TRB_ENTE,  " +
				" 	ATTESO,  " +
				" 	CIRCUITO,  " +
				" 	MODALITA,  " +
				" 	INCASSO, " +
				" 	SUM(NUMERO_PAGAMENTI),  " +
				" 	SUM(IMPORTO) " +
				" FROM  " +
				" 	(SELECT  " +
				" 		DATE(P.TS_DECORRENZA) AS DATA_ESTRAZIONE,  " +
				" 		P.ID_ENTE , " +
				" 		T.ID_SYSTEM, " +
				" 		T.ID_TRIBUTO, " +
				" 		P.CD_TRB_ENTE, " +
				" 		T.FL_PREDETERM AS ATTESO,  " +
				" 		CFG.ID AS CIRCUITO, " +
				" 		CMP.ID AS MODALITA, " +
				" 		CASE " +
							" WHEN P.FLAG_INCASSO = 'N' THEN 'N' " +
				" 			ELSE 'Y'  " +
				" 		END AS 'INCASSO', " +
				" 		SUM(P.IM_PAGATO) AS IMPORTO, " +
				" 		COUNT(*) AS NUMERO_PAGAMENTI " +
				" 	FROM PAGAMENTI P, TRIBUTIENTI T, DISTINTE_PAGAMENTO DP, CFG_GATEWAY_PAGAMENTO CGP, CFG_MODALITA_PAGAMENTO CMP, CFG_FORNITORE_GATEWAY CFG " +
				" 	WHERE  " +
				" 		P.ID_ENTE = T.ID_ENTE AND P.CD_TRB_ENTE = T.CD_TRB_ENTE AND " +
				" 		DP.ID = P.ID_DISTINTE_PAGAMENTO AND CGP.ID = DP.ID_CFG_GATEWAY_PAGAMENTO AND CMP.ID = CGP.ID_CFG_MODALITA_PAGAMENTO AND CFG.ID = CGP.ID_CFG_FORNITORE_GATEWAY " +
				" 		AND P.TS_DECORRENZA >= :fromTS  AND P.TS_DECORRENZA < :toTS " +
				" 		AND ST_PAGAMENTO='ES' " +
				" 		AND T.ID_TRIBUTO <> 'Categoria099' " + // BFL 
				" 	GROUP BY DATA_ESTRAZIONE, P.ID_ENTE, T.ID_SYSTEM, T.ID_TRIBUTO, P.CD_TRB_ENTE, ATTESO, CIRCUITO, MODALITA, INCASSO " +
				" 	) S " +
				" GROUP BY MESE_ESTRAZIONE, ID_ENTE, ID_SYSTEM, ID_TRIBUTO, CD_TRB_ENTE, ATTESO, CIRCUITO, MODALITA, INCASSO  " +
				" ORDER BY MESE_ESTRAZIONE, ID_ENTE, ID_SYSTEM, ID_TRIBUTO, CD_TRB_ENTE, ATTESO, CIRCUITO, MODALITA, INCASSO ";
   	 	Query queryBuild = em.createNativeQuery(theQuery);
		String fromTS = getFirstDayOfYesterdayMounth();
		String toTS = getYesterdayMidnight();
		queryBuild.setParameter("fromTS", fromTS);
		queryBuild.setParameter("toTS", toTS);
		logger.info("SELECT PAGAMENTI [fromTS: "+ fromTS + " - toTS: " + toTS + "]");
		return queryBuild.getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> buildReportPosizioni() {
		String theQuery = 
    			"SELECT "  +
    					"	DATE_FORMAT(DATA_ESTRAZIONE,'%Y-%m-01') AS MESE_ESTRAZIONE, " +
    					"	ID_ENTE, " +
    					"	ID_SYSTEM, " +
    					"	ID_TRIBUTO, " +
    					"	CD_TRB_ENTE, " +
    					"	TIPO_MESSAGGIO, " +
    					"	SUM(NUMERO_POSIZIONI) AS NUMERO_POSIZIONI, " +
    					"	SUM(IMPORTO) AS IMPORTO " +
    					" FROM " +
    					"	(SELECT " +
    					"		DATE(P.TIMESTAMP_RICEZIONE) AS DATA_ESTRAZIONE, " +
    					"		E.ID_ENTE ," +
    					"		P.SENDERSYS AS ID_SYSTEM," +
    					"		PE.ID_TRIBUTO ," +
    					"		PE.CD_TRB_ENTE ," +
    					"		P.TIPO_MESSAGGIO," +
    					"		SUM(PE.IM_TOTALE) AS IMPORTO," +
    					"		COUNT(P.NUM_PENDENZE) AS NUMERO_POSIZIONI" +
    					"	FROM PENDENZE_CART P, ENTI E, ESITI_PENDENZA EP, JLTPEND PE" +
    					"	WHERE " +
    					"		P.SENDERID = E.CD_ENTE AND " +
    					"		EP.E2EMSGID=P.E2EMSGID AND EP.SENDERID=P.SENDERID AND EP.SENDERSYS=P.SENDERSYS AND" +
    					"		PE.ID_PENDENZA=EP.ID_PENDENZA AND " +
    					"		P.TIMESTAMP_RICEZIONE >= :fromTS  AND P.TIMESTAMP_RICEZIONE < :toTS AND" +
    					"		TIPO_OPERAZIONE='Insert'" +
    					"	GROUP BY DATA_ESTRAZIONE, E.ID_ENTE, P.SENDERSYS, PE.ID_TRIBUTO, PE.CD_TRB_ENTE, P.TIPO_MESSAGGIO" +
    					"	) S" +
    					" GROUP BY MESE_ESTRAZIONE, ID_ENTE, ID_SYSTEM, ID_TRIBUTO, CD_TRB_ENTE, TIPO_MESSAGGIO" +
    					" ORDER BY MESE_ESTRAZIONE, ID_ENTE, ID_SYSTEM, ID_TRIBUTO, CD_TRB_ENTE, TIPO_MESSAGGIO";

		Query queryBuild = em.createNativeQuery(theQuery);
		String fromTS = getFirstDayOfYesterdayMounth();
		String toTS = getYesterdayMidnight();
		queryBuild.setParameter("fromTS", fromTS);
		queryBuild.setParameter("toTS", toTS);
		logger.info("SELECT POSIZIONI [fromTS: "+ fromTS + " - toTS: " + toTS + "]");
		return queryBuild.getResultList();
    }
	
	private String getFirstDayOfYesterdayMounth() {
		return DateUtils.formatTS(
					DateUtils.atStartOfDay(
							DateUtils.getMonthFirstDate(
									DateUtils.yesterday(Calendar.getInstance()))));
	}
	
	private String getYesterdayMidnight() {
		return DateUtils.formatTS(
					DateUtils.atEndOfDay(
							DateUtils.yesterday(Calendar.getInstance())));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int insertUpdateStatistichePosizioniMese(List<Object[]> rows) {
		Timestamp tsInserimento = new Timestamp(System.currentTimeMillis());
		for(Object[] elem : rows) {
			StatistichePosizioniMese spm = StatisticheBuilder.buildStatistichePosizioni(elem, OP_INSERIMENTO, tsInserimento);
			logger.debug(spm.toString());
			em.merge(spm);
		}
		return rows.size();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int insertUpdateStatistichePagamentiMese(List<Object[]> rows) {
		Timestamp tsInserimento = new Timestamp(System.currentTimeMillis());
		for(Object[] elem : rows) {
			StatistichePagamentiMese spm = StatisticheBuilder.buildStatistichePagamenti(elem, OP_INSERIMENTO, tsInserimento);
			logger.debug(spm.toString());
			em.merge(spm);
		}
		return rows.size();
	}
	
	public void saveReportElaborazione(LogElaborazioneBatch logElab) {
		logger.debug("		" + logElab.toString());
		em.persist(logElab);
	}
}

class StatisticheBuilder { 
	
	
	
	public static StatistichePagamentiMese buildStatistichePagamenti(Object[] row, String opInserimento, Timestamp tsInserimento) {
		StatistichePagamentiMese item = new StatistichePagamentiMese();
		
		StatistichePagamentiMesePK id = new StatistichePagamentiMesePK();
		id.setMeseEstrazione((String) row[0]);
		id.setIdEnte((String) row[1]);
		id.setIdSystem((String) row[2]);
		id.setIdTributo((String) row[3]);
		id.setCdTributoEnte((String) row[4]);
		id.setAtteso(String.valueOf(((Character) row[5])));
		id.setCircuito((Integer) row[6]);
		id.setModalita((Integer) row[7]);
		id.setIncasso((String) row[8]);
		item.setNumeroPagamenti((BigDecimal) row[9]); 
		item.setImporto((BigDecimal) row[10]);
		item.setOpInserimento(opInserimento);
		item.setTsInserimento(tsInserimento);
		item.setId(id);
		return item;
	}
	
	public static StatistichePosizioniMese buildStatistichePosizioni(Object[] row, String opInserimento, Timestamp tsInserimento) {
		StatistichePosizioniMese item = new StatistichePosizioniMese();
		StatistichePosizioniMesePK id = new StatistichePosizioniMesePK();
		id.setMeseEstrazione((String) row[0]);
		id.setIdEnte((String) row[1]);
		id.setIdSystem((String) row[2]);
		id.setIdTributo((String) row[3]);
		id.setCdTributoEnte((String) row[4]);
		id.setTipoMessaggio((String) row[5]);
		item.setId(id);
		item.setNumeroPosizioni((BigDecimal) row[6]);
		item.setImporto((BigDecimal) row[7]);
		item.setOpInserimento(opInserimento);
		item.setTsInserimento(tsInserimento);
		return item;
	}
}

