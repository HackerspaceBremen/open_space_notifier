<%@page import="de.hackerspacebremen.common.AppConstants"%>
<%@page import="de.hackerspacebremen.util.Constants"%>
<%@page import="de.hackerspacebremen.data.entities.SpaceStatus"%>
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
<%@ page import="de.hackerspacebremen.domain.api.SpaceStatusService" %>
<%@ page import="de.hackerspacebremen.commands.helper.StatusTimeFormat" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final SpaceStatusService statusService = inj.getInstance(SpaceStatusService.class);
	final SpaceStatus status = statusService.currentStatus();
%>

<html>
  <jsp:include page="basicJSPs/head.jsp">
  	<jsp:param name="title" value="Hackerspace Bremen" />
  </jsp:include>
  
  <body>
    <jsp:include page="basicJSPs/nav.jsp"/>
   	<!-- <div id="sectionHeader">
   		<img src="./images/banner.jpg">
   	</div> -->
   	<div class="row">
	   	<div class="large-9 columns">
		    	<h3 id="welcome">Willkommen auf dem Backend der Hackerspace-Bremen App!</h3>
				
				<div class="row">
					<div class="large-12 columns">
						<p id="info">
							Diese Seite soll nur zur manuellen &Ouml;ffnung oder Schlie&szlig;ung des Spaces dienen.
							Dies kann nur von Mitgliedern gemacht werden!
						</p>
					</div>
				</div>
				
				<div class="row">
					<div class="large-12 columns">
						<p id="status_text">
							<%
								if(status==null){
							%>
								<b>Der Space wurde bisher noch nie geöffnet!</b>
							<%
								}else{
							%>
								<%= new StatusTimeFormat("", status).createTimeFormat("de") %>
							<%
								}
							%>
						</p>
					</div>
				</div>
				
				<%
					if(status!=null && status.getMessage() != null){
				%>
				<div class="row">
					<div class="large-12 columns">
						<p id="status_message">
							<b>Aktuelle Statusnachricht:</b> <%= status.getMessage().getValue() %>
						</p>
					</div>
				</div>
				<%
					}
				%>
				
				<div class="row">
					<div class="large-12 columns">
						<form>
							<fieldset class="login_field_normal" id="login_field">
								<legend>Space Status &auml;ndern</legend>
								<div class="row">			
									<div class="large-12 columns">
										<p id="error_message"></p>
									</div>
								</div>
								
								<div class="row">			
									<div class="large-12 columns">
										<label for="message">Statusnachricht:</label>
										<textarea id="message" cols="30" rows="4"></textarea>
									</div>
								</div>
								<div class="row">			
									<div class="large-7 columns">
										<label for="login">Login:</label>
										<input id="login" type="text">
									</div>
								</div>
								<div class="row">			
									<div class="large-7 columns">
										<label for="pass">Passwort:</label>
										<input id="pass" type="password">
									</div>
								</div>
							</fieldset>
							<div class="row">			
								<div class="large-12 columns">
									<input id="change_message_btn" class="button" type="button" disabled="disabled" value="&Auml;ndere die Statusmessage" onclick="changeMessageRequest();">
									<%
									if(status == null || !status.getStatus().equals(AppConstants.OPEN)){
									%>
									<input id="change_btn" class="button success" type="submit" disabled="disabled" value="Öffne den Space">
									<%
									}else{
									%>
									<input id="change_btn" class="button error" type="submit" disabled="disabled" value="Schließe den Space">
									<%
									}
									%>
								</div>
							</div>
						</form>
					</div>
				</div>
		    </div>
		    <div class="large-3 columns">      
		      <ul class="side-nav">
		        <li><a href="http://www.hackerspace-bremen.de/">Hackerspace Bremen Homepage</a></li>
		        <li><a href="https://play.google.com/store/apps/details?id=de.hackerspacebremen">Android-App</a></li>
		        <li><a href="http://code.google.com/p/hackerspace-bremen/wiki/Backend">Webinterface Zugriff</a></li>
		        <li><a href="https://chrome.google.com/webstore/detail/apadeikhfnipflbiglhdcilnfocbikhc">Chrome Extension</a></li>
		        <li><a href="/v2/status.rss" target="_blank">RSS-Feed</a></li>
		        <li><a href="/v2/status.atom" target="_blank">Atom-Feed</a></li>
		      </ul>        
		    </div>
		</div>
  	<jsp:include page="basicJSPs/footer.jsp"/>
  </body>
</html>
