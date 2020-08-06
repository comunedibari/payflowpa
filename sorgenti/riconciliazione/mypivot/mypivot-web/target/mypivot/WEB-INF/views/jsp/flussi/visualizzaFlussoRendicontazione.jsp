<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRendicontazioneCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>
<script type="text/javascript">
	function visualizzaRicevuta(codIuf) {
		var form = $('#visualizzaRicevutaForm');
		form.find('#codiceIuf').val(codIuf);
		form.submit();
	}

	/*$(document).ready(
			function() {
				var regolamentoCheckedAttribute = $("#data_regolamento_check")
						.is(":checked") ? 'enable' : 'disable';
				$("#data_regolamento_da").datepicker(
						regolamentoCheckedAttribute);
				$("#data_regolamento_a")
						.datepicker(regolamentoCheckedAttribute);
			});*/
</script>

<div>
	<div class="home_content">
		<div class="titolo-pagina">
			<h1>
				<spring:message code="mypivot.visualizzaCompleta.title" /> / <span class="txt-titolo"><spring:message code="mypivot.label.flussi.rendicontazione" /></span>
			</h1>
		</div>
		<div>
			<div style="display: none;">

				<form action="visualizzaRicevuta.html" id="visualizzaRicevutaForm"
					method="get">
					<input id="forceClear" type="hidden" name="forceClear" value="true" />
					<input id="codiceIuf" type="hidden" name="iuf" />
				</form>
			</div>

			<form:form commandName="visualizzaFlussoRendicontazioneCommand"
				method="post"
				modelAttribute="visualizzaFlussoRendicontazioneCommand"
				action="visualizzaRendicontazione.html">
				<div class="row-fluid">
					<!-- Date di filtro -->
					<div class="debiti_content_filters date_filter span12">
						<div class="form-inline form-actions">
							<div class="span5"></div>
							<div class="span2" style="text-align: center;">
								<form:hidden id="data_regolamento_check"
									path="dataRegolamentoCheck" name="data_regolamento_check" />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.regolamento.dataRegolamento" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="dataRegolamentoDa" id="data_regolamento_da"
										type="text" name="data_regolamento_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="dataRegolamentoA" id="data_regolamento_a"
										type="text" name="data_regolamento_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<br />
							</div>
							<div class="span5"></div>
							<br />
						</div>
					</div>
				</div>

				<div class="debiti_content_filters">
					<div class="form-inline form-actions">
						<div class="row-fluid">
							<div class="span6">
								<label><spring:message
										code="mypivot.visualizza.flussoRendicontazione" /></label><br />
								<form:input path="iuf" class="input-block-level" id="iuf"
									name="iuf" type="text" placeholder="" />
								<br />
							</div>

							<div class="span6">
								<label><spring:message
										code="mypivot.visualizza.regolamento" /></label><br />
								<form:input path="identificativoUnivocoRegolamento"
									class="input-block-level" id="identificativoUnivocoRegolamento"
									name="identificativoUnivocoRegolamento" type="text"
									placeholder="" />
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
							value="visualizzaFlussoRendicontazioneCommand">
							<i class="fa fa-search"></i>&nbsp;&nbsp;&nbsp;
							<spring:message code="mypivot.button.cerca" />
						</button>
					</div>
				</div>
			</form:form>

		</div>

		<jsp:include page="rendicontazioneContent.jsp"></jsp:include>

	</div>
</div>