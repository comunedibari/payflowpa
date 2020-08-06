package it.tasgroup.iris.persistence.dao;
 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.CfgTributoEntePlugin;
import it.nch.is.fo.tributi.JltentrId;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.utility.GeneratoreIdUnivoci;
import it.tasgroup.addon.api.domain.DettaglioStrutturato;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnInsertPhaseSupport;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnPersistenceHelper;
import it.tasgroup.addon.api.manager.helper.AddOnSearchHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.IUVPosizEnteMap;
import it.tasgroup.iris.domain.IUVPosizEnteMapPK;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.SearchTributiRequest;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousAnagraficaDTO;
import it.tasgroup.iris.dto.anonymous.payment.CondizioneDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.services.util.enumeration.EnumStatoRiga;
import it.tasgroup.services.util.idgenerator.IDGenerator;

@Stateless(name = "PendenzaDaoService")
public class PendenzaDaoImpl extends DaoImplJpaCmtJta<Pendenza> implements PendenzaDao {

	private static final Logger LOGGER = LogManager.getLogger(PendenzaDaoImpl.class);
	private static final int STATO_PENDENZE_ESTRATTO_CONTO_DEBITORIO_DA_PAGARE=0;
	private static final int STATO_PENDENZE_ESTRATTO_CONTO_DEBITORIO_PAGATA=1;
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
    
	@EJB(name="IUVGenerator")
	private IUVGeneratorLocal iuvGenerator;
	
	
	@Override
	public List<Tuple> getAnonymousPaymentConditionByIdDistinta(Long idDistinta) {
		List<Tuple> ret = null;

		try {

			CriteriaBuilder builder = em.getCriteriaBuilder();

			CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
			Root<Pendenza> root = criteria.from(Pendenza.class);

			Join<Pendenza, Enti> tributoente = root.join("tributoEnte", JoinType.INNER);
			Join<TributoEnte, Enti> enti = tributoente.join("entiobj", JoinType.INNER);
			Join<TributoEnte, CfgTributoEntePlugin> cfgTributoEntePlugin = tributoente.join("cfgTributoEntePlugin", JoinType.LEFT);
			Join<Pendenza, CondizionePagamento> condizioni = root.join("condizioni", JoinType.INNER);
			Join<CondizionePagamento, Pagamenti> pagamenti = condizioni.join("pagamenti", JoinType.INNER);

			Path<String> idPendenza = root.get("idPendenza");
			Path<String> annoRiferimento = root.get("annoRiferimento");
			Path<String> causale = root.get("deCausale");
			Path<String> totalependenza = root.get("imTotale");
			Path<String> note = root.get("note");

			Path<String> tributo = tributoente.get("deTrb");
			Path<String> flRicevutaAnonimo = tributoente.get("flRicevutaAnonimo");
			Path<String> ente = enti.get("denominazione");
			Path<String> idEnte = enti.get("idEnte");
			Path<String> cdPlugin = cfgTributoEntePlugin.get("cdPlugin");

			Path<String> idCondizione = condizioni.get("idCondizione");
			Path<String> importo = pagamenti.get("imPagato");
			Path<String> datascadenza = condizioni.get("dtScadenza");
			Path<String> iuv = condizioni.get("idPagamento");

			Path<String> idPagamento = pagamenti.get("id");
			Path<String> statoPagamento = pagamenti.get("stPagamento");
			Path<String> flagIncasso = pagamenti.get("flagIncasso");

			Path<String> idTributoEnte = pagamenti.get("cdTrbEnte");
			Path<String> dtPagamento = pagamenti.get("tsDecorrenza");

			Path<String> causaleCondizione = condizioni.get("causalePagamento");

			criteria.multiselect(idCondizione.alias("idCondizione"), idPendenza.alias("idPendenza"), ente.alias("ente"), tributo.alias("tributo"),flRicevutaAnonimo.alias("flRicevutaAnonimo"),
					annoRiferimento.alias("annoRiferimento"), causale.alias("causalePendenza"), totalependenza.alias("totalePendenza"),
					importo.alias("importo"), datascadenza.alias("dataScadenza"), note.alias("notePendenza"),idPagamento.alias("idPagamento"), statoPagamento.alias("statoPagamento"), flagIncasso.alias("flagIncasso"),
					idTributoEnte.alias("cdTrbEnte"),
					dtPagamento.alias("tsDecorrenza"), causaleCondizione.alias("causaleCondizione"), iuv.alias("iuv"), cdPlugin.alias("cdPlugin"), idEnte.alias("idEnte")
					);

			criteria.where(builder.equal(pagamenti.get("flussoDistinta"), idDistinta));

			TypedQuery<Tuple> query = em.createQuery(criteria);

			ret = query.getResultList();

		} catch (Exception e) {
			LOGGER.error("error on getAnonymousPaymentConditionByIdDistinta ", e);
			throw new DAORuntimeException(e);
		}
		return ret;
	} 

	protected final String nextValDB2(String sequenceName) {

		try {
			Query query = em.createNativeQuery("values(NEXTVAL FOR " + sequenceName + ")");
			return query.getSingleResult().toString();
		} catch (Exception e) {
			LOGGER.warn("Got error with a sequence.. ");
			throw new RuntimeException("Got error with sequence "+sequenceName);
			
		}

	}

	public <T extends TributoStrutturato> Set<CondizionePagamento> createNewPendenza(T tributoStrutturato, boolean isNDPCompliant) {
		return createNewPendenza(tributoStrutturato, isNDPCompliant, null);
	}
	
