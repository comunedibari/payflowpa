<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- CSS IMPORTS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.orig.min.css" />
<!-- IMPORTANTE!!! IL BOOTSTRAP ORIG DEVE PRECEDERE QUELLO NON ORIG IN MODO CHE LE DEFINIZIONI COMUNI VENGANO SOVRASCRITTE -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap-fileupload.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/blitzer/jquery-ui-1.9.2.custom.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/stylesheet.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/font-awesome.min.css" />

<!-- JAVASCRIPT IMPORTS -->
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jQuery/jquery-1.8.1.min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jQuery/jquery.ui.datepicker-it.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jQuery/jquery.datetimepicker.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/bootstrap-fileupload.min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/main.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/vendor/jquery.ui.widget.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jquery.iframe-transport.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/jQuery/jquery.keepAlive.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery.ui.datepicker-it.js"></script>

<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%=request.getContextPath()%>/js/jquery.xdr-transport.js"></script>
<![endif]-->

</head>

<body class="main_body operatore">
	
	<div id="wrapper">
		<div id="header" class="clearfix">
			<tiles:insertAttribute name="header" />
			<div id="top_menu">
				<tiles:insertAttribute name="top_menu" />
			</div>
		</div>
		
		<div class="body clearfix">
			<div class="clearfix">
				<tiles:insertAttribute name="body" />
			</div>
            <div class="menu-bottom clearfix">
				<tiles:insertAttribute name="bottom_menu" />
			</div>
		</div>
		<div class="clear"></div>

		<div class="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>


