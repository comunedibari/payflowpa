package it.tasgroup.iris.business.ejb.pendenze;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.CommonConstant;
import it.nch.profile.IProfileManager;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.business.ejb.client.pendenze.PendenzeBusinessLocal;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.PrenotazioneAvvisiDigitali;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.persistence.dao.interfaces.AllegatiPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.PrenotazioneAvvisiDigitaliDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoAllegatiPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoPrenotazioneAvvisiDigitaliDao;

@Stateless(name = "PendenzeBusiness")
public class PendenzeBusinessBean implements PendenzeBusinessLocal {
	
	private static final Logger LOGGER = LogManager.getLogger(PendenzeBusinessBean.class);
	
	@EJB(name = "PendenzaDaoService")
	private PendenzaDao pendenzaDao;
	
	@EJB(name = "StoricoPendenzaDaoService")
	private StoricoPendenzaDao storicoPendenzaDao;
	
	@EJB(name = "EntiDaoService")
	private EntiDao entiDao;
	
	@EJB(name = "AllegatiDaoService")
	private AllegatiPendenzaDao allegatiDao;
	
	@EJB(name = "StoricoAllegatiDaoService")
	private StoricoAllegatiPendenzaDao storicoAllegatiDao;
	
	@EJB(name = "PrenotazioneAvvisiDigitaliDao") 
	private PrenotazioneAvvisiDigitaliDao prenotazioneAvvisiDigitaliDao; 
	
	@EJB(name = "StoricoPrenotazioneAvvisiDigitaliDao") 
	private StoricoPrenotazioneAvvisiDigitaliDao storicoPrenotazioneAvvisiDigitaliDao; 
	
	@EJB(name = "CondizioniPagamentoDaoService")
	private CondizioniPagamentoDao condizioniDao;
	
	
	@Override
	public List<Pendenza> switchRicercaAvvisi(String method, IProfileManager profilo, ContainerDTO dto){
		
		if (CommonConstant.LISTA_AVVISI_POSDEB.equals(method))
			return listaAvvisiDebitore(profilo, dto);

		if (CommonConstant.LISTA_AVVISI_POSCRED.equals(method))
			return listaAvvisiCreditore(profilo, dto);

		if (CommonConstant.LISTA_AVVISI_AMMINISTRAZIONE.equals(method))
			return listaAvvisiAmministrazione(profilo, dto);
		
		if (CommonConstant.RICERCA_AVVISI_AMMINISTRAZIONE.equals(method))
			return ricercaAvvisiAmministrazione(profilo, dto);

		if (CommonConstant.RICERCA_AVVISI_POSDEB.equals(method))
			return ricercaAvvisiDebitore(profilo, dto);

		if (CommonConstant.RICERCA_AVVISI_POSCRED.equals(method))
			return ricercaAvvisiCreditore(profilo, dto);
		
		else {
			Tracer.debug(getClass().getName(), " PendenzeFacadeBean >>> " + method, "Attenzione: il metodo " + method + " non e' stato trovato");
			
			throw new RuntimeException("Il metodo " + method + " non e' stato trovato");
		}
	}
	
	@Override
	public List<Pendenza> switchRicercaAvvisiStorico(String method, IProfileManager profilo, ContainerDTO dto){
		
		if (CommonConstant.LISTA_AVVISI_POSDEB.equals(method))
			return listaAvvisiDebitoreStorico(profilo, dto);

		if (CommonConstant.LISTA_AVVISI_POSCRED.equals(method))
			return listaAvvisiCreditoreStorico(profilo, dto);

		if (CommonConstant.LISTA_AVVISI_AMMINISTRAZIONE.equals(method))
			return listaAvvisiAmministrazioneStorico(profilo, dto);
		
		if (CommonConstant.RICERCA_AVVISI_AMMINISTRAZIONE.equals(method))
			return ricercaAvvisiAmministrazioneStorico(profilo, dto);

		if (CommonConstant.RICERCA_AVVISI_POSDEB.equals(method))
			return ricercaAvvisiDebitoreStorico(profilo, dto);

		if (CommonConstant.RICERCA_AVVISI_POSCRED.equals(method))
			return ricercaAvvisiCreditoreStorico(profilo, dto);
		
		else {
			Tracer.debug(getClass().getName(), " PendenzeFacadeBean >>> " + method, "Attenzione: il metodo " + method + " non e' stato trovato");
			
			throw new RuntimeException("Il metodo " + method + " non e' stato trovato");
		}
	}
	
	@Override
	public List<Pendenza> ricercaAvvisiDebitore(IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisi(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_DEBITORIA);
	}
	
