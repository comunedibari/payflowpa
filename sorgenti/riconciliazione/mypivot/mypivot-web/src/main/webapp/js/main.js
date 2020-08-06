var currentFilename;

$(function() {
	$('input[id="fileupload"]').change(
			function() {
				$('#upload-container > label').remove();
				$('#upload-container > #loading-info').remove();

				var filename = this.value.replace(/C:\\fakepath\\/i, '');

				currentFilename = filename;

				$('<label>File Selezionato: ' + filename + '</label>')
						.insertAfter($(this));

				$('#upload-container > label').css("margin-top", "-30px");
				$('#upload-container > label').css("margin-left", "80px");
				$('#upload-container > label').css("margin-bottom", "20px");

				$('#fileupload').css("color", "transparent");
			});
});

$(function() {
	$('#fileupload')
			.fileupload(
					{
						dataType : 'json',
						autoUpload : true,
						forceIframeTransport : true,
						add : function(e, data) {

							$("#completato-info").remove();
							$('#upload-container > button').remove();
							$('#progress .bar').css('width', '0%');
							data.context = $('<button id="upload_button"/>')
									.text('Upload')
									.appendTo('#upload-container')
									.click(
											function() {
												if ($("#tFlusso").val() == "P") {

													var valid = true;

													if ($(
															"#input_cod_identificativo_flusso")
															.val() == "")
														valid = false;

													if ($(
															"#input_dt_data_ora_flusso")
															.val() == "")
														valid = false;

													if ($(
															"#input_cod_identificativo_univoco_regolamento")
															.val() == "")
														valid = false;

													if ($(
															"#input_dt_data_regolamento")
															.val() == "")
														valid = false;

													if ($(
															"#input_num_importo_totale_pagamenti")
															.val() == "")
														valid = false;

													if (!valid) {
														data.context = $(
																'<div id="loading-info" class="alert alert-info"/>')
																.text(
																		'Completare tutti i campi prima di procedere con il caricamento')
																.replaceAll(
																		$(this));
														return;
													}

												} else if($("#tFlusso").val() == "C") {
													
													var valid = true;
													
													
													if ($("#input_de_anno_bolletta").val() == "")
														valid = false;
													if ($("#input_cod_bolletta").val() == "")
														valid = false;
													if ($("#input_dt_contabile").val() == "")
														valid = false;
													if ($("#input_de_denominazione").val() == "")
														valid = false;
													if ($("#input_de_causale").val() == "")
														valid = false;
													if ($("#input_num_importo").val() == "")
														valid = false;
													if ($("#input_dt_valuta").val() == "")
														valid = false;

													if (!$.isNumeric($("#input_de_anno_bolletta").val()))
														valid = false;
													if (!$.isNumeric($("#input_cod_bolletta").val()))
														valid = false;
													if (!$.isNumeric($("#input_dt_contabile").val()))
														valid = false;
													if (!$.isNumeric($("#input_de_denominazione").val()))
														valid = false;
													if (!$.isNumeric($("#input_de_causale").val()))
														valid = false;
													if (!$.isNumeric($("#input_num_importo").val()))
														valid = false;
													if (!$.isNumeric($("#input_dt_valuta").val()))
														valid = false;
													
													if(valid) {
														var array = new Array;
														array.push($("#input_de_anno_bolletta").val());
														array.push($("#input_cod_bolletta").val());
														array.push($("#input_dt_contabile").val());
														array.push($("#input_de_denominazione").val());
														array.push($("#input_de_causale").val());
														array.push($("#input_num_importo").val());
														array.push($("#input_dt_valuta").val());
														if(hasDuplicates(array))
															valid = false;
													}
													
													
													if (!valid) {
														data.context = $('<div id="loading-info" class="alert alert-error"/>')
															.text('Completare tutti i campi prima di procedere con il caricamento e verificare che non ci siano duplicati e che siano tutti numerici')
															.replaceAll($(this));
														return;
													}
												}

												data.context = $(
														'<div id="loading-info" class="alert alert-info"/>')
														.text(
																'Caricamento file...')
														.replaceAll($(this));

												$.fn
														.keepAlive({
															url : 'keepAlive.json',
															timer : 5000,
															contentType : 'application/json; charset=utf-8',
															dataType : 'json'
														});

												data.submit();

											});
						},

						done : function(e, data) {

							if (data._response.result === undefined)
								var codice = null;
							else
								var codice = data._response.result.codice;

							if (codice != null) {
								$("#loading-info").remove();
								var errorDesc = data._response.result.descrizione;

								$("#upload-container").after(
										'<div id="completato-info" class="alert alert-error">'
												+ errorDesc + '</div>');

								$("#upload-container").hide();
								$("#upload-refresh").removeClass("hidden");
								sendBackUploadEsito("KO", 0);

							} else {
								$("#loading-info").remove();
								$("#upload-container")
										.after(
												'<div id="completato-info" class="alert alert-success">Flusso caricato e preso in carico.</div>');
								$("#upload-container").hide();
								$("#upload-refresh").removeClass("hidden");

								sendBackUploadEsito("OK", data.total);

							}
						},

						progressall : function(e, data) {
							var progress = parseInt(data.loaded / data.total
									* 100, 10);
							$('#progress .bar').css('width', progress + '%');
						}

					});

	$('#fileupload').fileupload('option', {
		xhrFields : {
			withCredentials : true
		}
	});

});

