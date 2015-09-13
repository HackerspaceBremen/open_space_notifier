package de.hackerspacebremen.presentation.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.domain.helper.StatusTaskStarter;
import de.hackerspacebremen.presentation.BasicOsnRestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({"/cron","/v1/cron"})
public class CronJobController extends BasicOsnRestController{

	private SpaceStatusService statusService;
	private StatusTaskStarter statusTaskStarter;

	@Autowired
	public CronJobController(ErrorMessages errorMessages, SpaceStatusService statusService, StatusTaskStarter statusTaskStarter) {
		super(errorMessages);
		this.statusService = statusService;
		this.statusTaskStarter = statusTaskStarter;
	}

	@RequestMapping("/statuscheck")
	public BasicResultObject statusCheck(){
		BasicResultObject result = null;
		final SpaceStatus currentStatus = statusService.currentCopyStatus();
		if (currentStatus.getStatus() != null && currentStatus.getStatus().equals("OPEN")) {
			log.info("The space wasn't closed - START closing space!");
			final SpaceStatus status = statusService.closeSpace("ADMIN - AUTOMATIC", "");
			this.statusTaskStarter.startTasks(status);
			result = new BasicResultObject("The space is now closed");
		} else {
			log.info("The space was correctly closed");
			result = new BasicResultObject("The space was correctly closed");
		}
		
		return result;
	}
}
