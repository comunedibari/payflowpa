<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>

<jsp:include page="messages.jsp"></jsp:include>
<c:if test="${rendicontazioneSubsetDtoPage.totalRecords > 0}">   

	<div class="debiti_table">
		<div class="row-fluid mypay-custom row-border rendicontazioneTesoreriaHeaderSingle">
			<div class="span12 text-center rendicontazioneTesoreriaHeader">
				<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
			</div>
		</div>
		<div class="row-fluid mypay-custom rendicontazioneTesoreriaHeaderSingleB">
			<div class="span3 text-center rendicontazioneTesoreriaHeader no_wrap">
				<spring:message code="mypivot.visualizza.flussoRendicontazione" />
			</div>
			<div class="span1 text-center rendicontazioneTesoreriaHeader no_wrap">
				<spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />
			</div>
			<div class="span3 text-center rendicontazioneTesoreriaHeader no_wrap">
				<spring:message code="mypivot.visualizza.regolamento" />
			</div>
			<div class="span1 text-center rendicontazioneTesoreriaHeader no_wrap">
				<spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />
			</div>
			<div class="span1 text-center rendicontazioneTesoreriaHeader font-10">
				<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />
			</div>
			<div class="span1 text-center rendicontazioneTesoreriaHeader no_wrap">
				<spring:message code="mypivot.visualizza.regolamento.importoTotale" />
			</div>
			<div class="span2 text-center rendicontazioneTesoreriaHeader no_wrap">
				&nbsp;
			</div>
			
		</div>

		<c:forEach var="rendicontazioneSubsetDto" items="${rendicontazioneSubsetDtoPage.list}">
			<div class="row-fluid mypay-custom small-text2 ">
				<div class="span3 text-center rendicontazioneRow">
					<c:out value="${rendicontazioneSubsetDto.identificativoFlussoRendicontazione}" />
				</div>
				<div class="span1 text-center rendicontazioneRow">
					<c:out value="${rendicontazioneSubsetDto.rendicontazioneSubsetTO.dataOraFlussoRendicontazione}" />
				</div>
				<div class="span3 text-center rendicontazioneRow">
					<c:out value="${rendicontazioneSubsetDto.rendicontazioneSubsetTO.identificativoUnivocoRegolamento}" />
				</div>
				<div class="span1 text-center rendicontazioneRow no_wrap">
					<c:out value="${rendicontazioneSubsetDto.rendicontazioneSubsetTO.deDataRegolamento}" />
					<c:if test="${rendicontazioneSubsetDto.identificativoFlussoRendicontazione == 'n/a'}">
						<br />
						(
							<spring:message	code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
						)
					</c:if>
				</div>
				<div class="span1 text-center rendicontazioneRow no_wrap">
					<c:out value="${rendicontazioneSubsetDto.rendicontazioneSubsetTO.deDataUltimoAggiornamento}" />
				</div>
				<div class="span1 text-center rendicontazioneRow no_wrap">
					<c:out value="${rendicontazioneSubsetDto.rendicontazioneSubsetTO.importoTotalePagamenti}" />
				</div>
				<div class="span2 text-center rendicontazioneRow no_wrap">
					<div class="btn-group">
						<c:if test="${rendicontazioneSubsetDto.segnalazione.flgNascosto != null and rendicontazioneSubsetDto.segnalazione.flgNascosto}">
							<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="Elemento nascosto">
								<i class="fa fa-eye-slash"></i>
							</a>
						</c:if>
						<c:if test="${rendicontazioneSubsetDto.segnalazione.flgNascosto == null or !rendicontazioneSubsetDto.segnalazione.flgNascosto}">
							<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="Elemento attivo">
								<i class="fa fa-eye"></i>
							</a>
						</c:if>
						<c:if test="${rendicontazioneSubsetDto.segnalazione.id != null}">
							<c:set var="nota" value="${fn:substring(rendicontazioneSubsetDto.segnalazione.deNota, 0, 50)}" />
							<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="${nota}">
								<i class="fa fa-comment-o"></i>
							</a>
							<a href="javascript:void(0);" onclick="visualizzaStoricoSegnalazioni('${rendicontazioneSubsetDto.rendicontazioneSubsetTO.classificazioneCompletezza}', '', '${rendicontazioneSubsetDto.rendicontazioneSubsetTO.codIufKey}','')" type="button" class="btn btn-primary btn-medium" title="Storico Segnalazioni">
								<i class="fa fa-clock-o"></i>
							</a>
						</c:if>
						<a href="javascript:void(0);" onclick="visualizzaDettaglioRendicontazione('${rendicontazioneSubsetDto.rendicontazioneSubsetTO.classificazioneCompletezza}', '${rendicontazioneSubsetDto.rendicontazioneSubsetTO.codIufKey}')" type="button" class="btn btn-primary btn-medium" title="Dettaglio">
							<i class="fa fa-search"></i>
						</a>									
					</div>
				</div>
			</div>
		</c:forEach>
	</div>


