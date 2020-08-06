<%@page import="it.regioneveneto.mydictionary.controller.command.EditModelloCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.URLEncoder"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/codemirror-4.4/lib/codemirror.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/codemirror-4.4/addon/hint/show-hint.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/codemirror-4.4/addon/display/fullscreen.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/codemirror-4.4/addon/fold/foldgutter.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/codemirror-4.4/addon/dialog/dialog.css">
<script src="<%=request.getContextPath()%>/codemirror-4.4/lib/codemirror.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/edit/closetag.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/display/fullscreen.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/search/search.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/search/searchcursor.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/dialog/dialog.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/fold/foldgutter.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/fold/foldcode.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/fold/xml-fold.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/hint/show-hint.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/addon/hint/xml-hint.js"></script>
<script src="<%=request.getContextPath()%>/codemirror-4.4/mode/xml/xml.js"></script>

<style type="text/css">
	.CodeMirror { 
		border: 1px solid #DDD;
		border-radius: 5px;
		width: 866px;
	}

	.CodeMirror-fullscreen {
		width: 100%;
	}

	.edit-row {
		margin-bottom: 10px;
	}

	.full-screen {
		position: relative;
		top: -20px;
		right: 30px;
	}

	.help-btn {
		position: relative;
		top: -20px;
		right: 40px;
	}

	.help-win {
		position: absolute;
		top: -360px;
		right: 25px;
		display: none;
		background-color: white;
		border: 1px solid #DDD;
		border-radius: 4px;
		z-index: 2000;
		padding: 5px;
		white-space: nowrap;
	}

	.help-win table {
		margin: 0px;
		font-size: 14px;
	}

	.help-win table tr>td:first-child {
		min-width: 140px;
		font-family: monospace;
		color: #666;
	}

	.help-btn:hover .help-win {
		display: block;
	}
</style>

