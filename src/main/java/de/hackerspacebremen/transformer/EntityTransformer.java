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
package de.hackerspacebremen.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import de.hackerspacebremen.common.PresentationMode;
import de.hackerspacebremen.data.annotation.FormatPart;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

/**
 * @author Steve
 *
 */
public class EntityTransformer extends AbstractTransformer {

	private static final Logger logger = Logger.getLogger(EntityTransformer.class.getName());
	
	private List<PresentationMode> modes;
	
	public EntityTransformer(PresentationMode ... modes){
		this.modes = Arrays.asList(modes);
	}
	
	/* (non-Javadoc)
	 * @see flexjson.transformer.Transformer#transform(java.lang.Object)
	 */
	@Override
	public void transform(final Object entity) {
		final TypeContext typeContext = getContext().peekTypeContext();
		
		if(entity != null){
			if (!typeContext.isFirst()) 
				getContext().writeComma();
			
			getContext().writeName(typeContext.getPropertyName());
			getContext().writeOpenObject();
			boolean first = true;
			for(Field field : entity.getClass().getDeclaredFields()){
				if(field.isAnnotationPresent(FormatPart.class) && 
						modes.contains(PresentationMode.createInstance(field.getAnnotation(FormatPart.class).level()))){
					try{
					final Method method = entity.getClass()
							.getMethod(
									this.getGetter(field.getName(),
											field.getType()),
									(Class<?>[]) null);
					final Object methodResult = method.invoke(entity);
					if (methodResult != null) {
						if(first){
							first = false;
						}else{
							getContext().writeComma();
						}
						getContext().writeName(field.getAnnotation(FormatPart.class).key());
						getContext().transform(methodResult);
					}
					}catch(NoSuchMethodException e){
						logger.severe("NoSuchMethodException occurred: " + e.getMessage());
					} catch (IllegalArgumentException e) {
						logger.severe("IllegalArgumentException occurred: " + e.getMessage());
					} catch (IllegalAccessException e) {
						logger.severe("IllegalAccessException occurred: " + e.getMessage());
					} catch (InvocationTargetException e) {
						logger.severe("InvocationTargetException occurred: " + e.getMessage());
					}
				}
			}
			getContext().writeCloseObject();
		}
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
	
	@Override
    public Boolean isInline() {
        return Boolean.TRUE;
    }
}
