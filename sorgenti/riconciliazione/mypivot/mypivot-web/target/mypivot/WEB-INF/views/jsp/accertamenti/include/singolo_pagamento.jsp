<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="detailPagamento relative clearfix divider">
	<img class="arrow" src="<%=request.getContextPath() %>/images/arrow.png" />

	<!-- Lista Capitoli Vuota: mostro messaggio di errore -->
	<c:if test="${not empty emptyCapitoli and emptyCapitoli}">
		<div class="warn mtop10">
			<p>
				<i class="fa fa-exclamation-triangle"></i> <spring:message code="mypivot.accertamenti.errore.emptyCapitoli"/>
			</p>
		</div>
	</c:if>
					
	<!-- IMPORTO TOTALE ASSEGNATO AI CAPITOLI -->
	<c:if test="${not empty sceltaCommand.multiple and !sceltaCommand.multiple}">							
		<div class="mypay-custom priceP small-text btop form-inline nborderb">
			<div class="span9 hidden-xs mtop10"></div>
			<div class="span3 mtop10">
				<div class="span12 nomImp nop">
					<span class="priceParziale green">${sceltaCommand.formatTotImportoCapitoli}
						<button id="tooltipMulti" type="button" class="info" title="<spring:message code='mypivot.accertamenti.tooltip.totaleImportoCapitoli'/>">
							<i class="fa fa-info-circle" aria-hidden="true"></i>
						</button>				
					</span>
				</div>
				
				<div class="span12 nomImp nop">
					<span class="priceParziale small red">${sceltaCommand.formatTotImportoMancante}
						<button id="tooltipMulti" type="button" class="info" title="<spring:message code='mypivot.accertamenti.tooltip.totaleImportoMancante'/>">
							<i class="fa fa-info-circle" aria-hidden="true"></i>
						</button>				
					</span>
				</div>
			</div><!-- /.span3 -->
		</div><!-- /.priceP -->
	</c:if>


	<!-- FORM -->
	<c:if test="${sceltaCommand.enableFormAdd}">
		<div class="mypay-custom small-text bbtottom form-inline nborderb">
			
			<!-- UFFICIO -->
			<div class="span2 xs-span7 mtop10 control-label">
				<label>
					<span class="required">* </span><spring:message code="mypivot.accertamenti.label.codiceUfficio"/>
				</label>
				
				<form:select class="input-block-level" path="codUfficio" onchange="javascript:submitFormRicerca('CH_SEL_UFFICIO')">
					<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
					<form:options items="${ufficiList}" itemValue="codUfficio" itemLabel="deUfficio" />
				</form:select>
				
				<form:errors path="codUfficio" cssClass="error" />
			</div>
			
			<!-- ANNO ESERCIZIO -->
			<div class="span2 xs-span7 mtop10 control-label">
				<label>
					<span class="required">* </span><spring:message code="mypivot.accertamenti.label.annoEsercizio" />
				</label>
				
				<form:select class="input-block-level" path="annoEsercizio" disabled="${empty sceltaCommand.codUfficio}" onchange="javascript:submitFormRicerca('CH_SEL_ANNO')">
					<c:if test="${empty sceltaCommand.codUfficio}">
						<form:option value=""><spring:message code="mypivot.accertamenti.select.selezionaUfficio"></spring:message></form:option>
					</c:if>
					
					<c:if test="${not empty sceltaCommand.codUfficio}">
						<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
					</c:if>
					
					<form:options items="${yearsList}" />
				</form:select>
				
				<form:errors path="annoEsercizio" cssClass="error" />
			</div>
			
			<!-- CAPITOLI -->
			<div class="span3 xs-span7 mtop10 control-label">
				<label>
					<span class="required">* </span><spring:message code="mypivot.accertamenti.label.codiceCapitolo" />
				</label>
				
				<c:if test="${not empty emptyCapitoli && !emptyCapitoli}">
					<form:select class="input-block-level" path="codCapitolo" disabled="${empty sceltaCommand.annoEsercizio}" onchange="javascript:submitFormRicerca('CH_SEL_CAPITOLO')">
						<c:if test="${empty sceltaCommand.annoEsercizio}">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.selezionaAnnoEsercizio"></spring:message></form:option>
						</c:if>
						
						<c:if test="${not empty sceltaCommand.annoEsercizio}">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
						</c:if>
						
						<form:options items="${capitoliList}" itemValue="codCapitolo" itemLabel="deCapitolo" />
					</form:select>
					
					<form:errors path="codCapitolo" cssClass="error" />
				</c:if>
				
				<c:if test="${not empty emptyCapitoli && emptyCapitoli}">
					<label class="emptyerror width100"><spring:message code="mypivot.accertamenti.errore.emptyCapitoli.short"/></label>
				</c:if>
			</div>
			
			<!-- ACCERTAMENTO -->
			<div class="span2 xs-span7 mtop10">
				<label>
					<spring:message code="mypivot.accertamenti.label.codiceAccertamento" />
				</label>
				
				<c:if test="${empty sceltaCommand.codCapitolo or (not empty sceltaCommand.codCapitolo and not empty emptyAccertamenti && !emptyAccertamenti)}">
					<form:select class="input-block-level" path="codAccertamento" disabled="${empty sceltaCommand.codCapitolo}">
						<c:if test="${empty sceltaCommand.codCapitolo}">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.selezionaCapitolo"></spring:message></form:option>
						</c:if>
						
						<c:if test="${not empty sceltaCommand.codCapitolo}">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
						</c:if>
						
						<form:options items="${accertamentiList}" itemValue="codAccertamento" itemLabel="deAccertamento" />
					</form:select>
				</c:if>
				
				<c:if test="${not empty sceltaCommand.codCapitolo and not empty emptyAccertamenti and emptyAccertamenti}">
					<label class="emptyinfo width100"><spring:message code="mypivot.accertamenti.errore.emptyAccertamenti.short"/></label>
				</c:if>
			</div>
				
			<!-- IMPORTO -->
			<div class="span3 xs-span7 btnRight mtop10 control-label">
				<label>
					<span class="required">* </span><spring:message code="mypivot.accertamenti.label.importo" />
					<span class="euro">&#8364;</span>
				</label>
				
				<form:input path="importo" class="input-block-level" autocomplete="false" style="padding-left: 16px;" />
				
				<form:errors path="importo" cssClass="error" />
				
				<!-- BTN AGGIUNGI -->
				<button class="bttnAdd" type="button" onclick="javascript:submitFormRicerca('ADD_ITEM')"><i class="fa fa-plus"></i></button>
			</div>
			
			<!-- MESSAGE ERROR: righe duplicate, le combo sono valorizzate tutte allo stesso modo -->
			<div class="span11 xs-span7 control-label error-duplicate">
				<form:errors path="duplicateEntry" cssClass="error" />
			</div>
			
		</div><!-- /.mypay-custom -->
	</c:if>
	
	
	<!-- ELENCO CAPITOLI -->
	<div class="mypay-custom small-text bbtottom form-inline nborderb">
		<c:forEach var="rowDto" items="${sceltaCommand.ufficiTO}" varStatus="status">
	
			<!-- Input flussi da risottomettere al submit -->
			<input name="ufficiTO[${status.index}].codUfficio" 		value="${rowDto.codUfficio}"      type="hidden" />
			<input name="ufficiTO[${status.index}].deUfficio" 		value="${rowDto.deUfficio}" 	  type="hidden" />
			<input name="ufficiTO[${status.index}].codCapitolo" 	value="${rowDto.codCapitolo}" 	  type="hidden" />
			<input name="ufficiTO[${status.index}].deCapitolo" 		value="${rowDto.deCapitolo}" 	  type="hidden" />
			<input name="ufficiTO[${status.index}].deAnnoEsercizio" value="${rowDto.deAnnoEsercizio}" type="hidden" />
			<input name="ufficiTO[${status.index}].codAccertamento" value="${rowDto.codAccertamento}" type="hidden" />
			<input name="ufficiTO[${status.index}].deAccertamento" 	value="${rowDto.deAccertamento}"  type="hidden" />
			<input name="ufficiTO[${status.index}].numImporto" 		value="${rowDto.numImporto}" 	  type="hidden" />
		
			<div class="span12 nomImp textBreak">
				<!-- DESC UFFICIO -->
				<div class="span2 xs-span7">
					<label class="hidden"><spring:message code="mypivot.accertamenti.label.codiceUfficio" /></label>
					<p class="riepilogo">${rowDto.deUfficio}</p>
				</div>
				
				<!-- ANNO ESERCIZIO -->
				<div class="span2 xs-span7">
					<label class="hidden"><spring:message code="mypivot.accertamenti.label.annoEsercizio" /></label>
					<p class="riepilogo">${rowDto.deAnnoEsercizio}</p>
				</div>
				
				<!-- DESC CAPITOLO -->
				<div class="span3 xs-span7">
					<label class="hidden"><spring:message code="mypivot.accertamenti.label.codiceCapitolo" /></label>
					<p class="riepilogo">${rowDto.deCapitolo}</p>
				</div>
				
				<!-- DESC ACCERTAMENTO -->
				<div class="span2 xs-span7">
					<label class="hidden"><spring:message code="mypivot.accertamenti.label.codiceAccertamento" /></label>
					<p class="riepilogo">${rowDto.deAccertamento}</p>
				</div>
				
				<!-- IMPORTO -->
				<div class="span3 xs-span7 btnRight">
					<label class="hidden"><spring:message code="mypivot.accertamenti.label.importo" /></label>
					<p class="riepilogo">&#8364; ${rowDto.numImporto}</p>
					
					<!-- BTN RIMUOVI -->
					<button class="bttnRemove" type="button" onclick="javascript:submitFormRicerca('DEL_ITEM','${status.index}')"><i class="fa fa-close"></i></button>
				</div>
			</div>
		</c:forEach>
	</div>
</div>