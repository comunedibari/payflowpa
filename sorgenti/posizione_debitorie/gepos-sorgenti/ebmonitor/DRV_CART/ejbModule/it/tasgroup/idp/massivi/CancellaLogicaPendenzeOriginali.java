package it.tasgroup.idp.massivi;

import it.tasgroup.idp.bean.MassiveCommandExecutor;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Deprecated
public class CancellaLogicaPendenzeOriginali implements MassiveCommandExecutor {


	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;

	private final Log logger = LogFactory.getLog(this.getClass());


	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//recupero i dati di input
		PrevisitingData prevData = (PrevisitingData)data;

		String senderId = prevData.getIdMittente();
		String silMittente = prevData.getSilMittente();
		String cdTrbEnte = prevData.getTipoPendenza();
		logger.info(" Cancellazione Logica Massivi , senderId / senderSys / tipoPendenza = " +  senderId + " / " +  silMittente + " / " + cdTrbEnte);

		monData.setSenderid(senderId);
		monData.setSendersys(silMittente);

		//connessione db
		EntityTransaction trax = null;

		try {


			logger.info(" controllo pendenze ");
			Query deletePend = null;

			//Certo tutti gli id pendenza del mittente / sil / tributo
			List pendenzeList = listaPendenzeDaCancellareLogicamente(em, senderId, silMittente, cdTrbEnte);
			//registro anche quante sono le pendenze che sto per cancellare
			monData.setNumRecord(pendenzeList.size());

			int countEsitiPend = 0;
			int countPend = 0;

			trax = em.getTransaction();
			trax.begin();

			for (int i = 0; i < pendenzeList.size(); i++) {
				String idPendenza = new String((String) pendenzeList.get(i));

				logger.info(" idPend da cancellare = " + idPendenza);
				countEsitiPend++;

				//delete Pend
				deletePend = em.createQuery ("" +
						" update Pendenze PENDENZE " +
						" set PENDENZE.stRiga = :stRigaDeleteLogica," +
						" PENDENZE.tsAnnullamento = :tsAnnullamento " +
						"	where PENDENZE.idPendenza = :pendenza " +
						" and PENDENZE.cdTrbEnte = :cdTrbEnte " +
						" and PENDENZE.stRiga = :stRigaValida ");
				deletePend.setParameter("stRigaValida", "V");
				deletePend.setParameter("stRigaDeleteLogica", "X");
				deletePend.setParameter("tsAnnullamento", new Timestamp(System.currentTimeMillis()));
				deletePend.setParameter("pendenza", idPendenza);
				deletePend.setParameter("cdTrbEnte", cdTrbEnte);
				int result = deletePend.executeUpdate();
				countPend += result;

				//lista copd
//				Query queryCopd = em.createQuery ("" +
//						"SELECT CONDIZIONIPAGAMENTO " +
//						"FROM CondizioniPagamento as CONDIZIONIPAGAMENTO " +
//						" WHERE CONDIZIONIPAGAMENTO.idPendenza = :pendenza " +
//						" and CONDIZIONIPAGAMENTO.cdTrbEnte = :cdTrbEnte " +
//						" and CONDIZIONIPAGAMENTO.idPagamento is not null");
//				queryCopd.setParameter("pendenza", idPendenza);
//				queryCopd.setParameter("cdTrbEnte", cdTrbEnte);
//				List<CondizioniPagamento> condizioniList = queryCopd.getResultList();

//				for (int j = 0; j < condizioniList.size(); j++) {
//					CondizioniPagamento condPend = (CondizioniPagamento)condizioniList.get(j);
//					logger.info(" idCopd e idPaga associati = " + condPend.getIdCondizione() + " / " + condPend.getIdPagamento());
//					//Updata Paga
//					deletePend = em.createQuery ("" +
//							" update Pagamento as PAGAMENTO " +
//							" set PAGAMENTO.idCondizione = null  " +
//							" where PAGAMENTO.cdTrbEnte = :cdTrbEnte " +
//							" and PAGAMENTO.idCondizione = :idCondizione");
//					deletePend.setParameter("idCondizione", condPend.getIdCondizione());
//					deletePend.setParameter("cdTrbEnte", cdTrbEnte);
//					result5 = deletePend.executeUpdate();
//				}

				//delete copd, senza pagamenti associati
				deletePend = em.createQuery ("" +
						" update CondizioniPagamento CondPag " +
						" set CondPag.stRiga = :stRigaDeleteLogica " +
						" where CondPag.idPendenza = :pendenza " +
						" and CondPag.cdTrbEnte = :cdTrbEnte " +
						" and CondPag.stRiga = :stRigaValida ");
				deletePend.setParameter("stRigaValida", "V");
				deletePend.setParameter("stRigaDeleteLogica", "X");
				deletePend.setParameter("pendenza", idPendenza);
				deletePend.setParameter("cdTrbEnte", cdTrbEnte);
				int result1 = deletePend.executeUpdate();

				logger.info(" Pend (idPend="+idPendenza+") / Copd aggiornati = " + result + " / " + result1 );
			}

			//controllo di business
			if (pendenzeList.size()>=countPend) {
				trax.commit();
				logger.info(" Pendenze aggiornate = " + countPend );
			} else {
				trax.rollback();
				monData.setNumRecord(-1);
				logger.info(" Eseguo rollback , n° pend trovate != n° pend aggiornati = " + pendenzeList.size() + " / "+ countPend);
			}

			logger.info(" EsitiPend analizzati = " + countEsitiPend);
			logger.info(" Pendenze aggiornati = " + countPend);

		} catch (Exception e) {
			e.printStackTrace();
			if (trax!=null && trax.isActive()) trax.rollback();
			logger.error(this.getClass().getSimpleName() + " ERROR CANCELLAZIONE LOGICA = " + e.getMessage());
		}
		return monData;
	}





	@Override
	public String executeHtml(String msg, String senderId, String silMittente, String cdTrbEnte, String msgType) {
		String table = "";
		//connessione db
		try {

			//recupero i dati di input
			PrevisitingData prevData = new PrevisitingData();

			prevData.setE2EMsgId(msg);
			prevData.setIdMittente(senderId);
			prevData.setSilMittente(silMittente);
			prevData.setTipoPendenza(cdTrbEnte);

//			this.executeApplicationTransaction(prevData);
			//calcolo la lista delle pendenze da cancellare
			List<String> pendenzeList = listaPendenzeDaCancellareLogicamente(em, senderId, silMittente, cdTrbEnte);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + pendenzeList.size());
			table = "<br><br>";
			table += "<b>Lista Pendenze da Cancellare Logicamente</b> (Inserire SENDERID, SIL MITTENTE, CODICE TRIBUTO ENTE )";
			table += "<br>";
			table+="<TABLE border=\"\1\">";
			table+="<TR>" +
					"<TD>Id Pendenza</TD>" +
					"<TD>senderId</TD>" +
					"<TD>silMittente</TD>" +
					"<TD>CdTrbEnte</TD>" +
					"</TR>";

			for (int i = 0; i < pendenzeList.size(); i++) {
				String idPendenza = new String((String) pendenzeList.get(i));
				table+="<TR>";
					table+="<TD>";
						table+=idPendenza;
					table+="</TD>";
					table+="<TD>";
						table+=senderId;
					table+="</TD>";
					table+="<TD>";
						table+=silMittente;
					table+="</TD>";
					table+="<TD>";
						table+=cdTrbEnte;
					table+="</TD>";
				table+="</TR>";
			}
			table+="</TABLE>";

			} catch (Exception e) {
				logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
			}
//		table+="</PRE>";
		return table;
	}


	/**
	 *
	 * @param em
	 * @return
	 */
	private List<String> listaPendenzeDaCancellareLogicamente(EntityManager em, String senderId, String silMittente, String cdTrbEnte) {

		Query queryPend = em.createNativeQuery (
				" select varchar(Pendenze.id_pendenza) from PENDENZE as Pendenze " +
				" left outer join ESITI_PENDENZA as Esiti " +
				"	on (Pendenze.id_pendenza = Esiti.id_pendenza ) " +
				" where Esiti.senderid = :senderid and Esiti.sendersys = :sendersys  " +
				" and Pendenze.cd_trb_ente= :cd_trb_ente " +
				" and Pendenze.st_riga = :stRigaValida ");

		queryPend.setParameter("senderid", senderId);
		queryPend.setParameter("sendersys", silMittente);
		queryPend.setParameter("cd_trb_ente", cdTrbEnte);
		queryPend.setParameter("stRigaValida", "V");

//		List<EsitiPendenza> pendenzeList = queryPend.getResultList();
		List<String> pendenzeList = queryPend.getResultList();

		logger.info("trovati pendenze da cancellare logicamente  = " + pendenzeList.size());

		return pendenzeList;
	}

}
