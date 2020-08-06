<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- CSS IMPORTS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.orig.min.css" />
<!-- IMPORTANTE!!! IL BOOTSTRAP ORIG DEVE PRECEDERE QUELLO NON ORIG IN MODO CHE LE DEFINIZIONI COMUNI VENGANO SOVRASCRITTE -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap-fileupload.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/blitzer/jquery-ui-1.9.2.custom.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/stylesheet.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/font-awesome.min.css" />

<!-- JAVASCRIPT IMPORTS -->
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery-1.8.1.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/jQuery/jquery.ui.datepicker-it.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/bootstrap-fileupload.min.js"></script>
<script charset="utf-8"	src="<%=request.getContextPath()%>/js/main.js"></script>

<!-- 
<%-- <script charset="utf-8"	src="<%=request.getContextPath()%>/js/jquery.cookiesdirective.js"></script> --%>
<script type="text/javascript">
	// Using $(document).ready never hurts
	jQuery(document).ready(function(){
		// Cookie setting script wrapper
		var cookieScripts = function () {
		}
		
		jQuery.cookiesDirective({
<%-- 			privacyPolicyUri: '<%=request.getContextPath()%>/privacy.html', --%>
			explicitConsent : false,
			position : 'bottom',
			scriptWrapper : cookieScripts,
			backgroundColor : '#9E0C0F',
			fontColor : 'white',
			linkColor : '#ffffff'
		});
	});
</script>
 -->
 
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


