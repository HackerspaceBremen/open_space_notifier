package de.hackerspacebremen.valueobjects;

import lombok.Getter;
import lombok.Setter;

public final class EmailProperties {

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
	@Deprecated
	private String contentPart1;
	
	@Getter
	@Setter
	@Deprecated
	private String contentPart2;
	
	@Getter
	@Setter
	@Deprecated
	private String contentPart3;
	
	@Getter
	@Setter
	@Deprecated
	private String contentPart4;
	
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
		String filledContent = status.replaceAll("$STATUS$", status);
		if(message != null && !message.isEmpty()){
			filledContent = filledContent.replaceAll("$MESSAGE$", this.message + "\n'" + message + "'");
		}
		filledContent = filledContent.replaceAll("$URL$", url);
		filledContent = filledContent.replaceAll("$NEG_STATUS$", negStatus);
		
		return filledContent;
	}
}
