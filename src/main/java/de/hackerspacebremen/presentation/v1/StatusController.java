package de.hackerspacebremen.presentation.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/status","/v1/status"})
public class StatusController {

	@RequestMapping
	@ResponseBody
	public String status(){
		return "test";
	}
}
