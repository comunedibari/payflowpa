<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoSceltaUfficioCapitoloCommand"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">

	/**
	 * Sottometto il form della ricerca
	 *
	 * @param {link OPERATION} op, operation da eseguire lato server
	 * @param {link String} index, valorizzato con l'indice del capitolo da rimuovere se l'operation è "DEL_ITEM"
	 */
	function submitFormRicerca(op, index){
		/* set index */
		if(typeof index != 'undefined' && index != '') $('#rmIndex').val(index);
		/* set operation */
		$('#operation').val(op);

		var form = $('#formSceltaUffici');
			form.submit();
	}

	$(document).ready(function() {
		/**
		 * Recupero dal model la variabile "doSubmit" la quale servirà per determinare se al carimento della pagina 
		 * devo forzare il submit della form.
		 */
		var doSubmit = ${doSubmit};

		if(doSubmit){
			var form = $('#formSceltaUffici'); 	form.submit();
		}

		/**
		 * Build tooltip button importo totale dei dovuti e importo totale dei capitoli.
		 */
		$('#formSceltaUffici #tooltipAcc').tooltip({
			"container": "body",
			"trigger":   "click",
			"placement": "top"
		});
		$('#formSceltaUffici #tooltipMulti').tooltip({
			"container": "body",
			"trigger":   "click",
			"placement": "top"
		});

		/*
		 * Imposto il focus sul div che contiene le combo.
		 */
		$("body").scrollTop($("#formSceltaUffici #focusInsert").offset().top);
	});

</script>


