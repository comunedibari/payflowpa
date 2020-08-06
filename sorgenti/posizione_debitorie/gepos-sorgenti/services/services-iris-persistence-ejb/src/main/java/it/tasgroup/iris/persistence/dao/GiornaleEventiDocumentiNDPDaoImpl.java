package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.IrisGatewayClientShopCart;
import it.tasgroup.iris.gde.GiornaleEventiDocumentiNDP;
import it.tasgroup.iris.persistence.dao.interfaces.GiornaleEventiDocumentiNDPDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="GiornaleEventiDocumentiNDPDaoService")
public  class GiornaleEventiDocumentiNDPDaoImpl extends DaoImplJpaCmtJta<GiornaleEventiDocumentiNDP> implements GiornaleEventiDocumentiNDPDao {

	private static final Logger LOGGER = LogManager.getLogger(GiornaleEventiDocumentiNDPDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public GiornaleEventiDocumentiNDP getGiornaleEventiDocumenti(String codContesto, String idDominio, String iuv, String tipo,String idPsp){
		
		GiornaleEventiDocumentiNDP result = null;		
		Map params = new HashMap();		
		params.put("TIPO", tipo);
		params.put("ID_DOMINIO", idDominio);
		params.put("ID_UNIVOCO_VERSAMENTO", iuv);
		params.put("COD_CONTESTO_PAGAMENTO", codContesto);
		if (idPsp!=null) {
		  params.put("ID_PSP",idPsp);
		}
		try {
			List<GiornaleEventiDocumentiNDP> l = null;
			if (idPsp!=null) 
			   l=(List<GiornaleEventiDocumentiNDP>) listByQuery("getDocumentiNDP", params);
			else 
			   l=(List<GiornaleEventiDocumentiNDP>) listByQuery("getDocumentiNDPNoIdPsp", params);
			
			if (l==null || l.isEmpty()) {
				return null;
			}else {
				return l.get(0);
			}			
		} catch (Exception e) {			
			LOGGER.error("error on getGiornaleEventiDocumenti ", e);			
			throw new DAORuntimeException(e);
		}		
	}
	
	@Override
	public Long readVersion(String codContesto, String idDominio, String iuv, String tipo) {
		
		Long result = null;
		
		Map params = new HashMap();
		
		params.put("TIPO", tipo);	
		
		params.put("ID_DOMINIO", idDominio);
		
		params.put("ID_UNIVOCO_VERSAMENTO", iuv);
		
		params.put("COD_CONTESTO", codContesto);
		
		try {
					
			result = (Long) uniqueResultByQuery("getVersionDocumentiNDP", params);
			
		} catch (Exception e) {
			
			LOGGER.error("error on readVersion ", e);
			
			throw new DAORuntimeException(e);
		}
		
		return result;
	}
	
	@Override
	public void updateAckDownload(String ackDownload, String codContesto, String idDominio, String iuv, String tipo, Long version) {
		
		Query query = createUpdateQuery(ackDownload, version, codContesto, idDominio, iuv, tipo);
		
		query.executeUpdate();
		
	}	
	
	private Query createUpdateQuery(String ack_download, Long version, String codContesto, String idDominio, String iuv, String tipo) {

		String selectFromWhere = "update GDE_DOCUMENTI_NDP set ACK_DOWNLOAD = ?, VERSION = ?, TS_AGGIORNAMENTO = ?, OP_AGGIORNAMENTO = ? where ID_UNIVOCO_VERSAMENTO = ? and CODICE_CONTESTO_PAGAMENTO = ? and TIPO = ? and ID_DOMINIO = ?";
		
		Query query = em.createNativeQuery(selectFromWhere);

		query.setParameter(1, ack_download);

		query.setParameter(2, version+1);
		
		query.setParameter(3, new Timestamp(System.currentTimeMillis()));
		
		query.setParameter(4, "DOC_WS");

		query.setParameter(5, iuv);
		
		query.setParameter(6, codContesto);
		
		query.setParameter(7, tipo);
		
		query.setParameter(8, idDominio);

		return query;
	}


}
