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
package it.tasgroup.idp.timer;

import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.domain.TimersIris;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.TimerData;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Remote(CommandExecutor.class)
public class TimerStatus implements CommandExecutor, CommandExecutorLocal {

	private final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext(unitName = ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;
	
	@EJB(beanName = "TimerController")
	TimerCommandExecutorLocal timerController;
	

	/*
	private static final List<String> listaTimersImplementati = new ArrayList<String>();
	static {
		listaTimersImplementati.add("SpedizioneEsitiTimer");
		listaTimersImplementati.add("ReinoltroEsitiTimer");
		listaTimersImplementati.add("ReinoltroNotificheTimer");
		listaTimersImplementati.add("TimeoutTimer");
		listaTimersImplementati.add("RipartenzaDataStorageTimer");
		listaTimersImplementati.add("NotificaPagamentoTimer");
		listaTimersImplementati.add("ControllerUpdateMassivoTimer");
		listaTimersImplementati.add("AggregazioneBozzeBonificiTimer");
		listaTimersImplementati.add("BonificiRiaccreditoCbiTimer");
		listaTimersImplementati.add("BozzeBonificiManagerTimer");
		listaTimersImplementati.add("CasellarioInfoManagerTimer");
		listaTimersImplementati.add("SpazioOutputManagerTimer");
		listaTimersImplementati.add("SpazioIROutputManagerTimer");
		listaTimersImplementati.add("SpazioALOutputManagerTimer");
		listaTimersImplementati.add("RiconciliaBonificiRiaccreditoCbiTimer");
		listaTimersImplementati.add("AccettazioneRichiestaRevocaTimer");
		listaTimersImplementati.add("RichiestaAutorizzazioneTimer");
		listaTimersImplementati.add("RichiestaRevocaTimer");
		listaTimersImplementati.add("IncassoDisposizioniRidTimer");
		listaTimersImplementati.add("DisposizioneRidTimer");
		listaTimersImplementati.add("SprenotazionePagamentiTimer");
		listaTimersImplementati.add("PromozionePagamentiTimer");
		listaTimersImplementati.add("ParseAndMatchTimer");
		listaTimersImplementati.add("RichiestaCatalogoPSPTimer");
		listaTimersImplementati.add("ElencoFlussiRendicontazioneNdpTimer");
		listaTimersImplementati.add("DettaglioFlussoRendicontazioneNdpTimer");
		listaTimersImplementati.add("GiornaleDiCassaSiopeTimer");
	}
	*/
	
	private List<TimersIris> getTimersList() {
		Query query = manager.createNamedQuery("ListaTimersIris");
		@SuppressWarnings("unchecked")
		List<TimersIris> timersList = query.getResultList();
		return timersList;
	}

	public MonitoringData executeApplicationTransaction() {
		MonitoringData monData = new MonitoringData();
		return monData;
	}

	@Override
	public String executeHtml() throws Exception {
		String table = "";
		try {

			MonitoringData data = testTimers();

			logger.info(getClass().getSimpleName() + "executeHtml, found " + data.getFilesList().size());

			table = "<br><br>";
			table += "<b>TimersStatus: Lista Timers su Istanza corrente</b>";
			table += "<br>";
			table += "<TABLE border=\"\1\">";
			table += "<TR>";
			table += "<TD>File Name</TD>";
			table += "</TR>";

			List<String> filesList = data.getFilesList();
			for (Iterator<String> iterator = filesList.iterator(); iterator.hasNext();) {
				String fileName = iterator.next();
				table += "<TR>";
				table += "<TD>";
				table += (fileName);
				table += "</TD>";
				table += "</TR>";
			}
			table += "</TABLE>";

		} catch (Exception e) {
			logger.info(getClass().getSimpleName() + " ERROR EXECUTEHTML " + e.getMessage());
		}
		return table;
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		return null;
	}

	private MonitoringData testTimers() throws Exception {

		MonitoringData monitoringData = new MonitoringData();
		List<String> timersStatus = new ArrayList<String>();

		List<TimersIris> listaTimers = getTimersList();

		for (TimersIris timer : listaTimers) {
			TimerData tData = new TimerData(timer.getTimer(), TimerData.Action.CHECK);
			MonitoringData mData = timerController.executeApplicationTransaction(tData);
			timersStatus.add(mData.getEsito());
		}
		monitoringData.setFilesList(timersStatus);
		return monitoringData;
	}

}
