package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.util.ErrorMessages;

/**
 * Open Space Notifier Module.
 * 
 * Module for all classes which are not explicitly layered.
 * 
 * @author Steve
 *
 */
public class OSNModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(ErrorMessages.class).to(MyErrorMessages.class).in(Singleton.class);
	}
}
