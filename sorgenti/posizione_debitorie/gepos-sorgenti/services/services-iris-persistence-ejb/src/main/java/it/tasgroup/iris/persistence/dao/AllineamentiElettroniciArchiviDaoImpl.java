package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllineamentiElettroniciArchiviDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumCausaleAEA;
import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;
import it.tasgroup.services.util.enumeration.EnumStatoAEA;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="AllineamentiElettroniciArchiviDaoService") 
public class AllineamentiElettroniciArchiviDaoImpl extends DaoImplJpaCmtJta<AllineamentiElettroniciArchivi> implements AllineamentiElettroniciArchiviDao{
	private static final Logger LOGGER = LogManager.getLogger(AllineamentiElettroniciArchiviDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	
	@EJB(name = "IntestatariDaoImpl")
	IntestatariDAO intestatariDAO;
	
//	@EJB(name = "DistintePagamentoDaoImpl")
//	DistintePagamentoDao DPDAO;

	@EJB(name = "GestioneFlussiDaoService")
	GestioneFlussiDao gestioneFlussiDao;
	
	@Override
	public List<AllineamentiElettroniciArchivi> listAEAByIntestatario(
			ContainerDTO containerDTO, String causale)
			throws Exception {
		
		try {
			 
			 Map<String, String> parameters = new HashMap<String, String>();
			 		 
			 String filteringQuery= buildQueryAndParameters(containerDTO, causale, parameters);
				
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
				
			PagingData pagingData = containerDTO.getPagingData();
				
			List<AllineamentiElettroniciArchivi> l = paginateByQuery(AllineamentiElettroniciArchivi.class, filteringQuery, pagingCriteria, pagingData, parameters);
			 
			 return l;
			 
		} catch (Exception e) {
			
			LOGGER.error("error on listAEAByIntestatario "+ containerDTO, e);
			throw new DAORuntimeException(e);
			
		}
	}
	private String buildQueryAndParameters(ContainerDTO containerDTO, String causale, Map<String, String> parameters){
			
		String query = "select aeas.* from AEA aeas where aeas.CAUSALE=:causale and aeas.INTESTATARIO=:intestatario " +
					"and aeas.ID_FISCALE_SOTTOSCRITTORE=:sottoscrittore " +
					"order by DATA_CREAZIONE desc ";	
		
		 AllineamentiElettroniciArchiviDTO delega = (AllineamentiElettroniciArchiviDTO) containerDTO.getInputDTO();
		 String idFiscaleSottoscrittore = delega.getCodificaFiscaleSottoscrittore();
		 String intestatario = delega.getIntestatario();
		 
		 parameters.put("causale", causale);
		 parameters.put("intestatario", intestatario);
		 parameters.put("sottoscrittore", idFiscaleSottoscrittore);
		 
		return query;
	
	}
	@Override
	public List<AllineamentiElettroniciArchivi> listAEAAccettateBySottoscrittore(
			String sottoscrittore)
			throws Exception {
		try {
			 String query = "getDelegheAccettateBySottoscrittore";
			 Map<String, String> params = new HashMap<String, String>();
			 
//			 params.put("intestatario", intestatario);
			 params.put("sottoscrittore", sottoscrittore);
			 params.put("stato", EnumStatoAEA.ACCETTATA.getChiave());
			 
			 List<AllineamentiElettroniciArchivi> l = (List<AllineamentiElettroniciArchivi>) listByQuery(query, params);
			 return l;
			 
		} catch (Exception e) {
			LOGGER.error("Error on listAEAAccettateBySottoscrittore", e);
			throw e;
		}
	}
	
	@Override
	public List<AllineamentiElettroniciArchivi> listAEAAccettateByIntestatario(
			String sottoscrittore)
					throws Exception {
		try {
			String query = "getDelegheAccettateBySottoscrittore";
			Map<String, String> params = new HashMap<String, String>();
			
//			 params.put("intestatario", intestatario);
			params.put("sottoscrittore", sottoscrittore);
			params.put("stato", EnumStatoAEA.ACCETTATA.getChiave());
			
			List<AllineamentiElettroniciArchivi> l = (List<AllineamentiElettroniciArchivi>) listByQuery(query, params);
			return l;
			
		} catch (Exception e) {
			LOGGER.error("Error on listAEAAccettateByIntestatario", e);
			throw e;
		}
	}

	@Override
	public AllineamentiElettroniciArchivi createAEA(//IProfileManager profile,
			AllineamentiElettroniciArchiviDTO dto) throws Exception {
		
		try {
			
			AllineamentiElettroniciArchivi aea = new AllineamentiElettroniciArchivi();
			
			// non sono sicura che profile sia l'utente in sessione
			String operatorUsername = dto.getOperatoreUsername();
			String operatorRagSocAzienza = dto.getRagSocSottoscrittore();
			//attenzione mancano i dati nome e cognome
//			String operatorNomeCognome = null;
			//attenzione manca l'indirizzo
			String operatorIndirizzo = dto.getIndirizzoSottoscrittore(); 
			
//			per il cittadino operatore e intestatario coincidono
//			per azienda l'operatopre è la persona delegata dall'azienda a fare un operazione
//			mentre l'intestatario è l'azienda stessa
			
			//dipendono dall'utente in sessione
			
			aea.setCodIndividuale(dto.getCodIndividuale() != null ? dto.getCodIndividuale():operatorUsername);
			
			aea.setIdFiscaleSottoscrittore(operatorUsername);
			aea.setRagSocialeSottoscrittore(operatorRagSocAzienza);
			aea.setIndirizzoSottoscrittore(operatorIndirizzo);
			aea.setRagSocialeIntAddebito(operatorRagSocAzienza);
			
			//vedi dto
			aea.setDataCreazione(dto.getDataRichiesta());
			aea.setDataAttivazione(dto.getDataAttivazione());
			aea.setDataCessazione(dto.getDataCessazione());
			aea.setCausale(dto.getCausale());
			aea.setProgressivo(dto.getProgressivo());
			aea.setStato(dto.getStato());
			aea.setDescrizioneStato(dto.getDescrizioneStato());
			aea.setTipoCodIndividuale(dto.getTipoCodIndividuale());
			aea.setIbanAddebito(dto.getIbanAddebito().toUpperCase());
			aea.setCodPaeseAddebito(dto.getCodPaeseAddebito());
			aea.setCheckDigitAddebito(dto.getCheckDigitAddebito());
			aea.setCinAddebito(dto.getCinAddebito());
			aea.setAbiAddebito(dto.getAbiAddebito());
			aea.setCabAddebito(dto.getCabAddebito());
			aea.setNumeroCcAddebito(dto.getNumeroCcAddebito());
			aea.setTipoIncassoRid(dto.getTipoIncassoRid().getChiave());
			aea.setDivisa(CommonConstant.CURRENCY);

			aea.setFlagStorno(dto.getFlagStorno().getChiave());
			aea.setFlagIniziativa(dto.getFlagIniziativa());
			
			
			aea.setCodRiferimento(dto.getCodRiferimento());

			//da conf
			aea.setIdFiscaleCreditore(dto.getCodificaFiscaleCreditore());
			aea.setRagSocialeCreditore(dto.getRagSocCreditore());
			aea.setSiaCreditore(dto.getSiaCreditore());
			aea.setAbiBancaAllineamento(dto.getAbiBancaAllineamento());
			
			// questi sono fk attenzione
			Intestatari intestatario = intestatariDAO.getById(Intestatari.class, dto.getIntestatario());
			aea.setIntestatario(intestatario);
			
			if (dto.getIdDistintaPagamento() != null){
				GestioneFlussi dp = gestioneFlussiDao.getById(GestioneFlussi.class, dto.getIdDistintaPagamento());
				aea.setDistintePagamento(dp);
			}
			
			aea.setOpInserimento(operatorUsername);
			aea.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			
			System.out.println("delega:\n"+aea.toString());
			
			AllineamentiElettroniciArchivi created = create(aea);
			
			return created;
			
		} catch (Exception e) {
			LOGGER.error("error on  createAEA, " + dto, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public AllineamentiElettroniciArchivi updateAEA(AllineamentiElettroniciArchivi delega) {
		try {
			delega = this.update(delega);
		} catch (Exception e) {
			LOGGER.error("error on retriveAEAById ", e);
			throw new DAORuntimeException(e);
		}
		return delega;
	}

	@Override
	public AllineamentiElettroniciArchivi retriveAEAById(String id) {
		AllineamentiElettroniciArchivi delega = null;
		
		try {
			Long idAea = new Long(id);
			delega = this.getById(AllineamentiElettroniciArchivi.class, idAea);
		} catch (Exception e) {
			LOGGER.error("error on retriveAEAById ", e);
			throw new DAORuntimeException(e);
		}
		return delega;
	}

	@Override
	public AllineamentiElettroniciArchivi getAEAById(Long id) {
		
		AllineamentiElettroniciArchivi esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (AllineamentiElettroniciArchivi)uniqueResultByQuery("getAllineamentiElettroniciArchiviById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getAllineamentiElettroniciArchiviById ", e);
			throw new DAORuntimeException(e);
		}
		return esito;
	}

	@Override
	public List<AllineamentiElettroniciArchivi> listAEAAccettateByIntestAndSottosc(
			String intestatario, String sottoscrittore) throws Exception {
		try {
			 String query = "getDelegheAccettateByIntestAndSottoscr";
			 Map<String, String> params = new HashMap<String, String>();
			 
			 params.put("intestatario", intestatario);
			 params.put("sottoscrittore", sottoscrittore);
			 params.put("stato", EnumStatoAEA.ACCETTATA.getChiave());
			 
			 List<AllineamentiElettroniciArchivi> l = (List<AllineamentiElettroniciArchivi>) listByQuery(query, params);
			 return l;
			 
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "list", e.getMessage(),e);
			throw e;
		}
	}

	@Override
	public AllineamentiElettroniciArchivi verificaIbanDuplicato(String iban,String sottoscrittore) throws Exception {
		try {
//			String query = "getDelegaByIban";
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("iban", iban);
//			params.put("sottoscrittore", sottoscrittore);			
//			List<AllineamentiElettroniciArchivi> l = (List<AllineamentiElettroniciArchivi>) listByQuery(query, params);
			
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllineamentiElettroniciArchivi> criteria = builder.createQuery( AllineamentiElettroniciArchivi.class );
			Root<AllineamentiElettroniciArchivi> personRoot = criteria.from( AllineamentiElettroniciArchivi.class );
			criteria.select(personRoot);
			
			criteria.where(
				builder.and( 
					builder.equal( personRoot.get( "ibanAddebito" ), iban ),
					builder.equal( personRoot.get( "idFiscaleSottoscrittore" ), sottoscrittore ),
					builder.or( 
						builder.equal(personRoot.get("stato"), EnumStatoAEA.ACCETTATA.getChiave()),
						builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO.getChiave()),
						builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave()),
						builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO_REVOCA.getChiave()),
						builder.equal(personRoot.get("stato"), EnumStatoAEA.VARIATA.getChiave()))));
			
			List<AllineamentiElettroniciArchivi> l = em.createQuery( criteria ).getResultList();
		    
			return l != null && l.size()>0 ? l.get(0):null;
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "list", e.getMessage(),e);
			throw e;
		}
	}
	
	@Override
	public AllineamentiElettroniciArchivi verificaAbiDuplicato(String abi,String sottoscrittore) throws Exception {
		try {
//			String query = "getDelegaByIban";
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("iban", iban);
//			params.put("sottoscrittore", sottoscrittore);			
//			List<AllineamentiElettroniciArchivi> l = (List<AllineamentiElettroniciArchivi>) listByQuery(query, params);
			
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AllineamentiElettroniciArchivi> criteria = builder.createQuery( AllineamentiElettroniciArchivi.class );
			Root<AllineamentiElettroniciArchivi> personRoot = criteria.from( AllineamentiElettroniciArchivi.class );
			criteria.select(personRoot);
			
			criteria.where(
					builder.and( 
							builder.equal( personRoot.get( "abiAddebito" ), abi ),
							builder.equal( personRoot.get( "idFiscaleSottoscrittore" ), sottoscrittore ),
							builder.or( 
									builder.equal(personRoot.get("stato"), EnumStatoAEA.ACCETTATA.getChiave()),
									builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO.getChiave()),
									builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO_APPROVAZIONE.getChiave()),
									builder.equal(personRoot.get("stato"), EnumStatoAEA.IN_CORSO_REVOCA.getChiave()),
									builder.equal(personRoot.get("stato"), EnumStatoAEA.VARIATA.getChiave()))));
			
			List<AllineamentiElettroniciArchivi> l = em.createQuery( criteria ).getResultList();
			
			return l != null && l.size()>0 ? l.get(0):null;
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "list", e.getMessage(),e);
			throw e;
		}
	}
	
	@Override
	public List<AllineamentiElettroniciArchivi> listEsitiAeaByRendicontazioneAndCausale(ContainerDTO containerDTO){
		
		List<AllineamentiElettroniciArchivi> retList = null;
		
		Long id = (Long) containerDTO.getInputDTOList().get(0);
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select aea.* from AEA aea "+
							"where ID_RENDICONTAZIONI=:idRendicontazione "+
							"and aea.causale not in('90211', '90218','90440')";

                        String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";

                        }
			
			retList = paginateByQuery(AllineamentiElettroniciArchivi.class, query, pagingCriteria, pagingData, params);

		} catch (Exception e) {
			LOGGER.error("error on listEsitiRidByRendicontazioneAndCausale ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
		 
	}

	
}
