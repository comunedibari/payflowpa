/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * @author Giorgio Vallini
 * 
 */
public class FlussiUploadCommand {

	private String ente;
	private String myBoxContextURL;
	private Boolean showUploadForm;
	private String authorizationToken;
	private String requestToken;
	private String uploadPath;

	private String dateFrom;
	private String dateTo;
	private String fullTextSearch;
	private int page;
	private int pageSize;

	private String tFlusso;

	private String cod;

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
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

	public FlussiUploadCommand() {
		super();
	}

	public Boolean getShowUploadForm() {
		return showUploadForm;
	}

	public void setShowUploadForm(Boolean showUploadForm) {
		this.showUploadForm = showUploadForm;
	}

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	public String getMyBoxContextURL() {
		return myBoxContextURL;
	}

	public void setMyBoxContextURL(String myBoxContextURL) {
		this.myBoxContextURL = myBoxContextURL;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String gettFlusso() {
		return tFlusso;
	}

	public void settFlusso(String tFlusso) {
		this.tFlusso = tFlusso;
	}

}
