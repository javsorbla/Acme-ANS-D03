


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form readonly = "${readonly}">
	<acme:input-textbox code = "technician.maintenanceRecords.form.label.moment" path = "moment"/>
	<acme:input-textbox code = "technician.maintenanceRecords.form.label.status" path = "status"/>
	<acme:input-textbox code = "technician.maintenanceRecords.form.label.nextInspectionDate" path = "nextInspectionDate"/>
	<acme:input-textbox code = "technician.maintenanceRecords.form.label.estimatedCost" path = "estimatedCost"/>
	<acme:input-textbox code = "technician.maintenanceRecords.form.label.notes" path = "notes"/>
	
</acme:form>