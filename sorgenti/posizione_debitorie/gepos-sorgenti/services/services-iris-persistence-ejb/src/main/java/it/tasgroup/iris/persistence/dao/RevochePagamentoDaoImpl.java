package it.tasgroup.iris.persistence.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.revoca.RiepilogoRevocaPagamentoVO;
import it.nch.idp.backoffice.revoca.RevocaPagamentoVO;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.RevochePagamentoDao;
import it.tasgroup.iris.persistence.dao.util.DaoUtil;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name = "RevochePagamentoDaoService")
public class RevochePagamentoDaoImpl extends DaoImplJpaCmtJta<RevochePagamento> implements RevochePagamentoDao {

    private static final Logger LOGGER = LogManager.getLogger(RevochePagamentoDaoImpl.class);
    
    @PersistenceContext(unitName = "IrisPU")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<RevochePagamento> getRevocaPagamentoByIdPagamento(Long idPagamento) {
		List<RevochePagamento> rp = null;
		String query = "select * from REVOCHE_PAGAMENTO where ID_PAGAMENTI = :idPagamento order by TS_INSERIMENTO desc";
		Query theQuery = em.createNativeQuery(query, RevochePagamento.class);
		theQuery.setParameter("idPagamento", idPagamento);
		try {
			rp = (List<RevochePagamento>)theQuery.getResultList();
		} catch (Exception e) {
			LOGGER.info("nessuna revoca trovata, idPagamento = " + idPagamento);
		}
		return rp;
	}
	
	@Override
	public void updateRevocaPagamento(RevochePagamento revoca) {
		em.merge(revoca);
	}
	
	
	@Override
	public RiepilogoRevocaPagamentoVO getRiepilogoRevocaPagamento(String idPagamento) {
		String querysql = "SELECT  "
				+ "DISTINTE_PAGAMENTO.IUV, "//0
				+ "TRIBUTIENTI.DE_TRB, " //1
				+ "jltpend.ID_PENDENZAENTE, " //2
				+ "PAGAMENTI.IM_PAGATO, " //3
				+ "jltpend.DE_CAUSALE, " //4
				+ "DISTINTE_PAGAMENTO.UTENTE_CREATORE, " //5
				+ "PAGAMENTI.FLAG_INCASSO   " //6
				+ "FROM jltpend, TRIBUTIENTI, PAGAMENTI, DISTINTE_PAGAMENTO "
				+ "WHERE  pagamenti.ID = :idPagamento AND "
				+ "jltpend.ID_PENDENZA = PAGAMENTI.ID_PENDENZA AND "
				+ "TRIBUTIENTI.ID_ENTE = PAGAMENTI.ID_ENTE AND  "
				+ "TRIBUTIENTI.CD_TRB_ENTE = PAGAMENTI.CD_TRB_ENTE AND "
				+ "pagamenti.ID_DISTINTE_PAGAMENTO = DISTINTE_PAGAMENTO.ID";
		Query query = em.createNativeQuery(querysql);
		query.setParameter("idPagamento", Long.decode(idPagamento));
		Object[] obj = (Object[])query.getSingleResult();
		String iuv = (String)obj[0];
	 	String descrizioneDebito = (String)obj[1]; 
	 	String identificativoDebito = (String)obj[2];
	 	BigDecimal importo = (BigDecimal)obj[3];
	 	String causaleDebito = (String)obj[4];
	 	String versante = (String)obj[5];
	 	String flagIncasso = Character.toString((Character)obj[6]);
	 	return new RiepilogoRevocaPagamentoVO(iuv,
				descrizioneDebito, 
				identificativoDebito,
				importo,
				causaleDebito,
				versante,
				flagIncasso);
	}

	@Override
	public List<RevochePagamento> getListaRevochePagamento(ContainerDTO input) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		RevocaPagamentoVO rrVO = (RevocaPagamentoVO)input.getInputDTO();
		String query = "select rev.* from REVOCHE_PAGAMENTO rev "
		            		+ "join PAGAMENTI p ON p.ID = rev.ID_PAGAMENTI "
		            		+ "join DISTINTE_PAGAMENTO dp ON p.ID_DISTINTE_PAGAMENTO = dp.ID ";
		ArrayList<String> whereClause = new ArrayList<String>();
		if (rrVO.getIdTributoLista() != null && !rrVO.getIdTributoLista().isEmpty()) {
			 parameters.put("idTributoLista", rrVO.getIdTributoLista());
			 whereClause.add("p.CD_TRB_ENTE in (:idTributoLista)");
		 }
		if (DaoUtil.isNotEmptyProperty(rrVO.getStatoRevoca())) {
			parameters.put("statoRevoca", rrVO.getStatoRevoca());
			whereClause.add("rev.STATO_REVOCA = :statoRevoca");
		} else {
			whereClause.add("rev.STATO_REVOCA <> 'R'");
		}
		if (DaoUtil.isNotEmptyProperty(rrVO.getIuv())) {
			parameters.put("iuv", rrVO.getIuv());
			whereClause.add("dp.IUV = :iuv");
		}
		
		String where = "";
		for (int i = 0; i < whereClause.size(); i++) {
            if (i > 0) {
            	where += " and ";
            } else {
            	where += " where ";
            }
            where += (whereClause.get(i));
        }
		
		query += where + " order by rev.TS_INSERIMENTO desc ";
		LOGGER.debug("getRevocaPagamentoPsp [ query: " + query + " - params: " + parameters + "]");
		PagingCriteria pagingCriteria = input.getPagingCriteria();
		PagingData pagingData = input.getPagingData();
		try {
			return (List<RevochePagamento>)paginateByQuery(RevochePagamento.class, query, pagingCriteria, pagingData, parameters);
		} catch (Exception e) {
			LOGGER.error("ListaRevochePagamento [ query: " + query + " - params: " + parameters + "]", e);
			throw new DAORuntimeException(e);
		}
	}
	
	
}
