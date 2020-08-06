<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap-datepicker-1.7.1/dist/css/bootstrap-datepicker.min.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/js/bootstrap-datepicker-1.7.1/dist/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/bootstrap-datepicker-1.7.1/dist/locales/bootstrap-datepicker.it.min.js"></script>

<script type="text/javascript">

	/* ID del form */
	var formId_Cruscotto = "#formRcStatistica";
	 
	/**
	 * ID del div HTML contenitore del calendario per filtrare i risultati della statistica per anno.
	 */
	var divId_XAnno = formId_Cruscotto + " #divFilterXAnno";

	/**
	 * ID del div HTML contenitore del calendario per filtrare i risultati della statistica per mese.
	 */
	var divId_XMese = formId_Cruscotto + " #divFilterXMese";
	
	/**
	 * ID del div HTML contenitore del calendario per filtrare i risultati della statistica per giorno.
	 */
	var divId_XGiorno = formId_Cruscotto + " #divFilterXGiorno";

	/**
	 * Recupero dal model la tipologia di filtro attivo, di modo da customizzare correttamente la view.
	 */
	var annoCheck = 		  '${statisticheCommand.annoCheck}';
	var annoMeseCheck = 	  '${statisticheCommand.annoMeseCheck}';
	var annoMeseGiornoCheck = '${statisticheCommand.annoMeseGiornoCheck}';

	
	$(document).ready(function() {
		
/* TIPOLOGIA PER ANNO   ************************************************************************************************************/ 
			
		/*
		 * La funzione resetta il componente per filtrare i risultati per anno.
		 */
	    function resetFilter_XAnno(){
	    	/* 
	         * Deselezione del checkbox 
	         */
	    	$(formId_Cruscotto + " #annoCheck").prop('checked', false);
	    	/*
			 * Aggiungo il class css che nasconde il div del calendario.
			 */
			$(divId_XAnno).addClass("hidden");
	    }
			    
		/**
		 * Init del datepicker per la selezione degli anni.
		 */
		$(divId_XAnno + " #calendarXAnno").datepicker({
			autoclose: false, 		 // Se chiudere o meno il datapicker immediatamente quando viene selezionata una data
			clearBtn: true,			 // Se true, visualizza un pulsante "Cancella" nella parte inferiore di DatePicker per cancellare il valore di input.
			container: "body", 		 // Aggiunge il popup del selettore di date a un elemento specifico
			defaultViewDate: "year", // Data da visualizzare all'apertura iniziale del calendario.
			enableOnReadonly: true,
			endDate: new Date(),	  // Data o stringa. Impostazione predefinita: fine del tempo
			keyboardNavigation: true, // Se consentire o meno la navigazione della data con i tasti freccia.
			maxViewMode: 2,
	       	minViewMode: 2,
	   	 	language: "it",
	        multidate: false,		 // Enable multidate picking.
	        startView: 2,			 // La vista che il datepicker dovrebbe mostrare quando viene aperta.
	        title: "",				 // La stringa che apparirà in cima al datepicker. Se vuoto, il titolo sarà nascosto.
	        todayBtn: false, 		 // Se è vero o "linked", visualizza un pulsante "Oggi" nella parte inferiore di DatePicker per selezionare la data corrente. Se è vero, il pulsante "Oggi" sposta solo la data corrente nella vista; se "collegato", verrà selezionata anche la data corrente.
	        todayHighlight: true, 	 // Se vero, evidenzia la data corrente.
	        immediateUpdates: false,  // Se è vero, selezionando un anno o un mese in DatePicker si aggiornerà immediatamente il valore di input. Altrimenti, solo selezionando un giorno del mese si aggiornerà immediatamente il valore di input.
	        templates: {
				         leftArrow: '<i class="fa fa-angle-left fa-2x"></i>',
				         rightArrow: '<i class="fa fa-angle-right fa-2x"></i>'
				       }
	    });
			
		/**
	     * Chiamato quando è cliccato il checkbox che attiva o meno il filtro per anni. 
	     */
	    $(formId_Cruscotto + " #annoCheck").click(function(){  
			//
			var isCheck = $(formId_Cruscotto + " #annoCheck").is(":checked");
	
			// Cancello gli elementi selezionati
			$(divId_XAnno + " #calendarXAnno").datepicker('update', '');
			
			if(isCheck){
				/*
				 * La selezione dei checkbox dei filtri per anno, mese e giorno è esclusiva.
				 * Perciò avendo attivato il filtro per anno, deseleziono e resetto quello per mese e giorno.
				 */
				resetFilter_XMese(); 	resetFilter_XGiorno();
			 	/*
				 * Rimuovo il class css che nasconde il div del calendario.
				 */
				$(divId_XAnno).removeClass("hidden");
			}else{
				/*
				 * Aggiungo il class css che nasconde il div del calendario.
				 */
				$(divId_XAnno).addClass("hidden");
			}
		}); 
			    
		/**
		 * Verifico se quello per anno è il filtro attivo, di modo da riproporre a calendario la selezione dell'anno
		 */
	    if(annoCheck != "" && annoCheck == "true"){
	       // Leggo l'anno per cui si è filtrato. 
	       var anno = '${statisticheCommand.anno}';
	       
	       // Seleziono gli elementi nel calendario 
	       $(divId_XAnno + " #calendarXAnno").datepicker("setDates", ["01-01-" + anno]);
	
		   // Rimuovo il class css che nasconde il div del calendario.
		   $(divId_XAnno).removeClass("hidden");
	    } 
			    
/***********************************************************************************************************************************/ 
		
/* TIPOLOGIA PER MESE   ************************************************************************************************************/ 
			 
	 	/*
		 * La funzione resetta il componente per filtrare i risulati per mese.
		 */
	 	function resetFilter_XMese() {
	 		/* 
	         * Deselezione del checkbox 
	         */
			$(formId_Cruscotto + " #annoMeseCheck").prop('checked', false);
			/*
			 * Nascondo il div del calendario.
			 */
			$(divId_XMese).addClass("hidden");
	 	}
			 	
	    /**
		 * Init del datepicker per la selezione dell'anno e del mese.
		 */
		$(divId_XMese + " #calendarXMese").datepicker({
			format: "dd/mm/yyyy",
			autoclose: false, 		 
			clearBtn: true,
			container: "body",
			defaultViewDate: "month",
			enableOnReadonly: true,
			endDate: new Date(),
			keyboardNavigation: true,
			maxViewMode: 2,
	       	minViewMode: 1,
	   	 	language: "it",
	        multidate: false,
	        startView: 1,
	        title: "",
	        todayBtn: false,
	        todayHighlight: true,
	        immediateUpdates: false,
	        templates: {
				         leftArrow: '<i class="fa fa-angle-left fa-2x"></i>',
				         rightArrow: '<i class="fa fa-angle-right fa-2x"></i>'
				       }
	    });

		/**
	     * Chiamato quando è cliccato il checkbox che attiva o meno il filtro per anno/mese. 
	     */
	    $(formId_Cruscotto + " #annoMeseCheck").click(function(){  
			//
			var isCheck = $(formId_Cruscotto + " #annoMeseCheck").is(":checked");
	
			// Cancello gli elementi selezionati
			$(divId_XMese + " #calendarXMese").datepicker('update', '');
			
			if(isCheck){
				/*
				 * La selezione dei checkbox dei filtri per anno, mese e giorno è esclusiva.
				 * Perciò avendo attivato il filtro per mese, deseleziono quello per anno e giorno.
				 */
				resetFilter_XAnno(); 	resetFilter_XGiorno();
			 	/*
				 * Rimuovo il class css che nasconde il div del calendario.
				 */
				$(divId_XMese).removeClass("hidden");
			}else{
				/*
				 * Aggiungo il class css che nasconde il div del calendario.
				 */
				$(divId_XMese).addClass("hidden");
			}
		}); 
			       
		/**
		 * Verifico se quello per mese è il filtro attivo, di modo da riproporre a calendario la selezione del mese
		 */
	    if(annoMeseCheck != "" && annoMeseCheck == "true"){
	       // Leggo l'anno selezionato
	       var mese = '${statisticheCommand.mese}';
	       
	       // Seleziono gli elementi nel calendario 
	       $(divId_XMese + " #calendarXMese").datepicker("setDates", ["01/" + mese]);
	
		   // Rimuovo il class css che nasconde il div del calendario.
		   $(divId_XMese).removeClass("hidden");
	    }
			    
/***********************************************************************************************************************************/ 
			
/* TIPOLOGIA PER GIORNO   **********************************************************************************************************/ 
			
	 	/*
		 * La funzione resetta il componente per filtrare i risulati per giorno.
		 */
	 	function resetFilter_XGiorno() {
	 		/* 
	         * Deselezione del checkbox 
	         */
			$(formId_Cruscotto + " #annoMeseGiornoCheck").prop('checked', false);
			/*
			 * Nascondo il div del calendario.
			 */
			$(divId_XGiorno).addClass("hidden");
	 	}
			 	
	    /**
		 * Init del datepicker per la selezione del giorno.
		 */
		$(divId_XGiorno + " #calendarXGiorno #giorno").datepicker({
			format: "dd/mm/yyyy",
			autoclose: true, 		 
			clearBtn: true,
			container: "body",
			defaultViewDate: "day",
			enableOnReadonly: true,
			endDate: new Date(),
			keyboardNavigation: true,
			maxViewMode: 2,
	   	 	language: "it",
	        multidate: false,
	        title: "",
	        todayBtn: "linked",
	        todayHighlight: true,
	        immediateUpdates: false,
	        templates: {
				         leftArrow: '<i class="fa fa-angle-left fa-2x"></i>',
				         rightArrow: '<i class="fa fa-angle-right fa-2x"></i>'
				       }
	    });

		/**
	     * Chiamato quando è cliccato il checkbox che attiva o meno il filtro per anno/mese/giorno. 
	     */
	    $(formId_Cruscotto + " #annoMeseGiornoCheck").click(function(){  
			//
			var isCheck = $(formId_Cruscotto + " #annoMeseGiornoCheck").is(":checked");
	
			// Cancello gli elementi selezionati
			$(divId_XGiorno + " #calendarXGiorno #giorno").datepicker('update', '');
			
			if(isCheck){
				/*
				 * La selezione dei checkbox dei filtri per anno, mese e giorno è esclusiva.
				 * Perciò avendo attivato il filtro per giorno, deseleziono quello per anno e mese.
				 */
				resetFilter_XAnno();	resetFilter_XMese();
				
			 	/*
				 * Rimuovo il class css che nasconde il div del calendario.
				 */
				$(divId_XGiorno).removeClass("hidden");
			}else{
				/*
				 * Aggiungo il class css che nasconde il div del calendario.
				 */
				$(divId_XGiorno).addClass("hidden");
			}
		});

		/**
		 * Verifico se quello per giorno è il filtro attivo, di modo da riproporre a calendario la selezione del giorno
		 */
	    if(annoMeseGiornoCheck != "" && annoMeseGiornoCheck == "true"){

			// set date inizio e fine
	    	$(divId_XGiorno + " #calendarXGiorno #giorno").datepicker("update", "${statisticheCommand.giorno}");

		   	/*
			 * Rimuovo il class css che nasconde il div del calendario.
			 */
		   	$(divId_XGiorno).removeClass("hidden");
	    } 
			    
/***********************************************************************************************************************************/ 
	});
	
	/**
	 * Sottometto il form della ricerca
	 */
	function submitFormRicerca(){
		/*
		 * Verifico la selezione dei checkbox 
		 */
		var annoCheck 		= $(formId_Cruscotto + " #annoCheck").is(":checked");
	 	var annoMeseCheck 	= $(formId_Cruscotto + " #annoMeseCheck").is(":checked");
		var annoGiornoCheck = $(formId_Cruscotto + " #annoMeseGiornoCheck").is(":checked");
	
		/**
		 * Determino quale checkbox della tipologia di statistica è attivo
		 */
		if(annoCheck){
			// Get anni selezionati
			var selectedYear = $(divId_XAnno + " #calendarXAnno").datepicker("getDate");
	
		    // set value list anni per submit
			$(formId_Cruscotto + " #anno").val(selectedYear == null ? "" : selectedYear.getFullYear());
	 	}
		else
			if(annoMeseCheck){
				// Get anni selezionati
				var selectedMonth = $(divId_XMese + " #calendarXMese").datepicker("getDate");
	
			    // set value list mesi per submit
				$(formId_Cruscotto + " #mese").val(selectedMonth == null ? "" : (selectedMonth.getMonth() + 1) + "/" + selectedMonth.getFullYear());
			}
		else
			if(annoGiornoCheck){
				 // do nothing
			}
	
		/* */
		var form = $(formId_Cruscotto);
			form.submit();
	}
	
