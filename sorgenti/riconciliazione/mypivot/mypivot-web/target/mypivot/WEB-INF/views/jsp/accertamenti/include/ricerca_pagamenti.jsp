<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 

	 FORM FILTRI DI RICERCA PAGAMENTI
	
-->
<%
	/* Inserisco al contesto pagina gli stati che descrivo l'evoluzione dell'accertamento */
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_INSERITO",	 Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO);
%>

<script type="text/javascript">
	$(document).ready(function() {
		/*
		 * Abilita/Disabilita l'edit delle date
		 */
		var esitoCheckedAttribute = 			  $("#data_esito_singolo_pagamento_check").is(":checked") ? 'enable' : 'disable';
		var ultimoAggiornamentoCheckedAttribute = $("#data_ultimo_aggiornamento_check").is(":checked") ? 'enable' : 'disable';

		$("#data_esito_singolo_pagamento_da").datepicker(esitoCheckedAttribute);
		$("#data_esito_singolo_pagamento_a").datepicker(esitoCheckedAttribute);
		$("#data_ultimo_aggiornamento_da").datepicker(ultimoAggiornamentoCheckedAttribute);
		$("#data_ultimo_aggiornamento_a").datepicker(ultimoAggiornamentoCheckedAttribute);
	});
</script>

<!-- 
	IDENTIFICATIVO DELL'ACCERTAMENTO PER CUI AGGIUNGERE/RIMUOVERE PAGAMENTI  
-->
<form:input path="accertamentoId" type="hidden" name="accertamentoId" />

<!-- 
	TIPO DOVUTO DELL'ACCERTAMENTO 
-->
<form:input path="cod_tipo_dovuto" type="hidden" name="cod_tipo_dovuto" />


<!-- 
	HTML FILTRI
 -->
