package de.hackerspacebremen.commands.helper;

import java.util.Date;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.properties.EmailProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;

/**
 * This class helps the application to start new tasks.
 * 
 * @author Steve
 * 
 */
@Component
public class StatusTaskStarter {

	private Provider<Date> dateProvider;
	private PropertyService propertyService;
	
	@Autowired
	public StatusTaskStarter(Provider<Date> dateProvider, PropertyService propertyService) {
		this.dateProvider = dateProvider;
		this.propertyService = propertyService;
	}

	public void startTasks(final SpaceStatus status) throws ValidationException {
		final boolean open = status.getStatus().equals(AppConstants.OPEN);
		final PushProperties pushProperties = propertyService
				.fetchProperties(PushProperties.class);
		final EmailProperties emailProperties = this.propertyService
				.fetchProperties(EmailProperties.class);
		if (emailProperties.isMailEnabled()) {
			this.startMailTask(status, open);
		}
		if (pushProperties.isGcmEnabled()) {
			this.startGCMTask(status, open);
		}
		if (pushProperties.isApnsEnabled()) {
			this.startAPNSTask(status, open);
		}
	}

	private void startAPNSTask(final SpaceStatus status, final boolean open) {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder.withUrl("/v2/task/apns");
		taskOpt.method(Method.POST);
		if (open) {
			taskOpt.taskName("task_apns_open_" + dateProvider.get().getTime());
		} else {
			taskOpt.taskName("task_apns_close_" + dateProvider.get().getTime());
		}
		taskOpt.param("statusId", String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}

	private void startMailTask(final SpaceStatus status, final boolean open) {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder.withUrl("/v2/task/mail");
		taskOpt.method(Method.POST);
		if (open) {
			taskOpt.taskName("task_mail_open" + dateProvider.get().getTime());
		} else {
			taskOpt.taskName("task_mail_close" + dateProvider.get().getTime());
		}
		taskOpt.param("statusId", String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}

	private void startGCMTask(final SpaceStatus status, final boolean open)
			throws ValidationException {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder.withUrl("/v2/task/gcm");
		taskOpt.method(Method.POST);
		if (open) {
			taskOpt.taskName("task_gcm_open_" + dateProvider.get().getTime());
		} else {
			taskOpt.taskName("task_gcm_close_" + dateProvider.get().getTime());
		}
		taskOpt.param("statusId", String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}
}