</script>


<div class="row-fluid cruscotto-incassi">
	<div class="home_content">
		<div class="span12">
			<div class="panel panel-default table-responsive">
				
			<!-- 
			 	FORM PER LA RICERCA STATISTICA
			-->
				<form:form method="post" modelAttribute="statisticheCommand" id="formRcStatistica">
					
					<!-- TITLE PAGINA : NOME STATISTICA -->
					<div class="titolo-pagina">
						<h1>
							<spring:message code="mypivot.statistiche.menu.statistiche" /> /
							<span class="txt-titolo"><spring:message code="mypivot.statistiche.menu.statistica.DOVUTI" /></span>
						</h1>
					</div>
					
					<div class="panel-body">
						<div class="containers">
							
							<!-- FILTRI DI RICERCA -->
							<div class="sidebar">
								<div class="mypay-custom small-text form-inline searchContainer">
									
									<!-- 
										VALORI DA SOTTOMETTERE AL SUBMIT 
									-->
									<!-- L'anno per cui estrarre il dato statistico.	(Formato: yyyy) -->
									<form:input id="anno" path="anno" type="hidden" name="anno" />
									
									<!-- Il mese dell'anno(specificato nella property "anno") per cui estrarre il dato statistico. (Formato: MM/yyyy) -->
									<form:input id="mese" path="mese" type="hidden" name="mese" />
									
									<!-- 
										HTML CHECKBOX ABILITA/DISABILITA IMPORTI
									-->
									<div class="row-fluid">
										<div class="span6 mtop10">
											<!-- Text -->
											<label class="strong uppercase"><spring:message code="mypivot.statistiche.filter.importi" /></label>
											
											<div class="inputContainer checkboxContainer">
												<div class="checkbox">
													<label>
														<!-- Checkbox -->
														<input id="pagatiCheck" path="pagatiCheck" name="pagatiCheck" type="checkbox" <c:if test="${statisticheCommand.pagatiCheck}">checked="checked"</c:if> />
														<!--  -->
														<span class="cr"><i class="cr-icon fa fa-check"></i></span>
														<!-- Label -->
														<spring:message code="mypivot.statistiche.filter.pagati" />
													</label>
												</div>
												<div class="checkbox">
													<label>
														<!-- Checkbox --> 
														<input id="rendicontatiCheck" path="rendicontatiCheck" name="rendicontatiCheck" type="checkbox" <c:if test="${statisticheCommand.rendicontatiCheck}">checked="checked"</c:if> />
														<!--  -->
														<span class="cr"><i class="cr-icon fa fa-check"></i></span>
														<!-- Label -->
														<spring:message code="mypivot.statistiche.filter.rendicontati" />
													</label>
												</div>
												<div class="checkbox ${flgTesoreria ? '' : 'hidden'}">
													<label>
														<!-- Checkbox -->
														<input id="incassatiCheck" path="incassatiCheck" name="incassatiCheck" type="checkbox" <c:if test="${statisticheCommand.incassatiCheck}">checked="checked"</c:if> />
														<!--  -->
														<span class="cr"><i class="cr-icon fa fa-check"></i></span>
														<!-- Label -->
														<spring:message code="mypivot.statistiche.filter.incassati" />
													</label>
												</div>
											</div><!-- /.checkboxContainer -->
										</div><!-- /.span6 -->
									</div><!-- /.row-fluid -->


									<!-- 
										HTML FILTRI PER ANNO, PER MESE E PER GIORNO
									-->
									<div class="span6 mtop10 mbottom20">
										<!-- Text -->
										<label class="strong uppercase"><spring:message code="mypivot.statistiche.filter.data" /></label>
										
										<!-- Check Scelta Tipo Statistica -->
										<div class="inputContainer">
											<div class="checkboxContainer">
												<div class="checkbox">
													<label>
														<!-- Checkbox -->
														<input id="annoCheck" path="annoCheck" name="annoCheck" type="checkbox" <c:if test="${statisticheCommand.annoCheck}">checked="checked"</c:if> />
														<!--  -->
														<span class="cr"><i class="cr-icon fa fa-check"></i></span>
														<!-- Label -->
														<spring:message code="mypivot.statistiche.filter.anno" />
													</label>
												</div>
												<div class="checkbox">
													<label>
														<!-- Checkbox -->
														<input id="annoMeseCheck" path="annoMeseCheck" name="annoMeseCheck" type="checkbox" <c:if test="${statisticheCommand.annoMeseCheck}">checked="checked"</c:if> />
														<!--  -->
														<span class="cr"><i class="cr-icon fa fa-check"></i></span>
														<!-- Label -->
														<spring:message code="mypivot.statistiche.filter.annoMese" />
													</label>
												</div>
												<div class="checkbox">
													<label>
														<!-- Checkbox -->
														<input id="annoMeseGiornoCheck" path="annoMeseGiornoCheck" name="annoMeseGiornoCheck" type="checkbox" <c:if test="${statisticheCommand.annoMeseGiornoCheck}">checked="checked"</c:if> />
														<!--  -->
														<span class="cr"><i class="cr-icon fa fa-check"></i></span>
														<!-- Label -->
														<spring:message code="mypivot.statistiche.filter.annoMeseGiorno" />
													</label>
												</div>
											</div><!-- /.checkboxContainer -->
											
											<!-- 
												HTML CALENDARIO PER FILTRARE LA STATISTICA PER ANNO/I
											-->
											<div id="divFilterXAnno" class="containerYears hidden">  
											 	
											 	<div id="calendarXAnno"></div>
											 
											</div><!-- /.containerYears -->
											
											<!-- 
												HTML CALENDARIO PER FILTRARE LA STATISTICA PER ANNO E MESE/I
											-->
											<div id="divFilterXMese" class="containerYears containerMonth hidden">  
											 	
											 	<div id="calendarXMese"></div>
											 
											</div><!-- /.containerMonth -->
											
											<!-- 
												HTML CALENDARIO PER FILTRARE LA STATISTICA PER ANNO, MESE E GIORNO/I
											-->
											<div id="divFilterXGiorno" class="containerRangeDay hidden"> 
											    <div class="input-group text-center" id="calendarXGiorno">
											    	<!--  -->
													<input path="giorno" name="giorno" id="giorno" type="text" class="input-sm form-control calendar" autocomplete="off" />
												</div>
											</div><!-- /.containerRangeDay -->
											
										</div><!-- /.inputContainer --> 
									</div><!-- /.span6 -->
									
									
									<!-- 
										BUTTON RICERCA
									-->
									<div class="row-fluid text-center mtop20">
										<button type="button" onclick="javascript:submitFormRicerca();" class="bttn">
											<i class="fa fa-search padding-right"></i> <spring:message code="mypivot.button.cerca" />
										</button>
									</div>
								</div><!-- /.searchContainer -->
							</div><!-- /.sidebar -->
							
							
							<!-- LABEL 0 RISULTATI -->
							<c:if test="${empty responseResult.result}">
								<p class="muted warn text-center"><spring:message code="mypivot.statistiche.table.zeroRecords"></spring:message></p>
							</c:if>
								
									
							<!-- TABLE -->	
							<div class="table-responsive">
								<div class="tableContainer">
									<c:if test="${not empty responseResult.result}">
										<table class="table table-hover table-bordered">
											<thead>
												<tr class="nobb">
												
													<!-- Label: Data -->
													<th class="text-center" colspan="1">
														<c:choose>
													        <c:when test="${statisticheCommand.annoCheck}">
													        	${statisticheCommand.anno}
													        </c:when>
													        <c:when test="${statisticheCommand.annoMeseCheck}">
													            ${statisticheCommand.mese}
													        </c:when>
													        <c:when test="${statisticheCommand.annoMeseGiornoCheck}">
													            ${statisticheCommand.giorno}
													        </c:when>
												         	<c:otherwise></c:otherwise>
												      	</c:choose>
													</th>
													
													<!-- Label: Importi -->
													<th colspan="3" class="text-center">
														<spring:message code="mypivot.statistiche.table.importi"/>
													</th>
												</tr>
												<tr>
													<!-- Label: Tipi dovuti -->
													<th class="text-center nobtr bb" style="border-top: 0px;">
														<spring:message code="mypivot.statistiche.label.tipiDovuto" />
													</th>
													
													<!-- Label: I. Pagati -->
													<c:if test="${statisticheCommand.pagatiCheck}">
														<th class="text-center nobtr bb"><spring:message code="mypivot.statistiche.table.pagati"/></th>
													</c:if>
													
													<!-- Label: I. Rendicontati -->
													<c:if test="${statisticheCommand.rendicontatiCheck}">
														<th class="text-center bb"><spring:message code="mypivot.statistiche.table.rendicontati"/></th>
													</c:if>
													
													<!-- Label: I. Incassati -->
													<c:if test="${flgTesoreria && statisticheCommand.incassatiCheck}">
														<th class="text-center nobtr bb"><spring:message code="mypivot.statistiche.table.incassati" /></th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="rowDto" items="${responseResult.result}">
													<tr>
														<!-- Dovuto -->
													    <td class="text-center">${rowDto.desc}</td>
													    
													    <!-- I. Pagati -->
														<c:if test="${statisticheCommand.pagatiCheck}">
															<td class="text-center nowrap">${rowDto.importiDTO.importoPagato}</td>
														</c:if>
														
														<!-- I. Rendicantati -->
														<c:if test="${statisticheCommand.rendicontatiCheck}">
															<td class="text-center nowrap">${rowDto.importiDTO.importoRendicontato}</td>
														</c:if>
														
														<!-- I. Incassati -->
														<c:if test="${flgTesoreria && statisticheCommand.incassatiCheck}">
															<td class="text-center nowrap">${rowDto.importiDTO.importoIncassato}</td>
														</c:if>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr>
													<!-- Label: Totale -->
													<th class="text-center"><spring:message code="mypivot.statistiche.table.totale" /></th>
													
													<!-- Tot. Pagati -->
													<c:if test="${statisticheCommand.pagatiCheck}">
														<th class="text-center nowrap">${responseResult.totImportiDTO.importoPagato}</th>
													</c:if>
													
													<!-- Tot. Rendicontati -->
													<c:if test="${statisticheCommand.rendicontatiCheck}">
														<th class="text-center nowrap">${responseResult.totImportiDTO.importoRendicontato}</th>
													</c:if>
													
													<!-- Tot. Incassati -->
													<c:if test="${flgTesoreria && statisticheCommand.incassatiCheck}">
														<th class="text-center nowrap">${responseResult.totImportiDTO.importoIncassato}</th>
													</c:if>
												</tr>
											</tfoot>
										</table><!-- /.table -->
									</c:if>
								</div><!-- /.tableContainer -->
							</div><!-- /.table-responsive -->

						</div><!-- /.containers -->
					</div><!-- /.panel-body -->
				</form:form>
				
			</div><!-- /.table-responsive -->
		</div><!-- /.span12 -->
	</div><!-- /.home_content -->
</div><!-- /.cruscotto-incassi -->