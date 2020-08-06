<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="java.net.URLEncoder"%>

<script type="text/javascript" charset="UTF-8">

var listaAccertamenti;

	function visualizzaDettaglio(id){
		var form = $('#visualizzaDettaglioAnagraficaForm');

		form.find('#id').val(id);
		form.submit();
	}


	function flgClick(oldFlg, newFlg){
		var form = $('#modificaAnagraficaForm');
		
		if(newFlg.checked.toString() != oldFlg){
			form.find('#flgAttivoCambiato').val(true);
			$( "#dialog-info" ).dialog({
			      resizable: false,
			      height: "auto",
			      width: 400,
			      modal: true,
			      buttons: {
			        "Continua": function() {
			           $( this ).dialog( "close" );
			        }
			      }
			});
		}else{
			form.find('#flgAttivoCambiato').val(false);
			}
	}


	/* function settaDto(){
		var oldDto = "${anagUffCapAccDto}";
		var form = $('#modificaAnagraficaForm');
		//SALVA VARIABILI DELL'OLD DTO NELLE VARIABILI HIDDEN DELLA FORM
		form.find('#idDto').val("${anagUffCapAccDto.id}");
		form.find('#codAccertamentoDto').val("${anagUffCapAccDto.codAccertamento}");
		form.find('#codCapitoloDto').val("${anagUffCapAccDto.codCapitolo}");
		form.find('#codTipoDovutoDto').val("${anagUffCapAccDto.codTipoDovuto}");
		form.find('#deTipoDovutoDto').val("${anagUffCapAccDto.deTipoDovuto}");
		form.find('#codUfficioDto').val("${anagUffCapAccDto.codUfficio}");
		form.find('#deAccertamentoDto').val("${anagUffCapAccDto.deAccertamento}");
		form.find('#deAnnoEsercizioDto').val("${anagUffCapAccDto.deAnnoEsercizio}");
		form.find('#deCapitoloDto').val("${anagUffCapAccDto.deCapitolo}");
		form.find('#deUfficioDto').val("${anagUffCapAccDto.deUfficio}");
		form.find('#flgAttivoDto').val("${anagUffCapAccDto.flgAttivo}");
		}
	 window.onload = settaDto; */


	 function controllaCodAcc(codAcc){

			var cod = codAcc.value;

			if(cod!="n/a"){
				listaAccertamenti.forEach(function(cap, index){
					if(cap.codAccertamento == cod){
						$( "#dialog-info-accertamento" ).dialog({
						      resizable: false,
						      height: "auto",
						      width: 400,
						      modal: true,
						      buttons: {
						        "Continua": function() {
						           $( this ).dialog( "close" );
						           var form = $('#modificaAnagraficaForm');
						           form.find('#codiceAccertamento').val('n/a');
						        }
						      }
						});
						}
				});
			}
		}
	 

	  function isVeneto(){
		 var ente = "${requestScope.codIpaEnte}";
		 var form = $('#visualizzaDettaglioAnagraficaForm');
			if (ente != "R_VENETO" || (ente == "R_VENETO" && "${anagUffCapAccDto.codTipoDovuto}"!="")){
				$('select[name="codTipoDovuto"]').attr('disabled', 'disabled'); 
					}

			if(ente == "R_VENETO"){
				$('input[name="denominazioneUfficio"]').attr('readonly', 'true');
				$('input[name="denominazioneCapitolo"]').attr('readonly', 'true');
				$('input[name="denominazioneAccertamento"]').attr('readonly', 'true');
				$('input[name="codiceAccertamento"]').attr('readonly', 'true');
				$('input[name="codiceUfficio"]').attr('readonly', 'true');
				$('input[name="annoEsercizio"]').attr('readonly', 'true');
				document.getElementById('flgAttivo').setAttribute( "onClick", "return false;" );
				}

			if (ente != "R_VENETO"){
				if ("${anagUffCapAccDto.codUfficio}"!="n/a") {
					$('input[name="codiceUfficio"]').attr('readonly', 'true');
				} else {
					$('input[name="codiceUfficio"]').val("");
				}
				if ("${anagUffCapAccDto.deUfficio}"=="n/a"){
					$('input[name="denominazioneUfficio"]').val("");
				}
				
				if ("${anagUffCapAccDto.codAccertamento}"!="n/a") {
					$('input[name="codiceAccertamento"]').attr('readonly', 'true');
				} else {
					$('input[name="codiceAccertamento"]').val("");
				}
				if ("${anagUffCapAccDto.deAccertamento}"=="n/a"){
					$('input[name="denominazioneAccertamento"]').val("");
				}
			}
			
		}
		

		
	function getAccertamenti(){
			
			$.ajax({type: "GET",
				url: "/mypivot/protected/accertamentiAnagrafiche/getAccertamenti.json",
				data: 
					{
					codiceUfficio:"${anagUffCapAccDto.codUfficio}",
					codiceCapitolo:"${anagUffCapAccDto.codCapitolo}"
					},	
			success:function(result){
					
					listaAccertamenti = result;
					
					},
			error:function(error){
				
				listaAccertamenti = error;
					} 
			});
		}


	function inizializza(){
		getAccertamenti();
		isVeneto();
	}
	 

		window.onload=inizializza;

