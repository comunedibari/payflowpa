/**
 * 27/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.PrenotazioneAvvisiDigitaliModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.VociBilancioModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.sql.Timestamp;
import java.util.Iterator;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class CondizioniPagamentoDao extends KeyProviderIBatisDao {
	
	private final VociBilancioDao vociBilancioDao;
	private final AllegatoDao allegatoDao;

	public CondizioniPagamentoDao(SqlMapClient sqlMapClient,
			TablesUidProvider keysProvider) {
		super(sqlMapClient, keysProvider);
		this.vociBilancioDao = new VociBilancioDao(sqlMapClient, keysProvider);
		this.allegatoDao = new AllegatoDao(sqlMapClient, keysProvider);
	}

	/**
	 * 
	 * @param pendenzaModel
	 * @param condizioniPagamentoModel
	 * @param now
	 */
	public void insertCondizioniDiPagamento(PendenzeModel pendenzaModel, 
			CondizioniPagamentoModel condizioniPagamentoModel, 
			Timestamp now) {

		condizioniPagamentoModel.setIdPendenza(pendenzaModel.getIdPendenza());
		condizioniPagamentoModel.setIdCondizione(newCondizioniPagamentoId());
		condizioniPagamentoModel.setTsDecorrenza(pendenzaModel.getTsDecorrenza());
		condizioniPagamentoModel.setIdEnte(pendenzaModel.getIdEnte());
		condizioniPagamentoModel.setCdTrbEnte(pendenzaModel.getCdTrbEnte());
		
		//set ST-RIGA in funzione del VISIBILE
		if (pendenzaModel.isHidden()) {
			condizioniPagamentoModel.setStRiga("H");
			condizioniPagamentoModel.setTsAnnullamento(new Timestamp(System.currentTimeMillis()));
		} 		
		
		insert("insertCondizioniPagamento", condizioniPagamentoModel);
		// (TO DO HERE) 
		
		PrenotazioneAvvisiDigitaliModel pren = new PrenotazioneAvvisiDigitaliModel();
//		
		pren.setIdCondizione(condizioniPagamentoModel.getIdCondizione());
		pren.setIdEnte(condizioniPagamentoModel.getIdEnte());
		pren.setIdPagamento(condizioniPagamentoModel.getIdPagamento());
		pren.setNumTentativiAvviso(0L);
		pren.setCodiceAvviso(condizioniPagamentoModel.getIdPagamento());
		pren.setStatoAvviso("I");
		pren.setTipoProcesso("RT");
		pren.setOpInserimento("OTF");
		pren.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		if (pendenzaModel.getReplace()) {
			pren.setTipoOperazioneOriginale("U");
		} else {
			pren.setTipoOperazioneOriginale("C");
		}
		
		insert("insertPrenotazioneAvvisiDigitali", pren);
		
		if (condizioniPagamentoModel.getVociImporto()!=null) {
			for (Iterator it = condizioniPagamentoModel.getVociImporto().iterator(); it.hasNext();) {
				VociBilancioModel vociBilancioModel= (VociBilancioModel) it.next();
				vociBilancioDao.insertVociBilancio(pendenzaModel,condizioniPagamentoModel,vociBilancioModel);
			}
		}
		if (condizioniPagamentoModel.getAllegati()!=null) {
			allegatoDao.insertAllegatoCondizioniPagamento(condizioniPagamentoModel, now);
		}
	}

	
