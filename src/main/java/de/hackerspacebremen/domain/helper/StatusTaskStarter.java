package de.hackerspacebremen.domain.helper;

import java.util.Date;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.validation.ValidationException;
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
		final PushProperties pushProperties = propertyService.fetchProperties(PushProperties.class);
		final EmailProperties emailProperties = this.propertyService.fetchProperties(EmailProperties.class);
		if (emailProperties.isMailEnabled()) {
			startTask(status, open, "mail");
		}
		if (pushProperties.isGcmEnabled()) {
			startTask(status, open, "gcm");
		}
		if (pushProperties.isApnsEnabled()) {
			startTask(status, open, "apns");
		}
	}

	private void startTask(final SpaceStatus status, final boolean open, String type) {
		final Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder.withUrl("/latest/task/" + type);
		taskOpt.method(Method.POST);
		if (open) {
			taskOpt.taskName("task_" + type + "_open_" + dateProvider.get().getTime());
		} else {
			taskOpt.taskName("task_" + type + "_close_" + dateProvider.get().getTime());
		}
		taskOpt.param("statusId", String.valueOf(status.getId().longValue()));
		queue.add(taskOpt);
	}
}