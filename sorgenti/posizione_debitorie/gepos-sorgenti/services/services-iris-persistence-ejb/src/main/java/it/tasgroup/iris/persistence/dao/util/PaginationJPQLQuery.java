package it.tasgroup.iris.persistence.dao.util;


public class PaginationJPQLQuery {
		String query;
		String countQuery;
		String sumQuery;
		
		/**
		 *
		 * @param query JPQL query
		 * @param countQuery Count query (if exist)
		 */
		public PaginationJPQLQuery(String query, String countQuery) {
				this.query = query;
				this.countQuery = countQuery;
		}
		
		/**
		 *
		 * @param query JPQL query
		 * @param countQuery Count query (if exist)
		 */
		public PaginationJPQLQuery(String query, String countQuery, String sumQuery) {
				this.query = query;
				this.countQuery = countQuery;
				this.sumQuery = sumQuery;
		}
		
		public PaginationJPQLQuery(String query, String countQuery, String sumQuery, String countQueryRT) {
			this.query = query;
			this.countQuery = countQuery;
			this.sumQuery = sumQuery;
		}
		
		public String getQuery() {
				return query;
		}
		
		public String getCountQuery() {
				return countQuery;
		}
		
		public String getSumQuery() {
				return sumQuery;
		}
		
		public void setSumQuery(String sumQuery) {
				this.sumQuery = sumQuery;
		}

}
