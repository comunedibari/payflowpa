package it.tasgroup.iris.business.ejb.anagrafica;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.enti.TipologiaEnti;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IbanEnti;
import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.sistemienti.SistemaEnteId;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.business.ejb.client.anagrafica.TributoEnteBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.TributoEnteBusinessRemote;
import it.tasgroup.iris.domain.CfgNotificaPagamento;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CategoriaTributoDAO;
import it.tasgroup.iris.persistence.dao.interfaces.CfgNotificaPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IUVSequenceDao;
import it.tasgroup.iris.persistence.dao.interfaces.IbanEntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.SistemaEnteDAO;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoSistemaEnteDAO;
import it.tasgroup.iris.persistence.dao.interfaces.TipologiaEntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.TributoEnteDao;

@Stateless(name = "TributoEnteBusiness")
public class TributoEnteBusinessBean implements TributoEnteBusinessLocal, TributoEnteBusinessRemote {
	
    @EJB(name = "EntiDaoImpl")
    private EntiDao entiDAO;
    
    @EJB(name = "TributoEnteDaoImpl")
    private TributoEnteDao tributoEnteDao;
    
    @EJB(name = "CategoriaTributoDaoImpl")
    private CategoriaTributoDAO categoriaTributoDao;
    
    @EJB(name = "SistemaEnteDaoImpl")
    private SistemaEnteDAO sistemaEnteDao;
    
    @EJB(name = "StoricoSistemaEnteDaoImpl")
    private StoricoSistemaEnteDAO sistemaEnteDaoStorico;

    @EJB(name = "CfgNotificaPagamentoDao")
    private CfgNotificaPagamentoDao cfgNotificaPagamentoDao;
    
    @EJB(name = "TipologiaEntiDaoService")
    private TipologiaEntiDao tipologiaEntiDao;
    
    @EJB(name = "PendenzaDaoService")
	PendenzaDao pendenzaDao;

    @EJB(name = "IUVSequenceDaoImpl")
	IUVSequenceDao iuvSequenceDao;
    
    @EJB(name = "IbanEntiDaoImpl")
	IbanEntiDao ibanEntiDao;
    
