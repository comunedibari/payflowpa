package it.tasgroup.cluster.jboss;

import org.jboss.as.clustering.singleton.SingletonService;
import org.jboss.logging.Logger;
import org.jboss.msc.service.DelegatingServiceContainer;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;

public class SingletonsStarterServiceActivator implements ServiceActivator {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void activate(ServiceActivatorContext context) {
        log.info("Node " + System.getProperty("jboss.node.name") + " will be started");

        new SingletonService<String>(new SingletonsStarterService(), ServiceName.JBOSS.append("SingletonsStarter")).
        	build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()))
        			.setInitialMode(ServiceController.Mode.ACTIVE)
        			.install()
        ;
    }
}