function sendBackUploadEsito(esito, dimensioneFile) {

	var codRequestToken = $("#requestToken").val();

	var jsonData;
	if ($("#tFlusso").val() == "P") {
		jsonData = JSON.stringify({
			codRequestToken : codRequestToken,
			esito : esito,
			nomeFile : currentFilename,
			dimensioneFile : dimensioneFile,
			cod_identificativo_flusso : $("#input_cod_identificativo_flusso")
					.val(),
			dt_data_ora_flusso : $("#input_dt_data_ora_flusso").val(),
			cod_identificativo_univoco_regolamento : $(
					"#input_cod_identificativo_univoco_regolamento").val(),
			dt_data_regolamento : $("#input_dt_data_regolamento").val(),
			num_importo_totale_pagamenti : $(
					"#input_num_importo_totale_pagamenti").val(),
		});
	} else if($("#tFlusso").val() == "C"){
		jsonData = JSON.stringify({
			codRequestToken : codRequestToken,
			esito : esito,
			nomeFile : currentFilename,
			dimensioneFile : dimensioneFile,
			de_anno_bolletta : $("#input_de_anno_bolletta").val(),
			cod_bolletta : $("#input_cod_bolletta").val(),
			dt_contabile : $("#input_dt_contabile").val(),
			de_denominazione : $("#input_de_denominazione").val(),
			de_causale : $("#input_de_causale").val(),
			num_importo : $("#input_num_importo").val(),
			dt_valuta : $("#input_dt_valuta").val()
		});
	} else {
		jsonData = JSON.stringify({
			codRequestToken : codRequestToken,
			esito : esito,
			nomeFile : currentFilename,
			dimensioneFile : dimensioneFile
		});
	}

	var data;

	$.ajax({
		type : 'POST',
		url : 'flussiUploadEsito.json',
		contentType : 'application/json; charset=utf-8',
		data : jsonData,
		dataType : 'json',
		success : function(e, data) {
			$.fn.keepAlive('stop');
		},
		error : function(e, data) {
			$.fn.keepAlive('stop');
		},
	});
}


$(function() {
	$('input[id="uploadCsvAnag"]').change(
			function() {
				$('#upload-container > label').remove();
				$('#upload-container > #loading-info').remove();

				var filename = this.value.replace(/C:\\fakepath\\/i, '');

				currentFilename = filename;

				$('<label>File Selezionato: ' + filename + '</label>')
						.insertAfter($(this));

				$('#upload-container > label').css("margin-top", "-30px");
				$('#upload-container > label').css("margin-left", "80px");
				$('#upload-container > label').css("margin-bottom", "20px");

				$('#uploadCsvAnag').css("color", "transparent");
			});
});

