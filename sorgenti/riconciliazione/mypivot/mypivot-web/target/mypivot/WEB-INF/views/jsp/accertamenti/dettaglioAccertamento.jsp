<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%
	/* Inserisco al contesto pagina gli stati che descrivo l'evoluzione dell'accertamento */
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_INSERITO",	 Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO);
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_CHIUSO",		 Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO);
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO", 	 Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO);
%>
<script type="text/javascript">

	/**
	 * Recupero dal model la variabile "notAuthorizedClosedEmptyAcc" la quale servirà per determinare se al carimento della pagina 
	 * ci sono messaggi di errore da mostrare all'utente. Se si, il messaggio è mostrato in popup.
	 */
	var notAuthorizedClosedEmptyAcc = '${notAuthorizedClosedEmptyAcc}';
	/* 
	 * Il numero di elementi ritornati dalla ricerca mi serve per capire il numero di elementi in pagina.
	 */
	var sizeResultList = ${pagamentiAccertamentoCommand.resultList.size()}; 
	/**
	 * Recupero dal model la variabile "showCapitoli" la quale servirà per determinare se al carimento della pagina 
	 * c'e' da mostrare il dettaglio capitoli della RT selezionata.
	 */
	var showCapitoli = "${showCapitoli}";

	
	$(document).ready(function() {
		/** 
		 * Mi assicuro che tutti i checkbox siano per default deselezionati.
		 */
		$("#pagamenti_table input[type=checkbox]").prop("checked", false);
		
		/**
		 * Controllo se la variabile 'notAuthorizedClosedEmptyAcc' e' valorizzata a true
		 */
		if(notAuthorizedClosedEmptyAcc !== "" && notAuthorizedClosedEmptyAcc == "true"){
			$("#dialog-messages").dialog({
				resizable: false,
			   	height: "auto",
			    width: 400,
			    modal: true,
			    buttons: {
			    	"OK": function() {
			        	$( this ).dialog( "close" );
			        }
	        	}
			});
		}// close if

		/**
		 * Controllo se la variabile 'showCapitoli' e' valorizzata a true
		 */
		if(showCapitoli !== "" && showCapitoli == "true"){
			/*
			 * Determina la modalita' in cui sara' mostrato il dettaglio capitoli.
			 * Se l'intero importo della RT e' attribuito ad un singolo ufficio/capitolo/accertamento lo mostriamo inline.
			 * Altrimenti, tramite un pulsante di dettaglio apriamo un popup che riporta la lista delle tuple in una tabellina.
			 */
			var openAs = "${dtt_Capitoli['openAs']}"; 

			if(openAs != "" && openAs == "POPUP"){
				$("#dialog-capitoli").dialog({
					resizable: false,
				   	height: "auto",
				    width: 800,
				    dialogClass: "uid-info",
				    modal: true,
				    buttons: {
				    	"OK": function() {
				        	$( this ).dialog( "close" );
				        }
		        	}
				});
			}
		}// close if captioli
	});

	/**
	 * Funzione associata al Click sul button del dettaglio capitoli.
	 * Deseleziono tutti i checkbox in pagina, per poi selezionare solo quello corrispondente
	 * alla riga appena seleziona.
	 * Dopodicchè seto l'operazione e faccio il submit del form.
	 *
	 * @param {int} selected, posizione della riga della tabella di cui mostrare il dettaglio
	 */
	function openDettaglio(event, selected) {
		if(event) event.preventDefault();

		var index = 0
		while (index < sizeResultList) {
			/* Deseleziono il checkbox */
			$("#checkId_" + index).prop('checked', false);
			/* */
		    index++;
		}
		
		/* Seleziono il checkbox corrispondente al button cliccato */
		$("#checkId_" + selected).prop('checked', true);

		/* Set operation*/
		$('#operation').val("DETAIL");

		var form = $('#formRcPagamenti');
			form.submit();
	}// cose openDettaglio

	/**
	 * Funzione richiamata al click del button "Chiudi" presente nel dettaglio capitoli quando questo è
	 * mostrato inline.
	 *
	 * @param {int} selected, posizione della riga della tabella di cui nascondere il dettaglio
	 */
	function closeDettaglio(event, selected) {
		if(event) event.preventDefault();
		/*
		 * Add class hidden al dettaglio
		 */
		$("#tableId_" + selected).addClass("hidden");

		/* Set operation */
		$('#operation').val("RC");
	}
	