<div class="home_content ufficio_accertamento">

	<!-- TITOLO PAGINA -->
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.accertamenti.title.page.accertamento" /> / 
			<span class="txt-titolo"><spring:message code="mypivot.accertamenti.title.page.sceltaUfficio" /></span>
		</h1>
	</div>

	<!-- 
	 	ANAGRAFICA ACCERTAMENTO 
	-->
	<tiles:insertAttribute name="dettaglio_accertamento" />


	<!-- 
		FORM DEFINIZIOE DEI CAPITOLI IN ACCERTAMENTO
	 -->
	<form:form method="post" modelAttribute="sceltaCommand" id="formSceltaUffici">
			
		<!-- Identificativo dell'accertamento per cui aggiungere i pagamenti -->
		<form:input path="accertamentoId" type="hidden" name="accertamentoId" />
		
		<!-- Importo totale dei dovuti nell'accertamento (BigDecimal) -->
		<form:input path="totImportoDovuti" type="hidden" name="totImportoDovuti" />
		
		<!-- Importo totale dei dovuti nell'accertamento (Format: € 249.57) -->
		<form:input path="formatTotImportoDovuti" type="hidden" name="formatTotImportoDovuti" />
		
		<!-- Importo totale dei dovuti nell'accertamento (BigDecimal) -->
		<form:input path="totImportoCapitoli" type="hidden" name="totImportoCapitoli" />
		
		<!-- Importo totale dei dovuti nell'accertamento (Format: € 249.57) -->
		<form:input path="formatTotImportoCapitoli" type="hidden" name="formatTotImportoCapitoli" />
		
		<!-- Importo totale che resta da assegnare ai capitoli (Format: € 249.57) -->
		<form:input path="formatTotImportoMancante" type="hidden" name="formatTotImportoMancante" />
		
		<!-- Determina se è in gestione il caso di selezione di un singolo pagamento o di molteplici pagamanti -->
		<form:input path="multiple" type="hidden" name="multiple"/>
		
		<!-- Codice operation da sottomettere al submit -->
		<form:input id="operation" path="operation" type="hidden" name="operation"/>
		
		<!-- Per il caso di OPERATION "DEL_ITEM", l'attributo indica l'indice da rimuovere nella lista dei capitoli e accertamenti. -->
		<form:input id="rmIndex" path="rmIndex" type="hidden" name="rmIndex"/>
				
		
		<div class="row-fluid">	

			<!-- Input flussi da risottomettere al submit -->
			<c:forEach var="rowDto" items="${sceltaCommand.flussiExportDTO}" varStatus="status">
				<input name="flussiExportDTO[${status.index}].deTipoDovuto" value="${rowDto.deTipoDovuto}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].codTipoDovuto" value="${rowDto.codTipoDovuto}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].codiceIud" value="${rowDto.codiceIud}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].codiceIuv" value="${rowDto.codiceIuv}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].identificativoUnivocoRiscossione" value="${rowDto.identificativoUnivocoRiscossione}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].denominazioneAttestante" value="${rowDto.denominazioneAttestante}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].codiceIdentificativoUnivocoAttestante" value="${rowDto.codiceIdentificativoUnivocoAttestante}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].dataEsitoSingoloPagamento" value="${rowDto.dataEsitoSingoloPagamento}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].causaleVersamento" value="${rowDto.causaleVersamento}" type="hidden" />
				<input name="flussiExportDTO[${status.index}].singoloImportoPagato" value="${rowDto.singoloImportoPagato}" type="hidden" />
			</c:forEach>
			
			<!--  -->
			<c:set var="rowDto" scope="request" value="${sceltaCommand.flussiExportDTO[0]}" />
			
			<div class="panel panel-default table-responsive">
				<div class="panel-body" id="focusInsert">
					
					<!-- INTESTAZIONE SELEZIONE DI UNA SINGOLA RT -->
					<c:if test="${!sceltaCommand.multiple}">
						<div class="detailRT ofauto">
							
							<!-- Tipo dovuto - IUD - IUV -->
							<div class="span3">
								<p class="detailp bleft">
									<c:out value="${rowDto.deTipoDovuto}" /><br />
									<c:out value="${rowDto.codiceIud}" /><br />
									<c:out value="${rowDto.codiceIuv}" />
								</p>
							</div>
							
							<!-- IUR - Attestante -->
							<div class="span3">
								<p class="detailp bleft">
									<c:out value="${rowDto.identificativoUnivocoRiscossione}" /><br />
									<c:out value="${rowDto.denominazioneAttestante}" /><br />
									<c:out value="${rowDto.codiceIdentificativoUnivocoAttestante}" />
								</p>
							</div>
							
							<!-- Data Esito - Causale -->
							<div class="span3">
								<p class="detailp bleft">
									<c:out value="${rowDto.dataEsitoSingoloPagamento}" /><br />
									<c:if test="${fn:length(rowDto.causaleVersamento)>50}">
										<c:out value="${fn:substring(rowDto.causaleVersamento, 0, 50)}" />...
									</c:if>
									<c:if test="${fn:length(rowDto.causaleVersamento)<=50}">
										<c:out value="${rowDto.causaleVersamento}" />
									</c:if>
								</p>
							</div>
							
							<!-- Importo totale dei dovuti nell'accertamento (Format: € 249.57) -->
							<div class="span3 text-center">
								<p class="price">
									<c:out value="${sceltaCommand.formatTotImportoDovuti}" />
									<button id="tooltipAcc" type="button" class="info" title="<spring:message code='mypivot.accertamenti.tooltip.totaleImportoDovuti'/>">
										<i class="fa fa-info-circle" aria-hidden="true"></i>
									</button>
								</p>
							</div>
							
						</div><!-- /.detailRT -->
					</c:if><!-- /if singolo pagamento -->
					
					
					<!-- INTESTAZIONE SELEZIONE DI MOLTEPLICI RT -->
					<c:if test="${sceltaCommand.multiple}">
						<div class="detailRT ofauto">
							
							<!-- Hai aggiunto ... RT in accertamento -->
							<div class="span9">
								<p class="detailp bleft" style="padding: 10px;font-size: 0.9em;">
									<spring:message code="mypivot.accertamenti.label.numRtAccertamento" arguments="${sceltaCommand.flussiExportDTO.size()}" />
								</p>
							</div>
							
							<!-- Importo totale dei dovuti nell'accertamento (Format: € 249.57) -->
							<div class="span3 text-center">
								<p class="price">
									<c:out value="${sceltaCommand.formatTotImportoDovuti}" />
									<button id="tooltipAcc" type="button" class="info" title="<spring:message code='mypivot.accertamenti.tooltip.totaleImportoDovuti'/>">
										<i class="fa fa-info-circle" aria-hidden="true"></i>
									</button>
								</p>
							</div>
							
						</div><!-- /.detailRT -->
					</c:if>
					    
					 
					<!-- LISTA UFFICI VUOTA: mostro messaggio di errore -->
					<c:if test="${not empty emptyUffici and emptyUffici}">
						<div class="warn mtop10" style="width: 98%!important;">
							<p class="bold" style="color: #c00;">
								<i class="fa fa-exclamation-triangle"></i> <spring:message code="mypivot.accertamenti.errore.emptyUffici"/>
							</p>
						</div>
					</c:if>
					

					<c:if test="${not empty emptyUffici and !emptyUffici}">
						
						<c:if test="${not empty sceltaCommand.multiple and !sceltaCommand.multiple}">		
							<jsp:include page="include/singolo_pagamento.jsp"></jsp:include>
						</c:if>
						
						<c:if test="${not empty sceltaCommand.multiple and sceltaCommand.multiple}">		
							 <jsp:include page="include/molteplici_pagamenti.jsp"></jsp:include>
						</c:if>
					</c:if>
					
				</div><!-- /.panel-body -->
			</div><!-- /.panel -->
			
		</div><!-- /.row-fluid -->
		
		
		<!-- 
			Pulsantiera :
		-->
		<div class="well clearfix mtop20">
			
			<!-- TORNA INDIETRO -->
			<div class="btn-group pull-left">
				<a href="<%=request.getContextPath()%>/protected/accertamenti/addPagamenti.html?accertamentoID=${accertamentoDto.id}" class="btn btn-large">
					<i class="fa fa-chevron-circle-left fa-lg"></i> <spring:message code="mypivot.dettaglio.btn.indietro" /> 
				</a>
			</div>
			
			<!-- CONFERMA: visibile quando l'elenco uffici è vuoto -->
			<c:if test="${not empty sceltaCommand.enableConfirmButton and sceltaCommand.enableConfirmButton}">
				<div class="btn-group pull-right">
					<button type="button" class="btn btn-success btn-large" onclick="javascript:submitFormRicerca('SAVE')">
						<i class="fa fa-save fa-lg"></i> <spring:message code="mypivot.accertamenti.btn.label.conferma" />
					</button>
				</div>
			</c:if>
			
		</div><!-- /.well -->
		
	</form:form>
</div><!-- /.home_content -->
