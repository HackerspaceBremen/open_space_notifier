package de.hackerspacebremen.presentation.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.appengine.api.utils.SystemProperty;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.commands.resultobject.Status;
import de.hackerspacebremen.commands.resultobject.v11.Icon;
import de.hackerspacebremen.commands.resultobject.v11.StatusV11;
import de.hackerspacebremen.commands.resultobject.v12.StatusV12;
import de.hackerspacebremen.commands.resultobject.v13.IssueReport;
import de.hackerspacebremen.commands.resultobject.v13.Location;
import de.hackerspacebremen.commands.resultobject.v13.SpaceFED;
import de.hackerspacebremen.commands.resultobject.v13.State;
import de.hackerspacebremen.commands.resultobject.v13.StatusV13;
import de.hackerspacebremen.commands.resultobject.v13.cache.Cache;
import de.hackerspacebremen.commands.resultobject.v13.cache.Schedule;
import de.hackerspacebremen.commands.resultobject.v13.contact.ContactV13;
import de.hackerspacebremen.commands.resultobject.v13.contact.Google;
import de.hackerspacebremen.commands.resultobject.v8.StatusV8;
import de.hackerspacebremen.commands.resultobject.v9.StatusV9;
import de.hackerspacebremen.common.SpaceAPIVersion;
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.format.LanguageFormat;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.presentation.v1.StatusController;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;

@RestController
@RequestMapping({ "/v2/status", "/latest/status" })
public class StatusV2Controller extends StatusController {

	private SpaceStatusService statusService;
	private PropertyService propertyService;

	@Autowired
	public StatusV2Controller(ErrorMessages errorMessages, SpaceStatusService statusService,
			PropertyService propertyService) {
		super(errorMessages, statusService, propertyService);
		this.statusService = statusService;
		this.propertyService = propertyService;
	}

	@ResponseBody
	@RequestMapping("/{api}")
	public BasicResultObject forApi(@RequestParam(required = false) Boolean htmlEncoded,
			@RequestParam(required = false) String format,
			@PathVariable String api) {
		BasicResultObject result = null;

		SpaceAPIVersion spaceAPIVersion = SpaceAPIVersion.get(api, "v2");
		
		final SpaceStatus status = statusService.currentCopyStatus();
		if (status == null) {
			result = this.error(17);
		} else {
			final LanguageFormat languageFormat = LanguageFormat.createInstance(format);
			result = this.fetchStatus(spaceAPIVersion, status, languageFormat, Boolean.TRUE.equals(htmlEncoded));
		}

		return result;
	}

	@RequestMapping(".rss")
	public void rss() {
		// TODO
	}

	@RequestMapping(".atom")
	public void atom() {
		// TODO
	}
	
	private BasicResultObject fetchStatus(SpaceAPIVersion apiVersion, final SpaceStatus spaceStatus, final LanguageFormat format, final boolean htmlEncoded) {
		BasicResultObject result = null;
		if (apiVersion == SpaceAPIVersion.UNKNOWN) {
			result = this.error(30);
		} else {
			final StatusV13 status = this.fetchStatusV13(spaceStatus, htmlEncoded);
			status.setSuccess("Status found");

			if (apiVersion.isNewer(8)) {
				// TODO add the following information if available
				// statusResult.setEvents(events);
			} else if (apiVersion.isNewer(13)) {
				// TODO add the following information if available
				// statusResult.setSensors(sensors);
			}

			if (apiVersion == SpaceAPIVersion.API_0_13) {
				result = status;
			} else {
				switch (apiVersion) {
				case API_0_12:
					result = new StatusV12(status, new Status(spaceStatus, format));
					break;
				case API_0_11:
					result = new StatusV11(status);
					break;
				case API_0_09:
					result = new StatusV9(status);
					break;
				case API_0_08:
					result = new StatusV8(status);
					break;
				default:
					result = this.error(31);
				}
			}
		}
		
		return result;
	}

