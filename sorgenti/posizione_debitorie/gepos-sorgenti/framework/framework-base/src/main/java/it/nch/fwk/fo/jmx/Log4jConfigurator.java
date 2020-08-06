package it.nch.fwk.fo.jmx;

/**
 * classe interrogata via mbean per cambiare a caldo il livello del log
 */
import java.util.Enumeration;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jConfigurator {
	public void enableInfo(String target) {
		LogManager.getLogger(target).setLevel(Level.INFO);
	}

	public void enableWarn(String target) {
		LogManager.getLogger(target).setLevel(Level.WARN);
	}

	public void enableError(String target) {
		LogManager.getLogger(target).setLevel(Level.ERROR);
	}

	public void enableDebug(String target) {
		LogManager.getLogger(target).setLevel(Level.DEBUG);
	}
	
	public void enableInfoGlobally() {
		LogManager.getRootLogger().setLevel(Level.INFO);
	}

	public void enableWarnGlobally() {
		LogManager.getRootLogger().setLevel(Level.WARN);
	}

	public void enableErrorGlobally() {
		LogManager.getRootLogger().setLevel(Level.ERROR);
	}

	public void enableDebugGlobally() {
		LogManager.getRootLogger().setLevel(Level.DEBUG);
	}
	
	public String listAvailableLoggers(){
		Enumeration en = LogManager.getLoggerRepository().getCurrentLoggers();
		StringBuffer res = new StringBuffer();
		while(en.hasMoreElements()){
			Logger logger = (Logger)en.nextElement();
			res.append(logger.getName()).append("\n");
		}
		return res.toString();
	}
}
