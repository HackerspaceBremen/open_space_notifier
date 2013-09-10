package de.hackerspacebremen.commands.push;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class APNSCommand extends WebCommand{
	
	@Inject
    @Proxy
	private SpaceStatusService statusService;
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(APNSCommand.class.getName());

	
	@Override
	public void process() throws ServletException, IOException {
		try{
			SpaceStatus status = statusService.currentStatus();
			final String statusId = req.getParameter("statusId");
			if(statusId != null && status.getId().equals(Long.valueOf(statusId))){
				status = statusService.currentCopyStatus();
				MessageFormat.fitSmallMessageSize(status);
				final String kind = req.getParameter("format");
				
				// TODO use new apnsService instead
				//gcmDataService.sendMessageToDevices(FormatFactory.getFormatter(kind).format(status, AppConstants.LEVEL_VIEW));
				this.handleSuccess("Messages were sent to the APNS server!", null);
			}else{
				this.handleSuccess("The given status id is not valid anymore! The message couldn't be send ...'", null);
			}
		}catch(ValidationException ve){
			this.handleError(ve);
		} /*catch (FormatException e) {
			this.handleError(77);
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}*/
	}
}
