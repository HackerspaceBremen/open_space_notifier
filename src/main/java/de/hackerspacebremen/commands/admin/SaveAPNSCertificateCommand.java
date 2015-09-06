package de.hackerspacebremen.commands.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public final class SaveAPNSCertificateCommand extends WebCommand{

    
	private PropertyService propertyService;
	
	@Autowired
	public SaveAPNSCertificateCommand(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	@Override
	public void process() throws ServletException, IOException {
		final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		final Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		BlobKey blobKey = null;
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
		
		final String apnsFileString;
		if(blobKey==null){
			apnsFileString="";
		}else{
			apnsFileString=blobKey.getKeyString();
		}
		try {
			this.propertyService.saveAPNSCertificate(apnsFileString);
			req.setAttribute("result", "SUCCESS");
			req.setAttribute("code", Integer.valueOf(0));
			req.getRequestDispatcher("/admin/certificate").forward(req, resp);
		} catch (ValidationException e) {
			log.warn("ValidationException occured with error code: {}", e.getErrorCode());
			req.setAttribute("error", e.getMessage());
			req.setAttribute("result", "ERROR");
			req.setAttribute("code", e.getErrorCode());
			req.getRequestDispatcher("/admin/certificate").forward(req, resp);
		}
	}
}
