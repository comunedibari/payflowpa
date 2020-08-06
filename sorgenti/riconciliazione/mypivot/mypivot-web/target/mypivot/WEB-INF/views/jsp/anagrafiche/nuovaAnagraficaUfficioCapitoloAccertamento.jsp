<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.regioneveneto.mygov.payment.mypivot.utils.Constants"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="java.net.URLEncoder"%>

<script type="text/javascript" charset="UTF-8">

var listaAccertamenti;

function riempiDisabilitaUfficio(ufficioSelezionato){
	
		var form = $('#nuovaAnagraficaForm');
		
		var val = ufficioSelezionato.value; 
		
		var tt = "${listaUffici}";
		var map = {};
		var l=0;
		
		<c:forEach items="${listaUffici}" var="item" varStatus="loop">
			var myObject = new Object();
			myObject.id = "${item.id}";
			myObject.codiceUfficio = "${item.codUfficio}";
			myObject.denominazioneUfficio = "${item.deUfficio}";
			myObject.flgAttivo = "${item.flgAttivo}";
			map[${loop.index}]=myObject;
			l++;
		</c:forEach>

		if(val!=""){
			
			for (i=0; i<l; i++){
				var app = map[i].id;
				if(app == val){
					
					form.find('#codiceUfficio').attr('readonly',true);
					form.find('#codiceUfficio').val(map[i].codiceUfficio);

					form.find('#denominazioneUfficio').attr('readonly',true);
					form.find('#denominazioneUfficio').val(map[i].denominazioneUfficio);
					
					form.find('#flgAttivo').attr('onclick','return false;');
					var bool = map[i].flgAttivo;
					
					form.find('#flgAttivo').val(bool);
					
					if(bool == "false")
						form.find('#flgAttivo').removeAttr("checked");
					else
						if (bool == "true")
							form.find('#flgAttivo').attr('checked','checked');
					
				}
			}
			
			if(form.find('#codiceCapitolo').val() != "" && form.find('#codiceCapitolo').val() != null){

				getAccertamenti(form.find('#codiceCapitolo').val(), form.find('#codiceUfficio').val());
			}
			
			
		}else{
			form.find('#codiceUfficio').attr('readonly',false);
			form.find('#codiceUfficio').val('');

			form.find('#denominazioneUfficio').attr('readonly',false);
			form.find('#denominazioneUfficio').val('');

			form.find('#flgAttivo').attr('onclick','');
			form.find('#flgAttivo').val('');
			}
	}


function riempiDisabilitaCapitolo(capitoloSelezionato){

	var form = $('#nuovaAnagraficaForm');
	
	var val = capitoloSelezionato.value; 

	var tt = "${listaCapitoli}";
	var map = {};
	var l=0;


	<c:forEach items="${listaCapitoli}" var="item" varStatus="loop">
		var myObject = new Object();
		myObject.id = "${item.id}";
		myObject.codiceCapitolo = "${item.codCapitolo}";
		myObject.denominazioneCapitolo = "${item.deCapitolo}";
		myObject.annoEsercizio = "${item.deAnnoEsercizio}";
		map[${loop.index}]=myObject;
		l++;
	</c:forEach>
	
	form.find('#annoEsercizio').attr('readonly',false);
	form.find('#annoEsercizio').val('');
	
	if(val!=""){

		for (i=0; i<l; i++){

			var app = map[i].id;
			if(app == val){

				form.find('#codiceCapitolo').attr('readonly',true);
				form.find('#codiceCapitolo').val(map[i].codiceCapitolo);

				form.find('#denominazioneCapitolo').attr('readonly',true);
				form.find('#denominazioneCapitolo').val(map[i].denominazioneCapitolo);				
			}
			
		}
		
		if(form.find('#codiceUfficio').val() != "" && form.find('#codiceUfficio').val() != null){

			getAccertamenti(form.find('#codiceCapitolo').val(), form.find('#codiceUfficio').val());
			}
		
	}else{
		form.find('#codiceCapitolo').attr('readonly',false);
		form.find('#codiceCapitolo').val('');

		form.find('#denominazioneCapitolo').attr('readonly',false);
		form.find('#denominazioneCapitolo').val('');
	}
}

