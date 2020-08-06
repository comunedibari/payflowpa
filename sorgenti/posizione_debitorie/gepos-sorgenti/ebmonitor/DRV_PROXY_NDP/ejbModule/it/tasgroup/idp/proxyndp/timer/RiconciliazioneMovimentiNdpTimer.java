package it.tasgroup.idp.proxyndp.timer;

import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;
import it.tasgroup.idp.timer.AbstractTimer;
import it.tasgroup.idp.timer.ITimerLocal;
import it.tasgroup.idp.timer.ITimerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class RiconciliazioneMovimentiNdpTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@EJB(beanName = "RichiestaRiconciliazioneMovimentiNdp")
	private ExecutorLocal richiestaRiconciliazioneMovimentiNdp;

	@EJB(beanName = "RichiestaRiconciliazioneEsitiNdp")
	private ExecutorLocal richiestaRiconciliazioneEsitiNdp;

	
	private final Log logger = LogFactory.getLog(this.getClass());

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if (!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;

		// Eseguo la riconciliazione diretta dei flussi FR (senza incrociare i movimenti accredito 
		// Questa procedura ignora i Flussi FR destinati a enti  per i quali è disponibile anche la rendicontazione del 
		// conto tecnico /che verranno elaborati dallo step successivo)
		logger.info("Chiamata a RichiestaRiconciliazioneEsitiNdp");
		richiestaRiconciliazioneEsitiNdp.executeApplicationTransaction();

		// Eseguo la riconciliazione a partire dai movimenti accredito
		logger.info("Chiamata a RichiestaRiconciliazioneMovimentiNdp");
		richiestaRiconciliazioneMovimentiNdp.executeApplicationTransaction();
	}
}
