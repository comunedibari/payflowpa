<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>

<jsp:include page="messages.jsp"></jsp:include>

<c:if test="${tesoreriaSubsetDtoPage.totalRecords > 0}">

	<div class="debiti_table">

		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'TES_NO_IUF_OR_IUV'}">
			<div class="row-fluid mypay-custom row-border tesoreriaHeaderSingle">
				<div class="span12 text-center tesoreriaHeader">
					<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
				</div>
			</div>
			<div class="row-fluid mypay-custom tesoreriaHeaderSingleB">
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.conto" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.dataValuta" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.dataContabile" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="font-12">
						<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="row-fluid tesoreria-rowspan-2">
						<div class="font-12">
							<spring:message code="mypivot.visualizza.deAnnoBolletta" />
						</div>
					</div>
					<div class="row-fluid">
						<div class="font-12">
							<spring:message code="mypivot.visualizza.codBolletta" />
						</div>
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.importoTesoreria" />
					</div>
				</div>
				<div class="span2 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.codOR1" />
					</div>
				</div>
				<div class="span2 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.identificativoRiversamento" />
					</div>
				</div>
				<div class="span2 text-center tesoreriaHeader no_wrap">
					&nbsp;
				</div>
			</div>

			<c:forEach var="tesoreriaSubsetDto"	items="${tesoreriaSubsetDtoPage.list}">
				<div class="row-fluid mypay-custom small-text2">
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.codConto}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.deDataValuta}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.deDataContabile}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.deDataUltimoAggiornamento}" />
						<c:if test="${tesoreriaSubsetDto.tesoreriaSubsetTO.deDataUltimoAggiornamento == null}">
							n/a
						</c:if>
					</div>
					<div class="span1 text-center tesoreriaRow ${visualizzaCompletaDto.rowClass}">
						<div class="row-fluid tesoreria-rowspan-2">
							<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.deAnnoBolletta}" />
						</div>
						<div class="row-fluid">
							<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.codBolletta}" />
						</div>
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.deImporto}" />
					</div>
					<div class="span2 text-center tesoreriaRow">
						<c:out value="${tesoreriaSubsetDto.tesoreriaSubsetTO.codOr1}" />
					</div>
					<div class="span2 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.identificativoRiversamento}" />
					</div>
					<div class="span2 text-center tesoreriaRow no_wrap">
						<div class="btn-group">
							<c:if test="${tesoreriaSubsetDto.segnalazione.flgNascosto != null and tesoreriaSubsetDto.segnalazione.flgNascosto}">
								<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="Elemento nascosto">
									<i class="fa fa-eye-slash"></i>
								</a>
							</c:if>
							<c:if test="${tesoreriaSubsetDto.segnalazione.flgNascosto == null or !tesoreriaSubsetDto.segnalazione.flgNascosto}">
								<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="Elemento attivo">
									<i class="fa fa-eye"></i>
								</a>
							</c:if>
							<c:if test="${tesoreriaSubsetDto.segnalazione.id != null}">
								<c:set var="nota" value="${fn:substring(tesoreriaSubsetDto.segnalazione.deNota, 0, 50)}" />
								<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="${nota}">
									<i class="fa fa-comment-o"></i>
								</a>
								<a href="javascript:void(0);" onclick="visualizzaStoricoSegnalazioni('${tesoreriaSubsetDto.tesoreriaSubsetTO.classificazioneCompletezza}', '${tesoreriaSubsetDto.tesoreriaSubsetTO.codIuvKey}', '${tesoreriaSubsetDto.tesoreriaSubsetTO.codIufKey}','')" type="button" class="btn btn-primary btn-medium" title="Storico Segnalazioni">
									<i class="fa fa-clock-o"></i>
								</a>
							</c:if>
							<a href="javascript:void(0);" onclick="visualizzaDettaglio('${tesoreriaSubsetDto.tesoreriaSubsetTO.classificazioneCompletezza}', '${tesoreriaSubsetDto.tesoreriaSubsetTO.codIuvKey}', '${tesoreriaSubsetDto.tesoreriaSubsetTO.codIufKey}','')" type="button" class="btn btn-primary btn-medium" title="Dettaglio">
								<i class="fa fa-search"></i>
							</a>									
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'TES_NO_MATCH'}">
			<div class="row-fluid mypay-custom row-border tesoreriaHeaderSingle">
				<div class="span12 text-center tesoreriaHeader">
					<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
				</div>
			</div>
			<div class="row-fluid mypay-custom tesoreriaHeaderSingleB">
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.conto" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.dataValuta" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.dataContabile" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="font-12">
						<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.deAnnoBolletta" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.codBolletta" />
					</div>
				</div>
				<div class="span1 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.importoTesoreria" />
					</div>
				</div>
				<div class="span2 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.codOR1" />
					</div>
				</div>
				<div class="span3 text-center tesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.causaleRiversamento" />
					</div>
				</div>
			</div>

			<c:forEach var="tesoreriaSubsetDto"	items="${tesoreriaSubsetDtoPage.list}">
				<div class="row-fluid mypay-custom small-text2">
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.codConto}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deDataValuta}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deDataContabile}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deDataUltimoAggiornamento}" />
						<c:if test="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deDataUltimoAggiornamento == null}">
							n/a
						</c:if>
					</div>
					<div class="span1 text-center tesoreriaRow">
						<c:out value="${tesoreriaSubsetDto.deAnnoBolletta}" />
					</div>
					<div class="span1 text-center tesoreriaRow">
						<c:out value="${tesoreriaSubsetDto.codBolletta}" />
					</div>
					<div class="span1 text-center tesoreriaRow no_wrap">
						<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deImporto}" />
					</div>
					<div class="span2 text-center tesoreriaRow">
						<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.codOr1}" />
					</div>
					<div class="span3 text-center tesoreriaRow">
						<c:if test="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deCausaleT == null}">
							n/a
						</c:if>
						<c:if test="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deCausaleT != null}">
							<c:out value="${tesoreriaSubsetDto.tesoreriaNoMatchSubsetTO.deCausaleT}" />
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
				<c:out value="${tesoreriaSubsetDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${tesoreriaSubsetDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(tesoreriaSubsetDtoPage.page-1) * tesoreriaSubsetDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(tesoreriaSubsetDtoPage.page-1) * tesoreriaSubsetDtoPage.pageSize + fn:length(tesoreriaSubsetDtoPage.list)}" />

			</div>

			<div>
				<div class="pagination pagination-right">

					<ul>
						<c:if test="${tesoreriaSubsetDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=1&pgSize=${tesoreriaSubsetDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${tesoreriaSubsetDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${tesoreriaSubsetDtoPage.page-1}&pgSize=${tesoreriaSubsetDtoPage.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${tesoreriaSubsetDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${tesoreriaSubsetDtoPage.page-1}&pgSize=${tesoreriaSubsetDtoPage.pageSize}">
									<span><c:out
											value="${tesoreriaSubsetDtoPage.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out
									value="${tesoreriaSubsetDtoPage.page}"></c:out></span></li>

						<c:if test="${tesoreriaSubsetDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${tesoreriaSubsetDtoPage.page+1}&pgSize=${tesoreriaSubsetDtoPage.pageSize}">
									<span><c:out
											value="${tesoreriaSubsetDtoPage.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${tesoreriaSubsetDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${tesoreriaSubsetDtoPage.page+1}&pgSize=${tesoreriaSubsetDtoPage.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${tesoreriaSubsetDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${tesoreriaSubsetDtoPage.totalPages}&pgSize=${tesoreriaSubsetDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
						</c:if>


					</ul>

				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${tesoreriaSubsetDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>
