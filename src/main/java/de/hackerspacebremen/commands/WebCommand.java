/*
 * ljal - Java App Engine library
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
package de.hackerspacebremen.commands;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyService;

import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.format.FormatFactory;
import de.hackerspacebremen.format.Formatter;
import de.hackerspacebremen.util.ErrorMessages;
import de.hackerspacebremen.util.Result;

public abstract class WebCommand {

	
	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger.getLogger(WebCommand.class
			.getName());

	protected HttpServletRequest req;

	protected HttpServletResponse resp;

	protected JSONObject json;

	protected Result result;

	protected Formatter formatter;

	@Inject
	protected ErrorMessages errorMessages;

	
	public <T extends ErrorMessages> void init(final HttpServletRequest req,
			final HttpServletResponse resp, final Class<T> errorMessageClass) {
		if (req == null || resp == null) {
			logger.warning(this.getClass().getSimpleName()
					+ ": Response and/or Request are null!");
			throw new NullPointerException();
		}
		final String kind = req.getParameter("format");
		this.result = new Result(kind, errorMessages);
		this.req = req;
		this.resp = resp;
		this.formatter = FormatFactory.getFormatter(kind);
	}

	protected void handleError(ValidationException ve) {
		this.handleError(ve.getErrorCode());
	}

	protected void handleError(final int errorCode) {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		final String error;
		if (this.errorMessages == null) {
			error = "";
		} else {
			error = this.errorMessages.getMessage(errorCode);
		}
		result.addError(error, errorCode);
	}

	protected void handleSuccess(final String message, final String result) {
		resp.setStatus(HttpServletResponse.SC_OK);
		this.result.addSuccess(message);
		if (result != null)
			this.result.addResult(result);
	}

	public void process() throws ServletException, IOException {
		resp.setHeader("Content-Type", "text/javascript; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		final String res = result.toString();
		resp.getWriter().append(res);
		logger.info(res);
		ObjectifyService.ofy().clear();
	}
}
