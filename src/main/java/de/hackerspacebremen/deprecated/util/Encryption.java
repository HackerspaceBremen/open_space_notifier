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

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class holds static method for encrypting strings.
 * 
 * @author sliedtke
 */
public final class Encryption {

	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger.getLogger(Encryption.class
			.getName());

	/**
	 * private constructor so no object is created.
	 */
	private Encryption() {
	}

	public static byte[] encrypBlowfishBytes(final String toEncrypt,
			final String strkey) {
		try {
			final SecretKeySpec key = new SecretKeySpec(strkey.getBytes(),
					"Blowfish");
			final Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(toEncrypt.getBytes());
		} catch (InvalidKeyException ike) {
			logger.warning("InvalidKeyException occured: " + ike.getMessage());
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.warning("NoSuchAlgorithmException occured: "
					+ e.getMessage());
			return null;
		} catch (NoSuchPaddingException e) {
			logger.warning("NoSuchPaddingException occured: " + e.getMessage());
			return null;
		} catch (IllegalBlockSizeException e) {
			logger.warning("IllegalBlockSizeException occured: "
					+ e.getMessage());
			return null;
		} catch (BadPaddingException e) {
			logger.warning("BadPaddingException occured: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Encrypts the given toEncrypt String with the given strkey.
	 * 
	 * @param toEncrypt
	 *            given string to encrypt
	 * @param strkey
	 *            given key
	 * @return encrypted string
	 */
	public static String encryptBlowfish(final String toEncrypt,
			final String strkey) {
		return new String(encrypBlowfishBytes(toEncrypt, strkey));
	}

	public static String decryptBlowfish(final String toDecrypt,
			final String strKey) {
		try {
			SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "Blowfish");

			Cipher cipher = Cipher.getInstance("Blowfish");

			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(toDecrypt.getBytes());
			return new String(decrypted);
		} catch (NoSuchAlgorithmException e) {
			logger.warning("NoSuchAlgorithmException occured: "
					+ e.getMessage());
			return null;
		} catch (NoSuchPaddingException e) {
			logger.warning("NoSuchPaddingException occured: " + e.getMessage());
			return null;
		} catch (InvalidKeyException e) {
			logger.warning("InvalidKeyException occured: " + e.getMessage());
			return null;
		} catch (IllegalBlockSizeException e) {
			logger.warning("IllegalBlockSizeException occured: "
					+ e.getMessage());
			return null;
		} catch (BadPaddingException e) {
			logger.warning("BadPaddingException occured: " + e.getMessage());
			return null;
		}

	}

	// TODO remove from Encryption, because it isn't a real encryption
	public static String encryptMD5(final String toEncrypt) {
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = toEncrypt.getBytes("UTF-8");
			final MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < thedigest.length; i++) {
				sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException occured: "
					+ e.getMessage());
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.warning("NoSuchAlgorithmException occured: "
					+ e.getMessage());
			return null;
		}
	}

	// TODO remove from Encryption, because it isn't a real encryption
	public static String encryptSHA256(final String toEncrypt) {
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = toEncrypt.getBytes("UTF-8");
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] thedigest = md.digest(bytesOfMessage);
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < thedigest.length; i++) {
				sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException occured: "
					+ e.getMessage());
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.warning("NoSuchAlgorithmException occured: "
					+ e.getMessage());
			return null;
		}
	}
}