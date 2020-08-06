package it.nch.erbweb.business.dao.util;

import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.core.QueryParams;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

/**
 * Classe che consente l'utilizzo di Hibernate alla stregua di Ibatis.
 *
 * @author Vergolanis
 *
 */
public class QueryMgr {
	private final List 		parsers	= new ArrayList();
	private final IfParser 	ifParser= new IfParser();


    private String queryCmd;
    private QueryLocator ql = null;
    private String queryName;

	public QueryMgr(String queryName, QueryLocator ql) {

		this (ql.getQuery(queryName));
		this.ql = ql;
		this.queryName = queryName;

	}

    public QueryMgr(String queryCmd) {
    	this.queryCmd = queryCmd;
    	parsers.add(ifParser);
    }

    public FilterParser registerFilterParser() {
    	FilterParser parser = new FilterParser();
    	parsers.add(parser);
    	return parser;
	}

    public void addIf(String alias) {
    	ifParser.addIf(alias);
	}

	public void addOrderBy(List orderByFields) {
		if (orderByFields != null) {
			StringBuffer sb = new StringBuffer(" ORDER BY ");
			Iterator iter = orderByFields.iterator();
			while (iter.hasNext()) {
				sb.append(iter.next());
				if (iter.hasNext()) {
					sb.append(", ");
				}
			}
			queryCmd = queryCmd + sb.toString();
		}
	}

	public void addOrderBy(String orderByField) {
		if (orderByField != null)
		queryCmd = queryCmd + " ORDER BY " + orderByField;
	}

    public void addInput(String alias, Object value) {
    	//Tracer.debug(this.getClass().getName(), alias + ": " + value.toString(), "");
   // 	System.out.println(this.getClass().getName()+": "+alias + ": " + value.toString());
    	ifParser.addInput(alias, value);

	}

	public boolean ifInput(String alias, Object value) {
		return ifParser.ifInput(alias, value);
	}

	public Query getHibernateQuery(Session sm) {
		String sqlCmd = prepare(this.queryCmd);

		Query query = sm.createQuery(sqlCmd);
		setInput(query);

		return query;
	}

	public SQLQuery getHibernateSqlQuery(Session sm) {
		String sqlCmd = prepare(this.queryCmd);

		SQLQuery query = sm.createSQLQuery(sqlCmd);
		setInput(query);

		if (ql != null) ql.fillQuery(this.queryName, query);

		return query;
	}
	
	public String getSqlQuery() {
		String sqlCmd = prepare(this.queryCmd);
		return getTranslatedSql(sqlCmd.trim());
	}
	/**
	 *
	 * @param context
	 * @return
	 *
	 * @deprecated
	 */
    public Query getHibernateQuery(FrontEndContext context) {
    	String sqlCmd = prepare(this.queryCmd);

		StatelessSessionManager sm = ((BackEndContext) context).getStatelessSessionManager();
		Query query = sm.createHibernateQuery(sqlCmd);
		setInput(query);
		return query;
    }

	public String[] getReturnAliases() {
		if (ql != null) return ql.getReturnAliases(this.queryName);
		else return new String[0];
	}


    private String prepare(String target) {
    	for (Iterator i = parsers.iterator(); i.hasNext();) {
    		target = ((QueryParser)i.next()).prepare(target);
    	}
    	return target;
    }

    private void setInput(Query query) {
    	for (Iterator i = parsers.iterator(); i.hasNext();) {
    		QueryParser.PlaceHolder[] placeHolders = ((QueryParser)i.next()).getInputs();
    		for (int j=0; j < placeHolders.length; j++) {
    		//	Tracer.debug(getClass().getName(), "", "QueryMgr setInput " + placeHolders[j].getAlias() + "=" +placeHolders[j].getValue());
        		setInput(query, placeHolders[j].getAlias(), placeHolders[j].getValue());
    		}
    	}
    }

