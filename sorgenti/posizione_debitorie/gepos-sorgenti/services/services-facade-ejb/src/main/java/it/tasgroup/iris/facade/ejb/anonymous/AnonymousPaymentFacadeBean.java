package it.tasgroup.iris.facade.ejb.anonymous;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Tuple;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.business.ejb.bacheca.BachecaNewsBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anonymous.AnonymousPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AnnullamentoPagamentoServiceLocal;
import it.tasgroup.iris.domain.AnagraficaCorsiDottorato;
import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.NazioneCittadinanza;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.helper.PaymentConditionStatusCalculator;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.SearchTributiRequest;
import it.tasgroup.iris.dto.anonymous.UserData;
import it.tasgroup.iris.dto.anonymous.payment.AnagraficaCorsiDottoratoDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousAnagraficaDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousBachecaNewsDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousEntiDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentConditionDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentDTOLight;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPendenzaDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousTributoDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousTributoEnteDTO;
import it.tasgroup.iris.dto.anonymous.payment.CondizioneDTO;
import it.tasgroup.iris.dto.anonymous.payment.NazioneCittadinanzaDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.facade.ejb.anagrafica.AnagraficaDTOBuilder;
import it.tasgroup.iris.facade.ejb.anonymous.dto.builder.AnonymousBachecaNewsDTOBuilder;
import it.tasgroup.iris.facade.ejb.anonymous.dto.builder.AnonymousPaymentDTOBuilder;
import it.tasgroup.iris.facade.ejb.anonymous.dto.builder.SessionShoppingCartItemDTOBuilder;
import it.tasgroup.iris.facade.ejb.client.anonymous.AnonymousPaymentFacade;
import it.tasgroup.iris.facade.ejb.client.anonymous.AnonymousPaymentFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.anonymous.AnonymousPaymentFacadeRemote;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

@Stateless(name = "AnonymousPaymentFacade")
public class AnonymousPaymentFacadeBean implements AnonymousPaymentFacadeLocal, AnonymousPaymentFacadeRemote {

//	private static final Logger LOGGER = LogManager.getLogger(AnonymousPaymentFacadeBean.class);

	@EJB(name = "AnonimousPaymentBusiness")
	private AnonymousPaymentBusinessLocal anonymousPaymentBusinessBean;
	
	@EJB(name = "distintePagamentoBusinessBean")
	private DistintePagamentoBusinessLocal distintePagamentoBusinessBean;

	@EJB(name = "AnnullamentoBusiness")
	private AnnullamentoPagamentoServiceLocal annullamentoPagamentoBean;
	
	@EJB(name = "BachecaNewsBusiness")
	private BachecaNewsBusinessLocal bachecaNewsBean;
	
	

	@Override
	public AnonymousEntiDTO readEntiAbleToAnonimousPaymentByCdEnte(String idEnte) {

		Enti ente = anonymousPaymentBusinessBean.getEntiAbleToAnonymousPaymentByCdEnte(idEnte);

		AnonymousEntiDTO dto = AnonymousPaymentDTOBuilder.populateAnonymousEntiDTO(ente);

		return dto;
	}


	@Override
	public AnonymousEntiDTO readEntiAbleToAnonimousPaymentByIdEnte(String idEnte) {

		Enti ente = anonymousPaymentBusinessBean.getEntiAbleToAnonymousPaymentByIdEnte(idEnte);

		AnonymousEntiDTO dto = AnonymousPaymentDTOBuilder.populateAnonymousEntiDTO(ente);

		return dto;
	}
	
	@Override
	public AnonymousEntiDTO readEnteAbleToAnonimousPaymentByLapl(String lapl) {

		Enti ente = anonymousPaymentBusinessBean.getEntiAbleToAnonymousPaymentByLapl(lapl);

		AnonymousEntiDTO dto = AnonymousPaymentDTOBuilder.populateAnonymousEntiDTO(ente);

		return dto;
	}
	
