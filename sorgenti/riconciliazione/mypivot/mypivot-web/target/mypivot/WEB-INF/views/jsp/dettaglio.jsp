<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@page import="java.net.URLEncoder"%>

<div>
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.visualizzaCompleta.title" /> / <c:if test="${isRiconciliazione}">
				<spring:message code="mypivot.visualizzaCompleta.title.riconciliazione" />
			</c:if>
			<c:if test="${isAnomalie}">
				<spring:message code="mypivot.visualizzaCompleta.title.anomalie" />
			</c:if> / <spring:message code="mypivot.classificazioni.${dettaglio.classificazioneCompletezza}" /> / <span class="txt-titolo"><spring:message code="mypivot.dettaglio.title" /></span>
		</h1>
	</div>
	
	<div class="row-fluid">
		<jsp:include page="messages.jsp"></jsp:include>
	</div>
	<div class="row-fluid">
		<div class="divPadding">
			<c:if test="${dettaglio.tesoreria!=null and segnalazioneCommand.classificazioneCompletezza eq 'IUF_TES_DIV_IMP'}">
				<tiles:insertAttribute name="dettaglio_tesoreria" />
			</c:if>
			<c:if test="${dettaglio.pagamento!=null}">
				<tiles:insertAttribute name="dettaglio_pagamento" />
			</c:if>			
			<c:if test="${dettaglio.ricevuta!=null}">
				<tiles:insertAttribute name="dettaglio_ricevuta" />
			</c:if>
			<c:if test="${dettaglio.rendicontazione!=null}">
				<tiles:insertAttribute name="dettaglio_rendicontazione" />
			</c:if>			
			<c:if test="${dettaglio.tesoreria!=null and not (segnalazioneCommand.classificazioneCompletezza eq 'IUF_TES_DIV_IMP')}">
				<tiles:insertAttribute name="dettaglio_tesoreria" />
			</c:if>
			<tiles:insertAttribute name="dettaglio_segnalazione" />
		</div>	
	</div>
</div>
