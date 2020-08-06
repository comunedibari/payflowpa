<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 

	TEMPLATE HTML DELLA TABELLA DEI CAPITOLI ASSOCIATI ALLA RT IN ACCERTAMENTO
	
-->
<table class="table dettaglioTablePopup table-bordered">
    <thead>
       	<tr>
           	<th><spring:message code="mypivot.accertamenti.label.codiceUfficio"/></th>
          	<%-- <th class="nowrap"><spring:message code="mypivot.accertamenti.label.annoEsercizio" /></th> --%>
           	<th><spring:message code="mypivot.accertamenti.label.codiceCapitolo" /></th>
           	<th><spring:message code="mypivot.accertamenti.label.codiceAccertamento" /></th>
           	<th><spring:message code="mypivot.accertamenti.label.importo" /></th>
        </tr>
   	</thead>
    <tbody>
	   	<c:forEach var="rowDto" items="${dtt_Capitoli['capitoli']}" varStatus="status">
	    	<tr>
	        	<td>${not empty rowDto.deUfficio ? rowDto.deUfficio : rowDto.codUfficio}</td>
	         	<%-- <td>${rowDto.deAnnoEsercizio}</td> --%>
	         	<td>${not empty rowDto.deCapitolo ? rowDto.deCapitolo : rowDto.codCapitolo}</td>
	         	<td>${not empty rowDto.deAccertamento ? rowDto.deAccertamento : rowDto.codAccertamento}</td>
	         	<td>${rowDto.numImporto}</td>
	       	</tr>
	    </c:forEach>
    </tbody>
</table>
