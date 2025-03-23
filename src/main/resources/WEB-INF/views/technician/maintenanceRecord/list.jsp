

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code ="technician.maintenaceRecord.list.label.moment" path ="moment" width ="20%"/>
	<acme:list-column code ="technician.maintenaceRecord.list.label.status" path ="status" width ="20%"/>
	<acme:list-column code ="technician.maintenaceRecord.list.label.nextInspectionDate" path ="nextInspectionDate" width =""/>
	<acme:list-payload path="payload"/>	
</acme:list>

