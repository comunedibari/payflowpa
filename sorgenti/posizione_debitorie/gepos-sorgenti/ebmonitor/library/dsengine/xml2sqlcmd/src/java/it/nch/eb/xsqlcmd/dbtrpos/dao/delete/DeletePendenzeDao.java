/**
 * Created on 09/mar/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao.delete;

import it.nch.eb.xsqlcmd.commands.db.BaseIbatisDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FlussoDao;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.insert.InsertPendenzeDao;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.DestinatariModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampEffect;
import it.nch.eb.xsqlcmd.dbtrpos.operations.TimestampsTablesHelper;

import java.sql.Timestamp;
import java.util.Iterator;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class DeletePendenzeDao extends BaseIbatisDao implements PendenzeModelEffect {
	
	private final PendenzeModelEffect integrator;
	
	private final String operatorDefault;

	private final FlussoDao flussoDao; 

	public DeletePendenzeDao(SqlMapClient sqlMapClient, TablesUidProvider keysProvider, PendenzeModelEffect integrator) {
		this(sqlMapClient, keysProvider, integrator, PendenzeConsts.OPERATOR_DEFAULT);
	}
	public DeletePendenzeDao(SqlMapClient sqlMapClient, 
			TablesUidProvider keysProvider, 
			PendenzeModelEffect integrator,
			String opDflt) {
		super(sqlMapClient);
		this.flussoDao = new FlussoDao(sqlMapClient, keysProvider);
		this.integrator = integrator;
		this.operatorDefault = opDflt;
	}
	
	public  void apply(PendenzeModel pendenza) {
		if (integrator!=null) integrator.apply(pendenza);
		
		if (pendenza.getDestinatari()!=null){
			for (Iterator it = pendenza.getDestinatari().iterator(); it.hasNext();) {
				DestinatariModel destinatario = (DestinatariModel) it.next();
				FlussiModel flusso = destinatario.getFlusso();
				
				Timestamp now = new Timestamp(System.currentTimeMillis());
				InsertPendenzeDao.setDatiInserimentoPendenza(pendenza, now, operatorDefault, pendenza.getPrVersione());
				
				TimestampEffect efct = InsertPendenzeDao.defaultInsertTimestampEffect(now, operatorDefault, pendenza.getPrVersione());
				TimestampsTablesHelper.instance.set(flusso, efct);
				
				//inserire il flusso non serve a niente, non si usa mai	
				//flussoDao.insertNewFlusso(pendenza, flusso);
			}
		}
	}
	

}
