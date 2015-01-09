package de.hackerspacebremen.commands.resultobjects.v13;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class SpaceFEDV13 {

	private boolean spacenet;
	
	private boolean spacesaml;
	
	private boolean spacephone;
}
