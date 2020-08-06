<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRendicontazioneCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLEncoder"%>

<jsp:include page="../messages.jsp"></jsp:include>

<c:if test="${flussoRendicontazioneDtoPage.totalRecords > 0}">
	<div class="debiti_table">

		<!-- intestazione -->
		<div class="row-fluid mypay-custom">
			<div class="span12 text-center rendicontazioneHeader row-border border-right-0">
				<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
			</div>			
		</div>
				
		<c:forEach var="flussoRendicontazioneDto" items="${flussoRendicontazioneDtoPage.list}">

			<div class="row-fluid mypay-custom small-text-no-padding rendicontazioneRowBackground">

				<div class="rendicontazioneRow span11 padding-bottom-10">
					<div class="span4">
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione" />&nbsp;:</label>
						<c:out value="${flussoRendicontazioneDto.codIdentificativoFlusso}" /><br />
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;:</label>
						<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${flussoRendicontazioneDto.dtDataOraFlusso}"/><br />
					</div>

					<div class="span4">
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;:</label>
						<c:out value="${flussoRendicontazioneDto.codIdentificativoUnivocoRegolamento}" /><br />
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;:</label>
						<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoRendicontazioneDto.dtDataRegolamento}"/><br />
					</div>
					
					<div class="span4">
						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;:</label>
						<c:out value="${flussoRendicontazioneDto.deImportoTotalePagamenti}" />&nbsp;&euro;
					</div>
				</div>
				
				<div class="span1 rendicontazioneRow padding-bottom-10 border-right-0 txt-center">
					<label>Azioni</label>
					<br />
					<a href="javascript:void(0);" onclick="visualizzaRicevuta('${flussoRendicontazioneDto.codIdentificativoFlusso}')" type="button" class="btn btn-small btn-primary" title="Dettaglio"><i class="fa fa-search">&nbsp;Dettaglio</i></a>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row-fluid mypay-custom row-border">
		<div class="span12">
			<div class="pagination pagination-left">
				<spring:message code="mypivot.pager.pagina" />
				<c:out value="${flussoRendicontazioneDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${flussoRendicontazioneDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(flussoRendicontazioneDtoPage.page-1) * flussoRendicontazioneDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(flussoRendicontazioneDtoPage.page-1) * flussoRendicontazioneDtoPage.pageSize + fn:length(flussoRendicontazioneDtoPage.list)}" />

			</div>

			<div>
				<div class="pagination pagination-right">

					<ul>
						<c:if test="${flussoRendicontazioneDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaRendicontazione.html?pg=1&pgSize=${flussoRendicontazioneDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${flussoRendicontazioneDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaRendicontazione.html?pg=${flussoRendicontazioneDtoPage.page-1}&pgSize=${flussoRendicontazioneDtoPage.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${flussoRendicontazioneDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaRendicontazione.html?pg=${flussoRendicontazioneDtoPage.page-1}&pgSize=${flussoRendicontazioneDtoPage.pageSize}">
									<span><c:out value="${flussoRendicontazioneDtoPage.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out
									value="${flussoRendicontazioneDtoPage.page}"></c:out></span></li>

						<c:if test="${flussoRendicontazioneDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaRendicontazione.html?pg=${flussoRendicontazioneDtoPage.page+1}&pgSize=${flussoRendicontazioneDtoPage.pageSize}">
									<span><c:out value="${flussoRendicontazioneDtoPage.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${flussoRendicontazioneDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaRendicontazione.html?pg=${flussoRendicontazioneDtoPage.page+1}&pgSize=${flussoRendicontazioneDtoPage.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${flussoRendicontazioneDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaRendicontazione.html?pg=${flussoRendicontazioneDtoPage.totalPages}&pgSize=${flussoRendicontazioneDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${flussoRendicontazioneDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>