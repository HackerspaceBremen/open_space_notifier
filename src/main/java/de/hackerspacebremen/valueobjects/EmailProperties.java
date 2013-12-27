package de.hackerspacebremen.valueobjects;

public final class EmailProperties {

	private String senderName;
	
	private String receiverName;
	
	private String subjectTag;
	
	private String subjectOpened;
	
	private String subjectClosed;
	
	private String contentPart1;
	
	private String contentPart2;
	
	private String contentPart3;
	
	private String contentPart4;
	
	private String opened;
	
	private String closed;
	
	private String message;
	
	private String negatedOpened;
	
	private String negatedClosed;
	
	/**
	 * @return the subjectOpened
	 */
	public String getSubjectOpened() {
		return subjectOpened;
	}

	/**
	 * @param subjectOpened the subjectOpened to set
	 */
	public void setSubjectOpened(String subjectOpened) {
		this.subjectOpened = subjectOpened;
	}

	/**
	 * @return the subjectClosed
	 */
	public String getSubjectClosed() {
		return subjectClosed;
	}

	/**
	 * @param subjectClosed the subjectClosed to set
	 */
	public void setSubjectClosed(String subjectClosed) {
		this.subjectClosed = subjectClosed;
	}

	/**
	 * @return the contentPart1
	 */
	public String getContentPart1(final String status, final String messageVariable, final String messageVariable2) {
		return contentPart1 + status + messageVariable + messageVariable2;
	}

	/**
	 * @param contentPart1 the contentPart1 to set
	 */
	public void setContentPart1(String contentPart1) {
		this.contentPart1 = contentPart1;
	}

	/**
	 * @return the opened
	 */
	public String getOpened() {
		return opened;
	}

	/**
	 * @param opened the opened to set
	 */
	public void setOpened(String opened) {
		this.opened = opened;
	}

	/**
	 * @return the closed
	 */
	public String getClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(String closed) {
		this.closed = closed;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the negatedOpened
	 */
	public String getNegatedOpened() {
		return negatedOpened;
	}

	/**
	 * @param negatedOpened the negatedOpened to set
	 */
	public void setNegatedOpened(String negatedOpened) {
		this.negatedOpened = negatedOpened;
	}

	/**
	 * @return the negatedClosed
	 */
	public String getNegatedClosed() {
		return negatedClosed;
	}

	/**
	 * @param negatedClosed the negatedClosed to set
	 */
	public void setNegatedClosed(String negatedClosed) {
		this.negatedClosed = negatedClosed;
	}

	/**
	 * @return the subjectTag
	 */
	public String getSubjectTag() {
		return subjectTag;
	}

	/**
	 * @param subjectTag the subjectTag to set
	 */
	public void setSubjectTag(String subjectTag) {
		this.subjectTag = subjectTag;
	}

	/**
	 * @return the contentPart2
	 */
	public String getContentPart2(final String url) {
		return contentPart2 + url;
	}

	/**
	 * @param contentPart2 the contentPart2 to set
	 */
	public void setContentPart2(String contentPart2) {
		this.contentPart2 = contentPart2;
	}

	/**
	 * @return the contentPart3
	 */
	public String getContentPart3(final String negatedStatus) {
		return contentPart3 + negatedStatus;
	}

	/**
	 * @param contentPart3 the contentPart3 to set
	 */
	public void setContentPart3(String contentPart3) {
		this.contentPart3 = contentPart3;
	}

	/**
	 * @return the contentPart4
	 */
	public String getContentPart4() {
		return contentPart4;
	}

	/**
	 * @param contentPart4 the contentPart4 to set
	 */
	public void setContentPart4(String contentPart4) {
		this.contentPart4 = contentPart4;
	}

	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
}
