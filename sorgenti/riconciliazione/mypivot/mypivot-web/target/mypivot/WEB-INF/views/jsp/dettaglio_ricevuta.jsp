<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>


				<div class="dettaglio ricevuta  mypay-custom small-text"> 
					<div class="testata"><h2><spring:message code="mypivot.dettaglio.ricevuta.title"/></h2></div>
					<div class="corpo form-horizontal"> 
						<div class="row-fluid">
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.tipodovuto"/></label>
							</div>
							<div class="span3 data-column">
								<label>${dettaglio.ricevuta.tipoDovuto}</label>
							</div>
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.iud"/></label>
							</div>
							<div class="span3 data-column">
								<label>${dettaglio.ricevuta.codiceIud}</label>
							</div>
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.iuv"/></label>
							</div>
							<div class="span3 data-column">
								<label>${dettaglio.ricevuta.codiceIuv}</label>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.iur"/></label>
							</div>
							<div class="span3 data-column">
								<label>${dettaglio.ricevuta.codiceIur}</label>
							</div>
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.importo"/></label>
							</div>
							<div class="span3 data-column">
								<label>${dettaglio.ricevuta.singoloImportoPagato}</label>
							</div>
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.dataesito"/></label>
							</div>
							<div class="span3 data-column">
								<label>${dettaglio.ricevuta.deDataEsitoSingoloPagamento}</label>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span1 label-column">
								<label><spring:message code="mypivot.dettaglio.ricevuta.attestante"/></label>
							</div>
							<div class="span3 data-column">
								<c:if test="${empty dettaglio.ricevuta.attestante}">
									N/A
								</c:if>
								<c:if test="${not empty dettaglio.ricevuta.attestante}">
									<div class="multirow">
										<label>${dettaglio.ricevuta.attestante.anagrafica}</label>
									</div>
									<div class="multirow">
										<label>${dettaglio.ricevuta.attestante.codice}</label>
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
								<c:if test="${empty dettaglio.ricevuta.pagatore}">
									N/A
								</c:if>
								<c:if test="${not empty dettaglio.ricevuta.pagatore}">
									<div class="multirow">
										<label>${dettaglio.ricevuta.pagatore.anagrafica}</label>
									</div>
									<div class="multirow">
										<label>${dettaglio.ricevuta.pagatore.codice}</label>
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
								<c:if test="${empty dettaglio.ricevuta.versante}">
									N/A
								</c:if>
								<c:if test="${not empty dettaglio.ricevuta.versante}">
									<div class="multirow">
										<label>${dettaglio.ricevuta.versante.anagrafica}</label>
									</div>
									<div class="multirow">
										<label>${dettaglio.ricevuta.versante.codice}</label>
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
								<label>${dettaglio.ricevuta.causaleVersamento}</label>
							</div>
							<div class="span8">&nbsp;</div>
						</div>							
					</div>			
				</div>			