<div>
	<h2>
		<spring:message code="myDict.modifica.title" />
	</h2>
	<div class="edit_content">

		<jsp:include page="messages.jsp"></jsp:include>
		
		<div class="my_dict_content_filters">
			<form:form action="salva.html" modelAttribute="editModelloCommand" commandName="editModelloCommand" method="post" class="form-inline form-actions">
				<form:hidden path="codice"/>
				<div class="edit-row">
					<label><spring:message code="myDict.inserimentoModello.descrizione" /></label><br/>
					<form:input class="span9" path="descrizione"/>
				</div>
				<div class="edit-row">
					<label><spring:message code="myDict.inserimentoModello.modello" /></label><br/>
					<div class="full-screen pull-right" onClick="goFullScreen()"><i class="icon-fullscreen"></i></div>
					<div class="help-btn pull-right">
						<i class="icon-question-sign"></i>
						<div class="help-win pull-right">
							<table class="table table-condensed">
								<thead>
									<tr>
										<th>Shortcut</th>
										<th>Azione</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>Ctrl-Spazio</td>
										<td>Autocompleta</td>
									</tr>
									<tr>
										<td>Ctrl-D</td>
										<td>Elimina riga</td>
									</tr>
									<tr>
										<td>Alt-D</td>
										<td>Duplica riga</td>
									</tr>
									<tr>
										<td>ALt-Up</td>
										<td>Muovi riga su</td>
									</tr>
									<tr>
										<td>Alt-Down</td>
										<td>Muovi riga giù</td>
									</tr>
									<tr>
										<td>Ctrl-A</td>
										<td>Seleziona tutto</td>
									</tr>
									<tr>
										<td>Shift-Tab</td>
										<td>Identa selezione</td>
									</tr>
									<tr>
										<td>Ctrl-I</td>
										<td>Identa tutto</td>
									</tr>
									<tr>
										<td>Ctrl-Alt-F</td>
										<td>Chiudi/apri busta xml</td>
									</tr>
									<tr>
										<td>Ctrl-1</td>
										<td>Inserisci template TEXT</td>
									</tr>
									<tr>
										<td>Ctrl-9</td>
										<td>Inserisci template ASSOCIATION Allegati</td>
									</tr>
									<tr>
										<td>Ctrl-0</td>
										<td>Inserisci template ASSOCIATION Documenti</td>
									</tr>
									<tr>
										<td>Ctrl-F</td>
										<td>Cerca</td>
									</tr>
									<tr>
										<td>Ctrl-G</td>
										<td>Trova successivo</td>
									</tr>
									<tr>
										<td>Shift-Ctrl-G</td>
										<td>Trova precedente</td>
									</tr>
									<tr>
										<td>Shift-Ctrl-F</td>
										<td>Sostituisci in modo iterattivo</td>
									</tr>
									<tr>
										<td>Shift-Ctrl-R</td>
										<td>Sostituisci tutti</td>
									</tr>
									<tr>
										<td>Ctrl-Z</td>
										<td>Annulla ultima operazione</td>
									</tr>
									<tr>
										<td>Ctrl-Shift-Z</td>
										<td>Ripeti ultima operazione</td>
									</tr>
									<tr>
										<td>F11</td>
										<td>Ingrandisci/riduci la finestra</td>
									</tr>
									<tr>
										<td>Esc</td>
										<td>Riduci la finestra</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<form:textarea path="contenuto"/>
				</div>
				<div>
					<button class="btn" name="save" value="true">
						<spring:message code="myDict.button.save" />
					</button>
					<button class="btn" name="save" value="false">
						<spring:message code="myDict.button.abort" />
					</button>
				</div>

			</form:form>
		</div>
	</div>

	<script>
		var generic_txt_template =
			'<xs:element name="dyn_str_NAME" minOccurs="0">\n' +
			'    <xs:simpleType>\n' +
			'        <xs:restriction base="xs:string">\n' +
			'            <xs:annotation>\n' +
			'                <xs:appinfo>\n' +
			'                    <myp:render render_mode="TEXT"\n' +
			'                                insertable="true" insertable_order="30"\n' +
			'                                searchable="true" searchable_order="30"\n' +
			'                                listable="true" listable_order="30"\n' +
			'                                renderable="true" renderable_order="30"\n' +
			'                                html_label="LABEL"\n' +
			'                                bind_cms="dyn_str_NAME" />\n' +
			'                </xs:appinfo>\n' +
			'            </xs:annotation>\n' +
			'            <xs:pattern value=".*" />\n' +
			'        </xs:restriction>\n' +
			'    </xs:simpleType>\n' +
			'</xs:element>';

		var association_allegati_template =
			'<xs:element name="mul_association_allegati" type="myp:mul_association_allegati" minOccurs="0">\n' +
			'    <xs:annotation>\n' +
			'        <xs:appinfo>\n' +
			'            <myp:render render_mode="MULTIFIELD"\n' +
			'                        insertable="true" insertable_order="30"\n' +
			'                        searchable="false" searchable_order="30"\n' +
			'                        listable="true" listable_order="30"\n' +
			'                        renderable="true" renderable_order="30"\n' +
			'                        html_label="Allegati"\n' +
			'                        association="dyn_str_association_allegati_uuid"\n' +
			'                        bind_cms="rve:a-scheda-many-attachment"/>\n' +
			'        </xs:appinfo>\n' +
			'    </xs:annotation>\n' +
			'</xs:element>';

		var association_documenti_template =
			'<xs:element name="mul_association_documenti" type="myp:mul_association_documenti" minOccurs="0">\n' +
			'    <xs:annotation>\n' +
			'        <xs:appinfo>\n' +
			'            <myp:render render_mode="MULTIFIELD"\n' +
			'                        insertable="true" insertable_order="30"\n' +
			'                        searchable="false" searchable_order="30"\n' +
			'                        listable="true" listable_order="30"\n' +
			'                        renderable="true" renderable_order="30"\n' +
			'                        html_label="Documenti"\n' +
			'                        association="dyn_str_association_documenti_uuid"\n' +
			'                        bind_cms="myp:association-child-documento"/>\n' +
			'        </xs:appinfo>\n' +
			'    </xs:annotation>\n' +
			'</xs:element>';


		var boolean_choice = ['true', 'false'];
		var number_choice = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '20', '25', '30', '35', '40', '45', '50', '55', '60', '65', '70', '75', '80', '85', '90', '95', '99'];
		var generic_type = {
			attrs: {
				value: ['']
				}
			};

		var myp_render = {
			attrs: {
				render_mode: ['TEXT', 'INTEGER', 'DOUBLE', 'BOOLEAN', 'DATE', 'DATETIME', 'TEXTAREA', 'HTMLAREA', 'SELECT', 'MULTILINK', 'FILE', 'NONE', 'SINGLESELECT', 'MULTISELECT', 'MULTIFIELD', 'ASSOCIATION', 'LINKGENERATOR'],
				insertable: boolean_choice,
				insertable_order: number_choice,
				searchable: boolean_choice,
				searchable_order: number_choice,
				listable: boolean_choice,
				listable_order: number_choice,
				renderable: boolean_choice,
				renderable_order: number_choice,
				html_label: null,
				bind_cms: null,
				detail_link: boolean_choice
			},
			children: []
		};

		var tags = {
			'!xs:schema': ['xs:schema'],
			'!attrs': {
				id: null
			},
			'xs:schema': {
				attrs: {
				},
				children: ['xs:include', 'xs:annotation', 'xs:element', 'xs:complexType']
			},
			'xs:include': {
				attrs: {
					schemaLocation: ['/mydictionary/scarica.html?codice=mydynamo_system', '/mydictionary/scarica.html?codice=myp_common', '/mydictionary/scarica.html?codice=']
				}
			},
			'xs:annotation': {
				attrs: {
					id: ['render_override']
				},
				children: ['xs:appinfo']
			},
			'xs:appinfo': {
				attrs: null,
				children: ['myp:render', 'myp:render_override', 'myp:label', 'myp:default_order', 'myp:cmsType']
			},
			'myp:render_override': {
				attrs: {
					override: ['']
				},
				children: ['myp:render']
			},
			'xs:complexType': {
				attrs: {
					name: ['']
				},
				children: ['xs:complexContent']
			},
			'xs:complexContent': {
				children: ['xs:extension']
			},
			'xs:extension': {
				attrs: {
					base: ['myp:myp_common', 'myp:mydynamo_system']
				},
				children: ['xs:sequence']
			},
			'xs:sequence': {
				children: ['xs:element']
			},
			'xs:element': {
				attrs: {
					name: [''],
					fixed: [''],
					type: ['']
				},
				children: ['xs:simpleType']
			},
			'xs:simpleType': {

				children: ['xs:restriction']
			},
			'xs:restriction': {
				attrs: {
					base: ['xs:string', 'xs:decimal', 'xs:integer', 'xs:date', 'xs:dateTime', 'xs:double', 'xs:float', 'xs:boolean', 'xs:base64Binary', 'xs:anyURI']
				},
				children: ['xs:annotation', 'ex:pattern', 'ex:enumeration']
			},
			'ex:pattern': generic_type,
			'ex:enumeration': generic_type,
			'myp:render': myp_render,
			'myp:label': generic_type,
			'myp:default_order': generic_type,
			'myp:cmsType': generic_type
		};

		function completeAfter(cm, pred) {
			var cur = cm.getCursor();
			if (!pred || pred()) setTimeout(function() {
				if (!cm.state.completionActive)
					cm.showHint({completeSingle: true});
			}, 100);
			return CodeMirror.Pass;
		}

		function completeIfAfterLt(cm) {
			return completeAfter(cm, function() {
				var cur = cm.getCursor();
				return cm.getRange(CodeMirror.Pos(cur.line, cur.ch - 1), cur) == "<";
			});
		}

		function completeIfInTag(cm) {
			return completeAfter(cm, function() {
				var tok = cm.getTokenAt(cm.getCursor());
				if (tok.type == "string" && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1)) return false;
				var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
				return inner.tagName;
			});
		}

		function completeDynElement(cm) {

		}

		function indentAll(cm) {
			var last = cm.lineCount(); 
			cm.operation(function() { 
				for (var i = 0; i < last; ++i) {
					cm.indentLine(i)
				}; 
			}); 
		}

		function duplicateDown(cm) {
			var doc = cm.getDoc();
			var lineIdx = cm.getCursor().line;
			var line = doc.getLine(lineIdx);
			doc.replaceRange(line + '\n' + line, {line:lineIdx, ch:0}, {line:lineIdx, ch:line.length});
		}

		function moveDown(cm) {
			var doc = cm.getDoc();
			var lineIdx = cm.getCursor().line;
			if (lineIdx < doc.lineCount() - 1) {
				var line = doc.getLine(lineIdx);
				var lineNext = doc.getLine(lineIdx + 1);
				doc.replaceRange(lineNext + '\n' + line, {line:lineIdx, ch:0}, {line:lineIdx + 1, ch:lineNext.length});
			}
		}

		function moveUp(cm) {
			var doc = cm.getDoc();
			var lineIdx = cm.getCursor().line;
			var ch = cm.getCursor().ch;
			if (lineIdx > 0) {
				var line = doc.getLine(lineIdx);
				var linePrev = doc.getLine(lineIdx - 1);
				doc.replaceRange(line + '\n' + linePrev, {line:lineIdx - 1, ch:0}, {line:lineIdx, ch:line.length});
				doc.setCursor({line:lineIdx - 1, ch:ch});
			}
		}

		var editor = CodeMirror.fromTextArea(document.getElementById("contenuto"), {
			mode: "xml",
			autofocus: true,
			indentUnit: 4,
			lineNumbers: true,
		    foldGutter: true,
		    gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
			extraKeys: {
				"'<'": completeAfter,
				"'/'": completeIfAfterLt,
				"' '": completeIfInTag,
				"'='": completeIfInTag,
				//"'_'": completeDynElement,
				"Ctrl-Space": "autocomplete",
				"F11": function(cm) {
		          cm.setOption("fullScreen", !cm.getOption("fullScreen"));
		        },
		        "Esc": function(cm) {
		          if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
		        },
		        "Ctrl-Alt-F": function(cm){ console.log('ok');cm.foldCode(cm.getCursor()); },
		        "Ctrl-1": function(cm) { cm.replaceSelection(generic_txt_template); indentAll(cm);},
		        "Ctrl-9": function(cm) { cm.replaceSelection(association_allegati_template); indentAll(cm);},
		        "Ctrl-0": function(cm) { cm.replaceSelection(association_documenti_template); indentAll(cm);},
		        "Ctrl-I": indentAll,
		        "Alt-D": duplicateDown,
		        "Alt-Up": moveUp,
		        "Alt-Down": moveDown
			},
			autoCloseTags: {
				whenClosing: true,
				whenOpening: true,
				indentTags: ['xs:annotation', 'xs:appinfo', 'xs:complexType', 'xs:complexContent', 'xs:extension', 'xs:sequence', 'xs:element', 'xs:simpleType', 'xs:restriction']
			},
			hintOptions: {schemaInfo: tags}
		});

		function goFullScreen() {
			editor.setOption("fullScreen", true);
			editor.display.input.focus();
		}
	</script>
</div>