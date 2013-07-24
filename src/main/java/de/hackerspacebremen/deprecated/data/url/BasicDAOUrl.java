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
package de.hackerspacebremen.deprecated.data.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import de.hackerspacebremen.deprecated.data.api.BasicDAO;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;
import de.hackerspacebremen.deprecated.format.FormatException;
import de.hackerspacebremen.deprecated.format.FormatFactory;
import de.hackerspacebremen.deprecated.format.Formatter;
import de.hackerspacebremen.deprecated.format.ResultKind;
import de.hackerspacebremen.util.Constants;

public abstract class BasicDAOUrl implements BasicDAO {

	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger.getLogger(BasicDAOUrl.class
			.getName());

	protected String address;

	protected Formatter formatter = FormatFactory.getFormatter(ResultKind.JSON
			.toString());

	protected abstract String getDaoName();

	/**
	 * Abstract method so every child tells the class it representates.
	 * 
	 * @return {@link Class}
	 */
	public abstract <T extends BasicEntity> Class<T> getEntityClass();

	@Override
	public <T extends BasicEntity> void delete(final T entity) {
		try {
			this.requestUrl(this.getDaoName(), "delete",
					formatter.format(entity).toString());
		} catch (FormatException e) {
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		} catch (JSONException e) {
			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> T persist(final T entity) {
		T result = null;
		try {
			result = (T) formatter.reformat(
					this.requestUrl(this.getDaoName(), "persist",
							formatter.format(entity).toString()).toString(),
					this.getEntityClass());
		} catch (FormatException e) {
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		} catch (JSONException e) {
			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
		}
		return result;
	}

	@Override
	public void close() {
		// do nothing
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> T findByKey(final Key key) {
		T result = null;
		try {
			result = (T) formatter.reformat(
					this.requestUrl(this.getDaoName(), "findByKey",
							KeyFactory.keyToString(key)).toString(),
					this.getEntityClass());
		} catch (FormatException e) {
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		} catch (JSONException e) {
			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> List<T> findAll() {
		List<T> result = new ArrayList<T>();
		try {
			final JSONArray array = this.requestUrl(this.getDaoName(),
					"findAll", null).getJSONArray("array");
			for (int i = 0; i < array.length(); i++) {
				result.add((T) formatter.reformat(array.getJSONObject(i)
						.toString(), this.getEntityClass()));
			}
		} catch (JSONException e) {
			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
		} catch (FormatException e) {
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return result;
	}

	protected JSONObject requestUrl(final String daoName,
			final String methodName, final String jsonValue)
			throws JSONException {
		String jsonString = null;
		try {
			// Construct data
			final StringBuilder params = new StringBuilder();
			params.append(URLEncoder.encode("daoName", Constants.UTF8));
			params.append("=");
			params.append(URLEncoder.encode(daoName, Constants.UTF8));
			params.append("&");
			params.append(URLEncoder.encode("methodName", Constants.UTF8));
			params.append("=");
			params.append(URLEncoder.encode(methodName, Constants.UTF8));
			if (jsonValue != null) {
				params.append("&");
				params.append(URLEncoder.encode("jsonValue", Constants.UTF8));
				params.append("=");
				params.append(URLEncoder.encode(jsonValue, Constants.UTF8));
			}
			params.append("&");
			params.append(URLEncoder.encode("kind", Constants.UTF8));
			params.append("=");
			params.append(URLEncoder.encode(ResultKind.JSON.toString(),
					Constants.UTF8));

			// Send data
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(params.toString());
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			final StringBuilder sBuilder = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sBuilder.append(line);
			}
			jsonString = sBuilder.toString();
			wr.close();
			rd.close();
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException occured: "
					+ e.getMessage());
		} catch (IOException e) {
			logger.warning("IOException occured: " + e.getMessage());
		}
		JSONObject json = null;
		if (jsonString == null) {
			json = null;
		} else {
			json = new JSONObject(jsonString);
		}
		return json;
	}
}
