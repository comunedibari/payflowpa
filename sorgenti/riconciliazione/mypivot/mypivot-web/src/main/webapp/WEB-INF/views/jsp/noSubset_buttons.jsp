<%@page
	import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>

<div class="btn-group">
	<c:if test="${visualizzaCompletaDto.segnalazione.flgNascosto != null and visualizzaCompletaDto.segnalazione.flgNascosto}">
		<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="Elemento nascosto">
			<i class="fa fa-eye-slash"></i>
		</a>
	</c:if>
	<c:if test="${visualizzaCompletaDto.segnalazione.flgNascosto == null or !visualizzaCompletaDto.segnalazione.flgNascosto}">
		<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="Elemento attivo">
			<i class="fa fa-eye"></i>
		</a>
	</c:if>
	<c:if test="${visualizzaCompletaDto.segnalazione.id != null}">
		<c:set var="nota" value="${fn:substring(visualizzaCompletaDto.segnalazione.deNota, 0, 50)}" />
		<a href="javascript:void(0);" type="button" class="btn btn-info btn-medium bottone-testata-disabilitato" title="${nota}">
			<i class="fa fa-comment-o"></i>
		</a>
		<a href="javascript:void(0);" onclick="visualizzaStoricoSegnalazioni('${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.id.classificazioneCompletezza}', '${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIuvKey}', '${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIufKey}','${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}')" type="button" class="btn btn-primary btn-medium " title="Storico Segnalazioni">
			<i class="fa fa-clock-o"></i>
		</a>
	</c:if>
	<a href="javascript:void(0);" onclick="visualizzaDettaglio('${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.id.classificazioneCompletezza}', '${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIuvKey}', '${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIufKey}','${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}')" type="button" class="btn btn-primary btn-medium" title="Dettaglio">
		<i class="fa fa-search"></i>
	</a>									
</div>
