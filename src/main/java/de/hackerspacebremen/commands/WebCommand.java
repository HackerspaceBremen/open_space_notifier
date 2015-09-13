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

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.validation.ValidationException;
import de.hackerspacebremen.format.ResultKind;
import de.hackerspacebremen.util.Result;
import lombok.NonNull;

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

	@Inject
	protected ErrorMessages errorMessages;

	
	public  void init(@NonNull final HttpServletRequest req,
			@NonNull final HttpServletResponse resp, final Class<ErrorMessages> errorMessageClass) {
		final ResultKind kind = ResultKind.find(req.getParameter("format"));
		this.result = new Result(kind, errorMessages);
		this.req = req;
		this.resp = resp;
	}

	protected void handleError(ValidationException ve) {
		this.handleError(ve.getErrorCode());
	}

	protected void handleError(final int errorCode) {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		result.addError(errorCode);
	}

	protected void handleSuccess(final BasicResultObject result){
		resp.setStatus(HttpServletResponse.SC_OK);
		this.result.addSuccess(result);
	}
	
//	@Deprecated
//	protected void handleSuccess(final String message, final String result) {
//		resp.setStatus(HttpServletResponse.SC_OK);
//		this.result.addSuccess(message);
//		if (result != null)
//			this.result.addResult(result);
//	}

	public void process() throws ServletException, IOException {
		resp.setHeader("Content-Type", "text/javascript; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		final String res = this.result.toString();
		resp.getWriter().append(res);
		logger.info(res);
		ObjectifyService.ofy().clear();
	}
}
