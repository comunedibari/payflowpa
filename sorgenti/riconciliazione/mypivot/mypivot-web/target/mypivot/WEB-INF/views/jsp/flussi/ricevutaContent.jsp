<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.net.URLEncoder"%>

<jsp:include page="../messages.jsp"></jsp:include>

<!-- 
	Controllo se e' la pagina di dettaglio del cruscotto incassi, per customizzare l'url
-->
<c:choose>
	<c:when test="${not empty forceCruscotto and forceCruscotto}">
		<c:set var="urPaging" value="cruscottoIncassi/visualizzaDettaglioCruscotto.html" />
	</c:when>
	<c:otherwise>			
		<c:set var="urPaging" value="visualizzaRicevuta.html" />
	</c:otherwise>	
</c:choose>

<c:if test="${flussoRendicontazioneDto ne null}">
	<div class="debiti_table">

		<!-- intestazione -->
		<div class="row-fluid mypay-custom">
			<div class="span12 text-center rendicontazioneHeader row-border border-right-0">
				<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
			</div>			
		</div>
		
		<c:set var="flussoRendicontazioneTO" scope="request" value="${flussoRendicontazioneDto.flussoRendicontazioneTO}"/>

		<div class="row-fluid mypay-custom small-text-no-padding rendicontazioneRowBackground">

			<div class="rendicontazioneRow span12 padding-bottom-10">
				<div class="span4">
					<label><spring:message code="mypivot.visualizza.flussoRendicontazione" />&nbsp;:</label>
					<c:out value="${flussoRendicontazioneTO.codIdentificativoFlusso}" /><br />
					<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;:</label>
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${flussoRendicontazioneTO.dtDataOraFlusso}"/><br />
				</div>

				<div class="span4">
					<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;:</label>
					<c:out value="${flussoRendicontazioneTO.codIdentificativoUnivocoRegolamento}" /><br />
					<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;:</label>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoRendicontazioneTO.dtDataRegolamento}"/><br />
				</div>
				
				<div class="span4">
					<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;:</label>
					<c:out value="${flussoRendicontazioneDto.deImporto}" />&nbsp;&euro;
				</div>
			</div>
		</div>
	</div>