	public <T extends TributoStrutturato> Set<CondizionePagamento> createNewPendenza(T tributoStrutturato, boolean isNDPCompliant, AnonymousAnagraficaDTO anagraficaDTO) {

		String idPendenza = GeneratoreIdUnivoci.GetCurrent().generaId();

		Pendenza p = new Pendenza();
		p.setIdPendenza(idPendenza);
		p.setTributoStrutturato(tributoStrutturato);

		DettaglioStrutturato dettaglioStrutturato = tributoStrutturato.getDettaglioStrutturato();
		dettaglioStrutturato.setTsInserimento(currentTime());

//		// TODO: Codice da rimuovere una volta che si � migrata Regione Toscana a MySQL.
//		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");
//		String dialect = cpl.getProperty("hibernate.dialect");
//		if (dialect!=null) {
//			if ("org.hibernate.dialect.DB2Dialect".equals(dialect.trim())) {
//			 String idTributoStrutturato = nextValDB2("TRIBUTO_DETTAGLIO_SEQ");
//			 tributoStrutturato.setId(Long.parseLong(idTributoStrutturato));
//			}
//		}	
		
		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		dettaglioStrutturato.setOpInserimento("Idp");

		/* qui si setta l'id programmaticamente perch� per vincoli di deploy non si pu�
		 * far dipendere TributoStrutturato dalla classe Pendenza
		 */
		tributoStrutturato.setIdPendenza(idPendenza);
		
//		AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(tributoStrutturato.getTipoTributo());
		p.setTsDecorrenza(currentTime());

		JltentrId id = new JltentrId(dettaglioStrutturato.getIdEnte(), tributoStrutturato.getCdTrbEnte());
		TributoEnte tributoEnte = em.find(TributoEnte.class, id);
		p.setTributoEnte(tributoEnte);
		tributoStrutturato.setDenominazioneEnte(tributoEnte.getEntiobj().getDenominazione());
		tributoStrutturato.setDescTrbEnte(tributoEnte.getDeTrb());

		String idTributo = tributoEnte.getIdTributo();

		CategoriaTributo tributo = em.find(CategoriaTributo.class, idTributo);
		p.setCategoriaTributo(tributo);

//		String versante = (tributoStrutturato.getCfSoggettoVersante() != null && !"".equals(tributoStrutturato.getCfSoggettoVersante())) ? tributoStrutturato
//				.getCfSoggettoVersante() : dettaglioStrutturato.getCfSoggettoPassivo();
				
		String versante = dettaglioStrutturato.getCfSoggettoPassivo();
		if (StringUtils.isEmpty(versante))
			versante = tributoStrutturato.getCfSoggettoVersante();
		versante=versante.toUpperCase();

		String idGenerato = IDGenerator.generateID(15);
		p.setIdPendenzaente("000"+idGenerato);

		p.setIdSystem(tributoEnte.getSIL());
		p.setTsCreazioneente(p.getTsDecorrenza());
		p.setTsEmissioneente(p.getTsCreazioneente());

		Calendar dataPrescrizione = GregorianCalendar.getInstance();
		dataPrescrizione.add(GregorianCalendar.YEAR, 10);

		p.setTsPrescrizione(new Timestamp(dataPrescrizione.getTimeInMillis()));

		p.setAnnoRiferimento(dettaglioStrutturato.getAnnoRiferimento());

		p.setNote(tributoStrutturato.getNoteVersante());

		tributoStrutturato.setCfSoggettoVersante(versante);

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		tributoStrutturato.setOpInserimento("Idp");
		tributoStrutturato.setTsInserimento(currentTime());
		p.setDvRiferimento("EUR");
		p.setImTotale(dettaglioStrutturato.getImporto());
		p.setIdMittente("IRIS");
		p.setDeMittente(null);

		
		AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(tributoEnte.getIdEnte(), tributoEnte.getCdTrbEnte(), tributoStrutturato.getTipoTributo());

		
		AddOnPersistenceHelper<T> persistenceHelper = manager.getPersistenceHelper();
		AddOnInsertPhaseSupport<T> insertPhaseSupport = persistenceHelper.getAddonInsertPhaseSupport();
		String causale = insertPhaseSupport.computeCausale(tributoStrutturato);
		if (causale == null || causale.isEmpty()) {
			causale = " ";
		}
		p.setDeCausale(causale);

		p.setStPendenza("A");
		p.setFlModPagamento("S");

		p.setPrVersione(0);
		//fix #2018, per consentire la chiusura della pendenza
		p.setCartellaPagamento(new Integer(0));

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		p.setOpInserimento("IdP");
		p.setTsInserimento(currentTime());

		Set<DestinatariPendenza> destinatari = new HashSet<DestinatariPendenza>();
		DestinatariPendenza dp = new DestinatariPendenza();
		dp.setIdDestinatario(GeneratoreIdUnivoci.GetCurrent().generaId());
		dp.setTsDecorrenza(currentTime());

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		dp.setOpInserimento("IdP");
		dp.setTsInserimento(currentTime());
		
		dp.setCoDestinatario(versante);
		dp.setDeDestinatario(versante);
		if (dp.getCoDestinatario().length() == 11)
			dp.setTiDestinatario("AL"); //partite iva
		else
			dp.setTiDestinatario("CI"); // cittadini
		
		if(anagraficaDTO != null) {
			
			if (dp.getCoDestinatario().length() == 11)
				dp.setTipoSoggettoDestinatario("G"); //partite iva
			else
				dp.setTipoSoggettoDestinatario("F"); // cittadini
			String denominazione = anagraficaDTO.getDenominazione();
			
			if (denominazione != null)
				dp.setDeDestinatario(denominazione);
			dp.setAnagraficaDestinatario(anagraficaDTO.getDenominazione());
			
			dp.setIndirizzoDestinatario(anagraficaDTO.getIndirizzo());
			dp.setNumCivicoDestinatario(anagraficaDTO.getNumCivico());
			dp.setCapDestinatario(anagraficaDTO.getCapDestinatario());
			dp.setLocalitaDestinatario(anagraficaDTO.getLocalita());
			dp.setProvinciaDestinatario(anagraficaDTO.getProvincia());
			dp.setNazioneDestinatario(anagraficaDTO.getNazione());
			dp.setDataNascitaDestinatario(anagraficaDTO.getDataNascita());
			dp.setLuogoNascitaDestinatario(anagraficaDTO.getLuogoNascita());
			dp.setEmailDestinatario(anagraficaDTO.getEmail());
			
		} 

		dp.setPendenza(p);
		destinatari.add(dp);
		p.setDestinatari(destinatari);
		p.setPrVersione(0);

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		p.setOpInserimento("IdP");
		p.setTsInserimento(currentTime());

		Set<CondizionePagamento> condizioni = new HashSet<CondizionePagamento>();
		CondizionePagamento condizionePagamento = new CondizionePagamento();
		condizionePagamento.setIdCondizione(GeneratoreIdUnivoci.GetCurrent().generaId());
		condizionePagamento.setTsDecorrenza(currentTime());
		condizionePagamento.setTiPagamento("S");
		Enti e = em.find(Enti.class, dettaglioStrutturato.getIdEnte());
		condizionePagamento.setEnte(e);
		condizionePagamento.setCdTrbEnte(tributoStrutturato.getCdTrbEnte());
		
		//String idPagamento = isNDPCompliant? IDGenerator.Generate_PAYMENT_ID():GeneratoreIdUnivoci.GetCurrent().generaId();
		String iuvOrIdGenerato =getIdPagamentoOrIUV(idGenerato,tributoEnte);
		condizionePagamento.setIdPagamento(iuvOrIdGenerato);
		
		condizionePagamento.setDtScadenza(tributoStrutturato.getDataScadenza());

		condizionePagamento.setDtIniziovalidita(currentTime());

		if (tributoStrutturato.getDataFineValidita()==null)
		    condizionePagamento.setDtFinevalidita(tributoStrutturato.getDataScadenza());
		else
			condizionePagamento.setDtFinevalidita(tributoStrutturato.getDataFineValidita());

		condizionePagamento.setImTotale(dettaglioStrutturato.getImporto());
		condizionePagamento.setStPagamento("N");
		condizionePagamento.setPrVersione(0);

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		condizionePagamento.setOpInserimento("Idp");

		condizionePagamento.setTsInserimento(currentTime());
		condizionePagamento.setPendenza(p);

		BillItemInspector.makeHidden(condizionePagamento);

		condizioni.add(condizionePagamento);
		p.setCondizioni(condizioni);
		
		em.persist(tributoStrutturato);
		dettaglioStrutturato.setId(tributoStrutturato.getId());
		em.persist(dettaglioStrutturato);
		em.persist(p);
		
		return p.getCondizioni();

	}

	protected final static Timestamp currentTime() {
		return new Timestamp(System.currentTimeMillis());
	}


	@Override
	public <T extends TributoStrutturato> List<T> findTributi(SearchTributiRequest<T> searchWrapper) {

		
		AddOnManager<T> addOnManager = AddOnManagerFactory.getAddOnManager(searchWrapper.getIdEnte(), searchWrapper.getCdTrbEnte() , searchWrapper.getTributoStrutturato().getTipoTributo());
		
		AddOnSearchHelper<T> searchHelper = addOnManager.getSearchHelper();

		// Recupero la lista dei tributi strutturati che soddisfano i criteri di ricerca.
		List<T> rawList = searchHelper.find(searchWrapper, em);

		// Filtro i risultati prendendo l'elemento che � associato alla pendenza pi� recente con st_riga='V'
		// (il plugin me li deve tornare in ordine dal pi� recente al pi� vecchio)
		List<T> listOfOneElem = new ArrayList<T>();

		for (T t: rawList) {

			Pendenza p = em.find(Pendenza.class, t.getIdPendenza());

			if ("V".equals(p.getStRiga())&& !searchWrapper.isSearchHidden() ||
				"H".equals(p.getStRiga())&& searchWrapper.isSearchHidden()) {

	    		t.getDettaglioStrutturato().getImporto(); // force prerendering materialization

	    		listOfOneElem.add(t);

				break;
			}

		}

		return listOfOneElem;
	}

