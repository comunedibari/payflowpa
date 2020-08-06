<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoNuovoCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<div class="home_content">

	<!-- TITOLO PAGINA -->
	<div class="titolo-pagina">
		<h1>
			<spring:message code="mypivot.accertamenti.title.page.accertamento" /> / <span class="txt-titolo"><spring:message code="mypivot.accertamenti.title.page.nuovo" /></span>
		</h1>
	</div>
	
	<!-- MESSAGE -->
	<div class="row-fluid">
		<tiles:insertAttribute name="message" />
	</div>
	
	<!-- 

	 	FORM  PER LA DEFINIZIONE DI UN NUOVO ACCERTAMENTO 
	
	-->
	<form:form method="post" modelAttribute="accertamentoCommand" id="accertamentiFormNv">
		
		<div class="row-fluid">
			<div class="debiti_content_filters">
				<div class="form-inline form-actions">
						
					<!-- NOME -->
					<div class="span7 control-label">
						<!-- LABEL -->
						<label>
							<span class="required">* </span><spring:message code="mypivot.accertamenti.label.nomeAccertamento" />
						</label>
						
						<!-- INPUT -->
						<spring:message code="mypivot.accertamenti.placeholder.nomeAccertamento" var="placeholderNome"/>
						<form:textarea path="nomeAccertamento" class="input-block-level" name="nomeAccertamento" placeholder="${placeholderNome}" rows="5" cols="50"></form:textarea>
						
						<form:errors path="nomeAccertamento" cssClass="error" />
					</div><!-- /.span4 -->
					
					
					<!-- TIPO DOVUTO -->
					<div class="span3 mtop10 control-label">
						<!-- LABEL -->
						<label>
							<span class="required">* </span><spring:message code="mypivot.accertamenti.filter.tipoDovuto" />
						</label>
					
						<!-- SELECT -->
						<form:select class="input-block-level" path="codTipoDovuto">
							<form:option value=""><spring:message code="mypivot.accertamenti.select.seleziona"></spring:message></form:option>
							<c:forEach var="ctd" items="${listaTipiDovuti}">
								<form:option value="${ctd.codTipo}">
									<c:out value="${ctd.deTipo}"></c:out>
								</form:option>
							</c:forEach>
						</form:select>
						
						<form:errors path="codTipoDovuto" cssClass="error" />
					</div>
					
				</div><!-- /.form-actions -->
			</div><!-- /.debiti_content_filters -->
			
			
			<!-- PULSANTIERA -->
			<div class="well clearfix">
				
				<!-- TORNA ALLA RICERCA -->
				<div class="btn-group pull-left">
					<a href="<%=request.getContextPath()%>/protected/accertamenti/ricerca.html" class="btn btn-large">
						<i class="fa fa-chevron-circle-left fa-lg"></i> <spring:message code="mypivot.accertamenti.btn.label.indietro" /> 
					</a>
				</div>
				
				<!-- CONFERMA -->
				<div class="btn-group pull-right">
					<button type="submit" class="btn btn-success btn-large" value="accertamentoCommand">
						<i class="fa fa-save fa-lg"></i> <spring:message code="mypivot.accertamenti.btn.label.conferma" />
					</button>
				</div>
					
			</div><!-- /.well -->
		</div><!-- /.row-fluid -->
		
	</form:form>
	
</div><!-- /.home_content -->