	@Override
	public List<AnonymousEntiDTO> readListEntiAbleToAnonymousPayment() {
		return readListEntiAbleToAnonymousPayment(null);
	}

	@Override
	public List<AnonymousEntiDTO> readListEntiAbleToAnonymousPayment(String predeterminatoValue) {

		List<Enti> listaEnti = anonymousPaymentBusinessBean.getEntiAbleToAnonymousPayment(predeterminatoValue);

		List<AnonymousEntiDTO> dtos = AnonymousPaymentDTOBuilder.populateListaAnonymousEntiDTO(listaEnti);

		return dtos;
	}
	
	@Override
	public List<AnagraficaCorsiDottoratoDTO> readListAnagraficaCorsiDottorato() {

		List<AnagraficaCorsiDottorato> listaCorsiDottorato = anonymousPaymentBusinessBean.getAnagraficaCorsiDottorato();

		List<AnagraficaCorsiDottoratoDTO> dtos = AnagraficaDTOBuilder.fillListaAnagraficaCorsiDottoratoDTO(listaCorsiDottorato);

		return dtos;
	}
	
	@Override
	public List<NazioneCittadinanzaDTO> readListNazioneCittadinanza() {

		List<NazioneCittadinanza> listaNazioniCittadinanza = anonymousPaymentBusinessBean.getNazioneCittadinanza();

		List<NazioneCittadinanzaDTO> dtos = AnagraficaDTOBuilder.fillListaNazioneCittadinanzaDTO(listaNazioniCittadinanza);

		return dtos;
	}

	@Override
	public AnonymousTributoDTO readCategoriaTributoById(String idTributo) {

		CategoriaTributo tributo = anonymousPaymentBusinessBean.getCategoriaTributo(idTributo);

		AnonymousTributoDTO dto = AnonymousPaymentDTOBuilder.populateAnonymousTributoDTO(tributo);

		return dto;
	}

	@Override
	public List<AnonymousEntiDTO> readEntiAbleForTributo(String idTributo) {

		List<Enti> enti = anonymousPaymentBusinessBean.getEntiAbleForTributo(idTributo);

		List<AnonymousEntiDTO> dtos = AnonymousPaymentDTOBuilder.populateListaAnonymousEntiDTO(enti, idTributo);

		return dtos;
	}

	@Override
	public List<AnonymousEntiDTO> readEntiAbleForCdTrbEnte(String cdTrbEnte) {

		List<Enti> enti = anonymousPaymentBusinessBean.getEntiAbleForCdTrbEnte(cdTrbEnte);

		List<AnonymousEntiDTO> dtos = AnonymousPaymentDTOBuilder.populateListaAnonymousEntiDTO(enti);

		return dtos;
	}


	@Override
	public List<AnonymousTributoEnteDTO> readListTributiAbleToAnonymousPaymentByIdEnte(String idente) {

		List<TributoEnte> listaTributi = anonymousPaymentBusinessBean.getTributiAbleToAnonymousPaymentByIdEnte(idente);

		List<AnonymousTributoEnteDTO> dtos = AnonymousPaymentDTOBuilder.populateListaAnonymousTributoEnteDTO(listaTributi);

		return dtos;
	}
	
	@Override
	public List<AnonymousTributoEnteDTO> readListTributiAbleToAnonymousPayment() {

		List<TributoEnte> listaTributi = anonymousPaymentBusinessBean.getTributiAbleToAnonymousPayment();

		List<AnonymousTributoEnteDTO> dtos = AnonymousPaymentDTOBuilder.populateListaAnonymousTributoEnteDTO(listaTributi);

		return dtos;
	}

	@Override
	public AnonymousTributoEnteDTO readTributiAbleToAnonymousPaymentByIdEnteAndTipo(String idente, String tipoTributo) {

		List<TributoEnte> listaTributi = anonymousPaymentBusinessBean.getTributiAbleToAnonymousPaymentByIdEnte(idente);

		for (TributoEnte tributoEnte : listaTributi) {

			if(tributoEnte.getCdTrbEnte().equals(tipoTributo))
				return AnonymousPaymentDTOBuilder.populateAnonymousTributoEnteDTO(tributoEnte);

		}

		return null;
	}

