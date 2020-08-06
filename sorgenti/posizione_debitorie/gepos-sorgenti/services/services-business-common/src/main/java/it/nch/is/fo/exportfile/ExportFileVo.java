package it.nch.is.fo.exportfile;

import it.nch.fwk.fo.dto.business.Pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author sadpa
 * Created 03/07/2007
 */
public class ExportFileVo implements Pojo {

	/**
	 * PK - uuid generated
	 */
	private String fileIdentifier;
	
	/**
	 * Identifies the function for which the export
	 * is requested (e.g. XBP stands for 'Rendicontazioni Bonifici')
	 */
	private String functionCode;
	
	/**
	 * CC number 
	 */
	private String senderCurrentAccountNumber; 
	
	/**
	 * CC number 
	 */
	private ArrayList senderAllCurrentAccountNumbers; 
	private ArrayList senderCards; 
	
	/**
	 * Report format. Allowed values are:
	 * <li>STD (standard = .txt)
	 * <li>Excel (MS Excel = .xls)
	 */
	private String reportFormat;
	
	/**
	 * Logged user name
	 */
	private String username;
	
	/**
	 * Company code
	 */
	private String corporate;
	
	/**
	 * Creation date of current request
	 */
	private Date requestDate;
	
	/**
	 * Constraint 'from date' for extracting records
	 */
	private Date fromDate;
	
	/**
	 * Constraint 'to date' for extracting records
	 */
	private Date toDate;
	
	/**
	 * Name of the file (without .zip extension)
	 */
	private String fileName;
	
	
	/* ===================================================== */
	/* ================ BUSINESS-SPECIFIC ================== */
	/* ===================================================== */
	
	/**
	 * Currency filter (Bonifico Estero only)
	 */
	private String currency;
	
	/**
	 * Business param1
	 */
	private String parameter1;
	
	/**
	 * Business param2
	 */
	private String parameter2;
	
	/**
	 * Business param3
	 */
	private String parameter3;
	/**
	 * Business param4
	 */
	private String parameter4;
	
	/**
	 * Business param5
	 */
	private String parameter5;
	
	/**
	 * Business param6
	 */
	private String parameter6;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String NL = "\r\n";
		
		sb.append(NL);
		sb.append("fileIdentifier .................... " + fileIdentifier);
		sb.append(NL);
		sb.append("functionCode ...................... " + functionCode);
		sb.append(NL);
		sb.append("senderCurrentAccountNumber ........ " + senderCurrentAccountNumber);
		sb.append(NL);
		sb.append("username .......................... " + username);
		sb.append(NL);
		sb.append("corporate ......................... " + corporate);
		sb.append(NL);
		sb.append("requestDate ....................... " + requestDate);
		sb.append(NL);
		sb.append("fromDate .......................... " + fromDate);
		sb.append(NL);
		sb.append("toDate ............................ " + toDate);
		sb.append(NL);
		sb.append("fileName .......................... " + fileName);
		sb.append(NL);
		sb.append("currency .......................... " + currency);
		sb.append(NL);
		sb.append("parameter1 ........................ " + parameter1);
		sb.append(NL);
		sb.append("parameter2 ........................ " + parameter2);
		sb.append(NL);
		sb.append("parameter3 ........................ " + parameter3);
		sb.append(NL);
		sb.append("parameter4 ........................ " + parameter4);
		sb.append(NL);
		sb.append("parameter5 ........................ " + parameter5);
		sb.append(NL);
		sb.append("parameter6 ........................ " + parameter6);
		