$(function() {
	$('#uploadCsvAnag')
			.fileupload(
					{
						dataType : 'json',
						autoUpload : true,
						forceIframeTransport : true,
						add : function(e, data) {

							$("#completato-info").remove();
							$('#upload-container > button').remove();
							$('#progress .bar').css('width', '0%');
							data.context = $('<button id="upload_button"/>')
									.text('Upload')
									.appendTo('#upload-container')
									.click(
											function() {

												data.context = $(
														'<div id="loading-info" class="alert alert-info"/>')
														.text(
																'Caricamento file...')
														.replaceAll($(this));

												$.fn
														.keepAlive({
															url : 'keepAlive.json',
															timer : 5000,
															contentType : 'application/json; charset=utf-8',
															dataType : 'json'
														});

												data.submit();

											});
						},

						done : function(e, data) {

							if (data._response.result === undefined)
								var codice = "400";
							else
								var codice = data._response.result.code;

							if (codice != "200") {
								$("#loading-info").remove();
								var message = data._response.result.message;
								$("#upload-container").after(
										'<div id="completato-info" class="alert alert-error">'
												+ message + '</div>');

								$("#upload-container").hide();
								$("#upload-refresh").removeClass("hidden");
								$.fn.keepAlive('stop');

							} else {
								$("#loading-info").remove();
								var message = data._response.result.message;
								$("#upload-container")
										.after(
												'<div id="completato-info" class="alert alert-success">'
												+ message + '</div>');
								$("#upload-container").hide();
								$("#upload-refresh").removeClass("hidden");

								$.fn.keepAlive('stop');

							}
						},

						progressall : function(e, data) {
							var progress = parseInt(data.loaded / data.total
									* 100, 10);
							$('#progress .bar').css('width', progress + '%');
						}

					});

	$('#uploadCsvAnag').fileupload('option', {
		xhrFields : {
			withCredentials : true
		}
	});

});

function validaImporto(event) {
	var importo = $("#input_num_importo_totale_pagamenti").val();

	if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 46)
		return true;

	if (event.keyCode == 188 && importo.search(",") == -1) {
		return true;
	}

	var importoNew = importo + String.fromCharCode(event.keyCode);
	var pattern = new RegExp("^(([0-9]){1,9})$|^(([0-9]){1,9})\\,([0-9]){1,2}$");
	var valida = pattern.test(importoNew);
	if (!valida) {
		event.preventDefault();
	}
}

function handleDatePanel(element, elementName) {
	var action = !element.checked ? 'disable' : 'enable';
	switch (elementName) {
	case 'dataEsecuzione':
		$("#data_esecuzione_singolo_pagamento_da").datepicker(action);
		$("#data_esecuzione_singolo_pagamento_a").datepicker(action);
		break;
	case 'dataEsito':
		$("#data_esito_singolo_pagamento_da").datepicker(action);
		$("#data_esito_singolo_pagamento_a").datepicker(action);
		break;
	case 'dataRegolamento':
		$("#data_regolamento_da").datepicker(action)
		$("#data_regolamento_a").datepicker(action)
		break;
	case 'dataContabile':
		$("#data_contabile_da").datepicker(action)
		$("#data_contabile_a").datepicker(action)
		break;
	case 'dataValuta':
		$("#data_valuta_da").datepicker(action)
		$("#data_valuta_a").datepicker(action)
		break;
	case 'dataUltimoAggiornamento':
		$("#data_ultimo_aggiornamento_da").datepicker(action)
		$("#data_ultimo_aggiornamento_a").datepicker(action)
		break;
	default:
		break;
	}
}

function getFile(url) {
	$.ajax({
		url : url,
		type : "HEAD",
		success : function(data) {
			window.location.href = url;
		},
		statusCode : {
			500 : function(e, data) {
				alert("Errore interno. Si prega di riprovare in seguito.");
			},
			404 : function(e, data) {
				alert("Risorsa richiesta non trovata.");
			}
		}
	});
}

$(document).ready(function() {
	$(".my-input-date").datepicker({
		showOn : "button",
		buttonImage : "/mypivot/images/calendar.gif",
		buttonImageOnly : true,
		dateFormat : "dd/mm/yy",
		regional : "it"
	});
});

function changeEnableByElmId(elmId, checkbox) {
	var checked = $(checkbox).is(':checked');
	var elm = $("#" + elmId);
	if (!checked) {
		elm.val('');
	}
	elm.prop('disabled', !checked);
}

function toggleElementById(id) {
	$('#' + id).toggle();
}

function submitFormVersioneTracciato() {
	var form = $('#formVersioneTracciato');
	var currentVersioneTracciato = $('#versioneTracciatoSelect').val();
	form.find('#hiddenVersioneTracciato').val(currentVersioneTracciato);
	form.submit();
}

function hasDuplicates(array) {
    var valueCheck = Object.create(null);
    for (var i = 0; i < array.length; ++i) {
        var value = array[i];
        if (value in valueCheck) {
            return true;
        }
        valueCheck[value] = true;
    }
    return false;
}