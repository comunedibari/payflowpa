package it.tasgroup.iris.scheduler;

import static java.util.concurrent.TimeUnit.MINUTES;

import it.nch.fwk.fo.util.Tracer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Schedulatore di eventi ad intervalli regolari per una durata configurabile.
 * 
 * Può essere utilizzato per schedulare qualunque attività data la sua
 * genericità ottenuta mediante il Design Pattern Strategy.
 * 
 * @author pazzik
 * 
 */
public class TaskScheduler {

	private static final int numThread = 1;

	private static final ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(numThread);

	/**
	 * L'Executor è il task che viene schedulato differenzia il comportamento
	 * del TaskScheduler secondo il Pattern Strategy
	 */
	private SchedulableExecutor executor = null;

	public TaskScheduler(final SchedulableExecutor executor) {
		Tracer.info(TaskScheduler.class.getName(), "costruttore",  "inizializzazione TaskScheduler - executor: " + executor.getClass().getName());
		this.executor = executor;
	}

	/**
	 * Schedula l'attività e la terminazione dell'executor che ne rappresenta il
	 * comportamento.
	 * 
	 * @param executor
	 *            : il task da schedulare.
	 */
	public ScheduledFuture<?> scheduleExecutor() {
		Tracer.info(TaskScheduler.class.getName(), "scheduleExecutor",  "schedulazione del task con esecutore: " + executor.getClass().getName());

		// schedulazione del task periodico con ritardo di partenza
		// (initialDelay e period)
		final ScheduledFuture<?> executorHandle = schedulerService.scheduleAtFixedRate(executor, executor.getInitialDelay(), executor.getPeriod(), MINUTES);

		// se è stata impostata una durata massima per il task (durationTime)
		// allora schedulo la cancellazione dell'esecuzione del task appena
		// schedulato
		if (executor.getDurationTime() > 0) {
			Tracer.info(TaskScheduler.class.getName(), "scheduleExecutor",  "predispongo la cancellazione del task tra " + executor.getDurationTime() + " minuti");
			schedulerService.schedule(new Runnable() {
				public void run() {
					executorHandle.cancel(true);
				}
			}, executor.getDurationTime(), MINUTES);

		} else {
			Tracer.info(TaskScheduler.class.getName(), "scheduleExecutor",  "DURATA INDETERMINATA");
		}

		return executorHandle;
	}

}
