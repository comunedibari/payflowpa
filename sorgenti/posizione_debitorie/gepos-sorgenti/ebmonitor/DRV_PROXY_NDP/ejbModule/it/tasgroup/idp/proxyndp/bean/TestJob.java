package it.tasgroup.idp.proxyndp.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Stateless(name="TestJob")
//@Clustered
public class TestJob implements TestJobRemote {
    private final Log logger = LogFactory.getLog(this.getClass());

    public void performOperation (Serializable timerInfo) {

            long startAt = System.currentTimeMillis();

            try {
                String jbossNodeName = System.getProperty("jboss.node.name");

                String nodeName = jbossNodeName != null ? jbossNodeName :  InetAddress.getLocalHost().getHostName();

                logger.info("Starting job for timer : " + timerInfo + " on node : " + nodeName);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

            // introduce delay to simulate "long" task
            for (long i = 0; i < 10000000000L; i++) {

            }
   /*     try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        logger.info("Elapsed time on server for timer : " + timerInfo + ", " + (System.currentTimeMillis() - startAt));


    }
}
