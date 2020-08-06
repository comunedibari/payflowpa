package it.tasgroup.iris.business.ejb.anonymous;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Tuple;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.business.ejb.client.anonymous.AnonymousPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anonymous.AnonymousPaymentBusinessRemote;
import it.tasgroup.iris.domain.AnagraficaCorsiDottorato;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.NazioneCittadinanza;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.dto.SearchTributiRequest;
import it.tasgroup.iris.dto.anonymous.UserData;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousAnagraficaDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentDTOLight;
import it.tasgroup.iris.dto.anonymous.payment.CondizioneDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.persistence.dao.interfaces.AnagraficaCorsiDottoratoDAO;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.DestinatariPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.NazioneCittadinanzaDAO;
import it.tasgroup.iris.persistence.dao.interfaces.NotificheCartDao;
import it.tasgroup.iris.persistence.dao.interfaces.NotifichePagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.OperatoriDAO;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.TributoEnteDao;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;



@Stateless(name = "AnonymousPaymentBusiness")
public class AnonymousPaymentBusinessBean implements  AnonymousPaymentBusinessLocal, AnonymousPaymentBusinessRemote {

//	private static final Logger LOGGER = LogManager.getLogger(AnonymousPaymentBusinessBean.class);

	@EJB(name = "AnagraficaCorsiDottoratoDaoService")
	AnagraficaCorsiDottoratoDAO anagraficaCorsiDottoratoDAO;
	
	@EJB(name = "NazioneCittadinanzaService")
	NazioneCittadinanzaDAO nazioneCittadinanzaDAO;

	@EJB(name = "EntiDaoService")
	EntiDao entiDAO;

	@EJB(name = "TributoEnteDaoService")
	TributoEnteDao tributoEnteDao;

	@EJB(name = "GestioneFlussiDaoService")
	GestioneFlussiDao gestioneFlussiDao;

	@EJB(name = "PendenzaDaoService")
	PendenzaDao pendenzaDao;

	@EJB(name = "DestinatariPendenzaDaoService")
	DestinatariPendenzaDao destinatariPendenzaDao;

	@EJB (name="IntestatariDaoService")
	IntestatariDAO intestatariDao;
	
	@EJB (name="OperatoriDaoService")
	OperatoriDAO operatoriDao;

	@EJB (name="NotifichePagamentiDaoService")
	NotifichePagamentoDao notifichePagamentoDao;


//	@EJB (name="PosizioneDebitoriaService")
//	PosizioneDebitoriaService posDebDB;

	@EJB(name = "PagamentiDaoService")
	PagamentiDao pagamentiDao;

	@EJB(name = "NotificheCartDaoService")
	NotificheCartDao notificheCartDao;


	@EJB(name = "CondizioniPagamentoDaoService")
	private CondizioniPagamentoDao condPagDao;
	
	@Override
	public Enti getEntiAbleToAnonymousPaymentByIdEnte(String idEnte) {

		return (Enti) entiDAO.getEntiAbleToAnonymousPaymentByIdEnte(idEnte);
	}

	@Override
	public Enti getEntiAbleToAnonymousPaymentByCdEnte(String cdEnte) {
		return entiDAO.getEntiAbleToAnonymousPaymentByCdEnte(cdEnte);
	}

	@Override
	public Enti getEntiAbleToAnonymousPaymentByLapl(String lapl) {
		return entiDAO.getEntiAbleToAnonymousPaymentByLapl(lapl);
	}

	@Override
	public List<AnagraficaCorsiDottorato> getAnagraficaCorsiDottorato() {

		return (List<AnagraficaCorsiDottorato>) anagraficaCorsiDottoratoDAO.readListaCorsiDottorato();
	}
	
	@Override
	public List<NazioneCittadinanza> getNazioneCittadinanza() {

		return (List<NazioneCittadinanza>) nazioneCittadinanzaDAO.readListaNazioniCittadinanza();
	}
	
	@Override
	public List<Enti> getEntiAbleToAnonymousPayment() {

		return getEntiAbleToAnonymousPayment(null);
	}
	
	@Override
	public List<Enti> getEntiAbleToAnonymousPayment(String predeterminatoValue) {

		return (List<Enti>) entiDAO.listEntiAbleToAnonymousPayment(predeterminatoValue);
	}


	@Override
	public List<TributoEnte> getTributiAbleToAnonymousPaymentByIdEnte(String idEnte){

		return (List<TributoEnte>) tributoEnteDao.listTributiAbleToAnonymousPaymentByIdEnte(idEnte);
	}
	
	@Override
	public List<TributoEnte> getTributiAbleToAnonymousPayment(){

		return (List<TributoEnte>) tributoEnteDao.listTributiAbleToAnonymousPayment();
	}


	@Override
	public GestioneFlussi getAnonymousPaymentByToken(String token){

		return (GestioneFlussi) gestioneFlussiDao.getAnonymousPaymentByToken(token);

	}
		
