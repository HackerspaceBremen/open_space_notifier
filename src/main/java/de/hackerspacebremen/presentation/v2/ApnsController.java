package de.hackerspacebremen.presentation.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.ApnsDataService;
import de.hackerspacebremen.presentation.BasicOsnRestController;

@RestController
@RequestMapping({ "/v2/apns", "/latest/apns" })
public class ApnsController extends BasicOsnRestController {

	private ApnsDataService apnsDataService;

	@Autowired
	public ApnsController(ErrorMessages errorMessages, ApnsDataService apnsDataService) {
		super(errorMessages);
		this.apnsDataService = apnsDataService;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public BasicResultObject register(@RequestParam(required = false) String deviceId,
			@RequestParam(required = false) String deviceToken) {
		apnsDataService.register(deviceId, deviceToken);
		return new BasicResultObject("Your registry was successful");
	}

	@ResponseBody
	@RequestMapping(value = "/unregister", method = RequestMethod.POST)
	public BasicResultObject unregister(@RequestParam(required = false) String deviceId) {
		apnsDataService.unregister(deviceId);
		return new BasicResultObject("You successfully unregistered");
	}
}
