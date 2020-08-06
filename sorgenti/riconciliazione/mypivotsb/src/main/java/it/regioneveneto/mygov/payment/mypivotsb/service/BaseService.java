package it.regioneveneto.mygov.payment.mypivotsb.service;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;




public class BaseService<T,ID> {
		
	@SuppressWarnings("rawtypes")
	public Page getPaginatedList(List<?> lists, Pageable pageable) {
		return this.findPaginated(lists, pageable);
	}

	@SuppressWarnings("rawtypes")
	public Page findPaginated(List<?> lists, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<?> list;

		if (lists.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, lists.size());
			list = lists.subList(startItem, toIndex);
		}

		return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), lists.size());
	}
}