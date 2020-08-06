<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="dettaglio ricevuta  mypay-custom small-text"> 
	<div class="testata"><h2><spring:message code="mypivot.dettaglio.ricevuta.iuv"/> <c:out value="${ricevutaRendicontazione.codiceIuv}"/></h2></div>
	<div class="corpo form-horizontal"> 
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.tipodovuto"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.tipoDovuto}"/></label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.iud"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.codiceIud}"/></label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.iuv"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.codiceIuv}"/></label>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.iur"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.codiceIur}"/></label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.importo"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.singoloImportoPagato}"/></label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.dataesito"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.deDataEsitoSingoloPagamento}"/></label>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.attestante"/></label>
			</div>
			<div class="span3 data-column">
				<c:if test="${empty ricevutaRendicontazione.attestante.anagrafica}">
					N/A
				</c:if>
				<c:if test="${not empty ricevutaRendicontazione.attestante.anagrafica}">
					<div class="multirow">
						<label><c:out value="${ricevutaRendicontazione.attestante.anagrafica}"/></label>
					</div>
					<div class="multirow">
						<label><c:out value="${ricevutaRendicontazione.attestante.codice}"/></label>
					</div>
					<div class="multirow">
						<label>(<spring:message code="mypivot.tipoIdentificativoUnivoco.${dettaglio.ricevuta.attestante.tipoIdentificativo}" text = "N/A"/>)</label>
					</div>
				</c:if>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.pagatore"/></label>
			</div>
			<div class="span3 data-column">
				<c:if test="${empty ricevutaRendicontazione.pagatore.anagrafica}">
					N/A
				</c:if>
				<c:if test="${not empty ricevutaRendicontazione.pagatore.anagrafica}">
					<div class="multirow">
						<label><c:out value="${ricevutaRendicontazione.pagatore.anagrafica}"/></label>
					</div>
					<div class="multirow">
						<label><c:out value="${ricevutaRendicontazione.pagatore.codice}"/></label>
					</div>
					<div class="multirow">
						<label>(<spring:message code="mypivot.tipoIdentificativoUnivoco.${dettaglio.ricevuta.pagatore.tipoIdentificativo}" text = "N/A"/>)</label>
					</div>
				</c:if>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.versante"/></label>
			</div>
			<div class="span3 data-column">
				<c:if test="${empty ricevutaRendicontazione.versante.anagrafica}">
					N/A
				</c:if>
				<c:if test="${not empty ricevutaRendicontazione.versante.anagrafica}">
					<div class="multirow">
						<label><c:out value="${ricevutaRendicontazione.versante.anagrafica}"/></label>
					</div>
					<div class="multirow">
						<label><c:out value="${ricevutaRendicontazione.versante.codice}"/></label>
					</div>
					<div class="multirow">
						<label>(<spring:message code="mypivot.tipoIdentificativoUnivoco.${dettaglio.ricevuta.versante.tipoIdentificativo}" text = "N/A"/>)</label>
					</div>
				</c:if>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.ricevuta.causale"/></label>
			</div>
			<div class="span3 data-column wrapfix">
				<label><c:out value="${ricevutaRendicontazione.causaleVersamento}"/></label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.rendicontazione.ricevuta.indicedatisingolopagamento"/></label>
			</div>
			<div class="span3 data-column">
				<label><c:out value="${ricevutaRendicontazione.indiceDatiSingoloPagamento}"/></label>
			</div>
			<div class="span4">&nbsp;</div>
		</div>							
	</div>			
</div>			
