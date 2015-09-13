package de.hackerspacebremen.presentation.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.GcmDataService;
import de.hackerspacebremen.presentation.BasicOsnRestController;

@RestController
public class GcmController extends BasicOsnRestController {

	private GcmDataService gcmDataService;

	@Autowired
	public GcmController(ErrorMessages errorMessages, GcmDataService gcmDataService) {
		super(errorMessages);
		this.gcmDataService = gcmDataService;
	}

	@ResponseBody
	@RequestMapping(value = { "/register", "/v1/register" }, method = RequestMethod.POST)
	public BasicResultObject register(@RequestParam(required = false) final String deviceId,
			@RequestParam(required = false) final String registrationId) {
		this.gcmDataService.register(deviceId, registrationId);
		return new BasicResultObject("Your registry was successful");
	}

	@ResponseBody
	@RequestMapping(value = { "/unregister", "/v1/unregister" }, method = RequestMethod.POST)
	public BasicResultObject unregister(@RequestParam(required = false) final String deviceId) {
		gcmDataService.unregister(deviceId);
		return new BasicResultObject("You successfully unregistered");
	}
}
