package de.hackerspacebremen.commands.helper;

import java.util.Date;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.GCMAuth;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;
import de.hackerspacebremen.util.Encryption;

/**
 * This class helps the application to start new tasks.
 * 
 * @author Steve
 *
 */
public final class StatusTaskStarter {

	@Inject
	@Proxy
	private GCMAuthService gcmAuthService;
	
	public void startTasks(final SpaceStatus status) throws ValidationException{
		final boolean open = status.getStatus().equals(AppConstants.OPEN);
		this.startMailTask(status, open);
		this.startGCMTask(status, open);
		this.startAPNSTask(status, open);
	}
	
	
	private void startAPNSTask(final SpaceStatus status, final boolean open) {
		// TODO needs to be implemented
	}

	private void startMailTask(final SpaceStatus status, final boolean open) {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder
				.withUrl("/v2/task/mail");
		taskOpt.method(Method.POST);
		if(open){
			taskOpt.taskName("task_mail_open"
					+ new Date().getTime());
		}else{
			taskOpt.taskName("task_mail_close"
					+ new Date().getTime());
		}
		taskOpt.param(
				"statusId",
				String.valueOf(status.getId()));
		queue.add(taskOpt);
	}

	private void startGCMTask(final SpaceStatus status, final boolean open) throws ValidationException {
		final GCMAuth authToken = gcmAuthService.getAuthToken();
		if (authToken != null) {
			final Queue queue = QueueFactory.getDefaultQueue();
			TaskOptions taskOpt = TaskOptions.Builder
					.withUrl("/v2/task/gcm");
			taskOpt.method(Method.POST);
			if(open){
				taskOpt.taskName("task_gcm_open_"
						+ new Date().getTime());
			}else{
				taskOpt.taskName("task_gcm_close_"
						+ new Date().getTime());
			}
			taskOpt.param(
					"token",
					Encryption.encryptSHA256(authToken.getToken()
							+ status.getId()));
			queue.add(taskOpt);
		}
	}
}
