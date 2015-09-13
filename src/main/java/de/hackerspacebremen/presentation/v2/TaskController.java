package de.hackerspacebremen.presentation.v2;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.ApnsDataService;
import de.hackerspacebremen.domain.GcmDataService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.email.StatusEmail;
import de.hackerspacebremen.format.FormatException;
import de.hackerspacebremen.format.FormatFactory;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.format.ResultKind;
import de.hackerspacebremen.format.SpeakingDateFormat;
import de.hackerspacebremen.presentation.BasicOsnRestController;
import de.hackerspacebremen.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({ "/v2/task", "/latest/task" })
public class TaskController extends BasicOsnRestController {

	private SpaceStatusService statusService;
	private GcmDataService gcmService;
	private ApnsDataService apnsDataService;
	private StatusEmail statusEmail;

	@Autowired
	public TaskController(ErrorMessages errorMessages, SpaceStatusService statusService, GcmDataService gcmService,
			ApnsDataService apnsDataService, StatusEmail statusEmail) {
		super(errorMessages);
		this.statusService = statusService;
		this.gcmService = gcmService;
		this.apnsDataService = apnsDataService;
		this.statusEmail = statusEmail;
	}

	@RequestMapping(value = "/gcm", method = RequestMethod.POST)
	public BasicResultObject gcm(@RequestParam(required = false) Long statusId,
			@RequestParam(required = false) String format) {
		BasicResultObject result = null;
		try {
			SpaceStatus status = statusService.currentStatus();
			if (statusId != null && status.getId().equals(statusId)) {
				status = statusService.currentCopyStatus();
				MessageFormat.fitMessageSize(status);
				final ResultKind kind = ResultKind.find(format);

				gcmService
						.sendMessageToDevices(FormatFactory.getFormatter(kind).format(status, AppConstants.LEVEL_VIEW));
				result = new BasicResultObject("Messages were sent to the GCM server!");
			} else {
				result = new BasicResultObject(
						"The given status id is not valid anymore! The message couldn't be send ...'");
			}
		} catch (FormatException e) {
			result = this.error(77);
			log.warn(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/apns", method = RequestMethod.POST)
	public BasicResultObject apns(@RequestParam(required = false) Long statusId) {
		BasicResultObject result = null;

		SpaceStatus status = statusService.currentStatus();
		if (statusId != null && status.getId().equals(statusId)) {
			status = statusService.currentCopyStatus();
			MessageFormat.fitSmallMessageSize(status);

			final Date date = new Date(status.getTime());
			final String statusShort;
			if (status.getStatus().equals(AppConstants.OPEN)) {
				statusShort = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " ge\u00f6ffnet";
			} else {
				statusShort = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " geschlossen";
			}

			apnsDataService.sendMessageToDevices(statusShort, status.getMessage().toString());
			result = new BasicResultObject("Messages were sent to the APNS server!");
		} else {
			result = new BasicResultObject(
					"The given status id is not valid anymore! The message couldn't be send ...'");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public BasicResultObject mail(@RequestParam(required = false) Long statusId) {
		statusEmail.send(this.statusService.findById(statusId));
		return new BasicResultObject("Messages were sent via Mail!");
	}
}
