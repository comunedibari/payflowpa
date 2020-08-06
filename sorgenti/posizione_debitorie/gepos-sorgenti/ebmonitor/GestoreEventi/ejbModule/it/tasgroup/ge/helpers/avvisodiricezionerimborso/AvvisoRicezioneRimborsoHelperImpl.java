package it.tasgroup.ge.helpers.avvisodiricezionerimborso;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.helpers.GestoreEventiMailHelper;
import it.tasgroup.ge.pojo.MessaggioLogico;

import javax.persistence.EntityManager;



public class AvvisoRicezioneRimborsoHelperImpl extends GestoreEventiMailHelper {

	public AvvisoRicezioneRimborsoHelperImpl(EntityManager mgr) {
		manager = mgr;
	}
	@Override
	public MessaggioLogico getMessaggioLogico(String datiEvento,
			CfgEventi confEvento) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
