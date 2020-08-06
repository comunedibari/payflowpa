package it.tasgroup.idp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class NodeMode {
	private final Log logger = LogFactory.getLog(this.getClass());
	public static final NodeMode instance = new NodeMode();


	private State itsState = State.NODE;
	private final List<Listener> itsListeners = new ArrayList<Listener>();

	private NodeMode() {
	}

	public State getState() {
		return itsState;
	}

	public void markedAsMaster() {
		itsState = State.MASTER;

		for (Listener theIthListener : itsListeners)  {
			theIthListener.markedAsMaster();
		}
	}

	public void masterShuttingDown() {
		itsState = State.SHUTTING_DOWN;

		for (Listener theIthListener : itsListeners)  {
			theIthListener.masterShuttingDown();
		}
	}

	public void addListener(Listener theListener) {
		itsListeners.add(theListener);
	}

	public interface Listener {
		void markedAsMaster();
		void masterShuttingDown();
	}

	public enum State {
		NODE, MASTER, SHUTTING_DOWN
	}
}
