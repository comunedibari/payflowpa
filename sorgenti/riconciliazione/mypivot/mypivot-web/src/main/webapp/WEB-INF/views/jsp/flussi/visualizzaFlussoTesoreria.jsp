<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoTesoreriaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>
<script type="text/javascript">
	function visualizzaRendicontazione(codIuf){
	    var form = $('#visualizzaRendicontazioneForm');
	    form.find('#codiceIuf').val(codIuf);
	    form.submit();
	}
	
	function visualizzaRicevuta(codIuv){
	    var form = $('#visualizzaRicevutaForm');
	    form.find('#codiceIuv').val(codIuv);
	    form.submit();
	}
	
	function espandiTesoreria(deAnnoBolletta, codBolletta){
	    var form = $('#espandiTesoreriaForm');
	    form.find('#deAnnoBolletta').val(deAnnoBolletta);
	    form.find('#codBolletta').val(codBolletta);
	    form.submit();
	}
$(document).ready(function() {
	var contabileCheckedAttribute = $("#data_contabile_check").is(":checked") ? 'enable' : 'disable';
	var valutaCheckedAttribute = $("#data_valuta_check").is(":checked") ? 'enable' : 'disable';
	$("#data_contabile_da").datepicker(contabileCheckedAttribute);
	$("#data_contabile_a").datepicker(contabileCheckedAttribute);
	$("#data_valuta_da").datepicker(valutaCheckedAttribute);
	$("#data_valuta_a").datepicker(valutaCheckedAttribute);
	
    $("#importo").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter, comma and . and cmd
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 188, 190, 91]) !== -1 || e.metaKey ||
        		// Allow: Ctrl+A
	            (e.keyCode == 65 && e.ctrlKey === true) ||
	             // Allow: Ctrl+C
	            (e.keyCode == 67 && e.ctrlKey === true) ||
	            // Allow: Ctrl+V
	            (e.keyCode == 86 && e.ctrlKey === true) ||
	             // Allow: Ctrl+X
	            (e.keyCode == 88 && e.ctrlKey === true) ||
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)
        ) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
});
</script>

<div>
	<div class="home_content">
		<div class="titolo-pagina">
			<h1>
				<spring:message code="mypivot.visualizzaCompleta.title" /> / <span class="txt-titolo"><spring:message code="mypivot.label.flussi.giornaleDiCassa" /></span>
			</h1>
		</div>
		<div>
			<div style="display: none;">
				<form action="visualizzaRendicontazione.html"
					id="visualizzaRendicontazioneForm" method="get">
					<input id="forceClear" type="hidden" name="forceClear" value="true" />
					<input id="codiceIuf" type="hidden" name="iuf" />
				</form>
				<form action="visualizzaRicevuta.html" id="visualizzaRicevutaForm"
					method="get">
					<input id="codiceIuv" type="hidden" name="iuv" />
				</form>
				<form action="espandiTesoreria.html" id="espandiTesoreriaForm"
					method="get">
					<input id="deAnnoBolletta" type="hidden" name="deAnnoBolletta" />
					<input id="codBolletta" type="hidden" name="codBolletta" />
				</form>
			</div>

			<form:form commandName="visualizzaFlussoTesoreriaCommand"
				method="post" modelAttribute="visualizzaFlussoTesoreriaCommand"
				action="visualizzaTesoreria.html">
				<div class="row-fluid">
					<!-- Date di filtro -->
					<div class="debiti_content_filters date_filter span12">
						<div class="form-inline form-actions">
							<div class="span2_5"></div>
							<div class="span2_5" style="text-align: center;">
								<form:checkbox id="data_contabile_check"
									path="dataContabileCheck" name="data_contabile_check"
									class="date-trigger-checkbox"
									onclick="handleDatePanel(this, 'dataContabile');" />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.datiPagamento.dataContabile" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="dataContabileDa" id="data_contabile_da"
										type="text" name="data_contabile_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="dataContabileA" id="data_contabile_a"
										type="text" name="data_contabile_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<br />
							</div>
							<div class="span2_5"></div>
							<div class="span2_5" style="text-align: center;">
								<form:checkbox id="data_valuta_check" path="dataValutaCheck"
									name="data_valuta_check" class="date-trigger-checkbox"
									onclick="handleDatePanel(this, 'dataValuta');" />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.datiPagamento.dataValuta" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="dataValutaDa" id="data_valuta_da" type="text"
										name="data_valuta_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="dataValutaA" id="data_valuta_a" type="text"
										name="data_valuta_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
							</div>
							<div class="span2_5"></div>
							<br />
						</div>
					</div>
				</div>

				<div class="debiti_content_filters">
					<div class="form-inline form-actions">
						<div class="row-fluid">
							<div class="span4">
								<label><spring:message
										code="mypivot.visualizza.identificativo.IUV" /></label><br />
								<form:input path="iuv" class="input-block-level" id="iuv"
									name="iuv" type="text" placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.flussoRendicontazione" /></label><br />
								<form:input path="iuf" class="input-block-level" id="iuf"
									name="iuf" type="text" placeholder="" />
								<br />
								<%--<label><spring:message code="mypivot.visualizza.conto" /></label>
								<br />
								<form:input path="conto" class="input-block-level" id="conto"
									name="conto" type="text" placeholder="" />
								<br />--%>
							</div>

							<div class="span4">
								<label><spring:message
										code="mypivot.visualizza.deAnnoBolletta" /></label><br />
								<form:input path="deAnnoBolletta" class="input-block-level"
									id="deAnnoBolletta" name="deAnnoBolletta" type="text"
									placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.codBolletta" /></label><br />
								<form:input path="codBolletta" class="input-block-level"
									id="codBolletta" name="codBolletta" type="text" placeholder="" />
								<br />
							</div>
							<div class="span4">
								<label><spring:message code="mypivot.visualizza.codOR1" /></label>
								<br />
								<form:input path="codOr1" class="input-block-level" id="codOr1"
									name="codOr1" type="text" placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.importoTesoreria" /></label> <br />
								<form:input path="importo" class="input-block-level"
									id="importo" name="importo" type="text" placeholder="" />
								<br />
							</div>
							<form:hidden path="page" />
						</div>
					</div>

					<div class="align-center">
						<spring:message code="mypivot.pager.numElements" />
						<br />
						<form:select class="pagerSelect" itemLabel="pageSize"
							itemValue="pageSize" path="pageSize">
							<form:option value="5" />
							<form:option value="10" />
							<form:option value="20" />
						</form:select>
						<button type="submit" class="btn" style="margin-bottom: 10px;"
							value="visualizzaFlussoTesoreriaCommand">
							<i class="fa fa-search"></i>&nbsp;&nbsp;&nbsp;
							<spring:message code="mypivot.button.cerca" />
						</button>
					</div>
				</div>
			</form:form>

		</div>

		<jsp:include page="tesoreriaContent.jsp"></jsp:include>

	</div>
</div>