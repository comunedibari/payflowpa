/*
 * Created on Apr 9, 2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.insert;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeDataDecoder;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeValidator;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBErrorsBag;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory.DBErrorsId;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author BastiaP
 *
 */
public class InsertPendenzeBusinessValidator extends PendenzeValidator implements PendenzeModelValidator {
	
	static final Logger log = ResourcesUtil.createLogger(InsertPendenzeBusinessValidator.class);
	
	private final PendenzeDataDecoder decoder;
	private final DBErrorsId errIds;

	public InsertPendenzeBusinessValidator(SqlMapClient sqlMapClient, PendenzeDBErrorsFactory errFactory) {
		super(sqlMapClient, errFactory);
		this.decoder = new PendenzeDataDecoder(sqlMapClient, errFactory);
		errIds = errFactory.errorIds;
	}
	
	/**
	 * 
	 * @param pendenza
	 * @param errs
	 */
	public void decodeEnteTributo(PendenzeModel pendenza, DBErrorsBag errs) {
		String idEnte = decoder.decodeIdEnte(pendenza, errs);
		
		if (idEnte != null && decoder.checkPendenzaExists(pendenza)){
			errs.add( decoder.getDbErrorsFactory().error(errIds.pendenzaDuplicata, pendenza) );
		}
		if (errs.isEmpty()) {
			decoder.decodeIdTributo(pendenza, errs);	
		}
	}
	
	/**
	 * 
	 */
	public void checkCondizionePagamento(PendenzeModel pendenza,
			CondizioniPagamentoModel cp, DBErrorsBag errs) {
		super.checkCondizionePagamento(pendenza, cp, errs);
		checkNewCondizionePagamento(pendenza, cp, errs);
		checkCondizioniPagamentoRimborso(null, pendenza, pendenza.getCondizioniDiPagamento(), errs);
	}

	/**
	 * 
	 */
	public DBError[] apply(PendenzeModel pendenza) {
		DBErrorsBag errs = new DBErrorsBag();
		decodeEnteTributo(pendenza, errs);
		if (errs.isEmpty()) {
			checkPendenza(pendenza, errs);
		}
		return errs.getErrors();
	}

}
