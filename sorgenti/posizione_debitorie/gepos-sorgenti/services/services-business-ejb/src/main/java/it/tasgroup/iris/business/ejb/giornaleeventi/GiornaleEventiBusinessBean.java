package it.tasgroup.iris.business.ejb.giornaleeventi;


import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import it.tasgroup.iris.business.ejb.client.esportazioni.ExportBusinessLocal;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessRemote;
import it.tasgroup.iris.business.ejb.pagamenti.dto.builder.ExportDTOBuilder;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.EventoNDP_DTO;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.gde.GiornaleEventi;
import it.tasgroup.iris.gde.GiornaleEventiDocumentiNDP;
import it.tasgroup.iris.persistence.dao.interfaces.GiornaleEventiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GiornaleEventiDocumentiNDPDao;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumTipoExport;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless(name = "GiornaleEventiBusiness")
public class GiornaleEventiBusinessBean implements GiornaleEventiBusinessLocal,
		GiornaleEventiBusinessRemote { 
	
	@EJB(name="GiornaleEventiDaoService")
	private GiornaleEventiDao giornaleEventiDao;
	
	@EJB(name="GiornaleEventiDocumentiNDPDaoService")
	private GiornaleEventiDocumentiNDPDao giornaleEventiDocNDPDao;
	
	@EJB(name = "ExportBusiness")
    private ExportBusinessLocal exportBusinessBean;
	
	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save(GiornaleEventiExtDTO g) {
		boolean skip=conf.getBooleanProperty("usaProxyNdp");
		if (skip)
			return;
		GiornaleEventi ge = new GiornaleEventi();
		ge.setCanalePagamento(g.getCanalePagamento());
		ge.setCategoriaEvento(g.getCategoriaEvento());
		ge.setCodiceContestoPagamento(g.getCodiceContestoPagamento());
		ge.setComponente(g.getComponente());
		ge.setDataOraEvento(g.getDataOraEvento());
		ge.setEsito(g.getEsito());
		ge.setIdDominio(g.getIdDominio());
		ge.setIdErogatore(g.getIdErogatore());
		ge.setIdFruitore(g.getIdFruitore());
		ge.setIdPSP(g.getIdPSP());
		ge.setIdStazioneIntermediarioPA(g.getIdStazioneIntermediarioPA());
		ge.setIdUnivocoVersamento(g.getIdUnivocoVersamento());
		ge.setParametriSpecificiInterfaccia(g.getParametriSpecificiInterfaccia());
		ge.setSottoTipoEvento(g.getSottoTipoEvento());
		ge.setTipoEvento(g.getTipoEvento());
		ge.setTipoVersamento(g.getTipoVersamento());
		//
		ge.setOpAggiornamento(g.getOpAggiornamento());
		ge.setOpInserimento(g.getOpInserimento());
		ge.setTsAggiornamento(g.getTsAggiornamento());
		ge.setTsInserimento(g.getTsInserimento());
		//
		ge.setFaultCode(g.getFaultCode());
		ge.setFaultString(g.getFaultString());
		ge.setFaultId(g.getFaultId()); 
		ge.setFaultDescription(g.getFaultDescription());
		ge.setFaultSerial(g.getFaultSerial());
		ge.setOriginalFaultCode(g.getOriginalFaultCode()); 
	 	ge.setOriginalFaultString(g.getOriginalFaultString()); 
	 	ge.setOriginalFaultDescription(g.getOriginalDescription()); 
		try {
			
		   giornaleEventiDao.save(ge);
		   
		} catch (Throwable t){
			
			t.printStackTrace();
			
			throw new RuntimeException();
			
		}
		
		if (g.isStoreDocumentoNDP()) {
			// controllo se esiste già un oggetto in tabella relativo alla quadrupla
			// id_dominio, IUV, ccp, tipo, 
			GiornaleEventiDocumentiNDP gdo = giornaleEventiDocNDPDao.getGiornaleEventiDocumenti(g.getCodiceContestoPagamento(), g.getIdDominio(), g.getIdUnivocoVersamento(),g.getTipo(),g.getIdPSP());
			if (gdo==null) {
				gdo = new GiornaleEventiDocumentiNDP();
				gdo.setIdDominio(g.getIdDominio());
				gdo.setCodiceContestoPagamento(g.getCodiceContestoPagamento());
				gdo.setIdUnivocoVersamento(g.getIdUnivocoVersamento());
				gdo.setIdPSP(g.getIdPSP());
				gdo.setDimensione(g.getDimensione());
				gdo.setDocumento(g.getDocumento());
				gdo.setTentativo(g.getTentativo());
				gdo.setTipo(g.getTipo());
				gdo.setOpAggiornamento(g.getOpAggiornamento());
				gdo.setOpInserimento(g.getOpInserimento());
				gdo.setTsAggiornamento(g.getTsAggiornamento());
				gdo.setTsInserimento(g.getTsInserimento());
				try {
					giornaleEventiDocNDPDao.save(gdo);
				} catch (Throwable t) {
					throw new RuntimeException();
				}
			} else {
				
			}
		}
	}
	
	@Override
	public List<GiornaleEventi> getListEventiByFilterParams(ContainerDTO dto) {
		
		return (List<GiornaleEventi>) giornaleEventiDao.listEventiNDPByFilterParams(dto);
	
	}
	
	@Override
	public byte[] getGiornaleEventiDocumentiNDP(String codContesto, String idDominio, String iuv, String tipo, String idPsp) {
		
		byte[] result = null;
		
		GiornaleEventiDocumentiNDP docNDP = giornaleEventiDocNDPDao.getGiornaleEventiDocumenti(codContesto, idDominio, iuv, tipo,idPsp);
		
		if (docNDP!=null)
		   result =  docNDP.getDocumento();
				
		return result;
	}
	
	@Override
    @Asynchronous
    public void esportaEventiNDPFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, String cfOperatore, Locale locale){
        
		List<GiornaleEventi> listaEventi = getListEventiByFilterParams(inputDTO);
		
		List<EventoNDP_DTO> outputDTO = ExportDTOBuilder.populateListEventoNDP_DTO(listaEventi);
		
		PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(1);
		
		exportBusinessBean.esporta(prenotazione, mapListaCampi, cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.EVENTINDP.getChiave(), EnumDynaReportFormat.CSV_CUSTOM, null, EventoNDP_DTO.class, locale);

    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save(GiornaleEventiExtDTO[] g) {
		for (int i=0; i< g.length; i++) {
			save(g[i]);
		}
		
	}

	@Override
	public void ackRTAgid(String codContesto, String idDominio, String iuv, String tipo) {
		
		Long version = giornaleEventiDocNDPDao.readVersion(codContesto,idDominio, iuv,tipo);
		
		giornaleEventiDocNDPDao.updateAckDownload("1", codContesto,idDominio, iuv,tipo, version);
		
	}
	
	

}
