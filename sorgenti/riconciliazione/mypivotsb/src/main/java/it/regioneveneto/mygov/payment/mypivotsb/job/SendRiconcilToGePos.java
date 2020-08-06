package it.regioneveneto.mygov.payment.mypivotsb.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.regioneveneto.mygov.payment.mypivotsb.service.JobGePosService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class SendRiconcilToGePos {

	@Value("${myPivot.enableJobForGepos}")
	private Boolean enableJobForGepos;

	@Autowired
	private JobGePosService templService;

	@Scheduled(fixedDelay = 6000000, initialDelay = 10000)
	public void doJob() {
		if(enableJobForGepos) {
			try {
				log.info(" cron started");

				templService.tryToSendRiconcilToGePos();

				log.info(" cron ended");

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}        
	}
}
