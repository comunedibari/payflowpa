/**
 * 23/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathSegment;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope.Entry;
import it.nch.eb.xsqlcmd.dbtrpos.error.DBError;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.operations.IBatisPendenzeErrorHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class PendenzaXPathValidator {

	private static final Map operations = new HashMap() {
		private static final long serialVersionUID = 1L;
	{
		put("insert", "Insert");
		put("update", "UpdateStatus");
		put("replace", "Replace");
		put("delete", "Delete");
		put("updateMassivo", "UpdateMassivo");
	}};

	private final IBatisPendenzeErrorHandler errHandler;
	private final PendenzeDBErrorsFactory errorsFactory;

	public PendenzaXPathValidator(IBatisPendenzeErrorHandler errHandler,
			PendenzeDBErrorsFactory errorsFactory) {
		this.errHandler = errHandler;
		this.errorsFactory = errorsFactory;
	}

	private String[] allOpertionsExcept(String op) {
		String[] res = new String[operations.size()-1];
		int i = 0;
		for (Iterator it = operations.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			String opNm = (String) entry.getKey();
			if (!opNm.equals(op)) {
				res[i] = (String) entry.getValue();
				i++;
			}
		}
		return res;
	};

	public DBError[] validate(XPathPosition pos,
			XPathsMapBindings xpathBindings, PendenzeModel mdl) {
		String currentyOp = mdl.getOperationName();
		String[] exclOps = allOpertionsExcept(currentyOp);
		String firstInconsistentOperation = checkForInvalidXPath(pos, xpathBindings, exclOps);
		if (firstInconsistentOperation!=null) {
			DBError[] errs = new DBError[] {
				errorsFactory.error(
						errorsFactory.errorIds.xmlOperationInconsistent,
						mdl,
						errorsFactory.xmlErrrorsFactory.xmlOperationInconsistent(mdl, firstInconsistentOperation))
			};
			try {
				errHandler.getSqlMapClient().startTransaction();
				errHandler.onError(errs, mdl);
				errHandler.getSqlMapClient().getCurrentConnection().commit();
				errHandler.getSqlMapClient().commitTransaction();
			} catch (Exception e) {
			} finally {
				endTransaction(errHandler.getSqlMapClient());
			}
			return errs;
		} else {
			return null;
		}
	}

	private void endTransaction(SqlMapClient sqlMapClient) {
		try {
			sqlMapClient.endTransaction();
		} catch (SQLException e) {
		}
	}

	private String checkForInvalidXPath(XPathPosition pos,
			XPathsMapBindings xpathBindings, String[] exclOps) {
		XPathPosition[] exclPosPrfx = new XPathPosition[exclOps.length];
		for (int i = 0; i < exclPosPrfx.length; i++) {
			exclPosPrfx[i] = new XPathPosition(pos, new XPathSegment(null, exclOps[i] ));
		}
		Iterator xpaths = xpathBindings.entries();

		for (Iterator it = xpaths; it.hasNext();) {
			Entry entry = (Entry) it.next();
			BaseXPathPosition cpos = (BaseXPathPosition) entry.getPosition();
			for (int xpi = 0; xpi < exclPosPrfx.length; xpi++) {
				if (cpos.startsWith(exclPosPrfx[xpi])) {
					return exclOps[xpi];
				}
			}
		}
		return null;
	}

}
