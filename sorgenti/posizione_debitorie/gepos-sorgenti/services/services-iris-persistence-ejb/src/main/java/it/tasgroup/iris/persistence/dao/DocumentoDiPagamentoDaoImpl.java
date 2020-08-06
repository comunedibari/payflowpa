package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.helper.CommissioniCalculator;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ListaDocumentiInputDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.DDPDAO;
import it.tasgroup.iris.persistence.dao.util.DDP_IDGenerator;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PazziK
 * 
 */
@Stateless(name = "DocumentoDiPagamentoDao")
public class DocumentoDiPagamentoDaoImpl extends DaoImplJpaCmtJta<DocumentoDiPagamento> implements DDPDAO {

	private static final Logger LOGGER = LogManager.getLogger(DocumentoDiPagamentoDaoImpl.class);

	@EJB(name = "CondizioniPagamentoDaoService")
	CondizioniPagamentoDao condizioniDAO;

	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Integer countDDPByIdCondizione(String idCondizione) {

		Integer counter = null;

		try {

			Query listDDPByParameters = createCountDocumentsQuery(idCondizione);

			counter = (Integer) listDDPByParameters.getSingleResult();

		} catch (Exception e) {
			LOGGER.error("error on countDDPByIdCondizione " + idCondizione, e);
			throw new DAORuntimeException(e);
		}

		return counter;
	}

	@Override
	public void nullifyDDPList(String codFiscale, String[] idDocumenti) {

		try {

			Query nullifyDDPQuery = createNullifyDocumentsQuery(codFiscale, idDocumenti);

			int resultDDP = nullifyDDPQuery.executeUpdate();

			Query nullifyDDPCondQuery = createNullifyDocumentConditionsQuery(codFiscale, idDocumenti);

			int resultDDPCond = nullifyDDPCondQuery.executeUpdate();

		} catch (Exception e) {
			LOGGER.error("error on nullifyDDPList " + idDocumenti, e);
			throw new DAORuntimeException(e);
		}

	}

