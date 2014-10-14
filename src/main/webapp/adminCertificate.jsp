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
<%@ page import="de.hackerspacebremen.valueobjects.CertificateProperties" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final CertificateProperties properties = inj.getInstance(PropertyService.class).fetchCertificateProperties();
	final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<html>
  <jsp:include page="basicJSPs/head.jsp">
  	<jsp:param name="child" value="true" />
  </jsp:include>

  <body>
   	<jsp:include page="basicJSPs/nav.jsp" />
   	<div class="row">
   		<div class="large-9 push-3 columns">
	      
	      <h3>Push-Benachrichtigungen</h3>
		  
		  <form action='<%= blobstoreService.createUploadUrl("/admin/certificate/apns") %>' enctype="multipart/form-data" method="POST">
		  	  <jsp:include page="basicJSPs/validation.jsp" />
		  	
			  <div class="row">
				  <fieldset id="apns_field">
			  			<div class="row">
				  			<div class="large-11 columns">
							  	<h4>Apple Push Notification Service</h4>
							</div>
						</div>
						<br/>
						<div class="row">
							<div class="large-8 columns">
								<label>APNS Zertifikat (.p12):</label>
								<%
								if(!properties.getApnsCertificate().isEmpty()){ 
								%>
								<b>Es wurde bereits ein Zertifikat hinterlegt!</b>
								<%} %>
								<input type="file" name="apns_certificate" accept="application/x-pkcs12">
							</div>
						</div>
				  </fieldset>
			  </div>
			  
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
