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
package de.hackerspacebremen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Steve Liedtke
 *
 */
@Controller
@RequestMapping("/cmd")
public final class CommandController {

	
//	@RequestMapping(value="/{name}", method = RequestMethod.GET)
//	public String jsonGetMovie(@PathVariable String name, ModelMap model) {
// 
//		model.addAttribute("movie", name);
//		model.addAttribute("message", this.message);
// 
//		//return to jsp page, configured in mvc-dispatcher-servlet.xml, view resolver
//		return "list";
// 
//	}
}
