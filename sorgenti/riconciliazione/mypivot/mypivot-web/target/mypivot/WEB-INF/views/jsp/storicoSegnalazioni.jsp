<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.net.URLEncoder"%>
<script type="text/javascript">
function changePage(pageNum){
	var form = $('#visualizzaStoricoSegnalazioniForm');
	form.find('#pageNum').val(pageNum);
	form.submit();
}
</script>

<div>
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.visualizzaCompleta.title" /> / <span class="txt-titolo"><spring:message code="mypivot.storicosegnalazioni.title" /></span>
		</h1>
	</div>
	<div class="filtro-segnalazioni">
		<form action="visualizzaStoricoSegnalazioni.html" id="visualizzaStoricoSegnalazioniForm" method="get" class="form-horizontal">
			<input type="hidden" id="pageNum" name="pageNum" value="${filter.pageNum}"/>
			<input type="hidden" name="prevPageSize" value="${filter.pageSize}"/>
			<div class="debiti_content_filters">
				<div class="form-inline form-actions">
					<div class="row-fluid">
						<div class="span3">
							<c:set var="checkboxTitle"><spring:message code="mypivot.storicosegnalazioni.filter.disable"/></c:set>
							<label><spring:message code="mypivot.storicosegnalazioni.filter.codiceiuv" /></label><br />
							<input type="text" id="codiceIuvFilter" name="codiceIuv" ${filter.codiceIuvEnabled == true?'':'disabled'} class="input-x-large" value="${filter.codiceIuv}" />
							<input type="checkbox" name="codiceIuvEnabled" ${filter.codiceIuvEnabled == true?'checked':''} class="input-medium"  onchange="changeEnableByElmId('codiceIuvFilter', this)" title="${checkboxTitle}"/>
							<br /> 
							<label><spring:message code="mypivot.storicosegnalazioni.filter.codiceiuf" /></label><br />
							<input type="text" id="codiceIufFilter" name="codiceIuf" ${filter.codiceIufEnabled == true?'':'disabled'} class="input-x-large" value="${filter.codiceIuf}"/>
							<input type="checkbox" name="codiceIufEnabled"  ${filter.codiceIufEnabled == true?'checked':''} class="input-medium"  onchange="changeEnableByElmId('codiceIufFilter', this)" title="${checkboxTitle}"/>
							<br /> 
							<label><spring:message code="mypivot.storicosegnalazioni.filter.codiceiud" /></label><br />
							<input type="text" id="codiceIudFilter" name="codiceIud" ${filter.codiceIudEnabled == true?'':'disabled'} class="input-x-large" value="${filter.codiceIud}"/>
							<input type="checkbox" name="codiceIudEnabled"  ${filter.codiceIudEnabled == true?'checked':''} class="input-medium" onchange="changeEnableByElmId('codiceIudFilter', this)" title="${checkboxTitle}" />
						</div>
						<div class="span3">
							<label><spring:message code="mypivot.storicosegnalazioni.filter.datada" /></label><br />
							<input type="text" name="dataDa" class="input-medium my-input-date" value="${filter.dataDa}"/>
							<br /> 
							<label><spring:message code="mypivot.storicosegnalazioni.filter.dataa" /></label><br />
							<input type="text" name="dataA" class="input-medium my-input-date" value="${filter.dataA}"/>
							<%--
							<br />
							<label><spring:message code="mypivot.storicosegnalazioni.filter.pagesize" /></label><br />
							<select name="pageSize" class="input-small" value="${filter.pageSize}">
								<option value="5" ${filter.pageSize == 5?'selected':''}>5</option>
								<option value="10" ${filter.pageSize == 10?'selected':''}>10</option>
								<option value="20" ${filter.pageSize == 20?'selected':''}>20</option>
							</select>
							<button type="submit" class="btn btn-info">
							<i class="fa fa-filter"></i>&nbsp;&nbsp;&nbsp;Filtra</button>
							--%>
						</div>
						<div class="span3">
							<label><spring:message code="mypivot.storicosegnalazioni.filter.attivi" /></label><br />
							<select name="attivi" class="input-medium" value="${filter.attivi}">
								<option value="" ${filter.attivi == null?'selected':''}><spring:message code="mypivot.storicosegnalazioni.filter.attivi.tutti" /></option>
								<option value="true" ${filter.attivi == true?'selected':''} ><spring:message code="mypivot.storicosegnalazioni.filter.attivi.true" /></option>
								<option value="false" ${filter.attivi == false?'selected':''} ><spring:message code="mypivot.storicosegnalazioni.filter.attivi.false" /></option>
							</select>
							<br /> 
							<label><spring:message code="mypivot.storicosegnalazioni.filter.classificazionecompletezza" /></label><br />
							<select class="input-x-large" name="classificazioneCompletezza">
								<option value="" ${empty  filter.classificazioneCompletezza?'selected':''}><spring:message code="mypivot.storicosegnalazioni.filter.classificazionecompletezza.tutti" /></option>  
								<c:forEach var="ctd" items="${listaClassificazioni}">
									<option value="${ctd}" ${filter.classificazioneCompletezza == ctd?'selected':''}>
										<spring:message code="mypivot.classificazioni.${ctd}" />
									</option>
								</c:forEach>
							</select>
						</div>
						<div class="span3">
							<label><spring:message code="mypivot.storicosegnalazioni.filter.nascosti" /></label><br />
							<select name="nascosti" class="input-medium" value="${filter.nascosti}">
								<option value="" ${filter.nascosti == null?'selected':''}><spring:message code="mypivot.storicosegnalazioni.filter.nascosti.tutti" /></option>
								<option value="true" ${filter.nascosti == true?'selected':''} ><spring:message code="mypivot.storicosegnalazioni.filter.nascosti.true" /></option>
								<option value="false" ${filter.nascosti == false?'selected':''} ><spring:message code="mypivot.storicosegnalazioni.filter.nascosti.false" /></option>
							</select>
							<br /> 
							<label><spring:message code="mypivot.storicosegnalazioni.filter.codfeduserid" /></label><br />
							<select class="input-medium" name="codFedUserId">
								<option value="" ${empty  filter.codFedUserId?'selected':''}><spring:message code="mypivot.storicosegnalazioni.filter.codfeduserid.tutti" /></option>  
								<c:forEach var="utente" items="${utenti}">
									<option value="${utente.codFedUserId}" ${filter.codFedUserId == utente.codFedUserId?'selected':''}>
										<c:out value="${utente.deLastname}"/>&nbsp;<c:out value="${utente.deFirstname}"/> 
									</option>
								</c:forEach>
							</select>
						</div>					
					</div>
					<div class="align-center">
					<!--
						<br />
						<label><spring:message code="mypivot.storicosegnalazioni.filter.pagesize" /></label><br />
						<select name="pageSize" class="input-small" value="${filter.pageSize}">
							<option value="5" ${filter.pageSize == 5?'selected':''}>5</option>
							<option value="10" ${filter.pageSize == 10?'selected':''}>10</option>
							<option value="20" ${filter.pageSize == 20?'selected':''}>20</option>
						</select>
						<button type="submit" class="btn btn-info">
						<i class="fa fa-filter"></i>&nbsp;&nbsp;&nbsp;Filtra</button>
