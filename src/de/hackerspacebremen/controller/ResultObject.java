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

import de.liedtke.data.entity.BasicEntity;

/**
 * @author Steve Liedtke
 */
public final class ResultObject {

	private final int CODE;
	
	private final String MESSAGE;
	
	private final String SUCCESS;
	
	private final String ERROR;
	
	private BasicEntity RESULT;
	
	public ResultObject(final int CODE, final String MESSAGE){
		this.CODE = CODE;
		this.MESSAGE = MESSAGE;
		if(this.CODE==0){
			this.SUCCESS = MESSAGE;
			this.ERROR = null;
		}else{
			this.SUCCESS = null;
			this.ERROR = MESSAGE;
		}
		this.RESULT = null;
	}
	
	public ResultObject(final String MESSAGE, final BasicEntity RESULT){
		this.CODE = 0;
		this.MESSAGE = MESSAGE;
		this.SUCCESS = MESSAGE;
		this.ERROR = null;
		this.RESULT = RESULT;
	}

	/**
	 * @return the rESULT
	 */
	public BasicEntity getRESULT() {
		return RESULT;
	}

	/**
	 * @param rESULT the rESULT to set
	 */
	public void setRESULT(BasicEntity rESULT) {
		RESULT = rESULT;
	}

	/**
	 * @return the cODE
	 */
	public int getCODE() {
		return CODE;
	}

	/**
	 * @return the mESSAGE
	 */
	public String getMESSAGE() {
		return MESSAGE;
	}

	/**
	 * @return the sUCCESS
	 */
	public String getSUCCESS() {
		return SUCCESS;
	}

	/**
	 * @return the eRROR
	 */
	public String getERROR() {
		return ERROR;
	}
}