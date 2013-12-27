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
	      
	      	<h2>E-Mail Einstellungen</h2>
				
			<form action="/admin/push" method="POST">
				<jsp:include page="basicJSPs/validation.jsp" />
				
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
					<div class="large-8 columns">
						<label>Name des Email-Tags</label>
						<input id="subject_tag" name="subject_tag" type="text" value="<%=properties.getSubjectTag() %>">
						<small id="subject_tag_error" hidden>Bitte Email-Tag eintragen!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Email-Betreff bei Öffnung</label>
						<input id="subject_opened" name="subject_opened" type="text" value="<%=properties.getSubjectOpened() %>">
						<small id="subject_opened_error" hidden>Bitte Email-Betreff eintragen!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Email-Betreff bei Schließung</label>
						<input id="subject_closed" name="subject_closed" type="text" value="<%=properties.getSubjectClosed() %>">
						<small id="subject_closed_error" hidden>Bitte Email-Tag eintragen!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Teil 1 des Email-Inhalts</label>
						<input id="content_part1" name="content_part1" type="text" value="<%=properties.getContentPart1("", "", "") %>"> #STATUS #VARIABLE1 #VARIABLE2
						<small id="content_part1_error" hidden>Bitte füllen sie den ersten Teil des Inhalts!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Teil 2 des Email-Inhalts</label>
						<input id="content_part2" name="content_part1" type="text" value="<%=properties.getContentPart2("") %>"> #URL
						<small id="content_part2_error" hidden>Bitte füllen sie den zweiten Teil des Inhalts!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Teil 3 des Email-Inhalts</label>
						<input id="content_part3" name="content_part3" type="text" value="<%=properties.getContentPart3("") %>"> #NEGIERTER_STATUS
						<small id="content_part3_error" hidden>Bitte füllen sie den dritten Teil des Inhalts!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Teil 4 des Email-Inhalts</label>
						<input id="content_part4" name="content_part4" type="text" value="<%=properties.getContentPart4() %>">
						<small id="content_part4_error" hidden>Bitte füllen sie den vierten Teil des Inhalts!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Geöffneter Status des Spaces</label>
						<input id="opened" name="opened" type="text" value="<%=properties.getOpened() %>">
						<small id="opened_error" hidden>Bitte setzen Sie den geöffneten Status des Spaces!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Geschlossener Status des Spaces</label>
						<input id="closed" name="closed" type="text" value="<%=properties.getClosed() %>">
						<small id="closed_error" hidden>Bitte setzen Sie den geschlossenen Status des Spaces!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Ankündigung der Nachricht</label>
						<input id="message" name="message" type="text" value="<%=properties.getMessage() %>">
						<small id="message_error" hidden>Bitte setzen Sie die Ankündigung der Nachricht!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Negierte geöffneter Status</label>
						<input id="negated_opened" name="negated_opened" type="text" value="<%=properties.getNegatedOpened() %>">
						<small id="negated_opened_error" hidden>Bitte setzen Sie den negierten geöffneten Status!</small>
					</div>
				</div>
				<div class="row">
					<div class="large-8 columns">
						<label>Negierte geschloßener Status</label>
						<input id="negated_closed" name="negated_closed" type="text" value="<%=properties.getNegatedClosed() %>">
						<small id="negated_closed_error" hidden>Bitte setzen Sie den negierten geschloßenen Status!</small>
					</div>
				</div>
			</form>
	            
	    </div>
	    
	    <jsp:include page="basicJSPs/adminMenue.jsp" />
   	</div>
   	
   	<jsp:include page="basicJSPs/footer.jsp" />
  </body>
</html>