function riempiDisabilitaAccertamento(accertamentoSelezionato){

var form = $('#nuovaAnagraficaForm');
	
	var val = accertamentoSelezionato.value; 

	if(val!=""){

		listaAccertamenti.forEach(function(cap, index){
			var app = cap.id;
			if(app == val){

				form.find('#codiceAccertamento').attr('readonly',true);
				form.find('#codiceAccertamento').val(cap.codAccertamento);

				form.find('#denominazioneAccertamento').attr('readonly',true);
				form.find('#denominazioneAccertamento').val(cap.deAccertamento);
				
			}
		});

		
	}else{
		form.find('#codiceAccertamento').attr('readonly',false);
		form.find('#codiceAccertamento').val('');

		form.find('#denominazioneAccertamento').attr('readonly',false);
		form.find('#denominazioneAccertamento').val('');

		}
}


function controllaCodUff(codUff){


	var listaUffici = "${listaUffici}";
	var map = {};
	var l=0;
	<c:forEach items="${listaUffici}" var="item" varStatus="loop">
		var myObject = new Object();
		myObject.codiceUfficio = "${item.codUfficio}";
		map[${loop.index}]=myObject;
		l++;
	</c:forEach>

	var cod = codUff.value;
	for (i=0; i<l; i++){
		var app = map[i].codiceUfficio;
		if(app == cod){
			$( "#dialog-info-ufficio" ).dialog({
			      resizable: false,
			      height: "auto",
			      width: 400,
			      modal: true,
			      buttons: {
			        "Continua": function() {
			           $( this ).dialog( "close" );
			           var form = $('#nuovaAnagraficaForm');
			           form.find('#codiceUfficio').val('');
			        }
			      }
			});
		}
	}
}

function controllaCodCap(codCap){


	var listaCapitoli = "${listaCapitoli}";
	var map = {};
	var l=0;
	<c:forEach items="${listaCapitoli}" var="item" varStatus="loop">
		var myObject = new Object();
		myObject.codiceCapitolo = "${item.codCapitolo}";
		map[${loop.index}]=myObject;
		l++;
	</c:forEach>

	var cod = codCap.value;
	for (i=0; i<l; i++){
		var app = map[i].codiceCapitolo;
		if(app == cod){
			$( "#dialog-info-capitolo" ).dialog({
			      resizable: false,
			      height: "auto",
			      width: 400,
			      modal: true,
			      buttons: {
			        "Continua": function() {
			           $( this ).dialog( "close" );
			           var form = $('#nuovaAnagraficaForm');
			           form.find('#codiceCapitolo').val('');
			        }
			      }
			});
			}
		}
}

function controllaCodAcc(codAcc){
	
	var selAccertamenti = document.getElementById("elencoAccertamenti");
	var cod = codAcc.value;
	var i=0;
	while (i < selAccertamenti.options.length) {
		var acc = selAccertamenti.options[i].value;

		if(acc != ""){
			var app = JSON.parse(acc);
			if(app['codAccertamento'] == cod){
				$( "#dialog-info-accertamento" ).dialog({
				      resizable: false,
				      height: "auto",
				      width: 400,
				      modal: true,
				      buttons: {
				        "Continua": function() {
				           $( this ).dialog( "close" );
				           var form = $('#nuovaAnagraficaForm');
				           form.find('#codiceAccertamento').val('');
				        }
				      }
				});
			}
		}
		i++;
    }
}


function getAccertamenti(codUff, codCap){

	var selAccertamenti = document.getElementById("elencoAccertamenti");
	$('#elencoAccertamenti option[value!=""]').remove();

	$.ajax({type: "GET",
		url: "/mypivot/protected/accertamentiAnagrafiche/getAccertamenti.json",
		data: 
			{
			codiceUfficio:codUff,
			codiceCapitolo:codCap
			},	
	success:function(result){
			
			var selAccertamenti = document.getElementById("elencoAccertamenti");
			var fragOpt = document.createDocumentFragment();
			listaAccertamenti = result;
			result.forEach(function(cap, index){
				var opt = document.createElement("option");
				opt.innerHTML = cap.deAccertamento;
				opt.value = cap.id;
				fragOpt.appendChild(opt);
				});

			selAccertamenti.appendChild(fragOpt);
			
			},
	error:function(error){
			
			} 
	});
}

