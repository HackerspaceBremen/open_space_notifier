package de.hackerspacebremen.commands.resultobjects.v13.feeds;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class Feeds {

	private FeedV13 blog;
	
	private FeedV13 wiki;
	
	private FeedV13 calendar;
	
	private FeedV13 flickr;
}
