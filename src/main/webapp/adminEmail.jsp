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
<%@ page import="de.hackerspacebremen.valueobjects.EmailProperties" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final EmailProperties properties = inj.getInstance(PropertyService.class).fetchEmailProperties();
%>

<html>
  <jsp:include page="basicJSPs/head.jsp">
  	<jsp:param name="child" value="true" />
  </jsp:include>

  <body>
   	<jsp:include page="basicJSPs/nav.jsp" />
   	<div class="row">
   		<div class="large-9 push-3 columns">
	      
	      	<h3>E-Mail Einstellungen</h3>
				
			<form action="/admin/email" method="POST">
				<jsp:include page="basicJSPs/validation.jsp" />
				
				
				<fieldset id="email_content_field">
					
					<div class="row">
						<div class="large-12 columns">
							<h4>Versender und Empfänger</label>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Name des Email-Versenders</label>
							<input id="sender_name" name="sender_name" type="text" value="<%=properties.getSenderName() %>">
							<small id="sender_name_error" hidden>Bitte Namen des Email-Versenders eintragen!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Name des Email-Empfängers</label>
							<input id="receiver_name" name="receiver_name" type="text" value="<%=properties.getReceiverName() %>">
							<small id="receiver_name_error" hidden>Bitte Namen des Email-Empfängers eintragen!</small>
						</div>
					</div>
					
					<div class="row">
						<div class="large-12 columns">
							<h4>Email-Betreff</h4>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Name des Email-Tags</label>
							<input id="subject_tag" name="subject_tag" type="text" value="<%=properties.getSubjectTag() %>">
							<small id="subject_tag_error" hidden>Bitte Email-Tag eintragen!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Bei Öffnung</label>
							<input id="subject_opened" name="subject_opened" type="text" value="<%=properties.getSubjectOpened() %>">
							<small id="subject_opened_error" hidden>Bitte Email-Betreff eintragen!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>Bei Schließung</label>
							<input id="subject_closed" name="subject_closed" type="text" value="<%=properties.getSubjectClosed() %>">
							<small id="subject_closed_error" hidden>Bitte Email-Tag eintragen!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<h4>E-Mail Inhalt</label>
						</div>
					</div>
					
					<div class="row">
						<div class="large-12 columns">
							<label>Email-Inhalts</label>
							<textarea id="content" name="content" placeholder="large-8.columns"><%=properties.getContent() %></textarea>
							<small id="content_error" hidden>Bitte füllen sie den Inhalt!</small>
							<label>Mögliche Variablen: $STATUS$, $URL$, $NEG_STATUS$, $MESSAGE$</label>
						</div>
					</div>
					
					<div class="row">
						<div class="large-12 columns">
							<h4>Variablen für Inhalt</label>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>$STATUS$ geöffnet</label>
							<input id="opened" name="opened" type="text" value="<%=properties.getOpened() %>">
							<small id="opened_error" hidden>Bitte setzen Sie den geöffneten Status des Spaces!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>$STATUS$ geschloßen</label>
							<input id="closed" name="closed" type="text" value="<%=properties.getClosed() %>">
							<small id="closed_error" hidden>Bitte setzen Sie den geschlossenen Status des Spaces!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>$NEG_STATUS$ geöffnet</label>
							<input id="negated_opened" name="negated_opened" type="text" value="<%=properties.getNegatedOpened() %>">
							<small id="negated_opened_error" hidden>Bitte setzen Sie den negierten geöffneten Status!</small>
						</div>
					</div>
					<div class="row">
						<div class="large-8 columns">
							<label>$NEG_STATUS$ geschloßen</label>
							<input id="negated_closed" name="negated_closed" type="text" value="<%=properties.getNegatedClosed() %>">
							<small id="negated_closed_error" hidden>Bitte setzen Sie den negierten geschloßenen Status!</small>
						</div>
					</div>
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
