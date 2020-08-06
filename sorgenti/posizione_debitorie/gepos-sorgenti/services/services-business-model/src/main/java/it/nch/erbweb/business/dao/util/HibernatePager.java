package it.nch.erbweb.business.dao.util;

import it.nch.fwk.fo.core.SessionManagerHibernate;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.dto.DTOPo;
import it.nch.fwk.fo.dto.DTOPoImpl;
import it.nch.fwk.fo.pager.ListPager;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Classe che si occupa della paginazione di uno ScrollableResults ottenuto
 * eseguendo una query con Hibernate.
 * 
 * @author Vergolanis
 * 
 */
public class HibernatePager extends ListPager {

	static final String FIRST_COMMAND = "first";
	static final String LAST_COMMAND = "last";
	static final String PREV_COMMAND = "prev";
	static final String NEXT_COMMAND = "next";
	static final String PAGE_COMMAND = "page";
	static final String CURRENT_COMMAND = "current";
	static final String PAGE_FIRST = "1";
	static final int UNKNOWN_LAST_PAGE_NUMBER = -2;
	static final int FIRST_PAGE_NUMBER = 1;
	static final int NUM_PAGES_FOR_SEGMENT = 2;
	private static int NUM_MAX_RIGHE = 100;
	
	static{
		
		try {
			
			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("webapp.properties");
			
			String righe = props.getProperty("iris.sistema.pagamenti.creditori.errorelimiterighe");
			NUM_MAX_RIGHE = Integer.valueOf(righe).intValue(); 
			
			Tracer.debug(HibernatePager.class.getName(), "static initializer", "Inizializzato il num max di righe");
			
		} catch(Exception e) {
			Tracer.error(HibernatePager.class.getName(), "static initializer", "error", e);
		}
		
	}
	
	

	/**
	 * Il PagingCriteria in ingresso fornisce tutte le informazioni relative
	 * alla richiesta dell'utente di un avanzamento paginato nel ResultSet.
	 * Questo metodo imposta i campi di questo oggetto necessari per
	 * visualizzare la pagina successiva richiesta, in base a calcoli effettuati
	 * partendo dai valori provenienti dal PagingCriteria.
	 * 
	 * @param pcriteria
	 *            il PagingCriteria.
	 */
	private void getParametersForPagination(PagingCriteria pcriteria) {
		int firstNumRow = 0;
		// trovo il numero di pagine necessario per coprire tutto il resultset
		int numPages = (int) Math.ceil(((float) (this.lastNumRow) / (float) this.numRowPage));
		// System.out.println("getParametersForPagination numPages = "+numPages);
		int numPage = pcriteria.getNumCurrentPage();
		String pageCommand = pcriteria.getDirection();
		if (pageCommand == null || HibernatePager.PAGE_COMMAND.equals(pageCommand)) {
			if (pcriteria.getNumNextPage() != -1) {
				if (pcriteria.getNumNextPage() < numPages)
					numPage = pcriteria.getNumNextPage();
				else
					numPage = numPages;
			}
			if (numPage < 1)
				numPage = 1;
		} else if (HibernatePager.FIRST_COMMAND.equals(pageCommand)) {
			numPage = 1;
		} else if (HibernatePager.PREV_COMMAND.equals(pageCommand)) {
			if (numPage < 2) {
				numPage = 1;
			} else {
				numPage--;
			}
		} else if (HibernatePager.NEXT_COMMAND.equals(pageCommand)) {
			if (numPage < numPages)
				numPage++;
		} else if (HibernatePager.LAST_COMMAND.equals(pageCommand)) {
			numPage = numPages;
		} else {

		}

		firstNumRow = ((numPage - 1) * this.numRowPage);
		this.numPages = numPages;
		this.firstNumRow = firstNumRow;
		this.numPage = numPage;
	}

