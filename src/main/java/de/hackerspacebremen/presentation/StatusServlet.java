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
package de.hackerspacebremen.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.ViewStatusCommand;

/**
 * This servlet is used to get the status of the hackerspace.
 * 
 * States:
 * -Open
 * -Close
 * 
 * @author Steve Liedtke
 */
@Singleton
public class StatusServlet extends OSNServlet {
	
	
	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -8784338611898492134L;
	
	private final Provider<ViewStatusCommand> viewStatusCommand;
	
	@Inject
	public StatusServlet(final Provider<ViewStatusCommand> viewStatusCommand){
		this.viewStatusCommand = viewStatusCommand;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final ViewStatusCommand cmd = viewStatusCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}
}
