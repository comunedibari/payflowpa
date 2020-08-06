<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="java.net.URLEncoder"%>


<script type="text/javascript">
	function visualizzaDettaglio(id){
		var form = $('#visualizzaDettaglioAnagraficaForm');
		form.find('#id').val(id);
		form.submit();
	}

	function cancellaAnagrafica(id){
		var form = $('#eliminaAnagraficaForm');

		form.find('#id').val(id);
		form.submit();
		}
</script>

<!-- TITOLO PAGINA -->
<div class="titolo-pagina">
	<h1>
		<spring:message code="mypivot.accertamenti.title.page.accertamento" /> / <span class="txt-titolo"><spring:message code="mypivot.anagrafica.title.gestioneAnagrafiche" /></span>
	</h1>
</div>


<div style="display: none;">
	<form action="dettaglioAnagrafica.html" id="visualizzaDettaglioAnagraficaForm" method="get" accept-charset="UTF-8">
		<input id="id" type="hidden" name="id"/>
	</form>
</div>

<div style="display: none;">
	<form action="cancellaAnagrafica.html" id="eliminaAnagraficaForm" method="get">
		<input id="id" type="hidden" name="id"/>
	</form>
</div>


<div class="row-fluid">
	
		<div class="alert alert-info <c:if test="${esitoMsg==null || esitoMsg==''}">hidden</c:if>">
			<h4> <spring:message code="mypivot.messages.info" /> :</h4> 
			<c:if test="${esitoMsg=='CANCELLATO'}">
				<spring:message code="mypivot.anagrafica.info.anagraficaCancellata.ok"/>
			</c:if>
		</div>
	</div>


<ul class="nav nav-tabs">
		<li class="<c:if test="${tab eq 'U'}">active</c:if>"><a
			href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?tab=U"><spring:message
					code="mypivot.anagrafica.gestioneAnagrafica" /></a></li>

		<li class="<c:if test="${tab eq 'I'}">active</c:if> <c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}"> hidden </c:if>"><a
			href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?tab=I"><spring:message
					code="mypivot.anagrafica.importMassivo" /></a></li>
</ul>


