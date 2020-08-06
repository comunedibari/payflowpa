package it.tasgroup.idp.pojo;

import java.sql.Timestamp;

public class FilterData {
	
	public Timestamp getTsStart() {
		return tsStart;
	}
	public void setTsStart(Timestamp tsStart) {
		this.tsStart = tsStart;
	}
	public Timestamp getTsEnd() {
		return tsEnd;
	}
	public void setTsEnd(Timestamp tsEnd) {
		this.tsEnd = tsEnd;
	}
	private Timestamp tsStart;
	private Timestamp tsEnd;

}
