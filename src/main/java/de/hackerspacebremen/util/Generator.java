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

package de.hackerspacebremen.util;

import java.util.Random;

/**
 * This class holds static methods for generating objects.
 * 
 * @author Jan Stalhut, Matthias Friedrich, Steve Liedtke
 */
public final class Generator {

	/**
	 * holding all possible string characters used in string generation.
	 */
	private static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz.-_)(/$!:;,";

	/**
	 * private constructor so no object is created.
	 */
	private Generator() {
	}

	/**
	 * This method generates a random string.
	 * 
	 * @param length
	 *            given length of resulting string
	 * @return random string
	 */
	public static String randomString(final int length) {
		final StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(ABC.charAt(new Random().nextInt(ABC.length())));
		}
		return sb.toString();
	}
}