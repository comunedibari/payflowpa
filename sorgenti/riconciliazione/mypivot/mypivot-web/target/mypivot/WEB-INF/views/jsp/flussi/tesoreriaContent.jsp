<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoTesoreriaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLEncoder"%>

<jsp:include page="../messages.jsp"></jsp:include>

<c:if test="${flussoTesoreriaDtoPage.totalRecords > 0}">
	<div class="debiti_table">

		<!-- intestazione -->
		<div class="row-fluid mypay-custom">
			<div class="span12 text-center tesoreriaHeader row-border border-right-0">
				<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
			</div>
		</div>

		<c:forEach var="flussoTesoreriaDto" items="${flussoTesoreriaDtoPage.list}">
			<c:set var="flussoTesoreria" scope="request" value="${flussoTesoreriaDto.flussoTesoreriaTO}"/>

			<div class="row-fluid mypay-custom small-text-no-padding tesoreriaRowBackground">

				<div class="span11 tesoreriaRow padding-bottom-10">
					<div class="span4">
						<c:if test="${flussoTesoreria.deAnnoBolletta ne null}">
							<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(flussoTesoreria.deAnnoBolletta)}" />
						</c:if>
						
						<c:if test="${flussoTesoreria.codBolletta ne null}">
							<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(flussoTesoreria.codBolletta)}" />
						</c:if>
					
						<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
						<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoTesoreria.dtDataValutaRegione}" />
						
						<label><spring:message code="mypivot.visualizza.causaleRiversamento" />&nbsp;:</label>
						<c:out value="${fn:trim(flussoTesoreria.deCausale)}" />
					</div>
					<div class="span4">
						<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
						<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoTesoreria.dtBolletta}" />
					
						<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
						<c:out value="${flussoTesoreriaDto.deImporto}" />&nbsp;&euro;<br />
						
						<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
						<c:out value="${flussoTesoreria.deCognome}" />
					</div>
					<div class="span4">
						<c:if test="${flussoTesoreria.dtRicezione ne null}">
							<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
							<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${flussoTesoreria.dtRicezione}"/>
						</c:if>
						
						<c:if test="${flussoTesoreria.codIdUnivocoFlusso ne null}">
							<label><spring:message code="mypivot.visualizza.flussoRendicontazione" />&nbsp;:</label>
							<c:out value="${fn:trim(flussoTesoreria.codIdUnivocoFlusso)}" />
						</c:if>
						
						<c:if test="${flussoTesoreria.codIdUnivocoVersamento ne null}">
							<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;:</label>
							<c:out value="${fn:trim(flussoTesoreria.codIdUnivocoVersamento)}" />
						</c:if>
					</div>
				</div>
				<div class="span1 tesoreriaRow padding-bottom-10 border-right-0 txt-center">
					<label>Azioni</label>
					<br />
					<c:if test="${flussoTesoreria.codIdUnivocoFlusso ne null}">
						<a href="javascript:void(0);" onclick="visualizzaRendicontazione('${flussoTesoreria.codIdUnivocoFlusso}')" type="button" class="btn btn-small btn-primary" title="Dettaglio"><i class="fa fa-search">&nbsp;Dettaglio</i></a>
						<br /><br />
					</c:if>
					<c:if test="${flussoTesoreria.codIdUnivocoVersamento ne null}">
						<a href="javascript:void(0);" onclick="visualizzaRicevuta('${flussoTesoreria.codIdUnivocoVersamento}')" type="button" class="btn btn-small btn-primary" title="Dettaglio"><i class="fa fa-search">&nbsp;Dettaglio</i></a>
						<br /><br />
					</c:if>
					<a href="javascript:void(0);" onclick="espandiTesoreria('${flussoTesoreria.deAnnoBolletta}', '${flussoTesoreria.codBolletta}')" class="btn btn-small btn-info" id="espandiButton"><i class="fa fa-plus">&nbsp;Espandi</i></a>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row-fluid mypay-custom row-border">
		<div class="span12">
			<div class="pagination pagination-left">
				<spring:message code="mypivot.pager.pagina" />
				<c:out value="${flussoTesoreriaDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${flussoTesoreriaDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(flussoTesoreriaDtoPage.page-1) * flussoTesoreriaDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(flussoTesoreriaDtoPage.page-1) * flussoTesoreriaDtoPage.pageSize + fn:length(flussoTesoreriaDtoPage.list)}" />

			</div>

			<div>
				<div class="pagination pagination-right">

					<ul>
						<c:if test="${flussoTesoreriaDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaTesoreria.html?pg=1&pgSize=${flussoTesoreriaDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${flussoTesoreriaDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaTesoreria.html?pg=${flussoTesoreriaDtoPage.page-1}&pgSize=${flussoTesoreriaDtoPage.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${flussoTesoreriaDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaTesoreria.html?pg=${flussoTesoreriaDtoPage.page-1}&pgSize=${flussoTesoreriaDtoPage.pageSize}">
									<span><c:out value="${flussoTesoreriaDtoPage.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out
									value="${flussoTesoreriaDtoPage.page}"></c:out></span></li>

						<c:if test="${flussoTesoreriaDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaTesoreria.html?pg=${flussoTesoreriaDtoPage.page+1}&pgSize=${flussoTesoreriaDtoPage.pageSize}">
									<span><c:out value="${flussoTesoreriaDtoPage.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${flussoTesoreriaDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaTesoreria.html?pg=${flussoTesoreriaDtoPage.page+1}&pgSize=${flussoTesoreriaDtoPage.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${flussoTesoreriaDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaTesoreria.html?pg=${flussoTesoreriaDtoPage.totalPages}&pgSize=${flussoTesoreriaDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${flussoTesoreriaDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>