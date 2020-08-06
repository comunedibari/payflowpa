<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentiVisualizzaCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<%
	/* Inserisco al contesto pagina gli stati che descrivo l'evoluzione dell'accertamento */
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_INSERITO", Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO);
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_CHIUSO", Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO);
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO", Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO);
%>
<script type="text/javascript">

	/**
	 * Recupero dal model la variabile "notAuthorizedClosedEmptyAcc" la quale servirà per determinare se al carimento della pagina 
	 * ci sono messaggi di errore da mostrare all'utente. Se si, il messaggio è mostrato in popup.
	 */
	var notAuthorizedClosedEmptyAcc = '${notAuthorizedClosedEmptyAcc}';

	/*
	 * Abilita/Disabilita l'edit della data ultimo aggiornamento
	 */
	function toggleCheck(){
	
		var checkedValue = $("#accertamentiFormRc #dataUltimoAggCheck").is(":checked") ? 'enable' : 'disable';
	
		$("#accertamentiFormRc #dataUltimoAggDa").datepicker(checkedValue);
	
		$("#accertamentiFormRc #dataUltimoAggA").datepicker(checkedValue);
	};
	
	$(document).ready(function() {
		/**
		 * Init datepicker
		 */
		$("#accertamentiFormRc #dataUltimoAggDa").datepicker({
			showOn: "button",
			buttonImage: "/mypivot/images/calendar.gif",
			buttonImageOnly: true,
			dateFormat: "dd/mm/yy",
			regional: "it",
			maxDate: new Date(),
			onSelect: function (dateText, inst){
				// Setter min date
				$("#accertamentiFormRc #dataUltimoAggA").datepicker( "option", "minDate", new Date(inst.selectedYear, inst.selectedMonth, inst.selectedDay) );
			}
		});

		$("#accertamentiFormRc #dataUltimoAggA").datepicker({
			showOn: "button",
			buttonImage: "/mypivot/images/calendar.gif",
			buttonImageOnly: true,
			dateFormat: "dd/mm/yy",
			regional: "it",
			maxDate: new Date()
		});

		/** 
		 * Gestisce il click sul chekbox che abilita e disabilita l'edit sulle date. 
		 * Solo se il check è selezionato lato server viene considerato il filtro sulle date.
		 */
		$("#accertamentiFormRc #dataUltimoAggCheck").change(function (){
			toggleCheck();
		});

		/* Per default al caricamento disabilito l'edit al filtro delle date */
		toggleCheck();

		/**
		 * Controllo se la variabile 'notAuthorizedClosedEmptyAcc' è valorizzata a true
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
	});
</script>

<div class="home_content table-responsive">

	<!-- TITOLO PAGINA -->
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.accertamenti.title.page.accertamento" /> / <span class="txt-titolo"><spring:message code="mypivot.accertamenti.title.page.gestioneAccertamenti" /></span>
		</h1>
	</div>
		
	<!-- FORM FILTRI RICERCA -->
	<form:form method="post" modelAttribute="visualizzaAccertamentiCommand" id="accertamentiFormRc">

		<!-- MESSAGE -->
		<div class="row-fluid">
			<tiles:insertAttribute name="message" />
		</div>
		
		
		<!-- 
			DIALOG MESSAGE ERROR 
		 -->
		<c:if test="${notAuthorizedClosedEmptyAcc}">
			<div id="dialog-messages" title="<spring:message code="mypivot.messages.warning" />" style="display: none;">
				<spring:message code="mypivot.accertamenti.errore.notAuthorizedClosedEmptyAcc" />
			</div>
		</c:if>
		
				
		<div class="row-fluid">
			<div class="debiti_content_filters">
			
				<!-- FILTRI -->
				<div class="form-inline form-actions">
					<!-- RANGE DATA ULTIMO AGGIORNAMENTO -->
					<div class="span3 txt-center">
						
						<label>
							<!-- CHECKBOX -->
							<input id="dataUltimoAggCheck" 
							       path="dataUltimoAggCheck" 
								   name="dataUltimoAggCheck" 
								   type="checkbox" 
								   class="date-trigger-checkbox vasub"
								<c:if test="${visualizzaAccertamentiCommand.dataUltimoAggCheck}">checked="checked"</c:if> />
								
							<!-- TEXT -->
							<spring:message code="mypivot.accertamenti.filter.dataUltimoAggiornamento" /> 
						</label>
						
						<br />
						
						<!-- DATA MODIFICA DAL -->			
						<div class="mtop10">
							<label class="date-label"><spring:message code="mypivot.accertamenti.filter.dataDa" /></label>
							<form:input path="dataUltimoAggDa" id="dataUltimoAggDa" type="text" name="dataUltimoAggDa" class="input-small" />
						</div>
							
						<!-- DATA MODIFICA AL -->
						<div class="mtop10">
							<label class="date-label"><spring:message code="mypivot.accertamenti.filter.dataA" /></label>
							<form:input path="dataUltimoAggA" id="dataUltimoAggA" type="text" name="dataUltimoAggA" class="input-small" />
						</div>
						
						<br />
					</div><!-- /.span3 -->
					
					<div class="span4">
						
						<!-- IUV -->
						<label><spring:message code="mypivot.accertamenti.filter.IUV" /></label>
						<form:input path="codiceIuv" class="input-block-level" name="codiceIuv" type="text" placeholder="" />
						
					</div><!-- /.span4 -->
					
					<div class="span3">
						
						<!-- TIPI DOVUTO -->
						<label><spring:message code="mypivot.accertamenti.filter.tipoDovuto" /></label>
						<form:select class="input-block-level" path="codTipoDovuto">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.ALL"></spring:message></form:option>
							<c:forEach var="ctd" items="${listaTipiDovuti}">
								<form:option value="${ctd.codTipo}">
									<c:out value="${ctd.deTipo}"></c:out>
								</form:option>
							</c:forEach>
						</form:select>
						
						<!-- STATI -->
						<label><spring:message code="mypivot.accertamenti.filter.stato" /></label>
						<form:select class="input-block-level" path="codStato">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.ALL"></spring:message></form:option>
							<c:forEach var="ctd" items="${statiList}">
								<form:option value="${ctd.codStato}">
									<c:out value="${ctd.codStato}"></c:out>
								</form:option>
							</c:forEach>
						</form:select>
						
					</div><!-- /.span3 -->
				</div><!-- /.form-actions -->		
				
				
				<!-- PULSANTIERA RICERCA -->
				<div class="align-center">
					<label><spring:message code="mypivot.pager.numElements" /></label>
					
					<!-- SELECT COUNT ELEMENTS -->
					<form:select class="pagerSelect nomb" itemLabel="pageSize" itemValue="pageSize" path="pageSize">
						<form:option value="5" />
						<form:option value="10" />
						<form:option value="20" />
					</form:select>
					
					<button type="submit" class="btn" value="visualizzaAccertamentiCommand">
						<i class="fa fa-search padding-right"></i> <spring:message code="mypivot.button.cerca" />
					</button>
				</div>
				
				<!-- PULSANTIERA NUOVO / GESTIONE ANAGRAFICA -->
				<div class="align-center mtop10">
					<!-- PULSANTE NUOVO -->
					<div class="btn-group">
						<spring:message code="mypivot.accertamenti.btn.title.nuovo" var="titleBtNuovo"/>
                    	<a href="<%=request.getContextPath() %>/protected/accertamenti/nuovo.html" class="btn btn-primary" title="${titleBtNuovo}">
                        	<i class="fa fa-plus" aria-hidden="true"></i>
                            <spring:message code="mypivot.accertamenti.btn.label.nuovo" />         
                        </a>
                    </div>
				</div>
				
			</div><!-- /.debiti_content_filters -->
		</div><!-- /.row-fluid -->
		
	</form:form><!-- ./FORM FILTRI RICERCA -->
	
	
	<!-- TABELLA RICERCA -->
	<table class="table table-bordered table-accertamento">
		<thead>
			<tr>
				<th><spring:message code="mypivot.accertamenti.header.nomeAccertamento"/></th>
				<th><spring:message code="mypivot.accertamenti.header.tipoDovuto"/></th>
				<th><spring:message code="mypivot.accertamenti.header.stato"/></th>
				<th><spring:message code="mypivot.accertamenti.header.utente" /></th>
				<th><spring:message code="mypivot.accertamenti.header.aggiornatoIl" /></th>
				<th><spring:message code="mypivot.accertamenti.header.seleziona" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="accertamentoDto" items="${responseDtoPage.list}">
				<tr>
					<td class="wrapfix">
						<c:out value="${accertamentoDto.deNomeAccertamento}" />
					</td>
					<td class="text-center nowrap">
						<c:out value="${accertamentoDto.enteTipoDovuto.deTipo}" />
					</td>
					<td>
						<c:out value="${accertamentoDto.stato.codStato}" />
					</td>
					<td class="text-center nowrap">
						<c:out value="${accertamentoDto.utente.deFirstname}" /> <c:out value="${accertamentoDto.utente.deLastname}" />
					</td>
					<td class="text-center nowrap">
						<c:out value="${accertamentoDto.dtUltimaModifica}" />
					</td>
					<td class="text-center nowrap">
					
						<!-- DETTAGLIO -->
						<spring:message code="mypivot.accertamenti.btn.title.dettaglio" var="titleBtDettaglio"/>
						<a class="btn btn-mini btn-info" href="<%=request.getContextPath() %>/protected/accertamenti/dettaglio.html?accertamentoID=${accertamentoDto.id}" title="${titleBtDettaglio}"> 
							<i class="fa fa-pencil-square-o bold" aria-hidden="true"></i>
						</a>
						
						<!-- DOWNLOAD ( MEV RVE_E45A-225 NON PREVEDE IL DOWNLOAD DEL PDF )
						<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_CHIUSO}">
							<spring:message code="mypivot.accertamenti.btn.title.download" var="titleBtDownload"/>
							<a class="btn btn-mini btn-default" href="<%=request.getContextPath() %>/protected/accertamenti/ricerca/download.html?accertamentoID=${accertamentoDto.id}&pg=${responseDtoPage.page}" title="${titleBtDownload}">
								<i class="fa fa-download" aria-hidden="true"></i>
							</a>
						</c:if> -->
						
						<!-- CHIUDI -->
						<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_INSERITO}">
							<spring:message code="mypivot.accertamenti.btn.title.close" var="titleBtClose"/>
							<a class="btn btn-mini btn-success" href="<%=request.getContextPath() %>/protected/accertamenti/ricerca/setStato.html?accertamentoID=${accertamentoDto.id}&codStato=${COD_TIPO_STATO_ACCERTAMENTO_CHIUSO}" title="${titleBtClose}">
								<i class="fa fa-times-circle" aria-hidden="true"></i>
							</a>
						</c:if>
						
						<!-- ANNULLA -->
						<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_INSERITO}">
							<spring:message code="mypivot.accertamenti.btn.title.cancel" var="titleBtCancel"/>
							<a class="btn btn-mini btn-inverse" href="<%=request.getContextPath() %>/protected/accertamenti/ricerca/setStato.html?accertamentoID=${accertamentoDto.id}&codStato=${COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO}" title="${titleBtCancel}">
								<i class="fa fa-ban" aria-hidden="true"></i>
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	<!-- PAGINAZIONE TABELLA RICERCA -->
	<c:if test="${responseDtoPage.totalRecords > 0}">
		<div>
			<div class="pagination-left">
				<!-- Es: Pagina 1 di 33 -->
				<spring:message code="mypivot.pager.pagina" /> <c:out value="${responseDtoPage.page}" /> 
				<spring:message code="mypivot.pager.di" /> <c:out value="${responseDtoPage.totalPages}" />
				-
				<!-- Es: Elementi da 1 a 5 -->
				<spring:message code="mypivot.pager.elementiDa" /> <c:out value="${(responseDtoPage.page-1) * responseDtoPage.pageSize + 1}" />
				<spring:message code="mypivot.pager.a" /> <c:out value="${(responseDtoPage.page-1) * responseDtoPage.pageSize + fn:length(responseDtoPage.list)}" />
			</div><!-- /.pagination-left -->

			<div>
				<div class="pagination pagination-right">
					<ul>
						<c:if test="${responseDtoPage.previousPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html?pg=1&pgSize=${responseDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.prima" /></span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.previousPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html?pg=${responseDtoPage.page-1}&pgSize=${responseDtoPage.pageSize}">
									<span>&#171;</span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.previousPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html?pg=${responseDtoPage.page-1}&pgSize=${responseDtoPage.pageSize}">
									<span><c:out value="${responseDtoPage.page-1}"></c:out></span>
								</a>
							</li>
						</c:if>

						<li class="disabled"><span><c:out value="${responseDtoPage.page}"></c:out></span></li>

						<c:if test="${responseDtoPage.nextPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html?pg=${responseDtoPage.page+1}&pgSize=${responseDtoPage.pageSize}">
									<span><c:out value="${responseDtoPage.page+1}"></c:out></span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.nextPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html?pg=${responseDtoPage.page+1}&pgSize=${responseDtoPage.pageSize}">
									<span>&#187;</span>
								</a>
							</li>
						</c:if>

						<c:if test="${responseDtoPage.nextPage}">
							<li>
								<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html?pg=${responseDtoPage.totalPages}&pgSize=${responseDtoPage.pageSize}">
									<span><spring:message code="mypivot.pager.ultima" /></span>
								</a>
							</li>
						</c:if>
					</ul>
				</div><!-- /.pagination-right -->
			</div>
		</div>
	</c:if>
	
	
	<!-- LABEL PAGINAZIONE 0 RISULTATI -->
	<c:if test="${responseDtoPage.totalRecords == 0}">
		<p class="muted text-center"><spring:message code="mypivot.accertamenti.pagination.zeroRecords"></spring:message></p>
	</c:if>
	
</div><!-- /.home_content -->
