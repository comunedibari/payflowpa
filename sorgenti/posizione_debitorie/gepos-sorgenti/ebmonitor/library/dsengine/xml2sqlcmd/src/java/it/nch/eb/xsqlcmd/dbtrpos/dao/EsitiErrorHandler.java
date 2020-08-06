/**
 * 10/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.model.ErroriEsitiPendenzaModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.EsitoPendenzaModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.OtfModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.operations.IBatisPendenzeErrorHandler;

import java.sql.Timestamp;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class EsitiErrorHandler extends KeyProviderIBatisDao implements IBatisPendenzeErrorHandler {
	
//	private static final Logger log = it.nch.eb.common.utils.resource.ResourcesUtil.createLogger(EsitiErrorHandler.class);
	private final String operatorDefault ;
	
	public EsitiErrorHandler(SqlMapClient sqlMapClient, TablesUidProvider tablesUidProvider) {
		this(sqlMapClient, tablesUidProvider, PendenzeConsts.OPERATOR_DEFAULT);
	}
	
	public EsitiErrorHandler(SqlMapClient sqlMapClient, TablesUidProvider tablesUidProvider, String operatorDefault ) {
		super(sqlMapClient, tablesUidProvider);
		this.operatorDefault = operatorDefault;
	}

	/* @Override */
	public void onError(DBError[] errors, PendenzeModel pendenza) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (errors == null || errors.length == 0) {
			EsitoPendenzaModel esito = newEsitoPendenza(pendenza, PendenzeConsts.ESITO_OK, now);
			insert("insertEsitoPendenza", esito);			
//			log.debug(" INSERTED ESITO PEND., stato = " + esito.getEsitoPendenza() + ", E2emsgid = " + esito.getE2emsgid() + ", idPendenzaEnte = " + esito.getIdPendenzaEnte());					
		} else {
			EsitoPendenzaModel esito = newEsitoPendenza(pendenza, PendenzeConsts.ESITO_KO, now);
			insert("insertEsitoPendenza", esito);
//			log.debug(" INSERTED ESITO PEND., stato = " + esito.getEsitoPendenza() + ", E2emsgid = " + esito.getE2emsgid() + ", idPendenzaEnte = " + esito.getIdPendenzaEnte());			
			for (int i = 0; i < errors.length; i++) {
				DBError error = errors[i];
				ErroriEsitiPendenzaModel errEsito = 
					newErroreEsitoPendenza(error, esito, pendenza, now);
				insert("insertErroriEsitiPendenza", errEsito);
//				log.debug(" INSERTED ERRORE ESITO PEND., stato = " + errEsito.getCodice() + " , idPendenzaEnte = " + errEsito.getIdPendenzaEnte());				
			}
		}
	}

	private ErroriEsitiPendenzaModel newErroreEsitoPendenza(DBError error,
			EsitoPendenzaModel esito,
			PendenzeModel pendenza, Timestamp now) {
		ErroriEsitiPendenzaModel errEsito = new ErroriEsitiPendenzaModel();
		errEsito.setIdErrore( newErroreEsitoPendenzaId() );
		errEsito.setIdEsitoPendenza(esito.getIdEsitoPendenza());
		if (pendenza.getIdPendenza() !=null) {
			esito.setIdPendenza(pendenza.getIdPendenza());
		} else { 
			esito.setIdPendenza(pendenza.getReplacingPendenzaId());
		}
		errEsito.setIdPendenzaEnte(pendenza.getIdPendenzaEnte());
		errEsito.setCodice(error.getErrorId());
		errEsito.setPrVersione(PendenzeConsts.VERSIONE_INITIAL_VALUE);
		errEsito.setOpInserimento(operatorDefault);
		errEsito.setTsInserimento(now);
		errEsito.setDescrizioneErrore(error.getXmlInfos());
		return errEsito;
	}


	private EsitoPendenzaModel newEsitoPendenza(PendenzeModel pendenza,
			String esitoPendenza,
			Timestamp now) {
		EsitoPendenzaModel esito = new EsitoPendenzaModel();
		esito.setE2emsgid(pendenza.getMsgid());
		esito.setIdEsitoPendenza(  newEsitoPendenzaId() );
		if (pendenza.getIdPendenza() !=null) {
			esito.setIdPendenza(pendenza.getIdPendenza());
		} else { 
			esito.setIdPendenza(pendenza.getReplacingPendenzaId());
		}
		esito.setTsInserimento(now);
		esito.setOpInserimento(operatorDefault);
		esito.setPrVersione(PendenzeConsts.VERSIONE_INITIAL_VALUE);
		esito.setIdPendenzaEnte(pendenza.getIdPendenzaEnte());
		esito.setSenderId(pendenza.getSenderId());  
		esito.setSenderSys(pendenza.getIdSystem());
		esito.setEsitoPendenza(esitoPendenza);
		esito.setStato(PendenzeConsts.STATO_ESITO_COMPLETO);
		return esito;
	}

	@Override
	public void onError(DBError[] errors, OtfModel otf) {
		// TODO Auto-generated method stub
		
	}


}