	@Override
    public Map<String, String> mapEnti() {
        Map<String, String> l = new HashMap<String, String>();
        List<Enti> enti = null;
        try {
        	
            enti = listaEnti();

            for (Enti ente : enti) {
                l.put(ente.getIdEnte(), ente.getDenominazione());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(getClass().getName(), "mapEnti", e.getMessage());
        }
        return l;
    }

    // Metodi HP BO e Ente - START
    
    @Override
    public String getIdEnteFromIntestario(String intestatario){
    	
        String l = null;
        
        try {
        	
            l = (String) entiDAO.getIdEnteFromIntestatario(intestatario);
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            
            Tracer.error(getClass().getName(), "listaEnti", e.getMessage());
            
        }
        
        return l;
    }
    
    @Override
    public List<TributoEnte> listaTributiEnte(ContainerDTO containerDTO){
    	
    	List<TributoEnte> tributiEnte = tributoEnteDao.getTributiEnte(containerDTO);
    			  	
    	return tributiEnte;
    	
    }

	@Override
	public List<TributoEnte> listaIBANRiversamenti(ContainerDTO containerDTO) {
		
		List<TributoEnte> tributiEnte = tributoEnteDao.listaIBANRiversamenti(containerDTO);
	  	
    	return tributiEnte;
	}

	@Override
	public List<TributoEnte> listaIBANTributiNDP(ContainerDTO containerDTO) {
		
		List<TributoEnte> tributiEnte = tributoEnteDao.getIBANTributiEnteNDP(containerDTO);
	  	
    	return tributiEnte;
	}
	
	@Override
	public List<TributoEnte> listaTributiEntiByIBAN(String iban) {
		
		List<TributoEnte> tributiEnte = tributoEnteDao.getTributiEnteByIBAN(iban);
	  	
    	return tributiEnte;
	}
    
	@Override
    public TributoEnte getTributiEntiByKey(String idEnte,String idTributo) {
		  
		TributoEnte te = tributoEnteDao.getTributiEntiByKey( idEnte, idTributo);
		
		return te;
		
	}
	
	@Override
	public List<TributoEnte> listTributoEnteByFilterParams(ContainerDTO dtoIn){
		
		List<TributoEnte> tributiEnte = tributoEnteDao.listTributoEnteByFilterParams(dtoIn, true);

		return tributiEnte;
	}
	
	@Override
	public List<CategoriaTributo> listCategorieTributoByFilterParams(ContainerDTO dtoIn){
		
		return categoriaTributoDao.listCategoriaTributoByFilterParams(dtoIn);
	}
	
	@Override
	public List<SistemaEnte> listSistemaEnteByFilterParams(ContainerDTO dtoIn){
		
		return sistemaEnteDao.listSistemaEnteByFilterParams(dtoIn);
	}
	
	// TODO PAZZIK: VERIFICA SE SERVE ANCORA
	@Override
	public CfgNotificaPagamento createCfgNotificaPagamento(CfgNotificaPagamento cfg){
		
		return cfgNotificaPagamentoDao.createCfgNotificaPagamento(cfg);
	}
	
	@Override
	public TributoEnte createTributoEnte(TributoEnteDTO trbDTO){
		
		TributoEnte te =tributoEnteDao.createTributo(trbDTO);
		String idEnte = trbDTO.getIdEnte();
		if (idEnte==null && trbDTO.getEnte()!=null) {
			idEnte=trbDTO.getEnte().getId();
		}
		//#3804  inserisco sempre il record sulla SEQ_IUV 
		//if (!(trbDTO.getFlNdpIuvGenerato()==null || trbDTO.getFlNdpIuvGenerato().equals("0"))) {
			   iuvSequenceDao.insertOrEnable(idEnte, trbDTO.getCdTrbEnte());
		//} else {
		//		iuvSequenceDao.disable(idEnte, trbDTO.getCdTrbEnte());
		//}
		controllaEInserisciIban(te);
		
		return te;
	}
	
	@Override
	public SistemaEnte createSistemaEnte(SistemaEnteDTO silDTO) {
		
		SistemaEnte sil = sistemaEnteDao.createSistemaEnte(silDTO);

		return sil;
	}
	
	@Override
	public List<TributoEnte> getTributiBySysIDEnte(SistemaEnteDTO dtoIn){
		
		return tributoEnteDao.getTributiBySysIDEnte(dtoIn);
	}

	@Override
	public void deleteSistemaEnte(SistemaEnteDTO dtoIn) {
		SistemaEnteId key = new SistemaEnteId();
		key.setIdEnte(dtoIn.getIdEnte());
		key.setIdSystem(dtoIn.getIdSystem());
		sistemaEnteDao.deleteSistemaEnteByKey(key);
		
	}

	@Override
	public void changeStatusTributoEnte(List<TributoEnteDTO> inputList) {
		
		tributoEnteDao.changeStatusTributoEnteList(inputList);
		
	}

	@Override
	public void changeStatusCategoriaTributo(List<CategoriaTributoDTO> inputList) {
		
		categoriaTributoDao.changeStatusCategoriaTributoList(inputList);
		
	}
	
	@Override
	public CategoriaTributo createCategoriaTributo(CategoriaTributo cat) {
		
		return categoriaTributoDao.createCategoriaTributo(cat);
		
	}
	
	@Override
	public CategoriaTributo getCategoriaTributoById(String idTributo) {
		
		return categoriaTributoDao.getCategoriaTributoById(idTributo);
		
	}

	@Override
	public CategoriaTributo updateCategoriaTributo(CategoriaTributoDTO catDTO) {
		
		return categoriaTributoDao.updateCategoriaTributo(catDTO);
	}

	@Override
	public void deleteCategorieTributi(List<String> selectedIds) {
		
		categoriaTributoDao.deleteCategorieTributi(selectedIds);
		
	}

	@Override
	public TributoEnte updateTributoEnte(TributoEnteDTO tribDTO) {
		
		TributoEnte res =tributoEnteDao.updateTributo(tribDTO);
		String idEnte = tribDTO.getIdEnte();
		if (idEnte==null && tribDTO.getEnte()!=null) {
			idEnte=tribDTO.getEnte().getId();
		}
		if (!(tribDTO.getFlNdpIuvGenerato()==null || tribDTO.getFlNdpIuvGenerato().equals("0"))) {
		   iuvSequenceDao.insertOrEnable(idEnte, tribDTO.getCdTrbEnte());
		} else {
			iuvSequenceDao.disable(idEnte, tribDTO.getCdTrbEnte());
		}
		controllaEInserisciIban(res);
		
		return res;
		
	}
	
	@Override
	public SistemaEnte updateSistemaEnte(SistemaEnteDTO silDTO) {
		
		return sistemaEnteDao.updateSistemaEnte(silDTO);
	}
	
	@Override
	public List<CategoriaTributo> listCategorieTributiEnte(ContainerDTO dtoIn, String idEnte){
		
		return categoriaTributoDao.listCategorieTributiEnte(dtoIn, idEnte);
	}

	@Override
	public List<Enti> listaEnti() {
		
		return entiDAO.listaEnti();
	}

	@Override
	public List<TributoEnte> listaTributiEnti() {
		
		return tributoEnteDao.listaTributiEnti();
	}

	@Override
	public List<TipologiaEnti> getListaTipologiaEnti() {
		return tipologiaEntiDao.getListaTipologiaEnti();
	}

	@Override
	public void deleteTributiEnte(List<String> selectedIds) {
		
		tributoEnteDao.deleteTributiEnte(selectedIds);
		iuvSequenceDao.delete(selectedIds);
	}

	@Override
	public List<TributoEnte> getAllTributiEntibyEnte(String idEnte) {
		return tributoEnteDao.getAllTributiEntibyEnte(idEnte);
	}
	
	@Override
	public List<TributoEnte> getAllTributiEnteEseguiti(String idEnte) {
		return tributoEnteDao.getAllTributiEnteEseguiti(idEnte);
	}
	
	@Override
	public Long countPendenzeTributo(String idEnte, String cdTrbEnte){
				
    	Long numPendenze = pendenzaDao.countPendenzeTributo(idEnte, cdTrbEnte);
		
    	return numPendenze;
    		
	}
	
	@Override
	public List<TributoEnte> getTributiEntiIbanCCpNull() {
		
		return tributoEnteDao.getTributiEntiIbanCCpNull();
		
	}

	@Override
	public int countTributiEntiIbanCCpNull(String idEnte, String cdTrbEnte) {		
		return tributoEnteDao.countTributiEntiIbanCCpNull(idEnte, cdTrbEnte);
	}

	@Override
	public int countTributiEntiIbanMyBankNull(String idEnte, String cdTrbEnte) {
		return tributoEnteDao.countTributiEntiIbanMyBankNull(idEnte, cdTrbEnte);
	}

	@Override
	public List<SistemaEnte> selectActiveAndSemplSistemaEnte() {
		return sistemaEnteDao.selectActiveAndSemplSistemaEnte();
	}
	
	@Override
	public SistemaEnte getSistemaEnteByCdEnteIdSystem(String cdEnte, String idSystem) {;
		return sistemaEnteDao.getSistemaEnteByCdEnteAndIdSystem(cdEnte, idSystem);
	}
	
	@Override
	public SistemaEnte getSistemaEnteByCdEnteIdSystemStorico(String cdEnte, String idSystem) {;
		return sistemaEnteDaoStorico.getSistemaEnteByCdEnteAndIdSystem(cdEnte, idSystem);
	}
	
	@Override
	public List<String> getNdpIuvStartNumByIdEnte(String idEnte,Long oldNdpIuvStartNum) {
		return tributoEnteDao.getNdpIuvStartNumByIdEnte(idEnte,oldNdpIuvStartNum);
	}

	@Override
	public void updateFlagPerConfigurazioneCanaliPagamento(String idEnte, TributoEnteDTO tribDTO) {
		tributoEnteDao.updateFlagPerConfigurazioneCanaliPagamento(idEnte, tribDTO);
	}
	
	
	@Override
	public int countTributiEntiByIban(String idEnte, String iban) {		
		return tributoEnteDao.countTributiEntiByIban(idEnte, iban);
	}
	
	private void inserisciIbanEnti(TributoEnte te, String ibanDaInserire){
		
		IbanEnti ibanEnti= new IbanEnti();
		//ibanEnti.setDataCensimento(new Timestamp(System.currentTimeMillis()));
		//ibanEnti.setOpAggiornamento(te.getOpAggiornamento());
		//ibanEnti.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
		ibanEnti.setStRiga("V");
		ibanEnti.setIban(ibanDaInserire);
		ibanEnti.setIdEnte(te.getIdEnte());
		ibanEnti.setOpInserimento(te.getOpInserimento());
		ibanEnti.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		ibanEnti.setPrVersione(0);
		ibanEntiDao.insertIbanEnte(ibanEnti);
		
		
	}
	
	private void controllaEInserisciIban(TributoEnte te){
		String iban = te.getIBAN();
		
		if (iban!=null && !iban.equals("")){
			
			int numIban = ibanEntiDao.countIbanEnti(te.getIdEnte(), te.getIBAN());
			if (numIban==0){
				// devo inserire iban... 
				inserisciIbanEnti(te,te.getIBAN());
				
			}
		}
		if (te.getIBANContoTecnico()!=null && !te.getIBANContoTecnico().equals("")){
		
			
			int numIban = ibanEntiDao.countIbanEnti(te.getIdEnte(), te.getIBANContoTecnico());
			if (numIban==0){
				// devo inserire iban... 
				inserisciIbanEnti(te,te.getIBANContoTecnico());
				
			}
			
		}
		if (te.getIBAN_CCP()!=null && !te.getIBAN_CCP().equals("")){
			
			int numIban = ibanEntiDao.countIbanEnti(te.getIdEnte(), te.getIBAN_CCP());
			if (numIban==0){
				// devo inserire iban... 
				inserisciIbanEnti(te,te.getIBAN_CCP());
				
			}
		}
		if (te.getIBAN_MYBANK()!=null && !te.getIBAN_MYBANK().equals("")){
			
			int numIban = ibanEntiDao.countIbanEnti(te.getIdEnte(), te.getIBAN_MYBANK());
			if (numIban==0){
				// devo inserire iban... 
				inserisciIbanEnti(te,te.getIBAN_MYBANK());
				
			}
		}
	}
}
