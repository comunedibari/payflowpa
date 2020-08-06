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
%>

<div class="container">

<h2>Version Info</h2>
<h3>EBMonitor su <%=nomeServer %> [<%=IPServer %>]</h3>

	<table class="table table-striped">
<%--
	<thead>
	<tr><th>chiave</th><th>valore</th></tr>
	</thead>
 --%>	
	<tbody>
<%
	for (String chiave : prop.stringPropertyNames()) {
		String valore = prop.getProperty(chiave);
%>	<tr><td><%=chiave%></td><td> <%=valore%></td></tr>
<%	
	}
%>	</tbody>
	</table>

</div>




</body>
</html>