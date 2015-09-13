package de.hackerspacebremen.presentation.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.GcmDataService;
import de.hackerspacebremen.presentation.v1.GcmController;

@RestController
@RequestMapping({ "/v2/gcm", "/latest/gcm" })
public class GcmV2Controller extends GcmController {

	@Autowired
	public GcmV2Controller(ErrorMessages errorMessages, GcmDataService gcmDataService) {
		super(errorMessages, gcmDataService);
	}

	@Override
	@ResponseBody
	@RequestMapping("/register")
	public BasicResultObject register(@RequestParam(required = false) String deviceId,
			@RequestParam(required = false) String registrationId) {
		return super.register(deviceId, registrationId);
	}

	@Override
	@ResponseBody
	@RequestMapping("/unregister")
	public BasicResultObject unregister(@RequestParam(required = false) final String deviceId) {
		return super.unregister(deviceId);
	}
}
