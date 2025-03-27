


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code ="flight-crew-member.flight-assignment.list.label.duty" path ="duty" width ="20%"/>
	<acme:list-column code ="flight-crew-member.flight-assignment.list.label.lastUpdateMoment" path ="lastUpdateMoment" width ="20%"/>
	<acme:list-column code ="flight-crew-member.flight-assignment.list.label.currentStatus" path ="currentStatus" width ="20%"/>
	<acme:list-payload path="payload"/>	
</acme:list>

<acme:button code="flight-crew-member.flight-assignment.list.button.create" action ="/flight-crew-member/flight-assignment/create"/>
