/*
 * Hackerspace Bremen Backend - An Open-Space-Notifier
 * 
 * Copyright (C) 2012 Steve Liedtke <sliedtke57@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; either version 3 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 * the GNU General Public License for more details.
 * 
 * You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html.
 * 
 * Contributors:
 *     Steve Liedtke <sliedtke57@gmail.com>
 */
"use strict";

window.onload=function(){
	fetchStatus();
}


function fetchStatus(){
	$.ajax({
		url: "/status?format=de&htmlEncoded=true",
		dataType: 'json',
		success: function(data, textStatus, jqXHR){
			var res = JSON.parse(jqXHR.responseText);
			var status = res.RESULT.ST3; 
			var time = res.RESULT.ST2;
			var message = res.RESULT.ST5;
			var btn = $('#change_btn');
			var btn_message = $('#change_message_btn');
			var status_text = $('#status_text');
			var status_message = $('#status_message');
			status_text.html(time);
			//console.log("message: " + res.RESULT.ST5);
			if(typeof(message) != 'undefined'){
				status_message.html("<b>Aktuelle Statusnachricht:</b> " + message);
			}
			btn.removeAttr('disabled');
			btn_message.removeAttr('disabled');
			if(status=="OPEN"){
				btn.attr('class', 'OPEN');
				btn.val("Schlie"+unescape("%DF")+"e den Space");
				// TODO use image alternatively
				$("#status_text").css("color","green");
			}else{
				btn.attr('class','CLOSED');
				btn.val(unescape("%D6")+"ffne den Space");
				// TODO use image alternatively
				$("#status_text").css("color","red");
			}
			$('#error_message').html("");
			// TODO do that by removing or adding class
		},
		error: function(jqXHR, textStatus, errorThrown){
			var res = JSON.parse(jqXHR.responseText);
			var btn = $('#change_btn');
			switch(res.CODE){
			case 17:
				$('#status_text').html('Es existiert noch kein Space-Status!');
				$("#status_text").css("color","black");
				btn.attr('class','CLOSED');
				btn.val(unescape("%D6")+"ffne den Space");
				btn.removeAttr('disabled');
				break;
			default:
				console.log("result code: " + res.CODE);
				var btn = $('#change_btn');
				btn.attr('disabled','disabled');
				btn.attr('class','CLOSED');
				btn.val(unescape("%D6")+"ffne den Space");
				alert("Ein Fehler ist aufgetreten! Aktualisiere die Seite oder kontaktiere den Administrator.");
			}
		}
	});
}

function validate(){
	return validatePassword() & validateLogin();
}

function validateLogin(){
	if($("#login").val().length==0){
		showInputError('#login','Bitte gebe einen Login an!');
		return false;
	}
	return true;
}

function validatePassword(){
	if($("#pass").val().length<4){
		showInputError('#pass','Das Passwort ist zu kurz!');
		return false;
	}
	return true;
}

function showInputError(inputId, errorMessage){
	$('#error_message').html(errorMessage);
	$('#login_field').removeClass('login_field_normal').addClass('login_field_bigger');
	if(!$(inputId).hasClass('inputError')){
		$(inputId).addClass('inputError');
	}
}

function removeInputError(inputId){
	if($(inputId).hasClass('inputError')){
		$(inputId).removeClass('inputError');
	}
}

function resetLoginField(){
	$('#error_message').html('');
	$('#login_field').removeClass('login_field_bigger').addClass('login_field_normal');
}

function changeStatusRequest(){
	resetLoginField();
	if(validate()){
		var btn = $('#change_btn');
		btn.attr('disabled', 'disabled');
		var login = $('#login').val();
		var pass = $('#pass').val();
		var message = $('#message').val();
		var servlet;
		if(btn.attr('class')=="OPEN"){
			servlet = "/cmd/close";
		}else{
			servlet = "/cmd/open";
		}
		$.ajax({
			url: servlet,
			dataType: 'json',
			type: 'POST',
			data: "name=" + login + "&pass=" + encodeURIComponent(pass) + "&message=" + encodeURIComponent(message),
			success: function(data, textStatus, jqXHR){
				//var res = JSON.parse(jqXHR.responseText);
				$('#message').val("")
				alert("Der Status des Spaces wurde erfolgreich ge"+unescape("%E4")+"ndert!");
			},
			error: function(jqXHR, textStatus, errorThrown){
				//var res = JSON.parse(jqXHR.responseText);
				alert(unescape("%C4")+"nderung des Spaces konnte nicht durchgef"+unescape("%FC")+"hrt werden!")
			},
			complete: function(jqXHR, textStatus){
				btn.removeAttr('disabled');
				fetchStatus();
			}
		});
	}
}

function changeMessageRequest(){
	resetLoginField();
	var btn = $('#change_message_btn');
	btn.attr('disabled', 'disabled');
	var login = $('#login').val();
	var pass = $('#pass').val();
	var format = "de";
	var message = $('#message').val();
	var time = $('#status_text').html();
	console.log("name=" + login + "&pass=" + pass + "&message=" + message + "&format=" + format + "&time=" + time);
	
	var servlet = "/cmd/message";
	$.ajax({
		url: servlet,
		dataType: 'json',
		type: 'POST',
		data: "name=" + login + "&pass=" + encodeURIComponent(pass) + "&message=" + encodeURIComponent(message) + "&format=" + format + "&time=" + time,
		success: function(data, textStatus, jqXHR){
			//var res = JSON.parse(jqXHR.responseText);
			$('#message').val("")
			alert("Die Statusnachricht des Spaces wurde erfolgreich ge"+unescape("%E4")+"ndert!");
		},
		error: function(jqXHR, textStatus, errorThrown){
			//var res = JSON.parse(jqXHR.responseText);
			alert(unescape("%C4")+"nderung der Statusnachricht konnte nicht durchgef"+unescape("%FC")+"hrt werden! "+
					+"Ursache daf�r kann eine "+unescape("%C4")+"nderung des Status sein.");
			
		},
		complete: function(jqXHR, textStatus){
			btn.removeAttr('disabled');
			fetchStatus();
		}
	});
}