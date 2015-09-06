package de.hackerspacebremen.commands;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.AuthAttemptService;
import de.hackerspacebremen.domain.BasicHTTPAuthenticationService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.util.Constants;

@Component
public class VerifyLoginCommand extends WebCommand{

	private BasicHTTPAuthenticationService authService;
	private AuthAttemptService authAttemptService;

	@Autowired
	public VerifyLoginCommand(BasicHTTPAuthenticationService authService, AuthAttemptService authAttemptService) {
		this.authService = authService;
		this.authAttemptService = authAttemptService;
	}
	
	@Override
	public void process() throws ServletException, IOException {
		
		final String name = this.req.getParameter("name");
		final String encoded = this.req.getParameter("encoded");		
		final String pass; 
		
		if(encoded != null && encoded.equals("true")){
			pass = URLDecoder.decode(this.req.getParameter("pass"),Constants.UTF8);
		}else{
			pass = this.req.getParameter("pass");
		}
		
		try {
			if(authAttemptService.checkAttemptMax(name)){
				this.handleError(55);
			}else if (authService.authenticate(name, pass)) {
				this.handleSuccess(new BasicResultObject("Credentials are valid"));
			}else{
				this.handleError(1);
			}
		} catch (ValidationException e) {
			this.handleError(e);
		}
		
		// closing all
		super.process();
	}
}