	private StatusV13 fetchStatusV13(final SpaceStatus status, final boolean htmlEncoded) {
		final String url = "http://" + SystemProperty.applicationId.get() + ".appspot.com/";

		final GeneralProperties generalInformation = propertyService.fetchProperties(GeneralProperties.class);

		final StatusV13 statusV13 = new StatusV13();
		// statusResult.setCam(cam);
		final Cache cache = new Cache();
		cache.setSchedule(Schedule.QUARTER_HOUR);
		statusV13.setCache(cache);
		final ContactV13 contact = new ContactV13();
		// contact.setEmail("info@hackerspace-bremen.de");
		contact.setEmail(nullIfEmpty(generalInformation.getEmail()));
		// contact.setFacebook("https://www.facebook.com/HackerspaceBremen");
		contact.setFacebook(nullIfEmpty(generalInformation.getFacebookUrl()));
		// contact.setFoursquare(foursquare);
		contact.setFoursquare(nullIfEmpty(generalInformation.getFoursquareUrl()));
		final String googlePlusUrl = nullIfEmpty(generalInformation.getGooglePlusUrl());
		if (googlePlusUrl != null) {
			final Google google = new Google();
			// google.setPlus("https://plus.google.com/106849621647585475724");
			google.setPlus(googlePlusUrl);
			contact.setGoogle(google);
		}
		// contact.setIdentica(identica);
		contact.setIdentica(nullIfEmpty(generalInformation.getIdenticaUrl()));
		// contact.setIrc("irc://irc.freenode.net/#hshb");
		contact.setIrc(nullIfEmpty(generalInformation.getIrc()));
		// contact.setIssueMail("notifier@hackerspace-bremen.de");
		final String issueMail = nullIfEmpty(generalInformation.getIssueMail());
		final List<IssueReport> issueReports = new ArrayList<>();
		if (issueMail != null) {
			contact.setIssueMail(issueMail);
			issueReports.add(IssueReport.ISSUE_MAIL);
		}
		statusV13.setIssueReportChannels(issueReports);
		// contact.setJabber(jabber);
		contact.setJabber(nullIfEmpty(generalInformation.getJabber()));
		// contact.setKeymaster(keymaster);

		// contact.setMailinglist("public@lists.hackerspace-bremen.de");
		contact.setMailinglist(nullIfEmpty(generalInformation.getMailinglist()));
		// contact.setPhone("+49 421 14 62 92 15");
		contact.setPhone(nullIfEmpty(generalInformation.getPhone()));
		// contact.setSip(sip);
		contact.setSip(nullIfEmpty(generalInformation.getSip()));
		// contact.setTwitter("@hspacehb");
		contact.setTwitter(nullIfEmpty(generalInformation.getTwitter()));

		statusV13.setContact(contact);

		// statusResult.setFeeds(feeds);

		final Location location = new Location();
		// location.setAddress("Bornstraße 14/15, 28195 Bremen, Germany");
		location.setAddress(nullIfEmpty(generalInformation.getLocationAddress()));
		// location.setLat(53.08178);
		location.setLat(generalInformation.getLocationLatitude());
		// location.setLon(8.805831);
		location.setLon(generalInformation.getLocationLongitude());
		statusV13.setLocation(location);
		statusV13.setLogo(url + "images/hackerspace_icon.png");
		// statusResult.setProjects(projects);
		// statusResult.setRadioShow(radioShow);
		// statusResult.setSpace("Hackerspace Bremen e.V.");
		statusV13.setSpace(nullIfEmpty(generalInformation.getSpaceName()));
		final SpaceFED spacefed = new SpaceFED();
		spacefed.setSpacenet(false);
		spacefed.setSpacephone(false);
		spacefed.setSpacesaml(false);
		statusV13.setSpacefed(spacefed);
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

		statusV13.setState(state);

		// statusResult.setStream(stream);
		// statusResult.setUrl("http://www.hackerspace-bremen.de");
		statusV13.setUrl(nullIfEmpty(generalInformation.getUrl()));

		return statusV13;
	}

	private String nullIfEmpty(final String parameter) {
		final String result;
		if (parameter == null || parameter.isEmpty()) {
			result = null;
		} else {
			result = parameter;
		}

		return result;
	}
}
