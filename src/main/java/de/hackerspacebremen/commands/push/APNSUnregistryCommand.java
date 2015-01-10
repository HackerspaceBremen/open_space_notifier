package de.hackerspacebremen.commands.push;

import java.io.IOException;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class APNSUnregistryCommand extends WebCommand{

	@Inject
	@Proxy
	private APNSDataService apnsDataService;
	
	@Override
	public void process() throws ServletException, IOException {
		try{
			apnsDataService.unregister(req.getParameter("deviceId"));
			this.handleSuccess(new BasicResultObject("You successfully unregistered"));
		}catch(ValidationException e){
			this.handleError(e);
		}
		
		super.process();
	}
}
