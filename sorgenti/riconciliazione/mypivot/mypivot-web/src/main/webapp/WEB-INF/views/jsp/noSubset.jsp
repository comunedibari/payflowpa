<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>


<jsp:include page="messages.jsp"></jsp:include>
<script type="text/javascript">
	function visualizzaDettaglio(classificazioneCompletezza, codIuv, codIuf, codIud){
		var form = $('#visualizzaDettaglioForm');
		form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
		form.find('#codiceIuv').val(codIuv);
		form.find('#codiceIuf').val(codIuf);
		form.find('#codiceIud').val(codIud);
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

	<form action="visualizzaDettaglio.html" id="visualizzaDettaglioForm" method="get">
		<input id="classificazioneCompletezza" name="classificazioneCompletezza"/>
		<input id="codiceIuv" type="hidden" name="codiceIuv"/>
		<input id="codiceIuf" type="hidden" name="identificativoFlussoRendicontazione"/>
		<input id="codiceIud" type="hidden" name="codiceIud"/>
		<input id="tipoVisualizzazione" type="hidden" name="tipoVisualizzazione" value="${tipoVisualizzazione}" />
	</form>
</div>

<c:if test="${visualizzaCompletaDtoPage.totalRecords > 0}">

	<div class="debiti_table">

		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'IUD_RT_IUF_TES'}">
			<!-- intestazione -->
			<div class="row-fluid mypay-custom">
				<c:if test="${flgPagati}">
					<div class="span${numCols} text-center pagatiHeader row-border">
						<spring:message code="mypivot.visualizzaCompleta.pagati" /> 
					</div>
				</c:if>
				<div class="span${numCols} text-center ricevutaHeader  row-border">
					<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
				</div>
				<div class="span${numCols} text-center rendicontazioneHeader  row-border">
					<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
				</div>
				<c:if test="${flgTesoreria}">
					<div class="span${numCols} text-center tesoreriaHeader  row-border">
						<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
					</div>
				</c:if>
			</div>
					
			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>

				<div class="row-fluid text-center data-ult-agg-row-padding data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="noSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid mypay-custom small-text <c:if test="${empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}">colored</c:if>">
					
					<c:if test="${flgPagati}">
						<div class="pagatiTesoreriaRow span${numCols} pagatiSpan${numCols}${flgPagati}">
							<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
							
							<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;:</label>
							<c:out	value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />

							<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
							
							<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsecuzione" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsecuzionePagamento}" /><br />
					
							<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;:</label>
							<c:choose>
								<c:when
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
									<c:out
										value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
									<br />
									<c:out
										value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
									<br />
									<c:if
										test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
										<div class="no_wrap">
											(
											<spring:message
												code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
											)
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
							    	n/a
								</c:otherwise>
							</c:choose><br />

							<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;:</label>
							<div class="wrapfix">
							<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
								<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
							</c:if>
							<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
								<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
							</c:if>
							</div>
							<label><spring:message code="mypivot.visualizza.datiSpecificiRiscossione" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.datiSpecificiRiscossione}" />
						</div>
					</c:if>
					
					<div class="ricevutaTesoreriaRow span${numCols} ricevutaSpan${numCols}${flgPagati}">
						<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.codiceIuv}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUR" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.identificativoUnivocoRiscossione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsito" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsitoSingoloPagamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.attestante" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.denominazioneAttestante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}" />
								<br />
								<c:if
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante  }">
									<div class="no_wrap_left">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
						    </c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
									<small><div class="no_wrap">
											(
											<spring:message
												code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
											)
										</div> </small>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
											
						<label><spring:message code="mypivot.visualizza.versante" />&nbsp;:</label>
							<c:choose>
								<c:when
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}">
									<c:out
										value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaVersante}" />
									<br />
									<c:out
										value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}" />
									<br />
									<c:if
										test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'G'))}">
										<small><div class="no_wrap">
												(
												<spring:message
													code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante}" />
												)
											</div> </small>
									</c:if>
								</c:when>
								<c:otherwise>
									n/a
								</c:otherwise>
							</c:choose><br />

						<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;:</label>
						<div class="wrapfix">
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
							<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
						</c:if>
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
						</c:if>
						</div>
					</div>

					<div class="rendicontazioneTesoreriaRow span${numCols} rendicontazioneSpan${numCols}${flgPagati}">
					
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.identificativoFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.dataOraFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.identificativoUnivocoRegolamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRegolamento}" /><br />
						<c:if
							test="${visualizzaCompletaDto.identificativoFlussoRendicontazione == 'n/a'}">
							<div class="no_wrap">
								(
								<spring:message	code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
								)
							</div>
						</c:if>
						
						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.importoTotalePagamenti}" />
					</div>

					<c:if test="${flgTesoreria}">
						<div class="tesoreriaRow span${numCols} tesoreriaSpan${numCols}${flgPagati}">
							<label><spring:message code="mypivot.visualizza.conto" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codConto}" /><br />
							
							<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataValuta}" /><br />
							
							<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataContabile}" /><br />
							
							<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deImporto}" /><br />
							
							<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codOr1}" />
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.codIdDominio" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.deAnnoDocumento" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.codDocumento" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio)}" />
							</c:if>
							
							<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio) ne 'n/a'}">
								<label><spring:message code="mypivot.visualizza.codProvvisorio" />&nbsp;:</label>
								<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio)}" />
							</c:if>
						</div>
					</c:if>

				</div>
			</c:forEach>
		</c:if>
		
		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'IUD_RT_IUF'}">

			<!-- intestazione -->
			<div class="row-fluid mypay-custom">
				<div class="span4 text-center pagatiHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.pagati" />
				</div>
				<div class="span4 text-center ricevutaHeader  row-border">
					<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
				</div>
				<div class="span4 text-center rendicontazioneHeader  row-border">
					<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
				</div>			
			</div>
					
			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>

				<div class="row-fluid text-center data-ult-agg-row-padding data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="noSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid mypay-custom small-text <c:if test="${empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}">colored</c:if>">

				<div class="pagatiTesoreriaRow span4 pagatiSpan4true">
					<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;:</label>
					<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
					
					<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;:</label>
					<c:out	value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />

					<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;:</label>
					<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
					
					<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsecuzione" />&nbsp;:</label>
					<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsecuzionePagamento}" /><br />
			
					<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;:</label>
					<c:choose>
						<c:when
							test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
							<c:out
								value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
							<br />
							<c:out
								value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
							<br />
							<c:if
								test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
								<div class="no_wrap">
									(
									<spring:message
										code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
									)
								</div>
							</c:if>
						</c:when>
						<c:otherwise>
					    	n/a
						</c:otherwise>
					</c:choose><br />

					<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;:</label>
					<div class="wrapfix">
					<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
						<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
					</c:if>
					<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
					</c:if>
					</div>
					<label><spring:message code="mypivot.visualizza.datiSpecificiRiscossione" />&nbsp;:</label>
					<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.datiSpecificiRiscossione}" />
				</div>

					
					<div class="ricevutaTesoreriaRow span4 ricevutaSpan4true">
						<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.codiceIuv}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUR" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.identificativoUnivocoRiscossione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsito" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsitoSingoloPagamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.attestante" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.denominazioneAttestante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}" />
								<br />
								<c:if
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante  }">
									<div class="no_wrap_left">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
						    </c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
									<small><div class="no_wrap">
											(
											<spring:message
												code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
											)
										</div> </small>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
											
						<label><spring:message code="mypivot.visualizza.versante" />&nbsp;:</label>
							<c:choose>
								<c:when
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}">
									<c:out
										value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaVersante}" />
									<br />
									<c:out
										value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}" />
									<br />
									<c:if
										test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'G'))}">
										<small><div class="no_wrap">
												(
												<spring:message
													code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante}" />
												)
											</div> </small>
									</c:if>
								</c:when>
								<c:otherwise>
									n/a
								</c:otherwise>
							</c:choose><br />

						<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;:</label>
						<div class="wrapfix">
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
							<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
						</c:if>
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
						</c:if>
						</div>
					</div>

					<div class="rendicontazioneTesoreriaRow span4 rendicontazioneSpan4true">
					
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.identificativoFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.dataOraFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.identificativoUnivocoRegolamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRegolamento}" /><br />
						<c:if
							test="${visualizzaCompletaDto.identificativoFlussoRendicontazione == 'n/a'}">
							<div class="no_wrap">
								(
								<spring:message	code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
								)
							</div>
						</c:if>
						
						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.importoTotalePagamenti}" />
					</div>

				</div>
			</c:forEach>
		</c:if>

		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'IUD_NO_RT'}">
			<div class="row-fluid mypay-custom row-border pagatiHeaderSingle">
				<div class="span12 text-center pagatiHeader">
					<spring:message code="mypivot.visualizzaCompleta.pagati" />
				</div>
			</div>
			<div class="row-fluid mypay-custom pagatiHeaderSingleB">
				<div class="span3 text-center pagatiHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.tipoDovuto" /><br />
						<spring:message code="mypivot.visualizza.identificativo.IUD" /><br />
						<spring:message code="mypivot.visualizza.datiSpecificiRiscossione" />
					</div>
				</div>
				<div class="span2 text-center pagatiHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.pagatore" />
					</div>
				</div>
				<div class="span1 text-center pagatiHeader">
					<div class="font-12">
						<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />
					</div>
				</div>
				<div class="span4 text-center pagatiHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" /><br />
						<spring:message code="mypivot.visualizza.datiPagamento.dataEsecuzione" /><br />
						<spring:message code="mypivot.visualizza.datiPagamento.causale" />
					</div>
				</div>
				<div class="span2 text-center pagatiHeader">
					&nbsp;
				</div>
			</div>

			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
							<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>

				<div class="row-fluid mypay-custom small-text2">
					<div class="span3 text-center pagatiRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.datiSpecificiRiscossione}" />
						</div>
					</div>
					<div class="span2 text-center pagatiRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
							<c:if
							test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
								<br />
								(
								<spring:message
									code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
								)
						</c:if>
						</div>
					</div>
					<div class="span1 text-center pagatiRow">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
						</div>
					</div>
					<div class="span4 text-center pagatiRow">
						<div class="font-10 wrapfix">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsecuzionePagamento}" /><br />
							<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>70}">
								<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 70)}" />...
							</c:if>
							<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=70}">
								<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
							</c:if>
						</div>
					</div>
					<div class="span2 text-center pagatiRow">
						<jsp:include page="noSubset_buttons.jsp"></jsp:include>
					</div>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'RT_NO_IUD' or visualizzaCompletaCommand.tipoErrore == 'RT_IUF_TES'}">

			<!-- intestazione -->
			<div class="row-fluid mypay-custom">
				<div class="span4 text-center ricevutaHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
				</div>
				<div class="span4 text-center rendicontazioneHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
				</div>
				<div class="span4 text-center tesoreriaHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
				</div>
			</div>

			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>

				<div class="row-fluid data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="noSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid mypay-custom small-text <c:if test="${empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}">colored</c:if>">

					<div class="span4 ricevutaTesoreriaRow ricevutaSpan4false">
						<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;</label>
						<c:out	value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.codiceIuv}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUR" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.identificativoUnivocoRiscossione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsito" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsitoSingoloPagamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.attestante" />&nbsp;</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.denominazioneAttestante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}" />
								<br />
								<c:if
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante  }">
									<div class="no_wrap_left">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />

						<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
									<div class="no_wrap">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.versante" />&nbsp;</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaVersante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'G'))}">
									<div class="no_wrap">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;</label>
						<div class="wrapfix">
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
							<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
						</c:if>
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
						</c:if>
						</div>
					</div>


					<div class="span4 rendicontazioneTesoreriaRow rendicontazioneSpan4false">
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.identificativoFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.dataOraFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.identificativoUnivocoRegolamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRegolamento}" /><br />
						<c:if
							test="${visualizzaCompletaDto.identificativoFlussoRendicontazione == 'n/a'}">
							<div class="no_wrap">
								(
								<spring:message
									code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
								)
							</div><br />
						</c:if>

						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.importoTotalePagamenti}" />
					</div>


					<div class="span4 tesoreriaRow tesoreriaSpan4false">
						<label><spring:message code="mypivot.visualizza.conto" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codConto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataValuta}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataContabile}" /><br />
						
						<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deImporto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codOr1}" />
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codIdDominio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio)}" />
						</c:if>
					</div>
				</div>
			</c:forEach>
		</c:if>
		
		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'RT_TES'}">

			<!-- intestazione -->
			<div class="row-fluid mypay-custom">
				<div class="span6 text-center ricevutaHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
				</div>
				<div class="span6 text-center tesoreriaHeader row-border">
					<spring:message code="mypivot.visualizzaCompleta.tesoreria" />
				</div>
			</div>

			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>

				<div class="row-fluid data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="noSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid mypay-custom small-text <c:if test="${empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}">colored</c:if>">

					<div class="span6 ricevutaTesoreriaRow ricevutaSpan6false">
						<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;</label>
						<c:out	value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.codiceIuv}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUR" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.identificativoUnivocoRiscossione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsito" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsitoSingoloPagamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.attestante" />&nbsp;</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.denominazioneAttestante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}" />
								<br />
								<c:if
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante  }">
									<div class="no_wrap_left">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />

						<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
									<div class="no_wrap">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.versante" />&nbsp;</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaVersante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'G'))}">
									<div class="no_wrap">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;</label>
						<div class="wrapfix">
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
							<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
						</c:if>
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
						</c:if>
						</div>
					</div>

					<div class="span6 tesoreriaRow tesoreriaSpan6false">
						<label><spring:message code="mypivot.visualizza.conto" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codConto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataValuta}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataContabile}" /><br />
						
						<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deImporto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codOr1}" />
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codIdDominio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio)}" />
						</c:if>
					</div>
				</div>
			</c:forEach>
		</c:if>
		
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

			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>

				<div class="row-fluid data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="noSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid mypay-custom small-text <c:if test="${empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}">colored</c:if>">

					<div class="span6 rendicontazioneTesoreriaRow ricevutaSpan6false">
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.identificativoFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.dataOraFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.identificativoUnivocoRegolamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRegolamento}" /><br />
						<c:if
							test="${visualizzaCompletaDto.identificativoFlussoRendicontazione == 'n/a'}">
							<div class="no_wrap">
								(
								<spring:message
									code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
								)
							</div><br />
						</c:if>

						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.importoTotalePagamenti}" />
					</div>


					<div class="span6 tesoreriaRow tesoreriaSpan6false">
						<label><spring:message code="mypivot.visualizza.conto" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codConto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataValuta}" /><br />
						
						<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataContabile}" /><br />
						
						<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deImporto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codOr1}" />
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codBolletta)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codIdDominio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIdDominio)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRicezione)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codDocumento" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codDocumento)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deAnnoProvvisorio)}" />
						</c:if>
						
						<c:if test="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio) ne 'n/a'}">
							<label><spring:message code="mypivot.visualizza.codProvvisorio" />&nbsp;:</label>
							<c:out value="${fn:trim(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codProvvisorio)}" />
						</c:if>
					</div>
				</div>
			</c:forEach>
		</c:if>
		
		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'RT_IUF'}">
			<!-- intestazione -->
			<div class="row-fluid mypay-custom">
				<div class="span6 text-center ricevutaHeader  row-border">
					<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
				</div>
				<div class="span6 text-center rendicontazioneHeader  row-border">
					<spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
				</div>
			</div>

			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>
			
				<div class="row-fluid text-center data-ult-agg-row-padding data-ult-agg-row-color">
					<div class="span12">
						<div class="clearfix testata-multicolonna">
							<div class="pull-left">
								<label>
									<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />&nbsp;:
									<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
								</label>
							</div> 
							<div class="pull-right">
								<jsp:include page="noSubset_buttons.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid mypay-custom small-text <c:if test="${empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}">colored</c:if>">
							
					<div class="span6 ricevutaTesoreriaRow ricevutaSpan6false">
						<label><spring:message code="mypivot.visualizza.tipoDovuto" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUD" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.codiceIuv}" /><br />
						
						<label><spring:message code="mypivot.visualizza.identificativo.IUR" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.identificativoUnivocoRiscossione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.dataEsito" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsitoSingoloPagamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.attestante" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.denominazioneAttestante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}" />
								<br />
								<c:if
									test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante  }">
									<div class="no_wrap_left">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />

						<label><spring:message code="mypivot.visualizza.pagatore" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
									<div class="no_wrap">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.versante" />&nbsp;:</label>
						<c:choose>
							<c:when
								test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}">
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaVersante}" />
								<br />
								<c:out
									value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}" />
								<br />
								<c:if
									test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'G'))}">
									<div class="no_wrap">
										(
										<spring:message
											code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante}" />
										)
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								n/a
							</c:otherwise>
						</c:choose><br />
						
						<label><spring:message code="mypivot.visualizza.datiPagamento.causale" />&nbsp;:</label>
						<div class="wrapfix">
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>140}">
							<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 140)}" />...
						</c:if>
						<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=140}">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
						</c:if>
						</div>
					</div>


					<div class="span6 rendicontazioneTesoreriaRow rendicontazioneSpan6false">
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.identificativoFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.dataOraFlussoRendicontazione}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.identificativo" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.identificativoUnivocoRegolamento}" /><br />
						
						<label><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataRegolamento}" /><br />
						<c:if
							test="${visualizzaCompletaDto.identificativoFlussoRendicontazione == 'n/a'}">
							<div class="no_wrap">
								(
								<spring:message
									code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
								)
							</div><br />
						</c:if>

						<label><spring:message code="mypivot.visualizza.regolamento.importoTotale" />&nbsp;:</label>
						<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.importoTotalePagamenti}" />
					</div>
				</div>
			</c:forEach>
		</c:if>

		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'RT_NO_IUF'}">

			<div class="row-fluid mypay-custom row-border ricevutaTesoreriaHeaderSingle">
				<div class="span12 text-center ricevutaTesoreriaHeader">
					<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
				</div>
			</div>
			<div class="row-fluid mypay-custom ricevutaTesoreriaHeaderSingleB">
				<div class="span2 text-center ricevutaTesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.tipoDovuto" /><br />
						<spring:message code="mypivot.visualizza.identificativo.IUD" /><br />
						<spring:message code="mypivot.visualizza.identificativo.IUV" />
					</div>
				</div>
				<div class="span1 text-center ricevutaTesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.identificativo.IUR" /><br />
						<spring:message code="mypivot.visualizza.attestante" />
					</div>
				</div>
				<div class="span2 text-center ricevutaTesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.versante" />
					</div>
				</div>
				<div class="span2 text-center ricevutaTesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.pagatore" />
					</div>
				</div>
				<div class="span1 text-center ricevutaTesoreriaHeader">
					<div class="font-12">
						<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />
					</div>
				</div>
				<div class="span2 text-center ricevutaTesoreriaHeader">
					<div class="no_wrap">
						<spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" /><br />
						<spring:message code="mypivot.visualizza.datiPagamento.dataEsito" /><br />
						<spring:message code="mypivot.visualizza.datiPagamento.causale" />
					</div>
				</div>
				<div class="span2 text-center ricevutaTesoreriaHeader">&nbsp;</div>
			</div>

			<c:forEach var="visualizzaCompletaDto" items="${visualizzaCompletaDtoPage.list}">
				<c:set var="visualizzaCompletaDto" scope="request" value="${visualizzaCompletaDto}"/>
				<div class="row-fluid mypay-custom small-text2">
					<div class="span2 text-center ricevutaRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoDovuto}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIud}" /><br />
							<c:out value="${visualizzaCompletaDto.codiceIuv}" />
						</div>
					</div>
					<div class="span1 text-center ricevutaRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.identificativoUnivocoRiscossione}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.denominazioneAttestante}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoAttestante}" />
							<c:if test="${not empty visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante  }">
								<br />
								(
									<spring:message	code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoAttestante}" />
								)
							</c:if> 
						</div>
					</div>
					<div class="span2 text-center ricevutaRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaVersante}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoVersante}" />
							<c:if test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante == 'G'))}">
								<br />
								(
									<spring:message	code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoVersante}" />
								)
							</c:if>
						</div>
					</div>
					<div class="span2 text-center ricevutaRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.anagraficaPagatore}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codiceIdentificativoUnivocoPagatore}" />
							<c:if test="${((visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
								<br />
								(
									<spring:message code="mypivot.tipoIdentificativoUnivoco.${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.tipoIdentificativoUnivocoPagatore}" />
								)
							</c:if>
						</div>
					</div>
					<div class="span1 text-center ricevutaRow ${visualizzaCompletaDto.rowClass}">
						<div class="no_wrap">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataUltimoAggiornamento}" />
						</div>
					</div>
					<div class="span2 text-center ricevutaRow ${visualizzaCompletaDto.rowClass}">
						<div class="font-10 wrapfix">
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.singoloImportoPagato}" /><br />
							<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.deDataEsitoSingoloPagamento}" /><br />
							<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)>50}">
								<c:out value="${fn:substring(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento, 0, 50)}" />...
							</c:if>
							<c:if test="${fn:length(visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento)<=50}">
								<c:out value="${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.causaleVersamento}" />
							</c:if>
						</div>
					</div>
					<div class="span2 text-center ricevutaRow no_wrap ${visualizzaCompletaDto.rowClass}">
						<jsp:include page="noSubset_buttons.jsp"></jsp:include>
					</div>
				</div>
			</c:forEach>	
		</c:if>

	</div>

	<div class="row-fluid mypay-custom row-border">
		<div class="span12">
			<div class="pagination pagination-left">
				<spring:message code="mypivot.pager.pagina" />
				<c:out value="${visualizzaCompletaDtoPage.page}" />
				<spring:message code="mypivot.pager.di" />
				<c:out value="${visualizzaCompletaDtoPage.totalPages}" />
				-
				<spring:message code="mypivot.pager.elementiDa" />
				<c:out
					value="${(visualizzaCompletaDtoPage.page-1) * visualizzaCompletaDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" />
				<c:out
					value="${(visualizzaCompletaDtoPage.page-1) * visualizzaCompletaDtoPage.pageSize + fn:length(visualizzaCompletaDtoPage.list)}" />

			</div>

			<div>
				<div class="pagination pagination-right">

					<ul>
						<c:if test="${visualizzaCompletaDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=1&pgSize=${visualizzaCompletaDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
							</a></li>
						</c:if>

						<c:if test="${visualizzaCompletaDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${visualizzaCompletaDtoPage.page-1}&pgSize=${visualizzaCompletaDtoPage.pageSize}">
									<span>«</span>
							</a></li>
						</c:if>

						<c:if test="${visualizzaCompletaDtoPage.previousPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${visualizzaCompletaDtoPage.page-1}&pgSize=${visualizzaCompletaDtoPage.pageSize}">
									<span><c:out value="${visualizzaCompletaDtoPage.page-1}"></c:out></span>
							</a></li>
						</c:if>

						<li class="disabled"><span><c:out
									value="${visualizzaCompletaDtoPage.page}"></c:out></span></li>

						<c:if test="${visualizzaCompletaDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${visualizzaCompletaDtoPage.page+1}&pgSize=${visualizzaCompletaDtoPage.pageSize}">
									<span><c:out value="${visualizzaCompletaDtoPage.page+1}"></c:out></span>
							</a></li>
						</c:if>

						<c:if test="${visualizzaCompletaDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${visualizzaCompletaDtoPage.page+1}&pgSize=${visualizzaCompletaDtoPage.pageSize}">
									<span>»</span>
							</a></li>
						</c:if>

						<c:if test="${visualizzaCompletaDtoPage.nextPage}">
							<li><a
								href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html?pg=${visualizzaCompletaDtoPage.totalPages}&pgSize=${visualizzaCompletaDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
							</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${visualizzaCompletaDtoPage.totalRecords == 0}">
	<p class="muted text-center">
		<spring:message code="mypivot.visualizza.nessunDato" />
	</p>
</c:if>
