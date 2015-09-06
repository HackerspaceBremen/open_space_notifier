package de.hackerspacebremen.presentation.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/v2/task","/latest/task"})
public class TaskController {

	@RequestMapping("/gcm")
	public void gcm(){
		// TODO
	}
	
	@RequestMapping("/apns")
	public void apns(){
		// TODO
	}
	
	@RequestMapping("/mail")
	public void mail(){
		// TODO
	}
}
