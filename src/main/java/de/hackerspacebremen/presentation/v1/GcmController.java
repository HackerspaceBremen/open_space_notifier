package de.hackerspacebremen.presentation.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.GcmDataService;

@Controller
public class GcmController {

	private GcmDataService gcmDataService;

	@Autowired
	public GcmController(GcmDataService gcmDataService) {
		this.gcmDataService = gcmDataService;
	}

	@RequestMapping({ "/register", "/v1/register" })
	public BasicResultObject register(@RequestParam final String deviceId, @RequestParam final String registrationId) {
		this.gcmDataService.register(deviceId, registrationId);
		return new BasicResultObject("Your registry was successful");
	}

	@RequestMapping({ "/unregister", "/v1/unregister" })
	public void unregister(@RequestParam final String deviceId) {
		gcmDataService.unregister(deviceId);
	}
}
