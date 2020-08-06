<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- CSS IMPORTS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap-fileupload.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/blitzer/jquery-ui-1.9.2.custom.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/stylesheet.css" />

<!-- JAVASCRIPT IMPORTS -->
<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/jQuery/jquery-1.8.1.min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/jQuery/jquery.ui.datepicker-it.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/bootstrap-fileupload.min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/main.js"></script>

</head>

<body class="main_body">

	<div id="wrapper">

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

		<div class="body clearfix">
			<div class="clearfix">
				<div style="text-align: center;">
					<img alt="404" src="<%=request.getContextPath()%>/images/404.gif"
						title="404">
				</div>
				<div class="alert alert-error myAlert">
					<i class="fa fa-exclamation-circle fa-2x pull-left"></i> Siamo
					spiacenti, la pagina richiesta non esiste.
				</div>

			</div>
		</div>

		<div class="clear"></div>

		<div class="footer">
			<div class="row-fluid">
				<p class="span9">
					&copy; 2017 Regione del Veneto - P.I. 02392630279<br /> <a
						class="disabled" href="" title="accessibilit&agrave;">accessibilit&agrave;</a>&nbsp;&nbsp;-&nbsp;&nbsp;
					<a href="<%=request.getContextPath()%>/privacy.html"
						title="privacy">privacy</a>&nbsp;&nbsp;-&nbsp;&nbsp; <a
						href="<%=request.getContextPath()%>/cookies.html"
						title="informativa sui cookies">informativa sui cookies</a>&nbsp;&nbsp;-&nbsp;&nbsp;
					<a class="disabled" href="" title="credits">credits</a>&nbsp;&nbsp;-&nbsp;&nbsp;
					<a class="disabled" href="" title="mappa del sito">mappa del
						sito</a>
				</p>
				<p class="span3 text-right">
					<a href="http://www.regione.veneto.it" title="Regione del Veneto">
						<img alt="Regione del Veneto"
						src="<%=request.getContextPath()%>/images/logo_Regione-Veneto_small.png" />
					</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>