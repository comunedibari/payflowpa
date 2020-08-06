<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>

<div class="dettaglio tesoreria mypay-custom small-text"> 
	<div class="testata"><h2><spring:message code="mypivot.dettaglio.tesoreria.title"/></h2></div>
	<div class="corpo form-horizontal"> 
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.tesoreria.conto"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.tesoreria.codConto}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.tesoreria.datavaluta"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.tesoreria.deDataValuta}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.tesoreria.datacontabile"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.tesoreria.deDataContabile}</label>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.tesoreria.importotesoreria"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.tesoreria.deImporto}</label>
			</div>
			<div class="span1 label-column">
				<label><spring:message code="mypivot.dettaglio.tesoreria.codOR1"/></label>
			</div>
			<div class="span3 data-column">
				<label>${dettaglio.tesoreria.codOr1}</label>
			</div>
			<c:if test="${fn:trim(dettaglio.tesoreria.deDataRicezione) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.deDataRicezione" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.deDataRicezione)}" /></label>
				</div>
			</c:if>
		</div>
		<div class="row-fluid">
			<c:if test="${fn:trim(dettaglio.tesoreria.deAnnoBolletta) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.deAnnoBolletta" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.deAnnoBolletta)}" /></label>
				</div>
			</c:if>
			
			<c:if test="${fn:trim(dettaglio.tesoreria.codBolletta) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.codBolletta" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.codBolletta)}" /></label>
				</div>
			</c:if>
			
			<c:if test="${fn:trim(dettaglio.tesoreria.codIdDominio) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.codIdDominio" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.codIdDominio)}" /></label>
				</div>
			</c:if>
		</div>
		<div class="row-fluid">
			<c:if test="${fn:trim(dettaglio.tesoreria.deAnnoDocumento) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.deAnnoDocumento" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.deAnnoDocumento)}" /></label>
				</div>
			</c:if>
			
			<c:if test="${fn:trim(dettaglio.tesoreria.codDocumento) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.codDocumento" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.codDocumento)}" /></label>
				</div>
			</c:if>
			
			<c:if test="${fn:trim(dettaglio.tesoreria.deAnnoProvvisorio) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.deAnnoProvvisorio" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.deAnnoProvvisorio)}" /></label>
				</div>
			</c:if>
		</div>
		<div class="row-fluid">
			<c:if test="${fn:trim(dettaglio.tesoreria.codProvvisorio) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.codProvvisorio" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.codProvvisorio)}" /></label>
				</div>
			</c:if>
			<c:if test="${fn:trim(dettaglio.tesoreria.deCausaleT) ne 'n/a'}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.causaleRiversamento" /></label>
				</div>
				<div class="span7 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.deCausaleT)}" /></label>
				</div>
			</c:if>
		</div>
		

		<div class="row-fluid">
			<c:if test="${dettaglio.tesoreria.dtEffettivaSospeso ne null}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.dtEffettivaSospeso" /></label>
				</div>
				<div class="span3 data-column">		
					<label><fmt:formatDate pattern="dd/MM/yyyy" value="${dettaglio.tesoreria.dtEffettivaSospeso}" /></label>
				</div>
			</c:if>
			<c:if test="${fn:trim(dettaglio.tesoreria.codiceGestionaleProvvisorio) ne '' and fn:trim(dettaglio.tesoreria.codiceGestionaleProvvisorio) ne null}">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.visualizza.codiceGestionaleProvvisorio" /></label>
				</div>
				<div class="span3 data-column">
					<label><c:out value="${fn:trim(dettaglio.tesoreria.codiceGestionaleProvvisorio)}" /></label>
				</div>
			</c:if>
		</div>		
		
		
	</div>
	
	
</div>