	@Override
	public List<DocumentoDiPagamento> listDDPByIdCondizione(String idCondizione) {

		List<DocumentoDiPagamento> retList = null;

		try {

			Query listDDPByIdCondizione = createGetDocumentsQuery(idCondizione);

			retList = listDDPByIdCondizione.getResultList();

		} catch (Exception e) {
			LOGGER.error("error on listDDPByIdCondizione " + idCondizione, e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}

	@Override
	public List<DocumentoDiPagamento> listDDPBylistIdCondizioni(List<String> condizioni) {

		List<DocumentoDiPagamento> retList = null;

		try {

			Query listDDPByIdCondizione = createGetDocumentsQuery(condizioni);

			retList = listDDPByIdCondizione.getResultList();

		} catch (Exception e) {
			LOGGER.error("error on listDDPBylistIdCondizioni " + condizioni, e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}

	private Query createGetDocumentsQuery(List<String> condizioni) {

		String selectFromWhere = "Select doc.* from DOCUMENTI_PAGAMENTO doc, CONDIZIONI_DOCUMENTO cond_doc, JLTCOPD cond_pag where ";

		StringBuffer whereConditions = new StringBuffer(
				"doc.ID = cond_doc.ID_DOCUMENTO AND cond_doc.ID_CONDIZIONE = cond_pag.ID_CONDIZIONE AND cond_doc.TS_ANNULLAMENTO IS NULL ");

		whereConditions.append("AND cond_pag.ID_CONDIZIONE IN (");

		appendInCondition(whereConditions, condizioni);
		String ordering = " ORDER BY doc.TS_INSERIMENTO desc";

		Query query = em.createNativeQuery(selectFromWhere + whereConditions + ordering, DocumentoDiPagamento.class);

		addInParameters(query, condizioni, 0);

		return query;
	}

	@Override
	public List<DocumentoDiPagamento> listDDPByFilterParameters(String codFiscale, String azienda, ContainerDTO containerDTO) {

		ListaDocumentiInputDTO inputDTO = (ListaDocumentiInputDTO) containerDTO.getInputDTO();
		
		List<DocumentoDiPagamento> retList = null;
		
		try {

			Map<String, Object> parameters = new HashMap<String, Object>();

			String filteringQuery = buildQueryAndParameters(codFiscale, azienda, inputDTO, parameters);

			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();

			PagingData pagingData = containerDTO.getPagingData();

			retList = paginateByQuery(DocumentoDiPagamento.class, filteringQuery, pagingCriteria, pagingData, parameters);

		} catch (Exception e) {
			LOGGER.error("error on listDDPByFilterParameters " + containerDTO, e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}

	private String buildQueryAndParameters(String codFiscale, String azienda, ListaDocumentiInputDTO dto, Map<String, Object> parameters) {

		String selectFromWhere = "Select doc.* from DOCUMENTI_PAGAMENTO doc, CFG_GATEWAY_PAGAMENTO gtw, CFG_DOCUMENTO_PAGAMENTO cfgDoc "
				+ "where doc.ID_CFG_GATEWAY_PAGAMENTO = gtw.ID and cfgDoc.ID = gtw.ID_CFG_DOCUMENTO_PAGAMENTO ";

		StringBuffer whereConditions = new StringBuffer();
		
		if (!StringUtils.isEmpty(codFiscale) && !StringUtils.isEmpty(azienda)){
			
			whereConditions = new StringBuffer("AND doc.OP_INSERIMENTO =:OP_INSERIMENTO AND doc.INTESTATARIO =:INTESTATARIO");
			parameters.put("OP_INSERIMENTO", codFiscale);
			parameters.put("INTESTATARIO", azienda);
			
		}
		

		String id = dto.getDocIdFilter();
		
		if (id != null && id.trim().length() > 0){
			
			if(!id.contains("*")) {
				
				whereConditions.append(" AND doc.ID = :ID_DOCUMENTO");
				parameters.put("ID_DOCUMENTO", id);
				
			} else {
			
				whereConditions.append(" AND doc.ID like :ID_DOCUMENTO");
				parameters.put("ID_DOCUMENTO", id.replaceAll("[*]", "%"));
			}
		}
	
			
		String coPagante = dto.getCodPagante();
		if (coPagante != null && coPagante.trim().length() > 0) {
			
			if(!coPagante.contains("*")) {
				
				whereConditions.append(" AND doc.CO_PAGANTE =:CO_PAGANTE");
				parameters.put("CO_PAGANTE", coPagante.toUpperCase());
				
			} else {
				
				whereConditions.append(" AND doc.CO_PAGANTE like :CO_PAGANTE");
				parameters.put("CO_PAGANTE", coPagante.replaceAll("[*]", "%").toUpperCase());
				
			}
		}
		
		String emailPagante = dto.getEmailPagante();
		if (emailPagante != null && emailPagante.trim().length() > 0) {
			
			if(!emailPagante.contains("*")) {
				
				whereConditions.append(" AND doc.EMAIL_VERSANTE =:EMAIL_VERSANTE");
				parameters.put("EMAIL_VERSANTE", emailPagante);
				
			} else {
				
				whereConditions.append(" AND doc.EMAIL_VERSANTE like :EMAIL_VERSANTE");
				parameters.put("EMAIL_VERSANTE", emailPagante.replaceAll("[*]", "%"));
			}
		}

		EnumTipoDDP tipo = dto.getDocTypeFilter();
		if (tipo != null) {
			whereConditions.append(" AND gtw.ID_CFG_DOCUMENTO_PAGAMENTO =:ID_CFG_DOCUMENTO_PAGAMENTO");
			parameters.put("ID_CFG_DOCUMENTO_PAGAMENTO", tipo.getIdCfgDocumentoPagamento());
		}

		EnumStatoDDP stato = dto.getDocStateFilter();
		if (stato != null) {
			whereConditions.append(" AND doc.STATO =:STATO");
			parameters.put("STATO", stato.getChiave());
		}

		Date dataDa = dto.getStartDateFilter();
		if (dataDa != null) {
			whereConditions.append(" AND doc.TS_EMISSIONE >=:TS_EMISSIONE_DA");
			parameters.put("TS_EMISSIONE_DA", dataDa);
		}

		// importo da
		BigDecimal importoDa = dto.getImportoDa();
		if (importoDa !=null) {
			whereConditions.append(" AND (doc.IMPORTO + doc.IMPORTO_COMMISSIONI) >=:IMPORTO_DA ");
			parameters.put("IMPORTO_DA", importoDa);
		}
		// importo a
		BigDecimal importoA = dto.getImportoA();
		if (importoA !=null) {
			whereConditions.append(" AND (doc.IMPORTO + doc.IMPORTO_COMMISSIONI) <=:IMPORTO_A ");
			parameters.put("IMPORTO_A", importoA);
		}
		
		
		
		Date dataA = dto.getEndDateFilter();

		if (dataA != null) {
			// TODO PAZZIK centralizzare
			// aggiungo un giorno a dataA per selezionare anche i DDP creati
			// nell'ultimo giorno dell'intervallo,
			// altrimenti dataA contiene la data finale impostata all'ora 0 del
			// mattino e quindi non verrebbero
			// recuperati i ddp emessi nell'ultimo giorno dell'intervallo di
			// filtraggio
			
			dataA.setTime(dataA.getTime() + 24*60*60*1000 - 1);
			
			whereConditions.append(" AND doc.TS_EMISSIONE <=:TS_EMISSIONE_A");
			parameters.put("TS_EMISSIONE_A", dataA);
		}

		String sortingField = dto.getSortingField() != null ? dto.getSortingField() : "ID";
		String sortingDir = dto.getSortingDir() != null ? dto.getSortingDir() : "ASC";

		if (sortingField.equalsIgnoreCase("TIPO"))
			sortingField = "cfgDoc.DESCRIZIONE";
		else
			sortingField = "doc." + sortingField;

		String orderingCondition = " ORDER BY " + sortingField + " " + sortingDir;

		String queryString = selectFromWhere + whereConditions + orderingCondition;

		return queryString;
	}

	private Query createCountDocumentsQuery(String idCondizione) {

		String selectFromWhere = "Select count(*) from DOCUMENTI_PAGAMENTO doc, CONDIZIONI_DOCUMENTO cond_doc, JLTCOPD cond_pag where ";

		StringBuffer whereConditions = new StringBuffer("doc.ID = cond_doc.ID_DOCUMENTO AND cond_doc.ID_CONDIZIONE = cond_pag.ID_CONDIZIONE ");

		whereConditions.append("AND cond_pag.ID_CONDIZIONE = ?");

		Query query = em.createNativeQuery(selectFromWhere + whereConditions);

		query.setParameter(1, idCondizione);

		return query;
	}

	private Query createGetDocumentsQuery(String idCondizione) {

		String selectFromWhere = "Select doc.* from DOCUMENTI_PAGAMENTO doc, CONDIZIONI_DOCUMENTO cond_doc, JLTCOPD cond_pag where ";

		StringBuffer whereConditions = new StringBuffer(
				"doc.ID = cond_doc.ID_DOCUMENTO AND cond_doc.ID_CONDIZIONE = cond_pag.ID_CONDIZIONE AND cond_doc.TS_ANNULLAMENTO IS NULL ");

		whereConditions.append("AND cond_pag.ID_CONDIZIONE = ?");

		Query query = em.createNativeQuery(selectFromWhere + whereConditions, DocumentoDiPagamento.class);

		query.setParameter(1, idCondizione);

		return query;
	}

	private Query createNullifyDocumentsQuery(String codFiscale, String[] idDocumenti) {

		String selectFromWhere = "update DOCUMENTI_PAGAMENTO set STATO = ?, TS_ANNULLAMENTO = ?, OP_ANNULLAMENTO = ? ";
		
		StringBuffer inCondition = new StringBuffer("where ID IN (");

		List<String> documentiList = Arrays.asList(idDocumenti);

		appendInCondition(inCondition, documentiList);

		Query query = em.createNativeQuery(selectFromWhere + inCondition);

		query.setParameter(1, EnumStatoDDP.ANNULLATO_UTENTE.getChiave());

		query.setParameter(2, new Timestamp(System.currentTimeMillis()));

		query.setParameter(3, codFiscale);

		addInParameters(query, documentiList, 3);

		return query;
	}

	private Query createNullifyDocumentConditionsQuery(String codFiscale, String[] idDocumenti) {

		String selectFromWhere = "update CONDIZIONI_DOCUMENTO set TS_ANNULLAMENTO = ?, OP_ANNULLAMENTO = ? ";
		StringBuffer inCondition = new StringBuffer("where ID_DOCUMENTO IN (");

		for (String id : idDocumenti)
			inCondition.append("?,");
		

		inCondition.deleteCharAt(inCondition.length() - 1).append(")");

		Query query = em.createNativeQuery(selectFromWhere + inCondition);

		query.setParameter(1, new Timestamp(System.currentTimeMillis()));

		query.setParameter(2, codFiscale);

		int i = 1;

		for (String id : idDocumenti) {

			query.setParameter(2 + i, id);

			i++;
		}

		return query;
	}

	@Override
	public Set<CondizioneDocumento> listCondizioniDiPagamentoByIdDocumento(String idDocumento) {
		Set<CondizioneDocumento> retList = null;
		try {

			DocumentoDiPagamento dp = em.find(DocumentoDiPagamento.class, idDocumento);
			retList = dp.getCondizioni();

		} catch (Exception e) {
			LOGGER.error("error on listCondizioniDiPagamentoByIdDocumento " + idDocumento, e);
			throw new DAORuntimeException(e);
		}
		return retList;

	}

	@Override
	public Long countDDPByIdAndStatus(String docId, EnumStatoDDP status) {
		Long num;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codiceAutorizzazione", docId);
		params.put("statoDocumento", status.getChiave());

		try {
			num = (Long) uniqueResultByQuery("countByIdAndStatus", params, em);
		} catch (Exception e) {
			LOGGER.error("error on countByIdAndStatus " + params, e);
			throw new DAORuntimeException(e);
		}

		return num;
	}

	@Override
	public DocumentoDiPagamento retrieveDDPById(String id) {
		DocumentoDiPagamento retrieved = null;
		try {
			retrieved = loadById(DocumentoDiPagamento.class, id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveDDPById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}

	@Override
	public DocumentoDiPagamento createDDP(DDPInputDTO inputDTO, CfgGatewayPagamento cfg) {
		
		try {

			DocumentoDiPagamento ddp = new DocumentoDiPagamento();

			Set<CondizioneDocumento> condizioni = new HashSet<CondizioneDocumento>();
			
			for (String idCondizione : inputDTO.getCondizioniCarrello()) {
				
				CondizioneDocumento condDoc = new CondizioneDocumento();
				
				condDoc.setOpInserimento(inputDTO.getOperatorUsername());
				condDoc.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				CondizionePagamento condPag = condizioniDAO.getSingleCondizioneById(idCondizione);
				condDoc.setCondizionePagamento(condPag);
				condDoc.setDocumento(ddp);
				condizioni.add(condDoc);
				
				Date scadenza_min = condPag.getDtScadenza();
				//La data scadenza da portare sul doc è la data scadenza, ad
				//esclusione del tributo Bollo_auto che deve riportare la data fine validità.
				if ("BOLLO_AUTO".equalsIgnoreCase(condPag.getCdTrbEnte())) {
					//bollo auto
					scadenza_min = condPag.getDtFinevalidita();
				} 
				if (scadenza_min.compareTo(SharedConstants.NO_EXPIRE) != 0) {
					if (ddp.getDtScadenzaDoc() == null
							|| UtilDate.setOrarioEndOfDay(scadenza_min).compareTo(UtilDate.setOrarioEndOfDay(ddp.getDtScadenzaDoc())) < 0) {
						ddp.setDtScadenzaDoc(scadenza_min);
					}
				}
			}

			ddp.setCondizioni(condizioni);
			ddp.setCodFiscalePagante(inputDTO.getCfPagante());
			ddp.setEmailVersante(inputDTO.getEmailPagante());

			ddp.setCfgGatewayPagamento(cfg);

			ddp.setImporto(CommissioniCalculator.getTotalePagamentiDoc(condizioni));
			ddp.setImportoCommissioni(CommissioniCalculator.calculateTotaleCommissioni(ddp));

			ddp.setIntestatario(inputDTO.getLoggedIntestatario());
			ddp.setId(DDP_IDGenerator.Generate_DDP_ID(ddp));
			ddp.setOpInserimento(inputDTO.getOperatorUsername());
			ddp.setStatoEnum(EnumStatoDDP.EMESSO);
			ddp.setTsEmissione(new Timestamp(System.currentTimeMillis()));
			ddp.setTsInserimento(new Timestamp(System.currentTimeMillis()));

			DocumentoDiPagamento created = create(ddp);

			return created;

		} catch (Exception e) {
			LOGGER.error("error on  createDDP, " + inputDTO, e);
			throw new DAORuntimeException(e);
		}

	}

	@Override
	public DocumentoDiPagamento updateDDP(DocumentoDiPagamento ddp) {
		try {
			ddp = this.update(ddp);
		} catch (Exception e) {
			LOGGER.error("error on updateDDP ", e);
			throw new DAORuntimeException(e);
		}
		return ddp;
	}

}