-->

						<spring:message code="mypivot.storicosegnalazioni.filter.pagesize" />
						<br />
						<select name="pageSize" class="input-small" value="${filter.pageSize}">
							<option value="5" ${filter.pageSize == 5?'selected':''}>5</option>
							<option value="10" ${filter.pageSize == 10?'selected':''}>10</option>
							<option value="20" ${filter.pageSize == 20?'selected':''}>20</option>
						</select>
						<button type="submit" class="btn" value="visualizzaCompletaCommand">
							<i class="fa fa-search"></i>&nbsp;&nbsp;&nbsp;<spring:message code="mypivot.button.cerca" />
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="elenco-segnalazioni">
		<c:if test="${storicoSegnalazioni==null || fn:length(storicoSegnalazioni.segnalazioni) == 0}">
			<div class="row-fluid elenco-segnalazioni-vuoto">
				<div class="span12 elenco-segnalazioni-elm">
					<p><spring:message code="mypivot.storicosegnalazioni.elenco.empty" /></p>
				</div>
			</div>
		</c:if>
		<c:if test="${storicoSegnalazioni!=null && fn:length(storicoSegnalazioni.segnalazioni) > 0}">
			<div class="row-fluid elenco-segnalazioni-testata">
					<div class="span1 elenco-segnalazioni-elm">
						<h2><spring:message code="mypivot.storicosegnalazioni.elenco.header.stato" /></h2>
					</div>
					<div class="span1 elenco-segnalazioni-elm">
						<h2><spring:message code="mypivot.storicosegnalazioni.elenco.header.nome" /></h2>
					</div>
					<div class="span1 elenco-segnalazioni-elm">
						<h2><spring:message code="mypivot.storicosegnalazioni.elenco.header.modificato" /></h2>
					</div>
					<div class="span2 elenco-segnalazioni-elm">
						<h2><spring:message code="mypivot.storicosegnalazioni.elenco.header.classcompletezza" /></h2>
					</div>
					<div class="span4 elenco-segnalazioni-elm">
						<h2><spring:message code="mypivot.storicosegnalazioni.elenco.header.iuv" /> - <spring:message code="mypivot.storicosegnalazioni.elenco.header.iuf" /> - <spring:message code="mypivot.storicosegnalazioni.elenco.header.iud" /></h2>
					</div>
					<div class="span2 elenco-segnalazioni-elm">
						<h2><spring:message code="mypivot.storicosegnalazioni.elenco.header.nota" /></h2>
					</div>
			</div>
			<c:forEach var="segnalazione" items="${storicoSegnalazioni.segnalazioni}">
				<div class="row-fluid elenco-segnalazioni-corpo">
					<div class="span1 elenco-segnalazioni-elm">
						<c:if test="${segnalazione.flgNascosto}">
							<a href="javascript:void(0);" type="button" class="btn btn-danger btn-medium bottone-testata-disabilitato" title="Nascosto">
								<i class="icon-eye-close"></i>
							</a>
						</c:if>
						<c:if test="${!segnalazione.flgNascosto}">
							<a href="javascript:void(0);" type="button" class="btn btn-success btn-medium bottone-testata-disabilitato" title="Non Nascosto">
								<i class="icon-eye-open"></i>
							</a>
						</c:if>
						<c:if test="${segnalazione.flgAttivo}">
							<a href="javascript:void(0);" type="button" class="btn btn-success btn-medium bottone-testata-disabilitato" title="Attivo">
								<i class="icon-ok"></i>
							</a>
						</c:if>
						<c:if test="${!segnalazione.flgAttivo}">
							<a href="javascript:void(0);" type="button" class="btn btn-danger btn-medium bottone-testata-disabilitato" title="Non Attivo">
								<i class="icon-remove"></i>
							</a>
						</c:if>						
					</div>
					<div class="span1 elenco-segnalazioni-elm">
						<p>${segnalazione.deFirstname} ${segnalazione.deLastname}</p>
					</div>
					<div class="span1 elenco-segnalazioni-elm">
						<p><fmt:formatDate pattern="dd/MM/yy HH:mm:ss" value="${segnalazione.dtCreazione}"/></p>
					</div>
					<div class="span2 elenco-segnalazioni-elm">
						<p><spring:message code="mypivot.classificazioni.${segnalazione.classificazioneCompletezza}" /></p>
					</div>
					<div class="span4 elenco-segnalazioni-elm">
						<span>${segnalazione.codIuv!=null?segnalazione.codIuv:'n/a'}</span> - 
						<span>${segnalazione.codIuf!=null?segnalazione.codIuf:'n/a'}</span> - 
						<span>${segnalazione.codIud!=null?segnalazione.codIud:'n/a'}</span>
					</div>
					<div class="span2 elenco-segnalazioni-elm" style="text-align: left;">
						<p>${segnalazione.deNota}</p>
					</div>
				</div>
			</c:forEach>
			<div class="row-fluid mypay-custom row-border">
				<div class="span12">
					<div class="pagination pagination-left">
						<spring:message code="mypivot.pager.pagina" />
						<c:out value="${storicoSegnalazioni.currPage}" />
						<spring:message code="mypivot.pager.di" />
						<c:out value="${storicoSegnalazioni.totPage}" />
						-
						<spring:message code="mypivot.pager.elementiDa" />
						<c:out
							value="${storicoSegnalazioni.elemDa}" />
						<spring:message code="mypivot.pager.a" />
						<c:out
							value="${storicoSegnalazioni.elemA}" />
					</div>
					<div>
						<div class="pagination pagination-right">
							<ul>
								<c:if test="${storicoSegnalazioni.prev}">
									<li>
										<a href="javascript:void(0);" onclick="changePage(1)">
											<span><spring:message code="mypivot.pager.prima" /></span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);" onclick="changePage(${storicoSegnalazioni.currPage-1})">
											<span>«</span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);" onclick="changePage(${storicoSegnalazioni.currPage-1})">
											<span><c:out value="${storicoSegnalazioni.currPage-1}"/></span>
										</a>
									</li>
								</c:if>
								<li class="disabled">
									<span><c:out value="${storicoSegnalazioni.currPage}"/></span>
								</li>
								<c:if test="${storicoSegnalazioni.next}">
									<li>
										<a href="javascript:void(0);" onclick="changePage(${storicoSegnalazioni.currPage+1})">
											<span><c:out value="${storicoSegnalazioni.currPage+1}"/></span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);" onclick="changePage(${storicoSegnalazioni.currPage+1})">
											<span>»</span>
										</a>
									</li>
									<li>
										<a href="javascript:void(0);" onclick="changePage(${storicoSegnalazioni.totPage})">
											<span><spring:message code="mypivot.pager.ultima" /></span>
										</a>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div class="well carrello clearfix">
			<div class="btn-group pull-left">
				<a href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html" class="btn btn-large">
					<i class="fa fa-chevron-circle-left fa-lg"></i>&nbsp;&nbsp;&nbsp;
					<spring:message code="mypivot.dettaglio.btn.indietro" /> 
				</a>
			</div>
		</div>
	</div>
</div>