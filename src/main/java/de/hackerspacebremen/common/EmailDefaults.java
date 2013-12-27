package de.hackerspacebremen.common;

public class EmailDefaults {

	private EmailDefaults(){
		// private constructor
	}
	
	public static final String EMAIL_DEFAULT_SENDER_NAME = "Hackerspace Bremen";
	
	public static final String EMAIL_DEFAULT_RECEIVER_NAME = "Hackerspace Bremen Mailingliste";
	
	public static final String EMAIL_DEFAULT_SUBJECT_TAG = "[Open-Space-Notifier]";
	
	public static final String EMAIL_DEFAULT_SUBJECT_OPENED = "Der Space wurde geöffnet";
	
	public static final String EMAIL_DEFAULT_SUBJECT_CLOSED = "Der Space wurde geschlossen";
	
	public static final String EMAIL_DEFAULT_CONTENT_PART1 = "\n\nDer Space wurde soeben ";
	
	public static final String EMAIL_DEFAULT_CONTENT_PART2 = "\n\nDer Space kann über die URL ";
	
	public static final String EMAIL_DEFAULT_CONTENT_PART3 = "wieder ";
	
	public static final String EMAIL_DEFAULT_CONTENT_PART4 = "\n\nFröhliches Hacken,\ndein Hackerspace Bremen Team";
	
	public static final String EMAIL_DEFAULT_OPENED = "geöffnet! Es wartet Mate im Space auf dich ... "
									+ "also mach dich auf den Weg.";
	
	public static final String EMAIL_DEFAULT_CLOSED = "geschlossen!";
	
	public static final String EMAIL_DEFAULT_MESSAGE = "\n\nEs wurde folgende Nachricht hinterlegt:\n'";
	
	public static final String EMAIL_DEFAULT_NEGATED_OPENED = "geschlossen werden!";
	
	public static final String EMAIL_DEFAULT_NEGATED_CLOSED = "geöffnet werden!";
}
