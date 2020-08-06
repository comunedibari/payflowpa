<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 

	TEMPLATE HTML DELLA DIALOG DEI CAPITOLI ASSOCIATI ALLA RT IN ACCERTAMENTO
	
-->

<div class="home_content dialog_capitoli_acc">

	<div class="row-fluid">	

		<!-- 
			Dettaglio RT
		-->
		<div class="detailRT ofauto">
						
			<!-- Tipo dovuto - IUD - IUV -->
			<div class="span4">
				<p class="detailp bleft">
					<c:out value="${dtt_Capitoli['flussoRT'].deTipoDovuto}" /><br />
					<c:out value="${dtt_Capitoli['flussoRT'].codiceIud}" /><br />
					<c:out value="${dtt_Capitoli['flussoRT'].codiceIuv}" />
				</p>
			</div>
				
			<!-- IUR - Attestante -->
			<div class="span4">
				<p class="detailp bleft">
					<c:out value="${dtt_Capitoli['flussoRT'].identificativoUnivocoRiscossione}" /><br />
					<c:out value="${dtt_Capitoli['flussoRT'].denominazioneAttestante}" /><br />
					<c:out value="${dtt_Capitoli['flussoRT'].codiceIdentificativoUnivocoAttestante}" />
				</p>
			</div>
				
			<!-- Data Esito - Causale -->
			<div class="span4">
				<p class="detailp bleft">
					<c:out value="${dtt_Capitoli['flussoRT'].formatSingoloImportoPagato}" /><br />
					<c:out value="${dtt_Capitoli['flussoRT'].dataEsitoSingoloPagamento}" /><br />
					<c:if test="${fn:length(dtt_Capitoli['flussoRT'].causaleVersamento)>50}">
						<c:out value="${fn:substring(dtt_Capitoli['flussoRT'].causaleVersamento, 0, 50)}" />...
					</c:if>
					<c:if test="${fn:length(dtt_Capitoli['flussoRT'].causaleVersamento)<=50}">
						<c:out value="${dtt_Capitoli['flussoRT'].causaleVersamento}" />
					</c:if>
				</p>
			</div>
				
		</div><!-- /.detailRT -->
			
				 
		<!-- 
			Label: Elenco dei capitoli associati alla RT 
		-->
		<div class="span9 nomargin">
			<p class="detailp" style="padding: 10px; margin: 10px 0;font-size: 0.9em;">
				<spring:message code="mypivot.accertamenti.dialog.capitoli" />
			</p>
		</div>		 
				
				
		<!-- 
			Tabella Capitoli
		-->
		<jsp:include page="dettaglio_capitoli.jsp" />
    	
	</div><!-- /.row-fluid -->
</div><!-- /.dialog_capitoli_acc -->