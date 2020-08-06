<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>
<script type="text/javascript">
	$(document).ready(function() {

		$("#data_esito_singolo_pagamento_da").datepicker({
			showOn : "button",
			buttonImage : "/mypivot/images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "dd/mm/yy",
			regional : "it"
		});

		$("#data_esito_singolo_pagamento_a").datepicker({
			showOn : "button",
			buttonImage : "/mypivot/images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "dd/mm/yy",
			regional : "it"
		});

		$("#data_regolamento_da").datepicker({
			showOn : "button",
			buttonImage : "/mypivot/images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "dd/mm/yy",
			regional : "it"
		});

		$("#data_regolamento_a").datepicker({
			showOn : "button",
			buttonImage : "/mypivot/images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "dd/mm/yy",
			regional : "it"
		});
	});
</script>
<div>
	<h1>
		<spring:message code="mypivot.visualizza" />
	</h1>

	<c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}">
		<ul class="nav nav-tabs">
			<li class="active"><a
				href="<%=request.getContextPath()%>/protected/visualizza.html"><spring:message
						code="mypivot.visualizza" /></a></li>
			<li><a
				href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html"><spring:message
						code="mypivot.visualizzaCompleta" /></a></li>

		</ul>
	</c:if>

	<div class="home_content">

		<!-- 
		
TESTATA
mygov_ente_id
tipo_identificativo_univoco_beneficiario
codice_identificativo_univoco_beneficiario
denominazione_beneficiario


(r) codice_iud

(r) codice_iuv

tipo_identificativo_univoco_attestante
codice_identificativo_univoco_attestante
(r) denominazione_attestante

(r) identificativo_univoco_riscossione

tipo_identificativo_univoco_versante
(r) codice_identificativo_univoco_versante
(r) anagrafica_versante

tipo_identificativo_univoco_pagatore
(r) codice_identificativo_univoco_pagatore
(r) anagrafica_pagatore

(r) causale_versamento

singolo_importo_pagato
esito_singolo_pagamento
(r DA/A) data_esito_singolo_pagamento

(r) identificativo_flusso_rendicontazione
data_ora_flusso_rendicontazione

