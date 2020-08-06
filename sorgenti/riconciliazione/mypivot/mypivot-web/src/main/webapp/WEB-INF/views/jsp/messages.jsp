<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:forEach var="dsmessage" items="${messagesDto.successMessages}">
	<div class="alert alert-success">
		<h4>
			<spring:message code="mypivot.messages.success" />
			:
		</h4>
		<spring:message code="${dsmessage.code}" arguments="${dsmessage.arguments}"/>
	</div>
</c:forEach>
<c:forEach var="dimessage" items="${messagesDto.informationMessages}">
	<div class="alert alert-info">
		<h4>
			<spring:message code="mypivot.messages.info" />
			:
		</h4>
		<spring:message code="${dimessage.code}" arguments="${dimessage.arguments}"/>
	</div>
</c:forEach>
<c:forEach var="dwmessage" items="${messagesDto.warningMessages}">
	<div class="alert alert-block">
		<h4>
			<spring:message code="mypivot.messages.warning" />
			!
		</h4>
		<spring:message code="${dwmessage.code}" arguments="${dwmessage.arguments}"/>
	</div>
</c:forEach>
<c:forEach var="demessage" items="${messagesDto.errorMessages}">
	<div class="alert alert-error">
		<h4>
			<spring:message code="mypivot.messages.error" />
			!
		</h4>
		<spring:message code="${demessage.code}" arguments="${demessage.arguments}"/>
	</div>
</c:forEach>
