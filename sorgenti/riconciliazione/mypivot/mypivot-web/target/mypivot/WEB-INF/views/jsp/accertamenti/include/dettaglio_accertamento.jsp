<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 
	
	TEMPLATE HTML DETTAGLIO ACCERTAMENTO
	
 -->
 
<%
	/* Inserisco al contesto pagina gli stati che descrivo l'evoluzione dell'accertamento */
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_INSERITO", Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO);
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_CHIUSO", Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO);
	pageContext.setAttribute("COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO", Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO);
%>

<div class="row-fluid">
	<div class="dettaglio accertamento <c:out value="${accertamentoDto.stato.codStato}"/> mypay-custom small-text"> 
		
		<!-- LABEL TITLE -->
		<div class="dettaglio_testata_accertamento">
			<h2>
				<label class="pull-left">
					<spring:message code="mypivot.accertamenti.label.accertamento"/>
				</label>
				
				<!-- STATO -->
				<label class="labelStato pull-right">
					<spring:message code="mypivot.accertamenti.label.stato"/>:
					
					<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_INSERITO}">
						<span class="label label-warning notsh"> <c:out value="${accertamentoDto.stato.codStato}"/></span>
					</c:if>
					
					<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_CHIUSO}">
						<span class="label label-success notsh"> <c:out value="${accertamentoDto.stato.codStato}"/></span>
					</c:if>
					
					<c:if test="${accertamentoDto.stato.codStato==COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO}">
						<span class="label label-inverse notsh"> <c:out value="${accertamentoDto.stato.codStato}"/></span>
					</c:if>
				</label>
			</h2>
		</div><!-- /.dettaglio_testata_accertamento -->

		
		<div class="corpo form-horizontal"> 
			
			<!-- NOME ACCERTAMENTO -->
			<div class="row-fluid">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.accertamenti.label.nomeAccertamento"/></label>
				</div>
				<div class="span11 data-column">
					<div class="multirow">
						<label class="wrapfix"><c:out value="${accertamentoDto.deNomeAccertamento}"/></label>
					</div>
				</div>
			</div>
				
			<div class="row-fluid">
			
				<!-- TIPO DOVUTO -->
				<div class="span1 label-column">
					<label><spring:message code="mypivot.accertamenti.label.tipoDovuto"/></label>
				</div>
				<div class="span3 data-column">
					<label class="wrapfix"><c:out value="${accertamentoDto.enteTipoDovuto.deTipo}"/></label>
				</div>
				
				
				<!-- UTENTE -->
				<div class="span1 label-column">
					<label><spring:message code="mypivot.accertamenti.label.utente"/></label>
				</div>
				<div class="span3 data-column">
					<div class="multirow">
						<label class="wrapfix"><c:out value="${accertamentoDto.utente.deFirstname}" /> <c:out value="${accertamentoDto.utente.deLastname}" /></label>
					</div>
					<div class="multirow">
						<label class="wrapfix">${accertamentoDto.utente.codCodiceFiscaleUtente}</label>
					</div>
				</div>
	
				
				<!-- AGGIORNATO IL -->
				<div class="span1 label-column">
					<label><spring:message code="mypivot.accertamenti.label.aggiornatoIl"/></label>
				</div>
				<div class="span3 data-column">
					<label class="wrapfix"><c:out value="${accertamentoDto.dtUltimaModifica}"/></label>
				</div>
				
			</div><!-- /.row-fluid -->
		</div><!-- /.form-horizontal -->
		 
	</div><!-- /.dettaglio -->
</div><!-- /.row-fluid -->
	