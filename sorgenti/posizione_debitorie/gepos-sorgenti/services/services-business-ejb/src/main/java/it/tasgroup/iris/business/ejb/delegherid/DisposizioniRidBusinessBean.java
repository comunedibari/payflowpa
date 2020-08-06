package it.tasgroup.iris.business.ejb.delegherid;

import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.creditcard.DisposizioneCartaCreditoVO;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.profile.IProfileManager;
import it.nch.utility.GeneratoreIdUnivoci;
import it.tasgroup.iris.business.ejb.client.delegherid.DisposizioniRidBusinessLocal;
import it.tasgroup.iris.business.ejb.client.delegherid.DisposizioniRidRidBusinessRemote;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.Rid;
import it.tasgroup.iris.dto.AllineamentiElettroniciArchiviDTO;
import it.tasgroup.iris.dto.PagamentoRidDTO;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllineamentiElettroniciArchiviDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.RidDao;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.util.enumeration.EnumStatoDP;
import it.tasgroup.services.util.idgenerator.IDGenerator;

import java.sql.Timestamp;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "DisposizioniRidBusiness")
public class DisposizioniRidBusinessBean implements DisposizioniRidBusinessLocal, DisposizioniRidRidBusinessRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(DisposizioniRidBusinessBean.class);
	
	@EJB(name = "RidDaoImpl")
	RidDao pridDAO;
	
	@EJB(name = "AllineamentiElettroniciArchiviDaoImpl")
	AllineamentiElettroniciArchiviDao AEADAO;

//	@EJB(name = "DistintePagamentoDaoImpl")
//	DistintePagamentoDao DPDAO;
	
	@EJB(name = "PagamentiDaoImpl")
	PagamentiDao pagamDAO;
	
	@EJB(name = "PendenzaDaoImpl")
	PendenzaDao pendDAO;
	
	@EJB(name = "IntestatariDaoImpl")
	IntestatariDAO intestatariDAO;
	
	@EJB(name = "GestioneFlussiDaoService")
	GestioneFlussiDao gestioneFlussiDao;
	
	@Override
	public GestioneFlussi createDisposizioneRid(IProfileManager profile,
			PagamentoRidDTO riddto,
			AllineamentiElettroniciArchiviDTO delega, ContoTecnico contoTecnico) throws Exception {
		
		Intestatari intestatario = intestatariDAO.getById(Intestatari.class, delega.getIntestatario());
		String indirizzoDebitore = buildIndirizzoIntestatario(intestatario);
		// crea una riga in distinte_pagamento
		
		DistintePagamentoDTO dpdto = new DistintePagamentoDTO();
		
		dpdto.setStato(EnumStatoDP.IN_CORSO.getChiave());
		dpdto.setImporto(riddto.getImporto());
		dpdto.setImportoCommissione(riddto.getImportoCommissioni());
		dpdto.setDataCreazione(new Timestamp(System.currentTimeMillis()));
		dpdto.setUtenteCreatore(delega.getCodificaFiscaleSottoscrittore());
		dpdto.setNumDisposizioni(new Integer(1));
		
		String codTransizione = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
		System.out.println("codice generato tramite UUID:"+codTransizione+" ("+codTransizione.length()+")");
		dpdto.setCodTransazione(codTransizione);

		dpdto.setCodPagamento(IDGenerator.Generate_TRANSACTION_ID());
		
//		dpdto.setCodCanale(EnumCanale.IRIS.getChiave());
//		dpdto.setCodDistinta(EnumDistinta.RID_ON_LINE.getChiave());
		dpdto.setOperatorUsername(delega.getCodificaFiscaleSottoscrittore());
		
		dpdto.setIndirizzo(indirizzoDebitore);
		System.out.println("createDisposizioneRid IdCfgGateway: " +riddto.getIdCfgGateway() );
		dpdto.setIdCfgGateway(riddto.getIdCfgGateway());//new Integer(5));
		
		GestioneFlussi dp = gestioneFlussiDao.createDP(dpdto);
		
		riddto.setAbiBancaDomiciliaria(delega.getAbiAddebito());
		riddto.setCodDebitore(delega.getCodificaFiscaleSottoscrittore());
		riddto.setTipoIncassoRid(delega.getTipoIncassoRid().getChiave());
		riddto.setRagSocialeDebitore(delega.getRagSocSottoscrittore());
		riddto.setRiferimentoDebito(dp.getCodTransazione());
		riddto.setFlagIniziativa(delega.getFlagIniziativa());
		riddto.setFlagStorno(delega.getFlagStorno().getChiave());
		riddto.setIntestatario(intestatario.getCorporate());
		riddto.setIdDistintaPagamento(dp.getId());
		riddto.setIndirizzoDebitore(indirizzoDebitore);
		
		String codRiferimento = GeneratoreIdUnivoci.GetCurrent().generaCodiceRiferimento("IRIS");
		riddto.setCodRiferimento(codRiferimento);
		
		Rid rid = pridDAO.createPRid(riddto,contoTecnico);
		
		DistintaCartaCreditoVO dcc = (DistintaCartaCreditoVO) riddto.getDistintaCartaCreditoVO();
		for (Iterator iterator = dcc.getDisposizioni().iterator(); iterator.hasNext();) {
			DisposizioneCartaCreditoVO dispo = (DisposizioneCartaCreditoVO) iterator.next();
			SessionShoppingCartItemDTO pvo = (SessionShoppingCartItemDTO) dispo.getPagamentoVO();
			
			Pendenza pend = pendDAO.getById(Pendenza.class, pvo.getIdPendenza());
			
			Pagamenti pagamento = pagamDAO.salvaPagamento(
					delega.getCodificaFiscaleSottoscrittore(), 
					pend.getIdPendenzaente(),
					pvo, 
					dp.getId());
			
			if (dp.getStato().equals(StatiPagamentiIris.IN_CORSO.getFludMapping()))
					
					pendDAO.updateStRiga(pagamento.getCondPagamento());
			
		}
			
		return dp;
	}

	@Override
	public GestioneFlussi readDistintePagamentoById(String id)
			throws Exception {
		
		return gestioneFlussiDao.getById(GestioneFlussi.class, new Integer(id));
	}
	
	
	private String buildIndirizzoIntestatario(Intestatari intestatario) throws Exception{
		StringBuffer indirizzo = new StringBuffer();
		// append indirizzo
		appendAddress(indirizzo, intestatario.getIndirizzipostaliobj().getAddressIForm(), 30);
		// append cap
		appendAddress(indirizzo, intestatario.getIndirizzipostaliobj().getCapCodeIForm(), 5);
		// append città provincia
		String citta = intestatario.getIndirizzipostaliobj().getCityIForm()!= null && intestatario.getIndirizzipostaliobj().getCityIForm().length() > 22 ?  
				intestatario.getIndirizzipostaliobj().getCityIForm().substring(0,22):intestatario.getIndirizzipostaliobj().getCityIForm();
					
		appendAddress(indirizzo, citta + " " + intestatario.getIndirizzipostaliobj().getProvinceIForm(), 25);
		return indirizzo.toString();
	}
	
	private static void appendAddress(StringBuffer indirizzo, String ind, int length){
		int lung = ind == null ? 0 : ind.length();
		
		if (lung >= length)
			indirizzo.append(ind.substring(0, length));
		
		else {
			ind = StringUtils.rightPad(ind, length," ");
			indirizzo.append(ind);
		}
	}
	