	/**
	 * Restituisce un DTOPo contenente una lista contenente i record dello
	 * ScrollableResults in ingresso relativi alla pagina richiesta secondo i
	 * parametri registrati nell'oggetto PagingCriteria in ingresso. Nel
	 * PagingData del DTOPo restituito vengono memorizzati i parametri
	 * risultanti dopo questo avanzamento.
	 * 
	 * @param pcriteria
	 *            il PagingCriteria
	 * @param query
	 *            l'oggetto Query di Hibernate che si vuole paginare.
	 * @param bt
	 *            il BeanTransformer
	 * @return il DTOPo contenente la lista di record della pagina richiesta
	 *         come PersistenceObject e il PagingData ottenuto dopo
	 *         l'avanzamento.
	 */
	@SuppressWarnings("unchecked")
	public DTOPo scrollWithPagination(PagingCriteria pcriteria, Query query, BeanTransformer bt) {
		PagingData pagingData = new PagingData();
		List result;

		// query.setMaxResults(pcriteria.getResultsPerPage());
		// query.setFetchSize(pcriteria.getResultsPerPage());
		ScrollableResults scrollResults = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
		result = scrollWithPagination(pcriteria, pagingData, scrollResults, bt);

		DTOPo dtopo = new DTOPoImpl();
		dtopo.setPersistenceObject(result);
		dtopo.setPagingData(pagingData);
		return dtopo;
	}
	
	public DTOPo getDTOPoPagedCountQuery(PagingCriteria pagingCriteria, StatelessSessionManager sm, String sqlQuery, String countQuery, BeanTransformer bt) throws Exception {
		
		return getDTOPoPagedQuery(pagingCriteria, sm, sqlQuery, countQuery, true, bt);
		
	}
	
	public DTOPo getDTOPoPagedQuery(PagingCriteria pagingCriteria, StatelessSessionManager sm, String sqlQuery, String countQuery, BeanTransformer bt) throws Exception {
		
		return getDTOPoPagedQuery(pagingCriteria, sm, sqlQuery, countQuery, false, bt);
		
	}

	/**
	 * 
	 * @param pagingCriteria
	 * @param sm
	 * @param sqlQuery
	 * @param countQuery
	 * @param bt
	 * @return
	 * @throws Exception
	 */
	public DTOPo getDTOPoPagedQuery(PagingCriteria pagingCriteria, StatelessSessionManager sm, String sqlQuery, String countQuery, boolean doCount, BeanTransformer bt) throws Exception {
		List result = null;
		PagingData pagingData = new PagingData();

		SessionManagerHibernate smh = (SessionManagerHibernate) sm;
		SessionFactory sf = smh.sef;
		Session sess = sf.getCurrentSession();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DTOPo dtopo = new DTOPoImpl();

		try {
			conn = sess.connection();
			
			Query q = sess.createSQLQuery(countQuery);
			
			Integer numTotalRecords = null;
			
			if (doCount){
				
				numTotalRecords = (Integer) q.uniqueResult();
			
				pagingData.setNumTotalRecords(numTotalRecords);
			}
			
			
			if (!doCount || (doCount && numTotalRecords < NUM_MAX_RIGHE)){
				
				ps = conn.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				rs = ps.executeQuery();
				
				result = scrollWithPagination(pagingCriteria, pagingData, null, bt, rs);
				
				dtopo.setPersistenceObject(result);
			}	
						
			dtopo.setPagingData(pagingData);
			
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getDTOPoPagedQuery", e.getMessage(), e);
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (sess != null)
				sess.disconnect();
		}
		return dtopo;
	}

	/**
	 * Nuova query senza scrollable
	 * 
	 * @param pagingCriteria
	 * @param sm
	 * @param sqlQuery
	 * @param countQuery
	 * @param bt
	 * @return
	 * @throws Exception
	 */
	public DTOPo getDTOPoQuery(PagingCriteria pagingCriteria, StatelessSessionManager sm, String sqlQuery, String countQuery, BeanTransformer bt) throws Exception {
		List result = new ArrayList();
		PagingData pagingData = new PagingData();

		SessionManagerHibernate smh = (SessionManagerHibernate) sm;
		SessionFactory sf = smh.sef;
		Session sess = sf.getCurrentSession();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DTOPo dtopo = null;

		try {
			conn = sess.connection();
			ps = conn.prepareStatement(sqlQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(bt.transformTuple(getRowAsObjects(rs)));
			}

			dtopo = new DTOPoImpl();
			dtopo.setPersistenceObject(result);
			dtopo.setPagingData(pagingData);
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getDTOPoPagedQuery", e.getMessage(), e);
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (sess != null)
				sess.disconnect();
		}
		return dtopo;
	}

