<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>
<script type="text/javascript">
	$(document).ready(
			function() {
				var esitoCheckedAttribute = $("#data_esito_check").is(
						":checked") ? 'enable' : 'disable';
				$("#data_esito_singolo_pagamento_da").datepicker(
						esitoCheckedAttribute);
				$("#data_esito_singolo_pagamento_a").datepicker(
						esitoCheckedAttribute);
			});
</script>

<div>
	<div class="home_content">
		<div class="titolo-pagina">
			<h1>
				<spring:message code="mypivot.visualizzaCompleta.title" /> / 
				
				<!-- 
					Controllo se e' la pagina di dettaglio del cruscotto incassi.
				-->
				<c:choose>
					<c:when test="${not empty forceCruscotto and forceCruscotto}">
						<span class="txt-titolo"><spring:message code="mypivot.statistiche.menu.statistica.dettaglio" /></span>
					</c:when>
					<c:otherwise>			
						<span class="txt-titolo"><spring:message code="mypivot.label.flussi.ricevute" /></span>
					</c:otherwise>	
				</c:choose>
			</h1>
		</div>
		<div>
			<form:form commandName="visualizzaFlussoRicevutaCommand"
				method="post" modelAttribute="visualizzaFlussoRicevutaCommand">
				<div class="row-fluid">
					<!-- Date di filtro -->
					<div class="debiti_content_filters date_filter span12">
						<div class="form-inline form-actions">
							<div class="span5"></div>
							<div class="span2" style="text-align: center;">
								<form:checkbox id="data_esito_check"
									path="dataEsitoSingoloPagamentoCheck" name="data_esito_check"
									class="date-trigger-checkbox"
									onclick="handleDatePanel(this, 'dataEsito');" />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.datiPagamento.dataEsito" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="dataEsitoSingoloPagamentoDa"
										id="data_esito_singolo_pagamento_da" type="text"
										name="data_esito_singolo_pagamento_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="dataEsitoSingoloPagamentoA"
										id="data_esito_singolo_pagamento_a" type="text"
										name="data_esito_singolo_pagamento_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<br />
							</div>
							<div class="span5"></div>
							<form:hidden path="iuf"/>
							<br />
						</div>
					</div>
				</div>

				<div class="debiti_content_filters">
					<div class="form-inline form-actions">
						<div class="row-fluid">
							<div class="span4">
								<!-- 
								 	Controllo se e' la pagina di dettaglio del cruscotto incassi.
								 -->
								<c:choose>
									<c:when test="${not empty forceCruscotto and forceCruscotto}">
										<form:input path="iud" type="hidden" />
										<form:hidden path="listaIUD"/>
									</c:when>
									
									<c:otherwise>
										<label><spring:message code="mypivot.visualizza.identificativo.IUD" /></label><br />
										<form:input path="iud" class="input-block-level" id="codice_iud" name="codice_iud" type="text" placeholder="" />
										<br /> 
									</c:otherwise>
								</c:choose>
								
								<label><spring:message code="mypivot.visualizza.identificativo.IUV" /></label><br />
								<form:input path="iuv" class="input-block-level" id="codice_iuv" name="codice_iuv" type="text" placeholder="" />
								<br /> 
								
								<label><spring:message code="mypivot.visualizza.identificativo.IUR" /></label><br />
								<form:input path="identificativoUnivocoRiscossione"
									class="input-block-level"
									id="identificativo_univoco_riscossione"
									name="identificativo_univoco_riscossione" type="text"
									placeholder="" />
								<br />
							</div>

							<div class="span4">
								<label><spring:message
										code="mypivot.visualizza.attestante" /></label><br />
								<form:input path="denominazioneAttestante"
									class="input-block-level" id="denominazione_attestante"
									name="denominazione_attestante" type="text" placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.pagatore.codice" /></label><br />
								<form:input path="codiceIdentificativoUnivocoPagatore"
									class="input-block-level"
									id="codice_identificativo_univoco_pagatore"
									name="codice_identificativo_univoco_pagatore" type="text"
									placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.pagatore.anagrafica" /></label><br />
								<form:input path="anagraficaPagatore" class="input-block-level"
									id="anagrafica_pagatore" name="anagrafica_pagatore" type="text"
									placeholder="" />
								<br />
							</div>
							<div class="span4">
								<label><spring:message code="mypivot.visualizza.versante.codice" /></label><br />
								<form:input path="codiceIdentificativoUnivocoVersante"
									class="input-block-level"
									id="codice_identificativo_univoco_versante"
									name="codice_identificativo_univoco_versante" type="text"
									placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.versante.anagrafica" /></label><br />
								<form:input path="anagraficaVersante" class="input-block-level"
									id="anagrafica_versante" name="anagrafica_versante" type="text"
									placeholder="" />
								<br /> 
								
								<!-- 
								 	Controllo se e' la pagina di dettaglio del cruscotto incassi.
								 -->
								<c:choose>
									<c:when test="${not empty forceCruscotto and forceCruscotto}">
										<form:input path="codTipoDovuto" type="hidden" />
									</c:when>
									
									<c:otherwise>
										<label><spring:message code="mypivot.visualizza.tipoDovuto" /></label><br />
										<form:select class="input-block-level" path="codTipoDovuto" id="tipoDovutoCombo">
											<form:option value="">TUTTI</form:option>
											<c:forEach var="ctd" items="${listaTipiDovutoPerEnte}">
												<form:option value="${ctd.codTipo}">
													<c:out value="${ctd.deTipo}"></c:out>
												</form:option>
											</c:forEach>
										</form:select>
										<br />
									</c:otherwise>
								</c:choose>
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
							value="visualizzaFlussoRicevutaCommand">
							<i class="fa fa-search"></i>&nbsp;&nbsp;&nbsp;
							<spring:message code="mypivot.button.cerca" />
						</button>
					</div>
				</div>
			</form:form>

		</div>

		<jsp:include page="ricevutaContent.jsp"></jsp:include>

	</div>
</div>