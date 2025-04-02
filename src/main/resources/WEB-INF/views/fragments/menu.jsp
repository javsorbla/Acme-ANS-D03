<%--
- menu.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar>
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.MWK4397" action="http://www.reddit.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.alereyper" action="http://www.google.es/"/>
            <acme:menu-suboption code="master.menu.anonymous.nundelesc" action="https://www.informatica.us.es/"/>
            <acme:menu-suboption code="master.menu.anonymous.javgutpas" action="https://www.youtube.com/"/>
            <acme:menu-suboption code="master.menu.anonymous.HGD6373" action="http://www.github.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.list-user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-system-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator.airports" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.airports.airports-list" action="/administrator/airport/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator.airlines" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.airlines.airlines-list" action="/administrator/airlines/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator.aircraft" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.aircraft.aircraft-list" action="/administrator/aircraft/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.airline-manager" access="hasRealm('AirlineManager')">
			<acme:menu-suboption code="master.menu.airline-manager.flights-list" action="/airline-manager/flight/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.flightcrewmember" access="hasRealm('FlightCrewMember')">
			<acme:menu-suboption code="master.menu.flightcrewmember.flightassignments.flightAssignments-list-completed" action="/flight-crew-member/flight-assignment/list-completed"/>
			<acme:menu-suboption code="master.menu.flightcrewmember.flightassignments.flightAssignments-list-planned" action="/flight-crew-member/flight-assignment/list-planned"/>
		</acme:menu-option>
    
		<acme:menu-option code="master.menu.technician" access="hasRealm('Technician')">
			<acme:menu-suboption code="master.menu.technician.maintenanceRecords.maintenanceRecords-list" action="/technician/maintenance-record/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.technician.tasks.tasks-list" action="/technician/task/list"/>		
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.assistance-agent" access="hasRealm('AssistanceAgent')">
			<acme:menu-suboption code="master.menu.assistance-agent.claims.claims-list" action="/assistance-agent/claim/list"/>
<%--
-			<acme:menu-suboption code="master.menu.assistance-agent.claims.list-claims-pending" action="/assistance-agent/claim/pending"/>	
--%>
		</acme:menu-option>	
		
		<acme:menu-option code="master.menu.customer" access="hasRealm('Customer')">
 			<acme:menu-suboption code="master.menu.customer.bookings-list" action="/customer/booking/list"/>
 			<acme:menu-suboption code="master.menu.customer.passengers-list" action="/customer/passenger/list"/>
 		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRealm('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRealm('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
		
 		
	</acme:menu-left>

	<acme:menu-right>		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-profile" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRealm('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider-profile" action="/authenticated/provider/update" access="hasRealm('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRealm('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer-profile" action="/authenticated/consumer/update" access="hasRealm('Consumer')"/>
		</acme:menu-option>
	</acme:menu-right>
</acme:menu-bar>

