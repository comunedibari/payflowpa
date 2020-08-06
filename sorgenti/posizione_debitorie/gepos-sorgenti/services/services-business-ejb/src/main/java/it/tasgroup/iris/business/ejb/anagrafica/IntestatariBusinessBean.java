package it.tasgroup.iris.business.ejb.anagrafica;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IbanEnti;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessRemote;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.IbanEnteDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IbanEntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "IntestatariBusiness")
public class IntestatariBusinessBean implements IntestatariBusinessLocal, IntestatariBusinessRemote {
	 
	private static final Logger LOGGER = LogManager.getLogger(IntestatariBusinessBean.class);
	
	@EJB(name = "IntestatariDaoImpl")
	IntestatariDAO intestatariDAO;
	
	@EJB(name = "EntiDaoImpl")
	EntiDao entiDao;
	
	@EJB(name = "IbanEntiDaoImpl")
	IbanEntiDao ibanEntiDao;
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusiness#readIntestatarioById(java.lang.String)
	 */
	@Override
	public Intestatari readIntestatarioById(String id){		
		return intestatariDAO.retrieveIntestatarioById(id);
	}
	
	@Override
	public Intestatari readIntestatarioByLapl(String lapl, boolean addressRequired){		
		return intestatariDAO.getIntestatarioByLapl(lapl, addressRequired);
	}

	@Override
	public Enti readEnteByIntestatario(String intestatario) {
		return entiDao.readEnteByIntestatario(intestatario);
	}
	
	@Override
	public String readLAPLEnteByCdAziendaSanitaria(String cdAziendaSanitaria) {
		return intestatariDAO.getLAPLEnteByCdAziendaSanitaria(cdAziendaSanitaria);
	}
	
	@Override
	public Enti readEnte(String codEnte) {
		return entiDao.readEnte(codEnte);
	}

	@Override
	public Enti getEnteByCodiceFiscale(String codFiscaleEnte) {
		return entiDao.readEnteByCodFiscale(codFiscaleEnte);
	}

	@Override
	public List<Enti> getTuttiEnti() {
		return entiDao.listTuttiEnti();
	}

	@Override
	public boolean checkIdFiscaleEnte(String idFiscaleEnte) {
		return intestatariDAO.checkIdFiscaleEnte(idFiscaleEnte);
	} 

	@Override
	public Enti readEnteByIdEnte(String idEnte) {
		return entiDao.getEntiAbleToAnonymousPaymentByIdEnte(idEnte);
	}

	@Override
	public List<IbanEnti> getListIbanByEnte(ContainerDTO inputDTO) {
		return ibanEntiDao.getListIbanByEnte(inputDTO);
	}
	
	public void updateIbanByEnte(IbanEnteDTO ibanDTO) {
		ibanEntiDao.updateIbanEnte(ibanDTO);
	}
}