(r) identificativo_univoco_regolamento
(r DA/A) data_regolamento
importo_totale_pagamenti

	 -->

		<div class="debiti_content_filters">


			<form:form commandName="visualizzaCommand" method="post"
				class="form-inline form-actions">

				<div class="row-fluid">
					<div class="span2">
						<label><spring:message
								code="mypivot.visualizza.identificativo.IUD" /></label><br />
						<form:input path="codice_iud" class="input-medium" id="codice_iud"
							name="codice_iud" type="text" placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.identificativo.IUV" /></label><br />
						<form:input path="codice_iuv" class="input-medium" id="codice_iuv"
							name="codice_iuv" type="text" placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.identificativo.IUR" /></label><br />
						<form:input path="identificativo_univoco_riscossione"
							class="input-medium" id="identificativo_univoco_riscossione"
							name="identificativo_univoco_riscossione" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.attestante" /></label><br />
						<form:input path="denominazione_attestante" class="input-medium"
							id="denominazione_attestante" name="denominazione_attestante"
							type="text" placeholder="" />
						<br />
					</div>

					<div class="span2_5">
						<label><spring:message
								code="mypivot.visualizza.pagatore.codice" /></label><br />
						<form:input path="codice_identificativo_univoco_pagatore"
							class="input-medium" id="codice_identificativo_univoco_pagatore"
							name="codice_identificativo_univoco_pagatore" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.pagatore.anagrafica" /></label><br />
						<form:input path="anagrafica_pagatore" class="input-medium"
							id="anagrafica_pagatore" name="anagrafica_pagatore" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.versante.codice" /></label><br />
						<form:input path="codice_identificativo_univoco_versante"
							class="input-medium" id="codice_identificativo_univoco_versante"
							name="codice_identificativo_univoco_versante" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.versante.anagrafica" /></label><br />
						<form:input path="anagrafica_versante" class="input-medium"
							id="anagrafica_versante" name="anagrafica_versante" type="text"
							placeholder="" />
						<br />
					</div>


					<div class="span2_5">
						<label><spring:message
								code="mypivot.visualizza.datiPagamento.causale" /></label><br />
						<form:input path="causale_versamento" class="input-medium"
							id="causale_versamento" name="causale_versamento" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.flussoRendicontazione" /></label><br />
						<form:input path="identificativo_flusso_rendicontazione"
							class="input-medium" id="identificativo_flusso_rendicontazione"
							name="identificativo_flusso_rendicontazione" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.regolamento" /></label><br />
						<form:input path="identificativo_univoco_regolamento"
							class="input-medium" id="identificativo_univoco_regolamento"
							name="identificativo_univoco_regolamento" type="text"
							placeholder="" />
						<br /> <label><spring:message
								code="mypivot.visualizza.tipoDovuto" />: </label><br />
						<form:select class="normalSelect" path="cod_tipo_dovuto">
							<form:option value="">
									TUTTI
							</form:option>
							<c:forEach var="ctd" items="${listaTipiDovutoPerEnte}">
								<form:option value="${ctd.codTipo}">
									<c:out value="${ctd.deTipo}"></c:out>
								</form:option>
							</c:forEach>
						</form:select>
						<br />
					</div>

					<div class="span3">
						<label><spring:message
								code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamentoDa" />: </label><br />
						<form:input path="data_ultimo_aggiornamento_da"
							id="data_ultimo_aggiornamento_da" type="text"
							name="data_ultimo_aggiornamento_da" class="input-medium" />
						<br /> <label><spring:message
								code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamentoA" />: </label><br />
						<form:input path="data_ultimo_aggiornamento_a"
							id="data_ultimo_aggiornamento_a" type="text"
							name="data_ultimo_aggiornamento_a" class="input-medium" />
						<br /> <label><spring:message
								code="mypivot.visualizza.datiPagamento.dataEsitoDa" />: </label><br />
						<form:input path="data_esito_singolo_pagamento_da"
							id="data_esito_singolo_pagamento_da" type="text"
							name="data_esito_singolo_pagamento_da" class="input-medium" />
						<br /> <label><spring:message
								code="mypivot.visualizza.datiPagamento.dataEsitoA" />: </label><br />
						<form:input path="data_esito_singolo_pagamento_a"
							id="data_esito_singolo_pagamento_a" type="text"
							name="data_esito_singolo_pagamento_a" class="input-medium" />
						<br /> <label><spring:message
								code="mypivot.visualizza.regolamento.dataDa" />: </label><br />
						<form:input path="data_regolamento_da" id="data_regolamento_da"
							type="text" name="data_regolamento_da" class="input-medium" />

						<br /> <label><spring:message
								code="mypivot.visualizza.regolamento.dataA" />: </label><br />
						<form:input path="data_regolamento_a" id="data_regolamento_a"
							type="text" name="data_regolamento_a" class="input-medium" />

					</div>

					<form:hidden path="page" />

					<div class="span2">
						<spring:message code="mypivot.pager.numElements" />
						<br />
						<form:select class="pagerSelect width140" itemLabel="pageSize"
							itemValue="pageSize" path="pageSize">
							<form:option value="5" />
							<form:option value="10" />
							<form:option value="20" />
						</form:select>
						<br />
						<button type="submit" class="btn btn-large cerca width140"
							value="visualizzaCommand">
							<spring:message code="mypivot.button.cerca" />
						</button>
						<c:if test="${visualizzaDtoPage.totalRecords > 0}">
							<br />
							<a href="<%=request.getContextPath()%>/protected/prenotaExport.html"
								type="button" class="btn btn-large cerca width120">Prenota Export</a>
						</c:if>
					</div>
				</div>

			</form:form>

		</div>

		<c:if test="${visualizzaDtoPage.totalRecords > 0}">

			<jsp:include page="messages.jsp"></jsp:include>

			<div class="debiti_table">
			
			
			   
			   
			   <div class="row-fluid mypay-custom row-border">
			  <div class="span12 ">
			    
			    <div class="row-fluid mypay-custom">
			      <div class="span8 text-center ricevutaHeader  row-border">
			      	<spring:message code="mypivot.visualizzaCompleta.ricevuta" />
			      </div>
			      <div class="span4 text-center rendicontazioneHeader  row-border">
			        <spring:message code="mypivot.visualizzaCompleta.rendicontazione" />
			      </div>
			    </div>
			
			
					<c:forEach var="visualizzaDto" items="${visualizzaDtoPage.list}">
							
								<div class="row-fluid mypay-custom  <c:if test="${visualizzaDto.exportRendicontazioneTO.codiceIud == 'n/a'}">colored</c:if>">
									<div class="span12   row-border">
								 		<div class="row-fluid mypay-custom">
									 		<div class="span8 ricevutaRow">
										 		<div class="row-fluid mypay-custom">
											 		<div class="span6">
												 		<div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.tipoDovuto" /> :</div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.tipoDovuto}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.identificativo.IUD" /> : </div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.codiceIud}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.identificativo.IUV" /> :</div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.codiceIuv}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.identificativo.IUR" /> :</div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.identificativoUnivocoRiscossione}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" > <spring:message code="mypivot.visualizza.datiPagamento.singoloImportoPagato" /> : </div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.singoloImportoPagato}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" > <spring:message code="mypivot.visualizza.datiPagamento.dataEsito" /> :</div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.deDataEsitoSingoloPagamento}" /></div>
												        </div>
											 		</div>
											 		<div class="span6">
												 		<div class="row-fluid mypay-custom">
												          <div class="span4  text-right-important"><spring:message code="mypivot.visualizza.attestante" /> : </div>
												          <div class="span8  text-left-important">
														       <c:choose>
																    <c:when test="${not empty visualizzaDto.exportRendicontazioneTO.codiceIdentificativoUnivocoAttestante}">
																        <c:out value="${visualizzaDto.exportRendicontazioneTO.denominazioneAttestante}" />
															          	<br />
															          	<c:out value="${visualizzaDto.exportRendicontazioneTO.codiceIdentificativoUnivocoAttestante}" />
																		<br />
																			<c:if test="${not empty visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoAttestante  }">
																				<div class="no_wrap_left">
																					(
																					<spring:message
																						code="mypivot.tipoIdentificativoUnivocoAttestante.${visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoAttestante}" />
																					)
																				</div>
																			</c:if> 
																    </c:when>    
																    <c:otherwise>
																        n/a
																    </c:otherwise>
																</c:choose>
													       
													          	
												          </div>
												        </div>
												        
												        <div class="row-fluid mypay-custom">
												          <div class="span4  text-right-important"><spring:message code="mypivot.visualizza.pagatore" /> : </div>
												          <div class="span8  text-left-important">
														       <c:choose>
																    <c:when test="${not empty visualizzaDto.exportRendicontazioneTO.codiceIdentificativoUnivocoPagatore}">
															          	<c:out value="${visualizzaDto.exportRendicontazioneTO.anagraficaPagatore}" />									          	
															          	<br />
															          	<c:out value="${visualizzaDto.exportRendicontazioneTO.codiceIdentificativoUnivocoPagatore}" />
																		<br />
																			<c:if
																				test="${((visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoPagatore == 'F') or (visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoPagatore == 'G'))}">
																				<small><div class="no_wrap">
																						(
																						<spring:message
																							code="mypivot.tipoIdentificativoUnivoco.${visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoPagatore}" />
																						)
																					</div> </small>
																			</c:if> 
																	</c:when>    
																    <c:otherwise>
																        n/a
																    </c:otherwise>
																</c:choose>
												          </div>
												        </div>  
												          <div class="row-fluid mypay-custom">
													          <div class="span4  text-right-important"><spring:message code="mypivot.visualizza.versante" /> : </div>
													          <div class="span8  text-left-important">
																  <c:choose>
																    <c:when test="${not empty visualizzaDto.exportRendicontazioneTO.codiceIdentificativoUnivocoVersante}">
															          	<c:out value="${visualizzaDto.exportRendicontazioneTO.anagraficaVersante}" />									          	
															          	<br />
															          	<c:out value="${visualizzaDto.exportRendicontazioneTO.codiceIdentificativoUnivocoVersante}" />
																		<br />
																			<c:if test="${((visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoVersante == 'F') or (visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoVersante == 'G'))}">
																			  <small><div class="no_wrap">
																					(
																					<spring:message
																						code="mypivot.tipoIdentificativoUnivoco.${visualizzaDto.exportRendicontazioneTO.tipoIdentificativoUnivocoVersante}" />
																					)
																				</div> </small>
																			</c:if> 
																	</c:when>    
																    <c:otherwise>
																        n/a
																    </c:otherwise>
																</c:choose>
													          </div>
												        </div>
											  		</div>
											  	</div>
											  	<div class="row-fluid mypay-custom">
												  	<div class="span12">
												          <div class="span2 text-right-important" > <spring:message code="mypivot.visualizza.datiPagamento.causale" /> : </div>
												          <div class="span10 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.causaleVersamento}" /></div>								  		
											  		</div>
										  		</div>
										  	</div>
										  	<div class="span4 rendicontazioneRow">
										  		<div class="row-fluid mypay-custom">
											 		<div class="span12">
												 		<div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.flussoRendicontazione.identificativo" /> : </div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.identificativoFlussoRendicontazione}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.flussoRendicontazione.dataOra" /> : </div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.dataOraFlussoRendicontazione}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.regolamento.identificativo" /> : </div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.identificativoUnivocoRegolamento}" /></div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.regolamento.dataRegolamento" /> : </div>
												          <div class="span8 text-left-important">
												          	<c:out value="${visualizzaDto.exportRendicontazioneTO.deDataRegolamento}" />
												          	<c:if test="${visualizzaDto.exportRendicontazioneTO.identificativoFlussoRendicontazione== 'n/a'}">
																<small><div class="no_wrap">
																		(
																		<spring:message
																			code="mypivot.visualizza.flussoRendicontazione.dataRegolamento.presunta" />
																		)
																	</div> </small>
															</c:if>
												          </div>
												        </div>
												        <div class="row-fluid mypay-custom">
												          <div class="span4 text-right-important" ><spring:message code="mypivot.visualizza.regolamento.importoTotale" /> : </div>
												          <div class="span8 text-left-important"><c:out value="${visualizzaDto.exportRendicontazioneTO.importoTotalePagamenti}" /></div>
												        </div>
												    </div>
												</div>
										  	</div>

										</div>
								  	</div>
								</div>
					</c:forEach>										
			
			
			
			
			
			
			
			
			
			
			
			

				
				
				
				
				
				
			</div>



		<div class="row-fluid mypay-custom row-border">
			<div class="span12">
				<div class="pagination pagination-left">
					<spring:message code="mypivot.pager.pagina" />
					<c:out value="${visualizzaDtoPage.page}" />
					<spring:message code="mypivot.pager.di" />
					<c:out value="${visualizzaDtoPage.totalPages}" />
					-
					<spring:message code="mypivot.pager.elementiDa" />
					<c:out
						value="${(visualizzaDtoPage.page-1) * visualizzaDtoPage.pageSize + 1}" />
					<spring:message code="mypivot.pager.a" />
					<c:out
						value="${(visualizzaDtoPage.page-1) * visualizzaDtoPage.pageSize + fn:length(visualizzaDtoPage.list)}" />

				</div>

				<div>
					<div class="pagination pagination-right">

						<ul>
							<c:if test="${visualizzaDtoPage.previousPage}">
								<li><a
									href="<%=request.getContextPath()%>/protected/visualizza.html?pg=1&pgSize=${visualizzaDtoPage.pageSize}">
										<span><spring:message code="mypivot.pager.prima" /></span>
								</a></li>
							</c:if>

							<c:if test="${visualizzaDtoPage.previousPage}">
								<li><a
									href="<%=request.getContextPath()%>/protected/visualizza.html?pg=${visualizzaDtoPage.page-1}&pgSize=${visualizzaDtoPage.pageSize}">
										<span>«</span>
								</a></li>
							</c:if>

							<c:if test="${visualizzaDtoPage.previousPage}">
								<li><a
									href="<%=request.getContextPath()%>/protected/visualizza.html?pg=${visualizzaDtoPage.page-1}&pgSize=${visualizzaDtoPage.pageSize}">
										<span><c:out value="${visualizzaDtoPage.page-1}"></c:out></span>
								</a></li>
							</c:if>

							<li class="disabled"><span><c:out
										value="${visualizzaDtoPage.page}"></c:out></span></li>

							<c:if test="${visualizzaDtoPage.nextPage}">
								<li><a
									href="<%=request.getContextPath()%>/protected/visualizza.html?pg=${visualizzaDtoPage.page+1}&pgSize=${visualizzaDtoPage.pageSize}">
										<span><c:out value="${visualizzaDtoPage.page+1}"></c:out></span>
								</a></li>
							</c:if>

							<c:if test="${visualizzaDtoPage.nextPage}">
								<li><a
									href="<%=request.getContextPath()%>/protected/visualizza.html?pg=${visualizzaDtoPage.page+1}&pgSize=${visualizzaDtoPage.pageSize}">
										<span>»</span>
								</a></li>
							</c:if>

							<c:if test="${visualizzaDtoPage.nextPage}">
								<li><a
									href="<%=request.getContextPath()%>/protected/visualizza.html?pg=${visualizzaDtoPage.totalPages}&pgSize=${visualizzaDtoPage.pageSize}">
										<span><spring:message code="mypivot.pager.ultima" /></span>
								</a></li>
							</c:if>


						</ul>

					</div>
				</div>
			</div>
			</div>
		</c:if>
		<c:if test="${visualizzaDtoPage.totalRecords == 0}">
			<p class="muted text-center">
				<spring:message code="mypivot.visualizza.nessunDato" />
			</p>
		</c:if>



	</div>

</div>