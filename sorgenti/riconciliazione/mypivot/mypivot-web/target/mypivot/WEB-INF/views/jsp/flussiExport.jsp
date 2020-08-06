<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.FlussiExportCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#dateFrom").datepicker({
			showOn : "button",
			buttonImage : "/mypivot/images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "dd/mm/yy",
			regional : "it"
		});

		$("#dateTo").datepicker({
			showOn : "button",
			buttonImage : "/mypivot/images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "dd/mm/yy",
			regional : "it"
		});
	});
</script>

<!-- TITOLO PAGINA -->
<div class="titolo-pagina">
	<h1>
		<spring:message code="mypivot.flussi.title" /> / <span class="txt-titolo"><spring:message code="mypivot.flussi.title.export" /></span>
	</h1>
</div>

<jsp:include page="messages.jsp"></jsp:include>

<div class="debiti_content">
	<div class="debiti_content_filters">
		<form:form modelAttribute="flussiExportCommand"
			commandName="flussiExportCommand" method="post"
			class="form-inline form-actions">
			<div class="row-fluid">
				<div class="span3">
					<label><spring:message code="mypivot.flussi.nomeFlusso" /></label><br />
					<form:input path="fullTextSearch" class="input-medium"
						id="fullTextSearchTextbox" name="fullTextSearch" type="text"
						placeholder="search" />
				</div>
				<div class="span2_5">
					<label><spring:message code="mypivot.flussi.dataDal" />: </label><br />
					<form:input path="dateFrom" id="dateFrom" type="text"
						name="dateFrom" class="input-small" />
				</div>
				<div class="span2_5">
					<label><spring:message code="mypivot.flussi.dataAl" />: </label><br />
					<form:input path="dateTo" id="dateTo" type="text" name="dateTo"
						class="input-small" />
					<form:hidden path="page" />
				</div>
				<div class="span2">
					<label><spring:message code="mypivot.pager.numElements" /></label><br />
					<form:select class="pagerSelect" itemLabel="pageSize"
						itemValue="pageSize" path="pageSize">
						<form:option value="5" />
						<form:option value="10" />
						<form:option value="20" />
					</form:select>
				</div>

				<div class="span2">
					<button type="submit" class="btn btn-large cerca pull-right"
						value=flussiExportCommand>
						<spring:message code="mypivot.button.cerca" />
					</button>
				</div>
			</div>
		</form:form>
	</div>

	<table class="table table-bordered">
		<thead>
			<tr>
				<th><spring:message code="mypivot.flussi.dataPrenotazione" /></th>
				<th><spring:message code="mypivot.versioneTracciato" /></th>
				<th><spring:message code="mypivot.flussi.operatore" /></th>
				<th><spring:message code="mypivot.classificazioni" /></th>
				<th><spring:message code="mypivot.flussi.nomeFlusso" /></th>
				<th><spring:message code="mypivot.flussi.dimensione" /></th>
				<th><spring:message code="mypivot.flussi.stato" /></th>
				<th><spring:message
						code="mypivot.flussi.operazioniDownload" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="flussoDto" items="${flussiExportDtoPage.list}">
				<tr>
					<td><c:out value="${flussoDto.dataPrenotazione}" /></td>
					<td><c:out value="${flussoDto.versioneTracciato}" /></td>
					<td><c:out value="${flussoDto.operatore}" /></td>
					<td><c:out value="${flussoDto.classificazione}" /></td>
					<td class="wrapfix"><c:out value="${flussoDto.nome}" /></td>
					<td><c:out value="${flussoDto.dimensione}" /></td>
					<td><c:out value="${flussoDto.stato}" /></td>
					<td class="text-center">
						<c:if test="${flussiExportCommand.showDownloadButton}">
							<c:if test="${flussoDto.showDownloadButton}">
								<div class="btn btn-mini"
									onclick="getFile('${flussiExportCommand.myBoxContextURL}/rest/download.html?authorizationToken=${flussiExportCommand.authorizationToken}&fileName=${flussoDto.path}')">
									<i class="icon-download"></i>
								</div>
							</c:if>
						</c:if>
						<c:if test="${not flussiExportCommand.showDownloadButton}">
							<a class="btn btn-mini disabled" href=""> <i
								class="icon-warning-sign"></i>
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${flussiExportDtoPage.totalRecords > 0}">
		<div>
			<div class=" pagination-left">
				<spring:message code="mypivot.pager.pagina" />
				<c:out value="${flussiExportDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${flussiExportDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(flussiExportDtoPage.page-1) * flussiExportDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(flussiExportDtoPage.page-1) * flussiExportDtoPage.pageSize + fn:length(flussiExportDtoPage.list)}" />

			</div>

			<div>
				<div class="pagination pagination-right">

					<ul>
						<c:if test="${flussiExportDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html?pg=1&pgSize=${flussiExportDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${flussiExportDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html?pg=${flussiExportDtoPage.page-1}&pgSize=${flussiExportDtoPage.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${flussiExportDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html?pg=${flussiExportDtoPage.page-1}&pgSize=${flussiExportDtoPage.pageSize}">
									<span><c:out value="${flussiExportDtoPage.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out
									value="${flussiExportDtoPage.page}"></c:out></span></li>

						<c:if test="${flussiExportDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html?pg=${flussiExportDtoPage.page+1}&pgSize=${flussiExportDtoPage.pageSize}">
									<span><c:out value="${flussiExportDtoPage.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${flussiExportDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html?pg=${flussiExportDtoPage.page+1}&pgSize=${flussiExportDtoPage.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${flussiExportDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html?pg=${flussiExportDtoPage.totalPages}&pgSize=${flussiExportDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
						</c:if>


					</ul>

				</div>
			</div>
		</div>

	</c:if>
	<c:if test="${flussiExportDtoPage.totalRecords == 0}">
		<p class="muted text-center">Nessun flusso trovato per i parametri
			selezionati</p>
	</c:if>
</div>
