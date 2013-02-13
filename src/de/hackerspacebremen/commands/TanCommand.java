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
package de.hackerspacebremen.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import de.hackerspacebremen.Factory;
import de.hackerspacebremen.data.entities.Tan;
import de.hackerspacebremen.domain.api.TanService;
import de.liedtke.presentation.WebCommand;

/**
 * @author Steve Liedtke
 */
public class TanCommand extends WebCommand{

	/* (non-Javadoc)
	 * @see de.liedtke.presentation.WebCommand#process()
	 */
	@Override
	public void process() throws ServletException, IOException {
		final TanService tanService = Factory.createTanService();
		this.registerService(tanService);
		
		final Tan tan = tanService.createNewTan();
		
		this.handleSuccess(tan.getTanString(), null);
		
		super.process();
	}
}
