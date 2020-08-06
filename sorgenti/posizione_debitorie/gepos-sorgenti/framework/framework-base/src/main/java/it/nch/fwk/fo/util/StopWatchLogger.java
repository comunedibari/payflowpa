package it.nch.fwk.fo.util;

import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;

public class StopWatchLogger extends StopWatch {

	private String taskName="StopWatchLogger,DefaultTask";
	private String context;
	private Logger logger=Logger.getLogger("it.nch.is.fo.stopwatch");
	public StopWatchLogger(String className, String method, String context) {
		super();
		setKeepTaskList(false);
		taskName=className+","+method;
		this.context=context;
	}

	
	
    public void start() {
    	super.start(taskName);
    }
    public void stop() {
    	super.stop();
    	long time =super.getLastTaskTimeMillis();
    	if (logger.isInfoEnabled())
    		logger.info(taskName+","+time+","+context);
    	
    }
}
