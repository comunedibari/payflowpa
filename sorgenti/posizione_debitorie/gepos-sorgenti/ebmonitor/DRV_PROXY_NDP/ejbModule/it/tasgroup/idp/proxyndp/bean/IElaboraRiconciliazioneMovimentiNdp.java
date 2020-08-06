package it.tasgroup.idp.proxyndp.bean;

import it.tasgroup.iris2.enums.EnumFlagIncasso;
import it.tasgroup.iris2.pagamenti.MovimentiAccredito;
import it.tasgroup.iris2.pagamenti.Rendicontazioni;

// TODO #20	Documentazione

public interface IElaboraRiconciliazioneMovimentiNdp {
	public void setMaxExecutionOccurredOnMovimentiAccredito (MovimentiAccredito movimentiAccredito);
	public void doRiconciliazioneAccreditoSingolo (MovimentiAccredito movimentiAccredito, EnumFlagIncasso flagIncasso);
	public void doRiconciliazioneAccreditoCumulativo (MovimentiAccredito movimentiAccredito, EnumFlagIncasso flagIncasso);
	public void doRiconciliazioneFlussoFR(Rendicontazioni rendicontazione);
}