</script>


<div>

<div id="dialog-info-ufficio" title="Info" style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
		<spring:message code="mypivot.anagrafica.dialog.codiceUfficioEsistente" />
	</p>
</div>

<div id="dialog-info-capitolo" title="Info" style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
		<spring:message code="mypivot.anagrafica.dialog.codiceCapitoloEsistente" />
	</p>
</div>

<div id="dialog-info-accertamento" title="Info" style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
		<spring:message code="mypivot.anagrafica.dialog.codiceAccertamentoEsistente" />
	</p>
</div>

<div class="titolo-pagina">
		<h1>
			<span class="txt-titolo"><spring:message code="mypivot.anagrafica.nuovaAnagrafica"/></span>
		</h1>
	</div>
	
	<div class="row-fluid">
		<div class="alert alert-info">
			<h4> <spring:message code="mypivot.messages.info" /> :</h4> 
			<spring:message code="mypivot.anagrafica.info.flgAttivo"/>
		</div>
	</div>
	
	<div class="row-fluid">
		<div class="alert alert-error <c:if test="${esitoMsg==null || esitoMsg==''}">hidden</c:if>">
			<h4> <spring:message code="mypivot.messages.info" /> :</h4> 
			<c:if test="${esitoMsg=='EXIST'}">
				<spring:message code="mypivot.anagrafica.info.inserimentoAnagrafica.exist"/>
			</c:if>
			<c:if test="${esitoMsg=='ERROR'}">
				<spring:message code="mypivot.anagrafica.info.inserimentoAnagrafica.ko"/>
			</c:if>
		</div>
	</div>