	@Override
	public AnonymousPaymentDTO readAnonymousPaymentByToken(String token) {


		if (token.startsWith("RT") || token.startsWith("rt"))

			token = token.substring(2,token.length());


		GestioneFlussi distinta = anonymousPaymentBusinessBean.getAnonymousPaymentByToken(token);

		AnonymousPaymentDTO dto = AnonymousPaymentDTOBuilder.populateAnonymousTributoEnteDTO(distinta);

		return dto;

	}

	@Override
	public List<AnonymousPaymentConditionDTO> readListAnonymousPaymentConditionByIdDistinta(Long idDistinta){

		List<AnonymousPaymentConditionDTO> dtos = new ArrayList<AnonymousPaymentConditionDTO>();
		List<GestioneFlussi> l =distintePagamentoBusinessBean.getDistinteStessoGruppo(idDistinta);
		
		for (GestioneFlussi g :l) {
		    List<Tuple> lstPaymentCon = anonymousPaymentBusinessBean.getAnonymousPaymentConditionByIdDistinta(g.getId());

		    List<AnonymousPaymentConditionDTO> dtos1 = AnonymousPaymentDTOBuilder.populateListaAnonymousPaymentConditionDTO(lstPaymentCon);
            dtos.addAll(dtos1);
		    // aggiungo la lista dei debitori
		    for (AnonymousPaymentConditionDTO anonymousPaymentConditionDTO : dtos) {
			   List<String> cfDebitori = anonymousPaymentBusinessBean.listaDebitoriByIdPendenza(anonymousPaymentConditionDTO.getIdPendenza());
			   anonymousPaymentConditionDTO.setCfDebitori(cfDebitori);
		    }
		}
		return dtos;
	}

	@Override
	public SessionShoppingCartItemDTO createNewPendenza(TributoStrutturato tributo) {

		return createNewPendenza(tributo, null);
	}
	
	@Override
	public SessionShoppingCartItemDTO createNewPendenza(TributoStrutturato tributo, AnonymousAnagraficaDTO anagrafica) {
		Set<CondizionePagamento> condizioni = anonymousPaymentBusinessBean.createNewPendenza(tributo, anagrafica);
		SessionShoppingCartItemDTO dtos = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTO(condizioni.iterator().next(), null);

		return dtos;
	}

	@Override
	public SessionShoppingCartItemDTO createNewPendenza(CondizioneDTO condizione, AnonymousAnagraficaDTO anagrafica) {

		Set<CondizionePagamento> condizioni = anonymousPaymentBusinessBean.createNewPendenza(condizione, anagrafica);

		SessionShoppingCartItemDTO dto = SessionShoppingCartItemDTOBuilder.populateSessionShoppingCartItemDTO(condizioni.iterator().next(), null);

		return dto;
	}


	@Override
	public <T extends TributoStrutturato> List<T> searchPendenza(SearchTributiRequest<T> searchTributiRequest) {

		return anonymousPaymentBusinessBean.findTributo(searchTributiRequest);

	}

	@Override
	public <T extends TributoStrutturato> List<T> searchPendenzaHidden(SearchTributiRequest<T> searchTributiRequest) {

		return anonymousPaymentBusinessBean.findTributoHidden(searchTributiRequest);

	}
    @Override
    public UserData loadUserData(String sessionId) {
        return anonymousPaymentBusinessBean.loadUserData(sessionId);
    }



