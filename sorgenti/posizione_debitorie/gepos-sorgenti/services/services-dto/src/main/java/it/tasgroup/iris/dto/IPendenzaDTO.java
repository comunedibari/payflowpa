/**
 * 
 */
package it.tasgroup.iris.dto;

import java.util.List;

/**
 * @author pazzik
 *
 */
public interface IPendenzaDTO {

	public List getCondizioni();

	public String getDescrizioneEnte();

	public String getDescrizioneTributo();

	public String getCausale();

	public Object getIdTributoStrutturato();

	public Object getTributoStrutturato();

	public String getIdPendenzaEnte();
	
	public String getNote();

}