<!-- PORZIONE CODICE PAGINA PER ANAGRAFICA UFFICI -->
<div class="home_content table-responsive <c:if test="${tab eq 'I'}">hidden</c:if>">

	<!-- FORM FILTRI RICERCA -->
	<form:form method="post" modelAttribute="anagraficaUfficioCapitoloAccertamentoCommand" id="anagraficheFormRc" action="ricerca.html?tab=U">

		<!-- MESSAGE -->
		<div class="row-fluid">
			<tiles:insertAttribute name="message" />
		</div>
		
				
		<div class="row-fluid">
			
			<div class="debiti_content_filters">

				<div class="form-inline form-actions">


					<div class="span3">
						<!-- TIPI DOVUTO -->
						<label><spring:message
								code="mypivot.anagrafica.filter.tipoDovuto" /></label>
						<form:select class="input-block-level" path="codTipoDovuto">
							<form:option value="">
								<spring:message code="mypivot.anagrafica.select.tutti"/>
							</form:option>
							<c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}">
								<form:option value="n/a">
									<spring:message code="mypivot.anagrafica.select.nessuno" />
								</form:option>
							</c:if>
							<c:forEach var="ctd" items="${listaTipiDovuti}">
								<form:option value="${ctd.codTipo}">
									<c:out value="${ctd.deTipo}"></c:out>
								</form:option>
							</c:forEach>
						</form:select>

					</div><!-- /.span3 -->


					<div class="span3">

						<!-- Codice Ufficio-->
						<label><spring:message
								code="mypivot.anagrafica.filter.codiceUfficio" /></label>
						<form:input path="codiceUfficio" class="input-block-level"
							name="codiceUfficio" type="text" placeholder="" />

						<!-- Denominazione Ufficio-->
						<label><spring:message
								code="mypivot.anagrafica.filter.denominazioneUfficio" /></label>
						<form:input path="denominazioneUfficio" class="input-block-level"
							name="denominazioneUfficio" type="text" placeholder="" />

						<!-- FLAG ATTIVO -->

						<br>
						<label><spring:message code="mypivot.anagrafica.filter.flgAttivo" />
							<form:checkbox  path="flgAttivo" class="input-block-level" name="flgAttivo"/>
						</label>
								
					</div><!-- /.span3 -->
					


					<div class="span3">

						<!-- Codice Capitolo-->
						<label><spring:message
								code="mypivot.anagrafica.filter.codiceCapitolo" /></label>
						<form:input path="codiceCapitolo" class="input-block-level"
							name="codiceCapitolo" type="text" placeholder="" />

						<!-- Denominazione Capitolo-->
						<label><spring:message
								code="mypivot.anagrafica.filter.denominazioneCapitolo" /></label>
						<form:input path="denominazioneCapitolo" class="input-block-level"
							name="denominazioneCapitolo" type="text" placeholder="" />

						<!-- Anno Esercizio -->
						<label><spring:message
								code="mypivot.anagrafica.filter.annoEsercizio" /></label>
						<form:input path="annoEsercizio" class="input-block-level"
							name="annoEsercizio" type="text" placeholder="" />

					</div>
					<!-- /.span3 -->

					<div class="span3">

						<!-- Codice Accertamento-->
						<label><spring:message
								code="mypivot.anagrafica.filter.codiceAccertamento" /></label>
						<form:input path="codiceAccertamento" class="input-block-level"
							name="codiceAccertamento" type="text" placeholder="" />

						<!-- Denominazione Accertamento-->
						<label><spring:message
								code="mypivot.anagrafica.filter.denominazioneAccertamento" /></label>
						<form:input path="denominazioneAccertamento"
							class="input-block-level" name="denominazioneAccertamento"
							type="text" placeholder="" />

					</div>
					<!-- /.span3 -->

				</div>
				<!-- /.form-actions -->		
				
				
				<!-- PULSANTIERA RICERCA -->
				<div class="align-center">
					<label><spring:message code="mypivot.pager.numElements" /></label>
					
					<!-- SELECT COUNT ELEMENTS -->
					<form:select class="pagerSelect nomb" itemLabel="pageSize" itemValue="pageSize" path="pageSize">
						<form:option value="5" />
						<form:option value="10" />
						<form:option value="20" />
					</form:select>
					
					<button type="submit" class="btn" value="anagraficaUfficioCapitoloAccertamentoCommand">
						<i class="fa fa-search padding-right"></i> <spring:message code="mypivot.button.cerca" />
					</button>
				</div>
				
				<!-- PULSANTIERA NUOVA ANAGRAFICA -->
				<div class="align-center mtop10 <c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}"> hidden </c:if>"><!-- <c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}"> hidden </c:if> -->
					<!-- PULSANTE NUOVO -->
					<div class="btn-group">
						<spring:message code="mypivot.anagrafica.btn.title.nuovo" var="titleBtNuovo"/>
                    	<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/nuovaAnagrafica.html" class="btn btn-primary" title="${titleBtNuovo}">
                        	<i class="fa fa-plus" aria-hidden="true"></i>
                            <spring:message code="mypivot.anagrafica.btn.label.nuovo" />         
                        </a>
                    </div>
				</div>
			</div><!-- /.debiti_content_filters -->
		</div><!-- /.row-fluid -->
		
	</form:form>
	
