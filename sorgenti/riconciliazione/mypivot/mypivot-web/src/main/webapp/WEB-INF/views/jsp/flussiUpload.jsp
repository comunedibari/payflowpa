<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.FlussiUploadCommand"%>
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

		jQuery('#input_dt_data_ora_flusso').datetimepicker({
			datepicker : true,
			timepicker : true,
			format : 'd/m/Y H:i:s',
			mask : true,
			lang : "it"
		});

		jQuery('#input_dt_data_regolamento').datetimepicker({
			datepicker : true,
			timepicker : false,
			format : 'd/m/Y',
			mask : true,
			lang : "it"
		});
	});
</script>
<!-- TITOLO PAGINA -->
<div class="titolo-pagina">
	<h1>
		<spring:message code="mypivot.flussi.title" /> / <span class="txt-titolo"><spring:message code="mypivot.flussi.title.import" /></span>
	</h1>
</div>
<div>
	<ul class="nav nav-tabs">
		<li class="<c:if test="${tipoFlusso eq 'E'}">active</c:if>"><a
			href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=E"><spring:message
					code="mypivot.carica.flussiExport" /></a></li>

		<li class="<c:if test="${tipoFlusso eq 'R'}">active</c:if>"><a
			href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=R"><spring:message
					code="mypivot.carica.flussiRendicontazioneStandard" /></a></li>

		<%--<li class="<c:if test="${tipoFlusso eq 'P'}">active</c:if>"><a
			href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=P"><spring:message
					code="mypivot.carica.flussiRendicontazionePoste" /></a></li>--%>
		<c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}">
			<li class="<c:if test="${tipoFlusso eq 'T'}">active</c:if>"><a
				href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=T"><spring:message
						code="mypivot.carica.flussiRendicontazioneTesoreria" /></a></li>
		</c:if>
		<c:if test="${isTesoreriaActive}">
			<li class="<c:if test="${tipoFlusso eq 'C'}">active</c:if>"><a
				href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=C"><spring:message
						code="mypivot.carica.flussiGiornaleDiCassaCSV" /></a></li>
		</c:if>
		<c:if test="${isTesoreriaActive}">
			<li class="<c:if test="${tipoFlusso eq 'O'}">active</c:if>"><a
				href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=O"><spring:message
						code="mypivot.carica.flussiGiornaleDiCassaOPI" /></a></li>
		</c:if>
	</ul>

	<div class="home_content">

		<h2>
			<spring:message code="mypivot.carica.title" />
			<%--: <span class="colored"> <c:if test="${tipoFlusso eq 'E'}">
					<spring:message code="mypivot.carica.flussiExport" />
				</c:if> <c:if test="${tipoFlusso eq 'R'}">
					<spring:message code="mypivot.carica.flussiRendicontazioneStandard" />
				</c:if> <c:if test="${tipoFlusso eq 'P'}">
					<spring:message code="mypivot.carica.flussiRendicontazionePoste" />
				</c:if> <c:if test="${tipoFlusso eq 'T'}">
					<spring:message
						code="mypivot.carica.flussiRendicontazioneTesoreria" />
				</c:if> <c:if test="${tipoFlusso eq 'C'}">
					<spring:message code="mypivot.carica.flussiGiornaleDiCassaCSV" />
				</c:if>

			</span>--%>
		</h2>

		<div class="divBorder row-fluid">

			<%--<c:if test="${tipoFlusso eq 'P'}">
				<div class="divPaddingTop span4">
					<form class="form-horizontal ">
						<div class="control-group">
							<label class="control-label"
								for="input_cod_identificativo_flusso"><spring:message
									code="mypivot.carica.flussiRendicontazionePoste.cod_identificativo_flusso" /></label>
							<div class="controls">
								<input type="text" id="input_cod_identificativo_flusso"
									placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_dt_data_ora_flusso"><spring:message
									code="mypivot.carica.flussiRendicontazionePoste.dt_data_ora_flusso" /></label>
							<div class="controls">
								<input type="text" id="input_dt_data_ora_flusso" placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"
								for="input_cod_identificativo_univoco_regolamento"><spring:message
									code="mypivot.carica.flussiRendicontazionePoste.cod_identificativo_univoco_regolamento" /></label>
							<div class="controls">
								<input type="text"
									id="input_cod_identificativo_univoco_regolamento"
									placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_dt_data_regolamento"><spring:message
									code="mypivot.carica.flussiRendicontazionePoste.dt_data_regolamento" /></label>
							<div class="controls">
								<input type="text" id="input_dt_data_regolamento" placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"
								for="input_num_importo_totale_pagamenti"><spring:message
									code="mypivot.carica.flussiRendicontazionePoste.num_importo_totale_pagamenti" /></label>
							<div class="controls">
								<input type="text" id="input_num_importo_totale_pagamenti"
									placeholder=""> &euro;
							</div>
						</div>

					</form>
				</div>
			</c:if>--%>

			<c:if test="${tipoFlusso eq 'C'}">
				<div class="divPaddingTop span4">
					<form class="form-horizontal ">
						<div class="control-group">
							<label class="control-label" for="input_de_anno_bolletta"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_de_anno_bolletta" /></label>
							<div class="controls">
								<input type="text" id="input_de_anno_bolletta" value="1" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_cod_bolletta"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_cod_bolletta" /></label>
							<div class="controls">
								<input type="text" id="input_cod_bolletta" value="2" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_dt_contabile"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_dt_contabile" /></label>
							<div class="controls">
								<input type="text" id="input_dt_contabile" value="3" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_de_denominazione"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_de_denominazione" /></label>
							<div class="controls">
								<input type="text" id="input_de_denominazione" value="4" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_de_causale"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_de_causale" /></label>
							<div class="controls">
								<input type="text" id="input_de_causale" value="5" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_num_importo"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_num_importo" /></label>
							<div class="controls">
								<input type="text" id="input_num_importo" value="6" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input_dt_valuta"><spring:message
									code="mypivot.carica.flussiGiornaleDiCassaCSV.input_dt_valuta" /></label>
							<div class="controls">
								<input type="text" id="input_dt_valuta" value="7" />
							</div>
						</div>

					</form>
				</div>
			</c:if>

			<div
				class="divPadding <c:if test="${tipoFlusso eq 'P' or tipoFlusso eq 'C'}">span7</c:if><c:if test="${tipoFlusso ne 'P' and tipoFlusso ne 'C'}">span10</c:if>">
				<div id="upload-refresh" class="hidden">
					<a
						href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}"
						class="btn"><spring:message code="mypivot.carica.nuovoUpload" /></a>
				</div>

				<div id="upload-container">
					<input id="fileupload" type="file" name="files[]"
						accept="application/zip"
						data-url="${flussiUploadCommand.myBoxContextURL}/rest/upload.html?authorizationToken=${flussiUploadCommand.authorizationToken}&requestToken=${flussiUploadCommand.requestToken}&importPath=${flussiUploadCommand.uploadPath}"
						<c:if test="${not flussiUploadCommand.showUploadForm}">disabled</c:if>>

					<div id="progress" class="progress progress-striped">
						<div class="bar" style="width: 0%;"></div>
					</div>
				</div>
			</div>

		</div>

		<input type="hidden" name="requestToken" id="requestToken"
			value="${flussiUploadCommand.requestToken}">

		<jsp:include page="messages.jsp"></jsp:include>

		<h2>
			<spring:message code="mypivot.carica.listaTitle" />
			<%--: <span class="colored"> <c:if test="${tipoFlusso eq 'E'}">
					<spring:message code="mypivot.carica.flussiExport" />
				</c:if> <c:if test="${tipoFlusso eq 'R'}">
					<spring:message code="mypivot.carica.flussiRendicontazioneStandard" />
				</c:if> <c:if test="${tipoFlusso eq 'P'}">
					<spring:message code="mypivot.carica.flussiRendicontazionePoste" />
				</c:if> <c:if test="${tipoFlusso eq 'T'}">
					<spring:message
						code="mypivot.carica.flussiRendicontazioneTesoreria" />
				</c:if> <c:if test="${tipoFlusso eq 'C'}">
					<spring:message code="mypivot.carica.flussiGiornaleDiCassaCSV" />
				</c:if>
			</span>--%>
		</h2>
		<div class="debiti_content">

			<div class="debiti_content_filters">
				<form:form modelAttribute="flussiUploadCommand"
					commandName="flussiUploadCommand" method="post"
					class="form-inline form-actions">
					<div class="row-fluid">
						<div class="span3">
							<label><spring:message code="mypivot.flussi.nomeFlusso" /></label><br />
							<form:input path="fullTextSearch" class="input-medium"
								id="fullTextSearchTextbox" name="fullTextSearch" type="text"
								placeholder="search" />
						</div>
						<div class="span2_5">
							<label><spring:message code="mypivot.flussi.dataDal" />:
							</label><br />
							<form:input path="dateFrom" id="dateFrom" type="text"
								name="dateFrom" class="input-small" />
						</div>
						<div class="span2_5">
							<label><spring:message code="mypivot.flussi.dataAl" />:
							</label><br />
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

						<form:hidden id="tFlusso" path="tFlusso" value="${tipoFlusso}" />

						<div class="span2">
							<button type="submit" class="btn btn-large cerca pull-right"
								value="flussiUploadCommand">
								<spring:message code="mypivot.button.cerca" />
							</button>
						</div>
					</div>
				</form:form>
			</div>

			<div class="debiti_table">
				<table class="table table-bordered">
					<caption></caption>
					<thead>
						<tr>
							<th><spring:message code="mypivot.flussi.idFlusso" /></th>
							<th><spring:message code="mypivot.flussi.nomeFlusso" /></th>
							<th><spring:message code="mypivot.flussi.dataCaricamento" /></th>
							<th><spring:message code="mypivot.flussi.operatore" /></th>
							<th><spring:message code="mypivot.flussi.stato" /></th>
							<%-- 						<th><spring:message code="mypivot.flussi.operazioniAnnullamento" /></th> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="flussoDto" items="${flussiDtoPage.list}">
							<tr class="${flussoDto.codStato}">
								<td><c:out value="${flussoDto.idFlusso}" /></td>
								<td class="wrapfix"><c:out value="${flussoDto.nome}" /></td>
								<td><c:out value="${flussoDto.dataCaricamento}" /></td>
								<td><c:out value="${flussoDto.operatore}" /></td>
								<td><c:out value="${flussoDto.stato}" /></td>
								<%-- 							<td class="text-center"><c:if --%>
								<%-- 									test="${flussoDto.codStato == 'LOAD_FLOW' or flussoDto.codStato == 'CARICATO'}"> --%>
								<!-- 									<a class="btn btn-mini" -->
								<%-- 										href="<%=request.getContextPath()%>/protected/flussi/flussiImport.html?command=remove&cod=${flussoDto.idFlusso}"> --%>
								<!-- 										<i class="icon-remove"></i> -->
								<!-- 									</a> -->
								<%-- 								</c:if></td> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<c:if test="${flussiDtoPage.totalRecords > 0}">
				<div>
					<div class=" pagination-left">
						<spring:message code="mypivot.pager.pagina" />
						<c:out value="${flussiDtoPage.page}" />
						<spring:message code="mypivot.pager.di" />
						<c:out value="${flussiDtoPage.totalPages}" />
						-
						<spring:message code="mypivot.pager.elementiDa" />
						<c:out
							value="${(flussiDtoPage.page-1) * flussiDtoPage.pageSize + 1}" />
						<spring:message code="mypivot.pager.a" />
						<c:out
							value="${(flussiDtoPage.page-1) * flussiDtoPage.pageSize + fn:length(flussiDtoPage.list)}" />

					</div>

					<div>
						<div class="pagination pagination-right">

							<ul>
								<c:if test="${flussiDtoPage.previousPage}">
									<li><a
										href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}&pg=1&pgSize=${flussiDtoPage.pageSize}">
											<span><spring:message code="mypivot.pager.prima" /></span>
									</a></li>
								</c:if>

								<c:if test="${flussiDtoPage.previousPage}">
									<li><a
										href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}&pg=${flussiDtoPage.page-1}&pgSize=${flussiDtoPage.pageSize}">
											<span>«</span>
									</a></li>
								</c:if>

								<c:if test="${flussiDtoPage.previousPage}">
									<li><a
										href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}&pg=${flussiDtoPage.page-1}&pgSize=${flussiDtoPage.pageSize}">
											<span><c:out value="${flussiDtoPage.page-1}"></c:out></span>
									</a></li>
								</c:if>

								<li class="disabled"><span><c:out
											value="${flussiDtoPage.page}"></c:out></span></li>

								<c:if test="${flussiDtoPage.nextPage}">
									<li><a
										href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}&pg=${flussiDtoPage.page+1}&pgSize=${flussiDtoPage.pageSize}">
											<span><c:out value="${flussiDtoPage.page+1}"></c:out></span>
									</a></li>
								</c:if>

								<c:if test="${flussiDtoPage.nextPage}">
									<li><a
										href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}&pg=${flussiDtoPage.page+1}&pgSize=${flussiDtoPage.pageSize}">
											<span>»</span>
									</a></li>
								</c:if>

								<c:if test="${flussiDtoPage.nextPage}">
									<li><a
										href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=${tipoFlusso}&pg=${flussiDtoPage.totalPages}&pgSize=${flussiDtoPage.pageSize}">
											<span><spring:message code="mypivot.pager.ultima" /></span>
									</a></li>
								</c:if>


							</ul>

						</div>
					</div>
				</div>

			</c:if>
			<c:if test="${flussiDtoPage.totalRecords == 0}">
				<p class="muted text-center">Nessun flusso trovato per i
					parametri selezionati</p>
			</c:if>



		</div>

	</div>

	<script>
		$("#input_num_importo_totale_pagamenti").keydown(function(event) {
			validaImporto(event);
		});
	</script>
</div>