<div class="dettaglio debiti_content_filters">

	<!-- 
		TESTATA
	-->
	<div class="testata">
		<h2><spring:message code="mypivot.accertamenti.label.ricercaPagamenti"/></h2>
	</div>

	<!-- 
		FILTRI
	-->
	<div class="corpo form-horizontal nopb"> 
		<div class="row-fluid">
						
			<div class="mypay-custom small-text form-inline nborderb">
			
				<!-- RANGE DATA ULTIMO AGGIORNAMENTO -->
				<div class="span2 text-center">
					
					<label>
						<!-- CHECKBOX -->
						<input id="data_ultimo_aggiornamento_check" 
							   path="data_ultimo_aggiornamento_check"
							   name="data_ultimo_aggiornamento_check" 
							   type="checkbox"
							   class="date-trigger-checkbox vasub"
							   onclick="handleDatePanel(this, 'dataUltimoAggiornamento');"
						<c:if test="${pagamentiAccertamentoCommand.data_ultimo_aggiornamento_check}">checked="checked"</c:if> />
							
						<!-- TEXT -->
						<spring:message code="mypivot.accertamenti.filter.dataUltimoAggiornamento" /> 
					</label>
						
					<br />
					
					<!-- DATE DAL -->			
					<div class="mtop10">
						<label class="date-label"><spring:message code="mypivot.accertamenti.filter.dataDa" /></label>
						<form:input path="data_ultimo_aggiornamento_da" id="data_ultimo_aggiornamento_da" type="text" name="data_ultimo_aggiornamento_da" class="input-small my-input-date" />
					</div>
					
					<!-- DATE AL -->
					<div class="mtop10">
						<label class="date-label"><spring:message code="mypivot.visualizza.datiPagamento.dataA" />:</label>
						<form:input path="data_ultimo_aggiornamento_a" id="data_ultimo_aggiornamento_a" type="text" name="data_ultimo_aggiornamento_a" class="input-small my-input-date" />
					</div>
						
				</div><!-- /.span2_5 -->
				
		
				<!-- RANGE DATA ESITO SINGOLO PAGAMENTO -->
				<div class="span2 text-center">
					
					<label>
						<!-- CHECKBOX -->
						<input id="data_esito_singolo_pagamento_check" 
							   path="data_esito_singolo_pagamento_check"
							   name="data_esito_singolo_pagamento_check" 
							   type="checkbox"
							   class="date-trigger-checkbox vasub"
							   onclick="handleDatePanel(this, 'dataEsito');"
							<c:if test="${pagamentiAccertamentoCommand.data_esito_singolo_pagamento_check}">checked="checked"</c:if> />
					
						<!-- TEXT -->
						<spring:message code="mypivot.accertamenti.filter.dataEsito" /> 
					</label>
						
					<br />
					
					<!-- DATE DAL -->			
					<div class="mtop10">
						<label class="date-label"><spring:message code="mypivot.accertamenti.filter.dataDa" /></label>
						<form:input path="data_esito_singolo_pagamento_da" id="data_esito_singolo_pagamento_da" type="text" name="data_esito_singolo_pagamento_da" class="input-small my-input-date" />
					</div>
					
					<!-- DATE AL -->
					<div class="mtop10">
						<label class="date-label"><spring:message code="mypivot.visualizza.datiPagamento.dataA" />:</label>
						<form:input path="data_esito_singolo_pagamento_a" id="data_esito_singolo_pagamento_a" type="text" name="data_esito_singolo_pagamento_a" class="input-small my-input-date" />
					</div>
				</div><!-- /.span2_5 -->
				
				
				<!-- CODICE IUD -->
				<div class="span3 mtop10">
					<!-- TEXT -->
					<label><spring:message code="mypivot.accertamenti.filter.IUD" /></label>
					
					<form:input path="codice_iud" class="input-block-level" name="codice_iud" type="text" placeholder="" />
				</div>
				
				
				<!-- CODICE IUV -->
				<div class="span3 mtop10">	
					<!-- TEXT -->
					<label><spring:message code="mypivot.accertamenti.filter.IUV" /></label>
					
					<form:input path="codice_iuv" class="input-block-level" name="codice_iuv" type="text" placeholder="" />
				</div><!-- /.span2 -->
		 
		 	
				<!-- CF/PIVA PAGATORE -->
			 	<div class="span2 mtop10">
					<!-- TEXT -->
					<label><spring:message code="mypivot.accertamenti.filter.codice" /></label>
					
					<form:input path="codice_identificativo_univoco_pagatore" class="input-block-level" name="codice_identificativo_univoco_pagatore" type="text" placeholder="" />
				</div><!-- /.span2 -->
				
			</div><!-- /.form-inline -->
			
			
			<!-- PULSANTIERA RICERCA -->
			<div class="span12 mtop10 align-center">
				<label><spring:message code="mypivot.pager.numElements" /></label>
				
				<!-- SELECT COUNT ELEMENTS -->
				<form:select class="pagerSelect nomb nooutline" path="pageSize">
					<form:option value="5" />
					<form:option value="10" />
					<form:option value="20" />
					<form:option value="50" />
					<form:option value="100" />
				</form:select>
				
				<!-- CERCA BUTTON -->
				
				<c:if test="${!isReadonly}"> <!-- VISIBILE IN EDIT -->
					<button type="button" class="btn" onclick="javascript:avviaRicerca();">
						<i class="fa fa-search padding-right"></i> <spring:message code="mypivot.button.cerca" />
					</button>
				</c:if>
				
				<c:if test="${isReadonly}"> <!-- VISIBILE NEL DETTAGLIO -->
					<button type="submit" class="btn" value="pagamentiAccertamentoCommand">
						<i class="fa fa-search padding-right"></i> <spring:message code="mypivot.button.cerca" />
					</button>
				</c:if>
			</div>
			
			
			<!-- PULSANTIERA EDIT -->
			<c:if test="${!isReadonly}">
				<div class="span12 align-center mtop20">
				
					<!-- AGGIUNGI/RIMUOVI SELEZIONATI -->
					<div class="btn-group">
					
						<!-- AGGIUNGI SELEZIONATI -->
						<c:if test="${isAddAction}">  <!-- VISIBILE PER L'AGGIUNGI PAGAMENTI -->
							<spring:message code="mypivot.accertamenti.btn.title.aggiungiSelezionati" var="titleBtNuovo"/>
		                   	
		                   	<button id="aggiungiSelezionati" type="button" class="btn btn-mini btn-success" title="${titleBtNuovo}" onclick="javascript:submitFormRicerca('SAVE');">
		                       	<i class="fa fa-floppy-o" aria-hidden="true"></i>
		                        <spring:message code="mypivot.accertamenti.btn.label.aggiungiSelezionati" />         
		                    </button>
		                </c:if>
		                
		                <!-- RIMUOVI SELEZIONATI -->
		                <c:if test="${!isAddAction}">  <!-- VISIBILE PER RIMUOVI PAGAMENTI -->
		                	<spring:message code="mypivot.accertamenti.btn.title.rimuoviSelezionati" var="titleBtNuovo"/>
		                   	
		                   	<button id="rimuoviSelezionati" type="button" class="btn btn-mini btn-success" title="${titleBtNuovo}" onclick="javascript:submitFormRicerca('SAVE');">
		                       	<i class="fa fa-floppy-o" aria-hidden="true"></i>
		                        <spring:message code="mypivot.accertamenti.btn.label.rimuoviSelezionati" />         
		                    </button>
		                </c:if>
	                </div><!-- /.btn-group -->
	                
	                
	                <!-- SELEZIONA TUTTI -->
	                <div class="btn-group">
	                   	<button id="selezionaTutti" type="button" class="btn btn-mini btn-primary" onclick="javascript:selectAllRows(event);">
	                       	<i class="fa fa-check-square-o" aria-hidden="true"></i>
	                        <spring:message code="mypivot.accertamenti.btn.label.selezionaTutti" />         
	                    </button>
	                </div>
	                
	                <!-- DESELEZIONA TUTTI -->
	                <div class="btn-group">
	                   	<button id="deselezionaTutti" type="button" class="btn btn-mini btn-danger" onclick="javascript:unSelectAllRows(event);">
	                       	<i class="fa fa-ban" aria-hidden="true"></i>
	                        <spring:message code="mypivot.accertamenti.btn.label.deselezionaTutti" />         
	                    </button>
	                </div>
	                
				</div>
			</c:if><!-- /.isReadonly -->
			
			
			<!-- PULSANTIERA DETTAGLIO (In sola lettura e in stato inserito) -->
			<c:if test="${isReadonly and accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_INSERITO}">
				<div class="span12 align-center mtop20">
				
	                <!-- AGGIUNGI PAGAMENTI -->
	                <div class="btn-group">
						<a class="btn btn-mini btn-primary" href="<%=request.getContextPath() %>/protected/accertamenti/addPagamenti.html?accertamentoID=${accertamentoDto.id}" title="${titleBtConfirm}">
							<i class="fa fa-check-square-o bold" aria-hidden="true"></i> <spring:message code="mypivot.accertamenti.btn.label.aggiungiPagamenti"/>
						</a>
	                </div>
	                
	                <!-- RIMUOVI PAGAMENTI -->
	                <div class="btn-group">
						<a class="btn btn-mini btn-danger ${pagamentiAccertamentoCommand.resultList.size() == 0 ? 'disabled' : ''}" href="<%=request.getContextPath() %>/protected/accertamenti/deletePagamenti.html?accertamentoID=${accertamentoDto.id}" title="${titleBtConfirm}">
							<i class="fa fa-check-square-o bold" aria-hidden="true"></i> <spring:message code="mypivot.accertamenti.btn.label.rimuoviPagamenti"/>
						</a>
	                </div>
	                
				</div>
			</c:if><!-- /.isReadonly -->
			
		</div><!-- /.row-fluid -->

	</div><!-- /.corpo form-horizontal -->
</div><!-- /.dettaglio -->
