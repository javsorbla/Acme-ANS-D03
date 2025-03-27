


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>

	<jstl:set var="isShowOrUpdate" value="${_command eq 'show' or _command eq 'update'}"/>
	
	<acme:input-textbox code = "airline-manager.flight.form.label.tag" path = "tag"/>
	<acme:input-checkbox code ="airline-manager.flight.form.label.requiresSelfTransfer" path ="requiresSelfTransfer"/>
	<acme:input-money code ="airline-manager.flight.form.label.cost" path ="cost"/>
	<acme:input-textbox code ="airline-manager.flight.form.label.description" path ="description"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show|delete|publish')}">
			<acme:input-moment code ="airline-manager.flight.form.label.scheduledDeparture" path ="scheduledDeparture" readonly="true"/>
			<acme:input-moment code ="airline-manager.flight.form.label.scheduledArrival" path ="scheduledArrival" readonly="true"/>
			<acme:input-textbox code ="airline-manager.flight.form.label.originCity" path ="originCity" readonly="true"/>
			<acme:input-textbox code ="airline-manager.flight.form.label.destinationCity" path ="destinationCity" readonly="true"/>
			<acme:input-double code ="airline-manager.flight.form.label.numberOfLayovers" path ="numberOfLayovers" readonly="true"/>	
		</jstl:when>
	</jstl:choose>
	<!-- acme:input-integer no va bien -->
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show|update|delete|publish')}">
			<acme:submit code="airline-manager.flight.form.button.update" action="/airline-manager/flight/update/"/>	
			<acme:button code="airline-manager.flight.form.button.legs" action="/airline-manager/leg/list?flightId=${id}"/>
		</jstl:when>
		<jstl:when  test="${acme:anyOf(_command,'create')}">
			<acme:submit code="airline-manager.flight.form.button.create" action="/airline-manager/flight/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>