/**
 * 27/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import com.ibatis.sqlmap.client.SqlMapClient;

import it.nch.eb.xsqlcmd.commands.db.KeyProviderIBatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.VociBilancioModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.CondizioniPagamentoModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;

/**
 * @author gdefacci
 */
public class VociBilancioDao extends KeyProviderIBatisDao {

	public VociBilancioDao(SqlMapClient sqlMapClient,
			TablesUidProvider keysProvider) {
		super(sqlMapClient, keysProvider);
	}
	
	/**
	 * 
	 * @param pendenzaModel
	 * @param condizioniPagamentoModel
	 * @param vociBilancioModel
	 */
	public void insertVociBilancio(PendenzeModel pendenzaModel, CondizioniPagamentoModel condizioniPagamentoModel, 
			VociBilancioModel vociBilancioModel) {
		vociBilancioModel.setIdPendenza(pendenzaModel.getIdPendenza());
		vociBilancioModel.setIdCondizione(condizioniPagamentoModel.getIdCondizione());
		vociBilancioModel.setIdVoce(newVociBilancioId( ));
		vociBilancioModel.setTsDecorrenza(pendenzaModel.getTsDecorrenza());
		insert("insertVociBilancio", vociBilancioModel);
	}
	
	
	
	/**
	 * 
	 * @param pendenzaModel
	 * @param condizioniPagamentoModel
	 * @param vociBilancioModel
	 */
	public void deleteVociBilancio(PendenzeModel pendenzaModel, CondizioniPagamentoModel condizioniPagamentoModel, 
			VociBilancioModel vociBilancioModel) {
		vociBilancioModel.setIdPendenza(pendenzaModel.getIdPendenza());
		vociBilancioModel.setIdCondizione(condizioniPagamentoModel.getIdCondizione());
		delete("deleteVociBilancio", vociBilancioModel);
	}	
	

	
}