	@Override
	public TributoStrutturato getTributoStrutturatoByIdPendenza(String id) {

		TributoStrutturato tributo = null;

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("idPendenza", id);

		try {

			String querysql = "select trib.* from TRIBUTO_STRUTTURATO trib " +
							"where ID_PENDENZA=:idPendenza ";

			Query query = null;
			query= em.createNativeQuery(querysql, TributoStrutturato.class);
			query.setParameter("idPendenza", id);
			tributo = (TributoStrutturato)query.getSingleResult();

		} catch (NoResultException ne){
			return tributo;
		} catch (Exception e) {
			LOGGER.error("error on getTributoStrutturatoById "+params, e);
			throw new DAORuntimeException(e);
		}

	     return tributo;
	}

	@Override
	public List<Pendenza> listaPendenzeDaPagareEstrattoContoDebitorio(String codiceFiscale, Date dateFrom, Date dateTo, String idEnte, Integer annoRiferimento, String codCategoriaDebito, String codDebitoCreditore, boolean extractInProgressPayments) {
		return listaPendenzeEstrattoContoDebitorio(codiceFiscale, dateFrom, dateTo, STATO_PENDENZE_ESTRATTO_CONTO_DEBITORIO_DA_PAGARE, idEnte, annoRiferimento, codCategoriaDebito, codDebitoCreditore,  extractInProgressPayments);
	}

	@Override
	public List<Pendenza> listaPendenzePagateEstrattoContoDebitorio(String codiceFiscale, Date dateFrom, Date dateTo, String idEnte, Integer annoRiferimento, String codCategoriaDebito, String codDebitoCreditore, boolean extractInProgressPayments) {
		return listaPendenzeEstrattoContoDebitorio(codiceFiscale, dateFrom, dateTo, STATO_PENDENZE_ESTRATTO_CONTO_DEBITORIO_PAGATA, idEnte, annoRiferimento, codCategoriaDebito, codDebitoCreditore, extractInProgressPayments);
	}


	private List<Pendenza> listaPendenzeEstrattoContoDebitorio(String codiceFiscaleDebitore, Date dateFrom, Date dateTo, int stato, String idEnte, Integer annoRiferimento, String codCategoriaDebito, String codDebitoCreditore, boolean extractInProgressPayments) {

		/* Query che estrae tutte le pendenze che hanno un residuo pagabile oppure sono totalmente pagate */
		/* Si confronta il totale pendenza con il totale dei pagamenti eseguiti o eseguiti salvo buon fine collegati "
		 * Nel caso "DA PAGARE": Si selezionano tutte le pendenze che hanno la somma dei pagamenti eseguiti minore dell'importo totale
		 * Nel caso "PAGATO": Si selezionano tutte le pendenze che hanno la somma dei pagamenti eseguiti maggiore o uguale dell'importo totale
		 */
		PosizioneDebitoriaInputVO filterParameters = new PosizioneDebitoriaInputVO();

		filterParameters.setDestinatario(codiceFiscaleDebitore);

		if (dateFrom != null)
			filterParameters.setDataScadenza_da(dateFrom);

		if(dateTo != null)
			filterParameters.setDataScadenza_a(dateTo);

		if (idEnte != null)
			filterParameters.setFiltroEnte(idEnte);

//		if (annoRiferimento != null)
//			filterParameters.set

		if (codCategoriaDebito != null)
			filterParameters.setTipoTributo(codCategoriaDebito);

		if (codDebitoCreditore != null)
			filterParameters.setTributo(codDebitoCreditore);

		if (stato==STATO_PENDENZE_ESTRATTO_CONTO_DEBITORIO_DA_PAGARE)
			filterParameters.setFiltroStato(PosizioneDebitoriaConstants.STATO_DAPAGARE);
		else
			filterParameters.setFiltroStato(PosizioneDebitoriaConstants.STATO_PAGATO_O_CHIUSO);


		PosizioneDebitoriaOptions options = new PosizioneDebitoriaOptions();
		options.pagamentiInCorsoComePagati=!extractInProgressPayments;

		List<Pendenza> returnValue= listPendenzeByFilterParameters(filterParameters, null, null, options); //TODO: mettere nelle costanti

		return returnValue;

	}


	@Override
	public void updateStRiga(CondizionePagamento cond){

		try {
			if (BillItemInspector.isHidden(cond)) {
				// updatedPend = update(BillItemInspector.makeVisible(cond));

				String sqlCondizioni = "UPDATE JLTCOPD set ST_RIGA='V', TS_ANNULLAMENTO=NULL, TS_ANNULLAMENTO_MILLIS=0 WHERE ID_CONDIZIONE =:idCond";
				Query q0 = em.createNativeQuery(sqlCondizioni);
				q0.setParameter("idCond", cond.getIdCondizione());
				q0.executeUpdate();


				String sqlPendenza = "UPDATE JLTPEND set ST_RIGA='V', TS_ANNULLAMENTO=NULL, TS_ANNULLAMENTO_MILLIS=0 WHERE ID_PENDENZA =:idPend";
				Query q1 = em.createNativeQuery(sqlPendenza);
				q1.setParameter("idPend", cond.getPendenza().getIdPendenza());
				q1.executeUpdate();

				String sqlDestinatari = "UPDATE JLTDEPD set ST_RIGA='V' WHERE ID_PENDENZA =:idPend";
				Query q2 = em.createNativeQuery(sqlDestinatari);
				q2.setParameter("idPend", cond.getPendenza().getIdPendenza());
				q2.executeUpdate();

			}	
			return;

		} catch (Exception e) {

			LOGGER.error("error on updateStRiga "+ cond.getIdCondizione(), e);

			throw new DAORuntimeException(e);
		}

	}
	