	@Override public List<Map> readAnonymousPaymentByCondizione(String idCondizione) {
			return gestioneFlussiDao.readAnonymousPaymentByCondizione(idCondizione);
			
	}

	@Override
	public List<Tuple> getAnonymousPaymentConditionByIdDistinta(Long idDistinta){

		return pendenzaDao.getAnonymousPaymentConditionByIdDistinta(idDistinta);
	}

	@Override
	public Set<CondizionePagamento> createNewPendenza(TributoStrutturato tributo) {

		return createNewPendenza(tributo, null);
	}


	@Override
	public Set<CondizionePagamento> createNewPendenza(TributoStrutturato tributo, AnonymousAnagraficaDTO anagrafica) {

		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");

		String generazioneIUV = cpl.getProperty("iris.pagamentispontanei.generazioneIUV");

		boolean isNDPCompliant = generazioneIUV!= null && generazioneIUV.equals("NDPCompliant");

		Set<CondizionePagamento> condizioni =  pendenzaDao.createNewPendenza(tributo, isNDPCompliant, anagrafica);

		return condizioni;
	}

	@Override
	public <T extends TributoStrutturato> List<T> findTributo(SearchTributiRequest<T> searchRequestData) {
		return pendenzaDao.findTributi(searchRequestData);
	}

	@Override
	public <T extends TributoStrutturato> List<T> findTributoHidden(SearchTributiRequest<T> searchRequestData) {
		searchRequestData.setSearchHidden(true);
		return pendenzaDao.findTributi(searchRequestData);
	}
	@Override
	public UserData loadUserData(String sessionId) {
		Intestatari intestatario = intestatariDao.getIntestatarioBySessionId(sessionId);
		Operatori op = operatoriDao.getOperatoreByCodiceFiscale(intestatario.getIndirizzipostaliobjIForm().getFiscalCodeIForm());
		int profileNumber = op.getIntestatarioperatori().size();
	
//		UserData user = new UserData(intestatario.getIndirizzipostaliobjIForm().getFiscalCodeIForm(), intestatario.getIndirizzipostaliobjIForm().getEmailIForm(), intestatario.getRagionesocialeIForm());
		UserData user = new UserData(intestatario.getIndirizzipostaliobjIForm().getFiscalCodeIForm(), null, intestatario.getRagionesocialeIForm());

		user.setSingleProfile(profileNumber == 1);    
		return user;
	}

	@Override
	public Pendenza getPendenzaById(TributoStrutturato tributo) {

		return getPendenzaById(tributo.getIdPendenza());

	}

    @Override
    public Pendenza getIdPendenzaAttiva(TributoStrutturato tributo) {
        Pendenza pend = getPendenzaById(tributo.getIdPendenza());
        TributoEnte tributoEnte = pend.getTributoEnte();

        return getIdPendenzaAttiva(tributoEnte.getCdTrbEnte(), tributoEnte.getIdEnte(), pend.getIdPendenzaente());

    }

	@Override
	public Pendenza getPendenzaById(String idPendenza) {

		return pendenzaDao.getPendenzaById(idPendenza);

	}

	@Override
	public Pendenza getIdPendenzaAttiva(String cdTrbEnte, String idEnte, String idPendenzaEnte) {

		return pendenzaDao.getPendenzaByChiaveSemantica(cdTrbEnte, idEnte, idPendenzaEnte);

	}

	@Override
	public <T extends TributoStrutturato> List<AnonymousPaymentDTOLight> getExistingPayments(T tributo, String cdTrbEnte) {
		List<AnonymousPaymentDTOLight> existingValidPayments = new ArrayList<AnonymousPaymentDTOLight>();
		List<Pagamenti> existingPayments = pagamentiDao.getExistingPayments(tributo, cdTrbEnte);

		for (Pagamenti pagamento : existingPayments) {
			if(pagamento.isValidPayment())
				existingValidPayments.add(createAnonymousPaymentDTOLight(pagamento));

		}
		return existingValidPayments;
	}

	private AnonymousPaymentDTOLight createAnonymousPaymentDTOLight(Pagamenti pagamento) {
		if (pagamento == null)
			return null;

		AnonymousPaymentDTOLight payLight = new AnonymousPaymentDTOLight();
		payLight.setDistinta(pagamento.getFlussoDistinta().getId().toString());


		payLight.setStPagamento(pagamento.getStPagamento());
		payLight.setCdTrbEnte(pagamento.getCdTrbEnte());
		payLight.setCoPagante(pagamento.getCoPagante());
		payLight.setIdEnte(pagamento.getIdEnte());
		payLight.setImPagato(pagamento.getImPagato());

		return payLight;
	}

	@Override
	public CondizionePagamento getCondizioneByIUV(String iuv, String idEnte, String cdTrbEnte, String codiceFiscale) {

		CondizionePagamento cp = pendenzaDao.getCondizioneByIUV(iuv, idEnte, cdTrbEnte, codiceFiscale);
		return cp;

	}

