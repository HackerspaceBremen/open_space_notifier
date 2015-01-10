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

import lombok.Setter;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Inject;

import de.hackerspacebremen.commands.resultobjects.Status;
import de.hackerspacebremen.commands.resultobjects.v11.Icon;
import de.hackerspacebremen.commands.resultobjects.v11.StatusV11;
import de.hackerspacebremen.commands.resultobjects.v12.StatusV12;
import de.hackerspacebremen.commands.resultobjects.v13.Location;
import de.hackerspacebremen.commands.resultobjects.v13.SpaceFED;
import de.hackerspacebremen.commands.resultobjects.v13.State;
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.commands.resultobjects.v13.cache.Cache;
import de.hackerspacebremen.commands.resultobjects.v13.cache.Schedule;
import de.hackerspacebremen.commands.resultobjects.v13.contact.ContactV13;
import de.hackerspacebremen.commands.resultobjects.v13.contact.Google;
import de.hackerspacebremen.commands.resultobjects.v8.StatusV8;
import de.hackerspacebremen.commands.resultobjects.v9.StatusV9;
import de.hackerspacebremen.common.SpaceAPIVersion;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.format.LanguageFormat;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class ViewStatusCommand extends WebCommand {

	@Inject
	@Proxy
	private SpaceStatusService statusService;

	@Setter
	private SpaceAPIVersion apiVersion;

	protected void fetchStatus(final SpaceStatus spaceStatus,
			final LanguageFormat format, final boolean htmlEncoded) {
		if (this.apiVersion == SpaceAPIVersion.UNKNOWN) {
			this.handleError(30);
		} else {
			final StatusV13 status = this.fetchStatusV13(spaceStatus,
					htmlEncoded);
			status.setSuccess("Status found");

			if (this.apiVersion.isNewer(8)) {
				// TODO add the following information if available
				// statusResult.setEvents(events);
			} else if (this.apiVersion.isNewer(13)) {
				// TODO add the following information if available
				// statusResult.setSensors(sensors);
			}

			if (this.apiVersion == SpaceAPIVersion.API_0_13) {
				this.handleSuccess(status);
			} else {
				switch (this.apiVersion) {
				case API_0_12:
					this.handleSuccess(new StatusV12(status, new Status(
							spaceStatus, format)));
					break;
				case API_0_11:
					this.handleSuccess(new StatusV11(status));
					break;
				case API_0_09:
					this.handleSuccess(new StatusV9(status));
					break;
				case API_0_08:
					this.handleSuccess(new StatusV8(status));
					break;
				default:
					this.handleError(31);
				}
			}
		}
	}

	private StatusV13 fetchStatusV13(final SpaceStatus status,
			final boolean htmlEncoded) {
		final String url = "http://" + SystemProperty.applicationId.get()
				+ ".appspot.com/";

		final StatusV13 statusResult = new StatusV13();
		// statusResult.setCam(cam);
		final Cache cache = new Cache();
		cache.setSchedule(Schedule.QUARTER_HOUR);
		statusResult.setCache(cache);
		final ContactV13 contact = new ContactV13();
		contact.setEmail("info@hackerspace-bremen.de");
		contact.setFacebook("https://www.facebook.com/HackerspaceBremen");
		// contact.setFoursquare(foursquare);
		final Google google = new Google();
		google.setPlus("https://plus.google.com/106849621647585475724");
		contact.setGoogle(google);
		// contact.setIdentica(identica);
		contact.setIrc("irc://irc.freenode.net/#hshb");
		// contact.setIssueMail(issueMail);
		// contact.setJabber(jabber);
		// contact.setKeymaster(keymaster);
		contact.setMailinglist("public@lists.hackerspace-bremen.de");
		contact.setPhone("+49 421 14 62 92 15");
		// contact.setSip(sip);
		contact.setTwitter("@hspacehb");

		statusResult.setContact(contact);

		// statusResult.setFeeds(feeds);
		// statusResult.setIssueReportChannels(issueReportChannels);
		final Location location = new Location();
		location.setAddress("Bornstraße 14/15, 28195 Bremen, Germany");
		location.setLat(53.08178);
		location.setLon(8.805831);
		statusResult.setLocation(location);
		statusResult.setLogo(url + "images/hackerspace_icon.png");
		// statusResult.setProjects(projects);
		// statusResult.setRadioShow(radioShow);
		statusResult.setSpace("Hackerspace Bremen e.V.");
		final SpaceFED spacefed = new SpaceFED();
		spacefed.setSpacenet(false);
		spacefed.setSpacephone(false);
		spacefed.setSpacesaml(false);
		statusResult.setSpacefed(spacefed);
		final State state = new State();
		final Icon icon = new Icon();
		icon.setOpen(url + "images/status_auf_48px.png");
		icon.setClosed(url + "images/status_zu_48px.png");
		state.setIcon(icon);
		state.setLastchange(Long.valueOf(status.getTime() / 1000L));

		if (htmlEncoded) {
			MessageFormat.htmlEncode(status);
		}

		if (status.getMessage() == null) {
			state.setMessage("");
		} else {
			state.setMessage(status.getMessage().getValue());
		}

		state.setOpen(status.getStatus().equals("OPEN"));

		// state.setTriggerPerson(status.getOpenedBy());

		statusResult.setState(state);

		// statusResult.setStream(stream);
		statusResult.setUrl("http://www.hackerspace-bremen.de");

		return statusResult;
	}

	@Override
	public void process() throws ServletException, IOException {
		final boolean htmlEncoded = (req.getParameter("htmlEncoded") != null && req
				.getParameter("htmlEncoded").equals("true"));
		try {
			final SpaceStatus status = statusService.currentCopyStatus();
			if (status == null) {
				this.handleError(17);
			} else {
				final LanguageFormat format = LanguageFormat.createInstance(req
						.getParameter("format"));
				this.fetchStatus(status, format, htmlEncoded);
			}
		} catch (ValidationException ve) {
			this.handleError(ve);
		}

		// closing all services
		super.process();
	}
}