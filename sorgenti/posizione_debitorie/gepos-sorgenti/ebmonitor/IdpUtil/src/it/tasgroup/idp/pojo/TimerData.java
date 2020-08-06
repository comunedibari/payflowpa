package it.tasgroup.idp.pojo;

import java.io.Serializable;

public class TimerData implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Action {
		START, STOP, CHECK
	}

	public static final Integer DEFAULT_INTERVAL = 30000;
	public static final Integer DEFAULT_PERIOD = 30000;

	private String timerName;
	private Integer interval;
	private Integer period;
	private Action action;
	private String node_active;



	@SuppressWarnings("unused")
	private TimerData() {
		super();
	}

	public TimerData(String timerName, Action action, Integer interval, Integer period, String nodeActive) {
		super();
		this.timerName = timerName;
		this.interval = interval;
		this.period = period;
		this.action = action;
		this.node_active = nodeActive;
	}

	public TimerData(String timerName, Action action) {
		super();
		this.timerName = timerName;
		if (Action.START.equals(action)) {
			this.interval = DEFAULT_INTERVAL;
			this.period = DEFAULT_PERIOD;
		}
		this.action = action;
	}

	public String getTimerName() {
		return timerName;
	}

	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getNode_active() {
		return node_active;
	}

	public void setNode_active(String node_active) {
		this.node_active = node_active;
	}

}
