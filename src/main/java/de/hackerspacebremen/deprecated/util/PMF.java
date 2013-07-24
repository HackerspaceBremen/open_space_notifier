/*
 * Copyright 2012 Jan Stalhut, Steve Liedtke, Matthias Friedrich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

package de.hackerspacebremen.deprecated.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * This class is used to get the needed {@link PersistenceManagerFactory} for
 * datastore connections.
 * 
 * @author sliedtke
 */
public final class PMF {

	/**
	 * holding the {@link PersistenceManagerFactory}.
	 */
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	/**
	 * private constructor so no object is created.
	 */
	private PMF() {
	}

	/**
	 * public method which returns the {@link PersistenceManagerFactory}.
	 * 
	 * @return {@link PersistenceManagerFactory}
	 */
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}