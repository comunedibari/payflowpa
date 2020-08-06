/**
 * Created on 24/giu/08
 */
package it.nch.eb.flatx.files.model;

import it.nch.eb.common.utils.resource.StringPredicate;
import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.files.model.TokenizedLine;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gdefacci
 */
public class LineMatchersBuilder {
	
	private String lineMatcherName;
	private List/*<IndexPredicatePair>*/ predicates = new ArrayList();
	
	static class IndexPredicatePair {
		final int index;
		final StringPredicate predicate;
		public IndexPredicatePair(int index, StringPredicate predicate) {
			this.index = index;
			this.predicate = predicate;
		}
	}
	
	static class LineMatcherProduct implements LineMatcher {
		final String lineMatcherName;
		final IndexPredicatePair[] predicatePairs;
		
		public LineMatcherProduct(String lineMatcherName, List predicates) {
			this.lineMatcherName = lineMatcherName;
			this.predicatePairs = (IndexPredicatePair[]) predicates.toArray(new IndexPredicatePair[0]);
		}

		public boolean match(TokenizedLine tokenizedLine) {
			for (int i = 0; i < predicatePairs.length; i++) {
				IndexPredicatePair pair = predicatePairs[i];
				String str = tokenizedLine.get(pair.index);
				if (!pair.predicate.match(str)) return false; // returns false on first non match
			}
			return true;
		}
	
		public String toString() {
			return lineMatcherName;
		}
	}
	
	private LineMatchersBuilder addPredicateAndReturnThis(int idx, StringPredicate pred) {
		this.predicates.add(new IndexPredicatePair(idx, pred));
		return this;
	}
	
	public LineMatchersBuilder withName(String name) {
		this.lineMatcherName = name;
		return this;
	}
	
	public LineMatchersBuilder tokenMatch(int idx, StringPredicate pred) { return addPredicateAndReturnThis(idx, pred); }
	
	public LineMatchersBuilder tokenMatch(int idx, final String tkn) { 
		return addPredicateAndReturnThis(idx, new StringPredicate() {

			private static final long serialVersionUID = 1L;

			public boolean match(String str) {
				return str.equals(tkn);
			}
		
		}); 
	}
	
	public LineMatcher create() {
		return new LineMatcherProduct(this.lineMatcherName, predicates);
	}
}
