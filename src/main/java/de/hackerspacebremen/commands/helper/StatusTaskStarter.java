package de.hackerspacebremen.commands.helper;

import java.util.Date;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.PushProperties;

/**
 * This class helps the application to start new tasks.
 * 
 * @author Steve
 *
 */
public final class StatusTaskStarter {
	
	@Inject
	private Provider<Date> dateProvider;
	
	@Inject
	private PropertyService propertyService;
	
	public void startTasks(final SpaceStatus status) throws ValidationException{
		final boolean open = status.getStatus().equals(AppConstants.OPEN);
		final PushProperties properties = propertyService.fetchPushProperties();
		if(properties.isMailEnabled()){
			this.startMailTask(status, open);
		}
		if(properties.isGcmEnabled()){
			this.startGCMTask(status, open);
		}
		if(properties.isApnsEnabled()){
			this.startAPNSTask(status, open);
		}
	}
	
	
	private void startAPNSTask(final SpaceStatus status, final boolean open) {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder
				.withUrl("/v2/task/apns");
		taskOpt.method(Method.POST);
		if(open){
			taskOpt.taskName("task_apns_open_"
					+ dateProvider.get().getTime());
		}else{
			taskOpt.taskName("task_apns_close_"
					+ dateProvider.get().getTime());
		}
		taskOpt.param(
				"statusId",
				String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}

	private void startMailTask(final SpaceStatus status, final boolean open) {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder
				.withUrl("/v2/task/mail");
		taskOpt.method(Method.POST);
		if(open){
			taskOpt.taskName("task_mail_open"
					+ dateProvider.get().getTime());
		}else{
			taskOpt.taskName("task_mail_close"
					+ dateProvider.get().getTime());
		}
		taskOpt.param(
				"statusId",
				String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}

	private void startGCMTask(final SpaceStatus status, final boolean open) throws ValidationException {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder
				.withUrl("/v2/task/gcm");
		taskOpt.method(Method.POST);
		if(open){
			taskOpt.taskName("task_gcm_open_"
					+ dateProvider.get().getTime());
		}else{
			taskOpt.taskName("task_gcm_close_"
					+ dateProvider.get().getTime());
		}
		taskOpt.param(
				"statusId",
				String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}
}