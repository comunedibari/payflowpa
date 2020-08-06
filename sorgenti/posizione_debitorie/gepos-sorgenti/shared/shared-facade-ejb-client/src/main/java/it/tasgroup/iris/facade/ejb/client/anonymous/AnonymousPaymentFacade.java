package it.tasgroup.iris.facade.ejb.client.anonymous;

import it.tasgroup.addon.api.domain.TributoStrutturato;
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
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.math.BigDecimal;
import java.util.List;


public interface AnonymousPaymentFacade {
	
	public static int VALID_DOC_FOUND =1;
	public static int VALID_PAYMENT_FOUND = 2;
	
    
    public AnonymousEntiDTO readEntiAbleToAnonimousPaymentByIdEnte(String idEnte);

    public AnonymousEntiDTO readEntiAbleToAnonimousPaymentByCdEnte(String cdEnte);
    
    public AnonymousEntiDTO readEnteAbleToAnonimousPaymentByLapl(String lapl);

    public List<AnonymousEntiDTO> readListEntiAbleToAnonymousPayment();
    
    public List<AnonymousEntiDTO> readListEntiAbleToAnonymousPayment(String predeterminatoValue);
    
    public List<AnagraficaCorsiDottoratoDTO> readListAnagraficaCorsiDottorato();
    
    public List<NazioneCittadinanzaDTO> readListNazioneCittadinanza();

    public List<AnonymousTributoEnteDTO> readListTributiAbleToAnonymousPaymentByIdEnte(String idente);
    
    public List<AnonymousTributoEnteDTO> readListTributiAbleToAnonymousPayment();

    public AnonymousTributoEnteDTO readTributiAbleToAnonymousPaymentByIdEnteAndTipo(String idente, String tipoTributo);

    public AnonymousPaymentDTO readAnonymousPaymentByToken(String token);

    public List<AnonymousPaymentConditionDTO> readListAnonymousPaymentConditionByIdDistinta(Long idDistinta);

    public SessionShoppingCartItemDTO createNewPendenza( TributoStrutturato tributo);
    
    public SessionShoppingCartItemDTO createNewPendenza(TributoStrutturato tributo, AnonymousAnagraficaDTO anagrafica);

    public <T extends TributoStrutturato> List<T> searchPendenza(SearchTributiRequest<T> searchTributiRequest);

    public UserData loadUserData(String sessionId);

    public <T extends TributoStrutturato> List<CondizioneDTO> getCondizioniTributo(T tributo); /* eventualmente specificare un List<DTO> come return type */
    
    public <T extends TributoStrutturato> List<CondizioneDTO> getCondizioniTributoAggiornato(T tributo);
    
    public AnonymousPendenzaDTO getPendenzaByTributo(TributoStrutturato tributo, String idCondizione);
    
    public AnonymousPendenzaDTO getPendenzaAroundCondizione(String idPendenza, String idCondizione);
      
    public <T extends TributoStrutturato> List<AnonymousPaymentDTOLight> getExistingPayments(T tributo, String cdTrbEnte);

    public CondizioneDTO getCondizioneByIUV(String iuv, String idEnte, String cdTrbEnte, String codiceFiscale);
    
    public AnonymousTributoDTO readCategoriaTributoById(String idTributo);
    
    public List<AnonymousEntiDTO> readEntiAbleForTributo(String idTributo);

	public CondizioneDTO getCondizioneByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException;

	public <T extends TributoStrutturato> List<T> searchPendenzaHidden(SearchTributiRequest<T> searchTributiRequest);

	public int checkOperazioniInCorsoEffettuatoDocValido(TributoStrutturato tributo);
	
	public void annullaPagamentoNDP(String idCondizione);
	
	public SessionShoppingCartItemDTO createNewPendenza(CondizioneDTO condizione, AnonymousAnagraficaDTO anagrafica);

	public List<AnonymousEntiDTO> readEntiAbleForCdTrbEnte(String cdTrbEnte);
	
	public byte[] getDatiCfgTributoPlugin(String idEnte, String cdTributo);

	public List<AnonymousBachecaNewsDTO> readAnonymousBachecaNewsDTO();
    
	public AnonymousBachecaNewsDTO readAnonymousBachecaNewsDTO(Long id);

	public List<CondizioneDTO> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException;
	
	public String replaceCondizione(String idCondizione, BigDecimal importo, String noteVersante, String codiceFiscale);

	CondizioneDTO getCondizioneByIUV(String iuv, String codiceFiscale) throws BusinessConstraintException;

	List<CondizioneDTO> ricercaAvvisiByCodPagamento(String codPagamento, String idEnte) throws BusinessConstraintException;

	public AnonymousPaymentDTO readAnonymousPaymentByCondizione(String idCondizione);
}