</script>

<div id="dialog-info-accertamento" title="Info" style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
		<spring:message code="mypivot.anagrafica.dialog.codiceAccertamentoEsistenteModifica" />
	</p>
</div>


<div style="display: none;">
	<form action="dettaglioAnagrafica.html" id="visualizzaDettaglioAnagraficaForm" method="get">
		<input id="id" type="hidden" name="id"/>
	</form>
</div>

<div id="dialog-info" title="Info" style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
		<spring:message code="mypivot.anagrafica.dialog.flgAttivo" />
	</p>
</div>

<div>
	
	<div class="titolo-pagina">
		<h1>
			<span class="txt-titolo"><spring:message code="mypivot.anagrafica.modificaAnagrafica"/></span>
		</h1>
	</div>
	
	<div class="row-fluid"></div>
	
	<div class="row-fluid">
		<div class="alert alert-error <c:if test="${esitoMsg==null || esitoMsg==''}">hidden</c:if>">
			<h4> <spring:message code="mypivot.messages.info" /> :</h4> 
			<c:if test="${esitoMsg=='EXIST'}">
				<spring:message code="mypivot.anagrafica.info.updateAnagrafica.ko.exist"/>
			</c:if>
		</div>
	</div>


<c:url var="url" value="/protected/accertamentiAnagrafiche/modificaAnagrafica.html" />
<form:form action="${url}" modelAttribute="anagraficaUfficioCapitoloAccertamentoCommand" id="modificaAnagraficaForm" method="post" class="form-horizontal">

<div class="row-fluid">

