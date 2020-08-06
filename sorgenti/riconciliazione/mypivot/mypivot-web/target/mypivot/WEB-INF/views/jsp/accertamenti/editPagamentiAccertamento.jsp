<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">

	/**
	 * Recupero dal model le variabili "isReadonly" e "isAddAction", e quali serviranno poi a customizzare il comportamento dei 
	 * pulsanti della view.
	 */
	var isReadonly = ${isReadonly};	  	/* Determina se sono nella view di dettaglio o di edit */
	var isAddAction = ${isAddAction}; 	/* Nel caso sono nella view di edit, individua se si tratta di un'aggiunta o cancellazione dei pagamenti in accertamento. */
	/* 
	 * Il numero di elementi ritornati dalla ricerca mi serve per capire se disabilitare i pulsanti "Seleziona/Deseleziona Tutti"
	 * qualora la lista fosse vuota.
	 */
	var sizeResultList = ${pagamentiAccertamentoCommand.resultList.size()}; 
	/**
	 * Path relativo delle pagini per aggiungere/rimuovere pagamenti in accertamento.
	 */	
	var R_PATH_PAGE_ADD = '${R_PATH_PAGE_ADD}';
	var R_PATH_PAGE_DEL = '${R_PATH_PAGE_DEL}';
	/**
	 * Recupero dal model la variabile "successRtAdd" e "successRtDel" la quale servirà per determinare se al carimento della pagina 
	 * ci sono messaggi da mostrare all'utente. Se si, il messaggio è mostrato in popup.
	 */
	var successRtAdd = '${successRtAdd}'; 	var successRtDel = '${successRtDel}';

	
	$(document).ready(function() {

		/** 
		 * Mi assicuro che tutti i checkbox siano per default deselezionati.
		 */
		$("#pagamenti_table input[type=checkbox]").prop("checked", false);
		
		/**
		 * Controllo se l'accertamento lo si sta consultando in edit.
		 */
		if(!isReadonly){
			/*
			 * Controllo se l'edit dell'accertamento è relativo l'aggiunta o la cancellazione delle RT.
			 */
    		if(isAddAction){
        		/* Disabilito di default il pulsante "Aggiungi Selezionati" */
				$("#aggiungiSelezionati").prop("disabled",true);
			  	$("#aggiungiSelezionati").addClass("disabled");
    		}else{
    			/* Disabilito di default il pulsante "Rimuovi Selezionati" */
				$("#rimuoviSelezionati").prop("disabled",true);
			  	$("#rimuoviSelezionati").addClass("disabled");
        	}
        	
    		/* Di default nascondo il pulsante di deselezione dei checkbox */ 
		 	$("#deselezionaTutti").hide();

    		/* Se la result list è vuota disabilito i pulsanti "Seleziona/Deseleziona Tutti" */
		 	if(sizeResultList == 0) emptyResultList();

			/**
			 * Controllo se la variabile 'successRtAdd' o 'successRtDel' è valorizzata a true, se si devo aprire la dialog
			 * col messaggio "Operazione eseguita con successo".
			 */
			if((successRtAdd !== "" && successRtAdd == "true") || (successRtDel !== "" && successRtDel == "true")){
				$("#dialog-messages").dialog({
					resizable: false,
				   	height: "auto",
				    width: 400,
				    modal: true,
				    dialogClass: "uid-success",
				    buttons: {
				    	"OK": function() {
				        	$( this ).dialog( "close" );
				        }
		        	}
				});
			}// close if
		}// close isReadonly
	});
	
	/**
	 * La funzione è richiamata quando la ricerca non ha prodotto alcun risultato, disabilita i 
	 * pulsanti di "Seleziona/Deseleziona Tutti".
	 */
	function emptyResultList(){
		$("#selezionaTutti").prop("disabled",true);
		$("#selezionaTutti").addClass("disabled");
		$("#deselezionaTutti").prop("disabled",true);
		$("#deselezionaTutti").addClass("disabled");
	}

	/**
	 * Disabilito il pulsante dato l'id del tag html.
	 */
	function disabledButton(id){
		$("#" + id + "").prop("disabled",true);
	  	$("#" + id + "").addClass("disabled");
	}

	/**
	 * Abilito il pulsante dato l'id del tag html.
	 */
	function enabledButton(id){
		$("#" + id + "").prop("disabled",false);
	  	$("#" + id + "").removeClass("disabled");
	}
	
	/**
	 * Click sul pilsante "Seleziona Tutti", devo attivare tutti i checkbox della pagina corrente. 
	 */
	function selectAllRows(e) {
		e.preventDefault();

		if(isAddAction){
			/**
			 * Abilito il pulsante, per salvare i pagamenti selezionati.
			 */
			enabledButton("aggiungiSelezionati");
		}else{
			/**
			 * Abilito il pulsante, per cancellare i pagamenti selezionati.
			 */
			enabledButton("rimuoviSelezionati");
		}
		/*
		 * Nascondo il button "Seleziona Tutti"
		 */
	  	$("#selezionaTutti").hide();
	  	/*
		 * Mostro il button "Deseleziona Tutti"
		 */
	  	$("#deselezionaTutti").show();

		var index = 0
		while (index < sizeResultList) {
			/* Seleziono il checkbox */
			$("#checkId_" + index).prop('checked', true);
			/* */
		    index++;
		}
	}// close selectAllRows

	/**
	 * Click sul pilsante "Deseleziona Tutti", devo disattivare tutti i checkbox della pagina corrente. 
	 */
	function unSelectAllRows(e) {
		e.preventDefault();

		if(isAddAction){
			/**
			 * Disabilito il pulsante, per salvare i pagamenti selezionati.
			 */
			disabledButton("aggiungiSelezionati");
		}else{
			/**
			 * Disabilito il pulsante, per cancellare i pagamenti selezionati.
			 */
			disabledButton("rimuoviSelezionati");
		}
	  	/*
		 * Nascondo il button "Deseleziona Tutti"
		 */
	  	$("#deselezionaTutti").hide();
	  	/*
		 * Mostro il button "Seleziona Tutti"
		 */
	  	$("#selezionaTutti").show();

	  	var index = 0
		while (index < sizeResultList) {
			/* Deseleziono il checkbox */
			$("#checkId_" + index).prop('checked', false);
			/* */
		    index++;
		}
	}// close unSelectAllRows

	/**
	 * Funzione associata al Click sul singolo checkbox delle righe della tabella di ricerca.
	 * Controllo i checkbox selezionati di modo sapere se abilitare/disabilitare di default il pulsante
	 * "Aggiungi Selezionati" e mostrare/nascondere i pulsanti "Seleziona/Deseleziona Tutti".
	 */
	function selectRow() {

		var checked = 0; var index = 0;
		/*
		 * Ciclo gli elementi in tabella, di modo da ottenere il numero di elementi selezionati.
		 */
		while (index < sizeResultList) {
			/* */
			checked += $("#checkId_" + index).is(":checked") || 0;
			/* */
			index++;
		}

		/* Controllo se ci sono o meno elementi selezionati. */
		if(checked > 0){
			if(isAddAction){
				/**
				 * Abilito il pulsante, per salvare i pagamenti selezionati.
				 */
				enabledButton("aggiungiSelezionati");
			}else{
				/**
				 * Abilito il pulsante, per salvare i pagamenti selezionati.
				 */
				enabledButton("rimuoviSelezionati");
			}
			
			/* Se sono tutti selezionati */
		  	if(checked == sizeResultList){
		  		/*
				 * Nascondo il button "Seleziona Tutti"
				 */
			  	$("#selezionaTutti").hide();
			  	/*
				 * Mostro il button "Deseleziona Tutti"
				 */
			  	$("#deselezionaTutti").show();
			}else{
				/*
				 * Nascondo il button "Deseleziona Tutti"
				 */
				$("#deselezionaTutti").hide();
				/*
				 * Mostro il button "Seleziona Tutti"
				 */
			  	$("#selezionaTutti").show();
			}
		} // close if selezione non vuota
		else{
			if(isAddAction){
				/**
				 * Disabilito il pulsante, per salvare i pagamenti selezionati.
				 */
				disabledButton("aggiungiSelezionati");
			}else{
				/**
				 * Disabilito il pulsante, per cancellare i pagamenti selezionati.
				 */
				disabledButton("rimuoviSelezionati");
			}
		}// else selezione vuota
	}// cose selectRow
	
	/**
	 * Sottomette il form della ricerca, però prima controlla se ci sono checkbox selezionati.
	 * Se si, apre la finestra di dialogo informando l'utente che l'azione annulla eventuali selezioni e come vuole procedere.
	 */
	function avviaRicerca(){
		var checked = 0; var index = 0;

		/*
		 * Ciclo gli elementi in tabella, di modo da ottenere il numero di elementi selezionati.
		 */
		while (index < sizeResultList) {
			/* */
			checked += $("#checkId_" + index).is(":checked") || 0;
			/* */
			index++;
		}

		/**
		 * Controllo se ci sono elementi selezionati
		 */
		if(checked > 0){
			$("#dialog-confirm").dialog({
			    resizable: false,
			    height: "auto",
			    width: 400,
			    modal: true,
			    buttons: {
			      "Continua": function() {
			         $( this ).dialog( "close" );
	
					 /* Salva le modifiche */
			         submitFormRicerca("RC");
			      },
			      "Annulla": function() {
			         $( this ).dialog( "close" );
			      }
			   }
			});
		}else
		   /* Nessun checkbox selezionato, sottometto il form per avviare la ricerca */
		   submitFormRicerca("RC");
	}// close avviaRicerca

	/**
	 * Sottometto il form della ricerca
	 *
	 * @param {@link String} operation, codice operation da sottomettere al submit a seconda se devo salvare o meno le modfifiche 
	 *									apportate prima di ripetere la ricerca
	 */
	function submitFormRicerca(operation){
		$('#operation').val(operation);
		var form = $('#formRcPagamenti');
			form.submit();
	}

	/**
	 * Sottomette il form della ricerca, però prima controlla se ci sono checkbox selezionati.
	 * Se si, apre la finestra di dialogo informando l'utente che l'azione annulla eventuali selezioni e come vuole procedere.
	 */
	function cambioPagina(id, page, pageSize){
		var checked = 0; var index = 0

		/* Build url pagina di redirect */
		var goToUrl = "${pageContext.request.contextPath}" + 
				  	  (isAddAction ? R_PATH_PAGE_ADD : R_PATH_PAGE_DEL) + "?accertamentoID=" + id + "&pg=" + page + "&pgSize=" + pageSize;
		
		/*
		 * Ciclo gli elementi in tabella, di modo da ottenere il numero di elementi selezionati.
		 */
		while (index < sizeResultList) {
			/* */
			checked += $("#checkId_" + index).is(":checked") || 0;
			/* */
			index++;
		}

		/**
		 * Controllo se ci sono elementi selezionati, se mostro una finestra di diaologo
		 */
		if(checked > 0){
			$("#dialog-confirm").dialog({
				resizable: false,
			    height: "auto",
			    width: 400,
			    modal: true,
			    buttons: {
			       "Ricerca": function() {
			       	   $( this ).dialog( "close" );

			           window.location.href = goToUrl;
			       },
			       "Annulla": function() {
			           $( this ).dialog( "close" );
			       }
			    }
			});
		}else
		   /* Nessun checkbox selezionato, sottometto il form */
		   window.location.href = goToUrl;
    }// close cambioPagina
