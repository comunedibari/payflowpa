package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.is.fo.tributi.CategoriaTributo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CategoriaTributoDAO extends Dao<CategoriaTributo>{

	public List<CategoriaTributo> listCategoriaTributoByFilterParams(ContainerDTO dtoIn);

	public void changeStatusCategoriaTributoList(List<CategoriaTributoDTO> inputList);

	public CategoriaTributo createCategoriaTributo(CategoriaTributo cat);

	public CategoriaTributo getCategoriaTributoById(String idTributo);

	public CategoriaTributo updateCategoriaTributo(CategoriaTributoDTO catDTO);

	public void deleteCategorieTributi(List<String> selectedIds);

	public List<CategoriaTributo> listCategorieTributiEnte(ContainerDTO dtoIn, String idEnte);

}
