<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.net.URLEncoder"%>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var chartData;
	resetAll();
	$('#visualizza_grafico').click(function(){
		resetAllDialog();
		$('#dialog').dialog({
			 autoOpen: false,
             maxWidth: 1500,
             maxHeight: 700,
             width: 1500,
             height: 700
		});
		$('#dialog').dialog("open");
		google.charts.load('current', {packages:['wordtree'], 'language': 'it'});
		google.charts.setOnLoadCallback(drawChart);
		function drawChart() {
			$('#loaderDialog').show();
			/*$.ajax({
		    	url: "espandiTesoreriaJson.html",
		        dataType: "json",
		        data: {
		        	deAnnoBolletta: '<c:out value="${deAnnoBolletta}"/>', 
		           	codBolletta: '<c:out value="${codBolletta}"/>'
		        },error: function(){
		        	$("#loaderDialog").hide();
		        	$("#errorDialog").show();
		        },
		        timeout: 20000,
		        success: function (container) {*/
		        	var container = chartData;
		        	if(container == null) {
		        		$("#loaderDialog").hide();
		            	$("#errorDialog").show();
		        	} else {
		        		if(container.status === 'OK') {
		        			$("#loaderDialog").hide();
		        			$("#chart_div").show();
		        			var res = container.body;
		        			var arr = new Array;
		        			var testata = new Array;
		        			testata.push("Bilancio");
		        			arr.push(testata);
		        			for (var i=0; i<res.length; i++) {
		        				var row = res[i];
		        				var arr1 = new Array;
		        				var riga = "${fn:trim(deAnnoBolletta)} - ${fn:trim(codBolletta)}|" + row["codUfficio"] + "|" + row["codTipoDovuto"] + "|" + row["codCapitolo"] + "|" + row["codAccertamento"] + "|" + row["importo"];
		        				//console.log(riga);
		        				arr1.push(riga);
		        				arr.push(arr1);
		        			}
		        			//console.log(arr);
		        		
		        		  	var data = new google.visualization.arrayToDataTable(arr);
		        		 	var options = {
		        		  		wordtree: {
		        		    		format: 'implicit',
		        		    		type: 'suffix',
		        		    		word: '${fn:trim(deAnnoBolletta)} - ${fn:trim(codBolletta)}',
		        		    		wordSeparator: '|'
		        		    	}
		        		  	};
		        		  
		        		  	var chart = new google.visualization.WordTree(document.getElementById('chart_div'));
		        		  	chart.draw(data, options);
		        		} else {
		        			if(typeof container.infoMessage !== "undefined") {
		        				$("#loaderDialog").hide();
		        				$('#infomessageDialog').show();
		        				$('#infomessageDialog p').text(container.infoMessage);
		        			} else if(typeof container.errorMessage !== "undefined") {
		        				$("#loaderDialog").hide();
		        				$('#errormessageDialog').show();
		        				$('#errormessageDialog p').text(container.errorMessage);
		        			}
		        		}
		        	}
		        //}
			//});
		}
	});
	$('#loader').show();
	$.ajax({
    	url: "espandiTesoreriaJson.html",
        dataType: "json",
        data: {
        	deAnnoBolletta: '<c:out value="${deAnnoBolletta}"/>', 
           	codBolletta: '<c:out value="${codBolletta}"/>'
        },error: function(){
        	$("#loader").hide();
        	$("#error").show();
        },
        timeout: 30000,
        success: function (container) {
        	chartData = container;
        	if(container == null) {
        		$("#loader").hide();
            	$("#error").show();
        	} else {
        		if(container.status === 'OK') {
        			$("#loader").hide();
        			$("#tabellaRecord").show();
        			var res = container.body;
        			for (var i=0; i<res.length; i++) {
       					var row = res[i];
       					var riga = "<tr><td><c:out value='${deAnnoBolletta}'/> - <c:out value='${codBolletta}'/></td><td>" + row["codUfficio"] 
       					+ "</td><td>" + row["codTipoDovuto"] + "</td><td>" + row["codCapitolo"] + "</td><td>" + row["codAccertamento"] + "</td><td>" + row["importo"] + "</td></tr>";
       					$("#tabellaRecordTBody").append(riga);
       				}
        			$("#visualizzaGraficoButtonDiv").show();
        		} else {
        			if(typeof container.infoMessage !== "undefined") {
        				$("#loader").hide();
        				$('#infomessage').show();
        				$('#infomessage p').text(container.infoMessage);
        			} else if(typeof container.errorMessage !== "undefined") {
        				$("#loader").hide();
        				$('#errormessage').show();
        				$('#errormessage p').text(container.errorMessage);
        			}
        		}
        	}
        }
	});
});
function resetAll() {
	$("#loader").hide();
	$("#error").hide();
	$("#infomessage").hide();
	$("#errormessage").hide();
	$("#chart_div").hide();
	$("#tabellaRecord").hide();
	$("#visualizzaGraficoButtonDiv").hide();
	resetAllDialog();
}
function resetAllDialog() {
	$("#loaderDialog").hide();
	$("#errorDialog").hide();
	$("#infomessageDialog").hide();
	$("#errormessageDialog").hide();
	$("#chart_div").hide();
}
</script>
<div class="titolo-pagina">
	<h1>
		<spring:message code="mypivot.visualizzaCompleta.title" /> / <spring:message code="mypivot.label.flussi.giornaleDiCassa" /> / <span class="txt-titolo"><spring:message code="mypivot.dettaglio.title.giornaleDiCassa" /></span>
	</h1>