		return sb.toString();
	}

	// ==============================================
	//         GETTERS AND SETTERS
	// ==============================================
	
	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getFileIdentifier() {
		return fileIdentifier;
	}

	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getSenderCurrentAccountNumber() {
		return senderCurrentAccountNumber;
	}

	public void setSenderCurrentAccountNumber(String senderCurrentAccountNumber) {
		this.senderCurrentAccountNumber = senderCurrentAccountNumber;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getParameter1() {
		return parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	public String getParameter2() {
		return parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	public String getParameter3() {
		return parameter3;
	}

	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}

	public String getParameter4() {
		return parameter4;
	}

	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}

	public String getParameter5() {
		return parameter5;
	}

	public void setParameter5(String parameter5) {
		this.parameter5 = parameter5;
	}

	public String getParameter6() {
		return parameter6;
	}

	public void setParameter6(String parameter6) {
		this.parameter6 = parameter6;
	}

	public ArrayList getSenderAllCurrentAccountNumbers() {
		return senderAllCurrentAccountNumbers;
	}

	public void setSenderAllCurrentAccountNumbers(
			ArrayList senderAllCurrentAccountNumbers) {
		this.senderAllCurrentAccountNumbers = senderAllCurrentAccountNumbers;
	}
	
	public void setSenderCards(
			ArrayList senderCards) {
		this.senderCards = senderCards;
	}
	

	// ==== Custom getter ====
	public String getFileNameDotZip() {
		return fileName + ".zip";
	}
	

	// ==== POJO ====
	public Long getId() {
		return null;
	}

	public void setId(Long id) {
		return;
	}
	
	// ==== Utility methods ====
	public String getInClauseForAccounts() {
		StringBuffer sb = new StringBuffer();
		
		Iterator it = senderAllCurrentAccountNumbers.iterator();
		sb.append("(");
		while (it.hasNext()) {
			String conto = (String)it.next();
			conto = StringUtils.leftPad(conto,15,"0");
			sb.append("'" + conto + "'");
			if (it.hasNext())
				sb.append(",");
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	public String getInClauseForAccountsNoFilled() {
		StringBuffer sb = new StringBuffer();
		
		Iterator it = senderAllCurrentAccountNumbers.iterator();
		sb.append("(");
		while (it.hasNext()) {
			String conto = (String)it.next();
			sb.append("'" + conto + "'");
			if (it.hasNext())
				sb.append(",");
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	public String getInClauseForCards() {
		StringBuffer sb = new StringBuffer();
		
		Iterator it = senderCards.iterator();
		sb.append("(");
		while (it.hasNext()) {
			String carta = (String)it.next();
			sb.append("'" + carta + "'");
			if (it.hasNext())
				sb.append(",");
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	
	public Object clone() {
		ExportFileVo cloned = new ExportFileVo();
		
		cloned.setCorporate(this.getCorporate());
		cloned.setCurrency(this.getCurrency());
		cloned.setFileIdentifier(this.getFileIdentifier());
		cloned.setFileName(this.getFileName());
		cloned.setFromDate(this.getFromDate());
		cloned.setFunctionCode(this.getFunctionCode());
		cloned.setParameter1(this.getParameter1());
		cloned.setParameter2(this.getParameter2());
		cloned.setParameter3(this.getParameter3());
		cloned.setParameter4(this.getParameter4());
		cloned.setParameter5(this.getParameter5());
		cloned.setParameter6(this.getParameter6());
		cloned.setReportFormat(this.getReportFormat());
		cloned.setRequestDate(this.getRequestDate());
		cloned.setSenderAllCurrentAccountNumbers(this.getSenderAllCurrentAccountNumbers());
		cloned.setSenderCurrentAccountNumber(this.getSenderCurrentAccountNumber());
		cloned.setToDate(this.getToDate());
		cloned.setUsername(this.getUsername());
		
		return cloned;
	}
	
	
/*	
	public static void main (String[] args) {
		ExportFileVo efvo = new ExportFileVo();
		ArrayList list = new ArrayList();
		list.add("PIPPO");
		list.add("PLUTO");
		list.add("PAPERINO");
		efvo.setSenderAllCurrentAccountNumbers(list);
		System.out.println(efvo.getInClauseForAccounts());
	}
	*/	
}
