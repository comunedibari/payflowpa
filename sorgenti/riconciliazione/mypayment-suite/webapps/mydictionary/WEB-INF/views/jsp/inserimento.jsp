<%@page import="it.regioneveneto.mydictionary.controller.command.InserimentoModelloCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>


<div>
	<h2>
		<spring:message code="myDict.insermento.title" />
	</h2>
	<div class="ricerca_content">

		<jsp:include page="messages.jsp"></jsp:include>

		<div class="my_dict_content_filters">

			<form:form modelAttribute="inserimentoModelloCommand" commandName="inserimentoModelloCommand" method="post" action="inserimento.html"
				enctype="multipart/form-data" class="form-inline form-actions">

				<div class="row">
					<div class="span2">
						<label><spring:message code="myDict.inserimentoModello.codice" /></label><br />
						<form:input path="codice" class="input-medium" id="codiceTextbox" name="codice" type="text" placeholder="Codice" />
					</div>
					<div class="span2">
						<label><spring:message code="myDict.inserimentoModello.descrizione" /></label><br />
						<form:input path="descrizione" class="input-medium" id="descrizioneTextbox" name="descrizione" type="text" placeholder="Descrizione" />
					</div>
					<div class="fileupload fileupload-new span4" data-provides="fileupload">
						<label><spring:message code="myDict.inserimentoModello.ModelloXsd" /></label><br />
						<div class="input-append">
							<div class="uneditable-input span3">
								<i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span>
							</div>
							<span class="btn btn-file"> <span class="fileupload-new">Sfoglia...</span> <span class="fileupload-exists">Cambia</span>
							<form:input path="fileData" type="file" name="file" accept=".xsd" />
							</span> <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Rimuovi</a>
						</div>
					</div>
				</div>

				<input type="hidden" name="isArchive" value="false">
				<button type="submit" class="btn" name="modifica" value="false">
					<spring:message code="myDict.button.save" />
				</button>
				<button type="submit" class="btn" name="modifica" value="true">
					<spring:message code="myDict.button.savemodify" />
				</button>
			</form:form>
		</div>

		<div class="my_dict_content_filters">

			<form:form modelAttribute="inserimentoModelloCommand" commandName="inserimentoModelloCommand" method="post" action="inserimento.html"
				enctype="multipart/form-data" class="form-inline form-actions">

				<div class="row">
					<div class="fileupload fileupload-new span4" data-provides="fileupload">
						<label><spring:message code="myDict.inserimentoModello.ModelloZip" /></label><br />
						<div class="input-append">
							<div class="uneditable-input span3">
								<i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span>
							</div>
							<span class="btn btn-file"> <span class="fileupload-new">Sfoglia...</span> <span class="fileupload-exists">Cambia</span> 
							<form:input path="fileData" type="file" name="file" accept=".zip" />
							</span> <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Rimuovi</a>
						</div>
					</div>
				</div>

				<input type="hidden" name="isArchive" value="true">
				<button type="submit" class="btn" name="modifica" value="false">
					<spring:message code="myDict.button.save" />
				</button>
			</form:form>

		</div>

	</div>
</div>