</c:if>
<br />
<c:if test="${flussoRicevutaDtoPage.totalRecords > 0}">
	<div class="debiti_table">
		<!-- intestazione -->
		<div class="row-fluid mypay-custom">
			<div
				class="span12 text-center ricevutaHeader row-border border-right-0">
				<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
			</div>
		</div>

		<c:forEach var="flussoRicevutaDto"
			items="${flussoRicevutaDtoPage.list}">
			<c:set var="flussoExportTO" scope="request"
				value="${flussoRicevutaDto.flussoExportTO}" />

			<div
				class="row-fluid mypay-custom small-text-no-padding ricevutaRowBackground">

				<div class="ricevutaTesoreriaRow span12 padding-bottom-10">
					<div class="span4">
						<label><spring:message
								code="mypivot.visualizza.tipoDovuto" />&nbsp;:</label>
						<c:out value="${flussoRicevutaDto.deTipoDovuto}" />
						<br /> <label><spring:message
								code="mypivot.visualizza.identificativo.IUD" />&nbsp;:</label>
						<c:out value="${flussoExportTO.codIud}" />
						<br /> <label><spring:message
								code="mypivot.visualizza.identificativo.IUV" />&nbsp;:</label>
						<c:out
							value="${flussoExportTO.id.codRpSilinviarpIdUnivocoVersamento}" />
						<br /> <label><spring:message
								code="mypivot.visualizza.identificativo.IUR" />&nbsp;:</label>
						<c:out
							value="${flussoExportTO.id.codEDatiPagDatiSingPagIdUnivocoRiscoss}" />
						<br />
					</div>

					<div class="span4">
						<label><spring:message
								code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;:</label>
						<c:out value="${flussoRicevutaDto.deImportoSingolo}" />&nbsp;&euro;
						<br /> <label><spring:message
								code="mypivot.visualizza.datiPagamento.dataEsito" />&nbsp;:</label>
						<fmt:formatDate pattern="dd/MM/yyyy"
							value="${flussoExportTO.dtEDatiPagDatiSingPagDataEsitoSingoloPagamento}" />
						<br /> <label><spring:message
								code="mypivot.visualizza.attestante" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty flussoExportTO.deEIstitAttDenominazioneAttestante}">
								<c:out
									value="${flussoExportTO.deEIstitAttDenominazioneAttestante}" />
								<br />
							</c:when>
							<c:otherwise>
								n/a
						    </c:otherwise>
						</c:choose>
						<br /> <label><spring:message
								code="mypivot.visualizza.pagatore" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty flussoExportTO.codESoggPagIdUnivPagCodiceIdUnivoco}">
								<c:out value="${flussoExportTO.codESoggPagAnagraficaPagatore}" />
								<br />
								<c:out
									value="${flussoExportTO.codESoggPagIdUnivPagCodiceIdUnivoco}" />
								<br />
								<c:if
									test="${((flussoRicevutaDto.codESoggPagIdUnivPagTipoIdUnivoco == 'F') or (flussoRicevutaDto.codESoggPagIdUnivPagTipoIdUnivoco == 'G'))}">
									<small><div class="no_wrap">
											(
											<spring:message
												code="mypivot.tipoIdentificativoUnivoco.${flussoRicevutaDto.codESoggPagIdUnivPagTipoIdUnivoco}" />
											)
										</div> </small>
								</c:if>
							</c:when>
							<c:otherwise>
							n/a
							</c:otherwise>
						</c:choose>
						<br />
					</div>

					<div class="span4">
						<label><spring:message code="mypivot.visualizza.versante" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty flussoExportTO.codESoggVersIdUnivVersCodiceIdUnivoco}">
								<c:out value="${flussoExportTO.codESoggVersAnagraficaVersante}" />
								<br />
								<c:out
									value="${flussoExportTO.codESoggVersIdUnivVersCodiceIdUnivoco}" />
								<br />
								<c:if
									test="${((flussoRicevutaDto.codESoggVersIdUnivVersTipoIdUnivoco == 'F') or (flussoRicevutaDto.codESoggVersIdUnivVersTipoIdUnivoco == 'G'))}">
									<small><div class="no_wrap">
											(
											<spring:message
												code="mypivot.tipoIdentificativoUnivoco.${flussoRicevutaDto.codESoggVersIdUnivVersTipoIdUnivoco}" />
											)
										</div> </small>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose>
						<br /> <label><spring:message
								code="mypivot.visualizza.datiPagamento.causale" />&nbsp;:</label>
						<div class="wrapfix">
							<c:if
								test="${fn:length(flussoExportTO.deEDatiPagDatiSingPagCausaleVersamento)>140}">
								<c:out
									value="${fn:substring(flussoExportTO.deEDatiPagDatiSingPagCausaleVersamento, 0, 140)}" />...
							</c:if>
							<c:if
								test="${fn:length(flussoExportTO.deEDatiPagDatiSingPagCausaleVersamento)<=140}">
								<c:out
									value="${flussoExportTO.deEDatiPagDatiSingPagCausaleVersamento}" />
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row-fluid mypay-custom row-border">
		<div class="span12">
			<div class="pagination pagination-left">
				<spring:message code="mypivot.pager.pagina" />
				<c:out value="${flussoRicevutaDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${flussoRicevutaDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(flussoRicevutaDtoPage.page-1) * flussoRicevutaDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(flussoRicevutaDtoPage.page-1) * flussoRicevutaDtoPage.pageSize + fn:length(flussoRicevutaDtoPage.list)}" />
			</div>
			<div>
				<div class="pagination pagination-right">
					<ul>
						<c:if test="${flussoRicevutaDtoPage.previousPage}">
							<c:if test="${flussoRendicontazioneDto ne null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=1&pgSize=${flussoRicevutaDtoPage.pageSize}&iuf=${flussoRendicontazioneDto.flussoRendicontazioneTO.codIdentificativoFlusso}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
							</c:if>
							<c:if test="${flussoRendicontazioneDto == null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=1&pgSize=${flussoRicevutaDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
							</c:if>
						</c:if>

						<c:if test="${flussoRicevutaDtoPage.previousPage}">
							<c:if test="${flussoRendicontazioneDto ne null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page-1}&pgSize=${flussoRicevutaDtoPage.pageSize}&iuf=${flussoRendicontazioneDto.flussoRendicontazioneTO.codIdentificativoFlusso}">
									<span>«</span>
							</a></li>
							</c:if>
							<c:if test="${flussoRendicontazioneDto == null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page-1}&pgSize=${flussoRicevutaDtoPage.pageSize}">
									<span>«</span>
							</a></li>
							</c:if>
						</c:if>

						<c:if test="${flussoRicevutaDtoPage.previousPage}">
							<c:if test="${flussoRendicontazioneDto ne null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page-1}&pgSize=${flussoRicevutaDtoPage.pageSize}&iuf=${flussoRendicontazioneDto.flussoRendicontazioneTO.codIdentificativoFlusso}">
									<span><c:out value="${flussoRicevutaDtoPage.page-1}"></c:out></span>
							</a></li>
							</c:if>
							<c:if test="${flussoRendicontazioneDto == null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page-1}&pgSize=${flussoRicevutaDtoPage.pageSize}">
									<span><c:out value="${flussoRicevutaDtoPage.page-1}"></c:out></span>
							</a></li>
							</c:if>
						</c:if>

						<li class="disabled"><span><c:out
									value="${flussoRicevutaDtoPage.page}"></c:out></span></li>

						<c:if test="${flussoRicevutaDtoPage.nextPage}">
							<c:if test="${flussoRendicontazioneDto ne null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page+1}&pgSize=${flussoRicevutaDtoPage.pageSize}&iuf=${flussoRendicontazioneDto.flussoRendicontazioneTO.codIdentificativoFlusso}">
									<span><c:out value="${flussoRicevutaDtoPage.page+1}"></c:out></span>
							</a></li>
							</c:if>
							<c:if test="${flussoRendicontazioneDto == null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page+1}&pgSize=${flussoRicevutaDtoPage.pageSize}">
									<span><c:out value="${flussoRicevutaDtoPage.page+1}"></c:out></span>
							</a></li>
							</c:if>
						</c:if>

						<c:if test="${flussoRicevutaDtoPage.nextPage}">
							<c:if test="${flussoRendicontazioneDto ne null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page+1}&pgSize=${flussoRicevutaDtoPage.pageSize}&iuf=${flussoRendicontazioneDto.flussoRendicontazioneTO.codIdentificativoFlusso}">
									<span>»</span>
							</a></li>
							</c:if>
							<c:if test="${flussoRendicontazioneDto == null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.page+1}&pgSize=${flussoRicevutaDtoPage.pageSize}">
									<span>»</span>
							</a></li>
							</c:if>
						</c:if>

						<c:if test="${flussoRicevutaDtoPage.nextPage}">
							<c:if test="${flussoRendicontazioneDto ne null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.totalPages}&pgSize=${flussoRicevutaDtoPage.pageSize}&iuf=${flussoRendicontazioneDto.flussoRendicontazioneTO.codIdentificativoFlusso}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
							</c:if>
							<c:if test="${flussoRendicontazioneDto == null}">
							<li><a
								href="<%=request.getContextPath()%>/protected/${urPaging}?pg=${flussoRicevutaDtoPage.totalPages}&pgSize=${flussoRicevutaDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
							</c:if>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${flussoRicevutaDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>