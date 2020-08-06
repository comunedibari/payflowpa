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
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.ServiceLocalMapper;

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
public class RipristinaPendenzeOriginali implements MassiveCommandExecutor {


	private final Log logger = LogFactory.getLog(this.getClass());

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MonitoringData executeApplicationTransaction(Object data) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//recupero i dati di input
		PrevisitingData prevData = (PrevisitingData)data;

		String senderId = prevData.getIdMittente();
		String silMittente = prevData.getSilMittente();
		String cdTrbEnte = prevData.getTipoPendenza();

		monData.setSenderid(senderId);
		monData.setSendersys(silMittente);

		try {

			logger.info(" controllo pendenze e ripristino di quelle cancellate logicamente ");
			Query deletePend = null;

			//Certo tutti gli id pendenza del mittente / sil / tributo
			List<String> pendenzeList = listaPendenzeOriginaliDaRipristinare(em, senderId, silMittente, cdTrbEnte);
			//registro anche quante sono le pendenze che sto per cancellare
			monData.setNumRecord(pendenzeList.size());

			int countEsitiPend = 0;
			int countPend = 0;

			EntityTransaction trax = em.getTransaction();
			trax.begin();

			for (int i = 0; i < pendenzeList.size(); i++) {
				String idPendenza = new String((String) pendenzeList.get(i));

				logger.info(" idPend da ripristinare = " + idPendenza);
				countEsitiPend++;

				//update Pend, ripristino PEND , RIGA VALIDA
				deletePend = em.createQuery ("" +
						" update Pendenze PENDENZE " +
						" set PENDENZE.stRiga = :stRigaValida, " +
						" PENDENZE.tsAnnullamento = :tsAnnullamento " +
						"	where PENDENZE.idPendenza = :pendenza " +
						" and PENDENZE.cdTrbEnte = :cdTrbEnte " +
						" and PENDENZE.stRiga = :stRigaDeleteLogica ");
				deletePend.setParameter("stRigaValida", "V");
				deletePend.setParameter("tsAnnullamento", null);
				deletePend.setParameter("stRigaDeleteLogica", "X");
				deletePend.setParameter("pendenza", idPendenza);
				deletePend.setParameter("cdTrbEnte", cdTrbEnte);
				int result = deletePend.executeUpdate();
				countPend += result;

				//update copd, ripristino pagamenti associati, imposto COPD, RIGA VALIDA
				deletePend = em.createQuery ("" +
						" update CondizioniPagamento CondPag " +
						" set CondPag.stRiga = :stRigaValida " +
						" where CondPag.idPendenza = :pendenza " +
						" and CondPag.cdTrbEnte = :cdTrbEnte " +
						" and CondPag.stRiga = :stRigaDeleteLogica ");
				deletePend.setParameter("stRigaValida", "V");
				deletePend.setParameter("stRigaDeleteLogica", "X");
				deletePend.setParameter("pendenza", idPendenza);
				deletePend.setParameter("cdTrbEnte", cdTrbEnte);
				int result1 = deletePend.executeUpdate();
				logger.info(" IdPend da ripristinare = " + idPendenza + " , Copd = " + result1  );
			}

			//controllo di business
			if (pendenzeList.size()==countPend) {
				trax.commit();
				logger.info(" Pend / Copd ripristinati = " + countPend  );
			} else {
				trax.rollback();
				monData.setNumRecord(-1);
				logger.info(" Eseguo rollback , n° pend trovate != n° pend ripristinate = " + pendenzeList.size() + " / "+ countPend);
			}

			logger.info(" EsitiPend analizzati = " + countEsitiPend);
			logger.info(" Pendenze aggiornati = " + countPend);


		} catch (Exception e) {
			logger.info(" Error Ripristino Massivi = " + e.getMessage(),e);
			e.printStackTrace();
		}


		return monData;
	}


	@Override
	public String executeHtml(String msg, String senderId, String silMittente, String cdTrbEnte, String msgType) {
		String table = "";
		try {

			//recupero i dati di input
			PrevisitingData prevData = new PrevisitingData();

			prevData.setE2EMsgId(msg);
			prevData.setIdMittente(senderId);
			prevData.setSilMittente(silMittente);
			prevData.setTipoPendenza(cdTrbEnte);

//			this.executeApplicationTransaction(prevData);
			//calcolo la lista delle pendenze da cancellare
			List<String> pendenzeList = listaPendenzeOriginaliDaRipristinare(em, senderId, silMittente, cdTrbEnte);

			logger.info(this.getClass().getSimpleName() + "executeHtml, found " + pendenzeList.size());
			table = "<br><br>";
			table += "<b>Lista Pendenze Originali da Ripristinare</b> (Inserire SENDERID, SIL MITTENTE, CODICE TRIBUTO ENTE )";
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
	private List<String> listaPendenzeOriginaliDaRipristinare(EntityManager em, String senderId, String silMittente, String cdTrbEnte) {

		//Certo tutti gli id pendenza del mittente / sil / tributo
		Query queryPend = em.createNativeQuery (
				" select varchar(Pendenze.id_pendenza) from PENDENZE as Pendenze " +
				" left outer join ESITI_PENDENZA as Esiti " +
				"	on (Pendenze.id_pendenza = Esiti.id_pendenza ) " +
				" where Esiti.senderid = :senderid and Esiti.sendersys = :sendersys  " +
				" and Pendenze.cd_trb_ente= :cd_trb_ente " +
				" and Pendenze.st_riga = :stRigaDeleteLogica ");

		queryPend.setParameter("senderid", senderId);
		queryPend.setParameter("sendersys", silMittente);
		queryPend.setParameter("cd_trb_ente", cdTrbEnte);
		queryPend.setParameter("stRigaDeleteLogica", "X");

		List pendenzeList = queryPend.getResultList();

		logger.info("trovati pendenze originali da ripristinare = " + pendenzeList.size());

		return pendenzeList;
	}



}