//	private String buildIndirizzoIntestatario(Intestatari intestatario) throws Exception{
//		if (intestatario.getIndirizzipostaliobj() == null || intestatario.getIndirizzipostaliobj().equals(""))
//			return "";
//
//		StringBuffer indirizzo = new StringBuffer();
//		int lung = intestatario.getIndirizzipostaliobj().getAddressIForm() == null ? 0:intestatario.getIndirizzipostaliobj().getAddressIForm().length();
//			
//		if (lung == 30)
//			indirizzo.append(intestatario.getIndirizzipostaliobj().getAddressIForm());
//		else if (lung < 30){
//			indirizzo.append(intestatario.getIndirizzipostaliobj().getAddressIForm());
//			for (int i = 0; i < (30-lung); i++) {
//				indirizzo.append(" ");
//			}
//		}else
//			indirizzo.append(intestatario.getIndirizzipostaliobj().getAddressIForm().substring(0, 30));
//		
//		if (intestatario.getIndirizzipostaliobj().getCapCodeIForm() != null && intestatario.getIndirizzipostaliobj().getCapCodeIForm().length() != 5)
//			indirizzo.append(intestatario.getIndirizzipostaliobj().getCapCodeIForm());
//		else 
//			indirizzo.append("     ");
//		
//		indirizzo.append(intestatario.getIndirizzipostaliobj().getCityIForm()!= null ? intestatario.getIndirizzipostaliobj().getCityIForm() : "");
//		indirizzo.append(" ");
//		indirizzo.append(intestatario.getIndirizzipostaliobj().getProvinceIForm() != null ? intestatario.getIndirizzipostaliobj().getProvinceIForm() : "");
//
//		System.out.println("EMA------------------> indirizzo, cap, citta: "+ indirizzo.toString()+" ("+indirizzo.length()+")");
//		return indirizzo.length() > 60 ? indirizzo.substring(0, 60):indirizzo.toString();
//	}

}
