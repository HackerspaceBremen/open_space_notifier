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
package de.hackerspacebremen.format;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.data.annotations.OsnEntity;
import de.hackerspacebremen.data.annotations.FormatPart;
import de.hackerspacebremen.data.entities.BasicEntity;

public class JSONFormatter<T extends BasicEntity> implements Formatter<T> {

	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger.getLogger(JSONFormatter.class
			.getName());

	@Override
	public String format(final T entity,
			final String... levels) throws FormatException {
		final JSONObject json = new JSONObject();
		boolean allAttributes = (levels == null || levels.length == 0);
		final List<String> levelList;
		if (levels == null || levels.length == 0) {
			levelList = new ArrayList<String>();
		} else {
			levelList = Arrays.asList(levels);
		}
		if (entity.getClass().isAnnotationPresent(OsnEntity.class)) {
			final Field[] fields = entity.getClass().getDeclaredFields();
			for (final Field field : fields) {
				if (field.isAnnotationPresent(FormatPart.class)
						&& (allAttributes || levelList.contains(field
								.getAnnotation(FormatPart.class).level()))) {
					try {
						final Method method = entity.getClass()
								.getMethod(
										this.getGetter(field.getName(),
												field.getType()),
										(Class<?>[]) null);
						final Object methodResult = method.invoke(entity);
						if (methodResult != null) {
							final String type = field.getType().toString();
							if (type.equals("class com.google.appengine.api.datastore.Key")) {
								json.put(field.getAnnotation(FormatPart.class)
										.key(), KeyFactory
										.keyToString((Key) methodResult));
							} else if (type
									.equals("class com.google.appengine.api.datastore.Text")) {
								json.put(field.getAnnotation(FormatPart.class)
										.key(), ((Text) methodResult)
										.getValue());
							} else {
								json.put(field.getAnnotation(FormatPart.class)
										.key(), methodResult.toString());
							}
						}
					} catch (IllegalAccessException e) {
						logger.warning("IllegalAccessException occured: "
								+ e.getMessage());
						throw new FormatException();
					} catch (NoSuchMethodException e) {
						logger.warning("NoSuchMethodException occured: "
								+ e.getMessage());
						throw new FormatException();
					} catch (SecurityException e) {
						logger.warning("SecurityException occured: "
								+ e.getMessage());
						throw new FormatException();
					} catch (IllegalArgumentException e) {
						logger.warning("IllegalArgumentException occured: "
								+ e.getMessage());
						throw new FormatException();
					} catch (InvocationTargetException e) {
						logger.warning("InvocationTargetException occured: "
								+ e.getMessage());
						throw new FormatException();
					} catch (JSONException e) {
						logger.warning("JSONException occured: "
								+ e.getMessage());
						throw new FormatException();
					}
				}
			}
		}

		return json.toString();
	}

	@Override
	public T reformat(final String object,
			final Class<T> entityClass) throws FormatException {
		T result = null;
		final Map<String, Field> fieldMap = new HashMap<String, Field>();
		if (entityClass.isAnnotationPresent(OsnEntity.class)) {
			final Field[] fields = entityClass.getDeclaredFields();
			for (final Field field : fields) {
				if (field.isAnnotationPresent(FormatPart.class)) {
					fieldMap.put(field.getAnnotation(FormatPart.class).key(),
							field);
				}
			}
			try {
				final JSONObject json = new JSONObject(object);
				result = entityClass.newInstance();
				for (final String key : fieldMap.keySet()) {
					if (json.has(key)) {
						final Field field = fieldMap.get(key);
						final Method method = entityClass.getMethod(
								this.getSetter(field.getName()),
								new Class<?>[] { field.getType() });
						
						final String type = field.getType().toString();
						if (type.equals("class com.google.appengine.api.datastore.Key")) {
							method.invoke(result, KeyFactory
									.stringToKey(json.getString(key)));
						} else if (type
								.equals("class com.google.appengine.api.datastore.Text")) {
							method.invoke(result,
									new Text(json.getString(key)));
						} else if (type.equals("boolean")) {
							method.invoke(result, json.getBoolean(key));
						} else if (type.equals("long")) {
							method.invoke(result, json.getLong(key));
						} else if (type.equals("int")) {
							method.invoke(result, json.getInt(key));
						} else {
							method.invoke(result, json.get(key));
						}
					}
				}
			} catch (JSONException e) {
				logger.warning("JSONException occured: " + e.getMessage());
				throw new FormatException();
			} catch (NoSuchMethodException e) {
				logger.warning("NoSuchMethodException occured: "
						+ e.getMessage());
				throw new FormatException();
			} catch (SecurityException e) {
				logger.warning("SecurityException occured: " + e.getMessage());
				throw new FormatException();
			} catch (IllegalAccessException e) {
				logger.warning("IllegalAccessException occured: "
						+ e.getMessage());
				throw new FormatException();
			} catch (IllegalArgumentException e) {
				logger.warning("IllegalArgumentException occured: "
						+ e.getMessage());
				throw new FormatException();
			} catch (InvocationTargetException e) {
				logger.warning("InvocationTargetException occured: "
						+ e.getMessage());
				throw new FormatException();
			} catch (InstantiationException e) {
				logger.warning("InstantiationException occured: "
						+ e.getMessage());
				throw new FormatException();
			}
		}
		return result;
	}

	private String getGetter(final String fieldName, final Class<?> type) {
		final StringBuilder sBuilder = new StringBuilder();
		if (type.toString().equals("boolean")) {
			sBuilder.append("is");
		} else {
			sBuilder.append("get");
		}
		sBuilder.append(this.firstToUpperCharacter(fieldName));
		return sBuilder.toString();
	}

	private String getSetter(final String fieldName) {
		final StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("set");
		sBuilder.append(this.firstToUpperCharacter(fieldName));
		return sBuilder.toString();
	}

	private String firstToUpperCharacter(final String string) {
		final StringBuilder sBuilder = new StringBuilder();
		final char[] characters = string.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			if (i == 0) {
				sBuilder.append(("" + characters[i]).toUpperCase());
			} else {
				sBuilder.append(characters[i]);
			}
		}
		return sBuilder.toString();
	}
}