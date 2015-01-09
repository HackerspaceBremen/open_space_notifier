package de.hackerspacebremen.commands.push;

import java.io.IOException;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class APNSRegistryCommand extends WebCommand{

	@Inject
	@Proxy
	private APNSDataService apnsDataService;
	
	@Override
	public void process() throws ServletException, IOException {
		try{
			apnsDataService.register(req.getParameter("deviceId"), req.getParameter("deviceToken"));
			this.handleSuccess("Your registry was successful", null);
		}catch(ValidationException e){
			this.handleError(e);
		}
		
		super.process();
	}
}
