<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
	function modificaDettaglio(id){
		var form = $('#modificaDettaglioAnagraficaForm');

		form.find('#id').val(id);
		form.submit();
	}
</script>

<div style="display: none;">
	<form action="modificaAnagrafica.html" id="modificaDettaglioAnagraficaForm" method="get">
		<input id="id" type="hidden" name="id"/>
	</form>
</div>

<div>
	
	<div class="titolo-pagina">
		<h1>
			<span class="txt-titolo"><spring:message code="mypivot.anagrafica.dettaglioAnagrafica"/></span>
		</h1>
	</div>
	
	<div class="row-fluid">
		<div class="alert alert-success <c:if test="${esitoMsg==null || esitoMsg=='' || esitoMsg=='ERROR'}">hidden</c:if>">
			<h4> <spring:message code="mypivot.messages.info" /> :</h4> 
			<c:if test="${esitoMsg=='AGGIORNATA'}">
				<spring:message code="mypivot.anagrafica.info.updateAnagrafica.ok"/>
			</c:if>
			<c:if test="${esitoMsg=='INSERITA'}">
				<spring:message code="mypivot.anagrafica.info.inserimentoAnagrafica.ok"/>
			</c:if>
		</div>
	</div>
	
	<div class="row-fluid">
		<div class="alert alert-error <c:if test="${esitoMsg==null || esitoMsg=='' || esitoMsg!='ERROR'}">hidden</c:if>">
			<c:if test="${esitoMsg=='ERROR'}">
				<spring:message code="mypivot.anagrafica.info.updateAnagrafica.ko"/>
			</c:if>
		</div>
	</div>
	

<div class="row-fluid">

<div class="divPadding">


	<!-- TIPO DOVUTO -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.tipoDovuto"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.anagrafica.tipoDovuto"/>:</label>
				</div>
				<div class="span10 data-column">
					<c:choose>
    				<c:when test="${anagUffCapAccDto.deTipoDovuto eq '' || anagUffCapAccDto.deTipoDovuto eq 'null' || anagUffCapAccDto.deTipoDovuto eq null}">
        				<label>N/A</label>
   		 			</c:when>    
    				<c:otherwise>
        				<label>${anagUffCapAccDto.deTipoDovuto}</label>
    				</c:otherwise>
				</c:choose>
					   
				</div>
			</div>
		</div>
	</div>

	<!-- UFFICIO -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.datiUfficio"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
				</div>
				<div class="span2 data-column">
					<label>${anagUffCapAccDto.codUfficio}</label>
				</div>
			
				<div class="span2 label-column">
					<label><spring:message code="mypivot.anagrafica.denominazione"/></label>
				</div>
				<div class="span3 data-column">
					<label>${anagUffCapAccDto.deUfficio}</label>
				</div>
				
				<div class="span2 label-column">
					<label><spring:message code="mypivot.anagrafica.filter.flgAttivo" /></label>
				</div>
				<div class="span2 data-column">
					<form action="">
						<c:if test="${anagUffCapAccDto.flgAttivo == true}">
							<input type="checkbox" value="" disabled="disabled" checked="checked"/>
						</c:if>
    					<c:if test="${anagUffCapAccDto.flgAttivo == false}">
							<input type="checkbox" value="" disabled="disabled"/>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- CAPITOLO -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.datiCapitolo"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
				</div>
				<div class="span2 data-column">
					<label>${anagUffCapAccDto.codCapitolo}</label>
				</div>
			
				<div class="span2 label-column">
					<label><spring:message code="mypivot.anagrafica.denominazione"/></label>
				</div>
				<div class="span3 data-column">
					<label>${anagUffCapAccDto.deCapitolo}</label>
				</div>
				
				<div class="span2 label-column">
					<label><spring:message code="mypivot.anagrafica.annoEsercizio"/></label>
				</div>
				<div class="span2 data-column">
					<label>${anagUffCapAccDto.deAnnoEsercizio}</label>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ACCERTAMENTO	 -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.datiAccertamento"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span1 label-column">
					<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
				</div>
				<div class="span2 data-column">
					<label>${anagUffCapAccDto.codAccertamento}</label>
				</div>
			
				<div class="span2 label-column">
					<label><spring:message code="mypivot.anagrafica.denominazione"/></label>
				</div>
				<div class="span3 data-column">
					<label>${anagUffCapAccDto.deAccertamento}</label>
				</div>
			</div>
		</div>
	</div>

</div><!-- /<div class="divPadding"> -->

</div><!-- /<div class="row-fluid"> -->

<div class="well carrello clearfix">
		<div class="btn-group pull-left">
			<a href="/mypivot/protected/accertamentiAnagrafiche/ricerca.html?tab=U" class="btn btn-large">
				<i class="fa fa-chevron-circle-left fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.indietro"/>
			</a>
		</div><!-- /<div class="btn-group pull-left"> -->
		
		<div class="btn-group pull-right">
		
			<a href="javascript:void(0);" onclick="modificaDettaglio('${anagUffCapAccDto.id}')" class="btn btn-primary btn-large <c:if test="${requestScope.codIpaEnte eq 'R_VENETO' && anagUffCapAccDto.codTipoDovuto != null}"> hidden </c:if>">
				<i class="fa fa-pencil-square-o fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.modifica"/>						
			</a>
					
 
	<!-- 		<a href="javascript:void(0);" onclick="visualizzaStoricoSegnalazioni('IUD_RT_IUF_TES', 'RF03004400000000936260000', 'SCT2016060325500928801','008160602110645583BM404LT0013080000')" type="button" class="btn btn-primary btn-large">
					<i class="fa fa-clock-o fa-lg"></i>&nbsp;&nbsp;&nbsp;
					Storico Segnalazioni			
				</a>
					
				<!--
				<a href="/mypivot/public/carrello/anonimo/spontaneo/pagamentop.html?idSession="
					class="btn btn-success btn-large"> 
					Salva Segnalazione
				</a>						
				-->
				<!-- <button type="submit" class="btn btn-success btn-large" value="segnalazioneCommand">
					<i class="fa fa-save fa-lg"></i>&nbsp;&nbsp;&nbsp;
					Salva Segnalazione
				</button> -->
			
		</div>	<!-- /<div class="btn-group pull-right"> -->
</div><!-- /<div class="well carrello clearfix"> -->

</div>