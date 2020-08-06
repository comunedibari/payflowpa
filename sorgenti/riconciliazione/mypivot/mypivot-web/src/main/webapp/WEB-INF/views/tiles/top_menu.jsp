<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.UTENTE_SESSION_KEY != null}">
	<c:if test="${not empty requestScope.ente && requestScope.viewHeader}">
<script type="text/javascript">

</script>
<!-- ESEMPIO DROPDOWN - INIZIO -->
<div class="header_menu clearfix">
	<div class="btn-toolbar">
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-random"></i>&nbsp;&nbsp;<spring:message code="mypivot.visualizzaCompleta.title" />
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<li class="sub-menu-item"><a href="<%= request.getContextPath() %>/protected/visualizzaCompleta.html?tipoVisualizzazione=R&forceClear=true"><spring:message code="mypivot.visualizzaCompleta.title.riconciliazione" /></a></li>
				<li class="sub-menu-item"><a href="<%= request.getContextPath() %>/protected/visualizzaCompleta.html?tipoVisualizzazione=A&forceClear=true"><spring:message code="mypivot.visualizzaCompleta.title.anomalie" /></a></li>
				<li class="sub-menu-item"><a href="<%= request.getContextPath() %>/protected/visualizzaRicevuta.html?forceClear=true"><spring:message code="mypivot.label.flussi.ricevute" /></a></li>
				<li class="sub-menu-item"><a href="<%= request.getContextPath() %>/protected/visualizzaRendicontazione.html?forceClear=true"><spring:message code="mypivot.label.flussi.rendicontazione" /></a></li>
				<li class="sub-menu-item"><a href="<%= request.getContextPath() %>/protected/visualizzaTesoreria.html?forceClear=true"><spring:message code="mypivot.label.flussi.giornaleDiCassa" /></a></li>
				<li class="sub-menu-item"><a href="<%= request.getContextPath() %>/protected/visualizzaStoricoSegnalazioni.html"><spring:message code="mypivot.button.storicosegnalazioni" /></a></li>
			</ul>
		</div>
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-upload"></i>&nbsp;&nbsp;<spring:message code="mypivot.flussi.title" />
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<li class="sub-menu-item">
					<c:choose>
						<c:when test="${not requestScope.gestioneFlussiImportActive}">
							<a class="disabled" href="javascript:void(0);">
								<spring:message code="mypivot.flussi.title.import" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=E">
								<spring:message code="mypivot.flussi.title.import" />
							</a>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub-menu-item"><a href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html"><spring:message code="mypivot.flussi.title.export" /></a></li>
			</ul>
		</div>
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-search"></i>&nbsp;&nbsp;<spring:message code="mypivot.accertamenti.title.page.accertamento" />
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<c:choose>
					<c:when test="${not requestScope.gestioneAccertamentoActive}">
						<li class="sub-menu-item">
							<a class="disabled" href="javascript:void(0);">
								<spring:message code="mypivot.accertamenti.title.page.nuovo" />
							</a>
						</li>
						<li class="sub-menu-item">
							<a class="disabled" href="javascript:void(0);">
								<spring:message code="mypivot.accertamenti.title.page.gestioneAccertamenti" />
							</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="sub-menu-item">
							<a href="<%=request.getContextPath()%>/protected/accertamenti/nuovo.html">
								<spring:message code="mypivot.accertamenti.title.page.nuovo" />
							</a>
						</li>
						<li class="sub-menu-item">
							<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html">
								<spring:message code="mypivot.accertamenti.title.page.gestioneAccertamenti" />
							</a>
						</li>
					</c:otherwise>
				</c:choose>
				<li class="sub-menu-item">
					<c:choose>
						<c:when test="${not requestScope.gestioneAnagraficaActive}">
							<a class="disabled" href="javascript:void(0);">
								<spring:message code="mypivot.anagrafica.title.gestioneAnagrafiche" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="<%=request.getContextPath()%>/protected/accertamentiAnagrafiche/ricerca.html?tab=U">
								<spring:message code="mypivot.anagrafica.title.gestioneAnagrafiche" />
							</a>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-bar-chart"></i>&nbsp;&nbsp;<spring:message code="mypivot.statistiche.menu.statistiche" />
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<c:choose>
					<c:when test="${not requestScope.cruscottoIncassiActive}">
						<li class="sub-menu-item"><a class="disabled" href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliPerAnnoMeseGiorno.html?tipo=ANNO"><spring:message code="mypivot.statistiche.menu.statistica.GIORNO" /></a></li>
						<li class="sub-menu-item"><a class="disabled" href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerUffici.html"><spring:message code="mypivot.statistiche.menu.statistica.UFFICI" /></a></li>
						<li class="sub-menu-item"><a class="disabled" href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerDovuti.html"><spring:message code="mypivot.statistiche.menu.statistica.DOVUTI" /></a></li>
						<li class="sub-menu-item"><a class="disabled" href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerCapitoli.html"><spring:message code="mypivot.statistiche.menu.statistica.CAPITOLI" /></a></li>
						<li class="sub-menu-item"><a class="disabled" href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerAccertamenti.html"><spring:message code="mypivot.statistiche.menu.statistica.ACCERTAMENTI" /></a></li>
					</c:when>
					<c:otherwise>
						<li class="sub-menu-item"><a href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliPerAnnoMeseGiorno.html?tipo=ANNO"><spring:message code="mypivot.statistiche.menu.statistica.GIORNO" /></a></li>
						<li class="sub-menu-item"><a href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerUffici.html"><spring:message code="mypivot.statistiche.menu.statistica.UFFICI" /></a></li>
						<li class="sub-menu-item"><a href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerDovuti.html"><spring:message code="mypivot.statistiche.menu.statistica.DOVUTI" /></a></li>
						<li class="sub-menu-item"><a href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerCapitoli.html"><spring:message code="mypivot.statistiche.menu.statistica.CAPITOLI" /></a></li>
						<li class="sub-menu-item"><a href="<%=request.getContextPath()%>/protected/cruscottoIncassi/totaliRipartitiPerAccertamenti.html"><spring:message code="mypivot.statistiche.menu.statistica.ACCERTAMENTI" /></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>