<div class="divPadding">


	<!-- TIPO DOVUTO -->
	 <div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.tipoDovuto"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span12 control-label">
					<div class="span1 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.tipoDovuto"/>:</label>
					</div>
					
					<div class="span11 data-column">
						<form:select multiple="true" name="codTipoDovuto" id="codTipoDovuto" class="input-block-level" path="codTipoDovuto">
							<c:forEach var="ctd" items="${listaTipiDovuti}">
								<form:option value="${ctd.codTipo}">
									<c:out value="${ctd.deTipo}"></c:out>
								</form:option>
							</c:forEach>  
						</form:select>
					</div>
					
					
				<form:errors path="codTipoDovuto" cssClass="error" />
				</div>
			</div>
		</div>
	</div> 

	<!-- UFFICIO -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.datiUfficio"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span3 control-label">
					<div class="span4 label-column">
						<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
					</div>
					<div class="span8 data-column">
						<form:input path="codiceUfficio" class="input-block-level" name="codiceUfficio" type="text" placeholder="" />
					</div>
					<form:errors path="codiceUfficio" cssClass="error" />
				</div>
			
				<div class="span5 control-label">
					<div class="span5 label-column">
						<label><spring:message code="mypivot.anagrafica.denominazione"/>:</label>
					</div>
					<div class="span7 data-column">
						<form:input path="denominazioneUfficio" class="input-block-level" name="denominazioneUfficio" type="text" placeholder=""/>
					</div>
					<form:errors path="denominazioneUfficio" cssClass="error" />
				</div>
				
				<div class="span3 control-label">
					<div class="span8 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.filter.flgAttivo" />:</label>
					</div>
					<div class="span4 data-column">
						<form:checkbox  path="flgAttivo" class="input-block-level" name="flgAttivo" id="flgAttivo" onclick="flgClick('${anagraficaUfficioCapitoloAccertamentoCommand.flgAttivo}',this)"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- CAPITOLO -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.datiCapitolo"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span3 control-label">
					<div class="span4 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.codice"/>:</label>
					</div>
					<div class="span8 data-column">
						<form:input path="codiceCapitolo" class="input-block-level" name="codiceCapitolo" type="text" readonly="true" placeholder="" />
					</div>
					<form:errors path="codiceCapitolo" cssClass="error" />
				</div>
			
				<div class="span5 control-label">
					<div class="span5 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.denominazione"/>:</label>
					</div>
					<div class="span7 data-column">
						<form:input path="denominazioneCapitolo" class="input-block-level" name="denominazioneCapitolo" type="text" placeholder="" />
					</div>
					<form:errors path="denominazioneCapitolo" cssClass="error" />
				</div>
				
				<div class="span4 control-label">
					<div class="span6 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.annoEsercizio"/>:</label>
					</div>
					<div class="span6 data-column">
						<form:input path="annoEsercizio" class="input-block-level" name="annoEsercizio" type="text" placeholder="" />
					</div>
					<form:errors path="annoEsercizio" cssClass="error" />
				</div>
			</div>
		</div>
	</div>
	
	<!-- ACCERTAMENTO	 -->
	<div class="dettaglio segnalazione mypay-custom small-text">
		<div class="testata"><h2><spring:message code="mypivot.anagrafica.datiAccertamento"/></h2></div>
		<div class="corpo form-horizontal">
			<div class="row-fluid">
				<div class="span3 control-label">
					<div class="span4 label-column">
						<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
					</div>
					<div class="span8 data-column">
						<form:input path="codiceAccertamento" class="input-block-level" name="codiceAccertamento" type="text" placeholder="" onchange="controllaCodAcc(this)"/>
					</div>
					<form:errors path="codiceAccertamento" cssClass="error" />
				</div>
			
				<div class="span5 control-label">
					<div class="span5 label-column">
						<label><spring:message code="mypivot.anagrafica.denominazione"/>:</label>
					</div>
					<div class="span7 data-column">
						<form:input path="denominazioneAccertamento" class="input-block-level" name="denominazioneAccertamento" type="text" placeholder="" />
					</div>
					<form:errors path="denominazioneAccertamento" cssClass="error" />
				</div>
			</div>
		</div>
	</div>

</div><!-- /<div class="divPadding"> -->

</div><!-- /<div class="row-fluid"> -->

<div class="well carrello clearfix">
		<div class="btn-group pull-left">
		</div><!-- /<div class="btn-group pull-left"> -->
		
		<div class="btn-group pull-right">
		
			<a href="javascript:void(0);" onclick="visualizzaDettaglio('${anagUffCapAccDto.id}')" class="btn btn-primary btn-large">
				<i class="fa fa-trash-o fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.annullaModifica"/>						
			</a>
				
				
			<button type="submit" class="btn btn-success btn-large" value="anagraficaUfficioCapitoloAccertamentoCommand">
				<i class="fa fa-save fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.salvaAnagrafica"/>
			</button>  
			
		</div>	<!-- /<div class="btn-group pull-right"> -->
</div><!-- /<div class="well carrello clearfix"> -->


<form:input path="flgAttivoCambiato" id="flgAttivoCambiato" name="flgAttivoCambiato" type="hidden" />
<form:input path="anagraficaUffCapAccId" name="anagraficaUffCapAccId" type="hidden" />

<!-- Input hidden per conservazione old dto -->
<form:input path="idDto" type="hidden" name="idDto"/>
<form:input path="codAccertamentoDto" type="hidden" name="codAccertamentoDto"/>
<form:input path="codCapitoloDto" type="hidden" name="codCapitoloDto"/>
<form:input path="codTipoDovutoDto" type="hidden" name="codTipoDovutoDto"/>
<form:input path="deTipoDovutoDto" type="hidden" name="deTipoDovutoDto"/>
<form:input path="codUfficioDto" type="hidden" name="codUfficioDto"/>
<form:input path="deAccertamentoDto" type="hidden" name="deAccertamentoDto"/>
<form:input path="deAnnoEsercizioDto" type="hidden" name="deAnnoEsercizioDto"/>
<form:input path="deCapitoloDto" type="hidden" name="deCapitoloDto"/>
<form:input path="deUfficioDto" type="hidden" name="deUfficioDto"/>
<form:input path="flgAttivoDto" type="hidden" name="flgAttivoDto"/>

</form:form>

</div>