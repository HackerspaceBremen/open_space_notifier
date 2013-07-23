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

function registryRequest(){
	resetRegistryField();
	if(validate()){
		var btn = $('#registry_btn');
		btn.attr('disabled', 'disabled');
		var name = $('#login').val();
		var pass = $('#pass').val();
		var fullName = $('#fullName').val();
		var email = $('#email').val();
		
		$.ajax({
			url: "/cmd/createkeeper",
			dataType: 'json',
			type: 'POST',
			data: "name=" + name + "&pass=" + pass + "&fullName=" + fullName + "&email="+email,
			success: function(data, textStatus, jqXHR){
				// TODO dies nicht als alert lösen
				alert("Deine Regisitrierungsanfrage wurde aufgenommen. "
						+ "Du solltest eine Email erhalten haben. Darin befindet"
						+ " sich ein Link mit dem du deine Registrierung "
						+ "kompletieren kannst");
			},
			error: function(jqXHR, textStatus, errorThrown){
				// TODO implement error handling
			},
			complete: function(jqXHR, textStatus){
				btn.removeAttr('disabled');
			}
		});
	}
}

function validate(){
	return validateLogin() & validateFullName() & 
		validateEmail() & validatePasswords() & validatePassLength();
}

function validateLogin(){
	var length = $('#login').val().length;
	if(length==0){
		showInputError('#login', 'Bitte gebe einen Login an!');
		return false;
	}
	return true;
}

function validateFullName(){
	var length = $('#fullName').val().length;
	if(length==0){
		showInputError('#fullName', 'Bitte gebe deinen kompletten Namen an!');
		return false;
	}
	return true;
}

function validateEmail(){
	var length = $('#email').val().length;
	if(length==0){
		showInputError('#email', 'Bitte gebe eine Email-Adresse ein!');
		return false;
	}else{
		var atPoint = $('#email').val().indexOf('@');
		if(atPoint == -1 || $('#email').val().substring(atPoint,$('#email').val().length).indexOf('.') == -1){
			showInputError('#email', 'Die angegebene Adresse besitzt kein g'+unescape("%FC")+'ltiges Format!');
			return false;
		}
	}
	return true; 
}

function validatePasswords(){
	var equals = $('#pass').val() == $('#retypepass').val();
	if(!equals){
		showInputError('#pass', '');
		showInputError('#retypepass', 'Die eingegebenen Passw'+unescape("%F6")+'rter stimmen nicht '+unescape("%FC")+'berein')
	}
	return equals;
}

function validatePassLength(){
	var length = $('#pass').val().length;
	if(length<4){
		showInputError('#pass', 'Das angegebene Passwort ist zu kurz!');
		return false;
	}
	return true;
}

function showInputError(inputId, errorMessage){
	$('#error_message').html(errorMessage);
	$('#registry_field').removeClass('register_field_normal').addClass('register_field_bigger');
	if(!$(inputId).hasClass('inputError')){
		$(inputId).addClass('inputError');
	}
}

function removeInputError(inputId){
	if($(inputId).hasClass('inputError')){
		$(inputId).removeClass('inputError');
	}
}

function resetRegistryField(){
	$('#error_message').html('');
	$('#registry_field').removeClass('register_field_bigger').addClass('register_field_normal');
}