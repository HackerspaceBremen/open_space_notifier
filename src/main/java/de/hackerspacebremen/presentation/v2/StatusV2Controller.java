package de.hackerspacebremen.presentation.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hackerspacebremen.presentation.v1.StatusController;

@Controller
@RequestMapping({"/v2/status","/latest/status"})
public class StatusV2Controller extends StatusController{

	
	
	@RequestMapping("/{api}")
	public void forApi(){
		// TODO
	}
	
	@RequestMapping(".rss")
	public void rss(){
		// TODO
	}
	
	@RequestMapping(".atom")
	public void atom(){
		// TODO
	}
}
