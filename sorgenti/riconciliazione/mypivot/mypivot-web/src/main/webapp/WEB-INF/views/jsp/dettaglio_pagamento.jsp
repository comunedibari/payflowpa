<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>


<div class="dettaglio pagamento  mypay-custom small-text"> 
	<div class="testata"><h2><spring:message code="mypivot.dettaglio.pagamento.title"/></h2></div>
	<div class="corpo form-horizontal"> 
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.tipodovuto"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.pagamento.tipoDovuto}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.iud"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.pagamento.codiceIud}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.importo"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.pagamento.singoloImportoPagato}</label>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.dataesito"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.pagamento.deDataEsecuzionePagamento}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.causale"/></label>
			</div>
			<div class="span3 data-column wrapfix">
				<label>${dettaglio.pagamento.causaleVersamento}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.pagatore"/></label>
			</div>
			<div class="span3 data-column">
				<c:if test="${empty  dettaglio.pagamento.pagatore}">
					N/A
				</c:if>
				<c:if test="${not empty dettaglio.pagamento.pagatore}">
					<div class="multirow">
						<label>${dettaglio.pagamento.pagatore.anagrafica}</label>
					</div>
					<div class="multirow">
						<label>${dettaglio.pagamento.pagatore.codice}</label>
					</div>
					<div class="multirow">
						<label>(<spring:message code="mypivot.tipoIdentificativoUnivoco.${dettaglio.pagamento.pagatore.tipoIdentificativo}" text = "N/A"/>)</label>
					</div>
				</c:if>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.pagamento.datiSpecificiRiscossione"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.pagamento.datiSpecificiRiscossione}</label>
			</div>
			<div class="span8">&nbsp;</div>
		</div>
	</div>			
</div>			
