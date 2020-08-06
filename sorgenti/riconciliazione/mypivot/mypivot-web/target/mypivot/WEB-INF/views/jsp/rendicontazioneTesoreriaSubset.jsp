<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>


<jsp:include page="messages.jsp"></jsp:include>
<script type="text/javascript">
	function visualizzaDettaglioRendicontazione(classificazioneCompletezza, codIuf){
	    var form = $('#visualizzaDettaglioRendicontazioneForm');
	    form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
	    form.find('#codiceIuf').val(codIuf);
	    form.submit();
	}
	function visualizzaStoricoSegnalazioni(classificazioneCompletezza, codIuv, codIuf, codIud){
		var form = $('#visualizzaStoricoSegnalazioniForm');
		form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
		form.find('#codiceIuv').val(codIuv);
		form.find('#codiceIuf').val(codIuf);
		form.find('#codiceIud').val(codIud);
		form.find('.enable-field').val(true);
		form.submit();
	}
	/*
	function visualizzaStoricoCompletoSegnalazioni(){
		var form = $('#visualizzaStoricoSegnalazioniForm');
		form.find('.disable-field').val(true);
		form.submit();
	}
	*/

</script>
<div style="display: none;">
	<form action="visualizzaStoricoSegnalazioni.html" id="visualizzaStoricoSegnalazioniForm" method="get">
		<input type="hidden" id="classificazioneCompletezza" name="classificazioneCompletezza"/>
		<input type="hidden" id="codiceIuv" name="codiceIuv"/>
		<input type="hidden" id="codiceIuf" name="codiceIuf"/>
		<input type="hidden" id="codiceIud" name="codiceIud"/>
		<input class="enable-field" type="hidden" name="codiceIuvEnabled"/>
		<input class="enable-field" type="hidden" name="codiceIufEnabled"/>
		<input class="enable-field" type="hidden" name="codiceIudEnabled"/>
	</form>

	<form action="visualizzaDettaglioRendicontazione.html" id="visualizzaDettaglioRendicontazioneForm" method="get">
        <input id="classificazioneCompletezza" name="classificazioneCompletezza"/>
        <input id="codiceIuf" type="hidden" name="codiceIuf"/>
		<!--input id="page" type="hidden" name="page"/-->
		<!--input id="pageSize" type="hidden" name="pageSize"/-->
		<input id="tipoVisualizzazione" type="hidden" name="tipoVisualizzazione" value="${tipoVisualizzazione}" />
    </form>
</div>

<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.totalRecords > 0}">

	<div class="debiti_table">
				
		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'IUF_TES_DIV_IMP'}">

			<!-- intestazione -->
			<div class="row-fluid mypay-custom">
				<div class="span6 text-center rendicontazioneHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
				</div>
				<div class="span6 text-center tesoreriaHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
				</div>
			</div>

			<c:forEach var="rendicontazioneTesoreriaSubsetDto" items="${rendicontazioneTesoreriaSubsetDtoPage.list}">
				<c:set var="rendicontazioneTesoreriaSubsetDto" scope="request" value="${rendicontazioneTesoreriaSubsetDto}"/>

				<div class="row-fluid data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="rendicontazioneTesoreriaSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid mypay-custom small-text">

					<div class="span6 rendicontazioneTesoreriaRow ricevutaSpan6false">
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" />&nbsp;</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.identificativoFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.dataOraFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.identificativoUnivocoRegolamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deDataRegolamento}" /><br />
						<c:if
							test="${rendicontazioneTesoreriaSubsetDto.identificativoFlussoRendicontazione == 'n/a'}">
							<div class="no_wrap">
								(
								<spring:message
									code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
								)
							</div><br />
						</c:if>

						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.importoTotalePagamenti}" />
					</div>


					<div class="span6 tesoreriaRow tesoreriaSpan6false">
						<label><spring:message code="mypivot.visualizza.conto" />&nbsp;:</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codConto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deDataValuta}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deDataContabile}" /><br />
						
						<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deImporto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
						<c:out value="${rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codOr1}" />
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deAnnoBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deAnnoBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codIdDominio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codIdDominio" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codIdDominio)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deDataRicezione) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deDataRicezione)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deAnnoDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deAnnoDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deAnnoProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.deAnnoProvvisorio)}" />
						</c:if>
						
						<c:if test="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(rendicontazioneTesoreriaSubsetDto.rendicontazioneTesoreriaSubsetTO.codProvvisorio)}" />
						</c:if>
					</div>
				</div>
			</c:forEach>
		</c:if>
		
	</div>

	<div class="row-fluid mypay-custom row-border">
		<div class="span12">
			<div class="pagination pagination-left">
				<spring:message code="mypivot.pager.pagina" />
				<c:out value="${rendicontazioneTesoreriaSubsetDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${rendicontazioneTesoreriaSubsetDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(rendicontazioneTesoreriaSubsetDtoPage.page-1) * rendicontazioneTesoreriaSubsetDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(rendicontazioneTesoreriaSubsetDtoPage.page-1) * rendicontazioneTesoreriaSubsetDtoPage.pageSize + fn:length(rendicontazioneTesoreriaSubsetDtoPage.list)}" />

			</div>

			<div>
				<div class="pagination pagination-right">

					<ul>
						<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=1&pgSize=${rendicontazioneTesoreriaSubsetDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneTesoreriaSubsetDtoPage.page-1}&pgSize=${rendicontazioneTesoreriaSubsetDtoPage.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneTesoreriaSubsetDtoPage.page-1}&pgSize=${rendicontazioneTesoreriaSubsetDtoPage.pageSize}">
									<span><c:out value="${rendicontazioneTesoreriaSubsetDtoPage.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out
									value="${rendicontazioneTesoreriaSubsetDtoPage.page}"></c:out></span></li>

						<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneTesoreriaSubsetDtoPage.page+1}&pgSize=${rendicontazioneTesoreriaSubsetDtoPage.pageSize}">
									<span><c:out value="${rendicontazioneTesoreriaSubsetDtoPage.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneTesoreriaSubsetDtoPage.page+1}&pgSize=${rendicontazioneTesoreriaSubsetDtoPage.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneTesoreriaSubsetDtoPage.totalPages}&pgSize=${rendicontazioneTesoreriaSubsetDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${rendicontazioneTesoreriaSubsetDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>
