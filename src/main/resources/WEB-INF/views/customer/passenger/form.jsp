<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@taglib prefix="acme" uri="http://acme-framework.org/"%>
 
 <acme:form >
 	 <acme:input-textbox code="customer.passenger.list.label.fullName" path="fullName" />
     <acme:input-email code="customer.passenger.list.label.email" path="email"/>
     <acme:input-textbox code="customer.passenger.list.label.passportNumber" path="passportNumber" />
     <acme:input-moment code="customer.passenger.list.label.dateOfBirth" path="dateOfBirth" />
     <acme:input-textarea code="customer.passenger.list.label.specialNeeds" path="specialNeeds"/>
 </acme:form>