	/**
	 * 
	 * @param pcriteria
	 * @param pdata
	 * @param scrollResults
	 * @param bt
	 * @return
	 * @throws SQLException
	 */
	public List scrollWithPagination(PagingCriteria pcriteria, PagingData pdata, ScrollableResults scrollResults, BeanTransformer bt, ResultSet rs) throws SQLException {
		List l = null;

		if (rs != null) {
			l = scrollWithPagination(pcriteria, pdata, rs, bt);
		} else {
			l = scrollWithPagination(pcriteria, pdata, scrollResults, bt);
		}
		return l;
	}

	/**
	 * Paginatione with jdbc ResultSet
	 * 
	 * @param pcriteria
	 * @param pdata
	 * @param scrollResults
	 * @param bt
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public List scrollWithPagination(PagingCriteria pcriteria, PagingData pdata, ResultSet scrollResults, BeanTransformer bt) throws SQLException {
		List list = new ArrayList();
		try {

			int lastRowNumber = -1;
			if (pcriteria == null)
				pcriteria = new PagingCriteria();

			pdata.setResultsPerPage(pcriteria.getResultsPerPage());
			// se c'è qualche risultato ovvero ha senso posizionarsi
			// sull'ultimo record trovato
			if (scrollResults != null && scrollResults.last()) {
				lastRowNumber = scrollResults.getRow();
			} else {
				pdata.setPageNumber(0);
				pdata.setNumTotalPages(0);
				pdata.setNumTotalRecords(0);
				return list;
			}
			// imposto quanti risultati voglio per ogni pagina
			this.numRowPage = pcriteria.getResultsPerPage();
			// memorizzo l'indice sul resultset dell'ultimo record
			this.lastNumRow = lastRowNumber;
			getParametersForPagination(pcriteria);
			int firstRowNumber = this.firstNumRow;
			int maxRowsNumber = this.numRowPage;
			int backSteps = firstRowNumber - lastRowNumber;
			// mi posiziono sulla prima riga della pagina richiesta
			scrollResults.relative(backSteps);
			// aggiungo alla lista da restituire le tuple trasformate
			// solo per i record della pagina richiesta
			// list.add(bt.transformTuple(scrollResults.get()));
			// int pageRows = 1;
			// while (scrollResults.next() && pageRows < maxRowsNumber) {
			// list.add(bt.transformTuple(scrollResults.get()));
			// pageRows++;
			// }
			list = this.transformTuples(bt, scrollResults, maxRowsNumber);
			// registro sul PagingData i valori calcolati
			pdata.setPageNumber(this.numPage);
			pdata.setNumTotalRecords(lastRowNumber);
			pdata.setNumTotalPages(this.numPages);
		} finally {
			if (scrollResults != null) {
				scrollResults.close();
			}
		}
		return list;
	}

	/**
	 * Restituisce una lista contenente i record dello ScrollableResults in
	 * ingresso relativi alla pagina richiesta secondo i parametri registrati
	 * nell'oggetto PagingCriteria in ingresso. Nel PagingData vengono
	 * memorizzati i parametri risultanti dopo questo avanzamento.
	 * 
	 * @param pcriteria
	 *            il PagingCriteria
	 * @param pdata
	 *            il PagingData
	 * @param scrollResults
	 *            lo ScrollableResults
	 * @param bt
	 *            il BeanTransformer
	 * @return una lista contenente i record dello ScrollableResults in ingresso
	 *         relativi alla pagina richiesta.
	 */
	public List scrollWithPagination(PagingCriteria pcriteria, PagingData pdata, ScrollableResults scrollResults, BeanTransformer bt) {
		List list = new ArrayList();
		try {
			int lastRowNumber = -1;
			pdata.setResultsPerPage(pcriteria.getResultsPerPage());
			// se c'è qualche risultato ovvero ha senso posizionarsi
			// sull'ultimo record trovato
			if (scrollResults != null && scrollResults.last()) {
				lastRowNumber = scrollResults.getRowNumber();
			} else {
				pdata.setPageNumber(0);
				pdata.setNumTotalPages(0);
				pdata.setNumTotalRecords(0);
				return list;
			}
			// imposto quanti risultati voglio per ogni pagina
			this.numRowPage = pcriteria.getResultsPerPage();
			// memorizzo l'indice sul resultset dell'ultimo record
			this.lastNumRow = lastRowNumber;
			getParametersForPagination(pcriteria);
			int firstRowNumber = this.firstNumRow;
			int maxRowsNumber = this.numRowPage;
			int backSteps = firstRowNumber - lastRowNumber;
			// mi posiziono sulla prima riga della pagina richiesta
			scrollResults.scroll(backSteps);
			// aggiungo alla lista da restituire le tuple trasformate
			// solo per i record della pagina richiesta
			// list.add(bt.transformTuple(scrollResults.get()));
			// int pageRows = 1;
			// while (scrollResults.next() && pageRows < maxRowsNumber) {
			// list.add(bt.transformTuple(scrollResults.get()));
			// pageRows++;
			// }
			list = this.transformTuples(bt, scrollResults, maxRowsNumber);
			// registro sul PagingData i valori calcolati
			pdata.setPageNumber(this.numPage);
			pdata.setNumTotalRecords(lastRowNumber);
			pdata.setNumTotalPages(this.numPages);
		} finally {
			if (scrollResults != null) {
				scrollResults.close();
			}
		}
		return list;
	}