    @Override
    public List<CondizioneDTO> getCondizioniTributo(TributoStrutturato tributo) {

		Pendenza pend = anonymousPaymentBusinessBean.getPendenzaById(tributo);

		return AnonymousPaymentFacadeDTOBuilder.populateCondizioneDTOList(pend);

    }
    @Override
    public int checkOperazioniInCorsoEffettuatoDocValido(TributoStrutturato tributo) {

    	Pendenza pend = anonymousPaymentBusinessBean.getPendenzaById(tributo);
    	if (pend!=null) {
    	   if (pend.getCondizioni()!=null) {
    		   Iterator<CondizionePagamento> condIter= pend.getCondizioni().iterator();
    		   while (condIter.hasNext()) {
    			   CondizionePagamento cond = condIter.next();
    			   // test PAGAMENTI
    			   if (cond.getPagamenti()!=null) {
    				   Iterator<Pagamenti> pagamentiIter= cond.getPagamenti().iterator();
    				   while (pagamentiIter.hasNext()) {
    					   Pagamenti pagam= pagamentiIter.next();
    					   String stPag = pagam.getStPagamento();
    					   if (stPag.equals("EF") || stPag.equals("ES") || stPag.equals("IC")) {
    						   return AnonymousPaymentFacade.VALID_PAYMENT_FOUND;
    					   }

    				   }
    			   }
    			   // test CONDIZIONI_DOCUMENTO --> DOCUMENTI_PAGAMENTO
    			   if (cond.getCondizioniDocumento()!=null) {
    				  Iterator<CondizioneDocumento> condDocIter = cond.getCondizioniDocumento().iterator();
    				  while (condDocIter.hasNext()) {
    					  CondizioneDocumento condiz = condDocIter.next();
    					  if (condiz.getDocumento()!=null) {
    						  String stato = condiz.getDocumento().getStato();
    						  if ("E".equals(stato)|| "P".equals(stato)) {
    							  return AnonymousPaymentFacade.VALID_DOC_FOUND;
    						  }

    					  }

    				  }


    			   }
    		   }
    	   }

    	}
    	return -1;
    }




    @Override
    public List<CondizioneDTO> getCondizioniTributoAggiornato(TributoStrutturato tributo) {

		Pendenza pend = anonymousPaymentBusinessBean.getIdPendenzaAttiva(tributo);

		return AnonymousPaymentFacadeDTOBuilder.populateCondizioneDTOList(pend);

    }

    @Override
    public AnonymousPendenzaDTO getPendenzaByTributo(TributoStrutturato tributo, String idCondizione) {

		Pendenza pend = anonymousPaymentBusinessBean.getPendenzaById(tributo);

		return AnonymousPaymentFacadeDTOBuilder.populatePendenzaDTO(pend, idCondizione);

    }

	@Override
	public <T extends TributoStrutturato> List<AnonymousPaymentDTOLight> getExistingPayments(T tributo, String cdTrbEnte) {

		return anonymousPaymentBusinessBean.getExistingPayments(tributo, cdTrbEnte);

	}


	@Override
	public CondizioneDTO getCondizioneByIUV(String iuv, String idEnte, String cdTrbEnte, String codiceFiscale) {

		CondizionePagamento condizionePagamento = anonymousPaymentBusinessBean.getCondizioneByIUV(iuv, idEnte, cdTrbEnte, codiceFiscale);

		if(condizionePagamento != null) {

			condizionePagamento.updateStatoPagamentoCalcolato();
			return AnonymousPaymentFacadeDTOBuilder.populateCondizioneDTO(condizionePagamento);

		}

		return null;

	}

	public CondizioneDTO getCondizioneByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException{
		
		CondizionePagamento condizionePagamento = anonymousPaymentBusinessBean.getCondizioneByIUV(iuv, idTributo, codiceFiscale);

		if (condizionePagamento != null) {

			condizionePagamento.updateStatoPagamentoCalcolato();

			return AnonymousPaymentFacadeDTOBuilder.populateCondizioneDTO(condizionePagamento);

		}

		return null;

	}

