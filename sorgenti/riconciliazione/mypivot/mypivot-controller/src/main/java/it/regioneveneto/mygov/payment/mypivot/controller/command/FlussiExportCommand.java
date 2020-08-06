package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * @author Cristiano Perin
 */
public class FlussiExportCommand {

	private String ente;

	private String myBoxContextURL;

	private String authorizationToken;

	private String dateFrom;

	private String dateTo;

	private String fullTextSearch;

	private int page;

	private int pageSize;

	private boolean showDownloadButton;

	private String cod;

	public FlussiExportCommand() {
		super();
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getMyBoxContextURL() {
		return myBoxContextURL;
	}

	public void setMyBoxContextURL(String myBoxContextURL) {
		this.myBoxContextURL = myBoxContextURL;
	}

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowDownloadButton() {
		return showDownloadButton;
	}

	public void setShowDownloadButton(boolean showDownloadButton) {
		this.showDownloadButton = showDownloadButton;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
