/*
 * Creato il 3-mar-2006
 */
package it.nch.fwk.fo.pager;

/**
 * Classe che raccoglie tutti i dati necessari per la paginazione.
 * Reca i criteri di paginazione richiesti per eseguire un avanzamento di pagina.
 *
 * @author EE10802 (TroianielloG)
 */
public class PagingCriteria implements java.io.Serializable
{
	   	/**
	   	 * Indica il numero di risultati massimo per pagina.
	   	 */
	   	private int resultsPerPage;

	    /**
	     * Destinazione della paginazione richiesta:
	     * avanti di una pagina, indietro di una pagina, prima pagina, ultima pagina.
	     */
	    private String direction;

	    private int recordPosition;
	    /**
	     * Indica il numero di risultati effettivamente restituiti dalla query
	     * indipendentemente dalla paginazione.
	     */
	    private int numTotalRecords;
	    private boolean enablePaging = false;  //passing from front end is optional
	    private boolean enableCachedPaging = false;  //passing from front end is optional
	    private String userid;
	    private String orderBy;
        private String orderDirection;

		/**
		 * Indice della pagina corrente.
		 */
		private int numCurrentPage;

		/**
		 * Indice della pagina successiva richiesta.
		 */
		private int numNextPage;
		private String paginationSmart = null;
		private boolean customSqlQuery = false;

		private static final long serialVersionUID = 3206093459760846969L;



		public PagingCriteria()
		{
			this.resultsPerPage = 10; //TODO configure from properties
	        this.recordPosition = -1;
	        this.numTotalRecords = -1;
	        this.recordPosition = -1;
	        this.enablePaging = true;
	        this.enableCachedPaging = false;
			this.numCurrentPage = -1;
			this.numNextPage = -1;
			this.paginationSmart = new String("false");
		}


	    /**
		 * @return Restituisce il valore userid.
		 */
		public String getUserid() {
			return userid;
		}
		/**
		 * @param userid Il valore userid da impostare.
		 */
		public void setUserid(String userid) {
			this.userid = userid;
		}

		/**
		 * @return Restituisce il valore direction.
		 */
		public String getDirection() {
			return direction;
		}
		/**
		 * @param direction Il valore direction da impostare.
		 */
		public void setDirection(String direction) {
			this.direction = direction;
		}
		/**
		 * @return Restituisce il valore enablePaging.
		 */
		public boolean getEnablePaging() {
			return enablePaging;
		}
		/**
		 * @param enablePaging Il valore enablePaging da impostare.
		 */
		public void setEnablePaging(boolean enablePaging) {
			this.enablePaging = enablePaging;
		}
		/**
		 * @return Restituisce il valore numTotalRecords.
		 */
		public int getNumTotalRecords() {
			return numTotalRecords;
		}
		/**
		 * @param numTotalRecords Il valore numTotalRecords da impostare.
		 */
		public void setNumTotalRecords(int numTotalRecords) {
			this.numTotalRecords = numTotalRecords;
		}
		/**
		 * @return Restituisce il valore recordPosition.
		 */
		public int getRecordPosition() {
			return recordPosition;
		}
		/**
		 * @param recordPosition Il valore recordPosition da impostare.
		 */
		public void setRecordPosition(int recordPosition) {
			this.recordPosition = recordPosition;
		}
		/**
		 * @return Restituisce il valore resultsPerPage.
		 */
		public int getResultsPerPage() {
			return resultsPerPage;
		}
		/**
		 * @param resultsPerPage Il valore resultsPerPage da impostare.
		 */
		public void setResultsPerPage(int resultsPerPage) {
			this.resultsPerPage = resultsPerPage;
		}
		/**
		 * @return Returns the serialVersionUID.
		 */
		public static long getSerialVersionUID() {
			return serialVersionUID;
		}


		public boolean isEnableCachedPaging() {
			return enableCachedPaging;
		}


		public void setEnableCachedPaging(boolean enableCachedPaging) {
			this.enableCachedPaging = enableCachedPaging;
		}


		/**
		 * @return Returns the orderBy.
		 */
		public String getOrderBy() {
			return orderBy;
		}


		/**
		 * @param orderBy The orderBy to set.
		 */
		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
		}


        public String getOrderDirection() {
            return orderDirection;
        }


        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }


		public int getNumCurrentPage() {
			return numCurrentPage;
		}


		public void setNumCurrentPage(int numCurrentPage) {
			this.numCurrentPage = numCurrentPage;
		}


		public int getNumNextPage() {
			return numNextPage;
		}


		public void setNumNextPage(int numNextPage) {
			this.numNextPage = numNextPage;
		}


		public String getPaginationSmart() {
			return paginationSmart;
		}


		public void setPaginationSmart(String paginationSmart) {
			this.paginationSmart = paginationSmart;
		}


		public boolean isCustomSqlQuery() {
			return customSqlQuery;
		}


		public void setCustomSqlQuery(boolean customSqlQuery) {
			this.customSqlQuery = customSqlQuery;
		}


}
