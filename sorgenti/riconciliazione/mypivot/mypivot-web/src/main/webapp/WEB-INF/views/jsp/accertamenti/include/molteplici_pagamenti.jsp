<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="detailPagamento relative clearfix">
	<img class="arrow" src="<%=request.getContextPath() %>/images/arrow.png" />

	<!-- Lista Capitoli Vuota: mostro messaggio di errore -->
	<c:if test="${not empty emptyCapitoli and emptyCapitoli}">
		<div class="warn mtop10" style="width: 95%!important;">
			<p>
				<i class="fa fa-exclamation-triangle"></i> <spring:message code="mypivot.accertamenti.errore.emptyCapitoli"/>
			</p>
		</div>
	</c:if>
	
	<div class="mypay-custom inset small-text bbtottom form-inline nborderb">
		
		<!-- UFFICIO -->
		<div class="span8 xs-span7 mtop10 control-label">
			<label class="span4 bigText">
				<span class="required">* </span><spring:message code="mypivot.accertamenti.label.codiceUfficio"/>
			</label>
			
			<form:select class="input-block-level span8" path="codUfficio" onchange="javascript:submitFormRicerca('CH_SEL_UFFICIO')">
				<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
				<form:options items="${ufficiList}" itemValue="codUfficio" itemLabel="deUfficio" />
			</form:select>
		</div>
		
		<!-- ANNO ESERCIZIO -->
		<div class="span8 xs-span7 mtop10 control-label">
			<label class="span4 bigText">
				<span class="required">* </span><spring:message code="mypivot.accertamenti.label.annoEsercizio" />
			</label>
			
			<form:select class="input-block-level span8" path="annoEsercizio" disabled="${empty sceltaCommand.codUfficio}" onchange="javascript:submitFormRicerca('CH_SEL_ANNO')">
				<c:if test="${empty sceltaCommand.codUfficio}">
					<form:option value=""><spring:message code="mypivot.accertamenti.select.selezionaUfficio"></spring:message></form:option>
				</c:if>
				
				<c:if test="${not empty sceltaCommand.codUfficio}">
					<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
				</c:if>
				
				<form:options items="${yearsList}" />
			</form:select>
		</div>
		
		<!-- CAPITOLO -->
		<div class="span8 xs-span7 mtop10 control-label">
			<label class="span4 bigText">
				<span class="required">* </span><spring:message code="mypivot.accertamenti.label.codiceCapitolo" />
			</label>
			
			<c:if test="${not empty emptyCapitoli && !emptyCapitoli}">
				<form:select class="input-block-level span8" path="codCapitolo" disabled="${empty sceltaCommand.annoEsercizio}" onchange="javascript:submitFormRicerca('CH_SEL_CAPITOLO')">
					<c:if test="${empty sceltaCommand.annoEsercizio}">
						<form:option value=""><spring:message code="mypivot.accertamenti.select.selezionaAnnoEsercizio"></spring:message></form:option>
					</c:if>
					
					<c:if test="${not empty sceltaCommand.annoEsercizio}">
						<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
					</c:if>
					
					<form:options items="${capitoliList}" itemValue="codCapitolo" itemLabel="deCapitolo" />
				</form:select>
			</c:if>
			
			<c:if test="${not empty emptyCapitoli && emptyCapitoli}">
				<label class="span8 emptyerror"><spring:message code="mypivot.accertamenti.errore.emptyCapitoli.short"/></label>
			</c:if>
		</div>
		
		<!-- ACCERTAMENTO -->
		<div class="span8 xs-span7 mtop10">
			<label class="span4 bigText">
				<span class="required">&nbsp;&nbsp;</span><spring:message code="mypivot.accertamenti.label.codiceAccertamento" />
			</label>
			
			<c:if test="${empty sceltaCommand.codCapitolo or (not empty sceltaCommand.codCapitolo and not empty emptyAccertamenti && !emptyAccertamenti)}">
				<form:select class="input-block-level span8" path="codAccertamento" disabled="${empty sceltaCommand.codCapitolo}">
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
				<label class="span8 emptyinfo"><spring:message code="mypivot.accertamenti.errore.emptyAccertamenti.short"/></label>
			</c:if>
		</div>
		
	</div>
</div>