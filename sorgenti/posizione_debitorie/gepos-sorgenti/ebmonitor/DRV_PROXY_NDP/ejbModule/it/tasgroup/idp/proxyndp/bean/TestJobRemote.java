package it.tasgroup.idp.proxyndp.bean;

import javax.ejb.Remote;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import java.io.Serializable;

@Remote
public interface TestJobRemote {
    void performOperation(Serializable timer);
}
