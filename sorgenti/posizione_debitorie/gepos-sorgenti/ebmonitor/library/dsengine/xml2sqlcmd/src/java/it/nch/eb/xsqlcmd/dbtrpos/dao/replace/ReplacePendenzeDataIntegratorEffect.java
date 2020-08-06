/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.replace;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeDataDecoder;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.DbErrorsHolderException;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class ReplacePendenzeDataIntegratorEffect extends PendenzeValidator implements PendenzeModelEffect {

	private final PendenzeDataDecoder decoder;
	private final String operatorDefault;
	
	public ReplacePendenzeDataIntegratorEffect(SqlMapClient sqlMapClient, 
			PendenzeDBErrorsFactory dbErrorsFactory) {
		this(sqlMapClient, dbErrorsFactory, PendenzeConsts.OPERATOR_DEFAULT);
	}
	public ReplacePendenzeDataIntegratorEffect(SqlMapClient sqlMapClient, 
			PendenzeDBErrorsFactory dbErrorsFactory, String opDflt) {
		super(sqlMapClient, dbErrorsFactory);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, dbErrorsFactory);
		this.operatorDefault = opDflt;
	}
	
	public void apply(PendenzeModel pendenza) {
		integratePendenza(pendenza);
	}
	
	private void integratePendenza(PendenzeModel pendenza) {
		DBErrorsBag errs = new DBErrorsBag();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		it.nch.eb.xsqlcmd.dbtrpos.gen.model.PendenzeModel updPendenza = 
			decoder.selectPendenzaToUpdate(pendenza, errs);
		if (errs.isEmpty() && updPendenza!=null) {
			checkCondizioniPagamentoConsitency(updPendenza, pendenza, pendenza.getCondizioniDiPagamento(), errs);
			//aggiungere controllo su tabella PAGA
			checkPendenzeConPagamentiAssociati(updPendenza, pendenza, pendenza.getCondizioniDiPagamento(), errs);
			//controllo per non permettere la replace con pagamento rimborsato
			checkCondizioniPagamentoRimborso(updPendenza, pendenza, pendenza.getCondizioniDiPagamento(), errs);
			
			if (errs.isEmpty()) {
				updPendenza.setTsDecorrenza(now);
				updPendenza.setStRiga(PendenzeConsts.ST_RIGA_CANCELLED);
				Integer newVersion = new Integer( updPendenza.getPrVersione().intValue() + 1);
				updPendenza.setOpAnnullamento(operatorDefault);
				updPendenza.setTsAnnullamento(now);
				
				pendenza.setPrVersione(newVersion);
				pendenza.setOpAnnullamento(null);
				pendenza.setTsAnnullamento(null);
				pendenza.setReplacingPendenzaId(updPendenza.getIdPendenza());
				
				update("updatePendenzaToReplace", updPendenza);
				update("invalidateCondizioniPagamento", updPendenza);
				
				//TODO
				//aggiungere invalidate di voci
				update("invalidateVoci", updPendenza);				
				//invalidate di allegati
				update("invalidateAllegati", updPendenza);
				//invalidate di destinatari
				update("invalidateDestinatari", updPendenza);
				
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
