/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.massivi;

import it.tasgroup.idp.bean.MassiveCommandExecutor;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Deprecated
public class CancellaFisicaPendenzeOriginali implements MassiveCommandExecutor {


	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;

	private final Log logger = LogFactory.getLog(this.getClass());


	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//recupero i dati di input
		PrevisitingData prevData = (PrevisitingData)data;

		String e2eMsgId = prevData.getE2EMsgId();
		String senderId = prevData.getIdMittente();
		String silMittente = prevData.getSilMittente();
		String cdTrbEnte = prevData.getTipoPendenza();

		//settings per monitoraggio
		monData.setE2emsgid(e2eMsgId);
		monData.setSenderid(senderId);
		monData.setSendersys(silMittente);

		logger.info(" Pulizia Massivi , senderId / senderSys / tipoPendenza = " +  senderId + " / " +  silMittente + " / " + cdTrbEnte);

		try {


			logger.info(" cancellazione fisica delle pendenze originali ");

			//Certo tutti gli id pendenza del mittente / sil / tributo
			List<Object[]> pendenzeList = listaPendenzeDaCancellare(em, senderId, silMittente, cdTrbEnte);
			//registro anche quante sono le pendenze che sto per cancellare
			monData.setNumRecord(pendenzeList.size());

			int countEsitiPend = 0;
			int countPend = 0;
			logger.info(" pendenze da cancellare =  " + pendenzeList.size());
			Query deletePend = null;

			EntityTransaction trax = em.getTransaction();
			trax.begin();

			for (int i = 0; i < pendenzeList.size(); i++) {
				String idPendenza = new String(  (String)((Object[])pendenzeList.get(i))[0] );
				String idPendenzaEnte = new String(  (String)((Object[])pendenzeList.get(i))[1] );

				logger.info(" idPend da cancellare = " + idPendenza +  " idPendEnte = " + idPendenzaEnte);
				countEsitiPend++;

				int result2 = 0;
				int result3 = 0;
				int result4 = 0;
				int result5 = 0;

				//lista copd
				Query queryCopd = em.createQuery ("" +
						"SELECT CONDIZIONIPAGAMENTO " +
						"FROM CondizioniPagamento as CONDIZIONIPAGAMENTO " +
						" WHERE CONDIZIONIPAGAMENTO.idPendenza = :pendenza "
//						+ " and CONDIZIONIPAGAMENTO.cdTrbEnte = :cdTrbEnte " +
//						" and CONDIZIONIPAGAMENTO.idPagamento is not null"
						);
				queryCopd.setParameter("pendenza", idPendenza);
//				queryCopd.setParameter("cdTrbEnte", cdTrbEnte);
				List<CondizioniPagamento> condizioniList = queryCopd.getResultList();

				for (int j = 0; j < condizioniList.size(); j++) {
					CondizioniPagamento condPend = (CondizioniPagamento)condizioniList.get(j);
					logger.info(" idCopd e idPaga associati = " + condPend.getIdCondizione() + " / " + condPend.getIdPagamento());
					//Updata Paga
					deletePend = em.createQuery ("" +
							" update Pagamento as PAGAMENTO " +
							" set PAGAMENTO.idCondizione = null, " +
							" PAGAMENTO.idPendenzaEnte = '" +idPendenzaEnte +"', " +
							" PAGAMENTO.idPendenza = null   " +
							" where PAGAMENTO.cdTrbEnte = :cdTrbEnte " +
							" and PAGAMENTO.idCondizione = :idCondizione"							);
					deletePend.setParameter("idCondizione", condPend.getIdCondizione());
					deletePend.setParameter("cdTrbEnte", cdTrbEnte);
					result5 = deletePend.executeUpdate();
				}

				//dopo aver fatto l'aggiornamento della PAGA
				//allora posso cancellare le tabelle collegate
				//rispettando le due/tre FK
				//delete Pend
				deletePend = em.createQuery ("" +
						" delete from Pendenze as PENDENZE " +
						"	where PENDENZE.idPendenza = :pendenza ");
				deletePend.setParameter("pendenza", idPendenza);
				int result = deletePend.executeUpdate();
				countPend += result;

				//delete copd, senza pagamenti associati
				deletePend = em.createQuery ("" +
						" delete from CondizioniPagamento " +
						"	where idPendenza = :pendenza " +
						" and cdTrbEnte = :cdTrbEnte ");
				deletePend.setParameter("pendenza", idPendenza);
				deletePend.setParameter("cdTrbEnte", cdTrbEnte);
				int result1 = deletePend.executeUpdate();

				if (result==1) {
					//delete alpe
					deletePend = em.createQuery ("" +
							" delete from AllegatiPendenza " +
							"	where idPendenza = :pendenza ");
					deletePend.setParameter("pendenza", idPendenza);
					result2 = deletePend.executeUpdate();
					//delete depd
					deletePend = em.createQuery ("" +
							" delete from DestinatariPendenze " +
							"	where idPendenza = :pendenza ");
					deletePend.setParameter("pendenza", idPendenza);
					result3 = deletePend.executeUpdate();
					//delete vopd
					deletePend = em.createQuery ("" +
							" delete from VociPagamento " +
							"	where idPendenza = :pendenza ");
					deletePend.setParameter("pendenza", idPendenza);
					result4 = deletePend.executeUpdate();
				}

				logger.info(" Pend / Copd / Alpe / Depd / Vopd cancellati , Paga aggiornati = " + result + " / " + result1 + " / " + result2 + " / " + result3 + " / " + result4 + " / "  + result5 + " / " );
			}

			logger.info(" EsitiPend analizzati = " + countEsitiPend);
			logger.info(" Pendenze cancellati = " + countPend);

			//update pendenze cart
			Query queryUpdatePend =  em.createQuery ("Update PendenzeCart pend " +
					" set pend.stato = :stato,  " +
					" pend.numPendDeleted = :numPendDeleted " +
					" WHERE pend.pk.e2emsgid = :e2emsgid" +
					" AND pend.pk.senderid= :senderId " +
					" AND pend.pk.sendersys = :senderSys ");
			queryUpdatePend.setParameter("stato", StatoEnum.ESITO_OK_DA_CREARE);
			queryUpdatePend.setParameter("e2emsgid", e2eMsgId);
			queryUpdatePend.setParameter("senderId", senderId);
			queryUpdatePend.setParameter("senderSys", silMittente);
			queryUpdatePend.setParameter("numPendDeleted", countPend);
			int esitiPend = queryUpdatePend.executeUpdate();

			logger.info(" Update PendCart; e2eId, sendId, Sil , stato , pendDel  = "
							+ e2eMsgId + "/" + senderId + "/" + silMittente
							+ StatoEnum.ESITO_OK_DA_CREARE + "/" + countPend);

			//controllo di business
			if (pendenzeList.size()==countPend) {
				//commit all!
				trax.commit();
			} else {
				//commit all!
				monData.setNumRecord(-1);
				logger.info(" Eseguo rollback , n° pend trovate != n° pend cancellate = " + pendenzeList.size() + " / "+ countPend);
				trax.rollback();
			}

		} catch (Exception e) {
			logger.info(" Error Delete Fisica Pendenze = " + e.getMessage(),e);
			e.printStackTrace();
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
			List pendenzeList = listaPendenzeDaCancellare(em, senderId, silMittente, cdTrbEnte);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + pendenzeList.size());
			table = "<br><br>";
			table += "<b>Lista Pendenze da Cancellare</b> (Inserire SENDERID, SIL MITTENTE, CODICE TRIBUTO ENTE )";
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
//			table+="</PRE>";
			return table;
	}


	/**
	 *
	 * @param em
	 * @return
	 */
	private List<Object[]> listaPendenzeDaCancellare(EntityManager em, String senderId, String silMittente, String cdTrbEnte) {

		Query queryPend = em.createNativeQuery (
				" select varchar(Pendenze.id_pendenza),varchar(Pendenze.id_pendenzaente) from PENDENZE as Pendenze " +
				" left outer join ESITI_PENDENZA as Esiti " +
				"	on (Pendenze.id_pendenza = Esiti.id_pendenza ) " +
				" where Esiti.senderid = :senderid and Esiti.sendersys = :sendersys  " +
				" and Pendenze.cd_trb_ente= :cd_trb_ente " +
				" and (Pendenze.st_riga = :stRigaValida or Pendenze.st_riga = :stRigaAnnullata)");

		queryPend.setParameter("senderid", senderId);
		queryPend.setParameter("sendersys", silMittente);
		queryPend.setParameter("cd_trb_ente", cdTrbEnte);
		queryPend.setParameter("stRigaValida", "X");
		queryPend.setParameter("stRigaAnnullata", "A");
		List<Object[]> pendenzeList = queryPend.getResultList();

		logger.info("trovati pendenze da cancellare fisicamente  = " + pendenzeList.size());

		return pendenzeList;
	}



}
