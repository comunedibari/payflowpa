<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- JAVASCRIPT IMPORTS -->

<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery-1.8.1.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery.ui.datepicker-it.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/bootstrap-fileupload.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/main.js"></script>

<!-- CSS IMPORTS -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap-fileupload.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/blitzer/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/stylesheet.css" />

</head>

<body class="main_body">
	
	<div id="wrapper">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>

		<div class="clear"></div>

		<div class="body">
			<tiles:insertAttribute name="body" />
		</div>

		<div class="clear"></div>

		<div class="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>


