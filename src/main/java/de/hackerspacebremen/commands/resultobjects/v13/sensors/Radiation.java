package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class Radiation {

	private List<RadiationSensor> alpha;
	
	private List<RadiationSensor> beta;
	
	private List<RadiationSensor> gamma;
	
	@JsonProperty("beta_gamma")
	private List<RadiationSensor> betaGamma;
}
