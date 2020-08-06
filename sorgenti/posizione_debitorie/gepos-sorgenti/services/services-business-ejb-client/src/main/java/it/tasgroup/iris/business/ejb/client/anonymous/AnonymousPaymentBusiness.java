package it.tasgroup.iris.business.ejb.client.anonymous;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.domain.AnagraficaCorsiDottorato;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.NazioneCittadinanza;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.dto.SearchTributiRequest;
import it.tasgroup.iris.dto.anonymous.UserData;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousAnagraficaDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentDTOLight;
import it.tasgroup.iris.dto.anonymous.payment.CondizioneDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;


public interface AnonymousPaymentBusiness {

    public Enti getEntiAbleToAnonymousPaymentByIdEnte(String idEnte);
    
    public Enti getEntiAbleToAnonymousPaymentByCdEnte(String cdEnte);
    
    public Enti getEntiAbleToAnonymousPaymentByLapl(String lapl);

    public List<Enti> getEntiAbleToAnonymousPayment();
    
    public List<Enti> getEntiAbleToAnonymousPayment(String predeterminatoValue);
    
    public List<AnagraficaCorsiDottorato> getAnagraficaCorsiDottorato();
    
    public List<NazioneCittadinanza> getNazioneCittadinanza();

    public List<TributoEnte> getTributiAbleToAnonymousPaymentByIdEnte(String idEnte);
    
    public List<TributoEnte> getTributiAbleToAnonymousPayment();

    public GestioneFlussi getAnonymousPaymentByToken(String token);

    public List<Tuple> getAnonymousPaymentConditionByIdDistinta(Long idDistinta);

    public Set<CondizionePagamento> createNewPendenza(TributoStrutturato tributo);

    public <T extends TributoStrutturato> List<T> findTributo(SearchTributiRequest<T> searchTributiRequest);

    public <T extends TributoStrutturato> List<T> findTributoHidden(SearchTributiRequest<T> searchTributiRequest);

    public UserData loadUserData(String sessionId);

    public Pendenza getPendenzaById(TributoStrutturato tributo); /* eventualmente specificare un List<DTO> come return type */

    public Pendenza getPendenzaById(String idPendenza);

    public Pendenza getIdPendenzaAttiva(String cdTrbEnte, String idEnte, String idPendenzaEnte);

    public Pendenza getIdPendenzaAttiva(TributoStrutturato tributo);

    public <T extends TributoStrutturato> List<AnonymousPaymentDTOLight> getExistingPayments(T tributo, String cdTrbEnte);

    public CondizionePagamento getCondizioneByIUV(String iuv, String idEnte, String cdTrbEnte, String codiceFiscale);

    public List<String> listaDebitoriByIdPendenza(String idPendenza);

    public CategoriaTributo getCategoriaTributo(String idTributo);

    public List<Enti> getEntiAbleForTributo(String idTributo);
    
    public List<Pendenza> getCondizioniByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException;

    public CondizionePagamento getCondizioneByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException;

    public Set<CondizionePagamento> createNewPendenza(CondizioneDTO condizione, AnonymousAnagraficaDTO anagrafica);

    public List<Enti> getEntiAbleForCdTrbEnte(String cdTrbEnte);

    public CondizionePagamento getCondizioneByEnteIUV(String iuv, String idEnte, String codiceFiscale);

    List<CondizionePagamento> getCondizioniByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException;

    List<CondizionePagamento> getCondizioniByIdsWithStatoCalcolato(List<String> ids);

    CondizionePagamento getCondizioneQRCode(String codFiscaleCreditore, String idPagamentoEnte, BigDecimal importo);
    
    public byte[] getDatiCfgTributoPlugin(String idEnte, String cdTributo);

	List<Pendenza> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException;
	
	String replaceCondizione(String idCondizione, BigDecimal importo, String noteVersante, String codiceFiscale);

	CondizionePagamento getCondizioneByIUV(String iuv, String codiceFiscale) throws BusinessConstraintException;

	Set<CondizionePagamento> createNewPendenza(TributoStrutturato tributo, AnonymousAnagraficaDTO anagrafica);

	List<Map> readAnonymousPaymentByCondizione(String idCondizione);
}
