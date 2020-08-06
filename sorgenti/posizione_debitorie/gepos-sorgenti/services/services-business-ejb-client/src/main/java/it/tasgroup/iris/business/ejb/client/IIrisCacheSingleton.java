/**
 * 
 */
package it.tasgroup.iris.business.ejb.client;

import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.Province;

import java.io.Serializable;
import java.util.List;

/**
 * @author pazzik
 *
 */
public interface IIrisCacheSingleton extends Serializable{
	
	public ContoTecnico getContoTecnico();
	
	public Intestatari getAnagraficaRT();
	
	public List<Province> getProvinceItaliane();
	
}