<div class="row-fluid mypay-custom row-border">
	<div class="span12">
		<div class="pagination pagination-left">
			<spring:message code="mypivot.pager.pagina" />
			<c:out value="${rendicontazioneSubsetDtoPage.page}" />
			<spring:message code="mypivot.pager.di" />
			<c:out value="${rendicontazioneSubsetDtoPage.totalPages}" />
			-
			<spring:message code="mypivot.pager.elementiDa" />
			<c:out
				value="${(rendicontazioneSubsetDtoPage.page-1) * rendicontazioneSubsetDtoPage.pageSize + 1}" />
			<spring:message code="mypivot.pager.a" />
			<c:out
				value="${(rendicontazioneSubsetDtoPage.page-1) * rendicontazioneSubsetDtoPage.pageSize + fn:length(rendicontazioneSubsetDtoPage.list)}" />

		</div>

		<div>
			<div class="pagination pagination-right">

				<ul>
					<c:if test="${rendicontazioneSubsetDtoPage.previousPage}">
						<li><a
							href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=1&pgSize=${rendicontazioneSubsetDtoPage.pageSize}">
								<span><spring:message code="mypivot.pager.prima" /></span>
						</a></li>
					</c:if>

					<c:if test="${rendicontazioneSubsetDtoPage.previousPage}">
						<li><a
							href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneSubsetDtoPage.page-1}&pgSize=${rendicontazioneSubsetDtoPage.pageSize}">
								<span>«</span>
						</a></li>
					</c:if>

					<c:if test="${rendicontazioneSubsetDtoPage.previousPage}">
						<li><a
							href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneSubsetDtoPage.page-1}&pgSize=${rendicontazioneSubsetDtoPage.pageSize}">
								<span><c:out
										value="${rendicontazioneSubsetDtoPage.page-1}"></c:out></span>
						</a></li>
					</c:if>

					<li class="disabled"><span><c:out
								value="${rendicontazioneSubsetDtoPage.page}"></c:out></span></li>

					<c:if test="${rendicontazioneSubsetDtoPage.nextPage}">
						<li><a
							href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneSubsetDtoPage.page+1}&pgSize=${rendicontazioneSubsetDtoPage.pageSize}">
								<span><c:out
										value="${rendicontazioneSubsetDtoPage.page+1}"></c:out></span>
						</a></li>
					</c:if>

					<c:if test="${rendicontazioneSubsetDtoPage.nextPage}">
						<li><a
							href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneSubsetDtoPage.page+1}&pgSize=${rendicontazioneSubsetDtoPage.pageSize}">
								<span>»</span>
						</a></li>
					</c:if>

					<c:if test="${rendicontazioneSubsetDtoPage.nextPage}">
						<li><a
							href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${rendicontazioneSubsetDtoPage.totalPages}&pgSize=${rendicontazioneSubsetDtoPage.pageSize}">
								<span><spring:message code="mypivot.pager.ultima" /></span>
						</a></li>
					</c:if>


				</ul>

			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	 function visualizzaDettaglioRendicontazione(classificazioneCompletezza, codIuf){
        var form = $('#visualizzaDettaglioRendicontazioneForm');
        form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
        form.find('#codiceIuf').val(codIuf);
        form.submit();
    }

</script>
<div style="display: none;">
    <form action="visualizzaDettaglioRendicontazione.html" id="visualizzaDettaglioRendicontazioneForm" method="get">
        <input id="classificazioneCompletezza" name="classificazioneCompletezza"/>
        <input id="codiceIuf" type="hidden" name="codiceIuf"/>
		<!--input id="page" type="hidden" name="page"/-->
		<!--input id="pageSize" type="hidden" name="pageSize"/-->
		<input id="tipoVisualizzazione" type="hidden" name="tipoVisualizzazione" value="${tipoVisualizzazione}" />
    </form>
</div>
</c:if>
<c:if test="${rendicontazioneSubsetDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>

