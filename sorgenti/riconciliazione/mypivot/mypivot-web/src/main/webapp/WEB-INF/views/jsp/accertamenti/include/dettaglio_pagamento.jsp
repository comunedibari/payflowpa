<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- 

	TEMPLATE HTML DEI PAGAMENTI DI UN ACCERTAMENTO
	
-->

<%
	/* Inserisco al contesto pagina gli stati che descrivo l'evoluzione dell'accertamento */
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO", 	 Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO);
%>

<div class="debiti_table rt_accertamento_table" id="pagamenti_table">
		
			
	<!-- INTESTAZIONE TABLE -->
	<div class="row-fluid mypay-custom row-border ricevutaTesoreriaHeaderSingle">
		<div class="span12 text-center ricevutaTesoreriaHeader">
			<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
		</div>
	</div>
	
	
	<div class="row-fluid mypay-custom ricevutaTesoreriaHeaderSingleB">
		
		<!-- Tipo dovuto - IUD - IUV -->
		<div class="${accertamentoDto.stato.codStato!=COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO ? 'span2_5' : 'span3'} text-center ricevutaTesoreriaHeader">
			<div class="no_wrap">
				<spring:message code="mypivot.visualizza.tipoDovuto" /><br />
				<spring:message code="mypivot.visualizza.identificativo.IUD" /><br />
				<spring:message code="mypivot.visualizza.identificativo.IUV" />
			</div>
		</div>
		
		<!-- IUR - Attestante -->
		<div class="span2 text-center ricevutaTesoreriaHeader">
			<div class="no_wrap">
				<spring:message code="mypivot.visualizza.identificativo.IUR" /><br />
				<spring:message code="mypivot.visualizza.attestante" />
			</div>
		</div>
		
		<!-- Versante -->
		<div class="span2 text-center ricevutaTesoreriaHeader">
			<div class="no_wrap">
				<spring:message code="mypivot.visualizza.versante" />
			</div>
		</div>
		
		<!-- Pagatore -->
		<div class="span2 text-center ricevutaTesoreriaHeader">
			<div class="no_wrap">
				<spring:message code="mypivot.visualizza.pagatore" />
			</div>
		</div>
		
		<!-- Data ultimo agg -->
		<div class="span1 text-center ricevutaTesoreriaHeader">
			<div class="font-12">
				<spring:message code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" />
			</div>
		</div>
		
		<!-- Importo - Data Esito - Causale -->
		<div class="span2 text-center ricevutaTesoreriaHeader">
			<div class="no_wrap">
				<spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" /><br />
				<spring:message code="mypivot.visualizza.datiPagamento.dataEsito" /><br />
				<spring:message code="mypivot.visualizza.datiPagamento.causale" />
			</div>
		</div>
		
		<!-- 
			View Edit Pagamenti: Header Checkbox vuoto
		-->
		<c:if test="${!isReadonly}">
			<div class="span0_5 text-center ricevutaTesoreriaHeader">&nbsp;</div>
		</c:if>
		
		<!-- 
			View Dettaglio Pagamenti di un accertamento in stato INSERITO o CHIUSO: Header button vuoto 
		-->
		<c:if test="${isReadonly and accertamentoDto.stato.codStato!=COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO}">
			<div class="span0_5 text-center ricevutaTesoreriaHeader">&nbsp;</div>
		</c:if>
	</div><!-- ./INTESTAZIONE TABLE -->
	
	
	<!-- 
		FOR RESULTS LIST
	 -->
	<c:forEach var="rowDto" items="${pagamentiAccertamentoCommand.resultList}" varStatus="status">
		<div class="row-fluid mypay-custom small-text2">
		
			<!-- Tipo dovuto - IUD - IUV -->
			<div class="${accertamentoDto.stato.codStato!=COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO ? 'span2_5' : 'span3'} text-center ricevutaRow visualizza-lista-row-na">
				<div class="no_wrap">
					<c:out value="${rowDto.flussoExportDTO.deTipoDovuto}" /><br />
					<c:out value="${rowDto.flussoExportDTO.codiceIud}" /><br />
					<c:out value="${rowDto.flussoExportDTO.codiceIuv}" />
				</div>
			</div>
			
			<!-- IUR - Attestante -->
			<div class="span2 text-center ricevutaRow visualizza-lista-row-na">
				<div class="no_wrap">
					<c:out value="${rowDto.flussoExportDTO.identificativoUnivocoRiscossione}" /><br />
					<c:out value="${rowDto.flussoExportDTO.denominazioneAttestante}" /><br />
					<c:out value="${rowDto.flussoExportDTO.codiceIdentificativoUnivocoAttestante}" />
					<c:if test="${not empty rowDto.flussoExportDTO.tipoIdentificativoUnivocoAttestante}">
						<br />
						(
						 <spring:message code="mypivot.tipoIdentificativoUnivocoAttestante.${rowDto.flussoExportDTO.tipoIdentificativoUnivocoAttestante}" />
						)
					</c:if> 
				</div>
			</div>
			
			<!-- Versante -->
			<div class="span2 text-center ricevutaRow visualizza-lista-row-na">
				<div class="no_wrap">
					<c:out value="${rowDto.flussoExportDTO.anagraficaVersante}" /><br />
					<c:out value="${rowDto.flussoExportDTO.codiceIdentificativoUnivocoVersante}" />
					<c:if test="${((rowDto.flussoExportDTO.tipoIdentificativoUnivocoVersante == 'F') or (rowDto.flussoExportDTO.tipoIdentificativoUnivocoVersante == 'G'))}">
						<br />
						(
						  <spring:message code="mypivot.tipoIdentificativoUnivoco.${rowDto.flussoExportDTO.tipoIdentificativoUnivocoVersante}" />
						)
					</c:if>
				</div>
			</div>
			
			<!-- Pagatore -->
			<div class="span2 text-center ricevutaRow visualizza-lista-row-na">
				<div class="no_wrap">
					<c:out value="${rowDto.flussoExportDTO.anagraficaPagatore}" /><br />
					<c:out value="${rowDto.flussoExportDTO.codiceIdentificativoUnivocoPagatore}" />
					<c:if test="${((rowDto.flussoExportDTO.tipoIdentificativoUnivocoPagatore =='F') or (visualizzaCompletaDto.rowDto.tipoIdentificativoUnivocoPagatore == 'G'))}">
						<br />
						(
						 <spring:message code="mypivot.tipoIdentificativoUnivoco.${rowDto.flussoExportDTO.tipoIdentificativoUnivocoPagatore}" />
						)
					</c:if>
				</div>
			</div>
			
			<!-- Data ultimo agg -->
			<div class="span1 text-center ricevutaRow visualizza-lista-row-na">
				<div class="no_wrap">
					<c:out value="${rowDto.flussoExportDTO.deDataUltimoAggiornamento}" />
				</div>
			</div>
			
			<!-- Importo - Data Esito - Causale -->
			<div class="span2 text-center ricevutaRow visualizza-lista-row-na">
				<div class="font-10 wrapfix">
					<c:out value="${rowDto.flussoExportDTO.formatSingoloImportoPagato}" /><br />
					<c:out value="${rowDto.flussoExportDTO.dataEsitoSingoloPagamento}" /><br />
					<c:if test="${fn:length(rowDto.flussoExportDTO.causaleVersamento)>50}">
						<c:out value="${fn:substring(rowDto.flussoExportDTO.causaleVersamento, 0, 50)}" />...
					</c:if>
					<c:if test="${fn:length(rowDto.flussoExportDTO.causaleVersamento)<=50}">
						<c:out value="${rowDto.flussoExportDTO.causaleVersamento}" />
					</c:if>
				</div>
			</div>
			
			<!-- 
				View Edit Pagamenti: Mostro Checkbox 
			-->
			<c:if test="${!isReadonly}">
				<div class="span0_5 text-center ricevutaRow no_wrap visualizza-lista-row-na">
					<div class="btn-group">
						<!-- CHECKBOX -->
						<input id="checkId_${status.index}" name="resultList[${status.index}].selected" type="checkbox" onclick="javascript:selectRow();"/>
						<input name="resultList[${status.index}].id" 	 value="${rowDto.id}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.deTipoDovuto" value="${rowDto.flussoExportDTO.deTipoDovuto}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codTipoDovuto" value="${rowDto.flussoExportDTO.codTipoDovuto}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codiceIud" value="${rowDto.flussoExportDTO.codiceIud}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codiceIuv" value="${rowDto.flussoExportDTO.codiceIuv}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.identificativoUnivocoRiscossione" value="${rowDto.flussoExportDTO.identificativoUnivocoRiscossione}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.denominazioneAttestante" value="${rowDto.flussoExportDTO.denominazioneAttestante}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codiceIdentificativoUnivocoAttestante" value="${rowDto.flussoExportDTO.codiceIdentificativoUnivocoAttestante}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.dataEsitoSingoloPagamento" value="${rowDto.flussoExportDTO.dataEsitoSingoloPagamento}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.causaleVersamento" value="${rowDto.flussoExportDTO.causaleVersamento}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.singoloImportoPagato" value="${rowDto.flussoExportDTO.singoloImportoPagato}" type="hidden" />
					</div>
				</div><!-- ./checkbox -->
			</c:if>
			
			
			<!-- 
				View Dettaglio Pagamenti di un accertamento in stato INSERITO o CHIUSO: Mostro il button per il dettaglio capitoli associati alla RT 
			-->
			<c:if test="${isReadonly and accertamentoDto.stato.codStato!=COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO}">
				<div class="span0_5 text-center ricevutaRow no_wrap visualizza-lista-row-na">
					<div class="btn-group">
					
						<!-- Btn -->
						<a class="btn btn-mini btn-info" href="javascript:void(0);" onclick="javascript:openDettaglio(event, ${status.index});" style="border-radius: 4px;" title="<spring:message code='mypivot.accertamenti.btn.title.openDttCapitoli'/>">
							<i class="fa fa-pencil-square-o bold" aria-hidden="true"></i>
						</a>
						
						<!-- CHECKBOX -->
						<input id="checkId_${status.index}" name="resultList[${status.index}].selected" type="checkbox" class="hidden"/>
						<input name="resultList[${status.index}].flussoExportDTO.deTipoDovuto" value="${rowDto.flussoExportDTO.deTipoDovuto}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codTipoDovuto" value="${rowDto.flussoExportDTO.codTipoDovuto}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codiceIud" value="${rowDto.flussoExportDTO.codiceIud}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codiceIuv" value="${rowDto.flussoExportDTO.codiceIuv}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.identificativoUnivocoRiscossione" value="${rowDto.flussoExportDTO.identificativoUnivocoRiscossione}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.denominazioneAttestante" value="${rowDto.flussoExportDTO.denominazioneAttestante}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.codiceIdentificativoUnivocoAttestante" value="${rowDto.flussoExportDTO.codiceIdentificativoUnivocoAttestante}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.dataEsitoSingoloPagamento" value="${rowDto.flussoExportDTO.dataEsitoSingoloPagamento}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.causaleVersamento" value="${rowDto.flussoExportDTO.causaleVersamento}" type="hidden" />
						<input name="resultList[${status.index}].flussoExportDTO.formatSingoloImportoPagato" value="${rowDto.flussoExportDTO.formatSingoloImportoPagato}" type="hidden" />
					</div>
				</div>
			</c:if>
			
		</div><!-- /.row-fluid -->
		
		
		<!-- 
			DETTAGLIO CAPITOLI RT
		-->
		<c:if test="${isReadonly and accertamentoDto.stato.codStato!=COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO and not empty showCapitoli and showCapitoli and not empty dtt_Capitoli['openAs'] and dtt_Capitoli['openAs'] eq 'INLINE' and dtt_Capitoli['flussoRT'].codiceIud eq rowDto.flussoExportDTO.codiceIud and dtt_Capitoli['flussoRT'].codiceIuv eq rowDto.flussoExportDTO.codiceIuv}">
			<div class="tdDetail" id="tableId_${status.index}" tabindex='1'>
				
				<!-- Intestazione -->
				<p><spring:message code="mypivot.accertamenti.label.dttCapitoli" /></p>
				
				<!-- Btn Chiudi -->
				<div class="btn-group">
					<a class="btn btn-mini btn-warning" href="javascript:void(0);" onclick="javascript:closeDettaglio(event, ${status.index});" title="<spring:message code='mypivot.accertamenti.btn.title.closeDttCapitoli'/>">
						<i class="fa fa-times" aria-hidden="true"></i>
					</a>
				</div>
				
				<!-- Tabella -->
				<jsp:include page="dettaglio_capitoli.jsp" />
		    </div>
		    
		    <script type="text/javascript">
		    	/*
		    	 * Imposto il focus sul div del dettaglio
		    	 */
		    	$("body").scrollTop($("#tableId_${status.index}").offset().top);
			</script>
		</c:if>
		
	</c:forEach>
	
</div><!-- /.debiti_table -->
