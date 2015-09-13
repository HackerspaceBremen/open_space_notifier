package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

/**
 * Open Space Notifier Module.
 * 
 * Module for all classes which are not explicitly layered.
 * 
 * @author Steve
 *
 */
@Deprecated
public class OSNModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind(ErrorMessages.class).to(MyErrorMessages.class).in(Singleton.class);
	}
}
