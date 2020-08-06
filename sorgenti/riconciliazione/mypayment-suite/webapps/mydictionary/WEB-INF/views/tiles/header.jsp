<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="header_top">
	<h2>
		<!-- 		<img height="30" class="logo-mypay" alt="MyPay logo" -->
		<%-- 			src="<%=request.getContextPath()%>/resources/img/logo-MyPay.png" /> --%>
	</h2>
</div>

<div class="header_middle clearfix">
	<div>
		<a class="intestazione" href="<%=request.getContextPath()%>/home.html"
			title="Homepage"> <img class="logo-regione"
			alt="Regione del Veneto"
			src="<%=request.getContextPath()%>/resources/img/regione-veneto-logo.png" />
			<span class="payoff"><spring:message
					code="myDict.header.title" /></span>
		</a>
	</div>

	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="<%=request.getContextPath()%>/home.html">Home</a>
			<ul class="nav">
				<li>
					<a href="<%=request.getContextPath()%>/inserimento.html">
						<spring:message code="myDict.inserisci.title" />
					</a>
				</li>
				<li>
					<a href="<%=request.getContextPath()%>/ricerca.html">
						<spring:message code="myDict.ricerca.title" />
					</a>
				</li>
				<!-- li>
					<a href="<%=request.getContextPath()%>/grafico.html">
						<spring:message code="myDict.grafico.title" />
					</a>
				</li -->
			</ul>
		</div>
	</div>


</div>