<c:if test="${responseDtoPage.totalRecords > 0}">
	<!-- TABELLA RICERCA -->
	<div class="debiti_table">
		
		<!-- intestazione -->
		<div class="row-fluid mypay-custom">
			<div class="span3 text-center tabAnagraficaHeader row-border">
				<spring:message code="mypivot.anagrafica.table.header.tipoDovuti" />
			</div>
			<div class="span3 text-center tabAnagraficaHeader row-border">
				<spring:message code="mypivot.anagrafica.table.header.ufficio" />
			</div>
			<div class="span3 text-center tabAnagraficaHeader row-border">
				<spring:message code="mypivot.anagrafica.table.header.capitolo" />
			</div>
			<div class="span3 text-center tabAnagraficaHeader row-border">
				<spring:message code="mypivot.anagrafica.table.header.accertamento" />
			</div>
		</div>	
	</div>
	
	<c:forEach var="AnagraficaUfficioCapitoloAccertamentoDto" items="${responseDtoPage.list}">
		<c:set var="AnagraficaUfficioCapitoloAccertamentoDto" scope="request" value="${AnagraficaUfficioCapitoloAccertamentoDto}"/>
		
		<div class="row-fluid text-center data-ult-agg-row-padding data-ult-agg-row-color">
			<div class="span12">
				<div class="clearfix testata-multicolonna">
					<div class="pull-left">
						<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
						<c:out value="${AnagraficaUfficioCapitoloAccertamentoDto.dtUltimaModifica}" />
					</div>
					<div class="pull-right">
						<jsp:include page="visualizzaAnagraficaUfficioCapitoloAccertamento_buttons.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row-fluid mypay-custom small-text ">
			<!-- Tipo Dovuti -->
			<div class="span3">
				<label><b><spring:message code="mypivot.anagrafica.tipoDovuto" />&nbsp;:</b></label>
				<c:choose>
    				<c:when test="${AnagraficaUfficioCapitoloAccertamentoDto.deTipoDovuto eq 'null'}">
        				N/A
   		 			</c:when>    
    				<c:otherwise>
        				<c:out	value="${AnagraficaUfficioCapitoloAccertamentoDto.deTipoDovuto}" />
    				</c:otherwise>
				</c:choose>
				
				
			</div>
			<!-- Ufficio -->
			<div class="span3">
				<label><b><spring:message code="mypivot.anagrafica.codice" />&nbsp;:</b></label>
				<c:out value="${AnagraficaUfficioCapitoloAccertamentoDto.codUfficio}" />
			<br><br>
				<label><b><spring:message code="mypivot.anagrafica.denominazione" />&nbsp;:</b></label>
				<c:out	value="${AnagraficaUfficioCapitoloAccertamentoDto.deUfficio}" />
			<br><br>	
				<label><b><spring:message code="mypivot.anagrafica.filter.flgAttivo" />&nbsp;:</b></label>
				<form action="">
					<c:if test="${AnagraficaUfficioCapitoloAccertamentoDto.flgAttivo == true}">
						<input type="checkbox" value="" disabled="disabled" checked="checked"/>
					</c:if>
    				<c:if test="${AnagraficaUfficioCapitoloAccertamentoDto.flgAttivo == false}">
						<input type="checkbox" value="" disabled="disabled"/>
					</c:if>
				</form>
			</div>
			<!-- Capitolo -->
			<div class="span3">
				<label><b><spring:message code="mypivot.anagrafica.codice" />&nbsp;:</b></label>
				<c:out value="${AnagraficaUfficioCapitoloAccertamentoDto.codCapitolo}" /><br/>
			<br><br>			
				<label><b><spring:message code="mypivot.anagrafica.denominazione" />&nbsp;:</b></label>
				<c:out	value="${AnagraficaUfficioCapitoloAccertamentoDto.deCapitolo}" />
			<br><br>
				<label><b><spring:message code="mypivot.anagrafica.annoEsercizio" />&nbsp;:</b></label>
				<c:out	value="${AnagraficaUfficioCapitoloAccertamentoDto.deAnnoEsercizio}" />
			</div>
			<!-- Accertamento -->
			<div class="span3">
				<label><b><spring:message code="mypivot.anagrafica.codice" />&nbsp;:</b></label>
				<c:out value="${AnagraficaUfficioCapitoloAccertamentoDto.codAccertamento}" /><br/>
			<br><br>			
				<label><b><spring:message code="mypivot.anagrafica.denominazione" />&nbsp;:</b></label>
				<c:out	value="${AnagraficaUfficioCapitoloAccertamentoDto.deAccertamento}" />
			</div>
		</div>
		
	</c:forEach>