<!-- ESEMPIO DROPDOWN - FINE -->
<%--
	
		<div class="header_menu clearfix body2 <c:if test="${(not requestScope.gestioneAccertamentoActive)}">menu-3-bottoni</c:if> <c:if test="${(requestScope.gestioneAccertamentoActive)}">menu-4-bottoni</c:if>">
			<div
				class="header btn sx <c:if test="${requestScope.gestioneFlussiImportTileActive}">active</c:if> <c:if test="${(not requestScope.gestioneFlussiImportActive)}">disabled</c:if>">
				<a
					class="gestione-flussi <c:if test="${(requestScope.gestioneFlussiImportTileActive || not requestScope.gestioneFlussiImportActive)}">disabled</c:if>"
					href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=E"
					title="Flussi Import"> <i class="fa fa-upload fa-3x"></i></br>Flussi
					Import
				</a>
			</div>
			<div
				class="header btn center <c:if test="${requestScope.gestioneVisualizzaTileActive}">active</c:if> <c:if test="${not requestScope.gestioneVisualizzaActive}">disabled</c:if>">
				<a
					class="gestione-visualizza <c:if test="${requestScope.gestioneVisualizzaTileActive || not requestScope.gestioneVisualizzaActive}">disabled</c:if>"
					href="<%=request.getContextPath()%>/protected/visualizzaCompleta.html"
					title="Visualizzazione"> <i class="fa fa-random fa-3x"></i></br>Visualizzazione
				</a>
			</div>
			<div
				class="header btn dx <c:if test="${requestScope.gestioneFlussiExportTileActive}">active</c:if> <c:if test="${(not requestScope.gestioneFlussiExportActive)}">disabled</c:if>">
				<a
					class="gestione-flussi <c:if test="${(requestScope.gestioneFlussiExportTileActive || not requestScope.gestioneFlussiExportActive)}">disabled</c:if>"
					href="<%=request.getContextPath()%>/protected/flussi/flussiExport.html"
					title="Flussi Export"> <i class="fa fa-download fa-3x"></i></br>Flussi
					Export
				</a>
			</div>
			<!-- Accertamento -->
			<div class="header btn dx <c:if test="${requestScope.gestioneAccertamentoTileActive}">active</c:if> <c:if test="${(not requestScope.gestioneAccertamentoActive)}">hidden</c:if>">
				<a
					class="gestione-flussi <c:if test="${(requestScope.gestioneAccertamentoTileActive || not requestScope.gestioneAccertamentoActive)}">disabled</c:if>"
					href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html" title="Accertamento">
					<i class="fa fa-list-alt fa-3x" aria-hidden="true"></i></br>Accertamento
				</a>
			</div>
		</div>
 --%>
	</c:if>
	
</c:if>


