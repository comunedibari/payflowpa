/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * @author Giorgio Vallini
 * 
 */
public class SceltaEnteCommand {

	private String ente;

	private String fullTextSearch;
	private int page;
	private int pageSize;

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
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

	public SceltaEnteCommand() {
		super();
	}

}