</div>
<div class="debiti_table">
	<!-- intestazione -->
	<div class="row-fluid mypay-custom">
		<div class="span12 text-center tesoreriaHeader row-border border-right-0">
			<spring:message code="mypivot.dettaglio.title.giornaleDiCassa" />
		</div>
	</div>
	
	<div class="row-fluid mypay-custom small-text-no-padding tesoreriaRowBackground">	
		<div class="span12 tesoreriaRow padding-bottom-10 border-right-0">
			<div class="span4">
				<c:if test="${fn:trim(flussoTesoreriaDto.deAnnoBolletta) ne null}">
					<label><spring:message code="mypivot.visualizza.deAnnoBolletta" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.deAnnoBolletta)}" />
				</c:if>
				
				<c:if test="${fn:trim(flussoTesoreriaDto.codBolletta) ne null}">
					<label><spring:message code="mypivot.visualizza.codBolletta" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.codBolletta)}" />
				</c:if>
			
				<label><spring:message code="mypivot.visualizza.conto" />&nbsp;:</label>
				<c:out value="${flussoTesoreriaDto.flussoTesoreriaTO.codConto}" /><br />
				
				<label><spring:message code="mypivot.visualizza.dataValuta" />&nbsp;:</label>
				<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoTesoreriaDto.flussoTesoreriaTO.dtDataValutaRegione}"/><br />
				
				<label><spring:message code="mypivot.visualizza.dataContabile" />&nbsp;:</label>
				<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoTesoreriaDto.flussoTesoreriaTO.dtBolletta}" /><br />
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.deCausale ne null}">
					<label><spring:message code="mypivot.visualizza.causaleRiversamento" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.deCausale)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.dtEffettivaSospeso ne null}">
					<label><spring:message code="mypivot.visualizza.dtEffettivaSospeso" />&nbsp;:</label>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoTesoreriaDto.flussoTesoreriaTO.dtEffettivaSospeso}" /><br />
				</c:if>				
			</div>
			<div class="span4">
				<label><spring:message code="mypivot.visualizza.importoTesoreria" />&nbsp;:</label>
				<c:out value="${flussoTesoreriaDto.deImporto}" />&nbsp;&euro;<br />
				
				<label><spring:message code="mypivot.visualizza.codOR1" />&nbsp;:</label>
				<c:out value="${flussoTesoreriaDto.flussoTesoreriaTO.deCognome}" />
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codAbi ne null}">
					<label><spring:message code="mypivot.tipoIdentificativoUnivocoAttestante.A" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codAbi)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codCab ne null}">
					<label><spring:message code="mypivot.visualizza.codCab" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codCab)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codIdDominio ne null}">
					<label><spring:message code="mypivot.visualizza.codIdDominio" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codIdDominio)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.dtRicezione ne null}">
					<label><spring:message code="mypivot.visualizza.deDataRicezione" />&nbsp;:</label>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${flussoTesoreriaDto.flussoTesoreriaTO.dtRicezione}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codiceGestionaleProvvisorio ne null}">
					<label><spring:message code="mypivot.visualizza.codiceGestionaleProvvisorio" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codiceGestionaleProvvisorio)}" />
				</c:if>
			</div>
			<div class="span4">
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.deAnnoDocumento ne null}">
					<label><spring:message code="mypivot.visualizza.deAnnoDocumento" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.deAnnoDocumento)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codDocumento ne null}">
					<label><spring:message code="mypivot.visualizza.codDocumento" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codDocumento)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.deAeProvvisorio ne null}">
					<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.deAeProvvisorio)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codProvvisorio ne null}">
					<label><spring:message code="mypivot.visualizza.codProvvisorio" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codProvvisorio)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codIdUnivocoFlusso ne null}">
					<label><spring:message code="mypivot.visualizza.flussoRendicontazione" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codIdUnivocoFlusso)}" />
				</c:if>
				
				<c:if test="${flussoTesoreriaDto.flussoTesoreriaTO.codIdUnivocoVersamento ne null}">
					<label><spring:message code="mypivot.visualizza.identificativo.IUV" />&nbsp;:</label>
					<c:out value="${fn:trim(flussoTesoreriaDto.flussoTesoreriaTO.codIdUnivocoVersamento)}" />
				</c:if>
			</div>
		</div>
	</div>
