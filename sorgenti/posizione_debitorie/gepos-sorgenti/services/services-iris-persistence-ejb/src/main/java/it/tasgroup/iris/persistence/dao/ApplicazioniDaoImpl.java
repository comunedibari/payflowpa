package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.profilo.Applicazioni;
import it.nch.is.fo.profilo.Area;
import it.nch.is.fo.profilo.Funzioni;
import it.nch.is.fo.profilo.Servizi;
import it.tasgroup.iris.dto.FunzioniPropDTO;
import it.tasgroup.iris.persistence.dao.interfaces.ApplicazioniDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "ApplicazioniDao")
public class ApplicazioniDaoImpl extends DaoImplJpaCmtJta<Applicazioni> implements ApplicazioniDao {

	private static final Logger LOGGER = LogManager.getLogger(ApplicazioniDaoImpl.class);

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	
	
	private static final String ESTRAI_DATI_MENU_SQL = "select area.codarea,area.applicazione, serv.codservizio, funz.codfunzione,funz.priority,funz.tipoperatore"
			+ " from AREA area, SERVIZI serv, FUNZIONI funz"
			+ " where area.applicazione=? AND area.codarea=serv.codarea and serv.codservizio=funz.codservizio"
			+ " and serv.abl_global = ? and funz.abl_global = ?" + " order by area.codarea, serv.codservizio,funz.priority ";

	
	@Override
	public Applicazioni estraiDatiMenu(String applicazione) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("estraiDatiMenu");
				LOGGER.debug("estraiDatiMenu applicazione[" + applicazione + "]");
			}
			Applicazioni apl = new Applicazioni();

			if (LOGGER.isDebugEnabled())
				LOGGER.debug("estraiDatiMenu query = " + ESTRAI_DATI_MENU_SQL);

			Query q = em.createNativeQuery(ESTRAI_DATI_MENU_SQL);
			q.setParameter(1, applicazione);
			q.setParameter(2, "S");
			q.setParameter(3, "S");

			@SuppressWarnings("unchecked")
			List<Object[]> result = q.getResultList();

			Set<Area> listaArea = new LinkedHashSet<Area>();
			Set<Servizi> listaServizi = new LinkedHashSet<Servizi>();
			Set<Funzioni> listaFunzioni = new LinkedHashSet<Funzioni>();

			Servizi servizioCorrente = null;
			Funzioni funzioneCorrente = null;

			apl.setApplicationCode(applicazione);
			Area currentArea = null;
			if (result != null) {
				for (Object[] itemList : result) {

					String codarea = (String) itemList[0];
					// String codapplicazione = (String) itemList[1];
					String codservizio = (String) itemList[2];
					String codfunzione = (String) itemList[3];
					Integer priority = ((Number) itemList[4]).intValue();
					String tipoperatore = (String) itemList[5];

					if (servizioCorrente == null) {

						servizioCorrente = new Servizi();
						servizioCorrente.setServiceCode(codservizio);
						listaFunzioni = new LinkedHashSet<Funzioni>();

					} else {

						if (!codservizio.equals(servizioCorrente.getServiceCode())) {
							servizioCorrente.setFunzioni(listaFunzioni);

							listaServizi.add(servizioCorrente);
							listaFunzioni = new LinkedHashSet<Funzioni>();
							servizioCorrente = new Servizi();
							servizioCorrente.setServiceCode(codservizio);
						}
					}

					if (currentArea == null) {

						currentArea = new Area();
						currentArea.setAreaCode(codarea);
						listaServizi = new LinkedHashSet<Servizi>();

					} else {

						if (!codarea.equals(currentArea.getAreaCode())) {
							currentArea.setServizi(listaServizi);
							listaArea.add(currentArea);
							listaServizi = new LinkedHashSet<Servizi>();
							currentArea = new Area();
							currentArea.setAreaCode(codarea);
						}
					}

					funzioneCorrente = new Funzioni();
					funzioneCorrente.setFunctionCode(codfunzione);
					funzioneCorrente.setPriority(priority);
					funzioneCorrente.setOperatorType(tipoperatore);
					funzioneCorrente.setServiziobj(servizioCorrente);
					listaFunzioni.add(funzioneCorrente);

				}
				servizioCorrente.setFunzioni(listaFunzioni);
				listaServizi.add(servizioCorrente);
				currentArea.setServizi(listaServizi);
				listaArea.add(currentArea);
				apl.setAree(listaArea);

				if (LOGGER.isDebugEnabled()) {
					for (Area a : listaArea) {
						LOGGER.debug("CodArea: ....... " + a.getAreaCode());
						for (Servizi s : a.getServizi()) {
							LOGGER.debug("CodServizio: ........... " + s.getServiceCode());
							for (Funzioni f : s.getFunzioni()) {
								LOGGER.debug("CodFunzione: ................... " + f.getFunctionCode());
							}
						}
					}
				}
			}

			return apl;

		} catch (Exception e) {
			LOGGER.error("error on estraiDatiMenu ", e);
			throw new DAORuntimeException(e);
		}

	}

	@Override
	public String getCodApplicazioneByCategoria(String categoria) {

		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("categoria", categoria);			
		try {
			
			return (String) uniqueResultByQuery("getCodApplicazioneByCategoria", params, em);
			
		} catch (Exception e) {
			LOGGER.error("error on  getCodApplicazioneByCategoria, params = " + params, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public List<Applicazioni> getListaApplicazioni() {
		try {
			TypedQuery<Applicazioni> query = em.createQuery("from Applicazioni", Applicazioni.class);
			return query.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("error on  getListaApplicazioni", e);
			throw new DAORuntimeException(e);
		}
	}

	
	private static final String FUNZIONI_BY_APPLICAZIONE_CLASSE_SQL = "select f.codfunzione, f.descrizione, "
			+ " case coalesce(c.applicazione,'F') when 'F' then 'F' else 'T' end selezionato " 
			+ " from FUNZIONI f "
			+ " left outer join ( select * from CLASSIABIL where nomeclasse= ? and applicazione =? ) c on f.codfunzione = c.funzione "
			+ " where f.codservizio in " 
			+ " ( select codservizio from SERVIZI where codarea in( select codarea from AREA where applicazione = ?)) "
			+ " order by f.codfunzione ";
	
	@Override
	public List<FunzioniPropDTO> getFunzioniByApplicazioneClasse(String applicazione, String classe) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getFunzioniByApplicazioneClasse query = " + FUNZIONI_BY_APPLICAZIONE_CLASSE_SQL);
				LOGGER.debug("getFunzioniByApplicazioneClasse params applicazione=" + applicazione + ", classe=" + classe);
			}
			
			Query q = em.createNativeQuery(FUNZIONI_BY_APPLICAZIONE_CLASSE_SQL);
			q.setParameter(1, classe);
			q.setParameter(2, applicazione);
			q.setParameter(3, applicazione);
			
			@SuppressWarnings("unchecked") 
			List<Object[]> result = q.getResultList();
			
			List<FunzioniPropDTO> listaFunzioni = new ArrayList<FunzioniPropDTO>();
			for (Object[] row : result) {
				FunzioniPropDTO fp = new FunzioniPropDTO();
				
				fp.setFunzione((String)row[0]);
				fp.setDescrizioneFunzione((String)row[1]);
				if (row[2] instanceof Character) {
					Character c = (Character)row[2];
					fp.setAbilitata(c.toString());
				} else {				
				   fp.setAbilitata((String)row[2]);
				}
				listaFunzioni.add(fp);
			}
			return listaFunzioni;
			
		} catch (Exception e) {
			LOGGER.error("error getFunzioniByApplicazioneClasse, param applicazione=" + applicazione + ", classe=" + classe, e);
			throw new DAORuntimeException(e);
		}

	}

}