	@Override
	public List<String> listaDebitoriByIdPendenza(String idPendenza) {
		return destinatariPendenzaDao.listaDebitoriByIdPendenza(idPendenza);
	}

	@Override
	public CategoriaTributo getCategoriaTributo(String idTributo) {
		return pendenzaDao.getCategoriaTributo(idTributo);
	}

	@Override
	public List<Enti> getEntiAbleForTributo(String idTributo) {

		return entiDAO.listEntiAbleForTributo(idTributo);
	}

	@Override
	public List<Enti> getEntiAbleForCdTrbEnte(String cdTrbEnte) {

		return entiDAO.listEntiAbleForCdTrbEnte(cdTrbEnte);
	}

	@Override
	public CondizionePagamento getCondizioneByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException {

		CondizionePagamento cp = pendenzaDao.getCondizioneByIUV(iuv, idTributo, codiceFiscale);

		return cp;
	}

	@Override
	public CondizionePagamento getCondizioneByIUV(String iuv, String codiceFiscale) throws BusinessConstraintException {
		CondizionePagamento cp = pendenzaDao.getCondizioneByIUV(iuv, codiceFiscale);
		return cp;
	}

	@Override
	public List<Pendenza> getCondizioniByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException {

		CondizionePagamento cp = null;
		PosizioneDebitoriaInputVO filterParameters = new PosizioneDebitoriaInputVO();
		filterParameters.setDestinatario(codiceFiscale);
		filterParameters.setFiltroEnte(idEnte);
		filterParameters.setCodicePendenza(codPagamento);
		PagingCriteria pagingCriteria = new PagingCriteria();
		pagingCriteria.setDirection("page");
		PendenzaDao.PosizioneDebitoriaOptions options= new PendenzaDao.PosizioneDebitoriaOptions();
		
		List<Pendenza> pendenze = pendenzaDao.listPendenzeByFilterParameters(filterParameters, new PagingData(), pagingCriteria, options);

		return pendenze;
	}

   @Override
	public Set<CondizionePagamento>  createNewPendenza(CondizioneDTO condizione, AnonymousAnagraficaDTO anagrafica) {

		Set<CondizionePagamento> condizioni =  pendenzaDao.createNewPendenza(condizione, anagrafica);

		return condizioni;

	}


    @Override
    public List<CondizionePagamento> getCondizioniByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException {
        List<CondizionePagamento> condizionePagamentoList = pendenzaDao.getCondizioniByIUV(iuv, idTributo, codiceFiscale);
        return condizionePagamentoList;
    }

	@Override
	public CondizionePagamento getCondizioneByEnteIUV(String iuv, String codEnte, String codiceFiscale) {
		CondizionePagamento cp = pendenzaDao.getCondizioneByEnteIUV(iuv, codEnte, codiceFiscale);
		return cp;
	}


	@Override
	public List<CondizionePagamento> getCondizioniByIdsWithStatoCalcolato (List<String> ids) {
		List<CondizionePagamento> condizioniPagamento = condPagDao.getCondizioniByIdList(ids);
		return condizioniPagamento;
	}

	@Override
	public CondizionePagamento getCondizioneQRCode(String codFiscaleCreditore, String idPagamentoEnte, BigDecimal importo) {
		return condPagDao.getCondizioneByCFCreditoreIDEnteImporto (codFiscaleCreditore,idPagamentoEnte,importo);
	}

	@Override
	public byte[] getDatiCfgTributoPlugin(String idEnte, String cdTributo) {
		TributoEnte tributo = tributoEnteDao.getTributiEntiByKey(idEnte, cdTributo);
		if(tributo.getCfgTributoEntePlugin() != null)
			return tributo.getCfgTributoEntePlugin().getDati();
		else
			return null;
	}
	
	@Override
	public List<Pendenza> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException {
		PosizioneDebitoriaInputVO filterParameters = new PosizioneDebitoriaInputVO();
		if (codiceFiscale != null) filterParameters.setDestinatario(codiceFiscale);
		filterParameters.setFiltroEnte(idEnte);
		filterParameters.setCodicePendenza(codPagamento);
		PagingCriteria pagingCriteria = new PagingCriteria();
		pagingCriteria.setDirection("page");
		PendenzaDao.PosizioneDebitoriaOptions options= new PendenzaDao.PosizioneDebitoriaOptions();
		
		List<Pendenza> pendenze = pendenzaDao.listPendenzeByFilterParameters(filterParameters, new PagingData(), pagingCriteria, options);

		return pendenze;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String replaceCondizione (String idCondizione, BigDecimal importo, String noteVersante, String codiceFiscaleOperatore) {
		//aggiorno la posizione annullando la vecchia pendenza,condizione, ecc e creandone una nuova
		//NB: metodo custom per l'esigenza del DI
		String newCondId = pendenzaDao.replacePosizione(idCondizione,  importo,  noteVersante,  codiceFiscaleOperatore);
		//ritorno l'id della nuova condizione
		return newCondId;
		
	}


}
