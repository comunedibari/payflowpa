package it.tasgroup.iris.facade.ejb.anonymous.dto.builder;

import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousEntiDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentConditionDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousPaymentDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousTributoDTO;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousTributoEnteDTO;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Tuple;

public class AnonymousPaymentDTOBuilder {

	/**
	 * 
	 * @param listaEnti
	 * @return
	 */
	public static List<AnonymousEntiDTO> populateListaAnonymousEntiDTO(List<Enti> listaEnti) {

		return populateListaAnonymousEntiDTO(listaEnti, null);
	}
	
	/**
	 * 
	 * @param listaEnti
	 * @return
	 */
	public static List<AnonymousEntiDTO> populateListaAnonymousEntiDTO(List<Enti> listaEnti, String idTributo) {

		List<AnonymousEntiDTO> dtos = new ArrayList<AnonymousEntiDTO>();

		for (Enti e : listaEnti) {
			
			AnonymousEntiDTO dto = populateAnonymousEntiDTOFull(e, idTributo);
			
			dtos.add(dto);
			
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param e
	 * @return 
	 */
	public static AnonymousEntiDTO populateAnonymousEntiDTO(Enti e) {
		if (e == null)
			return null;
		
		AnonymousEntiDTO dto = new AnonymousEntiDTO();
		
		dto.setDenominazione(e.getDenominazione());
		dto.setIdEnte(e.getIdEnte());
		dto.setMaxNumTributi(e.getMaxNumTributi());
		dto.setStato(e.getStato());
		dto.setCdEnte(e.getCodiceEnte());
		
		
		return dto;
	}
	
	public static AnonymousEntiDTO populateAnonymousEntiDTOFull(Enti e, String idTributo) {
		if (e == null)
			return null;
		
		AnonymousEntiDTO dto = new AnonymousEntiDTO();
		
		dto.setDenominazione(e.getDenominazione());
		dto.setIdEnte(e.getIdEnte());
		dto.setCdEnte(e.getCodiceEnte());
		dto.setMaxNumTributi(e.getMaxNumTributi());
		dto.setStato(e.getStato());
		
		List<AnonymousTributoEnteDTO> listaTributi = new ArrayList<AnonymousTributoEnteDTO>();
		
		for(TributoEnte te : e.getTributiEnte()){
			if(idTributo != null) {
				 if ((te.getIdTributo().equals(idTributo)) && (te.getFlIniziativa().equals("Y")) 
						 && (te.getFlNascostoFe().equals("N")) && 
						 (!(te.getIstruzioniPagamento().equals(null)) && !(te.getIstruzioniPagamento().trim().equals("")))) {
					AnonymousTributoEnteDTO dtoTributo = populateAnonymousTributoEnteDTO(te);
					listaTributi.add(dtoTributo);
				 }
			} else 	{
				AnonymousTributoEnteDTO dtoTributo = populateAnonymousTributoEnteDTO(te);
				listaTributi.add(dtoTributo);
			}
		}
		
		dto.setTributiList(listaTributi);
		
		return dto;
	}
	
	
	public static AnonymousEntiDTO populateAnonymousEntiDTOFull(Enti e) {
		return populateAnonymousEntiDTOFull(e, null);
	}

	
	/**
	 * 
	 * @param listaTributi
	 * @return
	 */
	public static List<AnonymousTributoEnteDTO> populateListaAnonymousTributoEnteDTO(List<TributoEnte> listaTributi) {

		List<AnonymousTributoEnteDTO> dtos = new ArrayList<AnonymousTributoEnteDTO>();

		for (TributoEnte te : listaTributi) {
			
			AnonymousTributoEnteDTO dto = populateAnonymousTributoEnteDTO(te);			
			dtos.add(dto);
			dto.setIndex(dtos.size()-1);
			
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param te
	 * @return
	 */
	public static AnonymousTributoEnteDTO populateAnonymousTributoEnteDTO(TributoEnte te) {
		
		if (te == null)
			return null;
		
		AnonymousTributoEnteDTO dto = new AnonymousTributoEnteDTO();
		
		dto.setDeTrb(te.getDeTrb());
//		dto.setIdTributo(te.getTribEnId().getCdTrbEntePk());
		dto.setFlPredeterm(te.getFlPredeterm());
		dto.setFlIniziativa(te.getFlIniziativa());
		dto.setInfoUrl(te.getUrlInfoService());
		dto.setUpdServiceUrl(te.getUrlUpdService());
		dto.setIstruzioniPagamento(te.getIstruzioniPagamento());
		dto.setIdEnte(te.getIdEnte());
		dto.setCdTrbEnte(te.getCdTrbEnte());
		dto.setCategoria(te.getIdTributo());
		
		if (te.getCfgTributoEntePlugin() != null) {
			dto.setCdPlugin(te.getCfgTributoEntePlugin().getCdPlugin());
			dto.setDatiCfgPlugin(te.getCfgTributoEntePlugin().getDati());
		}
		
		return dto;
	}
	
	public static AnonymousTributoDTO populateAnonymousTributoDTO(CategoriaTributo te) {
		
		if (te == null)
			return null;
		
		AnonymousTributoDTO dto = new AnonymousTributoDTO();
		
		dto.setDeTrb(te.getDeTrb());
		dto.setIdTributo(te.getIdTributo());
		dto.setFlPredeterm(te.getFlPredeterm());
		dto.setIstruzioniPagamento(te.getIstruzioniPagamento());

		return dto;
	}
	
	
	/**
	 * 
	 * @param apayment
	 * @return
	 */
	public static AnonymousPaymentDTO populateAnonymousTributoEnteDTO(GestioneFlussi apayment) {
		if (apayment == null)
			return null;
		
		AnonymousPaymentDTO dto = new AnonymousPaymentDTO();
		
		dto.setCodPagamento(apayment.getCodPagamento());
		dto.setCodTranzazione(apayment.getCodTransazione());
		dto.setDataesecuzione(apayment.getDataSpedizione());
		dto.setIdDistinta(apayment.getId());
		// setto il valore di documento disponibile NB: CONSIDERO SOLO UN PAGAMENTO ASSOCIATO ALLA DISTINTA
		Iterator<Pagamenti> pagIter=apayment.getPagamenti().iterator();
		while (pagIter.hasNext()) {
			Pagamenti p = pagIter.next();
			dto.setAssociatedDocAvailable(p.isAssociatedDocumentAvailable());
		    break;
	    }	
		//
		
		// TODO: fix provvisoria. E' necessario sanare il meccanismo di scrittura sulla tabella delle distinte
		if (EnumTipoModalitaPagamento.DDPATMBT.getChiave().equals(""+apayment.getCfgGatewayPagamento().getCfgModalitaPagamento().getId())) {		

			dto.setImportoNetto(apayment.getTotimportipositivi());
			dto.setImportocommissioni(apayment.getImportoCommissioni());
			dto.setImportoTotale(apayment.getTotimportipositivi().add(apayment.getImportoCommissioni()));
		
		} else {

			dto.setImportoNetto(apayment.getTotimportipositivi().subtract(apayment.getImportoCommissioni()));
			dto.setImportocommissioni(apayment.getImportoCommissioni());
			dto.setImportoTotale(apayment.getTotimportipositivi());
	
		}
		
		dto.setStato(apayment.getStato());
		dto.setModalitapagamento(apayment.getCfgGatewayPagamento().getCfgModalitaPagamento().getDescrizione());
		dto.setModalitaPagamentoBundleKey(apayment.getCfgGatewayPagamento().getCfgFornitoreGateway().getBundleKey());
		dto.setCodPagante(apayment.getUtentecreatore());

		return dto;
	}
	
	
	/**
	 * 
	 * @param listaPagamenti
	 * @return
	 */
	public static List<AnonymousPaymentConditionDTO> populateListaAnonymousPaymentConditionDTO(List<Tuple> listaPaymentCond) {
		
		if (listaPaymentCond == null || listaPaymentCond.size() < 0)
			return null;
		
		List<AnonymousPaymentConditionDTO> dtos = new ArrayList<AnonymousPaymentConditionDTO>();
		
		for (Tuple p : (List<Tuple>)listaPaymentCond) {
			AnonymousPaymentConditionDTO dto = populateAnonymousPaymentConditionDTO(p);
			dtos.add(dto);
		}

		return dtos;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public static AnonymousPaymentConditionDTO populateAnonymousPaymentConditionDTO(Tuple p) {
		if (p == null)
			return null;
		
		AnonymousPaymentConditionDTO dto = new AnonymousPaymentConditionDTO();
		
		dto.setIdCondizione((String)p.get("idCondizione"));
		
		dto.setIdPagamento(((Long)p.get("idPagamento")).toString());
		
		String stringRicevutaAnonimo = (String)p.get("flRicevutaAnonimo");
		
		boolean flRicevutaAnonimo = (stringRicevutaAnonimo != null && stringRicevutaAnonimo.equals("Y"));
		
		dto.setFlRicevutaAnonimo(flRicevutaAnonimo);
		
		dto.setEnte((String)p.get("ente"));
		dto.setTributo((String)p.get("tributo"));
		dto.setAnnoRiferimento((Integer)p.get("annoRiferimento"));
		dto.setCausalePendenza((String)p.get("causalePendenza"));
		dto.setCausaleCondizione((String)p.get("causaleCondizione"));
		dto.setTotalePendenza((BigDecimal)p.get("totalePendenza"));
		dto.setImporto(((BigDecimal)p.get("importo")).setScale(2));
		dto.setDataScadenza((Date)p.get("dataScadenza"));
		dto.setNotePendenza((String)p.get("notePendenza"));
		String flagIncasso = (String) p.get("flagIncasso");
		dto.setIncassato(flagIncasso == null || flagIncasso.isEmpty() || flagIncasso.equals("0")? Boolean.FALSE : Boolean.TRUE);
		dto.setStato((String)p.get("statoPagamento"));
		dto.setIdPendenza((String)p.get("idPendenza"));
		dto.setIuv((String)p.get("iuv"));
		
		dto.setIdTributoEnte((String)p.get("cdTrbEnte"));
		dto.setDataPagamento((Date)p.get("tsDecorrenza"));

		return dto;
	}

	public static List<AnonymousEntiDTO> populateAnonymousEntiDTOList(List<Enti> enti) {
		
		List<AnonymousEntiDTO> entiDTO = new ArrayList<AnonymousEntiDTO>();
		
		for (Enti ente : enti) {
			
			AnonymousEntiDTO dto = populateAnonymousEntiDTO(ente);	
			
			entiDTO.add(dto);
		}
		
		return entiDTO;
	} 
	
	public static AnonymousPaymentDTO populateAnonymousTributoEnteDTO(GestioneFlussi distinta, Pagamenti pagamenti) {
		final AnonymousPaymentDTO anonymousPaymentDTO = populateAnonymousTributoEnteDTO(distinta);
		anonymousPaymentDTO.setIdPagamento(pagamenti.getId());
		return anonymousPaymentDTO;
    }
}
