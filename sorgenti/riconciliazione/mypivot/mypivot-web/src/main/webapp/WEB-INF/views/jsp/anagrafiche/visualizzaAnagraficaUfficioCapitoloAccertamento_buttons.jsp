<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>

<div class="btn-group">
	<a href="javascript:void(0);" onclick="cancellaAnagrafica('${AnagraficaUfficioCapitoloAccertamentoDto.id}')" type="button" class="btn btn-primary btn-medium <c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}"> hidden </c:if>"  title="Elimina"> <!-- <c:if test="${requestScope.codIpaEnte eq 'R_VENETO'}"> hidden </c:if> -->
		<i class="fa fa-trash-o"></i>
	</a>

	<a href="javascript:void(0);" onclick="visualizzaDettaglio('${AnagraficaUfficioCapitoloAccertamentoDto.id}')" type="button" class="btn btn-primary btn-medium" title="Dettaglio">
		<i class="fa fa-search"></i>
	</a>									
</div>


<!-- visualizzaDettaglio('${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.id.classificazioneCompletezza}', '${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIuvKey}', '${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIufKey}','${visualizzaCompletaDto.importExportRendicontazioneTesoreriaTO.codIudKey}') -->