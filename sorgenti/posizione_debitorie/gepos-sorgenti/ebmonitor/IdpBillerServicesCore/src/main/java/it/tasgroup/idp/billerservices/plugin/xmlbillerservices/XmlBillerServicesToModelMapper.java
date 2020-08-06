package it.tasgroup.idp.billerservices.plugin.xmlbillerservices;

import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.posizioneDebitoria.AllegatiPendenza;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.VociPagamento;

import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.xmlbillerservices.pendenze.CondizionePagamento;
import it.tasgroup.idp.xmlbillerservices.pendenze.Debitore;
import it.tasgroup.idp.xmlbillerservices.pendenze.Pendenza;
import it.tasgroup.idp.xmlbillerservices.pendenze.Pendenza.InfoPagamento;
import it.tasgroup.idp.xmlbillerservices.pendenze.VoceImporto;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.codec.binary.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlBillerServicesToModelMapper {

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	public Pendenze mapMessagePendenzaToModel(Pendenza p, Enti ente, TributiEnti tributo, String sil, Boolean isHidden) {
		
		// Diamo per assodato che p.getInsert() sia diverso da null in quanto controllato a priori dal chiamante.
		
		Timestamp tsDecorrenza = new Timestamp(System.currentTimeMillis());  
				
		Pendenze pp = new Pendenze();
		if (p.getAnnoRiferimento()!=null) {
			pp.setAnnoRiferimento(p.getAnnoRiferimento().getYear());
		}	
		pp.setCdTrbEnte(p.getTipoDebito());
		pp.setCoAbi(null);
//		if (p.getPagamentoOTF().getRiscossore()!=null) {
//			pp.setCoRiscossore(p.getInsert().getRiscossore().getId());
//			pp.setRiferimento(p.getInsert().getRiscossore().getRiferimento());
//		}	
		if ("E_BOLLO".equals(p.getTipoDebito())) {
			pp.setCoRiscossore("ADE");
			pp.setRiferimento(p.getRiferimentoDebito());
		}
		
		pp.setDeCausale(p.getCausaleDebito());
		pp.setDeMittente(p.getMittente().getDescrizione());
		if (p.getDivisa()!=null) {
			pp.setDvRiferimento(p.getDivisa().value());
		}	
		pp.setFlModPagamento("S"); //Default: poi sovrascritto sotto in  base alle condizioni di pagamento
		pp.setIdEnte(ente.getIdEnte());
		if (p.getMittente()!=null) {
			pp.setIdMittente(p.getMittente().getId());
		}
		pp.setIdPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());

		pp.setIdPendenzaente(p.getIdDebito());
		pp.setIdSystem(sil);
		pp.setIdTributo(tributo.getJlttrpa().getIdTributo());
		pp.setImTotale(p.getImportoTotale());
		pp.setNote(p.getNoteDebito());
		pp.setOpAnnullamento(null);
		pp.setPrVersione(0);
		
		
//		switch (p.getStato()) {
//		case APERTA:
//			pp.setStPendenza("A");
//			break;
//		case CHIUSA:
//			pp.setStPendenza("C");
//			break;
//		}

		pp.setStPendenza("A");
		
		if (isHidden) {
			pp.setStRiga("H");
			pp.setTsAnnullamento(new Timestamp(System.currentTimeMillis()));
		} else {
			pp.setStRiga("V");
			pp.setTsAnnullamento(null);
		}
		
		if (p.getDataCreazione()!=null) {
			pp.setTsCreazioneente(toTimestamp(p.getDataCreazione()) );
		}	
		pp.setTsDecorrenza(tsDecorrenza);
		if(p.getDataEmissione()!=null) {
			pp.setTsEmissioneente(toTimestamp(p.getDataEmissione()));
		}
		
//		if (p.getDataModificaEnte()!=null) {
//			pp.setTsModificaente(toTimestamp(p.getInsert().getDataModificaEnte()));
//		}
//		if (p.getDataPrescrizione()!=null) {
//			pp.setTsPrescrizione(toTimestamp(p.getInsert().getDataPrescrizione()));
//		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataPrescrizione=null;
		try {
			dataPrescrizione = sf.parse("2100-01-01");
		} catch (ParseException e) {
			logger.error("Errore che non si dovrebbe mai presentare",e);
		}
		
		pp.setTsPrescrizione(new Timestamp(dataPrescrizione.getTime()));
		
		pp.setIdTributoStrutturato(null);
			
		// Condizioni Pagamento
		Set<CondizioniPagamento> condizioni=new LinkedHashSet<CondizioniPagamento>();
		
		InfoPagamento ipag = p.getInfoPagamento();
			
		String tipoRateazionePagamento=null;
		
		pp.setFlModPagamento("S");
		tipoRateazionePagamento="S";
		
//		if(ipag.getTipoPagamento().equals(TipoPagamento.PAGAMENTO_UNICO)) {
//			if ("R".equals(pp.getFlModPagamento())) {
//				pp.setFlModPagamento("E");
//			} else {
//				pp.setFlModPagamento("S");
//			}
//		
//			tipoRateazionePagamento="S";
//			
//		} else if (ipag.getTipoPagamento().equals(TipoPagamento.PAGAMENTO_A_RATE)) {
//			if ("S".equals(pp.getFlModPagamento())) {
//				pp.setFlModPagamento("E");
//			} else {
//				pp.setFlModPagamento("S");
//			}
//
//			tipoRateazionePagamento="R";
//			
//		}
			
			for (CondizionePagamento dettaglioPagamento:ipag.getCondizionePagamento()) {
				CondizioniPagamento condizione = mapCondizioniToModel(p,dettaglioPagamento, ente, tributo, tsDecorrenza, tipoRateazionePagamento, isHidden);
				condizione.setPendenze(pp);
				if (condizione.getVociPagamento()!=null) {
					for(VociPagamento voce:condizione.getVociPagamento()) {
						voce.setIdPendenza(pp.getIdPendenza());
					}
				}
				if (condizione.getAllegatiPendenza()!=null){
					for(AllegatiPendenza all:condizione.getAllegatiPendenza()) {
						all.setIdPendenza(pp.getIdPendenza());
					}
				}
				
				condizioni.add(condizione);
			}
		
		pp.setCondizioniPagamento(condizioni);
		
		
		// Destinatari Pendenze
		
		Set<DestinatariPendenze> destinatari  = new LinkedHashSet<DestinatariPendenze>();
	
		for (Debitore destP:p.getDebitori().getDebitore()) {  //Non controllo il null in quanto tag obbligatorio
			DestinatariPendenze d=mapDestinatarioToModel(destP,tsDecorrenza,isHidden);
			d.setPendenze(pp);
			destinatari.add(d);
		}
		
		pp.setDestinatariPendenze(destinatari);
		
		pp.completeForInsert();
		
		
		return pp;
		
		
		
	}
	
	private DestinatariPendenze mapDestinatarioToModel(Debitore d, Timestamp tsDecorrenza, boolean isHidden) {
	
		DestinatariPendenze dp = new DestinatariPendenze();
		
		dp.setPrVersione(0);
		if (isHidden) {
			dp.setStRiga("H");
		}	
		else {
			dp.setStRiga("V");
		}
		
		// Scommentare per iniettare un "errore database": dp.setStRiga("INJECT AN ERROR ");
		
		dp.setTsDecorrenza(tsDecorrenza);
		dp.setIdDestinatario(GeneratoreIdUnivoci.GetCurrent().generaId());
		dp.setCoDestinatario(d.getIdFiscale());
		dp.setDeDestinatario(d.getAnagrafica());

		//		switch (d.getTipo()) {
		//		case CITTADINO:
		//			dp.setTiDestinatario("CI");
		//			break;
		//		case DELEGATO:
		//			dp.setTiDestinatario("DE");
		//			break;
		//		case ALTRO:
		//			dp.setTiDestinatario("AL");
		//			break;
		//		}
		
		dp.setTiDestinatario("CI");
		dp.completeForInsert();
		
		return dp;
	}
	
	private CondizioniPagamento mapCondizioniToModel(Pendenza p,CondizionePagamento dp,Enti ente, TributiEnti tributo, Timestamp tsDecorrenza, String tipoRateazionePagamento, Boolean isHidden) {
		CondizioniPagamento c = new CondizioniPagamento();
		
		c.setCdTrbEnte(tributo.getId().getCdTrbEnte());
		c.setIdEnte(tributo.getId().getIdEnte());
		c.setImTotale(dp.getImportoPagamento());
		c.setOpAnnullamento(null);
		c.setPrVersione(0);

		if (isHidden) {
			c.setStRiga("H");
			c.setTsAnnullamento(new Timestamp(System.currentTimeMillis()));
		} else {
			c.setStRiga("V");
			c.setTsAnnullamento(null);
		}

		c.setTsDecorrenza(tsDecorrenza);
		c.setIdCondizione(GeneratoreIdUnivoci.GetCurrent().generaId());
		
//		if (dp.getCIP()!=null) {
//			c.setCoCip(dp.getCIP().getCodice());
//			c.setTiCip(dp.getCIP().getTipo());
//		}
		
		if (dp.getDataFineValidita()!=null) {
			c.setDtFinevalidita(dp.getDataFineValidita().toGregorianCalendar().getTime());
		}
		if (dp.getDataInizioValidita()!=null) {
			c.setDtIniziovalidita(dp.getDataInizioValidita().toGregorianCalendar().getTime());
		}	
		
//		if (dp.getDettaglioTransazione()!=null) {
//			if (dp.getDettaglioTransazione().getDataPagamento()!=null) {
//				c.setDtPagamento(toTimestamp(dp.getDettaglioTransazione().getDataPagamento()));
//			}
//			c.setDeCanalepag(dp.getDettaglioTransazione().getCanalePagamento());
//			c.setImPagamento(dp.getDettaglioTransazione().getImportoPagamento());
//			c.setDeMezzopagamento(dp.getDettaglioTransazione().getMezzoPagamento());
//			c.setDeNotepagamento(dp.getDettaglioTransazione().getNotePagamento());
//
//		}
		
		c.setDtScadenza(dp.getDataScadenza().toGregorianCalendar().getTime());
		c.setIdPagamento(dp.getIdPagamento());
		
//		switch(dp.getStatoPagamento()) {
//		case PAGATO:
//			c.setStPagamento("P");
//			break;
//		case NON_PAGABILE:
//			c.setStPagamento("X");
//			break;
//		case NON_PAGATO:
//			c.setStPagamento("N");
//			break;			
//		case PAGAMENTO_IRREGOLARE:
//			c.setStPagamento("E");
//			break;			
//		}
		
		c.setStPagamento("N");
		
		c.setTiPagamento(tipoRateazionePagamento);
		
		if (dp.getVociPagamento()!=null && dp.getVociPagamento().getVoce()!=null) {
			Set<VociPagamento> voci = new HashSet<VociPagamento>();
			for (VoceImporto v:dp.getVociPagamento().getVoce()) {
				VociPagamento voce= buildVocePagamento(v,tsDecorrenza,isHidden);
				voce.setCondizioniPagamento(c);
				voci.add(voce);
			}
			
			c.setVociPagamento(voci);  
		}
		
		if ("E_BOLLO".equals(p.getTipoDebito())) {
			Set<AllegatiPendenza> allPend = new HashSet<AllegatiPendenza>();
			AllegatiPendenza ap = buildAllegatoMarcaDaBolloDigitale(p.getDatiMBD(),c);
			allPend.add(ap);
			c.setAllegatiPendenza(allPend);			
			
		}
		// c.setAllegatiPendenza(); // TODO: mappare

		//		if (dp.getAccreditoPagamento()!=null) {
		//			c.setRagioneSocialeBeneficiario(dp.getAccreditoPagamento().getCodiceIBAN());
		//			c.setIbanBeneficiario(dp.getAccreditoPagamento().getBeneficiario());
		//		}	
		
		
		c.setCausalePagamento(dp.getCausalePagamento());
		
		c.completeForInsert();
		return c;
	}
	
	private AllegatiPendenza buildAllegatoMarcaDaBolloDigitale(it.tasgroup.idp.xmlbillerservices.common.DatiMBD marcaBollo,CondizioniPagamento c) {
		AllegatiPendenza a = new AllegatiPendenza();
		
		a.setCondizioniPagamento(c);
		a.setIdAllegato(GeneratoreIdUnivoci.GetCurrent().generaId());
		a.setFlContesto("C");
		a.setTitolo("DatiMBD.txt");
		a.setTiCodificaBody("TXT_");
		a.setTiAllegato("DatiMBD"); 
		a.setStRiga("V");
		a.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
		
		
		String body = "TIPO="+marcaBollo.getTipoBollo()+";PROV="+marcaBollo.getProvinciaResidenzaDebitore()+";HASH="+Base64.encodeBase64String(marcaBollo.getHashDocumento());
		a.setDatiBody(body.getBytes());
		a.completeForInsert();
		return a;
	}
	private VociPagamento buildVocePagamento(VoceImporto vmsg, Timestamp  tsDecorrenza, Boolean isHidden) {
		
		VociPagamento vpag  = new VociPagamento();
		vpag.setIdVoce(GeneratoreIdUnivoci.GetCurrent().generaId());
		vpag.setPrVersione(0);
		if (isHidden) {
			vpag.setStRiga("H");
		} else {
			vpag.setStRiga("V");
		}
		vpag.setTsDecorrenza(tsDecorrenza);
		vpag.setCoAccertamento(vmsg.getCodiceAccertamento());
		vpag.setCoCapbilancio(vmsg.getCapitoloBilancio());
		vpag.setCoVoce(vmsg.getCodice());
		vpag.setDeVoce(vmsg.getDescrizione());
		vpag.setImVoce(vmsg.getImporto());
		vpag.setTiVoce(vmsg.getTipo());
		vpag.setOpInserimento("IDP");
//		vpag.setOpAggiornamento();
		vpag.setTsInserimento(new Timestamp(System.currentTimeMillis()));
//		vpag.setTsAggiornamento();
		
		return vpag;
		
	}
	
	private Timestamp toTimestamp(XMLGregorianCalendar xgc) {
		return new Timestamp(xgc.toGregorianCalendar().getTime().getTime());
	}
	
//	public static void main(String[] args) {
//		for (Method m: VociPagamento.class.getMethods()) {
//			if (m.getName().startsWith("set")) System.out.println("vpag."+m.getName()+"();");
//		}
//	}
	
}
