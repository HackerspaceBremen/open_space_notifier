<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!-- /* -->
<!--  * Hackerspace Bremen Backend - An Open-Space-Notifier -->
<!--  *  -->
<!--  * Copyright (C) 2012 Steve Liedtke <sliedtke57@gmail.com> -->
<!--  *  -->
<!--  * This program is free software; you can redistribute it and/or modify it under the terms of the  -->
<!--  * GNU General Public License as published by the Free Software Foundation; either version 3 of  -->
<!--  * the License, or (at your option) any later version. -->
<!--  *  -->
<!--  * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;  -->
<!--  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See  -->
<!--  * the GNU General Public License for more details. -->
<!--  *  -->
<!--  * You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html. -->
<!--  *  -->
<!--  * Contributors: -->
<!--  *     Steve Liedtke <sliedtke57@gmail.com> -->
<!--  */ -->

<%@ page import="com.google.inject.Injector"%>
<%@ page import="com.google.inject.Guice"%>
<%@ page import="de.hackerspacebremen.domain.api.PropertyService" %>
<%@ page import="de.hackerspacebremen.valueobjects.properties.GeneralProperties" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final GeneralProperties properties = inj.getInstance(PropertyService.class).fetchProperties(GeneralProperties.class);
%>

<html>
  <jsp:include page="basicJSPs/head.jsp">
  	<jsp:param name="child" value="true" />
  </jsp:include>

  <body>
   	<jsp:include page="basicJSPs/nav.jsp" />
   	<div class="row">
   		<div class="large-9 push-3 columns">
	      
	      	<form action="/admin/email" method="POST">
				<div class="large-11 columns">
				  	<h3>Allgemeine Einstellungen</h3>
				</div>
				<jsp:include page="basicJSPs/validation.jsp" />
				
				
				<fieldset id="general_information">
					<div class="row">
						<div class="large-12 columns">
							<h4>Allgemeine Informationen</h4>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Space-Name</label>
							<input id="space_name" name="space_name" type="text" value="<%=properties.getSpaceName() %>">
							<small id="space_name_error" hidden="hidden">Bitte Namen des Spaces eintragen!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Website-URL</label>
							<input id="space_url" name="receiver_name" type="text" value="<%=properties.getUrl() %>">
							<small id="space_url_error" hidden="hidden">Bitte URL der Space-Website eintragen!</small>
						</div>
					</div>
				</fieldset>
				<fieldset id="general_contact_information">
					<div class="row">
						<div class="large-12 columns">
							<h4>Kontakt Informationen</h4>
						</div>
					</div>
					
					<!-- TODO -->
				</fieldset>
				<fieldset id="general_social_information">
					<div class="row">
						<div class="large-12 columns">
							<h4>Soziale Netzwerke</h4>
						</div>
					</div>
					
					<!-- TODO -->
					
				</fieldset>
				
				<div class="row">
				  	<div class="large-4 large-offset-8 columns">
				  		<input type="submit" value="Speichern" class="small button round success">
				  	</div>
			  	</div>
			</form>
	            
	    </div>
	    
	    <jsp:include page="basicJSPs/adminMenue.jsp" />
   	</div>
   	
   	<jsp:include page="basicJSPs/footer.jsp" />
  </body>
</html>
