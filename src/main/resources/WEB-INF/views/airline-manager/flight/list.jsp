

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code ="airline-manager.flight.list.label.tag" path ="tag" width ="20%"/>
	<acme:list-column code ="airline-manager.flight.form.label.originCity" path ="originCity" width="30%"/>
	<acme:list-column code ="airline-manager.flight.form.label.destinationCity" path ="destinationCity" width="30%"/>
	<acme:list-column code ="airline-manager.flight.list.label.cost" path ="cost" width =""/>
	<acme:list-payload path="payload"/>	
</acme:list>

<acme:button code="airline-manager.flight.list.button.create" action ="/airline-manager/flight/create"/>