	@Override
	public CondizioneDTO getCondizioneByIUV(String iuv, String codiceFiscale) throws BusinessConstraintException{
		CondizionePagamento condizionePagamento = anonymousPaymentBusinessBean.getCondizioneByIUV(iuv, codiceFiscale);
		if (condizionePagamento != null) {
			condizionePagamento.updateStatoPagamentoCalcolato();
			return AnonymousPaymentFacadeDTOBuilder.populateCondizioneDTO(condizionePagamento);
		}
		return null;
	}

	@Override
	public AnonymousPendenzaDTO getPendenzaAroundCondizione(String idPendenza, String idCondizione) {

		Pendenza pendenza = anonymousPaymentBusinessBean.getPendenzaById(idPendenza);

		return AnonymousPaymentFacadeDTOBuilder.populatePendenzaDTO(pendenza, idCondizione);

	}

	@Override
	public void annullaPagamentoNDP(String idCondizione) {
		annullamentoPagamentoBean.annullaPagamentoNDP(idCondizione, StatiPagamentiIris.ANNULLATO_DA_OPERATORE);
	}
	
	@Override
	public List<AnonymousBachecaNewsDTO> readAnonymousBachecaNewsDTO() {
		ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.disablePaging();
		List<BachecaNews> lista = bachecaNewsBean.getBachecaNewsListToDisplay(containerDTO);
		return AnonymousBachecaNewsDTOBuilder.populateAnonymousBachecaNewsDTO(lista);
	}
	
	@Override
	public AnonymousBachecaNewsDTO readAnonymousBachecaNewsDTO(Long id) {
		AnonymousBachecaNewsDTO dto = null;
		try {
			BachecaNews bn = bachecaNewsBean.getBachecaNewsById(id);
			dto = AnonymousBachecaNewsDTOBuilder.populateAnonymousBachecaNewsDTO(bn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
		
	}

	@Override
	public byte[] getDatiCfgTributoPlugin(String idEnte, String cdTributo) {
		return anonymousPaymentBusinessBean.getDatiCfgTributoPlugin(idEnte, cdTributo);
	}
	
	@Override
	public List<CondizioneDTO> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException {
		List<Pendenza> pendenze = anonymousPaymentBusinessBean.ricercaAvvisiByCodPagamento(codPagamento, codiceFiscale, idEnte);
		
		List<CondizioneDTO> resultList = new ArrayList<CondizioneDTO>();
		for (Pendenza pend: pendenze) {
			for(CondizionePagamento condizione: pend.getCondizioni()) {
				EnumStatoPagamentoCondizione statoPagamentoCalcolato =  PaymentConditionStatusCalculator.calculateStatus(condizione);
				CondizioneDTO condizioneDTO = AnonymousPaymentFacadeDTOBuilder.populateCondizioneDTO(condizione);
				condizioneDTO.setStatoPagamentoCalcolato(statoPagamentoCalcolato);
				resultList.add(condizioneDTO);
			}
        }
		return resultList;
	}
	
	@Override
	public List<CondizioneDTO> ricercaAvvisiByCodPagamento(String codPagamento, String idEnte) throws BusinessConstraintException {
		return ricercaAvvisiByCodPagamento(codPagamento, null, idEnte);
	}
	
	@Override
	public String replaceCondizione (String idCondizione, BigDecimal importo, String noteVersante, String codiceFiscale) {
		return anonymousPaymentBusinessBean.replaceCondizione(idCondizione, importo, noteVersante, codiceFiscale);
	}

	@Override 
	public AnonymousPaymentDTO readAnonymousPaymentByCondizione(String idCondizione) {
		final List<java.util.Map> results = anonymousPaymentBusinessBean.readAnonymousPaymentByCondizione(idCondizione);
		final java.util.Map distintaAndPagamentoByCondizione = results.get(0);
		GestioneFlussi distinta = (GestioneFlussi) distintaAndPagamentoByCondizione.get("distinta");
		final Pagamenti pagamenti = (Pagamenti) distintaAndPagamentoByCondizione.get("pagamento");
		return AnonymousPaymentDTOBuilder.populateAnonymousTributoEnteDTO(distinta, pagamenti);
	}
}
