


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.duty" path="duty" choices="${dutyChoice }"/>
	<acme:input-moment code="flight-crew-member.flight-assignment.form.label.lastUpdateMoment" path="lastUpdateMoment"/>
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.currentStatus" path="currentStatus" choices="${currentStatusChoice }"/>
	<acme:input-textbox code="flight-crew-member.flight-assignment.form.label.remarks" path="remarks"/>
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.flightAssignmentLeg" path="flightAssignmentLeg" choices="${legChoice }"/>
	<acme:input-select code="flight-crew-member.flight-assignment.form.label.flightAssignmentCrewMember" path="flightAssignmentCrewMember" choices="${flightCrewMemberChoice }"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show') }">
		</jstl:when>	
		<jstl:when test="${acme:anyOf(_command,'create') }">
			<acme:submit code="flight-crew-member.flight-assignment.form.button.create" action="/flight-crew-member/flight-assignment/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>