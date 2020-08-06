<%@page import="it.tasgroup.idp.util.IrisProperties"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Version</title>
</head>
<body>
<%
	String nomeServer = InetAddress.getLocalHost().getHostName();
	String IPServer = InetAddress.getLocalHost().getHostAddress();

	Properties prop = new Properties();
	prop.load(this.getClass().getClassLoader().getResourceAsStream("build.properties"));
	
	String cbillSize = IrisProperties.getProperty("CbillSliceSize");
	String streamingMethod = IrisProperties.getProperty("StreamingMethod");
	String gatewayUrl =  IrisProperties.getProperty("GATEWAY_URL");
	
%>

<div class="container">

<h2>Version Info</h2>
<h3>EBMonitor su <%=nomeServer %> [<%=IPServer %>]</h3>

	<table class="table table-striped">
	<tbody>
<%
	for (String chiave : prop.stringPropertyNames()) {
		String valore = prop.getProperty(chiave);
%>	<tr><td><%=chiave%></td><td> <%=valore%></td></tr>
<%	
	}
%>	</tbody>
	</table>

<h3>iris-be.properties</h3>
	<table class="table table-striped">
	<tbody>
		<tr><td>cbillSize</td><td> <%=cbillSize%></td></tr>
		<tr><td>streamingMethod=</td><td> <%=streamingMethod%></td></tr>			
		<tr><td>GATEWAY_URL</td><td> <%=gatewayUrl%></td></tr>				
	</tbody>
	</table>

</div>






</body>
</html>