package de.hackerspacebremen.commands.push;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.format.SpeakingDateFormat;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class APNSCommand extends WebCommand{
	
	@Inject
    @Proxy
	private SpaceStatusService statusService;
	
	@Inject
	@Proxy
    private APNSDataService apnsDataService;

	
	@Override
	public void process() throws ServletException, IOException {
		try{
			SpaceStatus status = statusService.currentStatus();
			final String statusId = req.getParameter("statusId");
			if(statusId != null && status.getId().equals(Long.valueOf(statusId))){
				status = statusService.currentCopyStatus();
				MessageFormat.fitSmallMessageSize(status);
				
				final Date date = new Date(status.getTime());
				final String statusShort;
				if(status.getStatus().equals(AppConstants.OPEN)){
					statusShort = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " ge\u00f6ffnet";
				}else{
					statusShort = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " geschlossen";
				}
				
				apnsDataService.sendMessageToDevices(statusShort, status.getMessage().toString());
				this.handleSuccess(new BasicResultObject("Messages were sent to the APNS server!"));
			}else{
				this.handleSuccess(new BasicResultObject("The given status id is not valid anymore! The message couldn't be send ...'"));
			}
		}catch(ValidationException ve){
			this.handleError(ve);
//		} catch (FormatException e) {
//			this.handleError(77);
//			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
	}
}
