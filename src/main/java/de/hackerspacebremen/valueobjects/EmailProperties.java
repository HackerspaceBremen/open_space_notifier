package de.hackerspacebremen.valueobjects;

import lombok.Getter;
import lombok.Setter;

public final class EmailProperties {

	@Getter
	@Setter
	private boolean mailEnabled;
	
	@Getter
	@Setter
	private String senderName;
	
	@Getter
	@Setter
	private String receiverName;
	
	@Getter
	@Setter
	private String subjectTag;
	
	@Getter
	@Setter
	private String subjectOpened;
	
	@Getter
	@Setter
	private String subjectClosed;
	
	@Getter
	@Setter
	private String content;
	
	@Getter
	@Setter
	private String opened;
	
	@Getter
	@Setter
	private String closed;
	
	@Getter
	@Setter
	private String message;
	
	@Getter
	@Setter
	private String negatedOpened;
	
	@Getter
	@Setter
	private String negatedClosed;
	
	public String getContent(final String status, final String message, final String url, final String negStatus){
		String filledContent = this.content.replaceAll("\\$STATUS\\$", status);
		if(message != null && !message.isEmpty()){
			filledContent = filledContent.replaceAll("\\$MESSAGE\\$", "\n\n" + this.message + "\n'" + message + "'");
		}
		filledContent = filledContent.replaceAll("\\$URL\\$", url);
		filledContent = filledContent.replaceAll("\\$NEG_STATUS\\$", negStatus);
		
		return filledContent;
	}
}