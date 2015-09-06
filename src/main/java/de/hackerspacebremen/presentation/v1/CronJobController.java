package de.hackerspacebremen.presentation.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/cron","/v1/cron"})
public class CronJobController {

	@RequestMapping("/statuscheck")
	public void statusCheck(){
		// TODO
	}
}