</c:if>

<!-- PAGINAZIONE TABELLA RICERCA -->
	<c:if test="${responseDtoPage.totalRecords > 0}">
		<div>
			<div class="pagination-left">
				<!-- Es: Pagina 1 di 33 -->
				<spring:message code="mypivot.pager.pagina" /> <c:out value="${responseDtoPage.page}" /> 
				<spring:message code="mypivot.pager.di" /> <c:out value="${responseDtoPage.totalPages}" />
				-
				<!-- Es: Elementi da 1 a 5 -->
				<spring:message code="mypivot.pager.elementiDa" /> <c:out value="${(responseDtoPage.page-1) * responseDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" /> <c:out value="${(responseDtoPage.page-1) * responseDtoPage.pageSize + fn:length(responseDtoPage.list)}" />
			</div><!-- /.pagination-left -->

			<div>
				<div class="pagination pagination-right">
					<ul>
						<c:if test="${responseDtoPage.previousPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?page=1&pageSize=${responseDtoPage.pageSize}&tab=${tab}">
									<span><spring:message code="mypivot.pager.prima" /></span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.previousPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?page=${responseDtoPage.page-1}&pageSize=${responseDtoPage.pageSize}&tab=${tab}">
									<span>&#171;</span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.previousPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?page=${responseDtoPage.page-1}&pageSize=${responseDtoPage.pageSize}&tab=${tab}">
									<span><c:out value="${responseDtoPage.page-1}"></c:out></span>
								</a>
							</li>
						</c:if>

						<li class="disabled"><span><c:out value="${responseDtoPage.page}"></c:out></span></li>

						<c:if test="${responseDtoPage.nextPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?page=${responseDtoPage.page+1}&pageSize=${responseDtoPage.pageSize}&tab=${tab}">
									<span><c:out value="${responseDtoPage.page+1}"></c:out></span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?page=${responseDtoPage.page+1}&pageSize=${responseDtoPage.pageSize}&tab=${tab}">
									<span>&#187;</span>
							</a></li>
						</c:if>

						<c:if test="${responseDtoPage.nextPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?page=${responseDtoPage.totalPages}&pageSize=${responseDtoPage.pageSize}&tab=${tab}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
								</a>
							</li>
						</c:if>
					</ul>
				</div><!-- /.pagination-right -->
			</div>
		</div>
	</c:if>

<!-- LABEL PAGINAZIONE 0 RISULTATI -->
<c:if test="${responseDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>
	
</div><!-- /.home_content table-responsive-->


<!-- PORZIONE CODICE PAGINA PER IMPORT MASSIVO -->
<div class="home_content <c:if test="${tab eq 'U'}">hidden</c:if>">

	<div class="divBorder row-fluid">
	
		<div
				class="divPadding span14">
				<div id="upload-refresh" class="hidden">
					<a
						href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?tab=${tab}"
						class="btn"><spring:message code="mypivot.carica.nuovoUpload" /></a>
				</div>

				<div id="upload-container">
					<input id="uploadCsvAnag" type="file" name="file"
						accept="application/zip"
						data-url="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/uploadCSV.json">

					<div id="progress" class="progress progress-striped">
						<div class="bar" style="width: 0%;"></div>
					</div>
				</div>
			</div>
			
		<input type="hidden" name="requestToken" id="requestToken"
			value="${anagraficaUfficioCapitoloAccertamentoCommand.requestToken}">

		<jsp:include page="../messages.jsp"></jsp:include>
	
	</div><!-- /.divBorder row-fluid -->
	

</div><!-- /.home_content -->

<div class="well carrello clearfix">
		<div class="btn-group pull-left" style="margin-top:20px">
			<a href="/mypivot/protected/accertamenti/ricerca.html" class="btn btn-large">
				<i class="fa fa-chevron-circle-left fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.indietro"/>
			</a>
		</div><!-- /<div class="btn-group pull-left"> -->
</div>