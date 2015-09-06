package de.hackerspacebremen.presentation.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/cmd","/v1/cmd"})
public class CommandController {

	@RequestMapping("/gcm")
	public void gcm(){
		// TODO
	}
	
	@RequestMapping("/open")
	public void open(){
		// TODO
	}
	
	@RequestMapping("/close")
	public void close(){
		// TODO
	}
	
	@RequestMapping("/message")
	public void message(){
		
	}
}