	@Override
	public List<Pendenza> ricercaAvvisiDebitoreStorico(IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisiStorico(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_DEBITORIA);
	}

	@Override
	public List<Pendenza> ricercaAvvisiCreditore(IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisi(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_CREDITORIA);
	}
	
	@Override
	public List<Pendenza> ricercaAvvisiCreditoreStorico(IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisiStorico(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_CREDITORIA);
	}

	@Override
	public List<Pendenza> ricercaAvvisiAmministrazione(IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisi(profilo, dto, PosizioneDebitoriaConstants.MODALITA_AMMINISTRAZIONE);
	}
	
	@Override
	public List<Pendenza> ricercaAvvisiAmministrazioneStorico(IProfileManager profilo, ContainerDTO dto) {
		return ricercaAvvisiStorico(profilo, dto, PosizioneDebitoriaConstants.MODALITA_AMMINISTRAZIONE);
	}
	
	private List<Pendenza> ricercaAvvisi(IProfileManager profilo, ContainerDTO dto, String tipoUtente) {
		
		List<Pendenza> list = null;
		
		try {
			
			Tracer.debug(this.getClass().getName(), "ricercaAvvisi", "inizio");
			
			PosizioneDebitoriaInputVO filterParameters = (PosizioneDebitoriaInputVO) dto.getInputDTO();

			PendenzaDao.PosizioneDebitoriaOptions options = new PendenzaDao.PosizioneDebitoriaOptions();
			// ------------------------------------------------------------------------------
			// Sicurezza: se sei operatore ente puoi vedere solo le posizioni del tuo ente
			// ------------------------------------------------------------------------------			
			if (PosizioneDebitoriaConstants.OPERATORE_ENTE.equals(profilo.getCategoria())) {
				
				String idEnte= entiDao.getIdEnteFromIntestatario(profilo.getAzienda());
				filterParameters.setFiltroEnte(idEnte);
			}

			// ------------------------------------------------------------------------------
			// Sicurezza: se sei cittadino puoi vedere solo le posizioni tue
			// ------------------------------------------------------------------------------			
			
			if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equals(profilo.getCategoria())) {
				filterParameters.setDestinatario(profilo.getLapl());
				options.escludiTributiNascosti=true;
			}

			if (dto.getPagingData() == null)
				dto.setPagingData(new PagingData());
			
			list = pendenzaDao.listPendenzeByFilterParameters(filterParameters,dto.getPagingData(), dto.getPagingCriteria(),options);
	        

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "errore ricercaAvvisi", e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		
		Tracer.debug(this.getClass().getName(), "ricercaAvvisi", "fine");
		
		return list;
	}
	
	private List<Pendenza> ricercaAvvisiStorico(IProfileManager profilo, ContainerDTO dto, String tipoUtente) {
		
		List<Pendenza> list = null;
		
		try {
			
			Tracer.debug(this.getClass().getName(), "ricercaAvvisi", "inizio");
			
			PosizioneDebitoriaInputVO filterParameters = (PosizioneDebitoriaInputVO) dto.getInputDTO();

			PendenzaDao.PosizioneDebitoriaOptions options = new PendenzaDao.PosizioneDebitoriaOptions();
			// ------------------------------------------------------------------------------
			// Sicurezza: se sei operatore ente puoi vedere solo le posizioni del tuo ente
			// ------------------------------------------------------------------------------			
			if (PosizioneDebitoriaConstants.OPERATORE_ENTE.equals(profilo.getCategoria())) {
				
				String idEnte= entiDao.getIdEnteFromIntestatario(profilo.getAzienda());
				filterParameters.setFiltroEnte(idEnte);
			}

			// ------------------------------------------------------------------------------
			// Sicurezza: se sei cittadino puoi vedere solo le posizioni tue
			// ------------------------------------------------------------------------------			
			
			if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equals(profilo.getCategoria())) {
				filterParameters.setDestinatario(profilo.getLapl());
				options.escludiTributiNascosti=true;
			}

			if (dto.getPagingData() == null)
				dto.setPagingData(new PagingData());
			
			list = storicoPendenzaDao.listPendenzeByFilterParameters(filterParameters,dto.getPagingData(), dto.getPagingCriteria(),options);
	        

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "errore ricercaAvvisi", e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		
		Tracer.debug(this.getClass().getName(), "ricercaAvvisi", "fine");
		
		return list;
	}

	@Override
	public List<Pendenza> listaAvvisiDebitore(IProfileManager profilo, ContainerDTO dto) {
		return listaAvvisi(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_DEBITORIA);
	}
	
