package de.hackerspacebremen.presentation.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hackerspacebremen.presentation.v1.CronJobController;

@Controller
@RequestMapping({"/v2/cron","/latest/cron"})
public class CronJobV2Controller extends CronJobController{

	
	@Override
	@RequestMapping("/statuscheck")
	public void statusCheck() {
		super.statusCheck();
	}
	
	@RequestMapping("/clearauthattempts")
	public void clearAuthAttempts(){
		// TODO
	}
}
