package de.hackerspacebremen.presentation.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/v2/apns","/latest/apns"})
public class ApnsController {

	@RequestMapping("/register")
	public void register(){
		// TODO
	}
	
	@RequestMapping("/unregister")
	public void unregister(){
		// TODO
	}
}