	@Override
	public String replacePosizione(String idCondizione, BigDecimal importo, String noteVersante, String codiceFiscaleOperatore) {
		
		String resultId=idCondizione;
		Timestamp currentTS = new Timestamp(System.currentTimeMillis());
		
		String newIdPendenza = GeneratoreIdUnivoci.GetCurrent().generaId();
		
		try {
				String operatoreAggiornamento=codiceFiscaleOperatore;
				CondizionePagamento condizione = em.find(CondizionePagamento.class, idCondizione);
				 
			    if (operatoreAggiornamento == null) {
			       //recupero come operatore il debitore della posizione
				   DestinatariPendenza dest = condizione.getPendenza().getDestinatari().iterator().next();
				   operatoreAggiornamento = dest.getCoDestinatario();
			    }			    
			    
			    String idPendenza= condizione.getPendenza().getIdPendenza();			    
			    //recupero la pendenza da aggiornare
			    Pendenza oldPendenza=em.find(Pendenza.class,idPendenza);
			    
			    //annullo la vecchia posizione
			    oldPendenza.setStRiga(EnumStatoRiga.INVALIDATED.getChiave());
			    oldPendenza.setOpAnnullamento(operatoreAggiornamento);
			    oldPendenza.setTsAnnullamento(currentTS);

				Set<CondizionePagamento> condizioni = oldPendenza.getCondizioni();		
				for(CondizionePagamento cond : condizioni) {
					cond.setStRiga(EnumStatoRiga.INVALIDATED.getChiave());
					cond.setOpAnnullamento(operatoreAggiornamento);
					cond.setTsAnnullamento(currentTS);

					for(VocePagamento vp : cond.getVociPagamento()) {
						vp.setStRiga(EnumStatoRiga.INVALIDATED.getChiave());
						vp.setOpAggiornamento(operatoreAggiornamento);
						vp.setTsAggiornamento(currentTS);
					}

					for(AllegatiPendenza ap : cond.getAllegatiPendenza()) {
						ap.setStRiga(EnumStatoRiga.INVALIDATED.getChiave());
						ap.setOpAggiornamento(operatoreAggiornamento);
						ap.setTsAggiornamento(currentTS);
					}
				}
			
				Set<DestinatariPendenza> destinatari = oldPendenza.getDestinatari();
				for(DestinatariPendenza dest : destinatari) {
					dest.setStRiga(EnumStatoRiga.INVALIDATED.getChiave());
					dest.setOpAggiornamento(operatoreAggiornamento);
					dest.setTsAggiornamento(currentTS);	
				}
				
				em.flush();

				Pendenza newPendenza=condizione.getPendenza();
				em.detach(newPendenza);
				  
				//creo la nuova posizine sistemando condizioni e compagnia bella
				for(CondizionePagamento cond : newPendenza.getCondizioni())  {
					String newIdCondizione = GeneratoreIdUnivoci.GetCurrent().generaId();
					
					em.detach(cond);
					cond.setStRiga(EnumStatoRiga.VALID.getChiave());
					cond.setOpAggiornamento(null);
					cond.setTsAggiornamento(null);
					cond.setOpAnnullamento(null);
					cond.setTsAnnullamento(null);
					cond.setOpInserimento(operatoreAggiornamento);
					cond.setTsInserimento(currentTS);
					if (cond.getIdCondizione().equals(idCondizione)) {
						cond.setImTotale(importo);
						resultId = newIdCondizione;
					}
					//voci
					List<VocePagamento> lvp = new ArrayList<VocePagamento>();
					for(VocePagamento vp : cond.getVociPagamento()) {
						em.detach(vp);
						vp.setIdCondizione(newIdCondizione);
						vp.setIdPendenza(newIdPendenza);
						vp.setIdVoce(GeneratoreIdUnivoci.GetCurrent().generaId());
						vp.setStRiga(EnumStatoRiga.VALID.getChiave());
						vp.setOpInserimento(operatoreAggiornamento);
						vp.setTsInserimento(currentTS);
						vp.setOpAggiornamento(null);
						vp.setTsAggiornamento(null);
						lvp.add(vp);
						
					}
					cond.setVociPagamento(lvp);
					
					//allegati
					Set<AllegatiPendenza> lap = new HashSet<AllegatiPendenza>();
					for(AllegatiPendenza ap : cond.getAllegatiPendenza()) {
						em.detach(ap);
						ap.setIdCondizione(newIdCondizione);
						ap.setCondizioniPagamento(cond);
						ap.setIdPendenza(newIdPendenza);
						ap.setIdAllegato(GeneratoreIdUnivoci.GetCurrent().generaId());
						ap.setStRiga(EnumStatoRiga.VALID.getChiave());
						ap.setOpInserimento(operatoreAggiornamento);
						ap.setTsInserimento(currentTS);
						ap.setOpAggiornamento(null);
						ap.setTsAggiornamento(null);
						lap.add(ap);
					}
					cond.setAllegatiPendenza(lap);
					
					//pagamenti
					cond.setPagamenti(null);
					//condizioni documento
					cond.setCondizioniDocumento(null);

					cond.setIdCondizione(newIdCondizione);
				}
				//destinatari
				for(DestinatariPendenza dest : newPendenza.getDestinatari()) {
					em.detach(dest);
					dest.setIdDestinatario(GeneratoreIdUnivoci.GetCurrent().generaId());
					dest.setStRiga(EnumStatoRiga.VALID.getChiave());
					dest.setOpAggiornamento(null);
					dest.setTsAggiornamento(null);
					dest.setOpInserimento(operatoreAggiornamento);
					dest.setTsInserimento(currentTS);	
				}
				
				//ora aggiorno i dati della pendenza
				newPendenza.setIdPendenza(newIdPendenza);
				newPendenza.setNote(noteVersante);
				newPendenza.setStRiga(EnumStatoRiga.VALID.getChiave());
				newPendenza.setOpAnnullamento(null);
				newPendenza.setTsAnnullamento(null);
				newPendenza.setOpAggiornamento(null);
				newPendenza.setTsAggiornamento(null);
				newPendenza.setOpInserimento(operatoreAggiornamento);
				newPendenza.setTsInserimento(currentTS);
				
				em.persist(newPendenza);
				em.flush();
				
				return resultId;

		} catch (Exception e) {
			LOGGER.error("error on replacePosizione " + idCondizione, e);
			throw new DAORuntimeException(e);
		}

	}
	
	
	@Override
	public Pendenza getPendenzaById(String id){

		try {

			return getById(Pendenza.class, id);

		} catch (Exception e) {

			LOGGER.error("error on getPendenzaById "+ id, e);

			throw new DAORuntimeException(e);
		}

	}

	@Override
	public Pendenza getPendenzaByChiaveSemantica(String cdTrbEnte, String idEnte, String idPendenzaEnte){

		try {

			String queryDiRecuperoIdPendenzaAttiva = "select * " +
                                                                 "from JLTPEND p " +
                                                                 "where p.CD_TRB_ENTE = :cdTrbEnte " +
                                                                 "and p.ID_ENTE = :idEnte " +
                                                                 "and p.ID_PENDENZAENTE = :idPendenzaEnte " +
                                                                 "and p.ST_RIGA = 'V' and p.TS_ANNULLAMENTO is null";

			Query q = em.createNativeQuery(queryDiRecuperoIdPendenzaAttiva,Pendenza.class);
			q.setParameter("cdTrbEnte", cdTrbEnte);
			q.setParameter("idEnte", idEnte);
			q.setParameter("idPendenzaEnte", idPendenzaEnte);

			return (Pendenza) q.getSingleResult();

		} catch (Exception e) {

			LOGGER.error("getIdPendenzaAttiva ", e);

			throw new DAORuntimeException(e);

		}

	}

	@Override
	public List<Pendenza> getMultaByBusinessKeys(String targa, Date data_verbale,
			String numero_verbale, String serie_verbale) {

		//NOTA BENE: questa implementazione non segue la regola ortodossa del plugin.
		//Tuttavia esiste del codice nei WS pregresso all'introduzione dei plugin
		//che accedeva la Multa in modo diretto da Iris (come fosse un tipo nativo).
		//per questo motivo andiamo a fare una native query, che non ci vincola a compile time
		//ad avere una dipendenza tra l'ear ed i plugin.
		List<Pendenza> result = null;

		System.out.println(targa);
		System.out.println(data_verbale);
		System.out.println(numero_verbale);
		System.out.println(serie_verbale);

		try {

			String queryDiRecuperoPendenzaMulta = "select pend.* from JLTPEND pend, T_MULTA multa where "+
					  "pend.id_tributo_strutturato = multa.id "+
					  "and multa.targa=:targa "+
					  "and multa.num_verbale=:num_verbale "+
					  "and multa.data_verbale>=:data_verbale_from "+
					  "and multa.data_verbale<=:data_verbale_to "+
					  "and pend.st_riga='V' "+
					  "and pend.st_pendenza='A' ";

			if (serie_verbale!=null) {
				queryDiRecuperoPendenzaMulta=queryDiRecuperoPendenzaMulta+
				"and multa.serie_verbale=:serie_verbale ";
			}

	    	Calendar myCalFrom = new GregorianCalendar();
	    	myCalFrom.setTime(data_verbale);
	    	myCalFrom.set(Calendar.HOUR_OF_DAY, 0);
	    	myCalFrom.set(Calendar.MINUTE, 0);
	    	myCalFrom.set(Calendar.SECOND, 0);
	    	myCalFrom.set(Calendar.MILLISECOND, 0);

	    	Calendar myCalTo=new GregorianCalendar();
	    	myCalTo.setTime(myCalFrom.getTime());
	    	myCalTo.add(Calendar.DAY_OF_YEAR, 1);

			Query q = em.createNativeQuery(queryDiRecuperoPendenzaMulta,Pendenza.class);
			q.setParameter("num_verbale", numero_verbale);
			q.setParameter("data_verbale_from", myCalFrom.getTime());
			q.setParameter("data_verbale_to", myCalTo.getTime());
			q.setParameter("targa", targa);
			if (serie_verbale!=null) {
				q.setParameter("serie_verbale", serie_verbale);
			}

			return (List<Pendenza>) q.getResultList();

		} catch (Exception e) {

			LOGGER.error("getMultaByBusinessKeys ", e);

			throw new DAORuntimeException(e);

		}

	}


