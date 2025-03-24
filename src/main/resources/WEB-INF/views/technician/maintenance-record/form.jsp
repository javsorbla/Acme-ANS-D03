


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>
	<acme:input-textbox code = "technician.maintenance-record.form.label.moment" path = "moment"/>
	<acme:input-textbox code = "technician.maintenance-record.form.label.status" path = "status"/>
	<acme:input-textbox code = "technician.maintenance-record.form.label.nextInspectionDate" path = "nextInspectionDate"/>
	<acme:input-textbox code = "technician.maintenance-record.form.label.estimatedCost" path = "estimatedCost"/>
	<acme:input-textbox code = "technician.maintenance-record.form.label.notes" path = "notes"/>
	<acme:input-textbox code = "technician.maintenance-record.form.label.aircraft" path = "aircraft"/>
	
</acme:form>