    /**
     * 
     * @param querya
     * @return
     */
	private String getTranslatedSql(String querya) {
		String queryFormatted = querya;
	
		for (Iterator i = parsers.iterator(); i.hasNext();) {
			QueryParser.PlaceHolder[] placeHolders = ((QueryParser)i.next()).getInputs();
			for (int j=0; j < placeHolders.length; j++) {
				String strFormatted = getFormattedInput(placeHolders[j].getAlias(), placeHolders[j].getValue());
				queryFormatted = queryFormatted.replaceAll(":" + placeHolders[j].getAlias(), strFormatted);
			}
		}
		
		if (Tracer.isDebugEnabled(this.getClass().getName()))
			Tracer.debug(this.getClass().getName(), "TranslatedSQL", "QueryMgr - getTranslatedSQL - " + queryName + " = " + queryFormatted);

		
		return queryFormatted;
	}

    private static void setInput(Query query, String alias, Object value) {
		if (value == null) {
			query.setParameter(alias, null, new org.hibernate.type.StringType());
		//	throw new RuntimeException("SetInput (alias "+ alias + "): il tipo NULL non � gestito tra i possibili tipi di input" );

		} else if (value instanceof String) {
			query.setString(alias, (String)value);

		} else if (value instanceof Timestamp) {
			query.setTimestamp(alias, (Timestamp)value);

		} else if (value instanceof Date) {
			query.setDate(alias, (Date)value);
		} else if (value instanceof Double) {
			query.setDouble(alias, ((Double)value).doubleValue());
		} else if (value instanceof Long) {
			query.setLong(alias, ((Long)value).longValue());
		} else if (value instanceof BigDecimal) {
			query.setBigDecimal(alias, (BigDecimal)value);
		} else if (value instanceof String[]) {
			query.setParameterList(alias, (String[])value);
		} else if (value instanceof Character) {
			query.setCharacter(alias, ((Character)value).charValue());
		} else if (value instanceof Collection) {
			query.setParameterList(alias, (Collection)value);
		} else {
			throw new RuntimeException("SetInput (alias "+ alias + "): il tipo " + value.getClass().getName() + " non e' gestito tra i possibili tipi di input" );
		}
	}

    /**
     * 
     * @param alias
     * @param value
     * @return
     */
    private static String getFormattedInput(String alias, Object value) {
		final String dateFormat = "yyyy-MM-dd HH:mm:ss";
		
    	if (value == null) {
			return "'null'";
		//	throw new RuntimeException("SetInput (alias "+ alias + "): il tipo NULL non � gestito tra i possibili tipi di input" );
		} else if (value instanceof Timestamp) {
			return "'"+new SimpleDateFormat(dateFormat).format((Date)value)+"'";
		} else if (value instanceof Date) {
			return "'"+ new SimpleDateFormat(dateFormat).format((Date)value)+"'";
		} else if (value instanceof String) {
			return "'"+String.valueOf(value).replaceAll("'", "''")+"'";
		} else if (value instanceof Double) {
			return String.valueOf(value);
		} else if (value instanceof Long) {
			return String.valueOf(value);
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal)value).toString();
		} else if (value instanceof Character) {
			return "'"+String.valueOf(value)+"'";
		} else if (value instanceof List) {
			String result = "";
			if(((List)value).isEmpty())
				return result;
			
			for (String obj : (List<String>)value) {
				
				if(result.equals(""))
					result = obj;
				else
					result = result + "' , '" + obj.replaceAll("'", "''");
			}
			return "'"+result+"'";
		} else {
			throw new RuntimeException("SetInput (alias "+ alias + "): il tipo " + value.getClass().getName() + " non e' gestito tra i possibili tipi di input" );
		}
	}


	public static StatelessSessionManager getSsm(FrontEndContext fec) {
		return ((BackEndContext) fec).getStatelessSessionManager();
	}

	public static Query getHibernateQuery(String queryString, FrontEndContext fec) {
		return getSsm(fec).createHibernateQuery(queryString);
	}

    public PageInfo getPageInfo() {

    	String sqlCmd = prepare(this.queryCmd);
    	QueryParams params = new QueryParams();

    	for (Iterator i = parsers.iterator(); i.hasNext();) {
    		QueryParser.PlaceHolder[] placeHolders = ((QueryParser)i.next()).getInputs();

    		for (int j=0; j < placeHolders.length; j++) {
    			params.put(placeHolders[j].getAlias(), placeHolders[j].getValue());
    		}
    	}

    	return new PageInfo(sqlCmd, params);

    }

    public static int getRowCount(Query query) {
    	ScrollableResults result = query.scroll();
    	return result.last() ? result.getRowNumber() : 0;
    }

}