//	/**
//	 * 
//	 * @param pendenzaModel
//	 * @param condizioniPagamentoModel
//	 * @param now
//	 */
//	public void updateCondizioniDiPagamentoImporto(PendenzeModel pendenzaModel,
//			CondizioniPagamentoModel condizioniPagamentoModel, Timestamp now) {
//
//		condizioniPagamentoModel.setPrVersione(pendenzaModel.getPrVersione());
//
//		condizioniPagamentoModel.setTsDecorrenza(pendenzaModel.getTsAggiornamento());
//		condizioniPagamentoModel.setDtScadenza(condizioniPagamentoModel.getDtScadenza());
//		condizioniPagamentoModel.setTsAggiornamento(pendenzaModel.getTsAggiornamento());
//		condizioniPagamentoModel.setOpAggiornamento(pendenzaModel.getOpAggiornamento());
//		
//		allegatoDao.insertAllegatoCondizioniPagamento(condizioniPagamentoModel,now);
//		
//		//aggiorno le voci		
//		if (condizioniPagamentoModel.getVociImporto()!=null) {
//			//prima cancello tutte le voci
//			vociBilancioDao.deleteVociBilancio(pendenzaModel,condizioniPagamentoModel, new VociBilancioModel());
//			//e poi le reinserisco
//			for (Iterator it = condizioniPagamentoModel.getVociImporto().iterator(); it.hasNext();) {
//				VociBilancioModel vociBilancioModel= (VociBilancioModel) it.next();
//				
//				//imposto i campi fissi
//				vociBilancioModel.setTsAggiornamento(pendenzaModel.getTsAggiornamento());
//				vociBilancioModel.setOpAggiornamento(pendenzaModel.getOpAggiornamento());
//				vociBilancioModel.setTsInserimento(pendenzaModel.getTsAggiornamento());
//				vociBilancioModel.setOpInserimento(pendenzaModel.getOpAggiornamento());		
//				vociBilancioModel.setStRiga("V");
//				vociBilancioModel.setPrVersione(pendenzaModel.getPrVersione());
//				
//				vociBilancioDao.insertVociBilancio(pendenzaModel,condizioniPagamentoModel,vociBilancioModel);
//			}
//		}		
//		
//		update("updateCondizioniPagamentoImporto", condizioniPagamentoModel);
//
//	}
	
	
	
	
	/**
	 * 
	 * @param pendenzaModel
	 * @param condizioniPagamentoModel
	 * @param now
	 */
	public void updateCondizioniDiPagamento(PendenzeModel pendenzaModel,
			CondizioniPagamentoModel condizioniPagamentoModel, Timestamp now) {

		// condizioniPagamentoModel.setPrVersione(pendenzaModel.getPrVersione());
		if (condizioniPagamentoModel.getStPagamento() != null)
			condizioniPagamentoModel.setStPagamento(condizioniPagamentoModel.getStPagamento());
		condizioniPagamentoModel.setTsDecorrenza(pendenzaModel.getTsAggiornamento());
		condizioniPagamentoModel.setTsAggiornamento(pendenzaModel.getTsAggiornamento());
		condizioniPagamentoModel.setOpAggiornamento(pendenzaModel.getOpAggiornamento());
		condizioniPagamentoModel.setDtScadenza(condizioniPagamentoModel.getDtScadenza());
//updateAllegato??
		allegatoDao.insertAllegatoCondizioniPagamento(condizioniPagamentoModel,now);
		
		//aggiorno le voci		
		if (condizioniPagamentoModel.getVociImporto()!=null) {
			//prima cancello tutte le voci
			vociBilancioDao.deleteVociBilancio(pendenzaModel,condizioniPagamentoModel, new VociBilancioModel());
			//e poi le reinserisco
			for (Iterator it = condizioniPagamentoModel.getVociImporto().iterator(); it.hasNext();) {
				VociBilancioModel vociBilancioModel= (VociBilancioModel) it.next();
				
				//imposto i campi fissi
				vociBilancioModel.setTsAggiornamento(pendenzaModel.getTsAggiornamento());
				vociBilancioModel.setOpAggiornamento(pendenzaModel.getOpAggiornamento());
				vociBilancioModel.setTsInserimento(pendenzaModel.getTsAggiornamento());
				vociBilancioModel.setOpInserimento(pendenzaModel.getOpAggiornamento());		
				vociBilancioModel.setStRiga("V");
				vociBilancioModel.setPrVersione(pendenzaModel.getPrVersione());
				
				vociBilancioDao.insertVociBilancio(pendenzaModel,condizioniPagamentoModel,vociBilancioModel);
			}
		}				
		
		update("updateCondizioniPagamento", condizioniPagamentoModel);

	}
	

//	/**
//	 * 
//	 * @param pendenzaModel
//	 * @param condizioniPagamentoModel
//	 * @param now
//	 */
//	public void updateCondizioniDiPagamentoIrregolare(PendenzeModel pendenzaModel,
//			CondizioniPagamentoModel condizioniPagamentoModel, Timestamp now) {
//
//		// condizioniPagamentoModel.setPrVersione(pendenzaModel.getPrVersione());
//
//		condizioniPagamentoModel.setTsDecorrenza(pendenzaModel.getTsAggiornamento());
//		condizioniPagamentoModel.setTsAggiornamento(pendenzaModel.getTsAggiornamento());
//		condizioniPagamentoModel.setOpAggiornamento(pendenzaModel.getOpAggiornamento());
//		
//		condizioniPagamentoModel.setTsAnnullamento(pendenzaModel.getTsAggiornamento());
//		condizioniPagamentoModel.setOpAnnullamento(pendenzaModel.getOpAggiornamento());		
//		update("updateCondizioniPagamentoUpdateStatus", condizioniPagamentoModel);
//
//	}	
	

}
