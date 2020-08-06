package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaStatoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaStatoService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;

/**
 * @author Marianna Memoli
 */
@Service("anagraficaStatoService")
public class AnagraficaStatoServiceImpl implements AnagraficaStatoService {

	private static Log log = LogFactory.getLog(AnagraficaStatoServiceImpl.class);
	
	@Autowired
	private AnagraficaStatoDao anagraficaStatoDao;

	@Autowired
	private ModelMapperUtil modelMapperUtil;
	
	/**
	 * Restituisce l'elenco degli stati del tipo passato in ingresso alla funzione.
	 * 
	 * @param {@link String} deTipoStato, codice tipo stato
	 * 
	 * @return {@link List<AnagraficaStatoTO>}
	 * @author Marianna Memoli
	 */
	@Override
	public List<AnagraficaStatoTO> getAllByTipo(String deTipoStato) {
		try{
			log.debug("Get elenco stati del tipo " + deTipoStato);
			
			List<AnagraficaStato> entities = anagraficaStatoDao.getAllByTipo(deTipoStato);
			
			List<AnagraficaStatoTO> beans = new ArrayList<AnagraficaStatoTO>();
			
			for (AnagraficaStato item : entities) {
				/* copy entity to dto */
				AnagraficaStatoTO to = modelMapperUtil.map(item, AnagraficaStatoTO.class);
				/* */
				beans.add(to);
			}
			
			log.debug("Trovati N° " + beans.size() + " stati del tipo " + deTipoStato);
			
			return beans;
		}catch(Exception e){
			log.error("getAllByTipo >>> ", e);
			throw(e);
		}
	}
}
