<%@page import="it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.net.URLEncoder"%>
<script type="text/javascript">
    function visualizzaStoricoCompletoSegnalazioni(){
        var form = $('#visualizzaStoricoCompletoSegnalazioni');
        form.submit();
    }
    function visualizzaDettaglio(classificazioneCompletezza, codIuv, codIuf, codIud){
        var form = $('#visualizzaDettaglioForm');
        form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
        form.find('#codiceIuv').val(codIuv);
        form.find('#codiceIuf').val(codIuf);
        form.find('#codiceIud').val(codIud);
        form.submit();
    }
    function visualizzaStoricoSegnalazioni(classificazioneCompletezza, codIuv, codIuf, codIud){
        var form = $('#visualizzaStoricoSegnalazioniForm');
        form.find('#classificazioneCompletezza').val(classificazioneCompletezza);
        form.find('#codiceIuv').val(codIuv);
        form.find('#codiceIuf').val(codIuf);
        form.find('#codiceIud').val(codIud);
        form.find('.enable-field').val(true);
        form.submit();
    }

	$(document).ready(function() {
		var esecuzioneCheckedAttribute = $("#data_esecuzione_check").is(":checked") ? 'enable' : 'disable';
		var esitoCheckedAttribute = $("#data_esito_check").is(":checked") ? 'enable' : 'disable';
		var regolamentoCheckedAttribute = $("#data_regolamento_check").is(":checked") ? 'enable' : 'disable';
		var contabileCheckedAttribute = $("#data_contabile_check").is(":checked") ? 'enable' : 'disable';
		var valutaCheckedAttribute = $("#data_valuta_check").is(":checked") ? 'enable' : 'disable';
		var ultimoAggiornamentoCheckedAttribute = $("#data_ultimo_aggiornamento_check").is(":checked") ? 'enable' : 'disable';
		$("#data_esecuzione_singolo_pagamento_da").datepicker(esecuzioneCheckedAttribute);
		$("#data_esecuzione_singolo_pagamento_a").datepicker(esecuzioneCheckedAttribute);
		$("#data_esito_singolo_pagamento_da").datepicker(esitoCheckedAttribute);
		$("#data_esito_singolo_pagamento_a").datepicker(esitoCheckedAttribute);
		$("#data_regolamento_da").datepicker(regolamentoCheckedAttribute);
		$("#data_regolamento_a").datepicker(regolamentoCheckedAttribute);
		$("#data_contabile_da").datepicker(contabileCheckedAttribute);
		$("#data_contabile_a").datepicker(contabileCheckedAttribute);
		$("#data_valuta_da").datepicker(valutaCheckedAttribute);
		$("#data_valuta_a").datepicker(valutaCheckedAttribute);
		$("#data_ultimo_aggiornamento_da").datepicker(ultimoAggiornamentoCheckedAttribute);
		$("#data_ultimo_aggiornamento_a").datepicker(ultimoAggiornamentoCheckedAttribute);
		
	    $("#importo").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter, comma and . and cmd
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 188, 190, 91]) !== -1 || e.metaKey ||
	        		// Allow: Ctrl+A
		            (e.keyCode == 65 && e.ctrlKey === true) ||
		             // Allow: Ctrl+C
		            (e.keyCode == 67 && e.ctrlKey === true) ||
		            // Allow: Ctrl+V
		            (e.keyCode == 86 && e.ctrlKey === true) ||
		             // Allow: Ctrl+X
		            (e.keyCode == 88 && e.ctrlKey === true) ||
	             // Allow: home, end, left, right
	            (e.keyCode >= 35 && e.keyCode <= 39)
	        ) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	    
	    adjustFilters();
	});
	
    function adjustFilters(){

    	var value = $("#tipoErroreCombo").val();
    	switch(value) {
    	case 'IUD_RT_IUF_TES':
        	$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',false);
        	$("#denominazione_attestante").prop('disabled',false);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#codOr1").prop('disabled',false);
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',false);
        	$("#importo").prop('disabled',false);
        	$("#anagrafica_versante").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',false);
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',false);
        	$("#data_esecuzione_check").prop('disabled',false);
        	if($("#data_esecuzione_check").is(":checked")){
        		$("#data_esecuzione_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esecuzione_singolo_pagamento_a").prop('disabled',false);
        	}
        	$("#data_esito_check").prop('disabled',false);
        	if($("#data_esito_check").is(":checked")){
        		$("#data_esito_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esito_singolo_pagamento_a").prop('disabled',false);
        	}
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	$("#data_contabile_check").prop('disabled',false);
        	if($("#data_contabile_check").is(":checked")){
        		$("#data_contabile_da").prop('disabled',false);
        		$("#data_contabile_a").prop('disabled',false);
        	}
        	$("#data_valuta_check").prop('disabled',false);
        	if($("#data_valuta_check").is(":checked")){
        		$("#data_valuta_da").prop('disabled',false);
        		$("#data_valuta_a").prop('disabled',false);
        	}
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
    		break;
    	case 'IUD_RT_IUF':
        	$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',false);
        	$("#denominazione_attestante").prop('disabled',false);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#codOr1").prop('disabled',true);
        	$("#codOr1").prop("value","");
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',false);
        	$("#importo").prop('disabled',true);
        	$("#importo").prop("value","");
        	$("#anagrafica_versante").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',false);
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',true);
        	$("#conto").prop("value","");
        	$("#data_esecuzione_check").prop('disabled',false);
        	if($("#data_esecuzione_check").is(":checked")){
        		$("#data_esecuzione_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esecuzione_singolo_pagamento_a").prop('disabled',false);
        	}
        	$("#data_esito_check").prop('disabled',false);
        	if($("#data_esito_check").is(":checked")){
        		$("#data_esito_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esito_singolo_pagamento_a").prop('disabled',false);
        	}
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	$("#data_contabile_check").prop('disabled',true);
        	$("#data_contabile_check").prop('checked',false);
        	$("#data_contabile_da").datepicker("disable");
        	$("#data_contabile_a").datepicker("disable");
        	
        	$("#data_valuta_check").prop('disabled',true);
        	$("#data_valuta_check").prop('checked',false);
        	$("#data_valuta_da").datepicker("disable");
        	$("#data_valuta_a").datepicker("disable");
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
    		break;
    	case 'RT_IUF_TES':
    	case 'RT_NO_IUD':
        	$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',false);
        	$("#denominazione_attestante").prop('disabled',false);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#codOr1").prop('disabled',false);
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',false);
        	$("#importo").prop('disabled',false);
        	$("#anagrafica_versante").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',false);
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',false);
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',false);
        	if($("#data_esito_check").is(":checked")){
        		$("#data_esito_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esito_singolo_pagamento_a").prop('disabled',false);
        	}
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	$("#data_contabile_check").prop('disabled',false);
        	if($("#data_contabile_check").is(":checked")){
        		$("#data_contabile_da").prop('disabled',false);
        		$("#data_contabile_a").prop('disabled',false);
        	}
        	$("#data_valuta_check").prop('disabled',false);
        	if($("#data_valuta_check").is(":checked")){
        		$("#data_valuta_da").prop('disabled',false);
        		$("#data_valuta_a").prop('disabled',false);
        	}
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
    		break;
    	case 'RT_IUF':
    		$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',false);
        	$("#denominazione_attestante").prop('disabled',false);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',false);
        	$("#anagrafica_versante").prop('disabled',false);
        	$("#codOr1").prop('disabled',true);
        	$("#codOr1").prop("value","");
        	$("#importo").prop('disabled',true);
        	$("#importo").prop("value","");
        	$("#causale_versamento").prop('disabled',true);
        	$("#causale_versamento").prop("value","");
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',true);
        	$("#conto").prop("value","");
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',false);
        	if($("#data_esito_check").is(":checked")){
        		$("#data_esito_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esito_singolo_pagamento_a").prop('disabled',false);
        	}
        	
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	
        	$("#data_contabile_check").prop('disabled',true);
        	$("#data_contabile_check").prop('checked',false);
        	$("#data_contabile_da").datepicker("disable");
        	$("#data_contabile_a").datepicker("disable");
        	
        	$("#data_valuta_check").prop('disabled',true);
        	$("#data_valuta_check").prop('checked',false);
        	$("#data_valuta_da").datepicker("disable");
        	$("#data_valuta_a").datepicker("disable");
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
            
        	break;
        case 'RT_NO_IUF':
        	$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',false);
        	$("#denominazione_attestante").prop('disabled',false);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',false);
        	$("#anagrafica_versante").prop('disabled',false);
        	$("#codOr1").prop('disabled',true);
        	$("#codOr1").prop("value","");
        	$("#importo").prop('disabled',true);
        	$("#importo").prop("value","");
        	$("#causale_versamento").prop('disabled',true);
        	$("#causale_versamento").prop("value","");
        	$("#identificativo_flusso_rendicontazione").prop('disabled',true);
        	$("#identificativo_flusso_rendicontazione").prop("value","");
        	$("#identificativo_univoco_regolamento").prop('disabled',true);
        	$("#identificativo_univoco_regolamento").prop("value","");
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',true);
        	$("#conto").prop("value","");
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',false);
        	if($("#data_esito_check").is(":checked")){
        		$("#data_esito_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esito_singolo_pagamento_a").prop('disabled',false);
        	}
        	
        	$("#data_regolamento_check").prop('disabled',true);
        	$("#data_regolamento_check").prop('checked',false);
        	$("#data_regolamento_da").datepicker("disable");
        	$("#data_regolamento_a").datepicker("disable");
        	
        	$("#data_contabile_check").prop('disabled',true);
        	$("#data_contabile_check").prop('checked',false);
        	$("#data_contabile_da").datepicker("disable");
        	$("#data_contabile_a").datepicker("disable");
        	
        	$("#data_valuta_check").prop('disabled',true);
        	$("#data_valuta_check").prop('checked',false);
        	$("#data_valuta_da").datepicker("disable");
        	$("#data_valuta_a").datepicker("disable");
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
            
        	break;
        case 'IUF_NO_TES':
        	$("#codice_iud").prop('disabled',true);
        	$("#codice_iud").prop("value","");
        	$("#codice_iuv").prop('disabled',true);
        	$("#codice_iuv").prop("value","");
        	$("#identificativo_univoco_riscossione").prop('disabled',true);
        	$("#identificativo_univoco_riscossione").prop("value","");
        	$("#denominazione_attestante").prop('disabled',true);
        	$("#denominazione_attestante").prop("value","");
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',true);
        	$("#codice_identificativo_univoco_pagatore").prop("value","");
        	$("#anagrafica_pagatore").prop('disabled',true);
        	$("#anagrafica_pagatore").prop("value","");
        	$("#codice_identificativo_univoco_versante").prop('disabled',true);
        	$("#codice_identificativo_univoco_versante").prop("value","");
        	$("#anagrafica_versante").prop('disabled',true);
        	$("#anagrafica_versante").prop("value","");
        	$("#codOr1").prop('disabled',true);
        	$("#codOr1").prop("value","");
        	$("#importo").prop('disabled',true);
        	$("#importo").prop("value","");
        	$("#causale_versamento").prop('disabled',true);
        	$("#causale_versamento").prop("value","");
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',true);
        	$("#conto").prop("value","");
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',true);
        	$("#data_esito_check").prop("checked",false);
        	$("#data_esito_singolo_pagamento_da").datepicker("disable");
        	$("#data_esito_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	
        	$("#data_contabile_check").prop('disabled',true);
        	$("#data_contabile_check").prop('checked',false);
        	$("#data_contabile_da").datepicker("disable");
        	$("#data_contabile_a").datepicker("disable");
        	
        	$("#data_valuta_check").prop('disabled',true);
        	$("#data_valuta_check").prop('checked',false);
        	$("#data_valuta_da").datepicker("disable");
        	$("#data_valuta_a").datepicker("disable");
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
            
        	break;        	
        case 'IUV_NO_RT':
        	$("#codice_iud").prop('disabled',true);
        	$("#codice_iud").prop("value","");
        	$("#codice_iuv").prop('disabled',true);
        	$("#codice_iuv").prop("value","");
        	$("#identificativo_univoco_riscossione").prop('disabled',true);
        	$("#identificativo_univoco_riscossione").prop("value","");
        	$("#denominazione_attestante").prop('disabled',true);
        	$("#denominazione_attestante").prop("value","");
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',true);
        	$("#codice_identificativo_univoco_pagatore").prop("value","");
        	$("#anagrafica_pagatore").prop('disabled',true);
        	$("#anagrafica_pagatore").prop("value","");
        	$("#codice_identificativo_univoco_versante").prop('disabled',true);
        	$("#codice_identificativo_univoco_versante").prop("value","");
        	$("#anagrafica_versante").prop('disabled',true);
        	$("#anagrafica_versante").prop("value","");
        	$("#codOr1").prop('disabled',true);
        	$("#codOr1").prop("value","");
        	$("#importo").prop('disabled',true);
        	$("#importo").prop("value","");
        	$("#causale_versamento").prop('disabled',true);
        	$("#causale_versamento").prop("value","");
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',true);
        	$("#conto").prop('disabled',true);
        	$("#conto").prop("value","");
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',true);
        	$("#data_esito_check").prop("checked",false);
        	$("#data_esito_singolo_pagamento_da").datepicker("disable");
        	$("#data_esito_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	
        	$("#data_contabile_check").prop('disabled',true);
        	$("#data_contabile_check").prop('checked',false);
        	$("#data_contabile_da").datepicker("disable");
        	$("#data_contabile_a").datepicker("disable");
        	
        	$("#data_valuta_check").prop('disabled',true);
        	$("#data_valuta_check").prop('checked',false);
        	$("#data_valuta_da").datepicker("disable");
        	$("#data_valuta_a").datepicker("disable");
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
            
        	break;
        case 'TES_NO_IUF_OR_IUV':
        	$("#codice_iud").prop('disabled',true);
        	$("#codice_iud").prop("value","");
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',true);
        	$("#identificativo_univoco_riscossione").prop("value","");
        	$("#denominazione_attestante").prop('disabled',true);
        	$("#denominazione_attestante").prop("value","");
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',true);
        	$("#codice_identificativo_univoco_pagatore").prop("value","");
        	$("#anagrafica_pagatore").prop('disabled',true);
        	$("#anagrafica_pagatore").prop("value","");
        	$("#codice_identificativo_univoco_versante").prop('disabled',true);
        	$("#codice_identificativo_univoco_versante").prop("value","");
        	$("#anagrafica_versante").prop('disabled',true);
        	$("#anagrafica_versante").prop("value","");
        	$("#codOr1").prop('disabled',false);
        	$("#importo").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',true);
        	$("#causale_versamento").prop("value","");
        	$("#identificativo_flusso_rendicontazione").prop('disabled', false);
        	$("#identificativo_univoco_regolamento").prop('disabled',true);
        	$("#identificativo_univoco_regolamento").prop("value", "");
        	$("#tipoDovutoCombo").prop('disabled',true);
        	$("#conto").prop('disabled',false);
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',true);
        	$("#data_esito_check").prop("checked",false);
        	$("#data_esito_singolo_pagamento_da").datepicker("disable");
        	$("#data_esito_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_regolamento_check").prop('disabled',true);
        	$("#data_regolamento_check").prop('checked',false);
        	$("#data_regolamento_da").datepicker("disable");
        	$("#data_regolamento_a").datepicker("disable");
        	
        	$("#data_contabile_check").prop('disabled',false);
        	if($("#data_contabile_check").is(":checked")){
        		$("#data_contabile_da").prop('disabled',false);
        		$("#data_contabile_a").prop('disabled',false);
        	}
        	
        	$("#data_valuta_check").prop('disabled',false);
        	if($("#data_valuta_check").is(":checked")){
        		$("#data_valuta_da").prop('disabled',false);
        		$("#data_valuta_a").prop('disabled',false);
        	}
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
            break;
        case 'TES_NO_MATCH':
        	$("#codice_iud").prop('disabled',true);
        	$("#codice_iud").prop("value","");
        	$("#codice_iuv").prop('disabled',true);
        	$("#codice_iuv").prop("value","");
        	$("#identificativo_univoco_riscossione").prop('disabled',true);
        	$("#identificativo_univoco_riscossione").prop("value","");
        	$("#denominazione_attestante").prop('disabled',true);
        	$("#denominazione_attestante").prop("value","");
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',true);
        	$("#codice_identificativo_univoco_pagatore").prop("value","");
        	$("#anagrafica_pagatore").prop('disabled',true);
        	$("#anagrafica_pagatore").prop("value","");
        	$("#codice_identificativo_univoco_versante").prop('disabled',true);
        	$("#codice_identificativo_univoco_versante").prop("value","");
        	$("#anagrafica_versante").prop('disabled',true);
        	$("#anagrafica_versante").prop("value","");
        	$("#codOr1").prop('disabled',false);
        	$("#importo").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',false);
        	$("#identificativo_flusso_rendicontazione").prop('disabled', true);
        	$("#identificativo_flusso_rendicontazione").prop("value", "");
        	$("#identificativo_univoco_regolamento").prop('disabled',true);
        	$("#identificativo_univoco_regolamento").prop("value", "");
        	$("#tipoDovutoCombo").prop('disabled',true);
        	$("#conto").prop('disabled',false);
        	
        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',true);
        	$("#data_esito_check").prop("checked",false);
        	$("#data_esito_singolo_pagamento_da").datepicker("disable");
        	$("#data_esito_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_regolamento_check").prop('disabled',true);
        	$("#data_regolamento_check").prop('checked',false);
        	$("#data_regolamento_da").datepicker("disable");
        	$("#data_regolamento_a").datepicker("disable");
        	
        	$("#data_contabile_check").prop('disabled',false);
        	if($("#data_contabile_check").is(":checked")){
        		$("#data_contabile_da").prop('disabled',false);
        		$("#data_contabile_a").prop('disabled',false);
        	}
        	
        	$("#data_valuta_check").prop('disabled',false);
        	if($("#data_valuta_check").is(":checked")){
        		$("#data_valuta_da").prop('disabled',false);
        		$("#data_valuta_a").prop('disabled',false);
        	}
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
            break;
        case 'IUD_NO_RT':
        	$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',true);
        	$("#codice_iuv").prop("value","");
        	$("#identificativo_univoco_riscossione").prop('disabled',true);
        	$("#identificativo_univoco_riscossione").prop('value',"");
        	$("#denominazione_attestante").prop('disabled',true);
        	$("#denominazione_attestante").prop('value',"");
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#codOr1").prop('disabled',true);
        	$("#codOr1").prop('value',"");
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',true);
        	$("#codice_identificativo_univoco_versante").prop('value',"");
        	$("#importo").prop('disabled',true);
        	$("#importo").prop('value',"");
        	$("#anagrafica_versante").prop('disabled',true);
        	$("#anagrafica_versante").prop('value',"");
        	$("#causale_versamento").prop('disabled',false);
        	$("#identificativo_flusso_rendicontazione").prop('disabled',true);
        	$("#identificativo_flusso_rendicontazione").prop('value',"");
        	$("#identificativo_univoco_regolamento").prop('disabled',true);
        	$("#identificativo_univoco_regolamento").prop('value',"");
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',true);
        	$("#conto").prop('value',"");
        	
        	$("#data_esecuzione_check").prop('disabled',false);
        	if($("#data_esecuzione_check").is(":checked")){
        		$("#data_esecuzione_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esecuzione_singolo_pagamento_a").prop('disabled',false);
        	}
        	
        	$("#data_esito_check").prop('disabled',true);
        	$("#data_esito_check").prop("checked",false);
        	$("#data_esito_singolo_pagamento_da").datepicker("disable");
        	$("#data_esito_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_regolamento_check").prop('disabled',true);
        	$("#data_regolamento_check").prop('checked',false);
        	$("#data_regolamento_da").datepicker("disable");
        	$("#data_regolamento_a").datepicker("disable");
        	
        	$("#data_contabile_check").prop('disabled',true);
        	$("#data_contabile_check").prop('checked',false);
        	$("#data_contabile_da").datepicker("disable");
        	$("#data_contabile_a").datepicker("disable");
        	
        	$("#data_valuta_check").prop('disabled',true);
        	$("#data_valuta_check").prop('checked',false);
        	$("#data_valuta_da").datepicker("disable");
        	$("#data_valuta_a").datepicker("disable");
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
    		break;
        case 'RT_TES':
        	$("#codice_iud").prop('disabled',false);
        	$("#codice_iuv").prop('disabled',false);
        	$("#identificativo_univoco_riscossione").prop('disabled',false);
        	$("#denominazione_attestante").prop('disabled',false);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',false);
        	$("#codOr1").prop('disabled',false);
        	$("#anagrafica_pagatore").prop('disabled',false);
        	$("#codice_identificativo_univoco_versante").prop('disabled',false);
        	$("#importo").prop('disabled',false);
        	$("#anagrafica_versante").prop('disabled',false);
        	$("#causale_versamento").prop('disabled',true);
        	$("#identificativo_flusso_rendicontazione").prop('disabled',true);
        	$("#identificativo_univoco_regolamento").prop('disabled',true);
        	$("#tipoDovutoCombo").prop('disabled',false);
        	$("#conto").prop('disabled',false);

        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',false);
        	if($("#data_esito_check").is(":checked")){
        		$("#data_esito_singolo_pagamento_da").prop('disabled',false);
        		$("#data_esito_singolo_pagamento_a").prop('disabled',false);
        	}
        	
        	$("#data_regolamento_check").prop('disabled',true);
        	$("#data_regolamento_check").prop('checked',false);
        	$("#data_regolamento_da").datepicker("disable");
        	$("#data_regolamento_a").datepicker("disable");
        	
        	$("#data_contabile_check").prop('disabled',false);
        	if($("#data_contabile_check").is(":checked")){
        		$("#data_contabile_da").prop('disabled',false);
        		$("#data_contabile_a").prop('disabled',false);
        	}
        	$("#data_valuta_check").prop('disabled',false);
        	if($("#data_valuta_check").is(":checked")){
        		$("#data_valuta_da").prop('disabled',false);
        		$("#data_valuta_a").prop('disabled',false);
        	}
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
    		break;
        case 'IUF_TES_DIV_IMP':
        	$("#codice_iud").prop('disabled',true);
        	$("#codice_iuv").prop('disabled',true);
        	$("#identificativo_univoco_riscossione").prop('disabled',true);
        	$("#denominazione_attestante").prop('disabled',true);
        	$("#codice_identificativo_univoco_pagatore").prop('disabled',true);
        	$("#codOr1").prop('disabled',false);
        	$("#anagrafica_pagatore").prop('disabled',true);
        	$("#codice_identificativo_univoco_versante").prop('disabled',true);
        	$("#importo").prop('disabled',false);
        	$("#anagrafica_versante").prop('disabled',true);
        	$("#causale_versamento").prop('disabled',false);
        	$("#identificativo_flusso_rendicontazione").prop('disabled',false);
        	$("#identificativo_univoco_regolamento").prop('disabled',false);
        	$("#tipoDovutoCombo").prop('disabled',true);
        	$("#conto").prop('disabled',false);

        	$("#data_esecuzione_check").prop('disabled',true);
        	$("#data_esecuzione_check").prop('checked',false);
        	$("#data_esecuzione_singolo_pagamento_da").datepicker("disable");
        	$("#data_esecuzione_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_esito_check").prop('disabled',true);
        	$("#data_esito_check").prop('checked',false);
        	$("#data_esito_singolo_pagamento_da").datepicker("disable");
        	$("#data_esito_singolo_pagamento_a").datepicker("disable");
        	
        	$("#data_regolamento_check").prop('disabled',false);
        	if($("#data_regolamento_check").is(":checked")){
        		$("#data_regolamento_da").prop('disabled',false);
        		$("#data_regolamento_a").prop('disabled',false);
        	}
        	
        	$("#data_contabile_check").prop('disabled',false);
        	if($("#data_contabile_check").is(":checked")){
        		$("#data_contabile_da").prop('disabled',false);
        		$("#data_contabile_a").prop('disabled',false);
        	}
        	$("#data_valuta_check").prop('disabled',false);
        	if($("#data_valuta_check").is(":checked")){
        		$("#data_valuta_da").prop('disabled',false);
        		$("#data_valuta_a").prop('disabled',false);
        	}
        	
        	$("#data_ultimo_aggiornamento_check").prop('disabled',false);
        	if($("#data_ultimo_aggiornamento_check").is(":checked")){
        		$("#data_ultimo_aggiornamento_da").prop('disabled',false);
        		$("#data_ultimo_aggiornamento_a").prop('disabled',false);
        	}
        	
    		break;
        default:
			break;
    	} 
    }

    </script>
<div>

	<div class="home_content">

		<!-- TITOLO PAGINA -->
		<c:if test="${isRiconciliazione}">
			<div class="titolo-pagina">
				<h1>
					<spring:message code="mypivot.visualizzaCompleta.title" /> / <span class="txt-titolo"><spring:message code="mypivot.visualizzaCompleta.title.riconciliazione" /></span>
				</h1>
			</div>
		</c:if>
		<c:if test="${isAnomalie}">
			<div class="titolo-pagina">
				<h1>
					<spring:message code="mypivot.visualizzaCompleta.title" /> / <span class="txt-titolo"><spring:message code="mypivot.visualizzaCompleta.title.anomalie" /></span>
				</h1>
			</div>
		</c:if>

        <div style="display: none;">
            <form action="visualizzaStoricoSegnalazioni.html" id="visualizzaStoricoCompletoSegnalazioni" method="get">
                <input class="enable-field" type="hidden" name="codiceIuvEnabled" value="false" />
                <input class="enable-field" type="hidden" name="codiceIufEnabled" value="false"/>
                <input class="enable-field" type="hidden" name="codiceIudEnabled" value="false"/>
            </form>
            <form action="visualizzaStoricoSegnalazioni.html" id="visualizzaStoricoSegnalazioniForm" method="get">
                <input type="hidden" id="classificazioneCompletezza" name="classificazioneCompletezza"/>
                <input type="hidden" id="codiceIuv" name="codiceIuv"/>
                <input type="hidden" id="codiceIuf" name="codiceIuf"/>
                <input type="hidden" id="codiceIud" name="codiceIud"/>
                <input class="enable-field" type="hidden" name="codiceIuvEnabled"/>
                <input class="enable-field" type="hidden" name="codiceIufEnabled"/>
                <input class="enable-field" type="hidden" name="codiceIudEnabled"/>
            </form>

            <form action="visualizzaDettaglio.html" id="visualizzaDettaglioForm" method="get">
                <input id="classificazioneCompletezza" name="classificazioneCompletezza"/>
                <input id="codiceIuv" type="hidden" name="codiceIuv"/>
                <input id="codiceIuf" type="hidden" name="identificativoFlussoRendicontazione"/>
                <input id="codiceIud" type="hidden" name="codiceIud"/>
                <input id="tipoVisualizzazione" type="hidden" name="tipoVisualizzazione" value="${tipoVisualizzazione}" />
            </form>
        </div>

		<div>
			<form method="get" action="<%=request.getContextPath()%>/protected/prenotaExport.html" id="formVersioneTracciato">
				<input type="hidden" name="versioneTracciato" id="hiddenVersioneTracciato" />
			</form>

			<form:form commandName="visualizzaCompletaCommand" method="post" action="visualizzaCompleta.html">
			
				<div class="row-fluid">
					<!-- Data ultimo aggiornamento -->
					<div class="debiti_content_filters date_filter span2">
						<div class="form-inline form-actions">
							<div class="span12" style="text-align: center;">
								<input id="data_ultimo_aggiornamento_check" path="data_ultimo_aggiornamento_check"
									name="data_ultimo_aggiornamento_check" type="checkbox"
									class="date-trigger-checkbox"
									onclick="handleDatePanel(this, 'dataUltimoAggiornamento');"
									<c:if test="${visualizzaCompletaCommand.data_ultimo_aggiornamento_check == 'on'}">checked="checked"</c:if> />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.datiPagamento.dataUltimoAggiornamento" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="data_ultimo_aggiornamento_da"
										id="data_ultimo_aggiornamento_da" type="text"
										name="data_ultimo_aggiornamento_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="data_ultimo_aggiornamento_a"
										id="data_ultimo_aggiornamento_a" type="text"
										name="data_ultimo_aggiornamento_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<br />
							</div>
						</div>
					</div>
	
					<!-- Date di filtro -->
					<div class="debiti_content_filters date_filter date_filter_right span10">
						<div class="form-inline form-actions">
	
							<c:if test="${flgPagati}">
								<div class="span2_5" style="text-align: center;">
									<input id="data_esecuzione_check" path="data_esecuzione_check"
										name="data_esecuzione_check" type="checkbox"
										class="date-trigger-checkbox"
										onclick="handleDatePanel(this, 'dataEsecuzione');"
										<c:if test="${visualizzaCompletaCommand.data_esecuzione_check == 'on'}">checked="checked"</c:if> />
									<label style="vertical-align: -webkit-baseline-middle;"><spring:message
											code="mypivot.visualizza.datiPagamento.dataEsecuzione" /> </label><br />
									<div style="margin-top: 10px;">
										<label class="date-label"><spring:message
												code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
										<form:input path="data_esecuzione_singolo_pagamento_da"
											id="data_esecuzione_singolo_pagamento_da" type="text"
											name="data_esecuzione_singolo_pagamento_da"
											class="input-xlarge input-date-tesoreria my-input-date" />
									</div>
									<div style="margin-top: 10px;">
										<label class="date-label"><spring:message
												code="mypivot.visualizza.datiPagamento.dataA" />:</label>
										<form:input path="data_esecuzione_singolo_pagamento_a"
											id="data_esecuzione_singolo_pagamento_a" type="text"
											name="data_esecuzione_singolo_pagamento_a"
											class="input-xlarge input-date-tesoreria my-input-date" />
									</div>
									<br />
								</div>
							</c:if>
							<div class="span2_5" style="text-align: center;">
								<input id="data_esito_check" path="data_esito_check"
									name="data_esito_check" type="checkbox"
									class="date-trigger-checkbox"
									onclick="handleDatePanel(this, 'dataEsito');"
									<c:if test="${visualizzaCompletaCommand.data_esito_check == 'on'}">checked="checked"</c:if> />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.datiPagamento.dataEsito" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="data_esito_singolo_pagamento_da"
										id="data_esito_singolo_pagamento_da" type="text"
										name="data_esito_singolo_pagamento_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="data_esito_singolo_pagamento_a"
										id="data_esito_singolo_pagamento_a" type="text"
										name="data_esito_singolo_pagamento_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<br />
							</div>
							<div class="span2_5" style="text-align: center;">
								<input id="data_regolamento_check" path="data_regolamento_check"
									name="data_regolamento_check" type="checkbox"
									class="date-trigger-checkbox"
									onclick="handleDatePanel(this, 'dataRegolamento');"
									<c:if test="${visualizzaCompletaCommand.data_regolamento_check == 'on'}">checked="checked"</c:if> />
								<label style="vertical-align: -webkit-baseline-middle;"><spring:message
										code="mypivot.visualizza.regolamento.dataRegolamento" /> </label><br />
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
									<form:input path="data_regolamento_da" id="data_regolamento_da"
										type="text" name="data_regolamento_da"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<div style="margin-top: 10px;">
									<label class="date-label"><spring:message
											code="mypivot.visualizza.datiPagamento.dataA" />:</label>
									<form:input path="data_regolamento_a" id="data_regolamento_a"
										type="text" name="data_regolamento_a"
										class="input-xlarge input-date-tesoreria my-input-date" />
								</div>
								<br />
							</div>

							<c:if test="${flgTesoreria}">
								<div class="span2_5" style="text-align: center;">
									<input id="data_contabile_check" path="data_contabile_check"
										name="data_contabile_check" type="checkbox"
										class="date-trigger-checkbox"
										onclick="handleDatePanel(this, 'dataContabile');"
										<c:if test="${visualizzaCompletaCommand.data_contabile_check == 'on'}">checked="checked"</c:if> />
									<label style="vertical-align: -webkit-baseline-middle;"><spring:message
											code="mypivot.visualizza.datiPagamento.dataContabile" /> </label><br />
									<div style="margin-top: 10px;">
										<label class="date-label"><spring:message
												code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
										<form:input path="data_contabile_da" id="data_contabile_da"
											type="text" name="data_contabile_da"
											class="input-xlarge input-date-tesoreria my-input-date" />
									</div>
									<div style="margin-top: 10px;">
										<label class="date-label"><spring:message
												code="mypivot.visualizza.datiPagamento.dataA" />:</label>
										<form:input path="data_contabile_a" id="data_contabile_a"
											type="text" name="data_contabile_a"
											class="input-xlarge input-date-tesoreria my-input-date" />
									</div>
									<br />
								</div>
								<div class="span2_5" style="text-align: center;">
									<input id="data_valuta_check" path="data_valuta_check"
										name="data_valuta_check" type="checkbox"
										class="date-trigger-checkbox"
										onclick="handleDatePanel(this, 'dataValuta');"
										<c:if test="${visualizzaCompletaCommand.data_valuta_check == 'on'}">checked="checked"</c:if> />
									<label style="vertical-align: -webkit-baseline-middle;"><spring:message
											code="mypivot.visualizza.datiPagamento.dataValuta" /> </label><br />
									<div style="margin-top: 10px;">
										<label class="date-label"><spring:message
												code="mypivot.visualizza.datiPagamento.dataDa" />:</label>
										<form:input path="data_valuta_da" id="data_valuta_da"
											type="text" name="data_valuta_da"
											class="input-xlarge input-date-tesoreria my-input-date" />
									</div>
									<div style="margin-top: 10px;">
										<label class="date-label"><spring:message
												code="mypivot.visualizza.datiPagamento.dataA" />:</label>
										<form:input path="data_valuta_a" id="data_valuta_a"
											type="text" name="data_valuta_a"
											class="input-xlarge input-date-tesoreria my-input-date" />
									</div>
									<br />
								</div>
							</c:if>
	
						</div>
					</div>
				</div>

				<div class="debiti_content_filters">
					<div class="form-inline form-actions">
						<div class="row-fluid">
							<div class="span3">
								<label><spring:message
										code="mypivot.visualizza.tipoErrore" /></label><br />
								<form:select class="input-block-level" path="tipoErrore"
									id="tipoErroreCombo" onchange="adjustFilters()">
									
									<c:forEach var="ctd" items="${listaClassificazioni}">
										<form:option value="${ctd.cod}">
											<spring:message code="${ctd.desc}" />
										</form:option>
									</c:forEach>
								</form:select>
								<br /> <label><spring:message
										code="mypivot.visualizza.identificativo.IUD" /></label><br />
								<form:input path="codice_iud" class="input-block-level"
									id="codice_iud" name="codice_iud" type="text" placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.identificativo.IUV" /></label><br />
								<form:input path="codice_iuv" class="input-block-level"
									id="codice_iuv" name="codice_iuv" type="text" placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.identificativo.IUR" /></label><br />
								<form:input path="identificativo_univoco_riscossione"
									class="input-block-level"
									id="identificativo_univoco_riscossione"
									name="identificativo_univoco_riscossione" type="text"
									placeholder="" />
								<br />
                                <label> <spring:message code="mypivot.visualizza.nascosti" /></label>
                                <br />
                                <form:select class="input-block-level" path="visualizzaNascosti" name="visualizzaNascosti" id="visualizzaNascosti">
                                    <form:option value="all"><spring:message code="mypivot.visualizza.all" /></form:option>
                                    <form:option value="true"><spring:message code="mypivot.visualizza.true" /></form:option>
                                    <form:option value="false"><spring:message code="mypivot.visualizza.false" /></form:option>
                                </form:select>
                                <br />
							</div>

							<div class="span3">

								<label><spring:message
										code="mypivot.visualizza.pagatore.codice" /></label><br />
								<form:input path="codice_identificativo_univoco_pagatore"
									class="input-block-level"
									id="codice_identificativo_univoco_pagatore"
									name="codice_identificativo_univoco_pagatore" type="text"
									placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.pagatore.anagrafica" /></label><br />
								<form:input path="anagrafica_pagatore" class="input-block-level"
									id="anagrafica_pagatore" name="anagrafica_pagatore" type="text"
									placeholder="" />

								<br /> <label><spring:message
										code="mypivot.visualizza.versante.codice" /></label><br />
								<form:input path="codice_identificativo_univoco_versante"
									class="input-block-level"
									id="codice_identificativo_univoco_versante"
									name="codice_identificativo_univoco_versante" type="text"
									placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.versante.anagrafica" /></label><br />
								<form:input path="anagrafica_versante" class="input-block-level"
									id="anagrafica_versante" name="anagrafica_versante" type="text"
									placeholder="" />
								<br />
							</div>


							<div class="span3">
								<label><spring:message
										code="mypivot.visualizza.attestante" /></label><br />
								<form:input path="denominazione_attestante"
									class="input-block-level" id="denominazione_attestante"
									name="denominazione_attestante" type="text" placeholder="" />
								<br />
								<c:if test="${flgTesoreria}">
									<label><spring:message code="mypivot.visualizza.codOR1" /></label>
									<br />
									<form:input path="codOr1" class="input-block-level" id="codOr1"
										name="codOr1" type="text" placeholder="" />
									<br />
								</c:if>
								<label><spring:message
										code="mypivot.visualizza.flussoRendicontazione" /></label><br />
								<form:input path="identificativo_flusso_rendicontazione"
									class="input-block-level"
									id="identificativo_flusso_rendicontazione"
									name="identificativo_flusso_rendicontazione" type="text"
									placeholder="" />
								<br /> <label><spring:message
										code="mypivot.visualizza.regolamento" /></label><br />
								<form:input path="identificativo_univoco_regolamento"
									class="input-block-level"
									id="identificativo_univoco_regolamento"
									name="identificativo_univoco_regolamento" type="text"
									placeholder="" />
								<br />

							</div>
							<div class="span3">
								<label><spring:message
										code="mypivot.visualizza.tipoDovuto" /></label><br />
								<form:select class="input-block-level" path="cod_tipo_dovuto"
									id="tipoDovutoCombo">
									<form:option value="">
											TUTTI
									</form:option>
									<c:forEach var="ctd" items="${listaTipiDovutoPerEnte}">
										<form:option value="${ctd.codTipo}">
											<c:out value="${ctd.deTipo}"></c:out>
										</form:option>
									</c:forEach>
								</form:select>
								<br />
								<c:if test="${flgTesoreria}">
									<label><spring:message code="mypivot.visualizza.conto" /></label>
									<br />
									<form:input path="conto" class="input-block-level" id="conto"
										name="conto" type="text" placeholder="" />
									<br />
									<label><spring:message
											code="mypivot.visualizza.importoTesoreria" /></label>
									<br />
									<form:input path="importo" class="input-block-level"
										id="importo" name="importo" type="text" placeholder="" />
									<br />
								</c:if>
								<label><spring:message
										code="mypivot.visualizza.datiPagamento.causale" /></label><br />
								<form:input path="causale_versamento" class="input-block-level"
									id="causale_versamento" name="causale_versamento" type="text"
									placeholder="" />
								<br />
							</div>
							<form:hidden path="page" />
							<form:hidden path="tipoVisualizzazione" />
						</div>
					</div>

					<div class="align-center">
						<spring:message code="mypivot.pager.numElements" />
						<br />
						<form:select class="pagerSelect" itemLabel="pageSize"
							itemValue="pageSize" path="pageSize">
							<form:option value="5" />
							<form:option value="10" />
							<form:option value="20" />
						</form:select>
						<button type="submit" class="btn"
							style="margin-bottom: 10px;" value="visualizzaCompletaCommand">
							<i class="fa fa-search"></i>&nbsp;&nbsp;&nbsp;<spring:message code="mypivot.button.cerca" />
						</button>
                        <c:if test="${(fn:length(visualizzaCompletaDtoPage.list) > 0) or (fn:length(rendicontazioneSubsetDtoPage.list) > 0) or (fn:length(tesoreriaSubsetDtoPage.list) > 0) or (fn:length(rendicontazioneTesoreriaSubsetDtoPage.list) > 0)}">
							<br />
							<br />
							<c:if test="${(visualizzaCompletaDtoPage.totalRecords != null and visualizzaCompletaDtoPage.totalRecords > exportRecordThreshold) or (rendicontazioneSubsetDtoPage.totalRecords != null and rendicontazioneSubsetDtoPage.totalRecords > exportRecordThreshold) or (tesoreriaSubsetDtoPage.totalRecords != null and tesoreriaSubsetDtoPage.totalRecords > exportRecordThreshold) or (rendicontazioneTesoreriaSubsetDtoPage.totalRecords != null and rendicontazioneTesoreriaSubsetDtoPage.totalRecords > exportRecordThreshold)}">
								<spring:message code="mypivot.versioneTracciato" />&nbsp;
								<select id="versioneTracciatoSelect" disabled>
									<c:forEach var="currentVersioneTracciato" items="${versioniTracciato}" varStatus="loop">
											<option value="${currentVersioneTracciato}" ${loop.index==0?'selected="selected"':''}>${currentVersioneTracciato}</option>
									</c:forEach>
								</select>
								<a
									href="#"
									type="button" class="btn btn-primary btn-medium cerca disabled" style="margin-bottom: 10px;">Prenota export</a>
								<br />
							    <p class="alert" style="width: 40%; margin: 0 auto;">
		                            <spring:message code="mypivot.export.csv.record.limit.exceeded" />
		                        </p>
			                </c:if>
							<c:if test="${(visualizzaCompletaDtoPage.totalRecords != null and visualizzaCompletaDtoPage.totalRecords <= exportRecordThreshold) or (rendicontazioneSubsetDtoPage.totalRecords != null and rendicontazioneSubsetDtoPage.totalRecords <= exportRecordThreshold) or (tesoreriaSubsetDtoPage.totalRecords != null and tesoreriaSubsetDtoPage.totalRecords <= exportRecordThreshold) or (rendicontazioneTesoreriaSubsetDtoPage.totalRecords != null and rendicontazioneTesoreriaSubsetDtoPage.totalRecords <= exportRecordThreshold)}">
								<spring:message code="mypivot.versioneTracciato" />&nbsp;
								<select id="versioneTracciatoSelect">
									<c:forEach var="currentVersioneTracciato" items="${versioniTracciato}" varStatus="loop">
											<option value="${currentVersioneTracciato}" ${loop.index==0?'selected="selected"':''}>${currentVersioneTracciato}</option>
									</c:forEach>
								</select>
								<a
									href="javascript:void(0)" onclick="submitFormVersioneTracciato()"
									type="button" class="btn btn-primary btn-medium cerca" style="margin-bottom: 10px;">Prenota export</a>
							</c:if>
							<br />
						</c:if>
					</div>
					
					<script>
						$(document).ready(function(){
							$("#export-vecchio-disabilitato").popover({
								trigger : "hover",
								placement : "left"
							});
						});
					</script>
					<div class="align-right">
						<!-- Export CSV vecchia versione -->
						<c:if test="${fn:length(visualizzaCompletaDtoPage.list) > 0 and visualizzaCompletaCommand.tipoErrore eq 'RT_IUF' and requestScope.codIpaEnte eq 'C_G224'}">
							<c:if test="${(visualizzaCompletaDtoPage.totalRecords != null and visualizzaCompletaDtoPage.totalRecords > 5000)}">
								<a href="#" class="export-vecchia-versione" id="export-vecchio-disabilitato" data-toggle="popover" data-content="Superato numero massimo di record esportabili">Export CSV</a>
							</c:if>
							<c:if test="${(visualizzaCompletaDtoPage.totalRecords != null and visualizzaCompletaDtoPage.totalRecords <= 5000)}">
								<a href="<%=request.getContextPath()%>/protected/esportaCSVCompleta.html" class="export-vecchia-versione">Export CSV</a>
							</c:if>
							<br />
						</c:if>
					</div>
				</div>
			</form:form>

		</div>

		<c:if
			test="${
					visualizzaCompletaCommand.tipoErrore == 'IUD_RT_IUF_TES' || 
					visualizzaCompletaCommand.tipoErrore == 'RT_IUF_TES' || 
					visualizzaCompletaCommand.tipoErrore == 'RT_IUF' || 
					visualizzaCompletaCommand.tipoErrore == 'RT_NO_IUF'  || 
					visualizzaCompletaCommand.tipoErrore == 'RT_NO_IUD'  || 
					visualizzaCompletaCommand.tipoErrore == 'IUD_NO_RT'  || 
					visualizzaCompletaCommand.tipoErrore == 'IUD_RT_IUF' ||
					visualizzaCompletaCommand.tipoErrore == 'RT_TES'
				   }">
			<jsp:include page="noSubset.jsp"></jsp:include>
		</c:if>

		<c:if
			test="${
					visualizzaCompletaCommand.tipoErrore == 'IUF_NO_TES' || 
					visualizzaCompletaCommand.tipoErrore == 'IUV_NO_RT'
					}">
			<jsp:include page="rendicontazioneSubset.jsp"></jsp:include>
		</c:if>

		<c:if
			test="${visualizzaCompletaCommand.tipoErrore == 'TES_NO_IUF_OR_IUV' || 
					visualizzaCompletaCommand.tipoErrore == 'TES_NO_MATCH'
					}">
			<jsp:include page="tesoreriaSubset.jsp"></jsp:include>
		</c:if>
		
		<c:if test="${visualizzaCompletaCommand.tipoErrore == 'IUF_TES_DIV_IMP'}">
			<jsp:include page="rendicontazioneTesoreriaSubset.jsp"></jsp:include>
		</c:if>

	</div>
</div>