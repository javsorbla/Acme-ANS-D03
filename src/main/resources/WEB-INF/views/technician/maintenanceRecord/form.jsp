


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form readonly = "${readonly}">
	<acme:input-textbox code = "technician.maintenanceRecord.form.label.moment" path = "moment"/>
	<acme:input-textbox code = "technician.maintenanceRecord.form.label.status" path = "status"/>
	<acme:input-textbox code = "technician.maintenanceRecord.form.label.nextInspectionDate" path = "nextInspectionDate"/>
	<acme:input-textbox code = "technician.maintenanceRecord.form.label.estimatedCost" path = "estimatedCost"/>
	<acme:input-textbox code = "technician.maintenanceRecord.form.label.notes" path = "notes"/>
	
</acme:form>