package it.nch.utility.common;

import java.io.Serializable;

/**
 * @author simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryXmlVO  implements Serializable {
	static final public String QUERYTYPE_XML_XML = "XML_XML"; 
	static final public String QUERYTYPE_XML_VO = "XML_VO"; 
	private String queryXML;
	private String resultXML;
	private String queryType;

	/**
	 * 
	 */
	public QueryXmlVO() {
		this(null);
	}

	/**
	 * 
	 */
	public QueryXmlVO(String type) {
		super();
		if (type==null) this.queryType = QUERYTYPE_XML_XML; else queryType=type;
	}

	/**
	 * @return
	 */
	public String getQueryXML() {
		return queryXML;
	}

	/**
	 * @param string
	 */
	public void setQueryXML(String string) {
		queryXML = string;
	}

	/**
	 * @return
	 */
	public String getResultXML() {
		return resultXML;
	}

	/**
	 * @param string
	 */
	public void setResultXML(String string) {
		resultXML = string;
	}

	/**
	 * @return
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param string
	 */
	public void setQueryType(String string) {
		queryType = string;
	}

}