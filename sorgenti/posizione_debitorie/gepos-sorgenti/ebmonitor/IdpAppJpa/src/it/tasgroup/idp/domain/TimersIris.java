package it.tasgroup.idp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TIMERSIRIS")

@NamedQueries({
@NamedQuery(
	name="ListaTimersIris", 
	query="SELECT t FROM TimersIris t"),
@NamedQuery(
	name="getTimerByName", 
	query="SELECT t FROM TimersIris t WHERE t.timer = :timer")})

public class TimersIris implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String gruppo;

	@Column(name="INTERVAL")
	private int interval;

	@Column(name="NODE_ACTIVE")
	private String nodeActive;

	@Column(name="NODE_DEFAULT")
	private String nodeDefault;
	
	@Column(name="SUSPEND")
	private String suspend;

	private int period;

	private String timer;

	private Date startup;
	
	public TimersIris() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGruppo() {
		return this.gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public int getInterval() {
		return this.interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getNodeActive() {
		return this.nodeActive;
	}

	public void setNodeActive(String nodeActive) {
		this.nodeActive = nodeActive;
	}

	public String getNodeDefault() {
		return this.nodeDefault;
	}

	public void setNodeDefault(String nodeDefault) {
		this.nodeDefault = nodeDefault;
	}

	public int getPeriod() {
		return this.period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getTimer() {
		return this.timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public Date getStartup() {
		return startup;
	}

	public void setStartup(Date startup) {
		this.startup = startup;
	}
	
	public String getSuspend() {
		return this.suspend;
	}

	public void setSuspend(String suspend) {
		this.suspend = suspend;
	}


	@Override
	public String toString() {
		return "TimersIris{" +
				"id=" + id +
				", gruppo='" + gruppo + '\'' +
				", interval=" + interval +
				", nodeActive='" + nodeActive + '\'' +
				", nodeDefault='" + nodeDefault + '\'' +
				", period=" + period +
				", timer='" + timer + '\'' +
				", startup=" + startup + '\'' +
				", suspend='" + suspend +  
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TimersIris that = (TimersIris) o;

		if (id != that.id) return false;
		if (interval != that.interval) return false;
		if (period != that.period) return false;
		if (!gruppo.equals(that.gruppo)) return false;
		if (!nodeActive.equals(that.nodeActive)) return false;
		if (!nodeDefault.equals(that.nodeDefault)) return false;
		return timer.equals(that.timer);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + gruppo.hashCode();
		result = 31 * result + interval;
		result = 31 * result + nodeActive.hashCode();
		result = 31 * result + nodeDefault.hashCode();
		result = 31 * result + period;
		result = 31 * result + timer.hashCode();
		return result;
	}
}

