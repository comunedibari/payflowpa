<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>

<div>
	<div class="titolo-pagina">
		<h1>
			<span class="txt-titolo"><spring:message code="mypivot.sceltaEnte.titolo" /></span>
		</h1>
	</div>
	
	<c:if test="${entiDtoPage != null}">
		<div class="alert alert-info">
			<blockquote>
				<p>
					<spring:message code="mypivot.sceltaEnte.descrizione" />
				</p>
			</blockquote>
		 </div>
	</c:if>	

	<div class="debiti_content">

	<!--  
		<div class="debiti_content_filters">
			<form:form commandName="sceltaEnteCommand" method="post"
				class="form-horizontal form-actions">

				<div class="row">
					<div class="span4">
						<label class="control-label"><spring:message
								code="mypivot.sceltaEnte.ente" /></label>
						<form:input path="fullTextSearch" class="input-medium"
							id="fullTextSearchTextbox" name="fullTextSearch" type="text"
							placeholder="" />
					</div>
					<div class="span4">
						<label class="control-label"><spring:message
								code="mypivot.pager.numElements" /></label>
						<form:select class="pagerSelect" itemLabel="pageSize"
							itemValue="pageSize" path="pageSize">
							<form:option value="5" />
							<form:option value="10" />
							<form:option value="20" />
						</form:select>
					</div>
					<div class="span2">
						<button type="submit" class="btn cerca" value="sceltaEnteCommand">
							<spring:message code="mypivot.button.cerca" />
						</button>
					</div>
				</div>

			</form:form>
		</div>
		-->
		<c:if test="${entiDtoPage == null}">
			<jsp:include page="messages.jsp"></jsp:include>
		</c:if>
		<c:if test="${entiDtoPage != null}">

			<c:if test="${entiDtoPage.totalRecords > 0}">
				<jsp:include page="messages.jsp"></jsp:include>
	
				<div class="centered-content">
	
					<div class="enti_table">
						<table class="table table-bordered">
							<caption></caption>
							<thead>
								<tr>
									<th><spring:message code="mypivot.sceltaEnte.ente" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="enteDto" items="${entiDtoPage.list}">
									<tr>
										<td>
										  <div class="imgIconCenterContainer">
										    <img class="imgIconCenter" src="data:image/png;base64, ${enteDto.deLogoEnte}" />
										  </div>
										  <a
											href="<%=request.getContextPath()%>/protected/changeEnte.html?enteToChange=${enteDto.codIpaEnte}&redirectUrl=${redirectUrl}">
												<span><c:out value="${enteDto.deNomeEnte}" /></span>
										    </a>
										</td>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
	
	 			<!-- 
				<div class="">
					<div class=" pagination-left">
						<spring:message code="mypivot.pager.pagina" />
						<c:out value="${entiDtoPage.page}" />
						<spring:message code="mypivot.pager.di" />
						<c:out value="${entiDtoPage.totalPages}" />
						-
						<spring:message code="mypivot.pager.elementiDa" />
						<c:out value="${(entiDtoPage.page-1) * entiDtoPage.pageSize + 1}" />
						<spring:message code="mypivot.pager.a" />
						<c:out value="${(entiDtoPage.page-1) * entiDtoPage.pageSize + fn:length(entiDtoPage.list)}" />
	
					</div>
	
					<div>
						<div class="pagination pagination-right">
	
							<ul>
								<c:if test="${entiDtoPage.previousPage}">
									<li><a
	<%-- 									href="<%=request.getContextPath()%>/sceltaEnte.html?pg=1&fSearch=<%= URLEncoder.encode(((SceltaEnteCommand)request.getAttribute("sceltaEnteCommand")).getFullTextSearch())%>&pgSize=${entiDtoPage.pageSize}"> --%>
											<span><spring:message code="mypivot.pager.prima" /></span>
									</a></li>
								</c:if>
	
								<c:if test="${entiDtoPage.previousPage}">
									<li><a
	<%-- 									href="<%=request.getContextPath()%>/sceltaEnte.html?pg=${entiDtoPage.page-1}&fSearch=<%= URLEncoder.encode(((SceltaEnteCommand)request.getAttribute("sceltaEnteCommand")).getFullTextSearch())%>&pgSize=${entiDtoPage.pageSize}"> --%>
											<span>«</span>
									</a></li>
								</c:if>
	
								<c:if test="${entiDtoPage.previousPage}">
									<li><a
	<%-- 									href="<%=request.getContextPath()%>/sceltaEnte.html?pg=${entiDtoPage.page-1}&fSearch=<%= URLEncoder.encode(((SceltaEnteCommand)request.getAttribute("sceltaEnteCommand")).getFullTextSearch())%>&pgSize=${entiDtoPage.pageSize}"> --%>
											<span><c:out value="${entiDtoPage.page-1}"></c:out></span>
									</a></li>
								</c:if>
	
								<li class="disabled"><span><c:out
											value="${entiDtoPage.page}"></c:out></span></li>
	
								<c:if test="${entiDtoPage.nextPage}">
									<li><a
	<%-- 									href="<%=request.getContextPath()%>/sceltaEnte.html?pg=${entiDtoPage.page+1}&fSearch=<%= URLEncoder.encode(((SceltaEnteCommand)request.getAttribute("sceltaEnteCommand")).getFullTextSearch())%>&pgSize=${entiDtoPage.pageSize}"> --%>
											<span><c:out value="${entiDtoPage.page+1}"></c:out></span>
									</a></li>
								</c:if>
	
								<c:if test="${entiDtoPage.nextPage}">
									<li><a
	<%-- 									href="<%=request.getContextPath()%>/sceltaEnte.html?pg=${entiDtoPage.page+1}&fSearch=<%= URLEncoder.encode(((SceltaEnteCommand)request.getAttribute("sceltaEnteCommand")).getFullTextSearch())%>&pgSize=${entiDtoPage.pageSize}"> --%>
											<span>»</span>
									</a></li>
								</c:if>
	
								<c:if test="${entiDtoPage.nextPage}">
									<li><a
	<%-- 									href="<%=request.getContextPath()%>/sceltaEnte.html?pg=${entiDtoPage.totalPages}&fSearch=<%= URLEncoder.encode(((SceltaEnteCommand)request.getAttribute("sceltaEnteCommand")).getFullTextSearch())%>&pgSize=${entiDtoPage.pageSize}"> --%>
											<span><spring:message code="mypivot.pager.ultima" /></span>
									</a></li>
								</c:if>
	
	
							</ul>
	
						</div>
					</div>
				</div>
				-->
			</c:if>
			<c:if test="${entiDtoPage.totalRecords == 0}">
				<p class="muted text-center"><spring:message code="mypivot.sceltaEnte.nessunEnte" /></p>
			</c:if>
		</c:if>
	</div>


</div>