/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.delete;

import it.nch.eb.xsqlcmd.commands.db.BaseIbatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeDataDecoder;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.DbErrorsHolderException;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory.DBErrorsId;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.PrenotazioneAvvisiDigitaliModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class DeletePendenzaDataIntegratorEffect extends PendenzeValidator implements
		PendenzeModelEffect {

	private final PendenzeDataDecoder decoder;
	private final String operatorDefault; 

	public DeletePendenzaDataIntegratorEffect(SqlMapClient sqlMapClient,
			PendenzeDBErrorsFactory dbErrorsFactory) {
		this(sqlMapClient, dbErrorsFactory, PendenzeConsts.OPERATOR_DEFAULT);
	}
	public DeletePendenzaDataIntegratorEffect(SqlMapClient sqlMapClient,
			PendenzeDBErrorsFactory dbErrorsFactory, String opDflt) {
		super(sqlMapClient, dbErrorsFactory);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, dbErrorsFactory);
		this.operatorDefault = opDflt;
	}

	/* @Override */
	public void apply(PendenzeModel pendenza) {
		DBErrorsBag errs = new DBErrorsBag();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel updPendenza = decoder
				.selectPendenzaToUpdate(pendenza, errs);
		
		if (errs.isEmpty() && updPendenza!=null) {
			//aggiungere controllo su tabella PAGA
			checkPendenzeConPagamentiAssociati(updPendenza, pendenza, pendenza.getCondizioniDiPagamento(), errs);
				
			if (errs.isEmpty()) {
				updPendenza.setTsDecorrenza(now);
				updPendenza.setStRiga(PendenzeConsts.ST_RIGA_CANCELLED);
				Integer newVersion = new Integer( updPendenza.getPrVersione().intValue() + 1);
				updPendenza.setPrVersione(newVersion);
				updPendenza.setOpAnnullamento(operatorDefault);
				updPendenza.setTsAnnullamento(now);
				
				pendenza.setReplacingPendenzaId(updPendenza.getIdPendenza());
				
				update("updatePendenzaToDelete", updPendenza);
				
				
				//---- inizio lista condizioni da cancellare ---- 
				List dbCps = queryForList("selectCondizioniPagamentoToUpdate", updPendenza.getIdPendenza());
				
				for (Iterator it = dbCps.iterator(); it.hasNext();) {
					it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel  cp = (it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel) it.next();

					PrenotazioneAvvisiDigitaliModel pren = new PrenotazioneAvvisiDigitaliModel();
//					
					pren.setIdCondizione(cp.getIdCondizione());
					pren.setIdEnte(cp.getIdEnte());
					pren.setIdPagamento(cp.getIdPagamento());
					pren.setNumTentativiAvviso(0L);
					pren.setCodiceAvviso(cp.getIdPagamento());
					pren.setStatoAvviso("I");
					pren.setTipoProcesso("RT");
					pren.setOpInserimento("OTF");
					pren.setTsInserimento(new Timestamp(System.currentTimeMillis()));					
					pren.setTipoOperazioneOriginale("D");					
					
					insert("insertPrenotazioneAvvisiDigitali", pren);
				}
				//--- fine lista 
				
				//annullo anche le condizioni (TO DO HERE)
				update("invalidateCondizioniPagamento", updPendenza);
				
				//aggiungere invalidate di voci				
				update("invalidateVoci", updPendenza);				
				//aggiungere invalidate di destinatari
				update("invalidateAllegati", updPendenza);
				//aggiungere invalidate di allegati
				update("invalidateDestinatari", updPendenza);
				
				//TODO
				//aggiungere invalida anche del tributo strutturato se presente
				//invalido il tributo strutturato
				if (updPendenza.getIdTributoStrutturato()!=null) {

					if ("BOLLO_AUTO".equals(updPendenza.getCdTrbEnte())) {
						//invalidate tributo
						update("invalidateTributoStrutturato", updPendenza);	
						
						//invalidate di bollo auto
						update("invalidateBolloAuto", updPendenza);	
					}
					
				}				
			}
		}
		
		if (!errs.isEmpty()) {
			throw new DbErrorsHolderException(errs);
		}
	}
	
}
