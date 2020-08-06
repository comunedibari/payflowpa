package it.tasgroup.idp.billerservices.plugin.rfc;

import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.VociPagamento;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.Destinatario;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.DettaglioPagamentoInsertReplace;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.Pendenza;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.VoceImporto;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.PendenzaInsertReplace.InfoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.Voce;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RfcToModelMapper {

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	public Pendenze mapMessagePendenzaToModel(Pendenza p, Enti ente, TributiEnti tributo, String sil, Boolean isHidden) {
		
		// Diamo per assodato che p.getInsert() sia diverso da null in quanto controllato a priori dal chiamante.
		
		Timestamp tsDecorrenza = new Timestamp(System.currentTimeMillis());  
				
		Pendenze pp = new Pendenze();
		if (p.getInsert().getAnnoRiferimento()!=null) {
			pp.setAnnoRiferimento(p.getInsert().getAnnoRiferimento().getYear());
		}	
		pp.setCdTrbEnte(p.getTipoPendenza());
		pp.setCoAbi(null);
		if (p.getInsert().getRiscossore()!=null) {
			pp.setCoRiscossore(p.getInsert().getRiscossore().getId());
			pp.setRiferimento(p.getInsert().getRiscossore().getRiferimento());
		}	
		pp.setDeCausale(p.getInsert().getDescrizioneCausale());
		pp.setDeMittente(p.getMittente().getDescrizione());
		if (p.getInsert().getDivisa()!=null) {
			pp.setDvRiferimento(p.getInsert().getDivisa().value());
		}	
		pp.setFlModPagamento("S"); //Default: poi sovrascritto sotto in  base alle condizioni di pagamento
		pp.setIdEnte(ente.getIdEnte());
		if (p.getMittente()!=null) {
			pp.setIdMittente(p.getMittente().getId());
		}
		pp.setIdPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());

		pp.setIdPendenzaente(p.getIdPendenza());
		pp.setIdSystem(sil);
		pp.setIdTributo(tributo.getJlttrpa().getIdTributo());
		pp.setImTotale(p.getInsert().getImportoTotale());
		pp.setNote(p.getNote());
		pp.setOpAnnullamento(null);
		pp.setPrVersione(0);
		
		
		switch (p.getInsert().getStato()) {
		case APERTA:
			pp.setStPendenza("A");
			break;
		case CHIUSA:
			pp.setStPendenza("C");
			break;
		}
		
		if (isHidden) {
			pp.setStRiga("H");
			pp.setTsAnnullamento(new Timestamp(System.currentTimeMillis()));
		} else {
			pp.setStRiga("V");
			pp.setTsAnnullamento(null);
		}
		
		if (p.getInsert().getDataCreazione()!=null) {
			pp.setTsCreazioneente(toTimestamp(p.getInsert().getDataCreazione()) );
		}	
		pp.setTsDecorrenza(tsDecorrenza);
		if(p.getInsert().getDataEmissione()!=null) {
			pp.setTsEmissioneente(toTimestamp(p.getInsert().getDataEmissione()));
		}
		if (p.getInsert().getDataModificaEnte()!=null) {
			pp.setTsModificaente(toTimestamp(p.getInsert().getDataModificaEnte()));
		}
		if (p.getInsert().getDataPrescrizione()!=null) {
			pp.setTsPrescrizione(toTimestamp(p.getInsert().getDataPrescrizione()));
		}
		pp.setIdTributoStrutturato(null);
			
		// Condizioni Pagamento
		Set<CondizioniPagamento> condizioni=new LinkedHashSet<CondizioniPagamento>();
		
		for (InfoPagamento ipag:p.getInsert().getInfoPagamento()) {  //Non controllo il !=null, i tag sono obbligatori.
			
			String tipoRateazionePagamento=null;
			
			if(ipag.getTipoPagamento().equals(TipoPagamento.PAGAMENTO_UNICO)) {
				if ("R".equals(pp.getFlModPagamento())) {
					pp.setFlModPagamento("E");
				} else {
					pp.setFlModPagamento("S");
				}
			
				tipoRateazionePagamento="S";
				
			} else if (ipag.getTipoPagamento().equals(TipoPagamento.PAGAMENTO_A_RATE)) {
				if ("S".equals(pp.getFlModPagamento())) {
					pp.setFlModPagamento("E");
				} else {
					pp.setFlModPagamento("S");
				}

				tipoRateazionePagamento="R";
				
			}
			
			for (DettaglioPagamentoInsertReplace dettaglioPagamento:ipag.getDettaglioPagamento()) {
				CondizioniPagamento condizione = mapCondizioniToModel(dettaglioPagamento, ente, tributo, tsDecorrenza, tipoRateazionePagamento, isHidden);
				condizione.setPendenze(pp);
				if (condizione.getVociPagamento()!=null) {
					for(VociPagamento voce:condizione.getVociPagamento()) {
						voce.setIdPendenza(pp.getIdPendenza());
					}
				}
				
				condizioni.add(condizione);
			}
		}
		pp.setCondizioniPagamento(condizioni);
		
		
		// Destinatari Pendenze
		
		Set<DestinatariPendenze> destinatari  = new LinkedHashSet<DestinatariPendenze>();
	
		for (Destinatario destP:p.getDestinatari().getDestinatario()) {  //Non controllo il null in quanto tag obbligatorio
			DestinatariPendenze d=mapDestinatarioToModel(destP,tsDecorrenza,isHidden);
			d.setPendenze(pp);
			destinatari.add(d);
		}
		
		pp.setDestinatariPendenze(destinatari);
		
		pp.completeForInsert();
		
		
		return pp;
		
		
		
	}
	
	private DestinatariPendenze mapDestinatarioToModel(Destinatario d, Timestamp tsDecorrenza, boolean isHidden) {
	
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
		dp.setCoDestinatario(d.getId());
		dp.setDeDestinatario(d.getDescrizione());
		switch (d.getTipo()) {
		case CITTADINO:
			dp.setTiDestinatario("CI");
			break;
		case DELEGATO:
			dp.setTiDestinatario("DE");
			break;
		case ALTRO:
			dp.setTiDestinatario("AL");
			break;
		}
		
		dp.completeForInsert();
		
		return dp;
	}
	
	private CondizioniPagamento mapCondizioniToModel(DettaglioPagamentoInsertReplace dp,Enti ente, TributiEnti tributo, Timestamp tsDecorrenza, String tipoRateazionePagamento, Boolean isHidden) {
		CondizioniPagamento c = new CondizioniPagamento();
		
		c.setCdTrbEnte(tributo.getId().getCdTrbEnte());
		c.setIdEnte(tributo.getId().getIdEnte());
		c.setImTotale(dp.getImporto());
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
		
		if (dp.getCIP()!=null) {
			c.setCoCip(dp.getCIP().getCodice());
			c.setTiCip(dp.getCIP().getTipo());
		}
		
		if (dp.getDataFineValidita()!=null) {
			c.setDtFinevalidita(dp.getDataFineValidita().toGregorianCalendar().getTime());
		}
		if (dp.getDataInizioValidita()!=null) {
			c.setDtIniziovalidita(dp.getDataInizioValidita().toGregorianCalendar().getTime());
		}	
		
		if (dp.getDettaglioTransazione()!=null) {
			if (dp.getDettaglioTransazione().getDataPagamento()!=null) {
				c.setDtPagamento(toTimestamp(dp.getDettaglioTransazione().getDataPagamento()));
			}
			c.setDeCanalepag(dp.getDettaglioTransazione().getCanalePagamento());
			c.setImPagamento(dp.getDettaglioTransazione().getImportoPagamento());
			c.setDeMezzopagamento(dp.getDettaglioTransazione().getMezzoPagamento());
			c.setDeNotepagamento(dp.getDettaglioTransazione().getNotePagamento());

		}
		
		c.setDtScadenza(dp.getDataScadenza().toGregorianCalendar().getTime());
		c.setIdPagamento(dp.getIdPagamento());
		
		switch(dp.getStato()) {
		case PAGATO:
			c.setStPagamento("P");
			break;
		case NON_PAGABILE:
			c.setStPagamento("X");
			break;
		case NON_PAGATO:
			c.setStPagamento("N");
			break;			
		case PAGAMENTO_IRREGOLARE:
			c.setStPagamento("E");
			break;			
		}
		
		
		c.setTiPagamento(tipoRateazionePagamento);
		
		if (dp.getDettaglioImporto()!=null && dp.getDettaglioImporto().getVoce()!=null) {
			Set<VociPagamento> voci = new HashSet<VociPagamento>();
			for (VoceImporto v:dp.getDettaglioImporto().getVoce()) {
				VociPagamento voce= buildVocePagamento(v,tsDecorrenza,isHidden);
				voce.setCondizioniPagamento(c);
				voci.add(voce);
			}
			
			c.setVociPagamento(voci);  
		}
		
		// c.setAllegatiPendenza(); // TODO: mappare

		if (dp.getAccreditoPagamento()!=null) {
			c.setRagioneSocialeBeneficiario(dp.getAccreditoPagamento().getCodiceIBAN());
			c.setIbanBeneficiario(dp.getAccreditoPagamento().getBeneficiario());
		}	
		
		
		c.setCausalePagamento(dp.getCausalePagamento());
		
		c.completeForInsert();
		return c;
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
		vpag.setCoAccertamento(vmsg.getAccertamento());
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
