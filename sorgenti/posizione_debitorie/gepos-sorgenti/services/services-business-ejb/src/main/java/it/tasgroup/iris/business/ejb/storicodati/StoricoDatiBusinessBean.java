package it.tasgroup.iris.business.ejb.storicodati;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.tasgroup.iris.domain.SveLog;
import it.tasgroup.iris.domain.SveStato;
import it.tasgroup.iris.persistence.dao.interfaces.MonitoraggioPuliziaCaricamentiDao;


@Stateless(name = "StoricoDatiBusiness")
public class StoricoDatiBusinessBean implements StoricoDatiBusinessLocal, StoricoDatiBusinessRemote {
	
    @EJB(name = "StoricoDatiDaoService")
    private MonitoraggioPuliziaCaricamentiDao monitoraggioPuliziaCaricamentiDao;

	@Override
	public List<SveLog> getListaLogSvecchiamento(String nomeProcesso) {
		return monitoraggioPuliziaCaricamentiDao.getListaLogSvecchiamento(nomeProcesso);
	}
	
	@Override
	public List<SveStato> getListaStatoSvecchiamento() {
		return (List<SveStato>) monitoraggioPuliziaCaricamentiDao.getListaStatoSvecchiamento();
	}
	
}
