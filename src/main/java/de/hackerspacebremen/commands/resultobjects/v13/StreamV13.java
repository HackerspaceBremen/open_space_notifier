package de.hackerspacebremen.commands.resultobjects.v13;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class StreamV13 {

	private String m4;
	
	private String mjpeg;
	
	private String ustream;
}
