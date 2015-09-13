package de.hackerspacebremen.presentation.v1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.commands.resultobject.Status;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.AuthAttemptService;
import de.hackerspacebremen.domain.BasicHTTPAuthenticationService;
import de.hackerspacebremen.domain.GcmDataService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.domain.helper.StatusTaskStarter;
import de.hackerspacebremen.format.FormatException;
import de.hackerspacebremen.format.FormatFactory;
import de.hackerspacebremen.format.LanguageFormat;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.format.ResultKind;
import de.hackerspacebremen.presentation.BasicOsnRestController;
import de.hackerspacebremen.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({ "/cmd", "/v1/cmd" })
public class CommandController extends BasicOsnRestController{

	private SpaceStatusService statusService;
	private AuthAttemptService authAttemptService;
	private BasicHTTPAuthenticationService authService;
	private StatusTaskStarter statusTaskStarter;
	private GcmDataService gcmService;

	@Autowired
	public CommandController(ErrorMessages errorMessages, SpaceStatusService statusService,
			AuthAttemptService authAttemptService, BasicHTTPAuthenticationService authService,
			StatusTaskStarter statusTaskStarter, GcmDataService gcmService) {
		super(errorMessages);
		this.statusService = statusService;
		this.authAttemptService = authAttemptService;
		this.authService = authService;
		this.statusTaskStarter = statusTaskStarter;
		this.gcmService = gcmService;
	}

	@RequestMapping(value = "/gcm", method = RequestMethod.POST)
	public BasicResultObject gcm(@RequestParam(required = false) String statusId,
			@RequestParam(required = false) String format) {
		BasicResultObject result = null;
		try{
			SpaceStatus status = statusService.currentStatus();
			if(statusId != null && status.getId().equals(Long.valueOf(statusId))){
				status = statusService.currentCopyStatus();
				MessageFormat.fitMessageSize(status);
				final ResultKind kind = ResultKind.find(format);
				
				gcmService.sendMessageToDevices(FormatFactory.getFormatter(kind).format(status, AppConstants.LEVEL_VIEW));
				result = new BasicResultObject("Messages were sent to the GCM server!");
			}else{
				result = new BasicResultObject("The given status id is not valid anymore! The message couldn't be send ...'");
			}
		} catch (FormatException e) {
			result = this.error(77);
			log.warn(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	public BasicResultObject open(@RequestParam(required = false) final String name,
			@RequestParam(required = false) final Boolean encoded, @RequestParam(required = false) final String pass,
			@RequestParam(required = false) final String message) throws UnsupportedEncodingException {

		BasicResultObject result = null;

		final String password = getPassword(encoded, pass);
		final String decodedMessage = getMessage(encoded, message);

		if (authAttemptService.checkAttemptMax(name)) {
			result = this.error(55);
		} else if (authService.authenticate(name, password)) {
			SpaceStatus status = statusService.currentCopyStatus();
			if (AppConstants.OPEN.equals(status.getStatus())) {
				result = this.error(3);
			} else {
				status = statusService.openSpace(name, decodedMessage);
				statusTaskStarter.startTasks(status);
				result = new BasicResultObject("Space was opened");
			}
		} else {
			result = this.error(1);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public BasicResultObject close(@RequestParam(required = false) String name,
			@RequestParam(required = false) Boolean encoded, @RequestParam(required = false) String pass,
			@RequestParam(required = false) String message) throws UnsupportedEncodingException {
		BasicResultObject result = null;

		final String password = getPassword(encoded, pass);
		final String decodedMessage = getMessage(encoded, message);

		if (authAttemptService.checkAttemptMax(name)) {
			result = this.error(55);
		} else if (authService.authenticate(name, password)) {
			SpaceStatus status = statusService.currentCopyStatus();
			if (status == null || status.getStatus().equals(AppConstants.OPEN)) {
				status = statusService.closeSpace(name, decodedMessage);
				this.statusTaskStarter.startTasks(status);
				result = new BasicResultObject("Space was closed");
			} else {
				result = this.error(4);
			}
		} else {
			result = error(1);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public BasicResultObject message(@RequestParam(required = false) String name,
			@RequestParam(required = false) Boolean encoded, @RequestParam(required = false) String pass,
			@RequestParam(required = false) String message, @RequestParam(required = false) String format,
			@RequestParam(required = false) String time) throws UnsupportedEncodingException {

		BasicResultObject result = null;

		final String password = getPassword(encoded, pass);
		final String decodedMessage = getMessage(encoded, message);

		log.info("message: " + decodedMessage);
		log.info("format: " + format);
		log.info("time: " + time);

		if (authAttemptService.checkAttemptMax(name)) {
			result = this.error(55);
		} else if (authService.authenticate(name, password)) {
			final SpaceStatus status = statusService.currentStatus();
			final String timeOfCurrent;
			if (format == null || format.isEmpty()) {
				timeOfCurrent = "" + status.getTime();
			} else {
				timeOfCurrent = new Status(status, LanguageFormat.createInstance(format)).getTime();
			}
			log.info("timeOfCurrent: " + timeOfCurrent);
			if (time != null && timeOfCurrent.equals(time)) {
				statusService.changeMessage(status, decodedMessage);
				result = new BasicResultObject("Statusmessage was changed");
			} else {
				result = this.error(19);
			}
		} else {
			result = this.error(18);
		}

		return result;
	}

	private String getMessage(final Boolean encoded, final String message) throws UnsupportedEncodingException {
		final String decodedMessage;
		if (Boolean.TRUE.equals(encoded)) {
			if (message == null) {
				decodedMessage = null;
			} else {
				decodedMessage = URLDecoder.decode(message, Constants.UTF8);
			}
		} else {
			decodedMessage = message;
		}
		return decodedMessage;
	}

	protected String getPassword(final Boolean encoded, final String pass) throws UnsupportedEncodingException {
		final String password;
		if (Boolean.TRUE.equals(encoded)) {
			password = URLDecoder.decode(pass, Constants.UTF8);
		} else {
			password = pass;
		}
		return password;
	}
}
