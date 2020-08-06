<%@ attribute name="root" type="it.regioneveneto.mydictionary.controller.SchemaNode" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<span class="node">
	<a href="${pageContext.request.contextPath}/grafico.html?rootCode=${root.code}">${root.code}</a>
	<c:if test="${fn:length(root.children) > 1}">
		<i class="expander icon-plus" onclick="toggleBranch(this, '${root.code}');"></i>
	</c:if>
</span>
<c:if test="${!empty root.children}">
<ul>
	<c:if test="${fn:length(root.children) > 1}">
	<li class="grouper grouper_${root.code} sole">
       	<span class="node">
       		<a href="#">. . .</a>
		</span>
	</li>
	</c:if>
	<c:forEach var="node" items="${root.children}">
    <li class="content content_${root.code} ${fn:length(root.children) == 1 ? " sole" : ""}" <c:if test="${fn:length(root.children) > 1}">style="display: none;"</c:if>>
       	<myp:treenode root="${node}"/>
	</li>
    </c:forEach>
</ul>
</c:if>