package it.tasgroup.iris.business.ejb.authentication.dto.builder;

import it.nch.is.fo.profilo.Applicazioni;
import it.nch.is.fo.profilo.Area;
import it.nch.is.fo.profilo.Funzioni;
import it.nch.is.fo.profilo.Servizi;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;
import it.tasgroup.iris.dto.menu.AreeMenu;
import it.tasgroup.iris.dto.menu.FunzioniMenu;
import it.tasgroup.iris.dto.menu.ServiziMenu;

import java.util.Set;

public class ApplicazioniMenuBuilder {

	public static ApplicazioniMenu buidApplicazioniMenu(Applicazioni applicazioni) {

		ApplicazioniMenu appMenu = new ApplicazioniMenu();
		appMenu.setCode(applicazioni.getApplicationCode());

		Set<Area> aree = applicazioni.getAree();
		if (aree != null) {
			for (Area area : aree) {
				appMenu.getChildren().add(buidAreaMenu(area));
			}
		}
		return appMenu;
	}

	private static AreeMenu buidAreaMenu(Area area) {
		AreeMenu areaMenu = new AreeMenu();
		areaMenu.setCode(area.getAreaCode());

		Set<Servizi> servizi = area.getServizi();
		if (servizi != null) {
			for (Servizi servizio : servizi) {
				areaMenu.getChildren().add(buidServiziMenu(servizio));
			}
		}
		return areaMenu;
	}

	private static ServiziMenu buidServiziMenu(Servizi servizio) {
		ServiziMenu servizioMenu = new ServiziMenu();
		servizioMenu.setCode(servizio.getServiceCode());

		Set<Funzioni> funzioni = servizio.getFunzioni();
		if (funzioni != null) {
			for (Funzioni funzione : funzioni) {
				servizioMenu.getChildren().add(buidFunzioneMenu(funzione));
			}
		}
		return servizioMenu;
	}

	private static FunzioniMenu buidFunzioneMenu(Funzioni funzione) {
		FunzioniMenu funzioneMenu = new FunzioniMenu();
		funzioneMenu.setCode(funzione.getFunctionCode());
		funzioneMenu.setAccessed(funzione.getAccessed());
		funzioneMenu.setTipoOper(funzione.getOperatorType());

		return funzioneMenu;
	}

}
