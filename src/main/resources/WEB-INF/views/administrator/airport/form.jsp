


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form readonly = "${readonly}">
	<acme:input-textbox code = "administrator.airport.form.label.name" path = "name"/>
	<acme:input-textbox code = "administrator.airport.form.label.iataCode" path = "iataCode"/>
	<acme:input-textbox code = "administrator.airport.form.label.operationalScope" path = "operationalScope"/>
</acme:form>