	/**
	 * 
	 * @param bt
	 * @param scrollResults
	 * @param maxRowsNumber
	 * @return
	 */
	public List transformTuples(BeanTransformer bt, ScrollableResults scrollResults, int maxRowsNumber) {
		List list = new ArrayList();
		list.add(bt.transformTuple(scrollResults.get()));
		int pageRows = 1;
		while (scrollResults.next() && pageRows < maxRowsNumber) {
			list.add(bt.transformTuple(scrollResults.get()));
			pageRows++;
		}
		return list;
	}

	/**
	 * Trasformazione con ResultSet jdbc
	 * 
	 * @param bt
	 * @param scrollResults
	 * @param maxRowsNumber
	 * @return
	 * @throws SQLException
	 */
	public List transformTuples(BeanTransformer bt, ResultSet scrollResults, int maxRowsNumber) throws SQLException {
		List list = new ArrayList();
		int pageRows = 0;
		while (scrollResults.next() && pageRows < maxRowsNumber) {
			list.add(bt.transformTuple(getRowAsObjects(scrollResults)));
			pageRows++;
		}
		return list;
	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private Object[] getRowAsObjects(ResultSet rs) throws SQLException{
		Object[] objs = null;
		
		ResultSetMetaData rsm = rs.getMetaData();
		objs = new Object[rsm.getColumnCount()];
		for (int i = 1; i <= rsm.getColumnCount(); i++) {
			objs[i-1] = rs.getObject(i);
		}
		
		return objs;
	}

	private Object[] getRowAsObjects2(ResultSet rs) throws SQLException {
		Object[] objs = null;

		ResultSetMetaData rsm = rs.getMetaData();
		objs = new Object[rsm.getColumnCount()];
		for (int i = 1; i <= rsm.getColumnCount(); i++) {
			System.out.println("Column type" + rsm.getColumnTypeName(i));
			if ("BLOB".equalsIgnoreCase(rsm.getColumnTypeName(i))) {
				InputStream is = rs.getBinaryStream(i);
				byte[] bytes = null;
				try {
					bytes = IOUtils.toByteArray(is);
				} catch (IOException e) {
					e.printStackTrace();
				}
				objs[i - 1] = bytes;
			} else {
				objs[i - 1] = rs.getObject(i);
			}
		}

		return objs;
	}

	/**
	 * Indice della prima riga della pagina richiesta.
	 */
	public int firstNumRow = 0;

	/**
	 * Indice della pagina richiesta.
	 */
	public int numPage = 1;

	/**
	 * Numero di righe per pagina
	 */
	public int numRowPage = -1;

	/**
	 * Indice sul resultset dell'ultimo record
	 */
	public int lastNumRow = -1;

	/**
	 * Numero di pagine necessarie per visualizzare tutti i risultati.
	 */
	public int numPages = -1;

}
