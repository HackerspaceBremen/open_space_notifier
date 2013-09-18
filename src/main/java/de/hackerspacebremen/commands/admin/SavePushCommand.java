package de.hackerspacebremen.commands.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;

public class SavePushCommand extends WebCommand{

	@Inject
	private PropertyService propertyService;
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(SavePushCommand.class.getName());
	
	@Override
	public void process() throws ServletException, IOException {
		final boolean gcmEnabled = req.getParameter("gcm_enable") != null;
		final String gcmKey = req.getParameter("gcm_key");
		final boolean apnsEnabled = req.getParameter("apns_enable") != null;
		final boolean mpnsEnabled = req.getParameter("mpns_enable") != null;
		BlobKey blobKey = null;
		String apnsPassword = null; 
		if(apnsEnabled){
			final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			final Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			if(blobs==null || blobs.isEmpty()){
				try{
					blobKey = new BlobKey("apns_certificate");
					blobstoreService.fetchData(blobKey, 0L, 1L);
				}catch(IllegalArgumentException e){
					blobKey = null;
				}
			}else{
				final List<BlobKey> blobKeys = blobs.get("apns_certificate");
				blobKey = blobKeys.get(0);
			}
			apnsPassword = req.getParameter("apns_password");
		}
		
		try {
			propertyService.savePushProperties(gcmEnabled, apnsEnabled, mpnsEnabled, gcmKey, blobKey.getKeyString(), apnsPassword);
			resp.sendRedirect("/admin/push");
		} catch (ValidationException e) {
			logger.warning("ValidationException occured with error code: " + e.getErrorCode());
			resp.sendRedirect("/admin/push");
		}
	}
}