	private boolean isFiltroLike(String filtro) {
		if (filtro.contains("*") || filtro.contains("%")) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public List<Pendenza> listPendenzeByFilterParameters(PosizioneDebitoriaInputVO fp, PagingData pagingData, PagingCriteria pagingCriteria, PosizioneDebitoriaOptions options) {

		List<Pendenza> result = new ArrayList<Pendenza>();
		
		
		// ------------------------------------------------------------
		// Filtraggi base
		// ------------------------------------------------------------

		boolean filtroDestinatarioLike = StringUtils.isNotBlank(fp.getDestinatario())&&isFiltroLike(fp.getDestinatario());
		boolean filtroDestinatario = StringUtils.isNotBlank(fp.getDestinatario())&&!isFiltroLike(fp.getDestinatario());		
		boolean filtroEnte=StringUtils.isNotBlank(fp.getFiltroEnte());
		boolean filtroTributoEnte=StringUtils.isNotBlank(fp.getTributo());
		boolean filtroListaTributiEnti=!fp.getTipoTributoList().isEmpty();
		boolean filtroCategoriaTributo=StringUtils.isNotBlank(fp.getTipoTributo());
		boolean filtroIdPendenzaEnte=StringUtils.isNotBlank(fp.getCodicePendenza());

		// ------------------------------------------------------------
		// Filtraggi estesi
		// ------------------------------------------------------------
		
		boolean filtroDataEmissioneDa=fp.getDataEmissione_da()!=null;
		boolean filtroDataEmissioneA=fp.getDataEmissione_a()!=null;
		boolean filtroCausaleLike=StringUtils.isNotBlank(fp.getCausale());
		boolean filtroImportoDa=fp.getImportototale_da()!=null;
		boolean filtroImportoA=fp.getImportototale_a()!=null;
		boolean filtroAnnoRif=fp.getAnnoRiferimento()!=null;

		boolean filtroRiscossore=StringUtils.isNotBlank(fp.getRiscossore()) && !isFiltroLike(fp.getRiscossore());
		boolean filtroRiscossoreLike=StringUtils.isNotBlank(fp.getRiscossore()) && isFiltroLike(fp.getRiscossore());
				
		boolean filtroRiferimento=StringUtils.isNotBlank(fp.getRiferimento()) && !isFiltroLike(fp.getRiferimento());
		boolean filtroRiferimentoLike=StringUtils.isNotBlank(fp.getRiferimento()) && isFiltroLike(fp.getRiferimento());
		
		
		boolean filtroPagamentoSingolo = false;
		if ("S".equals(fp.getModoPagamento())) {
			filtroPagamentoSingolo=true;
		}

		boolean filtroPagamentoRateale = false;
		if ("R".equals(fp.getModoPagamento())) {
			filtroPagamentoRateale=true;
		}
		
		boolean filtroDataScadenzaDa = fp.getDataScadenza_da() != null;
		boolean filtroDataScadenzaA  = fp.getDataScadenza_a() != null;
		
		
		// ------------------------------------------------------------
		// Filtraggio IUV e id_pagamento
		// ------------------------------------------------------------
		
		boolean filtroIdPagamento=false;
		String idPagamento = null;
		if (fp.getIdPagamenti()!=null && fp.getIdPagamenti().size()>0) {
			if (fp.getIdPagamenti().size()>1) {
				throw new UnsupportedOperationException("Ricerca per idPagamento multipli non supportata");
			}

			idPagamento=(String)fp.getIdPagamenti().get(0);
			if (idPagamento!=null) {
				filtroIdPagamento=true;
			}	
		}
		
//  	 ATTENZIONE: nelle precedenti versioni c'era la gestione dello IUV in caso di generazione da piattaforma.
//		 Se la piattaforma poteva generare degli IUV allora il filtro per id_pagamento si avvaleva di una ricerca accessoria sulla tabella IUVMAP
//
//		  ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
//        boolean iuvGeneratoDaPiattaforma = props.getBooleanProperty("iris.iuv.generated.by.paytas");       
//       
		
		
// 		Semplificazione (iuv = id_pagamento) ovvero la piattaforma non genera mai gli IUV se non per gli spontanei non attesi.
		String iuv = fp.getIuv();
		if (StringUtils.isNotBlank(iuv)) {
			idPagamento=iuv;
			filtroIdPagamento=true;
		}
		
		
		// ------------------------------------------------------------
		// Filtraggio per stato
		// ------------------------------------------------------------

		
		boolean filtroChiusa = PosizioneDebitoriaConstants.STATO_CHIUSO.equalsIgnoreCase(fp.getFiltroStato());
		boolean filtroApertaDaPagare = PosizioneDebitoriaConstants.STATO_DAPAGARE.equalsIgnoreCase(fp.getFiltroStato());
		boolean filtroApertaPagata = PosizioneDebitoriaConstants.STATO_PAGATO.equalsIgnoreCase(fp.getFiltroStato());
		boolean filtroPagataOrChiusa = PosizioneDebitoriaConstants.STATO_PAGATO_O_CHIUSO.equalsIgnoreCase(fp.getFiltroStato());

		
		// ------------------------------------------------------------
		// Filtraggi speciali
		// ------------------------------------------------------------

		boolean hidePosizioniNascoste = options!=null && options.escludiTributiNascosti; 

		boolean pagamentiInCorsoComePagati = options!=null && options.pagamentiInCorsoComePagati;

		
		// -------------------------
		// Subquery pendenza pagata
		// ------------------------
		
		// Questa inner query calcola il totale pagato per una data pendenza e torna un record solo se la somma dei totali pagati � maggiore o uguale dell'
		// importo totale della pendenza.
		// Per effettuare il calcolo si sommano gli importi delle posizioni che hanno o stato 'P' (pagamento comunicato dall'ente) o un pagamento eseguito associato.
		// Nota. Per pagamenti eseguiti in genere si intendono solo quelli in stato 'ES'. Tuttavia � possibile opzionalmente (da estratto conto debitorio CBILL ad esempio)
		// Considerarli 'pagati' ai fini dell'estrazione delle posizioni da pagare.
		
		String inClauseStati = pagamentiInCorsoComePagati?"'ES','EF','IC'":"'ES'";
		String subqueryPendenzaPagata =  " select co.id_pendenza,sum(co.im_totale)  from JLTCOPD co " +
		        						 " left outer join PAGAMENTI paga on co.id_condizione=paga.id_condizione and paga.st_pagamento in ("+inClauseStati+") "+
		        						 " where " +
		        						 "    co.id_pendenza=pend.id_pendenza " +
		        						 "    and (co.st_pagamento='P' or paga.st_pagamento in ("+inClauseStati+")) "+
		        						 "group by co.id_pendenza "+						
		        						 "having sum(co.im_totale)>=pend.im_totale ";

		// -----------------
		// Build Query
		// -----------------

		String theQuery1 = " select distinct pend.* from JLTPEND pend                                               "+
						   " join JLTCOPD copd on pend.id_pendenza = copd.id_pendenza                               " +
						   " join JLTDEPD depd on pend.id_pendenza = depd.id_pendenza                               " +
						   " join JLTENTR entr on pend.id_ente=entr.id_ente and pend.cd_trb_ente=entr.cd_trb_ente   " +
						   " where                                                                                  " +
						   "      pend.st_riga='V'                                                                  " +
	   (filtroDestinatario?"      and depd.co_destinatario = :coDestinatario and depd.ti_destinatario<>'DE'         ":"")+				   
	   (filtroDestinatarioLike?"  and depd.co_destinatario LIKE :coDestinatario and depd.ti_destinatario<>'DE'      ":"")+
	   (filtroEnte?"              and pend.id_ente=:idEnte                                                          ":"")+  
	   (filtroTributoEnte?"       and pend.cd_trb_ente = :cdTrbEnte                                                 ":"")+
	   (filtroListaTributiEnti?"  and pend.cd_trb_ente in (:listaCdTributo)                                         ":"")+  
	   (filtroCategoriaTributo?"  and pend.id_tributo = :idTributo                                                  ":"")+
	   (filtroIdPendenzaEnte?"    and pend.id_pendenzaente = :idPendenzaEnte                                        ":"")+
	   (filtroDataEmissioneDa?"   and pend.ts_EmissioneEnte >= :dataEmissioneDa                                     ":"")+
	   (filtroDataEmissioneA?"    and pend.ts_EmissioneEnte <= :dataEmissioneA                                      ":"")+
	   (filtroCausaleLike?"       and pend.de_causale LIKE :deCausale                                               ":"")+
	   (filtroImportoDa?"         and pend.im_totale>=:importoTotaleDa                                              ":"")+
	   (filtroImportoA?"          and pend.im_totale<=:importoTotaleA                                               ":"")+
	   (filtroAnnoRif?"           and pend.anno_riferimento=:annoRiferimento                                        ":"")+
	   (filtroPagamentoSingolo?"  and (pend.fl_mod_pagamento='S' or pend.fl_mod_pagamento='E')                      ":"")+
	   (filtroPagamentoRateale?"  and (pend.fl_mod_pagamento='R' or pend.fl_mod_pagamento='E')                      ":"")+
	   (filtroRiscossoreLike?"	  and pend.co_riscossore LIKE :riscossore                                           ":"")+
	   (filtroRiscossore?"        and pend.co_riscossore=:riscossore                                                ":"")+
	   (filtroRiferimentoLike?"   and pend.riferimento LIKE :riferimento                                            ":"")+
	   (filtroRiferimento?"       and pend.riferimento = :riferimento                                               ":"")+
	   (filtroDataScadenzaDa?"    and copd.dt_scadenza>=:dataScadenzaDa                                             ":"")+
	   (filtroDataScadenzaA?"     and copd.dt_scadenza<=:dataScadenzaA                                              ":"")+
	   (filtroIdPagamento?"       and copd.id_pagamento=:idPagamento                                                ":"")+
	   (filtroChiusa?"            and pend.st_pendenza='C'                                                          ":"")+
	   (filtroApertaDaPagare?"    and pend.st_pendenza='A' and NOT exists ("+subqueryPendenzaPagata+")              ":"")+
	   (filtroApertaPagata?"      and pend.st_pendenza='A' and exists ("+subqueryPendenzaPagata+")                  ":"")+
	   (filtroPagataOrChiusa?"    and (pend.st_pendenza='C' or exists ("+subqueryPendenzaPagata+"))                 ":"")+
   	   (hidePosizioniNascoste?"   and (entr.fl_nascosto_fe is null or entr.fl_nascosto_fe <> 'Y')                   ":"")+
	                         " order by pend.ts_inserimento desc";


        LOGGER.info("listPendenzeByFilterParameters query = "+ theQuery1);
		// -----------------
		// Query parameters
		// -----------------

		Map<String, Object> parameters = new HashMap<String, Object>();
		
		
	 	   if (filtroDestinatario)     parameters.put("coDestinatario", fp.getDestinatario().toUpperCase().trim());				   
	 	   if (filtroDestinatarioLike) parameters.put("coDestinatario", fp.getDestinatario().replace('*', '%').toUpperCase().trim());
	 	   if (filtroEnte)             parameters.put("idEnte",fp.getFiltroEnte().trim());
	 	   if (filtroTributoEnte)      parameters.put("cdTrbEnte",fp.getTributo().trim());
	 	   if (filtroListaTributiEnti) parameters.put("listaCdTributo",fp.getTipoTributoList());
	 	   if (filtroCategoriaTributo) parameters.put("idTributo",fp.getTipoTributo().trim());
	 	   if (filtroIdPendenzaEnte)   parameters.put("idPendenzaEnte",fp.getCodicePendenza().trim());
	 	   if (filtroDataEmissioneDa)  parameters.put("dataEmissioneDa", fp.getDataEmissione_da());
	 	   if (filtroDataEmissioneA)   parameters.put("dataEmissioneA", fp.getDataEmissione_a());
	 	   if (filtroCausaleLike)      parameters.put("deCausale", fp.getCausale().replace('*', '%').trim());
	 	   if (filtroImportoDa)        parameters.put("importoTotaleDa",fp.getImportototale_da());
	 	   if (filtroImportoA)         parameters.put("importoTotaleA",fp.getImportototale_a());
	 	   if (filtroAnnoRif)          parameters.put("annoRiferimento",fp.getAnnoRiferimento());
	 	   //if (filtroPagamentoSingolo) nessun parametro necessario
	 	   //if (filtroPagamentoRateale) nessun parametro necessario
	 	   if (filtroRiscossoreLike)   parameters.put("riscossore", fp.getRiscossore().replace('*', '%').trim());
	 	   if (filtroRiscossore)       parameters.put("riscossore", fp.getRiscossore().trim());
	 	   if (filtroRiferimentoLike)  parameters.put("riferimento", fp.getRiferimento().replace('*', '%').trim());
	 	   if (filtroRiferimento)      parameters.put("riferimento", fp.getRiferimento().trim());
	 	   if (filtroDataScadenzaDa)   parameters.put("dataScadenzaDa", fp.getDataScadenza_da());
	 	   if (filtroDataScadenzaA)    parameters.put("dataScadenzaA", fp.getDataScadenza_a());
	 	   if (filtroIdPagamento)      parameters.put("idPagamento",idPagamento.trim());
	 	   //if (filtroChiusa)          nessun parametro necessario
		   //if (filtroApertaDaPagare)  nessun parametro necessario
		   //if (filtroApertaPagata)    nessun parametro necessario
		   //if (filtroPagataOrChiusa)  nessun parametro necessario
	 	   //if (hidePosizioniNascoste) nessun parametro necessario
	 		   
		
	 	   LOGGER.info("ORDINAMENTO SCELTO: "+fp.getOrdinamento());
		
// DA CAPIRE SE NECESSARIO
//	 	if(fp.getOrdinamento() != null && !fp.getOrdinamento().isEmpty()) {
//			theQuery1 += " ORDER BY " + fp.getOrdinamento() + " " + fp.getOrdinamentoTipo();
//		}

		// -----------------
		// Executing query
		// -----------------
		try {

			if (pagingCriteria != null) {
				// Se specificato, Pagina
				result = paginateByQuery(Pendenza.class, theQuery1, pagingCriteria, pagingData, parameters);
			} else {
				// Altrimenti non Pagina
				Query query = em.createNativeQuery(theQuery1, Pendenza.class);
				this.querySetup(query,parameters);
				result=query.getResultList();
			}

		} catch (Exception e) {
			LOGGER.error("error on posizione debitoria "+ fp, e);
			throw new DAORuntimeException(e);
		}

		return result;

	}

	
	
	
	
	
	//--------------------------------------------------------------------------------------------
	/**
	 * Calcola una distinct sui tributi ente in base alla posizione debitoria effettiva dell'utente.
	 * E non su tutti i tributi presenti sulla piattaforma.
	 * @param utente
	 * @return
	 */
	@Override
	public List<TributoEnte> listTributoEnteForUserPosizioneDebitoria(String utente) {

		String theQuery = "select distinct tr.*,e.* from JLTPEND pe, JLTDEPD de, JLTENTR tr, JLTENTI e "+
	                      " where de.id_pendenza = pe.id_pendenza "+
				          " and pe.id_ente = tr.id_ente "+
	                      " and pe.cd_trb_ente = tr.cd_trb_ente "+
				          " and pe.id_ente = e.id_ente "+
		//		          " and (tr.fl_Nascosto_Fe is null or tr.fl_Nascosto_Fe<>'Y')" +
	                      " and de.co_destinatario =:utente " +
				          " and pe.st_riga='V' " +
	                      " order by e.denom asc, tr.de_trb asc ";

		Query query = em.createNativeQuery(theQuery, TributoEnte.class);
		query.setParameter("utente", utente);

		return (List<TributoEnte>)query.getResultList();

	}

	private static final String QUERY_CONDIZIONI_BY_IUV = ""
			+ "  from CondizionePagamento "
			+ " where cdTrbEnte = :cdTrbEnte "
			+ "   and ente.idEnte = :idEnte "
			+ "   and idPagamento = :idPagamento"
			+ "	  and st_riga = 'V'"
			+ "   and tsAnnullamento is null";

	private static final String QUERY_CONDIZIONI_BY_IUV_NO_ENTE = "SELECT C.* "+
			"FROM JLTCOPD C, JLTDEPD D, JLTPEND P "+
			"WHERE C.ID_PENDENZA = D.ID_PENDENZA AND D.CO_DESTINATARIO=:cf "+
			"AND C.ID_PENDENZA = P.ID_PENDENZA "+
			"AND C.ID_ENTE IN (SELECT DISTINCT(ID_ENTE) FROM JLTENTR WHERE ID_TRIBUTO = :idTributo AND FL_INIZIATIVA='Y' AND STATO='A' ) " +
			"AND P.CD_TRB_ENTE IN (SELECT DISTINCT(CD_TRB_ENTE) FROM JLTENTR WHERE ID_TRIBUTO = :idTributo AND FL_INIZIATIVA='Y' AND STATO='A' ) "+
			"AND C.ID_PAGAMENTO = :idPagamento " +
			"AND C.ST_RIGA='V' ";
	private static final String QUERY_CONDIZIONI_BY_IUV_NO_ENTE_NO_TRB = "SELECT C.* "+
			"FROM JLTCOPD C, JLTDEPD D, JLTPEND P "+
			"WHERE C.ID_PENDENZA = D.ID_PENDENZA AND D.CO_DESTINATARIO=:cf "+
			"AND C.ID_PENDENZA = P.ID_PENDENZA "+
			"AND C.ID_ENTE IN (SELECT DISTINCT(ID_ENTE) FROM JLTENTR WHERE FL_INIZIATIVA='Y' AND STATO='A' ) " +
			"AND P.CD_TRB_ENTE IN (SELECT DISTINCT(CD_TRB_ENTE) FROM JLTENTR WHERE FL_INIZIATIVA='Y' AND STATO='A' ) "+
			"AND C.ID_PAGAMENTO = :idPagamento " +
			"AND C.ST_RIGA='V' ";

	@Override
	public CondizionePagamento getCondizioneByIUV(String iuv, String idEnte, String cdTrbEnte, String codiceFiscale) {

		Query query = em.createQuery(QUERY_CONDIZIONI_BY_IUV, CondizionePagamento.class);

		query.setParameter("cdTrbEnte", cdTrbEnte);

		query.setParameter("idEnte", idEnte);

		query.setParameter("idPagamento", iuv);

		CondizionePagamento c = null;

		try {

			c = (CondizionePagamento)query.getSingleResult();

		} catch (NoResultException e) {

		}

		if (c == null)

			return null;

		for(DestinatariPendenza d : c.getPendenza().getDestinatari())
				if(codiceFiscale.equalsIgnoreCase(d.getCoDestinatario()))
					return c;

		return null;

	}

	@Override
	public CategoriaTributo getCategoriaTributo(String idTributo) {
		try {

			return em.find(CategoriaTributo.class, idTributo);

		} catch (Exception e) {

			LOGGER.error("error on getCategoriaTributo ", e);

			throw new DAORuntimeException(e);

		}
	}

	@Override
	public CondizionePagamento getCondizioneByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException {

		Query query = em.createNativeQuery(QUERY_CONDIZIONI_BY_IUV_NO_ENTE, CondizionePagamento.class);

		query.setParameter("cf", codiceFiscale);

		query.setParameter("idTributo", idTributo);

		query.setParameter("idPagamento", iuv);

		CondizionePagamento c = null;

		try {

			c = (CondizionePagamento)query.getSingleResult();

		} catch (NoResultException e) {

		} catch (NonUniqueResultException nure){

			throw new BusinessConstraintException(EnumBusinessErrorCodes.APPEXC_IUV_NOT_UNIQUE, null, EnumBusinessErrorCodes.APPEXC_IUV_NOT_UNIQUE.getDescrizione());
		}

		if (c == null)
			return null;

		return c;

	}

	@Override
	public CondizionePagamento getCondizioneByIUV(String iuv, String codiceFiscale) throws BusinessConstraintException {

		Query query = em.createNativeQuery(QUERY_CONDIZIONI_BY_IUV_NO_ENTE_NO_TRB, CondizionePagamento.class);

		query.setParameter("cf", codiceFiscale);

		query.setParameter("idPagamento", iuv);

		CondizionePagamento c = null;

		try {

			c = (CondizionePagamento)query.getSingleResult();

		} catch (NoResultException e) {

		} catch (NonUniqueResultException nure){

			throw new BusinessConstraintException(EnumBusinessErrorCodes.APPEXC_IUV_NOT_UNIQUE, null, EnumBusinessErrorCodes.APPEXC_IUV_NOT_UNIQUE.getDescrizione());
		}

		if (c == null)
			return null;

		return c;

	}

	@Override
	public Long countPendenzeTributo(String idEnte, String cdTrbEnte) {

		Long num;

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("idEnte", idEnte);

		params.put("cdTrbEnte", cdTrbEnte);

		try {

			num = (Long) uniqueResultByQuery("countPendenzeTributo", params, em);

		} catch (Exception e) {

			LOGGER.error("error on countPendenzeTributo " + params, e);

			throw new DAORuntimeException(e);

		}

		return num;

	}


	@Override
	public CondizionePagamento getCondizioneByEnteIUV(String iuv, String codEnte, String codiceFiscale) {

		Query query = em.createNamedQuery("getCondizionePagamentoByEnteIdPagamento");

		query.setParameter("codEnte", codEnte);

		query.setParameter("idPagamento", iuv);

		query.setParameter("codiceFiscaleDebitore", codiceFiscale);

		CondizionePagamento c = null;

		List results = query.getResultList();
		if (!results.isEmpty()) {
			c = (CondizionePagamento) results.get(0);
		}

		return c;

	}



    @Override
    public List<CondizionePagamento> getCondizioniByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException {

        Query query = em.createNativeQuery(QUERY_CONDIZIONI_BY_IUV_NO_ENTE, CondizionePagamento.class);

        query.setParameter("cf", codiceFiscale);

        query.setParameter("idTributo", idTributo);

        query.setParameter("idPagamento", iuv);

        List<CondizionePagamento> condizioniDiPagamento = (List<CondizionePagamento>) query.getResultList();

        return condizioniDiPagamento;

    }

	@Override
	public Set<CondizionePagamento> createNewPendenza(CondizioneDTO condizione, AnonymousAnagraficaDTO anagraficaDTO) {

		String idPendenza = GeneratoreIdUnivoci.GetCurrent().generaId();

		Pendenza p = new Pendenza();

		p.setIdPendenza(idPendenza);

		p.setTsDecorrenza(currentTime());

		JltentrId id = new JltentrId(condizione.getEnte(), condizione.getTributo());

		TributoEnte tributoEnte = em.find(TributoEnte.class, id);

		p.setTributoEnte(tributoEnte);

		String idTributo = tributoEnte.getIdTributo();

		CategoriaTributo tributo = em.find(CategoriaTributo.class, idTributo);

		p.setCategoriaTributo(tributo);

		String idGenerato = IDGenerator.generateID(15);
		p.setIdPendenzaente("000"+idGenerato);

		p.setIdSystem(tributoEnte.getSIL());
		p.setTsCreazioneente(p.getTsDecorrenza());
		p.setTsEmissioneente(p.getTsCreazioneente());

		Calendar dataPrescrizione = GregorianCalendar.getInstance();
		dataPrescrizione.add(GregorianCalendar.YEAR, 10);

		p.setTsPrescrizione(new Timestamp(dataPrescrizione.getTimeInMillis()));

		p.setAnnoRiferimento(condizione.getAnnoRiferimento());

		p.setNote(condizione.getNote());

		p.setDvRiferimento("EUR");
		p.setImTotale(condizione.getImporto());
		p.setIdMittente("IRIS");
		p.setDeMittente(null);

		p.setDeCausale(condizione.getCausalePendenza());

		p.setStPendenza("A");
		p.setFlModPagamento("S");

		p.setPrVersione(0);
		//fix #2018, per consentire la chiusura della pendenza
		p.setCartellaPagamento(new Integer(0));

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		p.setOpInserimento("IdP");
		p.setTsInserimento(currentTime());

		Set<DestinatariPendenza> destinatari = new HashSet<DestinatariPendenza>();
		DestinatariPendenza dp = new DestinatariPendenza();
		dp.setIdDestinatario(GeneratoreIdUnivoci.GetCurrent().generaId());
		dp.setTsDecorrenza(currentTime());

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		dp.setOpInserimento("IdP");
		dp.setTsInserimento(currentTime());
		dp.setPendenza(p);
		dp.setCoDestinatario(anagraficaDTO.getCodFiscale());
		dp.setDeDestinatario(anagraficaDTO.getDenominazione());
		if (dp.getCoDestinatario().length() == 11)
			dp.setTiDestinatario("AL"); //partite iva
		else
			dp.setTiDestinatario("CI"); // cittadini
		if (dp.getCoDestinatario().length() == 11)
			dp.setTipoSoggettoDestinatario("G"); //partite iva
		else
			dp.setTipoSoggettoDestinatario("F"); // cittadini
		dp.setAnagraficaDestinatario(anagraficaDTO.getDenominazione());		
		dp.setIndirizzoDestinatario(anagraficaDTO.getIndirizzo());
		dp.setNumCivicoDestinatario(anagraficaDTO.getNumCivico());
		dp.setCapDestinatario(anagraficaDTO.getCapDestinatario());
		dp.setLocalitaDestinatario(anagraficaDTO.getLocalita());
		dp.setProvinciaDestinatario(anagraficaDTO.getProvincia());
		dp.setNazioneDestinatario(anagraficaDTO.getNazione());
		dp.setEmailDestinatario(anagraficaDTO.getEmail());
		dp.setLuogoNascitaDestinatario(anagraficaDTO.getLuogoNascita());
		dp.setDataNascitaDestinatario(anagraficaDTO.getDataNascita());
	
		destinatari.add(dp);
		p.setDestinatari(destinatari);
		p.setPrVersione(0);

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		p.setOpInserimento("IdP");
		p.setTsInserimento(currentTime());

		Set<CondizionePagamento> condizioni = new HashSet<CondizionePagamento>();
		CondizionePagamento condizionePagamento = new CondizionePagamento();
		condizionePagamento.setIdCondizione(GeneratoreIdUnivoci.GetCurrent().generaId());
		condizionePagamento.setTsDecorrenza(currentTime());
		condizionePagamento.setTiPagamento("S");
		Enti e = em.find(Enti.class, condizione.getEnte());
		condizionePagamento.setEnte(e);
		condizionePagamento.setCdTrbEnte(condizione.getTributo());
        // 
		String iuvOrIdGenerato =getIdPagamentoOrIUV(idGenerato,tributoEnte);
		condizionePagamento.setIdPagamento(iuvOrIdGenerato);

		condizionePagamento.setDtIniziovalidita(currentTime());

		condizionePagamento.setDtScadenza(condizione.getScadenza());

		condizionePagamento.setDtFinevalidita(condizione.getFineValidita());

		condizionePagamento.setImTotale(condizione.getImporto());
		condizionePagamento.setStPagamento("N");
		condizionePagamento.setPrVersione(0);

		// TODO PAZZIK valorizzare con utente anonimo od utente Iris, scrivere su DB IDP, quali informazioni d�?
		condizionePagamento.setOpInserimento("Idp");

		condizionePagamento.setTsInserimento(currentTime());
		condizionePagamento.setPendenza(p);

		BillItemInspector.makeHidden(condizionePagamento);

		condizioni.add(condizionePagamento);
		p.setCondizioni(condizioni);

		em.persist(p);

		return p.getCondizioni();

	}
	
	private String getIdPagamentoOrIUV(String idPagamentoGen,TributoEnte te){
		
		if (!te.getFlNdpIuvGenerato().equals("0")) {
			int numIuvDaGenerare = 1;
			//
			List<String> iuvList=iuvGenerator.IUVGeneratorByTributoEnte(te, numIuvDaGenerare);
			String iuv = iuvList.iterator().next();
					
			// 
			IUVPosizEnteMap iuvMap = new IUVPosizEnteMap();
			IUVPosizEnteMapPK iuvPK = new IUVPosizEnteMapPK(te.getIdEnte(), iuv);
			iuvMap.setId(iuvPK);
			iuvMap.setCdTrbEnte(te.getCdTrbEnte());
			iuvMap.setIdPagamento(idPagamentoGen);					
			iuvMap.setStRiga("V");
			iuvMap.setOpInserimento("LOADER");				
			iuvMap.setTsInserimento(new Timestamp(System.currentTimeMillis()));
					
			em.persist(iuvMap);
			return iuv;
		} else {
			return idPagamentoGen;
		}
		
	}
	
}