</script>

	
<div class="home_content">

	<!-- TITOLO PAGINA -->
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.accertamenti.title.page.accertamento" /> / 
			<span class="txt-titolo">
				<c:if test="${isAddAction}">
					<spring:message code="mypivot.accertamenti.title.page.addPagamenti" /></span>
				</c:if>
				<c:if test="${!isAddAction}">
					<span class="txt-titolo"><spring:message code="mypivot.accertamenti.title.page.removePagamenti" />
				</c:if>
			</span>
		</h1>
	</div>
	
	
	<!-- 
	 	ANAGRAFICA ACCERTAMENTO 
	-->
	<tiles:insertAttribute name="dettaglio_accertamento" />
	
	
	<!-- 
	 	FORM PER LA DEFINIZIONE DEI PAGAMENTI IN ACCERTAMENTO 
	-->
	<form:form method="post" modelAttribute="pagamentiAccertamentoCommand" id="formRcPagamenti">
		
		<!-- 
			DIALOG 
		-->
		<c:if test="${!isReadonly}">
			
			<!-- Codice operation da sottomettere al submit -->
			<form:input id="operation" path="operation" type="hidden" name="operation" value="RC"/>
		
			
			<!-- 
				DIALOG CONFIRM 
			-->
			<div id="dialog-confirm" title="Info" style="display: none;">
				<p>
			  		<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
			  	 	<c:if test="${isAddAction}">
			  	 		<spring:message code="mypivot.accertamenti.dialog.aggiungi" />
			  		</c:if>
			  		<c:if test="${!isAddAction}">
			  	 		<spring:message code="mypivot.accertamenti.dialog.rimuovi" />
			  		</c:if>
			  	</p>
			</div>
			
			<!-- 
				DIALOG SUCCESS
			-->
			<c:if test="${successRtAdd or successRtDel}">
				<div id="dialog-messages" title="<spring:message code="mypivot.messages.info" />" style="display: none;">
					<spring:message code="mypivot.accertamenti.success.operation" />
				</div>
			</c:if>
		</c:if>
		
		
		<!-- 
	 		FORM FILTRI DI RICERCA PAGAMENTI
		-->
		<tiles:insertAttribute name="ricerca_pagamenti" />
		
		
		<!-- MESSAGE -->
		<div class="row-fluid">
			<tiles:insertAttribute name="message" />
		</div>
	
	
		<!-- 
	 		TABELLA PAGAMENTI (RT)
		-->
		<c:if test="${not empty pagamentiAccertamentoCommand and pagamentiAccertamentoCommand.totalRecords > 0}">
			 
			<!-- 
			 	TEMPLATE HTML PAGAMENTO
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
										<a href="javascript:void(0)" onclick="javascript:cambioPagina(${accertamentoDto.id}, 1, ${pagamentiAccertamentoCommand.pageSize});">
											<span><spring:message code="mypivot.pager.prima" /></span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.previousPage}">
									<li>
										<a href="javascript:void(0)" onclick="javascript:cambioPagina(${accertamentoDto.id}, ${pagamentiAccertamentoCommand.page-1}, ${pagamentiAccertamentoCommand.pageSize});">
											<span>&#171;</span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.previousPage}">
									<li>
										<a href="javascript:void(0)" onclick="javascript:cambioPagina(${accertamentoDto.id}, ${pagamentiAccertamentoCommand.page-1}, ${pagamentiAccertamentoCommand.pageSize});">
											<span><c:out value="${pagamentiAccertamentoCommand.page-1}"></c:out></span>
										</a>
									</li>
								</c:if>
			
								<li class="disabled"><span><c:out value="${pagamentiAccertamentoCommand.page}"></c:out></span></li>
			
								<c:if test="${pagamentiAccertamentoCommand.nextPage}">
									<li>
										<a href="javascript:void(0)" onclick="javascript:cambioPagina(${accertamentoDto.id}, ${pagamentiAccertamentoCommand.page+1}, ${pagamentiAccertamentoCommand.pageSize});">
											<span><c:out value="${pagamentiAccertamentoCommand.page+1}"></c:out></span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.nextPage}">
									<li>
										<a href="javascript:void(0)" onclick="javascript:cambioPagina(${accertamentoDto.id}, ${pagamentiAccertamentoCommand.page+1}, ${pagamentiAccertamentoCommand.pageSize});">
											<span>&#187;</span>
										</a>
									</li>
								</c:if>
			
								<c:if test="${pagamentiAccertamentoCommand.nextPage}">
									<li>
										<a href="javascript:void(0)" onclick="javascript:cambioPagina(${accertamentoDto.id}, ${pagamentiAccertamentoCommand.totalPages}, ${pagamentiAccertamentoCommand.pageSize});">
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
			
			<!-- VAI AL DETTAGGLIO -->
			<div class="btn-group pull-right">
				<a href="<%=request.getContextPath()%>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}" class="btn btn-large">
					<i class="fa fa-chevron-circle-right fa-lg"></i> <spring:message code="mypivot.accertamenti.btn.label.dettaglio" /> 
				</a>
			</div>
			
		</div><!-- /.well -->
	</form:form>
		
</div><!-- /.home_content -->
