<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="header_top">
	<h2 class="mypivotBackgroundLeft">
		<img height="30" class="logo-mypivot" alt="MyPivot logo"
			src="<%=request.getContextPath()%>/images/logo-MyPivot.png" />
	</h2>
	<h1 class="text-center mainTitle">MyPivot</h1>
	<h2 class="mypivotBackgroundRight">
		<img class="logo-ente"
			src="<%=request.getContextPath()%>/images/pagolapa.png">
	</h2>
</div>

<div class="header_middle clearfix">
	<div class="row-fluid strumenti">
		<div class="span6">
			<div class="intestazione">
				<c:if test="${not empty requestScope.ente}">
					<img class="logo-ente" src="data:image/png;base64, ${requestScope.deLogoEnte}" />
					<span class="payoff"><c:out value="${requestScope.ente}"></c:out></span>
				</c:if>
				<c:if test="${empty requestScope.ente}">
					<img class="logo-ente" alt="Regione del Veneto"
						src="<%=request.getContextPath()%>/images/regione-veneto-logo-head.png" />
					<span class="payoff">Regione del Veneto</span>
				</c:if>
			</div>
		</div>


		<!-- GUEST -->
		<c:if test="${sessionScope.UTENTE_SESSION_KEY == null}">
			<div class="span2">&nbsp;</div>
			<div class="span4 control-button">
				<div class="btn-group pull-right">
					<a class="btn main-menu-button"
						href="<%=request.getContextPath()%>/protected/login"
						title="accedi"><i class="fa fa-lock fa-2x"></i><br /> <spring:message
							code="mypivot.header.login" /></a>

				</div>
			</div>
		</c:if>
		
		
		<!-- UTENTE LOGGATO -->
		<c:if test="${sessionScope.UTENTE_SESSION_KEY != null}">

			<div class="span2 utente">
				<div>
					<span class="italic small"><spring:message
							code="mypivot.home.benvenuto" /></span><br> <a class="profilo-utente"
						href="${requestScope.urlModificaProfiloFedera}"
						title="modifica profilo"><span class="bold"><c:out
								value="${requestScope.username}" /></span>&nbsp;<i
						class="fa fa-pencil"></i></a>
				</div>
			</div>

			<div class="span4 control-button">
				<div class="btn-group pull-right">
					
					<a class="btn main-menu-button"
							href="<%=request.getContextPath()%>/protected/sceltaEnte.html"
							title="gestione beneficiari"><i class="fa fa-cog fa-2x"></i><br />
							<spring:message code="mypivot.header.scegliEnte" /></a>
					

					<a class="btn main-menu-button"
						href="<%=request.getContextPath()%>/protected/logout.html" title="esci"><i
						class="fa fa-sign-out fa-2x"></i><br /><spring:message code="mypivot.header.esci" /></a>
				</div>
			</div>

		</c:if>



	</div>