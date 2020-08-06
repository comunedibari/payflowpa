package it.tasgroup.iris.business.ejb.client.pendenze;

import it.nch.profile.IProfileManager;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.backoffice.ente.form.CondizioniPagamentoForm;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.PrenotazioneAvvisiDigitali;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;

import java.util.List;


public interface PendenzeBusiness {
	
	
	public List<Pendenza> ricercaAvvisiDebitore(IProfileManager profilo, ContainerDTO dto);
	
	public List<Pendenza> ricercaAvvisiCreditore(IProfileManager profilo, ContainerDTO dto);
	
	public List<Pendenza> ricercaAvvisiAmministrazione(IProfileManager profilo, ContainerDTO dto);
	
	public List<Pendenza> listaAvvisiDebitore(IProfileManager profilo, ContainerDTO dto);
	
	public List<Pendenza> listaAvvisiCreditore(IProfileManager profilo, ContainerDTO dto);
	
	public List<Pendenza> listaAvvisiAmministrazione(IProfileManager profilo, ContainerDTO dto);

	public List<Pendenza> switchRicercaAvvisi(String method, IProfileManager profilo,ContainerDTO dto);
	
	public Pendenza getPendenzaById(String idPendenza);
	
	public Pendenza getPendenzaByChiaveSemantica(String cdTrbEnte, String idEnte, String idPendenzaEnte);
	
	public List<AllegatiPendenza> listaAllegatiPendenza(String idPendenza);
	
	public List<AllegatiPendenza> listaAllegatiCondizionePagamento(String idPendenza, String idCondizione);
	
	public TributoStrutturato getTributoStrutturatoByIdPendenza(String idPendenza);
	
	public AllegatiPendenza  getAllegatoPendenzaById(String idAllegato);

	public List<Pendenza> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException;
	
	public List<PrenotazioneAvvisiDigitali> getPrenotazioneAvvisiDigitaliByCondizione(String idCondizione); 
	
	public void resettaPrenotaAvvisiDigitali(Long id, String tipo);

	List<Pendenza> listaAvvisiDebitoreStorico(IProfileManager profilo, ContainerDTO dto);

	List<Pendenza> listaAvvisiCreditoreStorico(IProfileManager profilo, ContainerDTO dto);

	List<Pendenza> listaAvvisiAmministrazioneStorico(IProfileManager profilo, ContainerDTO dto);

	List<Pendenza> ricercaAvvisiAmministrazioneStorico(IProfileManager profilo, ContainerDTO dto);

	List<Pendenza> ricercaAvvisiDebitoreStorico(IProfileManager profilo, ContainerDTO dto);

	List<Pendenza> ricercaAvvisiCreditoreStorico(IProfileManager profilo, ContainerDTO dto);

	List<Pendenza> switchRicercaAvvisiStorico(String method, IProfileManager profilo, ContainerDTO dto);

	Pendenza getPendenzaByIdStorico(String idPendenza);

	List<AllegatiPendenza> listaAllegatiPendenzaStorico(String idPendenza);

	List<AllegatiPendenza> listaAllegatiCondizionePagamentoStorico(String idPendenza, String idCondizione);

	List<PrenotazioneAvvisiDigitali> getPrenotazioneAvvisiDigitaliByCondizioneStorico(String idCondizione);

	TributoStrutturato getTributoStrutturatoByIdPendenzaStorico(String idPendenza);
	
	CondizionePagamento getCondizioniByCfDebitoreAndIuv(String cfCreditore, String IUV);
}