</script>


<div class="home_content">

	<!-- TITOLO PAGINA -->
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.accertamenti.title.page.accertamento" /> / 
			<span class="txt-titolo"><spring:message code="mypivot.accertamenti.title.page.dettaglio" /></span>
		</h1>
	</div>
	
	
	<!-- 
		DIALOG MESSAGE ERROR 
	-->
	<c:if test="${notAuthorizedClosedEmptyAcc}">
		<div id="dialog-messages" title="<spring:message code="mypivot.messages.warning" />" style="display: none;">
			<spring:message code="mypivot.accertamenti.errore.notAuthorizedClosedEmptyAcc" />
		</div>
	</c:if>
	
	<!-- 
		DIALOG CAPITOLI 
	-->
	<c:if test="${showCapitoli}">
		<div id="dialog-capitoli" title="<spring:message code="mypivot.messages.info" />" style="display: none;">
			<tiles:insertAttribute name="popup_capitoli" />
		</div>
	</c:if>
	
	
	<!-- 
	 	ANAGRAFICA ACCERTAMENTO 
	-->
	<tiles:insertAttribute name="dettaglio_accertamento" />
	
	
	<!-- 
	 	FORM PER LA DEFINIZIONE DEI PAGAMENTI IN ACCERTAMENTO 
	-->
	<form:form method="post" modelAttribute="pagamentiAccertamentoCommand" id="formRcPagamenti">
		
		<!-- 
	 		FORM FILTRI DI RICERCA PAGAMENTI
		-->
		<tiles:insertAttribute name="ricerca_pagamenti" />
		
		
		<!-- MESSAGE -->
		<div class="row-fluid">
			<tiles:insertAttribute name="message" />
		</div>
	
	
		<!-- 
	 		TABELLA RT SPROVVISTE DI BILANCIO
		-->
		<c:if test="${not empty pagamentiAccertamentoCommand and pagamentiAccertamentoCommand.totalRecords > 0}">
			
			<!-- Codice operation da sottomettere al submit -->
			<form:input id="operation" path="operation" type="hidden" name="operation" value="RC"/>
		
			<!-- Per il caso di OPERATION "DETAIL", l'attributo contiene lo iud e iuv della RT selezionata. -->
			<form:input  path="page" type="hidden"/>
			
				
			<!-- 
				Template Html dei pagamenti in accertamento
			-->
			<tiles:insertAttribute name="dettaglio_pagamento" />
			
			
			<!-- PAGINAZIONE TABELLA RICERCA -->
			<div class="row-fluid mypay-custom row-border">
				<div class="span12">
				
					<div class="pagination-left">
						<!-- Es: Pagina 1 di 33 -->
						<spring:message code="mypivot.pager.pagina" /> <c:out value="${pagamentiAccertamentoCommand.page}" /> 
						<spring:message code="mypivot.pager.di" /> <c:out value="${pagamentiAccertamentoCommand.totalPages}" />
						-
						<!-- Es: Elementi da 1 a 5 -->
						<spring:message code="mypivot.pager.elementiDa" /> <c:out value="${(pagamentiAccertamentoCommand.page-1) * pagamentiAccertamentoCommand.pageSize + 1}" />
						<spring:message code="mypivot.pager.a" /> <c:out value="${(pagamentiAccertamentoCommand.page-1) * pagamentiAccertamentoCommand.pageSize + fn:length(pagamentiAccertamentoCommand.resultList)}" />
					</div><!-- /.pagination-left -->
					
					<div>
						<div class="pagination pagination-right">
							<ul>
								<c:if test="${pagamentiAccertamentoCommand.previousPage}">
									<li>
										<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}&pg=1&pgSize=${pagamentiAccertamentoCommand.pageSize}">
											<span><spring:message code="mypivot.pager.prima" /></span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.previousPage}">
									<li>
										<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}&pg=${pagamentiAccertamentoCommand.page-1}&pgSize=${pagamentiAccertamentoCommand.pageSize}">
											<span>&#171;</span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.previousPage}">
									<li>
										<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}&pg=${pagamentiAccertamentoCommand.page-1}&pgSize=${pagamentiAccertamentoCommand.pageSize}">
											<span><c:out value="${pagamentiAccertamentoCommand.page-1}"></c:out></span>
										</a>
									</li>
								</c:if>
			
								<li class="disabled"><span><c:out value="${pagamentiAccertamentoCommand.page}"></c:out></span></li>
			
								<c:if test="${pagamentiAccertamentoCommand.nextPage}">
									<li>
										<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}&pg=${pagamentiAccertamentoCommand.page+1}&pgSize=${pagamentiAccertamentoCommand.pageSize}">
											<span><c:out value="${pagamentiAccertamentoCommand.page+1}"></c:out></span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.nextPage}">
									<li>
										<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}&pg=${pagamentiAccertamentoCommand.page+1}&pgSize=${pagamentiAccertamentoCommand.pageSize}">
											<span>&#187;</span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.nextPage}">
									<li>
										<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}&pg=${pagamentiAccertamentoCommand.totalPages}&pgSize=${pagamentiAccertamentoCommand.pageSize}">
											<span><spring:message code="mypivot.pager.ultima" /></span>
										</a>
									</li>
								</c:if>
							</ul>
						</div><!-- /.pagination-right -->
					</div>
				</div>
			</div><!-- /.row-fluid -->
		</c:if>

		<!-- LABEL PAGINAZIONE 0 RISULTATI -->
		<c:if test="${empty pagamentiAccertamentoCommand or pagamentiAccertamentoCommand.totalRecords == 0}">
			<p class="muted text-center">
				<spring:message code="mypivot.accertamenti.pagination.nessunDato" />
			</p>
		</c:if>
		
		
		<!-- 
			PULSANTIERA : TORNA ALLA RICERCA - DETTAGLIO
		-->
		<div class="well clearfix mtop20">
			
			<!-- TORNA ALLA RICERCA -->
			<div class="btn-group pull-left">
				<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html" class="btn btn-large">
					<i class="fa fa-chevron-circle-left fa-lg"></i> <spring:message code="mypivot.accertamenti.btn.label.indietro" /> 
				</a>
			</div>
			
			
			<div class="btn-group pull-right">
				<!-- DOWNLOAD 
				<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_CHIUSO}">
					<spring:message code="mypivot.accertamenti.btn.title.download" var="titleBtDownload"/>
					<a class="btn btn-large btn-default" href="<%=request.getContextPath() %>/protected/accertamenti/dettaglio/download.html?accertamentoID=${accertamentoDto.id}" title="${titleBtDownload}">
						<i class="fa fa-download" aria-hidden="true"></i> <spring:message code="mypivot.accertamenti.btn.label.download"/>
					</a>
				</c:if> -->
				
				<!-- CHIUDI -->
				<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_INSERITO}">
					<spring:message code="mypivot.accertamenti.btn.title.close" var="titleBtClose"/>
					<a class="btn btn-large btn-success" href="<%=request.getContextPath() %>/protected/accertamenti/dettaglio/setStato.html?accertamentoID=${accertamentoDto.id}&codStato=${COD_TIPO_STATO_ACCERTAMENTO_CHIUSO}" title="${titleBtClose}">
						<i class="fa fa-times-circle" aria-hidden="true"></i> <spring:message code="mypivot.accertamenti.btn.label.chiudi"/>
					</a>
				</c:if>
				
				<!-- ANNULLA -->
				<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_INSERITO}">
					<spring:message code="mypivot.accertamenti.btn.title.cancel" var="titleBtCancel"/>
					<a class="btn btn-large btn-inverse" href="<%=request.getContextPath() %>/protected/accertamenti/dettaglio/setStato.html?accertamentoID=${accertamentoDto.id}&codStato=${COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO}" title="${titleBtCancel}">
						<i class="fa fa-ban" aria-hidden="true"></i> <spring:message code="mypivot.accertamenti.btn.label.annulla"/>
					</a>
				</c:if>
			</div>
			
		</div><!-- /.well -->
	</form:form>
		
</div><!-- /.home_content -->
