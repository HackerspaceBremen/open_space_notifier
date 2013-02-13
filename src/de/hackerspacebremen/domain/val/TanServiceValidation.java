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
package de.hackerspacebremen.domain.val;

import de.hackerspacebremen.data.entities.Tan;
import de.hackerspacebremen.domain.api.TanService;
import de.liedtke.business.validation.BasicServiceValidation;
import de.liedtke.validation.ValidationException;

/**
 * @author Steve Liedtke
 */
public final class TanServiceValidation extends BasicServiceValidation implements TanService{

	public TanServiceValidation(final TanService service){
		this.basicService = service;
	}
	
	/* (non-Javadoc)
	 * @see de.hackerspacebremen.domain.api.TanService#createNewTan()
	 */
	@Override
	public Tan createNewTan() {
		return ((TanService)this.basicService).createNewTan();
	}

	/* (non-Javadoc)
	 * @see de.hackerspacebremen.domain.api.TanService#checkTan(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkTan(String hash, String name, String decryptedPass,
			String message) throws ValidationException {
		this.validateIfEmpty(hash, 21);
		this.validateIfEmpty(decryptedPass, 12);
		this.validateIfEmpty(name, 11);
		this.validateIfEmpty(message, 10);
		return ((TanService)this.basicService).checkTan(hash, name, decryptedPass, message);
	}
}