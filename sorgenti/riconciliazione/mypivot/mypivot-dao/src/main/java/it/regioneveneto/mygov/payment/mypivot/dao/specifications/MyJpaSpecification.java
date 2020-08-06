package it.regioneveneto.mygov.payment.mypivot.dao.specifications;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.regioneveneto.mygov.payment.mypivot.dao.exception.DaoException;

public class MyJpaSpecification<T> implements Specification<T> {

	private SearchCriteria criteria;

	public MyJpaSpecification() {
	}

	public MyJpaSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		@SuppressWarnings("rawtypes")
		Class javaType;
		if (criteria.isIdField()) {
			javaType = root.get("id").get(criteria.getKey()).getJavaType();
		} 
		else if(StringUtils.isNotBlank(criteria.getChildPersistent())){
			javaType = root.get(criteria.getChildPersistent()).get(criteria.getKey()).getJavaType();
		}
		else {
			javaType = root.get(criteria.getKey()).getJavaType();
		}
		
		if (criteria.getOperation().equalsIgnoreCase(">=")) {
			if (javaType == Date.class) {
				predicates.add(builder.greaterThanOrEqualTo(root.<Date> get(criteria.getKey()), (Date) criteria.getValue()));

			} else {

				predicates.add(builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("<=")) {
			if (javaType == Date.class) {
				predicates.add(builder.lessThanOrEqualTo(root.<Date> get(criteria.getKey()), (Date) criteria.getValue()));

			} else {
				predicates.add(builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase(">")) {
			if (javaType == Date.class) {
				predicates.add(builder.greaterThan(root.<Date> get(criteria.getKey()), (Date) criteria.getValue()));

			} else {

				predicates.add(builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			if (javaType == Date.class) {
				predicates.add(builder.lessThan(root.<Date> get(criteria.getKey()), (Date) criteria.getValue()));

			} else {
				predicates.add(builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("like")) {

			if (javaType == String.class) {
				if (criteria.isIdField()) {
					Expression<String> exp = root.get("id").get(criteria.getKey());
					predicates.add(builder.like(builder.lower(exp), "%" + ((String)criteria.getValue()).toLowerCase() + "%"));
				} else {
					String value = criteria.getValue().equals("") ? "%" : "%" + criteria.getValue() + "%";
					predicates.add(builder.like(builder.lower(root.<String> get(criteria.getKey())), value.toLowerCase()));
				}
			} else {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("!=")) {
			if (javaType == String.class) {
				if (criteria.isIdField()) {
					Expression<String> exp = root.get("id").get(criteria.getKey());
					predicates.add(builder.equal(builder.lower(exp), ((String)criteria.getValue()).toLowerCase()));
				} else {
					predicates.add(builder.notEqual(builder.lower(root.<String> get(criteria.getKey())), ((String)criteria.getValue()).toLowerCase()));
				}
			} else {
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("=")) {

			if (javaType == String.class) {
				if (criteria.isIdField()) {
					Expression<String> exp = root.get("id").get(criteria.getKey());
					predicates.add(builder.equal(builder.lower(exp), ((String)criteria.getValue()).toLowerCase()));
				} 
				else if(StringUtils.isNotBlank(criteria.getChildPersistent())){
					Expression<String> exp = root.get(criteria.getChildPersistent()).get(criteria.getKey());
					predicates.add(builder.equal(builder.lower(exp), ((String)criteria.getValue()).toLowerCase()));
				}
				else {
					predicates.add(builder.equal(builder.lower(root.<String> get(criteria.getKey())), ((String)criteria.getValue()).toLowerCase()));
				}
			} else {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("eqOrNull")) {
			if (javaType == String.class) {
				Expression<String> exp;
				if (criteria.isIdField()) {
					exp = root.get("id").get(criteria.getKey());
				} else {
					exp = root.get(criteria.getKey());
				}
				String val = (String) criteria.getValue();
				if (StringUtils.isEmpty(val)) {
					predicates.add(builder.isNull(exp));
				} else {
					predicates.add(builder.equal(builder.lower(exp), val.toLowerCase()));
				}
			} else {
				Object val = criteria.getValue();
				if (val == null) {
					predicates.add(builder.isNull(root.get(criteria.getKey())));
				} else {
					predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				}
			}
		} else if (criteria.getOperation().equalsIgnoreCase("isNull")) {
			if (criteria.isIdField()) {
				predicates.add(builder.isNull(root.get("id").get(criteria.getKey())));
			} else {
				predicates.add(builder.isNull(root.get(criteria.getKey())));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("notNull")) {
			if (criteria.isIdField()) {
				predicates.add(builder.isNotNull(root.get("id").get(criteria.getKey())));
			} else {
				predicates.add(builder.isNotNull(root.get(criteria.getKey())));
			}
		} else if (criteria.getOperation().equalsIgnoreCase("distinct")) {
			query.distinct(true);
		} else if (criteria.getOperation().equalsIgnoreCase("in")) {
			if (criteria.isIdField()) {
				predicates.add(root.get("id").get(criteria.getKey()).in(criteria.getValue()));
			} else {
				predicates.add(root.get(criteria.getKey()).in(criteria.getValue()));
			}

		} else {
			throw new DaoException(
					"Predicato non aggiunto! Operazione non riconosciuta [" + criteria.getOperation() + "]");
		}

		return andTogether(predicates, builder);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
