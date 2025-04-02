


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>
	
	<acme:input-textbox code = "airline-manager.leg.form.label.flightNumber" path = "flightNumber"/>
	<acme:input-moment code ="airline-manager.leg.form.label.departure" path ="departure"/>
	<acme:input-moment code ="airline-manager.leg.form.label.arrival" path ="arrival"/>
	<acme:input-select code ="airline-manager.leg.form.label.status" path ="status" choices="${statuses}"/>
	<acme:input-select code ="airline-manager.leg.form.label.deployedAircraft" path ="deployedAircraft" choices="${aircrafts}"/>
	<acme:input-select code ="airline-manager.leg.form.label.departureAirport" path ="departureAirport" choices="${departureAirports}"/>
	<acme:input-select code ="airline-manager.leg.form.label.arrivalAirport" path ="arrivalAirport" choices="${arrivalAirports}"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show|delete|publish')}">
			<acme:input-double code ="airline-manager.leg.form.label.durationInHours" path ="durationInHours" readonly="true"/>	
		</jstl:when>
	</jstl:choose>
	<!-- acme:input-integer no va bien -->
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show|update|delete|publish') && publish == false}">
			<acme:submit code="airline-manager.leg.form.button.update" action="/airline-manager/leg/update"/>
			<acme:submit code="airline-manager.leg.form.button.delete" action="/airline-manager/leg/delete"/>
			<acme:submit code="airline-manager.leg.form.button.publish" action="/airline-manager/leg/publish"/>
		</jstl:when>
		<jstl:when  test="${acme:anyOf(_command,'create')}">
			<acme:submit code="airline-manager.leg.form.button.create" action="/airline-manager/leg/create?flightId=${flightId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>