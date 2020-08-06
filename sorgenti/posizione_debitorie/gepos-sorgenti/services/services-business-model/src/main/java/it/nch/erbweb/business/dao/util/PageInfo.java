package it.nch.erbweb.business.dao.util;

import it.nch.fwk.fo.core.QueryParams;

public class PageInfo {
	private final String sqlCmd;
	private final QueryParams params;

	public PageInfo(String sqlCmd, QueryParams params) {
		this.sqlCmd = sqlCmd;
		this.params = params;
	}

	public QueryParams getParams() {
		return params;
	}

	public String getSqlCmd() {
		return sqlCmd;
	}
}
