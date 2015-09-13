package de.hackerspacebremen.presentation.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.AuthAttemptService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.domain.helper.StatusTaskStarter;
import de.hackerspacebremen.presentation.v1.CronJobController;

@RestController
@RequestMapping({ "/v2/cron", "/latest/cron" })
public class CronJobV2Controller extends CronJobController {

	private AuthAttemptService authAttemptService;

	@Autowired
	public CronJobV2Controller(ErrorMessages errorMessages, SpaceStatusService statusService,
			StatusTaskStarter statusTaskStarter, AuthAttemptService authAttemptService) {
		super(errorMessages, statusService, statusTaskStarter);
		this.authAttemptService = authAttemptService;
	}

	@Override
	@RequestMapping("/statuscheck")
	public BasicResultObject statusCheck() {
		return super.statusCheck();
	}

	@RequestMapping("/clearauthattempts")
	public void clearAuthAttempts() {
		authAttemptService.clearAttemptAmounts();
	}
}
