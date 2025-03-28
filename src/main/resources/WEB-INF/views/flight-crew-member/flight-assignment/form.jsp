


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.duty" path="duty" choices="${duty }"/>
	<acme:input-moment code="flight-crew-member.flight-assignment.form.label.lastUpdateMoment" path="lastUpdateMoment"/>
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.currentStatus" path="currentStatus" choices="${currentStatus }"/>
	<acme:input-textbox code="flight-crew-member.flight-assignment.form.label.remarks" path="remarks"/>
</acme:form>