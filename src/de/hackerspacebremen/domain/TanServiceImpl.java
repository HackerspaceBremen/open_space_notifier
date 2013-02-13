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
package de.hackerspacebremen.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

import de.hackerspacebremen.data.api.TanDAO;
import de.hackerspacebremen.data.entities.Tan;
import de.hackerspacebremen.domain.api.TanService;
import de.liedtke.business.BasicServiceImpl;
import de.liedtke.util.Encryption;
import de.liedtke.util.Generator;

/**
 * @author Steve Liedtke
 */
public final class TanServiceImpl extends BasicServiceImpl implements TanService {

	/**
     * static attribute used for logging.
     */
	private static final Logger logger = Logger.getLogger(TanServiceImpl.class.getName());
	
	public TanServiceImpl(final TanDAO dao){
		this.basicDAO = dao;
	}
	
	/* (non-Javadoc)
	 * @see de.hackerspacebremen.domain.api.TanService#createNewTan()
	 */
	@Override
	public Tan createNewTan() {
		final Tan tan = new Tan();
		tan.setTanString(Generator.randomString(10));
		//tan.setActive(Boolean.TRUE);
		return ((TanDAO)this.basicDAO).persist(tan);
	}

	/* (non-Javadoc)
	 * @see de.hackerspacebremen.domain.api.TanService#checkTan(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkTan(String hash, String name, String decryptedPass,
			String message) {
		final Tan tan = ((TanDAO)this.basicDAO).findCurrent();
		
		logger.info("tan: " + tan.getTanString());
		logger.info("name: " + name);
		logger.info("pass: " + decryptedPass);
		logger.info("message: " + message);
		String serverHash = null;
		try {
			serverHash = Encryption.encryptSHA256(name+decryptedPass+tan.getTanString()+URLEncoder.encode(message, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException occured: " + e.getMessage());
		}
		logger.info("reqHash: " + hash);
		logger.info("serverHash: " + serverHash);
		return serverHash != null && hash.equals(serverHash);
	}
}