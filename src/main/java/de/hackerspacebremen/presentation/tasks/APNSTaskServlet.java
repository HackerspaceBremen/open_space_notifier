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
package de.hackerspacebremen.presentation.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.push.APNSCommand;
import de.hackerspacebremen.presentation.v2.TaskController;

/**
 * @deprecated use {@link TaskController#apns()} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public class APNSTaskServlet extends HttpServlet{

	
	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3408223150190634281L;
	
	private final Provider<APNSCommand> apnsCommand;
	
	@Inject
	public APNSTaskServlet(final Provider<APNSCommand> apnsCommand){
		this.apnsCommand = apnsCommand;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final APNSCommand cmd = apnsCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}
}
