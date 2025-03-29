


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code ="flight-crew-member.activity-log.list.label.registrationMoment" path ="registrationMoment" width ="20%"/>
	<acme:list-column code ="flight-crew-member.activity-log.list.label.incidentType" path ="incidentType" width ="20%"/>
	<acme:list-column code ="flight-crew-member.activity-log.list.label.severityLevel" path ="severityLevel" width ="20%"/>
	<acme:list-payload path="payload"/>	
</acme:list>


