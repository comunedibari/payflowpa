package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;
import java.util.List;

import javax.ejb.Local;

@Local
public interface PagamentiOnLineDao extends Dao<PagamentiOnline> {
	
	public PagamentiOnline getPagamentoOnLineByCodAutorizzazione(String codAutorizzazione);

	public PagamentiOnline savePOL(PagamentiOnline pol);
        
        public List<Object[]> readSystemIdsList();
        
        public List<Object[]> readApplicationIdsList();
        
        public List<Object[]> readTiOperationsList();
        
        public List<Object[]> listaOperazioniEsitiByFilters(ContainerDTO input);
        
        public List<PagamentiOnline> listaDettaglioOperazioniEsitiByFilters(ContainerDTO input);
}
