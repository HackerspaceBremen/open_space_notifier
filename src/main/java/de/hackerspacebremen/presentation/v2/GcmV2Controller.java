package de.hackerspacebremen.presentation.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.GcmDataService;
import de.hackerspacebremen.presentation.v1.GcmController;

@Controller
@RequestMapping({"/v2/gcm","/latest/gcm"})
public class GcmV2Controller extends GcmController {


	@Autowired
	public GcmV2Controller(GcmDataService gcmDataService) {
		super(gcmDataService);
	}
	
	@Override
	@RequestMapping("/register")
	public BasicResultObject register(@RequestParam String deviceId, 
			@RequestParam String registrationId) {
		return super.register(deviceId, registrationId);
	}
	
	@Override
	@RequestMapping("/unregister")
	public void unregister(@RequestParam final String deviceId) {
		super.unregister(deviceId);
	}
}
