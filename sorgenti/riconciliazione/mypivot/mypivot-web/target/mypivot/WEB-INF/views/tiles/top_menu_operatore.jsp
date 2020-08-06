<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="header_menu clearfix body2">
		<c:if test="${sessionScope.utente != null}">
			<c:if test="${not empty requestScope.ente}">
			
			
				<div class="header btn sx <c:if test="${requestScope.gestioneFlussiTileActive}">active</c:if>">
					<a class="gestione-flussi <c:if test="${requestScope.gestioneFlussiTileActive}">disabled</c:if>" href="<%=request.getContextPath()%>/protected/carica/flussiUpload.html?tipoFlusso=E" title="Gestione Flussi">
						<i class="fa fa-random fa-3x"></i></br>Gestione Flussi <span class="subtitle"><br />
					</span>
					</a>
				</div>
				<div class="header btn dx <c:if test="${requestScope.gestioneVisualizzaTileActive}">active</c:if>">
					<a class="gestione-visualizza <c:if test="${requestScope.gestioneVisualizzaTileActive}">disabled</c:if>" href="<%=request.getContextPath()%>/protected/visualizza.html" title="Visualizzazione">
						<i class="fa fa-clipboard fa-3x"></i></br>Visualizzazione <span class="subtitle"></span>
					</a>
				</div>
				
				
			</c:if>
		</c:if>
	</div>
</div>

