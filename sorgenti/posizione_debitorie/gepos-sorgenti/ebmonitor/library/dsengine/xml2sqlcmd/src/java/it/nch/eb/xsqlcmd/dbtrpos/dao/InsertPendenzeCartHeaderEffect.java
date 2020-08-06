/**
 * 19/giu/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.common.utils.ConnectionProvider;
import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.model.HeaderModel;
import it.nch.eb.xsqlcmd.utils.ConnectionEffect;
import it.nch.eb.xsqlcmd.utils.DbHelper;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * TODO: just used inside tests
 * @author gdefacci
 */
public class InsertPendenzeCartHeaderEffect implements HeaderEffect {

	/** we cant move this template inside ibatis since we have to put a blob and we need inside MESSAGGIO_XML so we have to use a prepared statement */
	private static final String STM_FIELDS =
		"(E2EMSGID, SENDERID, SENDERSYS, RECEIVERID, RECEIVERSYS, MESSAGGIO_XML, TIMESTAMP_RICEZIONE, STATO, PR_VERSIONE, OP_INSERIMENTO, TS_INSERIMENTO, NUM_PENDENZE) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final ConnectionProvider connectionProvider;
	private final String pstmString;
	private final String operatorDefault; 

	public InsertPendenzeCartHeaderEffect(ConnectionProvider connectionProvider, String pendenzeCartRealTableName) {
		this(connectionProvider, pendenzeCartRealTableName, PendenzeConsts.OPERATOR_DEFAULT);
	}
	public InsertPendenzeCartHeaderEffect(ConnectionProvider connectionProvider, String pendenzeCartRealTableName, String opDefault) {
		this.connectionProvider = connectionProvider;
		this.pstmString = "insert into " + pendenzeCartRealTableName + STM_FIELDS;
		this.operatorDefault = opDefault;
	}

	/*
	 * E2EMSGID, 
	 * SENDERID, 
	 * SENDERSYS, 
	 * RECEIVERID, 
	 * RECEIVERSYS, 
	 * MESSAGGIO_XML, 
	 * TIMESTAMP_RICEZIONE, 
	 * STATO, 
	 * PR_VERSIONE, 
	 * OP_INSERIMENTO, 
	 * TS_INSERIMENTO, 
	 * NUM_PENDENZE
	 */
	public void apply(final HeaderModel model, final InputsFactory xmlStream) {
		DbHelper dbHelper = new DbHelper(connectionProvider);
		dbHelper.withConnection(new ConnectionEffect() {

			public void apply(Connection conn) throws Exception {
				PreparedStatement stm = conn.prepareStatement(pstmString);
				Timestamp now = new Timestamp(System.currentTimeMillis());
				int initialPendenzeNumber = 0;
				
				stm.setString(1, model.getSenderE2EMsgId());
				stm.setString(2, model.getSenderId());
				stm.setString(3, model.getSenderSys());
				stm.setString(4, model.getReceiverId());
				stm.setString(5, model.getReceiverSys());
				InputStream is = xmlStream.createStream();
				int avaib = is.available();
				stm.setBinaryStream(6, is, avaib);
				stm.setTimestamp(7, model.getSenderXMLCrtDt());
				stm.setString(8, PendenzeConsts.STATO_INSERITO);
				stm.setInt(9, PendenzeConsts.VERSIONE_INITIAL_VALUE.intValue());
				stm.setString(10, operatorDefault);
				stm.setTimestamp(11, now);
				/** at some point this field get updated with the real number of pendenze in the xml */
				stm.setInt(12, initialPendenzeNumber); 
				
				stm.execute();
			}
			
		});
	}

}
