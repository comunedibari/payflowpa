<%@page import="it.regioneveneto.mydictionary.controller.command.RicercaCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>


<div>
	<h2>
		<spring:message code="myDict.ricerca.title" />
	</h2>
	<div class="ricerca_content">

		<div class="my_dict_content_filters">
			<form:form commandName="ricercaCommand" method="POST" class="form-inline form-actions">
				<div class="row">
					<div class="span2">
						<label><spring:message code="myDict.modello.codice" /></label><br />
						<form:input path="code_fullTextSearch" class="input-medium" id="codiceTextbox" name="codice" type="text" placeholder="search" />
					</div>
					<div class="span2">
						<label><spring:message code="myDict.modello.descrizione" /></label><br />
						<form:input path="desc_fullTextSearch" class="input-medium" id="descrizioneTextbox" name="descrizione" type="text" placeholder="search" />
					</div>

					<div class="span2">
						<spring:message code="myDict.pager.numElements" />
						<br />
						<form:select class="pagerSelect" itemLabel="pageSize" itemValue="pageSize" path="pageSize">
							<form:option value="10" />
							<form:option value="20" />
							<form:option value="30" />
							<form:option value="50" />
						</form:select>
					</div>
					<div class="span1">
						<button type="submit" class="btn btn-large cerca" name="action" value="ricerca">
							<spring:message code="myDict.button.cerca" />
						</button>
					</div>
				</div>
			</form:form>
		</div>

		<jsp:include page="messages.jsp"></jsp:include>

		<div class="modelli_table">
			<table class="table table-bordered">
				<caption></caption>
				<thead>
					<tr>
						<th><spring:message code="myDict.modello.codice" /></th>
						<th><spring:message code="myDict.modello.descrizione" /></th>
						<th style="width: 95px;"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="modelloDto" items="${modelloDtoPage.list}">
						<tr>
							<td><c:out value="${modelloDto.codice}" /></td>
							<td><c:out value="${modelloDto.descrizione}" /></td>
							<td><a class="btn btn-mini" href="${pageContext.request.contextPath}/scarica.html?codice=${modelloDto.codice}"> <i class="icon-download"></i>
							</a> <a class="btn btn-mini" href="${pageContext.request.contextPath}/modifica.html?codice=${modelloDto.codice}"> <i class="icon-edit"></i>
							</a>
								<form action="<%=request.getContextPath()%>/cancella.html?codice=${modelloDto.codice}" method="POST" style="display: inline-block;">
									<button type="submit" class="btn btn-mini" style="display: inline-block;">
										<i class="icon-trash"></i>
									</button>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${not empty modelloDtoPage.list}">
				<form:form commandName="ricercaCommand" method="POST">
					<form:hidden path="code_fullTextSearch"/>
					<form:hidden path="desc_fullTextSearch"/>
					<button type="submit" class="btn btn-large cerca" name="action" value="esporta">
						<spring:message code="myDict.button.esporta" text="myDict.button.esporta" />
					</button>
				</form:form>
			</c:if>
		</div>

		<div>
			<div class="pagination-left">
				<div>
					<spring:message code="myDict.pager.pagina" />
					<c:out value="${modelloDtoPage.page}" />
					<spring:message code="myDict.pager.di" />
					<c:out value="${modelloDtoPage.totalPages}" />
					-
					<spring:message code="myDict.pager.elementiDa" />
					<c:out value="${(modelloDtoPage.page - 1) * modelloDtoPage.pageSize + 1}" />
					<spring:message code="myDict.pager.a" />
					<c:out value="${modelloDtoPage.page * modelloDtoPage.pageSize}" />

				</div>
			</div>

			<div>
				<div class="pagination  pagination-right">

					<ul>
						<c:if test="${modelloDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/ricerca.html?
							pg=1&
							code_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getCode_fullTextSearch())%>&
							desc_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getDesc_fullTextSearch())%>&
							pgSize=${ricercaCommand.pageSize}">
									<span><spring:message code="myDict.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${modelloDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/ricerca.html?
							pg=${ricercaCommand.page-1}&
							code_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getCode_fullTextSearch())%>&
							desc_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getDesc_fullTextSearch())%>&
							pgSize=${ricercaCommand.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${modelloDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/ricerca.html?
							pg=${ricercaCommand.page-1}&
							code_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getCode_fullTextSearch())%>&
							desc_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getDesc_fullTextSearch())%>&
							pgSize=${ricercaCommand.pageSize}">
									<span><c:out value="${ricercaCommand.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out value="${ricercaCommand.page}"></c:out></span></li>

						<c:if test="${modelloDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/ricerca.html?
							pg=${ricercaCommand.page+1}&
							code_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getCode_fullTextSearch())%>&
							desc_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getDesc_fullTextSearch())%>&
							pgSize=${ricercaCommand.pageSize}">
									<span><c:out value="${ricercaCommand.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${modelloDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/ricerca.html?
							pg=${ricercaCommand.page+1}&
							code_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getCode_fullTextSearch())%>&
							desc_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getDesc_fullTextSearch())%>&
							pgSize=${ricercaCommand.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${modelloDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/ricerca.html?
							pg=${modelloDtoPage.totalPages}&
							code_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getCode_fullTextSearch())%>&
							desc_fSearch=<%= URLEncoder.encode(((RicercaCommand)request.getAttribute("ricercaCommand")).getDesc_fullTextSearch())%>&
							pgSize=${ricercaCommand.pageSize}">
									<span><spring:message code="myDict.pager.ultima" /></span>
							</a></li>
						</c:if>


					</ul>

				</div>
			</div>
		</div>
	</div>

</div>