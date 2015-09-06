package de.hackerspacebremen.commands.resultobjects.v13.feeds;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public final class FeedV13 {
	
	private String type;
	
	private String url;
}
