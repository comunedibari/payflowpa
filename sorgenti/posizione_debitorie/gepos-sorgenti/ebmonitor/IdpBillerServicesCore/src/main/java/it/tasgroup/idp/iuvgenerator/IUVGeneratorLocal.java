package it.tasgroup.idp.iuvgenerator;

import it.tasgroup.idp.domain.enti.TributiEnti;

import java.util.List;

import javax.ejb.Local;

@Local
public interface IUVGeneratorLocal {

	public List<String> IUVGeneratorByTributoEnte(TributiEnti trib,int numToBook);
}
