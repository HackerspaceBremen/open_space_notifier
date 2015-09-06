package de.hackerspacebremen.valueobjects.properties;

import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_CLOSED;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_CONTENT;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_MESSAGE;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_NEGATED_CLOSED;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_NEGATED_OPENED;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_OPENED;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_RECEIVER_NAME;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_SENDER_NAME;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_SUBJECT_CLOSED;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_SUBJECT_OPENED;
import static de.hackerspacebremen.common.EmailDefaults.EMAIL_DEFAULT_SUBJECT_TAG;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_CLOSED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_CONTENT;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_MESSAGE;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_NEGATED_CLOSED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_NEGATED_OPENED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_OPENED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_RECEIVER_NAME;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SENDER_NAME;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SUBJECT_CLOSED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SUBJECT_OPENED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SUBJECT_TAG;
import static de.hackerspacebremen.common.PropertyConstants.MAIL_ENABLED;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class EmailProperties {

	@DataProperty(key = MAIL_ENABLED, defaultValue = "false")
	private boolean mailEnabled;

	@DataProperty(key = EMAIL_SENDER_NAME, defaultValue = EMAIL_DEFAULT_SENDER_NAME)
	private String senderName;

	@DataProperty(key = EMAIL_RECEIVER_NAME, defaultValue = EMAIL_DEFAULT_RECEIVER_NAME)
	private String receiverName;

	@DataProperty(key = EMAIL_SUBJECT_TAG, defaultValue = EMAIL_DEFAULT_SUBJECT_TAG)
	private String subjectTag;

	@DataProperty(key = EMAIL_SUBJECT_OPENED, defaultValue = EMAIL_DEFAULT_SUBJECT_OPENED)
	private String subjectOpened;

	@DataProperty(key = EMAIL_SUBJECT_CLOSED, defaultValue = EMAIL_DEFAULT_SUBJECT_CLOSED)
	private String subjectClosed;

	@DataProperty(key = EMAIL_CONTENT, defaultValue = EMAIL_DEFAULT_CONTENT)
	private String content;

	@DataProperty(key = EMAIL_OPENED, defaultValue = EMAIL_DEFAULT_OPENED)
	private String opened;

	@DataProperty(key = EMAIL_CLOSED, defaultValue = EMAIL_DEFAULT_CLOSED)
	private String closed;

	@DataProperty(key = EMAIL_MESSAGE, defaultValue = EMAIL_DEFAULT_MESSAGE)
	private String message;

	@DataProperty(key = EMAIL_NEGATED_OPENED, defaultValue = EMAIL_DEFAULT_NEGATED_OPENED)
	private String negatedOpened;

	@DataProperty(key = EMAIL_NEGATED_CLOSED, defaultValue = EMAIL_DEFAULT_NEGATED_CLOSED)
	private String negatedClosed;

	public String getContent(final String status, final String message,
			final String url, final String negStatus) {
		String filledContent = this.content.replaceAll("\\$STATUS\\$", status);
		if (message == null || message.isEmpty()) {
			filledContent = filledContent.replaceAll("\\$MESSAGE\\$", "\n\n");
		} else {
			filledContent = filledContent.replaceAll("\\$MESSAGE\\$", "\n\n"
					+ this.message + "\n'" + message + "'");
		}
		filledContent = filledContent.replaceAll("\\$URL\\$", url);
		filledContent = filledContent.replaceAll("\\$NEG_STATUS\\$", negStatus);

		return filledContent;
	}
}