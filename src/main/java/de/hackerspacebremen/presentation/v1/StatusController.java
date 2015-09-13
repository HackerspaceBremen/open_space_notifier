package de.hackerspacebremen.presentation.v1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.appengine.api.utils.SystemProperty;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.commands.resultobject.Status;
import de.hackerspacebremen.commands.resultobject.v11.Icon;
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
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.format.LanguageFormat;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.presentation.BasicOsnRestController;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;

@RestController
@RequestMapping({ "/status", "/v1/status" })
public class StatusController extends BasicOsnRestController {

	private SpaceStatusService statusService;
	private PropertyService propertyService;

	@Autowired
	public StatusController(ErrorMessages errorMessages, SpaceStatusService statusService,
			PropertyService propertyService) {
		super(errorMessages);
		this.statusService = statusService;
		this.propertyService = propertyService;
	}

	@RequestMapping
	@ResponseBody
	public BasicResultObject status(@RequestParam(required = false) Boolean htmlEncoded,
			@RequestParam(required = false) String format) {
		BasicResultObject result = null;
		final SpaceStatus status = statusService.currentCopyStatus();
		if (status == null) {
			result = this.error(17);
		} else {
			final LanguageFormat languageFormat = LanguageFormat.createInstance(format);
			result = this.fetchStatus(status, languageFormat, Boolean.TRUE.equals(htmlEncoded));
		}
		return result;
	}

	private BasicResultObject fetchStatus(final SpaceStatus spaceStatus, final LanguageFormat format,
			final boolean htmlEncoded) {
		final StatusV13 status = this.fetchStatusV13(spaceStatus, htmlEncoded);
		status.setSuccess("Status found");

		// TODO add the following information if available
		// statusResult.setEvents(events);

		return new StatusV12(status, new Status(spaceStatus, format));
	}

	private StatusV13 fetchStatusV13(final SpaceStatus status, final boolean htmlEncoded) {
		final String url = "http://" + SystemProperty.applicationId.get() + ".appspot.com/";

		final GeneralProperties generalInformation = propertyService.fetchProperties(GeneralProperties.class);

		final StatusV13 statusResult = new StatusV13();
		// statusResult.setCam(cam);
		final Cache cache = new Cache();
		cache.setSchedule(Schedule.QUARTER_HOUR);
		statusResult.setCache(cache);
		final ContactV13 contact = new ContactV13();
		// TODO check if information is empty before setting
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
		statusResult.setIssueReportChannels(issueReports);
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

		statusResult.setContact(contact);

		// statusResult.setFeeds(feeds);

		final Location location = new Location();
		// location.setAddress("Bornstraße 14/15, 28195 Bremen, Germany");
		location.setAddress(nullIfEmpty(generalInformation.getLocationAddress()));
		// location.setLat(53.08178);
		location.setLat(generalInformation.getLocationLatitude());
		// location.setLon(8.805831);
		location.setLon(generalInformation.getLocationLongitude());
		statusResult.setLocation(location);
		statusResult.setLogo(url + "images/hackerspace_icon.png");
		// statusResult.setProjects(projects);
		// statusResult.setRadioShow(radioShow);
		// statusResult.setSpace("Hackerspace Bremen e.V.");
		statusResult.setSpace(nullIfEmpty(generalInformation.getSpaceName()));
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
		// statusResult.setUrl("http://www.hackerspace-bremen.de");
		statusResult.setUrl(nullIfEmpty(generalInformation.getUrl()));

		return statusResult;
	}

	private String nullIfEmpty(final String parameter) {
		final String result;
		if (StringUtils.isEmpty(parameter)) {
			result = null;
		} else {
			result = parameter;
		}

		return result;
	}
}
