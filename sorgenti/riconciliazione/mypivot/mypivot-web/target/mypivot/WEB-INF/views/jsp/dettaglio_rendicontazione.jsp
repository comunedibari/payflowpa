<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="dettaglio rendicontazione mypay-custom small-text"> 
	<div class="testata"><h2><spring:message code="mypivot.dettaglio.rendicontazione.title"/></h2></div>
	<div class="corpo form-horizontal"> 
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.rendicontazione.idrendicontazione"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.rendicontazione.identificativoFlussoRendicontazione}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.rendicontazione.dataora"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.rendicontazione.dataOraFlussoRendicontazione}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.rendicontazione.idregolamento"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.rendicontazione.identificativoUnivocoRegolamento}</label>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.rendicontazione.dataregolamento"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.rendicontazione.deDataRegolamento}<c:if
					test="${visualizzaCompletaDto.identificativoFlussoRendicontazione == 'n/a'}">(<spring:message	code="mypivot.dettaglio.rendicontazione.dataregolamento.presunta" />)</c:if></label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.rendicontazione.importototale"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.rendicontazione.importoTotalePagamenti}</label>
			</div>
			<div class="span4">&nbsp;</div>
		</div>
		<c:if test="${dettaglio.rendicontazione.ricevute != null && dettaglio.rendicontazione.ricevute.totalRecords>0}">
			<c:forEach var="ricevutaRendicontazione" items="${dettaglio.rendicontazione.ricevute.list}" >
				<c:set var="ricevutaRendicontazione" scope="request" value="${ricevutaRendicontazione}"/>
				<tiles:insertAttribute name="dettaglio_ricevuta_rendicontazione" />
			</c:forEach>
			<div class="row-fluid mypay-custom row-border">
				<div class="span12">
					<div class="pagination pagination-left">
						<spring:message code="mypivot.pager.pagina" />
						<c:out value="${dettaglio.rendicontazione.ricevute.page}" />
						<spring:message code="mypivot.pager.di" />
						<c:out value="${dettaglio.rendicontazione.ricevute.totalPages}" />
						-
						<spring:message code="mypivot.pager.elementiDa" />
						<c:out
							value="${(dettaglio.rendicontazione.ricevute.page-1) * dettaglio.rendicontazione.ricevute.pageSize + 1}" />
						<spring:message code="mypivot.pager.a" />
						<c:out
							value="${(dettaglio.rendicontazione.ricevute.page-1) * dettaglio.rendicontazione.ricevute.pageSize + fn:length(dettaglio.rendicontazione.ricevute.list)}" />
					</div>
					<div>
						<div class="pagination pagination-right">
							<ul>
								<c:if test="${dettaglio.rendicontazione.ricevute.previousPage}">
									<li>
										<a href="javascript:void(0);" onclick="cambiaPaginaRicevutaRendicontazione('1', '${dettaglio.rendicontazione.ricevute.pageSize}')">
											<span><spring:message code="mypivot.pager.prima" /></span>
										</a>
									</li>
								</c:if>

								<c:if test="${dettaglio.rendicontazione.ricevute.previousPage}">
									<li>
										<a href="javascript:void(0);" onclick="cambiaPaginaRicevutaRendicontazione('${dettaglio.rendicontazione.ricevute.page-1}', '${dettaglio.rendicontazione.ricevute.pageSize}')">
											<span>«</span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);" onclick="cambiaPaginaRicevutaRendicontazione('${dettaglio.rendicontazione.ricevute.page-1}', '${dettaglio.rendicontazione.ricevute.pageSize}')">
											<span><c:out value="${dettaglio.rendicontazione.ricevute.page-1}"></c:out></span>
										</a>
									</li>
								</c:if>

								<li class="disabled"><span><c:out
											value="${dettaglio.rendicontazione.ricevute.page}"></c:out></span></li>

								<c:if test="${dettaglio.rendicontazione.ricevute.nextPage}">
									<li>
										<a href="javascript:void(0);" onclick="cambiaPaginaRicevutaRendicontazione('${dettaglio.rendicontazione.ricevute.page+1}', '${dettaglio.rendicontazione.ricevute.pageSize}')">
											<span><c:out value="${dettaglio.rendicontazione.ricevute.page+1}"></c:out></span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);" onclick="cambiaPaginaRicevutaRendicontazione('${dettaglio.rendicontazione.ricevute.page+1}', '${dettaglio.rendicontazione.ricevute.pageSize}')">
											<span>»</span>
										</a>
									</li>
								</c:if>

								<c:if test="${dettaglio.rendicontazione.ricevute.nextPage}">
									<li>
										<a href="javascript:void(0);" onclick="cambiaPaginaRicevutaRendicontazione('${dettaglio.rendicontazione.ricevute.totalPages}', '${dettaglio.rendicontazione.ricevute.pageSize}')">
												<span><spring:message code="mypivot.pager.ultima" /></span>
										</a>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<script type="text/javascript">
				 function cambiaPaginaRicevutaRendicontazione(page, pageSize){
			        var form = $('#visualizzaDettaglioRendicontazioneForm');
			        form.find('#page').val(page);
			        form.find('#pageSize').val(pageSize);
			        form.submit();
			    }
			</script>
			<div style="display: none;">
			    <form action="visualizzaDettaglioRendicontazione.html" id="visualizzaDettaglioRendicontazioneForm" method="get">
			        <input id="classificazioneCompletezza" name="classificazioneCompletezza" value="${dettaglio.classificazioneCompletezza}" />
			        <input id="codiceIuf" type="hidden" name="codiceIuf" value="${dettaglio.codiceIuf}"/>
					<input id="page" type="hidden" name="page"/>
					<input id="pageSize" type="hidden" name="pageSize"/>
					<input id="tipoVisualizzazione" type="hidden" name="tipoVisualizzazione" value="${segnalazioneCommand.tipoVisualizzazione}" />
			    </form>
			</div>

		</c:if>
	</div>
</div>
