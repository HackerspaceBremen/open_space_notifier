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
package de.hackerspacebremen.util;

import lombok.Getter;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.format.JacksonInstance;
import de.hackerspacebremen.format.ResultKind;

public class Result {

	private BasicResultObject resultObject;

	@Getter
	private final ResultKind resultKind;

	private final ErrorMessages errorMessages;

	public Result(final ResultKind kind, final ErrorMessages errorMessages) {
		this.errorMessages = errorMessages;
		this.resultKind = kind;
	}

//	public void addResult(final String value) {
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				try{
//					json.put("RESULT", new JSONObject(value));
//				}catch(JSONException e){
//					json.put("RESULT", new JSONArray(value));
//				}
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			try {
//				json.put("RESULT", value);
//				logger.info(Constants.JSON_EXCEPTION_OCCURED + e.getMessage()
//						+ "/value will be returned as string instead of json");
//			} catch (JSONException e2) {
//				logger.warning(Constants.JSON_EXCEPTION_OCCURED
//						+ e2.getMessage());
//			}
//		}
//	}
	
//	public void addValue(final String key, final JSONObject value){
//		try {
//			json.put(key, value);
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}
	
//	public void addValue(final String key, final String value){
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				json.put(key, value);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}
	
//	public void addValue(final String key, final float value){
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				json.put(key, value);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}
	
//	public void addValue(final String key, final long value){
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				json.put(key, value);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}
	
//	public void addValue(final String key, final boolean value){
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				json.put(key, value);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}

	public <R extends BasicResultObject> void addSuccess(final R resultObject) {
		this.resultObject = resultObject;
	}
	
//	public void addSuccess(final String value) {
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				json.put("SUCCESS", value);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}

	public void addError(final int code) {
		if(this.resultObject == null){
			this.resultObject = new BasicResultObject();
		}
		if(this.errorMessages != null){
			this.resultObject.setError(this.errorMessages.getMessage(code));
		}
		this.resultObject.setErrorCode(code);
	}
	
//	public void addError(final String value, final int code) {
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				final String error;
//				if (this.errorMessages == null) {
//					error = "";
//				} else {
//					error = this.errorMessages.getMessage(code);
//				}
//				json.put("ERROR", error);
//				json.put("CODE", code);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}

	public String getError(){
		String result = null;
		if(this.resultObject != null){
			result = this.resultObject.getError();
		}
		return result;
	}
	
//	public String getError() {
//		String result = null;
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				if (json.has("ERROR"))
//					result = json.getString("ERROR");
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//		return result;
//	}

	public int getErrorCode() {
		int result = 0;
		if(this.resultObject != null && this.resultObject.getErrorCode() != null){
			result = this.resultObject.getErrorCode().intValue();
		}
		return result;
	}
	
//	public int getErrorCode() {
//		int result = 0;
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				if (json.has("CODE"))
//					result = json.getInt("CODE");
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//		return result;
//	}

	public void addWarn(final String warn) {
		if(this.resultObject == null){
			this.resultObject = new BasicResultObject();
		}
		this.resultObject.setWarn(warn);
	}
	
//	public void addWarn(final String value) {
//		try {
//			switch (this.resultKind) {
//			case JSON:
//				final JSONArray array;
//				if (json.has("WARN")) {
//					array = json.getJSONArray("WARN");
//				} else {
//					array = new JSONArray();
//				}
//				array.put(value);
//				json.put("WARN", array);
//				break;
//			case XML:
//				// nothing to implement yet
//				break;
//			}
//		} catch (JSONException e) {
//			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
//		}
//	}

	@Override
	public String toString() {
		String result = "";
		switch (this.resultKind) {
		case JSON:
			result = JacksonInstance.INSTANCE.write(this.resultObject);
			break;
		case XML:
			// nothing to implement here
			break;
		}
		return result;
	}
}
