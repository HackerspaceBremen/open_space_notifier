package de.hackerspacebremen.presentation.v2;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.AuthAttemptService;
import de.hackerspacebremen.domain.BasicHTTPAuthenticationService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.domain.helper.StatusTaskStarter;
import de.hackerspacebremen.presentation.v1.CommandController;

@RestController
@RequestMapping({ "/v2/cmd", "/latest/cmd" })
public class CommandV2Controller extends CommandController {

	private AuthAttemptService authAttemptService;
	private BasicHTTPAuthenticationService authService;

	@Autowired
	public CommandV2Controller(ErrorMessages errorMessages, SpaceStatusService statusService,
			AuthAttemptService authAttemptService, BasicHTTPAuthenticationService authService,
			StatusTaskStarter statusTaskStarter) {
		super(errorMessages, statusService, authAttemptService, authService, statusTaskStarter, null);
		this.authAttemptService = authAttemptService;
		this.authService = authService;
	}

	/**
	 * @deprecated see {@link TaskController#gcm()}
	 */
	@Deprecated
	@Override
	public BasicResultObject gcm(String statusId, String format) {
		// do nothing
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/verifylogin", method = RequestMethod.POST)
	public BasicResultObject verifyLogin(@RequestParam(required = false) final String name,
			@RequestParam(required = false) final Boolean encoded, @RequestParam(required = false) final String pass,
			@RequestParam(required = false) final String message) throws UnsupportedEncodingException {

		BasicResultObject result = null;

		final String password = getPassword(encoded, pass);

		if (authAttemptService.checkAttemptMax(name)) {
			result = this.error(55);
		} else if (authService.authenticate(name, password)) {
			result = new BasicResultObject("Credentials are valid");
		} else {
			result = this.error(1);
		}

		return result;
	}
}
