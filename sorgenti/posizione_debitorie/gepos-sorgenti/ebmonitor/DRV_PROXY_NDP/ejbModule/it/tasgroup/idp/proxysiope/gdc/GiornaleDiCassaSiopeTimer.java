package it.tasgroup.idp.proxysiope.gdc;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.timer.AbstractTimer;
import it.tasgroup.idp.timer.ITimerLocal;
import it.tasgroup.idp.timer.ITimerRemote;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GiornaleDiCassaSiopeTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {

	@EJB(beanName = "RichiestaGiornaleDiCassaSiope")
	private CommandExecutorLocal richiestaGiornaleDiCassaProxy;

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	private final Log logger = LogFactory.getLog(this.getClass());

	@Timeout
	public void handleTimeout(Timer timer) {
		if (!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;
		logger.debug(this.getClass().getName() + ", calling richiestaGiornaleDiCassaProxy");
		richiestaGiornaleDiCassaProxy.executeApplicationTransaction();
	}

}