</div>
<div class="menu-bottom breadcrumb visualizza_grafico" id="visualizzaGraficoButtonDiv">
	<div class="row-fluid">
		<div class="span5"></div>
		<div class="span2">
			<button type="button" id="visualizza_grafico" class="btn btn-block btn-info">
				Visualizza grafico
			</button>
			</div>
		<div class="span5"></div>
	</div>
</div>
<div class="row-fluid menu-bottom">
	<div class="span12">
		<div id="tabellaRecord">
			<table class="table">
				<thead>
					<tr>
						<th>Anno Bolletta - Codice Bolletta</th>
						<th>Codice Ufficio</th>
						<th>Tipo Dovuto</th>
						<th>Codice Capitolo</th>
						<th>Codice Accertamento</th>
						<th>Importo</th>
					</tr>
				</thead>
				<tbody id="tabellaRecordTBody">
				</tbody>
			</table>
		</div>
		<div id="loader" class="loader">
			<img src="<%= request.getContextPath() %>/images/loader.gif" alt="In caricamento..." />
			<p align="center">
				<spring:message code="mypivot.messages.inCaricamento" />
			</p>
		</div>
		<div id="error" class="alert alert-error">
			<p align="center">
				<spring:message code="mypivot.messages.error.elaborazioneDati" />
			</p>
		</div>
		<div id="infomessage" class="alert alert-info">
			<h4>
				<spring:message code="mypivot.messages.info" />:
			</h4>
			<p>
			</p>
		</div>
		<div id="errormessage" class="alert alert-error">
			<h4>
				<spring:message code="mypivot.messages.error" />:
			</h4>
			<p>
			</p>
		</div>
	</div>
</div>
<div class="row-fluid menu-bottom" id="dialog">
	<div class="span12">
		<div id="loaderDialog" class="loader">
			<img src="<%= request.getContextPath() %>/images/loader.gif" alt="In caricamento..." />
			<p align="center">
				<spring:message code="mypivot.messages.inCaricamento" />
			</p>
		</div>
		<div id="errorDialog">
			<p align="center">
				<spring:message code="mypivot.messages.error.elaborazioneDati" />
			</p>
		</div>
		<div id="infomessageDialog" class="alert alert-info">
			<h4>
				<spring:message code="mypivot.messages.info" />:
			</h4>
			<p>
			</p>
		</div>
		<div id="errormessageDialog" class="alert alert-error">
			<h4>
				<spring:message code="mypivot.messages.error" />:
			</h4>
			<p>
			</p>
		</div>
		<div id="chart_div" class="chart">
		</div>
	</div>
</div>