<c:url var="url" value="/protected/accertamentiAnagrafiche/inserisciAnagrafica.html" />
<form:form action="${url}" modelAttribute="anagraficaUfficioCapitoloAccertamentoCommand" id="nuovaAnagraficaForm" method="post" class="form-horizontal">

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
						<form:select class="input-block-level" path="codTipoDovuto">
							<form:option value=""><spring:message code="mypivot.anagrafica.select.seleziona"></spring:message></form:option>
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
				<div class="span8 control-label">
					<div class="span2 label-column">
						<label><spring:message code="mypivot.anagrafica.ufficiPresenti"/>:</label>
					</div>
					<div class="span10 data-column">
						<select class="input-block-level" name="elencoUffici" id="elencoUffici" onchange="riempiDisabilitaUfficio(this)">
							<option value=""><spring:message code="mypivot.anagrafica.select.seleziona"></spring:message></option>
							<c:forEach var="ctd" items="${listaUffici}">
								<option value="${ctd.id}">
									<c:out value="${ctd.deUfficio}"></c:out>
								</option>
							</c:forEach>  
						</select>
					</div>
				</div>
			</div>
			<div class="row-fluid">
			</div>
			
			
			<div class="row-fluid">
				<div class="span3 control-label">
					<div class="span4 label-column">
						<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
					</div>
					<div class="span8 data-column">
						<form:input path="codiceUfficio" class="input-block-level" name="codiceUfficio" id="codiceUfficio" type="text" readonly="false" placeholder="" onchange="controllaCodUff(this)"/>
					</div>
					<form:errors path="codiceUfficio" cssClass="error" />
				</div>
			
				<div class="span5 control-label">
					<div class="span5 label-column">
						<label><spring:message code="mypivot.anagrafica.denominazione"/>:</label>
					</div>
					<div class="span7 data-column">
						<form:input path="denominazioneUfficio" class="input-block-level" name="denominazioneUfficio" id="denominazioneUfficio" type="text" readonly="false" placeholder="" />
					</div>
					<form:errors path="denominazioneUfficio" cssClass="error" />
				</div>
				
				<div class="span3 control-label">
					<div class="span8 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.filter.flgAttivo" />:</label>
					</div>
					<div class="span4 data-column">
						<form:checkbox  path="flgAttivo" class="input-block-level" name="flgAttivo" id="flgAttivo" value="true" checked="checked" onclick=""/>
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
				<div class="span8 control-label">
					<div class="span2 label-column">
						<label><spring:message code="mypivot.anagrafica.capitoliPresenti"/>:</label>
					</div>
					<div class="span10 data-column">
						<select class="input-block-level" name="elencoCapitoli" id="elencoCapitoli" onchange="riempiDisabilitaCapitolo(this)">
							<option value=""><spring:message code="mypivot.anagrafica.select.seleziona"></spring:message></option>
							<c:forEach var="ctd" items="${listaCapitoli}">
								<option value="${ctd.id}">
									<c:out value="${ctd.deCapitolo}"></c:out>
								</option>
							</c:forEach>   
						</select>
					</div>
				</div>
			</div>
			<div class="row-fluid">
			</div>
		
		
			<div class="row-fluid">
				<div class="span3 control-label">
					<div class="span4 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.codice"/>:</label>
					</div>
					<div class="span8 data-column">
						<form:input path="codiceCapitolo" class="input-block-level" name="codiceCapitolo" id="codiceCapitolo" type="text" readonly="false" placeholder="" onchange="controllaCodCap(this)" />
					</div>
					<form:errors path="codiceCapitolo" cssClass="error" />
				</div>
			
				<div class="span5 control-label">
					<div class="span5 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.denominazione"/>:</label>
					</div>
					<div class="span7 data-column">
						<form:input path="denominazioneCapitolo" class="input-block-level" name="denominazioneCapitolo" id="denominazioneCapitolo" type="text" readonly="false" placeholder="" />
					</div>
					<form:errors path="denominazioneCapitolo" cssClass="error" />
				</div>
				
				<div class="span4 control-label">
					<div class="span6 label-column">
						<label><span class="required">* </span><spring:message code="mypivot.anagrafica.annoEsercizio"/>:</label>
					</div>
					<div class="span6 data-column">
						<form:input path="annoEsercizio" class="input-block-level" name="annoEsercizio" id="annoEsercizio" type="text" readonly="false" placeholder="" />
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
				<div class="span8 control-label">
					<div class="span2 label-column">
						<label><spring:message code="mypivot.anagrafica.AccertamentiPresenti"/>:</label>
					</div>
					<div class="span10 data-column">
						<select class="input-block-level" name="elencoAccertamenti" id="elencoAccertamenti" onchange="riempiDisabilitaAccertamento(this)">
							<option value=""><spring:message code="mypivot.anagrafica.select.seleziona"></spring:message></option>
						</select>
					</div>
				</div>
			</div>
			<div class="row-fluid">
			</div>
		
		
		
			<div class="row-fluid">
				<div class="span3 control-label">
					<div class="span4 label-column">
						<label><spring:message code="mypivot.anagrafica.codice"/>:</label>
					</div>
					<div class="span8 data-column">
						<form:input path="codiceAccertamento" class="input-block-level" name="codiceAccertamento" type="text" readonly="false" placeholder="" onchange="controllaCodAcc(this)" />
					</div>
					<form:errors path="codiceAccertamento" cssClass="error" />
				</div>
			
				<div class="span5 control-label">
					<div class="span5 label-column">
						<label><spring:message code="mypivot.anagrafica.denominazione"/>:</label>
					</div>
					<div class="span7 data-column">
						<form:input path="denominazioneAccertamento" class="input-block-level" name="denominazioneAccertamento" type="text" readonly="false" placeholder="" />
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
		
			<a href="/mypivot/protected/accertamentiAnagrafiche/ricerca.html?tab=U" class="btn btn-primary btn-large">
				<i class="fa fa-trash-o fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.annullaInserimento"/>
			</a>
					
			<button type="submit" class="btn btn-success btn-large" value="anagraficaUfficioCapitoloAccertamentoCommand">
				<i class="fa fa-save fa-lg"></i>&nbsp;&nbsp;&nbsp;
				<spring:message code="mypivot.dettaglio.btn.salvaAnagrafica"/>
			</button> 
			
		</div>	<!-- /<div class="btn-group pull-right"> -->
</div><!-- /<div class="well carrello clearfix"> -->

<form:input path="flgAttivoCambiato" id="flgAttivoCambiato" name="flgAttivoCambiato" type="hidden" />

</form:form>

</div>