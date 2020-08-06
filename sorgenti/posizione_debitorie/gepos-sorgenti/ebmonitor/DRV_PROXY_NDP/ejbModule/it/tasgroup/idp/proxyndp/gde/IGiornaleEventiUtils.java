package it.tasgroup.idp.proxyndp.gde;

import javax.ejb.Local;

@Local
public interface IGiornaleEventiUtils {
	public GiornaleEventiExtDTO saveGDE(Object a, Object b);
}
