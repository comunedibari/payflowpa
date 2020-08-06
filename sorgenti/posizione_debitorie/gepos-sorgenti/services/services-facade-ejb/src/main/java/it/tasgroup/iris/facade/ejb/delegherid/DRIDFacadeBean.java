package it.tasgroup.iris.facade.ejb.delegherid;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.CommonConstant;
import it.nch.profile.IProfileManager;
import it.nch.utility.iban.IbanHelper;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.delegherid.DelegheRidBusinessLocal;
import it.tasgroup.iris.business.ejb.client.delegherid.DisposizioniRidBusinessLocal;
import it.tasgroup.iris.domain.AllineamentiElettroniciArchivi;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.PagamentoRidDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.iris.facade.ejb.client.delegherid.DRIDFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.delegherid.DRIDFacadeRemote;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DRIDDTOBuilder;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "DRIDFacade")
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DRIDFacadeBean implements DRIDFacadeLocal,  DRIDFacadeRemote{
	
	private static final Logger LOGGER = LogManager.getLogger(DRIDFacadeBean.class);	
	
	@EJB(name="DelegheRidBusiness")
	private DelegheRidBusinessLocal delegheRidBusinessBean;
	
	@EJB(name="DisposizioniRidBusiness")
	private DisposizioniRidBusinessLocal disposizioniRidBusinessBean;
	
	@EJB
	private ConfPagamentiBusinessLocal confPagamentoBusinessBean;
	
	@EJB
	private IrisCacheSingletonLocal irisCache;
	
//	@EJB
//	private IntestatariBusinessLocal intestatariBusinessBean;
	
	@Override
	public ContainerDTO readRichiestaDelegheListByProfile(IProfileManager profile,
			ContainerDTO containerDTO)
			throws Exception {
		
		Tracer.debug(getClass().getName(), "readRichiestaDelegheListByProfile", "start...");
		AllineamentiElettroniciArchiviDTO delega = (AllineamentiElettroniciArchiviDTO) containerDTO.getInputDTO();
		
		initAEAByProfilo(profile, delega);
		


		List<AllineamentiElettroniciArchivi> aeaListEntityOut = delegheRidBusinessBean.readRichiestaDelegheByProfile(containerDTO);
		List<AllineamentiElettroniciArchiviDTO> retList = DRIDDTOBuilder.populateAEADTOList(aeaListEntityOut);
		
		containerDTO.setOutputDTOList(retList);
		
		
		Tracer.debug(getClass().getName(), "readRichiestaDelegheListByProfile", "end");
		
		return containerDTO;
	}

	@Override
	public AllineamentiElettroniciArchiviDTO verificaIbanDuplicato(
			IProfileManager profile, DTO dtoInput) throws Exception {
		Tracer.debug(getClass().getName(), "readDelegaByIban", "start...");
		AllineamentiElettroniciArchiviDTO delega = (AllineamentiElettroniciArchiviDTO)dtoInput.getVO();
		
		initAEAByProfilo(profile, delega);
		
		AllineamentiElettroniciArchivi aea = delegheRidBusinessBean.verificaIbanDuplicato(delega.getIbanAddebito(),delega.getCodificaFiscaleSottoscrittore());
		AllineamentiElettroniciArchiviDTO ret = DRIDDTOBuilder.populateAEADTO(aea);
		Tracer.debug(getClass().getName(), "readDelegaByIban", "end");
		return ret;
	}
	@Override
	public AllineamentiElettroniciArchiviDTO verificaAbiDuplicato(
			IProfileManager profile, DTO dtoInput) throws Exception {
		Tracer.debug(getClass().getName(), "readDelegaByIban", "start...");
		AllineamentiElettroniciArchiviDTO delega = (AllineamentiElettroniciArchiviDTO)dtoInput.getVO();
		
		initAEAByProfilo(profile, delega);
		
		AllineamentiElettroniciArchivi aea = delegheRidBusinessBean.verificaAbiDuplicato(delega.getAbiAddebito(),delega.getCodificaFiscaleSottoscrittore());
		AllineamentiElettroniciArchiviDTO ret = DRIDDTOBuilder.populateAEADTO(aea);
		Tracer.debug(getClass().getName(), "readDelegaByIban", "end");
		return ret;
	}
	@Override
	public AllineamentiElettroniciArchiviDTO readDelegaById(
			IProfileManager profile, DTO dtoInput) throws Exception {
		Tracer.debug(getClass().getName(), "readDelegaById", "start...");
		String id = (String)dtoInput.getVO();
		AllineamentiElettroniciArchivi aea = delegheRidBusinessBean.readDelegaById(id);
		AllineamentiElettroniciArchiviDTO ret = DRIDDTOBuilder.populateAEADTO(aea);
		Tracer.debug(getClass().getName(), "readDelegaById", "end");
		return ret;
	}

	@Override
	public AllineamentiElettroniciArchiviDTO createDelega(
			IProfileManager profile, DTO inputVO)
			throws Exception {
		
		
		Tracer.debug(getClass().getName(), "createDelega", "start...");
		AllineamentiElettroniciArchiviDTO dto = (AllineamentiElettroniciArchiviDTO) inputVO.getVO();
		initAEAByProfilo(profile, dto);
//		System.out.println("Profile: indirizzo: " + profile.getCap()+" "+profile.getComune()+" "+profile.getProvincia());
//		System.out.println("Profile: RagioneSocialeAzienda: " + profile.getRagioneSocialeAzienda());
//		System.out.println("Profile: CodiceFiscale: " + profile.getCodiceFiscale());
//
//		
//		System.out.println("operatore:" + (intes.getOperatore() != null ? intes.getOperatore().getCodiceFiscale():"-"));
//		System.out.println("corporate: " + (intes.getIntestatario() != null ? intes.getIntestatario().getCorporate():"-"));
//		System.out.println("corporateIForm: " + (intes.getIntestatariobj() != null ? intes.getIntestatariobj().getCorporateIForm():"-"));
//		
//		String intestatario = intes.getOpeId().getIntestatario();
//		String operatore = intes.getOpeId().getOperatore();
//
//		System.out.println("operatore from id:" + operatore);
//		System.out.println("corporate from id: " + intestatario);
//		
//		
//		
//		
//		dto.setIntestatario(intestatario);
		dto.setCittadino(profile.getCategoria().equals("CI"));
		
//		//TODO: nome e cognome non trovati
//		dto.setIndirizzoSottoscrittore();
//		dto.setCodificaFiscaleSottoscrittore(profile.getRagioneSocialeAzienda());
		
		dto.setCodificaFiscaleCreditore(CommonConstant.RT_PARTITA_IVA);
		dto.setRagSocCreditore(CommonConstant.RT_RAG_SOCIALE);
		dto.setSiaCreditore(CommonConstant.RT_SIA);
		
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		IbanHelper ibanHelper = new IbanHelper(contoTecnico.getIban());
		dto.setAbiBancaAllineamento(ibanHelper.getAbi());
		
		dto.setProgressivo(new Integer(0));
		
		AllineamentiElettroniciArchivi aea = delegheRidBusinessBean.createRichiestaDelega(profile, dto);
		AllineamentiElettroniciArchiviDTO ret = DRIDDTOBuilder.populateAEADTO(aea);
		Tracer.debug(getClass().getName(), "createDelega", "end");
		return ret;
	}

	@Override
	public AllineamentiElettroniciArchiviDTO revocaDelega(
			IProfileManager profile, DTO inputVO)
			throws Exception {
		Tracer.debug(getClass().getName(), "revocaDelega", "start ...");
		
		AllineamentiElettroniciArchiviDTO dto = (AllineamentiElettroniciArchiviDTO) inputVO.getVO();
		initAEAByProfilo(profile, dto);
		Long idDelega = new Long(dto.getIdReq());
		
//		dto.setIntestatario(intes.getOpeId().getIntestatario());
		dto.setId(idDelega);
		AllineamentiElettroniciArchivi aea = delegheRidBusinessBean.revocaDelega(profile, dto);
		AllineamentiElettroniciArchiviDTO ret = DRIDDTOBuilder.populateAEADTO(aea);
		Tracer.debug(getClass().getName(), "revocaDelega", "end");
		return ret;
	}

	@Override
	public List<AllineamentiElettroniciArchiviDTO> readDelegheAccettateListByProfile(
			IProfileManager profile, DTO inputVO)
			throws Exception {
		Tracer.debug(getClass().getName(), "readDelegheAccettateListByProfile", "start ...");
		AllineamentiElettroniciArchiviDTO dto = (AllineamentiElettroniciArchiviDTO) inputVO.getVO();
		initAEAByProfilo(profile, dto);
//		
//
//		List<AllineamentiElettroniciArchivi> list = delegheRidBusinessBean.readDelegheAccettateByProfile(profile.getCodiceFiscale(), intes.getOpeId().getIntestatario());
		List<AllineamentiElettroniciArchivi> list = delegheRidBusinessBean.readDelegheAccettateByProfile(dto.getCodificaFiscaleSottoscrittore(), null);
		
		List<AllineamentiElettroniciArchiviDTO> retList = DRIDDTOBuilder.populateAEADTOList(list);
		
		String sortingField = dto.getSortingField() != null ? dto.getSortingField() : "ID";
		System.out.println("-------------------sortingField: "+sortingField);
		
		String sortingDir = dto.getSortingDir() != null ?dto.getSortingDir() : "ASC";
		System.out.println("-------------------sortingDir: "+sortingDir);
		
		Tracer.debug(getClass().getName(), "readDelegheAccettateListByProfile", "end");
		return retList;
	}

	@Override
	public DistintePagamentoDTO createDisposizioneRid(IProfileManager profile,
			DTO inputVO) throws Exception {
		PagamentoRidDTO riddto = (PagamentoRidDTO) inputVO.getVO();
		AllineamentiElettroniciArchivi aea = delegheRidBusinessBean.readDelegaById(riddto.getDelega().getIdReq());
		AllineamentiElettroniciArchiviDTO delega = DRIDDTOBuilder.populateAEADTO(aea);
		
		initAEAByProfilo(profile, delega);
		
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		
		GestioneFlussi prid = disposizioniRidBusinessBean.createDisposizioneRid(profile, riddto,delega,contoTecnico);
		DistintePagamentoDTO ret = DRIDDTOBuilder.populateDPDTO(prid);
		Tracer.debug(getClass().getName(), "createDelega", "end");
		return ret;
	}

	@Override
	public DistintePagamentoDTO readDistentePagamentoById(
			IProfileManager profile, DTO inputVO) throws Exception {
		String id = (String) inputVO.getVO();
		GestioneFlussi dp = disposizioniRidBusinessBean.readDistintePagamentoById(id);
		DistintePagamentoDTO dto = DRIDDTOBuilder.populateDPDTO(dp);
		return dto;
	}

	private AllineamentiElettroniciArchiviDTO initAEAByProfilo(IProfileManager profile,AllineamentiElettroniciArchiviDTO delega){

		
		//TODO: VERIFICARE DATI DELEGA RID
		delega.setCodificaFiscaleSottoscrittore(profile.getCodiceFiscale());
		delega.setCodIndividuale(profile.getCodiceFiscale());
		delega.setRagSocSottoscrittore(profile.getRagioneSocialeAzienda()); 
		delega.setIntestatario(profile.getAzienda());  

//		System.out.println("session:  currentOperatore - cf:"+op.getCodiceFiscale());
//		System.out.println("session:  currentOperatore - corp:"+op.getCorporate());
//		System.out.println("session:  currentOperatore - op:"+op.getOperatore());
//		System.out.println("session:  currentOperatore - name:"+op.getName());
//		System.out.println("session:  currentOperatore - num.intest:"+op.getIntestatari().size());
//		
//		String selectedCorporate = (String)request.getSession().getAttribute(OperatorActionConstants.SELECTED_CORPORATE);
//		System.out.println("session: selectedCorporate :"+selectedCorporate);
//		String categoria = (String)request.getSession().getAttribute("CATEGORIA");
//		System.out.println("session: categoria :"+categoria);
		return delega;
	}

	@Override
	public AllineamentiElettroniciArchiviDTO initDelegaByProfile(
			IProfileManager profile) throws Exception {
		AllineamentiElettroniciArchiviDTO delega = new AllineamentiElettroniciArchiviDTO();
		return initAEAByProfilo(profile, delega);
	}
	

}
