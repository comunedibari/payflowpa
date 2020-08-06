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


<c:url var="url" value="/protected/aggiungiSegnalazione.html" />
<form:form action="${url}" modelAttribute="segnalazioneCommand" id="aggiungiSegnalazioneForm" method="post" class="form-horizontal">
	<form:hidden path="classificazioneCompletezza" name="classificazioneCompletezza" id="classificazioneCompletezza"/>
	<form:hidden path="idSegnalazione" name="idSegnalazione" id="idSegnalazione"/>
	<form:hidden path="codIud" name="codIud" id="codIud"/>
	<form:hidden path="codIuv" name="codIuv" id="codIuv"/>
	<form:hidden path="codIuf" name="codIuf" id="codIuf"/>
	<form:hidden path="rendicontazione" name="rendicontazione"/>
	<form:hidden path="tipoVisualizzazione" name="tipoVisualizzazione"/>
	<c:if test="${not empty segnalazioneCommand.idSegnalazione}">
		<div class="dettaglio segnalazione mypay-custom small-text"> 
			<div class="testata"><h2><spring:message code="mypivot.dettaglio.segnalazione.title"/></h2></div>
			<div class="corpo form-horizontal"> 
				<div class="row-fluid form-row">
					<div class="span1 label-column">
						<label for="nota"><spring:message code="mypivot.dettaglio.segnalazione.nota"/></label>
					</div>
					<div class="span11 data-column">
						<c:out value="${segnalazioneCommand.deNota}"/>
					</div>
				</div>

				<div class="row-fluid form-row">
					<div class="span1 label-column">
						<label for="nascosto"><spring:message code="mypivot.dettaglio.segnalazione.nascosto"/></label>
					</div>
					<div class="span3 data-column">
						<form:checkbox path="flgNascosto" disabled="true"/>
					</div>
					<div class="span8">&nbsp;</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${isAmministratore}">
		<div class="dettaglio segnalazione mypay-custom small-text"> 
			<div class="testata"><h2><spring:message code="mypivot.dettaglio.segnalazione.nuova.title"/></h2></div>
			<div class="corpo form-horizontal"> 
				<div class="row-fluid form-row">
					<div class="span1 label-column">
						<label for="nota"><spring:message code="mypivot.dettaglio.segnalazione.nota"/></label>
					</div>
					<div class="span11 data-column">
						<textarea name="deNota" rows="7" style="width: 95%"></textarea>
					</div>
				</div>

				<div class="row-fluid form-row">
					<div class="span1 label-column">
						<label for="nascosto"><spring:message code="mypivot.dettaglio.segnalazione.nascosto"/></label>
					</div>
					<div class="span3 data-column">
						 <input type="checkbox" name="flgNascosto"></input>
					</div>
					<div class="span8">&nbsp;</div>
				</div>
			</div>
		</div>	
	</c:if>
	<div class="well carrello clearfix">
		<div class="btn-group pull-left">
		<!-- 
			<a href="#" onClick="javascript:history.back()" class="btn btn-large">
		-->
			<a href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html" class="btn btn-large">
				<i class="fa fa-chevron-circle-left fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.indietro" /> 
			</a>
		</div>
		<div class="btn-group pull-right">
			<c:if test="${segnalazioneCommand.idSegnalazione!=null}">		

			<a href="javascript:void(0);" onclick="visualizzaStoricoSegnalazioni('${segnalazioneCommand.classificazioneCompletezza}', '${segnalazioneCommand.codIuv}', '${segnalazioneCommand.codIuf}','${segnalazioneCommand.codIud}')" 	type="button" class="btn btn-primary btn-large">
					<i class="fa fa-clock-o fa-lg"></i>&nbsp;&nbsp;&nbsp;
					<spring:message code="mypivot.dettaglio.btn.storicosegnalazioni" />			
				</a>
			</c:if>
			<c:if test="${isAmministratore}">
				<c:if test="${segnalazioneCommand.classificazioneCompletezza=='IUF_NO_TES' || segnalazioneCommand.classificazioneCompletezza=='IUV_NO_RT'}">
					<a href="<%=request.getContextPath()%>/protected/visualizzaDettaglioRendicontazione.html?classificazioneCompletezza=${segnalazioneCommand.classificazioneCompletezza}&codiceIuf=${segnalazioneCommand.codIuf}"
						class="btn btn-primary btn-large">
						<i class="fa fa-times-circle fa-lg"></i>&nbsp;&nbsp;&nbsp;
						<spring:message code="mypivot.dettaglio.btn.annulla" />							
					</a>
				</c:if>
				<c:if test="${segnalazioneCommand.classificazioneCompletezza!='IUF_NO_TES' && segnalazioneCommand.classificazioneCompletezza!='IUV_NO_RT'}">
					<a href="<%=request.getContextPath()%>/protected/visualizzaDettaglio.html?classificazioneCompletezza=${segnalazioneCommand.classificazioneCompletezza}&codiceIuv=${segnalazioneCommand.codIuv}&identificativoFlussoRendicontazione=${segnalazioneCommand.codIuf}&codiceIud=${segnalazioneCommand.codIud}"
						class="btn btn-primary btn-large">
						<i class="fa fa-times-circle fa-lg"></i>&nbsp;&nbsp;&nbsp;
						<spring:message code="mypivot.dettaglio.btn.annulla" />							
					</a>
				</c:if>
					
				<!--
				<a href="<%=request.getContextPath()%>/public/carrello/anonimo/spontaneo/pagamentop.html?idSession=${idSession}"
					class="btn btn-success btn-large"> 
					<spring:message code="mypivot.dettaglio.btn.salva" />
				</a>						
				-->
				<button type="submit" class="btn btn-success btn-large" value="segnalazioneCommand">
					<i class="fa fa-save fa-lg"></i>&nbsp;&nbsp;&nbsp;
					<spring:message code="mypivot.dettaglio.btn.salva" />
				</button>
			</c:if>
		</div>	
	</div>
</form:form>

<div style="display: none;">
	<script type="text/javascript">
		function visualizzaStoricoSegnalazioni(classificazioneCompletezza, codIuv, codIuf, codIud){
			var form = $('#visualizzaStoricoSegnalazioniForm');
			form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
			form.find('#codiceIuv').val(codIuv);
			form.find('#codiceIuf').val(codIuf);
			form.find('#codiceIud').val(codIud);
			form.find('.enable-field').val(true);
			form.submit();
		}
	</script>
	<form action="visualizzaStoricoSegnalazioni.html" id="visualizzaStoricoSegnalazioniForm" method="get">
		<input type="hidden" id="classificazioneCompletezza" name="classificazioneCompletezza"/>
		<input type="hidden" id="codiceIuv" name="codiceIuv"/>
		<input type="hidden" id="codiceIuf" name="codiceIuf"/>
		<input type="hidden" id="codiceIud" name="codiceIud"/>
		<input class="enable-field" type="hidden" name="codiceIuvEnabled"/>
		<input class="enable-field" type="hidden" name="codiceIufEnabled"/>
		<input class="enable-field" type="hidden" name="codiceIudEnabled"/>
	</form>
</div>