	@Override
	public List<Pendenza> listaAvvisiDebitoreStorico(IProfileManager profilo, ContainerDTO dto) {
		return listaAvvisiStorico(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_DEBITORIA);
	}
	
	@Override
	public List<Pendenza> listaAvvisiCreditore(IProfileManager profilo, ContainerDTO dto) {
		return listaAvvisi(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_CREDITORIA);
	}
	
	@Override
	public List<Pendenza> listaAvvisiCreditoreStorico(IProfileManager profilo, ContainerDTO dto) {
		return listaAvvisiStorico(profilo, dto, PosizioneDebitoriaConstants.MODALITA_POSIZIONE_CREDITORIA);
	}

	@Override
	public List<Pendenza> listaAvvisiAmministrazione(IProfileManager profilo, ContainerDTO dto) {
		return listaAvvisi(profilo, dto, PosizioneDebitoriaConstants.MODALITA_AMMINISTRAZIONE);
	}
	
	@Override
	public List<Pendenza> listaAvvisiAmministrazioneStorico(IProfileManager profilo, ContainerDTO dto) {
		return listaAvvisiStorico(profilo, dto, PosizioneDebitoriaConstants.MODALITA_AMMINISTRAZIONE);
	}
	@Override
	public List<Pendenza> ricercaAvvisiByCodPagamento(String codPagamento, String codiceFiscale, String idEnte) throws BusinessConstraintException {
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

	private List<Pendenza> listaAvvisi(IProfileManager profilo, ContainerDTO dto, String tipoUtente) {
		
		List<Pendenza> list = null;
		
		try {
			Tracer.debug(this.getClass().getName(), "listaAvvisi" + tipoUtente, "inizio");

			PosizioneDebitoriaInputVO filterParameters = (PosizioneDebitoriaInputVO) dto.getInputDTO();
			
			// Filtri particolari
			if (StringUtils.isNotBlank(filterParameters.getFiltroEnteTributo()) ) {
				String [] enteTributo = filterParameters.getFiltroEnteTributo().split("[|]");
				filterParameters.setFiltroEnte(enteTributo[0]);
				filterParameters.setTributo(enteTributo[1]);
				
			}
			
			PendenzaDao.PosizioneDebitoriaOptions options= new PendenzaDao.PosizioneDebitoriaOptions();
			// ------------------------------------------------------------------------------
			// Sicurezza: se sei operatore ente puoi vedere solo le posizioni del tuo ente
			// ------------------------------------------------------------------------------			
			if (PosizioneDebitoriaConstants.OPERATORE_ENTE.equals(profilo.getCategoria())) {
				
				String idEnte= entiDao.getIdEnteFromIntestatario(profilo.getAzienda());
				filterParameters.setFiltroEnte(idEnte);
			}

			// ------------------------------------------------------------------------------
			// Sicurezza: se sei cittadino puoi vedere solo le posizioni tue
			// ------------------------------------------------------------------------------			
			
			if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equals(profilo.getCategoria())) {
				filterParameters.setDestinatario(profilo.getLapl());
				options.escludiTributiNascosti=true;
			}
					
			if (dto.getPagingData()==null)
				dto.setPagingData(new PagingData());
			
			
			list = pendenzaDao.listPendenzeByFilterParameters(filterParameters,dto.getPagingData(), dto.getPagingCriteria(),options);
	        
			Tracer.debug(getClass().getName(), "", "AVVISI SIZE " + list.size());
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listaAvvisi" + tipoUtente, e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		
		Tracer.debug(this.getClass().getName(), "listaAvvisi" + tipoUtente, "fine");
		
		return list;
	}
	
	private List<Pendenza> listaAvvisiStorico(IProfileManager profilo, ContainerDTO dto, String tipoUtente) {
		
		List<Pendenza> list = null;
		
		try {
			Tracer.debug(this.getClass().getName(), "listaAvvisi" + tipoUtente, "inizio");

			PosizioneDebitoriaInputVO filterParameters = (PosizioneDebitoriaInputVO) dto.getInputDTO();
			
			// Filtri particolari
			if (StringUtils.isNotBlank(filterParameters.getFiltroEnteTributo()) ) {
				String [] enteTributo = filterParameters.getFiltroEnteTributo().split("[|]");
				filterParameters.setFiltroEnte(enteTributo[0]);
				filterParameters.setTributo(enteTributo[1]);
				
			}
			
			PendenzaDao.PosizioneDebitoriaOptions options= new PendenzaDao.PosizioneDebitoriaOptions();
			// ------------------------------------------------------------------------------
			// Sicurezza: se sei operatore ente puoi vedere solo le posizioni del tuo ente
			// ------------------------------------------------------------------------------			
			if (PosizioneDebitoriaConstants.OPERATORE_ENTE.equals(profilo.getCategoria())) {
				
				String idEnte= entiDao.getIdEnteFromIntestatario(profilo.getAzienda());
				filterParameters.setFiltroEnte(idEnte);
			}

			// ------------------------------------------------------------------------------
			// Sicurezza: se sei cittadino puoi vedere solo le posizioni tue
			// ------------------------------------------------------------------------------			
			
			if (PosizioneDebitoriaConstants.OPERATORE_CITTADINO.equals(profilo.getCategoria())) {
				filterParameters.setDestinatario(profilo.getLapl());
				options.escludiTributiNascosti=true;
			}
					
			if (dto.getPagingData()==null)
				dto.setPagingData(new PagingData());
			
			
			list = pendenzaDao.listPendenzeByFilterParameters(filterParameters,dto.getPagingData(), dto.getPagingCriteria(),options);
	        
			Tracer.debug(getClass().getName(), "", "AVVISI SIZE " + list.size());
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listaAvvisi" + tipoUtente, e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		
		Tracer.debug(this.getClass().getName(), "listaAvvisi" + tipoUtente, "fine");
		
		return list;
	}

	@Override
	public Pendenza getPendenzaById(String idPendenza) {
		
		Pendenza p = null;
		
		try  {
			p = pendenzaDao.getById(Pendenza.class, idPendenza);
		
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getPendenzaById" , e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
	
		return p;
		
	}

	@Override
	public Pendenza getPendenzaByIdStorico(String idPendenza) {
		
		Pendenza p = null;
		
		try  {
			p = storicoPendenzaDao.getById(Pendenza.class, idPendenza);
		
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getPendenzaByIdStorico" , e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
	
		return p;
		
	}

	@Override
	public List<AllegatiPendenza> listaAllegatiPendenza(String idPendenza) {
		return allegatiDao.getAllegatiPendenza(idPendenza);
	}

	@Override
	public List<AllegatiPendenza> listaAllegatiPendenzaStorico(String idPendenza) {
		return storicoAllegatiDao.getAllegatiPendenza(idPendenza);
	}


	@Override
	public List<AllegatiPendenza> listaAllegatiCondizionePagamento(String idPendenza, String idCondizione) {
		return allegatiDao.getAllAllegatiCondizione(idPendenza, idCondizione);
	}

	@Override
	public List<AllegatiPendenza> listaAllegatiCondizionePagamentoStorico(String idPendenza, String idCondizione) {
		return storicoAllegatiDao.getAllAllegatiCondizione(idPendenza, idCondizione);
	}

	@Override
	public TributoStrutturato getTributoStrutturatoByIdPendenza(
			String idPendenza) {
		return pendenzaDao.getTributoStrutturatoByIdPendenza(idPendenza);
	}
	
	@Override
	public TributoStrutturato getTributoStrutturatoByIdPendenzaStorico(
			String idPendenza) {
		return storicoPendenzaDao.getTributoStrutturatoByIdPendenza(idPendenza);
	}

	@Override
	public AllegatiPendenza getAllegatoPendenzaById(String idAllegato) {
		return allegatiDao.retrieveById(idAllegato);
	}

	@Override
	public Pendenza getPendenzaByChiaveSemantica(String cdTrbEnte,
			String idEnte, String idPendenzaEnte) {
		return pendenzaDao.getPendenzaByChiaveSemantica(cdTrbEnte, idEnte, idPendenzaEnte);
	}	


	@Override 
	public List<PrenotazioneAvvisiDigitali> getPrenotazioneAvvisiDigitaliByCondizione(String idCondizione) { 
		return prenotazioneAvvisiDigitaliDao.getPrenotazioneAvvisiDigitaliByCondizione(idCondizione); 
	} 
	
	@Override 
	public List<PrenotazioneAvvisiDigitali> getPrenotazioneAvvisiDigitaliByCondizioneStorico(String idCondizione) { 
		return storicoPrenotazioneAvvisiDigitaliDao.getPrenotazioneAvvisiDigitaliByCondizione(idCondizione); 
	} 

	@Override 
	public void resettaPrenotaAvvisiDigitali(Long id, String tipo) { 
		prenotazioneAvvisiDigitaliDao.resettaPrenotaAvvisiDigitali(id, tipo);            
	} 
	
	@Override
	public CondizionePagamento getCondizioniByCfDebitoreAndIuv(String cfCreditore, String IUV){
		return condizioniDao.getCondizioneByCFCreditoreIdPagamento(cfCreditore, IUV);		
	}
}
