package it.tasgroup.services.iuvgenerator;


import it.nch.is.fo.tributi.TributoEnte;

import java.util.List;

import javax.ejb.Local;

@Local
public interface IUVGeneratorLocal {

	public List<String> IUVGeneratorByTributoEnte(TributoEnte trib,int numToBook);
}
