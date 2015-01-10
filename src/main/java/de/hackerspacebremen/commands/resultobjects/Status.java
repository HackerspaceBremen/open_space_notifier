package de.hackerspacebremen.commands.resultobjects;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.format.LanguageFormat;
import de.hackerspacebremen.format.SpeakingDateFormat;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public final class Status {

	/**
	 * ST2.
	 */
	@JsonProperty("ST2")
	private String time;
	
	/**
	 * ST3.
	 */
	@JsonProperty("ST3")
	private String status;
	
	/**
	 * ST5.
	 */
	@JsonProperty("ST5")
	private String message;
	
	public Status(final SpaceStatus spaceStatus, final LanguageFormat format){
		this.message = spaceStatus.getMessage().getValue();
		this.status = spaceStatus.getStatus();
		if(format != null){
			final String statusTime = this.createTimeFormat(spaceStatus, format);
			if(statusTime != null){
				this.time = statusTime;
			}
		}
	}

	private String createTimeFormat(final SpaceStatus spaceStatus, final LanguageFormat format) {
		final String result;
		SpeakingDateFormat.setFormat(format); 
		final Date date = new Date(spaceStatus.getTime());
		if(format == LanguageFormat.GERMAN){
			if(spaceStatus.getStatus().equals(AppConstants.OPEN)){
				result = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " ge\u00f6ffnet";
			}else{
				result = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " geschlossen";
			}
		}else if(format == LanguageFormat.ENGLISH){
			if(spaceStatus.getStatus().equals(AppConstants.OPEN)){
				result = "The space is open since " + SpeakingDateFormat.format(date);
			}else{
				result = "The space is closed since " + SpeakingDateFormat.format(date);
			}
		}else{
			result = "" + spaceStatus.getTime();
		}
		return result;
	}
}
