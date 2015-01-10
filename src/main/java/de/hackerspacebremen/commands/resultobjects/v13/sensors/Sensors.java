package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class Sensors {

	private List<Temperature> temperature;
	
	@JsonProperty("door_locked")
	private List<DoorLocked> doorLocked;
	
	private List<Barometer> barometer;
	
	private Radiation radiation;
	
	private List<Humidity> humidity;
	
	@JsonProperty("beverage_supply")
	private List<BeverageSupply> beverageSupply;
	
	@JsonProperty("power_consumption")
	private List<PowerConsumption> powerConsumption;
	
	private List<Wind> wind;
	
	@JsonProperty("network_connections")
	private List<NetworkConnections> networkConnections;
	
	@JsonProperty("account_balance")
	private List<AccountBalance> accountBalance;
	
	@JsonProperty("total_member_count")
	private List<MemberCount> totalMemberCount;
	
	@JsonProperty("people_now_present")
	private List<PresentPeople> peopleNowPresent;
}
