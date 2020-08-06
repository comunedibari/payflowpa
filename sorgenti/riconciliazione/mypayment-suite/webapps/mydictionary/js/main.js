function setOperatoreBodyClass() {
	$(".main_body").addClass("operatore");
}



function getProvincie() {
	var nazione = $('#nazioneSelect').find(":selected").attr('value');
	if (nazione == '1') {

		$.ajax({
			type : 'GET',
			url : "provincie.html",
			data : {},
			dataType : 'json',
			cache : true,
			beforeSend : function() {
			},
			success : function(result) {
				if (result.error == undefined) {
					var provinciaSelect = $('#provinciaSelect');
					provinciaSelect.empty();
					var options = "";
					for ( var provincia in result) {
						options += "<option value='" + result[provincia].id
								+ "'>" + result[provincia].provincia
								+ "</option>";
					}
					provinciaSelect.html(options);

					provinciaSelect.removeAttr('disabled');
					
					$('#anagraficaSubmitButton').attr('disabled', 'disabled');
				} else {

				}

			},
			error : function(data) {

			},
			statusCode : {
				403 : function() {
					alert("403: ");
				},
				404 : function() {
					alert("404: ");
				}
			}
		});
	}else{
		$('#provinciaSelect').attr('disabled','disabled');
		$('#comuneSelect').attr('disabled','disabled');
		$('#provinciaSelect').empty();
		$('#comuneSelect').empty();
	}
}

function getComuni(){
	var provincia = $('#provinciaSelect').find(":selected").attr('value');
	$.ajax({
		type : 'GET',
		url : "comuni.html",
		data : {
			provinciaId : provincia
		},
		dataType : 'json',
		cache : true,
		beforeSend : function() {
		},
		success : function(result) {
			if (result.error == undefined) {
				var comuneSelect = $('#comuneSelect');
				comuneSelect.empty();
				var options = "";
				for ( var comune in result) {
					options += "<option value='" + result[comune].id
							+ "'>" + result[comune].comune
							+ "</option>";
				}
				comuneSelect.html(options);
				
				comuneSelect.removeAttr('disabled');
				enableSubmit();
			} else {

			}

		},
		error : function(data) {

		},
		statusCode : {
			403 : function() {
				alert("403: ");
			},
			404 : function() {
				alert("404: ");
			}
		}
	});
}

function enableSubmit(){
	$('#anagraficaSubmitButton').removeAttr('disabled');
}