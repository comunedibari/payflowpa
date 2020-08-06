<%@page import="it.regioneveneto.mydictionary.controller.command.EditModelloCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myp"%>
<%@page import="it.regioneveneto.mydictionary.controller.SchemaNode"%>

<style>
.graph_content {
	overflow-x: auto; 
}

*,*:before,*:after {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.tree {
	min-height: 50px; 
	position: relative;
}

.tree ul {
	position: relative;
	margin-left: 260px;
}

.tree ul:before {
	content: "";
	width: 30px;
	border-top: 1px solid #eee9dc;
	position: absolute;
	left: -60px;
	top: 50%;
	margin-top: 1px;
}

.tree li {
	position: relative;
	min-height: 50px;
	list-style-type: none;
}

.tree li:before {
	content: "";
	height: 100%;
	border-left: 1px solid #eee9dc;
	position: absolute;
	left: -30px;
}

.tree li:after {
	content: "";
	width: 30px;
	border-top: 1px solid #eee9dc;
	position: absolute;
	left: -30px;
	top: 50%;
	margin-top: 1px;
}

.tree li:nth-child(2):before {
	width: 10px;
	height: 50%;
	top: 50%;
	margin-top: 1px;
	border-radius: 10px 0 0 0;
}

.tree li:nth-child(2):after {
	height: 10px;
	border-radius: 10px 0 0 0;
}

.tree li:last-child:before {
	width: 10px;
	height: 50%;
	border-radius: 0 0 0 10px;
}

.tree li:last-child:after {
	height: 10px;
	border-top: none;
	border-bottom: 1px solid #eee9dc;
	border-radius: 0 0 0 10px;
	margin-top: -9px;
}

.tree li.sole:before {
	display: none;
}

.tree li.sole:after {
	width: 30px;
	height: 0;
	margin-top: 1px;
	border-radius: 0;
}

.tree span.node {
	display: block;
	min-width: 200px;
	padding: 5px 10px;
	text-align: center;
	position: absolute;
	left: 0;
	top: 50%;
	margin-top: -15px;
	border: 1px solid #ccc;
	display: inline-block;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	-transition: all 0.5s;
	-webkit-transition: all 0.5s;
	-moz-transition: all 0.5s;
}

.tree a {
	text-decoration: none;
	color: #666;
	font-family: arial, verdana, tahoma;
	font-size: 11px;
	line-height: 20px;
}

.tree .expander {
	position: absolute;
	right: -39px;
	top: 6px;
	z-index: 10;
	border: 1px solid #DDD;
	padding: 7px;
	border-radius: 2px;
	background-color: white;
}

.tree li span:hover,.tree li span:hover+ul li span {
	background: #c8e4f8;
	color: #000;
	border: 1px solid #94a0b4;
}

.tree li span.node:hover+ul li::after,.tree li span:hover+ul li::before,.tree li span:hover+ul::before,.tree li span:hover+ul ul::before {
	border-color: #94a0b4;
}

.btn-spaced {
	margin-left: 10px;
}
</style>
<div>
	<h2>
		<spring:message code="myDict.grafico.title" />
	</h2>
	<div class="graph_content"> 
		<a class="btn-spaced btn btn-default pull-right" onclick="collapseAll();">
			<i class="icon-minus"></i>
		</a>
		<a class="btn-spaced btn btn-default pull-right" onclick="expandAll();">
			<i class="icon-plus"></i>
		</a>
		<div style="clear: both;"></div>
		<c:forEach var="root" items="${rootNodes}">
			<c:if test="${not empty root.parent}">
				<a href="${pageContext.request.contextPath}/grafico.html?rootCode=${root.parent.code}" class="btn btn-default">
					<i class="icon-arrow-up"></i>
				</a>
			</c:if>
			<div class="tree">
				<myp:treenode root="${root}" />
				<div style="clear: both;"></div>
			</div>
		</c:forEach>
	</div>
</div>

<script>

	function toggleBranch(expander, code) {
		if ($(expander).hasClass('icon-plus')) {
			$(expander).switchClass('icon-plus', 'icon-minus', 10);
			$('.grouper_' + code).hide();
			$('.content_' + code).show();
		} else {
			$(expander).switchClass('icon-minus', 'icon-plus', 10);
			$('.grouper_' + code).show();
			$('.content_' + code).hide();
		}
	}
	
	function expandAll() {
		$('.grouper').hide();
		$('.content').show();
		$('.expander').switchClass('icon-plus', 'icon-minus', 10);
	}

	function collapseAll() {
		$('.grouper').show();
		$('.content').hide();
		$('.expander').switchClass('icon-minus', 'icon-plus', 10);
	}

</script>