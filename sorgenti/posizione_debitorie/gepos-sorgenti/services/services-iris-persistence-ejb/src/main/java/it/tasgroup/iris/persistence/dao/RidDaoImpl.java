package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.utility.iban.IbanHelper;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.PagamentoRidDTO;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.RidDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;
import it.tasgroup.services.util.enumeration.EnumStatoDRP;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="RidDaoService") 
public class RidDaoImpl extends DaoImplJpaCmtJta<Rid> implements RidDao{
	private static final Logger LOGGER = LogManager.getLogger(RidDaoImpl.class);	
	
	@EJB(name = "IntestatariDaoImpl")
	IntestatariDAO intestatariDAO;
	
//	@EJB(name = "DistintePagamentoDaoImpl")
//	DistintePagamentoDao DPDAO;

	@EJB(name = "GestioneFlussiDaoService")
	GestioneFlussiDao gestioneFlussiDao;
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public List<Rid> listPagamentiRidByIntestatario(String intestatario) throws Exception{
		 try {
			 String query = "getRidByIntestatario";
			 Map<String, String> params = new HashMap<String, String>();
			 
			 params.put("intestatario", intestatario);
			 
			 List<Rid> l = (List<Rid>) listByQuery(query, params);
			 return l;
			 
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "list", e.getMessage(),e);
			throw e;
		}
		 
	}

	@Override
	public Rid createPRid(//IProfileManager profile,
			PagamentoRidDTO riddto, ContoTecnico contoTecnico) throws Exception {
		
		Rid rid = new Rid();
	
		IbanHelper ibanHelper = new IbanHelper(contoTecnico.getIban());
		
		
		rid.setProgressivo(new Integer(1));
		rid.setStato(EnumStatoDRP.IN_CORSO.getChiave());
		rid.setDescrizioneStato(EnumStatoDRP.IN_CORSO.getDescrizione());
		rid.setDataCreazione(new Timestamp(System.currentTimeMillis()));
		rid.setDataScadenza(riddto.getDataScadenza());
		rid.setDataDecorrenza(riddto.getDataScadenza());
		rid.setCausale(CommonConstant.RID_CASUALE_VALUE);
		rid.setImporto(riddto.getImporto());
		rid.setAbiBancaAssuntrice(ibanHelper.getAbi());
		rid.setCabBancaAssuntrice(ibanHelper.getCab());
		rid.setSiaCreditore(CommonConstant.RT_SIA);
		rid.setTipoCodIndividuale("3");
		rid.setDivisa(CommonConstant.CURRENCY);
		rid.setIbanOrdinante(contoTecnico.getIban());
		rid.setRagSocialeCreditore(CommonConstant.RT_RAG_SOCIALE);
		
		rid.setCodRiferimento(riddto.getCodRiferimento());
		
		rid.setAbiBancaDomiciliaria(riddto.getAbiBancaDomiciliaria());
		rid.setCodDebitore(riddto.getCodDebitore());
		rid.setTipoIncassoRid(riddto.getTipoIncassoRid());
		rid.setRagSocialeDebitore(riddto.getRagSocialeDebitore());
		rid.setRiferimentoDebito(riddto.getRiferimentoDebito());
		rid.setFlagIniziativa(riddto.getFlagIniziativa());
		rid.setFlagStorno(riddto.getFlagStorno());
		
		rid.setIndirizzoDebitore(riddto.getIndirizzoDebitore());
		
		Intestatari intestatario = intestatariDAO.getById(Intestatari.class, riddto.getIntestatario());
		rid.setIntestatario(intestatario);
		
		if (riddto.getIdDistintaPagamento() != null){
			GestioneFlussi dp = gestioneFlussiDao.getById(GestioneFlussi.class, riddto.getIdDistintaPagamento());
			rid.setDistintePagamento(dp);
		}
		
		rid.setOpInserimento(riddto.getCodDebitore());
		rid.setTsInserimento(new Timestamp(System.currentTimeMillis()));

		System.out.println("EMA---------------> rid\n"+rid.toString());
		
		Rid ret = create(rid);
		return ret;
	}
	
	
	@Override
	public List<Rid> listEsitiRidByRendicontazioneAndCausale(ContainerDTO containerDTO, String causale){
		
		List<Rid> retList = null;
		
		Long id = (Long) containerDTO.getInputDTOList().get(0);
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);
			params.put("causale", causale);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select rids.* from RID rids "+
							"where ID_RENDICONTAZIONI=:idRendicontazione "+
							"and rids.causale<>:causale";
                        
                        String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";
                        }
			
			retList = paginateByQuery(Rid.class, query, pagingCriteria, pagingData, params);

		} catch (Exception e) {
			LOGGER.error("error on listEsitiRidByRendicontazioneAndCausale ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
		 
	}
	
	@Override
	public Rid getRidById(Long id) {
		
		Rid esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (Rid)uniqueResultByQuery("getRidById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getRidById ", e);
			throw new DAORuntimeException(e);
		}
		return esito;
	}


}
