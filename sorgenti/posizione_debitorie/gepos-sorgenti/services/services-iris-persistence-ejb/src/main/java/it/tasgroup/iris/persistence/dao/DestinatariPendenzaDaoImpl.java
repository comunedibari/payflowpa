package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.persistence.dao.interfaces.DestinatariPendenzaDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "DestinatariPendenzaDaoService")
@SuppressWarnings("unchecked")
public class DestinatariPendenzaDaoImpl extends DaoImplJpaCmtJta<DestinatariPendenza> implements DestinatariPendenzaDao {
	private static final Logger LOGGER = LogManager.getLogger(DestinatariPendenzaDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<DestinatariPendenza> listaDestinatariByCodiceFiscale(String codiceFiscale) {
		List<DestinatariPendenza> listaOut = new ArrayList<DestinatariPendenza>();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("coDestinatario", codiceFiscale);
		try {
			listaOut = (List<DestinatariPendenza>) listByQuery("listByCodiceFiscale", parameters);
		} catch (Exception e) {
			LOGGER.error("error on getByIDPendenza ", e);
			throw new DAORuntimeException(e);
		}
		return listaOut;

	}

	@Override
	public List<DestinatariPendenza> listaDestinatariByCodiceFiscaleAndStato(String codiceFiscale, String statoPendenza) {
		List<DestinatariPendenza> listaOut = new ArrayList<DestinatariPendenza>();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("coDestinatario", codiceFiscale);
		if (statoPendenza.equals("DA PAGARE"))
			statoPendenza = "A";
		parameters.put("statoPendenza", statoPendenza);
		try {
			listaOut = (List<DestinatariPendenza>) listByQuery("listByCodiceFiscaleAndStatoPendenza", parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaOut;
	}

	@Override
	public List<DestinatariPendenza> ListaDestinatariByCodiceFiscaleStatoAndDate(String codiceFiscale, String statoPendenza, XMLGregorianCalendar dataIni,
			XMLGregorianCalendar dataFin) {

		List<DestinatariPendenza> listaOut = new ArrayList<DestinatariPendenza>();
		Timestamp dataIniTimestampFormat = null;
		Timestamp dataFinTimestampFormat = null;
		if (dataIni != null) {
			GregorianCalendar gcDataIni = dataIni.toGregorianCalendar();
			Calendar cDataIni = gcDataIni;
			Long millDataIni = cDataIni.getTimeInMillis();
			dataIniTimestampFormat = new Timestamp(millDataIni);
		}
		if (dataFin != null) {
			GregorianCalendar gcDataFin = dataFin.toGregorianCalendar();
			Calendar cDataFin = gcDataFin;
			Long millDataFin = cDataFin.getTimeInMillis();
			dataFinTimestampFormat = new Timestamp(millDataFin);
		}
		try {
			listaOut = createListByCodiceFiscaleAndDateQuery(codiceFiscale, dataIniTimestampFormat, dataFinTimestampFormat).getResultList();
		} catch (Exception e) {
			LOGGER.error("error on ListaDestinatariByCodiceFiscaleStatoAndDate ", e);
			throw new DAORuntimeException(e);
		}
		return listaOut;
	}

	private Query createListByCodiceFiscaleAndDateQuery(String codFisc, java.sql.Timestamp dataIni, java.sql.Timestamp dataFin) {

		String strquery = "select dest from DestinatariPendenza dest where dest.coDestinatario=? ";
		if (dataIni != null) {
			strquery = strquery + " and dest.pendenza.tsDecorrenza > ?";
		}
		if (dataFin != null) {
			strquery = strquery + " and dest.pendenza.tsPrescrizione < ? ";
		}
		Query query = em.createQuery(strquery);
		query.setParameter(1, codFisc);
		int i = 2;
		if (dataIni != null) {
			query.setParameter(i, dataIni);
			i++;
		}
		if (dataFin != null) {
			query.setParameter(i, dataFin);
		}
		query.setMaxResults(100);
		return query;
	}

	@Override
	public List<String> listaDebitoriByIdPendenza(String idPendenza) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idPendenza", idPendenza);
		try {
			return (List<String>) this.listByQuery("getDebitoriPendenzaByIdPend", parameters);
		} catch (Exception e) {
			LOGGER.error("error on getByIDPendenza ", e);
			throw new DAORuntimeException(e);
		}
	}

}
