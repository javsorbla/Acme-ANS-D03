

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="assistance-agent.trackingLog.form.label.lastUpdateMoment" path="lastUpdateMoment"/>	
	<acme:input-textbox code="assistance-agent.trackingLog.form.label.step" path="step"/>	
	<acme:input-double code="assistance-agent.trackingLog.form.label.resolutionPercentage" path="resolutionPercentage"/>	
	<acme:input-select code="assistance-agent.trackingLog.form.label.status" path="status" choices="${status}"/>
	<acme:input-textbox code="assistance-agent.trackingLog.form.label.resolution" path="resolution"/>
	<acme:input-checkbox code ="assistance-agent.trackingLog.form.publish" path ="publish" readonly="true"/>
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && publish == false}">
			<acme:submit code="assistance-agent.trackingLog.form.button.update" action="/assistance-agent/tracking-log/update"/>
			<acme:submit code="assistance-agent.trackingLog.form.button.delete" action="/assistance-agent/tracking-log/delete"/>
			<acme:submit code="assistance-agent.trackingLog.form.button.publish" action="/assistance-agent/tracking-log/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistance-agent.trackingLog.form.button.create" action="/assistance-agent/tracking-log/create?claimId=${claimId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>