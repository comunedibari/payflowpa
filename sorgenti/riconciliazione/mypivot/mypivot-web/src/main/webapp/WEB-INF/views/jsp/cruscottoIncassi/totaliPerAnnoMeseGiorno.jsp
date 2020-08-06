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
	 * Recupero dal model la tipologia di statistica in visualizzazione, di modo da customizzare correttamente la view.
	 */
	var tipologiaStatistica = '${statisticheCommand.tipologia}';

	
	$(document).ready(function() {
		
/* TIPOLOGIA PER ANNO   ************************************************************************************************************/ 
			
		/*
		 * La funzione resetta il componente per filtrare i risultati per anno/i.
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
	        multidate: true,		 // Enable multidate picking.
	        multidateSeparator: ",", // Il separatore delle date quando si genera il valore dell'input.
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
		});// close click Checkbox Per Anno
			    
		/**
		 * Verifico quale tipologia di statistica è attiva, se lo è quella "Totali per Anno", devo:
		 */
	    if(tipologiaStatistica == 'ANNO'){
	       // Leggo l'elenco anni ritornati dal server. 
	       var selYears = ${statisticheCommand.listaAnni};
	       
	       /**
	        * Ciclo la lista e ogni elemento devo formattarlo come richiesto dal componente
	        * String[] dove la data è formattata come 'dd-MM-YYYY'.
	        */
	       for (i = 0; i < selYears.length; i++) selYears[i] = "01-01-" + selYears[i];   // Reinserisco la data formattata
	       
	       // Seleziono gli elementi nel calendario 
	       $(divId_XAnno + " #calendarXAnno").datepicker("setDates", selYears);
	
		   // Rimuovo il class css che nasconde il div del calendario.
		   $(divId_XAnno).removeClass("hidden");
	    }
			    
/***********************************************************************************************************************************/ 
		
/* TIPOLOGIA PER MESE   ************************************************************************************************************/ 
			 
	 	/**
	 	 * Variabile in cui salvo di volta in volta l'anno selezionato 
	 	 */ 
	 	var slcYearMonth_XMese = null;
			 	
	 	/*
		 * La funzione resetta il componente per filtrare i risulati per mese.
		 */
	 	function resetFilter_XMese() {
			/*
			 * Svuoto la varibile
			 */
	 		slcYearMonth_XMese = null;
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
		 * Init del datepicker per la selezione dell'anno e del mese/i.
		 */
		$(divId_XMese + " #calendarXMese").datepicker({
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
	        multidate: true,
	        multidateSeparator: ",",
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
		 * Evento chiamato quando cambia il mese.
		 */
		$(divId_XMese + " #calendarXMese").datepicker().on("changeMonth", function(e) {
			/* */
		    var date = e.date;
	    	/*
			 * Se l'anno in selezione è diverso dall'ultimo selezionato, elimino la selezione dei mesi riferiti a quell'anno.
			 */
	        if (slcYearMonth_XMese == null || (slcYearMonth_XMese != null && date.getFullYear() != slcYearMonth_XMese)) {
	        	// Cancello gli elementi selezionati
	    		$(divId_XMese + " #calendarXMese").datepicker('clearDates');
	
				// Conservo il nuovo anno selezionato
	    		slcYearMonth_XMese = date.getFullYear();
	        } 
	    });
			
		/**
	     * Chiamato quando è cliccato il checkbox che attiva o meno il filtro per anno/mese/i. 
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
		 * Verifico quale tipologia di statistica è attiva, se lo è quella "Totali per Mese", devo:
		 */
	    if(tipologiaStatistica == 'MESE'){
	       // Leggo l'anno selezionato
	       var slcYear = ${statisticheCommand.anno};
	       
	       // Leggo i mesi selezionati
	       var selectedMonths_XMese = ${statisticheCommand.listaMesi} ;
	
	       /**
	        * Ciclo la lista e ogni elemento devo formattarlo come richiesto dal componente
	        * String[] dove la data è formattata come 'dd-MM-YYYY'.
	        */
	       for (i = 0; i < selectedMonths_XMese.length; i++) { 
	    	   // Reinserisco la data formattata
	    	   selectedMonths_XMese[i] = "01-" + "-" + (selectedMonths_XMese[i]) + "-" + slcYear;
	       }
	
	       // Seleziono gli elementi nel calendario 
	       $(divId_XMese + " #calendarXMese").datepicker("setDates", selectedMonths_XMese);
	
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
		 * Init del datepicker per la selezione del range di date.
		 */
		$(divId_XGiorno + " #calendarXGiorno").datepicker({
			format: "dd/mm/yyyy",
			autoclose: false,
			clearBtn: true,
			container: "body",
			enableOnReadonly: true,
			endDate: new Date(),
			keyboardNavigation: true,
			maxViewMode: 2,
	   	 	language: "it",
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
	     * Chiamato quando è cliccato il checkbox che attiva o meno il filtro per anno/mese/giorno/i. 
	     */
	    $(formId_Cruscotto + " #annoMeseGiornoCheck").click(function(){  
			//
			var isCheck = $(formId_Cruscotto + " #annoMeseGiornoCheck").is(":checked");
	
			// Cancello gli elementi selezionati
			$(divId_XGiorno + " #calendarXGiorno input").each(function (){
				$(this).datepicker("clearDates");
			});
			
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
		 * Verifico quale tipologia di statistica è attiva, se lo è quella "Totali per Giorno", devo:
		 */
	    if(tipologiaStatistica == 'GIORNO'){

			// set date inizio e fine
	    	$(divId_XGiorno + " #calendarXGiorno #giornoDal").datepicker("update", "${statisticheCommand.giornoDal}");
	    	$(divId_XGiorno + " #calendarXGiorno #giornoAl").datepicker("update", "${statisticheCommand.giornoAl}");

	    	// update the range
	    	$(divId_XGiorno + " #calendarXGiorno").datepicker('updateDates');
	    	
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
			// set tipologia
			$(formId_Cruscotto + " #tipologia").val("ANNO");
	
			// Get anni selezionati
			var selectedYears = $(divId_XAnno + " #calendarXAnno").datepicker("getDates");
			
			var years = [];
	
			/* 
			 * Ciclo la lista di date, dalle quali estraggo solo l'anno 
			 */
		    for (i = 0; i < selectedYears.length; i++) { 
			    // add 
		    	years.push(selectedYears[i].getFullYear());
		    }
	
		    // set value list anni per submit
			$(formId_Cruscotto + " #listaAnni").val(years);
	 	}
		else
			if(annoMeseCheck){
				// set tipologia
				$(formId_Cruscotto + " #tipologia").val("MESE");
	
				// Get anni selezionati
				var selectedMonths = $(divId_XMese + " #calendarXMese").datepicker("getDates");
	
				var year = null; 	var months = [];
	
				/* 
				 * Ciclo la lista di date, dalle quali estraggo solo l'anno 
				 */
			    for (i = 0; i < selectedMonths.length; i++) { 
					// get anno, lo sovrascivo ogni volta 
					year = selectedMonths[i].getFullYear();
				    // add 
			    	months.push(selectedMonths[i].getMonth() + 1);
			    }
	
			    // set value anno per submit
				$(formId_Cruscotto + " #anno").val(year);
			    // set value list mesi per submit
				$(formId_Cruscotto + " #listaMesi").val(months);
			}
		else
			if(annoGiornoCheck){
				// set tipologia
				$(formId_Cruscotto + " #tipologia").val("GIORNO");
			}
	
		/* */
		var form = $(formId_Cruscotto);
			form.submit();
	}// submitFormRicerca
	
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
							<span class="txt-titolo"><spring:message code="mypivot.statistiche.menu.statistica.${statisticheCommand.tipologia}" /></span>
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
									<!-- Indica la tipologia di statistica richiesta in View. -->
									<form:input id="tipologia" path="tipologia" type="hidden" name="tipologia" value="" />
									
									<!-- Elenco degli anni per cui estrarre il dato statistico.	(Formato: yyyy) -->
									<form:input id="listaAnni" path="listaAnni" type="hidden" name="listaAnni" />
									
									<!-- L'anno per cui estrarre il dato statistico.	(Formato: yyyy) -->
									<form:input id="anno" path="anno" type="hidden" name="anno" />
									
									<!-- Elenco dei mesi dell'anno(specificato nella property "anno") per cui estrarre il dato statistico.	(Formato: MM) -->
									<form:input id="listaMesi" path="listaMesi" type="hidden" name="listaMesi" />
				
				
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
											    <div class="input-daterange input-group" id="calendarXGiorno">
											    	<!--  -->
													<input path="giornoDal" name="giornoDal" id="giornoDal" type="text" class="input-sm form-control calendar" autocomplete="off" />
												    <!-- Label -->
												    <span class="input-group-addon"><spring:message code="mypivot.statistiche.filter.al" /></span>
												    <!--  -->
												    <input path="giornoAl" name="giornoAl" id="giornoAl" type="text" class="input-sm form-control calendar" autocomplete="off" />
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
													<th class="bb text-center" rowspan="2">
														<c:choose>
													        <c:when test="${statisticheCommand.tipologia eq 'MESE'}">
													        	${statisticheCommand.anno}
													        </c:when>
												         	<c:otherwise></c:otherwise>
												      	</c:choose>
													</th>
													
													<!-- Label: N Pagamenti -->
													<th class="bb text-center" rowspan="2">
														<spring:message code="mypivot.statistiche.table.numPagamenti"/>
													</th>
													
													<!-- Label: Importi -->
													<th colspan="3" class="text-center">
														<spring:message code="mypivot.statistiche.table.importi"/>
													</th>
												</tr>
												<tr>
													<!-- Label: Pagati -->
													<c:if test="${statisticheCommand.pagatiCheck}">
														<th class="text-center nobtr bb">
															<spring:message code="mypivot.statistiche.table.pagati"/>
														</th>
													</c:if>
													
													<!-- Label: Rendicontati -->
													<c:if test="${statisticheCommand.rendicontatiCheck}">
														<th class="text-center bb">
															<spring:message code="mypivot.statistiche.table.rendicontati"/>
														</th>
													</c:if>
													
													<!-- Label: Incassati -->
													<c:if test="${flgTesoreria && statisticheCommand.incassatiCheck}">
														<th class="text-center nobtr bb">
															<spring:message code="mypivot.statistiche.table.incassati" />
														</th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="rowDto" items="${responseResult.result}">
													<tr>
														<!-- Data -->
														<c:choose>
												        	<c:when test = "${statisticheCommand.tipologia eq 'ANNO'}">
												            	<td class="text-center">${rowDto.anno}</td>
												        	</c:when>
													        <c:when test = "${statisticheCommand.tipologia eq 'MESE'}">
													            <td class="text-center text-capitalize">${rowDto.labelMese}</td>
													        </c:when>
													        <c:when test = "${statisticheCommand.tipologia eq 'GIORNO'}">
													            <td class="text-center">${rowDto.giorno}/${rowDto.mese}/${rowDto.anno}</td>
													        </c:when>
												         	<c:otherwise></c:otherwise>
												      	</c:choose>
												      	
												      	<!-- N Pagamenti -->
														<td class="text-center">${rowDto.numPagamenti}</td>
														
														<!-- I. Pagati -->
														<c:if test="${statisticheCommand.pagatiCheck}">
															<td class="text-center nowrap">${rowDto.importiDTO.importoPagato}</td>
														</c:if>
														
														<!-- I. Rendicontati -->
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

													<!-- Tot. Pagamenti -->
													<th class="text-center nowrap">${responseResult.totNumPagamenti}</th>

													<!-- Tot. Pagati -->
													<c:if test="${statisticheCommand.pagatiCheck}">
														<th class="text-center nowrap">${responseResult.totImportiDTO.importoPagato}</th>
													</c:if>
													
													<!-- Tot. Rendicontato -->
													<c:if test="${statisticheCommand.rendicontatiCheck}">
														<th class="text-center nowrap">${responseResult.totImportiDTO.importoRendicontato}</th>
													</c:if>
													
													<!-- Tot. Incassato -->
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
