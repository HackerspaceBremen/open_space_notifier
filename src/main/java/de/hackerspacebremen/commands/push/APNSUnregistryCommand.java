package de.hackerspacebremen.commands.push;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.ApnsDataService;
import de.hackerspacebremen.domain.val.ValidationException;

@Component
public class APNSUnregistryCommand extends WebCommand{

	private ApnsDataService apnsDataService;
	
	@Autowired
	public APNSUnregistryCommand(ApnsDataService apnsDataService) {
		this.apnsDataService = apnsDataService;
	}
	
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
