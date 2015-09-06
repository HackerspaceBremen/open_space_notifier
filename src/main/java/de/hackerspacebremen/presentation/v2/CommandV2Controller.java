package de.hackerspacebremen.presentation.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hackerspacebremen.presentation.v1.CommandController;

@Controller
@RequestMapping({"/v2/cmd","/latest/cmd"})
public class CommandV2Controller extends CommandController{

	
	/**
	 * @deprecated see {@link TaskController#gcm()}
	 */
	@Deprecated
	@Override
	public void gcm() {
		// do nothing
	}
	
	@RequestMapping("/verifylogin")
	public void verifyLogin(){
		// TODO
	}
}
