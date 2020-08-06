package it.tasgroup.cluster.jboss;

import java.util.concurrent.atomic.AtomicBoolean;

import it.tasgroup.idp.NodeMode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class SingletonsStarterService implements Service<String> {
    private static final Logger LOGGER = Logger.getLogger(SingletonsStarterService.class);

    private final AtomicBoolean itsStarted = new AtomicBoolean(false);

    /**
     * @return the name of the server node
     */
    public String getValue() throws IllegalStateException, IllegalArgumentException {
        LOGGER.infof("%s is %s at %s", SingletonsStarterService.class.getSimpleName(), (itsStarted.get() ? " started" : " not started"), System.getProperty("jboss.node.name"));
        return "";
    }

    public void start(StartContext arg0) throws StartException {
		if (!itsStarted.compareAndSet(false, true)) {
			throw new StartException("Master node " + System.getProperty("jboss.node.name") + " has already been started");
        }

        LOGGER.info("Starting master node " + System.getProperty("jboss.node.name"));

        try {
            NodeMode.instance.markedAsMaster();

        	LOGGER.info("Master node " + System.getProperty("jboss.node.name") + " started");
        } catch (Exception e) {
        	throw new StartException("Could not initialize master node " + System.getProperty("jboss.node.name"), e);
        }
    }

	public void stop(StopContext arg0) {
        if (!itsStarted.compareAndSet(true, false)) {
            LOGGER.warn("Master node " + System.getProperty("jboss.node.name") + " has not been started");
        } else {
            LOGGER.info("Stopping master node " + System.getProperty("jboss.node.name"));
            try {
            	NodeMode.instance.masterShuttingDown();
            } catch (Exception e) {
            	LOGGER.error("Could not stop master node " + System.getProperty("jboss.node.name"), e);
            }
        }
    }
}
